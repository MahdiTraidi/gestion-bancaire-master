package tn.esprit.gestionbancaire.utils;

public interface Constants {

  String APP_ROOT = "gestionbancaire/v1";

  String CREDIT = APP_ROOT + "/credits";

  String DOCUMENT = APP_ROOT + "/document";

  double MIN_SELF_FINANCING = 0.2;

  double MAX_VEHICLE_CREDIT = 120000.0;
  double MIN_VEHICLE_CREDIT = 15000.0;

  double MAX_PERSONAL_CREDIT = 60000.0;
  double MIN_PERSONAL_CREDIT = 8000.0;

  String ACCOUNT_NUMBER_PREFIX = "01";

  String EMAIL_ACCOUNT_SIGNATURE_SUBJECT = "Document signature for account creation";

  String ACCOUNT_INFORMATION = "Your new account information";

  String ACCOUNT_REQUEST_DECLINED = "Account creation request declined";

  String CARD_REQUEST_ACCEPTED = "Card request accepted";

  String CARD_REQUEST_DECLINED = "Card creation request declined";


  String MY_EAMIL = "phoenix.banking.2021@gmail.com";

  String MY_PASSWORD = "phoenix2021";
}
