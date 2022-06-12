package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.model.ReclamationComment;

import java.util.List;

public interface ReclamationCommentRepository extends JpaRepository<ReclamationComment, Integer> {
    List<ReclamationComment> findAllByReclamation(ReclamationComment reclamationComment);
}