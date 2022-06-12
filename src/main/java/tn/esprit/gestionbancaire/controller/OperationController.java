package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.OperationAPI;
import tn.esprit.gestionbancaire.enums.OperationStatus;
import tn.esprit.gestionbancaire.model.Operation;
import tn.esprit.gestionbancaire.services.IOperationService;

import java.util.List;

@RestController
public class OperationController implements OperationAPI {
    @Autowired
    IOperationService operationService;
    @Override
    public ResponseEntity<Operation> save(String x , Operation operation) {
        return ResponseEntity.ok(operationService.save(operation,x));
    }

    @Override
    public ResponseEntity<Operation> updateCreditStatus(Integer idCredit, OperationStatus operationStatus) {
        return ResponseEntity.ok(operationService.updateOperationStatus(idCredit,operationStatus));
    }

    @Override
    public Operation getOperationById(Integer id) {
        return operationService.findOperationById(id);
    }

    @Override
    public List<Operation> findAllByArchived(Boolean archived) {
        return operationService.getArchivedOperation(archived);
    }

    @Override
    public List<Operation> findAllByUser(Integer id) {
        return operationService.getAllOperationByClient(id);
    }

    @Override
    public List<Operation> getAllOperationByClientAndStatus(Integer id, OperationStatus operationStatus) {
        return operationService.getAllOperationByClientAndStatus(id,operationStatus);
    }

    @Override
    public ResponseEntity<Operation> revert(Integer idOperation) {
        return ResponseEntity.ok(operationService.revertOperation(idOperation));
    }
}
