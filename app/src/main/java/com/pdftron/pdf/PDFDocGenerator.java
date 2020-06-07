/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDFDocGenerator
/*     */ {
/*     */   public static PDFDoc generateBlankPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5) throws PDFNetException {
/*  42 */     return PDFDoc.__Create(GenerateBlankPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateGridPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) throws PDFNetException {
/*  63 */     return PDFDoc.__Create(GenerateGridPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateLinedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14, double paramDouble15, double paramDouble16, double paramDouble17, double paramDouble18, double paramDouble19) throws PDFNetException {
/*  93 */     return PDFDoc.__Create(GenerateLinedPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11, paramDouble12, paramDouble13, paramDouble14, paramDouble15, paramDouble16, paramDouble17, paramDouble18, paramDouble19));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateGraphPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11) throws PDFNetException {
/* 116 */     return PDFDoc.__Create(GenerateGraphPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramInt, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateMusicPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11) throws PDFNetException {
/* 139 */     return PDFDoc.__Create(GenerateMusicPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramInt, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10, paramDouble11));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateDottedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) {
/* 160 */     return PDFDoc.__Create(GenerateDottedPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PDFDoc generateIsometricDottedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10) {
/* 181 */     return PDFDoc.__Create(GenerateIsometricDottedPaperDoc(paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6, paramDouble7, paramDouble8, paramDouble9, paramDouble10));
/*     */   }
/*     */   
/*     */   static native long GenerateBlankPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5);
/*     */   
/*     */   static native long GenerateGridPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10);
/*     */   
/*     */   static native long GenerateLinedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11, double paramDouble12, double paramDouble13, double paramDouble14, double paramDouble15, double paramDouble16, double paramDouble17, double paramDouble18, double paramDouble19);
/*     */   
/*     */   static native long GenerateGraphPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, int paramInt, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11);
/*     */   
/*     */   static native long GenerateMusicPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10, double paramDouble11);
/*     */   
/*     */   static native long GenerateDottedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10);
/*     */   
/*     */   static native long GenerateIsometricDottedPaperDoc(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, double paramDouble7, double paramDouble8, double paramDouble9, double paramDouble10);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDocGenerator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */