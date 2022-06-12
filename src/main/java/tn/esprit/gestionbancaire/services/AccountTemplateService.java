package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.AccountTemplate;

import java.util.List;
import java.util.Map;

public interface AccountTemplateService {

    AccountTemplate save(AccountTemplate accountTemplate) throws InvalidEntityException;

    AccountTemplate findById(long id);

    List<AccountTemplate> findAll();

    AccountTemplate update(AccountTemplate accountTemplate);

    void delete(long id);

    Map<String, Integer> accountsPerTemplate();
}
