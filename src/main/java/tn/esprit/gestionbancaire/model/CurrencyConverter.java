package tn.esprit.gestionbancaire.model;
import lombok.Data;
import org.javamoney.moneta.FastMoney;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
public class CurrencyConverter implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    private String currentCurrency;
    private String targetCurrency;
    private BigDecimal amount;

}
