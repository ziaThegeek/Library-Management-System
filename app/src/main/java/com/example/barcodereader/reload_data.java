package com.example.barcodereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class reload_data extends AsyncTask<Void,Void,Void> {
    Context context;
    ProgressDialog progress;
    Document document;
    String batch_no;
    db_handler_acc_numbers db_accounts;
    List<String> acc_numbers;
    String division_code;

    public reload_data(Context context,String batch_no,List<String> acc_numbers,String division_code)
    {
        db_accounts=new db_handler_acc_numbers(context);
        progress=new ProgressDialog(context);
        this.context=context;
        this.batch_no=batch_no;
        this.acc_numbers=acc_numbers;
       this.division_code=division_code;
    }
    @Override
    protected Void doInBackground(Void...params) {


        progress.setMessage("updating data...");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setCanceledOnTouchOutside(false);
        for (int i=0;i<=acc_numbers.size();i++) {
            try {
                document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno="+batch_no+division_code+this.acc_numbers.get(i)) .get();
                db_accounts.update(acc_numbers.get(i), getName(), getAmount(),getAge(), batch_no);
                progress.setTitle(i+"/"+acc_numbers.size());
//                db_accounts.update("0368500","x"+batch_no,"x"+acc_numbers.toString(),"x",batch_no);
            } catch (Exception e) {
                e.printStackTrace();
            }
//                progress.setMessage(strings[i].toString());
//                Toast.makeText(context, batch_no+"12513"+strings[i], Toast.LENGTH_SHORT).show();
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
        progress.dismiss();
    }
    public String getName()
    {

            Element para = document.select("p").get(0);
            return para.text().toString().substring(15);

    }
    public String getAmount() {

        Element table = document.select("table").get(14);
        Elements rows = table.select("tr");
        Element row = rows.get(0);
        Elements cols = row.select("td");
        return cols.get(4).text();
    }
    public String getAge() {
        Element table = document.select("table").get(10);
        Elements rows = table.select("tr");
        Element row = rows.get(1);
        Elements cols = row.select("td");
        String age = cols.get(1).text();
        if (age == null)
            return null;
        if (age.contains("/")) {
            age = age.substring(age.lastIndexOf("/") + 1);
            return age;
        }
        return null;
    }
}
