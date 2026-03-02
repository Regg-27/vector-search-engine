# Design Doc — Vector Search Engine

## Problem
Given a query vector q, find the top-k most similar vectors from a database of N vectors.

## Similarity Metrics (planned)
- Cosine similarity
- L2 distance

## Baseline Approach
- Brute-force scan:
    - compute similarity against every vector
    - keep best k results

Time complexity (baseline): O(N * D) per query  
(where D = vector dimension)

## Optimized Approach (to decide)
Candidates:
- IVF (inverted file index / clustering)
- HNSW-like graph (harder)
- KD-tree (only good for low dimensions)