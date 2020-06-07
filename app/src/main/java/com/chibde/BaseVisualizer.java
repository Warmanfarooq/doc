/*     */ package com.chibde;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Paint;
/*     */ import android.media.audiofx.Visualizer;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.View;
/*     */ import androidx.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class BaseVisualizer
/*     */   extends View
/*     */ {
/*     */   protected byte[] bytes;
/*     */   protected Paint paint;
/*     */   protected Visualizer visualizer;
/*  38 */   protected int color = -16776961;
/*     */   
/*  40 */   protected int mMode = 0;
/*     */   
/*     */   public static final int MODE_PLAYER = 0;
/*     */   public static final int MODE_RECORDER = 1;
/*     */   
/*     */   public BaseVisualizer(Context context) {
/*  46 */     super(context);
/*  47 */     init(null);
/*  48 */     init();
/*     */   }
/*     */   
/*     */   public BaseVisualizer(Context context, @Nullable AttributeSet attrs) {
/*  52 */     super(context, attrs);
/*  53 */     init(attrs);
/*  54 */     init();
/*     */   }
/*     */   
/*     */   public BaseVisualizer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  58 */     super(context, attrs, defStyleAttr);
/*  59 */     init(attrs);
/*  60 */     init();
/*     */   }
/*     */   
/*     */   private void init(AttributeSet attributeSet) {
/*  64 */     this.paint = new Paint();
/*     */   }
/*     */   
/*     */   public void setMode(int mode) {
/*  68 */     this.mMode = mode;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(int color) {
/*  77 */     this.color = color;
/*  78 */     this.paint.setColor(this.color);
/*     */   }
/*     */   
/*     */   public void setRecorder(byte[] bytes) {
/*  82 */     this.mMode = 1;
/*  83 */     this.bytes = bytes;
/*  84 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setPlayer(int audioSessionId) {
/*  88 */     this.mMode = 0;
/*  89 */     this.visualizer = new Visualizer(audioSessionId);
/*  90 */     this.visualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
/*     */     
/*  92 */     this.visualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener()
/*     */         {
/*     */           public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate)
/*     */           {
/*  96 */             BaseVisualizer.this.bytes = bytes;
/*  97 */             BaseVisualizer.this.invalidate();
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onFftDataCapture(Visualizer visualizer, byte[] bytes, int samplingRate) {}
/* 104 */         },  Visualizer.getMaxCaptureRate() / 2, true, false);
/*     */     
/* 106 */     this.visualizer.setEnabled(true);
/*     */   }
/*     */   
/*     */   public void release() {
/* 110 */     this.visualizer.release();
/*     */   }
/*     */   
/*     */   public Visualizer getVisualizer() {
/* 114 */     return this.visualizer;
/*     */   }
/*     */   
/*     */   protected abstract void init();
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\chibde\BaseVisualizer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */