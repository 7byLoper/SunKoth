package by.loper.SunKoth;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.Map;

public class LocationTaskManager {
    private final SunKoth plugin;
    private final Map<Location, BukkitTask> tasks = new HashMap<>();
    private ProtocolManager protocolManager;
    public LocationTaskManager(SunKoth plugin) {
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.plugin = plugin;
    }
    public void startTaskAtLocation(Location location, Player p) {
        if (tasks.containsKey(location)) {
            return;
        }
        BukkitTask bukkitTask = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    PacketContainer greenBlockPacket = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
                    greenBlockPacket.getBlockPositionModifier().write(0, new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ()));
                    greenBlockPacket.getBlockData().write(0, WrappedBlockData.createData(Material.LIME_CONCRETE));
                    protocolManager.sendServerPacket(p, greenBlockPacket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.runTaskTimer(plugin, 2, 2);
        tasks.put(location, bukkitTask);
    }
    public void stopTaskAtLocation(Location location) {
        BukkitTask task = tasks.remove(location);
        if (task != null) {
            task.cancel();
        }
    }
    public void cancelAllTasks() {
        for (BukkitTask task : tasks.values()) {
            task.cancel();
        }
        tasks.clear();
    }
}
