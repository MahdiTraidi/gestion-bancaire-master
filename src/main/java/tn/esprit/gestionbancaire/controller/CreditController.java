package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CreditApi;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.services.CreditService;

import java.time.Instant;
import java.util.List;
import java.util.Map;


@RestController
public class CreditController implements CreditApi {

    private CreditService creditService;

    @Autowired
    public CreditController(
            CreditService creditService
    ) {
        this.creditService = creditService;
    }
    @Override
    public ResponseEntity<Credit> save(Credit credit) {
        credit.setLastModifiedDate(Instant.now());
        credit.setCreationDate(Instant.now());
        return ResponseEntity.ok(creditService.save(credit));
    }

    @Override
    public ResponseEntity<Credit> updateCreditStatus(Integer idCredit, CreditStatus creditStatus) {
        return ResponseEntity.ok(creditService.updateCreditStatus(idCredit, creditStatus));
    }

    @Override
    public ResponseEntity<Credit> updateCredit(Integer idCredit) {
        Credit credit = creditService.findById(idCredit);
        credit.setLastModifiedDate(Instant.now());
        return ResponseEntity.ok(creditService.save(credit));
    }

    @Override
    public void deleteCredit(Integer idCredit) {
        creditService.delete(idCredit);
    }

    @Override
    public List<Credit> findAllByArchived(Boolean archived) {
        return  creditService.findAllNotArchived(archived);
    }

    @Override
    public List<Credit> findAllByUser(Integer id) {
        return creditService.findAllByUser(id);
    }

    @Override
    public List<Credit> findAll() {
        return creditService.findAll();
    }

    @Override
    public List<Credit> findAllByStatus(CreditStatus status) {
        return creditService.findAllByCreditStatus(status);
    }

    @Override
    public List<String> addNote(Integer id, String note) {
        return creditService.addNote(id,note);
    }

    @Override
    public Credit findCreditById(Integer id) {
        return creditService.findById(id);
    }

    @Override
    public Map<String,Integer> mostUsedCredit() {
        return creditService.mostOpenedCreditByType();
    }


}
