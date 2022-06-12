package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.AccountRequest;

public interface AccountService {

    Account findByAccountNumber(String accountNumber);
    Account generateAccount(AccountRequest accountRequest);

}
