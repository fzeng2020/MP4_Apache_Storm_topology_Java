# Apache Storm

## 1. Overview

The final goal is to build a topology that finds the top N words in a given corpus.


## 2. General Requirements

All these are designed to work on the **Docker image** 

# Java submission

## Overview and Requirements

use `Storm V1.2.2`, `Redis 5.0.3` and `Open JDK 8`.

## Set up the environment

**Step 1**: Start the "default" Docker machine that you created when following the "Tutorial: Docker installation" in week 4, run:

    docker-machine start default
    docker-machine env
    # follow the instruction to configure your shell: eval $(...)

**Step 2**: Download the Dockerfile and related files, build, and run the docker image, run:

    cd MP4_docker
    docker build -t mp4_docker .

## Procedures

**Step 3**: Download the Java templates and change the current folder, run:

    cd MP4
    docker run -it -v "$(pwd)":/mp4/solution mp4_docker


# A: Simple Word Count Topology

build a simple word counter that counts the words a random sentence spout generates.

# B: Input Data from a File

create a new spout that reads data from an input file and emits each line as a tuple.
Remember to put a 1-second sleep after reading the whole file to avoid a busy loop.



After finishing the implementation of `FileReaderSpout `class, you have to wire up the topology with this new spout.

implement all components and to wire up these components.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

see the corresponding result in Redis.

# C: Normalizer Bolt

we don’t want to take common English words into consideration.

in this part, normalize the words by adding a normalizer bolt that gets the words from the splitter, normalizes them, and then sends them to the counter bolt.
The responsibility of the normalizer is to:

1. Make all input words lowercase.
2. Remove common English words.


 see the corresponding result in Redis.


# D: Top N Words

Add a new bolt which uses the output of the count bolt to keep track of and report the top N words.
Upon receipt of a new count from the count bolt, it updates the top N words and emits top N words set anytime it changes.

To output the top N words set,  use ", " connect all top words.

To save the output to Redis, save field-value pairs ("top-N", {top N words string}") in hashes `partDTopN`.
