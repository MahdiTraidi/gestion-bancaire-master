package tn.esprit.gestionbancaire.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CreditType implements Serializable {
    CONSUMPTION,
    STUDY,
    REAL_ESTATE;

    @JsonValue
    public String getType() {
        return this.name();
    }
}
