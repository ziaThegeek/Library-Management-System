package com.example.barcodereader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

public class feeders extends AppCompatActivity {
ListView listView;
EditText search_text;
Button add_feeder;
List<String> feeder_name,feeder_code,bd_name;
Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeders);

intent=getIntent();
getSupportActionBar().setTitle(intent.getStringExtra("division_code"));
db_handler_feeders db_feeders=new db_handler_feeders(this);
db_feeders.load_data(intent.getStringExtra("division_code"));
feeder_name=db_feeders.getFeeder_name();
feeder_code=db_feeders.getFeeder_code();
bd_name=db_feeders.getBd_mame();
listView=findViewById(R.id.listview);
listView.setEmptyView(findViewById(R.id.empty_view));

add_feeder=findViewById(R.id.add_feeder);
if (feeder_name!=null) {
    ListAdapter listAdapter = new listview_adapter(this, feeder_name,bd_name,null,feeder_code,null,"BD Name:\t",null,"Feeader Code:\t",null);
    listView.setAdapter(listAdapter);
}
add_feeder.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(feeders.this, add_feeder.class);
        intent.putExtra("division_code",getSupportActionBar().getTitle().toString());
        startActivity(intent);
    }
});
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(feeders.this,batches.class);
        intent.putExtra("feeder_name",feeder_name.get(i));
        intent.putExtra("division_code",getSupportActionBar().getTitle().toString());
        startActivity(intent);
    }
});

    }
}