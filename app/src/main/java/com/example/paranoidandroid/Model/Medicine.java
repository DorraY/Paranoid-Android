package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;

public class Medicine implements Serializable {

    private String refMed;
    private Date dateDebCons;
    private Date dateEnd;

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

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        return "Medicine{" +
                "refMed='" + refMed + '\'' +
                ", dateDebCons=" + dateDebCons +
                ", dateEnd=" + dateEnd +
                '}';
    }
}
