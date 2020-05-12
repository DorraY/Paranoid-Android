package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Medicine implements Serializable {

    private String refMed;
    private Date dateDebCons;
    private Integer duree;

    public String getRefMed() {
        return refMed;
    }

    public void setRefMed(String refMed) {
        this.refMed = refMed;
    }

    public Date getDateDebCons() {
        return dateDebCons;
    }

    public void setDateDebCons(Date dateDebCons) {
        this.dateDebCons = dateDebCons;
    }

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "refMed='" + refMed + '\'' +
                ", dateDebCons=" + dateDebCons +
                ", duree=" + duree +
                '}';
    }
}
