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
/*     */ public class OCRModule
/*     */ {
/*     */   public static boolean isModuleAvailable() throws PDFNetException {
/*  36 */     return IsModuleAvailable();
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
/*     */   public static void imageToPDF(PDFDoc paramPDFDoc, String paramString, OCROptions paramOCROptions) throws PDFNetException {
/*  49 */     ImageToPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), paramString, (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static void processPDF(PDFDoc paramPDFDoc, OCROptions paramOCROptions) throws PDFNetException {
/*  61 */     ProcessPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static String getOCRJsonFromImage(PDFDoc paramPDFDoc, String paramString, OCROptions paramOCROptions) throws PDFNetException {
/*  75 */     return GetOCRJsonFromImage((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), paramString, (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static String getOCRJsonFromPDF(PDFDoc paramPDFDoc, OCROptions paramOCROptions) throws PDFNetException {
/*  88 */     return GetOCRJsonFromPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static void applyOCRJsonToPDF(PDFDoc paramPDFDoc, String paramString) throws PDFNetException {
/* 100 */     ApplyOCRJsonToPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), paramString);
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
/*     */   public static String getOCRXmlFromImage(PDFDoc paramPDFDoc, String paramString, OCROptions paramOCROptions) throws PDFNetException {
/* 114 */     return GetOCRXmlFromImage((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), paramString, (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static String getOCRXmlFromPDF(PDFDoc paramPDFDoc, OCROptions paramOCROptions) throws PDFNetException {
/* 127 */     return GetOCRXmlFromPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), (paramOCROptions == null) ? 0L : paramOCROptions.a());
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
/*     */   public static void applyOCRXmlToPDF(PDFDoc paramPDFDoc, String paramString) throws PDFNetException {
/* 139 */     ApplyOCRXmlToPDF((paramPDFDoc == null) ? 0L : paramPDFDoc.__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native boolean IsModuleAvailable();
/*     */   
/*     */   static native void ImageToPDF(long paramLong1, String paramString, long paramLong2);
/*     */   
/*     */   static native void ProcessPDF(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetOCRJsonFromImage(long paramLong1, String paramString, long paramLong2);
/*     */   
/*     */   static native String GetOCRJsonFromPDF(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void ApplyOCRJsonToPDF(long paramLong, String paramString);
/*     */   
/*     */   static native String GetOCRXmlFromImage(long paramLong1, String paramString, long paramLong2);
/*     */   
/*     */   static native String GetOCRXmlFromPDF(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void ApplyOCRXmlToPDF(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\OCRModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */