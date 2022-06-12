package tn.esprit.gestionbancaire.services;


import tn.esprit.gestionbancaire.model.ReclamationComment;

import java.util.List;

public interface ReclamationCommentService {
    ReclamationComment findById(Integer idReclamation);
    List<ReclamationComment> findAll();
    ReclamationComment save(ReclamationComment reclamation);
    ReclamationComment update(Integer id,ReclamationComment reclamationComment);
    void delete(Integer id);

}
