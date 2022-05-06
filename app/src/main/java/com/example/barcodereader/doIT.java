package com.example.barcodereader;

import android.os.AsyncTask;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Table;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.PublicKey;
import java.util.ArrayList;

public class doIT extends AsyncTask<String,Void,Void> {
public  String words,ref_no,name,amount;
TextView Name,Amount,Age;
Document document;
static ArrayList<String> refNos;

static  int index=0;
OutputStream outputStream;
com.itextpdf.layout.Document workspace;
Table table;
    File file;
    String file_path;

    public doIT(TextView name,TextView amount,TextView age) throws FileNotFoundException {
        this.Name = name;
        this.Amount=amount;
        this.Age=age;
        file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
      file  = new File(file_path, "zia.pdf");
    }

    @Override
    protected Void doInBackground(String... params) {
        try {
//            outputStream = new FileOutputStream(file);
//            PdfWriter writer = new PdfWriter(file);
//            PdfDocument pdfDocument = new PdfDocument(writer);
//            workspace = new com.itextpdf.layout.Document(pdfDocument);
//            float column_width[] = {10f,60f, 300f,40f,25f,90f};
//            table = new Table(column_width);
//            table.addCell("Sr.No");
//            table.addCell("Account No");
//            table.addCell("Name & Address");
//            table.addCell("Amount");
//            table.addCell("Age");
//            table.addCell("Remarks");
            for (int i=0;i<params.length;i++) {
                document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno=" + params[i]).get();
                Name.setText(Name.getText()+getName()+"\n");
//                textView.setText(textView.getText()+params[i]);
//            words=getName()+"\n";
//            words+=getLastDate()+"\n";
//            words+=isArear()+"\n";
//            words+=getAmount()+"\n";
//            words+=getAge();

//            table.addCell(i+1+"");
//            table.addCell(params[i].substring(7));
//            table.addCell(getName());
//            table.addCell(getAmount());
//            table.addCell(getAge());
//            table.addCell("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);

    }

    public String getName()throws IndexOutOfBoundsException {
        Element para=document.select("p").get(0);
        return para.text().toString().substring(15);
    }
private String getLastDate()throws IndexOutOfBoundsException
{
    Element table = document.select("table").get(0);
    Elements rows = table.select("tr");
    Element row = rows.get(1);
    Elements cols = row.select("td");
    return  cols.get(6).text();
}
//public ArrayList<String> getDetails()
//    {
//        details.add(getName());
//        details.add(getAmount());
//        details.add(getAge());
//        return details;
//    }
private boolean isArear()throws IndexOutOfBoundsException
{
    Element table = document.select("table").get(8);
    Elements rows = table.select("tr");
    Element row = rows.get(1);
    Elements cols = row.select("td");
    if (cols.get(1).text()=="")
    {
        return true;
    }
    else
        return false;
}
private String getAge()throws IndexOutOfBoundsException
{
    Element table = document.select("table").get(8);
    Elements rows = table.select("tr");
    Element row = rows.get(1);
    Elements cols = row.select("td");
    String age=cols.get(1).text();
    if (age==null)
        return null;
    if (age.contains("/"))
    {
        age=age.substring(age.lastIndexOf("/")+1);
        return age;

    }
   return null;
}
    private String getAmount()throws IndexOutOfBoundsException
    {
    Element table = document.select("table").get(12);
    Elements rows = table.select("tr");
    Element row = rows.get(0);
    Elements cols = row.select("td");
    amount=cols.get(4).text();
    Amount.setText(cols.get(4).text());
    return cols.get(4).text();
}
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        try {
            Name.setText(getName());
            Age.setText(getAge());
            Amount.setText(getAmount());
//saveFile();
        }
        catch (Exception ex)
        {

        }
    }

public void saveFile() throws IOException {
    workspace.add(table);
    workspace.close();
    outputStream.close();
}
}
