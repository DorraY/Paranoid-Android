package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Dose implements Serializable {

    private static Integer numberOfDoses=0;

    private Integer doseId;
    private String descriiption;
    private Date date;
    private Integer hour;
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

    public String getDescriiption() {
        return descriiption;
    }

    public void setDescriiption(String descriiption) {
        this.descriiption = descriiption;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
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
