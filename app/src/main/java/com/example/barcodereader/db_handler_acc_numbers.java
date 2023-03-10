package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class db_handler_acc_numbers extends SQLiteOpenHelper {
    String batch_name,feeder_code,division_code,phone_number;
    List<String> ref_no,name,amount,age,meter_no,phoneNumber;

    public String getBatch_name() {
        return batch_name;
    }

    public List<String> getRef_no() {
        return ref_no;
    }
    public List<String> getRef_no_reverse() {
        Collections.reverse(ref_no);
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

    public List<String> getMeter_no() {
        return meter_no;
    }

    public List<String> getPhone_number() {
        return phoneNumber;
    }

    public db_handler_acc_numbers(Context context, String batch_name, String division_code, String feeder_code) {
        super(context, "nwl_03", null, 1);
        this.batch_name=batch_name;
        this.division_code=division_code;
        this.feeder_code=feeder_code;
        init();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }
    public void create_accout(String batch_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " +"acc_nos" + " (" +
                        "ref_no" + " TEXT PRIMARY KEY," +
                        "name" + " TEXT," +
                        "batch_no" + " TEXT," +
                        "meter_no" + " TEXT," +
                        "amount" + " INTEGER," +
                        "feeder_code" + " TEXT," +
                        "subdivision_code" + " TEXT," +
                        "age" + " INTEGER)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void insert(String ref_no,String name,int amount,int age,String meter_no,String phone_number)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("ref_no",ref_no);
        contentValues.put("name",name);
        contentValues.put("amount",amount);
        contentValues.put("batch_no",batch_name);
        contentValues.put("meter_no",meter_no);
        contentValues.put("feeder_code",feeder_code);
        contentValues.put("subdivision_code",division_code);
        contentValues.put("age",age);
        contentValues.put("phone_number",phone_number);
        long newRowId = sqLiteDatabase.insert("'"+"acc_nos"+"'", null, contentValues);
    }
    public void update(String ref_no,String name,int amount,int age,String meter_no)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
//        contentValues.put("ref_no",ref_no);
//        contentValues.put("batch_no",batch_name);
        contentValues.put("name",name);
        contentValues.put("meter_no",meter_no);
        contentValues.put("amount",amount);
        contentValues.put("age",age);

        sqLiteDatabase.update("'acc_nos'", contentValues, "ref_no=?", new String[]{ref_no});

    }
    public void load_data()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));

        }
        cursor.close();

    }

    public  void init()
    {
        this.name=new ArrayList<>();
            this.ref_no=new ArrayList<>();
        this.amount=new ArrayList<>();
        this.age=new ArrayList<>();
        this.meter_no=new ArrayList<>();
        phoneNumber=new ArrayList<>();
    }
    public List<String> search_name(String consumer_name)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and name like '%"+consumer_name+"%' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));
        }
        cursor.close();
        return null;
    }
    public List<String> search_amout(String Amount)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and amount like '%"+Amount+"%' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));
        }
        cursor.close();
        return null;
    }


    public List<String> search_age(String Age)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and age like '%"+Age+"%' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));
        }
        cursor.close();
        return null;
    }

    public List<String> search_acc(String acc_number)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and ref_no like '%"+acc_number+"%' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));
        }
        cursor.close();
        return null;
    }

    public List<String> search_metr(String meterno)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age,meter_no,phone_number  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and meter_no like '%"+meterno+"%' ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            ref_no.add(cursor.getString(0));
            name.add(cursor.getString(1));
            amount.add(String.valueOf(cursor.getInt(2)));
            age.add(String.valueOf(cursor.getInt(3)));
            meter_no.add(cursor.getString(4));
            phoneNumber.add(cursor.getString(5));
        }
        cursor.close();
        return null;
    }
    public void clear()
    {
        name.clear();
        ref_no.clear();
        amount.clear();
        age.clear();
        meter_no.clear();
        phoneNumber.clear();

    }
    public Cursor export_date(String acc_start,String acc_end,int amount_min,int amount_max,int age_min,int age_max)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  ref_no,name,amount,age" +
                "  FROM acc_nos " +
                "where batch_no='"+batch_name+"'  " +
                "and feeder_code='"+feeder_code+"' " +
                "and subdivision_code="+division_code+" " +
                "and ref_no between '"+acc_start+"' " + "and '"+acc_end+"' " +
                "and age between "+age_min+" and "+age_max+"" +
                " and amount between "+amount_min+" and "+amount_max+" " +
                "ORDER BY ref_no ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
return cursor;

    }
    public int getMaxAge()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  max(age)" +
                "  FROM acc_nos " +
                "where batch_no='"+this.batch_name+"'  " +
                "and feeder_code='"+this.feeder_code+"' " +
                "and subdivision_code="+this.division_code+"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        else
            return 10;
    }
    public int getMaxAmonnt()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  max(amount)" +
                "  FROM acc_nos " +
                "where batch_no='"+this.batch_name+"'  " +
                "and feeder_code='"+this.feeder_code+"' " +
                "and subdivision_code="+this.division_code+"";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor!=null) {
            cursor.moveToFirst();
            return cursor.getInt(0);
        }
        else return 100000;


    }
public boolean has_duplicate(String ref_no)
{
    SQLiteDatabase sqLiteDatabase=getReadableDatabase();
    String query="SELECT  age  FROM acc_nos where batch_no='"+batch_name+"'  and feeder_code='"+feeder_code+"' and subdivision_code="+division_code+" and ref_no='"+ref_no+"'";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    if (cursor.moveToFirst())
        return true;

    return false;
}
public   String bill_url(String account_number)
{
    SQLiteDatabase sqLiteDatabase=getReadableDatabase();
    String query="SELECT top 1 batch_name,subdivision_code  FROM acc_nos where ref_no like '%"+account_number+"'";
    Cursor cursor = sqLiteDatabase.rawQuery(query, null);
    return  ("https://bill.pitc.com.pk/gepcobill/general?refno="+cursor.getString(0)+ cursor.getString(1)+account_number);
}
}
//    and age>="+age_min+" and age<="+age_max+" and amount>="+amount_min+" and amount<="+amount_max+")