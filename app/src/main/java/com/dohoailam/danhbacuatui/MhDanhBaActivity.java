package com.dohoailam.danhbacuatui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.dohoailam.adapter.DanhBaAdapter;
import com.dohoailam.helper.DataBaseHelper;
import com.dohoailam.model.DanhBa;
import com.dohoailam.ultils.DongBoDanhBa;
import com.dohoailam.ultils.Lam;

import java.util.ArrayList;

public class MhDanhBaActivity extends AppCompatActivity {
    Intent intent;
    String tabName;
    int soBtn;
    ListView lvDanhba;
    DanhBaAdapter danhBaAdapter;
    DataBaseHelper db;
    DanhBa selectedDanhBa = new DanhBa();
    Lam lam = new Lam(this);
    ProgressDialog progressDialog;
    DongBoDanhBa dongBoDanhBa;
    ArrayList<DanhBa> listDbCheck;
    String kq = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_danh_ba);
        addControls();
        addEvents();
    }

    //////// ADDCONTROLS

    private void addControls() {
        intent = getIntent();
        tabName = intent.getStringExtra("TAB");
        soBtn = intent.getIntExtra("NUMBTN",0);
        db = new DataBaseHelper(this);
        lvDanhba = findViewById(R.id.lvDanhBa);
        danhBaAdapter = new DanhBaAdapter(this,R.layout.item);
        lvDanhba.setAdapter(danhBaAdapter);
        danhBaAdapter.addAll(db.getAllDanhBas());
        danhBaAdapter.notifyDataSetChanged();
        listDbCheck = new ArrayList<>();
        registerForContextMenu(lvDanhba);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Chờ tí...");

        dongBoDanhBa = new DongBoDanhBa(db,this);
    }



    /////////ADDEVENTS
    private void addEvents() {
        //event click chon DB
        eventClickChonDb();

    }

    //////////
    private void eventClickChonDb() {
        lvDanhba.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedDanhBa = danhBaAdapter.getItem(i);
                toad("đã chọn " + selectedDanhBa.getHo_ten());
                //sự kiện trong TAB1 - chọn quay số nhanh
                if(tabName.equals("TAB1"))
                {

                    xuLyChonDbTab1(selectedDanhBa);
                }else
                {
                    xuLyChonDbTab2(selectedDanhBa);
                    //textView.setText("Ban da chon tab 2");
                }
            }
        });
    }

    private void xuLyChonDbTab2(DanhBa d) {
        if(d.getActive())
        {
            d.setActive(false);
            listDbCheck.remove(d);
        }
        else
        {
            d.setActive(true);
            listDbCheck.add(d);
        }

       // Toast.makeText(this,"tab2" + d.getActive().toString(),Toast.LENGTH_SHORT).show();
        danhBaAdapter.notifyDataSetChanged();

    }

    //////////////


    /////
//chon danh ba cho quay so nhanh
    private void xuLyChonDbTab1(final DanhBa danhBa)
    {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông Báo.");
        builder.setMessage("Bạn có chọn " + danhBa.getHo_ten() +" không?");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //update STT cua danh ba theo number
                capNhatQuaySoNhanh(danhBa);

            }
        });

        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void capNhatQuaySoNhanh(DanhBa danhBa) {
        int kq =  db.updateSTTDanhBa(soBtn);
        int kq2 = db.updateDanhBa(danhBa,soBtn);
        //Toast.makeText(this,kq + " " + kq2,Toast.LENGTH_LONG).show();
        db.close();
        MhDanhBaActivity.this.finish();
    }

    /////////


    //////MENU SEARCH OPTION


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnuSearch);
        SearchView searchView = (SearchView) mnuSearch.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                danhBaAdapter.clear();
                danhBaAdapter.addAll(db.getAllDanhBasFilter(newText));
                danhBaAdapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mnuThem:
                moManHinhThemDanhBa();
                break;
            case R.id.mnuXoa:
                xuLyXoaDanhBa();
                break;
            case R.id.mnuDongBo:
                docDanhBaTuMay();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    private void docDanhBaTuMay() {
        try {
            TaskDocDanhBa taskDocDanhBa = new TaskDocDanhBa();
            taskDocDanhBa.execute();

        }catch (Exception ex)
        {
            Toast.makeText(this,"Lỗi gì đó rồi, thử lại sau nhé hihi..",Toast.LENGTH_SHORT).show();
        }

    }

    private void xuLyXoaDanhBa() {
        try {
            if(listDbCheck.size() > 0) {
                for (DanhBa danhba : listDbCheck) {
                    db.deleteDanhBa(danhba);
                }
                Toast.makeText(this, "Đã xóa danh bạ", Toast.LENGTH_LONG).show();
                listDbCheck.clear();
                danhBaAdapter.clear();
                danhBaAdapter.addAll(db.getAllDanhBas());
                danhBaAdapter.notifyDataSetChanged();
            }else
            {
                Toast.makeText(this, "chưa chọn danh bạ", Toast.LENGTH_LONG).show();
            }

        }catch (Exception ex)
        {

        }
    }

    private void moManHinhThemDanhBa() {
        Intent intentThemDanhBa = new Intent(this, MhQuanLyDanhBaActivity.class);
        startActivity(intentThemDanhBa);
    }
//////END MENU SEARCH OPTION

    //////MENU CONTEXT

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (selectedDanhBa != null) {
            switch (item.getItemId()) {
                case R.id.mnuCall:
                    lam.callPhone(selectedDanhBa.getSo_dt());
                    break;
                case R.id.mnuSms:
                    moManHinhNhanTin(selectedDanhBa.getSo_dt().toString());
                    break;
                case R.id.mnuSua:
                    moManHinhSuaDanhBa(selectedDanhBa);
                    break;

            }
        }else
        {
            Toast.makeText(this,"Bạn chưa chọn danh bạ.",Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    private void moManHinhSuaDanhBa(DanhBa selectedDanhBa) {
        if (selectedDanhBa != null) {
            Intent intentSuaDb = new Intent(this, MhQuanLyDanhBaActivity.class);
            intentSuaDb.putExtra("DANHBA", selectedDanhBa);
            startActivity(intentSuaDb);
        }else
        {
            Toast.makeText(this,"Bạn chưa chọn danh bạ",Toast.LENGTH_SHORT).show();
        }
    }

    private void moManHinhNhanTin(String sodt) {
        Intent intentSms = new Intent(this,MhNhanTinActivity.class);
        intentSms.putExtra("SDT",sodt);
        startActivity(intentSms);
    }

    //////END MENU CONTEXT

    @Override
    protected void onResume() {
        super.onResume();
        db = new DataBaseHelper(this);
        danhBaAdapter.clear();
        danhBaAdapter.addAll(db.getAllDanhBas());
        danhBaAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        db.close();
    }


    /////////////TASK
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
            danhBaAdapter.clear();
            danhBaAdapter.addAll(db.getAllDanhBas());
            danhBaAdapter.notifyDataSetChanged();
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

    private void toad(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }


}





