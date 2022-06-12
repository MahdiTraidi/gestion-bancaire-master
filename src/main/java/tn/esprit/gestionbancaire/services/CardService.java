package tn.esprit.gestionbancaire.services;


import tn.esprit.gestionbancaire.model.Card;
import tn.esprit.gestionbancaire.model.CardRequest;

public interface CardService {

    Card findByCardNumber(String cardNumber);
    Card generateCard(CardRequest cardRequest);

}
