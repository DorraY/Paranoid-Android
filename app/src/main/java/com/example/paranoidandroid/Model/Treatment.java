package com.example.paranoidandroid.Model;

import java.io.Serializable;
import java.util.Date;


public class Treatment implements Serializable {

    private static Integer numberOfTreatments=0;

    private Integer num_p ;
    private Date start_date;
    private Date end_date;
    private String sickness;

    public Treatment() {
        this.num_p = numberOfTreatments++;

    }

    public Integer getNum_p() {
        return num_p;
    }

    public void setNum_p(Integer id) {
        this.num_p = id;
    }



    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getSickness() {
        return sickness;
    }

    public void setSickness(String sickness) {
        this.sickness = sickness;
    }

    @Override
    public String toString() {
        return "Treatment{" +
                "num_p=" + num_p +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                ", sickness='" + sickness + '\'' +
                '}';
    }
}
