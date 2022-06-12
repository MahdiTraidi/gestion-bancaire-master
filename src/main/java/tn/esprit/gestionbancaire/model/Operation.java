package tn.esprit.gestionbancaire.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import tn.esprit.gestionbancaire.enums.OperationStatus;
import tn.esprit.gestionbancaire.enums.OperationSubType;
import tn.esprit.gestionbancaire.enums.OperationType;
import tn.esprit.gestionbancaire.services.AccountService;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name="TYPE_OP",discriminatorType=DiscriminatorType.STRING,length=1)
@Data
public class Operation extends AbstractEntity implements Serializable {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    private BigDecimal amount;
    private Boolean isInternal;
    private OperationType operationtype;
    private OperationSubType operationSubType;
    private OperationStatus  operationStatus;
    private Boolean isArchived;
    @ManyToOne()
    @JoinColumn(name = "account_id")
    @JsonBackReference
    private Account account;

    @OneToMany(mappedBy = "operation", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonManagedReference
    private Collection<Transaction> transactions = new ArrayList<>();

    public Operation(LocalDate date, BigDecimal v, boolean b, OperationType retrieve, OperationSubType regluement_credit, OperationStatus toBeExecuted) {
        this.date = date;
        this.amount = v;
        this.isInternal = b;
        this.operationtype = retrieve;
        this.operationSubType = regluement_credit;
        this.operationStatus = toBeExecuted;
    }

    public Operation() {

    }


    public void addTransactions(Collection<Transaction> transactions){
        this.transactions.addAll(transactions);
    }
}
