package at.hitm.hitmplugin.warps;

import at.hitm.hitmplugin.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.json.JSONObject;

import java.util.Objects;

public class Warp {

    private final Location location;
    private final String name;

    public Warp(JSONObject input) {
        this.location = new Location(Bukkit.getWorld(Objects.requireNonNull(Utils.getUUID(input.getString("world")))), input.getDouble("x"), input.getDouble("y"), input.getDouble("z"), input.has("yaw") ? input.getFloat("yaw") : 0, input.has("pitch") ? input.getFloat("pitch") : 0);
        this.name = input.getString("name");
    }

    public Warp(String name, Location loc) {
        this.name = name;
        this.location = loc;
    }

    public Location getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("world", location.getWorld().getUID())
                .put("x", location.getX())
                .put("y", location.getY())
                .put("z", location.getZ())
                .put("yaw", location.getYaw())
                .put("pitch", location.getPitch());
    }
}