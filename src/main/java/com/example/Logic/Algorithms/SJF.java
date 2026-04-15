package com.example.Logic.Algorithms;

import com.example.Logic.Process;
import java.util.Queue;

//non-preemptive version of SJF
public class SJF implements Scheduler {
   private Process currentRunning = null;

    @Override
    public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        // 1. If a process is already running and not finished, keep it 
        // it works as a lock on the CPU until it finishes, even if a shorter process arrives 
        if (currentRunning != null && !currentRunning.isFinished()) {//if there is a process running and it is not finished, return it
            return currentRunning;
        }

        // 2. If CPU is idle or process finished, find the shortest burst time among arrived processes 
        Process shortest = null;
        for (Process p : readyQueue) {
            if (p.getArrivalTime() <= currentTime) {
                if (shortest == null || p.getBurstTime() < shortest.getBurstTime()) {
                    shortest = p;
                }
            }
        }

        currentRunning = shortest;
        return currentRunning;
    }
}