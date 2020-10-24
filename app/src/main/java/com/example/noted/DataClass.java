package com.example.noted;

import java.util.ArrayList;
import java.util.stream.Stream;

public class DataClass {
    static ArrayList<String> Subjects=new ArrayList<>();
    static ArrayList<String> Streams=new ArrayList<>();
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
}
