package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;

import java.io.*;
import java.util.Arrays;

public class Day1 implements Challenge {


    @Override
    public void execute() throws Exception{
        int[] tops = new int[3];
        InputStream inputStream = getClass().getResourceAsStream("/Day1Input.txt");

        if(inputStream == null){
            System.out.println("File not found");
            return;
        }

        InputStreamReader reader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(reader);

        String line;
        int sum = 0;

        while((line = bufferedReader.readLine()) != null){
            if(line.isEmpty()){
                adjustTop(tops, sum);
                sum = 0;
                continue;
            }
            int calories = Integer.parseInt(line);
            sum += calories;
        }

        if(sum != 0){
            adjustTop(tops, sum);
        }

        System.out.println(Arrays.toString(tops));
        System.out.println(Arrays.stream(tops).sum());
    }

    private void adjustTop(int[] arr, int value){
        int index = arr.length;
        for(int x = 2; x >=0; x--){
            if(value <= arr[x]){
                break;
            }
            index = x;
        }

        while(index < arr.length){
            int temp2 = arr[index];
            arr[index] = value;
            index++;
            value = temp2;
        }
    }

}
