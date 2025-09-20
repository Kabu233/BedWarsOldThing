/*     */ package com.comphenix.tinyprotocol;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.Arrays;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Bukkit;
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
/*     */ public final class Reflection
/*     */ {
/*  79 */   private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
/*  80 */   private static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
/*  81 */   private static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
/*     */ 
/*     */   
/*  84 */   private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
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
/*     */ 
/*     */   
/*     */   public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType) {
/*  99 */     return getField(target, name, fieldType, 0);
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
/*     */   public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType) {
/* 111 */     return getField(getClass(className), name, fieldType, 0);
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
/*     */   public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index) {
/* 123 */     return getField(target, null, fieldType, index);
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
/*     */   public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index) {
/* 135 */     return getField(getClass(className), fieldType, index);
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index) {
/* 140 */     for (Field field : target.getDeclaredFields()) {
/* 141 */       if ((name == null || field.getName().equals(name)) && fieldType.isAssignableFrom(field.getType()) && index-- <= 0) {
/* 142 */         field.setAccessible(true);
/*     */ 
/*     */         
/* 145 */         return new FieldAccessor<T>()
/*     */           {
/*     */             
/*     */             public T get(Object target)
/*     */             {
/*     */               try {
/* 151 */                 return (T)field.get(target);
/* 152 */               } catch (IllegalAccessException e) {
/* 153 */                 throw new RuntimeException("Cannot access reflection.", e);
/*     */               } 
/*     */             }
/*     */ 
/*     */             
/*     */             public void set(Object target, Object value) {
/*     */               try {
/* 160 */                 field.set(target, value);
/* 161 */               } catch (IllegalAccessException e) {
/* 162 */                 throw new RuntimeException("Cannot access reflection.", e);
/*     */               } 
/*     */             }
/*     */ 
/*     */ 
/*     */             
/*     */             public boolean hasField(Object target) {
/* 169 */               return field.getDeclaringClass().isAssignableFrom(target.getClass());
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 176 */     if (target.getSuperclass() != null) {
/* 177 */       return getField(target.getSuperclass(), name, fieldType, index);
/*     */     }
/* 179 */     throw new IllegalArgumentException("Cannot find field with type " + fieldType);
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
/*     */   
/*     */   public static Field getParameterizedField(Class<?> target, Class<?> fieldType, Class<?>... params) {
/* 194 */     for (Field field : target.getDeclaredFields()) {
/* 195 */       if (field.getType().equals(fieldType)) {
/* 196 */         Type type = field.getGenericType();
/* 197 */         if (type instanceof ParameterizedType && 
/* 198 */           Arrays.equals((Object[])((ParameterizedType)type).getActualTypeArguments(), (Object[])params)) {
/* 199 */           return field;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 204 */     throw new IllegalArgumentException("Unable to find a field with type " + fieldType + " and params " + Arrays.toString(params));
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
/*     */   public static MethodInvoker getMethod(String className, String methodName, Class<?>... params) {
/* 217 */     return getTypedMethod(getClass(className), methodName, null, params);
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
/*     */   public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params) {
/* 230 */     return getTypedMethod(clazz, methodName, null, params);
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
/*     */   public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params) {
/* 244 */     for (Method method : clazz.getDeclaredMethods()) {
/* 245 */       if ((methodName == null || method.getName().equals(methodName)) && (returnType == null || method
/* 246 */         .getReturnType().equals(returnType)) && 
/* 247 */         Arrays.equals((Object[])method.getParameterTypes(), (Object[])params)) {
/* 248 */         method.setAccessible(true);
/*     */         
/* 250 */         return new MethodInvoker()
/*     */           {
/*     */             public Object invoke(Object target, Object... arguments)
/*     */             {
/*     */               try {
/* 255 */                 return method.invoke(target, arguments);
/* 256 */               } catch (Exception e) {
/* 257 */                 throw new RuntimeException("Cannot invoke method " + method, e);
/*     */               } 
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 266 */     if (clazz.getSuperclass() != null) {
/* 267 */       return getMethod(clazz.getSuperclass(), methodName, params);
/*     */     }
/* 269 */     throw new IllegalStateException(String.format("Unable to find method %s (%s).", new Object[] { methodName, Arrays.asList(params) }));
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
/*     */   public static ConstructorInvoker getConstructor(String className, Class<?>... params) {
/* 281 */     return getConstructor(getClass(className), params);
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
/*     */   public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params) {
/* 293 */     for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
/* 294 */       if (Arrays.equals((Object[])constructor.getParameterTypes(), (Object[])params)) {
/* 295 */         constructor.setAccessible(true);
/*     */         
/* 297 */         return new ConstructorInvoker()
/*     */           {
/*     */             public Object invoke(Object... arguments)
/*     */             {
/*     */               try {
/* 302 */                 return constructor.newInstance(arguments);
/* 303 */               } catch (Exception e) {
/* 304 */                 throw new RuntimeException("Cannot invoke constructor " + constructor, e);
/*     */               } 
/*     */             }
/*     */           };
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 312 */     throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", new Object[] { clazz, Arrays.asList(params) }));
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
/*     */   
/*     */   public static Class<Object> getUntypedClass(String lookupName) {
/* 327 */     Class<Object> clazz = (Class)getClass(lookupName);
/* 328 */     return clazz;
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
/*     */ 
/*     */   
/*     */   public static Class<Object> getUntypedClass(String lookupName, String... aliases) {
/* 344 */     Class<Object> clazz = (Class)getClass(lookupName, aliases);
/* 345 */     return clazz;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getClass(String lookupName) {
/* 377 */     return getCanonicalClass(expandVariables(lookupName));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getClass(String lookupName, String... aliases) {
/*     */     try {
/* 412 */       return getClass(lookupName);
/* 413 */     } catch (RuntimeException e) {
/* 414 */       Class<?> success = null;
/*     */ 
/*     */       
/* 417 */       for (String alias : aliases) {
/*     */         try {
/* 419 */           success = getClass(alias);
/*     */           break;
/* 421 */         } catch (RuntimeException runtimeException) {}
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 426 */       if (success != null) {
/* 427 */         return success;
/*     */       }
/*     */       
/* 430 */       throw new RuntimeException(String.format("Unable to find %s (%s)", new Object[] { lookupName, String.join(",", aliases) }));
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
/*     */   public static Class<?> getMinecraftClass(String name) {
/* 442 */     return getCanonicalClass(NMS_PREFIX + "." + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Class<?> getCraftBukkitClass(String name) {
/* 452 */     return getCanonicalClass(OBC_PREFIX + "." + name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> getCanonicalClass(String canonicalName) {
/*     */     try {
/* 463 */       return Class.forName(canonicalName);
/* 464 */     } catch (ClassNotFoundException e) {
/* 465 */       throw new IllegalArgumentException("Cannot find " + canonicalName, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String expandVariables(String name) {
/* 476 */     StringBuffer output = new StringBuffer();
/* 477 */     Matcher matcher = MATCH_VARIABLE.matcher(name);
/*     */     
/* 479 */     while (matcher.find()) {
/* 480 */       String variable = matcher.group(1);
/* 481 */       String replacement = "";
/*     */ 
/*     */       
/* 484 */       if ("nms".equalsIgnoreCase(variable)) {
/* 485 */         replacement = NMS_PREFIX;
/* 486 */       } else if ("obc".equalsIgnoreCase(variable)) {
/* 487 */         replacement = OBC_PREFIX;
/* 488 */       } else if ("version".equalsIgnoreCase(variable)) {
/* 489 */         replacement = VERSION;
/*     */       } else {
/* 491 */         throw new IllegalArgumentException("Unknown variable: " + variable);
/*     */       } 
/*     */       
/* 494 */       if (replacement.length() > 0 && matcher.end() < name.length() && name.charAt(matcher.end()) != '.')
/* 495 */         replacement = replacement + "."; 
/* 496 */       matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
/*     */     } 
/*     */     
/* 499 */     matcher.appendTail(output);
/* 500 */     return output.toString();
/*     */   }
/*     */   
/*     */   public static interface ConstructorInvoker {
/*     */     Object invoke(Object... param1VarArgs);
/*     */   }
/*     */   
/*     */   public static interface MethodInvoker {
/*     */     Object invoke(Object param1Object, Object... param1VarArgs);
/*     */   }
/*     */   
/*     */   public static interface FieldAccessor<T> {
/*     */     T get(Object param1Object);
/*     */     
/*     */     void set(Object param1Object1, Object param1Object2);
/*     */     
/*     */     boolean hasField(Object param1Object);
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\BedWarsLobby-1.0-SNAPSHOT.jar!\com\comphenix\tinyprotocol\Reflection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */