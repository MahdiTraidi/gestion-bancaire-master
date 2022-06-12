package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.AccountRequest;
import tn.esprit.gestionbancaire.model.CardRequest;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("CardRequest")
public interface CardRequestApi {

    @PostMapping(APP_ROOT + "/card_request/request/{idTemplate}/{accountNumber}")
    @ApiOperation(value = "", notes = "", response = CardRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = " "),
            @ApiResponse(code = 400, message = "")
    })
    ResponseEntity<Object> requestCard(@PathVariable("idTemplate") long idTemplate,
                                       @PathVariable("accountNumber") String accountNumber);

    @PatchMapping(value = APP_ROOT + "/card_request/request/progress/{idRequest}")
    @ApiOperation(value = "Change the status of the card request to in progress", notes = "this methode can Change the status of the card request to in progress", response = CardRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CardRequest status modified "),
            @ApiResponse(code = 400, message = "CardRequest not found")
    })
    ResponseEntity<Object> setRequestInProgress(@PathVariable("idRequest") long idRequest);

    @GetMapping(APP_ROOT + "/card_request/request/getByStatus/{requestStatus}")
    @ApiOperation(value = "Find all card requests by status", notes = "This methode retrieve all card requests by status", responseContainer = "List<CardRequest>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of CardRequest by status")
    })
    List<CardRequest> findRequestsByStatus(@PathVariable("requestStatus") AccountRequestStatus accountRequestStatus);

    @PatchMapping(value = APP_ROOT + "/card_request/request/validate/{idRequest}")
    @ApiOperation(value = "validate the request and create the card", notes = "this methode validate the request and create the card", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Card created "),
            @ApiResponse(code = 400, message = "CardRequest not found")
    })
    ResponseEntity<Object> validateRequest(@PathVariable("idRequest") long idRequest);

    @PatchMapping(value = APP_ROOT + "/card_request/request/reject/{idRequest}")
    @ApiOperation(value = "reject the request and send email", notes = "this methode reject the request and send email", response = AccountRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Card request rejected "),
            @ApiResponse(code = 400, message = "CardRequest not found")
    })
    ResponseEntity<Object> refuseRequest(@PathVariable("idRequest") long idRequest, @RequestBody String  reason);

}
