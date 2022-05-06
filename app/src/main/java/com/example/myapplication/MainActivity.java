package com.example.myapplication;
import  com.example.myapplication.Data.database_handler_class;
import com.example.myapplication.Data.menu_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.ChoiceFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import static com.example.myapplication.R.id.empty_list_view;

public class MainActivity extends AppCompatActivity  {

    private static final int REQUST_PERMISSION_CODE = 1;
    List<String> names,authers,ed_no,isbn_no,year,page,call_no,intigrated_string;
    List<Integer> qnty;
ListView listView;
Button add,update,delete,ulpoad_file;
ArrayAdapter<String> arrayAdapter;
String x;
AssetManager am;
import_excel_file imp1;
Listview_Adapter listview_adapter;
RelativeLayout bottom_view;
CheckBox select_all;
TextView delete_all;
TextView seleted_item_counter,curd_process;
public static final int REQUEST_CODE=10;
long[] indexes=new long[100];
    int pos=0;
    private database_handler_class Dbhelper;
boolean selection_started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("BOOKS RECORD");
        selection_started=false;
        setContentView(R.layout.activity_main);
        delete_all=findViewById(R.id.delete_all);
        bottom_view=findViewById(R.id.bottom_view);
        seleted_item_counter=findViewById(R.id.selected_items_text);
        select_all=findViewById(R.id.select_all);
        ulpoad_file=findViewById(R.id.pick_excel_file);
        names=new ArrayList<>();
        authers=new ArrayList<>();
        isbn_no=new ArrayList<>();
        year=new ArrayList<>();
        page=new ArrayList<>();
        call_no=new ArrayList<>();
        qnty=new ArrayList<>();
        //handleIntent(getIntent());
        ed_no=new ArrayList<>();
        intigrated_string=new ArrayList<>();
        Dbhelper=new database_handler_class(this);
        view_data();
listView.setChoiceMode(ListView.CHOICE_MODE_NONE);
       listView.setTextFilterEnabled(true);


ulpoad_file.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //soundFile = getContentResolver().openInputStream(Uri.parse(uri));
open_file_activity();
    }
});
//listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////
//        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
//        builder1.setMessage("Are You Sure to delete??.");
//        builder1.setCancelable(true);
//
//        builder1.setPositiveButton(
//                "Yes",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        update.setText("="+listView.getCount());
//
//                        names.remove(position);
//                        authers.remove(position);
//                        ed_no.remove(position);
//                        Dbhelper.deleteTitle(listview_adapter.getCall_no(position));
//                        dialog.cancel();
//                        listview_adapter.notifyDataSetChanged();
//                    }
//                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast.makeText(MainActivity.this, "teri mezi", Toast.LENGTH_SHORT).show();
//                        dialog.cancel();
//                    }
//                });
//
//        AlertDialog alert11 = builder1.create();
//        alert11.show();
//
//    }
//});

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selection_started) {
                    arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice, Dbhelper.getNames());
//                Intent intent=new Intent(MainActivity.this,book_details.class);
//                intent.putExtra("call_no",call_no.get(position));
//                startActivity(intent);
                }
else
                parent.setSelection(position);

//                if (selection_started)
//                {
//                    if (!listView.isItemChecked(position))
//                    {
//                        listView.setItemChecked(position,true);
//                    }
//                    else if (listView.isItemChecked(position)){
//                        listView.setItemChecked(position,false);
//
//                    }
                    seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());

//                }
//
//               else {
//Intent intent=new Intent(MainActivity.this,book_details.class);
//startActivity(intent);
//                }


            }
        });
        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (select_all.isChecked())
                {
                    for ( int i=0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, true);

                    }
                    seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());

                }
                else{
                    for ( int i=0; i < listView.getCount(); i++) {
                        listView.setItemChecked(i, false);
                    }
                    seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());

                }
            }
        });
        delete_all.setOnClickListener(new View.OnClickListener() {
            List<Integer> selected_items_list;
            @Override
            public void onClick(View v) {





                selected_items_list=getSeketeditem_list();
                arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,Dbhelper.getNames());
                listView.setAdapter(arrayAdapter);
                selection_started=false;
                if(!selected_items_list.isEmpty())
                {
                    for (int i=0;i<selected_items_list.size();i++)
                    {
                        Dbhelper.deleteTitle(call_no.get(selected_items_list.get(i)));
                     names.remove(selected_items_list.get(i));

                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                bottom_view.setVisibility(View.GONE);
            }
        });

listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        if (selection_started){
            listView.setItemChecked(position,true);
            seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());
        }
        else {
            bottom_view.setVisibility(View.VISIBLE);

            selection_started = true;
            arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_multiple_choice,Dbhelper.getNames());
            listView.setAdapter(arrayAdapter);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
            listView.setItemChecked(position, true);
            seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());
        }
        return true;
    }
});

    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {
open_file_activity();
        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults) {
if (requestCode==REQUST_PERMISSION_CODE&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
{
    if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
    {
        open_file_activity();
    }
    else {
        Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show();
    }
}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri=data.getData();
                    String filepath=uri.getPath();
                    filepath=filepath.substring(filepath.indexOf(":")+1);
                    String fileName = getFileName(uri);
                   String TEMP_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
                   File file=new File(uri.toString());
                   File copyFile = new File(TEMP_DIR_PATH +"/"+get_file_path(uri));
//                    Uri uri=data.getData();
//                    Toast.makeText(this, uri.toString() , Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, copyFile.toString(), Toast.LENGTH_LONG).show();
                    super.onActivityResult(requestCode, resultCode, data);
//                    File file=new File(data.getData().toString());
                    Dbhelper.OnCreate(copyFile.toString());
                   view_data();
                }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.select).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconified(true);
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("enter call no");
    searchView.setInputType(InputType.TYPE_CLASS_NUMBER);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                MainActivity.this.arrayAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MainActivity.this.arrayAdapter.getFilter().filter(newText);
                //                Log.e("Main"," data search"+newText);






                return true;
            }

        });


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        List<Integer> selected_items_list;
        switch (item.getItemId()) {

//            case R.id.share:
//                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//
//                break;
//            case R.id.details:
////                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
//                selected_items_list=getSeketeditem_list();
//                if(!selected_items_list.isEmpty())
//                {
//                    String text="";
//                    for (int i=0;i<selected_items_list.size();i++)
//                    {
//                        text+=names.get(selected_items_list.get(i))+"\t"+call_no.get(selected_items_list.get(i))+"\t"+authers.get(selected_items_list.get(i))+"\n";
//
//
//                    }
//                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.delete:
//                selected_items_list=getSeketeditem_list();
//                arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,Dbhelper.getNames());
//                listView.setAdapter(arrayAdapter);
//                selection_started=false;
//                if(!selected_items_list.isEmpty())
//                {
//                    for (int i=0;i<selected_items_list.size();i++)
//                    {
//                        Dbhelper.deleteTitle(call_no.get(selected_items_list.get(i)));
//names.remove(selected_items_list.get(i));
//
//                    }
//                    arrayAdapter.notifyDataSetChanged();
//                }
//                break;
            case R.id.upload_file:
open_file_activity();
                break;
            case R.id.add_single_book:
                add_single_item();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private List<Integer> getSeketeditem_list() {
        List<Integer> items=new ArrayList<>();
    for (int i=0;i<listView.getCount();i++)

    {

        if (listView.isItemChecked(i))
        {
            items.add(i);
        }
    }
    return items;
    }

    public  void populate_list_view(database_handler_class DbHandler)
{

//    listview_adapter=new Listview_Adapter(this,DbHandler.getNames(),DbHandler.getAuthers(),DbHandler.getEd_no(),DbHandler.getId(),DbHandler.getCall_no());
    listView=findViewById(R.id.list_view);
//    arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,DbHandler.getCall_no());
    listview_adapter=new Listview_Adapter(this,DbHandler.getNames(),DbHandler.getCall_no(),DbHandler.getId(),DbHandler.getAuthers(),DbHandler.getIsbn_no(),DbHandler.getYear());
    listView.setAdapter(listview_adapter);
listView.setEmptyView(findViewById(empty_list_view));
}
public  void delete(database_handler_class DbHandler)
{

}
    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }
    public  void add_single_item()
    {
        Intent intent = new Intent(MainActivity.this, add_books.class);
        startActivity(intent);
    }
    public  String getFileName(Uri uri) {
        if (uri==null)return null;
        String fileName = null;

        String path = uri.getPath();
        if (path.contains("/"))
        path=path.substring(path.lastIndexOf(":")+1);
        if (path.contains("/"))
    path=path.substring(path.lastIndexOf("/")+1);
        return path;
    }
    public void open_file_activity()
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            Intent browse_file=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            browse_file.setType("application/vnd.ms-excel");
//    browse_file.setType("*/*");
            startActivityForResult(browse_file,REQUEST_CODE);
        }
        else
            requestPermission();

    }
    public void view_data()
    {
        Dbhelper.insert_into_list_view(Dbhelper.Get_data());
        names=Dbhelper.getNames();
        authers=Dbhelper.getAuthers();
        ed_no=Dbhelper.getEd_no();
        isbn_no=Dbhelper.getIsbn_no();
        year=Dbhelper.getYear();
        page=Dbhelper.getPage();
        call_no=Dbhelper.getCall_no();
qnty=Dbhelper.getQnty();
        populate_list_view(Dbhelper);
    }
    public List<String> make_intigrated_string(List<String> book_name,List<String> call_no)
    {
        List<String> final_string=new ArrayList<>();
        for (int i=0;i<book_name.size();i++)
            final_string.add(call_no.get(i)+"\n"+book_name.get(i));
        return final_string;

    }
    public String get_file_path(Uri uri)
    {
        String path = uri.getPath();
        if (path.contains("/"))
            path=path.substring(path.lastIndexOf(":")+1);
        return path;
    }
    public  String getName(int i)
    {
        return names.get(i);
    }

    public  String getAutherName(int i)
    {
        return authers.get(i);
    }

}