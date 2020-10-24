package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class YearDisplay extends AppCompatActivity implements View.OnClickListener {

    int sem=1;
    Button f,s,t,fo,semChange;
    TextView semText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_display);
        f=findViewById(R.id.fstYear);
        s=findViewById(R.id.sYear);
        t=findViewById(R.id.tYear);
        fo=findViewById(R.id.foYear);
        f.setOnClickListener(YearDisplay.this);
        s.setOnClickListener(YearDisplay.this);
        t.setOnClickListener(YearDisplay.this);
        fo.setOnClickListener(YearDisplay.this);
        semChange=findViewById(R.id.semChange);
        semChange.setOnClickListener(YearDisplay.this);
        semText=findViewById(R.id.semText);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.semChange)
        {
            if(sem==1)
            {
                sem=2;
                semText.setText("2nd");
            }
            else
            {
                sem=1;
                semText.setText("1st");
            }
        }
        else {
            String year = "";
            switch (v.getId()) {
                case R.id.fstYear:
                    year = "FIRST";
                    break;
                case R.id.sYear:
                    year = "SECOND";
                    break;
                case R.id.tYear:
                    year = "THIRD";
                    break;
                case R.id.foYear:
                    year = "FOURTH";
                    break;
            }
            Intent intent = new Intent(YearDisplay.this, StreamSelector.class);
            intent.putExtra("YEAR", year);
            intent.putExtra("SEMESTER",sem);
            startActivity(intent);
        }
    }
}