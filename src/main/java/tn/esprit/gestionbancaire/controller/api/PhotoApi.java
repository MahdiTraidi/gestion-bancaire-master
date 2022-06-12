package tn.esprit.gestionbancaire.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("photos")
public interface PhotoApi {

  @PostMapping( APP_ROOT + "/save/{title}")
  String savePhoto(@RequestPart("file") MultipartFile photo, @PathVariable("title") String title) throws IOException, FlickrException;

}
