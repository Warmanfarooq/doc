/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.ObjSet;
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
/*     */ public class Text2PDFOptions
/*     */ {
/*  19 */   private ObjSet a = new ObjSet();
/*  20 */   private Obj b = this.a.createDict();
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
/*     */   public int getBytesPerBite() throws PDFNetException {
/*     */     Obj obj;
/*  41 */     if (!(obj = this.b.findObj("BytesPerBite")).isNull())
/*     */     {
/*  43 */       return (int)obj.getNumber();
/*     */     }
/*  45 */     return 1024;
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
/*     */   public Text2PDFOptions setBytesPerBite(int paramInt) throws PDFNetException {
/*  57 */     this.b.putNumber("BytesPerBite", paramInt);
/*  58 */     return this;
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
/*     */   public String getFontFace() throws PDFNetException {
/*     */     Obj obj;
/*  72 */     if (!(obj = this.b.findObj("FontFace")).isNull())
/*     */     {
/*  74 */       return obj.getAsPDFText();
/*     */     }
/*  76 */     return "Arial";
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
/*     */   public Text2PDFOptions setFontFace(String paramString) throws PDFNetException {
/*  89 */     this.b.putString("FontFace", paramString);
/*  90 */     return this;
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
/*     */   public double getFontSize() throws PDFNetException {
/*     */     Obj obj;
/* 104 */     if (!(obj = this.b.findObj("FontSize")).isNull())
/*     */     {
/* 106 */       return obj.getNumber();
/*     */     }
/* 108 */     return 12.0D;
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
/*     */   public Text2PDFOptions setFontSize(double paramDouble) throws PDFNetException {
/* 121 */     this.b.putNumber("FontSize", paramDouble);
/* 122 */     return this;
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
/*     */   public double getLineHeightMultiplier() throws PDFNetException {
/*     */     Obj obj;
/* 136 */     if (!(obj = this.b.findObj("LineHeightMultiplier")).isNull())
/*     */     {
/* 138 */       return obj.getNumber();
/*     */     }
/* 140 */     return 1.15D;
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
/*     */   public Text2PDFOptions setLineHeightMultiplier(double paramDouble) throws PDFNetException {
/* 153 */     this.b.putNumber("LineHeightMultiplier", paramDouble);
/* 154 */     return this;
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
/*     */   public double getMarginBottom() throws PDFNetException {
/*     */     Obj obj;
/* 168 */     if (!(obj = this.b.findObj("MarginBottom")).isNull())
/*     */     {
/* 170 */       return obj.getNumber();
/*     */     }
/* 172 */     return 1.25D;
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
/*     */   public Text2PDFOptions setMarginBottom(double paramDouble) throws PDFNetException {
/* 185 */     this.b.putNumber("MarginBottom", paramDouble);
/* 186 */     return this;
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
/*     */   public double getMarginLeft() throws PDFNetException {
/*     */     Obj obj;
/* 200 */     if (!(obj = this.b.findObj("MarginLeft")).isNull())
/*     */     {
/* 202 */       return obj.getNumber();
/*     */     }
/* 204 */     return 1.25D;
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
/*     */   public Text2PDFOptions setMarginLeft(double paramDouble) throws PDFNetException {
/* 217 */     this.b.putNumber("MarginLeft", paramDouble);
/* 218 */     return this;
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
/*     */   public double getMarginRight() throws PDFNetException {
/*     */     Obj obj;
/* 232 */     if (!(obj = this.b.findObj("MarginRight")).isNull())
/*     */     {
/* 234 */       return obj.getNumber();
/*     */     }
/* 236 */     return 1.25D;
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
/*     */   public Text2PDFOptions setMarginRight(double paramDouble) throws PDFNetException {
/* 249 */     this.b.putNumber("MarginRight", paramDouble);
/* 250 */     return this;
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
/*     */   public double getMarginTop() throws PDFNetException {
/*     */     Obj obj;
/* 264 */     if (!(obj = this.b.findObj("MarginTop")).isNull())
/*     */     {
/* 266 */       return obj.getNumber();
/*     */     }
/* 268 */     return 1.25D;
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
/*     */   public Text2PDFOptions setMarginTop(double paramDouble) throws PDFNetException {
/* 281 */     this.b.putNumber("MarginTop", paramDouble);
/* 282 */     return this;
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
/* 296 */     if (!(obj = this.b.findObj("PageHeight")).isNull())
/*     */     {
/* 298 */       return obj.getNumber();
/*     */     }
/* 300 */     return 11.0D;
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
/*     */   public Text2PDFOptions setPageHeight(double paramDouble) throws PDFNetException {
/* 313 */     this.b.putNumber("PageHeight", paramDouble);
/* 314 */     return this;
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
/* 328 */     if (!(obj = this.b.findObj("PageWidth")).isNull())
/*     */     {
/* 330 */       return obj.getNumber();
/*     */     }
/* 332 */     return 8.5D;
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
/*     */   public Text2PDFOptions setPageWidth(double paramDouble) throws PDFNetException {
/* 345 */     this.b.putNumber("PageWidth", paramDouble);
/* 346 */     return this;
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
/*     */   public boolean getUseSourceCodeFormatting() throws PDFNetException {
/*     */     Obj obj;
/* 360 */     if (!(obj = this.b.findObj("UseSourceCodeFormatting")).isNull())
/*     */     {
/* 362 */       return obj.getBool();
/*     */     }
/* 364 */     return false;
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
/*     */   public Text2PDFOptions setUseSourceCodeFormatting(boolean paramBoolean) throws PDFNetException {
/* 377 */     this.b.putBool("UseSourceCodeFormatting", paramBoolean);
/* 378 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Text2PDFOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */