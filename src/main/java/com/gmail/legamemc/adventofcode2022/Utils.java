package com.gmail.legamemc.adventofcode2022;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils {

    public static BufferedReader getInputs(Class<?> clazz) throws NullPointerException{
        String fileName = clazz.getSimpleName() + "Input.txt";
        InputStream inputStream = clazz.getResourceAsStream("/" + fileName);
        if(inputStream == null){
            System.out.println("Unable to find " + fileName);
            throw new NullPointerException();
        }

        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
