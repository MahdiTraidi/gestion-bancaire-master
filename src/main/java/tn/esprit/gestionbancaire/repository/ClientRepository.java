package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.gestionbancaire.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client,Long>{
	
	public Client findBynID(String cin);

}
