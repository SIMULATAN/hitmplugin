package at.hitm.hitmplugin.api;

import spark.Spark;

public class API {

    // TODO: save to config
    private static final int port = 3330;

    private static boolean initialized = false;

    public static void init() {
        if (initialized) return;
        initialized = true;
        Spark.port(port);
        Spark.webSocket("/ws", WebsocketHandler.class);
        Spark.init();
    }

    public static void shutdown() {
        WebsocketHandler.sessions.forEach(s -> s.close(1012, "The server is either stopping or reloading."));
        Spark.stop();
    }
}