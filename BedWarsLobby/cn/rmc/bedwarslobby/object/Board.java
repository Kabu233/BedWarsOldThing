/*     */ package cn.rmc.bedwarslobby.object;
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Deque;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class Board {
/*     */   private final Player owner;
/*  17 */   private final String title = "";
/*     */   private final List<String> lines;
/*     */   private final Sideline sideline;
/*     */   
/*     */   public Board(Player owner) {
/*  22 */     this.owner = owner;
/*     */     
/*  24 */     this.lines = Collections.singletonList("");
/*  25 */     this.sideline = new Sideline(new Sidebar(owner));
/*  26 */     this.sideline.getSidebar().setName(color(""));
/*  27 */     this.lines.stream().map(Board::color).forEach(this.sideline::add);
/*  28 */     this.sideline.flush();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String color(String source) {
/*  35 */     return ChatColor.translateAlternateColorCodes('&', source);
/*     */   }
/*     */   
/*     */   public void send(List<String> lines) {
/*  39 */     lines.stream().map(Board::color).forEach(this.sideline::add);
/*  40 */     this.sideline.flush();
/*     */   }
/*     */   
/*     */   public void send(String title, List<String> lines) {
/*  44 */     this.sideline.getSidebar().setName(color(title));
/*  45 */     send(lines);
/*     */   }
/*     */   
/*     */   public void remove() {
/*  49 */     this.sideline.getSidebar().remove();
/*     */   }
/*     */   
/*     */   public enum SpecificWriterType {
/*  53 */     DISPLAY, ACTIONCHANGE, ACTIONREMOVE;
/*     */   }
/*     */   
/*     */   public static class Sidebar
/*     */   {
/*     */     private final Player player;
/*     */     private final HashMap<String, Integer> linesA;
/*     */     private final HashMap<String, Integer> linesB;
/*     */     private final Board.SpecificWriterHandler handler;
/*  62 */     private Boolean a = Boolean.valueOf(true);
/*     */     
/*     */     public Sidebar(Player p)
/*     */     {
/*     */       try {
/*  67 */         this.player = p;
/*  68 */         this.linesA = new HashMap<>();
/*  69 */         this.linesB = new HashMap<>();
/*  70 */         this.handler = new Board.SpecificWriterHandler();
/*     */         
/*  72 */         PacketContainer createA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/*  73 */         createA.getStrings().write(0, "A")
/*  74 */           .write(1, "");
/*  75 */         createA.getIntegers().write(0, Integer.valueOf(0));
/*  76 */         this.handler.write(createA, Board.SpecificWriterType.DISPLAY);
/*     */         
/*  78 */         PacketContainer createB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/*  79 */         createB.getStrings().write(0, "B")
/*  80 */           .write(1, "");
/*  81 */         createB.getIntegers().write(0, Integer.valueOf(0));
/*  82 */         this.handler.write(createB, Board.SpecificWriterType.DISPLAY);
/*     */         
/*  84 */         ProtocolLibrary.getProtocolManager().sendServerPacket(p, createA);
/*  85 */         ProtocolLibrary.getProtocolManager().sendServerPacket(p, createB);
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       }  } private String getBuffer() {
/*  89 */       return this.a.booleanValue() ? "A" : "B";
/*     */     }
/*     */     
/*     */     private HashMap<String, Integer> linesBuffer() {
/*  93 */       return this.a.booleanValue() ? this.linesA : this.linesB;
/*     */     }
/*     */     
/*     */     private HashMap<String, Integer> linesDisplayed() {
/*  97 */       return !this.a.booleanValue() ? this.linesA : this.linesB;
/*     */     }
/*     */     
/*     */     private void swapBuffer() {
/* 101 */       this.a = Boolean.valueOf(!this.a.booleanValue());
/*     */     }
/*     */     
/*     */     public void send() {
/*     */       try {
/* 106 */         PacketContainer display = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
/* 107 */         display.getIntegers().write(0, Integer.valueOf(1));
/* 108 */         display.getStrings().write(0, getBuffer());
/*     */         
/* 110 */         swapBuffer();
/*     */         
/* 112 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, display);
/*     */         
/* 114 */         for (String text : linesDisplayed().keySet()) {
/* 115 */           if (linesBuffer().containsKey(text) && 
/* 116 */             linesBuffer().get(text) == linesDisplayed().get(text)) {
/*     */             continue;
/*     */           }
/*     */ 
/*     */           
/* 121 */           setLine(text, linesDisplayed().get(text));
/*     */         } 
/* 123 */         for (String text : new ArrayList(linesBuffer().keySet())) {
/* 124 */           if (!linesDisplayed().containsKey(text))
/* 125 */             removeLine(text); 
/*     */         } 
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       } 
/*     */     }
/*     */     public void remove() { try {
/* 132 */         PacketContainer removeA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 133 */         removeA.getStrings().write(0, "A").write(1, "");
/* 134 */         removeA.getIntegers().write(0, Integer.valueOf(1));
/* 135 */         PacketContainer removeB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 136 */         removeB.getStrings().write(0, "B").write(1, "");
/* 137 */         removeB.getIntegers().write(0, Integer.valueOf(1));
/*     */         
/* 139 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, removeA);
/* 140 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, removeB);
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       }  } public void clear() {
/* 144 */       for (String text : new ArrayList(linesBuffer().keySet())) {
/* 145 */         removeLine(text);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setLine(String text, Integer line) {
/*     */       try {
/* 151 */         if (text == null) {
/*     */           return;
/*     */         }
/* 154 */         if (text.length() > 40) {
/* 155 */           text = text.substring(0, 40);
/*     */         }
/* 157 */         if (linesBuffer().containsKey(text)) {
/* 158 */           removeLine(text);
/*     */         }
/* 160 */         PacketContainer set = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
/* 161 */         set.getStrings().write(0, text).write(1, getBuffer());
/* 162 */         set.getIntegers().write(0, line);
/* 163 */         this.handler.write(set, Board.SpecificWriterType.ACTIONCHANGE);
/*     */         
/* 165 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, set);
/* 166 */         linesBuffer().put(text, line);
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       } 
/*     */     }
/*     */     public void removeLine(String text) { try {
/* 172 */         if (text.length() > 40) {
/* 173 */           text = text.substring(0, 40);
/*     */         }
/* 175 */         if (!linesBuffer().containsKey(text)) {
/*     */           return;
/*     */         }
/* 178 */         Integer line = linesBuffer().get(text);
/*     */         
/* 180 */         PacketContainer reset = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
/* 181 */         reset.getStrings().write(0, text).write(1, getBuffer());
/* 182 */         reset.getIntegers().write(0, line);
/* 183 */         this.handler.write(reset, Board.SpecificWriterType.ACTIONREMOVE);
/*     */         
/* 185 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, reset);
/* 186 */         linesBuffer().remove(text);
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       }  } public String getName() {
/* 190 */       return this.player.getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setName(String displayName) {
/*     */       try {
/* 196 */         if (displayName.length() > 32) {
/* 197 */           displayName = displayName.substring(0, 32);
/*     */         }
/* 199 */         PacketContainer nameA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 200 */         nameA.getStrings().write(0, "A").write(1, displayName);
/* 201 */         nameA.getIntegers().write(0, Integer.valueOf(2));
/* 202 */         this.handler.write(nameA, Board.SpecificWriterType.DISPLAY);
/*     */         
/* 204 */         PacketContainer nameB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 205 */         nameB.getStrings().write(0, "B").write(1, displayName);
/* 206 */         nameB.getIntegers().write(0, Integer.valueOf(2));
/* 207 */         this.handler.write(nameB, Board.SpecificWriterType.DISPLAY);
/*     */         
/* 209 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, nameA);
/* 210 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, nameB);
/*     */       } catch (Throwable $ex) {
/*     */         throw $ex;
/*     */       } 
/*     */     } }
/*     */   
/*     */   public static class Sideline { Board.Sidebar sb;
/* 217 */     HashMap<Integer, String> old = new HashMap<>();
/* 218 */     Deque<String> buffer = new ArrayDeque<>();
/*     */     
/*     */     public Sideline(Board.Sidebar sb) {
/* 221 */       this.sb = sb;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 225 */       this.sb.clear();
/* 226 */       this.old.clear();
/*     */     }
/*     */     
/*     */     public void set(Integer i, String str) {
/* 230 */       if (this.old.containsKey(i)) {
/* 231 */         this.sb.removeLine(this.old.get(i));
/*     */       }
/*     */       
/* 234 */       if (str.equals("")) {
/* 235 */         str = " ";
/*     */       }
/* 237 */       str = makeUnique(str);
/*     */       
/* 239 */       this.old.put(i, str);
/* 240 */       this.sb.setLine(str, i);
/*     */     }
/*     */     
/*     */     public String makeUnique(String str) {
/* 244 */       while (this.old.containsValue(str)) {
/* 245 */         for (int j = 0; j < (ChatColor.values()).length; j++) {
/* 246 */           if (!this.old.containsValue(str + ChatColor.values()[j])) {
/* 247 */             str = str + ChatColor.values()[j];
/* 248 */             return str;
/*     */           } 
/*     */         } 
/* 251 */         str = str + ChatColor.RESET;
/*     */       } 
/* 253 */       return str;
/*     */     }
/*     */     
/*     */     public void add(String s) {
/* 257 */       this.buffer.add(s);
/*     */     }
/*     */     
/*     */     public void flush() {
/* 261 */       clear();
/* 262 */       Integer i = Integer.valueOf(0);
/* 263 */       Iterator<String> it = this.buffer.descendingIterator();
/* 264 */       while (it.hasNext()) {
/* 265 */         String line = it.next();
/* 266 */         Integer integer1 = i, integer2 = i = Integer.valueOf(i.intValue() + 1);
/* 267 */         set(i, line);
/*     */       } 
/*     */       
/* 270 */       this.buffer.clear();
/*     */       
/* 272 */       this.sb.send();
/*     */     }
/*     */     
/*     */     public void send() {
/* 276 */       this.sb.send();
/*     */     }
/*     */     
/*     */     public Integer getRemainingSize() {
/* 280 */       return Integer.valueOf(15 - this.buffer.size());
/*     */     }
/*     */     
/*     */     public Board.Sidebar getSidebar() {
/* 284 */       return this.sb;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SpecificWriterHandler
/*     */   {
/* 294 */     private static final String version = getNMSVersion(); private static Class<?> healthclass; static {
/*     */       try {
/* 296 */         healthclass = Class.forName(a("IScoreboardCriteria$EnumScoreboardHealthDisplay"));
/* 297 */         interger = healthclass.getEnumConstants()[0];
/* 298 */       } catch (Exception e) {
/* 299 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     private static Object interger;
/*     */     public static String a(String str) {
/* 304 */       return "net.minecraft.server." + version + "." + str;
/*     */     }
/*     */     
/*     */     public static String b(String str) {
/* 308 */       return "org.bukkit.craftbukkit." + version + "." + str;
/*     */     }
/*     */     
/*     */     public static String getNMSVersion() {
/* 312 */       return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
/*     */     }
/*     */     
/*     */     public void write(PacketContainer container, Board.SpecificWriterType type) {
/* 316 */       if (type == Board.SpecificWriterType.DISPLAY) {
/* 317 */         container.getModifier().write(2, interger);
/* 318 */       } else if (type == Board.SpecificWriterType.ACTIONCHANGE) {
/* 319 */         container.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);
/* 320 */       } else if (type == Board.SpecificWriterType.ACTIONREMOVE) {
/* 321 */         container.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.REMOVE);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }