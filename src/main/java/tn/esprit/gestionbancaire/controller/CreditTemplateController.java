package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CreditTemplateApi;
import tn.esprit.gestionbancaire.enums.CreditType;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.AccountTemplate;
import tn.esprit.gestionbancaire.model.CreditTemplate;
import tn.esprit.gestionbancaire.services.CreditTemplateService;

import java.util.List;

@RestController
public class CreditTemplateController implements CreditTemplateApi {

    private CreditTemplateService creditTemplateService;

    @Autowired
    public CreditTemplateController(CreditTemplateService creditTemplateService) {
        this.creditTemplateService = creditTemplateService;
    }

    @Override
    public ResponseEntity<Object> save(CreditTemplate creditTemplate) {
        try {
            creditTemplateService.save(creditTemplate);
            return ResponseEntity.ok(creditTemplate);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CreditTemplate> findAll() {
        return creditTemplateService.findAll();
    }

    @Override
    public ResponseEntity<Object> deleteCreditTemplate(long idCreditTemplate) {
        try {
            creditTemplateService.delete(idCreditTemplate);
            return ResponseEntity.ok("Credit Template with ID: " + idCreditTemplate + " has been deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> findAccountTemplate(long idCreditTemplate) {
        try {
            CreditTemplate byId = creditTemplateService.findById(idCreditTemplate);
            return ResponseEntity.ok(byId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CreditTemplate> findAccountTemplateByType(CreditType creditType) {
        return creditTemplateService.findByCreditType(creditType);
    }
}
