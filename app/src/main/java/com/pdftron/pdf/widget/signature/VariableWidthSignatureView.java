/*     */ package com.pdftron.pdf.widget.signature;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.RectF;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VariableWidthSignatureView
/*     */   extends View
/*     */ {
/*     */   private static final int DEFAULT_STROKE_COLOR = -16777216;
/*     */   private Bitmap mBitmap;
/*     */   @ColorInt
/*  26 */   private int mStrokeColor = -16777216;
/*     */ 
/*     */ 
/*     */   
/*  30 */   private float mLeft = 0.0F;
/*  31 */   private float mTop = 0.0F;
/*  32 */   private float mRight = 0.0F;
/*  33 */   private float mBottom = 0.0F;
/*     */   
/*     */   private boolean mIsFirstTouch = true;
/*     */   private boolean mIsPressureSensitive = true;
/*     */   private float mStrokeWidth;
/*  38 */   private ArrayList<InkListener> mListeners = new ArrayList<>();
/*     */   
/*  40 */   private float mPrevX = Float.MAX_VALUE;
/*  41 */   private float mPrevY = Float.MAX_VALUE;
/*     */   
/*     */   @Nullable
/*     */   private PointProcessor mPointProcessorObj;
/*     */   
/*     */   public VariableWidthSignatureView(Context context, AttributeSet attrs) {
/*  47 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public VariableWidthSignatureView(Context context, AttributeSet attrs, int defStyleAttr) {
/*  51 */     super(context, attrs, defStyleAttr);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/*  56 */     super.onSizeChanged(w, h, oldw, oldh);
/*     */     
/*  58 */     initializeState();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent e) {
/*  63 */     int action = e.getAction();
/*     */     
/*  65 */     float x = e.getX();
/*  66 */     float y = e.getY();
/*  67 */     float pressure = this.mIsPressureSensitive ? e.getPressure() : 1.0F;
/*     */     
/*  69 */     if (action == 2) {
/*  70 */       processOnMoveHistoricalMotionPoints(e);
/*  71 */     } else if (action == 0) {
/*  72 */       onTouchDown(x, y, pressure);
/*  73 */     } else if (action == 1) {
/*  74 */       onTouchUp(x, y, pressure);
/*     */     } 
/*     */     
/*  77 */     this.mLeft = Math.min(x, this.mLeft);
/*  78 */     this.mTop = Math.max(y, this.mTop);
/*  79 */     this.mRight = Math.max(x, this.mRight);
/*  80 */     this.mBottom = Math.min(y, this.mBottom);
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   private void processOnMoveHistoricalMotionPoints(MotionEvent e2) {
/*  85 */     float thres = 2.0F;
/*     */     
/*  87 */     int historySize = e2.getHistorySize();
/*  88 */     int pointerCount = e2.getPointerCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     for (int h = 0; h < historySize; h++) {
/*  95 */       if (pointerCount >= 1) {
/*     */         
/*  97 */         float x = e2.getHistoricalX(0, h);
/*  98 */         float y = e2.getHistoricalY(0, h);
/*     */         
/* 100 */         if (distance(x, y, this.mPrevX, this.mPrevY) > thres) {
/* 101 */           float pressure = e2.getPressure();
/* 102 */           onTouchMove(x, y, pressure);
/* 103 */           this.mPrevX = x;
/* 104 */           this.mPrevY = y;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private float distance(float x1, float y1, float x2, float y2) {
/* 111 */     float dx = x2 - x1;
/* 112 */     float dy = y2 - y1;
/* 113 */     return (float)Math.sqrt((dx * dx + dy * dy));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void onTouchDown(float x, float y, float pressure) {
/* 119 */     for (InkListener listener : this.mListeners) {
/* 120 */       listener.onInkStarted();
/*     */     }
/* 122 */     this.mPrevX = x;
/* 123 */     this.mPrevY = y;
/* 124 */     this.mPointProcessorObj.onDown(x, y, pressure);
/*     */ 
/*     */     
/* 127 */     if (this.mIsFirstTouch) {
/* 128 */       this.mLeft = x;
/* 129 */       this.mTop = y;
/* 130 */       this.mRight = x;
/* 131 */       this.mBottom = y;
/* 132 */       this.mIsFirstTouch = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTouchMove(float x, float y, float pressure) {
/* 138 */     this.mPointProcessorObj.onMove(x, y, pressure);
/*     */     
/* 140 */     if (!this.mIsFirstTouch) {
/* 141 */       this.mLeft = Math.min(x, this.mLeft);
/* 142 */       this.mTop = Math.max(y, this.mTop);
/* 143 */       this.mRight = Math.max(x, this.mRight);
/* 144 */       this.mBottom = Math.min(y, this.mBottom);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onTouchUp(float x, float y, float pressure) {
/* 150 */     this.mPointProcessorObj.onUp(x, y, pressure);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 156 */     canvas.drawBitmap(this.mBitmap, 0.0F, 0.0F, null);
/* 157 */     super.onDraw(canvas);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(InkListener listener) {
/* 166 */     if (!this.mListeners.contains(listener)) {
/* 167 */       this.mListeners.add(listener);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(InkListener listener) {
/* 177 */     this.mListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPressureSensitivity(boolean isEnabled) {
/* 186 */     this.mIsPressureSensitive = isEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(@ColorInt int color) {
/* 195 */     this.mStrokeColor = color;
/* 196 */     if (this.mPointProcessorObj != null) {
/* 197 */       this.mPointProcessorObj.setColorWithRedraw(color);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 205 */     resetState();
/* 206 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetState() {
/* 211 */     if (this.mPointProcessorObj != null) {
/* 212 */       this.mPointProcessorObj.destroy();
/* 213 */       this.mPointProcessorObj = null;
/*     */     } 
/*     */ 
/*     */     
/* 217 */     if (this.mBitmap != null) {
/* 218 */       this.mBitmap.recycle();
/* 219 */       this.mBitmap = null;
/*     */     } 
/*     */     
/* 222 */     initializeState();
/*     */   }
/*     */   
/*     */   private void initializeState() {
/* 226 */     if (this.mBitmap == null && this.mPointProcessorObj == null) {
/* 227 */       this.mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
/*     */       
/* 229 */       PointProcessor.DrawCallback drawCallback = new PointProcessor.DrawCallback()
/*     */         {
/*     */           public void onDrawInfoReceived(@NonNull InkDrawInfo drawInfo, @NonNull Bitmap bitmap) {
/* 232 */             Utils.throwIfOnMainThread();
/* 233 */             VariableWidthSignatureView.this.mBitmap = bitmap;
/* 234 */             VariableWidthSignatureView.this.postInvalidate(drawInfo.left, drawInfo.top, drawInfo.right, drawInfo.bottom);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onComplete(List<StrokeOutlineResult> strokeOutlineResults) {
/* 239 */             for (InkListener listener : VariableWidthSignatureView.this.mListeners) {
/* 240 */               listener.onInkCompleted(VariableWidthSignatureView.this.getStrokes(strokeOutlineResults));
/*     */             }
/* 242 */             VariableWidthSignatureView.this.resetState();
/*     */           }
/*     */         };
/* 245 */       this
/*     */         
/* 247 */         .mPointProcessorObj = new PointProcessor(getWidth(), getHeight(), this.mStrokeColor, this.mStrokeWidth, 1.0D, drawCallback);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 253 */       this.mIsFirstTouch = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setStrokeWidth(float strokeWidth) {
/* 258 */     this.mStrokeWidth = strokeWidth;
/* 259 */     if (this.mPointProcessorObj != null) {
/* 260 */       this.mPointProcessorObj.setStrokeWidth(strokeWidth);
/*     */     }
/*     */   }
/*     */   
/*     */   public RectF getBoundingBox() {
/* 265 */     float strokeWidthBuffer = this.mStrokeWidth * 1.5F;
/* 266 */     return new RectF(this.mLeft - strokeWidthBuffer, this.mTop + strokeWidthBuffer, this.mRight + strokeWidthBuffer, this.mBottom - strokeWidthBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<double[]> getStrokes(@NonNull List<StrokeOutlineResult> allStrokeOutlines) {
/* 273 */     List<double[]> outlines = (List)new ArrayList<>();
/* 274 */     for (StrokeOutlineResult outline : allStrokeOutlines) {
/* 275 */       outlines.add(outline.strokeOutline);
/*     */     }
/* 277 */     return outlines;
/*     */   }
/*     */   public static interface InkListener {
/*     */     void onInkStarted();
/*     */     void onInkCompleted(List<double[]> param1List); }
/*     */   
/*     */   public void finish() {
/* 284 */     if (this.mPointProcessorObj != null)
/* 285 */       this.mPointProcessorObj.finish(); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\signature\VariableWidthSignatureView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */