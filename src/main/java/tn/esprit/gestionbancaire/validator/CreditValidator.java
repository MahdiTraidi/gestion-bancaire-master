package tn.esprit.gestionbancaire.validator;

import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.model.CreditTemplate;

import java.util.ArrayList;
import java.util.List;

import static tn.esprit.gestionbancaire.utils.Constants.*;

public class CreditValidator {

    public static List<String> validate(Credit credit, CreditTemplate creditTemplate) {
        List<String> errors = new ArrayList<>();
        if (credit == null) {
            errors.add("Can you set Credit type (CAR CREDIT\n" +
                    "PERSONAL CREDIT\n" );
            return errors;
        }
        if ( credit.getAmount() < creditTemplate.getMinValue()  || credit.getAmount() > creditTemplate.getMaxValue() ){
            errors.add("Credit Amount should be in " +credit.getCreditTemplate().getMinValue() + " and "+credit.getCreditTemplate().getMaxValue());
        }
        if ( creditTemplate.getTitle().equals("Vehicle")){

            if( credit.getSelfFinancing() < credit.getAmount() * 0.2 ){
                errors.add("Self Financing must be > = 20% of credit amount " +credit.getAmount() * 0.2 );
            }
            if( credit.getVehicleFiscalPower() < 3){
                errors.add("Fiscal Power must be > 3 " );
            }
        }
        return errors;
    }

}
