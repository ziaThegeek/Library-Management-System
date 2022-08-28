package com.example.barcodereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account_numbers extends AppCompatActivity {
    private static final int REQUST_PERMISSION_CODE = 11;
    EditText search_text;
    ListView listView;
    Button add_acc_no;
   List<String> acc_number,name,amount,age,meter_no;
    String batch_name,feeder_name;
    String divsion_code;
    TextView search_by;
    listview_adapter listview_adapter;
    int index=0;
    db_handler_acc_numbers db_acc_numbers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_numbers);
        search_text=findViewById(R.id.search_text);
        listView=findViewById(R.id.listview);
        search_by=findViewById(R.id.search_by);
        add_acc_no=findViewById(R.id.add_account_number);
        listView.setEmptyView(findViewById(R.id.empty_view));
        Intent intent=getIntent();
        divsion_code=intent.getStringExtra("division_code");
        batch_name=intent.getStringExtra("batch_no");
        feeder_name=intent.getStringExtra("feeder_name");
   db_acc_numbers =new db_handler_acc_numbers(this,batch_name,divsion_code,feeder_name);
        String[] search_options={"Consumer Name","Account Number","Meter Number","Age","Amount"};

        getSupportActionBar().setTitle("Batch No_"+batch_name);
        db_handler_feeders db_feeders=new db_handler_feeders(this);
        db_acc_numbers.load_data();
//        Toast.makeText(this, acc_number.toString(), Toast.LENGTH_SHORT).show();
        name=db_acc_numbers.getName();
        amount=db_acc_numbers.getAmount();
        age=db_acc_numbers.getAge();
        acc_number=db_acc_numbers.getRef_no();
        meter_no=db_acc_numbers.getMeter_no();
//        ref_no= (String[]) acc_number.toArray();
        listview_adapter = new listview_adapter(this, name, acc_number, amount, age, null, "Account No:\t", "Amount:", "Age:\t", null, meter_no);
        listView.setAdapter(listview_adapter);
        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

db_acc_numbers.clear();
if (index==0)
    db_acc_numbers.search_name(charSequence.toString());
else if (index==1)
db_acc_numbers.search_acc(charSequence.toString());
else if (index==2)
    db_acc_numbers.search_metr(charSequence.toString());
else if (index==3)
    db_acc_numbers.search_age(charSequence.toString());
else
    db_acc_numbers.search_amout(charSequence.toString());

listview_adapter=null;
listview_adapter=new listview_adapter(Account_numbers.this,db_acc_numbers.getName(),db_acc_numbers.getRef_no(),db_acc_numbers.getAmount(),db_acc_numbers.getAge(),null, "Account No:\t", "Amount:", "Age:\t", null,db_acc_numbers.getMeter_no() );
listView.setAdapter(listview_adapter);
listview_adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
add_acc_no.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(Account_numbers.this,add_acc_numbers.class);
        intent1.putExtra("batch_no",batch_name);
        intent1.putExtra("division_code",divsion_code);
        intent1.putExtra("feeder_name",feeder_name);

        startActivity(intent1);
    }
});
search_by.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(Account_numbers.this);
        mBuilder.setTitle("Search By");
        mBuilder.setSingleChoiceItems(search_options, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(Account_numbers.this, search_options[i], Toast.LENGTH_SHORT).show();
                index=i;
                dialogInterface.dismiss();
            }
        }).show();
    }
});
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu_acc_no, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.refresh:
                Toast.makeText(this, "refresh", Toast.LENGTH_SHORT).show();
                break;
            case R.id.reload:
                new reload_data(this,batch_name,acc_number,divsion_code,feeder_name).execute();
                Toast.makeText(this, acc_number.size()+"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.export:
                Intent export_activity=new Intent(Account_numbers.this,export_settings.class);
                export_activity.putExtra("batch_no",batch_name);
                export_activity.putExtra("division_code",divsion_code);
                export_activity.putExtra("feeder_name",feeder_name);

                startActivity(export_activity);
//                    open_file_activity();
                    break;


        }
        return super.onOptionsItemSelected(item);
    }
public String[] cast_to_string()
{
    String[] temp=new String[acc_number.size()];
    for (int i=0;i<acc_number.size();i++)
    {
        temp[i]=acc_number.get(i);
    }
    return  temp;
}
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            open_file_activity();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUST_PERMISSION_CODE);
        }
    }
    public void open_file_activity() {
        if (ContextCompat.checkSelfPermission(Account_numbers.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            new export_data(this, acc_number, name, amount, age, batch_name, getSupportActionBar().getTitle().toString()).execute();
        } else
            requestPermission();
    }
    public void clear()
    {
        acc_number.clear();
        name.clear();
        amount.clear();
        age.clear();
        meter_no.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clear();
        db_acc_numbers.load_data();
listview_adapter=null;
        listview_adapter=new listview_adapter(Account_numbers.this,db_acc_numbers.getName(),db_acc_numbers.getRef_no(),db_acc_numbers.getAmount(),db_acc_numbers.getAge(),null, "Account No:\t", "Amount:", "Age:\t", null,db_acc_numbers.getMeter_no() );
        listView.setAdapter(listview_adapter);
    }
}