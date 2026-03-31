package com.regg.vse.recall;

import com.regg.vse.index.BruteForceIndex;

import java.util.HashSet;
import java.util.List;

public class RecallEvaluator {
    private BruteForceIndex index;

    public RecallEvaluator(BruteForceIndex index) {
        this.index = index;
    }

    public float evaluate(float[] query, int k) {
        List<BruteForceIndex.SearchResult> groundTruth = index.search(query, k);
        List<BruteForceIndex.SearchResult> actualResults = index.search(query, k);

        HashSet<Integer> set = new HashSet<>();
        for (BruteForceIndex.SearchResult result : actualResults) {
            set.add(result.id);
        }

        int counter = 0;
        for (BruteForceIndex.SearchResult result : groundTruth) {
            if (set.contains(result.id)) counter++;
        }
        return (float)counter/groundTruth.size();
    }

    public static void main(String[] args) {
        BruteForceIndex index = new BruteForceIndex();
        index.add(1, new float[]{1.0f, 0.0f, 0.0f});
        index.add(2, new float[]{2.0f, 1.0f, 1.0f});
        index.add(3, new float[]{3.0f, 4.0f, 5.0f});

        RecallEvaluator evaluator = new RecallEvaluator(index);

        float[] query = {1.0f, 0.0f, 0.0f};
        float recall = evaluator.evaluate(query, 3);

        System.out.println("Recall@5: " + recall);
    }
}
