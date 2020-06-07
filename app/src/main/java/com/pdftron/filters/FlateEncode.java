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
/*    */ public class FlateEncode
/*    */   extends Filter
/*    */ {
/*    */   public FlateEncode(Filter paramFilter) throws PDFNetException {
/* 20 */     super(Create((paramFilter != null) ? paramFilter.impl : 0L, -1, 256L), null);
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
/*    */   
/*    */   public FlateEncode(Filter paramFilter, int paramInt) throws PDFNetException {
/* 35 */     super(Create((paramFilter != null) ? paramFilter.impl : 0L, paramInt, 256L), null);
/* 36 */     if (paramFilter != null) {
/* 37 */       paramFilter.attached = this;
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
/*    */   public FlateEncode(Filter paramFilter, int paramInt, long paramLong) throws PDFNetException {
/* 50 */     super(Create((paramFilter != null) ? paramFilter.impl : 0L, paramInt, paramLong), null);
/* 51 */     if (paramFilter != null)
/* 52 */       paramFilter.attached = this; 
/*    */   }
/*    */   
/*    */   static native long Create(long paramLong1, int paramInt, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\FlateEncode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */