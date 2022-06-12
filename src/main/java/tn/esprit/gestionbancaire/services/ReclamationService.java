package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Reclamation;

import java.util.List;

public interface ReclamationService {

    Reclamation save(Reclamation reclamation);

    Reclamation updateReclamationStatus(Integer idReclamation, ReclamationStatus reclamationStatus);

    Reclamation findById(Integer idReclamation);

    List<Reclamation> findAll();

    List<Reclamation> findAllByStatus(ReclamationStatus reclamationStatus);

    void delete(Integer id);

    void getReclamationStats();
}
