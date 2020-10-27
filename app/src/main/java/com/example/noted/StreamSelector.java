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

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class StreamSelector extends AppCompatActivity implements AdapterView.OnItemClickListener {

    String year,sem;

    ListView streamList;
    ArrayList<String> streams=new ArrayList<>();
    HashSet<String> stream=new HashSet<>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_selector);
        sem=getIntent().getStringExtra("SEMESTER");
        year=getIntent().getStringExtra("YEAR");
        streamList=findViewById(R.id.StreamList);
        streamList.setOnItemClickListener(StreamSelector.this);
       //Toast.makeText(StreamSelector.this, "GETTING DATA of "+year+" "+sem, Toast.LENGTH_SHORT).show();
        ParseQuery<ParseObject> streamsQuery = ParseQuery.getQuery("URL");
        streamsQuery.whereEqualTo("Year", year);
        streamsQuery.whereEqualTo("Sem",sem);
        streamsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e==null) {
                    for (ParseObject data : objects) {
                        stream.add(data.get("Stream").toString());
                    }
                    Toast.makeText(StreamSelector.this, "STREAMS RETRIEVED", Toast.LENGTH_SHORT).show();
                    AfterCreation();

                } else
                    e.printStackTrace();
            }
        });
        //streams=DataClass.getStreams();


    }

    void AfterCreation()
    {
        streams.addAll(stream);
        if(streams.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }
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

        setTitle(year+" YEAR::"+sem+" SEMESTER");

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