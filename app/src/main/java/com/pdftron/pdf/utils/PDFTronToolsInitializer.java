/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.content.ContentProvider;
/*    */ import android.content.ContentValues;
/*    */ import android.content.Context;
/*    */ import android.database.Cursor;
/*    */ import android.net.Uri;
/*    */ import android.util.Log;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.annotation.RestrictTo;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdfnet.PDFNetInitializer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*    */ public class PDFTronToolsInitializer
/*    */   extends ContentProvider
/*    */ {
/*    */   private static final String TAG = "PDFTronToolsInitializer";
/*    */   
/*    */   public boolean onCreate() {
/* 27 */     Context applicationContext = getContext();
/* 28 */     String key = PDFNetInitializer.getLicenseKey(applicationContext);
/* 29 */     if (key != null && applicationContext != null) {
/*    */       try {
/* 31 */         AppUtils.initializePDFNetApplication(applicationContext, key);
/* 32 */       } catch (PDFNetException e) {
/* 33 */         e.printStackTrace();
/*    */       } 
/*    */     } else {
/* 36 */       Log.w("PDFTronToolsInitializer", "Tried to auto-initialize PDFTron SDK but no license key was found. Please add your license as described in this article: https://www.pdftron.com/documentation/android/guides/getting-started/add-license.");
/*    */     } 
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
/* 44 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getType(@NonNull Uri uri) {
/* 50 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
/* 61 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
/* 66 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PDFTronToolsInitializer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */