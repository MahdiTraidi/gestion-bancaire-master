package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.AccountRequest;
import tn.esprit.gestionbancaire.repository.AccountRequestRepository;
import tn.esprit.gestionbancaire.repository.ClientRepository;
import tn.esprit.gestionbancaire.services.AccountRequestService;

import java.util.List;
import java.util.Optional;

import static tn.esprit.gestionbancaire.utils.Constants.*;

@Service
@Slf4j
public class AccountRequestServiceImpl implements AccountRequestService {

    private AccountRequestRepository accountRequestRepository;
    private ClientRepository clientRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public AccountRequestServiceImpl(AccountRequestRepository accountRequestRepository,
                                     ClientRepository clientRepository, @Qualifier("getJavaMailSender") JavaMailSender javaMailSender) {
        this.accountRequestRepository = accountRequestRepository;
        this.clientRepository = clientRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public AccountRequest save(AccountRequest accountRequest, boolean newClient) {
        if (newClient) {
            clientRepository.save(accountRequest.getClient());
        }
        return accountRequestRepository.save(accountRequest);
    }

    @Override
    public AccountRequest findById(long id) {
        return accountRequestRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no account request with the ID: " + id,
                        ErrorCodes.ACCOUNT_REQUEST_NOT_FOUND)
        );
    }

    @Override
    public List<AccountRequest> findAll() {
        return accountRequestRepository.findAll();
    }

    @Override
    public List<AccountRequest> findByRequestStatus(AccountRequestStatus accountRequestStatus) {
        return accountRequestRepository.findByAccountRequestStatus(accountRequestStatus);
    }

    @Override
    public AccountRequest changeStatus(long requestId, AccountRequestStatus requestStatus) {
        AccountRequest byId = this.findById(requestId);
        byId.setAccountRequestStatus(requestStatus);
        return accountRequestRepository.save(byId);
    }

    @Override
    public void delete(long id) {
        Optional<AccountRequest> byId = accountRequestRepository.findById(id);
        if (byId.isPresent()) {
            accountRequestRepository.deleteById(id);
        } else {
            log.error("There is no account request with the ID: " + id);
            throw new EntityNotFoundException("There is no account request with the ID: " + id,
                    ErrorCodes.ACCOUNT_REQUEST_NOT_FOUND);
        }
    }

    @Override
    public void sendRequestValidationMail(AccountRequest byId){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        StringBuilder emailBody = new StringBuilder("Dear Mr/Mrs " + byId.getClient().getFirstName() +
                " " + byId.getClient().getLastName());
        emailBody.append("\n")
                .append("\n")
                .append("This is with reference to your account creation request dated ")
                .append(byId.getCreationDate())
                .append(", and we are happy to inform you that your request has been accepted by our bank.")
                .append("\n")
                .append("In order to conclude the account creation you are kindly requested to come personally " +
                        "to the bank along with the following documents:");
        byId.getAccountTemplate().getRequiredPapers().forEach(s ->
                emailBody.append("\n" + s));
        emailBody.append("\n")
                .append("\n")
                .append("Yours sincerely,");
        mailMessage.setTo(byId.getClient().getEmail());
        mailMessage.setSubject(EMAIL_ACCOUNT_SIGNATURE_SUBJECT);
        mailMessage.setText(emailBody.toString());

        this.javaMailSender.send(mailMessage);
    }

    @Override
    public void sendAccountCreatedMail(Account account) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        StringBuilder emailBody = new StringBuilder("Dear Mr/Mrs " + account.getClient().getFirstName() +
                " " + account.getClient().getLastName());
        emailBody.append("\n")
                .append("\n")
                .append("Thank you so much for allowing us to help you with your recent account opening. " +
                        "We are committed to providing our customers with the highest level of service and the most innovative banking products possible. ")
                .append("\n")
                .append("\n")
                .append("We are very glad you chose us as your financial institution and hope you will take " +
                        "advantage of our wide variety of savings, investment and loan products, all designed to meet your specific needs.")
                .append("\n")
                .append("\n")
                .append("Here is your account information:")
                .append("\n")
                .append("Account number: ")
                .append(account.getAccountNumber())
                .append("\n")
                .append("Account creation date: ")
                .append(account.getCreationDate())
                .append("\n")
                .append("Account owner: ")
                .append(account.getClient().getFirstName() + " " + account.getClient().getLastName())
                .append("\n")
                .append("\n")
                .append("Please do not hesitate to contact us, should you have any questions. We will contact you in the " +
                        "very near future to ensure you are completely satisfied with the services you have received thus far.")
                .append("\n")
                .append("\n")
                .append("Respectfully,");

        mailMessage.setTo(account.getClient().getEmail());
        mailMessage.setSubject(ACCOUNT_INFORMATION);
        mailMessage.setText(emailBody.toString());

        this.javaMailSender.send(mailMessage);
    }

    @Override
    public void sendAccountRequestRejectedMail(AccountRequest accountRequest, String reason) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        StringBuilder emailBody = new StringBuilder("Dear Mr/Mrs " + accountRequest.getClient().getFirstName() +
                " " + accountRequest.getClient().getLastName());

        emailBody.append("\n")
                .append("\n")
                .append("We are writing this letter to inform you that the application you filed for " +
                        "issuance of an account in your name has been rejected by our bank.")
                .append("\n")
                .append("\n")
                .append("It is with regret that we send this message to you that the account issuing authorities" +
                        " went through all the financial and other documents/ information you had provided" +
                        " with the application and they found you to be unsuitable for the purpose of issuing a bank" +
                        " account for the following reason:")
                .append("\n")
                .append("\"" + reason + "\"")
                .append("\n")
                .append("\n")
                .append("You can contact our officials to get advice on how to get your financial and credibility " +
                        "documents right for next application. It will be our pleasure to assist you in any " +
                        "other regards.")
                .append("\n")
                .append("\n")
                .append("Respectfully,");
        mailMessage.setTo(accountRequest.getClient().getEmail());
        mailMessage.setSubject(ACCOUNT_REQUEST_DECLINED);
        mailMessage.setText(emailBody.toString());

        this.javaMailSender.send(mailMessage);
    }
}
