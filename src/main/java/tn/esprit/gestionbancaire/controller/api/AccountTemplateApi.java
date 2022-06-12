package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.model.AccountTemplate;

import java.util.List;
import java.util.Map;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("AccountTemplate")
public interface AccountTemplateApi {

    @PostMapping(value = APP_ROOT + "/account_template/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add an account template", notes = "this methode can add an account template", response = AccountTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Account template added "),
            @ApiResponse(code = 400, message = "Account template is invalid")
    })
    ResponseEntity<Object> save(@RequestBody AccountTemplate accountTemplate);

    @GetMapping(value = APP_ROOT + "/account_template/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all account templates", notes = "This methode get all account templates ", responseContainer = "List<AccountTemplate>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of AccountTemplate")
    })
    List<AccountTemplate> findAll();

    @DeleteMapping(APP_ROOT + "/account_template/delete/{idAccountTemplate}")
    @ApiOperation(value = "Delete account template", notes = "This methode deletes an account template", response = AccountTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AccountTemplate deleted "),
            @ApiResponse(code = 400, message = "AccountTemplate not found")
    })
    ResponseEntity<Object> deleteAccountTemplate(@PathVariable("idAccountTemplate") long idAccountTemplate);

    @GetMapping(APP_ROOT + "/account_template/find/{idAccountTemplate}")
    @ApiOperation(value = "Find an account template", notes = "This methode retrieve an account template", response = AccountTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AccountTemplate retrieved"),
            @ApiResponse(code = 400, message = "AccountTemplate not found")
    })
    ResponseEntity<Object> findAccountTemplate(@PathVariable("idAccountTemplate") long idAccountTemplate);

    @GetMapping(APP_ROOT + "/account_template/stats/account_per_template")
    @ApiOperation(value = "acc", notes = "aaa", responseContainer = "Map<String, Integer>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "AccountTemplate retrieved"),
            @ApiResponse(code = 400, message = "AccountTemplate not found")
    })
    Map<String, Integer> getAccountsPerTemplate();
}
