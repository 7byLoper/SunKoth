package by.loper.SunKoth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class Manager {
    private static Boolean KothEnable = false;
    private static List<String> commandlist;
    private static Boolean SelectEnable = false;
    private static Boolean WandEnable = false;
    private static Player playerWand;
    private static ItemStack wand = new ItemStack(Material.STICK);
    private static HashMap<Location, Player> BlockProgress = new HashMap<>();
    private static HashMap<Player, Integer> PlayerProgress = new HashMap<>();
    public static Boolean getKothEnable() {
        return KothEnable;
    }
    public static void setKothEnable(Boolean kothEnable) {
        KothEnable = kothEnable;
    }
    private static Location loc1= new Location(Bukkit.getWorld(SunKoth.getInstance().getConfig().getString("Koth.Location.World")), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos1.x"), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos1.y"), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos1.z"));;
    private static Location loc2= new Location(Bukkit.getWorld(SunKoth.getInstance().getConfig().getString("Koth.Location.World")), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos2.x"), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos2.y"), SunKoth.getInstance().getConfig().getInt("Koth.Location.Pos2.z"));;
    public static List<Block> select(){
        List<Block> blocks = new ArrayList<Block>();
        int x1 = loc1.getBlockX();
        int y1 = loc1.getBlockY();
        int z1 = loc1.getBlockZ();
        int x2 = loc2.getBlockX();
        int y2 = loc2.getBlockY();
        int z2 = loc2.getBlockZ();
        int xMin, yMin, zMin;
        int xMax, yMax, zMax;
        int x, y, z;
        if(x1 > x2){
            xMin = x2;
            xMax = x1;
        }else{
            xMin = x1;
            xMax = x2;
        }
        if(y1 > y2){
            yMin = y2;
            yMax = y1;
        }else{
            yMin = y1;
            yMax = y2;
        }
        if(z1 > z2){
            zMin = z2;
            zMax = z1;
        }else{
            zMin = z1;
            zMax = z2;
        }
        for(x = xMin; x <= xMax; x ++){
            for(y = yMin; y <= yMax; y ++){
                for(z = zMin; z <= zMax; z ++){
                    Block b = new Location(loc1.getWorld(), x, y, z).getBlock();
                    blocks.add(b);
                }
            }
        }
        return blocks;
    }
    public static HashMap<Location, Player> getBlockProgress() {
        return BlockProgress;
    }
    public static void setBlockProgress(Location loc, Player p) {
        BlockProgress.put(loc,p);
    }
    public static void ClearBlockProgress() {
        BlockProgress.clear();
    }
    public static HashMap<Player, Integer> getPlayerProgress() {
        return PlayerProgress;
    }
    public static void setPlayerProgress(Player p, int progress) {
        PlayerProgress.put(p,progress);
    }
    public static void ClearPlayerProgress() {
        PlayerProgress.clear();
    }
    public static ItemStack getWand() {
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName("&cПалочка выделения области захвата".replace("&", "§"));
        List<String> lore = new ArrayList<>();
        lore.add("§f");
        lore.add("§fДанной §aпалочкой§f ты сможешь");
        lore.add("§eВыделеть§f облость для §cзахвата");
        lore.add("§fТочки. §c§nЛКН§7 -§f 1 точка, §c§nПКМ§7 -§f 2 точка");
        lore.add("§f");
        meta.setLore(lore);
        wand.setItemMeta(meta);
        return wand;
    }
    public static void WinEvent(Player player){
        commandlist = SunKoth.getInstance().getConfig().getStringList("Koth.RewardsCmd");
        Server server = Bukkit.getServer();
        Player p = player;
        List<Block> blocks = Manager.select();
        String Msg = SunKoth.getInstance().getConfig().getString("Koth.PlayerMsgWin");
        Msg = Msg.replace("&", "§");
        p.sendMessage(Msg);
        String BcMsg = SunKoth.getInstance().getConfig().getString("Koth.AllMsgWin");
        BcMsg = BcMsg.replace("%n%", "\n");
        BcMsg = BcMsg.replace("&", "§");
        BcMsg = BcMsg.replace("%player%", p.getName());
        server.broadcastMessage(BcMsg);
        SunKoth.getInstance().getTask().cancelAllTasks();
        setKothEnable(false);
        ClearBlockProgress();
        ClearPlayerProgress();
        for(int i = 0; i<commandlist.size(); i++ ){
            String cmd = commandlist.get(i);
            server.dispatchCommand(server.getConsoleSender(), cmd.replace("%player%", p.getName()));
        }
        for(Block block : blocks){
            block.setType(Material.WHITE_CONCRETE);
        }
    }
    public static String Message(String patch) {
        String message = SunKoth.getInstance().getConfig().getString(patch);
        message = message.replace("%n%", "\n");
        message = message.replace("&", "§");
        String[] str = message.split("\n");
        return message;
    }
    public static Location getLoc2() {
        return loc2;
    }
    public static void setLoc2(Location loc2) {
        Manager.loc2 = loc2;
    }
    public static Location getLoc1() {
        return loc1;
    }

    public static void setLoc1(Location loc1) {
        Manager.loc1 = loc1;
    }

    public static Boolean getSelectEnable() {
        return SelectEnable;
    }

    public static void setSelectEnable(Boolean selectEnable) {
        SelectEnable = selectEnable;
    }

    public static Boolean getWandEnable() {
        return WandEnable;
    }

    public static void setWandEnable(Boolean wandEnable) {
        WandEnable = wandEnable;
    }

    public static Player getPlayerWand() {
        return playerWand;
    }

    public static void setPlayerWand(Player playerWand) {
        Manager.playerWand = playerWand;
    }
}
