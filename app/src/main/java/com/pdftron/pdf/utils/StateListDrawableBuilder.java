/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.StateListDrawable;
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
/*     */ public class StateListDrawableBuilder
/*     */ {
/*  18 */   private static final int[] STATE_SELECTED = new int[] { 16842913 };
/*  19 */   private static final int[] STATE_PRESSED = new int[] { 16842919 };
/*  20 */   private static final int[] STATE_FOCUSED = new int[] { 16842908 };
/*  21 */   private static final int[] STATE_HOVERED = new int[] { 16843623 };
/*  22 */   private static final int[] STATE_ENABLED = new int[] { 16842910 };
/*  23 */   private static final int[] STATE_DISABLED = new int[] { -16842910 };
/*     */ 
/*     */   
/*     */   private Drawable mNormalDrawable;
/*     */   
/*     */   private Drawable mSelectedDrawable;
/*     */   
/*     */   private Drawable mFocusedDrawable;
/*     */   
/*     */   private Drawable mHoveredDrawable;
/*     */   
/*     */   private Drawable mPressedDrawable;
/*     */   
/*     */   private Drawable mDisabledDrawable;
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setNormalDrawable(Drawable normalDrawable) {
/*  40 */     this.mNormalDrawable = normalDrawable;
/*  41 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setPressedDrawable(Drawable pressedDrawable) {
/*  52 */     this.mPressedDrawable = pressedDrawable;
/*  53 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setSelectedDrawable(Drawable selectedDrawable) {
/*  64 */     this.mSelectedDrawable = selectedDrawable;
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setFocusedDrawable(Drawable focusedDrawable) {
/*  76 */     this.mFocusedDrawable = focusedDrawable;
/*  77 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setHoveredDrawable(Drawable hoveredDrawable) {
/*  88 */     this.mHoveredDrawable = hoveredDrawable;
/*  89 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawableBuilder setDisabledDrawable(Drawable disabledDrawable) {
/* 100 */     this.mDisabledDrawable = disabledDrawable;
/* 101 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StateListDrawable build() {
/* 110 */     StateListDrawable stateListDrawable = new StateListDrawable();
/* 111 */     if (this.mSelectedDrawable != null) {
/* 112 */       stateListDrawable.addState(STATE_SELECTED, this.mSelectedDrawable);
/*     */     }
/*     */     
/* 115 */     if (this.mPressedDrawable != null) {
/* 116 */       stateListDrawable.addState(STATE_PRESSED, this.mPressedDrawable);
/*     */     }
/*     */     
/* 119 */     if (this.mFocusedDrawable != null) {
/* 120 */       stateListDrawable.addState(STATE_FOCUSED, this.mFocusedDrawable);
/*     */     }
/*     */     
/* 123 */     if (this.mHoveredDrawable != null) {
/* 124 */       stateListDrawable.addState(STATE_HOVERED, this.mHoveredDrawable);
/*     */     }
/*     */     
/* 127 */     if (this.mNormalDrawable != null) {
/* 128 */       stateListDrawable.addState(STATE_ENABLED, this.mNormalDrawable);
/*     */     }
/*     */     
/* 131 */     if (this.mDisabledDrawable != null) {
/* 132 */       stateListDrawable.addState(STATE_DISABLED, this.mDisabledDrawable);
/*     */     }
/*     */     
/* 135 */     return stateListDrawable;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\StateListDrawableBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */