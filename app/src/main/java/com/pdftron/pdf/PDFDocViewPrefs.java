/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PDFDocViewPrefs
/*     */ {
/*     */   public static final int e_UseNone = 0;
/*     */   public static final int e_UseThumbs = 1;
/*     */   public static final int e_UseBookmarks = 2;
/*     */   public static final int e_FullScreen = 3;
/*     */   public static final int e_UseOC = 4;
/*     */   public static final int e_UseAttachments = 5;
/*     */   public static final int e_Default = 0;
/*     */   public static final int e_SinglePage = 1;
/*     */   public static final int e_OneColumn = 2;
/*     */   public static final int e_TwoColumnLeft = 3;
/*     */   public static final int e_TwoColumnRight = 4;
/*     */   public static final int e_TwoPageLeft = 5;
/*     */   public static final int e_TwoPageRight = 6;
/*     */   public static final int e_HideToolbar = 0;
/*     */   public static final int e_HideMenubar = 1;
/*     */   public static final int e_HideWindowUI = 2;
/*     */   public static final int e_FitWindow = 3;
/*     */   public static final int e_CenterWindow = 4;
/*     */   public static final int e_DisplayDocTitle = 5;
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public void setInitialPage(Destination paramDestination) throws PDFNetException {
/*  35 */     SetInitialPage(this.a, paramDestination.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPageMode(int paramInt) throws PDFNetException {
/*  74 */     SetPageMode(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPageMode() throws PDFNetException {
/*  84 */     return GetPageMode(this.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLayoutMode(int paramInt) throws PDFNetException {
/* 136 */     SetLayoutMode(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLayoutMode() throws PDFNetException {
/* 146 */     return GetLayoutMode(this.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPref(int paramInt, boolean paramBoolean) throws PDFNetException {
/* 204 */     SetPref(this.a, paramInt, paramBoolean);
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
/*     */   public boolean getPref(int paramInt) throws PDFNetException {
/* 216 */     return GetPref(this.a, paramInt);
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
/*     */   public void setNonFullScreenPageMode(int paramInt) throws PDFNetException {
/* 232 */     SetNonFullScreenPageMode(this.a, paramInt);
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
/*     */   public int getNonFullScreenPageMode() throws PDFNetException {
/* 245 */     return GetNonFullScreenPageMode(this.a);
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
/*     */   public void setDirection(boolean paramBoolean) throws PDFNetException {
/* 263 */     SetDirection(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getDirection() throws PDFNetException {
/* 274 */     return GetDirection(this.a);
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
/*     */   public void setViewArea(int paramInt) throws PDFNetException {
/* 287 */     SetViewArea(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewArea() throws PDFNetException {
/* 298 */     return GetViewArea(this.a);
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
/*     */   public void setViewClip(int paramInt) throws PDFNetException {
/* 310 */     SetViewClip(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getViewClip() throws PDFNetException {
/* 321 */     return GetViewClip(this.a);
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
/*     */   public void setPrintArea(int paramInt) throws PDFNetException {
/* 333 */     SetPrintArea(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrintArea() throws PDFNetException {
/* 344 */     return GetPrintArea(this.a);
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
/*     */   public void setPrintClip(int paramInt) throws PDFNetException {
/* 356 */     SetPrintClip(this.a, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPrintClip() throws PDFNetException {
/* 367 */     return GetPrintClip(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 377 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PDFDocViewPrefs(Obj paramObj) {
/* 387 */     this.a = paramObj.__GetHandle();
/* 388 */     this.b = paramObj.__GetRefHandle();
/*     */   }
/*     */   
/*     */   PDFDocViewPrefs(long paramLong, Object paramObject) {
/* 392 */     this.a = paramLong;
/* 393 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native void SetInitialPage(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetPageMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetPageMode(long paramLong);
/*     */   
/*     */   static native void SetLayoutMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetLayoutMode(long paramLong);
/*     */   
/*     */   static native void SetPref(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   static native boolean GetPref(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetNonFullScreenPageMode(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetNonFullScreenPageMode(long paramLong);
/*     */   
/*     */   static native void SetDirection(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native boolean GetDirection(long paramLong);
/*     */   
/*     */   static native void SetViewArea(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetViewArea(long paramLong);
/*     */   
/*     */   static native void SetViewClip(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetViewClip(long paramLong);
/*     */   
/*     */   static native void SetPrintArea(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetPrintArea(long paramLong);
/*     */   
/*     */   static native void SetPrintClip(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetPrintClip(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDocViewPrefs.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */