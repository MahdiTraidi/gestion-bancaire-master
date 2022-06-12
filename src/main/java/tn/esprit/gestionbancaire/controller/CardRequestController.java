package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CardRequestApi;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.model.*;
import tn.esprit.gestionbancaire.services.AccountService;
import tn.esprit.gestionbancaire.services.CardRequestService;
import tn.esprit.gestionbancaire.services.CardService;
import tn.esprit.gestionbancaire.services.CardTemplateService;

import java.util.List;

@RestController
public class CardRequestController implements CardRequestApi {

    private CardService cardService;
    private CardRequestService cardRequestService;
    private CardTemplateService cardTemplateService;
    private AccountService accountService;

    @Autowired
    public CardRequestController(CardService cardService,
                                 CardRequestService cardRequestService,
                                 CardTemplateService cardTemplateService,
                                 AccountService accountService) {
        this.cardService = cardService;
        this.cardRequestService = cardRequestService;
        this.cardTemplateService = cardTemplateService;
        this.accountService = accountService;
    }

    @Override
    public ResponseEntity<Object> requestCard(long idTemplate, String accountNumber) {
        try {
            CardTemplate cardTemplate = cardTemplateService.findById(idTemplate);
            Account byAccountNumber = accountService.findByAccountNumber(accountNumber);
            if (byAccountNumber == null){
                return new ResponseEntity<>("No account with this number", HttpStatus.BAD_REQUEST);
            }
            if (!byAccountNumber.getAccountTemplate().isHasCard()){
                return new ResponseEntity<>("Your account cannot have a card", HttpStatus.BAD_REQUEST);
            }
            CardRequest cardRequest = cardRequestService.save(byAccountNumber, cardTemplate);
            return ResponseEntity.ok(cardRequest);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> setRequestInProgress(long idRequest) {
        try {
            return ResponseEntity.ok(cardRequestService
                    .changeStatus(idRequest, AccountRequestStatus.IN_PROGRESS));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CardRequest> findRequestsByStatus(AccountRequestStatus accountRequestStatus) {
        return cardRequestService.findByRequestStatus(accountRequestStatus);
    }

    @Override
    public ResponseEntity<Object> validateRequest(long idRequest) {
        try {
            CardRequest byId = cardRequestService.findById(idRequest);
            Card card = cardService.generateCard(byId);
            cardRequestService.sendCardCreatedMail(card);
            cardRequestService.changeStatus(byId.getId(), AccountRequestStatus.VALIDATED);
            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> refuseRequest(long idRequest, String reason) {
        try {
            CardRequest byId = cardRequestService.findById(idRequest);
            cardRequestService.sendCardRequestRejectedMail(byId, reason);
            cardRequestService.changeStatus(idRequest, AccountRequestStatus.CLOSED);
            return new ResponseEntity<>(byId, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }
}
