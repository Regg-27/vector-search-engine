package com.regg.vse.benchmark;

import com.regg.vse.cli.SearchCLI;
import com.regg.vse.index.BruteForceIndex;

import java.util.List;

public class SearchBenchmark {
    private BruteForceIndex index;
    private int numVectors;
    private int dimensions;

    public SearchBenchmark(int numVectors, int dimensions) {
        this.index = new BruteForceIndex();
        this.dimensions = dimensions;
        this.numVectors = numVectors;
    }

    private void loadData() {
        for (int i = 0; i < numVectors; i++) {
            float[] floats = new float[dimensions];
            for (int j = 0; j < dimensions; j++) {
                floats[j] = (float)Math.random();
            }
            index.add(i, floats);
        }
    }

    public void run() {
        loadData();
        float[] query = new float[dimensions];
        for ( int i = 0; i < dimensions; i++) {
            query[i] = (float)Math.random();
        }
        long preSearch = System.nanoTime();
        List<BruteForceIndex.SearchResult> result = index.search(query, 10);
        long postSearch = System.nanoTime();
        long timeDifference = postSearch - preSearch;
        long differenceInMS = timeDifference/1000000;
        System.out.println("Vectors searched: " + numVectors);
        System.out.println("Time spent searching: " + differenceInMS + "ms");
    }

    public static void main(String[] args) {
        SearchBenchmark run = new SearchBenchmark(1000, 128);
        SearchBenchmark run2 = new SearchBenchmark(10000, 128);
        SearchBenchmark run3 = new SearchBenchmark(100000, 128);
        SearchBenchmark run4 = new SearchBenchmark(1000000, 128);

        run.run();
        run2.run();
        run3.run();
        run4.run();

    }
}
