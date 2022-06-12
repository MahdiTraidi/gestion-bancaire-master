package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.model.Client;

public interface ClientService {

    Client save(Client client);

    Client findById(long id);

    Client changeStatusToValidated(long idClient);

}
