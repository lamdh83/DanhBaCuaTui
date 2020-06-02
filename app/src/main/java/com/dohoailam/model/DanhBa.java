package com.dohoailam.model;

import java.io.Serializable;

public class DanhBa implements Serializable {
    private int so_tt;
    private String ho_ten;
    private String so_dt;
    private Boolean active = false;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public DanhBa(int so_tt, String ho_ten, String so_dt, Boolean active, String hinh_anh) {
        this.so_tt = so_tt;
        this.ho_ten = ho_ten;
        this.so_dt = so_dt;
        this.active = active;
        this.hinh_anh = hinh_anh;
    }

    private String hinh_anh;

    @Override
    public String toString() {
        return "DanhBa{" +
                "ho_ten='" + ho_ten + '\'' +
                ", so_dt='" + so_dt + '\'' +
                ", hinh_anh='" + hinh_anh + '\'' +
                '}';
    }

    public String getHo_ten() {
        return ho_ten;
    }

    public void setHo_ten(String ho_ten) {
        this.ho_ten = ho_ten;
    }

    public String getSo_dt() {
        return so_dt;
    }

    public void setSo_dt(String so_dt) {
        this.so_dt = so_dt;
    }

    public String getHinh_anh() {
        return hinh_anh;
    }

    public void setHinh_anh(String hinh_anh) {
        this.hinh_anh = hinh_anh;
    }

    public DanhBa() {

    }

    public DanhBa( String ho_ten, String so_dt, String hinh_anh) {


        this.ho_ten = ho_ten;
        this.so_dt = so_dt;
        this.hinh_anh = hinh_anh;
    }

    public DanhBa( String ho_ten, String so_dt) {


        this.ho_ten = ho_ten;
        this.so_dt = so_dt;

    }

    public int getSo_tt() {
        return so_tt;
    }

    public void setSo_tt(int so_tt) {
        this.so_tt = so_tt;
    }
}
