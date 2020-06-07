/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.graphics.Bitmap;
/*    */ import android.graphics.BitmapShader;
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.ColorFilter;
/*    */ import android.graphics.Paint;
/*    */ import android.graphics.Rect;
/*    */ import android.graphics.RectF;
/*    */ import android.graphics.Shader;
/*    */ import android.graphics.drawable.Drawable;
/*    */ import androidx.annotation.NonNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class RoundCornersDrawable
/*    */   extends Drawable
/*    */ {
/*    */   private final float mCornerRadius;
/* 28 */   private final RectF mRect = new RectF();
/*    */   private final Paint mPaint;
/*    */   private final Paint mBorderPaint;
/*    */   private final int mMargin;
/*    */   private boolean mDrawBorder;
/*    */   
/*    */   RoundCornersDrawable(Bitmap bitmap, float cornerRadius, int margin) {
/* 35 */     this.mCornerRadius = cornerRadius;
/*    */     
/* 37 */     BitmapShader bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
/*    */ 
/*    */     
/* 40 */     this.mPaint = new Paint();
/* 41 */     this.mPaint.setAntiAlias(true);
/* 42 */     this.mPaint.setShader((Shader)bitmapShader);
/*    */     
/* 44 */     this.mBorderPaint = new Paint();
/* 45 */     this.mBorderPaint.setAntiAlias(true);
/* 46 */     this.mBorderPaint.setStyle(Paint.Style.STROKE);
/*    */     
/* 48 */     this.mMargin = margin;
/*    */   }
/*    */   
/*    */   void disableBorder() {
/* 52 */     this.mDrawBorder = false;
/*    */   }
/*    */   
/*    */   void enableBorder(int color, float thickness) {
/* 56 */     this.mDrawBorder = true;
/* 57 */     this.mBorderPaint.setColor(color);
/* 58 */     this.mBorderPaint.setStrokeWidth(thickness);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onBoundsChange(Rect bounds) {
/* 63 */     super.onBoundsChange(bounds);
/* 64 */     this.mRect.set(this.mMargin, this.mMargin, (bounds.width() - this.mMargin), (bounds.height() - this.mMargin));
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(@NonNull Canvas canvas) {
/* 69 */     canvas.drawRoundRect(this.mRect, this.mCornerRadius, this.mCornerRadius, this.mPaint);
/*    */     
/* 71 */     if (this.mDrawBorder) {
/* 72 */       canvas.drawRoundRect(this.mRect, this.mCornerRadius, this.mCornerRadius, this.mBorderPaint);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOpacity() {
/* 78 */     return -3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAlpha(int alpha) {
/* 83 */     this.mPaint.setAlpha(alpha);
/* 84 */     this.mBorderPaint.setAlpha(alpha);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setColorFilter(ColorFilter cf) {
/* 89 */     this.mPaint.setColorFilter(cf);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\RoundCornersDrawable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */