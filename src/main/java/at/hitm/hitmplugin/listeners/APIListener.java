package at.hitm.hitmplugin.listeners;

import at.hitm.hitmplugin.api.WebsocketHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreeperPowerEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author _SIM_
 *
 * NOTE: all the EventHandlers have highest priority because the higher the priority, the later the event will get fired.
 * Because its supposed to log the correct messages (which might get modified by other plugins) the events have to fire last.
 */
public class APIListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        // playerjoinillegal will fire when a not whitelisted player tries to join (this shouldn't happen!)
        if (Bukkit.hasWhitelist() && !event.getPlayer().isWhitelisted())
            sendEvent("playerjoinillegal", new JSONObject().put("player", event.getPlayer().getDisplayName()).put("uuid", event.getPlayer().getUniqueId()));
        else
            sendEvent("playerjoin", new JSONObject().put("message", event.getJoinMessage()).put("location", getLocation(event.getPlayer().getLocation())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onLeave(PlayerQuitEvent event) {
        sendEvent("playerquit", new JSONObject().put("message", event.getQuitMessage()).put("location", getLocation(event.getPlayer().getLocation())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        sendEvent("chat", new JSONObject().put("name", event.getPlayer().getDisplayName()).put("uuid", event.getPlayer().getUniqueId()).put("message", event.getMessage()));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        sendEvent("playerdeath", new JSONObject().put("message", event.getDeathMessage()).put("location", getLocation(event.getEntity().getLocation())));
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onExplosion(EntityExplodeEvent event) {
        if (event.getEntityType() == EntityType.CREEPER) {
            sendEvent("creeperexplosion", getLocation(event.getLocation()));
        }
    }

    private static JSONObject getLocation(Location loc) {
        return new JSONObject().put("x", loc.getX()).put("y", loc.getY()).put("z", loc.getZ());
    }

    private static void sendEvent(String name, Object message) {
        WebsocketHandler.sessions.forEach(session -> {
            try {
                session.getRemote().sendString(new JSONObject().put("type", "EVENT").put("name", name).put("message", message).toString());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }
}