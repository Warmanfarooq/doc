/*     */ package com.pdftron.common;
/*     */ 
/*     */ import com.pdftron.pdf.PDFDoc;
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
/*     */ public class RecentlyUsedCache
/*     */ {
/*     */   public static void removeDocument(String paramString) {
/*  30 */     RemoveDocument(paramString);
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
/*     */   public static void renameDocument(String paramString1, String paramString2) {
/*  42 */     RenameDocument(paramString1, paramString2);
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
/*     */   public static void accessDocument(String paramString) {
/*  55 */     AccessDocument(paramString);
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
/*     */   public static void accessDocument(String paramString, PDFDoc paramPDFDoc) {
/*  69 */     AccessDocument(paramString, paramPDFDoc.__GetHandle());
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
/*     */   public static String getBitmapPathIfExists(String paramString) {
/*  84 */     return GetBitmapPathIfExists(paramString);
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
/*     */   public static void resetCache() {
/*  96 */     ResetCache();
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
/*     */   public static void initializeRecentlyUsedCache(long paramLong1, long paramLong2, double paramDouble) throws PDFNetException {
/* 110 */     InitializeRecentlyUsedCache(paramLong1, paramLong2, paramDouble);
/*     */   }
/*     */   
/*     */   static native void RemoveDocument(String paramString);
/*     */   
/*     */   static native void RenameDocument(String paramString1, String paramString2);
/*     */   
/*     */   static native void AccessDocument(String paramString);
/*     */   
/*     */   static native void AccessDocument(String paramString, long paramLong);
/*     */   
/*     */   static native String GetBitmapPathIfExists(String paramString);
/*     */   
/*     */   static native void ResetCache();
/*     */   
/*     */   static native void InitializeRecentlyUsedCache(long paramLong1, long paramLong2, double paramDouble);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\common\RecentlyUsedCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */