package com.example.myapplication.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.myapplication.Params.params;
import com.example.myapplication.import_student_file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

public class datbase_handler_students extends SQLiteOpenHelper {
    Context context;
    public  List<String> names,reg;
    List<Integer> id;
    public datbase_handler_students(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
        this.context=context;
        initial_work();
    }
    public  void initial_work()
    {
        names=new ArrayList<>();
        reg=new ArrayList<>();
        id=new ArrayList<>();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
//database_handler_class database_handler_class=new database_handler_class(context);
//database_handler_class.OnCreate(db);
//        Toast.makeText(context, "oncreate"+db, Toast.LENGTH_LONG).show();

//        Toast.makeText(context,"oncreate",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        String SQL_DELETE_TABLE =
//                "DROP TABLE IF EXISTS " + params.TABLE_NAME;
//        db.execSQL(SQL_DELETE_TABLE);
//        onCreate(db);

    }
    public void OnCreate(String filename)
    {

        import_student_file imp1=new import_student_file(context);
        SQLiteDatabase db=this.getWritableDatabase();

        try {

            imp1.read_excel_file(filename);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        names = imp1.getNames();
        reg=imp1.getReg();
        for (int i = 0; i < imp1.getRows(); i++) {
            try {
                insert_data(db,names.get(i), reg.get(i));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (BiffException e) {
                e.printStackTrace();
            }
        }
clear();
    }
  public List<String> getReg(){
        return  reg;
  }
    public List<String> getNames()
    {
        return  names;
    }
    public List<Integer> getid(){return  id;}


    public void Create_Table(SQLiteDatabase db)
    {
        String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + params.STUDENTS_TABLE + " (" +
                        params.STUDENT_NAME + " TEXT," +
                        params.REG + " TEXT PRIMARY KEY)";
        db.execSQL(SQL_CREATE_TABLE);


    }
public String getNameAt(String reg)
{
    SQLiteDatabase db=this.getWritableDatabase();
    String delete_query="SELECT "+params.REG+" FROM  "+params.STUDENTS_TABLE+" WHERE "+params.REG+"='"+id+"'";
    Cursor data=db.rawQuery(delete_query,null);
    if (data.getCount()<=0)
    {
        Toast.makeText(context,"no result found",Toast.LENGTH_SHORT).show();

    }
    else
    {
        data.moveToFirst();
        return data.getString(0);
    }
    return "NULL";
}
    public  void  insert_data(SQLiteDatabase db,String student_name,String reg) throws IOException, BiffException {
//         SQLiteDatabase db = this.getWritableDatabase();
//        name = new ArrayList<>();
//        auther = new ArrayList<>();
//        details = new ArrayList<>();
//        import_excel_file imp1 = new import_excel_file();
//        AssetManager am = context.getAssets();
//        imp1.read_excel_file(am);
//        name = imp1.getNames();
//        auther = imp1.getAuthers();
//        details = imp1.getDetails();
// Create a new map of values, where column names are the keys
        if (student_name.isEmpty()&&reg.isEmpty())
            return;
        ContentValues values = new ContentValues();

//            values.put(params.BOOK_ID, i);
        values.put(params.STUDENT_NAME, student_name);
        values.put(params.REG, reg);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(params.STUDENTS_TABLE, null, values);
    }
    public  Cursor Get_data()
    {
        SQLiteDatabase     db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + params.STUDENTS_TABLE;
        Cursor cursor = db.rawQuery(selectQuery, null);
//            db.close();
//        Toast.makeText(context,""+db,Toast.LENGTH_LONG).show();
//
        return cursor;
    }
    public  void close_connection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.close();
    }
    public void deleteTitle(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
        String delete_query="DELETE FROM  "+params.STUDENTS_TABLE+" WHERE "+params.REG+"='"+id+"'";
        db.execSQL(delete_query);
    }
    public void  clear()
    {
        names.clear();
        reg.clear();
        id.clear();
    }
    public  void  insert_into_list_view(Cursor data)
    {
//        Toast.makeText(context,""+data,Toast.LENGTH_LONG).show();
//        data.moveToFirst();
        while (data.moveToNext()) {
            names.add(data.getString(0));
            reg.add(data.getString(1));
        //    id.add(data.getInt(0));
        }
//data.close();

    }

    public void show()
    {

    }

    public void add_one_student(String name, String reg) throws IOException, BiffException {
        SQLiteDatabase db=this.getWritableDatabase();
        insert_data(db,name,reg);
    }
    public void update_student(String student_name,String reg)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values = new ContentValues();

//            values.put(params.BOOK_ID, i);
        values.put(params.STUDENT_NAME, student_name);
        values.put(params.REG, reg);
        db.update(params.STUDENTS_TABLE,values,params.REG+"=?",new String[]{reg});
        Toast.makeText(context, "updated successfully", Toast.LENGTH_SHORT).show();
    }
    public Cursor search(String registration_no)

    {
        SQLiteDatabase db=this.getReadableDatabase();
        String SEARCH_QUERY="SELECT * FROM "+params.STUDENTS_TABLE+" WHERE "+params.REG+"='"+registration_no+"'";
        Cursor cursor=db.rawQuery(SEARCH_QUERY,null);
        return cursor;
    }
}