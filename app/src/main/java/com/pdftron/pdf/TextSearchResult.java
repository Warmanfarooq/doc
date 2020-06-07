/*    */ package com.pdftron.pdf;
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
/*    */ public class TextSearchResult
/*    */ {
/*    */   public static final int e_done = 0;
/*    */   public static final int e_page = 1;
/*    */   public static final int e_found = 2;
/*    */   private int code;
/*    */   private int page_num;
/*    */   private String result_str;
/*    */   private String ambient_str;
/*    */   private Highlights highlights;
/*    */   
/*    */   public TextSearchResult(int paramInt1, int paramInt2, String paramString1, String paramString2, long paramLong) {
/* 41 */     this.code = paramInt1;
/* 42 */     this.page_num = paramInt2;
/* 43 */     this.result_str = paramString1;
/* 44 */     this.ambient_str = paramString2;
/* 45 */     this.highlights = new Highlights(paramLong);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getCode() {
/* 54 */     return this.code;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getPageNum() {
/* 63 */     return this.page_num;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getResultStr() {
/* 72 */     return this.result_str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getAmbientStr() {
/* 81 */     return this.ambient_str;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Highlights getHighlights() {
/* 90 */     return this.highlights;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\TextSearchResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */