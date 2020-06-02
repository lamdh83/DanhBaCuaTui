package com.dohoailam.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dohoailam.model.DanhBa;
//import com.dohoailam.model.Nhom;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {
    // Logcat tag
    private static final String LOG = DataBaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contact.db";

    // Table Names
    private static final String TABLE_DANH_BA = "danhbas";




    // DANH BA - NHOM table column names

    private static final String KEY_SO_DT = "so_dt";
    private static final String KEY_HO_TEN = "ho_ten";

    private static final String KEY_HINH_ANH = "hinh_anh";

    private static final String KEY_STT = "so_tt";






    // Table Create Statements
    // DANH_BA table create statement
    private static final String CREATE_TABLE_DANH_BA = "CREATE TABLE "
            + TABLE_DANH_BA + "(" + KEY_STT +  " INTEGER," + KEY_SO_DT + " TEXT," + KEY_HO_TEN
            + " TEXT," +  KEY_HINH_ANH + " TEXT, " + "PRIMARY KEY (" + KEY_SO_DT + "," + KEY_HO_TEN + ")"
            + ")";





    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_DANH_BA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANH_BA);


        // create new tables
        onCreate(db);

    }

    // ------------------------ "danhbas" table methods ----------------//

    /**
     * Creating a danhba
     */
    public void createDanhBa(DanhBa danhBa) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HO_TEN, danhBa.getHo_ten());
        values.put(KEY_SO_DT, danhBa.getSo_dt());
        values.put(KEY_HINH_ANH, danhBa.getHinh_anh());

        // insert row
        db.insert(TABLE_DANH_BA, null, values);

    }

    /**
     * getting all danhbas
     * */
    public List<DanhBa> getAllDanhBas() {
        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT *" +
                " FROM " + TABLE_DANH_BA +
                " ORDER BY " + KEY_HO_TEN  ;


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));




                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }

    public List<DanhBa> getAllDanhBasFilter(String f) {

        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT *" +
                " FROM " + TABLE_DANH_BA +
                " WHERE lower("+ KEY_HO_TEN + ") LIKE '%" + f.toLowerCase() + "%'" +
                " OR " + KEY_SO_DT + " LIKE '%" + f + "%'" +
                " ORDER BY " + KEY_HO_TEN  ;


        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));




                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }

    /**
     * getting all danhbas
     * */
    public List<DanhBa> getAllDanhBasDial() {
        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT *" +
                " FROM " + TABLE_DANH_BA +
                " WHERE " + KEY_STT + "< 13";




        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));
                td.setSo_tt(c.getInt((c.getColumnIndex(KEY_STT))));




                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }


    public List<DanhBa> getDanhBaStt(int stt) {
        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT *" +
                " FROM " + TABLE_DANH_BA +
                " WHERE " + KEY_STT + " = " + stt;




        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));
                td.setSo_tt(c.getInt((c.getColumnIndex(KEY_STT))));




                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }


    /**
     * getting all danhbas theo so dien thoai
     * */
    public List<DanhBa> getAllDanhBasTheoSoDt(String soDt) {
        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT  * FROM " + TABLE_DANH_BA + " WHERE " + KEY_SO_DT + " ='" + soDt +"'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));



                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }



    /**
     * Deleting a DANHBA
     */
    public void deleteDanhBa(DanhBa danhBa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DANH_BA, KEY_SO_DT + " = ?",
                new String[] { String.valueOf(danhBa.getSo_dt().toString()) });
    }

    /**
     * Deleting a DANHBA
     */
    public void deleteAllDanhBa() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DANH_BA,null,null);
    }

    /**
     * getting danhba count
     */
    public int getDanhbaCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DANH_BA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    /**
     * getting danhba count
     */
    public int getDanhbaCount(String sodt) {
        String countQuery = "SELECT  * FROM " + TABLE_DANH_BA + " WHERE " + KEY_SO_DT + " = '" + sodt +"'" ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();

        // return count
        return count;
    }

    //get danh ba theo ten
    public List<DanhBa> getAllDanhBasTheoTen(String ten) {
        List<DanhBa> danhbas = new ArrayList<DanhBa>();
        String selectQuery = "SELECT  * FROM " + TABLE_DANH_BA + " WHERE " + KEY_HO_TEN + " like'%" + ten +"%'";

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (c.moveToFirst()) {
            do {
                DanhBa td = new DanhBa();

                td.setHinh_anh(c.getString((c.getColumnIndex(KEY_HINH_ANH))));
                td.setHo_ten(c.getString((c.getColumnIndex(KEY_HO_TEN))));
                td.setSo_dt(c.getString((c.getColumnIndex(KEY_SO_DT))));



                // adding to danhba list
                danhbas.add(td);
            } while (c.moveToNext());
        }

        return danhbas;
    }

    //Update STT cho Danh Ba

    /**
     * Updating a Danh Ba
     */
    public int updateSTTDanhBa(int stt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STT, 13);


        // updating row
        return db.update(TABLE_DANH_BA, values, KEY_STT + " = ?",
                new String[]{String.valueOf(stt)});
    }


    public int updateDanhBa(DanhBa danhBa,int stt) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STT, stt);


        // updating row
        return db.update(TABLE_DANH_BA, values, KEY_SO_DT + " = ?",
                new String[]{String.valueOf(danhBa.getSo_dt())});
    }





}
