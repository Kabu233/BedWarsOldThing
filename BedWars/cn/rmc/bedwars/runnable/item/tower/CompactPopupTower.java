/*     */ package cn.rmc.bedwars.runnable.item.tower;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import cn.rmc.bedwars.BedWars;
/*     */ import cn.rmc.bedwars.game.PlayerData;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class CompactPopupTower implements Listener {
/*  20 */   private static final TowerBlock[][] forward = new TowerBlock[][];
/*     */ 