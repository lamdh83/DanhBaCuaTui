package com.dohoailam.danhbacuatui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dohoailam.helper.DataBaseHelper;
import com.dohoailam.model.DanhBa;

public class MhQuanLyDanhBaActivity extends AppCompatActivity {
    DataBaseHelper db;
    DanhBa danhBa ;
    Intent intent;
    ImageView imgHinhQlDb;
    EditText edtHoTenQlDb, edtSoDtQlDb;
    Button btnDongYQlDb, btnThoatQlDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_quan_ly_danh_ba);

        addControls();
        addEvents();
    }

    private void addControls() {
        db = new DataBaseHelper(this);
        intent = getIntent();
        danhBa = (DanhBa) intent.getSerializableExtra("DANHBA");

        imgHinhQlDb = findViewById(R.id.imgHinhQlDb);
        edtHoTenQlDb = findViewById(R.id.edtHoTenQlDb);
        edtSoDtQlDb = findViewById(R.id.edtSoDtQlDb);
        btnDongYQlDb = findViewById(R.id.btnDongYQlDb);
        btnThoatQlDb = findViewById(R.id.btnThoatQlDb);

        if(danhBa != null)
        {
            edtHoTenQlDb.setText(danhBa.getHo_ten());
            edtSoDtQlDb.setText(danhBa.getSo_dt());
        }
    }

    private void addEvents() {
        btnThoatQlDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.close();
                finish();
            }
        });
        btnDongYQlDb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    if (danhBa != null)
                    {
                        db.deleteDanhBa(danhBa);
                    }
                    DanhBa danhBaNew = new DanhBa();
                    danhBaNew.setHo_ten(edtHoTenQlDb.getText().toString());
                    danhBaNew.setSo_dt(edtSoDtQlDb.getText().toString());
                    db.createDanhBa(danhBaNew);

                    toad("...thành công");
                }catch (Exception ex)
                {

                }

                db.close();
                finish();
            }
        });

    }




    private void toad(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }
}
