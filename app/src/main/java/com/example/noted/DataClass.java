package com.example.noted;

import java.util.ArrayList;
import java.util.stream.Stream;

public class DataClass {
    static ArrayList<String> Subjects=new ArrayList<>();
    static ArrayList<String> Streams=new ArrayList<>();
    static ArrayList<String> Topics=new ArrayList<>();
    static ArrayList<String> getData(String year,String Stream,int sem)
    {
        if(year.equals("SECOND"))
        {
            if(sem==1)
            {
                if(Stream.equals("CSCE")) {
                    Subjects.add("DSA");
                    Subjects.add("OOPS");
                    Subjects.add("DEC");
                    Subjects.add("PSS");
                    Subjects.add("PS");
                    Subjects.add("ECO");
                }
            }
        }
        return Subjects;
    }
    static ArrayList<String> getStreams()
    {
        Streams.add("CSE");
        Streams.add("CSCE");
        Streams.add("CSSE");
        return Streams;
    }

    static ArrayList<String> getTopics(String subject)
    {
        switch (subject)
        {
            case "DSA":
                Topics.add("DSA Complete");
                break;
            case "OOPS":
                Topics.add("OOPS Complete");
                break;
            case "PSS":
                Topics.add("PSS Complete");
                break;
            case "PS":
                Topics.add("2.1");
                Topics.add("2.2");
                Topics.add("2.4");
                Topics.add("3.1-3.2");
                Topics.add("3.3");
                Topics.add("3.4");
                Topics.add("3.5");
                Topics.add("3.6");
                break;

        }
        return Topics;
    }
}
