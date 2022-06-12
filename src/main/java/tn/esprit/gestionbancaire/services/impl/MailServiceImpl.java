package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.config.EmailCfg;
import tn.esprit.gestionbancaire.enums.CreditStatus;
import tn.esprit.gestionbancaire.enums.ReclamationStatus;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.model.Reclamation;
import tn.esprit.gestionbancaire.model.User;
import tn.esprit.gestionbancaire.services.MailService;
import tn.esprit.gestionbancaire.services.UserService;
import tn.esprit.gestionbancaire.utils.MailTemplates;

import java.util.List;

@Service
@Slf4j
public class MailServiceImpl implements MailService {
    private final EmailCfg emailCfg;
    private final UserService userService;

     @Autowired
    public MailServiceImpl(EmailCfg emailCfg,UserService userService) {
        this.emailCfg = emailCfg;
        this.userService = userService;
    }
    @Override
    public void sendMail(String title) {
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        List<User> users = userService.getAll();
        for (User user:users ){

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("NoReply@SantanderBank.com");
        mailMessage.setTo(user.getClient().getEmail());
        mailMessage.setSubject(title);
        mailMessage.setText(MailTemplates.getTemplate(title,user.getClient().getFirstName()));

        // Send mail
        mailSender.send(mailMessage);

        }
    }

    @Override
    public void notify(ReclamationStatus reclamationStatus, String title) {
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("NoReply@SantanderBank.com");
        mailMessage.setTo("customer@gmail.com");
        mailMessage.setSubject("Update on your reclamation");
        mailMessage.setText(MailTemplates.getNotif(reclamationStatus,title));

        // Send mail
        mailSender.send(mailMessage);
    }

    @Override
    public void creditNotify(Credit credit, CreditStatus creditStatus) {
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("NoReply@SantanderBank.com");
        mailMessage.setTo(credit.getUser().getClient().getEmail());
        //mailMessage.setTo("customer@gmail.com");
        mailMessage.setSubject("Update on your Credit Request");
        if (creditStatus == null){
            mailMessage.setText(MailTemplates.creditRequestCreated(credit));
        }else {
            mailMessage.setText(MailTemplates.creditStatusUpdatedTemplate(credit,creditStatus));
        }

        // Send mail
        mailSender.send(mailMessage);
    }

    public void sendStats(List<Reclamation> reclamations) {
        // Create a mail sender
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.emailCfg.getHost());
        mailSender.setPort(this.emailCfg.getPort());
        mailSender.setUsername(this.emailCfg.getUsername());
        mailSender.setPassword(this.emailCfg.getPassword());

        // Create an email instance
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("NoReply@SantanderBank.com");
        mailMessage.setTo("admin@gmail.com");
        mailMessage.setSubject("Stats for this month");
        mailMessage.setText(MailTemplates.getStats(reclamations));

        // Send mail
        mailSender.send(mailMessage);
    }
}
