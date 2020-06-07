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
/*     */ public class Image2RGBA
/*     */   extends Filter
/*     */   implements __Delete
/*     */ {
/*     */   private Thread a;
/*     */   
/*     */   public Image2RGBA(Element paramElement) throws PDFNetException {
/*  25 */     super(Image2RGBAE(paramElement.a, false), null);
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
/*     */   public Image2RGBA(Obj paramObj) throws PDFNetException {
/*  37 */     super(Image2RGBAO(paramObj.__GetHandle(), false), null);
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
/*     */   public Image2RGBA(Image paramImage) throws PDFNetException {
/*  49 */     super(Image2RGBAI(paramImage.a, false), null);
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
/*     */   public Image2RGBA(Element paramElement, boolean paramBoolean) throws PDFNetException {
/*  62 */     super(Image2RGBAE(paramElement.a, paramBoolean), null);
/*  63 */     clearList();
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
/*     */   public Image2RGBA(Obj paramObj, boolean paramBoolean) throws PDFNetException {
/*  75 */     super(Image2RGBAO(paramObj.__GetHandle(), paramBoolean), null);
/*  76 */     clearList();
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
/*     */   public Image2RGBA(Image paramImage, boolean paramBoolean) throws PDFNetException {
/*  88 */     super(Image2RGBAI(paramImage.a, paramBoolean), null);
/*  89 */     clearList();
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
/* 102 */     super.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 110 */     if (this.impl != 0L && this.ref == null) {
/*     */ 
/*     */ 
/*     */       
/* 114 */       synchronized (h.b) {
/*     */         
/* 116 */         Object object = h.b.get(this.a);
/*     */       } 
/* 118 */       if (SYNTHETIC_LOCAL_VARIABLE_1 != null)
/*     */       {
/* 120 */         synchronized (SYNTHETIC_LOCAL_VARIABLE_1) {
/*     */           
/* 122 */           ((LinkedList<Image2RGBA>)SYNTHETIC_LOCAL_VARIABLE_1).add(this);
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
/*     */   protected void clearList() throws PDFNetException {
/* 136 */     this.a = Thread.currentThread();
/* 137 */     synchronized (h.b) {
/*     */       
/* 139 */       Object object = h.b.get(this.a);
/*     */     } 
/* 141 */     if (SYNTHETIC_LOCAL_VARIABLE_1 != null) {
/*     */       
/* 143 */       synchronized (SYNTHETIC_LOCAL_VARIABLE_1)
/*     */       {
/* 145 */         LinkedList<__Delete> linkedList = (LinkedList)SYNTHETIC_LOCAL_VARIABLE_1;
/* 146 */         while (!linkedList.isEmpty())
/*     */         {
/* 148 */           ((__Delete)linkedList.removeFirst()).destroy();
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 154 */       null = new LinkedList();
/* 155 */       synchronized (h.b) {
/*     */         
/* 157 */         h.b.put(this.a, null);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   static native long Image2RGBAE(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native long Image2RGBAO(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native long Image2RGBAI(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Image2RGBA.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */