package com.regg.vse.index;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BruteForceIndexTest {
    @Test
    public void exactMatchIsTopResult() {
        BruteForceIndex index = new BruteForceIndex();
        float[] a = {1.0f, 0.0f, 0.0f};
        index.add(42, a);
        List<BruteForceIndex.SearchResult> result = index.search(a, 1);
        assertEquals(42, result.get(0).id);
        assertEquals(1.0f, result.get(0).score);
    }

    @Test
    public void searchMoreVectors() {
        BruteForceIndex index = new BruteForceIndex();
        float[] a = {1.0f, 0.0f, 0.0f};
        float[] b = {2.0f, 3.0f, 4.0f};
        index.add(42, a);
        index.add(52, b);
        List<BruteForceIndex.SearchResult> result = index.search(a, 100);
        assertEquals(2, result.size());
    }

    @Test
    public void bestResultFirst() {
        BruteForceIndex index = new BruteForceIndex();
        float[] query = {1.0f, 0.0f, 0.0f};
        float[] a = {1.0f, 0.0f, 0.0f};
        float[] b = {2.0f, 1.0f, 1.0f};
        float[] c = {1.1f, 0.1f, 0.9f};
        index.add(42, a);
        index.add(52, b);
        index.add(62, c);
        List<BruteForceIndex.SearchResult> result = index.search(query, 3);
        assertEquals(42, result.get(0).id);
        assertEquals(52, result.get(1).id);
        assertEquals(62, result.get(2).id);
    }
}
