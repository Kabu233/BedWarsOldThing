package cn.rmc.bedwars.utils.nametag;

import java.util.List;
import org.bukkit.entity.Player;

public interface NametagAdapter {
  List<BufferedNametag> getPlate(Player paramPlayer);
  
  boolean showHealthBelowName(Player paramPlayer);
}