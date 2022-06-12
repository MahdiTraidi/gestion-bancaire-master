package tn.esprit.gestionbancaire.model;

import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "user")
public class User extends AbstractEntity{

	@Column(name = "username")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "active")
	private Boolean active;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(value = FetchMode.SUBSELECT)
	@JsonManagedReference
	private List<Credit> credits;

    @OneToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "client_id", nullable = true)

	private Client client;

	@Column(name = "roles")
	private String roles;

	public User(String userName, String password, Boolean active, Client client, String roles) {
		this.userName = userName;
		this.password = password;
		this.active = active;
		this.client = client;
		this.roles = roles;
	}
	// getters and setters

	public int getAge() {
		Calendar a = getCalendar(client.getBirthDate());
		Calendar b = getCalendar(new Date());
		int diff = b.get(YEAR) - a.get(YEAR);
		if (a.get(MONTH) > b.get(MONTH) || (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
			diff--;
		}
		return diff;

	}

	public static Calendar getCalendar(Date date) {
		Calendar cal = Calendar.getInstance(Locale.US);
		cal.setTime(date);
		return cal;
	}




}
