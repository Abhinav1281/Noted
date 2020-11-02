package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class SubjectDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String year,stream,sem;

    ListView subjects;
    ArrayAdapter arrayAdapter;
    ArrayList<String> subjectsList=new ArrayList<>();
    Set<String> subject=new HashSet<>();

    final String sharedPrefName="SUBJECTSSHAREDPREF";
    SharedPreferences subjectsharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_displayer);
        year=getIntent().getStringExtra("YEAR");
        sem = getIntent().getStringExtra("SEMESTER");
        stream=getIntent().getStringExtra("STREAM");

        subjects = findViewById(R.id.Subjects);
        subjects.setOnItemClickListener(SubjectDisplayer.this);
        subjectsharedPreferences=getSharedPreferences(sharedPrefName,MODE_PRIVATE);

        if(!isNetworkAvailable())
        {
            final PrettyDialog loading=new PrettyDialog(this)
                    .setTitle("LOADING");

            loading.setIcon(R.drawable.loading);
            loading.setCanceledOnTouchOutside(false);
            loading.setIconTint(R.color.pdlg_color_red);
            loading.show();
            loading.setMessage("Network not available.\n Check Network Settings");
            loading.addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_black, new PrettyDialogCallback() {
                @Override
                public void onClick() {
                    finish();
                }
            });
        }
           // Toast.makeText(SubjectDisplayer.this, "GETTING DATA", Toast.LENGTH_SHORT).show();
           if(subjectsharedPreferences.contains(stream))
           {
               subject.addAll(subjectsharedPreferences.getStringSet(stream,new HashSet<String>()));
             //  Toast.makeText(SubjectDisplayer.this,"LOADED FROM SHAREDPREF",Toast.LENGTH_SHORT).show();
               AfterCreation();
           }
            else
           {
               subjectAddWeb();
           }

        }


    @Override
    public void onBackPressed() {
        subjectsList.clear();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectsList);
        subjects.setAdapter(arrayAdapter);
        super.onBackPressed();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            Intent intent=new Intent(SubjectDisplayer.this,TopicDisplayer.class);
            intent.putExtra("SUBJECT",subjectsList.get(position));
            startActivity(intent);
        }


    void AfterCreation()
    {
        subjectsList.addAll(subject);
        if (subjectsList.size() <= 0) {
            PrettyDialog noneDisplay=new PrettyDialog(this)
                    .setTitle("UNDER CONSTRUCTION")
                    .setMessage("Sorry this is not available right now");
            noneDisplay.setIcon(R.drawable.buildicon);
            noneDisplay.setCancelable(false);
            noneDisplay.addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_black, new PrettyDialogCallback() {
                @Override
                public void onClick() {
                    finish();
                }
            });
            noneDisplay.show();
        } else {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectsList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View row = super.getView(position, convertView, parent);
                    row.setBackground((getDrawable(R.drawable.button2bg)));
                    row.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return row;
                }
            };
            subjects.setAdapter(arrayAdapter);
        }
    }

    void subjectAddWeb()
    {
        subject.clear();
        subjectsList.clear();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectsList);
        subjects.setAdapter(arrayAdapter);
        ParseQuery<ParseObject> subjectsQuery = ParseQuery.getQuery("URL");
        subjectsQuery.whereEqualTo("Year", year);
        subjectsQuery.whereEqualTo("Sem",sem);
        subjectsQuery.whereEqualTo("Stream",stream);
        subjectsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null) {
                    for (ParseObject data : objects) {
                        subject.add(data.get("Subject").toString());
                    }
                    //  Toast.makeText(SubjectDisplayer.this, "SUBJECTS RETRIEVED", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=subjectsharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    editor.putStringSet(stream,subject);
                    editor.apply();

                  //  Toast.makeText(SubjectDisplayer.this,"LOADED FROM WEB",Toast.LENGTH_SHORT).show();

                    AfterCreation();
                    Toast.makeText(SubjectDisplayer.this,"NEW DATA LOADED",Toast.LENGTH_SHORT).show();

                } else {
                    e.printStackTrace();
                    Toast.makeText(SubjectDisplayer.this, "NEW DATA COULD NOT BE LOADED", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.refreshmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.refresh)
        {
            subjectAddWeb();
        }
        return super.onOptionsItemSelected(item);
    }
}




