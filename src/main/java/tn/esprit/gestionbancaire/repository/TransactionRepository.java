package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionbancaire.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Integer> {

}
