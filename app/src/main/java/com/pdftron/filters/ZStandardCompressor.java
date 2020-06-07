/*    */ package com.pdftron.filters;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ 
/*    */ public class ZStandardCompressor {
/*    */   private long a;
/*    */   
/*    */   public ZStandardCompressor(byte[] paramArrayOfbyte, int paramInt) throws PDFNetException {
/*  9 */     this.a = Create(paramArrayOfbyte, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public String compressAsBase85(String paramString) throws PDFNetException {
/* 14 */     return CompressAsBase85(this.a, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public String decompressBase85(String paramString) throws PDFNetException {
/* 19 */     return DecompressBase85(this.a, paramString);
/*    */   }
/*    */   
/*    */   private static native long Create(byte[] paramArrayOfbyte, int paramInt);
/*    */   
/*    */   private static native String CompressAsBase85(long paramLong, String paramString);
/*    */   
/*    */   private static native String DecompressBase85(long paramLong, String paramString);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\filters\ZStandardCompressor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */