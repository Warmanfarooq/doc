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
/*    */ public class ViewChangeCollection
/*    */ {
/*    */   long a;
/*    */   
/*    */   public ViewChangeCollection() throws PDFNetException {
/* 19 */     this.a = ViewChangeCollectionCreate();
/*    */   }
/*    */ 
/*    */   
/*    */   public void destroy() throws PDFNetException {
/* 24 */     if (this.a != 0L) {
/*    */       
/* 26 */       Destroy(this.a);
/* 27 */       this.a = 0L;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void finalize() throws Throwable {
/* 33 */     destroy();
/*    */   }
/*    */ 
/*    */   
/*    */   ViewChangeCollection(long paramLong) {
/* 38 */     this.a = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ViewChangeCollection __Create(long paramLong) {
/* 43 */     return new ViewChangeCollection(paramLong);
/*    */   }
/*    */   
/*    */   public long __GetHandle() {
/* 47 */     return this.a;
/*    */   }
/*    */   
/*    */   static native long ViewChangeCollectionCreate();
/*    */   
/*    */   static native void Destroy(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ViewChangeCollection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */