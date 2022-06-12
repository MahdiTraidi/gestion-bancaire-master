package tn.esprit.gestionbancaire.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tn.esprit.gestionbancaire.enums.TransactionType;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
public class Transaction extends AbstractEntity implements Serializable {


    private LocalDate date;
    private TransactionType transactionType;
    private Boolean isNegativeTx;
    private Boolean isRevertedTransaction;
    private BigDecimal movement;


    @ManyToOne()
    @JoinColumn(name = "operation_id")
    @JsonBackReference
    private Operation operation;

    public Transaction(LocalDate date, TransactionType txtype, boolean b, boolean b1, Operation operation, BigDecimal m) {
        this.date = date;
        this.transactionType =txtype;
        this.isNegativeTx = b1;
      this.operation = operation;
        this.movement = m;
    }

    public Transaction() {

    }
}
