package com.example.myapplication.Data;
import com.example.myapplication.Params.params;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.provider.ContactsContract;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Issue_and_return_book extends SQLiteOpenHelper {
    Context context;
    public Issue_and_return_book( Context context) {
        super(context, params.DB_NAME,null , params.DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void create_table(SQLiteDatabase db){
        String SQL_CREATE_TABLE= "CREATE TABLE " + params.BOOKS_TABLE+ " (" +
                params.CALL_NO + " TEXT," +
                params.REG + " TEXT," +
                params.DATE_ISSUED + " TEXT)" ;
//                params.DATE_RETURNED+"TEXT)";
        db.execSQL(SQL_CREATE_TABLE);

    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  boolean insert(String call_no, String reg,String date )
    {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
//            values.put(params.BOOK_ID, i);

//        values.put(params.DATE_ISSUED, String.valueOf(Date.valueOf(date)));

        values.put(params.CALL_NO, call_no);
        values.put(params.REG, reg);
        values.put(params.DATE_ISSUED, date);
//        Toast.makeText(context, date.toString(), Toast.LENGTH_SHORT).show();
//        values.put(params.DATE_RETURNED,"uhgsf");
        long newRowId = db.insert(params.BOOKS_TABLE, null, values);
        if (newRowId==-1)
            return false;
        else
            return true;



    }
    public  void delete(String Reg,String call_no)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
        String delete_query="DELETE  FROM "+params.BOOKS_TABLE+" WHERE "+params.CALL_NO+"='"+call_no+"'"+" AND "+params.REG+"='"+Reg+"'";
        db.execSQL(delete_query);
    }
    public Cursor Get_data()
    {
        SQLiteDatabase     db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + params.BOOKS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);

//            db.close();
        //Toast.makeText(context,""+cursor.getColumnCount(),Toast.LENGTH_LONG).show();\
//
        return cursor;
    }
    public Cursor search(String call_no,String reg)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        String SEARCH_QUERY="SELECT * FROM "+ params.BOOKS_TABLE+" WHERE "+params.CALL_NO+"='"+call_no+"' OR "+params.REG+"='"+reg+"'";
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }

    public Cursor search(String reg)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        String SEARCH_QUERY="SELECT * FROM "+ params.BOOKS_TABLE+" WHERE "+params.REG+"='"+reg+"'";
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }
}
