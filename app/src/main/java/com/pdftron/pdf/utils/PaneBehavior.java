/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Point;
/*     */ import android.view.Gravity;
/*     */ import android.view.View;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.coordinatorlayout.widget.CoordinatorLayout;
/*     */ import androidx.core.view.GravityCompat;
/*     */ import androidx.core.view.ViewCompat;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PaneBehavior
/*     */   extends CoordinatorLayout.Behavior<View>
/*     */ {
/*  18 */   private Point mTempPoint = new Point();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static PaneBehavior from(View view) {
/*  28 */     if (view != null && view.getLayoutParams() instanceof CoordinatorLayout.LayoutParams) {
/*  29 */       CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)view.getLayoutParams();
/*  30 */       CoordinatorLayout.Behavior behavior = params.getBehavior();
/*  31 */       if (behavior instanceof PaneBehavior) {
/*  32 */         return (PaneBehavior)behavior;
/*     */       }
/*     */     } 
/*  35 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGravityForOrientation(Context context, int orientation) {
/*  45 */     int gravity = 0;
/*  46 */     if (Utils.isTablet(context)) {
/*  47 */       switch (orientation) {
/*     */         case 1:
/*  49 */           gravity = 48;
/*     */           break;
/*     */         case 2:
/*  52 */           gravity = 8388613;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  59 */     return gravity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onOrientationChanged(View child, int orientation) {
/*  68 */     int gravity = 0;
/*  69 */     if (Utils.isTablet(child.getContext())) {
/*  70 */       switch (orientation) {
/*     */         case 1:
/*  72 */           gravity = 48;
/*     */           break;
/*     */         case 2:
/*  75 */           gravity = 8388613;
/*     */           break;
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/*  82 */     setRelativeLayoutGravity(child, gravity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMeasureChild(CoordinatorLayout parent, View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
/*  92 */     int childGravity = getAbsoluteLayoutGravity(child);
/*  93 */     if (isValidGravity(childGravity) && Utils.isTablet(child.getContext())) {
/*  94 */       int widthMode = View.MeasureSpec.getMode(parentWidthMeasureSpec);
/*  95 */       int widthSize = View.MeasureSpec.getSize(parentWidthMeasureSpec);
/*  96 */       int heightMode = View.MeasureSpec.getMode(parentHeightMeasureSpec);
/*  97 */       int heightSize = View.MeasureSpec.getSize(parentHeightMeasureSpec);
/*     */       
/*  99 */       int desiredWidth = widthSize;
/* 100 */       int desiredHeight = heightSize;
/*     */       
/* 102 */       Point displaySize = this.mTempPoint;
/* 103 */       Utils.getDisplaySize(child.getContext(), displaySize);
/*     */ 
/*     */       
/* 106 */       if (Gravity.isHorizontal(childGravity)) {
/* 107 */         desiredWidth = displaySize.x / 3;
/*     */       } else {
/* 109 */         desiredHeight = displaySize.y / 3;
/*     */       } 
/*     */ 
/*     */       
/* 113 */       switch (widthMode) {
/*     */         case 1073741824:
/*     */         case -2147483648:
/* 116 */           widthSize = Math.min(desiredWidth, widthSize);
/*     */           break;
/*     */         case 0:
/* 119 */           widthSize = desiredWidth;
/*     */           break;
/*     */       } 
/* 122 */       switch (heightMode) {
/*     */         case 1073741824:
/*     */         case -2147483648:
/* 125 */           heightSize = Math.min(desiredHeight, heightSize);
/*     */           break;
/*     */         case 0:
/* 128 */           heightSize = desiredHeight;
/*     */           break;
/*     */       } 
/*     */       
/* 132 */       int childWidthMeasureSpec = View.MeasureSpec.makeMeasureSpec(widthSize, widthMode);
/* 133 */       int childHeightMeasureSpec = View.MeasureSpec.makeMeasureSpec(heightSize, heightMode);
/* 134 */       parent.onMeasureChild(child, childWidthMeasureSpec, widthUsed, childHeightMeasureSpec, heightUsed);
/* 135 */       return true;
/*     */     } 
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getAbsoluteLayoutGravity(View child) {
/* 142 */     int gravity = 0;
/* 143 */     CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
/* 144 */     if (lp != null) {
/* 145 */       gravity = GravityCompat.getAbsoluteGravity(lp.gravity, ViewCompat.getLayoutDirection(child));
/*     */     }
/* 147 */     return gravity;
/*     */   }
/*     */   
/*     */   private void setRelativeLayoutGravity(View child, int gravity) {
/* 151 */     CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams)child.getLayoutParams();
/* 152 */     if (lp != null && lp.gravity != gravity) {
/* 153 */       lp.gravity = gravity;
/* 154 */       child.requestLayout();
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isValidGravity(int gravity) {
/* 159 */     if (!Gravity.isHorizontal(gravity) && !Gravity.isVertical(gravity))
/*     */     {
/* 161 */       return false; } 
/* 162 */     if ((Gravity.isHorizontal(gravity) && Gravity.isVertical(gravity)) || (gravity & 0x7) == 7 || (gravity & 0x70) == 112)
/*     */     {
/*     */ 
/*     */       
/* 166 */       return false;
/*     */     }
/* 168 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PaneBehavior.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */