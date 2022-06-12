package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.ReclamationCommentAPI;
import tn.esprit.gestionbancaire.model.ReclamationComment;
import tn.esprit.gestionbancaire.services.ReclamationCommentService;
import tn.esprit.gestionbancaire.services.ReclamationService;

import java.util.List;

@RestController
public class ReclamationCommentController implements ReclamationCommentAPI {

    private final ReclamationCommentService reclamationCommentService;

    @Autowired
    public ReclamationCommentController(ReclamationCommentService reclamationCommentService) {
        this.reclamationCommentService = reclamationCommentService;
    }

    @Override
    public ResponseEntity<ReclamationComment> save(ReclamationComment reclamationComment) {
        return ResponseEntity.ok(reclamationCommentService.save(reclamationComment));
    }

    @Override
    public ResponseEntity<ReclamationComment> updateReclamationComment(Integer idReclamationComment,ReclamationComment reclamationComment) {
        return ResponseEntity.ok(reclamationCommentService.update(idReclamationComment,reclamationComment));
    }

    @Override
    public void deleteReclamationComment(Integer idReclamationComment) {
        reclamationCommentService.delete(idReclamationComment);
    }

    @Override
    public List<ReclamationComment> findAll() {
        return reclamationCommentService.findAll();
    }
}
