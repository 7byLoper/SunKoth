package by.loper.SunKoth;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
public class Events implements Listener {
    private final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private final long cooldownTimeMil = SunKoth.getInstance().getConfig().getInt("Koth.Cooldown"); // Задержка в миллисекундах (5 секунд)
    private final long cooldownTime = cooldownTimeMil * 1000;
    private SunKoth sunkoth;
    public Events(SunKoth sunkoth) {
        this.sunkoth = sunkoth;
    }
    @EventHandler
    public void onBreakBlock(BlockBreakEvent event) {
        List<Block> blocks = Manager.select();
        Block block = event.getBlock();
        Player p = event.getPlayer();
        if (Manager.getKothEnable()) {
            for (Block block1 : blocks) {
                if (block.getLocation().equals(block1.getLocation())) {
                    UUID playerId = p.getUniqueId();
                    long currentTime = System.currentTimeMillis();
                    if (cooldowns.containsKey(playerId)) {
                        long lastBreakTime = cooldowns.get(playerId);
                        long timeLeft = (lastBreakTime + cooldownTime) - currentTime;
                        String time = String.valueOf(timeLeft/1000);
                        String message = Manager.Message("Koth.ColldownTimeMsg").replace("%time%", time);
                        if (timeLeft > 0) {
                            p.sendMessage(message);
                            event.setCancelled(true);
                            return;
                        }
                    }
                    cooldowns.put(playerId, currentTime);
                    if(Manager.getBlockProgress().containsKey(block1.getLocation())){
                        if(Manager.getBlockProgress().get(block1.getLocation()) == p){
                            p.sendMessage(Manager.Message("Koth.BlockPlace"));
                            event.setCancelled(true);
                            return;
                        }else if (Manager.getBlockProgress().get(block1.getLocation()) != p){
                            Player p2 = Manager.getBlockProgress().get(block1.getLocation());
                            Manager.getPlayerProgress().put(p2, Manager.getPlayerProgress().get(p2) - 1);
                            Manager.getBlockProgress().remove(block1.getLocation(), p);
                            sunkoth.getTask().stopTaskAtLocation(block1.getLocation());
                            Manager.setBlockProgress(block1.getLocation(), p);
                            if(Manager.getPlayerProgress().get(p) == null) {
                                Manager.getPlayerProgress().put(p, 1);
                                sunkoth.getTask().startTaskAtLocation(block1.getLocation(), p);
                                if(Manager.getPlayerProgress().get(p) >= blocks.size()){
                                    Manager.WinEvent(p);
                                }
                            }else{
                                Manager.getPlayerProgress().put(p, Manager.getPlayerProgress().get(p) + 1);
                                sunkoth.getTask().startTaskAtLocation(block1.getLocation(), p);
                                if(Manager.getPlayerProgress().get(p) >= blocks.size()){
                                    Manager.WinEvent(p);
                                }
                            }
                        }
                    }else if(!Manager.getBlockProgress().containsKey(block1.getLocation())){
                        block1.setType(Material.RED_CONCRETE);
                        Manager.setBlockProgress(block1.getLocation(), p);
                        if(Manager.getPlayerProgress().containsKey(p)){
                            Manager.getPlayerProgress().put(p,Manager.getPlayerProgress().get(p) + 1);
                            sunkoth.getTask().startTaskAtLocation(block1.getLocation(), p);
                            if(Manager.getPlayerProgress().get(p) >= blocks.size()){
                                Manager.WinEvent(p);
                            }
                    }else{
                            Manager.getPlayerProgress().put(p, 1);
                            sunkoth.getTask().startTaskAtLocation(block1.getLocation(), p);
                            if(Manager.getPlayerProgress().get(p) >= blocks.size()){
                                Manager.WinEvent(p);
                            }
                        }
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (Manager.getSelectEnable()){
            if(event.getPlayer() == Manager.getPlayerWand()) {
                Block block = event.getClickedBlock();
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK && block != null) {
                    player.sendMessage("§fВы§a выделили §e2 точку§f на координатах");
                    player.sendMessage("§fX: §c" + block.getLocation().getBlockX() + "§f Y: §c" + block.getLocation().getBlockY() + "§f Z: §c" + block.getLocation().getBlockZ());
                    Manager.setLoc2(block.getLocation());
                    event.setCancelled(true);
                }else if (event.getAction() == Action.LEFT_CLICK_BLOCK && block != null) {
                    player.sendMessage("§fВы§a выделили §e1 точку§f на координатах");
                    player.sendMessage("§fX: §c" + block.getLocation().getBlockX() + "§f Y: §c" + block.getLocation().getBlockY() + "§f Z: §c" + block.getLocation().getBlockZ());
                    Manager.setLoc1(block.getLocation());
                    event.setCancelled(true);
                }
            }
        }
    }
}
