package at.hitm.hitmplugin.warps;

import at.hitm.hitmplugin.Main;
import com.google.common.io.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.stream.Collectors;

public class WarpManager {

    private static final File CONFIG_FILE = new File("warps.json");

    /*
     * Structure of the warps in the file:
     * [
     *   {
     *     "name": "testwarp",
     *     "world": "f00685de-0236-4992-b811-5418767e482d",
     *     "x": 187.69,
     *     "y": 69.420,
     *     "z": 1337.69,
     *     "yaw": 69,
     *     "pitch": 13.37
     *   }
     * ]
     */

    public static HashMap<String, Warp> warps = new HashMap<>();

    public static void loadFromFile() {
        warps.clear();
        try {
            if (!CONFIG_FILE.exists()) return;
            JSONArray initialConfig = new JSONArray(String.join("\n", Files.readLines(CONFIG_FILE, StandardCharsets.UTF_8)));
            for (int i = 0; i < initialConfig.length(); i++) {
                JSONObject warp = initialConfig.getJSONObject(i);
                if (warp.has("world") && warp.has("x") && warp.has("y") && warp.has("z") && warp.has("name")) {
                    warps.put(warp.getString("name").toLowerCase(), new Warp(warp));
                } else {
                    System.err.println("Invalid Warp: " + warp);
                }
            }
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
            e.printStackTrace();
        }
    }

    public static void saveToFile() {
        if (warps.size() == 0) return;
        try {
            Files.write(new JSONArray(warps.values().stream().map(Warp::toJson).collect(Collectors.toList())).toString(2), CONFIG_FILE, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}