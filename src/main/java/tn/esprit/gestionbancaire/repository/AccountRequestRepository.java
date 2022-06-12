package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.AccountRequest;

import java.util.List;

public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {

    List<AccountRequest> findByAccountRequestStatus(AccountRequestStatus accountRequestStatus);

}
