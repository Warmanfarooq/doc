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
/*     */ public class DisallowedChange
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public class Type
/*     */   {
/*     */     public static final int e_form_filled = 0;
/*     */     public static final int e_digital_signature_signed = 1;
/*     */     public static final int e_page_template_instantiated = 2;
/*     */     public static final int e_annotation_created_or_updated_or_deleted = 3;
/*     */     public static final int e_other = 4;
/*     */     public static final int e_unknown = 5;
/*     */     
/*     */     public Type(DisallowedChange this$0) {}
/*     */   }
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  43 */     if (this.a != 0L) {
/*     */       
/*  45 */       Destroy(this.a);
/*  46 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  55 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisallowedChange(long paramLong) {
/*  63 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getObjNum() throws PDFNetException {
/*  74 */     return GetObjNum(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() throws PDFNetException {
/*  85 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTypeAsString() throws PDFNetException {
/*  96 */     return GetTypeAsString(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 103 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native int GetObjNum(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native String GetTypeAsString(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DisallowedChange.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */