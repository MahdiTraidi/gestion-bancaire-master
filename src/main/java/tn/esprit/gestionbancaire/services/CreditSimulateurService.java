package tn.esprit.gestionbancaire.services;

import java.util.Map;

public interface CreditSimulateurService {
    Map<Integer, Double> prsonalCredit(double creditAmout, Integer repaymentPeriod);
    Map<Integer, Double> vehicleCredit(double vehicleAmout, Integer vehicleFiscalPower, double selfFinancing, Integer repaymentPeriod);


    Map<Integer, Double> prsonalCredit(double creditAmout, Integer repaymentPeriod, double rate);
    Map<Integer, Double> vehicleCredit(double vehicleAmout, Integer vehicleFiscalPower, double selfFinancing, Integer repaymentPeriod, double rate);

    double getUserRate(double score);

    Map<String,Integer> getUserStatusOverView(Integer id);
}
