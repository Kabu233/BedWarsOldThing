/*     */ package cn.rmc.bedwarslobby.util;
/*     */ import com.google.common.base.Charsets;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.io.Writer;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.configuration.ConfigurationOptions;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.MemoryConfigurationOptions;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.yaml.snakeyaml.DumperOptions;
/*     */ import org.yaml.snakeyaml.Yaml;
/*     */ import org.yaml.snakeyaml.representer.Representer;
/*     */ 
/*     */ public class Config extends YamlConfiguration {
/*     */   public Config(File file) {
/*  25 */     this.file = file;
/*     */     try {
/*  27 */       load(file);
/*  28 */     } catch (IOException|InvalidConfigurationException e) {
/*  29 */       Bukkit.getLogger().log(Level.SEVERE, "Could not load Configuration " + file.getName(), e);
/*     */     } 
/*     */   }
/*     */   File file;
/*     */   
/*     */   public void save(File file) throws IOException {
/*  35 */     Validate.notNull(file, "File can't be null");
/*  36 */     Files.createParentDirs(file);
/*  37 */     String data = saveToString();
/*  38 */     Writer writer = new OutputStreamWriter(new FileOutputStream(file), Charsets.UTF_8);
/*     */ 
/*     */     
/*  41 */     try { writer.write(data); }
/*  42 */     finally { writer.close(); }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   public String saveToString() {
/*     */     try {
/*  49 */       Field optionField = Reflections.getField(getClass(), "yamlOptions");
/*  50 */       Field representerField = Reflections.getField(getClass(), "yamlRepresenter");
/*  51 */       Field yamlField = Reflections.getField(getClass(), "yaml");
/*     */       
/*  53 */       if (optionField != null && representerField != null && yamlField != null) {
/*  54 */         optionField.setAccessible(true);
/*  55 */         representerField.setAccessible(true);
/*  56 */         yamlField.setAccessible(true);
/*     */         
/*  58 */         DumperOptions yamlOptions = (DumperOptions)optionField.get(this);
/*  59 */         Representer yamlRepresenter = (Representer)representerField.get(this);
/*  60 */         Yaml yaml = (Yaml)yamlField.get(this);
/*  61 */         DumperOptions.FlowStyle flow = DumperOptions.FlowStyle.BLOCK;
/*     */         
/*  63 */         yamlOptions.setIndent(options().indent());
/*  64 */         yamlOptions.setDefaultFlowStyle(flow);
/*  65 */         yamlOptions.setAllowUnicode(true);
/*  66 */         yamlRepresenter.setDefaultFlowStyle(flow);
/*     */         
/*  68 */         String header = buildHeader();
/*  69 */         String dump = yaml.dump(getValues(false));
/*     */         
/*  71 */         if (dump.equals("{}\n")) dump = ""; 
/*  72 */         return header + dump;
/*     */       } 
/*  74 */     } catch (Exception e) {
/*  75 */       Bukkit.getLogger().log(Level.SEVERE, "Error in converting Configuration to String", e);
/*     */     } 
/*  77 */     return "Error: Cannot be saved to String";
/*     */   }
/*     */   
/*     */   public void load(File file) throws IOException, InvalidConfigurationException {
/*  81 */     Validate.notNull(file, "File can't be null");
/*  82 */     load(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8));
/*     */   }
/*     */   
/*     */   public static class Reflections
/*     */   {
/*  87 */     public static final Field modifiers = getField(Field.class, "modifiers");
/*     */     
/*     */     public Reflections() {
/*  90 */       setAccessible(true, new Field[] { modifiers });
/*     */     }
/*     */     
/*     */     public Class<?> getNMSClass(String name) {
/*  94 */       String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */       try {
/*  96 */         return Class.forName("net.minecraft.server." + version + "." + name);
/*  97 */       } catch (Exception e) {
/*  98 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public Class<?> getBukkitClass(String name) {
/*     */       try {
/* 104 */         String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/* 105 */         return Class.forName("org.bukkit.craftbukkit." + version + "." + name);
/* 106 */       } catch (Exception ex) {
/* 107 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     public void sendPacket(Player to, Object packet) {
/*     */       try {
/* 113 */         Object playerHandle = to.getClass().getMethod("getHandle", new Class[0]).invoke(to, new Object[0]);
/* 114 */         Object playerConnection = playerHandle.getClass().getField("playerConnection").get(playerHandle);
/* 115 */         playerConnection.getClass().getMethod("sendPacket", new Class[] { getNMSClass("Packet") }).invoke(playerConnection, new Object[] { packet });
/* 116 */       } catch (Exception e) {
/* 117 */         Bukkit.getLogger().log(Level.INFO, "Could not send Packet to Player " + to.getName(), e);
/*     */       } 
/*     */     }
/*     */     
/*     */     public void setField(Object change, String name, Object to) {
/*     */       try {
/* 123 */         Field field = getField(change.getClass(), name);
/* 124 */         if (field != null) {
/* 125 */           setAccessible(true, new Field[] { field });
/* 126 */           field.set(change, to);
/* 127 */           setAccessible(false, new Field[] { field });
/*     */         } 
/* 129 */       } catch (Exception ex) {
/* 130 */         Bukkit.getLogger().log(Level.SEVERE, "Could not set Value " + to.getClass().getName() + " in Field " + name + " in Class " + change.getClass().getName(), ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static void setAccessible(boolean state, Field... fields) {
/*     */       try {
/* 136 */         for (Field field : fields) {
/* 137 */           field.setAccessible(state);
/* 138 */           if (Modifier.isFinal(field.getModifiers())) {
/* 139 */             field.setAccessible(true);
/* 140 */             modifiers.set(field, Integer.valueOf(field.getModifiers() & 0xFFFFFFEF));
/*     */           } 
/*     */         } 
/* 143 */       } catch (Exception ex) {
/* 144 */         Bukkit.getLogger().log(Level.WARNING, "Could not set Fields accessible", ex);
/*     */       } 
/*     */     }
/*     */     
/*     */     public static Field getField(Class<?> clazz, String name) {
/* 149 */       Field field = null;
/* 150 */       for (Field f : getFields(clazz)) {
/* 151 */         if (f.getName().equals(name)) field = f; 
/*     */       } 
/* 153 */       return field;
/*     */     }
/*     */     
/*     */     public static List<Field> getFields(Class<?> clazz) {
/* 157 */       List<Field> buf = new ArrayList<>();
/*     */       
/*     */       do {
/*     */         try {
/* 161 */           for (Field f : clazz.getDeclaredFields())
/* 162 */             buf.add(f); 
/* 163 */         } catch (Exception exception) {}
/* 164 */       } while ((clazz = clazz.getSuperclass()) != null);
/*     */       
/* 166 */       return buf;
/*     */     }
/*     */     
/*     */     public String getVersion() {
/* 170 */       return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */     }
/*     */   }
/*     */ }