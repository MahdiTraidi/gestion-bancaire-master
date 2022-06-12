package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;
import tn.esprit.gestionbancaire.model.AccountRequest;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("AccountRequest")
public interface AccountRequestApi {

    @PostMapping(APP_ROOT + "/account_request/request/{idTemplate}")
    @ApiOperation(value = "", notes = "", response = AccountRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Request created "),
            @ApiResponse(code = 400, message = "Request is invalid")
    })
    ResponseEntity<Object> saveForNewClient(@RequestBody AccountRequest accountRequest,
                                        @PathVariable("idTemplate") long idTemplate);

    @PatchMapping(value = APP_ROOT + "/account_request/request/progress/{idRequest}")
    @ApiOperation(value = "Change the status of the account request to in progress", notes = "this methode can Change the status of the account request to in progress", response = AccountRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AccountRequest status modified "),
            @ApiResponse(code = 400, message = "AccountRequest not found")
    })
    ResponseEntity<Object> setRequestInProgress(@PathVariable("idRequest") long idRequest);

    @GetMapping(APP_ROOT + "/account_request/request/getByStatus/{requestStatus}")
    @ApiOperation(value = "Find all account requests by status", notes = "This methode retrieve all account requests by status", responseContainer = "List<AccountRequest>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of AccountRequest by status")
    })
    List<AccountRequest> findRequestsByStatus(@PathVariable("requestStatus") AccountRequestStatus accountRequestStatus);

    @PatchMapping(value = APP_ROOT + "/account_request/request/sign/{idRequest}")
    @ApiOperation(value = "validate the request for account signature", notes = "this methode validate the request for account signature", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Email sent to the client "),
            @ApiResponse(code = 400, message = "AccountRequest not found")
    })
    ResponseEntity<Object> sendRequestToSignature(@PathVariable("idRequest") long idRequest);

    @PatchMapping(value = APP_ROOT + "/account_request/request/validate/{idRequest}")
    @ApiOperation(value = "validate the request and create the account", notes = "this methode validate the request and create the account", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account created "),
            @ApiResponse(code = 400, message = "AccountRequest not found")
    })
    ResponseEntity<Object> validateRequest(@PathVariable("idRequest") long idRequest);

    @PatchMapping(value = APP_ROOT + "/account_request/request/reject/{idRequest}")
    @ApiOperation(value = "reject the request and send email", notes = "this methode reject the request and send email", response = AccountRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account request rejected "),
            @ApiResponse(code = 400, message = "AccountRequest not found")
    })
    ResponseEntity<Object> refuseRequest(@PathVariable("idRequest") long idRequest, @RequestBody String  reason);

}
