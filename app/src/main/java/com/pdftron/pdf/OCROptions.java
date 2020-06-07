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
/*    */ 
/*    */ public class OCROptions
/*    */   extends OptionsBase
/*    */ {
/*    */   public OCROptions() throws PDFNetException {}
/*    */   
/*    */   public OCROptions(String paramString) throws PDFNetException {
/* 25 */     super(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   final long a() throws PDFNetException {
/* 33 */     return this.mDict.__GetHandle();
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
/*    */   public OCROptions addIgnoreZonesForPage(RectCollection paramRectCollection, int paramInt) throws PDFNetException {
/* 48 */     insertRectCollection("IgnoreZones", paramRectCollection, paramInt - 1);
/* 49 */     return this;
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
/*    */   public OCROptions addLang(String paramString) throws PDFNetException {
/* 62 */     pushBackText("Langs", paramString);
/* 63 */     return this;
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
/*    */   public OCROptions addTextZonesForPage(RectCollection paramRectCollection, int paramInt) throws PDFNetException {
/* 77 */     insertRectCollection("TextZones", paramRectCollection, paramInt - 1);
/* 78 */     return this;
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
/*    */   public OCROptions addDPI(int paramInt) throws PDFNetException {
/* 92 */     pushBackNumber("DPI", paramInt);
/* 93 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\OCROptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */