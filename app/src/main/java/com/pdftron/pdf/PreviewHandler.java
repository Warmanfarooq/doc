/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import android.os.Handler;
/*     */ import android.os.Message;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.Vector;
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
/*     */ public class PreviewHandler
/*     */ {
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_FAILURE = 0;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_SUCCESS = 1;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_SECURITY_ERROR = 2;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_CANCEL = 3;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_PACKAGE_ERROR = 4;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_PREVIOUS_CRASH = 5;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_NOT_FOUNT = 6;
/*     */   public static final int DOCUMENT_PREVIEW_RESULT_POSTPONED = 9;
/*     */   private PreviewHandlerCallback mListener;
/*  54 */   private final a mThumbHandler = new a(this);
/*     */   
/*     */   public PreviewHandler(PreviewHandlerCallback paramPreviewHandlerCallback) {
/*  57 */     this.mListener = paramPreviewHandlerCallback;
/*     */   }
/*     */   public void removeListener() {
/*  60 */     this.mListener = null;
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
/*     */   private void PreviewHandlerProc(int paramInt, String paramString, Object paramObject) {
/*  72 */     Thread.yield();
/*     */     
/*     */     Message message;
/*  75 */     (message = new Message()).setTarget(this.mThumbHandler);
/*     */     Vector<Integer> vector;
/*  77 */     (vector = new Vector<>()).add(Integer.valueOf(paramInt));
/*  78 */     vector.add(paramString);
/*  79 */     vector.add(paramObject);
/*     */     
/*  81 */     message.obj = vector;
/*  82 */     message.sendToTarget();
/*     */   }
/*     */   
/*     */   static class a extends Handler {
/*     */     private final WeakReference<PreviewHandler> a;
/*     */     
/*     */     public a(PreviewHandler param1PreviewHandler) {
/*  89 */       this.a = new WeakReference<>(param1PreviewHandler);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final void handleMessage(Message param1Message) {
/*     */       PreviewHandler previewHandler;
/*  96 */       if ((previewHandler = this.a.get()) != null && 
/*  97 */         previewHandler.mListener != null) {
/*     */         Vector<Integer> vector;
/*  99 */         int i = ((Integer)(vector = (Vector<Integer>)param1Message.obj).elementAt(0)).intValue();
/* 100 */         String str = (String)vector.elementAt(1);
/* 101 */         vector = (Vector<Integer>)vector.elementAt(2);
/*     */         
/* 103 */         previewHandler.mListener.PreviewHandlerProc(i, str, vector);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface PreviewHandlerCallback {
/*     */     void PreviewHandlerProc(int param1Int, String param1String, Object param1Object);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PreviewHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */