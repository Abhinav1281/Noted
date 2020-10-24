package com.example.noted;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebView;

import com.parse.ParseUser;

public class PdfViewer extends AppCompatActivity {
    WebView webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);
        webview = (WebView) findViewById(R.id.web);
        webview.getSettings().setJavaScriptEnabled(true);
        String pdf = getIntent().getStringExtra("PDFURL");
        webview.loadUrl(pdf);
        setTitle(getIntent().getStringExtra("SubjectName"));
    }

    @Override
    public void onBackPressed() {
        webview.loadUrl("about:blank");
        super.onBackPressed();
    }


}