package com.example.laptop88.model;

public class ChiTietDonHang {
    private int idChiTietDonHang;
    private int idDonHang;
    private int idSanPham;
    private String tenSanPham;
    private String hinhSanPham;
    private int baoHanh;
    private int soLuong;
    private int giaTong;
    private String xacNhan;
    private String daNhan;
    private int idAccount;
    private String hoTen;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private String ngayDatHang;
    private String ngayNhanHang;
    private int shipper; //idAccount of shipper (staff)

    public ChiTietDonHang(int idChiTietDonHang, int idDonHang, int idSanPham, String tenSanPham, String hinhSanPham, int baoHanh,int soLuong, int giaTong, String xacNhan, String daNhan, int idAccount, String hoTen, String soDienThoai, String ngayDatHang, String ngayNhanHang, int shipper) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idDonHang = idDonHang;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.hinhSanPham = hinhSanPham;
        this.baoHanh = baoHanh;
        this.soLuong = soLuong;
        this.giaTong = giaTong;
        this.daNhan = daNhan;
        this.xacNhan = xacNhan;
        this.idAccount = idAccount;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.ngayDatHang = ngayDatHang;
        this.ngayNhanHang = ngayNhanHang;
        this.shipper = shipper;
    }

    public ChiTietDonHang(int idChiTietDonHang, int idDonHang, int idSanPham, String tenSanPham, String hinhSanPham,int soLuong, int giaTong, String xacNhan, String daNhan, int idAccount, String hoTen, String soDienThoai, String email, String diaChi, String ngayDatHang, String ngayNhanHang) {
        this.idChiTietDonHang = idChiTietDonHang;
        this.idDonHang = idDonHang;
        this.idSanPham = idSanPham;
        this.tenSanPham = tenSanPham;
        this.hinhSanPham = hinhSanPham;
        this.soLuong = soLuong;
        this.giaTong = giaTong;
        this.daNhan = daNhan;
        this.xacNhan = xacNhan;
        this.idAccount = idAccount;
        this.hoTen = hoTen;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.ngayDatHang = ngayDatHang;
        this.ngayNhanHang = ngayNhanHang;
    }

    public int getIdChiTietDonHang() {
        return idChiTietDonHang;
    }

    public void setIdChiTietDonHang(int idChiTietDonHang) {
        this.idChiTietDonHang = idChiTietDonHang;
    }

    public int getIdDonHang() {
        return idDonHang;
    }

    public void setIdDonHang(int idDonHang) {
        this.idDonHang = idDonHang;
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

    public int getBaoHanh() {
        return baoHanh;
    }

    public void setBaoHanh(int baoHanh) {
        this.baoHanh = baoHanh;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTong() {
        return giaTong;
    }

    public void setGiaTong(int giaTong) {
        this.giaTong = giaTong;
    }

    public String getXacNhan() {
        return xacNhan;
    }

    public void setXacNhan(String xacNhan) {
        this.xacNhan = xacNhan;
    }

    public String getDaNhan() {
        return daNhan;
    }

    public void setDaNhan(String daNhan) {
        this.daNhan = daNhan;
    }

    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public int getShipper() {
        return shipper;
    }

    public void setShipper(int shipper) {
        this.shipper = shipper;
    }
}
