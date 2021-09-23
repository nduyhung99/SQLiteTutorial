package com.example.sqlitetutorial;

public class CongViec {
    int idCv;
    String tenCongViec;

    public CongViec(int idCv, String tenCongViec) {
        this.idCv = idCv;
        this.tenCongViec = tenCongViec;
    }

    public CongViec() {
    }

    public int getIdCv() {
        return idCv;
    }

    public void setIdCv(int idCv) {
        this.idCv = idCv;
    }

    public String getTenCongViec() {
        return tenCongViec;
    }

    public void setTenCongViec(String tenCongViec) {
        this.tenCongViec = tenCongViec;
    }
}
