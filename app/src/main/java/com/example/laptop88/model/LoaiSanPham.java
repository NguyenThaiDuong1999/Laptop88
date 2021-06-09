package com.example.laptop88.model;

public class LoaiSanPham {
    public int id;
    public String tenLoaiSP;
    public String hinhLoaiSP;

    public LoaiSanPham(int id, String tenLoaiSP, String hinhLoaiSP) {
        this.id = id;
        this.tenLoaiSP = tenLoaiSP;
        this.hinhLoaiSP = hinhLoaiSP;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenLoaiSP() {
        return tenLoaiSP;
    }

    public void setTenLoaiSP(String tenLoaiSP) {
        this.tenLoaiSP = tenLoaiSP;
    }

    public String getHinhLoaiSP() {
        return hinhLoaiSP;
    }

    public void setHinhLoaiSP(String hinhLoaiSP) {
        this.hinhLoaiSP = hinhLoaiSP;
    }
}
