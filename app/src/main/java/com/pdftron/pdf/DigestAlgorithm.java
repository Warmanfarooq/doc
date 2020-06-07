/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum DigestAlgorithm
/*    */ {
/* 14 */   e_sha1(0),
/* 15 */   e_sha256(1),
/* 16 */   e_sha384(2),
/* 17 */   e_sha512(3),
/* 18 */   e_ripemd160(4),
/* 19 */   e_unknown_digest_algorithm(5);
/*    */ 
/*    */   
/*    */   private int a;
/*    */   
/*    */   private static HashMap<Integer, DigestAlgorithm> b;
/*    */ 
/*    */   
/*    */   DigestAlgorithm(int paramInt1) {
/* 28 */     this.a = paramInt1;
/*    */   }
/*    */   static {
/* 31 */     b = new HashMap<>(); DigestAlgorithm[] arrayOfDigestAlgorithm;
/*    */     int i;
/*    */     byte b;
/* 34 */     for (i = (arrayOfDigestAlgorithm = values()).length, b = 0; b < i; ) { DigestAlgorithm digestAlgorithm = arrayOfDigestAlgorithm[b];
/*    */       
/* 36 */       b.put(Integer.valueOf(digestAlgorithm.a), digestAlgorithm);
/*    */       b++; }
/*    */   
/*    */   }
/*    */   
/*    */   static DigestAlgorithm a(int paramInt) {
/* 42 */     return b.get(Integer.valueOf(paramInt));
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DigestAlgorithm.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */