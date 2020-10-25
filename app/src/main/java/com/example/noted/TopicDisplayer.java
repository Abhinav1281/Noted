package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TopicDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView TopicList;
    ArrayList<String> Topics;
    ArrayAdapter TopicsAdapter;
    String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_displayer);
        subject=getIntent().getStringExtra("SUBJECT");
        TopicList=findViewById(R.id.TopicsList);
        Topics=DataClass.getTopics(subject);
        TopicsAdapter=new ArrayAdapter(TopicDisplayer.this,android.R.layout.simple_list_item_1,Topics);
        TopicList.setAdapter(TopicsAdapter);
        TopicList.setOnItemClickListener(TopicDisplayer.this);
        if(Topics.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String pdfUrl=PdfAdapter.ShowPdf(Topics.get(position),subject);
        if(pdfUrl.equals("BLANK"))
        {
            Toast.makeText(TopicDisplayer.this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Intent intent=new Intent(TopicDisplayer.this,PdfViewer.class);
            intent.putExtra("PDFURL",pdfUrl);
            intent.putExtra("SubjectName",Topics.get(position));
            startActivity(intent);
            pdfUrl="";
        }

    }
    @Override
    public void onBackPressed() {
        Topics.clear();
        TopicsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Topics);
        TopicList.setAdapter(TopicsAdapter);
        super.onBackPressed();

    }
}