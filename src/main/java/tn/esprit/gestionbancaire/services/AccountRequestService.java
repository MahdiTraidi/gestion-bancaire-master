package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.AccountRequest;

import java.util.List;

public interface AccountRequestService {

    AccountRequest save(AccountRequest accountRequest,boolean newClient);

    AccountRequest findById(long id);

    List<AccountRequest> findAll();

    List<AccountRequest> findByRequestStatus(AccountRequestStatus accountRequestStatus);

    AccountRequest changeStatus(long requestId, AccountRequestStatus accountRequestStatus);

    void delete(long id);

    void sendRequestValidationMail(AccountRequest byId);

    void sendAccountCreatedMail(Account account);

    void sendAccountRequestRejectedMail(AccountRequest accountRequest, String reason);

}
