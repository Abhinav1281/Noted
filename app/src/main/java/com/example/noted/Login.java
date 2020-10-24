package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class Login extends AppCompatActivity {

    EditText username,pass;
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if(ParseUser.getCurrentUser()!=null)
        {
            Loggedin();
        }
        username=findViewById(R.id.editLoginUser);
        pass=findViewById(R.id.editLoginPass);
        login=findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("") || pass.getText().toString().equals(""))
                    Toast.makeText(Login.this, "FIELD CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
                else {
                    final ProgressDialog progressDialog = new ProgressDialog(Login.this);
                    progressDialog.setMessage("SIGNING IN " + username.getText().toString());
                    progressDialog.show();
                    ParseUser.logInInBackground(username.getText().toString(),
                            pass.getText().toString(),
                            new LogInCallback() {
                                @Override
                                public void done(ParseUser user, ParseException e) {
                                    if (user != null && e == null) {
                                        Toast.makeText(Login.this, "LOGIN SUCCESSFUL " + username.getText().toString(), Toast.LENGTH_SHORT).show();
                                        Loggedin();
                                    } else
                                        Toast.makeText(Login.this, "SIGN UP NOT SUCCESS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });

                }
            }
        });
    }

    private void Loggedin()
    {
        Intent intent=new Intent(Login.this,YearDisplay.class);
        startActivity(intent);
    }
}