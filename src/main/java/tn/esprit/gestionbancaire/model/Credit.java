package tn.esprit.gestionbancaire.model;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tn.esprit.gestionbancaire.enums.CreditStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@ToString
@Table(name = "credit")
public class Credit extends AbstractEntity {

    @Column(name = "amount", nullable = false)
    private double amount;

    @Column(name = "repaymentperiod" , nullable = false)
    private int repaymentPeriod;

    @Column(name = "vehiclefiscalpower" , nullable = false)
    private int vehicleFiscalPower;

    @Column(name = "selffinancing" , nullable = false)
    private double selfFinancing;

    @ManyToOne
    private CreditTemplate creditTemplate;

    @Column(name = "creditstatus")
    @Enumerated(EnumType.STRING)
    private CreditStatus creditStatus;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "notes", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "note")
    private List<String> notes;

    @JsonIgnore
    @Column(name = "archived", nullable = false, columnDefinition = "bit default 0")
    private boolean archived;

    @OneToMany(mappedBy = "credit", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private List<AdministrativeDocument> administrativeDocuments;

    @ManyToOne
    @JsonBackReference
    //@JsonIgnore
    @JoinColumn(name = "user")
    private User user;

    public boolean isCreditClosed() {
        return (CreditStatus.ACCEPTED.equals(this.creditStatus) || CreditStatus.REFUSED.equals(this.creditStatus));
    }
}

