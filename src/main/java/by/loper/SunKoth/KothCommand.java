package by.loper.SunKoth;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class KothCommand implements CommandExecutor, TabCompleter {
    private SunKoth sunkoth;

    public KothCommand(SunKoth sunkoth) {
        this.sunkoth = sunkoth;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        List<Block> blocks = Manager.select();
        if (commandSender.hasPermission("SunKoth.permission.*")) {
            if (args.length != 0) {
                //Команда /koth start

                if (args[0].equalsIgnoreCase("start")) {
                    if (!Manager.getKothEnable()) {
                        Manager.setKothEnable(true);
                        Bukkit.broadcastMessage(Manager.Message("Koth.StartMsg"));
                        commandSender.sendMessage("§7[SunKoth] §fВы §aуспешно §fначали эвент");
                        for (Block block : blocks) {
                            block.setType(Material.WHITE_CONCRETE);
                        }
                    } else {
                        commandSender.sendMessage("§7[SunKoth]§c Эвент уже запущен");
                    }
                }

                //Команда /koth stop

                else if (args[0].equalsIgnoreCase("stop")) {
                    if (Manager.getKothEnable()) {
                        //stop event
                        Manager.setKothEnable(false);
                        Manager.ClearBlockProgress();
                        Manager.ClearPlayerProgress();
                        sunkoth.getTask().cancelAllTasks();
                        Bukkit.broadcastMessage(Manager.Message("Koth.EndMsg"));
                        //send message
                        commandSender.sendMessage("§7[SunKoth] §fВы §cуспешно §fзакончили эвент");
                        //repair zone
                        for (Block block : blocks) {
                            block.setType(Material.WHITE_CONCRETE);
                        }
                    } else {
                        commandSender.sendMessage("§7[SunKoth] §cЭвент не запущен");
                    }
                }

                //Команда /koth help

                else if (args[0].equalsIgnoreCase("help")) {

                    commandSender.sendMessage("§aПомощь по плагину:");
                    commandSender.sendMessage("§e/koth help §7- §fПомощь по плагину");
                    commandSender.sendMessage("§e/koth reload §7- §fПерезагрузка конфигурации плагина ");
                    commandSender.sendMessage("§e/koth wand select/save §7- §fВыделение облости");
                    commandSender.sendMessage("§e/koth start §7- §fЗапустить захват точки");
                    commandSender.sendMessage("§e/koth stop §7- §fОстановить захват точки");

                }

                //Команда /koth wand

                else if (args[0].equalsIgnoreCase("wand")) {
                    if (commandSender instanceof Player) {
                        if (args[1] == null) {
                            commandSender.sendMessage("§aПомощь по команде:");
                            commandSender.sendMessage("§e/koth wand select §7- §fНачать выделение облости");
                            commandSender.sendMessage("§e/koth wand save §7- §fЗакончить выделение облости");
                        } else {
                            Player p = (Player) commandSender;

                            //Команда /koth wand select

                            if (args[1].equalsIgnoreCase("select")) {
                                if (!Manager.getWandEnable()) {
                                    Manager.setPlayerWand(p);
                                    Manager.setSelectEnable(true);
                                    Manager.setWandEnable(true);
                                    p.sendMessage("§7[SunKoth] §fВы §aначали§f выделение §eоблости");
                                    p.sendMessage("§c§nЛКН§7 -§f 1 точка, §c§nПКМ§7 -§f 2 точка");
                                } else {
                                    p.sendMessage("§7[SunKoth] §cВы уже выделение пропишите /wand save для завершения выделения");
                                }
                            }

                            //Команда /koth wand save

                            else if (args[1].equalsIgnoreCase("save")) {
                                if (Manager.getWandEnable()) {
                                    p.sendMessage("§7[SunKoth] §fВы §aзакончили§f выделение §eоблости");
                                    Manager.setSelectEnable(false);
                                    Manager.setWandEnable(false);
                                    SunKoth.getInstance().getConfig().set("Koth.Location.World", Manager.getLoc1().getBlock().getWorld().getName());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos1.x", Manager.getLoc1().getBlockX());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos1.y", Manager.getLoc1().getBlockY());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos1.z", Manager.getLoc1().getBlockZ());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos2.x", Manager.getLoc2().getBlockX());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos2.y", Manager.getLoc2().getBlockY());
                                    SunKoth.getInstance().getConfig().set("Koth.Location.Pos2.z", Manager.getLoc2().getBlockZ());
                                } else {
                                    p.sendMessage("§7[SunKoth] §cВы не начинали выделение пропишите /wand select");
                                }

                            } else {
                                commandSender.sendMessage("§aПомощь по команде:");
                                commandSender.sendMessage("§e/koth wand select §7- §fНачать выделение облости");
                                commandSender.sendMessage("§e/koth wand save §7- §fЗакончить выделение облости");
                            }
                        }
                    } else {
                        commandSender.sendMessage("§cДанная комманда доступна только игрокам!");
                        return true;
                    }
                }

                //Конманда /koth reload

                else if (args[0].equalsIgnoreCase("reload")) {

                    commandSender.sendMessage(Manager.Message("Koth.Reload"));
                    SunKoth.getInstance().reloadConfig();
                } else {
                    commandSender.sendMessage("§aПомощь по плагину:");
                    commandSender.sendMessage("§e/koth help §7- §fПомощь по плагину");
                    commandSender.sendMessage("§e/koth reload §7- §fПерезагрузка конфигурации плагина ");
                    commandSender.sendMessage("§e/koth wand select/save §7- §fВыделение облости");
                    commandSender.sendMessage("§e/koth start §7- §fЗапустить захват точки");
                    commandSender.sendMessage("§e/koth stop §7- §fОстановить захват точки");
                }
            } else {
                commandSender.sendMessage("§aПомощь по плагину:");
                commandSender.sendMessage("§e/koth help §7- §fПомощь по плагину");
                commandSender.sendMessage("§e/koth reload §7- §fПерезагрузка конфигурации плагина ");
                commandSender.sendMessage("§e/koth wand select/save §7- §fВыделение облости");
                commandSender.sendMessage("§e/koth start §7- §fЗапустить захват точки");
                commandSender.sendMessage("§e/koth stop §7- §fОстановить захват точки");
            }
            return true;
        }else{
            commandSender.sendMessage(Manager.Message("Koth.NoPermission"));
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        List<String> TabComplete = new ArrayList<>();
        if(commandSender instanceof Player){
            if (strings.length == 2) {
                if (strings[0].equalsIgnoreCase("wand")) {
                    TabComplete.add("select");
                    TabComplete.add("save");
                    return TabComplete;
                }

            } else if(strings.length == 1) {
                TabComplete.add("start");
                TabComplete.add("stop");
                TabComplete.add("help");
                TabComplete.add("wand");
                TabComplete.add("reload");
                return TabComplete;
            }
        }
        return TabComplete;
    }
}
