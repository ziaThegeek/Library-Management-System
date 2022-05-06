package com.example.myapplication;
import  com.example.myapplication.Data.Issue_and_return_book;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.Data.database_handler_class;


import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import com.example.myapplication.Data.datbase_handler_students;
public class issued_books extends AppCompatActivity  {
ListView listView;
List<String> names,reg,date_issued,call_no,intigrated_string,student_name;
    Button export_excel,save_button,export_pdf;
    EditText filename;
    RelativeLayout file_save_controls;
        LinearLayout bottom_view;
    WritableWorkbook workbook;
    issued_books_adapter issued_books_adapter;
    Issue_and_return_book issue_and_return_book;
    datbase_handler_students dhs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_issued_books);
        getSupportActionBar().setTitle("ISSUED BOOKS");
        export_excel=findViewById(R.id.export_excel);
        export_pdf=findViewById(R.id.export_pdf);
        listView=findViewById(R.id.list_view_issued_books);
        filename=findViewById(R.id.file_name);
        save_button=findViewById(R.id.save_button);
        file_save_controls=findViewById(R.id.save_file_controls);
        bottom_view=findViewById(R.id.bottom_view);

        View view=bottom_view;

        names=new ArrayList<>();
        reg=new ArrayList<>();
        date_issued=new ArrayList<>();
        call_no=new ArrayList<>();
        intigrated_string=new ArrayList<>();
        student_name=new ArrayList<>();
    issue_and_return_book=new Issue_and_return_book(this);
        database_handler_class database_handler_class=new database_handler_class(this);
      dhs=new datbase_handler_students(this);
        Cursor data=issue_and_return_book.Get_data();

        Cursor name;
        while (data.moveToNext())
        {
            name=database_handler_class.search(data.getString(0));

            if (name.getCount()<=0) {
                Toast.makeText(this, "no record found", Toast.LENGTH_SHORT).show();
            }
            else {
                name.moveToFirst();
                names.add(name.getString(0));
                call_no.add(data.getString(0));
                reg.add(data.getString(1));
                date_issued.add(data.getString(2));
                intigrated_string.add(data.getString(0)+"\n"+name.getString(0)+"\n"+data.getString(1)+"\n"+data.getString(2));
            }
        }
        data.close();
        for (int i=0;i<reg.size();i++)
        {
            data=dhs.search(reg.get(i));
            if (data.getCount()>0)
            {
                data.moveToFirst();
                student_name.add(data.getString(0));
            }
        }

        populate_list_view(issue_and_return_book);
//        try {
//
//            warnings();
//        } catch (AddressException e) {
//            e.printStackTrace();
//        }
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
                    //your code
                    createExcelSheet();

                }

            }
        });
export_pdf.setOnClickListener(new View.OnClickListener() {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        else
        {
            //your code
            try {
                create_pdf_sheet();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
});
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(issued_books.this)
                        .setTitle("DETAILS")
                        .setMessage("Name:\n \t"+names.get(position)+"\n"+"Call No:\n\t"
                                +call_no.get(position)+"\nReg No:\n\t"+
                                reg.get(position)+"\nIssued Date:\n\t"+
                                date_issued.get(position))

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
                issued_books.this.issued_books_adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                issued_books.this.issued_books_adapter.getFilter().filter(newText);
                //                Log.e("Main"," data search"+newText);






                return true;
            }

        });


        return true;

    }

    public void populate_list_view( Issue_and_return_book isu1)
    {
        issued_books_adapter=new issued_books_adapter(this,names,reg,call_no,date_issued);
        listView.setAdapter(issued_books_adapter);
        listView.setEmptyView(findViewById(R.id.error_view));
    }
    void createExcelSheet() {
        File futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getPath(), "/Library management system/");
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.mkdirs();
        }
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String csvFile = "issued_books.xls";
//      File  sd = Environment.getExternalStorageDirectory();
//        File directory = new File(sd.getAbsolutePath());
        File    file = new File(futureStudioIconFile, csvFile);
        WorkboomkSettings wbSettings = new WorkbookSettings();
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
            sheet.addCell(new Label(2, 0, "reg No"));
            sheet.addCell(new Label(3, 0, "issued Date"));

            for (int i = 0; i < names.size(); i++) {
                sheet.addCell(new Label(0, i+1, names.get(i)));
                sheet.addCell(new Label(1, i+1, call_no.get(i)));
                sheet.addCell(new Label(2, i+1, reg.get(i)));
                sheet.addCell(new Label(3, i+1, date_issued.get(i)));
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
    public  void warnings() throws AddressException {
        int days=0;
        Cursor cursor = null;
        mailing_service mailing_service=new mailing_service(this,names.get(0),reg.get(0).toString());
        mailing_service.setpermssions();
        mailing_service.setProps();
        mailing_service.set_authentication();
        String regs = null;
        for (int i=0;i<reg.size();i++) {
            Date date = null;
            try {
                date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(date_issued.get(i));

            } catch (ParseException e) {
                e.printStackTrace();
            }
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
            int monthOfYear = c.get(Calendar.MONTH) + 1;
            int year = c.get(Calendar.YEAR);
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c = Calendar.getInstance();
            c.setTime(date1);
            days = c.get(Calendar.DAY_OF_MONTH) - dayOfMonth;
            days += (c.get(Calendar.MONTH) + 1 - monthOfYear) * 30;
            days += (c.get(Calendar.YEAR) - year) * 365;
            if (days >= 21)
                regs=regs+mailing_service.get_email_addres(reg.get(i))+",";
        }
        Toast.makeText(this, regs.toString(), Toast.LENGTH_SHORT).show();
mailing_service.sent_email("nnnnn",regs);


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
        for (int i=0;i<names.size();i++)
        {
            canvas.drawText(""+(i+1),40,150+i*30,paint);
            canvas.drawText(student_name.get(i),220,150+i*30,paint);
            canvas.drawText(reg.get(i),420,150+i*30,paint);
            canvas.drawText(call_no.get(i),620,150+i*30,paint);
            canvas.drawText(date_issued.get(i),820,150+i*30,paint);
            canvas.drawText(date_issued.get(i),1020,150+i*30,paint);
        }

        pdfDocument.finishPage(page1);



        File futureStudioIconFile = new File(Environment.getExternalStorageDirectory().getPath(), "/Library management system/");
        if (!futureStudioIconFile.exists()) {
            futureStudioIconFile.mkdirs();
        }
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String csvFile = "issued_books.pdf";
//      File  sd = Environment.getExternalStorageDirectory();
//        File directory = new File(sd.getAbsolutePath());
        File    file = new File(futureStudioIconFile, csvFile);
        pdfDocument.writeTo(new FileOutputStream(file));
        Toast.makeText(issued_books.this, "file saved to "+futureStudioIconFile, Toast.LENGTH_SHORT).show();
    }
}