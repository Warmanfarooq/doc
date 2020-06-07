/*     */ package com.pdftron.pdf.ocg;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Context
/*     */ {
/*     */   public static final int e_VisibleOC = 0;
/*     */   public static final int e_AllOC = 1;
/*     */   public static final int e_NoOC = 2;
/*     */   long a;
/*     */   private Object b;
/*     */   private boolean c;
/*     */   
/*     */   public Context(Context paramContext) throws PDFNetException {
/* 210 */     this.c = true; this.a = ContextCreateCtx(paramContext.a); this.b = paramContext.b; this.c = true; } public Context(Config paramConfig) throws PDFNetException { this.c = true; this.a = ContextCreateCfg(paramConfig.a); this.b = paramConfig.b; this.c = true; } private Context(long paramLong, Object paramObject) { this.c = true;
/*     */     this.a = paramLong;
/*     */     this.b = paramObject;
/*     */     this.c = false; }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/*     */     if (this.c == true)
/*     */       Destroy(this.a); 
/*     */     this.a = 0L;
/*     */     this.b = null;
/*     */   }
/*     */   
/*     */   public boolean getState(Group paramGroup) throws PDFNetException {
/*     */     return GetState(this.a, paramGroup.a);
/*     */   }
/*     */   
/*     */   public void setState(Group paramGroup, boolean paramBoolean) throws PDFNetException {
/*     */     SetState(this.a, paramGroup.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void resetStates(boolean paramBoolean) throws PDFNetException {
/*     */     ResetStates(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setNonOCDrawing(boolean paramBoolean) throws PDFNetException {
/*     */     SetNonOCDrawing(this.a, paramBoolean);
/*     */   }
/*     */   
/*     */   public void setOCDrawMode(int paramInt) throws PDFNetException {
/*     */     SetOCDrawMode(this.a, paramInt);
/*     */   }
/*     */   
/*     */   public int getOCMode() throws PDFNetException {
/*     */     return GetOCMode(this.a);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/*     */     return this.a;
/*     */   }
/*     */   
/*     */   public static Context __Create(long paramLong, Object paramObject) {
/*     */     if (paramLong == 0L)
/*     */       return null; 
/*     */     return new Context(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   static native long ContextCreateCtx(long paramLong);
/*     */   
/*     */   static native long ContextCreateCfg(long paramLong);
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native boolean GetState(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetState(long paramLong1, long paramLong2, boolean paramBoolean);
/*     */   
/*     */   static native void ResetStates(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetNonOCDrawing(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetOCDrawMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetOCMode(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ocg\Context.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */