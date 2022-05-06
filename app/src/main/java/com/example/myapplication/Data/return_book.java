package com.example.myapplication.Data;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.mailing_service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class return_book extends AppCompatActivity {
    EditText call_no,issued_date,returned_date,charges;
    AutoCompleteTextView reg_no;
    Button Select,Return;
    int days,months,years;
String issue_date,return_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_book);
        getSupportActionBar().setTitle("RETURN BOOKS");
        reg_no = findViewById(R.id.reg_no);
        call_no = findViewById(R.id.call_no);
        issued_date = findViewById(R.id.date_isued);
        Select = findViewById(R.id.select);
        Return = findViewById(R.id.return_book);
        returned_date=findViewById(R.id.returned_date);
        charges=findViewById(R.id.charges);
        String dateString;
        datbase_handler_students dhs=new datbase_handler_students(this);
        dhs.insert_into_list_view(dhs.Get_data());
        ArrayAdapter<String> arrayAdapte=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dhs.getReg());
        reg_no.setAdapter(arrayAdapte);
        Issue_and_return_book isue = new Issue_and_return_book(this);
books_returned returned_books=new books_returned(this);
        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor=isue.search(reg_no.getText().toString().toUpperCase());
                if (cursor.getCount()<=0){

                reg_no.setError("try another reg no");
                Toast.makeText(return_book.this,"no record found",Toast.LENGTH_SHORT).show();
                }
                else {
                    cursor.moveToFirst();
                    Date date= null;
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(cursor.getString(2));

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar c = Calendar.getInstance();
                    c.setTime(date);
                    int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
                    int monthOfYear=c.get(Calendar.MONTH)+1;
                    int year=c.get(Calendar.YEAR);
                    Date  date1= null;
                    try {
                        date1 = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    c = Calendar.getInstance();
                    c.setTime(date1);
                  days=c.get(Calendar.DAY_OF_MONTH)-dayOfMonth;
                  days+=(c.get(Calendar.MONTH)+1-monthOfYear)*30;
                  days+=(c.get(Calendar.YEAR)-year)*365;
                  if (days>=21)
                  days-=21;

                    issued_date.setText(cursor.getString(2));
                  returned_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date1));
                  charges.setText(days*10+"");
//issued_date.setText(date1.toString());
                    call_no.setText(cursor.getString(0));

                    Return.setEnabled(true);
                }
            }});
Return.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        ProgressDialog progressDialog=new ProgressDialog(return_book.this);
        progressDialog.setMessage("sending email");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
String name="";
        isue.delete(reg_no.getText().toString().toUpperCase(),call_no.getText().toString());
        String date= null;
        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new SimpleDateFormat("dd-MM-yyyy").parse(issued_date.getText().toString()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        returned_books.insert(call_no.getText().toString(),reg_no.getText().toString().toUpperCase(),
                days*10,date,
                new SimpleDateFormat("dd-MM-yyyy",Locale.getDefault()).format(new Date()));
        database_handler_class database_handler_class=new database_handler_class(return_book.this);
      Cursor cursor =database_handler_class.search(call_no.getText().toString());
        if (cursor.getCount()<=0)
            Toast.makeText(return_book.this, "no result found for "+reg_no, Toast.LENGTH_SHORT).show();
        else {
            cursor.moveToFirst();
//            call_no.setText(cursor.getString(0));
             name=cursor.getString(0);
        }
        mailing_service mailing_service=new mailing_service(return_book.this,name,reg_no.getText().toString());
        mailing_service.setpermssions();
        mailing_service.setProps();
        mailing_service.set_authentication();
        mailing_service.sent_email(" returned by ");
        progressDialog.dismiss();

        Return.setEnabled(false);
    }
});
        }
}

