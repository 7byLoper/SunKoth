package by.loper.SunKoth;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

import java.io.File;
import java.util.logging.Logger;
public final class SunKoth extends JavaPlugin {
    Logger log = Logger.getLogger("Minecraft");
    private static SunKoth instance;
    private LocationTaskManager taskManager;
    @Override
    public void onEnable() {
        this.log.warning("");
        this.log.warning(ChatColor.GREEN + "  |SunKoth Enabled   ");
        this.log.warning("");
        this.log.warning(ChatColor.GREEN + "  |Version : 1.0    ");
        this.log.warning("");

        taskManager = new LocationTaskManager(this);
        saveDefaultConfig();
        instance = this;
        Bukkit.getPluginManager().registerEvents(new Events(this), this);
        getCommand("koth").setExecutor(new KothCommand(this));
    }
    @Override
    public void onDisable() {
        this.log.warning("");
        this.log.warning(ChatColor.GREEN + "  |SunKoth Disabled   ");
        this.log.warning("");
        this.log.warning(ChatColor.GREEN + "  |Version : 1.0    ");
        this.log.warning("");
        getTask().cancelAllTasks();
    }
    public static SunKoth getInstance() {
        return instance;
    }
    public LocationTaskManager getTask() {
        return taskManager;
    }
}
