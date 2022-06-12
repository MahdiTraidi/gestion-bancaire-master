package tn.esprit.gestionbancaire.validator;

import tn.esprit.gestionbancaire.model.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionValidator {
    public static List<String> validate(Transaction transaction) {
        List<String> errors = new ArrayList<>();
        if (transaction == null) {
            errors.add("Can you set The Transaction Infos");
            return errors;
        }
        return errors;
    }
}
