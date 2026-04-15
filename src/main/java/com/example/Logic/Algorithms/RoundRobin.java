package com.example.Logic.Algorithms;

import com.example.Logic.Process;
import java.util.Queue;

public class RoundRobin implements Scheduler {
    private final int timeQuantum = 3; // Fixed at 3 seconds as requested
    private int quantumTracker = 0;
    private Process currentlyRunning = null;

    @Override
    public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        // 1. Check if the process that was running in the previous tick is still valid
        if (currentlyRunning != null) {
            quantumTracker++;

            // If the process finished its burst, it is handled by SimulationManager.tick()
            // We just need to reset our tracker here.
            if (currentlyRunning.isFinished()) {
                currentlyRunning = null;
                quantumTracker = 0;
            } 
            // 2. Preemption Logic: If quantum limit (3s) is reached
            else if (quantumTracker >= timeQuantum) {
                // If there are other processes waiting, swap this one out
                if (!readyQueue.isEmpty()) {
                    readyQueue.add(currentlyRunning);
                    currentlyRunning = null;
                    quantumTracker = 0;
                } else {
                    // Optimization: If queue is empty, keep running the same process 
                    // and reset tracker to start a new quantum.
                    quantumTracker = 0;
                }
            }
        }

        // 3. Dispatching: If CPU is idle, pick the next process from the queue
        if (currentlyRunning == null && !readyQueue.isEmpty()) {
            currentlyRunning = readyQueue.poll();
            quantumTracker = 0;
        }

        return currentlyRunning;
    }
}