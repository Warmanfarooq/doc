/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.ColorFilter;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.RectF;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TransparentDrawable
/*     */   extends Drawable
/*     */ {
/*     */   private boolean mDrawCircle;
/*  29 */   private float mRoundedCorner = 0.0F;
/*     */   private Paint mBorderPaint;
/*     */   private Paint mRedLinePaint;
/*     */   
/*     */   public TransparentDrawable(Context context) {
/*  34 */     this.mBorderPaint = new Paint(1);
/*  35 */     this.mBorderPaint.setStyle(Paint.Style.STROKE);
/*  36 */     this.mBorderPaint.setStrokeWidth(Utils.convDp2Pix(context, 0.5F));
/*  37 */     this.mBorderPaint.setColor(-7829368);
/*     */     
/*  39 */     this.mRedLinePaint = new Paint(1);
/*  40 */     this.mRedLinePaint.setStyle(Paint.Style.STROKE);
/*  41 */     this.mRedLinePaint.setStrokeWidth(Utils.convDp2Pix(context, 1.0F));
/*  42 */     this.mRedLinePaint.setColor(-65536);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void draw(@NonNull Canvas canvas) {
/*  52 */     int canvasWidth = canvas.getClipBounds().width();
/*  53 */     int canvasHeight = canvas.getClipBounds().height();
/*  54 */     float strokePadding = this.mBorderPaint.getStrokeWidth() / 2.0F;
/*  55 */     if (this.mDrawCircle) {
/*  56 */       this.mRoundedCorner = (canvasWidth / 2);
/*     */     }
/*  58 */     double corner = (this.mRoundedCorner + strokePadding);
/*  59 */     double d = Math.sqrt(corner * corner * 2.0D) - corner;
/*  60 */     float x = (float)Math.sqrt(d * d / 2.0D);
/*     */     
/*  62 */     canvas.drawLine(x, canvasHeight - x, canvasWidth - x, x, this.mRedLinePaint);
/*  63 */     RectF rectF = new RectF(strokePadding, strokePadding, canvasWidth - strokePadding, canvasHeight - strokePadding);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     canvas.drawRoundRect(rectF, this.mRoundedCorner, this.mRoundedCorner, this.mBorderPaint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAlpha(int alpha) {
/*  78 */     this.mBorderPaint.setAlpha(alpha);
/*  79 */     this.mRedLinePaint.setAlpha(alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorFilter(@Nullable ColorFilter colorFilter) {
/*  90 */     this.mBorderPaint.setColorFilter(colorFilter);
/*  91 */     this.mRedLinePaint.setColorFilter(colorFilter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOpacity() {
/* 101 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRoundedConer(float roundedConer) {
/* 110 */     this.mRoundedCorner = roundedConer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBorderColor(@ColorInt int borderColor) {
/* 119 */     this.mBorderPaint.setColor(borderColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDrawCircle(boolean circle) {
/* 126 */     this.mDrawCircle = circle;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\TransparentDrawable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */