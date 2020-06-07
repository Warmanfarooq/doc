/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.filters.Filter;
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
/*     */ public class DocumentPreviewCache
/*     */ {
/*     */   public static void initialize(long paramLong, double paramDouble) {
/*  43 */     Initialize(paramLong, paramDouble);
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
/*     */   public static void getBitmapWithPath(String paramString, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject) {
/*  61 */     GetBitmapWithPath(paramString, paramInt1, paramInt2, paramPreviewHandler, paramObject);
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
/*     */   public static void getBitmapWithID(String paramString, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject) {
/*  80 */     GetBitmapWithID(paramString, paramInt1, paramInt2, paramPreviewHandler, paramObject);
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
/*     */   public static void getBitmapWithID(String paramString, Filter paramFilter, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject) {
/*  99 */     GetBitmapWithIDFilter(paramString, paramFilter.__GetHandle(), paramInt1, paramInt2, paramPreviewHandler, paramObject);
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
/*     */   public static void createBitmapWithID(String paramString, PDFDoc paramPDFDoc, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject) {
/* 120 */     CreateBitmapWithID(paramString, paramPDFDoc.__GetHandle(), paramInt1, paramInt2, paramPreviewHandler, paramObject);
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
/*     */   public static void createBitmapWithID(String paramString, Filter paramFilter, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject) {
/* 140 */     CreateBitmapWithIDFilter(paramString, paramFilter.__GetHandle(), paramInt1, paramInt2, paramPreviewHandler, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cancelAllRequests() {
/* 149 */     CancelAllRequests();
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
/*     */   public static void cancelRequest(String paramString) {
/* 161 */     CancelRequest(paramString);
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
/*     */   public static void irrelevantChangeMade(String paramString) {
/* 173 */     IrrelevantChangeMade(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearCache() {
/* 182 */     ClearCache();
/*     */   }
/*     */   
/*     */   static native void Initialize(long paramLong, double paramDouble);
/*     */   
/*     */   static native void GetBitmapWithPath(String paramString, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject);
/*     */   
/*     */   static native void GetBitmapWithID(String paramString, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject);
/*     */   
/*     */   static native void GetBitmapWithIDFilter(String paramString, long paramLong, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject);
/*     */   
/*     */   static native void CreateBitmapWithID(String paramString, long paramLong, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject);
/*     */   
/*     */   static native void CreateBitmapWithIDFilter(String paramString, long paramLong, int paramInt1, int paramInt2, PreviewHandler paramPreviewHandler, Object paramObject);
/*     */   
/*     */   static native void CancelAllRequests();
/*     */   
/*     */   static native void CancelRequest(String paramString);
/*     */   
/*     */   static native void IrrelevantChangeMade(String paramString);
/*     */   
/*     */   static native void ClearCache();
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DocumentPreviewCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */