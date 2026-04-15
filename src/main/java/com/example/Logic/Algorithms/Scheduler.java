package com.example.Logic.Algorithms;
import com.example.Logic.Process;

import java.util.Queue;

// Interface for scheduling algorithms
public interface Scheduler {    
    // select the next process to execute based on the scheduling algorithm
    Process getNextProcess(Queue<Process> readyQueue, int currentTime);
}

