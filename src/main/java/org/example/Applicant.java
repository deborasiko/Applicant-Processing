package org.example;

import java.util.Date;

public class Applicant implements Comparable<Applicant> {
    private String name;
    private java.lang.String email;
    private Date deliveredDate;
    private double score;

    public Applicant(String name, java.lang.String email, Date deliveredDate, double score) {
        this.name = name;
        this.email = email;
        this.deliveredDate = deliveredDate;
        this.score = score;
    }

    public Applicant() {

    }

    public String getName() {
        return name;
    }

    public java.lang.String getEmail() {
        return email;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public double getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(java.lang.String email) {
        this.email = email;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public java.lang.String toString() {
        return "Applicant{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", deliveredDate=" + deliveredDate +
                ", score=" + score +
                '}';
    }

    @Override
    public int compareTo(Applicant o) {
        if (o == null) {
            return -1;
        }
        int c = Double.valueOf(score).compareTo(o.score);
        if (c != 0) {
            return c;
        }
        return o.compareTo(o);
    }
}
