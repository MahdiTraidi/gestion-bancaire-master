package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.enums.CreditStatus;
import java.util.List;
import java.util.Map;

public interface CreditService {

    Credit save(Credit credit);

    Credit updateCreditStatus(Integer idCredit, CreditStatus creditStatus);

    Credit findById(Integer id);

    List<Credit> findAll();

    List<Credit> findAllByUser(Integer id);

    List<Credit> findAllByCreditStatus(CreditStatus creditStatus);

    List<Credit> findAllNotArchived(Boolean archived);

    void delete(Integer id);

    List<String> addNote(Integer id,String note);
    //
    long countCreditByCreditStatus(CreditStatus status);

    List<Credit> autoValidate();

    Map<String, Integer> mostOpenedCreditByType();







}
