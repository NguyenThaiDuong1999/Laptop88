package com.example.laptop88.model;

import java.io.Serializable;

public class Hang implements Serializable {
    int idHang;
    String tenHang;
    String hinhHang;

    public Hang(int idHang, String tenHang, String hinhHang) {
        this.idHang = idHang;
        this.tenHang = tenHang;
        this.hinhHang = hinhHang;
    }

    public int getIdHang() {
        return idHang;
    }

    public void setIdHang(int idHang) {
        this.idHang = idHang;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getHinhHang() {
        return hinhHang;
    }

    public void setHinhHang(String hinhHang) {
        this.hinhHang = hinhHang;
    }
}
