package com.dohoailam.model;

import com.google.android.gms.maps.model.LatLng;

public class QuocGia {
    private String Ten;
    private LatLng latLng;
    private String TenThuDo;
    private String MaQG;
    private String TenChauLuc;

    public QuocGia(String ten, LatLng latLng, String tenThuDo, String maQG, String tenChauLuc) {
        Ten = ten;
        this.latLng = latLng;
        TenThuDo = tenThuDo;
        MaQG = maQG;
        TenChauLuc = tenChauLuc;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getTenThuDo() {
        return TenThuDo;
    }

    public void setTenThuDo(String tenThuDo) {
        TenThuDo = tenThuDo;
    }

    public String getMaQG() {
        return MaQG;
    }

    public void setMaQG(String maQG) {
        MaQG = maQG;
    }

    public String getTenChauLuc() {
        return TenChauLuc;
    }

    public void setTenChauLuc(String tenChauLuc) {
        TenChauLuc = tenChauLuc;
    }
}
