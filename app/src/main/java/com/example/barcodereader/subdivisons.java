package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class subdivisons extends AppCompatActivity {
    ListView listView;
    EditText search_text;
    Button add_division;
    List<String> divsion_name,division_code,sdo_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivisons);
        db_handler_sub_divisions db_sub_divisions=new db_handler_sub_divisions(this);
        db_sub_divisions.load_data();
        divsion_name=db_sub_divisions.getDivsion_name();
        division_code=db_sub_divisions.getDivision_code();
        sdo_name=db_sub_divisions.getSdo_mame();
        listView=findViewById(R.id.listview);
        listView.setEmptyView(findViewById(R.id.empty_view));
        add_division=findViewById(R.id.add_feeder);
        if (divsion_name!=null) {
            ListAdapter listAdapter = new listview_adapter(this, division_code,sdo_name,null,divsion_name,null,"SDO Name:\t",null,"Sub-Division Name:\t",null);
            listView.setAdapter(listAdapter);
        }
        add_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(subdivisons.this, add_sub_division.class);
                startActivity(intent);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(subdivisons.this,feeders.class);
                intent.putExtra("division_code",division_code.get(i));
                startActivity(intent);
            }
        });

    }
    }
