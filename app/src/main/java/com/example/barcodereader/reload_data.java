package com.example.barcodereader;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;*/

import java.util.List;
public class reload_data extends AsyncTask<Void, Void, Void> {
    Context context;
    ProgressDialog progress;
    Document document;
    String batch_no;
    db_handler_acc_numbers db_accounts;
    String division_code, feeder_code;
    List<String> acc_numbers;

    public reload_data(Context context, String batch_no, List<String> acc_numbers, String division_code, String feeder_code) {
        db_accounts = new db_handler_acc_numbers(context, batch_no, division_code, feeder_code);
        progress = new ProgressDialog(context);
        this.context = context;
        this.batch_no = batch_no;
        this.acc_numbers = acc_numbers;
        this.division_code = division_code;
        this.feeder_code = feeder_code;
        progress.setTitle("Updating Data...");
        progress.setMessage("Please Wait ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setMax(acc_numbers.toArray().length);
        progress.setCancelable(false);
        progress.setIndeterminate(false);

    }

    @Override
    protected Void doInBackground(Void... params) {

        for (int i = 0; i < acc_numbers.size(); i++) {
            try {

                if (valid_number(i)) {
                    progress.setMessage(acc_numbers.get(i) + "");
//                document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno=" + this.acc_numbers.get(i)).get();
                    if (getAge() == null)
                        db_accounts.update(acc_numbers.get(i), getName(), (getAmount().matches("^-?[0-9]{1,8}?$") ? Integer.parseInt(getAmount()) : 0), 0, getmeter_no());
                    else
                        db_accounts.update(acc_numbers.get(i), getName(), (getAmount().matches("^-?[0-9]{1,8}?$") ? Integer.parseInt(getAmount()) : 0), (getAge().matches("^-?\\d+$") ? Integer.parseInt(getAge()) : 0), getmeter_no());
//                progress.setTitle(i+"/"+acc_numbers.size());

                    progress.setProgress(i);
                }
            } catch (Exception e) {

                continue;

            }

//                db_accounts.update("0368500","x"+batch_no,"x"+acc_numbers.toString(),"x",batch_no);
//            } catch (Exception e) {
//                db_accounts.update(acc_numbers.get(i), "null",0, 0, "null");
//                e.printStackTrace();
//            }
//                progress.setMessage(strings[i].toString());
//                Toast.makeText(context, batch_no+"12513"+strings[i],  Toast.LENGTH_SHORT).show();
        }
        return null;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress.show();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
//     progress.dismiss();

    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        progress.dismiss();
    }

    public boolean valid_number(int index) {
        try {
            document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno=" + this.acc_numbers.get(index)).get();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getName() {
        try {

            Element para = document.select("p").get(0);
            if (para.text().toString().substring(15) != null)
                return para.text().toString().substring(15);
            return null;
        } catch (Exception e) {
            return null;
        }

    }

    public String getAmount() {

        Element table = document.select("table").get(14);
        Elements rows = table.select("tr");
        Element row = rows.get(0);
        Elements cols = row.select("td");
        if (cols.get(4).text() != null)
            return cols.get(4).text();
        return null;
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

    public String getmeter_no() {
        Element table = document.select("table").get(4);
        Elements rows = table.select("tr");
        Element row = rows.get(4);
        Elements cols = row.select("td");
        String meter_no = cols.get(0).text();
        if (meter_no != null)
            return meter_no;
        return null;


    }
}
