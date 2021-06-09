package com.example.laptop88.model;

public class GioHang {
    public int iDSanPham;
    public String tenSanPham;
    public long gia;
    public String hinhSanPham;
    public int soLuong;

    public GioHang(int iDSanPham, String tenSanPham, long gia, String hinhSanPham, int soLuong) {
        this.iDSanPham = iDSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.hinhSanPham = hinhSanPham;
        this.soLuong = soLuong;
    }

    public int getiDSanPham() {
        return iDSanPham;
    }

    public void setiDSanPham(int iDSanPham) {
        this.iDSanPham = iDSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public long getGia() {
        return gia;
    }

    public void setGia(long gia) {
        this.gia = gia;
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
}
