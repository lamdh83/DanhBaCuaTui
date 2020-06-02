package com.dohoailam.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dohoailam.danhbacuatui.R;
import com.dohoailam.model.DanhBa;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class GoogleInfoAdapter implements GoogleMap.InfoWindowAdapter {
    Activity context;
    DanhBa danhBa;

    public GoogleInfoAdapter(Activity context, DanhBa danhBa)
    {
        this.context = context;
        this.danhBa = danhBa;
    }
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.itemgooglemapinfo,null);
        TextView txtTenGooleMap = view.findViewById(R.id.txtTenMap);
        ImageView imgHinhMap = view.findViewById(R.id.imgHinhMap);
        txtTenGooleMap.setText( danhBa.getSo_dt());
        //imgHinhMap.setImageResource(danhBa.getHinh_anh());
        return view;
    }
}
