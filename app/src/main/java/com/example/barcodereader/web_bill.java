package com.example.barcodereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class web_bill extends AppCompatActivity {
    WebView web_bill;
    ProgressBar progressBar;
    String url;
    WebSettings webSettings;
    Intent webIntent,shareBill;
    ArrayList<PrintJob> printJobs = new ArrayList<PrintJob>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_bill);
        webIntent=getIntent();
        url=webIntent.getStringExtra("url");
        web_bill = findViewById(R.id.web_bill);
        progressBar=findViewById(R.id.progressBar);
        web_bill.setWebViewClient(new WebViewClient());
        webSettings=web_bill.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        web_bill.setInitialScale(70);
        progressBar.setVisibility(View.VISIBLE);
        web_bill.loadUrl(url);



web_bill.setDownloadListener(new DownloadListener() {
    @Override
    public void onDownloadStart(String s, String s1, String s2, String s3, long l) {
        Toast.makeText(web_bill.this, "Download Listener", Toast.LENGTH_SHORT).show();
    }
});



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.web_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.printBill:
                Toast.makeText(this, "Print Bill", Toast.LENGTH_SHORT).show();
                createWebPrintJob(web_bill);
                break;
            case R.id.shareBill:
                shareBill=new Intent(Intent.ACTION_SEND);
                shareBill.setType("text/*");
                shareBill.putExtra(Intent.EXTRA_TEXT,url);
                startActivity(Intent.createChooser(shareBill, "Share Electricity Bill"));
                break;



        }
        return super.onOptionsItemSelected(item);
    }


    private void createWebPrintJob(WebView webView) {

        // Get a PrintManager instance
        PrintManager printManager = (PrintManager) web_bill.this
                .getSystemService(Context.PRINT_SERVICE);

        String jobName = getString(R.string.app_name) + " Document";

        // Get a print adapter instance
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);

        // Create a print job with name and adapter instance
        PrintJob printJob = printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());

        printJobs.add(printJob);
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
        }
    }
}

