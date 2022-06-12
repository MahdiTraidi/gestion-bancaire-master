package tn.esprit.gestionbancaire.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tn.esprit.gestionbancaire.enums.AccountRequestStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EntityListeners(AuditingEntityListener.class)
public class AccountRequest implements Serializable {
    private static final long serialVersionUID = 7464451239676337813L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @CreatedDate
    @Column(name = "creationDate", nullable = false, updatable = false)
    private Instant creationDate;

    @LastModifiedDate
    @Column(name = "lastModifiedDate")
    private Instant lastModifiedDate;

    @ManyToOne
    private Client client;

    @ManyToOne
    private AccountTemplate accountTemplate;

    @Enumerated(EnumType.STRING)
    private AccountRequestStatus accountRequestStatus;

}
