/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
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
/*    */ public class GeometryCollection
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public class SnappingMode
/*    */   {
/*    */     public static final int e_default_snap_mode = 14;
/*    */     public static final int e_point_on_line = 1;
/*    */     public static final int e_line_midpoint = 2;
/*    */     public static final int e_line_intersection = 4;
/*    */     public static final int e_path_endpoint = 8;
/*    */     
/*    */     public SnappingMode(GeometryCollection this$0) {}
/*    */   }
/*    */   
/*    */   public void destroy() throws PDFNetException {
/* 39 */     if (this.a != 0L) {
/*    */       
/* 41 */       Destroy(this.a);
/* 42 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 51 */     destroy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public GeometryCollection(long paramLong) {
/* 59 */     this.a = paramLong;
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
/*    */   public Point snapToNearest(double paramDouble1, double paramDouble2, int paramInt) throws PDFNetException {
/* 73 */     return new Point(SnapToNearest(this.a, paramDouble1, paramDouble2, paramInt));
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
/*    */   public Point snapToNearestPixel(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt) throws PDFNetException {
/* 88 */     return new Point(SnapToNearestPixel(this.a, paramDouble1, paramDouble2, paramDouble3, paramInt));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long __GetHandle() {
/* 95 */     return this.a;
/*    */   }
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */   
/*    */   static native double[] SnapToNearest(long paramLong, double paramDouble1, double paramDouble2, int paramInt);
/*    */   
/*    */   static native double[] SnapToNearestPixel(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\GeometryCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */