package tn.esprit.gestionbancaire.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AccountRequestStatus {
    OPEN,
    IN_PROGRESS,
    VALIDATED,
    WAITING_FOR_CLIENT,
    CLOSED;

    @JsonValue
    public String getStatus() {
        return this.name();
    }
}
