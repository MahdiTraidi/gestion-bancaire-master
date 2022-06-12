package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.CreditType;
import tn.esprit.gestionbancaire.model.CreditTemplate;

import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("CreditTemplate")
public interface CreditTemplateApi {
    @PostMapping(value = APP_ROOT + "/credit_template/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Add a credit template", notes = "this methode can add a credit template", response = CreditTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credit template added "),
            @ApiResponse(code = 400, message = "Credit template is invalid")
    })
    ResponseEntity<Object> save(@RequestBody CreditTemplate creditTemplate);

    @GetMapping(value = APP_ROOT + "/credit_template/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all credits templates", notes = "This methode get all credits templates ", responseContainer = "List<CreditTemplate>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of CreditTemplate")
    })
    List<CreditTemplate> findAll();

    @DeleteMapping(APP_ROOT + "/credit_template/delete/{idCreditTemplate}")
    @ApiOperation(value = "Delete credit template", notes = "This methode deletes a credit template", response = CreditTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CreditTemplate deleted "),
            @ApiResponse(code = 400, message = "CreditTemplate not found")
    })
    ResponseEntity<Object> deleteCreditTemplate(@PathVariable("idCreditTemplate") long idCreditTemplate);

    @GetMapping(APP_ROOT + "/credit_template/find/{idCreditTemplate}")
    @ApiOperation(value = "Find a credit template", notes = "This methode retrieve a credit template", response = CreditTemplate.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "CreditTemplate retrieved"),
            @ApiResponse(code = 400, message = "CreditTemplate not found")
    })
    ResponseEntity<Object> findAccountTemplate(@PathVariable("idCreditTemplate") long idCreditTemplate);

    @GetMapping(APP_ROOT + "/credit_template/findByType/{creditType}")
    @ApiOperation(value = "Find a credit template by type", notes = "This methode retrieve a credit template", responseContainer = "List<CreditTemplate>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of CreditTemplate by type")
    })
    List<CreditTemplate> findAccountTemplateByType(@PathVariable("creditType") CreditType creditType);
}
