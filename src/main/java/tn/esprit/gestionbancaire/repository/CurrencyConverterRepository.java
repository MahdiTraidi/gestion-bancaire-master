package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionbancaire.model.CurrencyConverter;
@Repository
public interface CurrencyConverterRepository extends JpaRepository<CurrencyConverter,Integer> {
}
