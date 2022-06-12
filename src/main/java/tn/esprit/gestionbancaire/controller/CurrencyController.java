package tn.esprit.gestionbancaire.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionbancaire.controller.api.CurrencyAPI;
import tn.esprit.gestionbancaire.model.Currency;
import tn.esprit.gestionbancaire.services.ICurrencyService;
import tn.esprit.gestionbancaire.services.impl.CurrencyServiceImpl;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CurrencyController implements CurrencyAPI {

    @Autowired
    ICurrencyService currencyService;
    CurrencyServiceImpl currencyConverterService;
    @Override
    public ResponseEntity<Currency> save(Currency currency) {
        return ResponseEntity.ok(currencyService.save(currency));
    }

    @Override
    public ResponseEntity<Currency> updateCurrency(Integer idCCurrency, Currency currency) {
        //TODO
        return null;
    }

    @Override
    public ResponseEntity<List<Currency>>getAll() {
        return new ResponseEntity<List<Currency>>(currencyService.findAll(), HttpStatus.OK) ;
    }

    @Override
    public ResponseEntity<List<Currency>>  getAvailableToExchange() {
        return ResponseEntity.ok(currencyService.findAvailableToExchange());
    }

    @Override
    public ResponseEntity<List<Currency>>  getAllCryptoCurrency() {
        return ResponseEntity.ok(currencyService.findAllCryptoCurrency());
    }

    @Override
    public void deleteCurrency(Integer idCurrency) {
        currencyService.delete(idCurrency);
    }

    @Override
    public ResponseEntity<Currency> getByName(String code) {
        return ResponseEntity.ok(currencyService.findByCode(code));
    }

    @Override
    public MonetaryAmount convert(String currentCurrency, String targetCurrency, BigDecimal amount) {
        return   currencyService.convert(currentCurrency,targetCurrency,amount);
    }

  /*  @Override
    public ResponseEntity<String> retrieveExchangeValue(String from, String to, BigDecimal quantity) {
        Currency fromCurrency = currencyService.findByCode(from);
        Currency toCurrency = currencyService.findByCode(to);

        return ResponseEntity.ok(quantity.multiply(fromCurrency.getSellValue())
                .divide(toCurrency.getBuyValue()).toPlainString());
    }*/
}
