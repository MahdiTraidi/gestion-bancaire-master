package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestionbancaire.controller.api.AdministrativeDocumentApi;
import tn.esprit.gestionbancaire.model.AdministrativeDocument;
import tn.esprit.gestionbancaire.services.AdministrativeDocumentService;
import tn.esprit.gestionbancaire.services.CreditService;
import tn.esprit.gestionbancaire.services.FlickrService;

import java.io.IOException;
import java.util.List;

@RestController
public class AdministrativeDocumentController  implements AdministrativeDocumentApi {

    private AdministrativeDocumentService administrativeDocumentService;
    private FlickrService flickrService;

    @Autowired
    public AdministrativeDocumentController(
            AdministrativeDocumentService administrativeDocumentService,
            FlickrService flickrService
    ) {
        this.administrativeDocumentService = administrativeDocumentService;
        this.flickrService = flickrService;
    }
    @Override
    public List<AdministrativeDocument> findAllByCreditId(Integer idCredit) {
        return administrativeDocumentService.findAllByCreditId(idCredit);
    }



    @Override
    public ResponseEntity<AdministrativeDocument> updateAdministrativeDocumentImage(Integer id, MultipartFile photo, String title) throws IOException {
        String imageURL = flickrService.savePhoto(photo.getInputStream(),title);
        return ResponseEntity.ok( administrativeDocumentService.updateAdministrativeDocumentImage(id, imageURL));
    }

    @Override
    public void deleteAdministrativeDocumentImage(Integer id) {
        administrativeDocumentService.delete(id);
    }

    @Override
    public ResponseEntity<AdministrativeDocument> save(AdministrativeDocument administrativeDocument) {
        return ResponseEntity.ok(administrativeDocumentService.save(administrativeDocument));
    }

}
