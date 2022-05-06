package com.example.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Data.database_handler_class;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.myapplication.Data.datbase_handler_students;
public class listview_adapter_students extends ArrayAdapter {
    List<String> name,reg_no,contact_no;
    List<Integer> id;
    Activity context;
    TextView name_text,reg_no_text,email_text,contact_no_text;
LinearLayout edit,delete,issue;
datbase_handler_students datbaseHandlerStudents;

    public listview_adapter_students( Activity context,  List <String>  name, List <String>  reg_no) {
        super(context, R.layout.list_item,name);
        this.context=context;
        this.name = name;
       this.reg_no=reg_no;
       this.contact_no=contact_no;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View item_view=layoutInflater.inflate(R.layout.student_list,null,true);
        name_text=item_view.findViewById(R.id.name);
        reg_no_text=item_view.findViewById(R.id.reg_no);
        email_text=item_view.findViewById(R.id.email);
        contact_no_text=item_view.findViewById(R.id.contact);
        name_text.setText(name.get(position));
        reg_no_text.setText(reg_no.get(position));
        email_text.setText(reg_no_text.getText().toString().substring(0,4)+reg_no_text.getText().toString().substring(5,7).toLowerCase()+reg_no_text.getText().toString().substring(8,11)+"@student.uet.edu.pk");
        contact_no_text.setText("not provided");


        delete=item_view.findViewById(R.id.delete);
        edit=item_view.findViewById(R.id.edit);
        issue=item_view.findViewById(R.id.issue_book);



        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,edit_student.class);
                intent.putExtra("reg_no",reg_no.get(position));
                context.startActivity(intent);
            }
        });


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")

                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // Continue with delete operation
                                datbaseHandlerStudents=new datbase_handler_students(context);
                                datbaseHandlerStudents.deleteTitle(reg_no.get(position));


                            }
                        })

                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });


        issue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,books_details.class);
                intent.putExtra("email",reg_no.get(position));
                context.startActivity(intent);
            }
        });

        return  item_view;
    }

}
