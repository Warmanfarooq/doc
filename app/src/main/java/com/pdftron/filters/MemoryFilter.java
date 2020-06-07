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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MemoryFilter
/*    */   extends Filter
/*    */ {
/*    */   public MemoryFilter(long paramLong, boolean paramBoolean) throws PDFNetException {
/* 23 */     super(MemoryFilterCreate(paramLong, paramBoolean), null);
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
/*    */   public byte[] getBuffer() throws PDFNetException {
/* 36 */     return GetBuffer(this.impl);
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
/*    */   public void setAsInputFilter() throws PDFNetException {
/* 52 */     SetAsInputFilter(this.impl);
/*    */   }
/*    */   
/*    */   static native long MemoryFilterCreate(long paramLong, boolean paramBoolean);
/*    */   
/*    */   static native byte[] GetBuffer(long paramLong);
/*    */   
/*    */   static native void SetAsInputFilter(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\MemoryFilter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */