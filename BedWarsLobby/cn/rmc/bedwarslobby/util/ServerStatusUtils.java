/*     */ package cn.rmc.bedwarslobby.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.json.simple.parser.JSONParser;
/*     */ import org.json.simple.parser.ParseException;
/*     */ 
/*     */ public class ServerStatusUtils {
/*     */   public static ServerStatus pingServer(String ip, int port) throws IOException {
/*  16 */     try (Socket socket = new Socket()) {
/*     */       
/*  18 */       socket.connect(new InetSocketAddress(ip, port), 1000);
/*  19 */       socket.setSoTimeout(1000);
/*     */       
/*  21 */       DataInputStream in = new DataInputStream(socket.getInputStream());
/*  22 */       DataOutputStream out = new DataOutputStream(socket.getOutputStream());
/*     */ 
/*     */       
/*  25 */       ByteArrayOutputStream handshake = new ByteArrayOutputStream();
/*  26 */       DataOutputStream handshakeOut = new DataOutputStream(handshake);
/*     */       
/*  28 */       handshakeOut.writeByte(0);
/*  29 */       writeVarInt(handshakeOut, 47);
/*  30 */       writeString(handshakeOut, ip);
/*  31 */       handshakeOut.writeShort(port);
/*  32 */       writeVarInt(handshakeOut, 1);
/*     */       
/*  34 */       sendPacket(out, handshake.toByteArray());
/*     */       
/*  36 */       out.writeByte(1);
/*  37 */       out.writeByte(0);
/*     */       
/*  39 */       readVarInt(in);
/*  40 */       int packetId = readVarInt(in);
/*  41 */       if (packetId != 0) {
/*  42 */         throw new IOException("无效的响应包ID: " + packetId);
/*     */       }
/*     */       
/*  45 */       int jsonLength = readVarInt(in);
/*  46 */       byte[] jsonData = new byte[jsonLength];
/*  47 */       in.readFully(jsonData);
/*  48 */       String jsonString = new String(jsonData, StandardCharsets.UTF_8);
/*     */       
/*  50 */       return parseResponse(jsonString);
/*  51 */     } catch (Exception e) {
/*  52 */       return new ServerStatus("OFFLINE", "0", 0L, 0L, false);
/*     */     } 
/*     */   }
/*     */   static ServerStatus parseResponse(String json) {
/*     */     try {
/*     */       String motd;
/*  58 */       JSONParser parser = new JSONParser();
/*  59 */       JSONObject root = (JSONObject)parser.parse(json);
/*     */ 
/*     */       
/*  62 */       Object description = root.get("description");
/*  63 */       if (description instanceof JSONObject) {
/*  64 */         motd = (String)((JSONObject)description).get("text");
/*     */       } else {
/*  66 */         motd = description.toString();
/*     */       } 
/*     */       
/*  69 */       JSONObject version = (JSONObject)root.get("version");
/*  70 */       JSONObject players = (JSONObject)root.get("players");
/*     */       
/*  72 */       String protocol = (version != null) ? version.get("protocol").toString() : "0";
/*  73 */       long online = (players != null) ? ((Long)players.get("online")).longValue() : 0L;
/*  74 */       long max = (players != null) ? ((Long)players.get("max")).longValue() : 0L;
/*     */       
/*  76 */       boolean isWaiting = (motd != null && motd.contains("等待中"));
/*     */       
/*  78 */       return new ServerStatus((motd != null) ? motd : "Unknown", protocol, online, max, true, isWaiting);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  86 */     catch (ParseException e) {
/*  87 */       throw new RuntimeException("JSON解析失败: " + json, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   static void writeVarInt(DataOutputStream out, int value) throws IOException {
/*  92 */     while ((value & 0xFFFFFF80) != 0) {
/*  93 */       out.writeByte(value & 0x7F | 0x80);
/*  94 */       value >>>= 7;
/*     */     } 
/*  96 */     out.writeByte(value & 0x7F);
/*     */   }
/*     */   
/*     */   static int readVarInt(DataInputStream in) throws IOException {
/* 100 */     int value = 0;
/* 101 */     int length = 0;
/*     */     
/*     */     while (true) {
/* 104 */       byte current = in.readByte();
/* 105 */       value |= (current & Byte.MAX_VALUE) << length * 7;
/* 106 */       length++;
/* 107 */       if (length > 5) throw new IOException("VarInt过长"); 
/* 108 */       if ((current & 0x80) != 128)
/* 109 */         return value; 
/*     */     } 
/*     */   }
/*     */   static void writeString(DataOutputStream out, String str) throws IOException {
/* 113 */     byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
/* 114 */     writeVarInt(out, bytes.length);
/* 115 */     out.write(bytes);
/*     */   }
/*     */   
/*     */   static void sendPacket(DataOutputStream out, byte[] data) throws IOException {
/* 119 */     writeVarInt(out, data.length);
/* 120 */     out.write(data);
/*     */   }
/*     */   public static class ServerStatus {
/*     */     final String motd; final String protocolVersion; final int onlinePlayers;
/*     */     
/* 125 */     public String getMotd() { return this.motd; } final int maxPlayers; final boolean online; final boolean waiting; final String server; public String getProtocolVersion() {
/* 126 */       return this.protocolVersion; }
/* 127 */     public int getOnlinePlayers() { return this.onlinePlayers; }
/* 128 */     public int getMaxPlayers() { return this.maxPlayers; }
/* 129 */     public boolean isOnline() { return this.online; }
/* 130 */     public boolean isWaiting() { return this.waiting; } public String getServer() {
/* 131 */       return this.server;
/*     */     }
/*     */     public ServerStatus() {
/* 134 */       this("OFFLINE", "0", 0L, 0L, false, false, "");
/*     */     }
/*     */     
/*     */     public ServerStatus(String server) {
/* 138 */       this("OFFLINE", "0", 0L, 0L, false, false, server);
/*     */     }
/*     */     
/*     */     public ServerStatus(String motd, String protocolVersion, long onlinePlayers, long maxPlayers, boolean online) {
/* 142 */       this(motd, protocolVersion, safeLongToInt(onlinePlayers), safeLongToInt(maxPlayers), online, false, "");
/*     */     }
/*     */     
/*     */     public ServerStatus(String motd, String protocolVersion, long onlinePlayers, long maxPlayers, boolean online, boolean waiting) {
/* 146 */       this(motd, protocolVersion, safeLongToInt(onlinePlayers), safeLongToInt(maxPlayers), online, waiting, "");
/*     */     }
/*     */     
/*     */     public ServerStatus(String motd, String protocolVersion, long onlinePlayers, long maxPlayers, boolean online, boolean waiting, String server) {
/* 150 */       this.motd = motd;
/* 151 */       this.protocolVersion = protocolVersion;
/* 152 */       this.onlinePlayers = safeLongToInt(onlinePlayers);
/* 153 */       this.maxPlayers = safeLongToInt(maxPlayers);
/* 154 */       this.online = online;
/* 155 */       this.waiting = waiting;
/* 156 */       this.server = server;
/*     */     }
/*     */     
/*     */     public boolean isAvailable() {
/* 160 */       return (this.online && this.waiting && this.onlinePlayers < this.maxPlayers);
/*     */     }
/*     */     
/*     */     static int safeLongToInt(long l) {
/* 164 */       if (l < -2147483648L || l > 2147483647L) {
/* 165 */         throw new IllegalArgumentException(l + " cannot be cast to int without changing its value.");
/*     */       }
/* 167 */       return (int)l;
/*     */     }
/*     */   }
/*     */ }