/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.filters.Filter;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class PDFDocWithoutOwnership
/*    */   extends PDFDoc
/*    */ {
/*    */   public PDFDocWithoutOwnership() throws PDFNetException {}
/*    */   
/*    */   public PDFDocWithoutOwnership(Filter paramFilter) throws PDFNetException {
/* 44 */     super(paramFilter);
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
/*    */   public PDFDocWithoutOwnership(InputStream paramInputStream) throws PDFNetException, IOException {
/* 60 */     this(paramInputStream, 1048576);
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
/*    */   public PDFDocWithoutOwnership(InputStream paramInputStream, int paramInt) throws PDFNetException, IOException {
/* 79 */     super(paramInputStream, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/*    */     try {
/* 85 */       this.impl = 0L;
/*    */       return;
/*    */     } finally {
/* 88 */       super.finalize();
/*    */     } 
/*    */   }
/*    */   
/*    */   private PDFDocWithoutOwnership(long paramLong) {
/* 93 */     super(paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public static PDFDocWithoutOwnership __Create(long paramLong) {
/* 98 */     return new PDFDocWithoutOwnership(paramLong);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDocWithoutOwnership.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */