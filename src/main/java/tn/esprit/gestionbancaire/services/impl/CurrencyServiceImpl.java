package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidOperationException;
import tn.esprit.gestionbancaire.model.Currency;
import tn.esprit.gestionbancaire.repository.CurrencyRepository;
import tn.esprit.gestionbancaire.services.ICurrencyService;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CurrencyServiceImpl implements ICurrencyService {

    @Autowired
    CurrencyRepository currencyRepository;

    @Override
    public Currency save(Currency currency) {
        return currencyRepository.save(currency);
    }

    @Override
    public Currency updateCurrency(Integer idCurrency) {
        // Not more Used After Api Implementation
        return null;
    }

    @Override
    public Currency findByCode(String code) {

        return currencyRepository.findAll().stream()
                .filter(x -> x.getCode().equalsIgnoreCase(code)).findFirst().get();
    }

    @Override
    public List<Currency> findAll() {
        return currencyRepository.findAll();
    }

    @Override
    public List<Currency> findAvailableToExchange() {
        return null;
    }

    @Override
    public List<Currency> findAllCryptoCurrency() {
        return currencyRepository.findAll().stream()
                .filter(Currency::isCryptoCurrency).collect(Collectors.toList());
    }

    @Override
    public void delete(Integer id) {
        currencyRepository.deleteById(id);

    }


    private void checkSymbolCurrency(String SymbolCurrency) {
        if (SymbolCurrency == null) {
            log.error("Currency Symbol is NULL");
            throw new InvalidOperationException("Symbol is null",
                    ErrorCodes.CREDIT_IS_NULL);
        }
    }

    public MonetaryAmount convert(String currentCurrency, String targetCurrency, BigDecimal amount){

        CurrencyUnit currentCurrencyUnit = Monetary.getCurrency(currentCurrency);
        CurrencyUnit targetCurrencyUnit = Monetary.getCurrency(targetCurrency);

        MonetaryAmount current = Monetary.getDefaultAmountFactory().setCurrency(currentCurrencyUnit)
                .setNumber(amount).create();
        CurrencyConversion conversion = MonetaryConversions.getConversion(targetCurrencyUnit);
        MonetaryAmount convertedAmount = current.with(conversion);
        return convertedAmount;

    }

}
