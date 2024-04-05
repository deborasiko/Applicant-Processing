package org.example;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ApplicantProcessor class for processing the applicants.
 */
public class ApplicantsProcessor {

    public static final String SUCCESS_MESSAGE = "All applicants were processed successfully!";
    public static final String DATE_FORMAT = "YYYY-MM-DD'T'hh:mm:ss";
    public static final String SEPARATOR = ",";
    public static final String PATH_JSON_OUTPUT = "D:/University/applicant_processing/json/output.json";
    public static final String SPACE = " ";
    public static final String REGEX_CSV_LINE = "^[^a-zA-Z0-9]+$";
    public static final String INVALID_CSV_LINE_CONTINUE_WITH_NEXT_LINE = "Invalid CSV line, continue with next line";

    /**
     * @param csvStream input stream allowing to read the CSV input file
     * @return the processing output, in JSON format
     */
    public String processApplicants(InputStream csvStream) throws IOException, ParseException, InvalidCSVLineException {
        BufferedReader br = new BufferedReader(new InputStreamReader(csvStream));
        String firstLine = String.valueOf(br.readLine());
        String[] headers = firstLine.split(SEPARATOR);//todo:verify if csv contains a header line or not
        List<Applicant> applicants = csvToApplicants(br);
        double averageScore = ScoreManager.calculateAverageScore(applicants);
        System.out.printf("Average Score is: %.2f\n", averageScore);
        applicantToJSON(ScoreManager.calculateTopApplicants(applicants), averageScore);
        for (Applicant a : applicants) {
            System.out.println(a);
        }
        return SUCCESS_MESSAGE;
    }

    private static List<Applicant> csvToApplicants(BufferedReader br) throws IOException {
        String line;
        List<Applicant> applicants = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        while ((line = br.readLine()) != null) {
            String[] values = line.split(SEPARATOR);
            try {
                validateLine(values);
            } catch (InvalidCSVLineException e) {
                continue;
            }
            Applicant myApplicant = new Applicant();
            myApplicant.setName(values[0]);
            myApplicant.setEmail(values[1]);
            try {
                myApplicant.setDeliveredDate(formatter.parse(values[2]));
            } catch (ParseException e) {
                continue;
            }
            if (ApplicantValidator.validateScore(values[3])) {
                myApplicant.setScore(Double.parseDouble(values[3]));
            } else {
                continue;
            }
            if (ApplicantValidator.validateApplicant(myApplicant)) {
                boolean replaced = false;
                for (Applicant existingApplicant : applicants) {
                    if (myApplicant.getEmail().equals(existingApplicant.getEmail())) {
                        applicants.set(applicants.indexOf(existingApplicant), myApplicant);
                        replaced = true;
                    }
                }
                if (!replaced) {
                    applicants.add(myApplicant);
                }
            }

        }
        return applicants;
    }

    private static void writeToFile(JSONObject jsonObject) {
        try {
            FileWriter file = new FileWriter(PATH_JSON_OUTPUT);
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
            ;
        }
    }

    private static void applicantToJSON(List<Applicant> applicants, double averageScore) {
        JSONArray lastNames = new JSONArray();
        for (int i = 0; i < applicants.size(); i++) {
            String[] fullName = applicants.get(i).getName().split(SPACE);
            lastNames.add(fullName[fullName.length - 1]);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uniqueApplicants", applicants.size());
        jsonObject.put("topApplicants", lastNames);
        jsonObject.put("averageScore", averageScore);
        writeToFile(jsonObject);
    }

    private static void validateLine(String[] values) throws InvalidCSVLineException {
        if (values.length == 0 || (values.length == 1 && values[0].isEmpty())) {
            //empty line
            throw new InvalidCSVLineException();
        }
        Pattern pattern = Pattern.compile(REGEX_CSV_LINE);
        for (String value : values) {
            Matcher m = pattern.matcher(value);
            if(m.matches()) {
                //only commas or special characters
                throw new InvalidCSVLineException(INVALID_CSV_LINE_CONTINUE_WITH_NEXT_LINE);
            }
        }
    }
}

