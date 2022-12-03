package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Day3 implements Challenge<Integer> {
    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(this.getClass());

        return part2(reader);
    }


    private int part1(BufferedReader reader) throws IOException {
        String line;

        int sum = 0;
        while((line = reader.readLine()) != null){
            int half = line.length() / 2;

            Set<Character> added = new HashSet<>();
            String firstHalf = line.substring(0, half);
            String secondHalf = line.substring(half);

            for(char c : firstHalf.toCharArray()){
                if(secondHalf.contains(c + "") && added.add(c)){
                    //System.out.println("Both have " + c);
                    int num = c;
                    if(c >= 'A' && c <= 'Z'){
                        num -= (65 - 27) ;
                    }else{
                        num -= 96;
                    }
                    //System.out.println(num);
                    sum += num;
                    break;
                }
            }
            //System.out.println("\n------------");
        }
        return sum;
    }

    public int part2(BufferedReader reader) throws IOException {
        String line;
        int count = 0;

        HashMap<Character, Integer> map = new HashMap<>();
        Set<Character> added = new HashSet<>();

        int sum = 0;
        while((line = reader.readLine()) != null){
            if(count >= 3) {
                count = 0;
                map.clear();
                //System.out.println("Reached 3 line\n");
            }

            //System.out.println("Reading " + line);
            for(char c : line.toCharArray()){
                int charCount = map.computeIfAbsent(c, (character) -> 0);

                //System.out.println("Char[" + c + "] count: " + charCount);
                if(added.add(c)){
                    //System.out.println("Adding into map with value " + (charCount + 1));
                    if(charCount == 2){
                        //System.out.println("Found character that exist in 3 line: " + c);
                        if(c >= 'A' && c <= 'Z'){
                            sum += (c - (65 - 27)) ;
                        }else{
                            sum += c - 96;
                        }

                        count = 3;
                        break;
                    }
                    map.put(c, charCount + 1);
                }
            }
            added.clear();
            count++;
        }

        return sum;
    }
}
