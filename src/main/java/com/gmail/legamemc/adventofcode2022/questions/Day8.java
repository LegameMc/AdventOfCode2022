package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Day8 implements Challenge<Integer> {

    // 1765 too high

    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(getClass());

        List<String> list = reader.lines().toList();

        int rowSize = list.size();
        int columnSize = list.get(0).length();

        TreeStatus[][] grid = new TreeStatus[rowSize][columnSize];

        for(int lineIndex = 0; lineIndex < list.size(); lineIndex++){
            String line = list.get(lineIndex);
            //System.out.println("Reading line: " + line);
            char[] charArr = line.toCharArray();

            for(int x = 0; x < columnSize; x++){
                grid[lineIndex][x] = new TreeStatus(lineIndex, x, Integer.parseInt(charArr[x] + ""));
                //System.out.println("Parsing  " + charArr[x]);
            }
        }


        System.out.println("\n --- interior trees ---");

        int visible = rowSize * 2 + columnSize * 2 - 4;

        int highestScore = 1;
        for(int row = 1; row < rowSize - 1; row++){
            for(int column = 1; column < columnSize - 1; column++){
/*                TreeStatus current = grid[row][column];
                TreeStatus up = grid[row-1][column];
                TreeStatus right = grid[row][column+1];
                TreeStatus down = grid[row+1][column];
                TreeStatus left = grid[row][column-1];

                if(up.isVisible()){
                    if(up.getHeight() > current.getHeight()){
                        current.setInvisible(Direction.UP, up.getHeight());
                    }
                }*/
                System.out.println("Checking Tree[" + row + "," + column + "]");
                int score = 1;
                score *= checkDirection(grid, row, column, Direction.UP);
                score *= checkDirection(grid, row, column, Direction.DOWN);
                score *= checkDirection(grid, row, column, Direction.LEFT);
                score *= checkDirection(grid, row, column, Direction.RIGHT);

                if(grid[row][column].isVisible()){
                    visible++;
                    System.out.println("Tree[" + row + "," + column + "] is visible with score " + score);
                    if(score > highestScore){
                        highestScore = score;
                    }
                }
            }
            //System.out.print("\n");
        }

        System.out.println("\n\n\n");
        for(int x = 0; x < rowSize; x++){
            for(int y = 0; y < columnSize; y++){
                TreeStatus status = grid[x][y];
                if(!status.isVisible()){
                   System.out.println("Tree[" + status.getRow() + "," + status.getColumn() + "] is not visible");
                }
            }
        }
        return highestScore;
    }

    private enum Direction{
        UP,
        RIGHT,
        DOWN,
        LEFT
    }

    private int checkDirection(TreeStatus[][] grid, int row, int column, Direction direction) throws InterruptedException {
        System.out.println("  Checking Direction[" + direction + "]");
        int offSetRow = 0, offSetColumn = 0, edgeRow = row, edgeColumn = column;

        switch(direction){
            case UP:
                offSetRow = -1;
                edgeRow = 0;
                break;
            case DOWN:
                offSetRow = 1;
                edgeRow =  grid.length - 1;
                break;
            case RIGHT:
                offSetColumn = 1;
                edgeColumn = grid[row].length - 1;
                break;
            case LEFT:
                offSetColumn = -1;
                edgeColumn = 0;
                break;
            default: return 1;
        }

        TreeStatus current = grid[row][column];
        TreeStatus target;

        // Attempt 1
/*        if(target.isVisible(direction)){
            do{
                if(target.getHeight() >= current.getHeight()){
                    current.setInvisible(direction, target.getHeight());
                    System.out.println("Tree[" + target.getRow() + "," + target.getColumn() + "] has Blocked Tree[" + current.getRow() + "," + current.getColumn() + "] from Direction[" + direction + "]");
                    break;
                }
                row += offSetRow;
                column += offSetColumn;
            }while(row > 0  && row < grid.length - 1 && column > 0 && column < grid[row].length - 1&& (target = grid[row][column]) != null);


        }else{
            if(target.height <= current.getHeight()){
                int blockedDirectionHeight = target.getDirectionBlockHeight(direction);
                if(blockedDirectionHeight >= current.getHeight()){
                    current.setInvisible(direction, blockedDirectionHeight);
                }
            }
        }*/

        // Atempt 2, misread question, only check edge and the tree beside it

        int score = 0;
        boolean foundBlocked = false;

        do{
            target = grid[row + offSetRow][column + offSetColumn];

            System.out.println("    Comparing target Tree[" + target.getRow() + "," + target.getColumn() + "]");

            score++;
            // Improved of 2.1 code
            if(target.getHeight() >= current.getHeight()){
                if(!foundBlocked) current.setInvisible(direction, target.getHeight());
                System.out.println("    Tree[" + target.getRow() + "," + target.getColumn() + "] has block the tree from Direction[" + direction + "]");
                break;
            }else{
                if(!target.isVisible(direction) && !foundBlocked){
                    int blockedDirectionHeight = target.getDirectionBlockHeight(direction);
                    if(blockedDirectionHeight >= current.getHeight()){
                        foundBlocked = true;
                        current.setInvisible(direction, blockedDirectionHeight);
                        System.out.println("    Tree[" + target.getRow() + "," + target.getColumn() + "] is not visible and it is blocked by a tree with " + blockedDirectionHeight + " height from Direction[" + direction + "]");
                    }
                }
            }

            // Attempt 2.1
//            if(target.isVisible(direction)){
//                System.out.println("    Target is visible");
//                if(target.getHeight() >= current.getHeight()){
//                    current.setInvisible(direction, target.getHeight());
//                    System.out.println("    Tree[" + target.getRow() + "," + target.getColumn() + "] has Blocked Tree[" + current.getRow() + "," + current.getColumn() + "] from Direction[" + direction + "]");
//                    break;
//                }
//            }else{
//                System.out.println("    Target is not visible");
//                if(target.height <= current.getHeight()){
//                    int blockedDirectionHeight = target.getDirectionBlockHeight(direction);
//                    if(blockedDirectionHeight >= current.getHeight()){
//                        current.setInvisible(direction, blockedDirectionHeight);
//                        break;
//                    }
//                }
//            }

            row += offSetRow;
            column += offSetColumn;
            System.out.println("      Updated row and column to [" + row + "," + column + "]");
        }while(row > 0  && row < grid.length - 1 && column > 0 && column < grid[row].length - 1&& (target = grid[row][column]) != null);


        if(current.isVisible(direction)){
            TreeStatus edge = grid[edgeRow][edgeColumn];
            if(edge.getHeight() >= current.getHeight()){
                current.setInvisible(direction, edge.getHeight());
            }
        }


        System.out.println("      Score from current direction: " + score);
        return score;
    }




    private class TreeStatus{
        private final int row;
        private final int column;
        private final int height;
        private final HashMap<Direction, Integer> blocks;

        TreeStatus(int row, int column, int height){
            this.row = row;
            this.column = column;
            this.height = height;
            this.blocks = new HashMap<>();
        }


        public int getHeight() {
            return height;
        }

        public void setInvisible(Direction direction, int height){
            blocks.put(direction, height);
        }

        public boolean isVisible(Direction direction){
            return !blocks.containsKey(direction);
        }

        public int getDirectionBlockHeight(Direction direction){
            return blocks.get(direction);
        }

        public boolean isVisible(){
            return blocks.size() != 4;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }
    }

}
