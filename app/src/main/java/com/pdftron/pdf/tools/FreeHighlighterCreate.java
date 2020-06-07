/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Ink;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.PathPool;
/*     */ import com.pdftron.pdf.utils.PointFPool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class FreeHighlighterCreate
/*     */   extends SimpleShapeCreate
/*     */ {
/*  41 */   private static final String TAG = FreeHighlighterCreate.class.getName();
/*     */   
/*     */   private static boolean sDebug;
/*     */   
/*     */   private static final float TOUCH_TOLERANCE = 1.0F;
/*     */   public static final float BLEND_OPACITY = 0.8F;
/*  47 */   private ArrayList<PointF> mScreenStrokePoints = new ArrayList<>();
/*  48 */   private ArrayList<PointF> mCanvasStrokePoints = new ArrayList<>();
/*     */   
/*     */   private int mPageForFreehandAnnot;
/*     */   private boolean mIsStartPointOutsidePage;
/*     */   private float mOriginalX;
/*     */   private float mOriginalY;
/*     */   private float mMinPointX;
/*     */   private float mMinPointY;
/*     */   private float mMaxpointX;
/*     */   private float mMaxPointY;
/*     */   
/*     */   public FreeHighlighterCreate(@NonNull PDFViewCtrl ctrl) {
/*  60 */     super(ctrl);
/*     */     
/*  62 */     this.mNextToolMode = getToolMode();
/*  63 */     this.mPaint.setStrokeJoin(Paint.Join.ROUND);
/*  64 */     this.mPaint.setStrokeCap(Paint.Cap.ROUND);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  72 */     return ToolManager.ToolMode.FREE_HIGHLIGHTER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  77 */     return 1004;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/*  86 */     if (this.mStrokeColor != color || this.mOpacity != opacity || this.mThickness != thickness) {
/*  87 */       super.setupAnnotProperty(color, opacity, thickness, fillColor, icon, pdfTronFontName);
/*     */       
/*  89 */       float zoom = (float)this.mPdfViewCtrl.getZoom();
/*  90 */       this.mThicknessDraw = this.mThickness * zoom;
/*  91 */       this.mPaint.setStrokeWidth(this.mThicknessDraw);
/*  92 */       color = Utils.getPostProcessedColor(this.mPdfViewCtrl, this.mStrokeColor);
/*  93 */       this.mPaint.setColor(color);
/*  94 */       this.mPaint.setAlpha((int)(255.0F * this.mOpacity * 0.8F));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 103 */     super.onDown(e);
/*     */     
/* 105 */     this.mOriginalX = this.mMinPointX = this.mMaxpointX = this.mPt1.x;
/* 106 */     this.mOriginalY = this.mMinPointY = this.mMaxPointY = this.mPt1.y;
/*     */     
/* 108 */     this.mPageForFreehandAnnot = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 109 */     this.mIsStartPointOutsidePage = (this.mPageForFreehandAnnot < 1);
/* 110 */     if (this.mIsStartPointOutsidePage) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     if (this.mPageCropOnClientF != null && (
/* 115 */       this.mPt1.x < this.mPageCropOnClientF.left || this.mPt1.x > this.mPageCropOnClientF.right || this.mPt1.y < this.mPageCropOnClientF.top || this.mPt1.y > this.mPageCropOnClientF.bottom)) {
/*     */ 
/*     */ 
/*     */       
/* 119 */       setNextToolModeHelper(ToolManager.ToolMode.ANNOT_EDIT);
/* 120 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 124 */     resetCurrentPaths();
/* 125 */     this.mCanvasStrokePoints.add(PointFPool.getInstance().obtain(this.mPt1.x, this.mPt1.y));
/* 126 */     PointF screenPoint = PointFPool.getInstance().obtain(e.getX(), e.getY());
/* 127 */     this.mScreenStrokePoints.add(screenPoint);
/*     */     
/* 129 */     this.mAnnotPushedBack = false;
/*     */     
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 139 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/* 141 */     if (this.mAllowTwoFingerScroll) {
/* 142 */       return false;
/*     */     }
/*     */     
/* 145 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 146 */       return false;
/*     */     }
/*     */     
/* 149 */     if (this.mIsStartPointOutsidePage) {
/* 150 */       return false;
/*     */     }
/*     */     
/* 153 */     int historySize = e2.getHistorySize();
/* 154 */     int pointerCount = e2.getPointerCount();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 160 */     for (int h = 0; h < historySize; h++) {
/* 161 */       if (pointerCount >= 1) {
/* 162 */         float f1 = e2.getHistoricalX(0, h);
/* 163 */         float f2 = e2.getHistoricalY(0, h);
/* 164 */         processMotionPoint(f1, f2);
/*     */       } 
/*     */     } 
/* 167 */     float x = e2.getX();
/* 168 */     float y = e2.getY();
/* 169 */     processMotionPoint(x, y);
/*     */     
/* 171 */     return true;
/*     */   }
/*     */   
/*     */   private void processMotionPoint(float x, float y) {
/* 175 */     int sx = this.mPdfViewCtrl.getScrollX();
/* 176 */     int sy = this.mPdfViewCtrl.getScrollY();
/* 177 */     float canvasX = x + sx;
/* 178 */     float canvasY = y + sy;
/*     */ 
/*     */     
/* 181 */     if (this.mPageCropOnClientF != null) {
/* 182 */       if (canvasX < this.mPageCropOnClientF.left) {
/* 183 */         canvasX = this.mPageCropOnClientF.left;
/* 184 */         x = canvasX - sx;
/* 185 */       } else if (canvasX > this.mPageCropOnClientF.right) {
/* 186 */         canvasX = this.mPageCropOnClientF.right;
/* 187 */         x = canvasX - sx;
/*     */       } 
/* 189 */       if (canvasY < this.mPageCropOnClientF.top) {
/* 190 */         canvasY = this.mPageCropOnClientF.top;
/* 191 */         y = canvasY - sy;
/* 192 */       } else if (canvasY > this.mPageCropOnClientF.bottom) {
/* 193 */         canvasY = this.mPageCropOnClientF.bottom;
/* 194 */         y = canvasY - sy;
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     float dx = Math.abs(canvasX - this.mOriginalX);
/* 199 */     float dy = Math.abs(canvasY - this.mOriginalY);
/* 200 */     if (dx >= 1.0F || dy >= 1.0F) {
/* 201 */       this.mOriginalX = canvasX;
/* 202 */       this.mOriginalY = canvasY;
/*     */       
/* 204 */       this.mScreenStrokePoints.add(PointFPool.getInstance().obtain(x, y));
/* 205 */       this.mCanvasStrokePoints.add(PointFPool.getInstance().obtain(canvasX, canvasY));
/*     */       
/* 207 */       this.mMinPointX = Math.min(canvasX, this.mMinPointX);
/* 208 */       this.mMinPointY = Math.min(canvasY, this.mMinPointY);
/* 209 */       this.mMaxpointX = Math.max(canvasX, this.mMaxpointX);
/* 210 */       this.mMaxPointY = Math.max(canvasY, this.mMaxPointY);
/*     */       
/* 212 */       int minX = (int)(this.mMinPointX - this.mThicknessDraw);
/* 213 */       int maxX = (int)Math.ceil((this.mMinPointY + this.mThicknessDraw));
/* 214 */       int minY = (int)(this.mMaxpointX - this.mThicknessDraw);
/* 215 */       int maxY = (int)Math.ceil((this.mMaxPointY + this.mThicknessDraw));
/*     */       
/* 217 */       this.mPdfViewCtrl.invalidate(minX, minY, maxX, maxY);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 227 */     if (this.mAllowTwoFingerScroll) {
/* 228 */       doneTwoFingerScrolling();
/* 229 */       return false;
/*     */     } 
/*     */     
/* 232 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 233 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 237 */     if (this.mPt1.x == this.mPt2.x && this.mPt1.y == this.mPt2.y) {
/* 238 */       resetPts();
/* 239 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 244 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/* 245 */       return true;
/*     */     }
/*     */     
/* 248 */     if (this.mIsStartPointOutsidePage) {
/* 249 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 253 */     this.mAllowOneFingerScrollWithStylus = (this.mStylusUsed && e.getToolType(0) != 2);
/* 254 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 255 */       return true;
/*     */     }
/*     */     
/* 258 */     float x = e.getX();
/* 259 */     float y = e.getY();
/* 260 */     processMotionPoint(x, y);
/*     */     
/* 262 */     this.mPdfViewCtrl.invalidate();
/* 263 */     createFreeHighlighter();
/* 264 */     resetCurrentPaths();
/*     */     
/* 266 */     setNextToolModeHelper();
/* 267 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/* 269 */     addOldTools();
/*     */     
/* 271 */     this.mAnnotPushedBack = true;
/*     */     
/* 273 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 281 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDoubleTap(MotionEvent e) {
/* 286 */     onDoubleTapEvent(e);
/* 287 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDoubleTapEvent(MotionEvent e) {
/* 297 */     if (e.getAction() == 2) {
/* 298 */       onMove(e, e, 0.0F, 0.0F);
/*     */     }
/*     */     
/* 301 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 309 */     if (this.mIsStartPointOutsidePage) {
/*     */       return;
/*     */     }
/*     */     
/* 313 */     this.mPaint.setAlpha((int)(255.0F * this.mOpacity * 0.8F));
/*     */ 
/*     */     
/* 316 */     Path path = createPathFromCanvasPts(this.mCanvasStrokePoints);
/* 317 */     if (this.mPdfViewCtrl.isMaintainZoomEnabled()) {
/* 318 */       canvas.save();
/*     */       try {
/* 320 */         canvas.translate(0.0F, -this.mPdfViewCtrl.getScrollYOffsetInTools(this.mPageForFreehandAnnot));
/* 321 */         canvas.drawPath(path, this.mPaint);
/*     */       } finally {
/* 323 */         canvas.restore();
/*     */       } 
/*     */     } else {
/* 326 */       canvas.drawPath(path, this.mPaint);
/*     */     } 
/*     */   }
/*     */   
/*     */   private Path createPathFromCanvasPts(ArrayList<PointF> points) {
/* 331 */     Path path = PathPool.getInstance().obtain();
/* 332 */     if (points.size() <= 1) {
/* 333 */       return path;
/*     */     }
/*     */     
/* 336 */     if (this.mIsStylus) {
/* 337 */       path.moveTo(((PointF)points.get(0)).x, ((PointF)points.get(0)).y);
/* 338 */       for (PointF point : points) {
/* 339 */         path.lineTo(point.x, point.y);
/*     */       }
/*     */     } else {
/*     */       
/* 343 */       double[] currentBeizerPts, inputLine = new double[points.size() * 2];
/* 344 */       for (int i = 0, cnt = points.size(); i < cnt; i++) {
/* 345 */         inputLine[i * 2] = ((PointF)points.get(i)).x;
/* 346 */         inputLine[i * 2 + 1] = ((PointF)points.get(i)).y;
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 351 */         currentBeizerPts = Ink.getBezierControlPoints(inputLine);
/* 352 */       } catch (Exception e) {
/* 353 */         return path;
/*     */       } 
/*     */       
/* 356 */       path.moveTo((float)currentBeizerPts[0], (float)currentBeizerPts[1]);
/* 357 */       for (int j = 2, k = currentBeizerPts.length; j < k; j += 6) {
/* 358 */         path.cubicTo((float)currentBeizerPts[j], (float)currentBeizerPts[j + 1], (float)currentBeizerPts[j + 2], (float)currentBeizerPts[j + 3], (float)currentBeizerPts[j + 4], (float)currentBeizerPts[j + 5]);
/*     */       }
/*     */     } 
/*     */     
/* 362 */     return path;
/*     */   }
/*     */   
/*     */   private void createFreeHighlighter() {
/* 366 */     ArrayList<Point> pagePoints = new ArrayList<>(this.mScreenStrokePoints.size());
/* 367 */     for (PointF point : this.mScreenStrokePoints) {
/* 368 */       double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(point.x, point.y, this.mPageForFreehandAnnot);
/* 369 */       Point p = new Point();
/* 370 */       p.x = pts[0];
/* 371 */       p.y = pts[1];
/* 372 */       pagePoints.add(p);
/*     */     } 
/*     */     
/* 375 */     Rect annotRect = Utils.getBBox(pagePoints);
/* 376 */     if (annotRect == null) {
/*     */       return;
/*     */     }
/* 379 */     annotRect.inflate(this.mThickness);
/*     */     
/* 381 */     boolean shouldUnlock = false;
/*     */     try {
/* 383 */       this.mPdfViewCtrl.docLock(true);
/* 384 */       shouldUnlock = true;
/*     */       
/* 386 */       Ink ink = Ink.create((Doc)this.mPdfViewCtrl.getDoc(), annotRect);
/* 387 */       setStyle(ink);
/*     */       
/* 389 */       int pointIdx = 0;
/* 390 */       for (Point point : pagePoints) {
/* 391 */         ink.setPoint(0, pointIdx++, point);
/*     */       }
/*     */       
/* 394 */       ink.refreshAppearance();
/*     */       
/* 396 */       setAnnot((Annot)ink, this.mPageForFreehandAnnot);
/* 397 */       buildAnnotBBox();
/*     */       
/* 399 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mPageForFreehandAnnot);
/* 400 */       page.annotPushBack(this.mAnnot);
/*     */       
/* 402 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 404 */       raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/* 405 */     } catch (Exception e) {
/* 406 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 407 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/* 408 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 410 */       if (shouldUnlock) {
/* 411 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setStyle(Ink ink) {
/*     */     try {
/* 418 */       ink.setHighlightIntent(true);
/*     */       
/* 420 */       boolean inkSmoothing = false;
/* 421 */       if (!this.mIsStylus) {
/* 422 */         inkSmoothing = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isInkSmoothingEnabled();
/*     */       }
/* 424 */       ink.setSmoothing(inkSmoothing);
/*     */       
/* 426 */       ColorPt color = Utils.color2ColorPt(this.mStrokeColor);
/* 427 */       ink.setColor(color, 3);
/* 428 */       ink.setOpacity(this.mOpacity);
/* 429 */       Annot.BorderStyle bs = ink.getBorderStyle();
/* 430 */       bs.setWidth(this.mThickness);
/* 431 */       ink.setBorderStyle(bs);
/* 432 */       setAuthor((Markup)ink);
/* 433 */     } catch (PDFNetException e) {
/* 434 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void resetCurrentPaths() {
/* 439 */     PointFPool.getInstance().recycle(this.mScreenStrokePoints);
/* 440 */     this.mScreenStrokePoints.clear();
/* 441 */     PointFPool.getInstance().recycle(this.mCanvasStrokePoints);
/* 442 */     this.mCanvasStrokePoints.clear();
/*     */   }
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 446 */     sDebug = debug;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FreeHighlighterCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */