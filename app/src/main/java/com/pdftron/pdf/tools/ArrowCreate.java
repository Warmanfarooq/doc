/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Path;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Line;
/*     */ import com.pdftron.pdf.utils.DrawingUtils;
/*     */ import com.pdftron.sdf.Doc;
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
/*     */ public class ArrowCreate
/*     */   extends SimpleShapeCreate
/*     */ {
/*     */   protected PointF mPt3;
/*     */   protected PointF mPt4;
/*  33 */   protected Path mOnDrawPath = new Path();
/*     */ 
/*     */   
/*     */   protected double mZoom;
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrowCreate(@NonNull PDFViewCtrl ctrl) {
/*  41 */     super(ctrl);
/*     */     
/*  43 */     this.mNextToolMode = getToolMode();
/*     */     
/*  45 */     this.mPt3 = new PointF(0.0F, 0.0F);
/*  46 */     this.mPt4 = new PointF(0.0F, 0.0F);
/*     */     
/*  48 */     this.mZoom = ctrl.getZoom();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  56 */     return ToolManager.ToolMode.ARROW_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  61 */     return 1001;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  69 */     if (super.onDown(e)) {
/*  70 */       return true;
/*     */     }
/*     */     
/*  73 */     this.mZoom = this.mPdfViewCtrl.getZoom();
/*     */     
/*  75 */     calculateEndingStyle();
/*     */     
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/*  85 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */ 
/*     */     
/*  88 */     if (this.mAllowTwoFingerScroll) {
/*  89 */       return false;
/*     */     }
/*  91 */     if (this.mAllowOneFingerScrollWithStylus) {
/*  92 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     float min_x = Math.min(Math.min(Math.min(this.mPt1.x, this.mPt2.x), this.mPt3.x), this.mPt4.x);
/*  98 */     float max_x = Math.max(Math.max(Math.max(this.mPt1.x, this.mPt2.x), this.mPt3.x), this.mPt4.x);
/*  99 */     float min_y = Math.min(Math.min(Math.min(this.mPt1.y, this.mPt2.y), this.mPt3.y), this.mPt4.y);
/* 100 */     float max_y = Math.max(Math.max(Math.max(this.mPt1.y, this.mPt2.y), this.mPt3.y), this.mPt4.y);
/*     */     
/* 102 */     PointF snapPoint = snapToNearestIfEnabled(new PointF(e2.getX(), e2.getY()));
/*     */     
/* 104 */     snapPoint.x += this.mPdfViewCtrl.getScrollX();
/* 105 */     snapPoint.y += this.mPdfViewCtrl.getScrollY();
/*     */ 
/*     */     
/* 108 */     if (this.mPageCropOnClientF != null) {
/* 109 */       if (this.mPt2.x < this.mPageCropOnClientF.left) {
/* 110 */         this.mPt2.x = this.mPageCropOnClientF.left;
/* 111 */       } else if (this.mPt2.x > this.mPageCropOnClientF.right) {
/* 112 */         this.mPt2.x = this.mPageCropOnClientF.right;
/*     */       } 
/* 114 */       if (this.mPt2.y < this.mPageCropOnClientF.top) {
/* 115 */         this.mPt2.y = this.mPageCropOnClientF.top;
/* 116 */       } else if (this.mPt2.y > this.mPageCropOnClientF.bottom) {
/* 117 */         this.mPt2.y = this.mPageCropOnClientF.bottom;
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     calculateEndingStyle();
/*     */     
/* 123 */     min_x = Math.min(Math.min(min_x, this.mPt3.x), this.mPt4.x) - this.mThicknessDraw;
/* 124 */     max_x = Math.max(Math.max(max_x, this.mPt3.x), this.mPt4.x) + this.mThicknessDraw;
/* 125 */     min_y = Math.min(Math.min(min_y, this.mPt3.y), this.mPt4.y) - this.mThicknessDraw;
/* 126 */     max_y = Math.max(Math.max(max_y, this.mPt3.y), this.mPt4.y) + this.mThicknessDraw;
/* 127 */     this.mPdfViewCtrl.invalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/* 128 */     return true;
/*     */   }
/*     */   
/*     */   protected void calculateEndingStyle() {
/* 132 */     DrawingUtils.calcArrow(this.mPt1, this.mPt2, this.mPt3, this.mPt4, this.mThickness, this.mZoom);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doneTwoFingerScrolling() {
/* 140 */     super.doneTwoFingerScrolling();
/*     */     
/* 142 */     this.mPt2.set(this.mPt1);
/* 143 */     this.mPt3.set(this.mPt1);
/* 144 */     this.mPt4.set(this.mPt1);
/* 145 */     this.mPdfViewCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 153 */     Line line = Line.create((Doc)doc, bbox);
/* 154 */     line.setEndStyle(3);
/* 155 */     return (Annot)line;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetPts() {
/* 163 */     this.mPt1.set(0.0F, 0.0F);
/* 164 */     this.mPt2.set(0.0F, 0.0F);
/* 165 */     this.mPt3.set(0.0F, 0.0F);
/* 166 */     this.mPt4.set(0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ToolManager.ToolMode getDefaultNextTool() {
/* 174 */     return ToolManager.ToolMode.ANNOT_EDIT_LINE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCreateMarkupFailed(Exception e) {
/* 182 */     float min_x = Math.min(Math.min(Math.min(this.mPt1.x, this.mPt2.x), this.mPt3.x), this.mPt4.x) - this.mThicknessDraw;
/* 183 */     float max_x = Math.max(Math.max(Math.max(this.mPt1.x, this.mPt2.x), this.mPt3.x), this.mPt4.x) + this.mThicknessDraw;
/* 184 */     float min_y = Math.min(Math.min(Math.min(this.mPt1.y, this.mPt2.y), this.mPt3.y), this.mPt4.y) - this.mThicknessDraw;
/* 185 */     float max_y = Math.max(Math.max(Math.max(this.mPt1.y, this.mPt2.y), this.mPt3.y), this.mPt4.y) + this.mThicknessDraw;
/* 186 */     this.mPdfViewCtrl.postInvalidate((int)min_x, (int)min_y, (int)Math.ceil(max_x), (int)Math.ceil(max_y));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 197 */     if (this.mAllowTwoFingerScroll) {
/*     */       return;
/*     */     }
/* 200 */     if (this.mIsAllPointsOutsidePage) {
/*     */       return;
/*     */     }
/*     */     
/* 204 */     DrawingUtils.drawArrow(canvas, this.mPt1, this.mPt2, this.mPt3, this.mPt4, this.mOnDrawPath, this.mPaint);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\ArrowCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */