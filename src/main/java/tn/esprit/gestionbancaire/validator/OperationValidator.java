package tn.esprit.gestionbancaire.validator;

import tn.esprit.gestionbancaire.model.Operation;

import java.util.ArrayList;
import java.util.List;

public class OperationValidator {

    public static List<String> validate(Operation operation) {
        List<String> errors = new ArrayList<>();
        if (operation == null) {
            errors.add("Can you set The Operations Infos");
            return errors;
        }
        return errors;
    }

}
