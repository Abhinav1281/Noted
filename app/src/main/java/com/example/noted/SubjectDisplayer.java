package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class SubjectDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String year,stream;
    int sem;
    ListView subjects;
    ArrayAdapter arrayAdapter;
    ArrayList<String> subjectsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_displayer);

        sem=getIntent().getIntExtra("SEMESTER",1);
        year=getIntent().getStringExtra("YEAR");
        stream=getIntent().getStringExtra("STREAM");
        subjects=findViewById(R.id.Subjects);
        subjects.setOnItemClickListener(SubjectDisplayer.this);

        setTitle(year+" YEAR::"+sem+" SEMESTER::"+stream);

        subjectsList=DataClass.getData(year,stream,sem);
        if(subjectsList.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, subjectsList);
            subjects.setAdapter(arrayAdapter);
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

    }


