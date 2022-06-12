package tn.esprit.gestionbancaire.batch;

import org.springframework.batch.item.ItemProcessor;
import tn.esprit.gestionbancaire.model.Credit;

public class CreditProcessor implements ItemProcessor<Credit, Credit> {

    @Override
    public Credit process(Credit credit) throws Exception {

        return credit;
    }

}
