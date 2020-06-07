/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.RectF;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.RelativeLayout;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.ImageMemoryCache;
/*     */ import java.util.LinkedList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SignatureView
/*     */   extends View
/*     */ {
/*     */   private SignatureViewListener mSignatureViewListener;
/*     */   private Bitmap mBitmap;
/*     */   private Canvas mCanvas;
/*     */   private Path mPath;
/*     */   private Paint mBitmapPaint;
/*     */   private Paint mPencilPaint;
/*     */   private int mColor;
/*     */   private float mStrokeWidth;
/*     */   private LinkedList<PointF> mPathPoints;
/*     */   private LinkedList<LinkedList<PointF>> mSignaturePathPoints;
/*     */   private LinkedList<Path> mPaths;
/*  43 */   private float mLeft = 0.0F;
/*  44 */   private float mTop = 0.0F;
/*  45 */   private float mRight = 0.0F;
/*  46 */   private float mBottom = 0.0F; private boolean mIsFirstPoint = true;
/*     */   private float mX;
/*     */   
/*     */   public SignatureView(Context context) {
/*  50 */     super(context);
/*     */     
/*  52 */     this.mPath = new Path();
/*  53 */     this.mPaths = new LinkedList<>();
/*     */     
/*  55 */     this.mPencilPaint = new Paint();
/*  56 */     this.mPencilPaint.setAntiAlias(true);
/*  57 */     this.mPencilPaint.setDither(true);
/*  58 */     this.mPencilPaint.setStyle(Paint.Style.STROKE);
/*  59 */     this.mPencilPaint.setStrokeCap(Paint.Cap.ROUND);
/*  60 */     this.mPencilPaint.setStrokeJoin(Paint.Join.ROUND);
/*     */     
/*  62 */     this.mBitmapPaint = new Paint(4);
/*     */     
/*  64 */     this.mPathPoints = new LinkedList<>();
/*  65 */     this.mSignaturePathPoints = new LinkedList<>();
/*     */     
/*  67 */     RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
/*  68 */     layoutParams.addRule(13);
/*  69 */     setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/*     */   }
/*     */   private float mY; private static final float TOUCH_TOLERANCE = 1.0F;
/*     */   public void setSignatureViewListener(SignatureViewListener listener) {
/*  73 */     this.mSignatureViewListener = listener;
/*     */   }
/*     */   
/*     */   public void setup(int color, float thickness) {
/*  77 */     this.mColor = color;
/*  78 */     this.mStrokeWidth = thickness;
/*  79 */     this.mPencilPaint.setColor(this.mColor);
/*  80 */     this.mPencilPaint.setStrokeWidth(this.mStrokeWidth);
/*     */     
/*  82 */     this.mBitmapPaint.setColor(this.mColor);
/*  83 */     this.mBitmapPaint.setStrokeWidth(this.mStrokeWidth);
/*     */   }
/*     */   
/*     */   public void eraseSignature() {
/*  87 */     this.mPathPoints.clear();
/*  88 */     this.mSignaturePathPoints.clear();
/*  89 */     this.mIsFirstPoint = true;
/*     */ 
/*     */     
/*  92 */     this.mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
/*  93 */     this.mPath = new Path();
/*  94 */     this.mPaths.clear();
/*  95 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateStrokeThickness(float thickness) {
/*  99 */     this.mStrokeWidth = thickness;
/*     */     
/* 101 */     this.mBitmapPaint.setStrokeWidth(thickness);
/* 102 */     this.mPencilPaint.setStrokeWidth(thickness);
/*     */     
/* 104 */     this.mCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
/*     */     
/* 106 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateStrokeColor(int color) {
/* 111 */     this.mColor = color;
/* 112 */     this.mBitmapPaint.setColor(color);
/* 113 */     this.mPencilPaint.setColor(color);
/*     */     
/* 115 */     invalidate();
/*     */   }
/*     */   
/*     */   public void clearResources() {
/* 119 */     ImageMemoryCache.getInstance().addBitmapToReusableSet(this.mBitmap);
/* 120 */     this.mBitmap = null;
/*     */   }
/*     */   
/*     */   public LinkedList<LinkedList<PointF>> getSignaturePaths() {
/* 124 */     return this.mSignaturePathPoints;
/*     */   }
/*     */   
/*     */   public RectF getBoundingBox() {
/* 128 */     return new RectF(this.mLeft, this.mTop, this.mRight, this.mBottom);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/* 133 */     if (canvas == null || this.mBitmap == null) {
/*     */       return;
/*     */     }
/*     */     
/* 137 */     for (Path p : this.mPaths) {
/* 138 */       canvas.drawPath(p, this.mPencilPaint);
/*     */     }
/* 140 */     canvas.drawPath(this.mPath, this.mPencilPaint);
/*     */     
/* 142 */     canvas.drawBitmap(this.mBitmap, 0.0F, 0.0F, this.mBitmapPaint);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onSizeChanged(int w, int h, int oldw, int oldh) {
/* 147 */     super.onSizeChanged(w, h, oldw, oldh);
/*     */     
/*     */     try {
/* 150 */       this.mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ALPHA_8);
/* 151 */     } catch (Exception e) {
/* 152 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 153 */       if (this.mSignatureViewListener != null) {
/* 154 */         this.mSignatureViewListener.onError();
/*     */       }
/*     */       return;
/*     */     } 
/* 158 */     this.mCanvas = new Canvas(this.mBitmap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void touch_start(float x, float y) {
/* 165 */     this.mPath = new Path();
/* 166 */     this.mPath.moveTo(x, y);
/* 167 */     this.mX = x;
/* 168 */     this.mY = y;
/*     */     
/* 170 */     this.mPathPoints = new LinkedList<>();
/* 171 */     this.mPathPoints.add(new PointF(x, y));
/*     */     
/* 173 */     if (this.mSignatureViewListener != null) {
/* 174 */       this.mSignatureViewListener.onTouchStart(x, y);
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (this.mIsFirstPoint) {
/* 179 */       this.mLeft = x;
/* 180 */       this.mTop = y;
/* 181 */       this.mRight = x;
/* 182 */       this.mBottom = y;
/* 183 */       this.mIsFirstPoint = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void touch_move(float x, float y) {
/* 189 */     float dx = Math.abs(x - this.mX);
/* 190 */     float dy = Math.abs(y - this.mY);
/* 191 */     if (dx >= 1.0F || dy >= 1.0F) {
/*     */       
/* 193 */       this.mPath.quadTo(this.mX, this.mY, (x + this.mX) / 2.0F, (y + this.mY) / 2.0F);
/* 194 */       this.mX = x;
/* 195 */       this.mY = y;
/* 196 */       this.mPathPoints.add(new PointF(x, y));
/*     */       
/* 198 */       this.mLeft = Math.min(x, this.mLeft);
/* 199 */       this.mTop = Math.max(y, this.mTop);
/* 200 */       this.mRight = Math.max(x, this.mRight);
/* 201 */       this.mBottom = Math.min(y, this.mBottom);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void touch_up() {
/* 206 */     this.mPath.lineTo(this.mX, this.mY);
/* 207 */     this.mPaths.add(this.mPath);
/*     */     
/* 209 */     this.mCanvas.drawPath(this.mPath, this.mPencilPaint);
/*     */     
/* 211 */     this.mPath = new Path();
/*     */     
/* 213 */     this.mSignaturePathPoints.add(this.mPathPoints);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onTouchEvent(MotionEvent event) {
/* 218 */     float x = event.getX();
/* 219 */     float y = event.getY();
/*     */     
/* 221 */     switch (event.getAction()) {
/*     */       case 0:
/* 223 */         touch_start(x, y);
/* 224 */         invalidate();
/*     */         break;
/*     */       case 2:
/* 227 */         touch_move(x, y);
/* 228 */         invalidate();
/*     */         break;
/*     */       case 1:
/* 231 */         touch_up();
/* 232 */         invalidate();
/*     */         break;
/*     */     } 
/* 235 */     return true;
/*     */   }
/*     */   
/*     */   public static interface SignatureViewListener {
/*     */     void onTouchStart(float param1Float1, float param1Float2);
/*     */     
/*     */     void onError();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\SignatureView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */