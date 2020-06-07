/*    */ package com.pdftron.filters;
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
/*    */ public class ASCII85Encode
/*    */   extends Filter
/*    */ {
/*    */   public ASCII85Encode(Filter paramFilter) throws PDFNetException {
/* 20 */     super(ASCII85EncodeCreate((paramFilter != null) ? paramFilter.impl : 0L, 72L, 256L), null);
/* 21 */     if (paramFilter != null) {
/* 22 */       paramFilter.attached = this;
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
/*    */   public ASCII85Encode(Filter paramFilter, long paramLong) throws PDFNetException {
/* 34 */     super(ASCII85EncodeCreate((paramFilter != null) ? paramFilter.impl : 0L, paramLong, 256L), null);
/* 35 */     if (paramFilter != null) {
/* 36 */       paramFilter.attached = this;
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
/*    */   public ASCII85Encode(Filter paramFilter, long paramLong1, long paramLong2) throws PDFNetException {
/* 49 */     super(ASCII85EncodeCreate((paramFilter != null) ? paramFilter.impl : 0L, paramLong1, paramLong2), null);
/* 50 */     if (paramFilter != null)
/* 51 */       paramFilter.attached = this; 
/*    */   }
/*    */   
/*    */   static native long ASCII85EncodeCreate(long paramLong1, long paramLong2, long paramLong3);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\ASCII85Encode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */