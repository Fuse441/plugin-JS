package org.example.project.jumpscaremc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import static org.bukkit.Bukkit.getServer;

public class JumpScareCommand implements CommandExecutor {
    private final JavaPlugin plugin;

    public JumpScareCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // ดึงชื่อคำสั่งจาก config.yml
            String commandName = cmd.getName().toLowerCase();
            if (plugin.getConfig().contains("commands." + commandName)) {
                if (args.length >= 1) {
                    String targetName = args[0];
                    Player targetPlayer = getServer().getPlayer(targetName);

                    if (targetPlayer != null && targetPlayer.isOnline()) {
                        // ดึงค่าจาก config สำหรับคำสั่งที่กำหนด
                        String title = plugin.getConfig().getString("commands." + commandName + ".title");
                        String subtitle = plugin.getConfig().getString("commands." + commandName + ".subtitle");
                        String sound = plugin.getConfig().getString("commands." + commandName + ".sound");
                        float volume = (float) plugin.getConfig().getDouble("commands." + commandName + ".volume");
                        float pitch = (float) plugin.getConfig().getDouble("commands." + commandName + ".pitch");

                        // แสดง title และ subtitle
                        targetPlayer.sendTitle(title, subtitle);

                        // เล่นเสียง
                        try {
                            targetPlayer.playSound(targetPlayer.getLocation(), sound, volume, pitch);
                        } catch (IllegalArgumentException e) {
                            player.sendMessage("Invalid sound specified.");
                            plugin.getLogger().warning("Invalid sound specified: " + e.getMessage());
                        }
                    } else {
                        player.sendMessage("Player " + targetName + " is not online");
                    }
                } else {
                    player.sendMessage("Please specify a player name.");
                }
            } else {
                player.sendMessage("Command not found in config.");
            }
            return true;
        }
        return false;
    }
}
