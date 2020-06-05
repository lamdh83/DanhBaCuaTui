package com.dohoailam.danhbacuatui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
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
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.androdocs.httprequest.HttpRequest;
import com.dohoailam.helper.DataBaseHelper;
import com.dohoailam.model.DanhBa;
import com.dohoailam.model.QuocGia;
import com.dohoailam.ultils.DanhSachQuocGia;
import com.dohoailam.ultils.DongBoDanhBa;
import com.dohoailam.ultils.Lam;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    TabHost tabHost;
    DataBaseHelper db;
    DongBoDanhBa dongBoDanhBa;
    ProgressDialog progressDialog;
    Lam lam = new Lam(this);
    private final int REQ_CODE_SPEECH_INPUT = 100;
    ArrayList<String> arraySoDt = new ArrayList<>();
    //LatLng curLatLng = new LatLng(21.0278,105.8342);

    // TAB1 //
    ImageView imgMic;
    Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;

    //TAB2
    Button btnTab2_1,btnTab2_2,btnTab2_3,btnTab2_4,btnTab2_5,btnTab2_6,btnTab2_7,btnTab2_8,btnTab2_9,btnTab2_0
            ,btnTab2_Thang,btnTab2_Sao;
    ImageButton imgBtnTab2_DanhBa, imgBtnTab2_Call,imgBtnTab2_CLR;
    TextView txtSoDt;

    //TAB3
    LatLng curLatLng = new LatLng(21.0278,105.8342);;

    String CITY = "dhaka,bd";
    String API = "758c97093c1bd6a7203574553a198abe";

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    ArrayList<QuocGia> quocGiaArrayList = new ArrayList<>();
    AutoCompleteTextView autoCompleteQuocGia;
    DanhSachQuocGia danhSachQuocGia = new DanhSachQuocGia(quocGiaArrayList);
    ArrayAdapter<String> quocgiaAdapter;
    Button btnLamTuoi;

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

        KhaiBaoControlTab3();
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
        tab3.setIndicator("THỜI TIẾT");
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


    /////////////KhaiBaoControlTab3

    private void KhaiBaoControlTab3() {

        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);
//khai bao cho get current location

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            getLastLocation();
            new weatherTask().execute(curLatLng);
        }catch (Exception ex)
        {
            Toast.makeText(this,ex.toString(),Toast.LENGTH_LONG).show();
        }
//khoi tao autocompletequociga
        autoCompleteQuocGia = findViewById(R.id.autoCompleteQuocGia);
        danhSachQuocGia.taoDanhSach();
        quocgiaAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        autoCompleteQuocGia.setAdapter(quocgiaAdapter);
        String [] listQuocGia = getResources().getStringArray(R.array.arrayQuocGia);
        quocgiaAdapter.addAll(listQuocGia);
        btnLamTuoi = findViewById(R.id.btnLamTuoi);
    }

    /////////////////// ADD EVENTS////////////////////

    private void addEvents() {
        //envent chon TAB
        eventChonTab();
        //event clikc tab 1
        eventClickTab1();
        eventClickTab2();
        eventLongClickTab1();
        eventTab3();


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

    private void eventTab3() {
        autoCompleteQuocGia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String quocGiaName = quocgiaAdapter.getItem(i);

                try {
                    for (QuocGia g:quocGiaArrayList)
                    {
                        if (g.getTen().equals(quocGiaName))
                        {
                            LatLng latLngQg = g.getLatLng();
                            new weatherTask().execute(latLngQg);
                            break;
                        }
                    }

                }catch (Exception ex)
                {
                   toad(ex.toString());
                }


            }
        });


        btnLamTuoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getLastLocation();
                    new weatherTask().execute(curLatLng);
                }catch (Exception ex)
                {

                }

            }
        });
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

    ///TASK WEATHER
class weatherTask extends AsyncTask<LatLng,Void,String>
    {

        @Override
        protected String doInBackground(LatLng... latLngs) {
            String response = HttpRequest.excuteGet("https://api.openweathermap.org/data/2.5/weather?lat=" + latLngs[0].latitude + "&lon=" + latLngs[0].longitude + "&units=metric&appid=" + API);
            return response;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /* Showing the ProgressBar, Making the main design GONE */
            findViewById(R.id.loader).setVisibility(View.VISIBLE);
            findViewById(R.id.mainContainer).setVisibility(View.GONE);
            findViewById(R.id.errorText).setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObj = new JSONObject(s);
                JSONObject main = jsonObj.getJSONObject("main");
                JSONObject sys = jsonObj.getJSONObject("sys");
                JSONObject wind = jsonObj.getJSONObject("wind");
                JSONObject weather = jsonObj.getJSONArray("weather").getJSONObject(0);

                Long updatedAt = jsonObj.getLong("dt");
                String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                String temp = main.getString("temp") + "°C";
                String tempMin = "Min Temp: " + main.getString("temp_min") + "°C";
                String tempMax = "Max Temp: " + main.getString("temp_max") + "°C";
                String pressure = main.getString("pressure");
                String humidity = main.getString("humidity");

                Long sunrise = sys.getLong("sunrise");
                Long sunset = sys.getLong("sunset");
                String windSpeed = wind.getString("speed");
                String weatherDescription = weather.getString("description");

                String address = jsonObj.getString("name") + ", " + sys.getString("country");


                /* Populating extracted data into our views */
                addressTxt.setText(address);
                updated_atTxt.setText(updatedAtText);
                statusTxt.setText(weatherDescription.toUpperCase());
                tempTxt.setText(temp);
                temp_minTxt.setText(tempMin);
                temp_maxTxt.setText(tempMax);
                sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                windTxt.setText(windSpeed);
                pressureTxt.setText(pressure);
                humidityTxt.setText(humidity);

                /* Views populated, Hiding the loader, Showing the main design */
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.mainContainer).setVisibility(View.VISIBLE);


            } catch (JSONException e) {
                findViewById(R.id.loader).setVisibility(View.GONE);
                findViewById(R.id.errorText).setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
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


    /////////GETCURRENTLOCATION
    @SuppressLint("MissingPermission")
    private void getLastLocation(){
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    new weatherTask().execute(new LatLng(location.getLatitude(),location.getLongitude()));

                                }
                            }
                        }
                );
            } else {
                //y/c bat GPS
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                //chuyen den GPS
                /*Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);*/
            }
        } else {
            requestPermissions();
        }
    }


    @SuppressLint("MissingPermission")
    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            new weatherTask().execute(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()));

        }
    };

    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }


    public void toad(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_LONG).show();
    }
}
