package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class db_handler_batches extends SQLiteOpenHelper {
List<String> batch_no,village_names,last_date;
Context context;

    public db_handler_batches( Context context) {
        super(context, "nwl_03", null, 1);
        this.context=context;
        init();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + "feeder" + " (" +
                        "batch_no" + " TEXT," +
                        "vilages" + " TEXT," +
                        "last_date" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }

    public List<String> getBatch_no() {
        return batch_no;
    }

    public List<String> getVillage_names() {
        return village_names;
    }

    public List<String> getLast_date() {
        return last_date;
    }
    public String get_feeder_name(String feeder_code)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT feeder_name FROM '"+"feeders"+"' WHERE feeder_code="+feeder_code.trim()+"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst()){
            cursor.moveToFirst();
            return cursor.getString(0);
        }
        return null;
    }

    public void create_batch(String feeder_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
//        String SQL_CREATE_TABLE =
//                "CREATE TABLE IF NOT EXISTS '" +"batches" + "' (" +
//                        "batch_no" + " TEXT," +
//                        "vilages" + " TEXT," +
//                        "feeder_code" + " TEXT," +
//                        "subdivision_code" + " TEXT," +
//                        "last_date" + " TEXT)";
//        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    public void insert_data(String batch_no,String vilages,String last_date,String feeder_code,String division_code)
    {
SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("batch_no",batch_no);
        contentValues.put("vilages",vilages);
        contentValues.put("feeder_code",feeder_code);
        contentValues.put("subdivision_code",division_code);
        contentValues.put("last_date",last_date);

        long newRowId = sqLiteDatabase.insert("'"+"batches"+"'", null, contentValues);
    }
    public  void load_data(String feeder_code,String subdivision_code)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
//        if (get_feeder_code(feeder_name)!=null) {

            String query = "SELECT batch_no,vilages,last_date FROM batches WHERE feeder_code='" + feeder_code + "' AND subdivision_code=" + subdivision_code + "  ORDER BY batch_no ASC";
            Cursor cursor = sqLiteDatabase.rawQuery(query, null);
            while (cursor.moveToNext()) {
                batch_no.add(cursor.getString(0));
                village_names.add(cursor.getString(1));
                last_date.add(cursor.getString(2));
            }
            cursor.close();
//        Toast.makeText(context, get_feeder_name(feeder_code), Toast.LENGTH_SHORT).show();
//        }
    }
public void update(String batch_no,String vilages,String last_date)
{
    SQLiteDatabase sqLiteDatabase=getWritableDatabase();
    ContentValues contentValues=new ContentValues();
    contentValues.put("batch_no",batch_no);
    contentValues.put("vilages",vilages);
    contentValues.put("last_date",last_date);
    sqLiteDatabase.update("batch_no", contentValues, "batch_no=?", new String[]{batch_no});
}

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }
    public void init()
    {
        this.batch_no=new ArrayList<>();
        this.village_names=new ArrayList<>();
        this.last_date=new ArrayList<>();
    }
}
