package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.Client;
import tn.esprit.gestionbancaire.model.SavingsAccount;

import java.util.Optional;

public interface SavingsAccountRepository extends JpaRepository<SavingsAccount, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    Account findByClient(Client client);
    int countSavingsAccountByAccountTemplate_Id(long id);
}
