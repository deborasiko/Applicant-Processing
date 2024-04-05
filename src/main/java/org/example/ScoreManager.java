package org.example;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Order applicant based on score. Calculating different score relevant operations.
 */
public class ScoreManager {
    /**
     * Calculates the top 3 applicants.
     * @param applicants List
     * @return a sublist.
     */
    public static List<Applicant> calculateTopApplicants(List<Applicant> applicants) {
        if (applicants.size() >= 3) {
            return applicants.subList(0, 3);
        }
        return applicants;
    }

    //TODO: adjusting the score based on delivery date and time
    private static List<Applicant> adjustScore(List<Applicant> applicants) {
        Comparator<Applicant> dateComparator = new Comparator<Applicant>() {
            @Override
            public int compare(Applicant o1, Applicant o2) {
                return o1.getDeliveredDate().compareTo(o2.getDeliveredDate());
            }
        };
        Collections.sort(applicants, dateComparator);
        return applicants;
    }

    /**
     * Calculates the average score of applicants before adjustment.
     * @param applicants List
     * @return average score
     */
    public static double calculateAverageScore(List<Applicant> applicants) {
        orderByScore(applicants);
        double sum = 0;
        int i;
        if (applicants.size() == 1) {
            return applicants.get(0).getScore();
        }
        for (i = 0; i < applicants.size() / 2; i++) {
            sum = sum + applicants.get(i).getScore();
        }
        return sum / (i);
    }

    private static List<Applicant> orderByScore(List<Applicant> applicants) {
        Collections.sort(applicants);
        Collections.reverse(applicants);
        return applicants;
    }
}
