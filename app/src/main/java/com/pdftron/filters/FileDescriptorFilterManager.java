/*     */ package com.pdftron.filters;
/*     */ 
/*     */ import android.util.Log;
/*     */ import android.util.SparseArray;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
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
/*     */ public class FileDescriptorFilterManager
/*     */ {
/*     */   static class a
/*     */   {
/*     */     private static a a;
/*     */     private AtomicInteger b;
/*     */     
/*     */     public static a a() {
/*  28 */       if (a == null) {
/*  29 */         a = new a();
/*     */       }
/*  31 */       return a;
/*     */     }
/*     */     
/*     */     public a() {
/*  35 */       this.b = new AtomicInteger();
/*     */     }
/*     */     
/*     */     public final int b() {
/*  39 */       return this.b.incrementAndGet();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean a = false;
/*     */ 
/*     */   
/*     */   private SparseArray<CustomFilter> b;
/*     */ 
/*     */   
/*  51 */   private final Lock c = new ReentrantLock();
/*     */   
/*     */   public FileDescriptorFilterManager() {
/*  54 */     this.b = new SparseArray();
/*     */   }
/*     */   
/*     */   public boolean acquireLock() {
/*  58 */     Log.d("SaveFilterManager", "acquireLock");
/*  59 */     this.c.lock();
/*  60 */     this.a = true;
/*  61 */     return true;
/*     */   }
/*     */   
/*     */   public void releaseLock() {
/*  65 */     if (this.a) {
/*  66 */       Log.d("SaveFilterManager", "releaseLock");
/*  67 */       this.a = false;
/*  68 */       this.c.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getNewSequenceNumber() {
/*  73 */     synchronized (this) {
/*  74 */       return a.a(a.a()).incrementAndGet();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addReadFilter(CustomFilter paramCustomFilter) {
/*  79 */     synchronized (this) {
/*  80 */       a(paramCustomFilter);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   public void removeReadFilter(CustomFilter paramCustomFilter) {
/*  85 */     synchronized (this) {
/*  86 */       b(paramCustomFilter);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addReadWriteFilter(CustomFilter paramCustomFilter) {
/*  92 */     synchronized (this) {
/*  93 */       a(paramCustomFilter);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void removeReadWriteFilter(CustomFilter paramCustomFilter) {
/*  99 */     synchronized (this) {
/* 100 */       b(paramCustomFilter);
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cleanup() {
/* 106 */     for (byte b = 0; b < this.b.size(); b++) {
/* 107 */       CustomFilter customFilter = (CustomFilter)this.b.valueAt(b);
/* 108 */       FileChannel fileChannel = null;
/* 109 */       int i = -1;
/* 110 */       if (customFilter instanceof FileDescriptorFilter) {
/* 111 */         fileChannel = ((FileDescriptorFilter)customFilter).mFileChannel;
/* 112 */         i = ((FileDescriptorFilter)customFilter).mMySequenceNumber;
/* 113 */       } else if (customFilter instanceof FileDescriptorReadOnlyFilter) {
/* 114 */         fileChannel = ((FileDescriptorReadOnlyFilter)customFilter).mFileChannel;
/* 115 */         i = ((FileDescriptorReadOnlyFilter)customFilter).mMySequenceNumber;
/*     */       } 
/* 117 */       if (fileChannel != null) {
/*     */         try {
/* 119 */           if (fileChannel.isOpen()) {
/* 120 */             fileChannel.close();
/* 121 */             Log.d("SaveFilterManager", i + ": FileDescriptorFilter close FileChannel");
/*     */           } 
/* 123 */         } catch (Exception exception) {
/* 124 */           (customFilter = null).printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void a(CustomFilter paramCustomFilter) {
/* 131 */     synchronized (this) {
/* 132 */       int i = -1;
/* 133 */       if (paramCustomFilter instanceof FileDescriptorFilter) {
/* 134 */         i = ((FileDescriptorFilter)paramCustomFilter).mMySequenceNumber;
/* 135 */       } else if (paramCustomFilter instanceof FileDescriptorReadOnlyFilter) {
/* 136 */         i = ((FileDescriptorReadOnlyFilter)paramCustomFilter).mMySequenceNumber;
/*     */       } 
/* 138 */       if (i >= 0)
/* 139 */         this.b.put(i, paramCustomFilter); 
/*     */       return;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(CustomFilter paramCustomFilter) {
/* 145 */     FileChannel fileChannel = null;
/* 146 */     int i = 0;
/* 147 */     if (paramCustomFilter instanceof FileDescriptorFilter) {
/* 148 */       fileChannel = ((FileDescriptorFilter)paramCustomFilter).mFileChannel;
/* 149 */       i = ((FileDescriptorFilter)paramCustomFilter).mMySequenceNumber;
/* 150 */     } else if (paramCustomFilter instanceof FileDescriptorReadOnlyFilter) {
/* 151 */       fileChannel = ((FileDescriptorReadOnlyFilter)paramCustomFilter).mFileChannel;
/* 152 */       i = ((FileDescriptorReadOnlyFilter)paramCustomFilter).mMySequenceNumber;
/*     */     } 
/* 154 */     if (fileChannel != null)
/* 155 */       synchronized (this) {
/*     */         try {
/* 157 */           if (fileChannel.isOpen()) {
/* 158 */             fileChannel.close();
/* 159 */             Log.d("SaveFilterManager", i + ": FileDescriptorFilter close FileChannel");
/*     */           } 
/* 161 */         } catch (Exception exception) {
/* 162 */           (fileChannel = null).printStackTrace();
/*     */         } 
/* 164 */         this.b.remove(i);
/*     */         return;
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\FileDescriptorFilterManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */