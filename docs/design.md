# Design Doc — Vector Search Engine

## Problem
Given a query vector q, find the top-k most similar vectors from a database of N vectors.

## Similarity Metrics 
- Cosine similarity
- L2 distance

## Baseline Approach
- Brute-force scan:
    - compute similarity against every vector
    - keep best k results

Time complexity (baseline): O(N * D) per query  
(where D = vector dimension)

## Optimized Approach
IVF (inverted file index / clustering) was chosen in order to decrease search time with large amounts of vectors 
in the index. A KD-Tree would not work well for the large dimensions this vector search engine is built for 
(up to 128 dimensions for each vector). HNSW would have worked well but its implementation would have been more complex
than this project was designed for but there is room for that to be included in the future.


## Design Decisions
A min-heap was used to have quick access to the lowest/worst selections when searching for vectors to return after the
search. The heap gives O(log k) insertion and O(1) peek at the worst element, but the overall search is O(N log k) vs 
the O(N log N) for sorting. The VectorIndex interface resolved the issue of two different search methods between 
BruteForceIndex and IVFIndex unable to be used simultaneously in the RecallEvaluator where their performances are 
compared. Having BruteForceIndex as the ground truth acts as the control or the un-enhanced indexing and searching
method, giving us a reliable algorithm that our IVFIndex and future optimized approach(es) can be compared to.

## Known Limitations
Random initialization of centroids was a simpler alternative to create reference points for the clusters but it can 
cause unbalanced clusters. There are also limitations with the single cluster search. At search time, 
IVFIndex only searches the nearest cluster. If the true best match is in a neighboring cluster, it gets missed entirely.
That's why recall drops as numClusters increases. As for data persistence, all vectors are stored in memory as 
ArrayLists. When the program stops, everything is lost. A real search engine would save to disk and reload on startup.

## Notes: Future implementations
- exploring more efficient search algorithms for the index (HNSW)
- reading from files
- adding a 'remove from index' feature
- refactoring reused logic into their own methods
- instead of random vectors, generate embeddings using a small neural network
