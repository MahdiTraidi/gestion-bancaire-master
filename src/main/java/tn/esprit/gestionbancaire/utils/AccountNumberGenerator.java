package tn.esprit.gestionbancaire.utils;

import java.util.Random;

import static tn.esprit.gestionbancaire.utils.Constants.ACCOUNT_NUMBER_PREFIX;

public class AccountNumberGenerator {

    public static String generate() {
        String start = ACCOUNT_NUMBER_PREFIX;
        Random value = new Random();

        //Generate two values to append to 'ACCOUNT_NUMBER_PREFIX'
        int r1 = value.nextInt(10);
        int r2 = value.nextInt(10);
        start += r1 + r2 + " ";

        int count = 0;
        int n = 0;
        for (int i = 0; i < 12; i++) {
            if (count == 4) {
                start += " ";
                count = 0;
            } else
                n = value.nextInt(10);
            start += Integer.toString(n);
            count++;

        }
        return start;
    }
}
