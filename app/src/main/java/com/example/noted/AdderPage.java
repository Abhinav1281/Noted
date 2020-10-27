package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class AdderPage extends AppCompatActivity {
    EditText year,sem,subject,topic,url;
    Button upload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adder_page);
        year=findViewById(R.id.editYear);
        sem=findViewById(R.id.editSem);
        subject=findViewById(R.id.editSubject);
        topic=findViewById(R.id.editTopic);
        url=findViewById(R.id.editURL);
        upload=findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseObject urlAdder = new ParseObject("URL");
// Store an object
                urlAdder.put("Year", year.getText().toString());
                urlAdder.put("Sem", sem.getText().toString());
                urlAdder.put("Subject", subject.getText().toString());
                urlAdder.put("Topic",topic.getText().toString());
                urlAdder.put("URL",url.getText().toString());
// Saving object
                urlAdder.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            // Success
                            Toast.makeText(AdderPage.this,"SAVED SUCCESSFULLY",Toast.LENGTH_SHORT).show();
                        } else {
                            // Error
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}