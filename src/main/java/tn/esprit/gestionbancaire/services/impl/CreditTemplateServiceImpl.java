package tn.esprit.gestionbancaire.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.enums.CreditType;
import tn.esprit.gestionbancaire.exception.EntityNotFoundException;
import tn.esprit.gestionbancaire.exception.ErrorCodes;
import tn.esprit.gestionbancaire.exception.InvalidEntityException;
import tn.esprit.gestionbancaire.model.CreditTemplate;
import tn.esprit.gestionbancaire.repository.CreditTemplateRepository;
import tn.esprit.gestionbancaire.services.CreditTemplateService;
import tn.esprit.gestionbancaire.validator.ProductsValidator;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CreditTemplateServiceImpl implements CreditTemplateService {

    private CreditTemplateRepository creditTemplateRepository;

    @Autowired
    public CreditTemplateServiceImpl(CreditTemplateRepository creditTemplateRepository) {
        this.creditTemplateRepository = creditTemplateRepository;
    }

    @Override
    public CreditTemplate save(CreditTemplate creditTemplate) {
        List<String> validate = ProductsValidator.validate(creditTemplate, CreditTemplate.getNullableAttributes());
        if (!validate.isEmpty()) {
            log.error("The credit template you want to add is not valid", validate);
            throw new InvalidEntityException("The credit template you want to add is not valid",
                    ErrorCodes.CREDIT_NOT_VALID, validate);
        }
        return creditTemplateRepository.save(creditTemplate);
    }

    @Override
    public CreditTemplate findById(long id) {
        return creditTemplateRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("There is no credit template with the ID: " + id,
                        ErrorCodes.CREDIT_TEMPLATE_NOT_FOUND)
        );
    }

    @Override
    public List<CreditTemplate> findByCreditType(CreditType creditType) {
        return creditTemplateRepository.findByCreditType(creditType);
    }

    @Override
    public List<CreditTemplate> findAll() {
        return creditTemplateRepository.findAll();
    }

    @Override
    public CreditTemplate update(CreditTemplate creditTemplate) {
        return null;
    }

    @Override
    public CreditTemplate findCreditTemplateByTitle(String title) {
        return creditTemplateRepository.findCreditTemplateByTitle(title);
    }

    @Override
    public void delete(long id) {
        Optional<CreditTemplate> byId = creditTemplateRepository.findById(id);
        if (byId.isPresent()) {
            creditTemplateRepository.deleteById(id);
        } else {
            log.error("There is no credit template with the ID: " + id);
            throw new EntityNotFoundException("There is no credit template with the ID: " + id,
                    ErrorCodes.CREDIT_TEMPLATE_NOT_FOUND);
        }
    }
}
