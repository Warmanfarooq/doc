/*     */ package com.pdftron.common;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDFNetException
/*     */   extends Exception
/*     */ {
/*     */   protected String cond_expr;
/*     */   protected String file_name;
/*     */   protected long line_number;
/*     */   protected String function;
/*     */   protected String message;
/*     */   protected long error_code;
/*     */   public static final long e_error_general = 0L;
/*     */   public static final long e_error_network = 1L;
/*     */   public static final long e_error_credentials = 2L;
/*     */   public static final long e_error_num = 3L;
/*     */   
/*     */   private PDFNetException(String paramString) {
/*     */     String[] arrayOfString;
/*  24 */     if ((arrayOfString = paramString.split("%%%")).length == 5) {
/*     */       
/*  26 */       this.cond_expr = arrayOfString[0];
/*  27 */       this.line_number = Long.parseLong(arrayOfString[1]);
/*  28 */       this.file_name = arrayOfString[2];
/*  29 */       this.function = arrayOfString[3];
/*  30 */       this.message = arrayOfString[4]; return;
/*     */     } 
/*  32 */     if (arrayOfString.length == 6) {
/*     */       
/*  34 */       this.cond_expr = arrayOfString[0];
/*  35 */       this.line_number = Long.parseLong(arrayOfString[1]);
/*  36 */       this.file_name = arrayOfString[2];
/*  37 */       this.function = arrayOfString[3];
/*  38 */       this.message = arrayOfString[4];
/*  39 */       this.error_code = Long.parseLong(arrayOfString[5]);
/*     */       
/*     */       return;
/*     */     } 
/*  43 */     this.message = paramString;
/*  44 */     this.line_number = 0L;
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
/*     */   public PDFNetException(String paramString1, long paramLong, String paramString2, String paramString3, String paramString4) {
/*  60 */     this.cond_expr = paramString1;
/*  61 */     this.line_number = paramLong;
/*  62 */     this.file_name = paramString2;
/*  63 */     this.function = paramString3;
/*  64 */     this.message = paramString4;
/*  65 */     this.error_code = 0L;
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
/*     */   public PDFNetException(String paramString1, long paramLong1, String paramString2, String paramString3, String paramString4, long paramLong2) {
/*  81 */     this.cond_expr = paramString1;
/*  82 */     this.line_number = paramLong1;
/*  83 */     this.file_name = paramString2;
/*  84 */     this.function = paramString3;
/*  85 */     this.message = paramString4;
/*  86 */     this.error_code = paramLong2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCondExpr() {
/*  96 */     return this.cond_expr;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 106 */     return this.file_name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLineNumber() {
/* 116 */     return (int)this.line_number;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFunction() {
/* 126 */     return this.function;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/* 136 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getErrorCode() {
/* 146 */     return this.error_code;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 156 */     if (this.line_number != 0L)
/*     */     {
/* 158 */       return "Exception: \n\t Message: " + 
/* 159 */         getMessage() + "\n\t Conditional expression: " + 
/* 160 */         getCondExpr() + "\n\t Filename   : " + 
/* 161 */         getFileName() + "\n\t Function   : " + 
/* 162 */         getFunction() + "\n\t Linenumber : " + 
/* 163 */         getLineNumber() + "\n\t Error code : " + 
/* 164 */         getErrorCode() + "\n";
/*     */     }
/*     */ 
/*     */     
/* 168 */     return super.toString();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\common\PDFNetException.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */