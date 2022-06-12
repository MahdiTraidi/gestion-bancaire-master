package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.enums.CreditStatus;

import java.util.List;
import java.util.Map;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("credits")
public interface CreditApi {

    @PostMapping(value = APP_ROOT + "/credits/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a credit", notes = "this methode can add new credit", response = Credit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit Object added "),
            @ApiResponse(code = 400, message = "Credit Object is invalid")
    })
    ResponseEntity<Credit> save(@RequestBody Credit credit);

    @PatchMapping(value = APP_ROOT + "/credits/update/status/{idCredit}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify credit status", notes = "this methode can modify credit status", response = Credit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit status modified "),
            @ApiResponse(code = 400, message = "Credit status is invalid")
    })
    ResponseEntity<Credit> updateCreditStatus(@PathVariable("idCredit") Integer idCredit, @RequestParam("status") CreditStatus creditStatus);

    @PatchMapping(APP_ROOT + "/credits/update/credit/{idCredit}")
    @ApiOperation(value = "Modify credit", notes = "this methode can modify credit", response = Credit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit modified "),
            @ApiResponse(code = 400, message = "Credit is invalid")
    })
    ResponseEntity<Credit> updateCredit(@PathVariable("idCredit") Integer idCredit);

    @DeleteMapping(APP_ROOT + "/credits/delete/credit/{idCredit}")
    @ApiOperation(value = "Delete credit", notes = "This methode can delete credit", response = Credit.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit deleted "),
            @ApiResponse(code = 400, message = "Credit is invalid")
    })
    void deleteCredit(@PathVariable("idCredit") Integer idCredit);

    @GetMapping(value = APP_ROOT + "/credits/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all credits by archive", notes = "This methode get all credits by archive ", responseContainer = "List<Credit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Credit> findAllByArchived(@RequestParam("archived") Boolean archived);

    @GetMapping(value = APP_ROOT + "/credits/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all credits by user id", notes = "This methode get all credits by id ", responseContainer = "List<Credit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Credit> findAllByUser(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/credits/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all credits", notes = "This methode get all credit ", responseContainer = "List<Credit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Credit> findAll();
    @GetMapping(value = APP_ROOT + "/credits", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all credits have specific status", notes = "This methode get all credit with specific", responseContainer = "List<Credit>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Credit> findAllByStatus(@RequestParam("status") CreditStatus status);

    @GetMapping(value = APP_ROOT + "/credits/addnote/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add note to credit note", notes = "This methode Add note to credit note ", responseContainer = "List<String>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<String> addNote(@PathVariable("id") Integer id,@RequestParam("note") String note);

    @GetMapping(value = APP_ROOT + "/credits/findbyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get credit by id", notes = "This methode Get credit by id ", responseContainer = "Credit")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    Credit findCreditById(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/credits/mostusedcredit", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the most used credit", notes = "This methode Get the most used credit ", responseContainer = "Map<String,Integer>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    Map<String,Integer> mostUsedCredit();



}
