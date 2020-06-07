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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CharData
/*    */ {
/*    */   long a;
/*    */   
/*    */   public long getCharCode() throws PDFNetException {
/* 23 */     return GetCharCode(this.a);
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
/*    */   public double getGlyphX() throws PDFNetException {
/* 40 */     return GetGlyphX(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getGlyphY() throws PDFNetException {
/* 51 */     return GetGlyphY(this.a);
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
/*    */   public byte[] getCharData() throws PDFNetException {
/* 65 */     return GetCharData(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   CharData(long paramLong, Object paramObject) {
/* 76 */     this.a = paramLong;
/*    */   }
/*    */   
/*    */   static native byte[] GetCharData(long paramLong);
/*    */   
/*    */   static native long GetCharCode(long paramLong);
/*    */   
/*    */   static native double GetGlyphX(long paramLong);
/*    */   
/*    */   static native double GetGlyphY(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CharData.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */