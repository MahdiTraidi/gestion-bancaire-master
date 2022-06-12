package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.CardTemplate;
import tn.esprit.gestionbancaire.repository.CardTemplateRepository;
import tn.esprit.gestionbancaire.services.CardTemplateService;
import tn.esprit.gestionbancaire.validator.ProductsValidator;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CardTemplateServiceImpl implements CardTemplateService {

    private CardTemplateRepository cardTemplateRepository;

    @Autowired
    public CardTemplateServiceImpl(CardTemplateRepository cardTemplateRepository) {
        this.cardTemplateRepository = cardTemplateRepository;
    }

    @Override
    public CardTemplate save(CardTemplate cardTemplate) throws InvalidEntityException {
        List<String> validate = ProductsValidator.validate(cardTemplate, CardTemplate.getNullableAttributes());
        if (!validate.isEmpty()) {
            log.error("The card template you want to add is not valid", validate);
            throw new InvalidEntityException("The card template you want to add is not valid",
                    ErrorCodes.CARD_TEMPLATE_NOT_VALID, validate);
        }
        return cardTemplateRepository.save(cardTemplate);
    }

    @Override
    public CardTemplate findById(long id) {
        return cardTemplateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no account template with the ID: " + id,
                        ErrorCodes.CARD_TEMPLATE_NOT_FOUND)
        );
    }

    @Override
    public List<CardTemplate> findAll() {
        return cardTemplateRepository.findAll();
    }

    @Override
    public CardTemplate update(CardTemplate cardTemplate) {
        return null;
    }

    @Override
    public void delete(long id) {
        Optional<CardTemplate> byId = cardTemplateRepository.findById(id);
        if(byId.isPresent()){
            cardTemplateRepository.deleteById(id);
        }else {
            log.error("There is no card template with the ID: " + id);
            throw new EntityNotFoundException("There is no card template with the ID: " + id,
                    ErrorCodes.CARD_TEMPLATE_NOT_FOUND);
        }
    }
}
