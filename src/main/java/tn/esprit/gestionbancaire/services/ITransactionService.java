package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.TransactionType;
import tn.esprit.gestionbancaire.model.Transaction;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface ITransactionService {

    Transaction save(Transaction transaction);

    Transaction findTransactionById(Integer id);

    List<Transaction> getTransactionByType(TransactionType transactionType);

    List<Transaction> getTransactionByOperation(long idOperation, Boolean isNegative);

    List<Transaction> getTransactionByOperation(long idOperation);

    BigDecimal getYearlyNegBalanceByClient(long idUser, int year);

    Integer countNegativeTransactionBalanceByAccount(long idUser);

    Integer countNegativeBalanceByClient(long idUser);

    BigDecimal getAllNegativeBalance();

    List<Transaction> getMonthlyTransactions(Date date);

    List<Transaction> getMonthlyTransactionsByClient(Integer idClient, Date date);

    Transaction revertTransaction(Integer id);


}
