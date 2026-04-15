package com.example.Logic.Algorithms;

import com.example.Logic.Process;
import java.util.Queue;


//preemptive version of SJF
public class SRJF implements Scheduler {

    @Override
     public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        Process shortestRemaining = null;

        // Re-scan every second to see if a shorter task has arrived 
        for (Process p : readyQueue) {
            if (p.getArrivalTime() <= currentTime) {
                if (shortestRemaining == null || p.getRemainingTime() < shortestRemaining.getRemainingTime()) {
                    shortestRemaining = p;
                }
            }
        }

        return shortestRemaining;
    }
}