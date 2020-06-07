/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.annotation.TargetApi;
/*     */ import android.content.Context;
/*     */ import android.graphics.Rect;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowInsets;
/*     */ import android.widget.FrameLayout;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public abstract class InsectHandlerToolbar
/*     */   extends FrameLayout
/*     */ {
/*     */   private Object mLastInsets;
/*     */   
/*     */   public InsectHandlerToolbar(@NonNull Context context) {
/*  33 */     super(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsectHandlerToolbar(@NonNull Context context, @Nullable AttributeSet attrs) {
/*  41 */     super(context, attrs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InsectHandlerToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  50 */     super(context, attrs, defStyleAttr);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public InsectHandlerToolbar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/*  62 */     super(context, attrs, defStyleAttr, defStyleRes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onAttachedToWindow() {
/*  70 */     super.onAttachedToWindow();
/*     */     
/*  72 */     if (ViewCompat.getFitsSystemWindows((View)this) && this.mLastInsets == null)
/*     */     {
/*  74 */       ViewCompat.requestApplyInsets((View)this);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean fitSystemWindows(Rect insets) {
/*  84 */     if (Utils.isLollipop()) {
/*  85 */       return super.fitSystemWindows(insets);
/*     */     }
/*  87 */     if (ViewCompat.getFitsSystemWindows((View)this) && getLayoutParams() instanceof MarginLayoutParams) {
/*  88 */       MarginLayoutParams mlp = (MarginLayoutParams)getLayoutParams();
/*     */       
/*  90 */       if (insets != null && (mlp.leftMargin != insets.left || mlp.topMargin != insets.top || mlp.rightMargin != insets.right)) {
/*     */ 
/*     */ 
/*     */         
/*  94 */         mlp.setMargins(insets.left, insets.top, insets.right, 0);
/*     */ 
/*     */ 
/*     */         
/*  98 */         post(new Runnable()
/*     */             {
/*     */               public void run() {
/* 101 */                 InsectHandlerToolbar.this.requestLayout();
/*     */               }
/*     */             });
/*     */       } 
/*     */       
/* 106 */       if (this.mLastInsets == null) {
/* 107 */         this.mLastInsets = new Rect();
/*     */       }
/* 109 */       ((Rect)this.mLastInsets).set(insets);
/*     */       
/* 111 */       return true;
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @TargetApi(21)
/*     */   public WindowInsets onApplyWindowInsets(WindowInsets insets) {
/* 124 */     if (getFitsSystemWindows() && getLayoutParams() instanceof MarginLayoutParams) {
/* 125 */       MarginLayoutParams mlp = (MarginLayoutParams)getLayoutParams();
/* 126 */       if (insets != null && (mlp.leftMargin != insets
/* 127 */         .getSystemWindowInsetLeft() || mlp.topMargin != insets
/* 128 */         .getSystemWindowInsetTop() || mlp.rightMargin != insets
/* 129 */         .getSystemWindowInsetRight())) {
/*     */         
/* 131 */         mlp.setMargins(insets.getSystemWindowInsetLeft(), insets
/* 132 */             .getSystemWindowInsetTop(), insets.getSystemWindowInsetRight(), 0);
/*     */         
/* 134 */         requestLayout();
/*     */       } 
/*     */     } 
/* 137 */     this.mLastInsets = insets;
/*     */     
/* 139 */     return insets;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\InsectHandlerToolbar.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */