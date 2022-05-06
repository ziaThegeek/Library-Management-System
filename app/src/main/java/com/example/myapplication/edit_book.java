package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.database_handler_class;

public class edit_book extends AppCompatActivity {
    EditText book_name,auther_name,edition,call_no,isbn_no,year,pages;
    String Call_no;
    Button update,cancel;
    database_handler_class database_handler_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);
        getSupportActionBar().setTitle("Edit  Book");
        book_name = findViewById(R.id.book_name);
        auther_name = findViewById(R.id.auther_name);
        edition = findViewById(R.id.qnty);
        call_no = findViewById(R.id.call_no);
        isbn_no = findViewById(R.id.isbn_no);
        year = findViewById(R.id.year);
        pages = findViewById(R.id.pages);
        update = findViewById(R.id.update_book);
        cancel = findViewById(R.id.cancel);
        Intent intent = getIntent();
        Call_no = intent.getStringExtra("call_no");
        database_handler_class = new database_handler_class(this);
        Cursor cursor = database_handler_class.search(Call_no);

        if (cursor.getCount() <= 0)
            call_no.setError("try another");
//                    Toast.makeText(books_details.this, "no result found", Toast.LENGTH_SHORT).show();
        else {
            cursor.moveToFirst();
            book_name.setText(cursor.getString(0));
            auther_name.setText(cursor.getString(1));
            isbn_no.setText(cursor.getString(3));
            year.setText(cursor.getString(4));
            pages.setText(cursor.getString(5));

//                    publisher.setText("mango");
//                    location.setText("mango");


            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!verify_book_name(book_name.getText().toString()))
                        book_name.setError("invalid name");
                    else if (!verify_Auhter_name(auther_name.getText().toString()))
                        auther_name.getText().toString();
                    else if (!verify_isbn_no(isbn_no.getText().toString()))
                        isbn_no.setError("invalid isbn no");

                    else if (!verify_year(year.getText().toString()))
                        year.setError("invalid year");
                    else if (pages.getText().toString().isEmpty())
                        pages.setError("invalid pages");
                    else {
                        database_handler_class database_handler_class = new database_handler_class(getApplicationContext());
database_handler_class.update_book(book_name.getText().toString(),auther_name.getText().toString(),"1",isbn_no.getText().toString(),year.getText().toString(),pages.getText().toString(),Call_no,1);
                        Toast.makeText(edit_book.this, "added successfully", Toast.LENGTH_SHORT).show();


                    }
                }
            });
cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        edit_book.super.onBackPressed();
    }
});

        }
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