package com.example.myapplication;
import  com.example.myapplication.Data.database_handler_class;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;
import com.example.myapplication.Params.params;

public class import_excel_file {
    Workbook wb;
    WorkbookSettings ws;
    InputStream is;
    Sheet sheet;
    List<String> names,authers,ed_no,year,location,publisher,isbn_no,call_no,page;
    List<Integer> qnty;
    int rows;
    Context context;
    Listview_Adapter listview_adapter;
    database_handler_class DbHandler;
    public import_excel_file(Context context)
    {
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
//location=new ArrayList<>();
}
    public   void read_excel_file(String filename) throws IOException, BiffException {
        ws = new WorkbookSettings();
        ws.setGCDisabled(true);
        is=new FileInputStream(filename);
        wb = Workbook.getWorkbook(is);
        sheet = wb.getSheet(0);

        rows= sheet.getRows();
     //   Toast.makeText(context,""+sheet.getColumns(),Toast.LENGTH_LONG).show();
            for (int i = 0; i < sheet.getRows(); i++) {
                Cell[] row = sheet.getRow(i);
                names.add(row[1].getContents());
                authers.add(row[2].getContents());
                ed_no.add(row[3].getContents());
                isbn_no.add(row[4].getContents());
                year.add(row[5].getContents());
                page.add(row[6].getContents());
                call_no.add(row[7].getContents());
                qnty.add(Integer.parseInt("0"+row[8].getContents()));
//                publisher.add(row[10].getContents());
//                location.add(row[11].getContents());
               // location.add(row[11].getContents());
            }
        }
    public List<String> getNames()
    {
        return  names;
    }
    public List<String> getAuthers()
    {
        return  authers;
    }
    public List<String> getEd_no()
    {
        return  ed_no;
    }
    public int getRows()
    {
        return  rows;
    }

    public List<String> getYear() {
        return year;
    }

//    public List<String> getLocation() {
//        return location;
//    }
//
//    public List<String> getPublisher() {
//        return publisher;
//    }
//
    public List<String> getIsbn_no() {
        return isbn_no;
    }

    public List<Integer> getQnty() {
        return qnty;
    }

    public List<String> getCall_no() {
        return call_no;
    }

    public List<String> getPage() {
        return page;
    }

    //    public  void insert_into_datbase(database_handler_class dbHelper,String name,String auther_name,String details) throws IOException, BiffException {
//            dbHelper.insert_data(name,auther_name,details);
//
//    }
public void clear()
{
    names.clear();
    authers.clear();
    ed_no.clear();
    isbn_no.clear();
}
    public void delete(int id)
    {
       // DbHandler.deleteTitle(id);
        DbHandler.close_connection();
    }
}
