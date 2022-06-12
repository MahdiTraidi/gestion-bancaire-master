package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.AccountRequestApi;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.AccountRequest;
import tn.esprit.gestionbancaire.model.AccountTemplate;
import tn.esprit.gestionbancaire.services.AccountRequestService;
import tn.esprit.gestionbancaire.services.AccountService;
import tn.esprit.gestionbancaire.services.AccountTemplateService;
import tn.esprit.gestionbancaire.services.ClientService;

import java.util.List;


@RestController
public class AccountRequestController implements AccountRequestApi {

    private AccountRequestService accountRequestService;
    private AccountTemplateService accountTemplateService;
    private AccountService accountService;
    private ClientService clientService;

    @Autowired
    public AccountRequestController(AccountRequestService accountRequestService,
                                    AccountTemplateService accountTemplateService,
                                    AccountService accountService,
                                    ClientService clientService) {
        this.accountRequestService = accountRequestService;
        this.accountTemplateService = accountTemplateService;
        this.accountService = accountService;
        this.clientService = clientService;
    }

    @Override
    public ResponseEntity<Object> saveForNewClient(AccountRequest accountRequest, long idTemplate) {
        try {
            AccountTemplate accountTemplate = accountTemplateService.findById(idTemplate);
            accountRequest.setAccountRequestStatus(AccountRequestStatus.OPEN);
            accountRequest.setAccountTemplate(accountTemplate);
            accountRequest.getClient().setValidated(false);
            return ResponseEntity.ok(accountRequestService.save(accountRequest, true));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> setRequestInProgress(long idRequest) {
        try {
            return ResponseEntity.ok(accountRequestService
                    .changeStatus(idRequest, AccountRequestStatus.IN_PROGRESS));
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<AccountRequest> findRequestsByStatus(AccountRequestStatus accountRequestStatus) {
        return accountRequestService.findByRequestStatus(accountRequestStatus);
    }

    @Override
    public ResponseEntity<Object> sendRequestToSignature(long idRequest) {
        try {
            AccountRequest byId = accountRequestService.findById(idRequest);
            if (!byId.getClient().isValidated()) {
                byId.setAccountRequestStatus(AccountRequestStatus.WAITING_FOR_CLIENT);
                accountRequestService.sendRequestValidationMail(byId);
                accountRequestService.save(byId, false);
                return new ResponseEntity<>(byId, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("The customer is already validated, no need for signature", HttpStatus.BAD_REQUEST);
            }
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> validateRequest(long idRequest) {
        try {
            AccountRequest byId = accountRequestService.findById(idRequest);
            if(!byId.getClient().isValidated()) {
                clientService.changeStatusToValidated(byId.getClient().getId());
            }
            Account account = accountService.generateAccount(byId);
            accountRequestService.sendAccountCreatedMail(account);
            accountRequestService.changeStatus(byId.getId(), AccountRequestStatus.VALIDATED);
            return new ResponseEntity<>(account, HttpStatus.OK);

        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> refuseRequest(long idRequest, String reason) {
        try {
            AccountRequest byId = accountRequestService.findById(idRequest);
            accountRequestService.sendAccountRequestRejectedMail(byId, reason);
            accountRequestService.changeStatus(idRequest, AccountRequestStatus.CLOSED);
            return new ResponseEntity<>(byId, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

}
