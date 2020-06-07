/*    */ package com.pdftron.pdf;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathData
/*    */ {
/*    */   public static final int e_moveto = 1;
/*    */   public static final int e_lineto = 2;
/*    */   public static final int e_cubicto = 3;
/*    */   public static final int e_conicto = 4;
/*    */   public static final int e_rect = 5;
/*    */   public static final int e_closepath = 6;
/*    */   private byte[] a;
/*    */   private double[] b;
/*    */   private boolean c;
/*    */   
/*    */   public PathData(boolean paramBoolean, byte[] paramArrayOfbyte, double[] paramArrayOfdouble) {
/* 36 */     this.a = paramArrayOfbyte;
/* 37 */     this.c = paramBoolean;
/* 38 */     this.b = paramArrayOfdouble;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOperators(byte[] paramArrayOfbyte) {
/* 47 */     this.a = paramArrayOfbyte;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPoints(double[] paramArrayOfdouble) {
/* 56 */     this.b = paramArrayOfdouble;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getOperators() {
/* 65 */     return this.a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getPoints() {
/* 74 */     return this.b;
/*    */   }
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
/*    */   public boolean isDefined() {
/* 87 */     return this.c;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PathData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */