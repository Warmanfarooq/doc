/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.sdf.Obj;
/*    */ import com.pdftron.sdf.ObjSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ViewerOptimizedOptions
/*    */ {
/*    */   public static final int e_op_off = 0;
/*    */   public static final int e_op_on = 1;
/*    */   public static final int e_op_pdfx_on = 2;
/*    */   Obj a;
/*    */   private ObjSet b;
/*    */   
/*    */   public ViewerOptimizedOptions() {
/*    */     try {
/* 21 */       this.b = new ObjSet();
/* 22 */       this.a = this.b.createDict();
/*    */       return;
/* 24 */     } catch (PDFNetException pDFNetException) {
/*    */       
/* 26 */       System.err.println("Error Occurred when creating ViewerOptimizedOptions.");
/*    */       return;
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
/*    */   
/*    */   public void setThumbnailRenderingThreshold(int paramInt) throws PDFNetException {
/* 42 */     this.a.putNumber("COMPLEXITY_THRESHOLD", paramInt);
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
/*    */   public void setThumbnailSize(int paramInt) throws PDFNetException {
/* 54 */     this.a.putNumber("THUMB_SIZE", paramInt);
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
/*    */   public void setOverprint(int paramInt) throws PDFNetException {
/* 72 */     switch (paramInt) {
/*    */       
/*    */       case 0:
/* 75 */         this.a.putName("OVERPRINT_MODE", "OFF");
/*    */         return;
/*    */       case 1:
/* 78 */         this.a.putName("OVERPRINT_MODE", "ON");
/*    */         return;
/*    */       case 2:
/* 81 */         this.a.putName("OVERPRINT_MODE", "PDFX");
/*    */         break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ViewerOptimizedOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */