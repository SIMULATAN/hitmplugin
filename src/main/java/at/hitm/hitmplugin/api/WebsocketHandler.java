package at.hitm.hitmplugin.api;

import at.hitm.hitmplugin.utils.PerformanceMonitor;
import at.hitm.hitmplugin.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebSocket
public class WebsocketHandler {

    public static List<Session> sessions = new ArrayList<>();

    @OnWebSocketConnect
    public void connected(final Session session) {
        System.out.println(session.getRemote().getInetSocketAddress().getAddress().getHostAddress() + " connected.");
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(final Session session, final int statusCode, final String reason) {
        System.out.println(session.getRemote().getInetSocketAddress().getAddress().getHostAddress() + " disconnected.");
        sessions.remove(session);
    }

    @OnWebSocketError
    public void onError(final Session session, final Throwable error) {
        error.printStackTrace();
        try {
            session.getRemote().sendString(new JSONObject().put("error", true).put("message", error.getMessage()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(final Session sessionRaw, final String messageRaw) {
        int id = -1;
        EnumRequestType action = null;
        try {
            JSONObject requestAsJson = new JSONObject(messageRaw);
            id = requestAsJson.has("id") ? requestAsJson.getInt("id") : -1;
            if (!requestAsJson.has("type")) throw new JSONException("No action specified.");
            action = EnumRequestType.parse(requestAsJson.getString("type"));
            if (action == null) throw new JSONException("No action specified.");
            if (action.requiresData() && !requestAsJson.has("data")) throw new JSONException("No data.");
            Object requestData = null;
            if (requestAsJson.has("data")) requestData = requestAsJson.get("data");
            if (action == EnumRequestType.CHAT) {
                if (!(requestData instanceof JSONObject)) return;
                JSONObject data = (JSONObject) requestData;
                Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "<DISCORD> " + ChatColor.GRAY + data.getString("user") + ChatColor.GOLD + " > " + ChatColor.WHITE + data.getString("message"));
            } else if (action == EnumRequestType.USERINFO) {
                UUID uuid = Utils.getUUID((String) requestData);
                if (uuid == null) throw new JSONException("Invalid UUID.");
                OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);
                System.out.println("hi");
                System.out.println(player.getPlayer());
                if (player.getFirstPlayed() == 0) respond(sessionRaw, id, action, new JSONObject().put("unknown", true));
                else respond(sessionRaw, id, action, player.isOnline() && player.getPlayer() != null ? getUserinfo(player.getPlayer()) : getUserinfoOffline(player));
            } else if (action == EnumRequestType.ONLINE_PLAYERS) {
                JSONArray players = new JSONArray();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    players.put(getUserinfo(player));
                }
                respond(sessionRaw, id, action, players);
            } else if (action == EnumRequestType.WHITELIST) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString((String) requestData));
                p.setWhitelisted(true);
                respond(sessionRaw, id, action, p.getName() + " got whitelisted.");
            } else if (action == EnumRequestType.STATS) {
                Runtime runtime = Runtime.getRuntime();
                respond(sessionRaw, id, action, new JSONObject().put("memoryused", runtime.totalMemory() - runtime.freeMemory()).put("maxmemory", runtime.maxMemory()).put("tps", PerformanceMonitor.getTps()).put("online", Bukkit.getOnlinePlayers().size()));
            }
        } catch (Exception e) {
            try {
                sessionRaw.getRemote().sendString(new JSONObject().put("status", "error").put("success", false).put("id", id).put("type", action != null ? action : "NONE").put("errormessage", e.getMessage()).toString());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (!(e instanceof JSONException))
                e.printStackTrace();
        }
    }

    private static JSONObject getUserinfoOffline(OfflinePlayer player) {
        return new JSONObject().put("name", player.getName()).put("online", player.isOnline()).put("lastonline", player.getLastPlayed()).put("joindate", player.getFirstPlayed()).put("uuid", player.getUniqueId());
    }

    private static JSONObject getUserinfo(Player player) {
        Location loc = player.getLocation();
        return getUserinfoOffline(player).put("location", new JSONObject().put("x", loc.getX()).put("y", loc.getY()).put("z", loc.getZ())).put("health", player.getHealth()).put("ping", player.getPing());
    }

    private static void respond(Session session, Object msg) {
        respond(session, -1, msg);
    }

    private static void respond(Session session, int id, Object msg) {
        respond(session, id, EnumRequestType.CUSTOM, msg);
    }

    private static void respond(Session session, int id, EnumRequestType initialType, Object msg) {
        try {
            session.getRemote().sendString(new JSONObject().put("data", msg).put("id", id).put("type", initialType).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}