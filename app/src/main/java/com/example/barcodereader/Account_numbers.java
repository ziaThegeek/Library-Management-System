package com.example.barcodereader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Account_numbers extends AppCompatActivity {
    EditText search_text;
    ListView listView;
    Button add_acc_no;
   List<String> acc_number,name,amount,age;
    String batch_name,feeder_name;
    String divsion_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_numbers);
        search_text=findViewById(R.id.search_text);
        listView=findViewById(R.id.listview);
        add_acc_no=findViewById(R.id.add_account_number);
        listView.setEmptyView(findViewById(R.id.empty_view));
        db_handler_acc_numbers db_acc_numbers=new db_handler_acc_numbers(this);
        Intent intent=getIntent();
        divsion_code=intent.getStringExtra("division_code");
        batch_name=intent.getStringExtra("batch_no");
        feeder_name=intent.getStringExtra("feeder_name");
        getSupportActionBar().setTitle("Batch No_"+batch_name);
        db_acc_numbers.load_data(batch_name);
//        Toast.makeText(this, acc_number.toString(), Toast.LENGTH_SHORT).show();
        name=db_acc_numbers.getName();
        amount=db_acc_numbers.getAmount();
        age=db_acc_numbers.getAge();
        acc_number=db_acc_numbers.getRef_no();
//        ref_no= (String[]) acc_number.toArray();
        listview_adapter listview_adapter=new listview_adapter(this,name,acc_number,amount,age,null,"Account No:\t","Amount:","Age:\t",null);
        listView.setAdapter(listview_adapter);
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
                new reload_data(this,batch_name,acc_number,divsion_code).execute();
                Toast.makeText(this, acc_number.size()+"", Toast.LENGTH_SHORT).show();
                break;
            case R.id.export:
                new export_data(this,acc_number,name,amount,age,batch_name,getSupportActionBar().getTitle().toString()).execute();
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
}