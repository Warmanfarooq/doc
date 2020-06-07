/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Checkable;
/*     */ import android.widget.RelativeLayout;
/*     */ import java.util.ArrayList;
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
/*     */ public class CheckableRelativeLayout
/*     */   extends RelativeLayout
/*     */   implements Checkable
/*     */ {
/*     */   private boolean mChecked;
/*     */   private List<Checkable> mCheckableViews;
/*     */   private OnCheckedChangeListener mListener;
/*     */   
/*     */   public CheckableRelativeLayout(Context context) {
/*  47 */     super(context);
/*  48 */     init();
/*     */   }
/*     */   
/*     */   public CheckableRelativeLayout(Context context, AttributeSet attrs) {
/*  52 */     super(context, attrs);
/*  53 */     init();
/*     */   }
/*     */   
/*     */   public CheckableRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
/*  57 */     super(context, attrs, defStyle);
/*  58 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  62 */     this.mChecked = false;
/*  63 */     this.mCheckableViews = new ArrayList<>();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isChecked() {
/*  68 */     return this.mChecked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChecked(boolean checked) {
/*  73 */     this.mChecked = checked;
/*     */     
/*  75 */     for (Checkable c : this.mCheckableViews) {
/*  76 */       c.setChecked(checked);
/*     */     }
/*     */     
/*  79 */     if (this.mListener != null) {
/*  80 */       this.mListener.onCheckedChanged(this, checked);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void toggle() {
/*  86 */     this.mChecked = !this.mChecked;
/*     */     
/*  88 */     for (Checkable c : this.mCheckableViews) {
/*  89 */       c.toggle();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
/*  94 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onFinishInflate() {
/*  99 */     super.onFinishInflate();
/*     */     
/* 101 */     int childCount = getChildCount();
/* 102 */     for (int i = 0; i < childCount; i++) {
/* 103 */       findCheckableChildren(getChildAt(i));
/*     */     }
/*     */   }
/*     */   
/*     */   private void findCheckableChildren(View view) {
/* 108 */     if (view instanceof Checkable) {
/* 109 */       this.mCheckableViews.add((Checkable)view);
/*     */     }
/*     */     
/* 112 */     if (view instanceof ViewGroup) {
/* 113 */       ViewGroup vg = (ViewGroup)view;
/* 114 */       int childCount = vg.getChildCount();
/* 115 */       for (int i = 0; i < childCount; i++)
/* 116 */         findCheckableChildren(vg.getChildAt(i)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface OnCheckedChangeListener {
/*     */     void onCheckedChanged(CheckableRelativeLayout param1CheckableRelativeLayout, boolean param1Boolean);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\CheckableRelativeLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */