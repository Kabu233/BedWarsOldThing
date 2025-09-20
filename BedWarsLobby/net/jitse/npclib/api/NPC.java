package net.jitse.npclib.api;

import java.util.List;
import java.util.UUID;
import net.jitse.npclib.api.skin.Skin;
import net.jitse.npclib.api.state.NPCAnimation;
import net.jitse.npclib.api.state.NPCSlot;
import net.jitse.npclib.api.state.NPCState;
import net.jitse.npclib.internal.MinecraftVersion;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NPC {
  NPC removeText(Player paramPlayer);
  
  NPC setText(Player paramPlayer, List<String> paramList);
  
  NPC setText(List<String> paramList);
  
  List<String> getText(Player paramPlayer);
  
  NPC setLocation(Location paramLocation);
  
  NPC setSkin(Skin paramSkin);
  
  Location getLocation();
  
  World getWorld();
  
  NPC create();
  
  boolean isCreated();
  
  String getId();
  
  boolean isShown(Player paramPlayer);
  
  void show(Player paramPlayer);
  
  void hide(Player paramPlayer);
  
  void destroy();
  
  NPC toggleState(NPCState paramNPCState);
  
  void playAnimation(NPCAnimation paramNPCAnimation);
  
  boolean getState(NPCState paramNPCState);
  
  NPC setItem(NPCSlot paramNPCSlot, ItemStack paramItemStack);
  
  List<String> getText();
  
  ItemStack getItem(NPCSlot paramNPCSlot);
  
  void updateSkin(Skin paramSkin);
  
  UUID getUniqueId();
  
  void lookAt(Location paramLocation);
  
  MinecraftVersion getMinecraftVersion();
}


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\api\NPC.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */