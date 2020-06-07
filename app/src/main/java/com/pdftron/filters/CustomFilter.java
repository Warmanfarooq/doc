/*     */ package com.pdftron.filters;
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
/*     */ public abstract class CustomFilter
/*     */   extends Filter
/*     */ {
/*     */   public static final String TAG = "save CustomFilter";
/*     */   private byte[] a;
/*     */   public static final int READ_MODE = 0;
/*     */   public static final int WRITE_MODE = 1;
/*     */   public static final int APPEND_MODE = 2;
/*     */   public static final int SEEK_SET = 0;
/*     */   public static final int SEEK_CUR = 1;
/*     */   public static final int SEEK_END = 2;
/*     */   private Object b;
/*     */   protected long callback_data;
/*     */   
/*     */   public CustomFilter(int paramInt, Object paramObject) throws PDFNetException {
/*  34 */     super(0L, null);
/*  35 */     if (paramInt < 0 || paramInt > 2)
/*     */     {
/*  37 */       throw new PDFNetException("false", 31L, "CustomFilter.java", "CustomFilter()", "Filter mode is incorrect.");
/*     */     }
/*     */     
/*  40 */     this.a = null;
/*  41 */     this.b = paramObject;
/*  42 */     long[] arrayOfLong = CustomFilterCreate(new CustomFilterCallback(), paramInt);
/*  43 */     this.impl = arrayOfLong[0];
/*  44 */     this.callback_data = arrayOfLong[1];
/*     */   }
/*     */ 
/*     */   
/*     */   protected CustomFilter(long paramLong, Filter paramFilter) {
/*  49 */     super(paramLong, paramFilter);
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
/*     */   public long onTruncate(long paramLong, Object paramObject) {
/* 145 */     return -1L;
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
/*     */   public Object getUserObject() {
/* 185 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract long onRead(byte[] paramArrayOfbyte, Object paramObject);
/*     */   
/*     */   public abstract long onSeek(long paramLong, int paramInt, Object paramObject);
/*     */   
/*     */   public abstract long onTell(Object paramObject);
/*     */   
/*     */   public String getName() throws PDFNetException {
/* 196 */     return new String("CustomFilter");
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract long onFlush(Object paramObject);
/*     */ 
/*     */   
/*     */   public abstract long onWrite(byte[] paramArrayOfbyte, Object paramObject);
/*     */   
/*     */   public abstract long onCreateInputIterator(Object paramObject);
/*     */   
/*     */   public void destroy() throws PDFNetException {
/* 208 */     if (this.attached == null && this.impl != 0L && this.ref == null) {
/*     */       
/* 210 */       Destroy(this.impl);
/* 211 */       this.impl = 0L;
/*     */     } 
/*     */     
/* 214 */     if (this.attached == null && this.callback_data != 0L && this.ref == null) {
/*     */       
/* 216 */       DestroyCallbackData(this.callback_data);
/* 217 */       this.callback_data = 0L;
/*     */     } 
/*     */   } public abstract void onDestroy(Object paramObject);
/*     */   public abstract void close();
/*     */   static native void Destroy(long paramLong);
/*     */   protected void finalize() throws Throwable {
/* 223 */     destroy();
/*     */   }
/*     */ 
/*     */   
/*     */   static native void DestroyCallbackData(long paramLong);
/*     */ 
/*     */   
/*     */   static native long[] CustomFilterCreate(CustomFilterCallback paramCustomFilterCallback, int paramInt);
/*     */ 
/*     */   
/*     */   static native void AfterRead(long paramLong1, byte[] paramArrayOfbyte, long paramLong2, long paramLong3);
/*     */ 
/*     */   
/*     */   class CustomFilterCallback
/*     */   {
/*     */     private long Read(long param1Long1, long param1Long2, long param1Long3) {
/* 239 */       int i = (int)(param1Long1 * param1Long2);
/* 240 */       if (CustomFilter.a(CustomFilter.this) == null || (CustomFilter.a(CustomFilter.this)).length < i)
/*     */       {
/* 242 */         CustomFilter.a(CustomFilter.this, new byte[i]);
/*     */       }
/* 244 */       long l = CustomFilter.this.onRead(CustomFilter.a(CustomFilter.this), CustomFilter.b(CustomFilter.this));
/* 245 */       CustomFilter.a(CustomFilter.this, CustomFilter.a(CustomFilter.this), l, param1Long3);
/* 246 */       return l;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private long Seek(long param1Long, int param1Int) {
/*     */       long l;
/* 253 */       return l = CustomFilter.this.onSeek(param1Long, param1Int, CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private long Tell() {
/*     */       long l;
/* 260 */       return l = CustomFilter.this.onTell(CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */     
/*     */     private long Truncate(long param1Long) {
/*     */       long l;
/* 266 */       return l = CustomFilter.this.onTruncate(param1Long, CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private long Flush() {
/*     */       long l;
/* 273 */       return l = CustomFilter.this.onFlush(CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private long Write(byte[] param1ArrayOfbyte) {
/*     */       long l;
/* 280 */       return l = CustomFilter.this.onWrite(param1ArrayOfbyte, CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */     
/*     */     private long CreateInputIterator() {
/*     */       long l;
/* 286 */       return l = CustomFilter.this.onCreateInputIterator(CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */ 
/*     */     
/*     */     private void Destroy() {
/* 291 */       CustomFilter.this.onDestroy(CustomFilter.b(CustomFilter.this));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\CustomFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */