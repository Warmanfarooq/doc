/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WebFontDownloader
/*    */ {
/*    */   public static boolean isAvailable() throws PDFNetException {
/* 35 */     return IsAvailable();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void enableDownloads() throws PDFNetException {
/* 45 */     EnableDownloads();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void disableDownloads() throws PDFNetException {
/* 55 */     DisableDownloads();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void preCacheAsync() throws PDFNetException {
/* 65 */     PreCacheAsync();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void clearCache() throws PDFNetException {
/* 75 */     ClearCache();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setCustomWebFontURL(String paramString) throws PDFNetException {
/* 86 */     SetCustomWebFontURL(paramString);
/*    */   }
/*    */   
/*    */   static native boolean IsAvailable();
/*    */   
/*    */   static native void EnableDownloads();
/*    */   
/*    */   static native void DisableDownloads();
/*    */   
/*    */   static native void PreCacheAsync();
/*    */   
/*    */   static native void ClearCache();
/*    */   
/*    */   static native void SetCustomWebFontURL(String paramString);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\WebFontDownloader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */