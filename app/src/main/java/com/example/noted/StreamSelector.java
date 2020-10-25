package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class StreamSelector extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String year,stream;
    int sem;
    ListView streamList;
    ArrayList<String> streams;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_selector);
        sem=getIntent().getIntExtra("SEMESTER",1);
        year=getIntent().getStringExtra("YEAR");
        streamList=findViewById(R.id.StreamList);
        streams=DataClass.getStreams();
        arrayAdapter=new ArrayAdapter(StreamSelector.this,android.R.layout.simple_list_item_1,streams){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View row=super.getView(position,convertView,parent);
                row.setBackground((getDrawable(R.drawable.listbg)));
                row.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                return row;
            }
        };
        streamList.setAdapter(arrayAdapter);
        streamList.setOnItemClickListener(StreamSelector.this);
        setTitle(year+" YEAR::"+sem+" SEMESTER");
        if(streams.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(StreamSelector.this, SubjectDisplayer.class);
        intent.putExtra("YEAR", year);
        intent.putExtra("SEMESTER",sem);
        intent.putExtra("STREAM",streams.get(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        streams.clear();
        arrayAdapter=new ArrayAdapter(StreamSelector.this,android.R.layout.simple_list_item_1,streams);
        streamList.setAdapter(arrayAdapter);
        super.onBackPressed();
    }
}