package tn.esprit.gestionbancaire.services;


import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestionbancaire.model.AdministrativeDocument;

import java.io.IOException;
import java.util.List;

public interface AdministrativeDocumentService {

    AdministrativeDocument save(AdministrativeDocument administrativeDocument);
    AdministrativeDocument update(AdministrativeDocument administrativeDocument);
    List<AdministrativeDocument> findAllByCreditId(Integer id);
    AdministrativeDocument findById(Integer id);
    void delete(Integer id);
    AdministrativeDocument updateAdministrativeDocumentImage(Integer id, String imageURL);
}
