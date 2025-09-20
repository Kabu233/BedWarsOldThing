/*    */ package cn.rmc.bedwarslobby.object;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import cn.rmc.bedwarslobby.enums.PlayerState;
/*    */ import cn.rmc.bedwarslobby.loot.other.LootInfo;
/*    */ import cn.rmc.bedwarslobby.util.LevelUtils;
/*    */ import cn.rmc.bedwarslobby.util.database.DataBase;
/*    */ import cn.rmc.bedwarslobby.util.database.KeyValue;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ public class PlayerData {
/*    */   UUID uuid;
/*    */   PlayerState state;
/*    */   
/* 18 */   public void setUuid(UUID uuid) { this.uuid = uuid; } Board board; Integer currentLevel; Integer currentExperience; public void setState(PlayerState state) { this.state = state; } public void setBoard(Board board) { this.board = board; } public void setCurrentLevel(Integer currentLevel) { this.currentLevel = currentLevel; } public void setCurrentExperience(Integer currentExperience) { this.currentExperience = currentExperience; } public void setBoughtList(List<LootInfo> boughtList) { this.boughtList = boughtList; } public void setLootChest(Integer lootChest) { this.lootChest = lootChest; } public void setBoughtItem(List<String> boughtItem) { this.boughtItem = boughtItem; } public void setLootItem(List<String> lootItem) { this.lootItem = lootItem; } public void setDataBase(DataBase dataBase) { this.dataBase = dataBase; } public void setTable(String table) { this.table = table; } public void setTasks(List<BukkitTask> tasks) { this.tasks = tasks; } public boolean equals(Object o) { if (o == this) return true;  if (!(o instanceof PlayerData)) return false;  PlayerData other = (PlayerData)o; if (!other.canEqual(this)) return false;  Object this$uuid = getUuid(), other$uuid = other.getUuid(); if ((this$uuid == null) ? (other$uuid != null) : !this$uuid.equals(other$uuid)) return false;  Object this$state = getState(), other$state = other.getState(); if ((this$state == null) ? (other$state != null) : !this$state.equals(other$state)) return false;  Object this$board = getBoard(), other$board = other.getBoard(); if ((this$board == null) ? (other$board != null) : !this$board.equals(other$board)) return false;  Object this$currentLevel = getCurrentLevel(), other$currentLevel = other.getCurrentLevel(); if ((this$currentLevel == null) ? (other$currentLevel != null) : !this$currentLevel.equals(other$currentLevel)) return false;  Object this$currentExperience = getCurrentExperience(), other$currentExperience = other.getCurrentExperience(); if ((this$currentExperience == null) ? (other$currentExperience != null) : !this$currentExperience.equals(other$currentExperience)) return false;  Object<LootInfo> this$boughtList = (Object<LootInfo>)getBoughtList(), other$boughtList = (Object<LootInfo>)other.getBoughtList(); if ((this$boughtList == null) ? (other$boughtList != null) : !this$boughtList.equals(other$boughtList)) return false;  Object this$lootChest = getLootChest(), other$lootChest = other.getLootChest(); if ((this$lootChest == null) ? (other$lootChest != null) : !this$lootChest.equals(other$lootChest)) return false;  Object<String> this$boughtItem = (Object<String>)getBoughtItem(), other$boughtItem = (Object<String>)other.getBoughtItem(); if ((this$boughtItem == null) ? (other$boughtItem != null) : !this$boughtItem.equals(other$boughtItem)) return false;  Object<String> this$lootItem = (Object<String>)getLootItem(), other$lootItem = (Object<String>)other.getLootItem(); if ((this$lootItem == null) ? (other$lootItem != null) : !this$lootItem.equals(other$lootItem)) return false;  Object this$dataBase = getDataBase(), other$dataBase = other.getDataBase(); if ((this$dataBase == null) ? (other$dataBase != null) : !this$dataBase.equals(other$dataBase)) return false;  Object this$table = getTable(), other$table = other.getTable(); if ((this$table == null) ? (other$table != null) : !this$table.equals(other$table)) return false;  Object<BukkitTask> this$tasks = (Object<BukkitTask>)getTasks(), other$tasks = (Object<BukkitTask>)other.getTasks(); return !((this$tasks == null) ? (other$tasks != null) : !this$tasks.equals(other$tasks)); } protected boolean canEqual(Object other) { return other instanceof PlayerData; } public int hashCode() { int PRIME = 59; result = 1; Object $uuid = getUuid(); result = result * 59 + (($uuid == null) ? 43 : $uuid.hashCode()); Object $state = getState(); result = result * 59 + (($state == null) ? 43 : $state.hashCode()); Object $board = getBoard(); result = result * 59 + (($board == null) ? 43 : $board.hashCode()); Object $currentLevel = getCurrentLevel(); result = result * 59 + (($currentLevel == null) ? 43 : $currentLevel.hashCode()); Object $currentExperience = getCurrentExperience(); result = result * 59 + (($currentExperience == null) ? 43 : $currentExperience.hashCode()); Object<LootInfo> $boughtList = (Object<LootInfo>)getBoughtList(); result = result * 59 + (($boughtList == null) ? 43 : $boughtList.hashCode()); Object $lootChest = getLootChest(); result = result * 59 + (($lootChest == null) ? 43 : $lootChest.hashCode()); Object<String> $boughtItem = (Object<String>)getBoughtItem(); result = result * 59 + (($boughtItem == null) ? 43 : $boughtItem.hashCode()); Object<String> $lootItem = (Object<String>)getLootItem(); result = result * 59 + (($lootItem == null) ? 43 : $lootItem.hashCode()); Object $dataBase = getDataBase(); result = result * 59 + (($dataBase == null) ? 43 : $dataBase.hashCode()); Object $table = getTable(); result = result * 59 + (($table == null) ? 43 : $table.hashCode()); Object<BukkitTask> $tasks = (Object<BukkitTask>)getTasks(); return result * 59 + (($tasks == null) ? 43 : $tasks.hashCode()); } public String toString() { return "PlayerData(uuid=" + getUuid() + ", state=" + getState() + ", board=" + getBoard() + ", currentLevel=" + getCurrentLevel() + ", currentExperience=" + getCurrentExperience() + ", boughtList=" + getBoughtList() + ", lootChest=" + getLootChest() + ", boughtItem=" + getBoughtItem() + ", lootItem=" + getLootItem() + ", dataBase=" + getDataBase() + ", table=" + getTable() + ", tasks=" + getTasks() + ")"; }
/*    */ 
/*    */   
/* 21 */   public UUID getUuid() { return this.uuid; }
/* 22 */   public PlayerState getState() { return this.state; }
/* 23 */   public Board getBoard() { return this.board; }
/* 24 */   public Integer getCurrentLevel() { return this.currentLevel; } public Integer getCurrentExperience() {
/* 25 */     return this.currentExperience;
/* 26 */   } List<LootInfo> boughtList = new ArrayList<>(); public List<LootInfo> getBoughtList() { return this.boughtList; }
/* 27 */    Integer lootChest = Integer.valueOf(0); public Integer getLootChest() { return this.lootChest; }
/* 28 */    List<String> boughtItem = new ArrayList<>(); public List<String> getBoughtItem() { return this.boughtItem; }
/* 29 */    List<String> lootItem = new ArrayList<>(); public List<String> getLootItem() { return this.lootItem; }
/* 30 */    DataBase dataBase = BedWarsLobby.getDataBase(); public DataBase getDataBase() { return this.dataBase; }
/* 31 */    String table = "bedwars_lootchest"; public String getTable() { return this.table; }
/* 32 */    List<BukkitTask> tasks = new ArrayList<>(); public List<BukkitTask> getTasks() { return this.tasks; }
/*    */ 
/*    */   
/*    */   public PlayerData(UUID uuid) {
/* 36 */     this.uuid = uuid;
/* 37 */     this.currentExperience = DataUtils.getInt(uuid.toString(), Data.Field.EXPERIENCE);
/* 38 */     this.currentLevel = LevelUtils.getLevel(this.currentExperience.intValue());
/*    */     
/* 40 */     this.state = PlayerState.BREAKING;
/* 41 */     load();
/*    */   }
/*    */   
/*    */   public void addTask(BukkitTask task) {
/* 45 */     if (this.tasks.contains(task)) {
/*    */       return;
/*    */     }
/* 48 */     this.tasks.add(task);
/*    */   }
/*    */   
/*    */   public void removeTask(BukkitTask task) {
/* 52 */     if (this.tasks.contains(task)) {
/* 53 */       task.cancel();
/* 54 */       this.tasks.remove(task);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeAllTask() {
/* 59 */     this.tasks.forEach(BukkitTask::cancel);
/* 60 */     this.tasks.clear();
/* 61 */     this.tasks = null;
/*    */   }
/*    */   
/*    */   public boolean hasBought(LootInfo loot) {
/* 65 */     for (LootInfo str : this.boughtList) {
/* 66 */       if (str.getName().equalsIgnoreCase(loot.getName())) {
/* 67 */         return true;
/*    */       }
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */   
/*    */   private void load() {
/* 74 */     if (!this.dataBase.dbExist(this.table, new KeyValue("UUID", getPlayer().getUniqueId().toString()))) {
/* 75 */       this.dataBase.dbInsert(this.table, (new KeyValue("UUID", getPlayer().getUniqueId().toString())).add(new KeyValue("Name", getPlayer().getName())).add(new KeyValue("Amount", this.lootChest)));
/*    */     } else {
/* 77 */       String re = this.dataBase.dbSelectFirst(this.table, "Amount", new KeyValue("UUID", getPlayer().getUniqueId().toString()));
/* 78 */       this.lootChest = Integer.valueOf((re == null) ? 0 : Integer.parseInt(re));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void save() {
/* 83 */     this.dataBase.dbUpdate(this.table, new KeyValue("Amount", this.lootChest), new KeyValue("UUID", getPlayer().getUniqueId().toString()));
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 87 */     return Bukkit.getPlayer(this.uuid);
/*    */   }
/*    */ }