package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.gestionbancaire.enums.AccountType;
import tn.esprit.gestionbancaire.model.AccountTemplate;

import java.util.List;

public interface AccountTemplateRepository extends JpaRepository<AccountTemplate, Long> {

    List<AccountTemplate> findByAccountType(AccountType accountType);
    List<AccountTemplate> findByForBusiness(boolean forBusiness);
    List<AccountTemplate> findByIsResident(boolean isResident);
    List<AccountTemplate> findByHasCheque(boolean hasCheque);
}
