package com.regg.vse.benchmark;

import com.regg.vse.index.BruteForceIndex;
import com.regg.vse.index.IVFIndex;
import com.regg.vse.recall.RecallEvaluator;

import java.util.ArrayList;
import java.util.List;

public class ParameterTuner {
    private BruteForceIndex groundTruth;
    private List<float[]> vectors;
    private int dimensions;

    public ParameterTuner(int numVectors, int dimensions) {
        this.groundTruth = new BruteForceIndex();
        this.vectors = new ArrayList<>();
        this.dimensions = dimensions;
        for (int i = 0; i < numVectors; i++) {
            float[] floats = new float[dimensions];
            for (int j = 0; j < dimensions; j++) {
                floats[j] = (float)Math.random();
            }
            groundTruth.add(i, floats);
            vectors.add(floats);
        }
    }

    public void tune(int[] numClusters) {
        for (int cluster : numClusters) {
            IVFIndex index = new IVFIndex(cluster);
            index.train(vectors);

            float[] query = new float[dimensions];
            for ( int i = 0; i < dimensions; i++) {
                query[i] = (float)Math.random();
            }
            long preSearch = System.nanoTime();
            List<BruteForceIndex.SearchResult> result = index.search(query, 10);
            long postSearch = System.nanoTime();
            long timeDifference = postSearch - preSearch;
            long differenceInMS = timeDifference/1000000;

            RecallEvaluator evaluator = new RecallEvaluator(groundTruth, index);
            float recall = evaluator.evaluate(query, 25);

            System.out.println("Vectors searched: " + cluster);
            System.out.println("Time spent searching: " + differenceInMS + "ms");
            System.out.println("Recall@25: " + recall);
        }
    }

    public static void main(String[] args) {
        ParameterTuner tuner = new ParameterTuner(100000, 3);
        tuner.tune(new int[] {2, 5, 10, 20, 50, 100, 1000});
    }
}
