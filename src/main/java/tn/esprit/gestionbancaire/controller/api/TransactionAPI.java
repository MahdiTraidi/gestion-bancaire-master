package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionbancaire.enums.OperationType;
import tn.esprit.gestionbancaire.enums.TransactionType;
import tn.esprit.gestionbancaire.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("transactions")
public interface TransactionAPI {

    @PostMapping(value = APP_ROOT + "/transactions/create" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "add a Transaction", notes = "this methode can add new Transaction", response = Transaction.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Transaction Object added "),
            @ApiResponse(code = 400, message = "Transaction Object is invalid")
    })
    ResponseEntity<Transaction> save(@RequestBody Transaction transaction);

    @GetMapping(value = APP_ROOT + "/transactions/findbyid/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Transaction by id", notes = "This methode Get Transaction by id ", responseContainer = "Transaction")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Transaction / Void list")
    })
    Transaction getTransactionById(@PathVariable("id") Integer id);

    @GetMapping(value = APP_ROOT + "/transactions/findbytype/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Transactions by type", notes = "This methode Get Transactions by type ", responseContainer = "List<Transaction>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Transaction / Void list")
    })
    List<Transaction> getTransactionBytype(@PathVariable("type") TransactionType transactionType);

    @GetMapping(value = APP_ROOT + "/transactions/findbyoperation/{idOperation}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get Transactions by Operation", notes = "This methode Get Transaction by operations ", responseContainer = "List<Transaction>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Transaction / Void list")
    })
    List<Transaction> getTransactionByOperation(@PathVariable("idOperation") Integer idOperation);

    @GetMapping(value = APP_ROOT + "/transactions/{year}/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get sum of negative balance by User", notes = "This methode get sum of negative balance by User for this year ", responseContainer = "BigDecimal")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sum")
    })
    BigDecimal getYearlyNegBalanceByUser(@PathVariable("idUser") Integer id, @PathVariable("year") int year);


    @GetMapping(value = APP_ROOT + "/transactions/account/{idAccount}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get sum of negative balance by Account", notes = "This methode get sum of negative balance by Account ", responseContainer = "Integer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sum")
    })
    Integer countNegativeTransactionBalanceByAccount(@PathVariable("idAccount") Integer id);


    @GetMapping(value = APP_ROOT + "/transactions/user/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get sum of negative balance by user", notes = "This methode get sum of negative balance by user ", responseContainer = "Integer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sum")
    })
    Integer countNegativeTransactionBalanceByUser(@PathVariable("idUser") Integer idUser);

    @GetMapping(value = APP_ROOT + "/transactions/internal/negative", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get sum of negative balance by user", notes = "This methode get sum of negative balance by user ", responseContainer = "Integer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The sum")
    })
    BigDecimal getAllNegativeBalance();

    @GetMapping(value = APP_ROOT + "/transactions/", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all Transactions for this month", notes = "This methode get all Transactions for this month ", responseContainer = "List<Transaction>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Transaction / Void list")
    })
    List<Transaction> getMonthlyTransactions(@RequestParam("date") Date date);

    @GetMapping(value = APP_ROOT + "/transactions/{idUser}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get all Transactions by Client for this month", notes = "This methode get all Transactions by client for this month ", responseContainer = "List<Transaction>")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of Transaction / Void list")
    })
    List<Transaction> getMonthlyTransactionsByClient(@PathVariable("idUser") Integer idUser ,@RequestParam("date") Date date);

}
