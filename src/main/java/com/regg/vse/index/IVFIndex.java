package com.regg.vse.index;

import com.regg.vse.math.VectorMath;

import java.util.ArrayList;
import java.util.List;

public class IVFIndex implements VectorIndex {
    private int numClusters;
    private float[][] centroids;
    private List<List<float[]>> clusters;
    private ArrayList<List<Integer>> clusterIDs;

    public IVFIndex(int numClusters) {
        this.numClusters = numClusters;
        this.centroids = new float[numClusters][];
        this.clusters = new ArrayList<List<float[]>>();
        for (int i = 0; i < numClusters; i++) {
            this.clusters.add(new ArrayList<float[]>());
        }
        this.clusterIDs = new ArrayList<List<Integer>>();
        for (int i = 0; i < numClusters; i++) {
            this.clusterIDs.add(new ArrayList<Integer>());
        }
    }

    public void train(List<float[]> vectors) {
        for (int i = 0; i < numClusters; i++) {
            int randomIndex = (int)(Math.random() * vectors.size());
            this.centroids[i] = vectors.get(randomIndex);
        }
        for (int i = 0; i < vectors.size(); i++) {
            int nearestCluster = 0;
            float bestDistance = Float.MAX_VALUE;

            for (int j = 0; j < numClusters; j++) {
                float distance = VectorMath.l2Squared(vectors.get(i), centroids[j]);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    nearestCluster = j;
                }
            }
            this.clusters.get(nearestCluster).add(vectors.get(i));
            this.clusterIDs.get(nearestCluster).add(i);
        }
    }

    public void add(int id, float[] vector) {
            int nearestCluster = 0;
            float bestDistance = Float.MAX_VALUE;

            for (int i = 0; i < numClusters; i++) {
                float distance = VectorMath.l2Squared(vector, centroids[i]);
                if (distance < bestDistance) {
                    bestDistance = distance;
                    nearestCluster = i;
                }
            }
            this.clusters.get(nearestCluster).add(vector);
            this.clusterIDs.get(nearestCluster).add(id);
    }

    public List<BruteForceIndex.SearchResult> search(float[] query, int k) {
        int nearestCluster = 0;
        float bestDistance = Float.MAX_VALUE;

        for (int i = 0; i < numClusters; i++) {
            float distance = VectorMath.l2Squared(query, centroids[i]);
            if (distance < bestDistance) {
                bestDistance = distance;
                nearestCluster = i;
            }
        }
        List<float[]> clusterVectors = this.clusters.get(nearestCluster);

        BruteForceIndex tempIndex = new BruteForceIndex();
        int idIndex = 0;
        for (float[] vector : clusterVectors) {
            tempIndex.add(clusterIDs.get(nearestCluster).get(idIndex), vector);
            idIndex++;
        }
        List<BruteForceIndex.SearchResult> results = tempIndex.search(query, k);
        return results;
    }

    public static void main(String[] args) {
        float[] query = {1.0f, 0.0f, 0.0f};
        List<float[]> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            float[] floats = new float[3];
            for (int j = 0; j < 3; j++) {
                floats[j] = (float)Math.random();
            }
            list.add(i, floats);
        }
        IVFIndex index = new IVFIndex(3);
        index.train(list);
        List<BruteForceIndex.SearchResult> results = index.search(query, 2);
        for (BruteForceIndex.SearchResult result : results) {
            System.out.println("ID: " + result.id + " Score: " + result.score);
        }
    }
}


