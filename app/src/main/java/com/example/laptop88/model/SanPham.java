package com.example.laptop88.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    public int idSanPham;
    public String tenSanPham;
    public Integer gia;
    public String hinhSanPham;
    public String moTa;
    public String chuyenDung;
    public int idLoaiSanPham;
    public int baoHanh;
    public String diaChi;
    public String ngayDatHang;
    public String ngayNhanHang;
    public String xacNhan;
    public String daNhan;
    public int idChiTietDonHang;

    public SanPham(int idSanPham, String tenSanPham, Integer gia, String hinhSanPham, String moTa, String chuyenDung, int idLoaiSanPham, int baoHanh) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.hinhSanPham = hinhSanPham;
        this.moTa = moTa;
        this.chuyenDung = chuyenDung;
        this.idLoaiSanPham = idLoaiSanPham;
        this.baoHanh = baoHanh;
    }

    public SanPham(int idSanPham, String tenSanPham, Integer gia, String hinhSanPham, String moTa, String chuyenDung, int idLoaiSanPham, int baoHanh, String ngayDatHang) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.hinhSanPham = hinhSanPham;
        this.moTa = moTa;
        this.chuyenDung = chuyenDung;
        this.idLoaiSanPham = idLoaiSanPham;
        this.baoHanh = baoHanh;
        this.ngayDatHang = ngayDatHang;
    }

    public SanPham(int idSanPham, String tenSanPham, Integer gia, String hinhSanPham, String moTa, String chuyenDung, int idLoaiSanPham, int baoHanh, String diaChi, String ngayDatHang, String ngayNhanHang, int idChiTietDonHang, String xacNhan,String daNhan) {
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.gia = gia;
        this.hinhSanPham = hinhSanPham;
        this.moTa = moTa;
        this.chuyenDung = chuyenDung;
        this.idLoaiSanPham = idLoaiSanPham;
        this.baoHanh = baoHanh;
        this.diaChi = diaChi;
        this.ngayDatHang = ngayDatHang;
        this.ngayNhanHang = ngayNhanHang;
        this.idChiTietDonHang = idChiTietDonHang;
        this.xacNhan = xacNhan;
        this.daNhan = daNhan;
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

    public Integer getGia() {
        return gia;
    }

    public void setGia(Integer gia) {
        this.gia = gia;
    }

    public String getHinhSanPham() {
        return hinhSanPham;
    }

    public void setHinhSanPham(String hinhSanPham) {
        this.hinhSanPham = hinhSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getChuyenDung() {
        return chuyenDung;
    }

    public void setChuyenDung(String chuyenDung) {
        this.chuyenDung = chuyenDung;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdLoaiSanPham() {
        return idLoaiSanPham;
    }

    public void setIdLoaiSanPham(int idLoaiSanPham) {
        this.idLoaiSanPham = idLoaiSanPham;
    }

    public int getBaoHanh() {
        return baoHanh;
    }

    public void setBaoHanh(int baoHanh) {
        this.baoHanh = baoHanh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayDatHang() {
        return ngayDatHang;
    }

    public void setNgayDatHang(String ngayDatHang) {
        this.ngayDatHang = ngayDatHang;
    }

    public String getNgayNhanHang() {
        return ngayNhanHang;
    }

    public void setNgayNhanHang(String ngayNhanHang) {
        this.ngayNhanHang = ngayNhanHang;
    }

    public String getDaNhan() {
        return daNhan;
    }

    public void setDaNhan(String daNhan) {
        this.daNhan = daNhan;
    }

    public int getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(int idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public String getXacNhan() {
        return xacNhan;
    }

    public void setXacNhan(String xacNhan) {
        this.xacNhan = xacNhan;
    }
}
