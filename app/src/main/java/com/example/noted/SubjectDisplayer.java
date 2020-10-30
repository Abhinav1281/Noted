package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

public class SubjectDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String year,stream,sem;
    ImageButton reloadSubject;
    ListView subjects;
    ArrayAdapter arrayAdapter;
    ArrayList<String> subjectsList=new ArrayList<>();
    Set<String> subject=new HashSet<>();

    final String sharedPrefName="SUBJECTSSHAREDPREF";
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_displayer);
        year=getIntent().getStringExtra("YEAR");
        sem = getIntent().getStringExtra("SEMESTER");
        stream=getIntent().getStringExtra("STREAM");
        subjects = findViewById(R.id.Subjects);
        subjects.setOnItemClickListener(SubjectDisplayer.this);
        sharedPreferences=getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        reloadSubject=findViewById(R.id.reloadSubject);
        reloadSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subjectAddWeb();
            }
        });
           // Toast.makeText(SubjectDisplayer.this, "GETTING DATA", Toast.LENGTH_SHORT).show();
           if(sharedPreferences.contains(stream))
           {
               subject= sharedPreferences.getStringSet(stream,new HashSet<String>());
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
    void adderSubject(String s)
    {
        subject.add(s);

    }

    void AfterCreation()
    {
        subjectsList.addAll(subject);
        if (subjectsList.size() <= 0) {
            Toast.makeText(this, "NOTHING TO DISPLAY=" + subjectsList.size(), Toast.LENGTH_SHORT).show();
            finish();
        } else {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectsList) {
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View row = super.getView(position, convertView, parent);
                    row.setBackground((getDrawable(R.drawable.listbg)));
                    row.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return row;
                }
            };
            subjects.setAdapter(arrayAdapter);
        }
    }

    void subjectAddWeb()
    {
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
                if (objects.size() > 0) {
                    for (ParseObject data : objects) {
                        adderSubject(data.get("Subject").toString());
                    }
                    //  Toast.makeText(SubjectDisplayer.this, "SUBJECTS RETRIEVED", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putStringSet(stream,subject);
                    editor.apply();
                  //  Toast.makeText(SubjectDisplayer.this,"LOADED FROM WEB",Toast.LENGTH_SHORT).show();

                    AfterCreation();

                } else
                    e.printStackTrace();
            }
        });
    }
    }




