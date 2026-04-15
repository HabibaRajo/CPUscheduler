package com.example.Logic.Algorithms;

import com.example.Logic.Process;
import java.util.Queue;

public class PreemptivePriority implements Scheduler {
    @Override
    public Process getNextProcess(Queue<Process> readyQueue, int currentTime) {
        // Preemptive: every tick choose the highest-priority ready process.
        // Assumption: smaller priority number => higher priority.
        Process best = null;

        // Loop through processes in the ready queue
        for (Process p : readyQueue) {
            if (best == null
                    || p.getPriority() < best.getPriority() // check if priority is higher
                    || (p.getPriority() == best.getPriority() && p.getArrivalTime() < best.getArrivalTime()) // tie-breaker if priority equal: earlier arrival time
                    || (p.getPriority() == best.getPriority()
                    && p.getArrivalTime() == best.getArrivalTime()
                    && p.getId() < best.getId())) { // Lastly, another tie-breaker (priority and arrival are the same): lower process ID
                best = p;
            }
        }

        return best;
    }
}