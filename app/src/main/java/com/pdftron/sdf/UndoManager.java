/*     */ package com.pdftron.sdf;
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
/*     */ public class UndoManager
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public void destroy() throws PDFNetException {
/*  30 */     if (this.a != 0L) {
/*     */       
/*  32 */       Destroy(this.a);
/*  33 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*  42 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UndoManager(long paramLong) {
/*  50 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocSnapshot discardAllSnapshots() throws PDFNetException {
/*  61 */     return new DocSnapshot(DiscardAllSnapshots(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSnapshot undo() throws PDFNetException {
/*  72 */     return new ResultSnapshot(Undo(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canUndo() throws PDFNetException {
/*  83 */     return CanUndo(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocSnapshot getNextUndoSnapshot() throws PDFNetException {
/*  94 */     return new DocSnapshot(GetNextUndoSnapshot(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSnapshot redo() throws PDFNetException {
/* 105 */     return new ResultSnapshot(Redo(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRedo() throws PDFNetException {
/* 116 */     return CanRedo(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocSnapshot getNextRedoSnapshot() throws PDFNetException {
/* 127 */     return new DocSnapshot(GetNextRedoSnapshot(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSnapshot takeSnapshot() throws PDFNetException {
/* 138 */     return new ResultSnapshot(TakeSnapshot(this.a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 145 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long DiscardAllSnapshots(long paramLong);
/*     */   
/*     */   static native long Undo(long paramLong);
/*     */   
/*     */   static native boolean CanUndo(long paramLong);
/*     */   
/*     */   static native long GetNextUndoSnapshot(long paramLong);
/*     */   
/*     */   static native long Redo(long paramLong);
/*     */   
/*     */   static native boolean CanRedo(long paramLong);
/*     */   
/*     */   static native long GetNextRedoSnapshot(long paramLong);
/*     */   
/*     */   static native long TakeSnapshot(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\UndoManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */