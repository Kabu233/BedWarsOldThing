/*     */ package com.comphenix.tinyprotocol;
/*     */ 
/*     */ import com.google.common.collect.MapMaker;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelDuplexHandler;
/*     */ import io.netty.channel.ChannelFuture;
/*     */ import io.netty.channel.ChannelHandler;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import io.netty.channel.ChannelInitializer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import io.netty.channel.ChannelPromise;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.server.PluginDisableEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TinyProtocol
/*     */ {
/*  42 */   private static final AtomicInteger ID = new AtomicInteger(0);
/*     */ 
/*     */   
/*  45 */   private static final Class<?> entityPlayerClass = Reflection.getClass("{nms}.EntityPlayer", new String[] { "net.minecraft.server.level.EntityPlayer" });
/*  46 */   private static final Class<?> playerConnectionClass = Reflection.getClass("{nms}.PlayerConnection", new String[] { "net.minecraft.server.network.PlayerConnection" });
/*  47 */   private static final Class<?> networkManagerClass = Reflection.getClass("{nms}.NetworkManager", new String[] { "net.minecraft.network.NetworkManager" });
/*     */ 
/*     */   
/*  50 */   private static final Reflection.MethodInvoker getPlayerHandle = Reflection.getMethod("{obc}.entity.CraftPlayer", "getHandle", new Class[0]);
/*  51 */   private static final Reflection.FieldAccessor<?> getConnection = Reflection.getField(entityPlayerClass, (String)null, playerConnectionClass);
/*  52 */   private static final Reflection.FieldAccessor<?> getManager = Reflection.getField(playerConnectionClass, (String)null, networkManagerClass);
/*  53 */   private static final Reflection.FieldAccessor<Channel> getChannel = Reflection.getField(networkManagerClass, Channel.class, 0);
/*     */ 
/*     */   
/*  56 */   private static final Class<Object> minecraftServerClass = Reflection.getUntypedClass("{nms}.MinecraftServer", new String[] { "net.minecraft.server.MinecraftServer" });
/*  57 */   private static final Class<Object> serverConnectionClass = Reflection.getUntypedClass("{nms}.ServerConnection", new String[] { "net.minecraft.server.network.ServerConnection" });
/*  58 */   private static final Reflection.FieldAccessor<Object> getMinecraftServer = Reflection.getField("{obc}.CraftServer", minecraftServerClass, 0);
/*  59 */   private static final Reflection.FieldAccessor<Object> getServerConnection = Reflection.getField(minecraftServerClass, serverConnectionClass, 0);
/*     */ 
/*     */   
/*  62 */   private static final Class<?> PACKET_LOGIN_IN_START = Reflection.getClass("{nms}.PacketLoginInStart", new String[] { "net.minecraft.network.protocol.login.PacketLoginInStart" });
/*  63 */   private static final Reflection.FieldAccessor<String> getPlayerName = new PlayerNameAccessor();
/*     */ 
/*     */   
/*  66 */   private Map<String, Channel> channelLookup = (new MapMaker()).weakValues().makeMap();
/*     */   
/*     */   private Listener listener;
/*     */   
/*  70 */   private Set<Channel> uninjectedChannels = Collections.newSetFromMap((new MapMaker()).weakKeys().makeMap());
/*     */ 
/*     */   
/*     */   private List<Object> networkManagers;
/*     */ 
/*     */   
/*  76 */   private List<Channel> serverChannels = new ArrayList<>();
/*     */ 
/*     */   
/*     */   private ChannelInboundHandlerAdapter serverChannelHandler;
/*     */ 
/*     */   
/*     */   private ChannelInitializer<Channel> beginInitProtocol;
/*     */ 
/*     */   
/*     */   private ChannelInitializer<Channel> endInitProtocol;
/*     */   
/*     */   private String handlerName;
/*     */   
/*     */   protected volatile boolean closed;
/*     */   
/*     */   protected Plugin plugin;
/*     */ 
/*     */   
/*     */   public TinyProtocol(final Plugin plugin) {
/*  95 */     this.plugin = plugin;
/*     */ 
/*     */     
/*  98 */     this.handlerName = getHandlerName();
/*     */ 
/*     */     
/* 101 */     registerBukkitEvents();
/*     */     
/*     */     try {
/* 104 */       registerChannelHandler();
/* 105 */       registerPlayers(plugin);
/* 106 */     } catch (IllegalArgumentException ex) {
/*     */       
/* 108 */       plugin.getLogger().info("[TinyProtocol] Delaying server channel injection due to late bind.");
/*     */       
/* 110 */       (new BukkitRunnable()
/*     */         {
/*     */           public void run() {
/* 113 */             TinyProtocol.this.registerChannelHandler();
/* 114 */             TinyProtocol.this.registerPlayers(plugin);
/* 115 */             plugin.getLogger().info("[TinyProtocol] Late bind injection successful.");
/*     */           }
/* 117 */         }).runTask(plugin);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createServerChannelHandler() {
/* 123 */     this.endInitProtocol = new ChannelInitializer<Channel>()
/*     */       {
/*     */         
/*     */         protected void initChannel(Channel channel) throws Exception
/*     */         {
/*     */           try {
/* 129 */             synchronized (TinyProtocol.this.networkManagers) {
/*     */               
/* 131 */               if (!TinyProtocol.this.closed) {
/* 132 */                 channel.eventLoop().submit(() -> TinyProtocol.this.injectChannelInternal(channel));
/*     */               }
/*     */             } 
/* 135 */           } catch (Exception e) {
/* 136 */             TinyProtocol.this.plugin.getLogger().log(Level.SEVERE, "Cannot inject incomming channel " + channel, e);
/*     */           } 
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/* 143 */     this.beginInitProtocol = new ChannelInitializer<Channel>()
/*     */       {
/*     */         protected void initChannel(Channel channel) throws Exception
/*     */         {
/* 147 */           channel.pipeline().addLast(new ChannelHandler[] { (ChannelHandler)TinyProtocol.access$400(this.this$0) });
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 152 */     this.serverChannelHandler = new ChannelInboundHandlerAdapter()
/*     */       {
/*     */         public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
/*     */         {
/* 156 */           Channel channel = (Channel)msg;
/*     */ 
/*     */           
/* 159 */           channel.pipeline().addFirst(new ChannelHandler[] { (ChannelHandler)TinyProtocol.access$500(this.this$0) });
/* 160 */           ctx.fireChannelRead(msg);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerBukkitEvents() {
/* 170 */     this.listener = new Listener()
/*     */       {
/*     */         @EventHandler(priority = EventPriority.LOWEST)
/*     */         public final void onPlayerLogin(PlayerLoginEvent e) {
/* 174 */           if (TinyProtocol.this.closed) {
/*     */             return;
/*     */           }
/* 177 */           Channel channel = TinyProtocol.this.getChannel(e.getPlayer());
/*     */ 
/*     */           
/* 180 */           if (!TinyProtocol.this.uninjectedChannels.contains(channel)) {
/* 181 */             TinyProtocol.this.injectPlayer(e.getPlayer());
/*     */           }
/*     */         }
/*     */         
/*     */         @EventHandler
/*     */         public final void onPluginDisable(PluginDisableEvent e) {
/* 187 */           if (e.getPlugin().equals(TinyProtocol.this.plugin)) {
/* 188 */             TinyProtocol.this.close();
/*     */           }
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 194 */     this.plugin.getServer().getPluginManager().registerEvents(this.listener, this.plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerChannelHandler() {
/* 199 */     Object mcServer = getMinecraftServer.get(Bukkit.getServer());
/* 200 */     Object serverConnection = getServerConnection.get(mcServer);
/* 201 */     boolean looking = true;
/*     */     
/*     */     try {
/* 204 */       Field field = Reflection.getParameterizedField(serverConnectionClass, List.class, new Class[] { networkManagerClass });
/* 205 */       field.setAccessible(true);
/*     */       
/* 207 */       this.networkManagers = (List<Object>)field.get(serverConnection);
/* 208 */     } catch (Exception ex) {
/* 209 */       this.plugin.getLogger().info("Encountered an exception checking list fields" + ex);
/* 210 */       Reflection.MethodInvoker method = Reflection.getTypedMethod(serverConnectionClass, null, List.class, new Class[] { serverConnectionClass });
/*     */       
/* 212 */       this.networkManagers = (List<Object>)method.invoke(null, new Object[] { serverConnection });
/*     */     } 
/*     */     
/* 215 */     if (this.networkManagers == null) {
/* 216 */       throw new IllegalArgumentException("Failed to obtain list of network managers");
/*     */     }
/*     */     
/* 219 */     createServerChannelHandler();
/*     */ 
/*     */     
/* 222 */     for (int i = 0; looking; i++) {
/* 223 */       List<Object> list = Reflection.<List<Object>>getField(serverConnection.getClass(), (Class)List.class, i).get(serverConnection);
/*     */       
/* 225 */       for (Object item : list) {
/* 226 */         if (!ChannelFuture.class.isInstance(item)) {
/*     */           break;
/*     */         }
/*     */         
/* 230 */         Channel serverChannel = ((ChannelFuture)item).channel();
/*     */         
/* 232 */         this.serverChannels.add(serverChannel);
/* 233 */         serverChannel.pipeline().addFirst(new ChannelHandler[] { (ChannelHandler)this.serverChannelHandler });
/* 234 */         looking = false;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void unregisterChannelHandler() {
/* 240 */     if (this.serverChannelHandler == null) {
/*     */       return;
/*     */     }
/* 243 */     for (Channel serverChannel : this.serverChannels) {
/* 244 */       final ChannelPipeline pipeline = serverChannel.pipeline();
/*     */ 
/*     */       
/* 247 */       serverChannel.eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try {
/* 252 */                 pipeline.remove((ChannelHandler)TinyProtocol.this.serverChannelHandler);
/* 253 */               } catch (NoSuchElementException noSuchElementException) {}
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerPlayers(Plugin plugin) {
/* 263 */     for (Player player : plugin.getServer().getOnlinePlayers()) {
/* 264 */       injectPlayer(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onPacketOutAsync(Player receiver, Channel channel, Object packet) {
/* 279 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object onPacketInAsync(Player sender, Channel channel, Object packet) {
/* 293 */     return packet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(Player player, Object packet) {
/* 305 */     sendPacket(getChannel(player), packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacket(Channel channel, Object packet) {
/* 317 */     channel.pipeline().writeAndFlush(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void receivePacket(Player player, Object packet) {
/* 329 */     receivePacket(getChannel(player), packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void receivePacket(Channel channel, Object packet) {
/* 341 */     channel.pipeline().context("encoder").fireChannelRead(packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getHandlerName() {
/* 352 */     return "tiny-" + this.plugin.getName() + "-" + ID.incrementAndGet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void injectPlayer(Player player) {
/* 363 */     (injectChannelInternal(getChannel(player))).player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void injectChannel(Channel channel) {
/* 373 */     injectChannelInternal(channel);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PacketInterceptor injectChannelInternal(Channel channel) {
/*     */     try {
/* 384 */       PacketInterceptor interceptor = (PacketInterceptor)channel.pipeline().get(this.handlerName);
/*     */ 
/*     */       
/* 387 */       if (interceptor == null) {
/* 388 */         interceptor = new PacketInterceptor();
/* 389 */         channel.pipeline().addBefore("packet_handler", this.handlerName, (ChannelHandler)interceptor);
/* 390 */         this.uninjectedChannels.remove(channel);
/*     */       } 
/*     */       
/* 393 */       return interceptor;
/* 394 */     } catch (IllegalArgumentException e) {
/*     */       
/* 396 */       return (PacketInterceptor)channel.pipeline().get(this.handlerName);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Channel getChannel(Player player) {
/* 407 */     Channel channel = this.channelLookup.get(player.getName());
/*     */ 
/*     */     
/* 410 */     if (channel == null) {
/* 411 */       Object connection = getConnection.get(getPlayerHandle.invoke(player, new Object[0]));
/* 412 */       Object manager = getManager.get(connection);
/*     */       
/* 414 */       this.channelLookup.put(player.getName(), channel = getChannel.get(manager));
/*     */     } 
/*     */     
/* 417 */     return channel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninjectPlayer(Player player) {
/* 426 */     uninjectChannel(getChannel(player));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void uninjectChannel(final Channel channel) {
/* 438 */     if (!this.closed) {
/* 439 */       this.uninjectedChannels.add(channel);
/*     */     }
/*     */ 
/*     */     
/* 443 */     channel.eventLoop().execute(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 447 */             channel.pipeline().remove(TinyProtocol.this.handlerName);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInjected(Player player) {
/* 460 */     return hasInjected(getChannel(player));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasInjected(Channel channel) {
/* 470 */     return (channel.pipeline().get(this.handlerName) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() {
/* 477 */     if (!this.closed) {
/* 478 */       this.closed = true;
/*     */ 
/*     */       
/* 481 */       for (Player player : this.plugin.getServer().getOnlinePlayers()) {
/* 482 */         uninjectPlayer(player);
/*     */       }
/*     */ 
/*     */       
/* 486 */       HandlerList.unregisterAll(this.listener);
/* 487 */       unregisterChannelHandler();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class PlayerNameAccessor
/*     */     implements Reflection.FieldAccessor<String>
/*     */   {
/*     */     private Reflection.FieldAccessor<String> getPlayerName;
/*     */ 
/*     */     
/*     */     private Reflection.FieldAccessor<GameProfile> getGameProfile;
/*     */ 
/*     */     
/*     */     PlayerNameAccessor() {
/*     */       try {
/* 504 */         this.getGameProfile = Reflection.getField(TinyProtocol.PACKET_LOGIN_IN_START, GameProfile.class, 0);
/* 505 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 511 */         this.getPlayerName = Reflection.getField(TinyProtocol.PACKET_LOGIN_IN_START, String.class, 0);
/* 512 */       } catch (IllegalArgumentException illegalArgumentException) {}
/*     */ 
/*     */ 
/*     */       
/* 516 */       if (this.getGameProfile == null && this.getPlayerName == null) {
/* 517 */         throw new UnsupportedOperationException("The current server version is not supported by TinyProtocol");
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public String get(Object target) {
/* 523 */       if (this.getPlayerName != null) {
/* 524 */         String playerName = this.getPlayerName.get(target);
/* 525 */         return playerName.substring(0, Math.min(16, playerName.length()));
/*     */       } 
/*     */       
/* 528 */       return ((GameProfile)this.getGameProfile.get(target)).getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public void set(Object target, Object value) {
/* 533 */       throw new UnsupportedOperationException("Not supported");
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean hasField(Object target) {
/* 538 */       return (this.getPlayerName != null) ? this.getPlayerName
/* 539 */         .hasField(target) : this.getGameProfile
/* 540 */         .hasField(target);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class PacketInterceptor
/*     */     extends ChannelDuplexHandler
/*     */   {
/*     */     public volatile Player player;
/*     */ 
/*     */     
/*     */     private PacketInterceptor() {}
/*     */ 
/*     */     
/*     */     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 556 */       Channel channel = ctx.channel();
/* 557 */       handleLoginStart(channel, msg);
/*     */       
/*     */       try {
/* 560 */         msg = TinyProtocol.this.onPacketInAsync(this.player, channel, msg);
/* 561 */       } catch (Exception e) {
/* 562 */         TinyProtocol.this.plugin.getLogger().log(Level.SEVERE, "Error in onPacketInAsync().", e);
/*     */       } 
/*     */       
/* 565 */       if (msg != null) {
/* 566 */         super.channelRead(ctx, msg);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*     */       try {
/* 573 */         msg = TinyProtocol.this.onPacketOutAsync(this.player, ctx.channel(), msg);
/* 574 */       } catch (Exception e) {
/* 575 */         TinyProtocol.this.plugin.getLogger().log(Level.SEVERE, "Error in onPacketOutAsync().", e);
/*     */       } 
/*     */       
/* 578 */       if (msg != null) {
/* 579 */         super.write(ctx, msg, promise);
/*     */       }
/*     */     }
/*     */     
/*     */     private void handleLoginStart(Channel channel, Object packet) {
/* 584 */       if (TinyProtocol.PACKET_LOGIN_IN_START.isInstance(packet))
/* 585 */         TinyProtocol.this.channelLookup.put(TinyProtocol.getPlayerName.get(packet), channel); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\com\comphenix\tinyprotocol\TinyProtocol.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */