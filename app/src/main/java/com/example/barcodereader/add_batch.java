
package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class add_batch extends AppCompatActivity {
    EditText batch_no,vilage_names,last_date;
    Button add_batch;
    String feeder_name;
    TextView feedar_name,division_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_batch);
        batch_no=findViewById(R.id.batch_no);
        vilage_names=findViewById(R.id.vilage_name);
        last_date=findViewById(R.id.last_date);
        add_batch=findViewById(R.id.add_batch);
        Intent intent=getIntent();
        feedar_name=findViewById(R.id.feeder_name);
        division_code=findViewById(R.id.division_code);
        feeder_name=intent.getStringExtra("feeder_name");
        feedar_name.setText(intent.getStringExtra("feeder_name"));
        division_code.setText(intent.getStringExtra("division_code"));
        getSupportActionBar().setTitle(feeder_name);

        add_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_handler_batches db_batches=new db_handler_batches(add_batch.this);

                db_batches.insert_data(batch_no.getText().toString(),last_date.getText().toString(),vilage_names.getText().toString(),feeder_name,intent.getStringExtra("division_code"));
                Toast.makeText(add_batch.this, "batch added successfully", Toast.LENGTH_SHORT).show();
                finish();
//                db_handler_acc_numbers db_acc_numbers=new db_handler_acc_numbers(add_batch.this);
////                db_acc_numbers.create_batch(batch_no.getText().toString());

            }
        });
        
    }
}