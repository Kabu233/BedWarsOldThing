/*    */ package cn.rmc.bedwarslobby.npc;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.npc.list.PlayFourNPC;
/*    */ import cn.rmc.bedwarslobby.npc.list.ProfileNPC;
/*    */ import cn.rmc.bedwarslobby.npc.list.ShopKeeperNPC;
/*    */ import cn.rmc.bedwarslobby.util.SkinUtils;
/*    */ import cn.rmc.bedwarslobby.util.holographic.Holographic;
/*    */ import net.jitse.npclib.api.NPC;
/*    */ import net.jitse.npclib.api.skin.Skin;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class NPCStorage {
/*    */   HashMap<Player, List<NPC>> npcs;
/*    */   
/*    */   public HashMap<Player, List<NPC>> getNpcs() {
/* 18 */     return this.npcs;
/*    */   } HashMap<NPC, AbstractNPC> abstractNpcMap; public static Map<Holographic, AbstractNPC> holographics; public HashMap<NPC, AbstractNPC> getAbstractNpcMap() {
/* 20 */     return this.abstractNpcMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public NPCStorage() {
/* 26 */     this.npcs = new HashMap<>();
/* 27 */     this.abstractNpcMap = new HashMap<>();
/* 28 */     holographics = new HashMap<>();
/*    */   }
/*    */   
/*    */   public void showPlayer(Player player) {
/* 32 */     List<NPC> list = new ArrayList<>();
/* 33 */     for (AbstractNPC abstractNPC : Arrays.<AbstractNPC>asList(new AbstractNPC[] { (AbstractNPC)new PlayFourNPC(player), (AbstractNPC)new ProfileNPC(player), (AbstractNPC)new ShopKeeperNPC(player) })) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 38 */       NPC npc = abstractNPC.getNpc();
/* 39 */       npc.setLocation(abstractNPC.getSpawnLocation());
/* 40 */       if (abstractNPC.useSelfSkin()) {
/* 41 */         if (getNpcSkin(player) != null)
/* 42 */           npc.setSkin(getNpcSkin(player)); 
/* 43 */       } else if (abstractNPC.getSkin() != null) {
/* 44 */         npc.setSkin(abstractNPC.getSkin());
/*    */       } 
/* 46 */       if (abstractNPC.getHeldItem() != null)
/* 47 */         npc.setItem(NPCSlot.MAINHAND, abstractNPC.getHeldItem()); 
/* 48 */       if (abstractNPC.isUseHolographic()) {
/* 49 */         npc.setText(abstractNPC.getHolographicLine());
/* 50 */       } else if (abstractNPC.getHolographic() != null) {
/* 51 */         abstractNPC.getHolographic().display(player);
/* 52 */       }  npc.create();
/* 53 */       npc.show(player);
/*    */       
/* 55 */       if (!list.contains(npc))
/* 56 */         list.add(npc); 
/* 57 */       if (!this.abstractNpcMap.containsKey(npc))
/* 58 */         this.abstractNpcMap.put(npc, abstractNPC); 
/*    */     } 
/* 60 */     this.npcs.put(player, list);
/*    */   }
/*    */   
/*    */   public void onClick(NPC npc, Player player) {
/* 64 */     ((AbstractNPC)this.abstractNpcMap.get(npc)).onClick(player);
/*    */   }
/*    */   
/*    */   public void onClick(int id, Player player) {
/* 68 */     holographics.forEach((holographic, abstractNPC) -> {
/*    */           if (holographic.isSame(id, player))
/*    */             abstractNPC.onClick(player); 
/*    */         });
/*    */   }
/*    */   
/*    */   public Skin getNpcSkin(Player player) {
/* 75 */     String[] textures = SkinUtils.getProfileBySkinRestorer(player.getName());
/* 76 */     if (textures == null)
/* 77 */       return null; 
/* 78 */     return new Skin(textures[0], textures[1]);
/*    */   }
/*    */ }