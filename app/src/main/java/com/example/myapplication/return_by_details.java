package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.Issue_and_return_book;
import com.example.myapplication.Data.database_handler_class;
import com.example.myapplication.Data.datbase_handler_students;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import com.example.myapplication.Data.books_returned;
public class return_by_details extends AppCompatActivity {
TextView book_name,call_no,Student_name,reg_no,issued_date,returned_date,charges;
Button return_book;
Issue_and_return_book isue;
database_handler_class books_details;
datbase_handler_students student_details;
int days;
 books_returned returned_books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_by_details);
        return_book=findViewById(R.id.return_book);
        book_name=findViewById(R.id.book_name);
        call_no=findViewById(R.id.call_no);
        Student_name=findViewById(R.id.student_name);
        reg_no=findViewById(R.id.reg_no);
        issued_date=findViewById(R.id.date_issued);
        returned_date=findViewById(R.id.date_returned);
        charges=findViewById(R.id.charges);
returned_books=new books_returned(this);

        Intent intent=getIntent();
        call_no.setText(intent.getStringExtra("call_no"));
        reg_no.setText(intent.getStringExtra("reg_no"));
        issued_date.setText(intent.getStringExtra("date_issued"));



        isue=new Issue_and_return_book(this);
        books_details=new database_handler_class(this);
        student_details=new datbase_handler_students(this);

       Cursor cursor =books_details.search(call_no.getText().toString());

        if (cursor.getCount()<=0)
            call_no.setError("try another");
//                    Toast.makeText(books_details.this, "no result found", Toast.LENGTH_SHORT).show();
        else {
            cursor.moveToFirst();
            book_name.setText(cursor.getString(0));
        }
        Date date= null;

        try {
            date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).parse(issued_date.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfMonth = c.get(Calendar.DAY_OF_MONTH);
        int monthOfYear=c.get(Calendar.MONTH)+1;
        int year=c.get(Calendar.YEAR);
        Date date1= null;
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
        returned_date.setText(new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(date1));
        charges.setText(days*10+"");


//                    publisher.setText("mango");
//                    location.setText("mango");



           cursor= student_details.search(reg_no.getText().toString());
        if (cursor.getCount()<=0)
            call_no.setError("try another");
//                    Toast.makeText(books_details.this, "no result found", Toast.LENGTH_SHORT).show();
        else {
            cursor.moveToFirst();
            Student_name.setText(cursor.getString(0));
        }

return_book.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        isue.delete(reg_no.getText().toString().toUpperCase(),call_no.getText().toString());
        returned_books.insert(call_no.getText().toString(),reg_no.getText().toString().toUpperCase(),
                days*10,issued_date.getText().toString(),returned_date.getText().toString());

        Toast.makeText(return_by_details.this, "returned successfully", Toast.LENGTH_SHORT).show();
        return_by_details.super.onBackPressed();
    }
});

    }
}