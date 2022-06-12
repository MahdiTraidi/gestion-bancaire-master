package tn.esprit.gestionbancaire.utils;

import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.model.Reclamation;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.TemporalField;
import java.util.List;

public interface MailTemplates {
    String WELCOME = "Welcome to our Bank.";

    String NEW_LAW = "We may ask you to visit our webSite, there has been a new laws set by the " +
            "government that you need to be aware of.";

    static String getTemplate(String template, String Name) {
        if (template.equalsIgnoreCase("WELCOME")) {
            return "Dear " + Name + ", \n" + WELCOME + "\nThank you for your trust.";
        } else if (template.equalsIgnoreCase("NEW")) {
            return "Dear " + Name + ", \n" + NEW_LAW + "\nThank you for your trust.";
        }
        return "Thank you for your trust.";
    }

    static String getNotif(ReclamationStatus reclamationStatus, String title){
        if (ReclamationStatus.IN_PROGRESS.equals(reclamationStatus)){
            return "Hello, \nYour reclamation '"+title+"' is under review now with one of our consultants, we'll provide " +
                    "you with an update when it's resolved."
                    + "\nThank you for your trust.";
        }
        return "Hello, \nYour reclamation '"+title+"' has been fixed and updated with the status "+ reclamationStatus.toString()+". " +
                "you can check it on our website for further information."
                + "\nThank you for your trust.";
    }
    static String creditStatusUpdatedTemplate(Credit credit, CreditStatus creditStatus){
        return "Hello "+ credit.getUser().getUserName() +" \n" +
                " \n" +
                "Your credit request status has been updated to "+ creditStatus.toString() +"\n" +
                " \n" +
                "Thanks for your trust.";
    }

    static String creditRequestCreated(Credit credit){
        return "Hello " + credit.getUser().getUserName() +" \n" +
                " \n" +
                "Your credit request has been created \n" +
                " \n" +
                "Thanks for your trust.";
    }

    static String getStats(List<Reclamation> reclamations) {
        double reclamations_created = 0;
        double resolved = 0;
        int current = 0;
        int open = 0;
        for (Reclamation reclamation : reclamations) {
            if (reclamation.getCreationDate().atZone(ZoneId.systemDefault()).getMonth().getValue() == Instant.now()
                    .atZone(ZoneId.systemDefault()).getMonth().getValue()) {
                reclamations_created++;
            } else {
                continue;
            }
            if (reclamation.getStatus().equals(ReclamationStatus.CLOSED) || reclamation.getStatus().equals(ReclamationStatus.RESOLVED)) {
                resolved++;
            } else if (reclamation.getStatus().equals(ReclamationStatus.IN_PROGRESS)) {
                current++;
            } else {
                open++;
            }
        }
        double rate=0;
        if (reclamations_created>0)
        rate = (resolved/reclamations_created)*100;

        return "Hello, \nThis month '" + (int)reclamations_created + "' reclamations has been created with : \n" +
                "=> "+open +" reclamations open \n" +
                "=> "+current +" reclamations currently in progress \n" +
                "=> "+(int)resolved +" reclamations resolved \n" +
                "Productivity rate is at "+ rate +"% \n"+
                "Good Job :)";
    }
}
