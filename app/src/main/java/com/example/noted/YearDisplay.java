package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.ParseUser;

public class YearDisplay extends AppCompatActivity implements View.OnClickListener {

    String sem="FIRST";
    Button f,s,t,fo,semChange,godMode;
    TextView semText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_display);
        godMode=findViewById(R.id.godMode);
        if(ParseUser.getCurrentUser().get("AddingAuth").equals("True"))
        {
            godMode.setVisibility(View.VISIBLE);
            godMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(YearDisplay.this,AdderPage.class);
                    startActivity(intent);
                }
            });
        }
        else
        {
            godMode.setVisibility(View.GONE);
        }
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
        setTitle("WELCOME "+ParseUser.getCurrentUser().getUsername());



    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.semChange)
        {
            if(sem.equals("FIRST"))
            {
                sem="SECOND";
                semText.setText(sem);
            }
            else
            {
                sem="FIRST";
                semText.setText(sem);
            }
        }
        else if(v.getId()==R.id.fstYear)
        {
            String year = "FIRST";
            Intent intent = new Intent(YearDisplay.this, SubjectDisplayer.class);
            intent.putExtra("YEAR", year);
            intent.putExtra("SEMESTER","FIRST");
            intent.putExtra("STREAM","ALL");
            startActivity(intent);
        }
        else {
            String year = "";
            switch (v.getId()) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.LogoutUser) {
            ParseUser.logOutInBackground();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}