package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;

public class Day4 implements Challenge<Integer> {

    private static final int part = 2;
    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(getClass());

        String line;

        int fullyContained = 0;
        while((line = reader.readLine()) != null){
            System.out.println(line);
            String[] split = line.split(",");

            int[][] tda = new int[2][3];

            tda[0] = getStartEnd(split[0]);
            tda[1] = getStartEnd(split[1]);

            if(part == 1){
                if(tda[0][2] > tda[1][2]){
                    //System.out.println("A longer than B");
                    if(tda[1][0] >= tda[0][0] && tda[1][1] <= tda[0][1]){
                        System.out.println();
                        fullyContained++;
                    }
                }else if(tda[0][2] == tda[1][2]){
                    //System.out.println("Same length");
                    if(tda[0][0] == tda[1][0]){
                        fullyContained++;
                    }
                }else{
                    //System.out.println("B longer than A");
                    if(tda[0][0] >= tda[1][0] && tda[0][1] <= tda[1][1]){
                        fullyContained++;
                    }
                }
            }else{
/*                int start, end;
                int[] target;
                if(tda[0][2] > tda[1][2]){
                    start = tda[1][0];
                    end = tda[1][1];
                    target = tda[0];
                }else{
                    start = tda[0][0];
                    end = tda[0][1];
                    target = tda[1];
                }

                for(; start <= end; start++){
                    if(start >= target[0] && start <= target[1]){
                        System.out.println("overlapped at " + start);
                        fullyContained++;
                        break;
                    }
                }*/
                if(tda[0][2] > tda[1][2]){
                    if((tda[1][0] <= tda[0][1] && tda[1][0] >= tda[0][0]) || (tda[1][1] >= tda[0][0] && tda[1][1] <= tda[0][1])){
                        fullyContained++;
                    }
                }else{
                    if((tda[0][0] <= tda[1][1] && tda[0][0] >= tda[1][0]) || (tda[0][1] >= tda[1][0] && tda[0][1] <= tda[1][1])){
                        fullyContained++;
                    }
                }
            }

        }

        return fullyContained;
    }


    private int[] getStartEnd(String s){
        String[] split = s.split("-");

        int[] tda = new int[3];

        tda[0] = Integer.parseInt(split[0]);
        tda[1] = Integer.parseInt(split[1]);
        tda[2] = tda[1] - tda[0];

        return tda;
    }
}
