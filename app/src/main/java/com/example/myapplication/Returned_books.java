package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.Issue_and_return_book;
import com.example.myapplication.Data.books_returned;
import com.example.myapplication.Data.database_handler_class;
import com.example.myapplication.Data.books_returned;
import com.example.myapplication.Params.params;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.example.myapplication.Data.datbase_handler_students;
public class Returned_books extends AppCompatActivity {
ListView listView;
List<String> names,dates_issued,dates_returned,reg_no,call_no,intigrated_string,student_name;
List<Integer> charges;
Button export_excel,export_pdf;
    WritableWorkbook workbook;
    returned_books_adapter returned_books_adapter;
datbase_handler_students dhs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_returned_books);
        getSupportActionBar().setTitle("RETURNED BOOKS");
        export_excel=findViewById(R.id.export_excel);
        export_pdf=findViewById(R.id.export_pdf);
        listView = findViewById(R.id.list_view1);
        names=new ArrayList<>();
        dates_issued=new ArrayList<>();
        dates_returned=new ArrayList<>();
        charges=new ArrayList<>();
        reg_no=new ArrayList<>();
        call_no=new ArrayList<>();
student_name=new ArrayList<>();
        intigrated_string=new ArrayList<>();
        books_returned books_returned=new books_returned(this);
        database_handler_class database_handler_class = new database_handler_class(this);
        dhs=new datbase_handler_students(this);
        Cursor data = books_returned.Get_data();
        Cursor name;
        while (data.moveToNext()) {
            name = database_handler_class.search(data.getString(0));
            if (name.getCount() <= 0) {

            } else {
                name.moveToFirst();
                names.add(name.getString(0));
                call_no.add(data.getString(0));
                reg_no.add(data.getString(1));
                dates_issued.add(data.getString(2));
                dates_returned.add(data.getString(3));
                charges.add(data.getInt(4));
                intigrated_string.add(data.getString(0)+"\n"+name.getString(0)+"\n"+data.getString(1)+"\n"+data.getString(2)+"\n"+data.getString(3)+"\n"+data.getString(4));
            }
        }
        data.close();
        for (int i=0;i<reg_no.size();i++)
        {
            data=dhs.search(reg_no.get(i));
            if (data.getCount()>0)
            {
                data.moveToFirst();
                student_name.add(data.getString(0));
            }
        }
        populate_list_view();
        export_excel.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                }
                else
                {
                    Toast.makeText(Returned_books.this, "XY", Toast.LENGTH_SHORT).show();
                    //your code
                    createExcelSheet();

                }

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(Returned_books.this)
                        .setTitle("DETAILS")
                        .setMessage("Name:\n \t"+names.get(position)+"\n"+"Call No:\n\t"
                                +call_no.get(position)+"\nReg No:\n\t"+
                                reg_no.get(position)+"\nIssued Date:\n\t"+
                                dates_issued.get(position)+"\nReturned Date:\n\t"+
                                dates_returned.get(position)+"\ncharges:\n\t"+
                                charges.get(position)+"")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                dialog.cancel();
                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.cancel, null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });
        export_pdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                try {
                    create_pdf_sheet();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
                SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.select).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Returned_books.this.returned_books_adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Returned_books.this.returned_books_adapter.getFilter().filter(newText);
                //                Log.e("Main"," data search"+newText);






                return true;
            }

        });


        return true;

    }

//
//        books_returned books_returned=new books_returned(this);
//        Cursor data=books_returned.Get_data();
//        while (data.moveToNext())
//        {
//            TableRow tbrow = new TableRow(this);
//            TextView sr = new TextView(this);
//            sr.setGravity(Gravity.CENTER);
//            sr.setText("0");
//            tbrow.addView(sr);
//
//
//            TextView name = new TextView(this);
//            name.setText(data.getString(0));
//            name.setGravity(Gravity.CENTER);
//            tbrow.addView(name);
//
//
//            TextView reg = new TextView(this);
//            reg.setText(data.getString(1));
//            reg.setGravity(Gravity.CENTER);
//            tbrow.addView(reg);
//
//            TextView issued_date = new TextView(this);
//            issued_date.setText(data.getString(2));
//            issued_date.setGravity(Gravity.CENTER);
//            tbrow.addView(issued_date);
//
//            TextView returned_date = new TextView(this);
//            returned_date.setText(data.getString(3));
//            returned_date.setGravity(Gravity.CENTER);
//            tbrow.addView(returned_date);
//
//            TextView charges = new TextView(this);
//            charges.setText(data.getString(4));
//            charges.setGravity(Gravity.CENTER);
//            tbrow.addView(charges);
//
//
//            tableLayout.addView(tbrow);
//        }

    public void populate_list_view()
    {

returned_books_adapter=new returned_books_adapter(this,names,reg_no,call_no,dates_returned,charges);
    listView.setAdapter(returned_books_adapter);
        listView.setEmptyView(findViewById(R.id.error_view));
    }
    void createExcelSheet() {
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        File futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getPath(), "/Library management system/");

        String csvFile = "returned_books"+date+".xls";
//      File  sd = Environment.getExternalStorageDirectory();
//        File directory = new File(sd.getAbsolutePath());
    File    file = new File(futureStudioIconFile, csvFile);
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setLocale(new Locale("en", "EN"));
        try {

            workbook = Workbook.createWorkbook(file, wbSettings);
            createFirstSheet();
//            createSecondSheet();
            //closing cursor
            workbook.write();
            workbook.close();
            Toast.makeText(this, "file saved to"+file, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void createFirstSheet() {
        try {
            WritableSheet sheet = workbook.createSheet("sheet1", 0);
            sheet.addCell(new Label(0, 0, "Book Name"));
            sheet.addCell(new Label(1, 0, "Call No"));
            sheet.addCell(new Label(2, 0, "Issued date"));
            sheet.addCell(new Label(3, 0, "Returned Date"));
            sheet.addCell(new Label(4, 0, "Charges"));

            for (int i = 0; i < names.size(); i++) {
                sheet.addCell(new Label(0, i+1, names.get(i)));
                sheet.addCell(new Label(1, i+1, call_no.get(i)));
                sheet.addCell(new Label(2, i+1, dates_issued.get(i)));
                sheet.addCell(new Label(3, i+1, dates_returned.get(i)));
                sheet.addCell(new Label(4, i+1, charges.get(i).toString()));
            }
//            List<Bean> listdata = new ArrayList<>();
//
//            listdata.add(new Bean("mr","firstName1","middleName1","lastName1"));
//            listdata.add(new Bean("mr","firstName1","middleName1","lastName1"));
//            listdata.add(new Bean("mr","firstName1","middleName1","lastName1"));
//            //Excel sheet name. 0 (number)represents first sheet
//            WritableSheet sheet = workbook.createSheet("sheet1", 0);
//            // column and row title
//            sheet.addCell(new Label(0, 0, "NameInitial"));
//            sheet.addCell(new Label(1, 0, "firstName"));
//            sheet.addCell(new Label(2, 0, "middleName"));
//            sheet.addCell(new Label(3, 0, "lastName"));
//
//            for (int i = 0; i < listdata.size(); i++) {
//                sheet.addCell(new Label(0, i + 1, listdata.get(i).getInitial()));
//                sheet.addCell(new Label(1, i + 1, listdata.get(i).getFirstName()));
//                sheet.addCell(new Label(2, i + 1, listdata.get(i).getMiddleName()));
//                sheet.addCell(new Label(3, i + 1, listdata.get(i).getLastName()));
//            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void create_pdf_sheet() throws IOException {

        int pageWidth=1200;
        PdfDocument pdfDocument=new PdfDocument();
        Paint paint=new Paint();
        Paint title=new Paint();
        PdfDocument.PageInfo mypageinfo=new PdfDocument.PageInfo.Builder(1200,2010,1).create();
        PdfDocument.Page page1=pdfDocument.startPage(mypageinfo);
        Canvas canvas=page1.getCanvas();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(20,100,pageWidth-20,130,paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("Sr.No",40,120,paint);
        canvas.drawText("Student Name",220,120,paint);
        canvas.drawText("Reg.No",420,120,paint);
        canvas.drawText("Book Issued",620,120,paint);
        canvas.drawText("Date Issued",820,120,paint);
        canvas.drawText("Due date",1020,120,paint);
        canvas.drawLine(200,100,200,130,paint);
        canvas.drawLine(400,100,400,130,paint);
        canvas.drawLine(600,100,600,130,paint);
        canvas.drawLine(800,100,800,130,paint);
        canvas.drawLine(1000,100,1000,130,paint);
        for (int i=0;i<student_name.size();i++)
        {
            canvas.drawText(""+(i+1),40,150+i*30,paint);
            canvas.drawText(student_name.get(i),220,150+i*30,paint);
            canvas.drawText(reg_no.get(i),420,150+i*30,paint);
            canvas.drawText(call_no.get(i),620,150+i*30,paint);
            canvas.drawText(dates_returned.get(i),820,150+i*30,paint);
            canvas.drawText(charges.get(i)+"",1020,150+i*30,paint);
        }

        pdfDocument.finishPage(page1);



        File futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getPath(), "/Library management system/");
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.mkdirs();
        }
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String csvFile = "returned_books.pdf";
//      File  sd = Environment.getExternalStorageDirectory();
//        File directory = new File(sd.getAbsolutePath());
        File    file = new File(futureStudioIconFile, csvFile);
        pdfDocument.writeTo(new FileOutputStream(file));
        Toast.makeText(Returned_books.this, "file saved to "+futureStudioIconFile, Toast.LENGTH_SHORT).show();

    }


}