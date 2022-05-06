package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
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
import com.example.myapplication.Data.datbase_handler_students;

public class returned_books_adapter extends ArrayAdapter {
    List<String> book_name,student_name,reg_no,call_no,returned_date;
    List<Integer> charges;
    List<Integer> id;
    Activity context;
    TextView book_name_text,student_name_text,call_no_text,reg_no_text_text,returned_date_text,charges_text;
    LinearLayout delete;
datbase_handler_students datbaseHandlerStudents;
    public returned_books_adapter( Activity context,  List <String>  book_name, List <String>  reg_no,List <String>  call_no,List <String>  returned_date,List<Integer> charges) {
        super(context, R.layout.issued_list,book_name);
        this.context=context;
        this.book_name=book_name;
        this.reg_no=reg_no;
        this.call_no=call_no;
        this.student_name=student_name;
        this.returned_date=returned_date;
        this.charges=charges;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View item_view=layoutInflater.inflate(R.layout.returned_list,null,true);
        book_name_text=item_view.findViewById(R.id.book_title);
        student_name_text=item_view.findViewById(R.id.student_name);
        call_no_text=item_view.findViewById(R.id.call_no);
        reg_no_text_text=item_view.findViewById(R.id.reg_no);
        returned_date_text=item_view.findViewById(R.id.date_returned);
        charges_text=item_view.findViewById(R.id.charges);
       delete=item_view.findViewById(R.id.delete);
        book_name_text.setText(book_name.get(position));
        student_name_text.setText("not set");
        call_no_text.setText(call_no.get(position));
        reg_no_text_text.setText(reg_no.get(position));
        returned_date_text.setText(returned_date.get(position));
        charges_text.setText("Rs. "+charges.get(position));
datbaseHandlerStudents=new datbase_handler_students(context);
        Cursor cursor=datbaseHandlerStudents.search(reg_no.get(position));
        if (cursor.getCount()<=0)
            Toast.makeText(context, "no record found", Toast.LENGTH_SHORT).show();
        else
        {
            cursor.moveToFirst();
            student_name_text.setText(cursor.getString(0));
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_name.remove(position);
                notifyDataSetChanged();
            }
        });

        return  item_view;
    }

}
