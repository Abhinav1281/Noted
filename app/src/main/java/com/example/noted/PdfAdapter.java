package com.example.noted;

import android.content.Intent;

public class PdfAdapter {
    static String url;
    static String ShowPdf(String topics,String subject)
    {
       switch (subject)
       {
           case "DSA":
               switch (topics)
               {
                   case "DSA Complete":
                       url="https://drive.google.com/file/d/1wOYfoAnii8vlFZBYDF2cGX9hXoP5o68W/view?usp=sharing";
                       break;
               }
                break;
           case "OOPS":
               switch (topics)
               {
                   case "OOPS Complete":
                       url="https://drive.google.com/file/d/1BMLx4kfgOwlr6taSrIQIv5W045xbaokZ/view?usp=sharing";
                       break;
               }
               break;
           case "PSS":
               switch (topics)
               {
                   case "PSS Complete":
                       url="https://drive.google.com/file/d/1uLCgPG9yNs6kOSnB0TqtvSBDw0Zy4_bl/view?usp=sharing";
                       break;
               }
               break;
           default:
               url="BLANK";
               break;
       }
        return url;
    }
}
