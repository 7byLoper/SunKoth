# SunKoth

This plugin adds point capture to your minecraft server

### Optional Dependencies

- ProtocolLib

## Config (config.yml)

```yaml
#======================================#
#  Плагин версии 1.0 сделан для сервера#
#       SunLand Created by Loper       #
#      Server Ip: mc.SunLand.fun       #
#            Связь со мной:            #
#          Discord: Im_Loper           #
#            VK:@Loper_Ru              #
#======================================#
#Premission: SunKoth.permission.* - разрешение на использование всех комманд
Koth:
  Location:
    World: World
    Pos1:
      x: 348
      y: 79
      z: 1380
    Pos2:
      x: 343
      y: 79
      z: 1375
  EndMsg: "%n%          §x§F§B§A§6§0§8&lЗ§x§F§B§A§1§0§7&lА§x§F§C§9§C§0§6&lХ§x§F§C§9§6§0§6&lВ§x§F§D§9§1§0§5&lА§x§F§D§8§C§0§4&lТ §x§F§D§8§7§0§3&lТ§x§F§E§8§2§0§2&lО§x§F§E§7§C§0§2&lЧ§x§F§F§7§7§0§1&lК§x§F§F§7§2§0§0&lИ %n%%n%&c    Захват точки &fзакончился&f %n%&f    Победитель: &cОтсутствует%n%"
  StartMsg: "%n%          §x§F§B§A§6§0§8&lЗ§x§F§B§A§1§0§7&lА§x§F§C§9§C§0§6&lХ§x§F§C§9§6§0§6&lВ§x§F§D§9§1§0§5&lА§x§F§D§8§C§0§4&lТ §x§F§D§8§7§0§3&lТ§x§F§E§8§2§0§2&lО§x§F§E§7§C§0§2&lЧ§x§F§F§7§7§0§1&lК§x§F§F§7§2§0§0&lИ %n%%n%&cЗахват точки &fначался&f беги на &e/pvp %n%&f&a  Чтобы&f успеть &cзахватить&f точку%n%"
  RewardsCmd:
    - "p give %player% 700"
    - "money give %player 250000"
  PlayerMsgWin: "&x&F&B&A&F&0&8&lС&x&F&A&A&4&0&6&lЕ&x&F&A&9&9&0&5&lР&x&F&9&8&F&0&3&lВ&x&F&9&8&4&0&2&lЕ&x&F&8&7&9&0&0&lР&f &fВы &aзахватили&f точку"
  AllMsgWin: "%n%          §x§F§B§A§6§0§8&lЗ§x§F§B§A§1§0§7&lА§x§F§C§9§C§0§6&lХ§x§F§C§9§6§0§6&lВ§x§F§D§9§1§0§5&lА§x§F§D§8§C§0§4&lТ §x§F§D§8§7§0§3&lТ§x§F§E§8§2§0§2&lО§x§F§E§7§C§0§2&lЧ§x§F§F§7§7§0§1&lК§x§F§F§7§2§0§0&lИ %n%%n%&c    Захват точки &fзакончился&f %n%&f    Победитель: &e%player%%n%"
  Reload: "§x§F§B§A§6§0§8&lЗ§x§F§B§A§1§0§7&lА§x§F§C§9§C§0§6&lХ§x§F§C§9§6§0§6&lВ§x§F§D§9§1§0§5&lА§x§F§D§8§C§0§4&lТ §x§F§D§8§7§0§3&lТ§x§F§E§8§2§0§2&lО§x§F§E§7§C§0§2&lЧ§x§F§F§7§7§0§1&lК§x§F§F§7§2§0§0&lИ §eКонфигурация §fплагина была §aуспешно§f перезагружена"
  BlockPlace: "&x&F&B&A&F&0&8&lС&x&F&A&A&4&0&6&lЕ&x&F&A&9&9&0&5&lР&x&F&9&8&F&0&3&lВ&x&F&9&8&4&0&2&lЕ&x&F&8&7&9&0&0&lР&f &fВы уже &cсломали&f данный блок"
  Cooldown: 10
  ColldownTimeMsg: "&x&F&B&A&F&0&8&lС&x&F&A&A&4&0&6&lЕ&x&F&A&9&9&0&5&lР&x&F&9&8&F&0&3&lВ&x&F&9&8&4&0&2&lЕ&x&F&8&7&9&0&0&lР&f &fПодождите &e%time%&f сек, чтобы &cсломать&f блок "
```
