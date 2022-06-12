package tn.esprit.gestionbancaire.controller.api;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import static tn.esprit.gestionbancaire.utils.Constants.APP_ROOT;

@Api("mail")
public interface MailApi {
    @PostMapping( APP_ROOT + "/send/{title}")
    void sendMAil(@PathVariable("title") String title);

}
