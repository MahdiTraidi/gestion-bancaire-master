package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.enums.CreditType;
import tn.esprit.gestionbancaire.model.CreditTemplate;

import java.util.List;

public interface CreditTemplateRepository extends JpaRepository<CreditTemplate, Long> {

    List<CreditTemplate> findByCreditType(CreditType creditType);
    CreditTemplate findCreditTemplateByTitle(String title);
}
