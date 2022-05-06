package com.example.myapplication;
import com.example.myapplication.Data.menu_activity;
import com.example.myapplication.Data.seach_student;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Params.params;
import com.example.myapplication.Data.datbase_handler_students;

import java.util.ArrayList;
import java.util.List;

public class student_details extends AppCompatActivity {
EditText name,contact,email;
AutoCompleteTextView reg_no;
Button search,select;

    Cursor cursor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ISSUE BOOKS");
        setContentView(R.layout.activity_student_details);
reg_no=findViewById(R.id.registration_no);
name=findViewById(R.id.name);
contact=findViewById(R.id.contact);
email=findViewById(R.id.email);
search=findViewById(R.id.search);
select=findViewById(R.id.select_button);


datbase_handler_students dhs=new datbase_handler_students(this);
//        cursor=dhs.Get_data();
//        List<String> sugest=new ArrayList<>();
//        while (cursor.moveToNext())
//sugest.add(cursor.getString(1));
        dhs.insert_into_list_view(dhs.Get_data());
        ArrayAdapter<String> arrayAdapte=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dhs.getReg());
        reg_no.setAdapter(arrayAdapte);
search.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
       // SQLiteDatabase db = this.getReadableDatabase();
         cursor=dhs.search(reg_no.getText().toString().toUpperCase());
        if (cursor.getCount()<=0) {

reg_no.setError("try another reg No");
select.setEnabled(false);
        }
        else {
            cursor.moveToFirst();
            name.setText(cursor.getString(0));
            contact.setText("no data");
            email.setText(reg_no.getText().toString().substring(0,4)+reg_no.getText().toString().substring(5,7).toLowerCase()+reg_no.getText().toString().substring(8,11)+"@student.uet.edu.pk");
            select.setEnabled(true);
        }
    }
});
select.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(student_details.this, books_details.class);
        intent.putExtra("email",reg_no.getText().toString().toUpperCase());
        startActivity(intent);
    }
});

    }

}