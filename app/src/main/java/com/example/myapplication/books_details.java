package com.example.myapplication;
import com.example.myapplication.Data.datbase_handler_students;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Data.Issue_and_return_book;
import com.example.myapplication.Data.database_handler_class;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import com.sun.mail.handlers.handler_base;
public class books_details extends AppCompatActivity {
EditText name,auther_name,edition_no,ISBN_no,year,pages,quantity,publisher,location;
TextView student_name;
AutoCompleteTextView call_no;
Button add,done,select,date_picker;
List<String> names;
String email,reg;
Intent intent;
    CheckBox send_msg;
public static final String username="usama.arif.756@gmail.com";
    public static final String password="Proudtobeapakistani";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("ISSUE BOOKS");
        names=new ArrayList<>();
        setContentView(R.layout.activity_books_details);
        send_msg=(CheckBox) findViewById(R.id.check_box);
        name=findViewById(R.id.name);
        auther_name=findViewById(R.id.authar);
        edition_no=findViewById(R.id.edition);
        ISBN_no=findViewById(R.id.ISBN);
        intent=getIntent();
        year=findViewById(R.id.year);
        pages=findViewById(R.id.pages);
        quantity=findViewById(R.id.quantity);
//        location=findViewById(R.id.location);
        call_no=findViewById(R.id.call_no);
        select=findViewById(R.id.select);
        add=findViewById(R.id.add);
        student_name=findViewById(R.id.student_name_text);
        done=findViewById(R.id.done);

        mailing_service mailing_service=new mailing_service(this,name.getText().toString().trim(),reg);
        email=new String();
        String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        reg=intent.getStringExtra("email");
        email=reg;

        AddConstantTextInEditText(call_no,"`");
        database_handler_class dhs=new database_handler_class(this);
        dhs.insert_into_list_view(dhs.Get_data());
        ArrayAdapter<String> arrayAdapte=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dhs.getCall_no());
        call_no.setAdapter(arrayAdapte);
        datbase_handler_students datbase_handler_students=new datbase_handler_students(this);

        Cursor cursor=datbase_handler_students.search(reg);
        if (cursor.getCount()<=0) {

                    }
        else {
            cursor.moveToFirst();
            student_name.setText(cursor.getString(0));
        }
//        Toast.makeText(this,reg.substring(0,4)+reg.substring(5,7).toLowerCase()+reg.substring(8,11)+"@student.uet.edu.pk",Toast.LENGTH_LONG).show();
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//        email=email.substring(0,4)+email.substring(5,7).toLowerCase()+email.substring(8,11)+"@student.uet.edu.pk";


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SQLiteDatabase db = this.getReadableDatabase();
                Cursor cursor=dhs.search(call_no.getText().toString());
                if (cursor.getCount()<=0)
                    call_no.setError("try another");
//                    Toast.makeText(books_details.this, "no result found", Toast.LENGTH_SHORT).show();
                else {
                    cursor.moveToFirst();
                    name.setText(cursor.getString(0));
                    auther_name.setText(cursor.getString(1));
                    edition_no.setText(cursor.getString(2));
                    ISBN_no.setText(cursor.getString(3));
                    year.setText(cursor.getString(4));
                    pages.setText(cursor.getString(5));
                    quantity.setText(""+cursor.getInt(7));
//                    publisher.setText("mango");
//                    location.setText("mango");

                    done.setEnabled(true);

                }
            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Issue_and_return_book isue=new Issue_and_return_book(books_details.this);
                Cursor cursor1=isue.search(call_no.getText().toString(),reg);
                if (cursor1.getCount()<=0) {
                    ProgressDialog progressDialog=new ProgressDialog(books_details.this);
                    progressDialog.setMessage("sending mail");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    String date = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    boolean inserted = isue.insert(call_no.getText().toString(), reg, "10-02-2021");
                    if (inserted)
                        Log.d("x", "inserted");
//                    Toast.makeText(books_details.this,"inserted",Toast.LENGTH_LONG).show();
                    else

                        Log.d("s", "not inserted");
                    sms_service sms_service = new sms_service(books_details.this,mailing_service.get_messaage_text("issued to")+"\n\n\n\tNote:\tPlease take care of this book  and make sure to return in within time of (21 days) otherwise you will be charged Rs:10 per day in case of book lost you can face difficulty in your degree clarification" +
                            "\n\n\tRegards: InCharge library UET LHR Nwl Campus" , "03414657341", books_details.this);

                    if (send_msg.isChecked()) {
                        sms_service.set_permissions();
                    }
                    send_mail();

//    sms_service sms_service = new sms_service(books_details.this, "xyz", "03414657341",books_details.this);



                    clear();

//                Toast.makeText(books_details.this, "issued successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    done.setEnabled(false);
                }
                else {
                    Toast.makeText(books_details.this, "this book is already issued", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
    private void send_mail() {
    mailing_service mailing_service=new mailing_service(this,name.getText().toString().trim(),reg);
    mailing_service.setpermssions();
    mailing_service.setProps();
    mailing_service.set_authentication();
    mailing_service.sent_email("assigned to ");
//        final String username="tayyab222gb@gmail.com";
//        final String password="tayyabmian7890";
//     //   String messagetosend=_txtmessage.getText().toString();
//
//        Properties props = new Properties();
//        //    props.setProperty("mail.transport.protocol", "smtp");
//        // props.setProperty("mail.host", mailhost);
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.starttls.enable","true");
//        props.put("mail.smtp.host","smtp.gmail.com");
//        Session session=Session.getInstance(props,new javax.mail.Authenticator(){
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username,password);
//            }
//        });
//        try {
//            Message message=new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            Toast.makeText(getApplicationContext(),"2018cs531@student.uet.edu.pk",Toast.LENGTH_LONG).show();
//            message.setRecipients(Message.RecipientType.TO,InternetAddress.parse("2018cs531@student.uet.edu.pk"));
//            message.setSubject("Sending Email to Student");
//            message.setText(name.getText().toString()+"is assigned to "+reg);
//            Transport.send(message);
//            Toast.makeText(getApplicationContext(),"Email send Successfully",Toast.LENGTH_LONG).show();
//        }catch (MessagingException e){
//            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
//            //  Toast.makeText(getApplicationContext(),_txtemail.getText().toString(),Toast.LENGTH_LONG).show();
//
//
//        }
        }





    public void clear(){
        name.setText("");
    auther_name.setText("");
    edition_no.setText("");
    ISBN_no.setText("");
    year.setText("");
    pages.setText("");
    quantity.setText("");
    call_no.setText("");
//    name.setText("");
}
    private String getmail_text() {
        return "Dear  Student you have lent following books"+names+"you are directed to take care of them and return on time";
    }
    public void sendEmail(Session session,Message message) throws AddressException, MessagingException {

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", username, password);
        Log.i("GMail", "allrecipients: " + message.getAllRecipients());
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
        Log.i("GMail", "Email sent successfully.");
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public static void AddConstantTextInEditText(EditText edt, String text) {

        edt.setText(text);
        Selection.setSelection(edt.getText(), edt.getText().length());

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().startsWith(text)){
                    edt.setText(text);
                    Selection.setSelection(edt.getText(), edt.getText().length());
                }
            }
        });
    }

}

