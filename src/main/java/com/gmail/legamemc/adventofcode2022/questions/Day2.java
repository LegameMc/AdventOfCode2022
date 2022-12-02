package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.io.IOException;

public class Day2 implements Challenge<Integer> {
    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(this.getClass());

        return part2(reader);
    }


    private int part1(BufferedReader reader) throws IOException {
        String line;
        int score = 0;
        while((line = reader.readLine()) != null){

            char[] arr = line.toCharArray();
            int opponent = arr[0] - 'A' + 1;
            int player = arr[2] - 'X' + 1;

            System.out.println("Opponent: " + arr[0] + " " + opponent);
            System.out.println("Player: " + arr[2] + " " + player);

            int roundScore = 0;
            if(opponent < player){
                roundScore = opponent == 1 && player == 3 ? 0 : 6;
            }else if(opponent == player){
                roundScore = 3;
            }else if(player == 1 && opponent == 3){
                roundScore = 6;
            }

            score += player + roundScore;
        }


        return score;
    }

    private int part2(BufferedReader reader) throws IOException {
        String line;
        int score = 0;
        while((line = reader.readLine()) != null){

            char[] arr = line.toCharArray();
            // Rock = 0, Paper = 1, Scissors = 2
            int opponent = arr[0] - 'A';
            int playerDecision = arr[2] - 'X';

            //System.out.println("Opponent: " + arr[0] + " " + opponent);
            //System.out.println("Player: " + arr[2] + " " + playerDecision + " (" + (playerDecision == 0 ? "Lose" : playerDecision == 1 ? "Draw" : "Win") + ")");

            int roundScore = 0;
            int playerScore = 0;
            switch(playerDecision){
                case 1: // draw
                    roundScore = 3;
                    playerScore = opponent + 1;
                    break;
                case 2: //win
                    roundScore = 6;
                case 0: // lose
                    int a = (opponent + (playerDecision == 0 ? -1 : 1)) % 3;
                    if(a < 0) a = 2;
                    playerScore = a + 1;
                    break;

            }
            score += roundScore + playerScore;
            //System.out.println("Score earned: " + playerScore + "+" + roundScore + "=" + (playerScore + roundScore) + "\n");
        }

        return score;
    }
}
