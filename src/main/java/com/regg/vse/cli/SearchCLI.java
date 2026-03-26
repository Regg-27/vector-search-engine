package com.regg.vse.cli;

import com.regg.vse.index.BruteForceIndex;

import java.util.List;
import java.util.Scanner;

public class SearchCLI {
    private Scanner scanner;
    private BruteForceIndex index;

    public SearchCLI() {
        this.scanner = new Scanner(System.in);
        this.index = new BruteForceIndex();
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("1. Add a vector");
            System.out.println("2. Search");
            System.out.println("3. Exit");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    // addVector();
                    break;
                case "2":
                    // searchIndex();
                    break;
                case "3":
                    running = false;
                    break;
            }
        }
    }

    public void addVector() {
        System.out.println("Enter the number of dimensions: ");
        int dimension = scanner.nextInt();
        float[] vector = new float[dimension];

        System.out.println("Enter each dimension: ");
        for(int i = 0; i < vector.length; i++) {
            float dimensions = scanner.nextFloat();
            vector[i] = dimensions;
        }

        System.out.println("Enter the vector ID: ");
        int id = scanner.nextInt();
        index.add(id, vector);
    }

    public void searchIndex() {
        System.out.println("Enter the query vector's number of dimensions: ");
        int qDimension = scanner.nextInt();
        float[] vector = new float[qDimension];

        System.out.println("Enter each of the query vector's dimension: ");
        for(int i = 0; i < vector.length; i++) {
            float qDimensions = scanner.nextFloat();
            vector[i] = qDimensions;
        }

        System.out.println("Enter desired amount of search results: ");
        int k = scanner.nextInt();

        List<BruteForceIndex.SearchResult> results = index.search(vector, k);
        for (BruteForceIndex.SearchResult result : results) {
            System.out.println("ID: " + result.id + " Score: " + result.score);
        }
    }

    public static void main(String[] args) {
        SearchCLI cli = new SearchCLI();
        cli.start();
    }

}
