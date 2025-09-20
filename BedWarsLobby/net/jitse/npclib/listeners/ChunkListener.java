/*     */ package net.jitse.npclib.listeners;
/*     */ 
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import net.jitse.npclib.NPCLib;
/*     */ import net.jitse.npclib.internal.NPCBase;
/*     */ import net.jitse.npclib.internal.NPCManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.world.ChunkLoadEvent;
/*     */ import org.bukkit.event.world.ChunkUnloadEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkListener
/*     */   implements Listener
/*     */ {
/*     */   private final NPCLib instance;
/*     */   
/*     */   public ChunkListener(NPCLib instance) {
/*  30 */     this.instance = instance;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChunkUnload(ChunkUnloadEvent event) {
/*  35 */     Chunk chunk = event.getChunk();
/*     */     
/*  37 */     for (NPCBase npc : NPCManager.getAllNPCs()) {
/*  38 */       if (npc.getLocation() == null || !isSameChunk(npc.getLocation(), chunk)) {
/*     */         continue;
/*     */       }
/*     */       
/*  42 */       for (UUID uuid : npc.getShown()) {
/*     */ 
/*     */         
/*  45 */         if (npc.getAutoHidden().contains(uuid)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/*  50 */         Player player = Bukkit.getPlayer(uuid);
/*  51 */         if (player != null) {
/*  52 */           npc.hide(player, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onChunkLoad(ChunkLoadEvent event) {
/*  60 */     Chunk chunk = event.getChunk();
/*     */     
/*  62 */     for (NPCBase npc : NPCManager.getAllNPCs()) {
/*  63 */       if (npc.getLocation() == null || !isSameChunk(npc.getLocation(), chunk)) {
/*     */         continue;
/*     */       }
/*     */       
/*  67 */       for (UUID uuid : npc.getShown()) {
/*     */         
/*  69 */         if (!npc.getAutoHidden().contains(uuid)) {
/*     */           continue;
/*     */         }
/*     */         
/*  73 */         Player player = Bukkit.getPlayer(uuid);
/*  74 */         if (player == null) {
/*     */           continue;
/*     */         }
/*  77 */         if (!Objects.equals(npc.getWorld(), player.getWorld())) {
/*     */           continue;
/*     */         }
/*     */         
/*  81 */         double hideDistance = this.instance.getAutoHideDistance();
/*  82 */         double distanceSquared = player.getLocation().distanceSquared(npc.getLocation());
/*  83 */         boolean inRange = (distanceSquared <= hideDistance * hideDistance || distanceSquared <= (Bukkit.getViewDistance() << 4));
/*     */ 
/*     */         
/*  86 */         if (inRange) {
/*  87 */           npc.show(player, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static int getChunkCoordinate(int coordinate) {
/*  94 */     return coordinate >> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isSameChunk(Location loc, Chunk chunk) {
/* 100 */     return (getChunkCoordinate(loc.getBlockX()) == chunk.getX() && 
/* 101 */       getChunkCoordinate(loc.getBlockZ()) == chunk.getZ());
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\net\jitse\npclib\listeners\ChunkListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */