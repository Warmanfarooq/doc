/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.app.Activity;
/*     */ import android.content.Context;
/*     */ import android.content.res.Resources;
/*     */ import android.widget.Toast;
/*     */ import androidx.annotation.Nullable;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ public class CommonToast
/*     */ {
/*     */   @Nullable
/*  35 */   private static volatile WeakReference<CommonToast> weakCommonToast = null;
/*     */   
/*     */   @Nullable
/*     */   private static CommonToast getGlobalCommonToast() {
/*  39 */     if (weakCommonToast == null) {
/*  40 */       return null;
/*     */     }
/*     */     
/*  43 */     return weakCommonToast.get();
/*     */   }
/*     */   private Toast internalToast;
/*     */   private static void setGlobalCommonToast(@Nullable CommonToast globalCommonToast) {
/*  47 */     weakCommonToast = new WeakReference<>(globalCommonToast);
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
/*     */   private CommonToast(Toast toast) {
/*  67 */     if (toast == null) {
/*  68 */       throw new NullPointerException("CommonToast.CommonToast(Toast) requires a non-null parameter.");
/*     */     }
/*     */     
/*  71 */     this.internalToast = toast;
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
/*     */   @SuppressLint({"ShowToast"})
/*     */   private static CommonToast makeText(Context context, CharSequence text, int duration) {
/*  87 */     return new CommonToast(ToastCompat.makeText(context, text, duration));
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
/*     */   @SuppressLint({"ShowToast"})
/*     */   private static CommonToast makeText(Context context, int resId, int duration) throws Resources.NotFoundException {
/* 103 */     return new CommonToast(ToastCompat.makeText(context, resId, duration));
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
/*     */   @SuppressLint({"ShowToast"})
/*     */   private static CommonToast makeText(Context context, CharSequence text) {
/* 117 */     return new CommonToast(ToastCompat.makeText(context, text, 0));
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
/*     */   @SuppressLint({"ShowToast"})
/*     */   private static CommonToast makeText(Context context, int resId) throws Resources.NotFoundException {
/* 132 */     return new CommonToast(ToastCompat.makeText(context, resId, 0));
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
/*     */   public static void showText(@Nullable final Context context, final CharSequence text, final int duration) {
/* 151 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 155 */     if (context instanceof Activity) {
/* 156 */       ((Activity)context).runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 159 */               if (CommonToast.validContext(context)) {
/* 160 */                 CommonToast.makeText(context, text, duration).show();
/*     */               }
/*     */             }
/*     */           });
/*     */     } else {
/* 165 */       makeText(context, text, duration).show();
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
/*     */   public static void showText(@Nullable final Context context, final int resId, final int duration) throws Resources.NotFoundException {
/* 186 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 190 */     if (context instanceof Activity) {
/* 191 */       ((Activity)context).runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 194 */               if (CommonToast.validContext(context)) {
/* 195 */                 CommonToast.makeText(context, resId, duration).show();
/*     */               }
/*     */             }
/*     */           });
/*     */     } else {
/* 200 */       makeText(context, resId, duration).show();
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
/*     */   public static void showText(@Nullable final Context context, final CharSequence text) {
/* 217 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 221 */     if (validContext(context)) {
/* 222 */       if (context instanceof Activity) {
/* 223 */         ((Activity)context).runOnUiThread(new Runnable()
/*     */             {
/*     */               public void run() {
/* 226 */                 if (CommonToast.validContext(context)) {
/* 227 */                   CommonToast.makeText(context, text, 0).show();
/*     */                 }
/*     */               }
/*     */             });
/*     */       } else {
/* 232 */         makeText(context, text, 0).show();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showText(@Nullable final Context context, final int resId) throws Resources.NotFoundException {
/* 251 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 255 */     if (context instanceof Activity) {
/* 256 */       ((Activity)context).runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 259 */               if (CommonToast.validContext(context)) {
/* 260 */                 CommonToast.makeText(context, resId, 0).show();
/*     */               }
/*     */             }
/*     */           });
/*     */     } else {
/* 265 */       makeText(context, resId, 0).show();
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
/*     */   public static void showText(@Nullable final Context context, final CharSequence text, final int duration, final int gravity, final int xOffset, final int yOffset) {
/* 278 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 282 */     if (context instanceof Activity) {
/* 283 */       ((Activity)context).runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 286 */               if (CommonToast.validContext(context)) {
/* 287 */                 CommonToast toast = CommonToast.makeText(context, text, duration);
/* 288 */                 toast.internalToast.setGravity(gravity, xOffset, yOffset);
/* 289 */                 toast.show();
/*     */               } 
/*     */             }
/*     */           });
/*     */     } else {
/* 294 */       CommonToast toast = makeText(context, text, duration);
/* 295 */       toast.internalToast.setGravity(gravity, xOffset, yOffset);
/* 296 */       toast.show();
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
/*     */   public static void showText(@Nullable final Context context, final int resId, final int duration, final int gravity, final int xOffset, final int yOffset) throws Resources.NotFoundException {
/* 310 */     if (!validContext(context)) {
/*     */       return;
/*     */     }
/*     */     
/* 314 */     if (context instanceof Activity) {
/* 315 */       ((Activity)context).runOnUiThread(new Runnable()
/*     */           {
/*     */             public void run() {
/* 318 */               if (CommonToast.validContext(context)) {
/* 319 */                 CommonToast toast = CommonToast.makeText(context, resId, duration);
/* 320 */                 toast.internalToast.setGravity(gravity, xOffset, yOffset);
/* 321 */                 toast.show();
/*     */               } 
/*     */             }
/*     */           });
/*     */     } else {
/* 326 */       CommonToast toast = makeText(context, resId, duration);
/* 327 */       toast.internalToast.setGravity(gravity, xOffset, yOffset);
/* 328 */       toast.show();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean validContext(@Nullable Context context) {
/* 334 */     if (context == null)
/* 335 */       return false; 
/* 336 */     if (!(context instanceof Activity)) {
/* 337 */       return true;
/*     */     }
/* 339 */     return Utils.validActivity((Activity)context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 350 */     this.internalToast.cancel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 361 */     show(true);
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
/*     */   public void show(boolean cancelCurrent) {
/* 374 */     if (cancelCurrent) {
/* 375 */       CommonToast cachedGlobalCommonToast = getGlobalCommonToast();
/* 376 */       if (cachedGlobalCommonToast != null) {
/* 377 */         cachedGlobalCommonToast.cancel();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 382 */     setGlobalCommonToast(this);
/*     */     
/* 384 */     if (Utils.isJellyBeanMR1()) {
/* 385 */       if (Utils.isRtlLayout(this.internalToast.getView().getContext())) {
/* 386 */         this.internalToast.getView().setTextDirection(4);
/*     */       } else {
/* 388 */         this.internalToast.getView().setTextDirection(3);
/*     */       } 
/*     */     }
/*     */     
/* 392 */     this.internalToast.show();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\CommonToast.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */