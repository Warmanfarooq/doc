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
/*    */ public class MappedFile
/*    */   extends Filter
/*    */ {
/*    */   public static final int e_read_mode = 0;
/*    */   public static final int e_write_mode = 1;
/*    */   public static final int e_append_mode = 2;
/*    */   
/*    */   public MappedFile(String paramString) throws PDFNetException {
/* 46 */     super(MappedFileCreate(paramString), null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long fileSize() throws PDFNetException {
/* 57 */     return FileSize(this.impl);
/*    */   }
/*    */   
/*    */   static native long MappedFileCreate(String paramString);
/*    */   
/*    */   static native long FileSize(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\MappedFile.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */