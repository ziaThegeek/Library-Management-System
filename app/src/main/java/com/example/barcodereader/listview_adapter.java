package com.example.barcodereader;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class listview_adapter extends ArrayAdapter {
Activity context;
List<String> main,sec00,sec01,sec10,sec11;
String head00,head01,head10,head11;
TextView heading00,heading01,heading10,heading11;
TextView text00,text01,text10,text11,main_text;

    public listview_adapter(Activity context, List<String> main,List<String> sec00,List<String> sec01,List<String> sec10,List<String> sec11,String head00,String head01,String head10,String head11) {
        super(context, R.layout.list_item_view,main);
        this.context=context;
        this.main=main;
        this.sec00=sec00;
        this.sec01=sec01;
        this.sec10=sec10;
        this.sec11=sec11;
        this.head00=head00;
        this.head01=head01;
        this.head10=head10;
        this.head11=head11;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View item_view = layoutInflater.inflate(R.layout.list_item_view, null, true);
        main_text=item_view.findViewById(R.id.main_text);
        text00=item_view.findViewById(R.id.text00);
        text01=item_view.findViewById(R.id.text01);
        text10=item_view.findViewById(R.id.text10);
        text11=item_view.findViewById(R.id.text11);
        heading00=item_view.findViewById(R.id.heading00);
        heading01=item_view.findViewById(R.id.heading01);
        heading10=item_view.findViewById(R.id.heading10);
        heading11=item_view.findViewById(R.id.heading11);
//        sr_no=item_view.findViewById(R.id.sr_no);
        if (main!=null)
        main_text.setText(main.get(position));
        if (sec00!=null)
        text00.setText(sec00.get(position));
        if (sec01!=null)
        text01.setText(sec01.get(position));
        if (sec10!=null)
            text10.setText(sec10.get(position));
        if (sec11!=null)
            text11.setText(sec11.get(position));
        if (head00!=null)
            heading00.setText(head00);
        if (head01!=null)
            heading01.setText(head01);
        if (head10!=null)
            heading10.setText(head10);
        if (head11!=null)
            heading11.setText(head11);
//        sr_no.setText((position+1)+"");
        return item_view;
    }
}
