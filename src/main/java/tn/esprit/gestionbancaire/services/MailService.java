package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Reclamation;

import java.util.List;
import tn.esprit.gestionbancaire.model.Credit;

public interface MailService {

    void sendMail(String title);

    void notify(ReclamationStatus reclamationStatus, String title);

    void sendStats(List<Reclamation> reclamations);

    void creditNotify(Credit credit, CreditStatus creditStatus);
}
