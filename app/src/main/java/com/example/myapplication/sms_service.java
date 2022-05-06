package com.example.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class sms_service extends AppCompatActivity {
    private final static int SEND_SMS_PERMISSION_REQ=1;
    public String msg,receiver_no;
    Context context;
    Activity activity;
    public  sms_service(Context context,String msg,String receiver_no,Activity activity)
    {
        this.msg=msg;
        this.receiver_no=receiver_no;
        this.context=context;
        this.activity=activity;
    }
    public void set_permissions()
    {
        if(checkPermission(Manifest.permission.SEND_SMS))
        {
            //send sms
            send_sms();
        }
        else
        {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.SEND_SMS}, SEND_SMS_PERMISSION_REQ);
        }
    }
    private boolean checkPermission(String sendSms) {

        int checkpermission= ContextCompat.checkSelfPermission(context,sendSms);
        return checkpermission== PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SEND_SMS_PERMISSION_REQ:
                if (grantResults.length > 0 && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //send sms
                    send_sms();
                }
                break;
        }
    }

    public void  send_sms(){
        if(!TextUtils.isEmpty(msg)&&!TextUtils.isEmpty(receiver_no))
        {

            if(checkPermission(Manifest.permission.SEND_SMS))
            {
                SmsManager smsManager = SmsManager.getDefault();
                ArrayList<String> parts = smsManager.divideMessage(msg);
                smsManager.sendMultipartTextMessage("+923414657341", null, parts, null, null);
            }
            else {
                Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }
    }

