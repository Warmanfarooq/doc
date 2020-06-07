/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.RectF;
/*     */ import android.util.AttributeSet;
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
/*     */ class ColorPanelView
/*     */   extends View
/*     */ {
/*     */   private static final float BORDER_WIDTH_PX = 1.0F;
/* 240 */   private static float mDensity = 1.0F;
/*     */   
/* 242 */   private int mBorderColor = -9539986;
/* 243 */   private int mColor = -16777216;
/*     */   
/*     */   private Paint mBorderPaint;
/*     */   
/*     */   private Paint mColorPaint;
/*     */   
/*     */   private RectF mDrawingRect;
/*     */   private RectF mColorRect;
/*     */   private AlphaPatternDrawable mAlphaPattern;
/*     */   
/*     */   ColorPanelView(Context context) {
/* 254 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   ColorPanelView(Context context, AttributeSet attrs) {
/* 258 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   ColorPanelView(Context context, AttributeSet attrs, int defStyle) {
/* 262 */     super(context, attrs, defStyle);
/*     */     
/* 264 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/* 268 */     this.mBorderPaint = new Paint();
/* 269 */     this.mColorPaint = new Paint();
/* 270 */     mDensity = (getContext().getResources().getDisplayMetrics()).density;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 275 */     RectF rect = this.mColorRect;
/*     */ 
/*     */     
/* 278 */     this.mBorderPaint.setColor(this.mBorderColor);
/* 279 */     canvas.drawRect(this.mDrawingRect, this.mBorderPaint);
/*     */ 
/*     */     
/* 282 */     if (this.mAlphaPattern != null) {
/* 283 */       this.mAlphaPattern.draw(canvas);
/*     */     }
/*     */     
/* 286 */     this.mColorPaint.setColor(this.mColor);
/*     */     
/* 288 */     canvas.drawRect(rect, this.mColorPaint);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 293 */     int width = MeasureSpec.getSize(widthMeasureSpec);
/* 294 */     int height = MeasureSpec.getSize(heightMeasureSpec);
/*     */     
/* 296 */     setMeasuredDimension(width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 301 */     super.onSizeChanged(w, h, oldw, oldh);
/*     */     
/* 303 */     this.mDrawingRect = new RectF();
/* 304 */     this.mDrawingRect.left = getPaddingLeft();
/* 305 */     this.mDrawingRect.right = (w - getPaddingRight());
/* 306 */     this.mDrawingRect.top = getPaddingTop();
/* 307 */     this.mDrawingRect.bottom = (h - getPaddingBottom());
/*     */     
/* 309 */     setUpColorRect();
/*     */   }
/*     */   
/*     */   private void setUpColorRect() {
/* 313 */     RectF dRect = this.mDrawingRect;
/*     */     
/* 315 */     float left = dRect.left + 1.0F;
/* 316 */     float top = dRect.top + 1.0F;
/* 317 */     float bottom = dRect.bottom - 1.0F;
/* 318 */     float right = dRect.right - 1.0F;
/*     */     
/* 320 */     this.mColorRect = new RectF(left, top, right, bottom);
/*     */     
/* 322 */     this.mAlphaPattern = new AlphaPatternDrawable((int)(5.0F * mDensity));
/*     */     
/* 324 */     this.mAlphaPattern.setBounds(Math.round(this.mColorRect.left), 
/* 325 */         Math.round(this.mColorRect.top), 
/* 326 */         Math.round(this.mColorRect.right), 
/* 327 */         Math.round(this.mColorRect.bottom));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setColor(int color) {
/* 335 */     this.mColor = color;
/* 336 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getColor() {
/* 344 */     return this.mColor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setBorderColor(int color) {
/* 352 */     this.mBorderColor = color;
/* 353 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getBorderColor() {
/* 360 */     return this.mBorderColor;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ColorPanelView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */