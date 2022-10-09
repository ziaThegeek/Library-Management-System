package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.tagging.IStructureNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class export_settings extends AppCompatActivity {
    Spinner acc_start,acc_end,age_min,age_max,amount_min,amount_max;
List<String> ref_no,amount,age,name;
String batch_name,division_code,feeder_code;
Intent data_collector;
    ArrayAdapter<String> arrayAdapter;
    Button export_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export_settings);
        acc_start=findViewById(R.id.acc_number_start);
        acc_end=findViewById(R.id.acc_number_end);
        age_max=findViewById(R.id.age_max);
        age_min=findViewById(R.id.age_min);
        amount_min=findViewById(R.id.amount_min);
        amount_max=findViewById(R.id.amount_max);
        export_button=findViewById(R.id.export_button);
        String[] amount_options={"0","500","1000","2000","5000","10000","10000000"};
        String[] age_options={"0","1","2","3","4","5","6","7","8","9","10"};

        data_collector=getIntent();
        batch_name=data_collector.getStringExtra("batch_no");
        division_code=data_collector.getStringExtra("division_code");
        feeder_code=data_collector.getStringExtra("feeder_name");
//        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,search_options);
//        acc_start.setAdapter(arrayAdapter);
//        acc_end.setAdapter(arrayAdapter);
//        age_min.setAdapter(arrayAdapter);
//        age_max.setAdapter(arrayAdapter);
//        amount_max.setAdapter(arrayAdapter);
//        amount_min.setAdapter(arrayAdapter);
ref_no=new ArrayList<>();
age=new ArrayList<>();
amount=new ArrayList<>();
name=new ArrayList<>();
db_handler_acc_numbers db_acc_numbre=new db_handler_acc_numbers(this,batch_name,division_code,feeder_code);
db_acc_numbre.load_data();
      arrayAdapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,db_acc_numbre.getRef_no());
      acc_start.setAdapter(arrayAdapter);
//        List<String> revesed=db_acc_numbre.getRef_no();
//        Collections.reverse(revesed);
        arrayAdapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,db_acc_numbre.getRef_no());
        acc_end.setAdapter(arrayAdapter);


        arrayAdapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,age_options);
        age_min.setAdapter(arrayAdapter);
        age_max.setAdapter(arrayAdapter);
        arrayAdapter =new ArrayAdapter(this, android.R.layout.simple_list_item_1,amount_options);
        amount_max.setAdapter(arrayAdapter);
        amount_min.setAdapter(arrayAdapter);

export_button.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        if (Long.parseLong(acc_end.getSelectedItem().toString())<Long.parseLong(acc_start.getSelectedItem().toString()))
            Toast.makeText(export_settings.this, "starting account number should be less than ending account number", Toast.LENGTH_SHORT).show();
        else if (Integer.parseInt(age_max.getSelectedItem().toString())<Integer.parseInt(age_min.getSelectedItem().toString()))
            Toast.makeText(export_settings.this, "minimum age  should be less than or equal to maximum age", Toast.LENGTH_SHORT).show();
        else if (Integer.parseInt(amount_max.getSelectedItem().toString())<Integer.parseInt(amount_min.getSelectedItem().toString()))
            Toast.makeText(export_settings.this, "minimum amount  should be less than  or equal to maximum amount", Toast.LENGTH_SHORT).show();
        else {
            Cursor cursor = db_acc_numbre.export_date(acc_start.getSelectedItem().toString(), acc_end.getSelectedItem().toString(),
                    Integer.parseInt(amount_min.getSelectedItem().toString()), Integer.parseInt(amount_max.getSelectedItem().toString()),
                    Integer.parseInt(age_min.getSelectedItem().toString()), Integer.parseInt(age_max.getSelectedItem().toString()));
            if (cursor != null) {
                while (cursor.moveToNext()) {

                    ref_no.add(cursor.getString(0));
                    name.add(cursor.getString(1));
                    amount.add(String.valueOf(cursor.getInt(2)));
                    age.add(String.valueOf(cursor.getInt(3)));
                }
            }
            new export_data(export_settings.this, ref_no, name, amount, age, batch_name, "Batch_NO_" + batch_name).execute();
            File temp_file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "" + "/Batch_NO_" + batch_name + ".pdf");
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Uri apkURI = FileProvider.getUriForFile(getApplicationContext(), getPackageName() + ".provider", temp_file);
                intent.setDataAndType(apkURI, "application/pdf");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            } else {
                intent.setDataAndType(Uri.fromFile(temp_file), "application/pdf");
            }
            startActivity(intent);
        }
    }
});

    }

}