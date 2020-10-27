package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TopicDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView TopicList;
    String pdfUrl="BLANK";
    ArrayList<String> Topics=new ArrayList<>();
    ArrayAdapter TopicsAdapter;
    String subject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_displayer);
        subject=getIntent().getStringExtra("SUBJECT");
        TopicList=findViewById(R.id.TopicsList);

        TopicList.setOnItemClickListener(TopicDisplayer.this);
        //Topics=DataClass.getTopics(subject);
        ParseQuery<ParseObject> subjectsQuery = ParseQuery.getQuery("URL");
        subjectsQuery.whereEqualTo("Subject", subject);
        subjectsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    for (ParseObject data : objects) {
                        Topics.add(data.get("Topic").toString());
                    }
                    Toast.makeText(TopicDisplayer.this, "Topics RETRIEVED", Toast.LENGTH_SHORT).show();
                    AfterCreation();

                } else
                    e.printStackTrace();
            }
        });



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

        ParseQuery<ParseObject> pdfURLQuery=ParseQuery.getQuery("URL");
        pdfURLQuery.whereEqualTo("Topic",Topics.get(position));
       pdfURLQuery.getFirstInBackground(new GetCallback<ParseObject>() {
           @Override
           public void done(ParseObject object, ParseException e) {
              AddUrl(object.get("URL").toString(),position);
           }
       });



    }
    @Override
    public void onBackPressed() {
        Topics.clear();
        TopicsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Topics);
        TopicList.setAdapter(TopicsAdapter);
        super.onBackPressed();

    }

    void AfterCreation()
    {
        if(Topics.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            Toast.makeText(TopicDisplayer.this,"NO. OF TOPICS"+Topics.size(),Toast.LENGTH_SHORT).show();
            TopicsAdapter=new ArrayAdapter(TopicDisplayer.this,android.R.layout.simple_list_item_1,Topics){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                    View row=super.getView(position,convertView,parent);
                    row.setBackground((getDrawable(R.drawable.listbg)));
                    row.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    return row;
                }
            };
            TopicList.setAdapter(TopicsAdapter);
        }
    }

    void AddUrl(String s,int p)
    {
        pdfUrl=s;
        AfterClick(p);
    }

    void AfterClick(int position)
    {
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
            pdfUrl="BLANK";
        }
    }

}