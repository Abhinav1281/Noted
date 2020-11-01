package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import libs.mjn.prettydialog.PrettyDialog;
import libs.mjn.prettydialog.PrettyDialogCallback;

public class IssueActivity extends AppCompatActivity {

    EditText textTopic,textDes;
    Button uploadIssue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);
        textTopic=findViewById(R.id.issueTopic);
        textDes=findViewById(R.id.IssueDetails);
        uploadIssue=findViewById(R.id.uploadIssue);

        uploadIssue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(textTopic.getText().toString().equals("")||textDes.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),"FIELDS CANNOT BE EMPTY",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    ParseObject issueAdder=new ParseObject("ISSUE");
                    issueAdder.put("IssueGeneratedBy", ParseUser.getCurrentUser().getUsername());
                    issueAdder.put("IssueTopic",textTopic.getText().toString());
                    issueAdder.put("IssueDes",textTopic.getText().toString());
                    final PrettyDialog issueAdd=new PrettyDialog(IssueActivity.this)
                            .setTitle("ADDING ISSUE")
                            .setMessage("WAIT...");
                    issueAdd.setIcon(R.drawable.report_issue);
                    issueAdd.setCancelable(false);
                    issueAdd.show();
                    issueAdder.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                issueAdd.setMessage("ISSUE ADDED\nWILL BE REVIEWED SHORTLY");
                            }
                            else
                            {
                                issueAdd.setMessage("THERE IS AN ERROR\nPLEASE TRY AGAIN LATER");
                            }
                            issueAdd.addButton("OK", R.color.pdlg_color_white, R.color.pdlg_color_black, new PrettyDialogCallback() {
                                @Override
                                public void onClick() {
                                    finish();
                                }
                            });
                        }
                    });
                }
            }
        });
    }
}