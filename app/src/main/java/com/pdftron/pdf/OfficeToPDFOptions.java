/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ public class OfficeToPDFOptions
/*     */   extends ConversionOptions
/*     */ {
/*     */   public OfficeToPDFOptions() throws PDFNetException {}
/*     */   
/*     */   public OfficeToPDFOptions(String paramString) throws PDFNetException {
/*  25 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long a() throws PDFNetException {
/*  33 */     return this.mDict.__GetHandle();
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
/*     */   public double getExcelDefaultCellBorderWidth() throws PDFNetException {
/*     */     Obj obj;
/*  47 */     if ((obj = this.mDict.findObj("ExcelDefaultCellBorderWidth")) != null && !obj.isNull())
/*     */     {
/*  49 */       return obj.getNumber();
/*     */     }
/*  51 */     return 0.0D;
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
/*     */   public OfficeToPDFOptions setExcelDefaultCellBorderWidth(double paramDouble) throws PDFNetException {
/*  64 */     this.mDict.putNumber("ExcelDefaultCellBorderWidth", paramDouble);
/*  65 */     return this;
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
/*     */   public String getLayoutResourcesPluginPath() throws PDFNetException {
/*     */     Obj obj;
/*  79 */     if ((obj = this.mDict.findObj("LayoutResourcesPluginPath")) != null && !obj.isNull())
/*     */     {
/*  81 */       return obj.getAsPDFText();
/*     */     }
/*  83 */     return "";
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
/*     */   public OfficeToPDFOptions setLayoutResourcesPluginPath(String paramString) throws PDFNetException {
/*  96 */     this.mDict.putString("LayoutResourcesPluginPath", paramString);
/*  97 */     return this;
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
/*     */   public String getResourceDocPath() throws PDFNetException {
/*     */     Obj obj;
/* 111 */     if ((obj = this.mDict.findObj("ResourceDocPath")) != null && !obj.isNull())
/*     */     {
/* 113 */       return obj.getAsPDFText();
/*     */     }
/* 115 */     return "";
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
/*     */   public OfficeToPDFOptions setResourceDocPath(String paramString) throws PDFNetException {
/* 128 */     this.mDict.putString("ResourceDocPath", paramString);
/* 129 */     return this;
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
/*     */   public String getSmartSubstitutionPluginPath() throws PDFNetException {
/*     */     Obj obj;
/* 143 */     if ((obj = this.mDict.findObj("SmartSubstitutionPluginPath")) != null && !obj.isNull())
/*     */     {
/* 145 */       return obj.getAsPDFText();
/*     */     }
/* 147 */     return "";
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
/*     */   public OfficeToPDFOptions setSmartSubstitutionPluginPath(String paramString) throws PDFNetException {
/* 160 */     this.mDict.putString("SmartSubstitutionPluginPath", paramString);
/* 161 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\OfficeToPDFOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */