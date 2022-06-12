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
import tn.esprit.gestionbancaire.model.*;
import tn.esprit.gestionbancaire.repository.CardRequestRepository;
import tn.esprit.gestionbancaire.repository.CurrentAccountRepository;
import tn.esprit.gestionbancaire.repository.SavingsAccountRepository;
import tn.esprit.gestionbancaire.services.CardRequestService;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static tn.esprit.gestionbancaire.utils.Constants.*;

@Service
@Slf4j
public class CardRequestServiceImpl implements CardRequestService {

    private CardRequestRepository cardRequestRepository;
    private CurrentAccountRepository currentAccountRepository;
    private SavingsAccountRepository savingsAccountRepository;
    private JavaMailSender javaMailSender;

    @Autowired
    public CardRequestServiceImpl(CardRequestRepository cardRequestRepository,
                                  CurrentAccountRepository currentAccountRepository,
                                  SavingsAccountRepository savingsAccountRepository,
                                  @Qualifier("getJavaMailSender") JavaMailSender javaMailSender) {
        this.cardRequestRepository = cardRequestRepository;
        this.currentAccountRepository = currentAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
        this.javaMailSender = javaMailSender;
    }

    @Override
    public CardRequest save(Account account, CardTemplate cardTemplate) {
       CardRequest cardRequest = new CardRequest();
        cardRequest.setAccount(account);
        cardRequest.setCardTemplate(cardTemplate);
        cardRequest.setRequestStatus(AccountRequestStatus.OPEN);
        cardRequest.setCreationDate(Instant.now());
        return cardRequestRepository.save(cardRequest);
    }

    @Override
    public CardRequest findById(long id) {
        return cardRequestRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no card request with the ID: " + id,
                        ErrorCodes.ACCOUNT_REQUEST_NOT_FOUND)
        );
    }

    @Override
    public List<CardRequest> findAll() {
        return cardRequestRepository.findAll();
    }

    @Override
    public List<CardRequest> findByRequestStatus(AccountRequestStatus accountRequestStatus) {
        return cardRequestRepository.findByRequestStatus(accountRequestStatus);
    }

    @Override
    public CardRequest changeStatus(long requestId, AccountRequestStatus requestStatus) {
        CardRequest byId = this.findById(requestId);
        byId.setRequestStatus(requestStatus);
        return cardRequestRepository.save(byId);
    }

    @Override
    public void delete(long id) {
        Optional<CardRequest> byId = cardRequestRepository.findById(id);
        if (byId.isPresent()) {
            cardRequestRepository.deleteById(id);
        } else {
            log.error("There is no card request with the ID: " + id);
            throw new EntityNotFoundException("There is no card request with the ID: " + id,
                    ErrorCodes.ACCOUNT_REQUEST_NOT_FOUND);
        }
    }

    public void sendCardCreatedMail(Card card) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        StringBuilder emailBody = new StringBuilder("Dear Mr/Mrs " + card.getAccount().getClient().getFirstName() +
                " " + card.getAccount().getClient().getLastName());
        emailBody.append("\n")
                .append("\n")
                .append("Thank you so much for allowing us to help you with your recent card creating. " +
                        "We are committed to providing our customers with the highest level of service and the most innovative banking products possible. ")
                .append("\n")
                .append("\n")
                .append("We are very glad you chose us as your financial institution and hope you will take " +
                        "advantage of our wide variety of savings, investment and loan products, all designed to meet your specific needs.")
                .append("\n")
                .append("\n")
                .append("Here is your card information:")
                .append("\n")
                .append("Card number: ")
                .append(card.getCardNumber())
                .append("\n")
                .append("Associated Account: ")
                .append(card.getAccount().getAccountNumber())
                .append("\n")
                .append("\n")
                .append("Card owner: ")
                .append(card.getAccount().getClient().getFirstName() + " " + card.getAccount().getClient().getLastName())
                .append("\n")
                .append("\n")
                .append("You are kindly requested to come personally to the bank to receive your card")
                .append("\n")
                .append("\n")
                .append("Please do not hesitate to contact us, should you have any questions. We will contact you in the " +
                        "very near future to ensure you are completely satisfied with the services you have received thus far.")
                .append("\n")
                .append("\n")
                .append("Respectfully,");

        mailMessage.setTo(card.getAccount().getClient().getEmail());
        mailMessage.setSubject(CARD_REQUEST_ACCEPTED);
        mailMessage.setText(emailBody.toString());

        this.javaMailSender.send(mailMessage);
    }

    @Override
    public void sendCardRequestRejectedMail(CardRequest cardRequest, String reason) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        StringBuilder emailBody = new StringBuilder("Dear Mr/Mrs " + cardRequest.getAccount().getClient().getFirstName() +
                " " + cardRequest.getAccount().getClient().getLastName());

        emailBody.append("\n")
                .append("\n")
                .append("We are writing this letter to inform you that the application you filed for " +
                        "issuance associating a card to your account has been rejected by our bank.")
                .append("\n")
                .append("\n")
                .append("It is with regret that we send this message to you that the account issuing authorities" +
                        " went through all the financial and other documents/ information" +
                        " and they found you to be unsuitable for the purpose of issuing a card" +
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
        mailMessage.setTo(cardRequest.getAccount().getClient().getEmail());
        mailMessage.setSubject(CARD_REQUEST_DECLINED);
        mailMessage.setText(emailBody.toString());

        this.javaMailSender.send(mailMessage);
    }
}
