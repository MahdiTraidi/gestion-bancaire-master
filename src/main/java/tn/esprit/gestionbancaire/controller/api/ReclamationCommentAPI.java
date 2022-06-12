package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.model.ReclamationComment;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("ReclamationComment")
public interface ReclamationCommentAPI {

    @PostMapping(value = APP_ROOT + "/ReclamationComment/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a ReclamationComment", notes = "this methode can add new ReclamationComment", response = ReclamationComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReclamationComment Object added "),
            @ApiResponse(code = 400, message = "ReclamationComment Object is invalid")
    })
    ResponseEntity<ReclamationComment> save(@RequestBody ReclamationComment reclamationComment);
    
    @PutMapping(APP_ROOT + "/ReclamationComment/update/ReclamationComment/{idReclamationComment}")
    @ApiOperation(value = "Modify ReclamationComment", notes = "this methode can modify ReclamationComment", response = ReclamationComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReclamationComment modified "),
            @ApiResponse(code = 400, message = "ReclamationComment is invalid")
    })
    ResponseEntity<ReclamationComment> updateReclamationComment(@PathVariable("idReclamationComment") Integer idReclamationComment, @RequestBody ReclamationComment reclamationComment);

    @DeleteMapping(APP_ROOT + "/ReclamationComment/delete/ReclamationComment/{idReclamationComment}")
    @ApiOperation(value = "Delete ReclamationComment", notes = "This methode can delete ReclamationComment", response = ReclamationComment.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "ReclamationComment deleted "),
            @ApiResponse(code = 400, message = "ReclamationComment is invalid")
    })
    void deleteReclamationComment(@PathVariable("idReclamationComment") Integer idReclamationComment);


    @GetMapping(value = APP_ROOT + "/ReclamationComment/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all ReclamationCommentComment", notes = "This methode get all ReclamationComment ", responseContainer = "List<ReclamationComment>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of ReclamationCommentComment / Void list")
    })
    List<ReclamationComment> findAll();

}
