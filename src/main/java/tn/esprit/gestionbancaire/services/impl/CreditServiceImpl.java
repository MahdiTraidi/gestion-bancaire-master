package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.esprit.gestionbancaire.enums.AdministrativeDocumentType;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.exception.InvalidOperationException;
import tn.esprit.gestionbancaire.model.AdministrativeDocument;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.model.CreditTemplate;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.repository.CreditRepository;
import tn.esprit.gestionbancaire.services.*;
import tn.esprit.gestionbancaire.utils.Utility;
import tn.esprit.gestionbancaire.validator.CreditValidator;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class CreditServiceImpl implements CreditService {

    private CreditRepository creditRepository;
    private CreditSimulateurService creditSimulateurService;
    private MailService mailService;
    private CreditTemplateService creditTemplateService;
    private IOperationService operationService;

    @Autowired
    public CreditServiceImpl(CreditRepository creditRepository,
                             @Lazy CreditSimulateurService creditSimulateurService,
                             MailService mailService,
                             CreditTemplateService creditTemplateService,
                             IOperationService operationService) {
        this.creditRepository = creditRepository;
        this.creditSimulateurService = creditSimulateurService;
        this.mailService = mailService;
        this.creditTemplateService = creditTemplateService;
        this.operationService = operationService;

    }



    @Override
    public Credit save(Credit credit) {
        List<String> errors = CreditValidator.validate(credit,creditTemplateService.findById(credit.getCreditTemplate().getId()));
        if (!errors.isEmpty()) {
            log.error("Credit is not valid {}", errors);
            throw new InvalidEntityException("Credit is not valid", ErrorCodes.CREDIT_NOT_VALID, errors);
        }
        //if(credit.getCreditTemplate().getId() == 2){
        //  credit.setAmount(creditSimulateurService.vehicleCredit(credit.getAmount(),credit.getVehicleFiscalPower(),credit.getSelfFinancing(),credit.getRepaymentPeriod()).entrySet().stream().mapToDouble(e->e.getValue()).sum());
        //}
        User currentUser = Utility.getCurrenUser().getUser();
        credit.setUser(currentUser);
        credit.setCreationDate(Instant.now());
        credit.setLastModifiedDate(Instant.now());
        credit.setCreditStatus(CreditStatus.OPEN);
        mailService.creditNotify(credit,null);
        return  creditRepository.save(credit);
    }

    @Override
    public Credit updateCreditStatus(Integer idCredit, CreditStatus creditStatus) {
        isAdmin();
        checkIdCredit(idCredit);
        if (!StringUtils.hasLength(String.valueOf(creditStatus))) {
            log.error("Credit status is NULL");
            throw new InvalidOperationException("IS not allowed to chagne status to null",
                    ErrorCodes.CREDIT_NON_MODIFIABLE);
        }
        Credit credit = checkCreditStatus(idCredit);
        if(creditStatus.equals(CreditStatus.ACCEPTED)){
            Map<Integer,Double> similation = new HashMap<>();
            credit = checkCreditStatus(idCredit);

            List<String> notes = credit.getNotes();
            notes.add("Your Credit Request Now is :" + creditStatus);
            credit.setNotes(notes);
            credit.setLastModifiedDate(Instant.now());

            if( credit.getCreditTemplate().getTitle().equals("Vehicle") ){
                 similation = creditSimulateurService.vehicleCredit(credit.getAmount(),credit.getVehicleFiscalPower(),credit.getSelfFinancing(),credit.getRepaymentPeriod());
            } else if ( credit.getCreditTemplate().getTitle().equals("Personal") ){
                similation = creditSimulateurService.prsonalCredit(credit.getAmount(),credit.getRepaymentPeriod());
            }
            log.info(""+ similation);
            //call operation service
            operationService.processCreditBill(similation);
            credit.setCreditStatus(creditStatus);
            credit.setArchived(true);
            //operationService.p
            mailService.creditNotify(credit,creditStatus);
        }else if(creditStatus.equals(CreditStatus.REFUSED)){
            credit = checkCreditStatus(idCredit);
            credit.setCreditStatus(creditStatus);
            credit.setArchived(true);
            List<String> notes = credit.getNotes();
            notes.add("Your Credit Request Now is :" + creditStatus);
            credit.setNotes(notes);
            credit.setLastModifiedDate(Instant.now());
            //send email to client
            mailService.creditNotify(credit,creditStatus);
        }else if(creditStatus.equals(CreditStatus.OPEN)){
            credit = checkCreditStatus(idCredit);
            if (credit.getCreditStatus().equals(CreditStatus.DELETED)){
                throw new InvalidOperationException("Credit already deleted not possible to OPEN it",
                        ErrorCodes.CREDIT_NON_MODIFIABLE);
            }else {
                credit.setCreditStatus(creditStatus);
                List<String> notes = credit.getNotes();
                notes.add("Your Credit Request Now is :" + creditStatus);
                credit.setNotes(notes);
                credit.setLastModifiedDate(Instant.now());
                //send email to client
                mailService.creditNotify(credit, creditStatus);
            }
        }else{
            credit = checkCreditStatus(idCredit);
            credit.setCreditStatus(creditStatus);
            List<String> notes = credit.getNotes();
            notes.add("Your Credit Request Now is " + creditStatus);
            credit.setNotes(notes);
            credit.setLastModifiedDate(Instant.now());
            //send email to client
            mailService.creditNotify(credit,creditStatus);
        }


        return creditRepository.save(credit);
    }

    @Override
    public Credit findById(Integer id) {
        if (id == null) {
            log.error("Credit ID is null");
            return null;
        }
        Optional<Credit> credit = creditRepository.findById(id);
        if(credit.isPresent()){
            checkAuthorization(credit.get().getUser());
//            if ((credit.get().getUser() != currentUser) || !currentUser.getRoles().equals("admin") ){
//                throw new InvalidOperationException("UNAUTHORIZED USER MUST BE ADMIN OR CREDIT OWNER",
//                        ErrorCodes.UNAUTHORIZED);
//            }
        }
        return creditRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "There is no credit found with ID = " + id,
                        ErrorCodes.CREDIT_NOT_FOUND)
        );
    }


    @Override
    public List<Credit> findAll() {
        isAdmin();
        return creditRepository.findAll().stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Credit> findAllByUser(Integer id) {
        List<Credit> credit = creditRepository.findAllByUserId(id);
        if(credit.size()!=0) {
            checkAuthorization(credit.get(0).getUser());
        }
        return creditRepository.findAllByUserId(id);
    }

    @Override
    public List<Credit> findAllNotArchived(Boolean archived) {
        isAdmin();
        return creditRepository.findAllByArchived(archived).stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Credit> findAllByCreditStatus(CreditStatus creditStatus) {
        isAdmin();
        return creditRepository.findAllByCreditStatus(creditStatus);
    }

    //For admin
    @Override
    public void delete(Integer id) {

        if (id == null) {
            log.error("Credit ID is null");
            return;
        }

        Optional<Credit> credit = creditRepository.findById(id);

            if (credit.isPresent() && !credit.get().isArchived()){
                if((credit.get().getCreditStatus().equals(CreditStatus.ACCEPTED) || credit.get().getCreditStatus().equals(CreditStatus.REFUSED))) {
                    throw new InvalidOperationException("Faild to delete credit, Credit must be in 'OPEN' or 'IN_PROGRESS'", ErrorCodes.CREDIT_IS_NOT_CLOSED);
                }else {
                    credit.get().setCreditStatus(CreditStatus.DELETED);
                    credit.get().setArchived(true);
                    List<String> notes = credit.get().getNotes();
                    notes.add("Your Credit Request Now is " + CreditStatus.DELETED);
                    credit.get().setNotes(notes);
                    credit.get().setLastModifiedDate(Instant.now());
                    //send email to client
                    mailService.creditNotify(credit.get(),CreditStatus.DELETED);
                    creditRepository.save(credit.get());
                }

            }else {
                throw new InvalidOperationException("Faild to delete credit, Credit is not exist or already deleted", ErrorCodes.CREDIT_IS_NOT_CLOSED);
            }
    }


    @Override
    public List<String> addNote(Integer id,String note) {
        isAdmin();
        checkIdCredit(id);
        Credit credit = creditRepository.getById(id);
        List<String> notes = credit.getNotes();
        notes.add(note);
        credit.setNotes(notes);
        credit.setLastModifiedDate(Instant.now());
        return notes;
    }

    @Override
    public long countCreditByCreditStatus(CreditStatus status) {
        isAdmin();
        return creditRepository.countCreditByCreditStatus(status);
    }

    @Override
    public List<Credit> autoValidate() {
        isAdmin();
        //list of updated credit
        List<Credit> updated = new ArrayList<>();
        //list of opened credit
        List<Credit> credits = creditRepository.findAllByCreditStatus(CreditStatus.OPEN);
        //Administrative Document Type list
        List<String> doctypes = new ArrayList<>();

        for(Credit credit : credits){
            Instant now = Instant.now();
            Instant creationDate = credit.getCreationDate();
            long days = creationDate.until(now, ChronoUnit.DAYS);
            if ( credit.getCreditTemplate().getTitle().equals("Vehicle")){
                doctypes = Stream.of(AdministrativeDocumentType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList());
            }else if (credit.getCreditTemplate().getTitle().equals("Personal") ){
                doctypes = Stream.of(AdministrativeDocumentType.values())
                        .map(Enum::name)
                        .collect(Collectors.toList());
                doctypes.remove(3);
            }

            if (days >= 2){
                doctypes.stream().forEach(e -> log.info("Docs Types : " + e));

                //test for credit Type
                List<String> administrativeDocumetTypes = new ArrayList<>();
                for (AdministrativeDocument administrativeDocument : credit.getAdministrativeDocuments()){
                    administrativeDocumetTypes.add(administrativeDocument.getAdministrativeDocumentType().toString());
                }
                int i = 0;
                while ( i < doctypes.size() && administrativeDocumetTypes.contains(doctypes.get(i)) ){
                    log.info("Credit N: "+credit.getId() +" have doc of type "+ doctypes.get(i));
                    i++;
                }
                boolean existe = (i == doctypes.size());
                administrativeDocumetTypes.stream().forEach(e -> log.info(e));
                    log.info("Existe : "+existe);
                if(!existe){
                    updated.add(credit);
                    List<String> notes = credit.getNotes();
                    if ( credit.getCreditTemplate().getCreditType().equals("Vehicle")){
                        notes.add("Auto Validator : You need to add all needed Administrative Documents type '    INSURANCE,\n" +
                                "    CNSS,\n" +
                                "    WORK_CONTRACT,\n" +
                                "    FACTURE");
                    }else {
                        notes.add("Auto Validator : You need to add all needed Administrative Documents type '    INSURANCE,\n" +
                                "    CNSS,\n" +
                                "    WORK_CONTRACT,\n");
                    }
                    notes.add("Your Credit Request Now is " + CreditStatus.WAITING);
                    credit.setNotes(notes);
                    credit.setLastModifiedDate(Instant.now());
                    credit.setCreditStatus(CreditStatus.WAITING);
                    mailService.creditNotify(credit,CreditStatus.WAITING);
                    creditRepository.save(credit);
                }

            }
        }
        log.info("Updated Credit list :");
        updated.stream().forEach(e-> log.info("Credit ID: "+e.getId()));
        return updated;
    }

    @Override
    public Map<String, Integer> mostOpenedCreditByType() {
        isAdmin();
        Map<String, Integer> map = new LinkedHashMap<>();
        Integer vehicleCredit = creditRepository.countCreditByCreditTemplateTitle("Vehicle");
        Integer prsonalCredit = creditRepository.countCreditByCreditTemplateTitle("Personal");
        map.put("Vehicle",vehicleCredit);
        map.put("Personal",prsonalCredit);
        if( vehicleCredit == prsonalCredit ){
            map.put("Personal and Vehicle are equals ",prsonalCredit);
        }else if (vehicleCredit > prsonalCredit){
            map.put("Vehicle credit is the most used credit ",vehicleCredit);
        }else{
            map.put("Personal credit is the most used credit ",prsonalCredit);
        }
        return map;
    }

    private void checkIdCredit(Integer idCredit) {
        if (idCredit == null) {
            log.error("Credit ID is NULL");
            throw new InvalidOperationException("ID is null",
                    ErrorCodes.CREDIT_IS_NULL);
        }
    }

    private Credit checkCreditStatus(Integer idCredit) {
        Credit credit = findById(idCredit);
        if (credit.isCreditClosed()) {
            throw new InvalidOperationException("Is not allowed to change Credit status when he's done", ErrorCodes.CREDIT_NON_MODIFIABLE);
        }
        return credit;
    }
    private void isAdmin() {
        User currentUser = Utility.getCurrenUser().getUser();
        if(!currentUser.getRoles().equals("admin")) {
            throw new InvalidOperationException("UNAUTHORIZED USER MUST BE ADMIN",
                    ErrorCodes.UNAUTHORIZED);
            }
    }
    private void checkAuthorization(User user){
        User currentUser = Utility.getCurrenUser().getUser();
        if ((!user.getId().equals(currentUser.getId())) && !currentUser.getRoles().equals("admin") ){
                throw new InvalidOperationException("UNAUTHORIZED USER MUST BE ADMIN OR CREDIT OWNER",
                        ErrorCodes.UNAUTHORIZED);
            }

    }
}
