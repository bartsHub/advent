package com.bart.day1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;


public class SumTwo {
//System.out.println(st.findTwoSum_Sorting(data, 2020));

    public static void main(String... args) {
        SumTwo st = new SumTwo();
        try {
            int[] data = st.processIntfile();
            System.out.println(Arrays.toString(data));
            st.findTwoSum(data, 2020);
            st.find3Numbers(data, data.length, 2020);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Find sum for three
     * @param A
     * @param arr_size
     * @param sum
     * @return
     */
    boolean find3Numbers(int A[], int arr_size, int sum)
    {
        int l, r;

        // Fix the first element as A[i]
        for (int i = 0; i < arr_size - 2; i++) {

            // Fix the second element as A[j]
            for (int j = i + 1; j < arr_size - 1; j++) {

                // Now look for the third number
                for (int k = j + 1; k < arr_size; k++) {
                    if (A[i] + A[j] + A[k] == sum) {
                        System.out.print("Triplet is " + A[i] + ", " + A[j] + ", " + A[k]);
                        return true;
                    }
                }
            }
        }

        // If we reach here, then no triplet was found
        return false;
    }


    // Time complexity: O(n^2)
    int[] findTwoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    //System.out.println("I:" + i + " J:" + j);
                    System.out.println("Id:" + nums[i] + " Jd:" + nums[j]);

                    return new int[] { i, j };
                }
            }
        }
        return new int[] {};
    }


    /**
     * find the two number sin the file that eual the target
     * @param nums
     * @param target
     * @return
     */
    int[] findTwoSum_Sorting(int[] nums, int target) {
        Arrays.sort(nums);
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                return new int[]{nums[left], nums[right]};
            } else if (nums[left] + nums[right] < target) {
                left++;
            } else {
                right--;
            }
        }
        return new int[]{};
    }

    /**
     * process input file
     *
     * @return
     * @throws FileNotFoundException
     */
    int[] processIntfile() throws FileNotFoundException {
        int[] tall = {};
        try {
            File file = new File("src/com/bart/day1/input.txt");

            byte[] bytes = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);

            fis.read(bytes);

            fis.close();
            String[] valueStr = new String(bytes).trim().split("\\r?\\n");
            tall = new int[valueStr.length];
            for (int i = 0; i < valueStr.length; i++)
                tall[i] = Integer.parseInt(valueStr[i]);


            // System.out.println(Arrays.asList(tall));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tall;


    }
}
