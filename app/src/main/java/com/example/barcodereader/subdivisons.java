package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class subdivisons extends AppCompatActivity {
    private static final int REQUST_PERMISSION_CODE = 11;
    private static final int REQUST_PERMISSION_CODE_WRITE = 12;
    ListView listView;
    EditText search_text;
    Button add_division;
    List<String> divsion_name, division_code, sdo_name;
    db_handler_sub_divisions db_sub_divisions;
    ListAdapter listAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdivisons);
        init();
         db_sub_divisions  = new db_handler_sub_divisions(this);
        db_sub_divisions.load_data();
        divsion_name = db_sub_divisions.getDivsion_name();
        division_code = db_sub_divisions.getDivision_code();
        sdo_name = db_sub_divisions.getSdo_mame();
        listView = findViewById(R.id.listview);
        listView.setEmptyView(findViewById(R.id.empty_view));
        add_division = findViewById(R.id.add_feeder);
        request_permissions();
        if (divsion_name != null) {
          listAdapter   = new listview_adapter(this, division_code, sdo_name, null, divsion_name, null, "SDO Name:\t", null, "Sub-Division Name:\t", null,null);
            listView.setAdapter(listAdapter);
        }
        add_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(subdivisons.this, add_sub_division.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(subdivisons.this, feeders.class);
                intent.putExtra("division_code", division_code.get(i));
                startActivity(intent);
            }
        });

    }

    private void request_permissions() {
read();
write();
    }

    private void read() {
        if (ContextCompat.checkSelfPermission(subdivisons.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUST_PERMISSION_CODE);
            }
        }

    }
    private void write() {
        if (ContextCompat.checkSelfPermission(subdivisons.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUST_PERMISSION_CODE_WRITE);
            }
        }
    }


    public void init()
    {
        division_code=new ArrayList<>();
        divsion_name=new ArrayList<>();
        sdo_name=new ArrayList<>();
    }
    public void clear(){
        division_code.clear();
        divsion_name.clear();
        sdo_name.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clear();
        db_sub_divisions.load_data();
        listAdapter=null;
        listAdapter   = new listview_adapter(this, db_sub_divisions.getDivision_code(), db_sub_divisions.getSdo_mame(), null, db_sub_divisions.getDivsion_name(), null, "SDO Name:\t", null, "Sub-Division Name:\t", null,null);
        listView.setAdapter(listAdapter);
    }
}
