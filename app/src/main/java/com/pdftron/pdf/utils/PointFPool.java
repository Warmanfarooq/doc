/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.graphics.PointF;
/*     */ import androidx.annotation.NonNull;
/*     */ import java.util.List;
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
/*     */ public class PointFPool
/*     */ {
/*     */   public static final int length = 4096;
/*  20 */   private final Object mLock = new Object();
/*  21 */   private PointF[] mPool = new PointF[4096];
/*     */   private int mSize;
/*     */   
/*     */   private static class LazyHolder {
/*  25 */     private static final PointFPool INSTANCE = new PointFPool();
/*     */   }
/*     */   
/*     */   public static PointFPool getInstance() {
/*  29 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointF obtain() {
/*  36 */     return obtain(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PointF obtain(float x, float y) {
/*  43 */     synchronized (this.mLock) {
/*  44 */       if (this.mSize > 0) {
/*  45 */         int index = this.mSize - 1;
/*  46 */         PointF point = this.mPool[index];
/*  47 */         this.mPool[index] = null;
/*  48 */         this.mSize--;
/*  49 */         if (point == null) {
/*  50 */           point = new PointF();
/*     */         }
/*  52 */         point.set(x, y);
/*  53 */         return point;
/*     */       } 
/*     */     } 
/*  56 */     return new PointF(x, y);
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
/*     */   public void recycle(PointF point) {
/*  68 */     if (point == null) {
/*     */       return;
/*     */     }
/*     */     
/*  72 */     synchronized (this.mLock) {
/*  73 */       if (this.mPool == null) {
/*  74 */         this.mPool = new PointF[4096];
/*     */       }
/*  76 */       if (this.mSize < 4096) {
/*  77 */         this.mPool[this.mSize] = point;
/*  78 */         this.mSize++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recycle(@NonNull List<PointF> points) {
/*  89 */     int count = 4096 - this.mSize;
/*  90 */     if (count > 0) {
/*  91 */       if (points.size() < count) {
/*  92 */         count = points.size();
/*     */       }
/*  94 */       for (int i = 0; i < count; i++) {
/*  95 */         recycle(points.get(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recycle(int count) {
/* 106 */     if (count > 4096 - this.mSize) {
/* 107 */       count = 4096 - this.mSize;
/*     */     }
/* 109 */     for (int i = 0; i < count; i++) {
/* 110 */       recycle(new PointF());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 118 */     synchronized (this.mLock) {
/* 119 */       this.mPool = null;
/* 120 */       this.mSize = 0;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PointFPool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */