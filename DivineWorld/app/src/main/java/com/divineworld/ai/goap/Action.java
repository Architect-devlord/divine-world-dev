package com.divineworld.ai.brain.goap;

import com.divineworld.npc.DWBot;

/**
 * Action: small executable unit used by GoalPlanner.
 *  - id: human-readable identifier
 *  - runner: executes using bot context and returns a status.
 *
 * Implementations should be lightweight and call into DWBot to interact with world.
 */
public class Action {

    public enum Status { SUCCESS, RUNNING, FAILURE }

    public interface Runner {
        Status run(DWBot bot);
    }

    private final String id;
    private final Runner runner;

    public Action(String id, Runner runner) {
        this.id = id;
        this.runner = runner;
    }

    public Status run(DWBot bot) {
        return runner.run(bot);
    }

    public String getId() { return id; }
}
