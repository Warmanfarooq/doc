/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.res.Resources;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.BitmapFactory;
/*     */ import android.graphics.drawable.BitmapDrawable;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.collection.LruCache;
/*     */ import java.io.FileDescriptor;
/*     */ import java.lang.ref.SoftReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
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
/*     */ 
/*     */ public class ImageMemoryCache
/*     */ {
/*  31 */   private static final String TAG = ImageMemoryCache.class.getName();
/*     */   
/*     */   private static boolean sDebug;
/*     */   
/*     */   private static final int DEFAULT_MEM_CACHE_SIZE = 4096;
/*  36 */   private final HashMap<Key, List<SoftReference<Bitmap>>> mReusableBitmaps = new HashMap<>(16);
/*     */   private boolean mActive = true;
/*     */   private LruCache<String, BitmapDrawable> mMemCache;
/*     */   
/*     */   private static class LazyHolder
/*     */   {
/*  42 */     private static final ImageMemoryCache INSTANCE = new ImageMemoryCache();
/*     */   }
/*     */   
/*     */   public static ImageMemoryCache getInstance() {
/*  46 */     return LazyHolder.INSTANCE;
/*     */   }
/*     */   
/*     */   private ImageMemoryCache() {
/*  50 */     init(4096);
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
/*     */   public void setMemCacheSizePercent(float percent) {
/*  63 */     if (percent < 0.01F || percent > 0.8F) {
/*  64 */       throw new IllegalArgumentException("setMemCacheSizePercent - percent must be between 0.01 and 0.8 (inclusive)");
/*     */     }
/*     */     
/*  67 */     init(Math.round(percent * (float)Runtime.getRuntime().maxMemory() / 1024.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMemCacheSize(int memCacheSize) {
/*  76 */     init(memCacheSize);
/*     */   }
/*     */   
/*     */   private void init(int memCacheSize) {
/*  80 */     this.mMemCache = new LruCache<String, BitmapDrawable>(memCacheSize)
/*     */       {
/*     */         protected void entryRemoved(boolean evicted, String key, BitmapDrawable oldValue, BitmapDrawable newValue) {
/*  83 */           ImageMemoryCache.this.addBitmapToReusableSet(oldValue.getBitmap());
/*     */         }
/*     */ 
/*     */         
/*     */         protected int sizeOf(String key, BitmapDrawable value) {
/*  88 */           Bitmap bitmap = value.getBitmap();
/*  89 */           if (bitmap == null || bitmap.isRecycled()) {
/*  90 */             return 1;
/*     */           }
/*     */ 
/*     */           
/*  94 */           int bitmapSize = ImageMemoryCache.getBitmapSize(value) / 1024;
/*  95 */           return (bitmapSize == 0) ? 1 : bitmapSize;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BitmapDrawable getBitmapFromCache(String key) {
/* 108 */     return (BitmapDrawable)this.mMemCache.get(key);
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
/*     */   public Bitmap getBitmapFromReusableSet(int width, int height, Bitmap.Config config) {
/* 123 */     if (!this.mActive || width <= 0 || height <= 0 || this.mReusableBitmaps.isEmpty()) {
/* 124 */       return null;
/*     */     }
/*     */     
/* 127 */     Key key = new Key(width, height);
/* 128 */     synchronized (this.mReusableBitmaps) {
/* 129 */       List<SoftReference<Bitmap>> bitmaps = this.mReusableBitmaps.get(key);
/* 130 */       if (bitmaps == null || bitmaps.isEmpty()) {
/* 131 */         return null;
/*     */       }
/*     */       
/* 134 */       Iterator<SoftReference<Bitmap>> iterator = bitmaps.iterator();
/*     */ 
/*     */       
/* 137 */       while (iterator.hasNext()) {
/* 138 */         Bitmap item = ((SoftReference<Bitmap>)iterator.next()).get();
/*     */         
/* 140 */         if (item != null && item.isMutable()) {
/* 141 */           if (config == item.getConfig()) {
/*     */             
/* 143 */             iterator.remove();
/* 144 */             if (sDebug)
/* 145 */               Log.v(TAG, "a bitmap can be reused with width " + item.getWidth() + " and height " + item.getHeight()); 
/* 146 */             return item;
/*     */           } 
/*     */           continue;
/*     */         } 
/* 150 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 155 */     return null;
/*     */   }
/*     */   
/*     */   private Bitmap getInBitmapFromReusableSet(BitmapFactory.Options targetOptions) {
/* 159 */     if (!this.mActive || this.mReusableBitmaps.isEmpty()) {
/* 160 */       return null;
/*     */     }
/*     */     
/* 163 */     Bitmap bitmap = null;
/*     */     
/* 165 */     synchronized (this.mReusableBitmaps) {
/* 166 */       if (targetOptions.inSampleSize == 1) {
/* 167 */         bitmap = getBitmapFromReusableSet(targetOptions.outWidth, targetOptions.outHeight, targetOptions.inPreferredConfig);
/* 168 */         if (bitmap != null) {
/* 169 */           return bitmap;
/*     */         }
/*     */       } 
/*     */       
/* 173 */       for (Key key : this.mReusableBitmaps.keySet()) {
/* 174 */         List<SoftReference<Bitmap>> bitmaps = this.mReusableBitmaps.get(key);
/* 175 */         if (bitmaps == null || bitmaps.isEmpty()) {
/*     */           continue;
/*     */         }
/*     */         
/* 179 */         Iterator<SoftReference<Bitmap>> iterator = bitmaps.iterator();
/* 180 */         while (iterator.hasNext()) {
/* 181 */           Bitmap item = ((SoftReference<Bitmap>)iterator.next()).get();
/*     */           
/* 183 */           if (item != null && item.isMutable()) {
/*     */             
/* 185 */             if (canUseForInBitmap(item, targetOptions)) {
/* 186 */               bitmap = item;
/*     */ 
/*     */               
/* 189 */               iterator.remove();
/*     */             } 
/*     */ 
/*     */             
/*     */             break;
/*     */           } 
/*     */ 
/*     */           
/* 197 */           iterator.remove();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 203 */     return bitmap;
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
/*     */   public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, float downSampleFactor) {
/* 220 */     BitmapFactory.Options options = new BitmapFactory.Options();
/* 221 */     options.inJustDecodeBounds = true;
/* 222 */     BitmapFactory.decodeResource(res, resId, options);
/* 223 */     int height = options.outHeight;
/* 224 */     int width = options.outWidth;
/* 225 */     return decodeSampledBitmapFromResource(res, resId, (int)(height * downSampleFactor), (int)(width * downSampleFactor));
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
/*     */   public Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
/* 243 */     BitmapFactory.Options options = new BitmapFactory.Options();
/* 244 */     options.inJustDecodeBounds = true;
/* 245 */     BitmapFactory.decodeResource(res, resId, options);
/*     */ 
/*     */     
/* 248 */     if (reqWidth != 0 && reqHeight != 0) {
/* 249 */       options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
/*     */     }
/*     */ 
/*     */     
/* 253 */     options.inJustDecodeBounds = false;
/*     */     
/* 255 */     addInBitmapOptions(options);
/*     */     
/* 257 */     return BitmapFactory.decodeResource(res, resId, options);
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
/*     */   public Bitmap decodeSampledBitmapFromFile(String filename, int reqWidth, int reqHeight) {
/* 273 */     BitmapFactory.Options options = new BitmapFactory.Options();
/* 274 */     options.inJustDecodeBounds = true;
/* 275 */     BitmapFactory.decodeFile(filename, options);
/*     */ 
/*     */     
/* 278 */     if (reqWidth != 0 && reqHeight != 0) {
/* 279 */       options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
/*     */     }
/*     */ 
/*     */     
/* 283 */     options.inJustDecodeBounds = false;
/*     */     
/* 285 */     addInBitmapOptions(options);
/*     */     
/* 287 */     return BitmapFactory.decodeFile(filename, options);
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
/*     */   public Bitmap decodeSampledBitmapFromDescriptor(FileDescriptor fileDescriptor, int reqWidth, int reqHeight) {
/* 303 */     BitmapFactory.Options options = new BitmapFactory.Options();
/* 304 */     options.inJustDecodeBounds = true;
/* 305 */     BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
/*     */ 
/*     */     
/* 308 */     options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
/*     */ 
/*     */     
/* 311 */     options.inJustDecodeBounds = false;
/*     */     
/* 313 */     addInBitmapOptions(options);
/*     */     
/* 315 */     return BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addInBitmapOptions(BitmapFactory.Options options) {
/* 321 */     options.inMutable = true;
/*     */ 
/*     */     
/* 324 */     Bitmap inBitmap = getInBitmapFromReusableSet(options);
/*     */     
/* 326 */     if (inBitmap != null) {
/* 327 */       options.inBitmap = inBitmap;
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
/*     */   private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
/* 347 */     int height = options.outHeight;
/* 348 */     int width = options.outWidth;
/* 349 */     int inSampleSize = 1;
/*     */     
/* 351 */     if (height > reqHeight || width > reqWidth) {
/*     */       
/* 353 */       int halfHeight = height / 2;
/* 354 */       int halfWidth = width / 2;
/*     */ 
/*     */ 
/*     */       
/* 358 */       while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth)
/*     */       {
/* 360 */         inSampleSize *= 2;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 369 */       long totalPixels = (width * height / inSampleSize);
/*     */ 
/*     */       
/* 372 */       long totalReqPixelsCap = (reqWidth * reqHeight * 2);
/*     */       
/* 374 */       while (totalPixels > totalReqPixelsCap) {
/* 375 */         inSampleSize *= 2;
/* 376 */         totalPixels /= 2L;
/*     */       } 
/*     */     } 
/* 379 */     return inSampleSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(19)
/*     */   private static boolean canUseForInBitmap(Bitmap candidate, BitmapFactory.Options targetOptions) {
/* 391 */     if (!Utils.isKitKat())
/*     */     {
/* 393 */       return (candidate.getWidth() == targetOptions.outWidth && candidate
/* 394 */         .getHeight() == targetOptions.outHeight && targetOptions.inSampleSize == 1);
/*     */     }
/*     */ 
/*     */     
/* 398 */     if (targetOptions.inSampleSize == 0) {
/* 399 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 404 */     int width = targetOptions.outWidth / targetOptions.inSampleSize;
/* 405 */     int height = targetOptions.outHeight / targetOptions.inSampleSize;
/* 406 */     int byteCount = width * height * getBytesPerPixel(candidate.getConfig());
/* 407 */     return (byteCount <= candidate.getAllocationByteCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getBytesPerPixel(Bitmap.Config config) {
/* 417 */     if (config == Bitmap.Config.ARGB_8888)
/* 418 */       return 4; 
/* 419 */     if (config == Bitmap.Config.RGB_565)
/* 420 */       return 2; 
/* 421 */     if (config == Bitmap.Config.ARGB_4444)
/* 422 */       return 2; 
/* 423 */     if (config == Bitmap.Config.ALPHA_8) {
/* 424 */       return 1;
/*     */     }
/* 426 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBitmapToCache(String key, BitmapDrawable bitmap) {
/* 437 */     if (key == null || bitmap == null) {
/*     */       return;
/*     */     }
/* 440 */     this.mMemCache.put(key, bitmap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBitmapToReusableSet(@Nullable Bitmap bitmap) {
/* 449 */     if (!this.mActive || bitmap == null) {
/*     */       return;
/*     */     }
/*     */     
/* 453 */     int width = bitmap.getWidth();
/* 454 */     int height = bitmap.getHeight();
/* 455 */     Key key = new Key(width, height);
/* 456 */     synchronized (this.mReusableBitmaps) {
/* 457 */       List<SoftReference<Bitmap>> bitmaps = this.mReusableBitmaps.get(key);
/* 458 */       if (bitmaps == null) {
/* 459 */         bitmaps = new ArrayList<>();
/* 460 */         this.mReusableBitmaps.put(key, bitmaps);
/*     */       } 
/* 462 */       bitmaps.add(new SoftReference<>(bitmap));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setReusableActive(boolean active) {
/* 472 */     this.mActive = active;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isReusableActive() {
/* 479 */     return this.mActive;
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
/*     */   @TargetApi(19)
/*     */   private static int getBitmapSize(BitmapDrawable value) {
/* 492 */     Bitmap bitmap = value.getBitmap();
/*     */ 
/*     */ 
/*     */     
/* 496 */     if (Utils.isKitKat()) {
/* 497 */       return bitmap.getAllocationByteCount();
/*     */     }
/*     */     
/* 500 */     return bitmap.getByteCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAll() {
/* 507 */     clearReusableBitmaps();
/* 508 */     setReusableActive(false);
/* 509 */     clearCache();
/* 510 */     setReusableActive(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearCache() {
/* 517 */     this.mMemCache.evictAll();
/*     */   }
/*     */   
/*     */   void clearReusableBitmaps() {
/* 521 */     synchronized (this.mReusableBitmaps) {
/* 522 */       this.mReusableBitmaps.clear();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 527 */     sDebug = debug;
/*     */   }
/*     */   
/*     */   private class Key
/*     */   {
/*     */     private final int x;
/*     */     private final int y;
/*     */     
/*     */     public Key(int x, int y) {
/* 536 */       this.x = x;
/* 537 */       this.y = y;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object o) {
/* 542 */       if (this == o) {
/* 543 */         return true;
/*     */       }
/* 545 */       if (o instanceof Key) {
/* 546 */         Key key = (Key)o;
/* 547 */         return (this.x == key.x && this.y == key.y);
/*     */       } 
/* 549 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 554 */       int hash = this.x;
/* 555 */       hash = hash * 31 + this.y;
/* 556 */       return hash;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ImageMemoryCache.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */