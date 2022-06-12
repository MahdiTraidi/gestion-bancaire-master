package tn.esprit.gestionbancaire.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.esprit.gestionbancaire.model.AdministrativeDocument;

import java.util.List;

public interface AdministrativeDocumentRepository extends JpaRepository<AdministrativeDocument, Integer> {

    // JPQL query
    @Query(value = "select ad from AdministrativeDocument ad where ad.credit.id = :id")
    List<AdministrativeDocument> findAllByCreditId(Integer id);
}
