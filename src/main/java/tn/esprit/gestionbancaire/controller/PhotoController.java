package tn.esprit.gestionbancaire.controller;


import com.flickr4java.flickr.FlickrException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestionbancaire.controller.api.PhotoApi;
import tn.esprit.gestionbancaire.services.FlickrService;

import java.io.IOException;

@RestController
public class PhotoController implements PhotoApi {

  private FlickrService flickrService;

  @Autowired
  public PhotoController(FlickrService flickrService) {
    this.flickrService = flickrService;
  }

  @Override
  public String savePhoto(MultipartFile photo, String title) throws IOException, FlickrException {
    return flickrService.savePhoto(photo.getInputStream(), title);
  }
}
