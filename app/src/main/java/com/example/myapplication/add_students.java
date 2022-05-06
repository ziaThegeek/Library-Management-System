package com.example.myapplication;
import  com.example.myapplication.Data.datbase_handler_students;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import jxl.read.biff.BiffException;

public class add_students extends AppCompatActivity {
EditText name,reg,contact,email;
Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Add Students");
        setContentView(R.layout.activity_add_students);
        name=findViewById(R.id.name);
        reg=findViewById(R.id.reg);
  contact=findViewById(R.id.contact);
  email=findViewById(R.id.email);
  add=findViewById(R.id.insert);
  add.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

          datbase_handler_students datbase_handler_students=new datbase_handler_students(getApplicationContext());
          try {
              if (!verifyName(name.getText().toString()))
                  name.setError("invalid Name");

              else if (!verifyReg(reg.getText().toString()))
                  reg.setError("invalid reg");
              else if (!verifyContact(contact.getText().toString()))
                  contact.setError("invalid contact");
              else if (!verifyemail(email.getText().toString()))
                  email.setError("invalid email");
else
              datbase_handler_students.add_one_student(name.getText().toString().toUpperCase(),reg.getText().toString().toUpperCase());
        } catch (IOException e) {
              e.printStackTrace();
          } catch (BiffException e) {
              e.printStackTrace();
          }

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
            Toast.makeText(add_students.this, "Please enter " + "\n" + " valid phone number", Toast.LENGTH_SHORT).show();
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