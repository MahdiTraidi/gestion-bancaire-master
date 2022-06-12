package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.TransactionAPI;
import tn.esprit.gestionbancaire.enums.TransactionType;
import tn.esprit.gestionbancaire.model.Transaction;
import tn.esprit.gestionbancaire.services.ITransactionService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@RestController
public class TransactionController implements TransactionAPI {
    @Autowired
    ITransactionService transactionService;
    @Override
    public ResponseEntity<Transaction> save(Transaction transaction) {
        return ResponseEntity.ok(transactionService.save(transaction));
    }

    @Override
    public Transaction getTransactionById(Integer id) {
        return transactionService.findTransactionById(id);
    }

    @Override
    public List<Transaction> getTransactionBytype(TransactionType transactionType) {
        return transactionService.getTransactionByType(transactionType);
    }

    @Override
    public List<Transaction> getTransactionByOperation(Integer idOperation) {
        return transactionService.getTransactionByOperation(idOperation);
    }

    @Override
    public BigDecimal getYearlyNegBalanceByUser(Integer id, int year) {
        return transactionService.getYearlyNegBalanceByClient(id,year);
    }

    @Override
    public Integer countNegativeTransactionBalanceByAccount(Integer id) {
        return transactionService.countNegativeTransactionBalanceByAccount(id);
    }

    @Override
    public Integer countNegativeTransactionBalanceByUser(Integer idUser) {
        return transactionService.countNegativeBalanceByClient(idUser);
    }

    @Override
    public BigDecimal getAllNegativeBalance() {
        return transactionService.getAllNegativeBalance();
    }

    @Override
    public List<Transaction> getMonthlyTransactions(Date date) {
        return transactionService.getMonthlyTransactions(date);
    }

    @Override
    public List<Transaction> getMonthlyTransactionsByClient(Integer idUser, Date date) {
        return transactionService.getMonthlyTransactionsByClient(idUser,date);
    }

}
