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
/*    */ public class StrokeOutlineBuilder
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public enum TipOptions
/*    */   {
/* 19 */     HAS_END_TIP(1),
/*    */     
/* 21 */     NO_SPECIAL_OPTIONS(2);
/*    */     private final int a;
/*    */     
/* 24 */     TipOptions(int param1Int1) { this.a = param1Int1; } public final int getValue() {
/* 25 */       return this.a;
/*    */     }
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
/*    */   public StrokeOutlineBuilder(double paramDouble) {
/* 39 */     this.a = StrokeOutlineBuilderCreate(paramDouble);
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
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPoint(double paramDouble1, double paramDouble2, double paramDouble3) {
/* 55 */     AddPoint(this.a, paramDouble1, paramDouble2, paramDouble3);
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
/*    */   public double[] getOutline() {
/* 68 */     return GetOutline(this.a);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] getLastSegmentOutline(int paramInt, TipOptions paramTipOptions) {
/* 87 */     return GetLastSegmentOutline(this.a, paramInt, paramTipOptions.getValue());
/*    */   }
/*    */   
/*    */   static native long StrokeOutlineBuilderCreate(double paramDouble);
/*    */   
/*    */   static native int AddPoint(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3);
/*    */   
/*    */   static native double[] GetOutline(long paramLong);
/*    */   
/*    */   static native double[] GetLastSegmentOutline(long paramLong1, int paramInt, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\StrokeOutlineBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */