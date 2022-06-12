package tn.esprit.gestionbancaire.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class CardTemplate extends Product {
    private static final long serialVersionUID = -1803522953297566209L;

    private long cap;

    private boolean onlinePurchase;

    private boolean otherATMWithdraw;

    private boolean forBusiness;

    private float cardFees;

    public static List<String> getNullableAttributes(){
        return new ArrayList<>();
    }

}
