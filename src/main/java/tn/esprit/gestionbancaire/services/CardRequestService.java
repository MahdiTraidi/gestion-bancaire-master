package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.Card;
import tn.esprit.gestionbancaire.model.CardRequest;
import tn.esprit.gestionbancaire.model.CardTemplate;

import java.util.List;

public interface CardRequestService {

    CardRequest save(Account account, CardTemplate cardTemplate);
    CardRequest findById(long id);
    List<CardRequest> findAll();
    List<CardRequest> findByRequestStatus(AccountRequestStatus accountRequestStatus);
    CardRequest changeStatus(long requestId, AccountRequestStatus requestStatus);
    void delete(long id);
    void sendCardCreatedMail(Card card);
    void sendCardRequestRejectedMail(CardRequest cardRequest, String reason);
}
