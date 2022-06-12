package tn.esprit.gestionbancaire.controller.api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.gestionbancaire.model.AdministrativeDocument;
import tn.esprit.gestionbancaire.model.Credit;

import java.io.IOException;
import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("administrativedocuments")
public interface AdministrativeDocumentApi {
    @GetMapping(value = APP_ROOT + "/administrativedocuments/{idCredit}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all administrative documents of credit", notes = "This methode get all administrative documents by credits ", responseContainer = "List<AdministrativeDocument>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of administrative documents / Void list")
    })
    List<AdministrativeDocument> findAllByCreditId(@PathVariable("idCredit") Integer idCredit);


    @PostMapping(value = APP_ROOT + "/administrativedocuments/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a administrative documents", notes = "this methode can add new administrative documents", response = AdministrativeDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "administrative documents added "),
            @ApiResponse(code = 400, message = "administrative documents is invalid")
    })
    ResponseEntity<AdministrativeDocument> save(@RequestBody AdministrativeDocument administrativeDocument) ;


    @PatchMapping(value = APP_ROOT + "/administrativedocuments/update/photo/{id}/{title}")
    @ApiOperation(value = "Modify administrative documents image", notes = "this methode can modify administrative documents image", response = AdministrativeDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "administrative documents image modified "),
            @ApiResponse(code = 400, message = "administrative documents image is invalid")
    })
    ResponseEntity<AdministrativeDocument> updateAdministrativeDocumentImage(@PathVariable("id") Integer id , @RequestPart("file") MultipartFile photo, @PathVariable("title") String title) throws IOException, FlickrException;

    @DeleteMapping(APP_ROOT + "/administrativedocuments/delete/{id}")
    @ApiOperation(value = "Delete administrativedocuments", notes = "This methode can delete administrativedocuments", response = AdministrativeDocument.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "administrativedocuments deleted "),
            @ApiResponse(code = 400, message = "administrativedocuments is invalid")
    })
    void deleteAdministrativeDocumentImage(@PathVariable("id") Integer id);


}
