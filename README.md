# Vector Search Engine (Project 1)

Goal: Learn vector similarity search by building it from scratch.

## Planned Features
- Brute-force nearest neighbor search (baseline)
- Optimized search (to be implemented)
- Benchmarking (latency, throughput)
- Evaluation (recall@k vs brute-force ground truth)

## Repo Structure
- docs/   : design notes + learning log
- data/   : small sample datasets (no huge files)
- bench/  : benchmark outputs and notes

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
Day 10: 

## Notes: Future implementations
- exploring more efficient search algorithms for the index (local beam, hill-climbing, A*, other AI related search algorithms)
- reading from files
- adding a remove from index feature
- refactor reused logic to their own methods