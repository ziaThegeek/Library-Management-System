package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import org.jsoup.Jsoup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button button;
String name,amount;
List<String> ref_no;
    String[] ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.btnView);
        ref_no=new ArrayList<>();
        db_handler_acc_numbers db_accounts=new db_handler_acc_numbers(this);
        ref_no=db_accounts.getRef_no();
         ref =new String[ref_no.size()];
         ref=ref_no.toArray(new String[ref_no.size()]);
        String[] xyz = { "08125130937001", "08125130613102", "08125130589604","08125130589000","08125130588900","08125130588802","08125130588801",
                "08125130588800","08125130588709"};

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                   doIT object = (doIT) new doIT(textView,null,null).execute(xyz);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

//                    details = object.getDetails();
//                    String file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//                    File file = new File(file_path, "MyFile.pdf");
//                try {
//                    OutputStream outputStream = new FileOutputStream(file);
//                    PdfWriter writer = new PdfWriter(file);
//                    PdfDocument pdfDocument = new PdfDocument(writer);
//                    Document document = new Document(pdfDocument);
////                    float column_width[] = {200f, 200f};
////                    Table table = new Table(column_width);
////                    table.addCell("name");
////                    table.addCell("amount");
////                    table.addCell("details.get(0)");
////                    table.addCell("details.get(1)");
////                    document.add(table);
////                    document.close();
////                    outputStream.close();
//                }
//                catch (Exception ex)
//                {
//                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
//                }
            }

        });




    }



}
