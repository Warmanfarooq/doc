/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.Path;
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
/*     */ import com.pdftron.pdf.annots.FreeText;
/*     */ import com.pdftron.pdf.annots.PolyLine;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.MeasureUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class AnnotEditAdvancedShape
/*     */   extends AnnotEdit
/*     */ {
/*     */   public static final int CALLOUT_END_POINT_INDEX = 10;
/*     */   public static final int CALLOUT_KNEE_POINT_INDEX = 9;
/*     */   public static final int CALLOUT_START_POINT_INDEX = 8;
/*     */   private static final int SIDE_X1Y1_X1Y2 = 1;
/*     */   private static final int SIDE_X1Y1_X2Y1 = 2;
/*     */   private static final int SIDE_X1Y2_X2Y2 = 3;
/*     */   private static final int SIDE_X2Y1_X2Y2 = 4;
/*     */   PolyLine mPoly;
/*     */   FreeText mCallout;
/*  53 */   private Path mPath = new Path();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotEditAdvancedShape(@NonNull PDFViewCtrl ctrl) {
/*  59 */     super(ctrl);
/*  60 */     setOriginalCtrlPtsDisabled(true);
/*  61 */     this.mSelectionBoxMargin = 0;
/*  62 */     this.mSnapEnabled = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  70 */     return ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate() {
/*  78 */     super.onCreate();
/*     */     
/*  80 */     if (this.mAnnot == null) {
/*     */       return;
/*     */     }
/*     */     
/*  84 */     boolean shouldUnlockRead = false;
/*     */ 
/*     */     
/*     */     try {
/*  88 */       this.mPdfViewCtrl.docLockRead();
/*  89 */       shouldUnlockRead = true;
/*     */       
/*  91 */       if (this.mAnnot.getType() == 6 || this.mAnnot
/*  92 */         .getType() == 7) {
/*  93 */         this.mPoly = new PolyLine(this.mAnnot);
/*  94 */         if (this.mPoly.isValid()) {
/*  95 */           this.CTRL_PTS_CNT = this.mPoly.getVertexCount();
/*     */         }
/*  97 */       } else if (this.mAnnot.getType() == 2) {
/*  98 */         this.mCallout = new FreeText(this.mAnnot);
/*  99 */         if (AnnotUtils.isCallout(this.mAnnot)) {
/* 100 */           setOriginalCtrlPtsDisabled(false);
/* 101 */           int count = 0;
/* 102 */           if (this.mCallout.getCalloutLinePoint1() != null) {
/* 103 */             count++;
/*     */           }
/* 105 */           if (this.mCallout.getCalloutLinePoint2() != null) {
/* 106 */             count++;
/*     */           }
/* 108 */           if (this.mCallout.getCalloutLinePoint3() != null) {
/* 109 */             count++;
/*     */           }
/* 111 */           this.CTRL_PTS_CNT = 8 + count;
/*     */         } 
/*     */       } 
/* 114 */       if (this.CTRL_PTS_CNT > 8) {
/* 115 */         this.mCtrlPts = new PointF[this.CTRL_PTS_CNT];
/* 116 */         this.mCtrlPtsOnDown = new PointF[this.CTRL_PTS_CNT];
/* 117 */         for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 118 */           this.mCtrlPts[i] = new PointF();
/* 119 */           this.mCtrlPtsOnDown[i] = new PointF();
/*     */         } 
/*     */       } 
/* 122 */     } catch (Exception e) {
/* 123 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 125 */       if (shouldUnlockRead) {
/* 126 */         this.mPdfViewCtrl.docUnlockRead();
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
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 139 */     if (!super.onMove(e1, e2, x_dist, y_dist)) {
/* 140 */       return false;
/*     */     }
/*     */     
/* 143 */     if (this.mEffCtrlPtId >= 0) {
/* 144 */       if (this.mCallout != null && this.mEffCtrlPtId < 8) {
/* 145 */         return true;
/*     */       }
/* 147 */       RectF tempRect = new RectF(this.mBBox);
/* 148 */       PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/* 149 */       float totalMoveX = snapPoint.x - e1.getX();
/* 150 */       float totalMoveY = snapPoint.y - e1.getY();
/* 151 */       float thresh = 2.0F * this.mCtrlRadius;
/* 152 */       float left = this.mBBoxOnDown.left + this.mCtrlRadius;
/* 153 */       float right = this.mBBoxOnDown.right - this.mCtrlRadius;
/* 154 */       float top = this.mBBoxOnDown.top + this.mCtrlRadius;
/* 155 */       float bottom = this.mBBoxOnDown.bottom - this.mCtrlRadius;
/*     */       
/* 157 */       float newX = (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).x + totalMoveX;
/* 158 */       float newY = (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).y + totalMoveY;
/* 159 */       boolean valid = true;
/* 160 */       for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 161 */         if (i != this.mEffCtrlPtId && 
/* 162 */           Math.abs(newX - (this.mCtrlPtsOnDown[i]).x) < thresh && 
/* 163 */           Math.abs(newY - (this.mCtrlPtsOnDown[i]).y) < thresh) {
/* 164 */           valid = false;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 169 */       if (this.mCallout != null && this.mEffCtrlPtId == 10)
/*     */       {
/* 171 */         valid = false;
/*     */       }
/* 173 */       if (valid) {
/* 174 */         (this.mCtrlPts[this.mEffCtrlPtId]).x = newX;
/* 175 */         (this.mCtrlPts[this.mEffCtrlPtId]).y = newY;
/*     */         
/* 177 */         if (this.mCallout != null && this.mEffCtrlPtId == 9) {
/* 178 */           snapCalloutPoint();
/*     */         }
/*     */         
/* 181 */         left = Math.min(left, newX);
/* 182 */         right = Math.max(right, newX);
/* 183 */         top = Math.min(top, newY);
/* 184 */         bottom = Math.max(bottom, newY);
/* 185 */         updateCtrlPts(false, left, right, top, bottom, this.mBBox);
/* 186 */         this.mModifiedAnnot = true;
/*     */         
/* 188 */         float min_x = Math.min(tempRect.left, this.mBBox.left);
/* 189 */         float max_x = Math.max(tempRect.right, this.mBBox.right);
/* 190 */         float min_y = Math.min(tempRect.top, this.mBBox.top);
/* 191 */         float max_y = Math.max(tempRect.bottom, this.mBBox.bottom);
/* 192 */         this.mPdfViewCtrl.invalidate((int)min_x - 1, (int)min_y - 1, (int)Math.ceil(max_x) + 1, (int)Math.ceil(max_y) + 1);
/*     */       } 
/*     */     } 
/* 195 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 204 */     super.onDraw(canvas, tfm);
/*     */     
/* 206 */     if (this.mAnnot == null || (this.mHideCtrlPts && this.mCallout == null)) {
/*     */       return;
/*     */     }
/*     */     
/* 210 */     this.mPaint.setColor(this.mPdfViewCtrl.getResources().getColor(R.color.tools_annot_edit_line_shadow));
/* 211 */     this.mPaint.setStyle(Paint.Style.STROKE);
/*     */     
/* 213 */     this.mPath.reset();
/*     */     
/* 215 */     if (this.mCallout != null) {
/*     */       try {
/* 217 */         this.mPath.moveTo((this.mCtrlPts[8]).x, (this.mCtrlPts[8]).y);
/* 218 */         this.mPath.lineTo((this.mCtrlPts[9]).x, (this.mCtrlPts[9]).y);
/* 219 */         this.mPath.lineTo((this.mCtrlPts[10]).x, (this.mCtrlPts[10]).y);
/* 220 */         canvas.drawPath(this.mPath, this.mPaint);
/* 221 */       } catch (Exception ex) {
/* 222 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/* 224 */     } else if (this.mModifiedAnnot && this.mEffCtrlPtId >= 0) {
/*     */       try {
/* 226 */         if (this.mEffCtrlPtId != 0) {
/* 227 */           this.mPath.moveTo((this.mCtrlPtsOnDown[this.mEffCtrlPtId - 1]).x, (this.mCtrlPtsOnDown[this.mEffCtrlPtId - 1]).y);
/* 228 */           this.mPath.lineTo((this.mCtrlPts[this.mEffCtrlPtId]).x, (this.mCtrlPts[this.mEffCtrlPtId]).y);
/* 229 */         } else if (this.mAnnot.getType() == 6) {
/* 230 */           this.mPath.moveTo((this.mCtrlPtsOnDown[this.CTRL_PTS_CNT - 1]).x, (this.mCtrlPtsOnDown[this.CTRL_PTS_CNT - 1]).y);
/* 231 */           this.mPath.lineTo((this.mCtrlPts[this.mEffCtrlPtId]).x, (this.mCtrlPts[this.mEffCtrlPtId]).y);
/*     */         } else {
/* 233 */           this.mPath.moveTo((this.mCtrlPts[this.mEffCtrlPtId]).x, (this.mCtrlPts[this.mEffCtrlPtId]).y);
/*     */         } 
/*     */         
/* 236 */         if (this.mEffCtrlPtId != this.CTRL_PTS_CNT - 1) {
/* 237 */           this.mPath.lineTo((this.mCtrlPtsOnDown[this.mEffCtrlPtId + 1]).x, (this.mCtrlPtsOnDown[this.mEffCtrlPtId + 1]).y);
/* 238 */         } else if (this.mAnnot.getType() == 6) {
/* 239 */           this.mPath.lineTo((this.mCtrlPtsOnDown[0]).x, (this.mCtrlPtsOnDown[0]).y);
/*     */         } 
/* 241 */       } catch (PDFNetException e) {
/* 242 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */       } 
/* 244 */       canvas.drawPath(this.mPath, this.mPaint);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setCtrlPts() {
/* 253 */     setCtrlPts(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setCtrlPts(boolean resetAnnotView) {
/* 258 */     super.setCtrlPts(resetAnnotView);
/*     */     
/* 260 */     if (this.mPdfViewCtrl == null || (this.mPoly == null && this.mCallout == null) || 
/*     */       
/* 262 */       onInterceptAnnotationHandling(this.mAnnot)) {
/*     */       return;
/*     */     }
/*     */     
/* 266 */     addAnnotView();
/*     */ 
/*     */ 
/*     */     
/* 270 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 271 */     float sy = this.mPdfViewCtrl.getScrollY();
/* 272 */     boolean isContinuous = this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode());
/* 273 */     PointF[] vertices = new PointF[this.CTRL_PTS_CNT];
/*     */     try {
/* 275 */       if (this.mPoly != null) {
/* 276 */         for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 277 */           float x, y; Point pagePoint = this.mPoly.getVertex(i);
/* 278 */           if (isContinuous) {
/* 279 */             double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(pagePoint.x, pagePoint.y, this.mAnnotPageNum);
/* 280 */             x = (float)pts[0] + sx;
/* 281 */             y = (float)pts[1] + sy;
/*     */           } else {
/* 283 */             double[] pts = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(pagePoint.x, pagePoint.y, this.mAnnotPageNum);
/* 284 */             x = (float)pts[0];
/* 285 */             y = (float)pts[1];
/*     */           } 
/* 287 */           (this.mCtrlPtsOnDown[i]).x = x;
/* 288 */           (this.mCtrlPtsOnDown[i]).y = y;
/* 289 */           vertices[i] = new PointF((this.mCtrlPts[i]).x - sx, (this.mCtrlPts[i]).y - sy);
/*     */         } 
/* 291 */       } else if (this.mCallout != null) {
/*     */         
/* 293 */         int xOffset = this.mPdfViewCtrl.getScrollX();
/* 294 */         int yOffset = this.mPdfViewCtrl.getScrollY();
/* 295 */         for (int i = 0; i < this.mCtrlPts.length; i++) {
/* 296 */           PointF p = this.mCtrlPts[i];
/* 297 */           vertices[i] = new PointF(p.x - xOffset, p.y - yOffset);
/*     */         } 
/*     */         
/* 300 */         Point pt1 = this.mCallout.getCalloutLinePoint1();
/* 301 */         Point pt2 = this.mCallout.getCalloutLinePoint2();
/* 302 */         Point pt3 = this.mCallout.getCalloutLinePoint3();
/* 303 */         int j = 8;
/* 304 */         if (pt1 != null) {
/* 305 */           setCalloutPoint(pt1, j, vertices, sx, sy);
/* 306 */           j++;
/*     */         } 
/* 308 */         if (pt2 != null) {
/* 309 */           setCalloutPoint(pt2, j, vertices, sx, sy);
/* 310 */           j++;
/*     */         } 
/* 312 */         if (pt3 != null) {
/* 313 */           setCalloutPoint(pt3, j, vertices, sx, sy);
/*     */         }
/*     */       } 
/* 316 */     } catch (PDFNetException e) {
/* 317 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*     */     } 
/* 319 */     setBBoxFromAllVertices();
/*     */     
/* 321 */     if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 322 */       this.mAnnotView.setVertices(vertices);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setCalloutPoint(Point pt, int i, PointF[] vertices, float sx, float sy) {
/* 327 */     double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(pt.x, pt.y, this.mAnnotPageNum);
/* 328 */     float x = (float)pts[0] + sx;
/* 329 */     float y = (float)pts[1] + sy;
/* 330 */     (this.mCtrlPtsOnDown[i]).x = x;
/* 331 */     (this.mCtrlPtsOnDown[i]).y = y;
/* 332 */     vertices[i] = new PointF((float)pts[0], (float)pts[1]);
/*     */   }
/*     */   
/*     */   private void setBBoxFromAllVertices() {
/* 336 */     if (this.mCallout != null) {
/*     */ 
/*     */       
/* 339 */       updateAnnotView();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 344 */     float right = (this.mCtrlPts[0]).x, left = right;
/* 345 */     float bottom = (this.mCtrlPts[0]).y, top = bottom;
/* 346 */     for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 347 */       float x = (this.mCtrlPts[i]).x;
/* 348 */       float y = (this.mCtrlPts[i]).y;
/* 349 */       if (x < left) {
/* 350 */         left = x;
/*     */       }
/* 352 */       if (x > right) {
/* 353 */         right = x;
/*     */       }
/* 355 */       if (y < top) {
/* 356 */         top = y;
/*     */       }
/* 358 */       if (y > bottom) {
/* 359 */         bottom = y;
/*     */       }
/*     */     } 
/* 362 */     this.mBBox.left = left - this.mCtrlRadius;
/* 363 */     this.mBBox.top = top - this.mCtrlRadius;
/* 364 */     this.mBBox.right = right + this.mCtrlRadius;
/* 365 */     this.mBBox.bottom = bottom + this.mCtrlRadius;
/*     */     
/* 367 */     updateAnnotView();
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
/*     */   protected void updateCtrlPts(boolean translate, float left, float right, float top, float bottom, RectF which) {
/* 380 */     if (this.mCallout == null || this.mEffCtrlPtId < 8) {
/* 381 */       super.updateCtrlPts(translate, left, right, top, bottom, which);
/*     */     }
/*     */     
/* 384 */     int xOffset = this.mPdfViewCtrl.getScrollX();
/* 385 */     int yOffset = this.mPdfViewCtrl.getScrollY();
/*     */     
/* 387 */     if (this.mEffCtrlPtId == -2) {
/* 388 */       updateAnnotView();
/* 389 */       float w = this.mBBox.left - this.mBBoxOnDown.left;
/* 390 */       float h = this.mBBox.top - this.mBBoxOnDown.top;
/* 391 */       for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 392 */         (this.mCtrlPtsOnDown[i]).x += w;
/* 393 */         (this.mCtrlPtsOnDown[i]).y += h;
/*     */         
/* 395 */         if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 396 */           this.mAnnotView.updateVertices(i, new PointF((this.mCtrlPts[i]).x - xOffset, (this.mCtrlPts[i]).y - yOffset));
/*     */         }
/*     */       }
/*     */     
/* 400 */     } else if (this.mEffCtrlPtId >= 0) {
/* 401 */       float totalMoveX = (this.mCtrlPts[this.mEffCtrlPtId]).x - (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).x;
/* 402 */       float totalMoveY = (this.mCtrlPts[this.mEffCtrlPtId]).y - (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).y;
/* 403 */       float x = (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).x += totalMoveX;
/* 404 */       float y = (this.mCtrlPtsOnDown[this.mEffCtrlPtId]).y += totalMoveY;
/* 405 */       (this.mCtrlPts[this.mEffCtrlPtId]).x = Math.max(this.mPageCropOnClientF.left, 
/* 406 */           Math.min(this.mPageCropOnClientF.right, x));
/* 407 */       (this.mCtrlPts[this.mEffCtrlPtId]).y = Math.max(this.mPageCropOnClientF.top, 
/* 408 */           Math.min(this.mPageCropOnClientF.bottom, y));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 413 */       boolean updateFromAllVertices = (x != (this.mCtrlPts[this.mEffCtrlPtId]).x || y != (this.mCtrlPts[this.mEffCtrlPtId]).y);
/* 414 */       if (Math.abs((this.mCtrlPtsOnDown[this.mEffCtrlPtId]).x - this.mCtrlRadius - this.mBBoxOnDown.left) < 1.0F) {
/* 415 */         updateFromAllVertices = true;
/*     */       }
/* 417 */       if (Math.abs((this.mCtrlPtsOnDown[this.mEffCtrlPtId]).x + this.mCtrlRadius - this.mBBoxOnDown.right) < 1.0F) {
/* 418 */         updateFromAllVertices = true;
/*     */       }
/* 420 */       if (Math.abs((this.mCtrlPtsOnDown[this.mEffCtrlPtId]).y - this.mCtrlRadius - this.mBBoxOnDown.top) < 1.0F) {
/* 421 */         updateFromAllVertices = true;
/*     */       }
/* 423 */       if (Math.abs((this.mCtrlPtsOnDown[this.mEffCtrlPtId]).y + this.mCtrlRadius - this.mBBoxOnDown.bottom) < 1.0F) {
/* 424 */         updateFromAllVertices = true;
/*     */       }
/* 426 */       if (updateFromAllVertices) {
/* 427 */         setBBoxFromAllVertices();
/*     */       } else {
/* 429 */         which.left = left - this.mCtrlRadius;
/* 430 */         which.top = top - this.mCtrlRadius;
/* 431 */         which.right = right + this.mCtrlRadius;
/* 432 */         which.bottom = bottom + this.mCtrlRadius;
/*     */         
/* 434 */         if (this.mCallout == null) {
/* 435 */           updateAnnotView();
/*     */         }
/*     */       } 
/*     */       
/* 439 */       if (this.mAnnotView != null && this.mAnnotView.getDrawingView() != null) {
/* 440 */         this.mAnnotView.updateVertices(this.mEffCtrlPtId, new PointF((this.mCtrlPts[this.mEffCtrlPtId]).x - xOffset, (this.mCtrlPts[this.mEffCtrlPtId]).y - yOffset));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void snapCalloutPoint() {
/* 447 */     double ax = (this.mCtrlPts[9]).x;
/* 448 */     double ay = (this.mCtrlPts[9]).y;
/*     */     
/* 450 */     double x1 = (this.mContentBox.left + this.mCtrlRadius);
/* 451 */     double y1 = (this.mContentBox.bottom - this.mCtrlRadius);
/* 452 */     double x2 = (this.mContentBox.right - this.mCtrlRadius);
/* 453 */     double y2 = (this.mContentBox.top + this.mCtrlRadius);
/*     */     
/* 455 */     double midX = (x1 + x2) / 2.0D;
/* 456 */     double midY = (y1 + y2) / 2.0D;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 462 */     double[] distances = { Math.abs(Utils.calcDistance(x1, midY, ax, ay)), Math.abs(Utils.calcDistance(midX, y1, ax, ay)), Math.abs(Utils.calcDistance(midX, y2, ax, ay)), Math.abs(Utils.calcDistance(x2, midY, ax, ay)) };
/*     */ 
/*     */     
/* 465 */     double x = -1.0D;
/* 466 */     double y = -1.0D;
/* 467 */     int minIndex = Utils.findMinIndex(distances);
/* 468 */     switch (minIndex + 1) {
/*     */       case 1:
/* 470 */         x = x1;
/* 471 */         y = midY;
/*     */         break;
/*     */       case 2:
/* 474 */         x = midX;
/* 475 */         y = y1;
/*     */         break;
/*     */       case 3:
/* 478 */         x = midX;
/* 479 */         y = y2;
/*     */         break;
/*     */       case 4:
/* 482 */         x = x2;
/* 483 */         y = midY;
/*     */         break;
/*     */     } 
/*     */     
/* 487 */     if (x >= 0.0D && y >= 0.0D) {
/* 488 */       (this.mCtrlPts[10]).x = (float)x;
/* 489 */       (this.mCtrlPts[10]).y = (float)y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateAnnot() throws PDFNetException {
/* 500 */     if (this.mAnnot == null || onInterceptAnnotationHandling(this.mAnnot) || this.mPdfViewCtrl == null || this.mCtrlPts == null || (this.mPoly == null && this.mCallout == null) || this.mEffCtrlPtId == -1) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 507 */     Rect oldUpdateRect = null;
/* 508 */     if (!this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 509 */       oldUpdateRect = getOldAnnotScreenPosition();
/*     */     }
/*     */     
/* 512 */     float sx = this.mPdfViewCtrl.getScrollX();
/* 513 */     float sy = this.mPdfViewCtrl.getScrollY();
/* 514 */     if (this.mEffCtrlPtId == -2) {
/* 515 */       float deltaX = this.mBBox.left - this.mBBoxOnDown.left;
/* 516 */       float deltaY = this.mBBox.top - this.mBBoxOnDown.top;
/*     */ 
/*     */       
/* 519 */       boolean isContinuous = this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode());
/* 520 */       if (this.mPoly != null) {
/* 521 */         for (int i = 0; i < this.CTRL_PTS_CNT; i++) {
/* 522 */           float x, y; Point pagePoint = this.mPoly.getVertex(i);
/* 523 */           if (isContinuous) {
/* 524 */             double[] pts = this.mPdfViewCtrl.convPagePtToScreenPt(pagePoint.x, pagePoint.y, this.mAnnotPageNum);
/* 525 */             x = (float)pts[0] + sx;
/* 526 */             y = (float)pts[1] + sy;
/*     */           } else {
/* 528 */             double[] pts = this.mPdfViewCtrl.convPagePtToHorizontalScrollingPt(pagePoint.x, pagePoint.y, this.mAnnotPageNum);
/* 529 */             x = (float)pts[0];
/* 530 */             y = (float)pts[1];
/*     */           } 
/* 532 */           double[] pt = this.mPdfViewCtrl.convScreenPtToPagePt((x + deltaX - sx), (y + deltaY - sy), this.mAnnotPageNum);
/*     */ 
/*     */           
/* 535 */           this.mPoly.setVertex(i, new Point(pt[0], pt[1]));
/*     */         } 
/* 537 */       } else if (this.mCallout != null) {
/* 538 */         super.updateAnnot();
/*     */       } 
/*     */     } else {
/* 541 */       double[] pt = this.mPdfViewCtrl.convScreenPtToPagePt(((this.mCtrlPts[this.mEffCtrlPtId]).x - sx), ((this.mCtrlPts[this.mEffCtrlPtId]).y - sy), this.mAnnotPageNum);
/*     */ 
/*     */       
/* 544 */       if (this.mPoly != null) {
/* 545 */         this.mPoly.setVertex(this.mEffCtrlPtId, new Point(pt[0], pt[1]));
/* 546 */       } else if (this.mCallout != null) {
/* 547 */         if (this.mEffCtrlPtId < 8) {
/* 548 */           super.updateAnnot();
/*     */         } else {
/*     */           
/* 551 */           Rect contentRect = this.mCallout.getContentRect();
/* 552 */           if (this.mEffCtrlPtId == 8) {
/* 553 */             this.mCallout.setCalloutLinePoints(new Point(pt[0], pt[1]), this.mCallout
/* 554 */                 .getCalloutLinePoint2(), this.mCallout.getCalloutLinePoint3());
/* 555 */           } else if (this.mEffCtrlPtId == 9) {
/* 556 */             double[] pt2 = this.mPdfViewCtrl.convScreenPtToPagePt(((this.mCtrlPts[10]).x - sx), ((this.mCtrlPts[10]).y - sy), this.mAnnotPageNum);
/*     */ 
/*     */ 
/*     */             
/* 560 */             this.mCallout.setCalloutLinePoints(this.mCallout.getCalloutLinePoint1(), new Point(pt[0], pt[1]), new Point(pt2[0], pt2[1]));
/*     */           } 
/*     */           
/* 563 */           this.mCallout.setRect(contentRect);
/* 564 */           this.mCallout.setContentRect(contentRect);
/* 565 */           this.mCallout.refreshAppearance();
/* 566 */           setCtrlPts();
/*     */         } 
/*     */       } 
/*     */     } 
/* 570 */     boolean shouldUpdateBBox = !this.mBBox.equals(this.mBBoxOnDown);
/* 571 */     if (shouldUpdateBBox) {
/* 572 */       Rect newAnnotRect = getNewAnnotPagePosition();
/* 573 */       if (newAnnotRect == null) {
/*     */         return;
/*     */       }
/* 576 */       if (this.mPoly != null) {
/* 577 */         this.mPoly.setRect(newAnnotRect);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 582 */     RulerItem rulerItem = MeasureUtils.getRulerItemFromAnnot((Annot)this.mPoly);
/* 583 */     ArrayList<Point> points = AnnotUtils.getPolyVertices((Annot)this.mPoly);
/* 584 */     if (null != rulerItem && null != points) {
/* 585 */       if (this.mPoly.getType() == 7) {
/* 586 */         PerimeterMeasureCreate.adjustContents((Annot)this.mPoly, rulerItem, points);
/* 587 */       } else if (this.mPoly.getType() == 6) {
/* 588 */         AreaMeasureCreate.adjustContents((Annot)this.mPoly, rulerItem, points);
/*     */       } 
/*     */     }
/*     */     
/* 592 */     this.mAnnot.refreshAppearance();
/* 593 */     if (shouldUpdateBBox) {
/* 594 */       buildAnnotBBox();
/* 595 */       if (oldUpdateRect != null) {
/* 596 */         this.mPdfViewCtrl.update(oldUpdateRect);
/*     */       }
/*     */     } 
/* 599 */     this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void adjustExtraFreeTextProps(Rect oldContentRect, Rect newContentRect) {
/* 604 */     super.adjustExtraFreeTextProps(oldContentRect, newContentRect);
/* 605 */     if (this.mCallout == null || oldContentRect == null || newContentRect == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 610 */       Point pt2 = this.mCallout.getCalloutLinePoint2();
/* 611 */       Point pt3 = this.mCallout.getCalloutLinePoint3();
/*     */ 
/*     */       
/* 614 */       Point p1 = Utils.calcIntersection(pt2.x, pt2.y, pt3.x, pt3.y, oldContentRect
/* 615 */           .getX1(), oldContentRect.getY1(), oldContentRect.getX1(), oldContentRect.getY2());
/* 616 */       Point p2 = Utils.calcIntersection(pt2.x, pt2.y, pt3.x, pt3.y, oldContentRect
/* 617 */           .getX1(), oldContentRect.getY1(), oldContentRect.getX2(), oldContentRect.getY1());
/* 618 */       Point p3 = Utils.calcIntersection(pt2.x, pt2.y, pt3.x, pt3.y, oldContentRect
/* 619 */           .getX1(), oldContentRect.getY2(), oldContentRect.getX2(), oldContentRect.getY2());
/* 620 */       Point p4 = Utils.calcIntersection(pt2.x, pt2.y, pt3.x, pt3.y, oldContentRect
/* 621 */           .getX2(), oldContentRect.getY1(), oldContentRect.getX2(), oldContentRect.getY2());
/*     */ 
/*     */       
/* 624 */       int which = 0;
/* 625 */       boolean found = false;
/* 626 */       if (p1 != null) {
/* 627 */         double x = p1.x;
/* 628 */         double y = p1.y;
/* 629 */         found = Utils.isSamePoint(pt3.x, pt3.y, x, y);
/* 630 */         if (found) {
/* 631 */           which = 1;
/*     */         }
/*     */       } 
/* 634 */       if (!found && p2 != null) {
/* 635 */         double x = p2.x;
/* 636 */         double y = p2.y;
/* 637 */         found = Utils.isSamePoint(pt3.x, pt3.y, x, y);
/* 638 */         if (found) {
/* 639 */           which = 2;
/*     */         }
/*     */       } 
/* 642 */       if (!found && p3 != null) {
/* 643 */         double x = p3.x;
/* 644 */         double y = p3.y;
/* 645 */         found = Utils.isSamePoint(pt3.x, pt3.y, x, y);
/* 646 */         if (found) {
/* 647 */           which = 3;
/*     */         }
/*     */       } 
/* 650 */       if (!found && p4 != null) {
/* 651 */         double x = p4.x;
/* 652 */         double y = p4.y;
/* 653 */         found = Utils.isSamePoint(pt3.x, pt3.y, x, y);
/* 654 */         if (found) {
/* 655 */           which = 4;
/*     */         }
/*     */       } 
/* 658 */       if (found) {
/* 659 */         double newX = -1.0D;
/* 660 */         double newY = -1.0D;
/* 661 */         switch (which) {
/*     */           case 1:
/* 663 */             newX = newContentRect.getX1();
/* 664 */             newY = (newContentRect.getY1() + newContentRect.getY2()) / 2.0D;
/*     */             break;
/*     */           case 2:
/* 667 */             newX = (newContentRect.getX1() + newContentRect.getX2()) / 2.0D;
/* 668 */             newY = newContentRect.getY1();
/*     */             break;
/*     */           case 3:
/* 671 */             newX = (newContentRect.getX1() + newContentRect.getX2()) / 2.0D;
/* 672 */             newY = newContentRect.getY2();
/*     */             break;
/*     */           case 4:
/* 675 */             newX = newContentRect.getX2();
/* 676 */             newY = (newContentRect.getY1() + newContentRect.getY2()) / 2.0D;
/*     */             break;
/*     */         } 
/* 679 */         if (newX >= 0.0D && newY >= 0.0D) {
/* 680 */           this.mCallout.setCalloutLinePoints(this.mCallout
/* 681 */               .getCalloutLinePoint1(), this.mCallout
/* 682 */               .getCalloutLinePoint2(), new Point(newX, newY));
/*     */         }
/*     */       }
/*     */     
/*     */     }
/* 687 */     catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canAddAnnotView(Annot annot, AnnotStyle annotStyle) {
/* 693 */     if (!((ToolManager)this.mPdfViewCtrl.getToolManager()).isRealTimeAnnotEdit()) {
/* 694 */       return false;
/*     */     }
/* 696 */     return (this.mPdfViewCtrl.isAnnotationLayerEnabled() || !annotStyle.hasAppearance());
/*     */   }
/*     */   
/*     */   protected void updateAnnotView() {
/* 700 */     if (this.mAnnotView != null) {
/* 701 */       float x1 = this.mBBox.left + this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 702 */       float y1 = this.mBBox.top + this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/* 703 */       float x2 = this.mBBox.right - this.mCtrlRadius - this.mPdfViewCtrl.getScrollX();
/* 704 */       float y2 = this.mBBox.bottom - this.mCtrlRadius - this.mPdfViewCtrl.getScrollY();
/* 705 */       this.mAnnotView.setAnnotRect(new RectF(x1, y1, x2, y2));
/* 706 */       int xOffset = this.mPdfViewCtrl.getScrollX();
/* 707 */       int yOffset = this.mPdfViewCtrl.getScrollY();
/* 708 */       this.mAnnotView.layout(xOffset, yOffset, xOffset + this.mPdfViewCtrl
/*     */           
/* 710 */           .getWidth(), yOffset + this.mPdfViewCtrl
/* 711 */           .getHeight());
/*     */ 
/*     */       
/* 714 */       for (int i = 0; i < this.mCtrlPts.length; i++) {
/* 715 */         PointF p = this.mCtrlPts[i];
/* 716 */         if (p != null) {
/* 717 */           this.mAnnotView.updateVertices(i, new PointF(p.x - xOffset, p.y - yOffset));
/*     */         }
/*     */       } 
/*     */       
/* 721 */       this.mAnnotView.invalidate();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotEditAdvancedShape.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */