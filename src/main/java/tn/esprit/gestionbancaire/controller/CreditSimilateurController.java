package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CreditSimilateurApi;
import tn.esprit.gestionbancaire.services.CreditSimulateurService;

import java.util.Map;
@RestController
public class CreditSimilateurController implements CreditSimilateurApi {

    private CreditSimulateurService creditSimulateurService;

    @Autowired
    public CreditSimilateurController(
            CreditSimulateurService creditSimulateurService
    ) {
        this.creditSimulateurService = creditSimulateurService;
    }

    @Override
    public Map<Integer, Double> vehicleCredit(double vehicleAmout, Integer vehicleFiscalPower, double selfFinancing, Integer repaymentPeriod) {
        return creditSimulateurService.vehicleCredit(vehicleAmout,vehicleFiscalPower,selfFinancing,repaymentPeriod);
    }

    @Override
    public Map<Integer, Double> vehicleCredit(double vehicleAmout, Integer vehicleFiscalPower, double selfFinancing, Integer repaymentPeriod, double rate) {
        return creditSimulateurService.vehicleCredit(vehicleAmout,vehicleFiscalPower,selfFinancing,repaymentPeriod,rate);
    }

    @Override
    public Map<Integer, Double> personalCredit(double creditAmout, Integer repaymentPeriod) {
        return  creditSimulateurService.prsonalCredit(creditAmout,repaymentPeriod);
    }

    @Override
    public Map<Integer, Double> personalCredit(double creditAmout, Integer repaymentPeriod, double rate) {
        return  creditSimulateurService.prsonalCredit(creditAmout,repaymentPeriod,rate);
    }

    @Override
    public Map<String, Integer> getUserUserOverView(Integer id) {
        return creditSimulateurService.getUserStatusOverView(id);
    }


}
