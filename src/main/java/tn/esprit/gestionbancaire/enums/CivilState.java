package tn.esprit.gestionbancaire.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.io.Serializable;

public enum CivilState implements Serializable {
	Maried,
	Single;

	@JsonValue
	public String getCState() {
		return this.name();
	}
}
