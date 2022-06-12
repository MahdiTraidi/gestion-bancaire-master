package tn.esprit.gestionbancaire.model;
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.esprit.gestionbancaire.enums.CivilState;
import tn.esprit.gestionbancaire.enums.Sexe;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nID;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String phoneNumber;
    private int age;
    private Date birthDate;
    private String job;
    private boolean validated;
	private Sexe sexe;
	private CivilState civilState;
    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "user_id", nullable = true)

    User user;
	public Client(String nID, String firstName, String lastName, Date birthDate, String job, boolean validated,
			Sexe sexe, CivilState civilState,String email) {
		super();
		this.nID = nID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.job = job;
		this.email = email;
		this.validated = validated;
		this.sexe = sexe;
		this.civilState = civilState;
	}

}
