package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.Client;
import tn.esprit.gestionbancaire.model.CurrentAccount;

import java.util.Optional;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);
    Account findByClient(Client client);
    int countCurrentAccountByAccountTemplate_Id(long id);
}
