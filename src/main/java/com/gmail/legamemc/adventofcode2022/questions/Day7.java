package com.gmail.legamemc.adventofcode2022.questions;

import com.gmail.legamemc.adventofcode2022.Challenge;
import com.gmail.legamemc.adventofcode2022.Utils;

import java.io.BufferedReader;
import java.util.*;

public class Day7 implements Challenge<Integer> {

    private final int PART = 2;

    @Override
    public Integer execute() throws Exception {
        BufferedReader reader = Utils.getInputs(getClass());

        String line;


        Directory rootDirectory = new Directory("/", null); // root
        Directory currentDirectory = rootDirectory;

        while((line = reader.readLine()) != null){

            if(line.startsWith("dir") || line.startsWith("$")){
                line = line.replace("$ ", "");

                String[] split = line.split(" ");
                switch(split[0]){
                    case "dir":
                        currentDirectory.createDirectory(split[1]);
                        break;
                    case "cd":
                        if(split[1].equalsIgnoreCase("..")){
                            currentDirectory = currentDirectory.getParent();
                        }else if(split[1].equalsIgnoreCase("/")){
                            currentDirectory = rootDirectory;
                        }else{
                            currentDirectory = currentDirectory.getDirectory(split[1]);
                        }
                        break;
/*                    case "ls":
                        currentDirectory.list();
                        break;*/
                    default: break;
                }
            }else{
                String[] split = line.split(" ");
                int size = Integer.parseInt(split[0]);

                currentDirectory.createFile(split[1], size);
            }
        }


        Set<Directory> checked = new HashSet<>();
        int total = 0;

        currentDirectory = rootDirectory;

        //showAsTree(0, currentDirectory);

        int remainingSpace = (70000000 - rootDirectory.getSize());
        int requiredSpaceToUpdate = 30000000 - remainingSpace;
        Directory directoryToDelete = null;
        int dtdSize = 0;

        ml: while(currentDirectory != null){

/*            int size = currentDirectory.getSize();

            // If the size of the current directory is at most 100000
            if(size <= 100000){
                System.out.println("Currently at " + currentDirectory.getDirectoryName() + " directory (" + size + ")");
                total += size;
                checked.add(currentDirectory);
                // go back to parent
                currentDirectory = currentDirectory.getParent();
            }else{

                for(Directory directory : currentDirectory.getDirectories()){
                    if(!checked.contains(directory)){
                        currentDirectory = directory;
                        continue ml;
                    }
                }
                // all the directory in the current location has been checked, go back to parent direcetory
                checked.add(currentDirectory);
                currentDirectory = currentDirectory.getParent();
            }*/
            //System.out.println("Currently in Directory: " + currentDirectory.getDirectoryName() + " Dir count: " + currentDirectory.getDirectories().size() + ", File Count: " + currentDirectory.getFiles().size());
            if(checked.contains(currentDirectory) && checked.containsAll(currentDirectory.getDirectories())){
                //System.out.println("  All the directory in " + currentDirectory.getDirectoryName() + " has been checked, going to parent " + (currentDirectory.getParent() == null ? "null" : currentDirectory.getParent().getDirectoryName()));
                checked.add(currentDirectory);
                currentDirectory = currentDirectory.getParent();
                continue;
            }

            int size = currentDirectory.getSize();

            if(size == 0){
                checked.add(currentDirectory);
                currentDirectory = currentDirectory.getParent();
                continue;
            }

            if(directoryToDelete == null && size > requiredSpaceToUpdate){
                directoryToDelete = currentDirectory;
                dtdSize = directoryToDelete.getSize();
            }else if(size > requiredSpaceToUpdate){
                if(size - requiredSpaceToUpdate < dtdSize - requiredSpaceToUpdate){
                    //System.out.println("Changed from " + dtdSize + " to " + size);
                    directoryToDelete = currentDirectory;
                    dtdSize = directoryToDelete.getSize();
                }
            }


            if(size <= 100000){
                //System.out.println("  Added " + currentDirectory.getDirectoryName() + " size to total: " + size);
                total += size;
                checked.add(currentDirectory);
            }

            for(Directory directory : currentDirectory.getDirectories()){
                if(!checked.contains(directory)){
                    currentDirectory = directory;
                    //Thread.sleep(500);
                    continue ml;
                }
            }

            checked.add(currentDirectory);

            //Thread.sleep(500);
        }

        //System.out.println("Remaining Space: " + remainingSpace);
        //System.out.println("Required Space: " + requiredSpaceToUpdate);

        return PART == 1 ? total : directoryToDelete.getSize();
    }

    private void showAsTree(int x, Directory directory){
        String indent = "  ".repeat(x);
        System.out.println(indent + "> " + directory.getDirectoryName() + " (" + directory.getSize() + ")");
        for(Directory dir : directory.getDirectories()){
            showAsTree(x + 1, dir);
        }
        directory.getFiles().forEach( (name, size) -> System.out.println(indent + "  - " + name + " (" + size + ")"));
    }

    private class Directory{
        private final String directoryName;
        private final Directory parent;

        private final HashMap<String, Directory> directories;
        private final HashMap<String, Integer> files;

        public Directory(String directoryName, Directory parent){
            this.directoryName = directoryName;
            this.parent = parent;
            this.files = new HashMap<>();
            this.directories = new HashMap<>();
        }

        public String getDirectoryName(){
            return directoryName;
        }

        public Directory getParent() {
            // on root directory

            return parent;
        }

        public int getSize() {
            int fileSize = files.values().stream().mapToInt(Integer::intValue).sum();
            int directoriesSize = directories.values().stream().map(Directory::getSize).mapToInt(i -> i).sum();

            return fileSize + directoriesSize;
        }

        public void createFile(String name, int size){
            files.put(name, size);
        }

        public void createDirectory(String name){
            directories.put(name, new Directory(name, this));
        }

        public Directory getDirectory(String directoryName){
            return directories.get(directoryName);
        }


        public HashMap<String, Integer> getFiles(){
            return files;
        }
        public Collection<Directory> getDirectories(){
            return directories.values();
        }

        public void list(){
            System.out.println("Listing Directory (" + getDirectoryName() + ")" + " Size: " + getSize());
            directories.values().forEach( (dir) -> System.out.println("> " + dir.getDirectoryName() + "(" + dir.getSize() + ")"));
            files.forEach( (name, size) -> System.out.println("- " + name + " (" + size + ")"));
            System.out.println("\n");
        }
    }

}
