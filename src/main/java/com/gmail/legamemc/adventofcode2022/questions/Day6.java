package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.util.ArrayDeque;

public class Day6 implements Challenge<Integer> {

    private static final int DISTINCT_CHARACTER_COUNT = 14;
    @Override
    public Integer execute() throws Exception {

        BufferedReader reader = Utils.getInputs(getClass());

        String line = reader.readLine();

        char[] arr = line.toCharArray();

        ArrayDeque<Character> arrayDeque = new ArrayDeque<>();

        int x = 0;
        for(;x < arr.length && arrayDeque.size() != DISTINCT_CHARACTER_COUNT; x++){
            if(arrayDeque.contains(arr[x])){
                char removed;
                do{
                    removed = arrayDeque.removeFirst();
                }while(removed != arr[x]);

            }
            arrayDeque.add(arr[x]);
            //System.out.println(arrayDeque);
        }


        return x;
    }
}
