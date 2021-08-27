package test;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class PerfectSquareCount {

    static int countSquares(int m, int n) {
        if (n < m) {
            int temp = m;
            m = n;
            n = temp;
        }

        return m * (m + 1) * (2 * m + 1) /
                6 + (n - m) * m * (m + 1) / 2;
    }

    static void executeTests(int testCases, Scanner input, List<Integer> results) {
        for (int i = 0; i < testCases; i++) {
            System.out.println("Enter length and breath:");
            int length = input.nextInt();
            int breath = input.nextInt();


            int noOfSquares = countSquares(length, breath);
            results.add(noOfSquares);
        }
    }


    private static void echoResults(List<Integer> results) {
        for (var result : results)
            System.out.println(result);
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter test cases:");

        int testCases = input.nextInt();
        List<Integer> results = new java.util.ArrayList<>(Collections.emptyList());

        executeTests(testCases, input, results);
        echoResults(results);

    }
}



