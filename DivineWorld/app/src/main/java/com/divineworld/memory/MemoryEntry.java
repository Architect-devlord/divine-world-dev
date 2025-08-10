package com.divineworld.memory;

public class MemoryEntry {
    public String event;
    public String subject;
    public long time;     
    public double impact;  

    public MemoryEntry(String event, String subject, long time, double impact) {
        this.event = event;
        this.subject = subject;
        this.time = time;
        this.impact = impact;
    }
}