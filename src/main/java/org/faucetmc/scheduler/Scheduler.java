package org.faucetmc.scheduler;

import org.faucetmc.server.Faucet;

import java.util.*;

public class Scheduler {

    private long scheduledRelative, asyncRelative;
    private HashMap<Long, List<Runnable>> scheduled = new HashMap<>();
    private HashMap<Long, List<Runnable>> scheduledAsync = new HashMap<>();

    public void scheduleTask(long ticks, Runnable task) {
        scheduled.computeIfAbsent(ticks, (ignored) -> new LinkedList<>()).add(task);
    }

    public void update() {
        runTasks();
        if(scheduled.size() == 0) scheduledRelative = Faucet.MINECRAFT_SERVER.getTickTime();
        runTasksAsync();
    }

    private void runTasks() {
        long tick = Faucet.MINECRAFT_SERVER.getTickTime() - scheduledRelative;
        List<Runnable> list = scheduled.get(tick);
        if(list == null) return;
        for(Runnable r : list) r.run();
    }

    private void runTasksAsync() {
        long tick = Faucet.MINECRAFT_SERVER.getTickTime() - asyncRelative;
        List<Runnable> list = scheduled.get(tick);
        if(list == null) return;
        for(Runnable r : list) Faucet.EXECUTOR_SERVICE.submit(r);
    }
}
