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
           case "PS":
               switch (topics)
               {
                   case "2.1-2.2":
                       url="https://drive.google.com/file/d/1_kpYb7mtvo6ijXDGfuZJ61kpVBa_OJJB/view?usp=sharing";
                       break;
                   case "2.4":
                       url="https://drive.google.com/file/d/1EOcGGqB8orT2pvvVPRmEqxziQ97B8OFK/view?usp=sharing";
                        break;
                   case "2.5":
                       url="https://drive.google.com/file/d/1s71Jlj4iyM917wy3I9q0A2tzSnybiQSG/view?usp=sharing";
                        break;
                   case "3.1-3.2":
                        url="https://drive.google.com/file/d/1G63NO5z4wGE0XDUAO29gnyaZzU3NQ5fa/view?usp=sharing";
                        break;
                   case "3.3":
                       url="https://drive.google.com/file/d/1DkrYAcWjgDXN6Yl630lKrRnrxmurJIj0/view?usp=sharing";
                       break;
                   case "3.4":
                       url="https://drive.google.com/file/d/1O8TWGJy6v15ZE0PO7l-J3k-rUfc-vvlr/view?usp=sharing";
                       break;
                   case "3.5":
                       url="https://drive.google.com/file/d/1AypWupmocztfxiqswBJB23nyls-bW_-e/view?usp=sharing";
                       break;
                   case "3.6":
                       url="https://drive.google.com/file/d/10Xgtd6K2uxJYNouimLtnq5kBsmKKPc0w/view?usp=sharing";
                       break;
               }
               break;
           case "ECO":
               switch (topics)
               {
                   case "ECONOMICS Complete":
                       url="https://drive.google.com/file/d/1qrhAAeYRBENR2nLptzjtxhuu4iy02kQe/view?usp=sharing";
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
