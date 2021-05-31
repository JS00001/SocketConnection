package tech.maxi.websocketv2;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.URISyntaxException;

public final class WebSocketV2 extends JavaPlugin {

    private static WebSocketV2 instance;
    private static Connection ws;

    @Override
    public void onEnable() {
        try {
            instance = this;
            System.out.println("Plugin Enabled");
            ws = new Connection(new URI("ws://69.55.61.83:8080"));
            ws.connect();
            this.registerEvents();
            this.registerCommands();
        } catch(URISyntaxException ignored) {}
    }

    private void registerEvents() {
        this.getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
    }

    private void registerCommands() {
        String[] commands = {"ping", "tp", "death", "name"};
        for(String command : commands) {
            this.getCommand(command).setExecutor(new Commands());
            System.out.println("Loaded Command: " + command);
        }
    }

    public static WebSocketV2 getInstance() {
        return instance;
    }

    public static Connection getWs() {
        return ws;
    }
}
