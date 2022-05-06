package com.example.myapplication;
import  com.example.myapplication.Data.database_handler_class;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import com.example.myapplication.Data.datbase_handler_students;
import com.example.myapplication.Params.params;

import org.w3c.dom.Text;

public class Student_file extends AppCompatActivity {
    List<String> names,reg,details,intigrated_string;
    ListView listView;
    Button add,update,delete,ulpoad_file;
    ArrayAdapter<String> arrayAdapter;
    String x;
    RelativeLayout bottom_view;
    TextView seleted_item_counter,delete_all;
    CheckBox select_all;
    AssetManager am;
    import_excel_file imp1;
    listview_adapter_students listview_adapter_students;
    long[] indexes=new long[100];
    private static final int REQUST_PERMISSION_CODE = 1;
    public static final int REQUEST_CODE=10;
    int pos=0;
    datbase_handler_students Dbhelper;
    private boolean selection_started=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_student_file);
        getSupportActionBar().setTitle("STUDENTS RECORD");
        bottom_view=findViewById(R.id.bottom_view);
        seleted_item_counter=findViewById(R.id.selected_items_text);
        select_all=findViewById(R.id.select_all);
        add=findViewById(R.id.add);
        update=findViewById(R.id.update);
//        delete=findViewById(R.id.delete);
        ulpoad_file=findViewById(R.id.pick_excel_file);
        delete_all=findViewById(R.id.delete_all);
        names=new ArrayList<>();
        reg=new ArrayList<>();
        details=new ArrayList<>();
        intigrated_string=new ArrayList<>();
        Dbhelper=new datbase_handler_students(this);

        view_data();
        populate_list_view(Dbhelper);
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                delete(Dbhelper);
////                    listView.removeViewAt(0);
//            }
//        });



        ulpoad_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //soundFile = getContentResolver().openInputStream(Uri.parse(uri));
                if (ContextCompat.checkSelfPermission(Student_file.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                {
                    Intent browse_file=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    browse_file.setType("application/vnd.ms-excel");
//    browse_file.setType("*/*");
                    startActivityForResult(browse_file,REQUEST_CODE);
                }
                else
                    requestPermission();

            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                if (selection_started){
                    listView.setItemChecked(position,true);
                }
                else {
                    bottom_view.setVisibility(View.VISIBLE);
                    selection_started = true;
                    arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice,Dbhelper.reg);
                    listView.setAdapter(arrayAdapter);
                    listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                    listView.setItemChecked(position, true);
                    seleted_item_counter.setText("selected"+getSeketeditem_list().size()+"/"+arrayAdapter.getCount());
                }
                return true;
            }
        });

        delete_all.setOnClickListener(new View.OnClickListener() {
            List<Integer> selected_items_list;
            @Override
            public void onClick(View v) {
                selected_items_list=getSeketeditem_list();

                arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Dbhelper.getReg());
                listView.setAdapter(arrayAdapter);
                selection_started=false;
                if(!selected_items_list.isEmpty())
                {
                    for (int i=0;i<selected_items_list.size();i++)
                    {
                        Dbhelper.deleteTitle(reg.get(selected_items_list.get(i)));
                        names.remove(selected_items_list.get(i));
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
                bottom_view.setVisibility(View.GONE);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selection_started) {
                    arrayAdapter = new ArrayAdapter<String>(Student_file.this, android.R.layout.simple_list_item_multiple_choice, Dbhelper.getNames());
//                Intent intent=new Intent(MainActivity.this,book_details.class);
//                intent.putExtra("call_no",call_no.get(position));
//                startActivity(intent);
                } else
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
                seleted_item_counter.setText("selected" + getSeketeditem_list().size() + "/" + arrayAdapter.getCount());

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

    }


    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE))
        {

        }
        else {
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},REQUST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQUST_PERMISSION_CODE)
        {
            if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent browse_file=new Intent(Intent.ACTION_OPEN_DOCUMENT);
                browse_file.setType("application/vnd.ms-excel");
                startActivityForResult(browse_file,REQUEST_CODE);
            }
            else {
                Toast.makeText(this,"permision denied",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    String filepath = uri.getPath();
                    filepath = filepath.substring(filepath.indexOf(":") + 1);
                    String fileName = getFileName(uri);
                    String TEMP_DIR_PATH = Environment.getExternalStorageDirectory().getPath();
                    File file = new File(uri.toString());
                    File copyFile = new File(TEMP_DIR_PATH + "/" + get_file_path(uri));

//                    Uri uri=data.getData();
//                    Toast.makeText(this, uri.toString() , Toast.LENGTH_LONG).show();
//                    Toast.makeText(this, copyFile.toString(), Toast.LENGTH_LONG).show();
//                    File file=new File(data.getData().toString());
                    Dbhelper.OnCreate(copyFile.toString());
                    view_data();
                }
        }


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
    public void view_data()
    {
        Dbhelper.insert_into_list_view(Dbhelper.Get_data());
        names=Dbhelper.getNames();
        reg=Dbhelper.getReg();
        populate_list_view(Dbhelper);
    }
    public List<String> make_intigrated_string(List<String> student_name,List<String> reg)
    {
        List<String> final_string=new ArrayList<>();
        for (int i=0;i<student_name.size();i++)
            final_string.add(reg.get(i)+"\n"+student_name.get(i));
        return final_string;

    }
    public String get_file_path(Uri uri)
    {
        String path = uri.getPath();
        if (path.contains("/"))
            path=path.substring(path.lastIndexOf(":")+1);
        return path;

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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Student_file.this.arrayAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Student_file.this.arrayAdapter.getFilter().filter(newText);
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
//                        text+=names.get(selected_items_list.get(i))+"\t"+reg.get(selected_items_list.get(i))+"\n";
//
//
//                    }
//                    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.delete:
//                selected_items_list=getSeketeditem_list();
//
//                arrayAdapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,Dbhelper.getReg());
//                listView.setAdapter(arrayAdapter);
//                selection_started=false;
//                if(!selected_items_list.isEmpty())
//                {
//                    for (int i=0;i<selected_items_list.size();i++)
//                    {
//                        Dbhelper.deleteTitle(reg.get(selected_items_list.get(i)));
//                        names.remove(selected_items_list.get(i));
//                        arrayAdapter.notifyDataSetChanged();
//                    }
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

    public  void populate_list_view(datbase_handler_students DbHandler)
    {
        listview_adapter_students=new listview_adapter_students(this,DbHandler.getNames(),DbHandler.getReg());
//        arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,make_intigrated_string(DbHandler.getNames(),DbHandler.getReg()));
//        students_list_view students_list_view=new students_list_view(this,DbHandler.getNames(),DbHandler.getReg());
     //   listview_adapter=new Listview_Adapter(this,DbHandler.getNames(),DbHandler.getReg(),null,DbHandler.getid());
        listView=findViewById(R.id.list_view);
        listView.setAdapter(listview_adapter_students);
        listView.setEmptyView(findViewById(R.id.empty_list_view));

    }

    public  void add_single_item()
    {
        Intent intent = new Intent(Student_file.this, add_students.class);
        startActivity(intent);
    }
    public void open_file_activity()
    {
        if (ContextCompat.checkSelfPermission(Student_file.this, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            Intent browse_file=new Intent(Intent.ACTION_OPEN_DOCUMENT);
            browse_file.setType("application/vnd.ms-excel");
//    browse_file.setType("*/*");
            startActivityForResult(browse_file,REQUEST_CODE);
        }
        else
            requestPermission();

    }

}

