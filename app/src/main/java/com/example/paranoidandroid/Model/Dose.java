package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Dose implements Serializable {

    private static Integer numberOfDoses=0;

    private String doseId;
    private String description;
    private Date dateAndTime;
    private Date time;
    private Integer qte;
    private Medicine refMed;

    public Dose() {
        numberOfDoses++;
    }

    public String getDoseId() {
        return doseId;
    }

    public void setDoseId(String doseId) {
        this.doseId = doseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(Date dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getQte() {
        return qte;
    }

    public void setQte(Integer qte) {
        this.qte = qte;
    }

    public Medicine getRefMed() {
        return refMed;
    }

    public void setRefMed(Medicine refMed) {
        this.refMed = refMed;
    }

    public static Integer getNumberOfDoses() {
        return numberOfDoses;
    }

    public static void setNumberOfDoses(Integer numberOfDoses) {
        Dose.numberOfDoses = numberOfDoses;
    }

    @Override
    public String toString() {
        return "Dose{" +
                ", description='" + description + '\'' +
                ", dateAndTime=" + dateAndTime +
                ", time=" + time +
                ", qte=" + qte +
                ", refMed=" + refMed +
                '}';
    }
}
