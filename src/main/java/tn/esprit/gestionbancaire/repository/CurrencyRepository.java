package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionbancaire.model.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency,Integer> {
}
