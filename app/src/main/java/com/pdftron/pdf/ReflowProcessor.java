/*     */ package com.pdftron.pdf;
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
/*     */ public class ReflowProcessor
/*     */ {
/*     */   public static void initialize() {
/*  28 */     Initialize();
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
/*     */   public static void initialize(String paramString1, String paramString2) {
/*  40 */     Initialize();
/*  41 */     SetNoReflowContent(paramString1);
/*  42 */     SetReflowFailedContent(paramString2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isInitialized() {
/*  52 */     return IsInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cancelAllRequests() {
/*  60 */     CancelAllRequests();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cancelRequest(Page paramPage) {
/*  71 */     CancelRequest(paramPage.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearCache() {
/*  79 */     ClearCache();
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
/*     */   public static void getReflow(Page paramPage, RequestHandler paramRequestHandler, Object paramObject) {
/*  92 */     GetReflow(paramPage.__GetHandle(), paramRequestHandler, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setNoReflowContent(String paramString) {
/* 103 */     SetNoReflowContent(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setReflowFailedContent(String paramString) {
/* 114 */     SetReflowFailedContent(paramString);
/*     */   }
/*     */   
/*     */   static native void Initialize();
/*     */   
/*     */   static native boolean IsInitialized();
/*     */   
/*     */   static native void CancelAllRequests();
/*     */   
/*     */   static native void CancelRequest(long paramLong);
/*     */   
/*     */   static native void ClearCache();
/*     */   
/*     */   static native void GetReflow(long paramLong, RequestHandler paramRequestHandler, Object paramObject);
/*     */   
/*     */   static native void SetNoReflowContent(String paramString);
/*     */   
/*     */   static native void SetReflowFailedContent(String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ReflowProcessor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */