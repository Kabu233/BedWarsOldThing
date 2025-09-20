/*     */ package org.bstats.bukkit;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonPrimitive;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.logging.Level;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import javax.net.ssl.HttpsURLConnection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.ServicePriority;
/*     */ 
/*     */ public class Metrics {
/*     */   static {
/*  35 */     if (System.getProperty("bstats.relocatecheck") == null || !System.getProperty("bstats.relocatecheck").equals("false")) {
/*     */       
/*  37 */       String defaultPackage = new String(new byte[] { 111, 114, 103, 46, 98, 115, 116, 97, 116, 115, 46, 98, 117, 107, 107, 105, 116 });
/*     */       
/*  39 */       String examplePackage = new String(new byte[] { 121, 111, 117, 114, 46, 112, 97, 99, 107, 97, 103, 101 });
/*     */       
/*  41 */       if (Metrics.class.getPackage().getName().equals(defaultPackage) || Metrics.class.getPackage().getName().equals(examplePackage)) {
/*  42 */         throw new IllegalStateException("bStats Metrics class has not been relocated correctly!");
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static final int B_STATS_VERSION = 1;
/*     */ 
/*     */   
/*     */   private static final String URL = "https://bStats.org/submitData/bukkit";
/*     */ 
/*     */   
/*     */   private boolean enabled;
/*     */ 
/*     */   
/*     */   private static boolean logFailedRequests;
/*     */ 
/*     */   
/*     */   private static boolean logSentData;
/*     */ 
/*     */   
/*     */   private static boolean logResponseStatusText;
/*     */ 
/*     */   
/*     */   private static String serverUUID;
/*     */ 
/*     */   
/*     */   private final Plugin plugin;
/*     */ 
/*     */   
/*     */   private final int pluginId;
/*     */   
/*  75 */   private final List<CustomChart> charts = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Metrics(Plugin plugin, int pluginId) {
/*  85 */     if (plugin == null) {
/*  86 */       throw new IllegalArgumentException("Plugin cannot be null!");
/*     */     }
/*  88 */     this.plugin = plugin;
/*  89 */     this.pluginId = pluginId;
/*     */ 
/*     */     
/*  92 */     File bStatsFolder = new File(plugin.getDataFolder().getParentFile(), "bStats");
/*  93 */     File configFile = new File(bStatsFolder, "config.yml");
/*  94 */     YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
/*     */ 
/*     */     
/*  97 */     if (!config.isSet("serverUuid")) {
/*     */ 
/*     */       
/* 100 */       config.addDefault("enabled", Boolean.valueOf(true));
/*     */       
/* 102 */       config.addDefault("serverUuid", UUID.randomUUID().toString());
/*     */       
/* 104 */       config.addDefault("logFailedRequests", Boolean.valueOf(false));
/*     */       
/* 106 */       config.addDefault("logSentData", Boolean.valueOf(false));
/*     */       
/* 108 */       config.addDefault("logResponseStatusText", Boolean.valueOf(false));
/*     */ 
/*     */       
/* 111 */       config.options().header("bStats collects some data for plugin authors like how many servers are using their plugins.\nTo honor their work, you should not disable it.\nThis has nearly no effect on the server performance!\nCheck out https://bStats.org/ to learn more :)")
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 116 */         .copyDefaults(true);
/*     */       try {
/* 118 */         config.save(configFile);
/* 119 */       } catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.enabled = config.getBoolean("enabled", true);
/* 125 */     serverUUID = config.getString("serverUuid");
/* 126 */     logFailedRequests = config.getBoolean("logFailedRequests", false);
/* 127 */     logSentData = config.getBoolean("logSentData", false);
/* 128 */     logResponseStatusText = config.getBoolean("logResponseStatusText", false);
/*     */     
/* 130 */     if (this.enabled) {
/* 131 */       boolean found = false;
/*     */       
/* 133 */       for (Class<?> service : (Iterable<Class<?>>)Bukkit.getServicesManager().getKnownServices()) {
/*     */         try {
/* 135 */           service.getField("B_STATS_VERSION");
/* 136 */           found = true;
/*     */           break;
/* 138 */         } catch (NoSuchFieldException noSuchFieldException) {}
/*     */       } 
/*     */ 
/*     */       
/* 142 */       Bukkit.getServicesManager().register(Metrics.class, this, plugin, ServicePriority.Normal);
/* 143 */       if (!found)
/*     */       {
/* 145 */         startSubmitting();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnabled() {
/* 156 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCustomChart(CustomChart chart) {
/* 165 */     if (chart == null) {
/* 166 */       throw new IllegalArgumentException("Chart cannot be null!");
/*     */     }
/* 168 */     this.charts.add(chart);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void startSubmitting() {
/* 175 */     final Timer timer = new Timer(true);
/* 176 */     timer.scheduleAtFixedRate(new TimerTask()
/*     */         {
/*     */           public void run() {
/* 179 */             if (!Metrics.this.plugin.isEnabled()) {
/* 180 */               timer.cancel();
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 185 */             Bukkit.getScheduler().runTask(Metrics.this.plugin, () -> Metrics.this.submitData());
/*     */           }
/*     */         }300000L, 1800000L);
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
/*     */   public JsonObject getPluginData() {
/* 200 */     JsonObject data = new JsonObject();
/*     */     
/* 202 */     String pluginName = this.plugin.getDescription().getName();
/* 203 */     String pluginVersion = this.plugin.getDescription().getVersion();
/*     */     
/* 205 */     data.addProperty("pluginName", pluginName);
/* 206 */     data.addProperty("id", Integer.valueOf(this.pluginId));
/* 207 */     data.addProperty("pluginVersion", pluginVersion);
/* 208 */     JsonArray customCharts = new JsonArray();
/* 209 */     for (CustomChart customChart : this.charts) {
/*     */       
/* 211 */       JsonObject chart = customChart.getRequestJsonObject();
/* 212 */       if (chart == null) {
/*     */         continue;
/*     */       }
/* 215 */       customCharts.add((JsonElement)chart);
/*     */     } 
/* 217 */     data.add("customCharts", (JsonElement)customCharts);
/*     */     
/* 219 */     return data;
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
/*     */   private JsonObject getServerData() {
/*     */     int playerAmount;
/*     */     try {
/* 233 */       Method onlinePlayersMethod = Class.forName("org.bukkit.Server").getMethod("getOnlinePlayers", new Class[0]);
/*     */ 
/*     */       
/* 236 */       playerAmount = onlinePlayersMethod.getReturnType().equals(Collection.class) ? ((Collection)onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).size() : ((Player[])onlinePlayersMethod.invoke(Bukkit.getServer(), new Object[0])).length;
/* 237 */     } catch (Exception e) {
/* 238 */       playerAmount = Bukkit.getOnlinePlayers().size();
/*     */     } 
/* 240 */     int onlineMode = Bukkit.getOnlineMode() ? 1 : 0;
/* 241 */     String bukkitVersion = Bukkit.getVersion();
/* 242 */     String bukkitName = Bukkit.getName();
/*     */ 
/*     */     
/* 245 */     String javaVersion = System.getProperty("java.version");
/* 246 */     String osName = System.getProperty("os.name");
/* 247 */     String osArch = System.getProperty("os.arch");
/* 248 */     String osVersion = System.getProperty("os.version");
/* 249 */     int coreCount = Runtime.getRuntime().availableProcessors();
/*     */     
/* 251 */     JsonObject data = new JsonObject();
/*     */     
/* 253 */     data.addProperty("serverUUID", serverUUID);
/*     */     
/* 255 */     data.addProperty("playerAmount", Integer.valueOf(playerAmount));
/* 256 */     data.addProperty("onlineMode", Integer.valueOf(onlineMode));
/* 257 */     data.addProperty("bukkitVersion", bukkitVersion);
/* 258 */     data.addProperty("bukkitName", bukkitName);
/*     */     
/* 260 */     data.addProperty("javaVersion", javaVersion);
/* 261 */     data.addProperty("osName", osName);
/* 262 */     data.addProperty("osArch", osArch);
/* 263 */     data.addProperty("osVersion", osVersion);
/* 264 */     data.addProperty("coreCount", Integer.valueOf(coreCount));
/*     */     
/* 266 */     return data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void submitData() {
/* 273 */     JsonObject data = getServerData();
/*     */     
/* 275 */     JsonArray pluginData = new JsonArray();
/*     */     
/* 277 */     for (Class<?> service : (Iterable<Class<?>>)Bukkit.getServicesManager().getKnownServices()) {
/*     */       try {
/* 279 */         service.getField("B_STATS_VERSION");
/*     */         
/* 281 */         for (RegisteredServiceProvider<?> provider : (Iterable<RegisteredServiceProvider<?>>)Bukkit.getServicesManager().getRegistrations(service)) {
/*     */           try {
/* 283 */             Object plugin = provider.getService().getMethod("getPluginData", new Class[0]).invoke(provider.getProvider(), new Object[0]);
/* 284 */             if (plugin instanceof JsonObject) {
/* 285 */               pluginData.add((JsonElement)plugin); continue;
/*     */             } 
/*     */             try {
/* 288 */               Class<?> jsonObjectJsonSimple = Class.forName("org.json.simple.JSONObject");
/* 289 */               if (plugin.getClass().isAssignableFrom(jsonObjectJsonSimple)) {
/* 290 */                 Method jsonStringGetter = jsonObjectJsonSimple.getDeclaredMethod("toJSONString", new Class[0]);
/* 291 */                 jsonStringGetter.setAccessible(true);
/* 292 */                 String jsonString = (String)jsonStringGetter.invoke(plugin, new Object[0]);
/* 293 */                 JsonObject object = (new JsonParser()).parse(jsonString).getAsJsonObject();
/* 294 */                 pluginData.add((JsonElement)object);
/*     */               } 
/* 296 */             } catch (ClassNotFoundException e) {
/*     */               
/* 298 */               if (logFailedRequests) {
/* 299 */                 this.plugin.getLogger().log(Level.SEVERE, "Encountered unexpected exception", e);
/*     */               }
/*     */             }
/*     */           
/* 303 */           } catch (NullPointerException|NoSuchMethodException|IllegalAccessException|java.lang.reflect.InvocationTargetException nullPointerException) {}
/*     */         }
/*     */       
/* 306 */       } catch (NoSuchFieldException noSuchFieldException) {}
/*     */     } 
/*     */ 
/*     */     
/* 310 */     data.add("plugins", (JsonElement)pluginData);
/*     */ 
/*     */     
/* 313 */     (new Thread(() -> {
/*     */           
/*     */           try {
/*     */             sendData(this.plugin, data);
/* 317 */           } catch (Exception e) {
/*     */             
/*     */             if (logFailedRequests) {
/*     */               this.plugin.getLogger().log(Level.WARNING, "Could not submit plugin stats of " + this.plugin.getName(), e);
/*     */             }
/*     */           } 
/* 323 */         })).start();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void sendData(Plugin plugin, JsonObject data) throws Exception {
/* 334 */     if (data == null) {
/* 335 */       throw new IllegalArgumentException("Data cannot be null!");
/*     */     }
/* 337 */     if (Bukkit.isPrimaryThread()) {
/* 338 */       throw new IllegalAccessException("This method must not be called from the main thread!");
/*     */     }
/* 340 */     if (logSentData) {
/* 341 */       plugin.getLogger().info("Sending data to bStats: " + data);
/*     */     }
/* 343 */     HttpsURLConnection connection = (HttpsURLConnection)(new URL("https://bStats.org/submitData/bukkit")).openConnection();
/*     */ 
/*     */     
/* 346 */     byte[] compressedData = compress(data.toString());
/*     */ 
/*     */     
/* 349 */     connection.setRequestMethod("POST");
/* 350 */     connection.addRequestProperty("Accept", "application/json");
/* 351 */     connection.addRequestProperty("Connection", "close");
/* 352 */     connection.addRequestProperty("Content-Encoding", "gzip");
/* 353 */     connection.addRequestProperty("Content-Length", String.valueOf(compressedData.length));
/* 354 */     connection.setRequestProperty("Content-Type", "application/json");
/* 355 */     connection.setRequestProperty("User-Agent", "MC-Server/1");
/*     */ 
/*     */     
/* 358 */     connection.setDoOutput(true);
/* 359 */     try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
/* 360 */       outputStream.write(compressedData);
/*     */     } 
/*     */     
/* 363 */     StringBuilder builder = new StringBuilder();
/* 364 */     try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
/*     */       String line;
/* 366 */       while ((line = bufferedReader.readLine()) != null) {
/* 367 */         builder.append(line);
/*     */       }
/*     */     } 
/*     */     
/* 371 */     if (logResponseStatusText) {
/* 372 */       plugin.getLogger().info("Sent data to bStats and received response: " + builder);
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
/*     */   private static byte[] compress(String str) throws IOException {
/* 384 */     if (str == null) {
/* 385 */       return null;
/*     */     }
/* 387 */     ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
/* 388 */     try (GZIPOutputStream gzip = new GZIPOutputStream(outputStream)) {
/* 389 */       gzip.write(str.getBytes(StandardCharsets.UTF_8));
/*     */     } 
/* 391 */     return outputStream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static abstract class CustomChart
/*     */   {
/*     */     final String chartId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     CustomChart(String chartId) {
/* 408 */       if (chartId == null || chartId.isEmpty()) {
/* 409 */         throw new IllegalArgumentException("ChartId cannot be null or empty!");
/*     */       }
/* 411 */       this.chartId = chartId;
/*     */     }
/*     */     
/*     */     private JsonObject getRequestJsonObject() {
/* 415 */       JsonObject chart = new JsonObject();
/* 416 */       chart.addProperty("chartId", this.chartId);
/*     */       try {
/* 418 */         JsonObject data = getChartData();
/* 419 */         if (data == null)
/*     */         {
/* 421 */           return null;
/*     */         }
/* 423 */         chart.add("data", (JsonElement)data);
/* 424 */       } catch (Throwable t) {
/* 425 */         if (Metrics.logFailedRequests) {
/* 426 */           Bukkit.getLogger().log(Level.WARNING, "Failed to get data for custom chart with id " + this.chartId, t);
/*     */         }
/* 428 */         return null;
/*     */       } 
/* 430 */       return chart;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract JsonObject getChartData() throws Exception;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimplePie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<String> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimplePie(String chartId, Callable<String> callable) {
/* 451 */       super(chartId);
/* 452 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 457 */       JsonObject data = new JsonObject();
/* 458 */       String value = this.callable.call();
/* 459 */       if (value == null || value.isEmpty())
/*     */       {
/* 461 */         return null;
/*     */       }
/* 463 */       data.addProperty("value", value);
/* 464 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedPie(String chartId, Callable<Map<String, Integer>> callable) {
/* 482 */       super(chartId);
/* 483 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 488 */       JsonObject data = new JsonObject();
/* 489 */       JsonObject values = new JsonObject();
/* 490 */       Map<String, Integer> map = this.callable.call();
/* 491 */       if (map == null || map.isEmpty())
/*     */       {
/* 493 */         return null;
/*     */       }
/* 495 */       boolean allSkipped = true;
/* 496 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 497 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/* 500 */         allSkipped = false;
/* 501 */         values.addProperty(entry.getKey(), entry.getValue());
/*     */       } 
/* 503 */       if (allSkipped)
/*     */       {
/* 505 */         return null;
/*     */       }
/* 507 */       data.add("values", (JsonElement)values);
/* 508 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class DrilldownPie
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Map<String, Integer>>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public DrilldownPie(String chartId, Callable<Map<String, Map<String, Integer>>> callable) {
/* 526 */       super(chartId);
/* 527 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonObject getChartData() throws Exception {
/* 532 */       JsonObject data = new JsonObject();
/* 533 */       JsonObject values = new JsonObject();
/* 534 */       Map<String, Map<String, Integer>> map = this.callable.call();
/* 535 */       if (map == null || map.isEmpty())
/*     */       {
/* 537 */         return null;
/*     */       }
/* 539 */       boolean reallyAllSkipped = true;
/* 540 */       for (Map.Entry<String, Map<String, Integer>> entryValues : map.entrySet()) {
/* 541 */         JsonObject value = new JsonObject();
/* 542 */         boolean allSkipped = true;
/* 543 */         for (Map.Entry<String, Integer> valueEntry : (Iterable<Map.Entry<String, Integer>>)((Map)map.get(entryValues.getKey())).entrySet()) {
/* 544 */           value.addProperty(valueEntry.getKey(), valueEntry.getValue());
/* 545 */           allSkipped = false;
/*     */         } 
/* 547 */         if (!allSkipped) {
/* 548 */           reallyAllSkipped = false;
/* 549 */           values.add(entryValues.getKey(), (JsonElement)value);
/*     */         } 
/*     */       } 
/* 552 */       if (reallyAllSkipped)
/*     */       {
/* 554 */         return null;
/*     */       }
/* 556 */       data.add("values", (JsonElement)values);
/* 557 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SingleLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Integer> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SingleLineChart(String chartId, Callable<Integer> callable) {
/* 575 */       super(chartId);
/* 576 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 581 */       JsonObject data = new JsonObject();
/* 582 */       int value = ((Integer)this.callable.call()).intValue();
/* 583 */       if (value == 0)
/*     */       {
/* 585 */         return null;
/*     */       }
/* 587 */       data.addProperty("value", Integer.valueOf(value));
/* 588 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class MultiLineChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public MultiLineChart(String chartId, Callable<Map<String, Integer>> callable) {
/* 607 */       super(chartId);
/* 608 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 613 */       JsonObject data = new JsonObject();
/* 614 */       JsonObject values = new JsonObject();
/* 615 */       Map<String, Integer> map = this.callable.call();
/* 616 */       if (map == null || map.isEmpty())
/*     */       {
/* 618 */         return null;
/*     */       }
/* 620 */       boolean allSkipped = true;
/* 621 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 622 */         if (((Integer)entry.getValue()).intValue() == 0) {
/*     */           continue;
/*     */         }
/* 625 */         allSkipped = false;
/* 626 */         values.addProperty(entry.getKey(), entry.getValue());
/*     */       } 
/* 628 */       if (allSkipped)
/*     */       {
/* 630 */         return null;
/*     */       }
/* 632 */       data.add("values", (JsonElement)values);
/* 633 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class SimpleBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, Integer>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SimpleBarChart(String chartId, Callable<Map<String, Integer>> callable) {
/* 652 */       super(chartId);
/* 653 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 658 */       JsonObject data = new JsonObject();
/* 659 */       JsonObject values = new JsonObject();
/* 660 */       Map<String, Integer> map = this.callable.call();
/* 661 */       if (map == null || map.isEmpty())
/*     */       {
/* 663 */         return null;
/*     */       }
/* 665 */       for (Map.Entry<String, Integer> entry : map.entrySet()) {
/* 666 */         JsonArray categoryValues = new JsonArray();
/* 667 */         categoryValues.add((JsonElement)new JsonPrimitive(entry.getValue()));
/* 668 */         values.add(entry.getKey(), (JsonElement)categoryValues);
/*     */       } 
/* 670 */       data.add("values", (JsonElement)values);
/* 671 */       return data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class AdvancedBarChart
/*     */     extends CustomChart
/*     */   {
/*     */     private final Callable<Map<String, int[]>> callable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public AdvancedBarChart(String chartId, Callable<Map<String, int[]>> callable) {
/* 690 */       super(chartId);
/* 691 */       this.callable = callable;
/*     */     }
/*     */ 
/*     */     
/*     */     protected JsonObject getChartData() throws Exception {
/* 696 */       JsonObject data = new JsonObject();
/* 697 */       JsonObject values = new JsonObject();
/* 698 */       Map<String, int[]> map = this.callable.call();
/* 699 */       if (map == null || map.isEmpty())
/*     */       {
/* 701 */         return null;
/*     */       }
/* 703 */       boolean allSkipped = true;
/* 704 */       for (Map.Entry<String, int[]> entry : map.entrySet()) {
/* 705 */         if (((int[])entry.getValue()).length == 0) {
/*     */           continue;
/*     */         }
/* 708 */         allSkipped = false;
/* 709 */         JsonArray categoryValues = new JsonArray();
/* 710 */         for (int categoryValue : (int[])entry.getValue()) {
/* 711 */           categoryValues.add((JsonElement)new JsonPrimitive(Integer.valueOf(categoryValue)));
/*     */         }
/* 713 */         values.add(entry.getKey(), (JsonElement)categoryValues);
/*     */       } 
/* 715 */       if (allSkipped)
/*     */       {
/* 717 */         return null;
/*     */       }
/* 719 */       data.add("values", (JsonElement)values);
/* 720 */       return data;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\org\bstats\bukkit\Metrics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */