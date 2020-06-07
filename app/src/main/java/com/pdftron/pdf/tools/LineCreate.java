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
/*    */ import com.pdftron.pdf.annots.Line;
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
/*    */ public class LineCreate
/*    */   extends SimpleShapeCreate
/*    */ {
/*    */   public LineCreate(@NonNull PDFViewCtrl ctrl) {
/* 32 */     super(ctrl);
/* 33 */     this.mNextToolMode = getToolMode();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 41 */     return ToolManager.ToolMode.LINE_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 46 */     return 3;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 54 */     return (Annot)Line.create((Doc)doc, bbox);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 62 */     if (this.mAllowTwoFingerScroll) {
/*    */       return;
/*    */     }
/* 65 */     if (this.mIsAllPointsOutsidePage) {
/*    */       return;
/*    */     }
/* 68 */     DrawingUtils.drawLine(canvas, this.mPt1, this.mPt2, this.mPaint);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected ToolManager.ToolMode getDefaultNextTool() {
/* 76 */     return ToolManager.ToolMode.ANNOT_EDIT_LINE;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\LineCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */