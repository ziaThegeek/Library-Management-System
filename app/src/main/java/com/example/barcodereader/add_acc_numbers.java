package com.example.barcodereader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jxl.read.biff.BiffException;

public class add_acc_numbers extends AppCompatActivity {
    private static final int REQUEST_CODE = 10;
    private static final int REQUST_PERMISSION_CODE = 1;
    EditText acc_no;
    TextView batch_no, feeder_name, amount, name, age, ref_no,division_code;
    Button add_acc_no, Load_data, upload_file;
    String acc_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_acc_numbers);
        Intent intent = getIntent();
        batch_no = findViewById(R.id.batch_no);
        feeder_name = findViewById(R.id.feeder_name);
        amount = findViewById(R.id.amount);
        name = findViewById(R.id.name);
        age = findViewById(R.id.age);
        upload_file = findViewById(R.id.upload_file);
        division_code=findViewById(R.id.division_cod);
        add_acc_no = findViewById(R.id.add);
        Load_data = findViewById(R.id.load_data);
        acc_no = findViewById(R.id.acc_number);
        division_code.setText(intent.getStringExtra("division_code"));
        batch_no.setText(intent.getStringExtra("batch_no"));
        feeder_name.setText(intent.getStringExtra("feeder_name"));
        Load_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String[] ref = {batch_no.getText().toString() + division_code.getText().toString() + acc_no.getText().toString()};
                    Toast.makeText(add_acc_numbers.this, ref[0], Toast.LENGTH_SHORT).show();
                    new load_number(name,amount,age).execute(ref);
                    acc_number = acc_no.getText().toString();

                } catch (IndexOutOfBoundsException e) {
                    Toast.makeText(add_acc_numbers.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
        add_acc_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db_handler_acc_numbers db_account_numbers = new db_handler_acc_numbers(add_acc_numbers.this);
                db_account_numbers.insert(acc_number, name.getText().toString(), amount.getText().toString(), age.getText().toString(), batch_no.getText().toString());
                Toast.makeText(add_acc_numbers.this, "account number added successfully", Toast.LENGTH_SHORT).show();
            }
        });
        upload_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_file_activity();
            }
        });


    }

    public void open_file_activity() {
        if (ContextCompat.checkSelfPermission(add_acc_numbers.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent browse_file = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            browse_file.setType("application/vnd.ms-excel");
            startActivityForResult(browse_file, REQUEST_CODE);
        } else
            requestPermission();
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            open_file_activity();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUST_PERMISSION_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filepath = uri.getPath();
//                    filepath = filepath.substring(filepath.indexOf(":") + 1);
//                    String fileName = getFileName(uri);
                    String TEMP_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
//                    File file = new File(uri.toString());
                    File copyFile = new File(TEMP_DIR_PATH + "/" + get_file_path(uri));
                    import_excel_file import_excel_file=new import_excel_file(this,batch_no.getText().toString());
                    try {
                        import_excel_file.read_excel_file(copyFile.toString());
                    }
                    catch (IOException e) {
                        e.printStackTrace();
                    } catch (BiffException e) {
                        e.printStackTrace();
                    }
                    super.onActivityResult(requestCode, resultCode, data);
                }
        }
        }


    public  String getFileName(Uri uri) {
            if (uri==null)return null;
            String fileName = null;

            String path = uri.getPath();
            if (path.contains("/"))
                path=path.substring(path.lastIndexOf(":")+1);
            if (path.contains("/"))
                path=path.substring(path.lastIndexOf("/")+1);
            return path;
        }
    public String get_file_path(Uri uri)
    {
        String path = uri.getPath();
        if (path.contains("/"))
            path=path.substring(path.lastIndexOf(":")+1);
        return path;
    }
}