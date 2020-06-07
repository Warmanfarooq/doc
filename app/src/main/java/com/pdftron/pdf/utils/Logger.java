/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.util.Log;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum Logger
/*    */ {
/* 10 */   INSTANCE;
/*    */   
/*    */   private boolean mDebug;
/*    */   private boolean mAllowLoggingInReleaseMode;
/*    */   
/*    */   Logger() {
/* 16 */     this.mAllowLoggingInReleaseMode = false;
/*    */   }
/*    */   
/*    */   public void setDebug(boolean debug) {
/* 20 */     this.mDebug = debug;
/*    */   }
/*    */   
/*    */   public void LogV(String tag, String message) {
/* 24 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 25 */       tag != null && message != null) {
/* 26 */       Log.v(tag, message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogD(String tag, String message) {
/* 32 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 33 */       tag != null && message != null) {
/* 34 */       Log.d(tag, message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogI(String tag, String message) {
/* 40 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 41 */       tag != null && message != null) {
/* 42 */       Log.i(tag, message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogW(String tag, String message) {
/* 48 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 49 */       tag != null && message != null) {
/* 50 */       Log.w(tag, message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogW(String tag, String message, Throwable tr) {
/* 56 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 57 */       tag != null && message != null) {
/* 58 */       Log.w(tag, message, tr);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogE(String tag, String message) {
/* 64 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 65 */       tag != null && message != null) {
/* 66 */       Log.e(tag, message);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void LogE(String tag, Exception exception) {
/* 72 */     if (this.mDebug || this.mAllowLoggingInReleaseMode) {
/* 73 */       exception.printStackTrace();
/* 74 */       if (tag != null && exception.getMessage() != null) {
/* 75 */         Log.e(tag, exception.getMessage());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void LogE(String tag, String message, Throwable tr) {
/* 81 */     if ((this.mDebug || this.mAllowLoggingInReleaseMode) && 
/* 82 */       tag != null && message != null)
/* 83 */       Log.e(tag, message, tr); 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\Logger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */