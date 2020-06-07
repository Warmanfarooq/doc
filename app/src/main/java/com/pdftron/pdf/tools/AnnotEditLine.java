/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PathEffect;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Line;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.MeasureUtils;
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
/*     */ @Keep
/*     */ public class AnnotEditLine
/*     */   extends AnnotEdit
/*     */ {
/*  37 */   private Line mLine = null;
/*     */   
/*     */   private RectF mTempRect;
/*     */   private Path mPath;
/*  41 */   private final int e_start_point = 0;
/*  42 */   private final int e_end_point = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotEditLine(@NonNull PDFViewCtrl ctrl) {
/*  48 */     super(ctrl);
/*     */     
/*  50 */     this.CTRL_PTS_CNT = 2;
/*  51 */     this.mCtrlPts = new PointF[this.CTRL_PTS_CNT];
/*  52 */     this.mCtrlPtsOnDown = new PointF[this.CTRL_PTS_CNT];
/*  53 */     for (int i = 0; i < 2; i++) {
/*  54 */       this.mCtrlPts[i] = new PointF();
/*  55 */       this.mCtrlPtsOnDown[i] = new PointF();
/*     */     } 
/*     */     
/*  58 */     this.mPaint = new Paint();
/*  59 */     this.mPaint.setAntiAlias(true);
/*  60 */     this.mBBox = new RectF();
/*  61 */     this.mTempRect = new RectF();
/*  62 */     this.mPath = new Path();
/*  63 */     this.mModifiedAnnot = false;
/*  64 */     this.mCtrlPtsSet = false;
/*  65 */     this.mScaled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate() {
/*  73 */     super.onCreate();
/*  74 */     if (this.mAnnot != null) {
/*  75 */       this.mHasSelectionPermission = hasPermission(this.mAnnot, 0);
/*  76 */       this.mHasMenuPermission = hasPermission(this.mAnnot, 1);
/*     */       
/*     */       try {
/*  79 */         this.mLine = new Line(this.mAnnot);
/*  80 */       } catch (PDFNetException e) {
/*  81 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  87 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mAnnotPageNum);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  97 */     return ToolManager.ToolMode.ANNOT_EDIT_LINE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCtrlPts() {
/* 102 */     setCtrlPts(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCtrlPts(boolean resetAnnotView) {
/* 107 */     if (this.mPdfViewCtrl == null || this.mLine == null || 
/*     */       
/* 109 */       onInterceptAnnotationHandling(this.mAnnot)) {
/*     */       return;
/*     */     }
/*     */     
/* 113 */     this.mCtrlPtsSet = true;
/*     */     
/*     */     try {
/* 116 */       float x1 = (float)(this.mLine.getStartPoint()).x;
/* 117 */       float y1 = (float)(this.mLine.getStartPoint()).y;
/* 118 */       float x2 = (float)(this.mLine.getEndPoint()).x;
/* 119 */       float y2 = (float)(this.mLine.getEndPoint()).y;
/*     */       
/* 121 */       float sx = this.mPdfViewCtrl.getScrollX();
/* 122 */       float sy = this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */       
/* 125 */       double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(x1, y1, this.mAnnotPageNum);
/* 126 */       (this.mCtrlPts[0]).x = (float)pts[0] + sx;
/* 127 */       (this.mCtrlPts[0]).y = (float)pts[1] + sy;
/*     */ 
/*     */       
/* 130 */       pts = this.mPdfViewCtrl.convPagePtToScreenPt(x2, y2, this.mAnnotPageNum);
/* 131 */       (this.mCtrlPts[1]).x = (float)pts[0] + sx;
/* 132 */       (this.mCtrlPts[1]).y = (float)pts[1] + sy;
/*     */ 
/*     */       
/* 135 */       this.mBBox.left = Math.min((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) - this.mCtrlRadius;
/* 136 */       this.mBBox.top = Math.min((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) - this.mCtrlRadius;
/* 137 */       this.mBBox.right = Math.max((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) + this.mCtrlRadius;
/* 138 */       this.mBBox.bottom = Math.max((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) + this.mCtrlRadius;
/*     */       
/* 140 */       addAnnotView();
/* 141 */       updateAnnotView();
/*     */       
/* 143 */       for (int i = 0; i < 2; i++) {
/* 144 */         this.mCtrlPtsOnDown[i].set(this.mCtrlPts[i]);
/*     */       }
/*     */     }
/* 147 */     catch (PDFNetException e) {
/* 148 */       this.mCtrlPtsSet = false;
/* 149 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateCtrlPts(boolean translate, float left, float right, float top, float bottom, RectF which) {
/* 155 */     setCtrlPts();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canAddAnnotView(Annot annot, AnnotStyle annotStyle) {
/* 160 */     if (!((ToolManager)this.mPdfViewCtrl.getToolManager()).isRealTimeAnnotEdit()) {
/* 161 */       return false;
/*     */     }
/* 163 */     return (this.mPdfViewCtrl.isAnnotationLayerEnabled() || !annotStyle.hasAppearance());
/*     */   }
/*     */   
/*     */   protected void updateAnnotView() {
/* 167 */     if (this.mAnnotView != null) {
/* 168 */       this.mAnnotView.setAnnotRect(getAnnotRect());
/* 169 */       int xOffset = this.mPdfViewCtrl.getScrollX();
/* 170 */       int yOffset = this.mPdfViewCtrl.getScrollY();
/* 171 */       this.mAnnotView.layout(xOffset, yOffset, xOffset + this.mPdfViewCtrl
/*     */           
/* 173 */           .getWidth(), yOffset + this.mPdfViewCtrl
/* 174 */           .getHeight());
/* 175 */       PointF start = new PointF((this.mCtrlPts[0]).x - xOffset, (this.mCtrlPts[0]).y - yOffset);
/*     */       
/* 177 */       PointF end = new PointF((this.mCtrlPts[1]).x - xOffset, (this.mCtrlPts[1]).y - yOffset);
/*     */       
/* 179 */       this.mAnnotView.setVertices(new PointF[] { start, end });
/* 180 */       this.mAnnotView.invalidate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 189 */     super.onDraw(canvas, tfm);
/*     */     
/* 191 */     float left = (this.mCtrlPts[0]).x;
/* 192 */     float top = (this.mCtrlPts[1]).y;
/* 193 */     float right = (this.mCtrlPts[1]).x;
/* 194 */     float bottom = (this.mCtrlPts[0]).y;
/*     */     
/* 196 */     if (this.mAnnot != null) {
/* 197 */       if (this.mModifiedAnnot) {
/* 198 */         this.mPaint.setColor(this.mPdfViewCtrl.getResources().getColor(R.color.tools_annot_edit_line_shadow));
/* 199 */         this.mPaint.setStyle(Paint.Style.STROKE);
/* 200 */         this.mPaint.setPathEffect((PathEffect)this.mDashPathEffect);
/*     */ 
/*     */ 
/*     */         
/* 204 */         this.mPath.reset();
/* 205 */         this.mPath.moveTo(right, top);
/* 206 */         this.mPath.lineTo(left, bottom);
/* 207 */         canvas.drawPath(this.mPath, this.mPaint);
/*     */       } 
/*     */       
/* 210 */       if (!this.mHasSelectionPermission) {
/* 211 */         drawSelectionBox(canvas, left, top, right, bottom);
/*     */       }
/*     */     } 
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
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 226 */     super.onUp(e, priorEventMode);
/*     */     
/* 228 */     if (this.mAnnotView != null) {
/* 229 */       this.mAnnotView.setActiveHandle(-1);
/*     */     }
/*     */     
/* 232 */     this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_LINE;
/* 233 */     this.mScaled = false;
/*     */     
/* 235 */     if (!this.mHasMenuPermission && 
/* 236 */       this.mAnnot != null) {
/* 237 */       showMenu(getAnnotRect());
/*     */     }
/*     */ 
/*     */     
/* 241 */     if (this.mAnnot != null && (this.mModifiedAnnot || !this.mCtrlPtsSet || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.DOUBLE_TAP)) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 248 */       if (!this.mCtrlPtsSet) {
/* 249 */         setCtrlPts();
/*     */       }
/*     */       
/* 252 */       resizeLine(priorEventMode);
/*     */ 
/*     */ 
/*     */       
/* 256 */       setCtrlPts();
/*     */       
/* 258 */       showMenu(getAnnotRect());
/*     */ 
/*     */       
/* 261 */       return (priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.FLING);
/*     */     } 
/*     */     
/* 264 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void resizeLine(PDFViewCtrl.PriorEventMode priorEventMode) {
/* 269 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot)) {
/*     */       return;
/*     */     }
/* 272 */     boolean shouldUnlock = false;
/*     */     try {
/* 274 */       this.mPdfViewCtrl.docLock(true);
/* 275 */       shouldUnlock = true;
/* 276 */       if (this.mModifiedAnnot) {
/* 277 */         this.mModifiedAnnot = false;
/* 278 */         raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*     */ 
/*     */         
/* 281 */         float x1 = (this.mCtrlPts[0]).x - this.mPdfViewCtrl.getScrollX();
/* 282 */         float y1 = (this.mCtrlPts[0]).y - this.mPdfViewCtrl.getScrollY();
/* 283 */         float x2 = (this.mCtrlPts[1]).x - this.mPdfViewCtrl.getScrollX();
/* 284 */         float y2 = (this.mCtrlPts[1]).y - this.mPdfViewCtrl.getScrollY();
/*     */         
/* 286 */         double[] pts1 = this.mPdfViewCtrl.convScreenPtToPagePt(x1, y1, this.mAnnotPageNum);
/* 287 */         double[] pts2 = this.mPdfViewCtrl.convScreenPtToPagePt(x2, y2, this.mAnnotPageNum);
/* 288 */         Rect new_annot_rect = new Rect(pts1[0], pts1[1], pts2[0], pts2[1]);
/* 289 */         new_annot_rect.normalize();
/*     */         
/* 291 */         Rect old_update_rect = null;
/* 292 */         if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/*     */ 
/*     */           
/* 295 */           Rect r = this.mAnnot.getRect();
/* 296 */           double[] pts1_old = this.mPdfViewCtrl.convPagePtToScreenPt(r.getX1(), r.getY1(), this.mAnnotPageNum);
/* 297 */           double[] pts2_old = this.mPdfViewCtrl.convPagePtToScreenPt(r.getX2(), r.getY2(), this.mAnnotPageNum);
/* 298 */           old_update_rect = new Rect(pts1_old[0], pts1_old[1], pts2_old[0], pts2_old[1]);
/* 299 */           old_update_rect.normalize();
/*     */         } 
/*     */         
/* 302 */         this.mAnnot.resize(new_annot_rect);
/*     */         
/* 304 */         Line line = new Line(this.mAnnot);
/*     */ 
/*     */         
/* 307 */         RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot(this.mAnnot);
/* 308 */         if (rulerItem == null) {
/* 309 */           rulerItem = RulerItem.getRulerItem(this.mAnnot);
/* 310 */           RulerItem.removeRulerItem(this.mAnnot);
/*     */         } 
/* 312 */         if (null != rulerItem) {
/* 313 */           RulerCreate.adjustContents((Annot)line, rulerItem, pts1[0], pts1[1], pts2[0], pts2[1]);
/*     */         }
/*     */         
/* 316 */         line.setStartPoint(new Point(pts1[0], pts1[1]));
/* 317 */         line.setEndPoint(new Point(pts2[0], pts2[1]));
/*     */         
/* 319 */         this.mAnnot.refreshAppearance();
/* 320 */         buildAnnotBBox();
/* 321 */         if (null != old_update_rect) {
/* 322 */           this.mPdfViewCtrl.update(old_update_rect);
/*     */         }
/* 324 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 325 */         raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/* 326 */       } else if (priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.DOUBLE_TAP) {
/* 327 */         setCtrlPts();
/*     */       } 
/* 329 */     } catch (Exception ex) {
/* 330 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 332 */       if (shouldUnlock) {
/* 333 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 343 */     super.onDown(e);
/*     */     
/* 345 */     float x = e.getX() + this.mPdfViewCtrl.getScrollX();
/* 346 */     float y = e.getY() + this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */     
/* 349 */     this.mEffCtrlPtId = -1;
/* 350 */     float thresh = this.mCtrlRadius * 2.25F;
/* 351 */     float shortest_dist = -1.0F;
/* 352 */     for (int i = 0; i < 2; i++) {
/* 353 */       float s = (this.mCtrlPts[i]).x;
/* 354 */       float t = (this.mCtrlPts[i]).y;
/*     */       
/* 356 */       float dist = (x - s) * (x - s) + (y - t) * (y - t);
/* 357 */       dist = (float)Math.sqrt(dist);
/* 358 */       if (dist <= thresh && (dist < shortest_dist || shortest_dist < 0.0F)) {
/* 359 */         this.mEffCtrlPtId = i;
/* 360 */         shortest_dist = dist;
/*     */       } 
/*     */       
/* 363 */       this.mCtrlPtsOnDown[i].set(this.mCtrlPts[i]);
/*     */     } 
/*     */ 
/*     */     
/* 367 */     if (this.mEffCtrlPtId < 0 && 
/* 368 */       pointToLineDistance(x, y)) {
/* 369 */       this.mEffCtrlPtId = -2;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 375 */     if (this.mAnnot != null) {
/* 376 */       this.mPageCropOnClientF = Utils.buildPageBoundBoxOnClient(this.mPdfViewCtrl, this.mAnnotPageNum);
/*     */     }
/*     */     
/* 379 */     if (this.mAnnotView != null) {
/* 380 */       this.mAnnotView.setActiveHandle(this.mEffCtrlPtId);
/*     */     }
/*     */     
/* 383 */     if (this.mAnnot != null && 
/* 384 */       !isInsideAnnot(e) && this.mEffCtrlPtId < 0) {
/* 385 */       removeAnnotView(true);
/* 386 */       unsetAnnot();
/* 387 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/* 388 */       setCtrlPts();
/*     */       
/* 390 */       this.mPdfViewCtrl.invalidate((int)Math.floor(this.mBBox.left), (int)Math.floor(this.mBBox.top), (int)Math.ceil(this.mBBox.right), (int)Math.ceil(this.mBBox.bottom));
/*     */     } 
/*     */ 
/*     */     
/* 394 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isInsideAnnot(MotionEvent e) {
/* 399 */     int x = (int)(e.getX() + 0.5D);
/* 400 */     int y = (int)(e.getY() + 0.5D);
/*     */ 
/*     */     
/* 403 */     Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 404 */     return (this.mAnnot != null && this.mAnnot.equals(tempAnnot));
/*     */   }
/*     */   
/*     */   private boolean pointToLineDistance(double x, double y) {
/* 408 */     double lineXDist = ((this.mCtrlPts[1]).x - (this.mCtrlPts[0]).x);
/* 409 */     double lineYDist = ((this.mCtrlPts[1]).y - (this.mCtrlPts[0]).y);
/*     */     
/* 411 */     double squaredDist = lineXDist * lineXDist + lineYDist * lineYDist;
/*     */     
/* 413 */     double distRatio = ((x - (this.mCtrlPts[0]).x) * lineXDist + (y - (this.mCtrlPts[0]).y) * lineYDist) / squaredDist;
/*     */     
/* 415 */     if (distRatio < 0.0D) {
/* 416 */       distRatio = 0.0D;
/*     */     }
/* 418 */     if (distRatio > 1.0D) {
/* 419 */       distRatio = 0.0D;
/*     */     }
/*     */     
/* 422 */     double dx = (this.mCtrlPts[0]).x - x + distRatio * lineXDist;
/* 423 */     double dy = (this.mCtrlPts[0]).y - y + distRatio * lineYDist;
/*     */     
/* 425 */     double dist = dx * dx + dy * dy;
/*     */     
/* 427 */     return (dist < (this.mCtrlRadius * this.mCtrlRadius * 4.0F));
/*     */   }
/*     */   
/*     */   private void boundCornerCtrlPts(float ox, float oy, boolean translate) {
/* 431 */     if (this.mPageCropOnClientF != null)
/*     */     {
/* 433 */       if (translate) {
/* 434 */         float max_x = Math.max((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x);
/* 435 */         float min_x = Math.min((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x);
/* 436 */         float max_y = Math.max((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y);
/* 437 */         float min_y = Math.min((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y);
/*     */         
/* 439 */         float shift_x = 0.0F, shift_y = 0.0F;
/* 440 */         if (min_x < this.mPageCropOnClientF.left) {
/* 441 */           shift_x = this.mPageCropOnClientF.left - min_x;
/*     */         }
/* 443 */         if (min_y < this.mPageCropOnClientF.top) {
/* 444 */           shift_y = this.mPageCropOnClientF.top - min_y;
/*     */         }
/* 446 */         if (max_x > this.mPageCropOnClientF.right) {
/* 447 */           shift_x = this.mPageCropOnClientF.right - max_x;
/*     */         }
/* 449 */         if (max_y > this.mPageCropOnClientF.bottom) {
/* 450 */           shift_y = this.mPageCropOnClientF.bottom - max_y;
/*     */         }
/*     */         
/* 453 */         (this.mCtrlPts[0]).x += shift_x;
/* 454 */         (this.mCtrlPts[0]).y += shift_y;
/* 455 */         (this.mCtrlPts[1]).x += shift_x;
/* 456 */         (this.mCtrlPts[1]).y += shift_y;
/*     */       }
/*     */       else {
/*     */         
/* 460 */         if ((this.mCtrlPts[0]).x > this.mPageCropOnClientF.right && ox > 0.0F) {
/* 461 */           (this.mCtrlPts[0]).x = this.mPageCropOnClientF.right;
/* 462 */         } else if ((this.mCtrlPts[0]).x < this.mPageCropOnClientF.left && ox < 0.0F) {
/* 463 */           (this.mCtrlPts[0]).x = this.mPageCropOnClientF.left;
/* 464 */         } else if ((this.mCtrlPts[1]).x > this.mPageCropOnClientF.right && ox > 0.0F) {
/* 465 */           (this.mCtrlPts[1]).x = this.mPageCropOnClientF.right;
/* 466 */         } else if ((this.mCtrlPts[1]).x < this.mPageCropOnClientF.left && ox < 0.0F) {
/* 467 */           (this.mCtrlPts[1]).x = this.mPageCropOnClientF.left;
/*     */         } 
/*     */ 
/*     */         
/* 471 */         if ((this.mCtrlPts[0]).y < this.mPageCropOnClientF.top && oy < 0.0F) {
/* 472 */           (this.mCtrlPts[0]).y = this.mPageCropOnClientF.top;
/* 473 */         } else if ((this.mCtrlPts[0]).y > this.mPageCropOnClientF.bottom && oy > 0.0F) {
/* 474 */           (this.mCtrlPts[0]).y = this.mPageCropOnClientF.bottom;
/* 475 */         } else if ((this.mCtrlPts[1]).y < this.mPageCropOnClientF.top && oy < 0.0F) {
/* 476 */           (this.mCtrlPts[1]).y = this.mPageCropOnClientF.top;
/* 477 */         } else if ((this.mCtrlPts[1]).y > this.mPageCropOnClientF.bottom && oy > 0.0F) {
/* 478 */           (this.mCtrlPts[1]).y = this.mPageCropOnClientF.bottom;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 489 */     if (this.mScaled)
/*     */     {
/* 491 */       return false;
/*     */     }
/* 493 */     if (!this.mHasSelectionPermission)
/*     */     {
/* 495 */       return false;
/*     */     }
/*     */     
/* 498 */     if (this.mEffCtrlPtId != -1) {
/* 499 */       PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/*     */       
/* 501 */       float sx = this.mPdfViewCtrl.getScrollX();
/* 502 */       float sy = this.mPdfViewCtrl.getScrollY();
/* 503 */       snapPoint.x += sx;
/* 504 */       snapPoint.y += sy;
/* 505 */       setLoupeInfo(snapPoint.x, snapPoint.y);
/*     */       
/* 507 */       float totalMoveX = snapPoint.x - e1.getX();
/* 508 */       float totalMoveY = snapPoint.y - e1.getY();
/*     */       
/* 510 */       this.mTempRect.set(this.mBBox);
/*     */       
/* 512 */       if (this.mEffCtrlPtId == -2) {
/* 513 */         for (int i = 0; i < 2; i++) {
/* 514 */           (this.mCtrlPtsOnDown[i]).x += totalMoveX;
/* 515 */           (this.mCtrlPtsOnDown[i]).y += totalMoveY;
/*     */         } 
/* 517 */         boundCornerCtrlPts(totalMoveX, totalMoveY, true);
/* 518 */         if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 519 */           this.mAnnotView.getDrawingView().setOffset((int)totalMoveX, (int)totalMoveY);
/*     */         }
/*     */ 
/*     */         
/* 523 */         this.mBBox.left = Math.min((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) - this.mCtrlRadius;
/* 524 */         this.mBBox.top = Math.min((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) - this.mCtrlRadius;
/* 525 */         this.mBBox.right = Math.max((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) + this.mCtrlRadius;
/* 526 */         this.mBBox.bottom = Math.max((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) + this.mCtrlRadius;
/*     */         
/* 528 */         this.mModifiedAnnot = true;
/*     */       } else {
/*     */         
/* 531 */         boolean valid = false;
/* 532 */         switch (this.mEffCtrlPtId) {
/*     */           case 0:
/* 534 */             (this.mCtrlPtsOnDown[0]).x += totalMoveX;
/* 535 */             (this.mCtrlPtsOnDown[0]).y += totalMoveY;
/* 536 */             valid = true;
/*     */             break;
/*     */           case 1:
/* 539 */             (this.mCtrlPtsOnDown[1]).x += totalMoveX;
/* 540 */             (this.mCtrlPtsOnDown[1]).y += totalMoveY;
/* 541 */             valid = true;
/*     */             break;
/*     */         } 
/* 544 */         this.mModifiedAnnot = true;
/*     */         
/* 546 */         if (valid) {
/* 547 */           boundCornerCtrlPts(totalMoveX, totalMoveY, false);
/*     */ 
/*     */           
/* 550 */           this.mBBox.left = Math.min((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) - this.mCtrlRadius;
/* 551 */           this.mBBox.top = Math.min((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) - this.mCtrlRadius;
/* 552 */           this.mBBox.right = Math.max((this.mCtrlPts[0]).x, (this.mCtrlPts[1]).x) + this.mCtrlRadius;
/* 553 */           this.mBBox.bottom = Math.max((this.mCtrlPts[0]).y, (this.mCtrlPts[1]).y) + this.mCtrlRadius;
/*     */           
/* 555 */           this.mModifiedAnnot = true;
/*     */         } 
/*     */       } 
/*     */       
/* 559 */       float min_x = Math.min(this.mTempRect.left, this.mBBox.left);
/* 560 */       float max_x = Math.max(this.mTempRect.right, this.mBBox.right);
/* 561 */       float min_y = Math.min(this.mTempRect.top, this.mBBox.top);
/* 562 */       float max_y = Math.max(this.mTempRect.bottom, this.mBBox.bottom);
/* 563 */       this.mPdfViewCtrl.invalidate((int)min_x - 1, (int)min_y - 1, (int)Math.ceil(max_x) + 1, (int)Math.ceil(max_y) + 1);
/*     */       
/* 565 */       updateAnnotView();
/*     */       
/* 567 */       return true;
/*     */     } 
/* 569 */     return false;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectCtrlPointId(float x, float y) {
/* 585 */     int effCtrlPtId = -1;
/* 586 */     float thresh = this.mCtrlRadius * 2.25F;
/* 587 */     float shortest_dist = -1.0F;
/* 588 */     for (int i = 0; i < 2; i++) {
/* 589 */       float s = (this.mCtrlPts[i]).x;
/* 590 */       float t = (this.mCtrlPts[i]).y;
/*     */       
/* 592 */       float dist = (x - s) * (x - s) + (y - t) * (y - t);
/* 593 */       dist = (float)Math.sqrt(dist);
/* 594 */       if (dist <= thresh && (dist < shortest_dist || shortest_dist < 0.0F)) {
/* 595 */         effCtrlPtId = i;
/* 596 */         shortest_dist = dist;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 601 */     if (effCtrlPtId < 0 && pointToLineDistance(x, y)) {
/* 602 */       effCtrlPtId = 2;
/*     */     }
/* 604 */     return effCtrlPtId;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotEditLine.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */