package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class add_feeder extends AppCompatActivity {
    EditText feeder_name, feeder_code, bd_name;
    Button add_feeder;
Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeder);
        feeder_name = findViewById(R.id.feeder_name);
        feeder_code = findViewById(R.id.feeder_code);
        bd_name = findViewById(R.id.bd_name);
        add_feeder = findViewById(R.id.add_feeder);
        intent=getIntent();
        getSupportActionBar().setTitle(intent.getStringExtra("division_code"));
        add_feeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                db_handler_feeders db_feeders = new db_handler_feeders(add_feeder.this);
                db_feeders.insert(feeder_name.getText().toString(), feeder_code.getText().toString(), intent.getStringExtra("division_code"), bd_name.getText().toString());
                db_handler_batches db_batches=new db_handler_batches(add_feeder.this);
                db_batches.create_batch(feeder_name.getText().toString());
            }
        });
    }

    public boolean validate_name(String name, EditText name_text) {
        return false;
    }

    public boolean validate_code(String code, EditText code_text) {
        if (code.isEmpty())
return true;

            return false;
    }

}