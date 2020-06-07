/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.graphics.Path;
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
/*     */ public class PathPool
/*     */ {
/*     */   public static final int length = 4096;
/*  20 */   private final Object mLock = new Object();
/*  21 */   private Path[] mPool = new Path[4096];
/*     */   private int mSize;
/*     */   
/*     */   private static class LazyHolder {
/*  25 */     private static final PathPool INSTANCE = new PathPool();
/*     */   }
/*     */   
/*     */   public static PathPool getInstance() {
/*  29 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Path obtain() {
/*  40 */     synchronized (this.mLock) {
/*  41 */       if (this.mSize > 0) {
/*  42 */         int index = this.mSize - 1;
/*  43 */         Path path = this.mPool[index];
/*  44 */         this.mPool[index] = null;
/*  45 */         this.mSize--;
/*  46 */         if (path == null) {
/*  47 */           path = new Path();
/*     */         }
/*  49 */         return path;
/*     */       } 
/*     */     } 
/*  52 */     return new Path();
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
/*     */   public void recycle(Path path) {
/*  64 */     if (path == null) {
/*     */       return;
/*     */     }
/*  67 */     path.reset();
/*     */     
/*  69 */     synchronized (this.mLock) {
/*  70 */       if (this.mPool == null) {
/*  71 */         this.mPool = new Path[4096];
/*     */       }
/*  73 */       if (this.mSize < 4096) {
/*  74 */         this.mPool[this.mSize] = path;
/*  75 */         this.mSize++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void recycle(@NonNull List<Path> paths) {
/*  86 */     int count = 4096 - this.mSize;
/*  87 */     if (count > 0) {
/*  88 */       if (paths.size() < count) {
/*  89 */         count = paths.size();
/*     */       }
/*  91 */       for (int i = 0; i < count; i++) {
/*  92 */         recycle(paths.get(i));
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
/* 103 */     if (count > 4096 - this.mSize) {
/* 104 */       count = 4096 - this.mSize;
/*     */     }
/* 106 */     for (int i = 0; i < count; i++) {
/* 107 */       recycle(new Path());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 115 */     synchronized (this.mLock) {
/* 116 */       this.mPool = null;
/* 117 */       this.mSize = 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   private PathPool() {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PathPool.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */