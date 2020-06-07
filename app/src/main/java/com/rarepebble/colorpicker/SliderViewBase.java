/*     */ package com.rarepebble.colorpicker;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.Rect;
/*     */ import android.graphics.RectF;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
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
/*     */ public abstract class SliderViewBase
/*     */   extends View
/*     */ {
/*     */   private final Paint borderPaint;
/*     */   private final Paint checkerPaint;
/*  34 */   private final Rect viewRect = new Rect();
/*     */   
/*     */   private int w;
/*     */   private int h;
/*     */   private final Path borderPath;
/*     */   private Bitmap bitmap;
/*     */   private final Path pointerPath;
/*     */   private final Paint pointerPaint;
/*     */   private float currentPos;
/*     */   
/*     */   public SliderViewBase(Context context, AttributeSet attrs) {
/*  45 */     super(context, attrs);
/*  46 */     this.checkerPaint = Resources.makeCheckerPaint(context);
/*  47 */     this.borderPaint = Resources.makeLinePaint(context);
/*  48 */     this.pointerPaint = Resources.makeLinePaint(context);
/*  49 */     this.pointerPath = Resources.makePointerPath(context);
/*  50 */     this.borderPath = new Path();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void notifyListener(float paramFloat);
/*     */   
/*     */   protected abstract Bitmap makeBitmap(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract int getPointerColor(float paramFloat);
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/*  61 */     this.w = w;
/*  62 */     this.h = h;
/*  63 */     this.viewRect.set(0, 0, w, h);
/*  64 */     float inset = this.borderPaint.getStrokeWidth() / 2.0F;
/*  65 */     this.borderPath.reset();
/*  66 */     this.borderPath.addRect(new RectF(inset, inset, w - inset, h - inset), Path.Direction.CW);
/*  67 */     updateBitmap();
/*     */   }
/*     */   
/*     */   protected void setPos(float pos) {
/*  71 */     this.currentPos = pos;
/*  72 */     optimisePointerColor();
/*     */   }
/*     */   
/*     */   protected void updateBitmap() {
/*  76 */     if (this.w > 0 && this.h > 0) {
/*  77 */       this.bitmap = makeBitmap(this.w, this.h);
/*  78 */       optimisePointerColor();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent event) {
/*  85 */     int action = event.getActionMasked();
/*  86 */     switch (action) {
/*     */       case 0:
/*     */       case 2:
/*  89 */         this.currentPos = valueForTouchPos(event.getX(), event.getY());
/*  90 */         optimisePointerColor();
/*  91 */         notifyListener(this.currentPos);
/*  92 */         invalidate();
/*  93 */         getParent().requestDisallowInterceptTouchEvent(true);
/*  94 */         return true;
/*     */     } 
/*  96 */     return super.onTouchEvent(event);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 101 */     super.onDraw(canvas);
/* 102 */     canvas.drawPath(this.borderPath, this.checkerPaint);
/* 103 */     canvas.drawBitmap(this.bitmap, null, this.viewRect, null);
/* 104 */     canvas.drawPath(this.borderPath, this.borderPaint);
/*     */     
/* 106 */     canvas.save();
/* 107 */     if (isWide()) {
/* 108 */       canvas.translate(this.w * this.currentPos, (this.h / 2));
/*     */     } else {
/*     */       
/* 111 */       canvas.translate((this.w / 2), this.h * (1.0F - this.currentPos));
/*     */     } 
/* 113 */     canvas.drawPath(this.pointerPath, this.pointerPaint);
/* 114 */     canvas.restore();
/*     */   }
/*     */   
/*     */   private boolean isWide() {
/* 118 */     return (this.w > this.h);
/*     */   }
/*     */   
/*     */   private float valueForTouchPos(float x, float y) {
/* 122 */     float val = isWide() ? (x / this.w) : (1.0F - y / this.h);
/* 123 */     return Math.max(0.0F, Math.min(1.0F, val));
/*     */   }
/*     */   
/*     */   private void optimisePointerColor() {
/* 127 */     this.pointerPaint.setColor(getPointerColor(this.currentPos));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\SliderViewBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */