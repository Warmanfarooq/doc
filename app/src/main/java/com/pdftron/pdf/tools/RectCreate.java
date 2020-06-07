/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.Matrix;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Square;
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
/*    */ public class RectCreate
/*    */   extends SimpleShapeCreate
/*    */ {
/*    */   public RectCreate(@NonNull PDFViewCtrl ctrl) {
/* 32 */     super(ctrl);
/* 33 */     this.mNextToolMode = getToolMode();
/* 34 */     this.mHasFill = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 42 */     return ToolManager.ToolMode.RECT_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 47 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 55 */     return (Annot)Square.create((Doc)doc, bbox);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 64 */     if (this.mAllowTwoFingerScroll) {
/*    */       return;
/*    */     }
/*    */     
/* 68 */     DrawingUtils.drawRectangle(canvas, this.mPt1, this.mPt2, this.mThicknessDraw, this.mFillColor, this.mStrokeColor, this.mFillPaint, this.mPaint);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RectCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */