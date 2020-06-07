/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ public class ActionParameter
/*     */ {
/*     */   private Action b;
/*     */   long a;
/*     */   
/*     */   public ActionParameter(Action paramAction, Field paramField) throws PDFNetException {
/*  21 */     this.a = ActionParameterCreateWithField(paramAction.a, paramField.a);
/*  22 */     this.b = paramAction;
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
/*     */   public ActionParameter(Action paramAction, Annot paramAnnot) throws PDFNetException {
/*  35 */     this.a = ActionParameterCreateWithAnnot(paramAction.a, paramAnnot.a);
/*  36 */     this.b = paramAction;
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
/*     */   public ActionParameter(Action paramAction, Page paramPage) throws PDFNetException {
/*  49 */     this.a = ActionParameterCreateWithPage(paramAction.a, paramPage.a);
/*  50 */     this.b = paramAction;
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
/*     */   public ActionParameter(Action paramAction) throws PDFNetException {
/*  62 */     this.a = ActionParameterCreate(paramAction.a);
/*  63 */     this.b = paramAction;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Action getAction() throws PDFNetException {
/*  73 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  78 */     if (this.a != 0L) {
/*     */       
/*  80 */       Destroy(this.a);
/*  81 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  89 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   private ActionParameter(long paramLong) {
/*  94 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   public static ActionParameter __Create(long paramLong) {
/*  98 */     return new ActionParameter(paramLong);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 102 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long ActionParameterCreateWithField(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long ActionParameterCreateWithAnnot(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long ActionParameterCreateWithPage(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long ActionParameterCreate(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ActionParameter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */