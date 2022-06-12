package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.model.Currency;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("currencies")
public interface CurrencyAPI {

    @PostMapping(value = APP_ROOT + "/currencies/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a Currency", notes = "this methode can add new Currency", response = Currency.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency Object added "),
            @ApiResponse(code = 400, message = "Currency Object is invalid")
    })
    ResponseEntity<Currency> save(@RequestBody Currency currency);


    @PatchMapping(APP_ROOT + "/currencies/update/currency/{idCurrency}")
    @ApiOperation(value = "Modify Currency", notes = "this methode can modify Currency", response = Currency.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency modified "),
            @ApiResponse(code = 400, message = "Currency is invalid")
    })
    ResponseEntity<Currency> updateCurrency(@PathVariable("idCurrency") Integer idCCurrency,@RequestBody Currency currency );

    @GetMapping(value = APP_ROOT + "/currencies/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all currencies", notes = "This methode get all Currency ", responseContainer = "List<Currency>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Currency / Void list")
    })
    ResponseEntity<List<Currency>> getAll();

    @GetMapping(value = APP_ROOT + "/currencies/available", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all availables currencies", notes = "This methode get all available Currency ", responseContainer = "List<Currency>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Currency / Void list")
    })
    ResponseEntity<List<Currency>>  getAvailableToExchange();

    @GetMapping(value = APP_ROOT + "/currencies/crypto", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all CryptoCurrency", notes = "This methode get all CryptoCurrency ", responseContainer = "List<Currency>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Currency / Void list")
    })
    ResponseEntity<List<Currency>>  getAllCryptoCurrency();

    @DeleteMapping(APP_ROOT + "/currencies/delete/currency/{idCurrency}")
    @ApiOperation(value = "Delete currency", notes = "This methode can delete Currency", response = Currency.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency deleted "),
            @ApiResponse(code = 400, message = "Currency is invalid")
    })
    void deleteCurrency(@PathVariable("idCurrency") Integer idCurrency);

    @GetMapping(value = APP_ROOT + "/currencies/find", consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "find currency by code", notes = "this methode can find currency by code", response = Currency.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency found "),
            @ApiResponse(code = 404, message = "Currency is not found")
    })
    ResponseEntity<Currency> getByName(@RequestBody String code);

   /* @GetMapping(APP_ROOT + "/currencies/Convert/{from}/{to}")
    @ApiOperation(value = "convert currencies", notes = "this methode can convert currencies", response = String.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Currency exchanged "),
            @ApiResponse(code = 404, message = "Currency is not exchanged")
    })
    ResponseEntity<String> retrieveExchangeValue(@PathVariable("from") String from,@PathVariable("to") String to,@RequestBody BigDecimal quantity);*/
   @PostMapping(value = APP_ROOT + "/currencies/convert")
   @ResponseBody
   MonetaryAmount convert(@RequestParam String currentCurrency, String targetCurrency, BigDecimal amount) ;
}
