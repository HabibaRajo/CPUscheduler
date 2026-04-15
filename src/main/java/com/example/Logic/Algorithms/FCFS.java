package com.example.Logic.Algorithms;
import com.example.Logic.Process;

import java.util.Queue;

// First-Come, First-Served (FCFS) scheduling algorithm implementation
public class FCFS implements Scheduler {
    @Override
    public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        return readyQueue.peek(); //return first one, first one arrived
    }
}