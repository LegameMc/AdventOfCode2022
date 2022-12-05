package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.sql.Array;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day5 implements Challenge<String> {

    private static final int part = 2;
    private static final Pattern pattern = Pattern.compile("[0-9]+");

    @Override
    public String execute() throws Exception {
        BufferedReader reader = Utils.getInputs(getClass());

        String line;
        boolean startMove = false;

        HashMap<Integer, LinkedList<Character>> map = new HashMap<>();
        while((line = reader.readLine()) != null){
            if(line.isEmpty()){
                startMove = true;

                //map.forEach(((id, list) -> System.out.println(id + ": " + list)));
                continue;
            }

            if(startMove){
                //System.out.println("Processing " + line);
                Matcher matcher = pattern.matcher(line);
                int[] instruction = new int[3]; // 0 = move X, 1 = from, 2 = to
                int index = 0;
                while (matcher.find()){
                    int start = matcher.start();
                    int end = matcher.end();
                    String sub = line.substring(start, end);
                    //System.out.println("Start: " + start + ", End: " + end + ", " + sub);
                    instruction[index++] = Integer.parseInt(sub);
                }
                //System.out.println("Current: " + map.get(instruction[1]));

                LinkedList<Character> from = map.get(instruction[1]);
                LinkedList<Character> to = map.get(instruction[2]);

                if(part == 1){
                    for(int x = 0; x < instruction[0]; x++){
                        to.add(from.removeLast());
                    }
                }else{
                    for(int x = instruction[0]; x > 0; x--){
                        to.add(from.remove(from.size() - x));
                    }
                }

                //System.out.println("Updated from:" + " " + from);
                //System.out.println("Updated to:" + " " + map.get(instruction[2]));
            }else{
                char[] arr = line.toCharArray();
                for(int x = -1, y = 1; x < arr.length; x+= 4, y++){
                    char item = arr[x + 2];

                    if(item >= 48 && item <= 57) break;
                    if(item == ' ') continue;
                    LinkedList<Character> stack = map.computeIfAbsent(y, (a) -> new LinkedList<>());

                    stack.addFirst(item);
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for(LinkedList<Character> list : map.values()){
            result.append(list.getLast());
        }
        return result.toString();
    }
}
