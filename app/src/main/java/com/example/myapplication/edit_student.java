package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.datbase_handler_students;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class edit_student extends AppCompatActivity {
Button update,cancel;
EditText name,reg_no,email,contact;
datbase_handler_students datbaseHandlerStudents;
String reg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student);
        name = findViewById(R.id.name);
        reg_no = findViewById(R.id.reg_no);
        email = findViewById(R.id.email);
        contact = findViewById(R.id.contact);
        update = findViewById(R.id.update);
        cancel = findViewById(R.id.cancel);
        Intent intent = getIntent();
        reg = intent.getStringExtra("reg_no");
        datbaseHandlerStudents = new datbase_handler_students(this);
        Cursor cursor = datbaseHandlerStudents.search(reg);

        if (cursor.getCount() <= 0)
            Toast.makeText(edit_student.this, "no result found", Toast.LENGTH_SHORT).show();
        else {
            cursor.moveToFirst();
            name.setText(cursor.getString(0));
            email.setText(reg.substring(0, 4) + reg.substring(5, 7).toLowerCase() + reg.substring(8, 11) + "@student.uet.edu.pk");
            contact.setText("not provided");
            reg_no.setText(reg);

//                    publisher.setText("mango");
//                    location.setText("mango");


        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datbase_handler_students datbase_handler_students=new datbase_handler_students(getApplicationContext());
                if (!verifyName(name.getText().toString()))
                    name.setError("invalid Name");
                else if (!verifyReg(reg_no.getText().toString()))
                    reg_no.setError("invalid reg");
                else if (!verifyContact(contact.getText().toString()))
                    contact.setError("invalid contact");
                else if (!verifyemail(email.getText().toString()))
                    email.setError("invalid email");
                else
                    datbase_handler_students.update_student(name.getText().toString().toUpperCase(),reg_no.getText().toString());

            }

        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit_student.super.onBackPressed();
            }
        });
    }


    public boolean verifyName(String Name)
    {
        if (Name.isEmpty()) {
            return false;
        }
        else if(!Name.matches("^[a-zA-Z\\ ]+$")){
            return false;
        }
        else
            return true;
    }
    public boolean verifyReg(String reg){

        String regexStr="\\d{4}-[a-zA-Z]+-\\d{3}";

        return reg.matches(regexStr);

    }
    public boolean verifyContact(String contact){
        String regexStr = "^[+]?[0-9]{10,13}$";


        String number=contact;

        if(number.length()<10 || number.length()>13 || number.matches(regexStr)==false  ) {
            Toast.makeText(edit_student.this, "Please enter " + "\n" + " valid phone number", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
        // am_checked=0;

    }
    public boolean verifyemail(String email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());

    }

}