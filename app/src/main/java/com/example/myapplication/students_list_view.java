package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class students_list_view extends ArrayAdapter {
    List<String> name,regitration_NO,contact,email;
    Activity context;
    TextView name_text,auther_text,isbn_NO_text;
    public students_list_view( Activity context,  List <String>  name, List <String>  registration_no) {
        super(context, R.layout.list_item,name);
        this.name = name;
        this.regitration_NO = registration_no;
//        this.contact = contact;
//        this.email=email;
        this.context = context;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View item_view=layoutInflater.inflate(R.layout.list_item,null,true);
        name_text=item_view.findViewById(R.id.name);
        auther_text=item_view.findViewById(R.id.authar);
//        isbn_NO_text=item_view.findViewById(R.id.details);
               name_text.setText(name.get(position));
//               auther_text.setText(regitration_NO.get(position));
//        isbn_NO_text.setText(isbn_NO.get(position));

        return  item_view;
    }

    public List<String> getName() {
        return name;
    }

    public List<String> getRegitration_NO() {
        return regitration_NO;
    }

    public List<String> getContact() {
        return contact;
    }

    public List<String> getEmail() {
        return email;
    }
}
