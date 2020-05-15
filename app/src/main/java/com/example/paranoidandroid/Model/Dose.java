package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Dose implements Serializable {

    private static Integer numberOfDoses=0;

    private Integer doseId;
    private String description;
    private Date date;
    private Integer time;
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

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
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

    @Override
    public String toString() {
        return "Dose{" +
                "doseId=" + doseId +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", qte=" + qte +
                ", refMed=" + refMed +
                '}';
    }
}
