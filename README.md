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
