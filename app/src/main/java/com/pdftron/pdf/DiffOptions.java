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
/*     */ public class DiffOptions
/*     */   extends OptionsBase
/*     */ {
/*     */   public DiffOptions() throws PDFNetException {}
/*     */   
/*     */   public DiffOptions(String paramString) throws PDFNetException {
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
/*     */   public boolean getAddGroupAnnots() throws PDFNetException {
/*     */     Obj obj;
/*  47 */     if (!(obj = this.mDict.findObj("AddGroupAnnots")).isNull())
/*     */     {
/*  49 */       return obj.getBool();
/*     */     }
/*  51 */     return false;
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
/*     */   public DiffOptions setAddGroupAnnots(boolean paramBoolean) throws PDFNetException {
/*  64 */     this.mDict.putBool("AddGroupAnnots", paramBoolean);
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
/*     */   public int getBlendMode() throws PDFNetException {
/*     */     Obj obj;
/*  79 */     if (!(obj = this.mDict.findObj("BlendMode")).isNull())
/*     */     {
/*  81 */       return (int)obj.getNumber();
/*     */     }
/*  83 */     return 5;
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
/*     */   public DiffOptions setBlendMode(int paramInt) throws PDFNetException {
/*  96 */     this.mDict.putNumber("BlendMode", paramInt);
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
/*     */   public ColorPt getColorA() throws PDFNetException {
/*     */     Obj obj;
/* 111 */     if (!(obj = this.mDict.findObj("ColorA")).isNull())
/*     */     {
/* 113 */       return a(obj.getNumber());
/*     */     }
/* 115 */     return a(-3407872.0D);
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
/*     */   public DiffOptions setColorA(ColorPt paramColorPt) throws PDFNetException {
/* 128 */     this.mDict.putNumber("ColorA", a(paramColorPt));
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
/*     */   public ColorPt getColorB() throws PDFNetException {
/*     */     Obj obj;
/* 143 */     if (!(obj = this.mDict.findObj("ColorB")).isNull())
/*     */     {
/* 145 */       return a(obj.getNumber());
/*     */     }
/* 147 */     return a(-1.6724788E7D);
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
/*     */   public DiffOptions setColorB(ColorPt paramColorPt) throws PDFNetException {
/* 160 */     this.mDict.putNumber("ColorB", a(paramColorPt));
/* 161 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DiffOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */