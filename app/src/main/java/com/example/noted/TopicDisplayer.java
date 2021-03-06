package com.example.noted;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TopicDisplayer extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView TopicList;
    String pdfUrl="BLANK";
    Set<String> Topics=new HashSet<>();
    ArrayList<String> TopicsList=new ArrayList<>();
    ArrayAdapter TopicsAdapter;
    String subject;

    final String sharedPrefName="URLSHAREDPREF";
    SharedPreferences sharedPreferences;

    final String TopicssharedPrefName="TOPICSSHAREDPREF";
    SharedPreferences TopicssharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_displayer);
        subject=getIntent().getStringExtra("SUBJECT");
        TopicList=findViewById(R.id.TopicsList);
        sharedPreferences=getSharedPreferences(sharedPrefName,MODE_PRIVATE);
        TopicList.setOnItemClickListener(TopicDisplayer.this);
        //Topics=DataClass.getTopics(subject);
        TopicssharedPreferences=getSharedPreferences(TopicssharedPrefName,MODE_PRIVATE);
        if(TopicssharedPreferences.contains(subject))
        {
            Topics.addAll(TopicssharedPreferences.getStringSet(subject,new HashSet<String>()));
            //Toast.makeText(TopicDisplayer.this,"LOADED FROM SHAREDPREF",Toast.LENGTH_SHORT).show();
            AfterCreation();
        }
        else
        {
            TopicAddweb();
        }



    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        if(sharedPreferences.contains(TopicsList.get(position)))
        {
            String URLSHARED=sharedPreferences.getString(TopicsList.get(position),"");
            AddUrl(URLSHARED,position);
            //Toast.makeText(TopicDisplayer.this,"LOADED FROM SHAREDPREF",Toast.LENGTH_SHORT).show();
        }
        else {
            ParseQuery<ParseObject> pdfURLQuery = ParseQuery.getQuery("URL");
            pdfURLQuery.whereEqualTo("Topic", TopicsList.get(position));
            pdfURLQuery.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString(TopicsList.get(position),object.get("URL").toString());
                    editor.apply();
                    //Toast.makeText(TopicDisplayer.this,"LOADED FROM WEB",Toast.LENGTH_SHORT).show();
                    AddUrl(object.get("URL").toString(), position);
                }
            });

        }

    }
    @Override
    public void onBackPressed() {
        Topics.clear();
        TopicsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, TopicsList);
        TopicList.setAdapter(TopicsAdapter);
        super.onBackPressed();

    }

    void AfterCreation()
    {
        TopicsList.clear();
        TopicsList.addAll(Topics);
        Collections.sort(TopicsList);
        if(TopicsList.size()<=0)
        {
            Toast.makeText(this,"NOTHING TO DISPLAY",Toast.LENGTH_SHORT).show();
            finish();
        }
        else
        {
            //Toast.makeText(TopicDisplayer.this,"NO. OF TOPICS"+Topics.size(),Toast.LENGTH_SHORT).show();
            TopicsAdapter=new ArrayAdapter(TopicDisplayer.this,android.R.layout.simple_list_item_1,TopicsList){
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
            intent.putExtra("SubjectName",TopicsList.get(position));
            startActivity(intent);
            pdfUrl="BLANK";
        }
    }

    void TopicAddweb()
    {
        Topics.clear();
        TopicsList.clear();
        TopicsAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, TopicsList);
        TopicList.setAdapter(TopicsAdapter);
        ParseQuery<ParseObject> subjectsQuery = ParseQuery.getQuery("URL");
        subjectsQuery.whereEqualTo("Subject", subject);
        subjectsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0) {
                    for (ParseObject data : objects) {
                        Topics.add(data.get("Topic").toString());
                    }
                    //Toast.makeText(TopicDisplayer.this, "Topics RETRIEVED", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor=TopicssharedPreferences.edit();
                    editor.clear();
                    editor.apply();
                    editor.putStringSet(subject, Topics);
                    editor.apply();
                    //Toast.makeText(TopicDisplayer.this,"LOADED FROM WEB",Toast.LENGTH_SHORT).show();
                    AfterCreation();
                    Toast.makeText(TopicDisplayer.this,"NEW DATA LOADED",Toast.LENGTH_SHORT).show();

                } else{
                    e.printStackTrace();
                    Toast.makeText(TopicDisplayer.this,"NEW DATA COULD NOT BE LOADED",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.refreshmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.refresh)
        {
            TopicAddweb();
        }
        return super.onOptionsItemSelected(item);
    }
}