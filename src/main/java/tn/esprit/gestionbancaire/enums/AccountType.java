package tn.esprit.gestionbancaire.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum AccountType implements Serializable {
    CURRENT,
    SAVINGS;

    @JsonValue
    public String getType() {
        return this.name();
    }
}
