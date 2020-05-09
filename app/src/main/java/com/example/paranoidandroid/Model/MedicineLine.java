package com.example.paranoidandroid.Model;

public class MedicineLine {

    private Treatment num_p;
    private Medicine refMed;

    public Treatment getNum_p() {
        return num_p;
    }

    public void setNum_p(Treatment num_p) {
        this.num_p = num_p;
    }

    public Medicine getRefMed() {
        return refMed;
    }

    public void setRefMed(Medicine refMed) {
        this.refMed = refMed;
    }
}
