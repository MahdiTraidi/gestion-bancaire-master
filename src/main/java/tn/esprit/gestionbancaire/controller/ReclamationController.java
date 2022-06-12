package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.ReclamationAPI;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Reclamation;
import tn.esprit.gestionbancaire.services.ReclamationService;

import java.util.List;

@RestController
public class ReclamationController implements ReclamationAPI {
    private final ReclamationService reclamationService;

    @Autowired
    public ReclamationController(ReclamationService reclamationService) {
        this.reclamationService = reclamationService;
    }
    @Override
    public ResponseEntity<Reclamation> save(Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.save(reclamation));
    }

    @Override
    public ResponseEntity<Reclamation> updateReclamationStatus(Integer idReclamation, ReclamationStatus reclamationStatus) {
        return ResponseEntity.ok(reclamationService.updateReclamationStatus(idReclamation, reclamationStatus));
    }

    @Override
    public ResponseEntity<Reclamation> updateReclamation(Integer idReclamation) {
        return null;
    }

    @Override
    public void deleteReclamation(Integer idReclamation) {
        reclamationService.delete(idReclamation);
    }

    @Override
    public List<Reclamation> findAllByStatus(ReclamationStatus reclamationStatus) {
        return reclamationService.findAllByStatus(reclamationStatus);
    }

    @Override
    public List<Reclamation> findAll() {
        return reclamationService.findAll();
    }

    @Override
    public void getStats() {
         reclamationService.getReclamationStats();
    }
}
