package com.example.paranoidandroid.Model;

import java.util.Date;

public class Medicine {

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
}
