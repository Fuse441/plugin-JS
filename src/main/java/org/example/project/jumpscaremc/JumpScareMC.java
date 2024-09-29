package org.example.project.jumpscaremc;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Set;

public class JumpScareMC extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("plugin JumpScareMC work!");

        // โหลดไฟล์ config ถ้ายังไม่มี
        saveDefaultConfig();

        // ดึงคำสั่งทั้งหมดจาก config.yml
        Set<String> commands = getConfig().getConfigurationSection("commands").getKeys(false);

        // ลงทะเบียนคำสั่งที่สร้างขึ้นโดยผู้ใช้
        for (String command : commands) {
            getCommand(command).setExecutor(new JumpScareCommand(this));
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("plugin JumpScareMC disabled.");
    }
}
