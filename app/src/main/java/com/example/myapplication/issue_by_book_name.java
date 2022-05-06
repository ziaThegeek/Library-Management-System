package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Data.Issue_and_return_book;
import com.example.myapplication.Data.datbase_handler_students;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class issue_by_book_name extends AppCompatActivity {
    EditText name,contact,email;
    AutoCompleteTextView reg_no;
    Button search,select;
String Call_no;
    Cursor cursor;
    CheckBox send_sms;
sms_service sms_service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue_by_book_name);
        getSupportActionBar().setTitle("ISSUE BOOK");
        reg_no = findViewById(R.id.reg_no);
        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        email = findViewById(R.id.email);
        search = findViewById(R.id.search);
        select = findViewById(R.id.done);
Intent intent=getIntent();
Call_no=intent.getStringExtra("call_no");
        send_sms = findViewById(R.id.send_sms);
        datbase_handler_students dhs = new datbase_handler_students(this);
        mailing_service mailing_service=new mailing_service(this,name.getText().toString().trim(),reg_no.getText().toString());

//        cursor=dhs.Get_data();
//        List<String> sugest=new ArrayList<>();
//        while (cursor.moveToNext())
//sugest.add(cursor.getString(1));
        dhs.insert_into_list_view(dhs.Get_data());
        ArrayAdapter<String> arrayAdapte = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dhs.getReg());
        reg_no.setAdapter(arrayAdapte);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQLiteDatabase db = this.getReadableDatabase();
                cursor = dhs.search(reg_no.getText().toString().toUpperCase());
                if (cursor.getCount() <= 0) {
                    reg_no.setError("try another reg No");
                    select.setEnabled(false);
                } else {
                    cursor.moveToFirst();
                    name.setText(cursor.getString(0));
                    contact.setText("no data");
                    email.setText(reg_no.getText().toString().substring(0, 4) + reg_no.getText().toString().substring(5, 7).toLowerCase() + reg_no.getText().toString().substring(8, 11) + "@student.uet.edu.pk");
                    select.setEnabled(true);
                }
            }
        });
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Issue_and_return_book isue = new Issue_and_return_book(issue_by_book_name.this);
                Cursor cursor1 = isue.search(Call_no, reg_no.getText().toString().toUpperCase());
                if (cursor1.getCount() <= 0) {
                    ProgressDialog progressDialog = new ProgressDialog(issue_by_book_name.this);
                    progressDialog.setMessage("sending mail");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    boolean inserted = isue.insert(Call_no, reg_no.getText().toString(), "10-02-2021");
                    if (inserted)
                        Log.d("x", "inserted");
//                    Toast.makeText(books_details.this,"inserted",Toast.LENGTH_LONG).show();
                    else

                        Log.d("s", "not inserted");
                        sms_service sms_service = new sms_service(issue_by_book_name.this, mailing_service.get_messaage_text("issued to") + "\n\n\n\tNote:\tPlease take care of this book  and make sure to return in within time of (21 days) otherwise you will be charged Rs:10 per day in case of book lost you can face difficulty in your degree clarification" +
                            "\n\n\tRegards: InCharge library UET LHR Nwl Campus", "03414657341", issue_by_book_name.this);

                    if (send_sms.isChecked()) {
                        sms_service.set_permissions();
                    }
                    send_mail();

                    clear();

//                Toast.makeText(books_details.this, "issued successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    select.setEnabled(false);
                } else {
                    Toast.makeText(issue_by_book_name.this, "this book is already issued", Toast.LENGTH_SHORT).show();
                }

            }


        });
    }
    public void clear(){
        name.setText("");
        contact.setText("");
        email.setText("");
        reg_no.setText("");


//    name.setText("");
    }
        private void send_mail() {
            mailing_service mailing_service = new mailing_service(this, name.getText().toString().trim(), reg_no.getText().toString());
            mailing_service.setpermssions();
            mailing_service.setProps();
            mailing_service.set_authentication();
            mailing_service.sent_email("my name");
        }
}