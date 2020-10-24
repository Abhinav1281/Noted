package com.example.noted;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

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
    }

    @Override
    public void onBackPressed() {
        webview.loadUrl("about:blank");
        super.onBackPressed();
    }
}