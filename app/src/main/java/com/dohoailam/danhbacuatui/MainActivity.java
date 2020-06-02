package com.dohoailam.danhbacuatui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.dohoailam.helper.DataBaseHelper;
import com.dohoailam.model.DanhBa;
import com.dohoailam.ultils.DongBoDanhBa;
import com.dohoailam.ultils.Lam;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    TabHost tabHost;
    DataBaseHelper db;
    DongBoDanhBa dongBoDanhBa;
    ProgressDialog progressDialog;
    Lam lam = new Lam(this);
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayList<String> arraySoDt = new ArrayList<>();

    // TAB1 //
    ImageView imgMic;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;

    //TAB2
    Button btnTab2_1,btnTab2_2,btnTab2_3,btnTab2_4,btnTab2_5,btnTab2_6,btnTab2_7,btnTab2_8,btnTab2_9,btnTab2_0
            ,btnTab2_Thang,btnTab2_Sao;
    ImageButton imgBtnTab2_DanhBa, imgBtnTab2_Call,imgBtnTab2_CLR;
    TextView txtSoDt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }


///////// ADD CONTROL /////////////////
    private void addControls() {
        //khai bao tabhost
        khaiBaoTabHost();
        //khai bao control main
        khaiBaoControlMain();
        //doc danh ba tu CSDL
        docDanhBaTuCSDL();
        //Khai bao control Tab1
        KhaiBaoControlTab1();

        KhaiBaoControlTab2();
    }



    private void khaiBaoTabHost() {
        tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("GỌI NHANH");
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("BÀN PHÍM");
        tabHost.addTab(tab2);

        TabHost.TabSpec tab3 = tabHost.newTabSpec("t3");
        tab3.setContent(R.id.tab3);
        tab3.setIndicator("BẢN ĐỒ");
        tabHost.addTab(tab3);
    }

    /////
    private void khaiBaoControlMain() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Chờ tí...");
        khoiTaoCSDL();

    }
    private void khoiTaoCSDL() {
        db = new DataBaseHelper(this);
        dongBoDanhBa = new DongBoDanhBa(db,this);
    }
    ////////
    private void docDanhBaTuCSDL() {
        int dem = db.getDanhbaCount();
        if(dem ==0)
        {
            TaskDocDanhBa taskDocDanhBa = new TaskDocDanhBa();
            taskDocDanhBa.execute();
        }
    }

    /////
    private void KhaiBaoControlTab1() {
        imgMic = findViewById(R.id.imgMic);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        //gan ten
        ganTenControlTab1();
    }
    private void ganTenControlTab1() {
        ArrayList<DanhBa> danhBaArrayList = (ArrayList<DanhBa>) db.getAllDanhBasDial();

        for (DanhBa d : danhBaArrayList )
        {

            int i = d.getSo_tt();
            String ten ;
            if (d.getHo_ten().length() > 8)
            {
                ten = d.getHo_ten().substring(0,7);
            }else
            {
                ten = d.getHo_ten();
            }
            switch (i)
            {
                case 1:
                    btn1.setText(ten);
                    break;
                case 2:
                    btn2.setText(ten);
                    break;
                case 3:
                    btn3.setText(ten);
                    break;
                case 4:
                    btn4.setText(ten);
                    break;
                case 5:
                    btn5.setText(ten);
                    break;
                case 6:
                    btn6.setText(ten);
                    break;
                case 7:
                    btn7.setText(ten);
                    break;
                case 8:
                    btn8.setText(ten);
                    break;
                case 9:
                    btn9.setText(ten);
                    break;
                case 10:
                    btn10.setText(ten);
                    break;
                case 11:
                    btn11.setText(ten);
                    break;
                case 12:
                    btn12.setText(ten);
                    break;
            }
        }

    }
    //////

    private void KhaiBaoControlTab2() {
        imgBtnTab2_CLR = findViewById(R.id.imgTab2_CLR);
        txtSoDt = findViewById(R.id.txtSoDt);
        btnTab2_1 = findViewById(R.id.btnTab2_1);
        btnTab2_2 = findViewById(R.id.btnTab2_2);
        btnTab2_3 = findViewById(R.id.btnTab2_3);
        btnTab2_4 = findViewById(R.id.btnTab2_4);
        btnTab2_5 = findViewById(R.id.btnTab2_5);
        btnTab2_6 = findViewById(R.id.btnTab2_6);
        btnTab2_7 = findViewById(R.id.btnTab2_7);
        btnTab2_8 = findViewById(R.id.btnTab2_8);
        btnTab2_9 = findViewById(R.id.btnTab2_9);
        btnTab2_0 = findViewById(R.id.btnTab2_0);
        btnTab2_Thang = findViewById(R.id.btnTab2_Thang);
        btnTab2_Sao = findViewById(R.id.btnTab2_Sao);
        imgBtnTab2_Call = findViewById(R.id.imgBtnTab2_Call);
        imgBtnTab2_DanhBa = findViewById(R.id.imgBtnTab2_DanhBa);

    }

    /////////////////// ADD EVENTS////////////////////

    private void addEvents() {
        //envent chon TAB
        eventChonTab();
        //event clikc tab 1
        eventClickTab1();
        eventClickTab2();
        eventLongClickTab1();


    }




    ////
    private void eventChonTab() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                switch (s)
                {
                    case "t1":
                        chonTab(1);
                        break;
                    case "t2":
                        chonTab(2);
                        break;
                    case "t3":
                        chonTab(3);
                        break;
                }
            }
        });
    }
    private void chonTab(int i) {

    }
    //////
    private void eventClickTab1() {
        imgMic.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn10.setOnClickListener(this);
        btn11.setOnClickListener(this);
        btn12.setOnClickListener(this);
    }
    private void eventClickTab2() {
        btnTab2_1.setOnClickListener(this);
        btnTab2_2.setOnClickListener(this);
        btnTab2_3.setOnClickListener(this);
        btnTab2_4.setOnClickListener(this);
        btnTab2_5.setOnClickListener(this);
        btnTab2_6.setOnClickListener(this);
        btnTab2_7.setOnClickListener(this);
        btnTab2_8.setOnClickListener(this);
        btnTab2_9.setOnClickListener(this);
        btnTab2_0.setOnClickListener(this);
        btnTab2_Thang.setOnClickListener(this);
        btnTab2_Sao.setOnClickListener(this);
        imgBtnTab2_Call.setOnClickListener(this);
        imgBtnTab2_DanhBa.setOnClickListener(this);
        imgBtnTab2_CLR.setOnClickListener(this);
    }
    private void eventLongClickTab1() {
        btn1.setOnLongClickListener(this);
        btn2.setOnLongClickListener(this);
        btn3.setOnLongClickListener(this);
        btn4.setOnLongClickListener(this);
        btn5.setOnLongClickListener(this);
        btn6.setOnLongClickListener(this);
        btn7.setOnLongClickListener(this);
        btn8.setOnLongClickListener(this);
        btn9.setOnLongClickListener(this);
        btn10.setOnLongClickListener(this);
        btn11.setOnLongClickListener(this);
        btn12.setOnLongClickListener(this);
    }



    ////On Click
    @Override
    public void onClick(View view) {
        onClickTab1(view);
        onClickTab2(view);

    }

    private void onClickTab1(View view) {
        switch (view.getId()) {
            //Click Tab 1
            case R.id.imgMic:
                promptSpeechInput();
                break;
            case R.id.btn1:
                callNhanh(1);
                break;
            case R.id.btn2:
                callNhanh(2);
                break;
            case R.id.btn3:
                callNhanh(3);
                break;
            case R.id.btn4:
                callNhanh(4);
                break;
            case R.id.btn5:
                callNhanh(5);
                break;
            case R.id.btn6:
                callNhanh(6);
                break;
            case R.id.btn7:
                callNhanh(7);
                break;
            case R.id.btn8:
                callNhanh(8);
                break;
            case R.id.btn9:
                callNhanh(9);
                break;
            case R.id.btn10:
                callNhanh(10);
                break;
            case R.id.btn11:
                callNhanh(11);
                break;
            case R.id.btn12:
                callNhanh(12);
                break;
        }
    }

    private void callNhanh(int i) {
        ArrayList<DanhBa> listDb = (ArrayList<DanhBa>) db.getDanhBaStt(i);
        if (listDb.size() > 0 )
        {
            lam.callPhone(listDb.get(0).getSo_dt());
        }
        else
        {
            Toast.makeText(this,"Không tìm thấy số điện thoại",Toast.LENGTH_SHORT).show();
        }

    }

    ///ON LONG CLICK
    @Override
    public boolean onLongClick(View view) {
        onLongClickTab1(view);

        return false;
    }


    private void onLongClickTab1(View view) {
        switch (view.getId())
        {
            //Click Tab 1

            case R.id.btn1:
                addSoNhanh(1);
                break;
            case R.id.btn2:
                addSoNhanh(2);
                break;
            case R.id.btn3:
                addSoNhanh(3);
                break;
            case R.id.btn4:
                addSoNhanh(4);
                break;
            case R.id.btn5:
                addSoNhanh(5);
                break;
            case R.id.btn6:
                addSoNhanh(6);
                break;
            case R.id.btn7:
                addSoNhanh(7);
                break;
            case R.id.btn8:
                addSoNhanh(8);
                break;
            case R.id.btn9:
                addSoNhanh(9);
                break;
            case R.id.btn10:
                addSoNhanh(10);
                break;
            case R.id.btn11:
                addSoNhanh(11);
                break;
            case R.id.btn12:
                addSoNhanh(12);
                break;
        }
    }
    private void onClickTab2(View view) {
        switch (view.getId())
        {
            case R.id.btnTab2_0:
                nhanSoDt("0");
                break;
            case R.id.btnTab2_1:
                nhanSoDt("1");
                break;
            case R.id.btnTab2_2:
                nhanSoDt("2");
                break;
            case R.id.btnTab2_3:
                nhanSoDt("3");
                break;
            case R.id.btnTab2_4:
                nhanSoDt("4");
                break;
            case R.id.btnTab2_5:
                nhanSoDt("5");
                break;
            case R.id.btnTab2_6:
                nhanSoDt("6");
                break;
            case R.id.btnTab2_7:
                nhanSoDt("7");
                break;
            case R.id.btnTab2_8:
                nhanSoDt("8");
                break;
            case R.id.btnTab2_9:
                nhanSoDt("9");
                break;
            case R.id.btnTab2_Sao:
                nhanSoDt("*");
                break;
            case R.id.btnTab2_Thang:
                nhanSoDt("#");
                break;
            case R.id.imgBtnTab2_Call:
                lam.callPhone(txtSoDt.getText().toString());
                break;
            case R.id.imgBtnTab2_DanhBa:
                Intent intenttab2 = new Intent(MainActivity.this,MhDanhBaActivity.class);
                intenttab2.putExtra("TAB","TAB2");
                startActivity(intenttab2);
                break;
            case R.id.imgTab2_CLR:
                nhanSoDt("CLR");
                break;


        }
    }

    private void nhanSoDt(String i) {

        if(i.equals("CLR") && arraySoDt.size() > 0)
        {
            arraySoDt.remove(arraySoDt.size() -1);
        }
        else if(arraySoDt.size() < 20 && i.length() < 3)
        {
            arraySoDt.add(i);
        }

        String kq = " ";
        for (String s : arraySoDt  )
        {
            kq +=s;
        }

        txtSoDt.setText(kq.trim());

    }


    private void addSoNhanh(int i) {
        //mo man hinh chon danh ba
        Intent intent = new Intent(this,MhDanhBaActivity.class);
        intent.putExtra("TAB","TAB1");
        intent.putExtra("NUMBTN",i);
        startActivity(intent);
    }


    ////////////// TASK DONG BO DANH BA --khoi tao Doc danh ba tu CSDL, neu chua co du lieu thi doc tu dien thoai
    class  TaskDocDanhBa extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dongBoDanhBa.RunDongBo();
            return null;
        }
    }
    /////////////// INPUT SPEED

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    xuLyGoiDt(result.get(0));
                }
                break;
            }

        }
    }

    private void xuLyGoiDt(String s) {

        if(s.toLowerCase().contains("sms"))
        {
            //gui sms
            String[] arrayString = s.toLowerCase().split("sms");
            String k1 = arrayString[0];
            final String k2 = arrayString[1];
            ArrayList<DanhBa> dsdb = (ArrayList<DanhBa>) db.getAllDanhBasTheoTen(k1.trim());
            Log.e("dsdbne " , dsdb.size() +"");
            if(dsdb.size() > 0) {
                final String sdt = dsdb.get(0).getSo_dt();
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Gửi sms đến " + sdt);
                builder.setMessage(k2);
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendSmsResult(sdt,k2);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                //Log.e("Gui sms", dsdb.size() + sdt + " noi dung: " + k2);
            }
            else
            {
                Toast.makeText(this,"Không tìm thấy danh bạ " + k1.trim(),Toast.LENGTH_SHORT).show();
            }
        }else
        {
            //goi dien thoai
            ArrayList<DanhBa> dsdb = (ArrayList<DanhBa>) db.getAllDanhBasTheoTen(s);
            if(dsdb.size()>0) {
                String sdt = dsdb.get(0).getSo_dt();
                Toast.makeText(this,"Gọi đến số " + s + " " + sdt,Toast.LENGTH_SHORT).show();
                lam.callPhone(sdt);
            }
            else
            {
               Toast.makeText(this,"Không tìm thấy danh bạ",Toast.LENGTH_SHORT).show();
            }
            //Log.e("Goi dt: ", sdt );
        }

    }
    //gui sms
    public void sendSmsResult(String phone,String smsMsg)
    {
        SmsManager smsManager = SmsManager.getDefault();
        Intent intentMsg = new Intent("ACTION_MSG_SENT");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                MainActivity.this,
                0,
                intentMsg,
                0);

        MainActivity.this.registerReceiver(new BroadcastReceiver() {
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



    ///////////// ONPAUSE va ONRESUM

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //doc lai CSDL
        khoiTaoCSDL();
        ganTenControlTab1();

    }
}
