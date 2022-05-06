package com.example.myapplication.Data;
import com.example.myapplication.Listview_Adapter;
import com.example.myapplication.Params.params;
import com.example.myapplication.R;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class books_returned extends SQLiteOpenHelper {
Context context;

    public books_returned(Context context) {

        super(context,params.RETURNED_BOOKS , null, params.DB_VERSION);
  this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
create_table(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void create_table(SQLiteDatabase db) {
        String SQL_CREATE_TABLE = "CREATE TABLE " + params.RETURNED_BOOKS + " (" +
                params.CALL_NO + " TEXT," +
                params.REG + " TEXT," +
                params.DATE_ISSUED + " TEXT," +
                params.DATE_RETURNED + " TEXT," +
                params.CHARGES + " INTEGER)" ;
        db.execSQL(SQL_CREATE_TABLE);
    }
    public  void insert(String call_no,String reg,int charges ,String date_issued,String date_returned)
    {

        String date = new SimpleDateFormat("dd.MM.yyyy").format(Calendar
                .getInstance().getTime());
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
//            values.put(params.BOOK_ID, i);
        values.put(params.CALL_NO, call_no);
        values.put(params.REG, reg);
values.put(params.DATE_RETURNED,date_returned);
        values.put(params.DATE_ISSUED, date_issued);
        values.put(params.CHARGES,charges);

        long newRowId = db.insert(params.RETURNED_BOOKS, null, values);
        if (newRowId==-1)
            Toast.makeText(context,"not inserted",Toast.LENGTH_LONG);
        else
            Toast.makeText(context,"inserted",Toast.LENGTH_LONG);

    }
    public  void delete(String Reg)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
        String delete_query="DELETE FROM  "+params.RETURNED_BOOKS+" WHERE "+params.REG+"='"+Reg+"'";
        db.execSQL(delete_query);
    }
    public Cursor Get_data()


    {
        SQLiteDatabase     db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + params.RETURNED_BOOKS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        return cursor;
    }
    public Cursor search(String reg)

    {
        SQLiteDatabase db=this.getReadableDatabase();
        String SEARCH_QUERY="SELECT * FROM "+params.RETURNED_BOOKS+" WHERE "+params.REG+"='"+reg+"'";
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }
    public Cursor search(String call_no,String reg)
    {
        SQLiteDatabase db=this.getReadableDatabase();

        String SEARCH_QUERY="SELECT * FROM "+ params.RETURNED_BOOKS+" WHERE "+params.CALL_NO+"='"+call_no+"' and "+params.REG+"='"+reg+'"';
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }
}
