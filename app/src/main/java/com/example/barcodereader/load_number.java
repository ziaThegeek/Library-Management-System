package com.example.barcodereader;

import android.os.AsyncTask;
import android.util.Size;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class load_number extends AsyncTask<String,Void,Void> {
    TextView name,amount,age;
    Document document;
    public load_number(TextView name,TextView amount,TextView age) {
        this.name=name;
        this.amount=amount;
        this.age=age;

    }

    @Override
    protected Void doInBackground(String... strings) {
        for (int i=0;i<strings.length;i++)
        {
            try {
                document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno=" + strings[i]).get();

            } catch (Exception e) {

            }

        }
        return null;

    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        try {
            if (getName() != null)
                name.setText(getName());
            else
                name.setText("record not found");
            if (getAmount() != null)
                amount.setText(getAmount());
            if (getAge() != null)
                age.setText(getAge());
        }
        catch (Exception e)
        {
            name.setText("record not found");
            amount.setText("record not found");
            e.printStackTrace();
        }
    }

    private String getAge() throws IndexOutOfBoundsException {
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

    private String getAmount() throws IndexOutOfBoundsException{
        Element table = document.select("table").get(14);
        Elements rows = table.select("tr");
        Element row = rows.get(0);
        Elements cols = row.select("td");
        return cols.get(4).text();
    }

    private String getName()throws IndexOutOfBoundsException {
        Element para=document.select("p").get(0);
        return para.text().toString().substring(15);
    }
    public String getmeter_no()throws IndexOutOfBoundsException{
        Element table = document.select("table").get(4);
        Elements rows = table.select("tr");
        Element row = rows.get(4);
        Elements cols = row.select("td");
        String meter_no = cols.get(0).text();
        return meter_no;



    }
}
