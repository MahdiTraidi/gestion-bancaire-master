package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.enums.TransactionType;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.exception.InvalidOperationException;
import tn.esprit.gestionbancaire.model.Operation;
import tn.esprit.gestionbancaire.model.Transaction;
import tn.esprit.gestionbancaire.repository.TransactionRepository;
import tn.esprit.gestionbancaire.services.*;
import tn.esprit.gestionbancaire.validator.TransactionValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TransactionServiceImpl implements ITransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    IOperationService operationService;
    AccountService accountService;
    ClientService clientService;

    @Override
    public Transaction save(Transaction transaction) {
        List<String> errors = TransactionValidator.validate(transaction);
        if (!errors.isEmpty()) {
            log.error("transaction is not valid {}", transaction);
            throw new InvalidEntityException("Operation is not valid", ErrorCodes.OPERATION_NOT_VALID, errors);
        }
        transaction.setCreationDate(Instant.now());
        transaction.setTransactionType(TransactionType.DEBIT);
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction findTransactionById(Integer id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(
                        "There is no Transaction found with ID = " + id,
                        ErrorCodes.TX_NOT_FOUND)
        );
    }

    @Override
    public List<Transaction> getTransactionByType(TransactionType transactionType) {
        return transactionRepository.findAll().stream()
                .filter(x -> x.getTransactionType().equals(transactionType)).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionByOperation(long idOperation, Boolean isNegative) {
        return transactionRepository.findAll().stream().filter(x -> x.getOperation().getId() == idOperation).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getTransactionByOperation(long idOperation) {
        return transactionRepository.findAll().stream().filter(x -> x.getOperation().getId() == idOperation).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getYearlyNegBalanceByClient(long idUser, int year) {

        List<Operation> OpByUser = operationService.getAllOperationByClient(idUser)
                .stream().filter(x -> x.getDate().getYear() == year).collect(Collectors.toList());
        BigDecimal a = BigDecimal.ZERO;
        OpByUser.stream().forEach(operation -> this.getTransactionByOperation(operation.getId(), true)
                .stream().forEach((Consumer<? super Transaction>) a.add(operation.getAmount())));
        return a;
    }

    @Override
    public Integer countNegativeTransactionBalanceByAccount(long idAccount) {
        int a = 0;
       List <Operation> operations = operationService.findOperationByAccount(idAccount);
       for(Operation o : operations){
          List<Transaction> transactions = this.getTransactionByOperation(o.getId(),true);
          a +=  transactions.size();
       }
       return a;
    }

    @Override
    public Integer countNegativeBalanceByClient(long idClient) {

        return null; //clientService.findById(idClient);
    }

    @Override
    public BigDecimal getAllNegativeBalance() {
        BigDecimal a = BigDecimal.ZERO;
      List<Transaction> transactions = transactionRepository.findAll().stream().filter(x-> x.getIsNegativeTx()).collect(Collectors.toList());
      for(Transaction t : transactions){
          a.add(t.getOperation().getAmount());
      }
      return a;
    }

    @Override
    public List<Transaction> getMonthlyTransactions(Date date) {
        return  transactionRepository.findAll().stream().filter(x -> x.getDate().getMonth().equals(date.getMonth())).collect(Collectors.toList());
    }

    @Override
    public List<Transaction> getMonthlyTransactionsByClient(Integer id,Date date) {
        return null;
    }


    @Override
    public Transaction revertTransaction(Integer id) {
        checkIdTransaction(id);
        Transaction t = this.findTransactionById(id);
        t.setIsRevertedTransaction(true);
        return t;

    }


    private void checkIdTransaction(Integer idTxt) {
        if (idTxt == null) {
            log.error("Transaction ID is NULL");
            throw new InvalidOperationException("ID is null",
                    ErrorCodes.TX_IS_NULL);
        }
    }


}
