package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.BatchApi;
import tn.esprit.gestionbancaire.model.Credit;
import tn.esprit.gestionbancaire.services.CreditService;

import java.util.List;
@RestController
public class BatchController  implements BatchApi {
    private CreditService creditService;

    @Autowired
    public BatchController(
            CreditService creditService
    ) {
        this.creditService = creditService;
    }
    @Override
    @Scheduled(cron ="00 00 20 */1 * *" )
    public List<Credit> autoValidatorBatch() {
        return creditService.autoValidate();
    }

}
