package tn.esprit.gestionbancaire.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.model.Client;
import tn.esprit.gestionbancaire.repository.ClientRepository;
import tn.esprit.gestionbancaire.services.ClientService;


@Service
public class ClientServiceImpl implements ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client save(Client client) {
        // TODO Auto-generated method stub
        return clientRepository.save(client);
    }

    @Override
    public Client findById(long id) {
        return clientRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no client with the ID: " + id,
                        ErrorCodes.CLIENT_NOT_FOUND)
        );
    }

    @Override
    public Client changeStatusToValidated(long idClient) {
        Client byId = this.findById(idClient);
        byId.setValidated(true);
        return clientRepository.save(byId);
    }
}
