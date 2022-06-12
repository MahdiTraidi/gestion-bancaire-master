package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Reclamation;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("reclamation")
public interface ReclamationAPI {

    @PostMapping(value = APP_ROOT + "/Reclamations/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a Reclamation", notes = "this methode can add new Reclamation", response = Reclamation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reclamation Object added "),
            @ApiResponse(code = 400, message = "Reclamation Object is invalid")
    })
    ResponseEntity<Reclamation> save(@RequestBody Reclamation Reclamation);

    @RequestMapping (method = RequestMethod.PUT,value = APP_ROOT + "/Reclamations/update/etat/{idReclamation}/{reclamationStatus}" , consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify Reclamation status", notes = "this methode can modify Reclamation status", response = Reclamation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reclamation status modified "),
            @ApiResponse(code = 400, message = "Reclamation status is invalid")
    })
    ResponseEntity<Reclamation> updateReclamationStatus(@PathVariable("idReclamation") Integer idReclamation, @PathVariable("reclamationStatus") ReclamationStatus reclamationStatus);

    @PutMapping(APP_ROOT + "/Reclamations/update/Reclamation/{idReclamation}")
    @ApiOperation(value = "Modify Reclamation", notes = "this methode can modify Reclamation", response = Reclamation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reclamation modified "),
            @ApiResponse(code = 400, message = "Reclamation is invalid")
    })
    ResponseEntity<Reclamation> updateReclamation(@PathVariable("idReclamation") Integer idReclamation);

    @DeleteMapping(APP_ROOT + "/Reclamations/delete/Reclamation/{idReclamation}")
    @ApiOperation(value = "Delete Reclamation", notes = "This methode can delete Reclamation", response = Reclamation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reclamation deleted "),
            @ApiResponse(code = 400, message = "Reclamation is invalid")
    })
    void deleteReclamation(@PathVariable("idReclamation") Integer idReclamation);

    @GetMapping(value = APP_ROOT + "/Reclamations/all/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all Reclamations", notes = "This methode get all Reclamation ", responseContainer = "List<Reclamation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Reclamations / Void list")
    })
    List<Reclamation> findAllByStatus(@PathVariable("status") ReclamationStatus reclamationStatus);

    @GetMapping(value = APP_ROOT + "/Reclamations/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all Reclamations", notes = "This methode get all Reclamation ", responseContainer = "List<Reclamation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Reclamations / Void list")
    })
    List<Reclamation> findAll();

    @PostMapping(value = APP_ROOT + "/Reclamations/stats")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Reclamations / Void list")
    })
    void getStats();

}
