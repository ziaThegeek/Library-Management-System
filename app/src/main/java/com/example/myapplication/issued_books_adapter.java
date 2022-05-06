package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.myapplication.Data.return_book;
import com.example.myapplication.Data.datbase_handler_students;
public class issued_books_adapter extends ArrayAdapter {
    List<String> book_name,student_name,reg_no,call_no,issued_date,charges;
    List<Integer> id;
    Activity context;
    TextView book_name_text,student_name_text,call_no_text,reg_no_text_text;
LinearLayout return_book,send_mail,send_sms;
datbase_handler_students datbaseHandlerStudents;


    public issued_books_adapter( Activity context,  List <String>  book_name, List <String>  reg_no,List <String>  call_no,List <String>  issued_date) {
        super(context, R.layout.issued_list,book_name);
        this.context=context;
        this.book_name=book_name;
        this.reg_no=reg_no;
        this.call_no=call_no;
        this.student_name=student_name;
        this.issued_date=issued_date;
        this.charges=charges;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View item_view=layoutInflater.inflate(R.layout.issued_list,null,true);
      book_name_text=item_view.findViewById(R.id.book_title);
      student_name_text=item_view.findViewById(R.id.student_name);
      call_no_text=item_view.findViewById(R.id.call_no);
      reg_no_text_text=item_view.findViewById(R.id.reg_no);


      return_book=item_view.findViewById(R.id.return_book);
      send_mail=item_view.findViewById(R.id.send_mail);
      send_sms=item_view.findViewById(R.id.send_sms);

datbaseHandlerStudents=new datbase_handler_students(context);
        Cursor cursor=datbaseHandlerStudents.search(reg_no.get(position));
        if (cursor.getCount()<=0)
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
      else
        {
            cursor.moveToFirst();
            student_name_text.setText(cursor.getString(0));
        }
      book_name_text.setText(book_name.get(position));
      call_no_text.setText(call_no.get(position));
      reg_no_text_text.setText(reg_no.get(position));



      return_book.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent=new Intent(context, return_by_details.class);
              intent.putExtra("call_no",call_no.get(position));
              intent.putExtra("reg_no",reg_no.get(position));
              intent.putExtra("date_issued",issued_date.get(position));
              context.startActivity(intent);


          }
      });


      send_mail.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              mailing_service mailing_service=new mailing_service(context,book_name.get(position),reg_no.get(position));
              mailing_service.setpermssions();
              mailing_service.setProps();
              mailing_service.set_authentication();
              mailing_service.sent_email("\tDear "+reg_no.get(position)+"," +
                      "\nYou Have Borrowed Following Book from library Note \n"
                      +book_name.get(position)+" On "+issued_date.get(position)+"\nNote   make sure to return it on time to avoid extra charging");


          }
      });
send_sms.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        sms_service sms_service=new sms_service(context,"\tDear "+reg_no.get(position)+"," +
                "\nYou Have Borrowed Following Book from library Note \n"
                +book_name.get(position)+" On "+issued_date.get(position)+"\nNote   make sure to return it on time to avoid extra charging","03414657341",context);
            sms_service.set_permissions();
        }

});


        return  item_view;
    }

}
