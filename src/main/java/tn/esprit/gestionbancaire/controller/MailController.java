package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.MailApi;
import tn.esprit.gestionbancaire.services.MailService;

@RestController
public class MailController implements MailApi {
    private final MailService mailService;

    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @Override
    public void sendMAil(String title) {
        mailService.sendMail(title);
    }


}
