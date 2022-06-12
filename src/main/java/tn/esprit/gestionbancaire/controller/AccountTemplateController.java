package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.AccountTemplateApi;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.AccountTemplate;
import tn.esprit.gestionbancaire.services.AccountTemplateService;

import java.util.List;
import java.util.Map;

@RestController
public class AccountTemplateController implements AccountTemplateApi {

    private AccountTemplateService accountTemplateService;

    @Autowired
    public AccountTemplateController(AccountTemplateService accountTemplateService) {
        this.accountTemplateService = accountTemplateService;
    }

    @Override
    public ResponseEntity<Object> save(AccountTemplate accountTemplate) {
        try {
            accountTemplateService.save(accountTemplate);
            return ResponseEntity.ok(accountTemplate);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<AccountTemplate> findAll() {
        return accountTemplateService.findAll();
    }

    @Override
    public ResponseEntity<Object> deleteAccountTemplate(long idAccountTemplate) {
        try {
            accountTemplateService.delete(idAccountTemplate);
            return ResponseEntity.ok("Account with ID: " + idAccountTemplate + " has been deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> findAccountTemplate(long idAccountTemplate) {
        try {
            AccountTemplate byId = accountTemplateService.findById(idAccountTemplate);
            return ResponseEntity.ok(byId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public Map<String, Integer> getAccountsPerTemplate(){
        return accountTemplateService.accountsPerTemplate();
    }
}
