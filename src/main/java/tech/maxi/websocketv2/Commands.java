package tech.maxi.websocketv2;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        HashMap<Player, String> deathsList = PlayerDeathEvent.getDeathsList();
        String commandName = command.getName().toLowerCase();
        Player p = (Player) sender;

        if(commandName.equals("tp")) {
            if(args.length == 1) {
                Player teleportTo = Bukkit.getPlayer(args[0]);
                if(teleportTo != null) {
                    BukkitEvents.sendCommand("teleport " + p.getName() + " " + teleportTo.getName());
                    WebSocketV2.getWs().send("{\"type\":\"tp\",\"player\":\"pName\", \"target\":\"targetName\"}".replace("pName", p.getName()).replace("targetName", teleportTo.getName()));;
                    p.sendMessage(BukkitEvents.format("&9You were teleported to " + teleportTo.getName()));
                    teleportTo.sendMessage(BukkitEvents.format("&9" + p.getName() + " teleported to you."));
                } else p.sendMessage(BukkitEvents.format("&cThat player is offline"));
            } else p.sendMessage(BukkitEvents.format("&cYou must specify a user."));
        }

        else if(commandName.equals("death")) {
            if(deathsList.containsKey(p)) p.sendMessage(BukkitEvents.format("&9Your last death was at &7" + deathsList.get(p)));
            else p.sendMessage(BukkitEvents.format("&cYou do not have a stored death"));
        }

        else if(commandName.equals("name")) {
            ItemStack item = p.getInventory().getItemInMainHand();
            ItemMeta meta = item.getItemMeta();

            if(item.getType() != Material.AIR) {
                if(args.length >= 1) {
                    meta.setDisplayName(BukkitEvents.format(String.join(" ", args)));
                    item.setItemMeta(meta);
                    p.sendMessage(BukkitEvents.format("&9Successfully renamed item to: " + String.join(" ", args)));
                } else p.sendMessage(BukkitEvents.format("&cSpecify a name."));
            } else p.sendMessage(BukkitEvents.format("&cYou must be holding an item."));
        }

        else if(commandName.equals("ping")) {
            if(args.length == 1) {
                Location l = p.getLocation();
                String coords = "("  + l.getBlockX() + "," + l.getBlockY() + "," + l.getBlockZ() + ")";
                Bukkit.broadcastMessage(BukkitEvents.format("&7[&9Coords&7] &7" + p.getName() + " marked " + args[0] + ": " + coords));
                WebSocketV2.getWs().send("{\"type\":\"ping\",\"player\":\"pName\", \"coords\":\"coordsList\"}".replace("pName", p.getName()).replace("coordsList", args[0] + ": " + coords));;
            } else p.sendMessage(BukkitEvents.format("&cSpecify a location. EX: /ping stronghold"));
        }

        return true;
    }
}
