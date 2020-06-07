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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PageSet
/*     */ {
/*     */   public static final int e_all = 0;
/*     */   public static final int e_odd = 1;
/*     */   public static final int e_even = 2;
/*     */   long a;
/*     */   
/*     */   public PageSet() {
/*  32 */     this.a = PageSetCreate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PageSet(int paramInt) {
/*  43 */     this.a = PageSetCreate(paramInt);
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
/*     */   public PageSet(int paramInt1, int paramInt2) {
/*  55 */     this.a = PageSetCreate(paramInt1, paramInt2);
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
/*     */   public PageSet(int paramInt1, int paramInt2, int paramInt3) {
/*  68 */     this.a = PageSetCreate(paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  79 */     if (this.a != 0L) {
/*  80 */       Destroy(this.a);
/*  81 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPage(int paramInt) {
/*  91 */     AddPage(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRange(int paramInt1, int paramInt2) {
/* 101 */     AddRange(this.a, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRange(int paramInt1, int paramInt2, int paramInt3) {
/* 112 */     AddRange(this.a, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 116 */     return this.a;
/*     */   }
/*     */   
/*     */   static native long PageSetCreate();
/*     */   
/*     */   static native long PageSetCreate(int paramInt);
/*     */   
/*     */   static native long PageSetCreate(int paramInt1, int paramInt2);
/*     */   
/*     */   static native long PageSetCreate(int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void AddPage(long paramLong, int paramInt);
/*     */   
/*     */   static native void AddRange(long paramLong, int paramInt1, int paramInt2);
/*     */   
/*     */   static native void AddRange(long paramLong, int paramInt1, int paramInt2, int paramInt3);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PageSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */