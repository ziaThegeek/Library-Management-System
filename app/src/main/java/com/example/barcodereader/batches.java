package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;
import java.util.Locale;

public class batches extends AppCompatActivity {
    List<String> batch_no,vilages,last_date;
    EditText search_text;
    ListView listView;
    Button add_batch;
    String division_code;
    db_handler_batches db_batches;
    listview_adapter listview_adapter;
    String feeder_code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        listView=findViewById(R.id.listview);
        search_text=findViewById(R.id.search_text);
        add_batch=findViewById(R.id.add_batch);
        Intent intent=getIntent();
        division_code=intent.getStringExtra("division_code");
        feeder_code=intent.getStringExtra("feeder_name");
        db_batches=new db_handler_batches(this);
        getSupportActionBar().setTitle(db_batches.get_feeder_name(intent.getStringExtra("feeder_name")));
        db_batches.load_data(intent.getStringExtra("feeder_name"),intent.getStringExtra("division_code"));

        batch_no=db_batches.getBatch_no();
        vilages=db_batches.getVillage_names();
        last_date=db_batches.getLast_date();
        listView.setEmptyView(findViewById(R.id.empty_view));
        listview_adapter=new listview_adapter(this,batch_no,vilages,null,last_date,null,"Last Date:\t",null,"Village(s):\t",null,null,false,null);
        listView.setAdapter(listview_adapter);

add_batch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(batches.this, add_batch.class);
        intent1.putExtra("feeder_name",intent.getStringExtra("feeder_name"));
        intent1.putExtra("division_code",division_code);
        startActivity(intent1);
    }
});
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1=new Intent(batches.this,Account_numbers.class);
        intent1.putExtra("batch_no",batch_no.get(i));
        intent1.putExtra("feeder_name",intent.getStringExtra("feeder_name"));
        intent1.putExtra("division_code",division_code);
        startActivity(intent1);
    }
});
    }
    public void clear()
    {
        batch_no.clear();
        vilages.clear();
        last_date.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();
        clear();
        db_batches.load_data(feeder_code,division_code);
        listview_adapter=null;
        listview_adapter=new listview_adapter(this,db_batches.getBatch_no(),db_batches.getVillage_names(),null,db_batches.getLast_date(),null,"Last Date:\t",null,"Village(s):\t",null,null,false,null);
        listView.setAdapter(listview_adapter);

    }
}