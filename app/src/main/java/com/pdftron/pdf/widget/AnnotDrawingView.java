/*     */ package com.pdftron.pdf.widget;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.util.AttributeSet;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.CurvePainter;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.RotateInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.model.ink.InkItem;
/*     */ import com.pdftron.pdf.model.ink.PressureInkItem;
/*     */ import com.pdftron.pdf.tools.FreehandCreate;
/*     */ import com.pdftron.pdf.tools.RulerCreate;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.pdf.utils.PressureInkUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotDrawingView
/*     */   extends AppCompatImageView
/*     */ {
/*     */   private AnnotViewImpl mAnnotViewImpl;
/*  46 */   private RectF mOval = new RectF();
/*     */   
/*     */   private int mPageNum;
/*  49 */   private PointF mPt3 = new PointF(0.0F, 0.0F);
/*  50 */   private PointF mPt4 = new PointF(0.0F, 0.0F);
/*  51 */   private PointF mPt5 = new PointF(0.0F, 0.0F);
/*  52 */   private PointF mPt6 = new PointF(0.0F, 0.0F);
/*     */   
/*     */   private int mXOffset;
/*     */   
/*     */   private int mYOffset;
/*  57 */   private Path mOnDrawPath = new Path();
/*     */   
/*  59 */   private RectF mOffsetRect = new RectF();
/*     */ 
/*     */   
/*  62 */   private RectF mTempRect = new RectF();
/*     */   
/*     */   private String mIcon;
/*     */   @Nullable
/*     */   private Drawable mIconDrawable;
/*     */   @NonNull
/*  68 */   private ArrayList<InkItem> mInks = new ArrayList<>();
/*     */   
/*  70 */   private PointF mInkOffset = new PointF();
/*     */   
/*     */   private float mInitialWidthScreen;
/*     */   private float mInitialHeightScreen;
/*     */   private float mScaleWidthScreen;
/*     */   private float mScaleHeightScreen;
/*     */   private boolean mInitRectSet;
/*     */   @NonNull
/*  78 */   private RectF mDownRect = new RectF();
/*     */   
/*     */   private RectF mInkDownRect;
/*  81 */   private Matrix mInkTransform = new Matrix();
/*     */   
/*     */   private float mRotDegree;
/*     */   
/*     */   private float mRotDegreeSave;
/*     */   private boolean mRotating;
/*     */   private boolean mRotated;
/*     */   private Integer mSnapDegree;
/*     */   private boolean mCanDraw;
/*     */   private Bitmap mAnnotBitmap;
/*     */   
/*     */   public AnnotDrawingView(Context context) {
/*  93 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public AnnotDrawingView(Context context, AttributeSet attrs) {
/*  97 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public AnnotDrawingView(Context context, AttributeSet attrs, int defStyleAttr) {
/* 101 */     super(context, attrs, defStyleAttr);
/*     */     
/* 103 */     init(context);
/*     */   }
/*     */   
/*     */   private void init(Context context) {
/* 107 */     this.mAnnotViewImpl = new AnnotViewImpl(context);
/*     */   }
/*     */   
/*     */   public void setCanDraw(boolean canDraw) {
/* 111 */     this.mCanDraw = canDraw;
/*     */   }
/*     */   
/*     */   public boolean getCanDraw() {
/* 115 */     return this.mCanDraw;
/*     */   }
/*     */   
/*     */   public void setAnnotStyle(AnnotViewImpl annotViewImpl) {
/* 119 */     this.mAnnotViewImpl = annotViewImpl;
/* 120 */     updateIcon(this.mAnnotViewImpl.mAnnotStyle.getIcon());
/*     */   }
/*     */   
/*     */   public void setAnnotBitmap(Bitmap bitmap) {
/* 124 */     this.mAnnotBitmap = bitmap;
/*     */     
/* 126 */     this.mDownRect.set(this.mAnnotViewImpl.mAnnotRectF);
/* 127 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setCurvePainter(CurvePainter curvePainter) {
/* 131 */     if (curvePainter == null) {
/*     */       return;
/*     */     }
/* 134 */     if (this.mAnnotViewImpl.mCurvePainter != null && this.mRotated) {
/*     */       return;
/*     */     }
/*     */     
/* 138 */     this.mAnnotViewImpl.mCurvePainter = curvePainter;
/* 139 */     if (curvePainter.getRect() != null) {
/* 140 */       this.mInitialWidthScreen = this.mScaleWidthScreen = curvePainter.getRect().width();
/* 141 */       this.mInitialHeightScreen = this.mScaleHeightScreen = curvePainter.getRect().height();
/*     */     } 
/* 143 */     invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isSizeOfAnnot() {
/* 148 */     return (ToolConfig.getInstance().getAnnotationHandlerToolMode(this.mAnnotViewImpl.mAnnotStyle.getAnnotType()) == ToolManager.ToolMode.ANNOT_EDIT || this.mAnnotViewImpl.mAnnotStyle
/* 149 */       .getAnnotType() == 1 || this.mAnnotViewImpl.mAnnotStyle
/* 150 */       .getAnnotType() == 19);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initInkItem(Annot inkAnnot, int pageNum, PointF offset) {
/* 157 */     if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 14 || this.mAnnotViewImpl
/* 158 */       .isFreeHighlighter()) {
/*     */       try {
/* 160 */         if (this.mInks.isEmpty()) {
/* 161 */           InkItem item; Ink ink = new Ink(inkAnnot);
/* 162 */           Rect rect = inkAnnot.getRect();
/* 163 */           rect.normalize();
/*     */ 
/*     */           
/* 166 */           if (PressureInkUtils.isPressureSensitive((Annot)ink)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 177 */             PressureInkItem pressureInkItem = new PressureInkItem(UUID.randomUUID().toString(), null, null, FreehandCreate.createStrokeListFromArrayObj(ink.getSDFObj().findObj("InkList")), PressureInkUtils.getThicknessList(ink), pageNum, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mOpacity, this.mAnnotViewImpl.mThickness, (float)this.mAnnotViewImpl.mPdfViewCtrl.getZoom() * this.mAnnotViewImpl.mThickness, false);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           }
/*     */           else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 189 */             item = new InkItem(UUID.randomUUID().toString(), null, FreehandCreate.createStrokeListFromArrayObj(ink.getSDFObj().findObj("InkList")), pageNum, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mOpacity, this.mAnnotViewImpl.mThickness, (float)this.mAnnotViewImpl.mPdfViewCtrl.getZoom() * this.mAnnotViewImpl.mThickness, false);
/*     */           } 
/*     */ 
/*     */           
/* 193 */           item.getPaint(this.mAnnotViewImpl.mPdfViewCtrl).setColor(Utils.getPostProcessedColor(this.mAnnotViewImpl.mPdfViewCtrl, this.mAnnotViewImpl.mStrokeColor));
/* 194 */           if (this.mAnnotViewImpl.isFreeHighlighter()) {
/* 195 */             item.getPaint(this.mAnnotViewImpl.mPdfViewCtrl).setAlpha((int)(255.0F * this.mAnnotViewImpl.mOpacity * 0.8F));
/*     */           }
/* 197 */           this.mInks.add(item);
/* 198 */           this.mInkOffset.set(offset);
/*     */         } 
/* 200 */       } catch (Exception ex) {
/* 201 */         ex.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateColor(int color) {
/* 207 */     this.mAnnotViewImpl.updateColor(color);
/* 208 */     if (!Utils.isNullOrEmpty(this.mIcon)) {
/* 209 */       updateIcon(this.mIcon);
/*     */     }
/* 211 */     if (!this.mInks.isEmpty()) {
/* 212 */       ArrayList<InkItem> newInkItems = new ArrayList<>();
/* 213 */       for (InkItem inkItem : this.mInks) {
/*     */         InkItem newInkItem;
/* 215 */         if (inkItem instanceof PressureInkItem) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 226 */           PressureInkItem pressureInkItem = new PressureInkItem(inkItem.id, inkItem.currentActiveStroke, ((PressureInkItem)inkItem).currentActivePressure, inkItem.finishedStrokes, ((PressureInkItem)inkItem).finishedPressures, inkItem.pageNumber, color, this.mAnnotViewImpl.mPaint.getAlpha() / 255.0F, inkItem.baseThickness, this.mAnnotViewImpl.mPaint.getStrokeWidth(), inkItem.isStylus);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 238 */           newInkItem = new InkItem(inkItem.id, inkItem.currentActiveStroke, inkItem.finishedStrokes, inkItem.pageNumber, color, this.mAnnotViewImpl.mPaint.getAlpha() / 255.0F, inkItem.baseThickness, this.mAnnotViewImpl.mPaint.getStrokeWidth(), inkItem.isStylus);
/*     */         } 
/*     */ 
/*     */         
/* 242 */         newInkItems.add(newInkItem);
/*     */       } 
/* 244 */       this.mInks = newInkItems;
/*     */     } 
/* 246 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateFillColor(int color) {
/* 250 */     this.mAnnotViewImpl.updateFillColor(color);
/* 251 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateThickness(float thickness) {
/* 255 */     this.mAnnotViewImpl.updateThickness(thickness);
/* 256 */     if (!this.mInks.isEmpty()) {
/* 257 */       ArrayList<InkItem> newInkItems = new ArrayList<>();
/* 258 */       for (InkItem inkItem : this.mInks) {
/*     */         InkItem newInkItem;
/* 260 */         if (inkItem instanceof PressureInkItem) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 271 */           PressureInkItem pressureInkItem = new PressureInkItem(inkItem.id, inkItem.currentActiveStroke, ((PressureInkItem)inkItem).currentActivePressure, inkItem.finishedStrokes, ((PressureInkItem)inkItem).finishedPressures, inkItem.pageNumber, this.mAnnotViewImpl.mPaint.getColor(), this.mAnnotViewImpl.mPaint.getAlpha() / 255.0F, thickness, (float)(thickness * this.mAnnotViewImpl.mPdfViewCtrl.getZoom()), inkItem.isStylus);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 283 */           newInkItem = new InkItem(inkItem.id, inkItem.currentActiveStroke, inkItem.finishedStrokes, inkItem.pageNumber, this.mAnnotViewImpl.mPaint.getColor(), this.mAnnotViewImpl.mPaint.getAlpha() / 255.0F, thickness, (float)(thickness * this.mAnnotViewImpl.mPdfViewCtrl.getZoom()), inkItem.isStylus);
/*     */         } 
/*     */ 
/*     */         
/* 287 */         newInkItems.add(newInkItem);
/*     */       } 
/* 289 */       this.mInks = newInkItems;
/*     */     } 
/* 291 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateOpacity(float opacity) {
/* 295 */     this.mAnnotViewImpl.updateOpacity(opacity);
/* 296 */     if (!Utils.isNullOrEmpty(this.mIcon)) {
/* 297 */       updateIcon(this.mIcon);
/*     */     }
/* 299 */     if (!this.mInks.isEmpty()) {
/* 300 */       ArrayList<InkItem> newInkItems = new ArrayList<>();
/* 301 */       for (InkItem inkItem : this.mInks) {
/*     */         InkItem newInkItem;
/*     */         
/* 304 */         if (inkItem instanceof PressureInkItem) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 315 */           PressureInkItem pressureInkItem = new PressureInkItem(inkItem.id, inkItem.currentActiveStroke, ((PressureInkItem)inkItem).currentActivePressure, inkItem.finishedStrokes, ((PressureInkItem)inkItem).finishedPressures, inkItem.pageNumber, this.mAnnotViewImpl.mPaint.getColor(), opacity, inkItem.baseThickness, this.mAnnotViewImpl.mPaint.getStrokeWidth(), inkItem.isStylus);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 327 */           newInkItem = new InkItem(inkItem.id, inkItem.currentActiveStroke, inkItem.finishedStrokes, inkItem.pageNumber, this.mAnnotViewImpl.mPaint.getColor(), opacity, inkItem.baseThickness, this.mAnnotViewImpl.mPaint.getStrokeWidth(), inkItem.isStylus);
/*     */         } 
/*     */ 
/*     */         
/* 331 */         newInkItems.add(newInkItem);
/*     */       } 
/* 333 */       this.mInks = newInkItems;
/*     */     } 
/* 335 */     invalidate();
/*     */   }
/*     */   
/*     */   public void updateRulerItem(RulerItem rulerItem) {
/* 339 */     this.mAnnotViewImpl.updateRulerItem(rulerItem);
/* 340 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setZoom(double zoom) {
/* 344 */     this.mAnnotViewImpl.setZoom(zoom);
/*     */   }
/*     */   
/*     */   public void setAnnotRect(@Nullable RectF rect) {
/* 348 */     if (null == rect) {
/*     */       return;
/*     */     }
/* 351 */     RectF inflatedRect = null;
/*     */     
/*     */     try {
/* 354 */       double borderWidth = this.mAnnotViewImpl.mAnnotStyle.getThickness() * this.mAnnotViewImpl.mZoom;
/* 355 */       Rect pRect = new Rect(rect.left, rect.top, rect.right, rect.bottom);
/* 356 */       pRect.normalize();
/* 357 */       if (pRect.getWidth() > borderWidth && pRect.getHeight() > borderWidth) {
/* 358 */         pRect.inflate(-borderWidth / 2.0D);
/*     */       }
/* 360 */       inflatedRect = new RectF((float)pRect.getX1(), (float)pRect.getY1(), (float)pRect.getX2(), (float)pRect.getY2());
/* 361 */     } catch (Exception ex) {
/* 362 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     
/* 365 */     if (!this.mInitRectSet) {
/* 366 */       this.mInitialWidthScreen = rect.width();
/* 367 */       this.mInitialHeightScreen = rect.height();
/* 368 */       this.mScaleWidthScreen = this.mInitialWidthScreen;
/* 369 */       this.mScaleHeightScreen = this.mInitialHeightScreen;
/* 370 */       this.mDownRect.set(rect);
/* 371 */       if (inflatedRect != null) {
/* 372 */         this.mInkDownRect = new RectF(inflatedRect);
/*     */       }
/*     */       
/* 375 */       this.mInitRectSet = true;
/*     */     } 
/* 377 */     this.mAnnotViewImpl.mPt1.set(rect.left, rect.top);
/* 378 */     this.mAnnotViewImpl.mPt2.set(rect.right, rect.bottom);
/*     */     
/* 380 */     this.mScaleWidthScreen = rect.width();
/* 381 */     this.mScaleHeightScreen = rect.height();
/*     */     
/* 383 */     this.mAnnotViewImpl.mAnnotRectF.set(rect);
/* 384 */     rect.round(this.mAnnotViewImpl.mAnnotRect);
/*     */     
/* 386 */     if (this.mInkDownRect != null && inflatedRect != null) {
/* 387 */       this.mInkTransform.setRectToRect(this.mInkDownRect, inflatedRect, Matrix.ScaleToFit.FILL);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setOffset(int x, int y) {
/* 392 */     this.mXOffset = x;
/* 393 */     this.mYOffset = y;
/* 394 */     invalidate();
/*     */   }
/*     */   
/*     */   public void setPageNum(int pageNum) {
/* 398 */     this.mPageNum = pageNum;
/*     */   }
/*     */   
/*     */   public void updateIcon(String icon) {
/* 402 */     this.mIcon = icon;
/*     */     
/* 404 */     this.mIconDrawable = AnnotStyle.getIconDrawable(getContext(), this.mIcon, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mOpacity);
/*     */   }
/*     */   
/*     */   public boolean hasIcon() {
/* 408 */     if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 0) {
/* 409 */       return (this.mIconDrawable != null);
/*     */     }
/* 411 */     return true;
/*     */   }
/*     */   
/*     */   public RotateInfo handleRotation(PointF downPt, PointF movePt, boolean done) {
/* 415 */     this.mRotating = !done;
/* 416 */     this.mRotated = true;
/* 417 */     PointF pivot = center();
/*     */     
/* 419 */     this.mRotDegree = (float)Utils.angleBetweenTwoPointsWithPivot(downPt.x, downPt.y, movePt.x, movePt.y, pivot.x, pivot.y);
/* 420 */     if (done) {
/* 421 */       this.mRotDegreeSave += this.mRotDegree;
/*     */     }
/* 423 */     invalidate();
/* 424 */     return new RotateInfo(-this.mRotDegree, pivot);
/*     */   }
/*     */   
/*     */   public void snapToDegree(@Nullable Integer degree, float startDegree) {
/* 428 */     this.mSnapDegree = degree;
/* 429 */     if (this.mSnapDegree != null) {
/* 430 */       this.mRotDegree = -(this.mSnapDegree.intValue() - startDegree);
/*     */     }
/* 432 */     invalidate();
/*     */   }
/*     */   
/*     */   public PointF center() {
/* 436 */     return new PointF(this.mAnnotViewImpl.mAnnotRectF.centerX(), this.mAnnotViewImpl.mAnnotRectF.centerY());
/*     */   }
/*     */   
/*     */   private void drawSelectionBox(Canvas canvas) {
/* 440 */     if (!this.mAnnotViewImpl.mCanDrawCtrlPts) {
/*     */       return;
/*     */     }
/* 443 */     if (this.mAnnotViewImpl.isAnnotEditLine() || this.mAnnotViewImpl.isAnnotEditAdvancedShape()) {
/*     */       return;
/*     */     }
/* 446 */     if (!this.mAnnotViewImpl.mHasSelectionPermission) {
/*     */       return;
/*     */     }
/* 449 */     float left = (this.mAnnotViewImpl.mCtrlPts[3]).x;
/* 450 */     float top = (this.mAnnotViewImpl.mCtrlPts[3]).y;
/* 451 */     float right = (this.mAnnotViewImpl.mCtrlPts[1]).x;
/* 452 */     float bottom = (this.mAnnotViewImpl.mCtrlPts[1]).y;
/* 453 */     DrawingUtils.drawSelectionBox(this.mAnnotViewImpl.mCtrlPtsPaint, getContext(), canvas, left, top, right, bottom, this.mAnnotViewImpl.mHasSelectionPermission);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canUseCoreRender() {
/* 459 */     return (this.mAnnotViewImpl.mAnnotStyle.hasAppearance() || this.mAnnotViewImpl.isStamp());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onDraw(Canvas canvas) {
/*     */     try {
/* 465 */       canvas.save();
/* 466 */       PointF centerPt = center();
/* 467 */       if (centerPt != null) {
/* 468 */         float degree = this.mRotating ? (this.mRotDegreeSave + this.mRotDegree) : this.mRotDegreeSave;
/* 469 */         canvas.rotate(degree, centerPt.x, centerPt.y);
/*     */       } 
/* 471 */       if (this.mAnnotViewImpl.mCurvePainter != null && canUseCoreRender() && this.mCanDraw) {
/* 472 */         if (isSizeOfAnnot()) {
/* 473 */           if (this.mAnnotViewImpl.mCurvePainter.getBitmap() != null) {
/* 474 */             Paint paint = this.mAnnotViewImpl.mBmpPaint;
/* 475 */             if (this.mAnnotViewImpl.isFreeHighlighter() && !this.mAnnotViewImpl.isNightMode()) {
/* 476 */               paint = this.mAnnotViewImpl.mBmpMultBlendPaint;
/*     */             }
/* 478 */             this.mOffsetRect.left = (this.mAnnotViewImpl.mCurvePainter.getRect()).left + this.mAnnotViewImpl.mAnnotRectF.left;
/* 479 */             this.mOffsetRect.right = this.mOffsetRect.left + this.mAnnotViewImpl.mCurvePainter.getRect().width();
/* 480 */             this.mOffsetRect.top = (this.mAnnotViewImpl.mCurvePainter.getRect()).top + this.mAnnotViewImpl.mAnnotRectF.top;
/* 481 */             this.mOffsetRect.bottom = this.mOffsetRect.top + this.mAnnotViewImpl.mCurvePainter.getRect().height();
/* 482 */             canvas.drawBitmap(this.mAnnotViewImpl.mCurvePainter.getBitmap(), null, this.mOffsetRect, paint);
/*     */           } else {
/* 484 */             this.mAnnotViewImpl.mCurvePainter.draw(canvas, this.mAnnotViewImpl.mAnnotRectF.left, this.mAnnotViewImpl.mAnnotRectF.top, (this.mScaleWidthScreen / this.mInitialWidthScreen) * this.mAnnotViewImpl.mZoom, (this.mScaleHeightScreen / this.mInitialHeightScreen) * this.mAnnotViewImpl.mZoom, this.mAnnotViewImpl.mZoom, this.mAnnotViewImpl.mZoom);
/*     */           }
/*     */         
/*     */         }
/*     */         else {
/*     */           
/* 490 */           RectF rect = this.mAnnotViewImpl.mAnnotRectF;
/* 491 */           if (this.mAnnotViewImpl.mCurvePainter.getBitmap() != null) {
/*     */             
/* 493 */             canvas.drawBitmap(this.mAnnotViewImpl.mCurvePainter.getBitmap(), rect.left + this.mXOffset, rect.top + this.mYOffset, this.mAnnotViewImpl.mBmpPaint);
/*     */           } else {
/* 495 */             this.mAnnotViewImpl.mCurvePainter.draw(canvas, rect.left + this.mXOffset, rect.top + this.mYOffset, this.mAnnotViewImpl.mZoom, this.mAnnotViewImpl.mZoom, this.mAnnotViewImpl.mZoom, this.mAnnotViewImpl.mZoom);
/*     */           }
/*     */         
/*     */         }
/*     */       
/* 500 */       } else if (this.mCanDraw) {
/* 501 */         if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 4) {
/* 502 */           DrawingUtils.drawRectangle(canvas, this.mAnnotViewImpl.mPt1, this.mAnnotViewImpl.mPt2, this.mAnnotViewImpl.mThicknessDraw, this.mAnnotViewImpl.mFillColor, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mPaint);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 507 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 5) {
/* 508 */           DrawingUtils.drawOval(canvas, this.mAnnotViewImpl.mPt1, this.mAnnotViewImpl.mPt2, this.mAnnotViewImpl.mThicknessDraw, this.mOval, this.mAnnotViewImpl.mFillColor, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mPaint);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/* 514 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 3) {
/* 515 */           DrawingUtils.drawLine(canvas, this.mAnnotViewImpl.mVertices.get(0), this.mAnnotViewImpl.mVertices.get(1), this.mAnnotViewImpl.mPaint);
/* 516 */         } else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 1001) {
/* 517 */           DrawingUtils.calcArrow(this.mAnnotViewImpl.mVertices.get(0), this.mAnnotViewImpl.mVertices.get(1), this.mPt3, this.mPt4, this.mAnnotViewImpl.mThickness, this.mAnnotViewImpl.mZoom);
/*     */           
/* 519 */           DrawingUtils.drawArrow(canvas, this.mAnnotViewImpl.mVertices.get(0), this.mAnnotViewImpl.mVertices.get(1), this.mPt3, this.mPt4, this.mOnDrawPath, this.mAnnotViewImpl.mPaint);
/*     */         }
/* 521 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 1006) {
/* 522 */           DrawingUtils.calcRuler(this.mAnnotViewImpl.mVertices.get(0), this.mAnnotViewImpl.mVertices.get(1), this.mPt3, this.mPt4, this.mPt5, this.mPt6, this.mAnnotViewImpl.mThickness, this.mAnnotViewImpl.mZoom);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 527 */           double[] pts1 = this.mAnnotViewImpl.mPdfViewCtrl.convScreenPtToPagePt(((PointF)this.mAnnotViewImpl.mVertices.get(0)).x, ((PointF)this.mAnnotViewImpl.mVertices.get(0)).y, this.mPageNum);
/* 528 */           double[] pts2 = this.mAnnotViewImpl.mPdfViewCtrl.convScreenPtToPagePt(((PointF)this.mAnnotViewImpl.mVertices.get(1)).x, ((PointF)this.mAnnotViewImpl.mVertices.get(1)).y, this.mPageNum);
/*     */           
/* 530 */           String text = RulerCreate.getLabel(this.mAnnotViewImpl.mAnnotStyle.getRulerItem(), pts1[0], pts1[1], pts2[0], pts2[1]);
/*     */           
/* 532 */           DrawingUtils.drawRuler(canvas, this.mAnnotViewImpl.mVertices.get(0), this.mAnnotViewImpl.mVertices.get(1), this.mPt3, this.mPt4, this.mPt5, this.mPt6, this.mOnDrawPath, this.mAnnotViewImpl.mPaint, text, this.mAnnotViewImpl.mZoom);
/*     */         
/*     */         }
/* 535 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 7 || this.mAnnotViewImpl.mAnnotStyle
/* 536 */           .getAnnotType() == 1008) {
/* 537 */           DrawingUtils.drawPolyline(this.mAnnotViewImpl.mPdfViewCtrl, this.mPageNum, canvas, this.mAnnotViewImpl.mVertices, this.mOnDrawPath, this.mAnnotViewImpl.mPaint, this.mAnnotViewImpl.mStrokeColor);
/*     */         }
/* 539 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 6 || this.mAnnotViewImpl.mAnnotStyle
/* 540 */           .getAnnotType() == 1009) {
/* 541 */           DrawingUtils.drawPolygon(this.mAnnotViewImpl.mPdfViewCtrl, this.mPageNum, canvas, this.mAnnotViewImpl.mVertices, this.mOnDrawPath, this.mAnnotViewImpl.mPaint, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mFillColor, null);
/*     */         
/*     */         }
/* 544 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 1012) {
/* 545 */           DrawingUtils.drawPolygon(this.mAnnotViewImpl.mPdfViewCtrl, this.mPageNum, canvas, this.mAnnotViewImpl.mVertices, this.mOnDrawPath, this.mAnnotViewImpl.mPaint, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mFillColor, this.mInkTransform);
/*     */         
/*     */         }
/* 548 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 1005) {
/* 549 */           DrawingUtils.drawCloud(this.mAnnotViewImpl.mPdfViewCtrl, this.mPageNum, canvas, this.mAnnotViewImpl.mVertices, this.mOnDrawPath, this.mAnnotViewImpl.mPaint, this.mAnnotViewImpl.mStrokeColor, this.mAnnotViewImpl.mFillPaint, this.mAnnotViewImpl.mFillColor, this.mAnnotViewImpl.mAnnotStyle
/*     */               
/* 551 */               .getBorderEffectIntensity());
/* 552 */         } else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 14 || this.mAnnotViewImpl.mAnnotStyle
/* 553 */           .getAnnotType() == 1004) {
/* 554 */           DrawingUtils.drawInk(this.mAnnotViewImpl.mPdfViewCtrl, canvas, this.mInks, this.mInkTransform, this.mInkOffset);
/*     */         }
/* 556 */         else if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 0 && this.mIconDrawable != null && this.mAnnotBitmap == null) {
/* 557 */           this.mIconDrawable.setBounds(this.mAnnotViewImpl.mAnnotRect);
/* 558 */           this.mIconDrawable.draw(canvas);
/* 559 */         } else if (this.mAnnotBitmap != null) {
/*     */           
/* 561 */           if (this.mAnnotViewImpl.mAnnotStyle.getAnnotType() == 2 || this.mAnnotViewImpl.mAnnotStyle
/* 562 */             .getAnnotType() == 1011) {
/* 563 */             this.mTempRect.set(this.mAnnotViewImpl.mAnnotRectF.left, this.mAnnotViewImpl.mAnnotRectF.top, this.mAnnotViewImpl.mAnnotRectF.left + this.mDownRect
/*     */                 
/* 565 */                 .width(), this.mAnnotViewImpl.mAnnotRectF.top + this.mDownRect
/* 566 */                 .height());
/* 567 */             canvas.drawBitmap(this.mAnnotBitmap, null, this.mTempRect, this.mAnnotViewImpl.mBmpPaint);
/*     */           }
/*     */           else {
/*     */             
/* 571 */             canvas.drawBitmap(this.mAnnotBitmap, null, this.mAnnotViewImpl.mAnnotRectF, this.mAnnotViewImpl.mBmpPaint);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 577 */       if (!this.mRotated) {
/* 578 */         drawSelectionBox(canvas);
/*     */       }
/*     */       
/* 581 */       canvas.restore();
/*     */       
/* 583 */       if (this.mSnapDegree != null) {
/* 584 */         DrawingUtils.drawGuideline(this.mSnapDegree.intValue(), this.mAnnotViewImpl.mRotateCenterRadius, canvas, this.mAnnotViewImpl.mBBox, this.mAnnotViewImpl.mGuidelinePath, this.mAnnotViewImpl.mGuidelinePaint);
/*     */       }
/*     */ 
/*     */       
/* 588 */       if (this.mAnnotViewImpl.mSnapMode != null) {
/* 589 */         DrawingUtils.drawGuideline(this.mAnnotViewImpl.mSnapMode, this.mAnnotViewImpl.mGuidelinExtend, canvas, this.mAnnotViewImpl.mBBox, this.mAnnotViewImpl.mGuidelinePath, this.mAnnotViewImpl.mGuidelinePaint);
/*     */       }
/*     */     }
/* 592 */     catch (Exception ex) {
/* 593 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\AnnotDrawingView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */