# Vector Search Engine

## Overview
A vector similarity search engine built from scratch in Java, featuring brute-force and IVF-based
approximate nearest neighbor search with benchmarking and recall evaluation.

---

## Motivation
This project was built as a structured introduction to systems programming, using vector search as a vehicle to apply
concepts from a Numerical Methods and Computing course. The goal was to reinforce core Java fundamentals —
data structures, algorithm design, and performance measurement — while building something non-trivial from scratch.
Vector search was chosen deliberately: it sits at the intersection of algorithms and machine learning infrastructure,
making it both technically educational and relevant to modern software engineering.

---

## Architecture & Design
This project is divided into 4 layers. The foundation being the math package where the basic mathematical methods live
for vector computations. The next layer, index, depends on those math functions in order to store and search for vectors.
This is where the issue arose of RecallEvaluator not knowing which index search function to use. To resolve that, the
search method was put into the VectorIndex interface to be able to run both search methods from the two different index
classes. Benchmark and recall require the index package to run performance and speed tests and return those results. The
top-most layer being cli is where the user interacts with everything behind the system.

### Architectural Style
The structure of this project mirrors a Model-View-Controller architecture style to keep classes separated if they do
not directly relate or interact with each other. It also gave me an opportunity to experiment with a newly learned
project structure promoting clarity and tidiness.

### Package Structure
vector-search-engine/
├── docs/
│   ├── design.md
│   └── learning_log.md
├── data/
├── bench/
├── src/
│   ├── main/java/com/regg/vse/
│   │   ├── math/
│   │   │   └── VectorMath.java
│   │   ├── index/
│   │   │   ├── VectorIndex.java
│   │   │   ├── BruteForceIndex.java
│   │   │   └── IVFIndex.java
│   │   ├── benchmark/
│   │   │   ├── SearchBenchmark.java
│   │   │   └── ParameterTuner.java
│   │   ├── recall/
│   │   │   └── RecallEvaluator.java
│   │   └── cli/
│   │       └── SearchCLI.java
│   └── test/java/com/regg/vse/
│       ├── math/
│       │   └── VectorMathTest.java
│       └── index/
│           └── BruteForceIndexTest.java
└── pom.xml

---

## Benchmark Results
Averaged over 20 runs with 10 warmup runs to account for JVM warmup.

| Vectors | Avg Search Time |
|---------|----------------|
| 1,000 | 0ms |
| 10,000 | 2ms |
| 100,000 | 39ms |

---

## Recall Evaluation
As more clusters are created, search time decreases but recall drops — faster but less accurate,
since correct results may end up in unselected clusters.

| numClusters | Recall@25 | Avg Search Time |
|-------------|-----------|-----------------|
| 2 | 0.88 - 1.0 | 16-28ms |
| 5 | 0.44 | 1-2ms |
| 50 | 0.32 | ~0ms |
| 1000 | 0.04 | ~0ms |

---

## How to Run
**Prerequisites:** Java 23, Maven

- Run tests: `mvn test`
- Run from IntelliJ: click the green play button next to any `main` method
- Run CLI from terminal: `mvn exec:java -Dexec.mainClass="com.regg.vse.cli.SearchCLI"`

---

## What I Learned
How Java functions and various DSA algorithms translate into a real project outside of coursework assignments.
I also learned a lot about project structure from an architectural perspective, the importance of keeping package
structure organized and neat for future refactoring and new implementations. Before this project, I did not consider
much how important it is for the code to have a built-in way of measuring performance and speed. Most notably, the
documentation process, both for my own personal reference and for others reviewing my code. Having a well-structured
README and Design Doc for others and a Learning Log for me was a great addition and something I will be sure to keep
doing for future personal projects. In terms of what I learned from the vector search engine itself, speed and accuracy
tend to have a trade-off. Faster searching will often return less accurate results and in order to get higher accuracy,
the search will be slower.


