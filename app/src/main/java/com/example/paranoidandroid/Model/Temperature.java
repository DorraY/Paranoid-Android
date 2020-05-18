package com.example.paranoidandroid.Model;

import java.io.Serializable;

public class Temperature implements Serializable {

    private static Integer numberOfTemps=0;

    private Integer numTemp;
    private Double degree;
    private Treatment num_p;

    public Temperature() {
        this.numTemp = numberOfTemps++;
    }

    public Integer getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(Integer numTemp) {
        this.numTemp = numTemp;
    }

    public Double getDegree() {
        return degree;
    }

    public void setDegree(Double degree) {
        this.degree = degree;
    }

    public Treatment getNum_p() {
        return num_p;
    }

    public void setNum_p(Treatment num_p) {
        this.num_p = num_p;
    }
}
