/*    */ package com.pdftron.pdf.model;
/*    */ 
/*    */ public class RotateInfo {
/*    */   private float degree;
/*    */   
/*    */   public RotateInfo(float degree, PointF pivot) {
/*  7 */     this.degree = degree;
/*  8 */     this.pivot = pivot;
/*    */   }
/*    */   private PointF pivot;
/*    */   public float getDegree() {
/* 12 */     return this.degree;
/*    */   }
/*    */   
/*    */   public void setDegree(float degreePDF) {
/* 16 */     this.degree = degreePDF;
/*    */   }
/*    */   
/*    */   public PointF getPivot() {
/* 20 */     return this.pivot;
/*    */   }
/*    */   
/*    */   public void setPivot(PointF pivot) {
/* 24 */     this.pivot = pivot;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\RotateInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */