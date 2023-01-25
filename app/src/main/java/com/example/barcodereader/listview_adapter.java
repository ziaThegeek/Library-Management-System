package com.example.barcodereader;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.List;

public class listview_adapter extends ArrayAdapter {
    Activity context;
    private static final int REQUEST_PHONE_CALL = 10;

    List<String> main, sec00, sec01, sec10, sec11,Meter_no,Phone;
    String head00, head01, head10, head11;
    TextView heading00, heading01, heading10, heading11;
    TextView text00, text01, text10, text11, main_text, meter_no, metr_heading,phone_number;
    TextView more_details;
    ImageView phoneIcon;
    boolean isphone;
    Intent call;

    public listview_adapter(Activity context, List<String> main, List<String> sec00,
                            List<String> sec01, List<String> sec10, List<String> sec11, String head00,
                            String head01, String head10, String head11,List<String> meter_no,boolean isPhone,List<String> phone_number) {
        super(context, R.layout.list_item_view, main);
        this.context = context;
        this.main = main;
        this.sec00 = sec00;
        this.sec01 = sec01;
        this.sec10 = sec10;
        this.sec11 = sec11;
        this.head00 = head00;
        this.head01 = head01;
        this.head10 = head10;
        this.head11 = head11;
        this.Meter_no=meter_no;
        this.Phone=phone_number;
        this.isphone=isPhone;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        View item_view = layoutInflater.inflate(R.layout.list_item_view, null, true);
        main_text = item_view.findViewById(R.id.main_text);
        text00 = item_view.findViewById(R.id.text00);
        text01 = item_view.findViewById(R.id.text01);
        text10 = item_view.findViewById(R.id.text10);
        text11 = item_view.findViewById(R.id.text11);
        heading00 = item_view.findViewById(R.id.heading00);
        heading01 = item_view.findViewById(R.id.heading01);
        heading10 = item_view.findViewById(R.id.heading10);
        heading11 = item_view.findViewById(R.id.heading11);
        meter_no = item_view.findViewById(R.id.meter_no);
        metr_heading = item_view.findViewById(R.id.metr_title);
        phoneIcon=item_view.findViewById(R.id.phoneImage);
        phone_number=item_view.findViewById(R.id.phoneNumber);
        more_details=item_view.findViewById(R.id.more_details);

//        sr_no=item_view.findViewById(R.id.sr_no);
         update_view(position);
//        sr_no.setText((position+1)+"");
        phone_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);

                    } else {
                        call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + Phone.get(position)));
                        context.startActivity(call);
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Intent we_bill_activity=new Intent(context,web_bill.class);
               we_bill_activity.putExtra("url","https://bill.pitc.com.pk/gepcobill/general?refno="+sec00.get(position));
               context.startActivity(we_bill_activity);
            }
        });
        return item_view;
    }

    public void update_view( int position) {
        if (main != null)
            main_text.setText(main.get(position));
        if (sec00 != null)
            if (sec00.get(position).length()>7)
                text00.setText(sec00.get(position).substring(7));
            else
            text00.setText(sec00.get(position));
        if (sec01 != null)
                text01.setText(sec01.get(position));

        if (sec10 != null)
            text10.setText(sec10.get(position));
        if (sec11 != null)
            text11.setText(sec11.get(position));
        if (head00 != null)
            heading00.setText(head00);
        if (head01 != null)
            heading01.setText(head01);
        if (head10 != null)
            heading10.setText(head10);
        if (head11 != null)
            heading11.setText(head11);
        if (Meter_no!=null)
        {
            metr_heading.setText("Meter No");
            meter_no.setText(Meter_no.get(position));
        }
        if (isphone)
        {
            phoneIcon.setVisibility(View.VISIBLE);
            phone_number.setVisibility(View.VISIBLE);
            phone_number.setText(Phone.get(position));
            more_details.setVisibility(View.VISIBLE);
        }
        else if (!isphone)
        {
            phoneIcon.setVisibility(View.GONE);
            phone_number.setVisibility(View.GONE);
            more_details.setVisibility(View.GONE);
        }


    }
    public void update_view(String name, String amount, String age, String meter_No, int position) {

        if (main != null)
            main_text.setText(main.get(position));
        if (sec00 != null)
            text00.setText(sec00.get(position));
        if (sec01 != null)
            text01.setText(sec01.get(position));
        if (sec10 != null)
            text10.setText(sec10.get(position));
        if (sec11 != null)
            text11.setText(sec11.get(position));
        if (head00 != null)
            heading00.setText(head00);
        if (head01 != null)
            heading01.setText(head01);
        if (head10 != null)
            heading10.setText(head10);
        if (head11 != null)
            heading11.setText(head11);
        metr_heading.setText("Meter No");
        meter_no.setText(meter_No);

    }


}




