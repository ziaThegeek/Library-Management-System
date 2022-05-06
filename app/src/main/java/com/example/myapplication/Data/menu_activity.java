package com.example.myapplication.Data;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.Returned_books;
import com.example.myapplication.Student_file;
import com.example.myapplication.issued_books;
import com.example.myapplication.student_details;

public class menu_activity extends AppCompatActivity {
CardView all_books,all_students,asign_books,return_books,issued_books,returned_books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    all_books=findViewById(R.id.all_books);
    all_students=findViewById(R.id.all_students);
    asign_books=findViewById(R.id.asign_books);
    return_books=findViewById(R.id.return_books);
    issued_books=findViewById(R.id.assigned_books);
    returned_books=findViewById(R.id.books_returned);
database_handler_class database_handler_class=new database_handler_class(this);
database_handler_class.Get_data();
    all_books.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(menu_activity.this, MainActivity.class);
            startActivity(intent);


        }
    });
    all_students.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(menu_activity.this, Student_file.class);
            startActivity(intent);

        }
    });
    asign_books.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(menu_activity.this, student_details.class);
            startActivity(intent);
        }
    });
    return_books.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(menu_activity.this, return_book.class);
                    startActivity(intent);
                }
            }
    );
    issued_books.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(menu_activity.this,issued_books.class);
            startActivity(intent);
        }
    });
    returned_books.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(menu_activity.this, Returned_books.class);
            startActivity(intent);
        }
    });
    }
}