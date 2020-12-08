package com.bart.day6;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CustomsCheck {

    public static void main(String... args) {
        CustomsCheck cc = new CustomsCheck();
        try {
            // process the input file
            List<String> data = cc.processCustomsfile();

            //int yesCount = countAnyoneYes(data);
            //System.out.println("Anyone YES:" + yesCount);

            int yesCount2 = countEveryoneYes(data);
            System.out.println("Everyone YES:" + yesCount2);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * abc
     * <p>
     * a
     * b
     * c
     * <p>
     * ab
     * ac
     * <p>
     * [a, b, c]
     * <p>
     * [a]
     * [b]
     * [c]
     * <p>
     * [a, b]
     * [a, c]
     *
     * @param data
     * @return
     */
    private static int countEveryoneYes(List<String> data) {
        int sumOfCounts = 0;
        // process
        List<List<Set<Character>>> fullList = new ArrayList<>();


        for (String line : data) {
            List<Set<Character>> setList = new ArrayList<>();
            Set<Character> set = new HashSet<>();
            // length of original line
            //System.out.println("LENGTH:" + line.length());
            // clean it up
            String trimData = line.trim();
            //System.out.println("trimData LENGTH:" + trimData.length() + " trim DATA:" + trimData);

            for (int i = 0; i < trimData.length(); i++) {
                if (Character.isAlphabetic(trimData.charAt(i)) && !Character.isWhitespace(trimData.charAt(i))) {
                    set.add(trimData.charAt(i));
                } else {
                    setList.add(set);
                    set = new HashSet<>();
                    if (Character.isAlphabetic(trimData.charAt(i)) && !Character.isWhitespace(trimData.charAt(i))) {
                        set.add(trimData.charAt(i));
                    }
                }
            }
            //set.stream().forEach(System.out::println);
            //System.out.println("SET COUNT: " + set.size());
            setList.add(set);
            fullList.add(setList);
        }

        fullList.stream().forEach(System.out::println);

        //[[a, b, c]]
        //[[a], [b], [c]]
        //[[a, b], [a, c]]
        //[[a], [a], [a], [a]]
        for(List<Set<Character>> full : fullList)
        {
            if(full.size() == 1)
            {
                //System.out.println("Found a Single");
                sumOfCounts += full.get(0).size();
            } else
            {
                System.out.println("New Set List");
                System.out.println(full);

                Set firstSet = full.get(0);
                for(int x = 1; x <= full.size() - 1; x++)
                {
                    System.out.println("Set:" + full.get(x));
                    firstSet.retainAll(full.get(x));
                }
                System.out.println("Whats left: "  + firstSet);
                sumOfCounts += firstSet.size();
            }
        }


        //System.out.println("SUM:" + sumOfCounts);
        return sumOfCounts;

    }

    /**
     * @param data
     * @return
     */
    private static int countAnyoneYes(List<String> data) {
        int sumOfCounts = 0;
        // process
        for (String line : data) {
            Set<Character> set = new HashSet<>();
            // length of original line
            //System.out.println("LENGTH:" + line.length());
            // clean it up
            String trimData = line.trim();
            //System.out.println("trimData LENGTH:" + trimData.length() + " trim DATA:" + trimData);

            for (int i = 0; i < trimData.length(); i++) {
                if (Character.isAlphabetic(trimData.charAt(i)) && !Character.isWhitespace(trimData.charAt(i))) {
                    set.add(trimData.charAt(i));
                }
            }
            //set.stream().forEach(System.out::println);
            //System.out.println("SET COUNT: " + set.size());
            sumOfCounts += set.size();
        }

        //System.out.println("SUM:" + sumOfCounts);
        return sumOfCounts;
    }


    /**
     * process input file
     *
     * @return
     * @throws FileNotFoundException
     */
    List<String> processCustomsfile() throws FileNotFoundException {
        String[] valueStr = {};
        try {
            File file = new File("src/com/bart/day6/input.txt");

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytes);

            fis.close();

            valueStr = new String(bytes).trim().split("(?m)^\\s*$");

            //System.out.println("SIZE:" + valueStr.length);
            //System.out.println(Arrays.toString(valueStr));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(valueStr);
    }

}

