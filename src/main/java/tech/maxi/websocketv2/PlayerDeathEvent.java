package tech.maxi.websocketv2;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class PlayerDeathEvent implements Listener {
    public static HashMap<Player, String> deathsList = new HashMap<>();

    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent e) {
        Player p = e.getEntity();
        Connection socket = WebSocketV2.getWs();

        String coords = "(" + p.getLocation().getBlockX() + ", " + p.getLocation().getBlockY() + ", " + p.getLocation().getBlockZ() + ")";
        if(deathsList.containsKey(p)) deathsList.replace(p, coords);
        else deathsList.put(p, coords);

        p.sendMessage(BukkitEvents.format("&9Use /death to view your death coords."));
        socket.send("{\"type\":\"death\",\"player\":\"pName\",\"coords\":\"coordsName\"}".replace("pName", p.getDisplayName()).replace("coordsName", coords));
    }

    public static HashMap<Player, String> getDeathsList() {
        return deathsList;
    }
}

