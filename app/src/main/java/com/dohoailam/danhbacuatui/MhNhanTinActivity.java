package com.dohoailam.danhbacuatui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dohoailam.ultils.Lam;

public class MhNhanTinActivity extends AppCompatActivity {
    EditText edtSms;
    Button btnGui, btnThoat;
    Intent intent;
    String sodt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_nhan_tin);
        addControls();
        addEvents();
    }

    private void addControls() {
        edtSms = findViewById(R.id.edtSms);
        btnGui = findViewById(R.id.btnGuiSms);
        btnThoat = findViewById(R.id.btnThoatSms);
        intent = getIntent();
        sodt = intent.getStringExtra("SDT");

    }

    private void addEvents() {
        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        /////

        btnGui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSmsResult(sodt,edtSms.getText().toString());
            }
        });

    }

    /////
    public void sendSmsResult(String phone,String smsMsg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        Intent intentMsg = new Intent("ACTION_MSG_SENT");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intentMsg,
                0);

        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                if(result == Activity.RESULT_OK)
                {
                    Toast.makeText(context,"Đã gửi SMS",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context,"Gửi SMS Thất Bại...",Toast.LENGTH_SHORT).show();
                }
            }
        }, new IntentFilter("ACTION_MSG_SENT"));

        smsManager.sendTextMessage(
                phone,
                null,
                smsMsg,
                pendingIntent,
                null
        );
    }
}
