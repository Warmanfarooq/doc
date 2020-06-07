/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Type;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.json.JSONObject;
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
/*     */ public class StandardStampOption
/*     */   extends CustomStampOption
/*     */ {
/*     */   private static final String FILE_STANDARD_STAMP_INFO = "com_pdftron_pdf_model_file_standard_stamp";
/*     */   
/*     */   public StandardStampOption(@NonNull String text, @Nullable String secondText, int bgColorStart, int bgColorEnd, int textColor, int borderColor, double fillOpacity, boolean isPointingLeft, boolean isPointingRight) {
/*  37 */     super(text, secondText, bgColorStart, bgColorEnd, textColor, borderColor, fillOpacity, isPointingLeft, isPointingRight);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized boolean checkStandardStamp(@Nullable Context context, @NonNull String name) {
/*  48 */     if (context == null) {
/*  49 */       return false;
/*     */     }
/*  51 */     String path = getStandardStampBitmapPath(context, name);
/*  52 */     return (!Utils.isNullOrEmpty(path) && getStandardStampObj(context, name) != null);
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
/*     */   @Nullable
/*     */   public static synchronized Bitmap getStandardStampBitmap(@Nullable Context context, @NonNull String name) {
/*  65 */     if (context == null) {
/*  66 */       return null;
/*     */     }
/*  68 */     String path = getStandardStampBitmapPath(context, name);
/*  69 */     return BitmapFactory.decodeFile(path);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static synchronized Obj getStandardStampObj(@NonNull Context context, @NonNull String name) {
/*  81 */     if (Utils.isNullOrEmpty(name)) {
/*  82 */       return null;
/*     */     }
/*     */     
/*  85 */     FileInputStream fis = null;
/*     */     try {
/*  87 */       fis = new FileInputStream(getStandardStampInfoPath(context, name));
/*  88 */       String info = IOUtils.toString(fis);
/*  89 */       JSONObject jsonObject = new JSONObject(info);
/*  90 */       CustomStampOption customStampOption = new CustomStampOption(jsonObject);
/*  91 */       return convertToObj(customStampOption);
/*  92 */     } catch (Exception e) {
/*  93 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/*  95 */       Utils.closeQuietly(fis);
/*     */     } 
/*     */     
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void saveStandardStamp(@Nullable Context context, @NonNull String name, @NonNull CustomStampOption stampOption, @NonNull Bitmap bitmap) {
/* 109 */     if (context == null || Utils.isNullOrEmpty(name)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 114 */     FileOutputStream fos = null;
/*     */     try {
/* 116 */       String path = getStandardStampBitmapPath(context, name);
/* 117 */       fos = new FileOutputStream(path);
/* 118 */       bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
/* 119 */     } catch (Exception e) {
/* 120 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 122 */       Utils.closeQuietly(fos);
/*     */     } 
/*     */     
/* 125 */     saveStandardStamp(context, name, stampOption);
/*     */   }
/*     */   
/*     */   private static void saveStandardStamp(@NonNull Context context, @NonNull String name, @NonNull CustomStampOption stampOption) {
/* 129 */     Gson gson = new Gson();
/*     */     
/* 131 */     Type collectionType = (new TypeToken<CustomStampOption>() {  }).getType();
/* 132 */     String serializedData = gson.toJson(stampOption, collectionType);
/* 133 */     FileOutputStream fos = null;
/*     */     try {
/* 135 */       fos = new FileOutputStream(getStandardStampInfoPath(context, name));
/* 136 */       IOUtils.write(serializedData, fos);
/* 137 */     } catch (IOException e) {
/* 138 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 140 */       Utils.closeQuietly(fos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getStandardStampBitmapPath(@NonNull Context context, @NonNull String name) {
/* 145 */     return context.getFilesDir().getAbsolutePath() + File.separator + "standard_stamp_bitmap_" + name + ".png";
/*     */   }
/*     */   
/*     */   private static String getStandardStampInfoPath(@NonNull Context context, @NonNull String name) {
/* 149 */     return context.getFilesDir().getAbsolutePath() + File.separator + "com_pdftron_pdf_model_file_standard_stamp" + name;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\StandardStampOption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */