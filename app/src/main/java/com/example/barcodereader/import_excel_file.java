package com.example.barcodereader;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class import_excel_file {
    Workbook wb;
    WorkbookSettings ws;
    InputStream is;
    Sheet sheet;
    List<String> account_number;
    int rows,columns;
    Context context;
    String batch_name,feeder_code,division_code;
    int batch_no;
    public import_excel_file(Context context,String batch_name,String feeder_code,String division_code)
    {
        this.batch_name=batch_name;
        batch_no=Integer.parseInt(batch_name);
        this.context=context;
        this.feeder_code=feeder_code;
        this.division_code=division_code;
//        Toast.makeText(this.context, batch_no+"", Toast.LENGTH_SHORT).show();
    }
    public   void read_excel_file(String filename) throws IOException, BiffException {
        db_handler_acc_numbers db_account=new db_handler_acc_numbers(context,batch_name,division_code,feeder_code);
        ws = new WorkbookSettings();
        ws.setGCDisabled(true);
        is=new FileInputStream(filename);
        wb = Workbook.getWorkbook(is);
        sheet = wb.getSheet(--batch_no);
        rows= sheet.getRows();
        //   Toast.makeText(context,""+sheet.getColumns(),Toast.LENGTH_LONG).show();
        for (int i = 0; i < sheet.getRows(); i++) {
            Cell[] row = sheet.getRow(i);
            if (row[0].getContents()!="");
            db_account.insert(batch_name+division_code+row[0].getContents(),"null",0,0,null,
                    sheet.getColumns()>1?row[1].getContents():"N/A");
        }
        
    }

}
