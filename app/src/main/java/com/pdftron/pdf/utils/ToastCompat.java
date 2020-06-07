/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.ContextWrapper;
/*     */ import android.content.res.Resources;
/*     */ import android.os.Build;
/*     */ import android.view.Display;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowManager;
/*     */ import android.widget.Toast;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.StringRes;
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ final class ToastCompat
/*     */   extends Toast
/*     */ {
/*     */   @NonNull
/*     */   private final Toast toast;
/*     */   
/*     */   private ToastCompat(Context context, @NonNull Toast base) {
/*  26 */     super(context);
/*  27 */     this.toast = base;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToastCompat makeText(Context context, CharSequence text, int duration) {
/*  34 */     Toast toast = Toast.makeText(context, text, duration);
/*  35 */     setContextCompat(toast.getView(), (Context)new SafeToastContext(context, toast));
/*  36 */     return new ToastCompat(context, toast);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Toast makeText(Context context, @StringRes int resId, int duration) throws Resources.NotFoundException {
/*  41 */     return makeText(context, context.getResources().getText(resId), duration);
/*     */   }
/*     */ 
/*     */   
/*     */   public void show() {
/*  46 */     this.toast.show();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDuration(int duration) {
/*  51 */     this.toast.setDuration(duration);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGravity(int gravity, int xOffset, int yOffset) {
/*  56 */     this.toast.setGravity(gravity, xOffset, yOffset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMargin(float horizontalMargin, float verticalMargin) {
/*  61 */     this.toast.setMargin(horizontalMargin, verticalMargin);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(int resId) {
/*  66 */     this.toast.setText(resId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setText(CharSequence s) {
/*  71 */     this.toast.setText(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setView(View view) {
/*  76 */     this.toast.setView(view);
/*  77 */     setContextCompat(view, (Context)new SafeToastContext(view.getContext(), this));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHorizontalMargin() {
/*  82 */     return this.toast.getHorizontalMargin();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getVerticalMargin() {
/*  87 */     return this.toast.getVerticalMargin();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDuration() {
/*  92 */     return this.toast.getDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGravity() {
/*  97 */     return this.toast.getGravity();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getXOffset() {
/* 102 */     return this.toast.getXOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getYOffset() {
/* 107 */     return this.toast.getYOffset();
/*     */   }
/*     */ 
/*     */   
/*     */   public View getView() {
/* 112 */     return this.toast.getView();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancel() {
/* 117 */     this.toast.cancel();
/*     */   }
/*     */   
/*     */   private static void setContextCompat(@NonNull View view, @NonNull Context context) {
/* 121 */     if (Build.VERSION.SDK_INT == 25)
/*     */       try {
/* 123 */         Field field = View.class.getDeclaredField("mContext");
/* 124 */         field.setAccessible(true);
/* 125 */         field.set(view, context);
/* 126 */       } catch (Throwable throwable) {
/* 127 */         throwable.printStackTrace();
/*     */       }  
/*     */   }
/*     */   
/*     */   private static final class SafeToastContext
/*     */     extends ContextWrapper {
/*     */     @NonNull
/*     */     private Toast toast;
/*     */     
/*     */     SafeToastContext(@NonNull Context base, @NonNull Toast toast) {
/* 137 */       super(base);
/* 138 */       this.toast = toast;
/*     */     }
/*     */ 
/*     */     
/*     */     public Context getApplicationContext() {
/* 143 */       return (Context)new ApplicationContextWrapper(getBaseContext().getApplicationContext());
/*     */     }
/*     */     
/*     */     private final class ApplicationContextWrapper
/*     */       extends ContextWrapper {
/*     */       private ApplicationContextWrapper(Context base) {
/* 149 */         super(base);
/*     */       }
/*     */ 
/*     */       
/*     */       public Object getSystemService(@NonNull String name) {
/* 154 */         if ("window".equals(name)) {
/* 155 */           return new WindowManagerWrapper((WindowManager)getBaseContext().getSystemService(name));
/*     */         }
/* 157 */         return super.getSystemService(name);
/*     */       }
/*     */     }
/*     */     
/*     */     private final class WindowManagerWrapper
/*     */       implements WindowManager {
/*     */       @NonNull
/*     */       private final WindowManager base;
/*     */       
/*     */       private WindowManagerWrapper(WindowManager base) {
/* 167 */         this.base = base;
/*     */       }
/*     */ 
/*     */       
/*     */       public Display getDefaultDisplay() {
/* 172 */         return this.base.getDefaultDisplay();
/*     */       }
/*     */ 
/*     */       
/*     */       public void removeViewImmediate(View view) {
/* 177 */         this.base.removeViewImmediate(view);
/*     */       }
/*     */ 
/*     */       
/*     */       public void addView(View view, ViewGroup.LayoutParams params) {
/*     */         try {
/* 183 */           this.base.addView(view, params);
/* 184 */         } catch (BadTokenException badTokenException) {
/*     */         
/* 186 */         } catch (Throwable throwable) {
/* 187 */           AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable));
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void updateViewLayout(View view, ViewGroup.LayoutParams params) {
/* 193 */         this.base.updateViewLayout(view, params);
/*     */       }
/*     */ 
/*     */       
/*     */       public void removeView(View view) {
/* 198 */         this.base.removeView(view);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ToastCompat.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */