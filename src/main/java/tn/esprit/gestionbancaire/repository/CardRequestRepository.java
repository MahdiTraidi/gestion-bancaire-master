package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.CardRequest;

import java.util.List;

public interface CardRequestRepository extends JpaRepository<CardRequest, Long> {

    List<CardRequest> findByRequestStatus(AccountRequestStatus requestStatus);
}
