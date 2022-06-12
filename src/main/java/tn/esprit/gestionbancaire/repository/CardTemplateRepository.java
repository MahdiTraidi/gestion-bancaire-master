package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.model.CardTemplate;

public interface CardTemplateRepository extends JpaRepository<CardTemplate, Long> {
}
