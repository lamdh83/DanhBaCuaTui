package com.dohoailam.ultils;

import android.app.Activity;
import android.content.Context;

import com.dohoailam.helper.DataBaseHelper;
import com.dohoailam.model.Contact_Model;
import com.dohoailam.model.DanhBa;

import java.util.ArrayList;

public class DongBoDanhBa {
   private DataBaseHelper db;
   private Context context;
   Lam lam;

    public DongBoDanhBa(DataBaseHelper db, Context context) {
        this.db = db;
        this.context = context;
    }

    public void  RunDongBo()
    {
        lam = new Lam((Activity) context);
        db = new DataBaseHelper(context);
        ArrayList<Contact_Model> contact_models = new ArrayList<>();
        contact_models = lam.readContacts();
        for (Contact_Model c : contact_models )
        {
            int dem = db.getDanhbaCount(c.getContactNumber());
            if(dem ==0 )
            {
                DanhBa d = new DanhBa();
                d.setHo_ten(c.getContactName());
                d.setSo_dt(c.getContactNumber());
                d.setSo_tt(13);
                db.createDanhBa(d);
            }


        }
    }
}
