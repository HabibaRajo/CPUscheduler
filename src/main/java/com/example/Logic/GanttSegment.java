package com.example.Logic;

public class GanttSegment {
    private final int startTime;
    private final int endTime;
    private final int pid;

    public GanttSegment(int startTime, int endTime, int pid) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.pid = pid;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public int getPid() {
        return pid;
    }

}
