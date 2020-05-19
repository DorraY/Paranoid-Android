package com.example.paranoidandroid.Model;

import java.io.Serializable;

public class Temperature implements Serializable {

    private static Integer numberOfTemps=0;

    private String numTemp;
    private Double degree;
    private Treatment num_p;

    public Temperature() {

        numberOfTemps++;
    }

    public String getNumTemp() {
        return numTemp;
    }

    public void setNumTemp(String numTemp) {
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

    public static Integer getNumberOfTemps() {
        return numberOfTemps;
    }

    public static void setNumberOfTemps(Integer numberOfTemps) {
        Temperature.numberOfTemps = numberOfTemps;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "numTemp='" + numTemp + '\'' +
                ", degree=" + degree +
                ", num_p=" + num_p +
                '}';
    }
}
