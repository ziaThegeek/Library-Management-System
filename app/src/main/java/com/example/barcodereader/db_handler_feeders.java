package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class db_handler_feeders extends SQLiteOpenHelper {
    List<String> feeder_name,feeder_code,bd_mame;
    Context context;

    public List<String> getFeeder_name() {
        return feeder_name;
    }

    public List<String> getFeeder_code() {
        return feeder_code;
    }

    public List<String> getBd_mame() {
        return bd_mame;
    }

    public db_handler_feeders(Context context) {
        super(context, "nwl_03", null, 1);
        this.context=context;
        init();
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + "feeders" + " (" +
                        "feeder_name" + " TEXT," +
                        "feeder_code" + " TEXT," +
                        "subdivision_code" + " TEXT," +
                        "bd_name" + " TEXT)";
//        Toast.makeText(context, "created", Toast.LENGTH_SHORT).show();
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//        create_bacthes(sqLiteDatabase);

    }
    public void  create_feader()
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS '" + "feeders" + "' (" +
                        "feeder_name" + " TEXT," +
                        "feeder_code" + " TEXT," +
                        "subdivision_code" + " TEXT," +
                        "bd_name" + " TEXT)";
//        Toast.makeText(context, "created", Toast.LENGTH_SHORT).show();
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//        create_bacthes(sqLiteDatabase);
    }
    public  void insert(String feeder_name,String feeder_code,String division_code,String bd_name )
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
contentValues.put("feeder_name",feeder_name);
contentValues.put("feeder_code",feeder_code);
contentValues.put("subdivision_code",division_code);
contentValues.put("bd_name",bd_name);

long newRowId = sqLiteDatabase.insert("'"+"feeders"+"'", null, contentValues);
    }
    public  void update(String feeder_name,String feeder_code,String division_code,String bd_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("feeder_name",feeder_name);
        contentValues.put("feeder_code",feeder_code);
        contentValues.put("bd_name",bd_name);
        sqLiteDatabase.update(division_code, contentValues, "feeder_code=?", new String[]{feeder_code});
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public  void load_data(String division_code)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT feeder_name,feeder_code,bd_name FROM "+"feeders"+" WHERE subdivision_code="+division_code+" ORDER BY feeder_code ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        while (cursor.moveToNext())
        {
            feeder_name.add(cursor.getString(0));
            feeder_code.add(cursor.getString(1));
            bd_mame.add(cursor.getString(2));
        }
        cursor.close();
    }
//    public void create_bacthes(SQLiteDatabase sqLiteDatabase)
//    {
//        String SQL_CREATE_TABLE =
//                "CREATE TABLE IF NOT EXISTS " + "batches" + " (" +
//                        "batch_no" + " TEXT," +
//                        "vilages" + " TEXT," +
//                        "last_date" + " TEXT)";
//        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//    }
//    public  void create_account_numbers(SQLiteDatabase sqLiteDatabase,String feeder_name)
//    {
//        String SQL_CREATE_TABLE =
//                "CREATE TABLE IF NOT EXISTS " +"batches_"+ feeder_name + " (" +
//                        "ref_no" + " TEXT," +
//                        "name" + " TEXT," +
//                        "amount" + " TEXT," +
//                        "age" + " TEXT)";
//        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
//    }
    public void init()
    {
        feeder_name=new ArrayList<>();
        feeder_code=new ArrayList<>();
        bd_mame=new ArrayList<>();
    }
    public String get_feeder_code(String feeder_name){
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT feeder_code FROM '"+"feeders"+"' WHERE feerder_name='zia'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst())
            return cursor.getString(0);

        return null;

    }
}
