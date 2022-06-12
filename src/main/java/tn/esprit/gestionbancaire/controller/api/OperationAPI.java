package tn.esprit.gestionbancaire.controller.api;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.OperationStatus;
import tn.esprit.gestionbancaire.model.Currency;
import tn.esprit.gestionbancaire.model.Operation;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("operations")
public interface OperationAPI {

    @PostMapping(value = APP_ROOT + "/operations/create/{accountNumber}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a Operation", notes = "this methode can add new Operation", response = Operation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Object added "),
            @ApiResponse(code = 400, message = "Operation Object is invalid")
    })
    ResponseEntity<Operation> save(@PathVariable("accountNumber") String accountNumber ,@RequestBody Operation operation);

    @PatchMapping(value = APP_ROOT + "/operations/update/status/{idOperation}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Modify credit status", notes = "this methode can modify operations status", response = Operation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "operation status modified "),
            @ApiResponse(code = 400, message = "operation status is invalid")
    })
        ResponseEntity<Operation> updateCreditStatus(@PathVariable("idOperation") Integer idCredit, @RequestParam("status") OperationStatus operationStatus);

    @GetMapping(value = APP_ROOT + "/operations/findbyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Operation by id", notes = "This methode Get Operation by id ", responseContainer = "Operation")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Operation / Void list")
    })
    Operation getOperationById(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/operations/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by archive", notes = "This methode get all operations by archive ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of credits / Void list")
    })
    List<Operation> findAllByArchived(@RequestParam("archived") Boolean archived);

    @GetMapping(value = APP_ROOT + "/operations/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by user id", notes = "This methode get all credits by id ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of operations / Void list")
    })
    List<Operation> findAllByUser(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/operations/{id}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all operations by user id", notes = "This methode get all credits by id ", responseContainer = "List<Operation>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of operations / Void list")
    })
    List<Operation> getAllOperationByClientAndStatus(@PathVariable("id") Integer id,@PathVariable("status") OperationStatus operationStatus);

    @PatchMapping(value = APP_ROOT + "/operations/revert/{idOperation}" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "revert an Operation", notes = "this methode can revert an Operation", response = Operation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Operation Object added "),
            @ApiResponse(code = 400, message = "Operation Object is invalid")
    })
    ResponseEntity<Operation> revert(@PathVariable("idOperation")  Integer idOperation);

}
