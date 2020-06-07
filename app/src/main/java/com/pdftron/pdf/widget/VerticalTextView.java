/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.text.TextPaint;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.Gravity;
/*     */ import androidx.appcompat.widget.AppCompatTextView;
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
/*     */ public class VerticalTextView
/*     */   extends AppCompatTextView
/*     */ {
/*     */   private boolean topDown = false;
/*     */   
/*     */   public VerticalTextView(Context context) {
/*  29 */     super(context);
/*  30 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalTextView(Context context, AttributeSet attrs) {
/*  37 */     super(context, attrs);
/*  38 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  45 */     super(context, attrs, defStyleAttr);
/*  46 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  50 */     int gravity = getGravity();
/*  51 */     if (Gravity.isVertical(gravity) && (gravity & 0x70) == 80) {
/*  52 */       setGravity(gravity & 0x7 | 0x30);
/*  53 */       this.topDown = false;
/*     */     } else {
/*     */       
/*  56 */       this.topDown = true;
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
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/*  72 */     super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*  73 */     setMeasuredDimension(getMeasuredHeight(), getMeasuredWidth());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/*  82 */     TextPaint textPaint = getPaint();
/*  83 */     textPaint.setColor(getCurrentTextColor());
/*  84 */     textPaint.drawableState = getDrawableState();
/*     */     
/*  86 */     canvas.save();
/*     */     
/*  88 */     if (this.topDown) {
/*  89 */       canvas.translate(getWidth(), 0.0F);
/*  90 */       canvas.rotate(90.0F);
/*     */     } else {
/*     */       
/*  93 */       canvas.translate(0.0F, getHeight());
/*  94 */       canvas.rotate(-90.0F);
/*     */     } 
/*     */     
/*  97 */     canvas.translate(getCompoundPaddingLeft(), 
/*  98 */         getExtendedPaddingTop());
/*     */     
/* 100 */     getLayout().draw(canvas);
/* 101 */     canvas.restore();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\VerticalTextView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */