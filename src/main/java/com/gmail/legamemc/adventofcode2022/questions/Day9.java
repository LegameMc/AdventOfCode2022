package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day9 implements Challenge<Integer> {
    private static final int knotsSize = 2; // PART 1 = 2, PART 2 = 10

    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(getClass());
        Set<String> trailVisitedCords = new HashSet<>();

        Knot[] pos2 = new Knot[knotsSize];
        for(int x = 0; x < pos2.length; x++){
            pos2[x] = new Knot();
        }

        String line;

        while((line = reader.readLine()) != null){
            String[] split = line.split(" ");

            System.out.println("Parsing " + line);
            int step = Integer.parseInt(split[1]);

            movePart2a2(split[0], step, pos2, trailVisitedCords);

            System.out.print("    Updated pos: [");
            for(Knot k : pos2){
                System.out.print(Arrays.toString(k.currentPos) + ",");
            }
            System.out.println("]");

        }

        System.out.println("\n\n");
        displayVisitedPlace(trailVisitedCords);

        return trailVisitedCords.size();
    }

    private void movePart1(String direction, int step, int[][] pos, Set<String> trailVisitedCords) {
        int xMove = 0, yMove = 0;

        switch (direction) {
            case "L" -> xMove = -1;
            case "R" -> xMove = 1;
            case "U" -> yMove = 1;
            case "D" -> yMove = -1;
        }

/*        for(; step > 0; step--){
            int[] previousHeadPos = new int[2];
            previousHeadPos[0] = pos[0][0];
            previousHeadPos[1] = pos[0][1];

            pos[0][0] += xMove;
            pos[0][1] += yMove;
            System.out.println("  Head pos: " + Arrays.toString(pos[0]));
            System.out.println("  Trail pos: " + Arrays.toString(pos[1]));

            int xDiff = Math.abs(pos[0][0] - pos[1][0]);
            int yDiff = Math.abs(pos[0][1] - pos[1][1]);
            if(xDiff == 2 || yDiff == 2){
                pos[1] = previousHeadPos;
                System.out.println("  Too far! Updating position to " + Arrays.toString(previousHeadPos));
                trailVisitedCords.add(previousHeadPos[0] + "," + previousHeadPos[1]);
            }
        }*/

        int currentFocus = 0;
        for(; step > 0; step--){
            do{
                int[] previousHeadPos = new int[2];
                previousHeadPos[0] = pos[0][0];
                previousHeadPos[1] = pos[0][1];

                pos[0][0] += xMove;
                pos[0][1] += yMove;
                System.out.println("  Head pos: " + Arrays.toString(pos[0]));
                System.out.println("  Trail pos: " + Arrays.toString(pos[1]));

                int xDiff = Math.abs(pos[0][0] - pos[1][0]);
                int yDiff = Math.abs(pos[0][1] - pos[1][1]);
                if(xDiff == 2 || yDiff == 2){
                    pos[1] = previousHeadPos;
                    System.out.println("  Too far! Updating position to " + Arrays.toString(previousHeadPos));
                    trailVisitedCords.add(previousHeadPos[0] + "," + previousHeadPos[1]);
                }

                currentFocus++;
            }while (currentFocus < pos.length && Math.abs(pos[0][0] - pos[1][0]) == 2 && Math.abs(pos[0][1] - pos[1][1]) == 2);
        }
    }

    private void movePart2(String direction, int step, int[][] pos, Set<String> trailVisitedCords){
        int xMove = 0, yMove = 0;

        switch (direction) {
            case "L" -> xMove = -1;
            case "R" -> xMove = 1;
            case "U" -> yMove = 1;
            case "D" -> yMove = -1;
        }

        int currentFocus = 0; //focus head by default

        for(int count = 1; step > 0; step--, count++){
            System.out.println("  Step " + count);
            boolean b = false;

            int[] previousPos = new int[2];
            previousPos[0] = pos[currentFocus][0];
            previousPos[1] = pos[currentFocus][1];

            pos[currentFocus][0] += xMove;
            pos[currentFocus][1] += yMove;
            for(int next = currentFocus + 1; next < pos.length; next++){

                System.out.println("      Updated Current[" + currentFocus + "] to " + Arrays.toString(pos[currentFocus]));
                int xDiff = Math.abs(pos[currentFocus][0] - pos[next][0]);
                int yDiff = Math.abs(pos[currentFocus][1] - pos[next][1]);
                System.out.println("        Comparing Current" + Arrays.toString(pos[currentFocus]) + " and Next" + Arrays.toString(pos[next]) + ": " + xDiff + " " + yDiff);

                if(xDiff == 2 || yDiff == 2){
                    int[] temp = pos[next];
                    pos[next] = previousPos;
                    previousPos = temp;
                    System.out.println("        Updating Index[" + next + "] to " + Arrays.toString(pos[next]));
                    currentFocus++;
                }else{
                    break;
                }

            }

            currentFocus = 0;
            int trail = pos.length - 1;
            trailVisitedCords.add(pos[trail][0] + "," + pos[trail][1]);

            System.out.print("    Updated pos: [");
            for(int[] a : pos){
                System.out.print(Arrays.toString(a) + ",");
            }
            System.out.println("]");
        }
    }

    private void movePart2a2(String direction, int step, Knot[] knots, Set<String> trailVisitedCords) throws InterruptedException {
        int xMove = 0, yMove = 0;


        switch (direction) {
            case "L" -> xMove = -1;
            case "R" -> xMove = 1;
            case "U" -> yMove = 1;
            case "D" -> yMove = -1;
        }

        int xAdjust = -yMove, yAdjust = -xMove;

        int current = 0;
        int count = 1;


        for(;step > 0; step--, count++){
            boolean startAdjust = false;
            current = 0;
            System.out.println("  Step " + count);

            knots[current].adjustPos(xMove, yMove);
            System.out.println("      Head moved from " + Arrays.toString(knots[current].previousPos) + " to " + Arrays.toString(knots[current].currentPos));
            for(int next = current + 1; next < knots.length; next++){

                Knot currentKnot = knots[current];
                Knot nextKnot = knots[next];

                int[] distance = getDistances(currentKnot.getCurrentPos(), nextKnot.getCurrentPos());
                System.out.println("        Comparing Current[" + current + "] Pos" + Arrays.toString(currentKnot.currentPos) + " and Next[" + next + "] Pos" + Arrays.toString(nextKnot.currentPos) + ": " + distance[0] + " " + distance[1]);

                if(startAdjust){

                    if(distance[0] == 2 || distance[1] == 2 ){
                        if(distance[0] == 2 && distance[1] == 2 || (currentKnot.previousPos[0] != nextKnot.currentPos[0] && currentKnot.previousPos[1] != nextKnot.currentPos[1])){
                            startAdjust = false;
                            System.out.println("          Adjust will be disabled in next loop");
                            //nextKnot.setPos(currentKnot.previousPos[0], currentKnot.previousPos[1]);
                            int xa = xAdjust;
                            int ya = yAdjust;

                            if(currentKnot.currentPos[0] == nextKnot.currentPos[0]){
                                xAdjust = 0;
                            }
                            if(currentKnot.currentPos[1] == nextKnot.currentPos[1]){
                                yAdjust = 0;
                            }
                            System.out.println("            X/Y adjust: " + xa + " " + ya);
                            nextKnot.adjustPos(xa, ya);
                            current++;
                            continue;
                        }

                        System.out.println("          --- Adjusting ---   ");
                        System.out.println("            Current current pos: " + Arrays.toString(currentKnot.currentPos));
                        System.out.println("            Current previous pos: " + Arrays.toString(currentKnot.previousPos));
                        System.out.println("            Next current pos: " + Arrays.toString(nextKnot.currentPos));
                        System.out.println("            X/Y Adjust: " + xAdjust + " " + yAdjust);

                        if(currentKnot.currentPos[0] == nextKnot.currentPos[0]){
                            xAdjust = 0;
                        }
                        if(currentKnot.currentPos[1] == nextKnot.currentPos[1]){
                            yAdjust = 0;
                        }
                        //nextKnot.setPos(currentKnot.currentPos[0] + xAdjust, currentKnot.currentPos[1] + yAdjust);
                        nextKnot.adjustPos(xAdjust, yAdjust);
                        //Thread.sleep(1000);
                        System.out.println("              Updated Next Knot pos from " + Arrays.toString(nextKnot.previousPos) + " to " + Arrays.toString(nextKnot.currentPos));
                        //display(knots);
                    }else{
                        startAdjust = false;
                        System.out.println("          Adjust will be disabled in next loop");
                    }
                    current++;
                    continue;
                }

                if(distance[0] == 2 || distance[1] == 2){

                    int[] currentPrevious = currentKnot.getPreviousPos();

                    if(currentKnot.currentPos[0] != nextKnot.currentPos[0] && currentKnot.currentPos[1] != nextKnot.currentPos[1]){
                        startAdjust = true;
                    }
                    nextKnot.setPos(currentPrevious[0], currentPrevious[1]);

                    xAdjust = nextKnot.currentPos[0] - nextKnot.previousPos[0];
                    yAdjust = nextKnot.currentPos[1] - nextKnot.previousPos[1];
                    System.out.println("          Updated Next Knot pos from " + Arrays.toString(nextKnot.previousPos) + " to " + Arrays.toString(nextKnot.currentPos));
                    current++;
                }else{
                    break;
                }
            }

            Knot last = knots[knots.length-1];
            trailVisitedCords.add(last.currentPos[0] + "," + last.currentPos[1]);
            System.out.print("    Updated pos: [");
            for(Knot k : knots){
                System.out.print(Arrays.toString(k.currentPos) + ",");
            }
            System.out.println("]");

            //display(knots);
        }
    }

    private void display(Knot[] knots){
        char[][] grid = new char[100][100];

        for(int index = 0; index < knots.length; index++){
            Knot knot = knots[index];
            int[] currentPos = knot.currentPos;
            if(grid[currentPos[1] + 19][currentPos[0] + 20] == '\u0000')
                grid[currentPos[1] + 19][currentPos[0] + 20] = index == 0 ? 'H' : (char) (index + '0');
        }

        for(int row = grid.length - 1; row >= 0; row--){
            for(int column = 0; column < grid[row].length; column++) {
                char c = grid[row][column];
                System.out.print(c == '\u0000' ? '.' : c);
            }

            System.out.print(" " +  (row - 19)+ "\n");
        }

/*        for(int c = -20; c < 30; c++){
            int b = c;
            if(b < -9) {
                b %= 10;
            }
            System.out.print(Math.abs(b));
        }
        System.out.print("\n");*/
    }

    public void displayVisitedPlace(Set<String> visited){
        for(int row = 300; row >= -300; row--){
            for(int column = -300; column < 300; column++) {
                String pos = column + "," + row;

                if(visited.contains(pos)){
                    System.out.print("#");
                }else{
                    System.out.print(".");
                }
            }
            System.out.print("\n");
        }
    }

    private class Knot{
        private final int[] previousPos = new int[2];
        private final int[] currentPos = new int[2];

        public void setPos(int x, int y){
            previousPos[0] = currentPos[0];
            previousPos[1] = currentPos[1];

            currentPos[0] = x;
            currentPos[1] = y;
        }

        public void adjustPos(int x, int y){
            currentPos[0] = x + (previousPos[0] = currentPos[0]);
            currentPos[1] = y + (previousPos[1] = currentPos[1]);
        }

        public int[] getPreviousPos(){
            return previousPos;
        }

        public int[] getCurrentPos(){
            return currentPos;
        }
    }

    private int[] getDistances(int[] a, int[] b){
        int[] result = new int[2];

        result[0] = Math.abs(a[0] - b[0]);
        result[1] = Math.abs(a[1] - b[1]);
        return result;
    }

}
