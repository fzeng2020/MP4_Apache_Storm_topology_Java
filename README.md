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

To make the implementation easier, we have provided a boilerplate for the topology needed in the following file: `src/main/resources/part_b_topology.yaml`.

implement all components and to wire up these components.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

see the corresponding result in Redis.

# Exercise C: Normalizer Bolt

The application we developed in Exercise B counts the words “Apple” and “apple” as two different words.
However, if we want to find the top N words, we have to count these words the same.
Additionally, we don’t want to take common English words into consideration.

Therefore, in this part, we are going to normalize the words by adding a normalizer bolt that gets the words from the splitter, normalizes them, and then sends them to the counter bolt.
The responsibility of the normalizer is to:

1. Make all input words lowercase.
2. Remove common English words.

To make the implementation easier, we have provided a boilerplate for the normalizer bolt in the following file: `src/main/java/edu/illinois/storm/NormalizerBolt.java`.

There is a list of common words to filter in this class, so please make sure you use this exact list to in order to receive the maximum points for this part.
After finishing the implementation of this class, you have to wire up the topology with this bolt added to the topology.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_c_topology.yaml -s 15000

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently and maybe develop some simple tools to help you build some tests.

# Exercise D: Top N Words

In this exercise, we are going to add a new bolt which uses the output of the count bolt to keep track of and report the top N words.
Upon receipt of a new count from the count bolt, it updates the top N words and emits top N words set anytime it changes.

To output the top N words set, you should use ", " connect all top words.
You don't need to worry about the sequence.
The result should contain and only contain the top N words.
For example, if 3-top words are "blue", "red" and "green", "blue, red, green", "red, blue, green" are all correct answer.

To save the output to Redis, you should save field-value pairs ("top-N", {top N words string}") in hashes `partDTopN`.
It's not the best way to save a set in Redis, but Redis is not the key point for this assignment.
So we decided to make it easier for you to implement by keeping using hashes in part d.
We've provided you the template in `src/main/java/edu/illinois/storm/TopNStoreMapper.java`.
To make it clear, in the auto-grader, we retrieve your answer from Redis by executing script equivalent to:

    redis-cli -a uiuc_cs498_mp4 HGET partDTopN top-N
    # the output for example above should be:
    # "blue, red, green"

After finishing the implementation of this class, you have to wire up the topology with this bolt added to the topology.

You need to implement all components and to wire up these components.
To make the implementation easier, we have provided boilerplates.

Note that, when auto-graded, this topology will run for 15 seconds and automatically gets killed after that.
For this part, different algorithm will have huge different performance.
15s is not a strict restriction, but your solution may failed to finish within 15s on Coursera even though it works on your local environment.
We suggest you to refine your algorithm to make it more efficient if you failed in this part while you get correct answer locally.
Algorithm with time complexity of `O(nN)` seems to be save to pass where n is the number of total words and N is the amount of top words you need track.

You can build and run the application using the command below inside the container:

    # The template folder will be map to /mp4/solution in the container if you follow our instruction correctly
    cd /mp4/solution
    mvn clean package
    storm jar ./target/storm-example-0.0.1-SNAPSHOT.jar org.apache.storm.flux.Flux --local -R /part_d_topology.yaml -s 15000

If your solution is right, you should see the corresponding result in Redis.
We suggest you think about how you can debug your solution efficiently and maybe develop some simple tools to help you build some tests.
