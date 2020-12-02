package com.bart.day2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckPass {

    public static void main(String... args) {
        CheckPass cp = new CheckPass();
        try {
            // process the input file
            List<String> data = cp.processPassfile();
            // create pass details list
            List<PassDetail> passList = createPassDetailList(data);
            // validate the passwords
            int totalGood = checkPasswords(passList);
            System.out.println("Total:" + totalGood);

            int totalGood2 = checkPasswords2(passList);
            System.out.println("Total2:" + totalGood2);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * if(d.pass.charAt(d.min) == c | d.pass.charAt(d.max) == c) {
     * good++;
     * }
     *
     * 1-3 a: abcde is valid: position 1 contains a and position 3 does not.
     * 1-3 b: cdefg is invalid: neither position 1 nor position 3 contains b.
     * 2-9 c: ccccccccc is invalid: both position 2 and position 9 contain c.
     *
     * @param passList
     * @return number of good passwords
     */
    private static int checkPasswords2(List<PassDetail> passList) {
        int good = 0;
        for (PassDetail d : passList) {
            char c = d.val.charAt(0);
            // its in the first spot, but not the second
            if (d.pass.charAt(d.min) == c) {
                if (d.pass.charAt(d.max) == c) {
                } else
                {
                    System.out.println("Found Pass in First Spot: " + d.pass + " for char: " + c + " min postion:" + d.min);
                    System.out.println("Not Found char: " + c + " max postion:" + d.max);
                    good++;
                }
            } else if (d.pass.charAt(d.max) == c)
            {
                System.out.println("Found Pass in Second Spot: " + d.pass + " for char: " + c + " min postion:" + d.min +
                        " max postion:" + d.max);
                good++;
            }
        }
        return good;
    }

    /**
     * @param passList
     * @return number of good passwords
     */
    private static int checkPasswords(List<PassDetail> passList) {
        int good = 0;
        for (PassDetail d : passList) {
            char c = d.val.charAt(0);
            long charCount = d.pass.chars().filter(ch -> ch == c).count();
            if (charCount >= d.min && charCount <= d.max) {
                good++;
            }
        }
        return good;
    }

    /**
     * @param data
     * @return
     */
    private static List<PassDetail> createPassDetailList(List<String> data) {
        List<PassDetail> pdList = new ArrayList<>();
        for (String s : data) {
            // 2-4 r: prrmspx
            final String[] colonSplit = s.split(":");
            // 2-4 r
            final String[] spaceSplit = colonSplit[0].split(" ");
            // 2-4
            final String[] dashSplit = spaceSplit[0].split("-");
            PassDetail pd =
                    new PassDetail(Integer.parseInt(dashSplit[0]), Integer.parseInt(dashSplit[1]), spaceSplit[1],
                            colonSplit[1]);
            pdList.add(pd);
        }
        // process the list of pass details
        // pdList.stream().forEach(System.out::println);
        return pdList;
    }


    /**
     * process input file
     *
     * @return
     * @throws FileNotFoundException
     */
    List<String> processPassfile() throws FileNotFoundException {
        String[] valueStr = {};
        try {
            File file = new File("src/com/bart/day2/pass.txt");

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytes);

            fis.close();
            valueStr = new String(bytes).trim().split("\\r?\\n");

            // System.out.println(Arrays.toString(valueStr));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Arrays.asList(valueStr);
    }

}

class PassDetail {
    int min;
    int max;
    String val;
    String pass;

    public PassDetail(int min, int max, String val, String pass) {
        this.min = min;
        this.max = max;
        this.val = val;
        this.pass = pass;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public String getVal() {
        return val;
    }

    public String getPass() {
        return pass;
    }

    @Override public String toString() {
        return "PassDetail{" +
                "min=" + min +
                ", max=" + max +
                ", val='" + val + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
