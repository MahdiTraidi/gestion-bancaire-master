package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.CreditType;
import tn.esprit.gestionbancaire.model.CreditTemplate;

import java.util.List;

public interface CreditTemplateService {
    CreditTemplate save(CreditTemplate creditTemplate) ;

    CreditTemplate findById(long id);

    List<CreditTemplate> findByCreditType(CreditType creditType);

    List<CreditTemplate> findAll();

    CreditTemplate update(CreditTemplate creditTemplate);

    CreditTemplate findCreditTemplateByTitle(String title);

    void delete(long id);
}
