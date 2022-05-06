package com.example.myapplication.Data;
import  com.example.myapplication.import_excel_file;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

import  com.example.myapplication.Params.params;

public class database_handler_class extends SQLiteOpenHelper {
    Context context;
    List<String> names,authers,ed_no,year,location,publisher,isbn_no,call_no,page;
    List<Integer> id,qnty;
//    qnty;
    public database_handler_class(Context context) {
        super(context, params.DB_NAME, null, params.DB_VERSION);
        this.context=context;
        initial_work();
    }
public  void initial_work()
{
    names = new ArrayList<>();
    authers = new ArrayList<>();
    ed_no = new ArrayList<>();
    isbn_no=new ArrayList<>();
    year=new ArrayList<>();
    page=new ArrayList<>();
    call_no=new ArrayList<>();
    qnty=new ArrayList<>();
//    publisher=new ArrayList<>();
//    location=new ArrayList<>();
    id=new ArrayList<>();
}
    @Override
    public void onCreate(SQLiteDatabase db) {
//     OnCreate(db);
//        SQLiteDatabase db=this.getWritableDatabase();
//        Toast.makeText(context,"oncretae",Toast.LENGTH_LONG).show();
        datbase_handler_students dhs=new datbase_handler_students(context);
        dhs.Create_Table(db);
        Issue_and_return_book isue=new Issue_and_return_book(context);
        isue.create_table(db);
        books_returned returned_books=new books_returned(context);
        returned_books.create_table(db);
        Create_Table(db);

         }
public void Create_Tales(){

}
//    private void collect_data(import_excel_file imp1) {
//
//    names=imp1.getNames();
//    authers=imp1.getAuthers();
//    ed_no=imp1.getEd_no();
//    isbn_no=imp1.getIsbn_no();
//    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

//        String SQL_DELETE_TABLE =
//                "DROP TABLE IF EXISTS " + params.TABLE_NAME;
//        db.execSQL(SQL_DELETE_TABLE);
//        onCreate(db);
    }

public  void OnCreate(String excelfile)
{
    SQLiteDatabase db=this.getWritableDatabase();
//    Create_Table();
    import_excel_file imp1 = new import_excel_file(context);
    try {

        imp1.read_excel_file(excelfile);
    } catch (IOException e) {
        e.printStackTrace();
    } catch (BiffException e) {
        e.printStackTrace();
    }
    set_values(imp1);
    for (int i = 0; i < imp1.getRows(); i++) {
        try {
            insert_data(db,names.get(i), authers.get(i),ed_no.get(i),isbn_no.get(i),year.get(i),page.get(i),call_no.get(i),qnty.get(i));

//                ,year.get(i),
//                        page.get(i),call_no.get(i),qnty.get(i),publisher.get(i),location.get(i));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
    clear_data();
//        Toast.makeText(context,"oncreate",Toast.LENGTH_LONG).show();


}

    public List<String> getNames() {
        return names;
    }

    public List<String> getAuthers() {
        return authers;
    }

    public List<String> getEd_no() {
        return ed_no;
    }

//    public List<String> getYear() {
//        return year;
//    }
//
//    public List<String> getLocation() {
//        return location;
//    }
//
//    public List<String> getPublisher() {
//        return publisher;
//    }

    public List<String> getIsbn_no() {
        return isbn_no;
    }

//    public List<String> getCall_no() {
//        return call_no;
//    }

    public List<String> getYear() {
        return year;
    }

    public List<String> getPage() {
        return page;
    }

    public List<Integer> getQnty() {
        return qnty;
    }

    //    public List<String> getPage() {
//        return page;
//    }
//
    public List<Integer> getId() {
        return id;
    }
    public List<String> getCall_no() {
        return call_no;
    }
//
public  void  insert_data(SQLiteDatabase db,
                          String book_name,String auther_name,
                          String ed_no,String isbn_No,String year,String page,String call_no,int qnty)throws IOException, BiffException {
//Toast.makeText(context,"insert",Toast.LENGTH_LONG).show();
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
    ContentValues values = new ContentValues();

//            values.put(params.BOOK_ID, i);
    values.put(params.book_name, book_name);
    values.put(params.auther, auther_name);
    values.put(params.ED_NO, ed_no);
    values.put(params.ISBN_NO,isbn_No);
//            values.put(params.BOOK_ID,1);
    values.put(params.YEAR,year);
    values.put(params.PAGE,page);
    values.put(params.CALL_NO,call_no);
    values.put(params.QUANTITY,qnty);

//            values.put(params.PUBLISHER,publisher);
//            values.put(params.LOCATION,location);
// Insert the new row, returning the primary key value of the new row

  try {


      db.insert(params.TABLE_NAME, null, values);
  }
  catch (SQLiteConstraintException ex)
  {
      Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
  }

//
//    if (newRowId==-1)
//        Toast.makeText(context,"not inserted",Toast.LENGTH_LONG);
//    else
//        Toast.makeText(context,"inserted",Toast.LENGTH_LONG);
}
//    public List<Integer> getQnty() {
//        return qnty;

//    }

    public void Create_Table(SQLiteDatabase db)
    {

      String SQL_CREATE_TABLE= "CREATE TABLE " + params.TABLE_NAME + " (" +

                params.book_name + " TEXT," +
                params.auther + " TEXT," +
                params.ED_NO + " TEXT,"+
              params.ISBN_NO+" TEXT,"+
              params.YEAR+" TEXT,"+
              params.PAGE+" TEXT,"+
              params.CALL_NO+" TEXT PRIMARY KEY,"+
              params.QUANTITY+" INTEGER)";

        db.execSQL(SQL_CREATE_TABLE);


    }
    public void add_one_book(String book_name,String auther_name,
                             String ed_no,String isbn_No,String year,String page,String call_no,int qnty){
        SQLiteDatabase db=this.getReadableDatabase();
        try {
            insert_data(db,book_name,auther_name,ed_no,isbn_No,year,page,call_no,qnty);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
    }
    public void update_book(String book_name,String auther_name,
                             String ed_no,String isbn_No,String year,String page,String call_no,int qnty){
        SQLiteDatabase db=this.getReadableDatabase();
        ContentValues values = new ContentValues();

//            values.put(params.BOOK_ID, i);
        values.put(params.book_name, book_name);
        values.put(params.auther, auther_name);
        values.put(params.ED_NO, ed_no);
        values.put(params.ISBN_NO,isbn_No);
//            values.put(params.BOOK_ID,1);
        values.put(params.YEAR,year);
        values.put(params.PAGE,page);
        values.put(params.CALL_NO,call_no);
        values.put(params.QUANTITY,qnty);
        db.update(params.TABLE_NAME,values,params.CALL_NO+"=?",new String[]{call_no});
        Toast.makeText(context, "updated successfully", Toast.LENGTH_SHORT).show();

    }
    public  Cursor Get_data()
    {
        SQLiteDatabase     db = this.getReadableDatabase();
            String selectQuery = "SELECT  * FROM " + params.TABLE_NAME;
            Cursor cursor = db.rawQuery(selectQuery, null);

//            db.close();
        //Toast.makeText(context,""+cursor.getColumnCount(),Toast.LENGTH_LONG).show();\
//
        return cursor;


    }
    public  void close_connection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        db.close();
    }
    public void deleteTitle(String call_no)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Toast.makeText(context,"deleted",Toast.LENGTH_LONG).show();
        String delete_query="DELETE FROM  "+params.TABLE_NAME+" WHERE "+params.CALL_NO+"='"+call_no+"'";
        db.execSQL(delete_query);
            }
            public void  clear_data()
            {
                names.clear();
                authers.clear();
                ed_no.clear();
                isbn_no.clear();
                id.clear();
                year.clear();
                page.clear();
                call_no.clear();
//                qnty.clear();
//                publisher.clear();
//                location.clear();
            }
    public  void  insert_into_list_view(Cursor data)
    {
        //Toast.makeText(context,""+data,Toast.LENGTH_LONG).show();
//        data.moveToFirst();
        while (data.moveToNext()) {

            names.add(data.getString(0));
            authers.add(data.getString(1));
            ed_no.add(data.getString(2));
            isbn_no.add(data.getString(3));
            year.add(data.getString(4));
            page.add(data.getString(5));
            call_no.add(data.getString(6));
            qnty.add(data.getInt(7));
        }
//data.close();

    }

    public void show()
{

}
public void set_values(import_excel_file imp1)
{
    names = imp1.getNames();
    authers = imp1.getAuthers();
    ed_no = imp1.getEd_no();
    isbn_no=imp1.getIsbn_no();
    year=imp1.getYear();
    page=imp1.getPage();
    call_no=imp1.getCall_no();
    qnty=imp1.getQnty();
//    publisher=imp1.getPublisher();
//location=imp1.getLocation();
}
public  void add_data(Cursor data)
{
   // id.add(data.getInt(0));
    names.add(data.getString(0));
    authers.add(data.getString(1));
    ed_no.add(data.getString(2));
    isbn_no.add(data.getString(3));
    year.add(data.getString(4));
    page.add(data.getString(5));
    call_no.add(data.getString(6));
    qnty.add(data.getInt(7));
   // id.add(data.getInt(8));
//    publisher.add(data.getString(9));
//    location.add(data.getString(10));
//    id.add(data.getInt(0));
}
    public Cursor search(String call_no)

    {
        SQLiteDatabase db=this.getReadableDatabase();
        char c=(char)96;
        String SEARCH_QUERY="SELECT * FROM "+params.TABLE_NAME+" WHERE "+params.CALL_NO+"='"+call_no+"'";
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }
    public Cursor search(String call_no,String reg)

    {
        SQLiteDatabase db=this.getReadableDatabase();
        char c=(char)96;
        String SEARCH_QUERY="SELECT * FROM "+params.TABLE_NAME+" WHERE "+params.CALL_NO+"='"+call_no+"'"+" AND "+params.REG+"="+reg;
        Cursor x=db.rawQuery(SEARCH_QUERY,null);
        return x;
    }
}



