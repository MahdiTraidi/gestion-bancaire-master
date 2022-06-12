package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.enums.CreditStatus;

import java.util.List;

public interface CreditRepository extends JpaRepository<Credit, Integer> {


    List<Credit> findAllByUserId(Integer id);
    List<Credit> findAllByCreditStatus(CreditStatus creditStatus);
    List<Credit> findAllByArchived(boolean archived);
    long countCreditByCreditStatus (CreditStatus status);
    Integer countCreditByCreditTemplateTitle(String title);

}
