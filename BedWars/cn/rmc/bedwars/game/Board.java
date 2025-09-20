/*     */ package cn.rmc.bedwars.game;
/*     */ import com.comphenix.protocol.PacketType;
/*     */ import com.comphenix.protocol.ProtocolLibrary;
/*     */ import com.comphenix.protocol.events.PacketContainer;
/*     */ import com.comphenix.protocol.wrappers.EnumWrappers;
/*     */ import java.lang.reflect.InvocationTargetException;
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
/*  17 */   private final String title = ""; private final Player owner;
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
/*     */     
/*     */     public Sidebar(Player p) {
/*  66 */       this.player = p;
/*  67 */       this.linesA = new HashMap<>();
/*  68 */       this.linesB = new HashMap<>();
/*  69 */       this.handler = new Board.SpecificWriterHandler();
/*     */       
/*  71 */       PacketContainer createA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/*  72 */       createA.getStrings().write(0, "A")
/*  73 */         .write(1, "");
/*  74 */       createA.getIntegers().write(0, Integer.valueOf(0));
/*  75 */       this.handler.write(createA, Board.SpecificWriterType.DISPLAY);
/*     */       
/*  77 */       PacketContainer createB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/*  78 */       createB.getStrings().write(0, "B")
/*  79 */         .write(1, "");
/*  80 */       createB.getIntegers().write(0, Integer.valueOf(0));
/*  81 */       this.handler.write(createB, Board.SpecificWriterType.DISPLAY);
/*     */       
/*     */       try {
/*  84 */         ProtocolLibrary.getProtocolManager().sendServerPacket(p, createA);
/*  85 */         ProtocolLibrary.getProtocolManager().sendServerPacket(p, createB);
/*  86 */       } catch (InvocationTargetException e) {
/*  87 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     private String getBuffer() {
/*  92 */       return this.a.booleanValue() ? "A" : "B";
/*     */     }
/*     */     
/*     */     private HashMap<String, Integer> linesBuffer() {
/*  96 */       return this.a.booleanValue() ? this.linesA : this.linesB;
/*     */     }
/*     */     
/*     */     private HashMap<String, Integer> linesDisplayed() {
/* 100 */       return !this.a.booleanValue() ? this.linesA : this.linesB;
/*     */     }
/*     */     
/*     */     private void swapBuffer() {
/* 104 */       this.a = Boolean.valueOf(!this.a.booleanValue());
/*     */     }
/*     */     
/*     */     public void send() {
/* 108 */       PacketContainer display = new PacketContainer(PacketType.Play.Server.SCOREBOARD_DISPLAY_OBJECTIVE);
/* 109 */       display.getIntegers().write(0, Integer.valueOf(1));
/* 110 */       display.getStrings().write(0, getBuffer());
/*     */       
/* 112 */       swapBuffer();
/*     */       
/*     */       try {
/* 115 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, display);
/* 116 */       } catch (InvocationTargetException e) {
/* 117 */         e.printStackTrace();
/*     */       } 
/*     */       
/* 120 */       for (String text : linesDisplayed().keySet()) {
/* 121 */         if (linesBuffer().containsKey(text) && 
/* 122 */           linesBuffer().get(text) == linesDisplayed().get(text)) {
/*     */           continue;
/*     */         }
/*     */ 
/*     */         
/* 127 */         setLine(text, linesDisplayed().get(text));
/*     */       } 
/* 129 */       for (String text : new ArrayList(linesBuffer().keySet())) {
/* 130 */         if (!linesDisplayed().containsKey(text)) {
/* 131 */           removeLine(text);
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*     */     public void remove() {
/* 137 */       PacketContainer removeA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 138 */       removeA.getStrings().write(0, "A").write(1, "");
/* 139 */       removeA.getIntegers().write(0, Integer.valueOf(1));
/* 140 */       PacketContainer removeB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 141 */       removeB.getStrings().write(0, "B").write(1, "");
/* 142 */       removeB.getIntegers().write(0, Integer.valueOf(1));
/*     */       
/*     */       try {
/* 145 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, removeA);
/* 146 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, removeB);
/* 147 */       } catch (InvocationTargetException e) {
/* 148 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void clear() {
/* 153 */       for (String text : new ArrayList(linesBuffer().keySet())) {
/* 154 */         removeLine(text);
/*     */       }
/*     */     }
/*     */     
/*     */     public void setLine(String text, Integer line) {
/* 159 */       if (text == null) {
/*     */         return;
/*     */       }
/* 162 */       if (text.length() > 40) {
/* 163 */         text = text.substring(0, 40);
/*     */       }
/* 165 */       if (linesBuffer().containsKey(text)) {
/* 166 */         removeLine(text);
/*     */       }
/* 168 */       PacketContainer set = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
/* 169 */       set.getStrings().write(0, text).write(1, getBuffer());
/* 170 */       set.getIntegers().write(0, line);
/* 171 */       this.handler.write(set, Board.SpecificWriterType.ACTIONCHANGE);
/*     */       
/*     */       try {
/* 174 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, set);
/* 175 */         linesBuffer().put(text, line);
/* 176 */       } catch (InvocationTargetException e) {
/* 177 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void removeLine(String text) {
/* 183 */       if (text.length() > 40) {
/* 184 */         text = text.substring(0, 40);
/*     */       }
/* 186 */       if (!linesBuffer().containsKey(text)) {
/*     */         return;
/*     */       }
/* 189 */       Integer line = linesBuffer().get(text);
/*     */       
/* 191 */       PacketContainer reset = new PacketContainer(PacketType.Play.Server.SCOREBOARD_SCORE);
/* 192 */       reset.getStrings().write(0, text).write(1, getBuffer());
/* 193 */       reset.getIntegers().write(0, line);
/* 194 */       this.handler.write(reset, Board.SpecificWriterType.ACTIONREMOVE);
/*     */       
/*     */       try {
/* 197 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, reset);
/* 198 */         linesBuffer().remove(text);
/* 199 */       } catch (InvocationTargetException e) {
/* 200 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public String getName() {
/* 205 */       return this.player.getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setName(String displayName) {
/* 210 */       if (displayName.length() > 32) {
/* 211 */         displayName = displayName.substring(0, 32);
/*     */       }
/* 213 */       PacketContainer nameA = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 214 */       nameA.getStrings().write(0, "A").write(1, displayName);
/* 215 */       nameA.getIntegers().write(0, Integer.valueOf(2));
/* 216 */       this.handler.write(nameA, Board.SpecificWriterType.DISPLAY);
/*     */       
/* 218 */       PacketContainer nameB = new PacketContainer(PacketType.Play.Server.SCOREBOARD_OBJECTIVE);
/* 219 */       nameB.getStrings().write(0, "B").write(1, displayName);
/* 220 */       nameB.getIntegers().write(0, Integer.valueOf(2));
/* 221 */       this.handler.write(nameB, Board.SpecificWriterType.DISPLAY);
/*     */       
/*     */       try {
/* 224 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, nameA);
/* 225 */         ProtocolLibrary.getProtocolManager().sendServerPacket(this.player, nameB);
/* 226 */       } catch (InvocationTargetException e) {
/* 227 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Sideline
/*     */   {
/*     */     Board.Sidebar sb;
/* 235 */     HashMap<Integer, String> old = new HashMap<>();
/* 236 */     Deque<String> buffer = new ArrayDeque<>();
/*     */     
/*     */     public Sideline(Board.Sidebar sb) {
/* 239 */       this.sb = sb;
/*     */     }
/*     */     
/*     */     public void clear() {
/* 243 */       this.sb.clear();
/* 244 */       this.old.clear();
/*     */     }
/*     */     
/*     */     public void set(Integer i, String str) {
/* 248 */       if (this.old.containsKey(i)) {
/* 249 */         this.sb.removeLine(this.old.get(i));
/*     */       }
/*     */       
/* 252 */       if (str.equals("")) {
/* 253 */         str = " ";
/*     */       }
/* 255 */       str = makeUnique(str);
/*     */       
/* 257 */       this.old.put(i, str);
/* 258 */       this.sb.setLine(str, i);
/*     */     }
/*     */     
/*     */     public String makeUnique(String str) {
/* 262 */       while (this.old.containsValue(str)) {
/* 263 */         for (int j = 0; j < (ChatColor.values()).length; j++) {
/* 264 */           if (!this.old.containsValue(str + ChatColor.values()[j])) {
/* 265 */             str = str + ChatColor.values()[j];
/* 266 */             return str;
/*     */           } 
/*     */         } 
/* 269 */         str = str + ChatColor.RESET;
/*     */       } 
/* 271 */       return str;
/*     */     }
/*     */     
/*     */     public void add(String s) {
/* 275 */       this.buffer.add(s);
/*     */     }
/*     */     
/*     */     public void flush() {
/* 279 */       clear();
/* 280 */       Integer i = Integer.valueOf(0);
/* 281 */       Iterator<String> it = this.buffer.descendingIterator();
/* 282 */       while (it.hasNext()) {
/* 283 */         String line = it.next();
/* 284 */         Integer integer1 = i, integer2 = i = Integer.valueOf(i.intValue() + 1);
/* 285 */         set(i, line);
/*     */       } 
/*     */       
/* 288 */       this.buffer.clear();
/*     */       
/* 290 */       this.sb.send();
/*     */     }
/*     */     
/*     */     public void send() {
/* 294 */       this.sb.send();
/*     */     }
/*     */     
/*     */     public Integer getRemainingSize() {
/* 298 */       return Integer.valueOf(15 - this.buffer.size());
/*     */     }
/*     */     
/*     */     public Board.Sidebar getSidebar() {
/* 302 */       return this.sb;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SpecificWriterHandler
/*     */   {
/* 312 */     private static final String version = getNMSVersion(); private static Class<?> healthclass; private static Object interger; static {
/*     */       try {
/* 314 */         healthclass = Class.forName(a("IScoreboardCriteria$EnumScoreboardHealthDisplay"));
/* 315 */         interger = healthclass.getEnumConstants()[0];
/* 316 */       } catch (Exception e) {
/* 317 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*     */     public static String a(String str) {
/* 322 */       return "net.minecraft.server." + version + "." + str;
/*     */     }
/*     */     
/*     */     public static String b(String str) {
/* 326 */       return "org.bukkit.craftbukkit." + version + "." + str;
/*     */     }
/*     */     
/*     */     public static String getNMSVersion() {
/* 330 */       return Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
/*     */     }
/*     */     
/*     */     public void write(PacketContainer container, Board.SpecificWriterType type) {
/* 334 */       if (type == Board.SpecificWriterType.DISPLAY) {
/* 335 */         container.getModifier().write(2, interger);
/* 336 */       } else if (type == Board.SpecificWriterType.ACTIONCHANGE) {
/* 337 */         container.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.CHANGE);
/* 338 */       } else if (type == Board.SpecificWriterType.ACTIONREMOVE) {
/* 339 */         container.getScoreboardActions().write(0, EnumWrappers.ScoreboardAction.REMOVE);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }