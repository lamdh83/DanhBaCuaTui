package com.dohoailam.adapter;


import android.app.Activity;
import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dohoailam.model.DanhBa;
import com.dohoailam.danhbacuatui.R;

import java.util.ArrayList;

public class DanhBaAdapter extends ArrayAdapter<DanhBa> {
    Activity context;
    int resource;

    com.dohoailam.ultils.Lam lam = new com.dohoailam.ultils.Lam(context);
    public DanhBaAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View customView;
        LayoutInflater inflater = this.context.getLayoutInflater();
        customView = inflater.inflate(this.resource,null);

        ImageView imgHinh = customView.findViewById(R.id.imgHinh);
        TextView txtTen = customView.findViewById(R.id.txtTen);
        TextView txtSoDt = customView.findViewById(R.id.txtSoDt);
        final ImageView imgCheck = customView.findViewById(R.id.imgCheck);
        final DanhBa danhBa = getItem(position);
        txtTen.setText(danhBa.getHo_ten());
        txtSoDt.setText(danhBa.getSo_dt());

        try
        {
            lam.openReduceBitMap(imgHinh,danhBa.getHinh_anh());
        }catch (Exception ex)
        {

        }

        if(danhBa.getActive())
        {
            imgCheck.setBackgroundResource(R.drawable.check2);
        }
        else
        {
            imgCheck.setBackgroundResource(R.drawable.uncheck2);
        }




        return customView;
    }
}
