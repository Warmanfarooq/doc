/*     */ package com.chibde.visualizer;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.util.AttributeSet;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.chibde.BaseVisualizer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LineBarVisualizer
/*     */   extends BaseVisualizer
/*     */ {
/*     */   private Paint middleLine;
/*     */   private float density;
/*     */   private int gap;
/*     */   
/*     */   public LineBarVisualizer(Context context) {
/*  42 */     super(context);
/*     */   }
/*     */   
/*     */   public LineBarVisualizer(Context context, @Nullable AttributeSet attrs) {
/*  46 */     super(context, attrs);
/*     */   }
/*     */   
/*     */   public LineBarVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  50 */     super(context, attrs, defStyleAttr);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void init() {
/*  55 */     this.density = 50.0F;
/*  56 */     this.gap = 4;
/*  57 */     this.middleLine = new Paint();
/*  58 */     this.middleLine.setColor(-16776961);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDensity(float density) {
/*  69 */     if (this.density > 180.0F) {
/*  70 */       this.middleLine.setStrokeWidth(1.0F);
/*  71 */       this.gap = 1;
/*     */     } else {
/*  73 */       this.gap = 4;
/*     */     } 
/*  75 */     this.density = density;
/*  76 */     if (density > 256.0F) {
/*  77 */       this.density = 250.0F;
/*  78 */       this.gap = 0;
/*  79 */     } else if (density <= 10.0F) {
/*  80 */       this.density = 10.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/*  86 */     if (this.middleLine.getColor() != -16776961) {
/*  87 */       this.middleLine.setColor(this.color);
/*     */     }
/*  89 */     if (this.bytes != null) {
/*  90 */       float barWidth = getWidth() / this.density;
/*  91 */       float div = this.bytes.length / this.density;
/*  92 */       canvas.drawLine(0.0F, (getHeight() / 2), getWidth(), (getHeight() / 2), this.middleLine);
/*  93 */       this.paint.setStrokeWidth(barWidth - this.gap);
/*     */       
/*  95 */       for (int i = 0; i < this.density; i++) {
/*  96 */         int top, bottom, bytePosition = (int)Math.ceil((i * div));
/*     */ 
/*     */         
/*  99 */         if (this.mMode == 0) {
/*     */ 
/*     */           
/* 102 */           top = canvas.getHeight() / 2 + (128 - Math.abs(this.bytes[bytePosition])) * canvas.getHeight() / 2 / 128;
/*     */ 
/*     */ 
/*     */           
/* 106 */           bottom = canvas.getHeight() / 2 - (128 - Math.abs(this.bytes[bytePosition])) * canvas.getHeight() / 2 / 128;
/*     */         } else {
/* 108 */           top = canvas.getHeight() / 2 + Math.abs(this.bytes[bytePosition]);
/* 109 */           bottom = canvas.getHeight() / 2 - Math.abs(this.bytes[bytePosition]);
/*     */         } 
/*     */         
/* 112 */         float barX = i * barWidth + barWidth / 2.0F;
/* 113 */         canvas.drawLine(barX, bottom, barX, (canvas.getHeight() / 2), this.paint);
/* 114 */         canvas.drawLine(barX, top, barX, (canvas.getHeight() / 2), this.paint);
/*     */       } 
/* 116 */       super.onDraw(canvas);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\chibde\visualizer\LineBarVisualizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */