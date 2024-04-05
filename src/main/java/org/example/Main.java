package org.example;

import java.io.*;
import java.text.ParseException;

public class Main {
    public static final String PATH_TO_CSV = "D:\\University\\applicant_processing\\csv\\input.csv";

    public static void main(String[] args) throws IOException, ParseException, InvalidCSVLineException {
        File inputFile = new File(PATH_TO_CSV);
        InputStream ins = new FileInputStream(inputFile);
        ApplicantsProcessor ap = new ApplicantsProcessor();
        System.out.println(ap.processApplicants(ins));
    }
}