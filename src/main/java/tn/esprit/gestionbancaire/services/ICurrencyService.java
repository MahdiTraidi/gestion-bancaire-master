package tn.esprit.gestionbancaire.services;

import tn.esprit.gestionbancaire.model.Currency;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.util.List;

public interface ICurrencyService {

    Currency save(Currency currency);

    Currency updateCurrency(Integer idCurrency);

    Currency findByCode(String code);

    List<Currency> findAll();

    List<Currency> findAvailableToExchange();

    List<Currency> findAllCryptoCurrency();

    void delete(Integer id);

    MonetaryAmount convert(String currentCurrency, String targetCurrency, BigDecimal amount);
}
