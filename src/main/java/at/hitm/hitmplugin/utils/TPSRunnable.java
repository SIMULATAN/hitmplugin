package at.hitm.hitmplugin.utils;

import at.hitm.hitmplugin.Main;

public class TPSRunnable implements Runnable {

    long lastPoll = System.currentTimeMillis() - 3000;
    Main plugin;

    public TPSRunnable(Main instance){
        plugin = instance;
    }

    @Override
    public void run() {
        long now = System.currentTimeMillis();
        long timeSpent = (now - lastPoll) / 1000;
        if (timeSpent == 0){
            timeSpent = 1;
        }
        float tps = 40F / timeSpent;
        PerformanceMonitor.setTps(tps);
        lastPoll = now;
    }
}