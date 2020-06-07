/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Separation
/*    */ {
/*    */   private byte[] a;
/*    */   private String b;
/*    */   private byte c;
/*    */   private byte d;
/*    */   private byte e;
/*    */   private byte f;
/*    */   
/*    */   public Separation(String paramString, byte[] paramArrayOfbyte, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4) {
/* 19 */     this.b = paramString;
/* 20 */     this.a = paramArrayOfbyte;
/* 21 */     this.c = paramByte1;
/* 22 */     this.d = paramByte2;
/* 23 */     this.e = paramByte3;
/* 24 */     this.f = paramByte4;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte C() throws PDFNetException {
/* 32 */     return this.c;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte M() throws PDFNetException {
/* 39 */     return this.d;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte Y() throws PDFNetException {
/* 46 */     return this.e;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte K() throws PDFNetException {
/* 53 */     return this.f;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDataSize() throws PDFNetException {
/* 60 */     return this.a.length;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSeparationName() throws PDFNetException {
/* 67 */     return this.b;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getData() throws PDFNetException {
/* 74 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Separation.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */