package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity {

    EditText username,email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.editSignupUserName);
        email=findViewById(R.id.editSignupEmail);
        pass=findViewById(R.id.editSignupPass);
        if(ParseUser.getCurrentUser()!=null)
            Login();
    }
    public void SigninTapped(View view) {
        ParseUser user = new ParseUser();
        if (email.getText().toString().equals("") || username.getText().toString().equals("") || pass.getText().toString().equals(""))
            Toast.makeText(MainActivity.this, "FIELD CANNOT BE EMPTY", Toast.LENGTH_SHORT).show();
        else {
            user.setEmail(email.getText().toString());
            user.setUsername(username.getText().toString());
            user.setPassword(pass.getText().toString());
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("SIGNING IN "+username.getText().toString());
            progressDialog.show();
            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(MainActivity.this, "SIGN UP SUCCESSFUL " + username.getText().toString(), Toast.LENGTH_SHORT).show();
                        Login();
                    }else
                        Toast.makeText(MainActivity.this, "SIGN UP NOT SUCCESS " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
    }

    public void Login(View view)
    {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }
    public void Login()
    {
        Intent intent=new Intent(MainActivity.this,Login.class);
        startActivity(intent);
    }
}