package tn.esprit.gestionbancaire.model;

import lombok.*;
import tn.esprit.gestionbancaire.enums.CreditType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CreditTemplate extends Product {
    private static final long serialVersionUID = 2312601340596660509L;

    private String requirements;

    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    //  Indicate if this template needs a maxValue and minValue or the amount will
    //  be decided by the bank employee.
    private boolean isCustomValue;

    private long maxValue;

    private long minValue;

    //duration to pay the credit in months
    private int duration;

    public static List<String> getNullableAttributes(){
        return new ArrayList<>();
    }
}
