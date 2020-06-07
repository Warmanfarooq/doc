/*    */ package com.pdftron.pdfnet;
/*    */ 
/*    */ import android.content.ContentProvider;
/*    */ import android.content.ContentValues;
/*    */ import android.content.Context;
/*    */ import android.content.pm.ApplicationInfo;
/*    */ import android.database.Cursor;
/*    */ import android.net.Uri;
/*    */ import android.os.Bundle;
/*    */ import android.util.Log;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.PDFNet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PDFNetInitializer
/*    */   extends ContentProvider
/*    */ {
/*    */   private static final String TAG = "PDFNetInitializer";
/*    */   public static final String MSG = "Tried to auto-initialize PDFTron SDK but no license key was found. Please add your license as described in this article: https://www.pdftron.com/documentation/android/guides/getting-started/add-license.";
/*    */   
/*    */   public boolean onCreate() {
/* 30 */     Context applicationContext = getContext();
/* 31 */     String key = getLicenseKey(applicationContext);
/* 32 */     if (key != null && applicationContext != null) {
/*    */       try {
/* 34 */         PDFNet.initialize(applicationContext, R.raw.pdfnet, key);
/* 35 */       } catch (PDFNetException e) {
/* 36 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/* 39 */       Log.w("PDFNetInitializer", "Tried to auto-initialize PDFTron SDK but no license key was found. Please add your license as described in this article: https://www.pdftron.com/documentation/android/guides/getting-started/add-license.");
/*    */     } 
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
/* 46 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType(Uri uri) {
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Uri insert(Uri uri, ContentValues values) {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int delete(Uri uri, String selection, String[] selectionArgs) {
/* 61 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
/* 66 */     return 0;
/*    */   }
/*    */   
/*    */   public static String getLicenseKey(Context applicationContext) {
/*    */     try {
/* 71 */       ApplicationInfo ai = applicationContext.getPackageManager().getApplicationInfo(applicationContext
/* 72 */           .getPackageName(), 128);
/*    */       
/* 74 */       Bundle bundle = ai.metaData;
/* 75 */       return bundle.getString("pdftron_license_key");
/* 76 */     } catch (Exception exception) {
/*    */ 
/*    */       
/* 79 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\classes.jar!\com\pdftron\pdfnet\PDFNetInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */