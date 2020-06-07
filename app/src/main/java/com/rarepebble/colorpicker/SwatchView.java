/*     */ package com.rarepebble.colorpicker;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.RectF;
/*     */ import android.util.AttributeSet;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SwatchView
/*     */   extends SquareView
/*     */   implements ObservableColor.Observer
/*     */ {
/*     */   private final Paint borderPaint;
/*     */   private final Path borderPath;
/*     */   private final Paint checkerPaint;
/*     */   private final Path oldFillPath;
/*     */   private final Path newFillPath;
/*     */   private final Paint oldFillPaint;
/*     */   private final Paint newFillPaint;
/*     */   private final float radialMarginPx;
/*     */   
/*     */   public SwatchView(Context context) {
/*  41 */     this(context, null);
/*     */   }
/*     */   
/*     */   public SwatchView(Context context, AttributeSet attrs) {
/*  45 */     super(context, attrs);
/*     */     
/*  47 */     if (attrs != null) {
/*  48 */       TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SwatchView, 0, 0);
/*  49 */       this.radialMarginPx = a.getDimension(R.styleable.SwatchView_radialMargin, 0.0F);
/*     */     } else {
/*     */       
/*  52 */       this.radialMarginPx = 0.0F;
/*     */     } 
/*     */     
/*  55 */     this.borderPaint = Resources.makeLinePaint(context);
/*  56 */     this.checkerPaint = Resources.makeCheckerPaint(context);
/*  57 */     this.oldFillPaint = new Paint();
/*  58 */     this.newFillPaint = new Paint();
/*     */     
/*  60 */     this.borderPath = new Path();
/*  61 */     this.oldFillPath = new Path();
/*  62 */     this.newFillPath = new Path();
/*     */   }
/*     */   
/*     */   void setOriginalColor(int color) {
/*  66 */     this.oldFillPaint.setColor(color);
/*  67 */     invalidate();
/*     */   }
/*     */   
/*     */   void observeColor(ObservableColor observableColor) {
/*  71 */     observableColor.addObserver(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateColor(ObservableColor observableColor) {
/*  76 */     this.newFillPaint.setColor(observableColor.getColor());
/*  77 */     invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/*  84 */     float inset = this.borderPaint.getStrokeWidth() / 2.0F;
/*  85 */     float r = Math.min(w, h);
/*     */ 
/*     */ 
/*     */     
/*  89 */     float margin = this.radialMarginPx;
/*  90 */     float diagonal = r + 2.0F * margin;
/*  91 */     float opp = (float)Math.sqrt((diagonal * diagonal - r * r));
/*  92 */     float edgeLen = r - opp;
/*     */ 
/*     */     
/*  95 */     float outerAngle = (float)Math.toDegrees(Math.atan2(opp, r));
/*  96 */     float startAngle = 270.0F - outerAngle;
/*     */     
/*  98 */     float sweepAngle = outerAngle - 45.0F;
/*     */     
/* 100 */     float cornerSweepAngle = 90.0F - outerAngle;
/*     */ 
/*     */     
/* 103 */     beginBorder(this.borderPath, inset, edgeLen, margin, cornerSweepAngle);
/* 104 */     mainArc(this.borderPath, r, margin, startAngle, 2.0F * sweepAngle);
/* 105 */     endBorder(this.borderPath, inset, edgeLen, margin, cornerSweepAngle);
/*     */ 
/*     */     
/* 108 */     this.oldFillPath.reset();
/* 109 */     this.oldFillPath.moveTo(inset, inset);
/* 110 */     mainArc(this.oldFillPath, r, margin, 225.0F, sweepAngle);
/* 111 */     endBorder(this.oldFillPath, inset, edgeLen, margin, cornerSweepAngle);
/*     */ 
/*     */     
/* 114 */     beginBorder(this.newFillPath, inset, edgeLen, margin, cornerSweepAngle);
/* 115 */     mainArc(this.newFillPath, r, margin, startAngle, sweepAngle);
/* 116 */     this.newFillPath.lineTo(inset, inset);
/* 117 */     this.newFillPath.close();
/*     */   }
/*     */   
/*     */   private static void beginBorder(Path path, float inset, float edgeLen, float cornerRadius, float cornerSweepAngle) {
/* 121 */     path.reset();
/* 122 */     path.moveTo(inset, inset);
/* 123 */     cornerArc(path, edgeLen, inset, cornerRadius - inset, 0.0F, cornerSweepAngle);
/*     */   }
/*     */   
/*     */   private static void endBorder(Path path, float inset, float edgeLen, float cornerRadius, float cornerSweepAngle) {
/* 127 */     cornerArc(path, inset, edgeLen, cornerRadius - inset, 90.0F - cornerSweepAngle, cornerSweepAngle);
/* 128 */     path.lineTo(inset, inset);
/* 129 */     path.close();
/*     */   }
/*     */   
/*     */   private static void cornerArc(Path path, float cx, float cy, float r, float startAngle, float sweepAngle) {
/* 133 */     RectF ovalRect = new RectF(-r, -r, r, r);
/* 134 */     ovalRect.offset(cx, cy);
/* 135 */     path.arcTo(ovalRect, startAngle, sweepAngle);
/*     */   }
/*     */   
/*     */   private static void mainArc(Path path, float viewSize, float margin, float startAngle, float sweepAngle) {
/* 139 */     float r = viewSize + margin;
/* 140 */     RectF ovalRect = new RectF(-r, -r, r, r);
/* 141 */     ovalRect.offset(viewSize, viewSize);
/* 142 */     path.arcTo(ovalRect, startAngle, sweepAngle);
/*     */   }
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 146 */     super.onDraw(canvas);
/* 147 */     canvas.drawPath(this.borderPath, this.checkerPaint);
/* 148 */     canvas.drawPath(this.oldFillPath, this.oldFillPaint);
/* 149 */     canvas.drawPath(this.newFillPath, this.newFillPaint);
/* 150 */     canvas.drawPath(this.borderPath, this.borderPaint);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\SwatchView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */