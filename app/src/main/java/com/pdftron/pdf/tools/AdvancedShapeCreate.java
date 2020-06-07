/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.controls.OnToolbarStateUpdateListener;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.PointFPool;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Stack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
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
/*     */ public abstract class AdvancedShapeCreate
/*     */   extends SimpleShapeCreate
/*     */ {
/*  42 */   protected Path mPath = new Path();
/*     */   
/*  44 */   protected ArrayList<Point> mPagePoints = new ArrayList<>();
/*     */   
/*  46 */   private Stack<Snapshot> mUndoStack = new Stack<>();
/*  47 */   private Stack<Snapshot> mRedoStack = new Stack<>();
/*     */   
/*  49 */   private int mPageNum = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mIsEditToolbarShown;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mIsStartPointOutsidePage;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mScrollEventOccurred = true;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mLastOnDownPointRemoved;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mOnDownPointAdded;
/*     */ 
/*     */   
/*     */   private boolean mFlinging;
/*     */ 
/*     */   
/*     */   private boolean mIsScaleBegun;
/*     */ 
/*     */   
/*     */   private OnToolbarStateUpdateListener mOnToolbarStateUpdateListener;
/*     */ 
/*     */   
/*     */   private OnEditToolbarListener mOnEditToolbarListener;
/*     */ 
/*     */ 
/*     */   
/*     */   public AdvancedShapeCreate(@NonNull PDFViewCtrl ctrl) {
/*  86 */     super(ctrl);
/*  87 */     if (this.mOnToolbarStateUpdateListener != null) {
/*  88 */       this.mOnToolbarStateUpdateListener.onToolbarStateUpdated();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract Annot createMarkup(PDFDoc paramPDFDoc, ArrayList<Point> paramArrayList) throws PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawMarkup(@NonNull Canvas paramCanvas, Matrix paramMatrix, ArrayList<PointF> paramArrayList);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(int strokeColor, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/* 112 */     if (this.mPdfViewCtrl == null || this.mPaint == null || this.mFillPaint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 116 */     if (this.mStrokeColor != strokeColor || this.mOpacity != opacity || this.mThickness != thickness || this.mFillColor != fillColor) {
/* 117 */       super.setupAnnotProperty(strokeColor, opacity, thickness, fillColor, icon, pdfTronFontName);
/* 118 */       int color = Utils.getPostProcessedColor(this.mPdfViewCtrl, strokeColor);
/* 119 */       this.mPaint.setColor(color);
/* 120 */       this.mPaint.setAlpha((int)(255.0F * opacity));
/*     */ 
/*     */       
/* 123 */       if (this.mHasFill) {
/* 124 */         this.mFillPaint.setColor(Utils.getPostProcessedColor(this.mPdfViewCtrl, fillColor));
/* 125 */         this.mFillPaint.setAlpha((int)(255.0F * opacity));
/*     */       } 
/*     */       
/* 128 */       this.mPdfViewCtrl.invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showEditToolbar() {
/* 134 */     if (!this.mIsEditToolbarShown) {
/* 135 */       this.mIsEditToolbarShown = true;
/* 136 */       if (this.mOnEditToolbarListener != null) {
/* 137 */         ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(getToolMode());
/* 138 */         this.mOnEditToolbarListener.showEditToolbar(toolMode, null, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 146 */     super.onDown(e);
/*     */     
/* 148 */     this.mScrollEventOccurred = false;
/* 149 */     this.mOnDownPointAdded = false;
/*     */     
/* 151 */     int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 152 */     this.mIsStartPointOutsidePage = (pageNum < 1);
/* 153 */     if (this.mIsStartPointOutsidePage) {
/* 154 */       return false;
/*     */     }
/*     */     
/* 157 */     if (this.mPageNum == -1) {
/* 158 */       this.mPageNum = pageNum;
/* 159 */     } else if (this.mPageNum != pageNum) {
/*     */ 
/*     */       
/* 162 */       commit();
/* 163 */       this.mPageNum = pageNum;
/*     */       
/* 165 */       this.mPagePoints.clear();
/* 166 */       this.mUndoStack.clear();
/* 167 */       this.mRedoStack.clear();
/* 168 */       if (this.mOnToolbarStateUpdateListener != null) {
/* 169 */         this.mOnToolbarStateUpdateListener.onToolbarStateUpdated();
/*     */       }
/*     */     } 
/*     */     
/* 173 */     if (this.mStylusUsed && e.getToolType(0) != 2) {
/* 174 */       return false;
/*     */     }
/*     */     
/* 177 */     this.mLastOnDownPointRemoved = false;
/* 178 */     this.mOnDownPointAdded = true;
/* 179 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e.getX(), e.getY()));
/* 180 */     double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(snapPoint.x, snapPoint.y, this.mPageNum);
/*     */     
/* 182 */     Point pagePoint = new Point(pts[0], pts[1]);
/* 183 */     this.mPagePoints.add(pagePoint);
/*     */     
/* 185 */     this.mPdfViewCtrl.invalidate();
/*     */     
/* 187 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPointerDown(MotionEvent e) {
/* 192 */     removeLastOnDownPoint();
/* 193 */     this.mPdfViewCtrl.invalidate();
/* 194 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 205 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */ 
/*     */     
/* 208 */     if (this.mAllowTwoFingerScroll) {
/* 209 */       removeLastOnDownPoint();
/* 210 */       animateLoupe(false);
/* 211 */       this.mLoupeEnabled = false;
/* 212 */       return false;
/*     */     } 
/*     */     
/* 215 */     if (this.mIsStartPointOutsidePage) {
/* 216 */       return false;
/*     */     }
/*     */     
/* 219 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 220 */       animateLoupe(false);
/* 221 */       this.mLoupeEnabled = false;
/* 222 */       return false;
/*     */     } 
/*     */     
/* 225 */     int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e2.getX(), e2.getY());
/* 226 */     if (pageNum != this.mPageNum)
/*     */     {
/* 228 */       return false;
/*     */     }
/*     */     
/* 231 */     if (!this.mOnDownPointAdded || this.mLastOnDownPointRemoved) {
/* 232 */       return false;
/*     */     }
/*     */     
/* 235 */     showEditToolbar();
/*     */     
/* 237 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/* 238 */     double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(snapPoint.x, snapPoint.y, this.mPageNum);
/* 239 */     int size = this.mPagePoints.size();
/*     */     
/* 241 */     if (size == 1) {
/* 242 */       pushNewPointToStack(this.mPagePoints.get(0));
/* 243 */       Point pagePoint = new Point(pts[0], pts[1]);
/* 244 */       this.mPagePoints.add(pagePoint);
/* 245 */     } else if (size > 1) {
/* 246 */       Point pagePoint = this.mPagePoints.get(this.mPagePoints.size() - 1);
/* 247 */       pagePoint.x = pts[0];
/* 248 */       pagePoint.y = pts[1];
/*     */     } 
/*     */     
/* 251 */     this.mPdfViewCtrl.invalidate();
/*     */     
/* 253 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 263 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING) {
/* 264 */       this.mFlinging = true;
/*     */     }
/*     */     
/* 267 */     if (this.mAllowTwoFingerScroll) {
/* 268 */       doneTwoFingerScrolling();
/* 269 */       this.mScrollEventOccurred = true;
/* 270 */       removeLastOnDownPoint();
/* 271 */       return false;
/*     */     } 
/*     */     
/* 274 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 275 */       removeLastOnDownPoint();
/* 276 */       return false;
/*     */     } 
/*     */     
/* 279 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 280 */       doneOneFingerScrollingWithStylus();
/* 281 */       this.mScrollEventOccurred = true;
/* 282 */       removeLastOnDownPoint();
/* 283 */       return true;
/*     */     } 
/*     */     
/* 286 */     if (this.mScrollEventOccurred) {
/* 287 */       this.mScrollEventOccurred = false;
/* 288 */       removeLastOnDownPoint();
/* 289 */       return false;
/*     */     } 
/*     */     
/* 292 */     if (this.mIsStylus && e.getToolType(0) != 2) {
/* 293 */       removeLastOnDownPoint();
/* 294 */       return false;
/*     */     } 
/*     */     
/* 297 */     if (this.mStylusUsed && e.getToolType(0) != 2) {
/* 298 */       removeLastOnDownPoint();
/* 299 */       return false;
/*     */     } 
/*     */     
/* 302 */     int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(e.getX(), e.getY());
/* 303 */     if (pageNum != this.mPageNum) {
/*     */       
/* 305 */       removeLastOnDownPoint();
/* 306 */       return false;
/*     */     } 
/*     */     
/* 309 */     if (this.mIsStartPointOutsidePage) {
/* 310 */       return false;
/*     */     }
/*     */     
/* 313 */     if (!this.mOnDownPointAdded || this.mLastOnDownPointRemoved) {
/* 314 */       return false;
/*     */     }
/*     */     
/* 317 */     if (canSelectTool(e)) {
/* 318 */       return false;
/*     */     }
/*     */     
/* 321 */     showEditToolbar();
/*     */     
/* 323 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e.getX(), e.getY()));
/* 324 */     double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(snapPoint.x, snapPoint.y, this.mPageNum);
/* 325 */     int size = this.mPagePoints.size();
/* 326 */     boolean addToStack = true;
/* 327 */     if (size > 1 && !this.mUndoStack.isEmpty()) {
/* 328 */       Snapshot lastSnapshot = this.mUndoStack.peek();
/* 329 */       if (!lastSnapshot.isRemoved() && lastSnapshot.mPagePoints.size() > 0) {
/* 330 */         Point lastPoint = lastSnapshot.mPagePoints.get(lastSnapshot.mPagePoints.size() - 1);
/* 331 */         if (lastPoint.x == pts[0] && lastPoint.y == pts[1]) {
/* 332 */           addToStack = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 336 */     if (addToStack) {
/* 337 */       Point pagePoint = this.mPagePoints.get(size - 1);
/* 338 */       pagePoint.x = pts[0];
/* 339 */       pagePoint.y = pts[1];
/* 340 */       pushNewPointToStack(pagePoint);
/*     */     } 
/*     */     
/* 343 */     this.mPdfViewCtrl.invalidate();
/*     */     
/* 345 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSelectTool(MotionEvent e) {
/* 357 */     if (this.mIsEditToolbarShown) {
/* 358 */       return false;
/*     */     }
/*     */     
/* 361 */     ToolManager.ToolMode toolMode = ToolManager.getDefaultToolMode(getToolMode());
/* 362 */     if (this.mForceSameNextToolMode && toolMode != ToolManager.ToolMode.INK_CREATE && toolMode != ToolManager.ToolMode.INK_ERASER && toolMode != ToolManager.ToolMode.TEXT_ANNOT_CREATE) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 367 */       setCurrentDefaultToolModeHelper(toolMode);
/*     */       
/* 369 */       int x = (int)(e.getX() + 0.5D);
/* 370 */       int y = (int)(e.getY() + 0.5D);
/* 371 */       Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 372 */       int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */       try {
/* 374 */         if (null != tempAnnot && tempAnnot.isValid()) {
/* 375 */           ((ToolManager)this.mPdfViewCtrl.getToolManager()).selectAnnot(tempAnnot, page);
/* 376 */           return true;
/*     */         } 
/* 378 */         this.mNextToolMode = getToolMode();
/*     */       }
/* 380 */       catch (PDFNetException pDFNetException) {}
/*     */     } 
/*     */     
/* 383 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPageTurning(int old_page, int cur_page) {
/* 391 */     super.onPageTurning(old_page, cur_page);
/* 392 */     if (cur_page != this.mPageNum && 
/* 393 */       this.mIsEditToolbarShown && this.mOnEditToolbarListener != null) {
/* 394 */       this.mOnEditToolbarListener.closeEditToolbar();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void pushNewPointToStack(Point pagePoint) {
/* 401 */     if (this.mUndoStack == null || this.mRedoStack == null) {
/*     */       return;
/*     */     }
/* 404 */     this.mUndoStack.push(new Snapshot(pagePoint));
/* 405 */     this.mRedoStack.clear();
/* 406 */     if (this.mOnToolbarStateUpdateListener != null) {
/* 407 */       this.mOnToolbarStateUpdateListener.onToolbarStateUpdated();
/*     */     }
/*     */   }
/*     */   
/*     */   private void removeLastOnDownPoint() {
/* 412 */     if (this.mOnDownPointAdded && !this.mLastOnDownPointRemoved) {
/* 413 */       this.mLastOnDownPointRemoved = true;
/* 414 */       int size = this.mPagePoints.size();
/* 415 */       if (size > 0) {
/* 416 */         this.mPagePoints.remove(size - 1);
/* 417 */         this.mPdfViewCtrl.invalidate();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDoubleTap(MotionEvent e) {
/* 425 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDoubleTapEvent(MotionEvent e) {
/* 431 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 437 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleBegin(float x, float y) {
/* 445 */     this.mIsScaleBegun = true;
/* 446 */     return super.onScaleBegin(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleEnd(float x, float y) {
/* 454 */     if (this.mPdfViewCtrl != null) {
/* 455 */       this.mPdfViewCtrl.invalidate();
/*     */     }
/* 457 */     return super.onScaleEnd(x, y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onFlingStop() {
/* 465 */     super.onFlingStop();
/* 466 */     if (this.mAllowOneFingerScrollWithStylus) {
/* 467 */       doneOneFingerScrollingWithStylus();
/*     */     }
/* 469 */     this.mFlinging = false;
/* 470 */     this.mIsScaleBegun = false;
/* 471 */     this.mPdfViewCtrl.invalidate();
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 480 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 489 */     if (!this.mIsEditToolbarShown || this.mPaint == null || this.mPageNum == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 494 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.isSlidingWhileZoomed() || (this.mFlinging && this.mIsScaleBegun)) {
/*     */       return;
/*     */     }
/*     */     
/* 498 */     ArrayList<PointF> canvasPoints = getCanvasPoints();
/* 499 */     if (canvasPoints.size() < 1) {
/*     */       return;
/*     */     }
/*     */     
/* 503 */     float zoom = (float)this.mPdfViewCtrl.getZoom();
/* 504 */     this.mThicknessDraw = this.mThickness * zoom;
/* 505 */     this.mPaint.setStrokeWidth(this.mThicknessDraw);
/*     */     
/* 507 */     if (canvasPoints.size() == 1) {
/* 508 */       PointF point = canvasPoints.get(0);
/* 509 */       if (this.mHasFill && this.mFillColor != 0) {
/* 510 */         canvas.drawPoint(point.x, point.y, this.mFillPaint);
/*     */       }
/* 512 */       if (this.mStrokeColor != 0) {
/* 513 */         canvas.drawPoint(point.x, point.y, this.mPaint);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/* 518 */     drawMarkup(canvas, tfm, canvasPoints);
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   private ArrayList<PointF> getCanvasPoints() {
/* 523 */     ArrayList<PointF> canvasPoints = new ArrayList<>();
/* 524 */     if (this.mPdfViewCtrl == null || this.mPagePoints == null || this.mPagePoints.isEmpty()) {
/* 525 */       return canvasPoints;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 530 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 531 */     float sy = this.mPdfViewCtrl.getScrollY();
/* 532 */     boolean isContinuous = this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode());
/* 533 */     for (Point pagePoint : this.mPagePoints) {
/* 534 */       float x, y; if (isContinuous) {
/* 535 */         double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(pagePoint.x, pagePoint.y, this.mPageNum);
/* 536 */         x = (float)pts[0] + sx;
/* 537 */         y = (float)pts[1] + sy;
/*     */       } else {
/* 539 */         double[] pts = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(pagePoint.x, pagePoint.y, this.mPageNum);
/* 540 */         x = (float)pts[0];
/* 541 */         y = (float)pts[1];
/*     */       } 
/* 543 */       canvasPoints.add(PointFPool.getInstance().obtain(x, y));
/*     */     } 
/*     */     
/* 546 */     return canvasPoints;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canClear() {
/* 553 */     return (this.mPagePoints != null && this.mPagePoints.size() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 560 */     if (this.mPdfViewCtrl == null || this.mUndoStack == null || this.mRedoStack == null || this.mPagePoints == null) {
/*     */       return;
/*     */     }
/*     */     
/* 564 */     this.mUndoStack.push(new Snapshot(this.mPagePoints, true));
/* 565 */     this.mRedoStack.clear();
/* 566 */     this.mPagePoints.clear();
/*     */     
/* 568 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUndo() {
/* 575 */     return (this.mUndoStack != null && !this.mUndoStack.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void undo() {
/* 582 */     if (this.mPdfViewCtrl == null || this.mUndoStack == null || this.mRedoStack == null || this.mUndoStack.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 586 */     Snapshot snapshot = this.mUndoStack.pop();
/* 587 */     this.mRedoStack.push(snapshot);
/* 588 */     if (snapshot.isRemoved()) {
/* 589 */       this.mPagePoints.addAll(snapshot.mPagePoints);
/*     */     } else {
/* 591 */       this.mPagePoints.removeAll(snapshot.mPagePoints);
/*     */     } 
/*     */     
/* 594 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRedo() {
/* 601 */     return (this.mRedoStack != null && !this.mRedoStack.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void redo() {
/* 608 */     if (this.mPdfViewCtrl == null || this.mUndoStack == null || this.mRedoStack == null || this.mRedoStack.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 612 */     Snapshot snapshot = this.mRedoStack.pop();
/* 613 */     this.mUndoStack.push(snapshot);
/* 614 */     if (snapshot.isRemoved()) {
/* 615 */       this.mPagePoints.removeAll(snapshot.mPagePoints);
/*     */     } else {
/* 617 */       this.mPagePoints.addAll(snapshot.mPagePoints);
/*     */     } 
/*     */     
/* 620 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() {
/* 627 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 631 */     if (this.mPageNum == -1 || this.mPagePoints.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 635 */     boolean shouldUnlock = false;
/*     */     try {
/* 637 */       this.mPdfViewCtrl.docLock(true);
/* 638 */       shouldUnlock = true;
/*     */       
/* 640 */       Annot markup = createMarkup(this.mPdfViewCtrl.getDoc(), this.mPagePoints);
/*     */       
/* 642 */       setStyle(markup);
/* 643 */       markup.refreshAppearance();
/*     */       
/* 645 */       setAnnot(markup, this.mPageNum);
/* 646 */       buildAnnotBBox();
/*     */       
/* 648 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mPageNum);
/* 649 */       page.annotPushBack(this.mAnnot);
/*     */       
/* 651 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 653 */       raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/* 654 */     } catch (Exception ex) {
/* 655 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 656 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 657 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/* 658 */       onCreateMarkupFailed(ex);
/*     */     } finally {
/* 660 */       if (shouldUnlock) {
/* 661 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageNum() {
/* 670 */     return this.mPageNum;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnToolbarStateUpdateListener(OnToolbarStateUpdateListener listener) {
/* 679 */     this.mOnToolbarStateUpdateListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnEditToolbarListener(OnEditToolbarListener listener) {
/* 688 */     this.mOnEditToolbarListener = listener;
/*     */   }
/*     */   
/*     */   private static class Snapshot {
/* 692 */     ArrayList<Point> mPagePoints = new ArrayList<>();
/*     */     boolean mIsRemoved;
/*     */     
/*     */     Snapshot(Point pagePoint) {
/* 696 */       this.mPagePoints.add(pagePoint);
/*     */     }
/*     */     
/*     */     Snapshot(List<Point> pagePoints, boolean isRemoved) {
/* 700 */       this.mPagePoints.addAll(pagePoints);
/* 701 */       this.mIsRemoved = isRemoved;
/*     */     }
/*     */     
/*     */     boolean isRemoved() {
/* 705 */       return this.mIsRemoved;
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnEditToolbarListener {
/*     */     void showEditToolbar(@NonNull ToolManager.ToolMode param1ToolMode, @Nullable Annot param1Annot, int param1Int);
/*     */     
/*     */     void closeEditToolbar();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AdvancedShapeCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */