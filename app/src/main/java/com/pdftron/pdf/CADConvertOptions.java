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
/*     */ public class CADConvertOptions
/*     */   extends OptionsBase
/*     */ {
/*     */   public CADConvertOptions() throws PDFNetException {}
/*     */   
/*     */   public CADConvertOptions(String paramString) throws PDFNetException {
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
/*     */   public boolean getLineWeight() throws PDFNetException {
/*     */     Obj obj;
/*  46 */     if ((obj = this.mDict.findObj("Line-weight")) != null)
/*     */     {
/*  48 */       return obj.getBool();
/*     */     }
/*  50 */     return false;
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
/*     */   public CADConvertOptions setLineWeight(boolean paramBoolean) throws PDFNetException {
/*  63 */     putBool("Line-weight", Boolean.valueOf(paramBoolean));
/*  64 */     return this;
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
/*     */   public boolean getAutoRotate() throws PDFNetException {
/*     */     Obj obj;
/*  77 */     if ((obj = this.mDict.findObj("Auto-rotate")) != null)
/*     */     {
/*  79 */       return obj.getBool();
/*     */     }
/*  81 */     return false;
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
/*     */   public CADConvertOptions setAutoRotate(boolean paramBoolean) throws PDFNetException {
/*  94 */     putBool("Auto-rotate", Boolean.valueOf(paramBoolean));
/*  95 */     return this;
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
/*     */   public double getPageHeight() throws PDFNetException {
/*     */     Obj obj;
/* 109 */     if ((obj = this.mDict.findObj("Page-height")) != null)
/*     */     {
/* 111 */       return obj.getNumber();
/*     */     }
/* 113 */     return 594.0D;
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
/*     */   public CADConvertOptions setPageHeight(double paramDouble) throws PDFNetException {
/* 126 */     putNumber("Page-height", paramDouble);
/* 127 */     return this;
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
/*     */   public double getPageWidth() throws PDFNetException {
/*     */     Obj obj;
/* 141 */     if ((obj = this.mDict.findObj("Page-width")) != null)
/*     */     {
/* 143 */       return obj.getNumber();
/*     */     }
/* 145 */     return 840.0D;
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
/*     */   public CADConvertOptions setPageWidth(double paramDouble) throws PDFNetException {
/* 158 */     putNumber("Page-width", paramDouble);
/* 159 */     return this;
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
/*     */   public double getRasterDPI() throws PDFNetException {
/*     */     Obj obj;
/* 173 */     if ((obj = this.mDict.findObj("Raster-dpi")) != null)
/*     */     {
/* 175 */       return obj.getNumber();
/*     */     }
/* 177 */     return 72.0D;
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
/*     */   public CADConvertOptions setRasterDPI(double paramDouble) throws PDFNetException {
/* 190 */     putNumber("Raster-dpi", paramDouble);
/* 191 */     return this;
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
/*     */   public CADConvertOptions addSheets(String paramString) throws PDFNetException {
/* 205 */     pushBackText("Sheets", paramString);
/* 206 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CADConvertOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */