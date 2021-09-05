package at.hitm.hitmplugin.utils;

public class PerformanceMonitor {

    private static float tps;

    public static float getTps() {
        return tps;
    }

    public static void setTps(float tps) {
        PerformanceMonitor.tps = tps;
    }
}