/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.LinkedList;
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
/*     */ public class Image2RGB
/*     */   extends Filter
/*     */   implements __Delete
/*     */ {
/*     */   private Thread a;
/*     */   
/*     */   public Image2RGB(Element paramElement) throws PDFNetException {
/*  25 */     super(Image2RGBE(paramElement.a), null);
/*  26 */     clearList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image2RGB(Obj paramObj) throws PDFNetException {
/*  37 */     super(Image2RGBO(paramObj.__GetHandle()), null);
/*  38 */     clearList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Image2RGB(Image paramImage) throws PDFNetException {
/*  49 */     super(Image2RGBI(paramImage.a), null);
/*  50 */     clearList();
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
/*     */   public void destroy() throws PDFNetException {
/*  63 */     super.destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  68 */     if (this.impl != 0L && this.ref == null) {
/*     */ 
/*     */ 
/*     */       
/*  72 */       synchronized (h.b) {
/*     */         
/*  74 */         Object object = h.b.get(this.a);
/*     */       } 
/*  76 */       if (SYNTHETIC_LOCAL_VARIABLE_1 != null)
/*     */       {
/*  78 */         synchronized (SYNTHETIC_LOCAL_VARIABLE_1) {
/*     */           
/*  80 */           ((LinkedList<Image2RGB>)SYNTHETIC_LOCAL_VARIABLE_1).add(this);
/*     */           return;
/*     */         } 
/*     */       }
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
/*     */   protected void clearList() throws PDFNetException {
/*  95 */     this.a = Thread.currentThread();
/*  96 */     synchronized (h.b) {
/*     */       
/*  98 */       Object object = h.b.get(this.a);
/*     */     } 
/* 100 */     if (SYNTHETIC_LOCAL_VARIABLE_1 != null) {
/*     */       
/* 102 */       synchronized (SYNTHETIC_LOCAL_VARIABLE_1)
/*     */       {
/* 104 */         LinkedList<__Delete> linkedList = (LinkedList)SYNTHETIC_LOCAL_VARIABLE_1;
/* 105 */         while (!linkedList.isEmpty())
/*     */         {
/* 107 */           ((__Delete)linkedList.removeFirst()).destroy();
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 113 */       null = new LinkedList();
/* 114 */       synchronized (h.b) {
/*     */         
/* 116 */         h.b.put(this.a, null);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static native long Image2RGBE(long paramLong);
/*     */   
/*     */   static native long Image2RGBO(long paramLong);
/*     */   
/*     */   static native long Image2RGBI(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Image2RGB.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */