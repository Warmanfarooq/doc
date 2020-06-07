/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import android.widget.GridView;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class ExpandableGridView
/*     */   extends GridView
/*     */ {
/*     */   private boolean mExpanded;
/*     */   
/*     */   public ExpandableGridView(Context context) {
/*  25 */     super(context);
/*  26 */     init(null, 0);
/*     */   }
/*     */   
/*     */   public ExpandableGridView(Context context, AttributeSet attrs) {
/*  30 */     super(context, attrs);
/*  31 */     init(attrs, 0);
/*     */   }
/*     */   
/*     */   public ExpandableGridView(Context context, AttributeSet attrs, int defStyle) {
/*  35 */     super(context, attrs, defStyle);
/*  36 */     init(null, defStyle);
/*     */   }
/*     */   
/*     */   private void init(AttributeSet attrs, int defStyle) {
/*  40 */     TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableGridView, defStyle, 0);
/*  41 */     setExpanded(a.getBoolean(R.styleable.ExpandableGridView_expanded, false));
/*  42 */     a.recycle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/*  50 */     if (isExpanded()) {
/*     */ 
/*     */       
/*  53 */       int expandSpec = MeasureSpec.makeMeasureSpec(16777215, -2147483648);
/*  54 */       super.onMeasure(widthMeasureSpec, expandSpec);
/*     */     }
/*     */     else {
/*     */       
/*  58 */       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpanded(boolean expanded) {
/*  67 */     this.mExpanded = expanded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isExpanded() {
/*  75 */     return this.mExpanded;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasFocus() {
/*  84 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/*  93 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasWindowFocus() {
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ExpandableGridView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */