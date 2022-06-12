package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CardTemplateApi;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.CardTemplate;
import tn.esprit.gestionbancaire.services.CardTemplateService;

import java.util.List;

@RestController
public class CardTemplateController implements CardTemplateApi {

    private CardTemplateService cardTemplateService;

    @Autowired
    public CardTemplateController(CardTemplateService cardTemplateService) {
        this.cardTemplateService = cardTemplateService;
    }

    @Override
    public ResponseEntity<Object> save(CardTemplate cardTemplate) {
        try {
            cardTemplateService.save(cardTemplate);
            return ResponseEntity.ok(cardTemplate);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getErrors(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<CardTemplate> findAll() {
        return cardTemplateService.findAll();
    }

    @Override
    public ResponseEntity<Object> deleteCardTemplate(long idCardTemplate) {
        try {
            cardTemplateService.delete(idCardTemplate);
            return ResponseEntity.ok("Card template with ID: " + idCardTemplate + " has been deleted");
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Object> findCardTemplate(long idCardTemplate) {
        try {
            CardTemplate byId = cardTemplateService.findById(idCardTemplate);
            return ResponseEntity.ok(byId);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getErrorCode(), HttpStatus.BAD_REQUEST);
        }
    }
}
