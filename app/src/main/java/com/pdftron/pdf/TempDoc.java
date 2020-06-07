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
/*    */ public class TempDoc
/*    */   extends PDFDoc
/*    */ {
/*    */   public TempDoc() throws PDFNetException {}
/*    */   
/*    */   public TempDoc(Filter paramFilter) throws PDFNetException {
/* 40 */     super(paramFilter);
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
/*    */   public TempDoc(InputStream paramInputStream) throws PDFNetException, IOException {
/* 56 */     this(paramInputStream, 1048576);
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
/*    */   public TempDoc(InputStream paramInputStream, int paramInt) throws PDFNetException, IOException {
/* 75 */     super(paramInputStream, paramInt);
/*    */   }
/*    */   
/*    */   public void invalidate() {
/* 79 */     this.impl = 0L;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\TempDoc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */