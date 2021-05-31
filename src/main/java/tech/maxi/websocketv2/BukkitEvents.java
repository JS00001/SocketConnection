package tech.maxi.websocketv2;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class BukkitEvents {
    private final static WebSocketV2 instance = WebSocketV2.getInstance();

    public static void sendCommand(String command) {
        Bukkit.getScheduler().runTask(instance, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
    }

    public static String format(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
