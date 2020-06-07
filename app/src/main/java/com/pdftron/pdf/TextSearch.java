/*     */ package com.pdftron.pdf;
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
/*     */ public class TextSearch
/*     */ {
/*     */   public static final int e_reg_expression = 1;
/*     */   public static final int e_case_sensitive = 2;
/*     */   public static final int e_whole_word = 4;
/*     */   public static final int e_search_up = 8;
/*     */   public static final int e_page_stop = 16;
/*     */   public static final int e_highlight = 32;
/*     */   public static final int e_ambient_string = 64;
/* 136 */   private long a = TextSearchCreate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 146 */     if (this.a != 0L) {
/* 147 */       Delete(this.a);
/* 148 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 153 */     destroy();
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
/*     */   public boolean begin(PDFDoc paramPDFDoc, String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 178 */     return Begin(this.a, paramPDFDoc.__GetHandle(), paramString, paramInt1, paramInt2, paramInt3);
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
/*     */   public TextSearchResult run() {
/* 197 */     return Run(this.a);
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
/*     */   public boolean setPattern(String paramString) {
/* 211 */     return SetPattern(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMode() {
/* 220 */     return GetMode(this.a);
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
/*     */   public void setMode(int paramInt) {
/* 240 */     SetMode(this.a, paramInt);
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
/*     */   public int getCurrentPage() {
/* 253 */     return GetCurrentPage(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRightToLeftLanguage(boolean paramBoolean) {
/* 260 */     SetRightToLeftLanguage(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   static native long TextSearchCreate();
/*     */   
/*     */   static native void Delete(long paramLong);
/*     */   
/*     */   static native boolean Begin(long paramLong1, long paramLong2, String paramString, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   static native TextSearchResult Run(long paramLong);
/*     */   
/*     */   static native boolean SetPattern(long paramLong, String paramString);
/*     */   
/*     */   static native int GetMode(long paramLong);
/*     */   
/*     */   static native void SetMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetCurrentPage(long paramLong);
/*     */   
/*     */   static native void SetRightToLeftLanguage(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\TextSearch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */