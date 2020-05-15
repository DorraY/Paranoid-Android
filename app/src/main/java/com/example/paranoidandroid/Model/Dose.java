package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

public class Dose implements Serializable {

    private static Integer numberOfDoses=0;

    private Integer doseId;
    private String description;
    private Date date;
    private LocalTime hour;
    private Integer qte;
    private Medicine refMed;

    public Dose() {
        this.doseId = numberOfDoses++;
    }

    public Integer getDoseId() {
        return doseId;
    }

    public void setDoseId(Integer doseId) {
        this.doseId = doseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalTime getHour() {
        return hour;
    }

    public void setHour(LocalTime hour) {
        this.hour = hour;
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
}
