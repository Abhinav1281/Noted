package com.example.noted;

import android.content.Intent;

public class PdfAdapter {
    static String url;
    static String ShowPdf(String subject)
    {
        if(subject.equals("DSA"))
            url="https://drive.google.com/file/d/1wOYfoAnii8vlFZBYDF2cGX9hXoP5o68W/view?usp=sharing";
        else if(subject.equals("OOPS"))
                url="https://drive.google.com/file/d/1BMLx4kfgOwlr6taSrIQIv5W045xbaokZ/view?usp=sharing";
        else if(subject.equals("PSS"))
            url="https://drive.google.com/file/d/1uLCgPG9yNs6kOSnB0TqtvSBDw0Zy4_bl/view?usp=sharing";
        else
            url="BLANK";
        return url;
    }
}
