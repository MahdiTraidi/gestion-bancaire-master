package tn.esprit.gestionbancaire.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class Currency implements Serializable {

    private static final long serialVersionUID = 2948915794988044361L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String symbol;
    private String name;
    private Integer decimalDigits;
    private Integer rounding;
    private String code;
    private boolean isCryptoCurrency;

}
