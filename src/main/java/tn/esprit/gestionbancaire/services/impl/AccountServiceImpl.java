package tn.esprit.gestionbancaire.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionbancaire.enums.AccountType;
import tn.esprit.gestionbancaire.model.Account;
import tn.esprit.gestionbancaire.model.AccountRequest;
import tn.esprit.gestionbancaire.model.CurrentAccount;
import tn.esprit.gestionbancaire.model.SavingsAccount;
import tn.esprit.gestionbancaire.repository.CurrentAccountRepository;
import tn.esprit.gestionbancaire.repository.SavingsAccountRepository;
import tn.esprit.gestionbancaire.services.AccountService;
import tn.esprit.gestionbancaire.utils.AccountNumberGenerator;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private CurrentAccountRepository currentAccountRepository;
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    public AccountServiceImpl(CurrentAccountRepository currentAccountRepository,
                              SavingsAccountRepository savingsAccountRepository) {
        this.currentAccountRepository = currentAccountRepository;
        this.savingsAccountRepository = savingsAccountRepository;
    }

    public Account findByAccountNumber(String accountNumber) {
        Optional<Account> currentAccount = currentAccountRepository.findByAccountNumber(accountNumber);
        if (currentAccount.isPresent()) {
            return currentAccount.get();
        }
        Optional<Account> savingsAccount = savingsAccountRepository.findByAccountNumber(accountNumber);
        if (savingsAccount.isPresent()) {
            return savingsAccount.get();
        }
        return null;
    }

    public Account generateAccount(AccountRequest accountRequest) {
        AccountType accountType = accountRequest.getAccountTemplate().getAccountType();
        Account account = null;
        if (accountType.equals(AccountType.CURRENT)) {
            account = new CurrentAccount();
        }
        if (accountType.equals(AccountType.SAVINGS)) {
            account = new SavingsAccount();
        }

        Account byAccountNumber;
        String generateAccNumber;
        do {
            generateAccNumber = AccountNumberGenerator.generate();
            byAccountNumber = findByAccountNumber(generateAccNumber);
        } while (byAccountNumber != null);
        account.setAccountNumber(generateAccNumber);
        account.setClient(accountRequest.getClient());
        account.setAccountTemplate(accountRequest.getAccountTemplate());
        account.setCreationDate(new Date());
        account.setBalance(BigDecimal.ZERO);
        if (account instanceof CurrentAccount){
            return currentAccountRepository.save((CurrentAccount)account);
        }
        return savingsAccountRepository.save((SavingsAccount)account);
    }
}
