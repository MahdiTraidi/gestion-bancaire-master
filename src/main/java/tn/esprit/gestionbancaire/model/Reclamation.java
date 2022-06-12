package tn.esprit.gestionbancaire.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "reclamation")
public class Reclamation extends AbstractEntity {

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ReclamationStatus status;

    @Column(name = "reclamation_title")
    private String reclamationTitle;

    @Column(name = "reclamation_description")
    private String reclamationDescription;

    @Column(name = "assignee_name")
    private String assigneeName;

    @Column(name = "user_id")
    private Long userId;

    @JsonManagedReference
    @OneToMany(mappedBy = "reclamation", fetch = FetchType.LAZY)
    private List<ReclamationComment> reclamationComments;

    public boolean isReclamationClosed() {
        return (ReclamationStatus.CLOSED.equals(this.status));
    }
}
