package com.example.barcodereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class export_data extends AsyncTask<Void,Void,Void> {
    List<String> ref_no,name,amount,age;
    OutputStream outputStream;
    float column_width[] = {10f,60f, 300f,40f,25f,90f};
    com.itextpdf.layout.Document workspace;
    Table table;
    File file;
    String file_path,file_name;
    Context context;
   String batch_no;
    ProgressDialog progress;
   db_handler_acc_numbers db_accounts;
   int sr_no;
    public export_data(Context context,List<String> ref_no,List<String> name,List<String> amount,List<String> age,String batch_no,String file_name) {
        this.context=context;
        db_accounts=new db_handler_acc_numbers(context);
        this.ref_no=ref_no;
        this.name=name;
        this.amount=amount;
        this.age=age;
        this.batch_no=batch_no;
        progress=new ProgressDialog(context);
        sr_no=0;
        table = new Table(column_width);
        this.file_name=file_name;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            progress.setMessage("exporting data... ");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            init_file_writing();
            add_headers();
            for (int i=0;i<ref_no.size();i++)
            {
                if (isArear(i))
                add_new_row(i,++sr_no);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.show();
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        try {

            progress.dismiss();
            saveFile(table);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init_file_writing() throws FileNotFoundException {
        file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        file  = new File(file_path, file_name+".pdf");
//        outputStream = new FileOutputStream(file);
            PdfWriter writer = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(writer);
workspace=new Document(pdfDocument);

    }

    private void add_new_row(int i,int sr_no) {
            table.addCell(sr_no+"");
            table.addCell(ref_no.get(i));
            table.addCell(name.get(i));
            table.addCell(amount.get(i));
            if (age.get(i)!=null)
            table.addCell(age.get(i));
            else
                table.addCell("");

//            else
//                table.addCell("0");
            table.addCell("");
    }
    public boolean isArear(int i)
    {
        if (age.get(i)!=null)
            return true;
        return false;
    }
    private void add_headers() {
          table.addCell("Sr.No");
            table.addCell("Account No");
            table.addCell("Name & Address");
            table.addCell("Amount");
            table.addCell("Age");
            table.addCell("Remarks");
    }
    public void saveFile(Table table) throws IOException {
        workspace.add(table);
        workspace.close();
    }
}
