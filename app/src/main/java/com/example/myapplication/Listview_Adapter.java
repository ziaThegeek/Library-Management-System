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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Data.menu_activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.example.myapplication.Data.database_handler_class;

public class Listview_Adapter extends ArrayAdapter {
     List<String> name,authur,isbn_NO,call_no,year,page,publisher;
     List<Integer> id;
    Activity context;
    TextView name_text,auther_text,isbn_NO_text,call_no_text;
    LinearLayout edit_book,delete_book,issue_book;
database_handler_class database_handler_class;
    public Listview_Adapter( Activity context,  List <String>  name, List <String>  call_no, List<Integer> id,List<String> auther,List<String> isbn_no,List<String> year) {
        super(context, R.layout.list_item,name);
        this.name = name;
        this.authur = auther;
        this.isbn_NO = isbn_no;
        this.context = context;
        this.id=id;
        this.call_no=call_no;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        LayoutInflater layoutInflater=context.getLayoutInflater();
        View item_view=layoutInflater.inflate(R.layout.list_item,null,true);
        name_text=item_view.findViewById(R.id.book_title);
        auther_text=item_view.findViewById(R.id.book_auhter);
        isbn_NO_text=item_view.findViewById(R.id.isbn_title);
        call_no_text=item_view.findViewById(R.id.call_no_title);
        edit_book=item_view.findViewById(R.id.edit_book);
delete_book=item_view.findViewById(R.id.delete_book);
issue_book=item_view.findViewById(R.id.issue_book);


        name_text.setText(name.get(position));
        auther_text.setText(authur.get(position));
        isbn_NO_text.setText(isbn_NO.get(position));
        call_no_text.setText(call_no.get(position));







        edit_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, edit_book.class);
                intent.putExtra("call_no",call_no.get(position));
                context.startActivity(intent);
            }
        });

        delete_book.setOnClickListener(new View.OnClickListener() {
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
                                database_handler_class=new database_handler_class(context);
                                database_handler_class.deleteTitle(call_no.get(position));


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

        issue_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, issue_by_book_name.class);
                intent.putExtra("call_no",call_no.get(position));
                context.startActivity(intent);
            }
        });




        return  item_view;
    }
    public int getId(int position)
    {
        return  id.get(position);
    }
public  String getauther(int pos){
        return  authur.get(pos);
}
public  String getCall_no(int position){return call_no.get(position);}
//public  void filter(String query){
//query=query.toLowerCase(Locale.getDefault());
//List<String> x=new ArrayList<>();
//if (query.length()==0){
//    x.addAll(name);
//}
//else {
//    for (String model :name){
//        if (model.toLowerCase(Locale.getDefault()).contains(query)){
//            x.add(model);
//        }
//
//    }
//}
//notifyDataSetChanged();
//}

}
