package com.example.barcodereader;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class db_handler_sub_divisions extends SQLiteOpenHelper {
    List<String> divsion_name,division_code,sdo_mame;
    Context context;

    public List<String> getDivsion_name() {
        return divsion_name;
    }

    public List<String> getDivision_code() {
        return division_code;
    }

    public List<String> getSdo_mame() {
        return sdo_mame;
    }

    public db_handler_sub_divisions(Context context) {
        super(context, "nwl_03", null, 1);
        this.context=context;
        init();
    }

    private void init() {
        division_code=new ArrayList<>();
        divsion_name=new ArrayList<>();
        sdo_mame=new ArrayList<>();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + "sub_divisions" + " (" +
                        "division_name" + " TEXT," +
                        "division_code" + " TEXT PRIMARY KEY," +
                        "sdo_name" + " TEXT)";
//        Toast.makeText(context, "created", Toast.LENGTH_SHORT).show();
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
        create_feader(sqLiteDatabase);
        create_batch(sqLiteDatabase);
        create_accout(sqLiteDatabase);

//        create_bacthes(sqLiteDatabase);

    }
    public  void insert(String division_name,String division_code,String sdo_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("division_name",division_name);
        contentValues.put("division_code",division_code);
        contentValues.put("sdo_name",sdo_name);
        long newRowId = sqLiteDatabase.insert("sub_divisions", null, contentValues);
    }
    public  void update(String feeder_name,String feeder_code,String bd_name)
    {
        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("division_name",feeder_name);
        contentValues.put("division_code",feeder_code);
        contentValues.put("sdo_name",bd_name);
        sqLiteDatabase.update("feeders", contentValues, "division_code=?", new String[]{feeder_code});
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void load_data()
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT * FROM sub_divisions ORDER BY division_code ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        while (cursor.moveToNext())
        {
            divsion_name.add(cursor.getString(0));
            division_code.add(cursor.getString(1));
            sdo_mame.add(cursor.getString(2));
        }
        cursor.close();
    }
    public void  create_feader(SQLiteDatabase sqLiteDatabase)
    {
//        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
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
    public void create_batch(SQLiteDatabase sqLiteDatabase)
    {
//        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS '" +"batches" + "' (" +
                        "batch_no" + " TEXT," +
                        "vilages" + " TEXT," +
                        "feeder_code" + " TEXT," +
                        "subdivision_code" + " TEXT," +
                        "last_date" + " TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    public void create_accout(SQLiteDatabase sqLiteDatabase)
    {
//        SQLiteDatabase sqLiteDatabase=getWritableDatabase();
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " +"acc_nos" + " (" +
                        "ref_no" + " TEXT PRIMARY KEY," +
                        "name" + " TEXT," +
                        "meter_no" + " TEXT," +
                        "batch_no" + " TEXT," +
                        "amount" + " INT," +
                        "feeder_code" + " TEXT," +
                        "subdivision_code" + " TEXT," +
                        "age" + " INT,"+
                         "phone_number"+" TEXT)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE);
    }
    public boolean has_duplicate(String division_code)
    {
        SQLiteDatabase sqLiteDatabase=getReadableDatabase();
        String query="SELECT  division_code  FROM sub_divisions where division_code='"+division_code+"'";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        if (cursor.moveToFirst())
            return true;

        return false;
    }
}
