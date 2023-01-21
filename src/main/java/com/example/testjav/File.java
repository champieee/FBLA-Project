package com.example.testjav;



import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class File {

    ArrayList<String> list = new ArrayList<String>();

    public ArrayList getFile() throws FileNotFoundException {
        Scanner s = new Scanner(new java.io.File("words_alpha.txt"));
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();
        return list;
    }

}
