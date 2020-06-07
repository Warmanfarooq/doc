/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import android.os.Handler;
/*     */ import android.os.Message;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Vector;
/*     */ 
/*     */ 
/*     */ public class RequestHandler
/*     */ {
/*     */   public static final int JOB_REQUEST_RESULT_FAILURE = 0;
/*     */   public static final int JOB_REQUEST_RESULT_SUCCESS = 1;
/*     */   public static final int JOB_REQUEST_RESULT_SECURITY_ERROR = 2;
/*     */   public static final int JOB_REQUEST_RESULT_CANCEL = 3;
/*     */   public static final int JOB_REQUEST_RESULT_PACKAGE_ERROR = 4;
/*     */   public static final int JOB_REQUEST_RESULT_PREVIOUS_CRASH = 5;
/*     */   public static final int JOB_REQUEST_RESULT_POSTPONED = 9;
/*     */   private RequestHandlerCallback mListener;
/*     */   
/*     */   public enum JobRequestResult
/*     */   {
/*  24 */     FINISHED(1),
/*     */ 
/*     */ 
/*     */     
/*  28 */     CANCELLED(3),
/*     */ 
/*     */ 
/*     */     
/*  32 */     POSTPONED(9),
/*     */ 
/*     */ 
/*     */     
/*  36 */     FAILED(0),
/*     */ 
/*     */ 
/*     */     
/*  40 */     SECURITY_ERROR(2),
/*     */ 
/*     */ 
/*     */     
/*  44 */     PACKAGE_ERROR(4),
/*     */ 
/*     */ 
/*     */     
/*  48 */     PREVIOUS_CRASH(5);
/*     */     
/*     */     private final int a;
/*  51 */     private static Map<Integer, JobRequestResult> b = new HashMap<>(7);
/*     */     
/*     */     static
/*     */     {
/*     */       JobRequestResult[] arrayOfJobRequestResult;
/*     */       int i;
/*     */       byte b;
/*  58 */       for (i = (arrayOfJobRequestResult = values()).length, b = 0; b < i; ) { JobRequestResult jobRequestResult = arrayOfJobRequestResult[b];
/*  59 */         b.put(Integer.valueOf(jobRequestResult.a), jobRequestResult);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     public final int getValue() {
/*  64 */       return this.a;
/*     */     }
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
/*     */     JobRequestResult(int param1Int1) {
/*     */       this.a = param1Int1;
/*     */     }
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
/*     */ 
/*     */ 
/*     */   
/* 113 */   private final a mJobRequestHandler = new a(this);
/*     */   
/*     */   public RequestHandler(RequestHandlerCallback paramRequestHandlerCallback) {
/* 116 */     this.mListener = paramRequestHandlerCallback;
/*     */   }
/*     */   public void removeListener() {
/* 119 */     this.mListener = null;
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
/*     */   private void RequestHandlerProc(int paramInt, String paramString, Object paramObject) {
/* 131 */     Thread.yield();
/*     */     
/*     */     Message message;
/* 134 */     (message = new Message()).setTarget(this.mJobRequestHandler);
/*     */     Vector<Integer> vector;
/* 136 */     (vector = new Vector<>()).add(Integer.valueOf(paramInt));
/* 137 */     vector.add(paramString);
/* 138 */     vector.add(paramObject);
/*     */     
/* 140 */     message.obj = vector;
/* 141 */     message.sendToTarget();
/*     */   }
/*     */   
/*     */   public static interface RequestHandlerCallback {
/*     */     void RequestHandlerProc(JobRequestResult param1JobRequestResult, String param1String, Object param1Object); }
/*     */   
/*     */   static class a extends Handler { public a(RequestHandler param1RequestHandler) {
/* 148 */       this.a = new WeakReference<>(param1RequestHandler);
/*     */     }
/*     */     
/*     */     private final WeakReference<RequestHandler> a;
/*     */     
/*     */     public final void handleMessage(Message param1Message) {
/*     */       RequestHandler requestHandler;
/* 155 */       if ((requestHandler = this.a.get()) != null && 
/* 156 */         requestHandler.mListener != null) {
/*     */         Vector<Integer> vector;
/* 158 */         int i = ((Integer)(vector = (Vector<Integer>)param1Message.obj).elementAt(0)).intValue();
/* 159 */         String str = (String)vector.elementAt(1);
/* 160 */         vector = (Vector<Integer>)vector.elementAt(2);
/*     */         
/* 162 */         requestHandler.mListener.RequestHandlerProc(JobRequestResult.valueOf(i), str, vector);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\RequestHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */