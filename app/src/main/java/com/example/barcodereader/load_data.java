package com.example.barcodereader;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public  class load_data  {
    Document document;
    String ref_no;
    public load_data(String ref_no)
    {
        this.ref_no=ref_no;
    }
    public void load() throws IOException {
        document = Jsoup.connect("https://bill.pitc.com.pk/gepcobill/general?refno=" + ref_no).get();
    }
    public String getName()
    {
        Element para=document.select("p").get(0);
        return para.text().toString().substring(15);
    }
    public String getAmount()
    {
        Element table = document.select("table").get(12);
        Elements rows = table.select("tr");
        Element row = rows.get(0);
        Elements cols = row.select("td");
        return cols.get(4).text();
    }
    public String getAge()
    {
        Element table = document.select("table").get(9);
        Elements rows = table.select("tr");
        Element row = rows.get(1);
        Elements cols = row.select("td");
        String age=cols.get(1).text();
        if (age==null)
            return null;
        if (age.contains("/"))
        {
            age=age.substring(age.lastIndexOf("/")+1);
        }
        return age;
    }
public String getRef_no()
{
    return  ref_no;
}
}
