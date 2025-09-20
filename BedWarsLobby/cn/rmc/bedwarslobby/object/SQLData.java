/*    */ package cn.rmc.bedwarslobby.object;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.sql.Statement;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import cn.rmc.bedwarslobby.BedWarsLobby;
/*    */ import cn.rmc.bedwarslobby.util.database.DataBaseCore;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SQLData
/*    */   extends BukkitRunnable
/*    */ {
/*    */   List<HashMap<String, Object>> data;
/* 22 */   static DataBaseCore dataBaseCore = BedWarsLobby.getDataBase().getDataBaseCore(); public List<HashMap<String, Object>> getData() {
/* 23 */     return this.data;
/*    */   }
/*    */   String sql;
/*    */   String[] fields;
/*    */   
/*    */   public SQLData(String sql, String... fields) {
/* 29 */     this.sql = sql;
/* 30 */     this.fields = fields;
/* 31 */     run();
/* 32 */     runTaskTimer((Plugin)BedWarsLobby.getInstance(), 0L, 1200L);
/*    */   }
/*    */   
/*    */   public void run() {
/*    */     try {
/* 37 */       DataBaseCore.Group<DataBaseCore.Group<Connection, Statement>, ResultSet> group = dataBaseCore.executeQuery(this.sql);
/* 38 */       ResultSet resultSet = (ResultSet)group.getB();
/* 39 */       List<HashMap<String, Object>> resultt = new ArrayList<>();
/*    */       
/* 41 */       while (resultSet.next()) {
/* 42 */         HashMap<String, Object> result = new HashMap<>();
/*    */         
/* 44 */         for (String field : this.fields) {
/* 45 */           Object object = resultSet.getObject(field);
/* 46 */           result.put(field, object);
/*    */         } 
/*    */         
/* 49 */         resultt.add(result);
/*    */       } 
/*    */       
/* 52 */       this.data = resultt;
/* 53 */       ((ResultSet)group.getB()).close();
/* 54 */       ((Statement)((DataBaseCore.Group)group.getA()).getB()).close();
/* 55 */       ((Connection)((DataBaseCore.Group)group.getA()).getA()).close();
/* 56 */     } catch (SQLException throwables) {
/* 57 */       throwables.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }