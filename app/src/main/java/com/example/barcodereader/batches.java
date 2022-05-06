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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batches);
        listView=findViewById(R.id.listview);
        search_text=findViewById(R.id.search_text);
        add_batch=findViewById(R.id.add_batch);
        Intent intent=getIntent();
        division_code=intent.getStringExtra("division_code");
        getSupportActionBar().setTitle(intent.getStringExtra("feeder_name").toUpperCase(Locale.ROOT));
        db_handler_batches db_batches=new db_handler_batches(this);
        db_batches.load_data(getSupportActionBar().getTitle().toString());
        batch_no=db_batches.getBatch_no();
        vilages=db_batches.getVillage_names();
        last_date=db_batches.getLast_date();
        listView.setEmptyView(findViewById(R.id.empty_view));
        listview_adapter listview_adapter=new listview_adapter(this,batch_no,vilages,null,last_date,null,"Last Date:\t",null,"Village(s):\t",null);
        listView.setAdapter(listview_adapter);

add_batch.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent1=new Intent(batches.this, add_batch.class);
        intent1.putExtra("feeder_name",getSupportActionBar().getTitle().toString());
        startActivity(intent1);
    }
});
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1=new Intent(batches.this,Account_numbers.class);
        intent1.putExtra("batch_no",batch_no.get(i));
        intent1.putExtra("feeder_name",getSupportActionBar().getTitle().toString());
        intent1.putExtra("division_code",division_code);
        startActivity(intent1);
    }
});
    }

}