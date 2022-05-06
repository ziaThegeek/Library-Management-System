package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

public class db_handler_acc_numbers extends SQLiteOpenHelper {
    String batch_name;
    List<String> ref_no,name,amount,age;

    public String getBatch_name() {
        return batch_name;
    }

    public List<String> getRef_no() {
        return ref_no;
    }

    public List<String> getName() {
        return name;
    }

    public List<String> getAmount() {
        return amount;
    }

    public List<String> getAge() {
        return age;
    }

    public db_handler_acc_numbers(Context context) {
        super(context, "nwl_03", null, 1);
        this.batch_name=batch_name;
        init();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    public void create_batch(String batch_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS '" + batch_name + "' (" +
                        "ref_no" + " TEXT PRIMARY KEY," +
                        "name" + " TEXT," +
                        "amount" + " TEXT," +
                        "age" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String ref_no,String name,String amount,String age,String batch_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ref_no",ref_no);
        contentValues.put("name",name);
        contentValues.put("amount",amount);
        contentValues.put("age",age);
        long newRowId = sqLiteDatabase.insert("'"+batch_name+"'", null, contentValues);
    }
    public void update(String ref_no,String name,String amount,String age,String batch_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
//        contentValues.put("ref_no",ref_no);
        contentValues.put("name",name);
        contentValues.put("amount",amount);
        contentValues.put("age",age);
        sqLiteDatabase.update("'"+batch_name+"'", contentValues, "ref_no=?", new String[]{ref_no});

    }
    public void load_data(String batch_name)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT * FROM '"+batch_name+"' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(cursor.getString(2));
            age.add(cursor.getString(3));
        }
        cursor.close();

    }

    public  void init()
    {
        this.name=new ArrayList<>();
        this.ref_no=new ArrayList<>();
        this.amount=new ArrayList<>();
        this.age=new ArrayList<>();
    }
}
