package com.example.Logic.Algorithms;

import java.util.Queue;
import com.example.Logic.Process;

public class NonPreemptivePriority implements Scheduler {
    // Holds the process chosen to run; keep it until it finishes.
    private Process currentSelected = null;

    @Override
    public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        // If previously selected process is still alive, keep it running.
        if (currentSelected != null && !currentSelected.isFinished()) {
            return currentSelected;
        }

        // Otherwise choose a new one by priority (lower number = higher priority).
        // Same thing as PreemptivePriority.
        Process best = null;
        for (Process p : readyQueue) {
            if (best == null
                    || p.getPriority() < best.getPriority()
                    || (p.getPriority() == best.getPriority() && p.getArrivalTime() < best.getArrivalTime())
                    || (p.getPriority() == best.getPriority()
                    && p.getArrivalTime() == best.getArrivalTime()
                    && p.getId() < best.getId())) {
                best = p;
            }
        }

        currentSelected = best;
        return currentSelected;
    }
}