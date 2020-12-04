package com.bart.day3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TravelTree {

    public static void main(String... args) {
        TravelTree tt = new TravelTree();
        try {
            // process the input file
            List<String> data = tt.processTreefile();

            // widen the rows j times
            for (int j = 0; j <= 15; j++) {
                // append the original test data
                for (int w = 0; w <= data.size() - 1; w++) {
                    String orig = data.get(w);
                    String dblOrig = orig + orig;
                    data.set(w, dblOrig);
                }
            }
            //data.stream().forEach(System.out::println);

            int count = countTrees2(1,data);
            System.out.println("COUNT:" + count);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
.#..........#......#..#.....#..3=O
....#.............#.#....#..#..6=O
.....##...###....#..#.......#..9
.#....#..#......#........#.....12
.#.........###.#..........##...15
...............##........#.....18
#..#..........#..##..#....#.#..21
     */
    private static int countTrees(int right, List<String> data) {
        int count = 0;
        int i = 0;
        int treeIndex = right;
        for (String s : data) {
            int length = s.length();
            //System.out.println("Length:" + length);
            //skip the first row
            if (i != 0) {
                // check char index 3
                char c = s.charAt(treeIndex);
                //System.out.println("postion: " + treeIndex + " " + c);
                if (Character.compare(c, '#') == 0) {
                    count++;
                }
                treeIndex += right;
            }
            i++;
            //System.out.println("I=" + i);

        }
        return count;
    }

    private static int countTrees2(int right, List<String> data) {
        int count = 0;
        int i = 0;
        int treeIndex = right;
        for (String s : data) {
            int length = s.length();
            //System.out.println("Length:" + length);
            //skip the first row
            if (i > 1) {
                if ( i % 2 == 0 ){
                    // check char index 3
                    char c = s.charAt(treeIndex);
                    //System.out.println("postion: " + treeIndex + " " + c);
                    if (Character.compare(c, '#') == 0) {
                        count++;
                    }
                    treeIndex += right;
                }
            }
            i++;
            //System.out.println("I=" + i);

        }
        return count;
    }

    /**
     * process input file
     *
     * @return
     * @throws FileNotFoundException
     */
    List<String> processTreefile() throws FileNotFoundException {
        String[] valueStr = {};
        try {
            File file = new File("src/com/bart/day3/trees.txt");

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytes);

            fis.close();
            valueStr = new String(bytes).trim().split("\\r?\\n");

            //System.out.println(Arrays.toString(valueStr));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(valueStr);
    }

}
