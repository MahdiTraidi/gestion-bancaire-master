package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tn.esprit.gestionbancaire.enums.OperationStatus;
import tn.esprit.gestionbancaire.enums.OperationSubType;
import tn.esprit.gestionbancaire.enums.OperationType;
import tn.esprit.gestionbancaire.enums.TransactionType;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.exception.InvalidOperationException;
import tn.esprit.gestionbancaire.model.CurrentAccount;
import tn.esprit.gestionbancaire.model.Operation;
import tn.esprit.gestionbancaire.model.Transaction;
import tn.esprit.gestionbancaire.repository.OperationRepository;
import tn.esprit.gestionbancaire.services.IOperationService;
import tn.esprit.gestionbancaire.services.ITransactionService;
import tn.esprit.gestionbancaire.validator.OperationValidator;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OperationServiceImpl implements IOperationService {
    @Autowired
    OperationRepository operationRepository;
    @Autowired
    TransactionServiceImpl transactionService ;
    @Autowired
    AccountServiceImpl accountService;

    @Override
    public Operation save(Operation operation,String x) {
        List<String> errors = OperationValidator.validate(operation);
        if (!errors.isEmpty()) {
            log.error("Operation is not valid {}", operation);
            throw new InvalidEntityException("Operation is not valid", ErrorCodes.OPERATION_NOT_VALID, errors);
        }
        operation.setOperationStatus(OperationStatus.TO_BE_EXECUTED);
        operation.setCreationDate(Instant.now());
        operation.setAccount(accountService.findByAccountNumber(x));
        Collection<Transaction> transactions = new ArrayList<>();
        if(operation.getOperationtype().equals(OperationType.PAYMENT) || operation.getOperationtype().equals(OperationType.RETRIEVE)){
            if(!operation.getOperationSubType().equals(OperationSubType.Regluement_Credit)){
                if(operation.getAmount().compareTo(operation.getAccount().getBalance())>0){
                    Operation o = operationRepository.save(operation);
                    Transaction T1 = new Transaction(operation.getDate(), TransactionType.CREDIT,false,false,o, operation.getAccount().getBalance());
                    Transaction T2 = transactionService.save(new Transaction(operation.getDate(), TransactionType.CREDIT,true,false,o,
                            operation.getAmount().subtract(operation.getAccount().getBalance()))
                    );
                    transactions.add(T1);
                    transactions.add(T2);
                    operation.setTransactions(transactions);

                }else{
                    Operation o = operationRepository.save(operation);
                    Transaction t1  =transactionService.save(new Transaction(operation.getDate(), TransactionType.CREDIT,false,false,o,operation.getAmount()));
                    operation.setTransactions(transactions);
                    transactions.add(t1);

                }}else{
                Operation o = operationRepository.save(operation);
                Transaction t1  =transactionService.save(new Transaction(operation.getDate(), TransactionType.CREDIT,false,false,o,operation.getAmount()));
                operation.setTransactions(transactions);
                transactions.add(t1);

            }

        }else{
            Operation o = operationRepository.save(operation);
            Transaction t1 = transactionService.save(new Transaction(operation.getDate(), TransactionType.DEBIT,false,false,o, operation.getAmount()));
            transactions.add(t1);
            operation.setTransactions(transactions);

        }
        operation.setOperationStatus(OperationStatus.EXECUTED);

        return operationRepository.save(operation);
    }

    @Override
    public Operation findOperationById(Integer id) {
        return operationRepository.findById(id).orElseThrow(
                () ->
                        new EntityNotFoundException(
                                "There is no Operation found with ID = " + id,
                                ErrorCodes.OPERATION_NOT_FOUND
                        ));
    }

    @Override
    public List<Operation> findOperationByAccount(long idAccount) {
        return operationRepository.findAll().stream().filter(x->idAccount==x.getAccount().getId()).collect(Collectors.toList());
    }

    @Override
    public Operation updateOperationStatus(Integer IdOperation, OperationStatus operationStatus) {
        checkIdOperation(IdOperation);
        checkStatus(operationStatus);
        if (operationStatus.equals(OperationStatus.EXECUTED) || operationStatus.equals(OperationStatus.CANCELLED)) {
           Operation o = this.findOperationById(IdOperation);
            o.setOperationStatus(operationStatus);
            return o;
        }
        return null;

    }

    @Override
    public List<Operation> getArchivedOperation(boolean isArchived) {
        return operationRepository.findAll().stream().filter(x -> x.getIsArchived().equals(isArchived)).collect(Collectors.toList());
    }



    @Override
    public List<Operation> getAllOperationByClient(long accountNumber) {
        return operationRepository.findAll().stream()
                .filter(x -> x.getAccount().getId() == accountNumber)
                .collect(Collectors.toList());
    }

    @Override
    public List<Operation> getAllOperationByClientAndStatus(long accountNumber, OperationStatus operationStatus) {
        Collection<Operation> operations = getAllOperationByClient(accountNumber);
        return operations.stream().filter(x -> x.getOperationStatus().equals(operationStatus)).collect(Collectors.toList());
    }

    @Override
    public Operation revertOperation(Integer idOperation) {
        Operation operation = this.findOperationById(idOperation);
       operation.setOperationStatus(OperationStatus.CANCELLED);
       if(operation.getAccount() instanceof CurrentAccount){
        manageRevertForCurrentAccount(operation);
       }else{
           manageRevertForSavingAccount(operation);
       }
        return operation;
    }

    private void manageRevertForSavingAccount(Operation operation) {
        List<Transaction> transactions = transactionService.getTransactionByOperation(operation.getId(),false);
        for (Transaction t : transactions){
            if(t.getTransactionType().equals(TransactionType.CREDIT)) {
                operation.getAccount().setBalance(operation.getAccount().getBalance().add(operation.getAmount()));
            }else{
                operation.getAccount().setBalance(operation.getAccount().getBalance().add(operation.getAmount().negate()));
            }
            transactionService.revertTransaction(t.getId());
        }
    }


    private void manageRevertForCurrentAccount(Operation operation) {
        manageRevertForSavingAccount(operation);
        List<Transaction> transactions = transactionService.getTransactionByOperation(operation.getId(),true);
    }

    private void checkIdOperation(Integer idopt) {
        if (idopt == null) {
            log.error("Operation ID is NULL");
            throw new InvalidOperationException("ID is null",
                    ErrorCodes.OPERATION_IS_NULL);
        }
    }

    private void checkStatus(OperationStatus operationStatus) {
        if (!StringUtils.hasLength(String.valueOf(operationStatus))) {
            log.error("Operation status is NULL");
            throw new InvalidOperationException("IS not allowed to chagne status to null",
                    ErrorCodes.CREDIT_NON_MODIFIABLE);
        }

    }

    public void processCreditBill(Map<Integer,Double> map ){
        LocalDate calendar =  LocalDate.now();
        map.forEach((k,v) ->this.save(new Operation(calendar.plusMonths(k),
                BigDecimal.valueOf(v)  ,true,OperationType.RETRIEVE, OperationSubType.Regluement_Credit,OperationStatus.TO_BE_EXECUTED),"INT")
        );
    }
}
