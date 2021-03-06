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
    int rows;
    Context context;
    String batch_name;
    int batch_no;
    public import_excel_file(Context context,String batch_name)
    {
        this.batch_name=batch_name;
        batch_no=Integer.parseInt(batch_name);
        this.context=context;
        Toast.makeText(this.context, batch_no+"", Toast.LENGTH_SHORT).show();
    }
    public   void read_excel_file(String filename) throws IOException, BiffException {
        db_handler_acc_numbers db_account=new db_handler_acc_numbers(context);
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
            db_account.insert(row[0].getContents(),"null","null","null",batch_name);

        }
    }

}
