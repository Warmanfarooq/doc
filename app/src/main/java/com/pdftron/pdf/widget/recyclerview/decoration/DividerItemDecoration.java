/*     */ package com.pdftron.pdf.widget.recyclerview.decoration;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.view.View;
/*     */ import androidx.annotation.DrawableRes;
/*     */ import androidx.core.content.ContextCompat;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ 
/*     */ public class DividerItemDecoration
/*     */   extends RecyclerView.ItemDecoration {
/*  15 */   private static final int[] ATTRS = new int[] { 16843284 };
/*     */ 
/*     */   
/*     */   private Drawable mDivider;
/*     */ 
/*     */   
/*     */   private DividerLookup mDividerLookup;
/*     */ 
/*     */   
/*     */   public DividerItemDecoration(Context context) {
/*  25 */     TypedArray styledAttributes = context.obtainStyledAttributes(ATTRS);
/*  26 */     this.mDivider = styledAttributes.getDrawable(0);
/*  27 */     styledAttributes.recycle();
/*     */   }
/*     */   
/*     */   public DividerItemDecoration(Context context, @DrawableRes int resId) {
/*  31 */     this.mDivider = ContextCompat.getDrawable(context, resId);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
/*  37 */     boolean drawDivider = true;
/*     */     
/*  39 */     int position = getChildPosition(parent, view);
/*  40 */     if (position == -1 || view == null) {
/*  41 */       drawDivider = false;
/*  42 */     } else if (this.mDividerLookup != null) {
/*  43 */       drawDivider = this.mDividerLookup.drawDivider(position);
/*     */     } 
/*     */     
/*  46 */     if (drawDivider) {
/*  47 */       outRect.set(0, 0, 0, (this.mDivider != null) ? this.mDivider.getIntrinsicHeight() : 0);
/*     */     } else {
/*  49 */       outRect.setEmpty();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
/*  55 */     RecyclerView.LayoutManager lm = parent.getLayoutManager();
/*  56 */     int childCount = parent.getChildCount();
/*     */     
/*  58 */     for (int i = 0; i < childCount; i++) {
/*  59 */       View child = parent.getChildAt(i);
/*  60 */       boolean drawDivider = true;
/*     */       
/*  62 */       if (child == null) {
/*     */         continue;
/*     */       }
/*  65 */       if (this.mDividerLookup != null) {
/*  66 */         int position = getChildPosition(parent, child);
/*  67 */         if (position == -1) {
/*     */           continue;
/*     */         }
/*     */         
/*  71 */         drawDivider = this.mDividerLookup.drawDivider(position);
/*     */       } 
/*  73 */       if (!drawDivider);
/*     */       continue;
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
/*     */   private int getChildPosition(RecyclerView parent, View child) {
/*  94 */     int position = parent.getChildAdapterPosition(child);
/*  95 */     if (position == -1)
/*     */     {
/*  97 */       position = parent.getChildLayoutPosition(child);
/*     */     }
/*  99 */     return position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDividerLookup(DividerLookup dividerLookup) {
/* 107 */     this.mDividerLookup = dividerLookup;
/*     */   }
/*     */   
/*     */   public static interface DividerLookup {
/*     */     boolean drawDivider(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\decoration\DividerItemDecoration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */