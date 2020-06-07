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
/*     */ public class Action
/*     */ {
/*     */   public static final int e_GoTo = 0;
/*     */   public static final int e_GoToR = 1;
/*     */   public static final int e_GoToE = 2;
/*     */   public static final int e_Launch = 3;
/*     */   public static final int e_Thread = 4;
/*     */   public static final int e_URI = 5;
/*     */   public static final int e_Sound = 6;
/*     */   public static final int e_Movie = 7;
/*     */   public static final int e_Hide = 8;
/*     */   public static final int e_Named = 9;
/*     */   public static final int e_SubmitForm = 10;
/*     */   public static final int e_ResetForm = 11;
/*     */   public static final int e_ImportData = 12;
/*     */   public static final int e_JavaScript = 13;
/*     */   public static final int e_SetOCGState = 14;
/*     */   public static final int e_Rendition = 15;
/*     */   public static final int e_Trans = 16;
/*     */   
/*     */   public static Action createGoto(Destination paramDestination) throws PDFNetException {
/*  35 */     return new Action(CreateGoto(paramDestination.a), paramDestination.b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int e_GoTo3DView = 17;
/*     */   
/*     */   public static final int e_Unknown = 18;
/*     */   
/*     */   public static final int e_exclude = 0;
/*     */   public static final int e_include_no_value_fields = 1;
/*     */   public static final int e_export_format = 2;
/*     */   public static final int e_get_method = 3;
/*     */   public static final int e_submit_coordinates = 4;
/*     */   public static final int e_xfdf = 5;
/*     */   
/*     */   public static Action createGoto(byte[] paramArrayOfbyte, Destination paramDestination) throws PDFNetException {
/*  51 */     return new Action(CreateGoto(paramArrayOfbyte, paramDestination.a), paramDestination.b);
/*     */   }
/*     */ 
/*     */   
/*     */   public static final int e_include_append_saves = 6;
/*     */   
/*     */   public static final int e_include_annotations = 7;
/*     */   public static final int e_submit_pdf = 8;
/*     */   public static final int e_canonical_format = 9;
/*     */   public static final int e_excl_non_user_annots = 10;
/*     */   public static final int e_excl_F_key = 11;
/*     */   public static final int e_embed_form = 13;
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public static Action createGotoRemote(FileSpec paramFileSpec, int paramInt) throws PDFNetException {
/*  67 */     return new Action(CreateGotoRemote(paramFileSpec.a, paramInt), paramFileSpec.b);
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
/*     */   public static Action createGotoRemote(FileSpec paramFileSpec, int paramInt, boolean paramBoolean) throws PDFNetException {
/*  85 */     return new Action(CreateGotoRemote(paramFileSpec.a, paramInt, paramBoolean), paramFileSpec.b);
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
/*     */   public static Action createURI(PDFDoc paramPDFDoc, String paramString) throws PDFNetException {
/* 103 */     return new Action(CreateURI(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
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
/*     */   public static Action createSubmitForm(FileSpec paramFileSpec) throws PDFNetException {
/* 119 */     return new Action(CreateSubmitForm(paramFileSpec.a), paramFileSpec.b);
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
/*     */   public static Action createLaunch(PDFDoc paramPDFDoc, String paramString) {
/* 132 */     return new Action(CreateLaunch(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
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
/*     */   public static Action createHideField(PDFDoc paramPDFDoc, String[] paramArrayOfString) {
/* 145 */     return new Action(CreateHideField(paramPDFDoc.__GetHandle(), paramArrayOfString), paramPDFDoc);
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
/*     */   public static Action createImportData(PDFDoc paramPDFDoc, String paramString) {
/* 163 */     return new Action(CreateImportData(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
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
/*     */   public static Action createResetForm(PDFDoc paramPDFDoc) {
/* 175 */     return new Action(CreateResetForm(paramPDFDoc.__GetHandle()), paramPDFDoc);
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
/*     */   public static Action createJavaScript(PDFDoc paramPDFDoc, String paramString) {
/* 188 */     return new Action(CreateJavaScript(paramPDFDoc.__GetHandle(), paramString), paramPDFDoc);
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
/*     */   public Action(Obj paramObj) throws PDFNetException {
/* 202 */     this.a = paramObj.__GetHandle();
/* 203 */     this.b = paramObj.__GetRefHandle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean needsWriteLock() throws PDFNetException {
/* 214 */     return NeedsWriteLock(this.a);
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
/*     */   public KeyStrokeActionResult executeKeyStrokeAction(KeyStrokeEventData paramKeyStrokeEventData) throws PDFNetException {
/* 227 */     return new KeyStrokeActionResult(ExecuteKeyStrokeAction(this.a, paramKeyStrokeEventData.a));
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
/*     */   public void Execute() throws PDFNetException {
/* 239 */     Execute(this.a);
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
/*     */   public Obj GetNext() throws PDFNetException {
/* 260 */     return Obj.__Create(GetNext(this.a), this.b);
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
/*     */   public boolean equals(Object paramObject) {
/* 273 */     if (paramObject != null && paramObject.getClass().equals(getClass())) {
/* 274 */       return (this.a == ((Action)paramObject).a);
/*     */     }
/* 276 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 284 */     return (int)this.a;
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
/*     */   public boolean isValid() throws PDFNetException {
/* 298 */     return IsValid(this.a);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() throws PDFNetException {
/* 372 */     return GetType(this.a);
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
/*     */   public Destination getDest() throws PDFNetException {
/* 388 */     return new Destination(GetDest(this.a), this.b);
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
/*     */   public int getFormActionFlag(int paramInt) throws PDFNetException {
/* 446 */     return GetFormActionFlag(this.a, paramInt);
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
/*     */   public void setFormActionFlag(int paramInt, boolean paramBoolean) throws PDFNetException {
/* 460 */     SetFormActionFlag(this.a, paramInt, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 471 */     return Obj.__Create(this.a, this.b);
/*     */   }
/*     */ 
/*     */   
/*     */   Action(long paramLong, Object paramObject) {
/* 476 */     this.a = paramLong;
/* 477 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   public static Action __Create(long paramLong, Object paramObject) {
/* 481 */     if (paramLong == 0L) {
/* 482 */       return null;
/*     */     }
/* 484 */     return new Action(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 488 */     return this.a;
/*     */   }
/*     */   
/*     */   public Object __GetRefHandle() {
/* 492 */     return this.b;
/*     */   }
/*     */   
/*     */   static native boolean NeedsWriteLock(long paramLong);
/*     */   
/*     */   static native long ExecuteKeyStrokeAction(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateGoto(long paramLong);
/*     */   
/*     */   static native long CreateGoto(byte[] paramArrayOfbyte, long paramLong);
/*     */   
/*     */   static native long CreateGotoRemote(long paramLong, int paramInt);
/*     */   
/*     */   static native long CreateGotoRemote(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   static native long CreateURI(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateSubmitForm(long paramLong);
/*     */   
/*     */   static native long CreateLaunch(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateHideField(long paramLong, String[] paramArrayOfString);
/*     */   
/*     */   static native long CreateImportData(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateResetForm(long paramLong);
/*     */   
/*     */   static native long CreateJavaScript(long paramLong, String paramString);
/*     */   
/*     */   static native void Execute(long paramLong);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetDest(long paramLong);
/*     */   
/*     */   static native int GetFormActionFlag(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetFormActionFlag(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   static native long GetNext(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Action.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */