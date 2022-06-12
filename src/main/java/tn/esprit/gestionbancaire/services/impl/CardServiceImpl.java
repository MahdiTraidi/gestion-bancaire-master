package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.enums.AccountType;
import tn.esprit.gestionbancaire.model.*;
import tn.esprit.gestionbancaire.repository.CardRepository;
import tn.esprit.gestionbancaire.services.CardService;
import tn.esprit.gestionbancaire.utils.AccountNumberGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    @Autowired
    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findByCardNumber(String cardNumber) {
        Optional<Card> card = cardRepository.findByCardNumber(cardNumber);
        if (card.isPresent()) {
            return card.get();
        }
        return null;
    }

    public Card generateCard(CardRequest cardRequest) {
        Card card = new Card();

        Card byCardNumber;
        String generateCardNumber;
        do {
            generateCardNumber = AccountNumberGenerator.generate();
            byCardNumber = findByCardNumber(generateCardNumber);
        } while (byCardNumber != null);
        card.setAccount(cardRequest.getAccount());
        card.setCardNumber(generateCardNumber);
        card.setBlocked(false);
        card.setCardTemplate(cardRequest.getCardTemplate());

        return cardRepository.save(card);
    }
}
