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
/*     */ public class ExternalAnnotManager
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  33 */     if (this.a != 0L) {
/*     */       
/*  35 */       Destroy(this.a);
/*  36 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  45 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExternalAnnotManager(long paramLong) {
/*  53 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeXFDF(String paramString) throws PDFNetException {
/*  64 */     MergeXFDF(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String undo() throws PDFNetException {
/*  75 */     return Undo(this.a);
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
/*     */   public Rect jumpToAnnotWithID(String paramString) throws PDFNetException {
/*  87 */     return Rect.__Create(JumpToAnnotWithID(this.a, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLastXFDF() throws PDFNetException {
/*  98 */     return GetLastXFDF(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLastJSON() throws PDFNetException {
/* 109 */     return GetLastJSON(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String redo() throws PDFNetException {
/* 120 */     return Redo(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNextRedoInfo() throws PDFNetException {
/* 131 */     return GetNextRedoInfo(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNextUndoInfo() throws PDFNetException {
/* 142 */     return GetNextUndoInfo(this.a);
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
/*     */   public String takeSnapshot(String paramString) throws PDFNetException {
/* 154 */     return TakeSnapshot(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 161 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void MergeXFDF(long paramLong, String paramString);
/*     */   
/*     */   static native String Undo(long paramLong);
/*     */   
/*     */   static native long JumpToAnnotWithID(long paramLong, String paramString);
/*     */   
/*     */   static native String GetLastXFDF(long paramLong);
/*     */   
/*     */   static native String GetLastJSON(long paramLong);
/*     */   
/*     */   static native String Redo(long paramLong);
/*     */   
/*     */   static native String GetNextRedoInfo(long paramLong);
/*     */   
/*     */   static native String GetNextUndoInfo(long paramLong);
/*     */   
/*     */   static native String TakeSnapshot(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ExternalAnnotManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */