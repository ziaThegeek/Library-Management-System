package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_sub_division extends AppCompatActivity {
    EditText division_name, division_code, sdo_name;
    Button add_division;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_division);
        division_name = findViewById(R.id.division_name);
        division_code = findViewById(R.id.division_code);
        sdo_name = findViewById(R.id.sdo_name);
        add_division = findViewById(R.id.add_division);
add_division.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        db_handler_sub_divisions db_sub_divisions = new db_handler_sub_divisions(add_sub_division.this);
        db_sub_divisions.insert(division_name.getText().toString(), division_code.getText().toString(), sdo_name.getText().toString());
        db_handler_feeders db_feeders=new db_handler_feeders(add_sub_division.this);
        db_feeders.create_feader(division_code.getText().toString());
        Toast.makeText(add_sub_division.this, "subdivision"+division_code.getText().toString()+"added", Toast.LENGTH_SHORT).show();
        finish();
    }
});

    }
}