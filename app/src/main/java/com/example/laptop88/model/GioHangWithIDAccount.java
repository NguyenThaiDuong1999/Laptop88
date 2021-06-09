package com.example.laptop88.model;

public class GioHangWithIDAccount {
    int idAccount;
    int idSanPham;
    String tenSanPham;
    String hinhSanPham;
    int soLuong;
    long gia;

    public GioHangWithIDAccount(int idAccount, int idSanPham, String tenSanPham, String hinhSanPham, int soLuong, long gia) {
        this.idAccount = idAccount;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.hinhSanPham = hinhSanPham;
        this.soLuong = soLuong;
        this.gia = gia;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public int getIdSanPham() {
        return idSanPham;
    }

    public void setIdSanPham(int idSanPham) {
        this.idSanPham = idSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }
}
