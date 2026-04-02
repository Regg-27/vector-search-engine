package com.regg.vse.recall;

import com.regg.vse.index.BruteForceIndex;
import com.regg.vse.index.IVFIndex;
import com.regg.vse.index.VectorIndex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class RecallEvaluator {
    private VectorIndex groundTruth;
    private VectorIndex candidateIndex;
    private BruteForceIndex index;

    public RecallEvaluator(VectorIndex groundTruth, VectorIndex candidateIndex) {
        this.groundTruth = groundTruth;
        this.candidateIndex = candidateIndex;
    }

    public float evaluate(float[] query, int k) {
        List<BruteForceIndex.SearchResult> trueResults = groundTruth.search(query, k);
        List<BruteForceIndex.SearchResult> actualResults = candidateIndex.search(query, k);

        HashSet<Integer> set = new HashSet<>();
        for (BruteForceIndex.SearchResult result : actualResults) {
            set.add(result.id);
        }

        int counter = 0;
        for (BruteForceIndex.SearchResult result : trueResults) {
            if (set.contains(result.id)) counter++;
        }
        return (float)counter/trueResults.size();
    }

    public static void main(String[] args) {
        BruteForceIndex index = new BruteForceIndex();
        IVFIndex index2 = new IVFIndex(2);

        List<float[]> list = new ArrayList<>();
        list.add(0, new float[]{1.0f, 0.0f, 0.0f});
        list.add(1, new float[]{2.0f, 1.0f, 1.0f});
        list.add(2, new float[]{3.0f, 4.0f, 5.0f});

        index.add(1, new float[]{1.0f, 0.0f, 0.0f});
        index.add(2, new float[]{2.0f, 1.0f, 1.0f});
        index.add(3, new float[]{3.0f, 4.0f, 5.0f});
        index2.train(list);

        RecallEvaluator evaluator = new RecallEvaluator(index, index2);

        float[] query = {1.0f, 0.0f, 0.0f};
        float recall = evaluator.evaluate(query, 3);

        System.out.println("Recall@3: " + recall);
    }
}
