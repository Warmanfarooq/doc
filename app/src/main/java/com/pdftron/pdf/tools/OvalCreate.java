/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.Matrix;
/*    */ import android.graphics.RectF;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Circle;
/*    */ import com.pdftron.pdf.utils.DrawingUtils;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class OvalCreate
/*    */   extends SimpleShapeCreate
/*    */ {
/*    */   private RectF mOval;
/*    */   
/*    */   public OvalCreate(@NonNull PDFViewCtrl ctrl) {
/* 35 */     super(ctrl);
/* 36 */     this.mNextToolMode = getToolMode();
/* 37 */     this.mOval = new RectF();
/* 38 */     this.mHasFill = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 46 */     return ToolManager.ToolMode.OVAL_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 51 */     return 5;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 59 */     return (Annot)Circle.create((Doc)doc, bbox);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 67 */     if (this.mAllowTwoFingerScroll) {
/*    */       return;
/*    */     }
/*    */     
/* 71 */     DrawingUtils.drawOval(canvas, this.mPt1, this.mPt2, this.mThicknessDraw, this.mOval, this.mFillColor, this.mStrokeColor, this.mFillPaint, this.mPaint);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\OvalCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */