package com.example.myapplication.Data;
import com.example.myapplication.Params.params;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class seach_student extends SQLiteOpenHelper {
    Context context;
    public seach_student( Context context) {
        super(context, params.STUDENT_NAME, null, params.DB_VERSION);
    this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public  boolean search(String reg_no)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        String[] columns = new String[]{params.REG};
        SQLiteQueryBuilder qb=new SQLiteQueryBuilder();
        String where = params.REG + " = ?";
        String[]whereArgs = new String[]{"2018-CS-531"};



        try {
            Cursor cursor = db.rawQuery("Select * from  "+params.STUDENTS_TABLE+" where "+params.REG+"=?", new String[]{reg_no});

            Toast.makeText(context.getApplicationContext(), cursor.getCount()+"",Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d("eror", "SEARCH EXCEPTION! " + e);
        }

        return true;
    }

//        Toast.makeText(context, cursor.getString(0), Toast.LENGTH_SHORT).show();



}
