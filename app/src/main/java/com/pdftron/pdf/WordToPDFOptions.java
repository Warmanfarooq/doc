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
/*     */ 
/*     */ public class WordToPDFOptions
/*     */   extends ConversionOptions
/*     */ {
/*     */   public WordToPDFOptions() throws PDFNetException {}
/*     */   
/*     */   public WordToPDFOptions(String paramString) throws PDFNetException {
/*  26 */     super(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final long a() throws PDFNetException {
/*  34 */     return this.mDict.__GetHandle();
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
/*     */   public String getLayoutResourcesPluginPath() throws PDFNetException {
/*     */     Obj obj;
/*  47 */     if ((obj = this.mDict.findObj("LayoutResourcesPluginPath")) != null && !obj.isNull())
/*     */     {
/*  49 */       return obj.getAsPDFText();
/*     */     }
/*  51 */     return "";
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
/*     */   public WordToPDFOptions setLayoutResourcesPluginPath(String paramString) throws PDFNetException {
/*  64 */     this.mDict.putString("LayoutResourcesPluginPath", paramString);
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
/*     */   public String getResourceDocPath() throws PDFNetException {
/*     */     Obj obj;
/*  79 */     if ((obj = this.mDict.findObj("ResourceDocPath")) != null && !obj.isNull())
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
/*     */   public WordToPDFOptions setResourceDocPath(String paramString) throws PDFNetException {
/*  96 */     this.mDict.putString("ResourceDocPath", paramString);
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
/*     */   public String getSmartSubstitutionPluginPath() throws PDFNetException {
/*     */     Obj obj;
/* 111 */     if ((obj = this.mDict.findObj("SmartSubstitutionPluginPath")) != null && !obj.isNull())
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
/*     */   public WordToPDFOptions setSmartSubstitutionPluginPath(String paramString) throws PDFNetException {
/* 128 */     this.mDict.putString("SmartSubstitutionPluginPath", paramString);
/* 129 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\WordToPDFOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */