/*     */ package com.pdftron.pdf.config;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.Log;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class PDFNetConfig
/*     */ {
/*  30 */   private static final String TAG = PDFNetConfig.class.getName();
/*     */   
/*     */   private static final String LAYOUT_PLUGIN_NAME = "pdftron_layout_resources.plugin";
/*     */   private static final String LAYOUT_SMART_PLUGIN_NAME = "pdftron_smart_substitution.plugin";
/*     */   private ArrayList<File> extraResourcePaths;
/*     */   
/*     */   public PDFNetConfig() {}
/*     */   
/*     */   public PDFNetConfig(Context context, int xmlRes) {
/*     */     try {
/*  40 */       HashMap<String, String> results = Utils.parseDefaults(context, xmlRes);
/*  41 */       for (Map.Entry<String, String> entry : results.entrySet()) {
/*  42 */         String key = entry.getKey();
/*  43 */         String value = entry.getValue();
/*  44 */         if (key.equals("javaScriptEnabled")) {
/*  45 */           this.javaScriptEnabled = Boolean.parseBoolean(value);
/*     */         }
/*  47 */         if (key.equals("diskCachingEnabled")) {
/*  48 */           this.diskCachingEnabled = Boolean.parseBoolean(value);
/*     */         }
/*  50 */         if (key.equals("persistentCachePath")) {
/*  51 */           File file = new File(value);
/*  52 */           if (file.exists()) {
/*  53 */             this.persistentCachePath = value;
/*     */           }
/*     */         } 
/*  56 */         if (key.equals("extraResourcePaths")) {
/*  57 */           File file = new File(value);
/*  58 */           if (file.exists()) {
/*  59 */             addExtraResourcePaths(file);
/*     */           }
/*     */         } 
/*  62 */         if (key.equals("tempPath")) {
/*  63 */           File file = new File(value);
/*  64 */           if (file.exists()) {
/*  65 */             this.tempPath = value;
/*     */           }
/*     */         } 
/*  68 */         if (key.equals("viewerCacheMaxSize")) {
/*  69 */           this.viewerCacheMaxSize = Integer.parseInt(value);
/*     */         }
/*  71 */         if (key.equals("viewerCacheOnDisk")) {
/*  72 */           this.viewerCacheOnDisk = Boolean.parseBoolean(value);
/*     */         }
/*     */       } 
/*  75 */     } catch (Exception ex) {
/*  76 */       ex.printStackTrace();
/*  77 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<File> getExtraResourcePaths() {
/*  86 */     return this.extraResourcePaths;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addExtraResourcePaths(File extraResourcePath) {
/*  94 */     if (this.extraResourcePaths == null) {
/*  95 */       this.extraResourcePaths = new ArrayList<>();
/*     */     }
/*     */     
/*  98 */     this.extraResourcePaths.add(extraResourcePath);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isJavaScriptEnabled() {
/* 105 */     return this.javaScriptEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setJavaScriptEnabled(boolean javaScriptEnabled) {
/* 112 */     this.javaScriptEnabled = javaScriptEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPersistentCachePath() {
/* 119 */     return this.persistentCachePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPersistentCachePath(String persistentCachePath) {
/* 126 */     this.persistentCachePath = persistentCachePath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTempPath() {
/* 133 */     return this.tempPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTempPath(String tempPath) {
/* 140 */     this.tempPath = tempPath;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDiskCachingEnabled() {
/* 147 */     return this.diskCachingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDiskCachingEnabled(boolean diskCachingEnabled) {
/* 154 */     this.diskCachingEnabled = diskCachingEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewerCacheMaxSize() {
/* 161 */     return this.viewerCacheMaxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewerCacheMaxSize(int viewerCacheMaxSize) {
/* 168 */     this.viewerCacheMaxSize = viewerCacheMaxSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isViewerCacheOnDisk() {
/* 175 */     return this.viewerCacheOnDisk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setViewerCacheOnDisk(boolean viewerCacheOnDisk) {
/* 182 */     this.viewerCacheOnDisk = viewerCacheOnDisk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLayoutPluginPath(Context applicationContext) {
/*     */     try {
/* 192 */       return Utils.copyResourceToTempFolder(applicationContext, R.raw.pdftron_layout_resources, false, "pdftron_layout_resources.plugin");
/*     */     }
/* 194 */     catch (Exception e) {
/* 195 */       Log.e(TAG, e.getMessage());
/*     */       
/* 197 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLayoutSmartPluginPath(Context applicationContext) {
/*     */     try {
/* 207 */       Utils.copyResourceToTempFolder(applicationContext, R.raw.pdftron_smart_substitution, false, "pdftron_smart_substitution.plugin");
/*     */     }
/* 209 */     catch (Exception e) {
/* 210 */       Log.e(TAG, e.getMessage());
/*     */     } 
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean javaScriptEnabled = true;
/*     */   
/*     */   private boolean diskCachingEnabled = true;
/*     */   
/*     */   private String persistentCachePath;
/*     */   
/*     */   private String tempPath;
/* 223 */   private int viewerCacheMaxSize = 104857600;
/*     */ 
/*     */   
/*     */   private boolean viewerCacheOnDisk = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFNetConfig getDefaultConfig() {
/* 231 */     return new PDFNetConfig();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFNetConfig loadFromXML(Context applicationContext, int xmlRes) {
/* 241 */     return new PDFNetConfig(applicationContext, xmlRes);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\PDFNetConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */