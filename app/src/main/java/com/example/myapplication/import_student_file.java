package com.example.myapplication;
import  com.example.myapplication.Data.database_handler_class;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;

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

public class import_student_file {
    Workbook wb;
    WorkbookSettings ws;
    InputStream is;
    Sheet sheet;
    List<String> names,reg;
    int rows;
    Context context;
    Listview_Adapter listview_adapter;
    database_handler_class DbHandler;
    public import_student_file(Context context)
    {
        this.context=context;
        initialwork();
    }

    private void initialwork() {
        names=new ArrayList<>();
        reg=new ArrayList<>();
    }

    public   void read_excel_file(String filename) throws IOException, BiffException {
        ws = new WorkbookSettings();
        ws.setGCDisabled(true);

        is=new FileInputStream(filename);
        wb = Workbook.getWorkbook(is);
        sheet = wb.getSheet(0);
        rows= sheet.getRows();
        for (int i = 0; i < sheet.getRows(); i++) {
            Cell[] row = sheet.getRow(i);
            reg.add(row[0].getContents());
            names.add(row[1].getContents());
        }
    }
    public List<String> getNames()
    {
        return  names;
    }
    public List<String> getReg()
    {
        return reg;
    }
    public int getRows()
    {
        return  rows;
    }
    //    public  void insert_into_datbase(database_handler_class dbHelper,String name,String auther_name,String details) throws IOException, BiffException {
//            dbHelper.insert_data(name,auther_name,details);
//
//    }
//    public void clear()
//    {
//        names.clear();
//        reg.clear();
//    }

}
