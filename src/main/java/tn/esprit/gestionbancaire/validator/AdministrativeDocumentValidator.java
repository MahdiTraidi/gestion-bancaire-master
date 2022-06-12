package tn.esprit.gestionbancaire.validator;

import tn.esprit.gestionbancaire.model.AdministrativeDocument;
import tn.esprit.gestionbancaire.model.Credit;

import java.util.ArrayList;
import java.util.List;

public class AdministrativeDocumentValidator {

    public static List<String> validate(AdministrativeDocument administrativeDocument) {
        List<String> errors = new ArrayList<>();
        if (administrativeDocument == null) {
            errors.add("User Id is needed\n" +
                    "Photo is needed\n");
            return errors;
        }
        return errors;
    }
}
