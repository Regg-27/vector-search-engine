package com.regg.vse.index;

import java.util.List;

public interface VectorIndex {
    List<BruteForceIndex.SearchResult> search(float[] query, int k);
}
