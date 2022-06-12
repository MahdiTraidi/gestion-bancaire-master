package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.model.CardTemplate;

import java.util.List;

public interface CardTemplateService {

    CardTemplate save(CardTemplate cardTemplate);
    CardTemplate findById(long id);
    List<CardTemplate> findAll();
    CardTemplate update(CardTemplate cardTemplate);
    void delete(long id);
}
