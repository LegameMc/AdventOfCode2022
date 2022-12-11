package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.HashSet;

public class Day10 implements Challenge<Integer> {
    @Override
    public Integer execute() throws Exception {

        BufferedReader reader = Utils.getInputs(getClass());

        String line;
        HashMap<Integer, Integer> map = new HashMap<>();
        int x = 1;
        int loop = 1;

        String[] screen = new String[6];

        while((line = reader.readLine()) != null){
            if(line.startsWith("addx")){
                int num = Integer.parseInt(line.split(" ")[1]);

                map.put(loop, x);
                cycle(loop, x, screen);

                map.put(loop + 1, x);;
                cycle(loop + 1, x, screen);

                loop += 2;
                x += num;
            }else{
                map.put(loop, x);
                cycle(loop, x, screen);
                loop++;
            }
        }

        int[] arr = { 20, 60, 100, 140, 180, 220};

        int sum = 0;
        for(int a : arr){
            sum += (a * map.get(a));
        }

        for(String s : screen){
            System.out.println(s);
        }

        return sum;
    }

    private void cycle(int cycle, int spritePos, String[] screen){
        int row = (cycle - 1) / 40;

        String line = screen[row];

        if(line == null){
            line = "";
        }

        int length = line.length() + 1;
        if(length >= spritePos && length < spritePos + 3){
            line += "#";
        }else{
            line += ".";
        }

        screen[row] = line;
    }
}
