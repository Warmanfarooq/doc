/*    */ package com.pdftron.pdf.widget.signature;
/*    */ 
/*    */ import android.graphics.PointF;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ class StrokeOutlineResult
/*    */ {
/*    */   private final ArrayList<PointF> pointPath;
/*    */   final double[] strokeOutline;
/*    */   
/*    */   StrokeOutlineResult(ArrayList<PointF> pointPath, double[] strokeOutline) {
/* 12 */     this.pointPath = pointPath;
/* 13 */     this.strokeOutline = strokeOutline;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\signature\StrokeOutlineResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */