# Learning Log

## Progress
Day 1: repo setup
Day 2: implemented vector math utilities (dot product, cosine similarity, L2 distance) with unit tests
Day 3: implemented BruteForceIndex with top-k heap search and unit tests
Day 4: implemented CLI with functional add, search, and exit options
Day 5: implemented time benchmark for search results in milliseconds
Day 6: implemented recall@k evaluator
Day 7: implemented optimized index search, updated recall@k, added VectorIndex interface
Day 8: implemented parameter tuner for IVFIndex
Day 9: updated SearchBenchmark to account for JVM warmup
Day 10: finished documentation


---


## Day 1
### What I built
- Local Git repo connected to GitHub
- Maven project with Java 23, JUnit dependency
- Minimal App.java and AppTest.java

### What confused me
- GitHub authentication with PAT token

### How I resolved it
- Configured PAT token for remote push access

### Performance notes
- N/A — build setup only

### If I restarted, I would
-

---

## Day 2
### What I built
- VectorMath class with dot, l2Squared, norm, cosine methods
- Input validation and exception handling for mismatched dimensions
- Unit tests for all math operations

### What confused me
- Why cosine similarity needs special handling for zero vectors

### How I resolved it
- Added explicit zero vector guard before division in cosine method

### Performance notes
- N/A — math utilities only

### If I restarted, I would
-

---

## Day 3
### What I built
- BruteForceIndex with add() and search() methods
- Top-k selection using a min-heap (PriorityQueue)
- SearchResult inner class holding id and score
- Unit tests for exact match, ordering, edge cases

### What confused me
- Why a for-each loop wouldn't work for search — needed index i to look up IDs
- Why Collections.reverse() was needed after draining the heap
- Understanding static methods vs instance methods

### How I resolved it
- Switched to regular for loop to access both vector and ID by index
- Recognized min-heap drains worst-first, reverse gives best-first
- Learned static belongs to the class, instance belongs to an object

### Performance notes
- N/A — correctness baseline, not yet benchmarked

### If I restarted, I would
-

---

## Day 4
### What I built
- SearchCLI with add, search, and exit options
- Scanner-based terminal input
- switch statement for menu navigation

### What confused me
- Difference between static and instance method calls
- When to use switch vs if/else

### How I resolved it
- Static methods called with ClassName.method(), instance methods called on objects
- Switch is cleaner when checking one variable against many specific values

### Performance notes
- N/A — CLI interface only

### If I restarted, I would
-

---

## Day 5
### What I built
- SearchBenchmark with random vector generation
- Nanosecond timing converted to milliseconds
- Multi-scale testing across 1k, 10k, 100k, 1M vectors

### What confused me
- Why nanoseconds instead of milliseconds for timing

### How I resolved it
- Searches can complete in under 1ms — milliseconds would show 0 for small datasets

### Performance notes
- 1,000 vectors: ~3ms
- 10,000 vectors: ~5ms
- 100,000 vectors: ~29ms
- 1,000,000 vectors: ~202ms

### If I restarted, I would
-

---

## Day 6
### What I built
- RecallEvaluator comparing two indexes
- VectorIndex interface for polymorphic search
- Recall score calculated as fraction of ground truth results found

### What confused me
- Variable naming conflict between field groundTruth and local variable groundTruth

### How I resolved it
- Renamed local variable to trueResults to avoid shadowing the field

### Performance notes
- BruteForce vs BruteForce always yields recall 1.0 — confirmed evaluator correctness

### If I restarted, I would
-

---

## Day 7
### What I built
- IVFIndex with train(), add(), and search() methods
- Cluster-based vector assignment using L2 distance to centroids
- clusterIDs parallel list to preserve original vector IDs
- Temporary BruteForceIndex inside search to reuse top-k logic

### What confused me
- Variable name conflicts in nested loops (i vs j vs c)
- How Java differentiates which add() to call — learned it's based on the object, not imports
- Why IVFIndex needs train() before add()

### How I resolved it
- Used distinct loop variable names (i, j, c) for nested loops
- Method dispatch is determined by the object the method belongs to
- Centroids must exist before vectors can be assigned to clusters

### Performance notes
- IVF with 2 clusters: high recall (~0.88-1.0), slowest search
- IVF with 50+ clusters: recall drops below 0.4

### If I restarted, I would
-

---

## Day 8
### What I built
- ParameterTuner testing multiple numClusters values
- Combined recall and speed measurement per experiment
- Tested cluster counts: 2, 5, 10, 20, 50, 100, 1000

### What confused me
- Why recall doesn't improve with more clusters — expected a bell curve

### How I resolved it
- More clusters = smaller search space per cluster = higher chance of missing correct results
- Extreme case: 1000 clusters with 100k vectors gives ~100 vectors per cluster, recall drops to 0.04

### Performance notes
- 2 clusters at 100k vectors: 16-28ms, recall ~0.88-1.0
- 5 clusters: 1-2ms, recall drops to 0.44
- 50+ clusters: sub-millisecond, recall below 0.32
- 1000 clusters: 0ms, recall 0.04

### If I restarted, I would
-

---

## Day 9
### What I built
- JVM warmup phase (10 runs before measurement)
- Averaged timing over 20 measured runs
- More accurate and stable benchmark results

### What confused me
- Why first benchmark runs are artificially slow

### How I resolved it
- JVM interprets then compiles code — first runs are slower until JIT compiler kicks in
- Warmup runs let JVM stabilize before measuring

### Performance notes
- Post-warmup BruteForce at 100k vectors: ~39ms averaged over 20 runs
- Pre-warmup single measurement was 29ms — less accurate
- 1k vectors: 0ms (too fast for millisecond precision)
- 10k vectors: ~2ms

### If I restarted, I would
- spend less time in-between coding sessions as to not forget what I did the session prior
- not worry too much about making a perfect project, thus leaving room for future implementations