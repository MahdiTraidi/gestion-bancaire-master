package tn.esprit.gestionbancaire.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card implements Serializable {

    private static final long serialVersionUID = -4001423874330476267L;

    private String cardNumber;

    private boolean isBlocked;

    //This should be reset to 0 every week
    private float withdrawnAmount;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private CardTemplate cardTemplate;

    @ManyToOne
    @JoinColumn(name = "Account_Id")
    @JsonManagedReference
    private Account account;

}
