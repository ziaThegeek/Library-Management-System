package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.datbase_handler_students;

import java.io.IOException;

import jxl.read.biff.BiffException;
import  com.example.myapplication.Data.database_handler_class;

public class add_books extends AppCompatActivity {
EditText book_name,auther_name,edition,call_no,isbn_no,year,pages;
Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Books");
        setContentView(R.layout.activity_add_books);
        book_name=findViewById(R.id.book_name);
        auther_name=findViewById(R.id.auther_name);
        edition=findViewById(R.id.qnty);
        call_no=findViewById(R.id.call_no);
        isbn_no=findViewById(R.id.isbn_no);
        year=findViewById(R.id.year);
        pages=findViewById(R.id.pages);
        add=findViewById(R.id.insert);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(!verify_book_name(book_name.getText().toString()))
    book_name.setError("invalid name");
else if(!verify_Auhter_name(auther_name.getText().toString()))
auther_name.getText().toString();
else if(!verify_call_no(call_no.getText().toString()))
    call_no.setError("invalid call no");
else if(!verify_isbn_no(isbn_no.getText().toString()))
    isbn_no.setError("invalid isbn no");
else if (edition.getText().toString().isEmpty())
    edition.setError("invalid edition");
else if (!verify_year(year.getText().toString()))
    year.setError("invalid year");
else if (pages.getText().toString().isEmpty())
    pages.setError("invalid pages");
else {

    database_handler_class database_handler_class = new database_handler_class(getApplicationContext());
    database_handler_class.add_one_book(book_name.getText().toString(), auther_name.getText().toString(), edition.getText().toString(), isbn_no.getText().toString(), year.getText().toString(), pages.getText().toString(), call_no.getText().toString(),Integer.parseInt(edition.getText().toString()) );
    Toast.makeText(add_books.this, "added successfully", Toast.LENGTH_SHORT).show();

}
            }
        });
    }
    boolean verify_book_name(String book_name)
    {
        if (book_name.isEmpty()) {
            return false;
        }
        else if(!book_name.matches("^[a-zA-Z\\ ]+$")){
            return false;
        }
        else

            return true;

    }
    boolean verify_Auhter_name(String auther_name){
        if (auther_name.isEmpty()) {
            return false;
        }
        else if(!auther_name.matches("^[a-zA-Z\\ ]+$")){
            return false;
        }
        else

            return true;
    }

    boolean verify_call_no(String call_no){
        if (call_no.isEmpty()||!call_no.matches("^`[0-9]+$"))
            return false;
        else
        return true;
    }

    boolean verify_year(String value ){
        if (value.isEmpty()||!value.matches("^(19|20)\\d{2}+$"))
            return false;
        else
            return true;
    }
    boolean verify_isbn_no(String value ){
        if (value.isEmpty()||!value.matches("^[0-9\\-]+$"))
            return false;
        else
            return true;

    }

}