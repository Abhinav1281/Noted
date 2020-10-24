package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StreamSelector extends AppCompatActivity implements View.OnClickListener {

    String year,stream;
    int sem;
    Button cs,csc,css;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream_selector);
        sem=getIntent().getIntExtra("SEMESTER",1);
        year=getIntent().getStringExtra("YEAR");
        cs=findViewById(R.id.CSE);
        csc=findViewById(R.id.CSCE);
        css=findViewById(R.id.CSSE);
        cs.setOnClickListener(StreamSelector.this);
        csc.setOnClickListener(StreamSelector.this);
        css.setOnClickListener(StreamSelector.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.CSE:
                stream="CSE";
                break;
            case R.id.CSCE:
                stream="CSCE";
                break;
            case R.id.CSSE:
                stream="CSSE";
                break;
        }
        Intent intent = new Intent(StreamSelector.this, SubjectDisplayer.class);
        intent.putExtra("YEAR", year);
        intent.putExtra("SEMESTER",sem);
        intent.putExtra("STREAM",stream);
        startActivity(intent);
    }
}