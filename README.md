# Vector Search Engine
A vector similarity search engine built from scratch in Java, featuring brute-force and IVF-based approximate nearest neighbor search with benchmarking and recall evaluation.

---

## Overview
This engine indexes high-dimensional float vectors and retrieves the top-k most similar results for a given query using cosine similarity and L2 distance. Two indexing strategies are implemented: a brute-force linear scan as the correctness baseline, and an IVF (Inverted File Index) that clusters vectors at index time to narrow the search space at query time. A benchmarking layer measures search latency across dataset sizes, and a recall evaluator compares IVF accuracy against brute-force ground truth — exposing the speed/accuracy tradeoff directly.

---

## Motivation
Vector search is the infrastructure behind semantic search, recommendation systems, and RAG pipelines — anywhere you need to find "things similar to this" across large datasets. Most engineers use it as a black box through libraries like FAISS or Pinecone. Building it from scratch means understanding why approximate search exists, what clustering actually does to search behavior, and where the speed/accuracy tradeoff comes from. The recall evaluation table below makes that tradeoff concrete: more clusters means faster search but lower accuracy, because correct results increasingly end up in unselected clusters.

---

## Benchmark Results

Brute-force search, averaged over 20 runs with 10 JVM warmup runs:

| Vectors   | Avg Search Time |
|-----------|----------------|
| 1,000     | 0 ms           |
| 10,000    | 2 ms           |
| 100,000   | 39 ms          |

IVF recall vs. speed at 100k vectors (Recall@25):

| numClusters | Recall@25  | Avg Search Time |
|-------------|------------|-----------------|
| 2           | 0.88–1.0   | 16–28 ms        |
| 5           | 0.44       | 1–2 ms          |
| 50          | 0.32       | ~0 ms           |
| 1000        | 0.04       | ~0 ms           |

As clusters increase, each cluster shrinks — correct results end up in unselected clusters more often, dropping recall while search time falls.

---

## Architecture & Design

### Architectural Style
Layered architecture loosely modeled on MVC — each package has a single responsibility and only depends on packages below it.

### Package Structure
```
vector-search-engine/
├── docs/
│   ├── design.md
│   └── learning_log.md
├── src/main/java/vse/
│   ├── math/
│   │   └── VectorMath.java         # dot product, cosine similarity, L2 distance
│   ├── index/
│   │   ├── VectorIndex.java        # shared interface for all index types
│   │   ├── BruteForceIndex.java    # linear scan baseline, top-k via min-heap
│   │   └── IVFIndex.java          # cluster-based approximate search
│   ├── benchmark/
│   │   ├── SearchBenchmark.java    # latency measurement across dataset sizes
│   │   └── ParameterTuner.java     # sweeps numClusters, records recall + speed
│   ├── recall/
│   │   └── RecallEvaluator.java    # compares IVF against brute-force ground truth
│   └── cli/
│       └── SearchCLI.java          # terminal interface for add/search
└── pom.xml
```

### Key Design Decisions
- **Min-heap for top-k:** O(log k) insertion vs O(N log N) full sort — efficient for large N, small k
- **VectorIndex interface:** allows BruteForceIndex and IVFIndex to be swapped in RecallEvaluator without changing evaluation logic
- **BruteForce as ground truth:** acts as the control — recall is measured as the fraction of brute-force results the IVF index finds
- **KD-Tree ruled out:** degrades in high dimensions (curse of dimensionality); IVF handles up to 128-dimensional vectors effectively

### Known Limitations
- Random centroid initialization can produce unbalanced clusters
- IVF searches only the single nearest cluster — results in neighboring clusters are missed
- All vectors are in-memory; nothing persists between sessions

---

## How to Run

**Prerequisites:** Java 23, Maven

```bash
# Run tests
mvn test

# Run CLI
mvn exec:java -Dexec.mainClass="vse.cli.SearchCLI"

# Run benchmark
mvn exec:java -Dexec.mainClass="vse.benchmark.SearchBenchmark"
```

---

## What I Learned
Speed and accuracy have a fundamental tradeoff in approximate search — more clusters means faster queries but lower recall, because correct results increasingly land in unselected clusters. Building the recall evaluator made that tradeoff visible rather than theoretical. I also learned the value of measuring performance from day one: the JVM warmup effect (first runs are slow until the JIT compiler kicks in) only became obvious because the benchmark was there to show it. Project structure and documentation turned out to matter more than expected — having a design doc and learning log made it easier to pick up where I left off between sessions.
