/*    */ package com.pdftron.pdf.widget.signature;
/*    */ 
/*    */ import android.graphics.Paint;
/*    */ import android.graphics.Path;
/*    */ 
/*    */ class InkDrawInfo {
/*    */   public final int left;
/*    */   public final int right;
/*    */   public final int top;
/*    */   
/*    */   InkDrawInfo(int left, int right, int top, int bottom, Path path, Paint paint) {
/* 12 */     this.left = left;
/* 13 */     this.right = right;
/* 14 */     this.top = top;
/* 15 */     this.bottom = bottom;
/* 16 */     this.path = path;
/* 17 */     this.paint = paint;
/*    */   }
/*    */   
/*    */   public final int bottom;
/*    */   public final Path path;
/*    */   public final Paint paint;
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\signature\InkDrawInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */