package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Validator for validating the applicant.
 */
public class ApplicantValidator {

    public static final String VALID_EMAIL_FORMAT_REGEX = "^[A-Za-z][A-Za-z0-9._-]*@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    public static final String SPACE = " ";
    public static final String DECIMAL_SEPARATOR = "\\.";


    /**
     * Validate applicant by email and name.
     * @param myApplicant Applicant
     * @return boolean
     */
    public static boolean validateApplicant(Applicant myApplicant) {
        return validEmail(myApplicant.getEmail()) && validName(myApplicant.getName());
    }

    private static boolean validName(String name) {
        String[] fullName = name.split(SPACE);
        if (fullName.length >= 2) {
            return true;
        }
        return false;
    }

    private static boolean validEmail(String email) {

        Pattern pattern = Pattern.compile(VALID_EMAIL_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validate applicant score.
     * @param scoreString String
     * @return boolean
     */
    public static boolean validateScore(String scoreString) {
        double score = Double.parseDouble(scoreString);
        if (score >= 0 && score <= 10) {
            // Check if score has up to 2 decimal places
            String[] parts = scoreString.split(DECIMAL_SEPARATOR);
            if (parts.length == 1 || (parts.length == 2 && parts[1].length() <= 2)) {
                return true;
            }
        }
        return false;
    }
}
