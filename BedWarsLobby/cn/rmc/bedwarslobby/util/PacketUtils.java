 package cn.rmc.bedwarslobby.util;

 import net.minecraft.server.v1_8_R3.Packet;
 import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
 import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
 import org.bukkit.Location;
 import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
 import org.bukkit.entity.Player;
 import org.bukkit.util.Vector;

 public class PacketUtils

   public static void teleportPacket(int id, Location loc, Player player) {
     Location location = loc.clone();
     PacketPlayOutEntityTeleport teleport = new PacketPlayOutEntityTeleport(id, (int)(location.getX() * 32.0D), (int)(location.getY() * 32.0D), (int)(location.getZ() * 32.0D), (byte)(int)(location.getYaw() * 256.0F / 360.0F), (byte)(int)(location.getPitch() * 256.0F / 360.0F), false);
     sendPacket(player, (Packet<?>)teleport);
   }

   public static void sendPacket(Player p, Packet<?> packet) {
     (((CraftPlayer)p).getHandle()).playerConnection.sendPacket(packet);
   }

   public static void destroyPacket(Player p, int... ids) {
     sendPacket(p, (Packet<?>)new PacketPlayOutEntityDestroy(ids));
   }
 }