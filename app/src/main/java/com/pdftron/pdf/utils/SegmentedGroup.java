/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.ColorStateList;
/*     */ import android.content.res.Resources;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.graphics.drawable.StateListDrawable;
/*     */ import android.util.AttributeSet;
/*     */ import android.util.TypedValue;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.RadioGroup;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SegmentedGroup
/*     */   extends RadioGroup
/*     */ {
/*     */   private int oneDP;
/*     */   private Resources resources;
/*     */   private int mTintColor;
/*  28 */   private int mCheckedTextColor = -1;
/*     */   
/*     */   public SegmentedGroup(Context context) {
/*  31 */     super(context);
/*  32 */     this.resources = getResources();
/*  33 */     this.mTintColor = this.resources.getColor(R.color.tools_radio_button_selected_color);
/*  34 */     this.oneDP = (int)TypedValue.applyDimension(1, 1.0F, this.resources.getDisplayMetrics());
/*     */   }
/*     */   
/*     */   public SegmentedGroup(Context context, AttributeSet attrs) {
/*  38 */     super(context, attrs);
/*  39 */     this.resources = getResources();
/*  40 */     this.mTintColor = this.resources.getColor(R.color.tools_radio_button_selected_color);
/*  41 */     this.oneDP = (int)TypedValue.applyDimension(1, 1.0F, this.resources.getDisplayMetrics());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFinishInflate() {
/*  46 */     super.onFinishInflate();
/*     */     
/*  48 */     updateBackground();
/*     */   }
/*     */   
/*     */   public void setTintColor(int tintColor) {
/*  52 */     this.mTintColor = tintColor;
/*  53 */     updateBackground();
/*     */   }
/*     */   
/*     */   public void setTintColor(int tintColor, int checkedTextColor) {
/*  57 */     this.mTintColor = tintColor;
/*  58 */     this.mCheckedTextColor = checkedTextColor;
/*  59 */     updateBackground();
/*     */   }
/*     */   
/*     */   public void updateBackground() {
/*  63 */     int count = getChildCount();
/*  64 */     boolean rtl = Utils.isRtlLayout(getContext());
/*  65 */     if (count > 1) {
/*  66 */       View child = getChildAt(rtl ? (count - 1) : 0);
/*  67 */       LayoutParams initParams = (LayoutParams)child.getLayoutParams();
/*  68 */       LayoutParams params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
/*  69 */       params.setMargins(0, 0, -this.oneDP, 0);
/*  70 */       child.setLayoutParams((ViewGroup.LayoutParams)params);
/*  71 */       updateBackground(getChildAt(rtl ? (count - 1) : 0), R.drawable.radio_checked_left, R.drawable.radio_unchecked_left);
/*  72 */       for (int i = 1; i < count - 1; i++) {
/*  73 */         updateBackground(getChildAt(rtl ? (count - 1 - i) : i), R.drawable.radio_checked_middle, R.drawable.radio_unchecked_middle);
/*  74 */         View child2 = getChildAt(rtl ? (count - 1 - i) : i);
/*  75 */         initParams = (LayoutParams)child2.getLayoutParams();
/*  76 */         params = new LayoutParams(initParams.width, initParams.height, initParams.weight);
/*  77 */         params.setMargins(0, 0, -this.oneDP, 0);
/*  78 */         child2.setLayoutParams((ViewGroup.LayoutParams)params);
/*     */       } 
/*  80 */       updateBackground(getChildAt(rtl ? 0 : (count - 1)), R.drawable.radio_checked_right, R.drawable.radio_unchecked_right);
/*  81 */     } else if (count == 1) {
/*  82 */       updateBackground(getChildAt(0), R.drawable.radio_checked_default, R.drawable.radio_unchecked_default);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBackground(View view, int checked, int unchecked) {
/*  88 */     ColorStateList colorStateList = new ColorStateList(new int[][] { { 16842919 }, , { -16842919, -16842912 }, , { -16842919, 16842912 },  }, new int[] { -7829368, this.mTintColor, this.mCheckedTextColor });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  93 */     ((Button)view).setTextColor(colorStateList);
/*     */ 
/*     */     
/*  96 */     Drawable checkedDrawable = this.resources.getDrawable(checked).mutate();
/*  97 */     Drawable uncheckedDrawable = this.resources.getDrawable(unchecked).mutate();
/*  98 */     ((GradientDrawable)checkedDrawable).setColor(this.mTintColor);
/*  99 */     ((GradientDrawable)uncheckedDrawable).setStroke(this.oneDP, this.mTintColor);
/*     */ 
/*     */     
/* 102 */     StateListDrawable stateListDrawable = new StateListDrawable();
/* 103 */     stateListDrawable.addState(new int[] { -16842912 }, uncheckedDrawable);
/* 104 */     stateListDrawable.addState(new int[] { 16842912 }, checkedDrawable);
/*     */ 
/*     */     
/* 107 */     view.setBackground((Drawable)stateListDrawable);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\SegmentedGroup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */