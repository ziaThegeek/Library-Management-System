package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class db_handler_batches extends SQLiteOpenHelper {
List<String> batch_no,village_names,last_date;

    public db_handler_batches( Context context) {
        super(context, "nwl_03", null, 1);
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

    public void create_batch(String feeder_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS '" + feeder_name + "' (" +
                        "batch_no" + " TEXT," +
                        "vilages" + " TEXT," +
                        "last_date" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    public void insert_data(String batch_no,String vilages,String last_date,String feeder_name)
    {
SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("batch_no",batch_no);
        contentValues.put("vilages",vilages);
        contentValues.put("last_date",last_date);
        long newRowId = sqLiteDatabase.insert("'"+feeder_name+"'", null, contentValues);
    }
    public  void load_data(String feeder_name)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
String query="SELECT * FROM '"+feeder_name+"' ORDER BY batch_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
while (cursor.moveToNext())
{
batch_no.add(cursor.getString(0));
village_names.add(cursor.getString(1));
last_date.add(cursor.getString(2));
}
cursor.close();
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
