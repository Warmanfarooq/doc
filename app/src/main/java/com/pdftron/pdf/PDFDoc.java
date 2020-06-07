/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.fdf.FDFDoc;
/*      */ import com.pdftron.filters.FileDescriptorFilter;
/*      */ import com.pdftron.filters.FileDescriptorReadOnlyFilter;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.FilterReader;
/*      */ import com.pdftron.filters.SecondaryFileFilter;
/*      */ import com.pdftron.pdf.ocg.Config;
/*      */ import com.pdftron.pdf.struct.STree;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import com.pdftron.sdf.SDFDoc;
/*      */ import com.pdftron.sdf.SecurityHandler;
/*      */ import com.pdftron.sdf.SignatureHandler;
/*      */ import com.pdftron.sdf.UndoManager;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class PDFDoc
/*      */   extends Doc
/*      */ {
/*      */   public static final int e_none = 0;
/*      */   public static final int e_insert_bookmark = 1;
/*      */   public static final int e_forms_only = 0;
/*      */   public static final int e_annots_only = 1;
/*      */   public static final int e_both = 2;
/*      */   public static final int e_flatten_forms_only = 1;
/*      */   public static final int e_flatten_annots_only = 2;
/*      */   public static final int e_flatten_link_only = 4;
/*      */   public static final int e_flatten_all = 8;
/*      */   
/*      */   public enum InsertBookmarkMode
/*      */   {
/*   45 */     NONE(0),
/*      */ 
/*      */ 
/*      */     
/*   49 */     INSERT(1),
/*      */ 
/*      */ 
/*      */     
/*   53 */     INSERT_GOTO(2);
/*      */     private final int a;
/*      */     
/*   56 */     InsertBookmarkMode(int param1Int1) { this.a = param1Int1; } public final int getValue() {
/*   57 */       return this.a;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum FlattenMode
/*      */   {
/*   82 */     FORMS(1),
/*      */ 
/*      */ 
/*      */     
/*   86 */     ANNOTS(2),
/*      */ 
/*      */ 
/*      */     
/*   90 */     LINK(4),
/*      */ 
/*      */ 
/*      */     
/*   94 */     ALL(8);
/*      */     private final int a;
/*      */     
/*   97 */     FlattenMode(int param1Int1) { this.a = param1Int1; } public final int getValue() {
/*   98 */       return this.a;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  122 */   public Filter mCustomFilter = null;
/*  123 */   private String a = null;
/*      */   public static final int e_action_trigger_doc_will_close = 17;
/*      */   public static final int e_action_trigger_doc_will_save = 18;
/*      */   public static final int e_action_trigger_doc_did_save = 19;
/*      */   public static final int e_action_trigger_doc_will_print = 20;
/*      */   public static final int e_action_trigger_doc_did_print = 21;
/*      */   
/*      */   public PDFDoc() throws PDFNetException {
/*  131 */     this.impl = PDFDocCreate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(SDFDoc paramSDFDoc) throws PDFNetException {
/*  146 */     if (paramSDFDoc.__GetRefHandle() != null) {
/*  147 */       throw new PDFNetException("false", 78L, "PDFDoc.java", "PDFDoc(SDFDoc)", "SDFDoc is already owned by another document.");
/*      */     }
/*      */     
/*  150 */     this.impl = paramSDFDoc.__GetHandle();
/*  151 */     paramSDFDoc.__SetRef(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(String paramString) throws PDFNetException {
/*  165 */     this.a = paramString;
/*  166 */     this.impl = PDFDocCreate(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(Filter paramFilter) throws PDFNetException {
/*  189 */     this.mCustomFilter = paramFilter;
/*  190 */     paramFilter.__SetRefHandle(this);
/*  191 */     this.impl = PDFDocCreateFilter(paramFilter.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(byte[] paramArrayOfbyte) throws PDFNetException {
/*  204 */     if (paramArrayOfbyte == null) {
/*  205 */       throw new PDFNetException("", 0L, "PDFDoc.java", "PDFDoc(byte[])", "Memory buffer is null.");
/*      */     }
/*      */     
/*  208 */     this.impl = PDFDocCreate(paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(InputStream paramInputStream) throws PDFNetException, IOException {
/*  224 */     this(paramInputStream, 1048576);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDoc(InputStream paramInputStream, int paramInt) throws PDFNetException, IOException {
/*  244 */     long l = 0L;
/*      */     try {
/*  246 */       l = MemStreamCreateMemFilt(paramInputStream.available());
/*  247 */       byte[] arrayOfByte = new byte[paramInt];
/*      */       int i;
/*  249 */       while ((i = paramInputStream.read(arrayOfByte)) != -1) {
/*  250 */         MemStreamWriteData(l, arrayOfByte, i);
/*      */       }
/*      */ 
/*      */       
/*  254 */       long l1 = l;
/*  255 */       l = 0L;
/*  256 */       this.impl = MemStreamCreateDoc(l1); return;
/*  257 */     } catch (PDFNetException pDFNetException) {
/*      */       Filter filter;
/*  259 */       (filter = Filter.__Create(l, null)).destroy();
/*  260 */       throw pDFNetException;
/*  261 */     } catch (IOException iOException) {
/*      */       Filter filter;
/*  263 */       (filter = Filter.__Create(l, null)).destroy();
/*  264 */       throw iOException;
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void finalize() throws Throwable {
/*  269 */     close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void close() throws PDFNetException {
/*  278 */     if (this.impl != 0L) {
/*  279 */       Close(this.impl);
/*  280 */       this.impl = 0L;
/*      */       
/*  282 */       a();
/*  283 */       this.mCustomFilter = null;
/*  284 */       this.a = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void a() throws PDFNetException {
/*  290 */     if (this.mCustomFilter != null) {
/*  291 */       if (this.mCustomFilter instanceof FileDescriptorFilter) {
/*  292 */         ((FileDescriptorFilter)this.mCustomFilter).close(); return;
/*  293 */       }  if (this.mCustomFilter instanceof FileDescriptorReadOnlyFilter) {
/*  294 */         ((FileDescriptorReadOnlyFilter)this.mCustomFilter).close(); return;
/*  295 */       }  if (this.mCustomFilter instanceof SecondaryFileFilter) {
/*  296 */         ((SecondaryFileFilter)this.mCustomFilter).close();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public enum ActionTriggerMode
/*      */   {
/*  305 */     DOC_WILL_CLOSE(17),
/*      */ 
/*      */ 
/*      */     
/*  309 */     DOC_WILL_SAVE(18),
/*      */ 
/*      */ 
/*      */     
/*  313 */     DOC_DID_SAVE(19),
/*      */ 
/*      */ 
/*      */     
/*  317 */     DOC_WILL_PRINT(20),
/*      */ 
/*      */ 
/*      */     
/*  321 */     DOC_DID_PRINT(21);
/*      */     private final int a;
/*      */     
/*  324 */     ActionTriggerMode(int param1Int1) { this.a = param1Int1; } public final int getValue() {
/*  325 */       return this.a;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getTriggerAction(int paramInt) throws PDFNetException {
/*  370 */     return Obj.__Create(GetTriggerAction(this.impl, paramInt), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getTriggerAction(ActionTriggerMode paramActionTriggerMode) throws PDFNetException {
/*  381 */     return Obj.__Create(GetTriggerAction(this.impl, paramActionTriggerMode.getValue()), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEncrypted() throws PDFNetException {
/*  391 */     return IsEncrypted(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initSecurityHandler() throws PDFNetException {
/*  413 */     return InitSecurityHandler(this.impl, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initSecurityHandler(Object paramObject) throws PDFNetException {
/*  427 */     return InitSecurityHandler(this.impl, paramObject);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initStdSecurityHandler(String paramString) throws PDFNetException {
/*  454 */     return InitStdSecurityHandler(this.impl, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean initStdSecurityHandler(byte[] paramArrayOfbyte) throws PDFNetException {
/*  483 */     return InitStdSecurityHandlerBuffer(this.impl, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SecurityHandler getSecurityHandler() {
/*      */     SecurityHandler securityHandler;
/*  505 */     if ((securityHandler = SecurityHandler.__Create(GetSecurityHandler(this.impl), this)).__GetHandle() == 0L)
/*      */     {
/*  507 */       return null;
/*      */     }
/*      */     
/*  510 */     return securityHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecurityHandler(SecurityHandler paramSecurityHandler) {
/*  527 */     SetSecurityHandler(this.impl, paramSecurityHandler.__GetHandle());
/*  528 */     paramSecurityHandler.__SetRefHandle(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDocInfo getDocInfo() throws PDFNetException {
/*  540 */     return new PDFDocInfo(GetDocInfo(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFDocViewPrefs getViewPrefs() throws PDFNetException {
/*  554 */     return new PDFDocViewPrefs(GetViewPrefs(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isModified() throws PDFNetException {
/*  565 */     return IsModified(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasChangesSinceSnapshot() throws PDFNetException {
/*  576 */     return HasChangesSinceSnapshot(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasRepairedXRef() throws PDFNetException {
/*  592 */     return HasRepairedXRef(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLinearized() throws PDFNetException {
/*  623 */     return IsLinearized(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(String paramString, long paramLong, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  676 */     Save(this.impl, paramString, paramLong, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(String paramString, SDFDoc.SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  720 */     save(paramString, paramSaveMode.getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(String paramString, SDFDoc.SaveMode[] paramArrayOfSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  729 */     long l = 0L;
/*  730 */     if (paramArrayOfSaveMode != null) {
/*  731 */       int i; byte b; for (i = (paramArrayOfSaveMode = paramArrayOfSaveMode).length, b = 0; b < i; ) { SDFDoc.SaveMode saveMode = paramArrayOfSaveMode[b];
/*  732 */         l |= saveMode.getValue(); b++; }
/*      */     
/*      */     } 
/*  735 */     save(paramString, l, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] save(long paramLong, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  767 */     return Save(this.impl, paramLong, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] save(SDFDoc.SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  789 */     return save(paramSaveMode.getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] save(SDFDoc.SaveMode[] paramArrayOfSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*  798 */     long l = 0L;
/*  799 */     if (paramArrayOfSaveMode != null) {
/*  800 */       int i; byte b; for (i = (paramArrayOfSaveMode = paramArrayOfSaveMode).length, b = 0; b < i; ) { SDFDoc.SaveMode saveMode = paramArrayOfSaveMode[b];
/*  801 */         l |= saveMode.getValue(); b++; }
/*      */     
/*      */     } 
/*  804 */     return save(l, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, long paramLong, ProgressMonitor paramProgressMonitor) throws PDFNetException, IOException {
/*  840 */     save(paramOutputStream, paramLong, paramProgressMonitor, 1048576);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, SDFDoc.SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException, IOException {
/*  866 */     save(paramOutputStream, paramSaveMode.getValue(), paramProgressMonitor, 1048576);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, SDFDoc.SaveMode[] paramArrayOfSaveMode, ProgressMonitor paramProgressMonitor) throws PDFNetException, IOException {
/*  875 */     long l = 0L;
/*  876 */     if (paramArrayOfSaveMode != null) {
/*  877 */       int i; byte b; for (i = (paramArrayOfSaveMode = paramArrayOfSaveMode).length, b = 0; b < i; ) { SDFDoc.SaveMode saveMode = paramArrayOfSaveMode[b];
/*  878 */         l |= saveMode.getValue(); b++; }
/*      */     
/*      */     } 
/*  881 */     save(paramOutputStream, l, paramProgressMonitor, 1048576);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, long paramLong, ProgressMonitor paramProgressMonitor, int paramInt) throws PDFNetException, IOException {
/*  918 */     long arrayOfLong[], l1 = (arrayOfLong = SaveStream(this.impl, paramLong, paramProgressMonitor))[0];
/*  919 */     long l2 = arrayOfLong[1];
/*  920 */     byte[] arrayOfByte = new byte[paramInt];
/*      */     
/*  922 */     long l3 = l2 - paramInt;
/*      */     
/*  924 */     while (l1 < l3) {
/*  925 */       ReadData(arrayOfByte, paramInt, l1);
/*  926 */       paramOutputStream.write(arrayOfByte);
/*  927 */       l1 += paramInt;
/*      */     } 
/*      */     int i;
/*  930 */     if ((i = (int)(l2 - l1)) > 0) {
/*  931 */       ReadData(arrayOfByte, i, l1);
/*  932 */       paramOutputStream.write(arrayOfByte, 0, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, SDFDoc.SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor, int paramInt) throws PDFNetException, IOException {
/*  959 */     save(paramOutputStream, paramSaveMode.getValue(), paramProgressMonitor, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(OutputStream paramOutputStream, SDFDoc.SaveMode[] paramArrayOfSaveMode, ProgressMonitor paramProgressMonitor, int paramInt) throws PDFNetException, IOException {
/*  968 */     long l = 0L;
/*  969 */     if (paramArrayOfSaveMode != null) {
/*  970 */       int i; byte b; for (i = (paramArrayOfSaveMode = paramArrayOfSaveMode).length, b = 0; b < i; ) { SDFDoc.SaveMode saveMode = paramArrayOfSaveMode[b];
/*  971 */         l |= saveMode.getValue(); b++; }
/*      */     
/*      */     } 
/*  974 */     save(paramOutputStream, l, paramProgressMonitor, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Filter paramFilter, long paramLong) throws PDFNetException, IOException {
/* 1004 */     if (paramFilter instanceof FileDescriptorFilter && paramFilter
/* 1005 */       .isInputFilter() && ((FileDescriptorFilter)paramFilter).canWriteOnInputFilter()) {
/*      */       FileDescriptorFilter fileDescriptorFilter;
/* 1007 */       if ((fileDescriptorFilter = ((FileDescriptorFilter)paramFilter).createOutputIterator()) != null && !fileDescriptorFilter.isInputFilter()) {
/* 1008 */         SaveCustomFilter(this.impl, fileDescriptorFilter.__GetHandle(), paramLong);
/*      */       }
/* 1010 */       a();
/* 1011 */       this.mCustomFilter = paramFilter;
/* 1012 */       paramFilter.__SetRefHandle(this); return;
/* 1013 */     }  if (paramFilter instanceof SecondaryFileFilter && paramFilter.isInputFilter())
/*      */     { SecondaryFileFilter secondaryFileFilter;
/* 1015 */       if ((secondaryFileFilter = ((SecondaryFileFilter)paramFilter).createOutputIterator()) != null && !secondaryFileFilter.isInputFilter())
/*      */       { 
/* 1017 */         try { SaveCustomFilter(this.impl, secondaryFileFilter.__GetHandle(), paramLong); }
/*      */         finally
/* 1019 */         { secondaryFileFilter.close(); }  }
/*      */       else { return; }
/*      */        }
/* 1022 */     else { if (!paramFilter.isInputFilter()) {
/* 1023 */         SaveCustomFilter(this.impl, paramFilter.__GetHandle(), paramLong); return;
/*      */       } 
/* 1025 */       throw new PDFNetException("false", 568L, "PDFDoc.java", "PDFDoc(save)", "The filter is not an output filter."); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Filter paramFilter, SDFDoc.SaveMode paramSaveMode) throws PDFNetException, IOException {
/* 1047 */     save(paramFilter, paramSaveMode.getValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(Filter paramFilter, SDFDoc.SaveMode[] paramArrayOfSaveMode) throws PDFNetException, IOException {
/* 1056 */     long l = 0L;
/* 1057 */     if (paramArrayOfSaveMode != null) {
/* 1058 */       int i; byte b; for (i = (paramArrayOfSaveMode = paramArrayOfSaveMode).length, b = 0; b < i; ) { SDFDoc.SaveMode saveMode = paramArrayOfSaveMode[b];
/* 1059 */         l |= saveMode.getValue(); b++; }
/*      */     
/*      */     } 
/* 1062 */     save(paramFilter, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(long paramLong) throws PDFNetException, IOException {
/* 1081 */     if (paramLong != 32769L) {
/* 1082 */       throw new PDFNetException("false", 580L, "PDFDoc.java", "PDFDoc(save)", "This flag is not supported.");
/*      */     }
/*      */ 
/*      */     
/* 1086 */     if (this.mCustomFilter != null && this.mCustomFilter instanceof FileDescriptorFilter) {
/*      */       FileDescriptorFilter fileDescriptorFilter;
/* 1088 */       if ((fileDescriptorFilter = ((FileDescriptorFilter)this.mCustomFilter).createOutputIterator()) != null && !fileDescriptorFilter.isInputFilter()) {
/* 1089 */         long l = SaveCustomFilter2(this.impl, fileDescriptorFilter.__GetHandle(), paramLong);
/* 1090 */         this.mCustomFilter = (Filter)FileDescriptorFilter.__Create(l, fileDescriptorFilter);
/*      */       } else {
/* 1092 */         throw new PDFNetException("false", 1034L, "PDFDoc.java", "PDFDoc.save(long)", "The filter is not an output filter.");
/*      */       } 
/*      */     } else {
/*      */       
/* 1096 */       if (this.mCustomFilter != null && this.mCustomFilter instanceof SecondaryFileFilter) {
/*      */         SecondaryFileFilter secondaryFileFilter;
/* 1098 */         if ((secondaryFileFilter = ((SecondaryFileFilter)this.mCustomFilter).createOutputIterator()) != null && !secondaryFileFilter.isInputFilter()) {
/*      */           try {
/* 1100 */             SaveCustomFilter2(this.impl, secondaryFileFilter.__GetHandle(), paramLong);
/*      */           } finally {
/* 1102 */             secondaryFileFilter.close();
/*      */           } 
/*      */         } else {
/* 1105 */           throw new PDFNetException("false", 1047L, "PDFDoc.java", "PDFDoc.save(long)", "The filter is not an output filter.");
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/* 1110 */       throw new PDFNetException("false", 580L, "PDFDoc.java", "PDFDoc(save)", "Custom filter is not valid.");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save() throws PDFNetException, IOException {
/* 1128 */     if (this.a != null) {
/* 1129 */       Save(this.impl, this.a, 1L, null); return;
/*      */     } 
/* 1131 */     save(32769L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean saveIncrementalData(String paramString) throws PDFNetException {
/* 1146 */     return SaveIncrementalData(this.impl, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageIterator getPageIterator() throws PDFNetException {
/* 1163 */     return new PageIterator(GetPageIteratorBegin(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageIterator getPageIterator(int paramInt) throws PDFNetException {
/* 1175 */     return new PageIterator(GetPageIterator(this.impl, paramInt), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page getPage(int paramInt) throws PDFNetException {
/*      */     long l;
/* 1194 */     if ((l = GetPage(this.impl, paramInt)) != 0L) {
/* 1195 */       return new Page(l, this);
/*      */     }
/* 1197 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pageRemove(PageIterator paramPageIterator) throws PDFNetException {
/* 1212 */     PageRemove(this.impl, paramPageIterator.a());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pageInsert(PageIterator paramPageIterator, Page paramPage) throws PDFNetException {
/* 1229 */     PageInsert(this.impl, paramPageIterator.a(), paramPage.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertPages(int paramInt1, PDFDoc paramPDFDoc, int paramInt2, int paramInt3, int paramInt4, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*      */     PageSet pageSet;
/* 1259 */     (pageSet = new PageSet()).addRange(paramInt2, paramInt3);
/* 1260 */     insertPages(paramInt1, paramPDFDoc, pageSet, paramInt4, paramProgressMonitor);
/*      */     
/* 1262 */     pageSet.destroy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertPages(int paramInt1, PDFDoc paramPDFDoc, int paramInt2, int paramInt3, InsertBookmarkMode paramInsertBookmarkMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1289 */     insertPages(paramInt1, paramPDFDoc, paramInt2, paramInt3, paramInsertBookmarkMode.getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertPages(int paramInt1, PDFDoc paramPDFDoc, PageSet paramPageSet, int paramInt2, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1316 */     InsertPageSet(this.impl, paramInt1, paramPDFDoc.impl, paramPageSet.a, paramInt2, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void insertPages(int paramInt, PDFDoc paramPDFDoc, PageSet paramPageSet, InsertBookmarkMode paramInsertBookmarkMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1342 */     insertPages(paramInt, paramPDFDoc, paramPageSet, paramInsertBookmarkMode.getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void movePages(int paramInt1, PDFDoc paramPDFDoc, int paramInt2, int paramInt3, int paramInt4, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/*      */     PageSet pageSet;
/* 1377 */     (pageSet = new PageSet()).addRange(paramInt2, paramInt3);
/* 1378 */     movePages(paramInt1, paramPDFDoc, pageSet, paramInt4, paramProgressMonitor);
/*      */     
/* 1380 */     pageSet.destroy();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void movePages(int paramInt1, PDFDoc paramPDFDoc, int paramInt2, int paramInt3, InsertBookmarkMode paramInsertBookmarkMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1412 */     movePages(paramInt1, paramPDFDoc, paramInt2, paramInt3, paramInsertBookmarkMode.getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void movePages(int paramInt1, PDFDoc paramPDFDoc, PageSet paramPageSet, int paramInt2, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1444 */     MovePageSet(this.impl, paramInt1, paramPDFDoc.impl, paramPageSet.a, paramInt2, paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void movePages(int paramInt, PDFDoc paramPDFDoc, PageSet paramPageSet, InsertBookmarkMode paramInsertBookmarkMode, ProgressMonitor paramProgressMonitor) throws PDFNetException {
/* 1475 */     MovePageSet(this.impl, paramInt, paramPDFDoc.impl, paramPageSet.a, paramInsertBookmarkMode
/* 1476 */         .getValue(), paramProgressMonitor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pagePushFront(Page paramPage) throws PDFNetException {
/* 1488 */     PagePushFront(this.impl, paramPage.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void pagePushBack(Page paramPage) throws PDFNetException {
/* 1501 */     PagePushBack(this.impl, paramPage.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page[] importPages(Page[] paramArrayOfPage) throws PDFNetException {
/* 1521 */     return importPages(paramArrayOfPage, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page[] importPages(Page[] paramArrayOfPage, boolean paramBoolean) throws PDFNetException {
/* 1536 */     long[] arrayOfLong1 = new long[paramArrayOfPage.length];
/*      */     
/* 1538 */     for (byte b = 0; b < paramArrayOfPage.length; b++) {
/* 1539 */       arrayOfLong1[b] = (paramArrayOfPage[b]).a;
/*      */     }
/*      */     long[] arrayOfLong2;
/* 1542 */     paramArrayOfPage = new Page[(arrayOfLong2 = ImportPages(this.impl, arrayOfLong1, paramBoolean)).length];
/* 1543 */     for (paramBoolean = false; paramBoolean < arrayOfLong2.length; paramBoolean++) {
/* 1544 */       paramArrayOfPage[paramBoolean] = new Page(arrayOfLong2[paramBoolean], this);
/*      */     }
/* 1546 */     return paramArrayOfPage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page pageCreate() throws PDFNetException {
/* 1563 */     return pageCreate(new Rect(0.0D, 0.0D, 612.0D, 792.0D));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Page pageCreate(Rect paramRect) throws PDFNetException {
/* 1575 */     return new Page(PageCreate(this.impl, paramRect.a), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Bookmark getFirstBookmark() throws PDFNetException {
/* 1588 */     return new Bookmark(GetFirstBookmark(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addRootBookmark(Bookmark paramBookmark) throws PDFNetException {
/* 1601 */     AddRootBookmark(this.impl, paramBookmark.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getTrailer() throws PDFNetException {
/* 1612 */     return Obj.__Create(GetTrailer(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getRoot() throws PDFNetException {
/* 1623 */     return Obj.__Create(GetRoot(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initializeJSContext() throws PDFNetException {
/* 1630 */     JSContextInitialize(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getPages() throws PDFNetException {
/* 1640 */     return Obj.__Create(GetPages(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPageCount() throws PDFNetException {
/* 1650 */     return GetPagesCount(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldIterator getFieldIterator() throws PDFNetException {
/* 1675 */     return new FieldIterator(GetFieldIteratorBegin(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FieldIterator getFieldIterator(String paramString) throws PDFNetException {
/* 1694 */     return new FieldIterator(GetFieldIterator(this.impl, paramString), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field getField(String paramString) throws PDFNetException {
/*      */     long l;
/* 1707 */     if ((l = GetField(this.impl, paramString)) != 0L) {
/* 1708 */       return new Field(l, this);
/*      */     }
/* 1710 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field fieldCreate(String paramString, int paramInt) throws PDFNetException {
/* 1727 */     return new Field(FieldCreate(this.impl, paramString, paramInt, 0L, 0L), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field fieldCreate(String paramString, int paramInt, Obj paramObj) throws PDFNetException {
/* 1745 */     return new Field(FieldCreate(this.impl, paramString, paramInt, paramObj
/* 1746 */           .__GetHandle(), 0L), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field fieldCreate(String paramString, int paramInt, Obj paramObj1, Obj paramObj2) throws PDFNetException {
/* 1765 */     return new Field(FieldCreate(this.impl, paramString, paramInt, paramObj1
/* 1766 */           .__GetHandle(), paramObj2.__GetHandle()), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field fieldCreate(String paramString1, int paramInt, String paramString2, String paramString3) throws PDFNetException {
/* 1785 */     return new Field(FieldCreate(this.impl, paramString1, paramInt, paramString2, paramString3), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field fieldCreate(String paramString1, int paramInt, String paramString2) throws PDFNetException {
/* 1804 */     return new Field(FieldCreate(this.impl, paramString1, paramInt, paramString2, ""), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void refreshFieldAppearances() throws PDFNetException {
/* 1816 */     RefreshFieldAppearances(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flattenAnnotations(boolean paramBoolean) throws PDFNetException {
/* 1826 */     FlattenAnnotations(this.impl, paramBoolean);
/*      */   }
/*      */   
/*      */   public void flattenAnnotations() throws PDFNetException {
/* 1830 */     FlattenAnnotations(this.impl, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flattenAnnotationsAdvanced(long paramLong) throws PDFNetException {
/* 1847 */     FlattenAnnotationsAdvanced(this.impl, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flattenAnnotationsAdvanced(FlattenMode[] paramArrayOfFlattenMode) throws PDFNetException {
/* 1856 */     long l = 0L;
/* 1857 */     if (paramArrayOfFlattenMode != null) {
/* 1858 */       int i; byte b; for (i = (paramArrayOfFlattenMode = paramArrayOfFlattenMode).length, b = 0; b < i; ) { FlattenMode flattenMode = paramArrayOfFlattenMode[b];
/* 1859 */         l |= flattenMode.getValue(); b++; }
/*      */     
/*      */     } 
/* 1862 */     FlattenAnnotationsAdvanced(this.impl, l);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getAcroForm() throws PDFNetException {
/* 1873 */     return Obj.__Create(GetAcroForm(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDFDoc fdfExtract() throws PDFNetException {
/* 1883 */     return FDFDoc.__Create(FDFExtract(this.impl, 0));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDFDoc fdfExtract(int paramInt) throws PDFNetException {
/* 1894 */     return FDFDoc.__Create(FDFExtract(this.impl, paramInt));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDFDoc fdfExtract(ArrayList<Annot> paramArrayList) throws PDFNetException {
/* 1905 */     long[] arrayOfLong = new long[paramArrayList.size()];
/* 1906 */     for (byte b = 0; b < paramArrayList.size(); b++) {
/* 1907 */       Annot annot = paramArrayList.get(b);
/* 1908 */       arrayOfLong[b] = annot.getSDFObj().__GetHandle();
/*      */     } 
/* 1910 */     return FDFDoc.__Create(FDFExtract(this.impl, arrayOfLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDFDoc fdfExtract(Annot[] paramArrayOfAnnot) throws PDFNetException {
/* 1921 */     long[] arrayOfLong = new long[paramArrayOfAnnot.length];
/* 1922 */     for (byte b = 0; b < paramArrayOfAnnot.length; b++) {
/* 1923 */       arrayOfLong[b] = paramArrayOfAnnot[b].getSDFObj().__GetHandle();
/*      */     }
/* 1925 */     return FDFDoc.__Create(FDFExtract(this.impl, arrayOfLong));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FDFDoc fdfExtract(ArrayList<Annot> paramArrayList1, ArrayList<Annot> paramArrayList2, ArrayList<Annot> paramArrayList3) throws PDFNetException {
/* 1938 */     if (paramArrayList1 == null) {
/* 1939 */       paramArrayList1 = new ArrayList<>(0);
/*      */     }
/* 1941 */     if (paramArrayList2 == null) {
/* 1942 */       paramArrayList2 = new ArrayList<>(0);
/*      */     }
/* 1944 */     if (paramArrayList3 == null) {
/* 1945 */       paramArrayList3 = new ArrayList<>(0);
/*      */     }
/* 1947 */     long[] arrayOfLong1 = new long[paramArrayList1.size()];
/* 1948 */     for (byte b2 = 0; b2 < paramArrayList1.size(); b2++) {
/* 1949 */       Annot annot = paramArrayList1.get(b2);
/* 1950 */       arrayOfLong1[b2] = annot.getSDFObj().__GetHandle();
/*      */     } 
/* 1952 */     long[] arrayOfLong2 = new long[paramArrayList2.size()];
/* 1953 */     for (byte b3 = 0; b3 < paramArrayList2.size(); b3++) {
/* 1954 */       Annot annot = paramArrayList2.get(b3);
/* 1955 */       arrayOfLong2[b3] = annot.getSDFObj().__GetHandle();
/*      */     } 
/* 1957 */     long[] arrayOfLong3 = new long[paramArrayList3.size()];
/* 1958 */     for (byte b1 = 0; b1 < paramArrayList3.size(); b1++) {
/* 1959 */       Annot annot = paramArrayList3.get(b1);
/* 1960 */       arrayOfLong3[b1] = annot.getSDFObj().__GetHandle();
/*      */     } 
/* 1962 */     return FDFDoc.__Create(FDFExtract(this.impl, arrayOfLong1, arrayOfLong2, arrayOfLong3));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fdfMerge(FDFDoc paramFDFDoc) throws PDFNetException {
/* 1973 */     FDFMerge(this.impl, paramFDFDoc.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void fdfUpdate(FDFDoc paramFDFDoc) throws PDFNetException {
/* 1984 */     FDFUpdate(this.impl, paramFDFDoc.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeXFDF(Filter paramFilter, String paramString) throws PDFNetException {
/* 2000 */     MergeXFDF(this.impl, paramFilter.__GetHandle(), paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void mergeXFDFString(String paramString1, String paramString2) throws PDFNetException {
/* 2016 */     MergeXFDFString(this.impl, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Action getOpenAction() throws PDFNetException {
/* 2031 */     return new Action(GetOpenAction(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOpenAction(Action paramAction) throws PDFNetException {
/* 2044 */     SetOpenAction(this.impl, paramAction.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addFileAttachment(String paramString, FileSpec paramFileSpec) throws PDFNetException {
/* 2070 */     AddFileAttachment(this.impl, paramString, paramFileSpec.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PageLabel getPageLabel(int paramInt) throws PDFNetException {
/* 2085 */     return new PageLabel(GetPageLabel(this.impl, paramInt), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPageLabel(int paramInt, PageLabel paramPageLabel) throws PDFNetException {
/* 2104 */     SetPageLabel(this.impl, paramInt, paramPageLabel.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePageLabel(int paramInt) throws PDFNetException {
/* 2118 */     RemovePageLabel(this.impl, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public STree getStructTree() throws PDFNetException {
/* 2128 */     return STree.__Create(GetStructTree(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasOC() throws PDFNetException {
/* 2141 */     return HasOC(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getOCGs() throws PDFNetException {
/* 2154 */     return Obj.__Create(GetOCGs(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Config getOCGConfig() throws PDFNetException {
/* 2165 */     return Config.__Create(GetOCGConfig(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectName(String paramString) throws PDFNetException {
/* 2179 */     return Obj.__Create(CreateIndirectName(this.impl, paramString), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectArray() throws PDFNetException {
/* 2189 */     return Obj.__Create(CreateIndirectArray(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectBool(boolean paramBoolean) throws PDFNetException {
/* 2200 */     return Obj.__Create(CreateIndirectBool(this.impl, paramBoolean), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectDict() throws PDFNetException {
/* 2210 */     return Obj.__Create(CreateIndirectDict(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectNull() throws PDFNetException {
/* 2220 */     return Obj.__Create(CreateIndirectNull(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectNumber(double paramDouble) throws PDFNetException {
/* 2231 */     return Obj.__Create(CreateIndirectNumber(this.impl, paramDouble), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectString(byte[] paramArrayOfbyte) throws PDFNetException {
/* 2243 */     return Obj.__Create(CreateIndirectString(this.impl, paramArrayOfbyte), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectString(String paramString) throws PDFNetException {
/* 2255 */     return Obj.__Create(CreateIndirectString(this.impl, paramString), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectStream(FilterReader paramFilterReader) throws PDFNetException {
/* 2266 */     return Obj.__Create(CreateIndirectStream(this.impl, paramFilterReader.__GetHandle(), 0L), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectStream(FilterReader paramFilterReader, Filter paramFilter) throws PDFNetException {
/* 2280 */     if (paramFilter != null)
/* 2281 */       paramFilter.__SetRefHandle(this); 
/* 2282 */     return Obj.__Create(
/* 2283 */         CreateIndirectStream(this.impl, paramFilterReader.__GetHandle(), paramFilter
/* 2284 */           .__GetHandle()), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectStream(byte[] paramArrayOfbyte) throws PDFNetException {
/* 2295 */     return Obj.__Create(CreateIndirectStream(this.impl, paramArrayOfbyte, 0L), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectStream(byte[] paramArrayOfbyte, Filter paramFilter) throws PDFNetException {
/* 2308 */     if (paramFilter != null)
/* 2309 */       paramFilter.__SetRefHandle(this); 
/* 2310 */     return Obj.__Create(
/* 2311 */         CreateIndirectStream(this.impl, paramArrayOfbyte, paramFilter.__GetHandle()), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SDFDoc getSDFDoc() {
/* 2321 */     return SDFDoc.__Create(this.impl, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void lock() throws PDFNetException {
/* 2333 */     Lock(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unlock() throws PDFNetException {
/* 2342 */     Unlock(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryLock() throws PDFNetException {
/* 2354 */     return TryLock(this.impl, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean timedLock(int paramInt) throws PDFNetException {
/* 2368 */     return TryLock(this.impl, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void lockRead() throws PDFNetException {
/* 2384 */     LockRead(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unlockRead() throws PDFNetException {
/* 2393 */     UnlockRead(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tryLockRead() throws PDFNetException {
/* 2405 */     return TryLockRead(this.impl, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean timedLockRead(int paramInt) throws PDFNetException {
/* 2419 */     return TryLockRead(this.impl, paramInt);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFileName() throws PDFNetException {
/* 2431 */     return GetFileName(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateThumbnails(long paramLong) throws PDFNetException {
/* 2440 */     GenerateThumbnails(this.impl, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendVisualDiff(Page paramPage1, Page paramPage2, DiffOptions paramDiffOptions) throws PDFNetException {
/* 2449 */     AppendVisualDiff(this.impl, (paramPage1 != null) ? paramPage1.a : 0L, (paramPage2 != null) ? paramPage2.a : 0L, (paramDiffOptions != null) ? paramDiffOptions.a() : 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getDownloadedByteCount() throws PDFNetException {
/* 2463 */     return GetDownloadedByteCount(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getTotalRemoteByteCount() throws PDFNetException {
/* 2477 */     return GetTotalRemoteByteCount(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasDownloader() throws PDFNetException {
/* 2486 */     return HasDownloader(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UndoManager getUndoManager() throws PDFNetException {
/* 2495 */     return new UndoManager(GetUndoManager(this.impl));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSecurity() {
/* 2502 */     RemoveSecurity(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addHighlights(String paramString) {
/* 2516 */     AddHighlights(this.impl, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isTagged() {
/* 2525 */     return IsTagged(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasSignatures() {
/* 2534 */     return HasSignatures(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addSignatureHandler(SignatureHandler paramSignatureHandler) throws PDFNetException {
/* 2551 */     return AddSignatureHandler(this.impl, paramSignatureHandler);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addStdSignatureHandler(String paramString1, String paramString2) throws PDFNetException {
/* 2567 */     return AddStdSignatureHandlerFromFile(this.impl, paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long addStdSignatureHandler(byte[] paramArrayOfbyte, String paramString) throws PDFNetException {
/* 2583 */     return AddStdSignatureHandlerFromBuffer(this.impl, paramArrayOfbyte, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSignatureHandler(long paramLong) throws PDFNetException {
/* 2595 */     RemoveSignatureHandler(this.impl, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public SignatureHandler getSignatureHandler(long paramLong) throws PDFNetException {
/* 2610 */     return GetSignatureHandler(this.impl, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigitalSignatureField createDigitalSignatureField(String paramString) throws PDFNetException {
/* 2623 */     return new DigitalSignatureField(CreateDigitalSignatureField(this.impl, paramString), this);
/*      */   }
/*      */   
/*      */   public DigitalSignatureField createDigitalSignatureField() throws PDFNetException {
/* 2627 */     return new DigitalSignatureField(CreateDigitalSignatureField(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigitalSignatureFieldIterator getDigitalSignatureFieldIterator() throws PDFNetException {
/* 2638 */     return new DigitalSignatureFieldIterator(GetDigitalSignatureFieldIteratorBegin(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DigitalSignatureField.DocumentPermissions getDigitalSignaturePermissions() throws PDFNetException {
/* 2650 */     return DigitalSignatureField.DocumentPermissions.a(GetDigitalSignaturePermissions(this.impl));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveViewerOptimized(String paramString, ViewerOptimizedOptions paramViewerOptimizedOptions) {
/* 2666 */     SaveViewerOptimized(this.impl, paramString, paramViewerOptimizedOptions.a.__GetHandle());
/*      */   }
/*      */ 
/*      */   
/*      */   PDFDoc(long paramLong) {
/* 2671 */     this.impl = paramLong;
/*      */   }
/*      */ 
/*      */   
/*      */   public static PDFDoc __Create(long paramLong) {
/* 2676 */     return new PDFDoc(paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   public long __GetHandle() {
/* 2681 */     return this.impl;
/*      */   }
/*      */   
/*      */   static native long PDFDocCreate();
/*      */   
/*      */   static native long PDFDocCreate(String paramString);
/*      */   
/*      */   static native long PDFDocCreateFilter(long paramLong);
/*      */   
/*      */   static native long PDFDocCreate(byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long GetTriggerAction(long paramLong, int paramInt);
/*      */   
/*      */   static native long MemStreamCreateMemFilt(long paramLong) throws PDFNetException;
/*      */   
/*      */   static native void MemStreamWriteData(long paramLong, byte[] paramArrayOfbyte, int paramInt);
/*      */   
/*      */   static native long MemStreamCreateDoc(long paramLong);
/*      */   
/*      */   static native void Close(long paramLong);
/*      */   
/*      */   static native boolean IsEncrypted(long paramLong);
/*      */   
/*      */   static native boolean InitSecurityHandler(long paramLong, Object paramObject);
/*      */   
/*      */   static native boolean InitStdSecurityHandler(long paramLong, String paramString);
/*      */   
/*      */   static native boolean InitStdSecurityHandlerBuffer(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long GetSecurityHandler(long paramLong);
/*      */   
/*      */   static native void SetSecurityHandler(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetDocInfo(long paramLong);
/*      */   
/*      */   static native long GetViewPrefs(long paramLong);
/*      */   
/*      */   static native boolean IsModified(long paramLong);
/*      */   
/*      */   static native boolean HasChangesSinceSnapshot(long paramLong);
/*      */   
/*      */   static native boolean HasRepairedXRef(long paramLong);
/*      */   
/*      */   static native boolean IsLinearized(long paramLong);
/*      */   
/*      */   static native boolean SaveIncrementalData(long paramLong, String paramString);
/*      */   
/*      */   static native void Save(long paramLong1, String paramString, long paramLong2, ProgressMonitor paramProgressMonitor);
/*      */   
/*      */   static native byte[] Save(long paramLong1, long paramLong2, ProgressMonitor paramProgressMonitor);
/*      */   
/*      */   static native long[] SaveStream(long paramLong1, long paramLong2, ProgressMonitor paramProgressMonitor);
/*      */   
/*      */   static native void SaveCustomFilter(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long SaveCustomFilter2(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void ReadData(byte[] paramArrayOfbyte, int paramInt, long paramLong);
/*      */   
/*      */   static native void RemoveSecurity(long paramLong);
/*      */   
/*      */   static native long GetPageIteratorBegin(long paramLong);
/*      */   
/*      */   static native long GetPageIterator(long paramLong, int paramInt);
/*      */   
/*      */   static native long GetPage(long paramLong, int paramInt);
/*      */   
/*      */   static native void PageRemove(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void PageInsert(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native void InsertPageSet(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, ProgressMonitor paramProgressMonitor);
/*      */   
/*      */   static native void MovePageSet(long paramLong1, int paramInt1, long paramLong2, long paramLong3, int paramInt2, ProgressMonitor paramProgressMonitor);
/*      */   
/*      */   static native void PagePushFront(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void PagePushBack(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long[] ImportPages(long paramLong, long[] paramArrayOflong, boolean paramBoolean);
/*      */   
/*      */   static native long PageCreate(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetFirstBookmark(long paramLong);
/*      */   
/*      */   static native void AddRootBookmark(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetTrailer(long paramLong);
/*      */   
/*      */   static native long GetRoot(long paramLong);
/*      */   
/*      */   static native void JSContextInitialize(long paramLong);
/*      */   
/*      */   static native long GetPages(long paramLong);
/*      */   
/*      */   static native int GetPagesCount(long paramLong);
/*      */   
/*      */   static native long GetFieldIteratorBegin(long paramLong);
/*      */   
/*      */   static native long GetFieldIterator(long paramLong, String paramString);
/*      */   
/*      */   static native long GetField(long paramLong, String paramString);
/*      */   
/*      */   static native long FieldCreate(long paramLong1, String paramString, int paramInt, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long FieldCreate(long paramLong, String paramString1, int paramInt, String paramString2, String paramString3);
/*      */   
/*      */   static native void RefreshFieldAppearances(long paramLong);
/*      */   
/*      */   static native void FlattenAnnotations(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native void FlattenAnnotationsAdvanced(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetAcroForm(long paramLong);
/*      */   
/*      */   static native long FDFExtract(long paramLong, int paramInt);
/*      */   
/*      */   static native long FDFExtract(long paramLong, long[] paramArrayOflong);
/*      */   
/*      */   static native long FDFExtract(long paramLong, long[] paramArrayOflong1, long[] paramArrayOflong2, long[] paramArrayOflong3);
/*      */   
/*      */   static native void FDFMerge(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void FDFUpdate(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void MergeXFDF(long paramLong1, long paramLong2, String paramString);
/*      */   
/*      */   static native void MergeXFDFString(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native long GetOpenAction(long paramLong);
/*      */   
/*      */   static native void SetOpenAction(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void AddFileAttachment(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long GetPageLabel(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetPageLabel(long paramLong1, int paramInt, long paramLong2);
/*      */   
/*      */   static native void RemovePageLabel(long paramLong, int paramInt);
/*      */   
/*      */   static native long GetStructTree(long paramLong);
/*      */   
/*      */   static native boolean HasOC(long paramLong);
/*      */   
/*      */   static native long GetOCGs(long paramLong);
/*      */   
/*      */   static native long GetOCGConfig(long paramLong);
/*      */   
/*      */   static native long CreateIndirectName(long paramLong, String paramString);
/*      */   
/*      */   static native long CreateIndirectArray(long paramLong);
/*      */   
/*      */   static native long CreateIndirectBool(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native long CreateIndirectDict(long paramLong);
/*      */   
/*      */   static native long CreateIndirectNull(long paramLong);
/*      */   
/*      */   static native long CreateIndirectNumber(long paramLong, double paramDouble);
/*      */   
/*      */   static native long CreateIndirectString(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long CreateIndirectString(long paramLong, String paramString);
/*      */   
/*      */   static native long CreateIndirectStream(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long CreateIndirectStream(long paramLong1, byte[] paramArrayOfbyte, long paramLong2);
/*      */   
/*      */   static native void Lock(long paramLong);
/*      */   
/*      */   static native void Unlock(long paramLong);
/*      */   
/*      */   static native boolean TryLock(long paramLong, int paramInt);
/*      */   
/*      */   static native void LockRead(long paramLong);
/*      */   
/*      */   static native void UnlockRead(long paramLong);
/*      */   
/*      */   static native boolean TryLockRead(long paramLong, int paramInt);
/*      */   
/*      */   static native void AddHighlights(long paramLong, String paramString);
/*      */   
/*      */   static native boolean IsTagged(long paramLong);
/*      */   
/*      */   static native String GetFileName(long paramLong);
/*      */   
/*      */   static native void GenerateThumbnails(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void AppendVisualDiff(long paramLong1, long paramLong2, long paramLong3, long paramLong4);
/*      */   
/*      */   static native long GetDownloadedByteCount(long paramLong);
/*      */   
/*      */   static native long GetTotalRemoteByteCount(long paramLong);
/*      */   
/*      */   static native boolean HasDownloader(long paramLong);
/*      */   
/*      */   static native long GetUndoManager(long paramLong);
/*      */   
/*      */   static native boolean HasSignatures(long paramLong);
/*      */   
/*      */   static native long AddSignatureHandler(long paramLong, SignatureHandler paramSignatureHandler);
/*      */   
/*      */   static native long AddStdSignatureHandlerFromFile(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native long AddStdSignatureHandlerFromBuffer(long paramLong, byte[] paramArrayOfbyte, String paramString);
/*      */   
/*      */   static native SignatureHandler RemoveSignatureHandler(long paramLong1, long paramLong2);
/*      */   
/*      */   static native SignatureHandler GetSignatureHandler(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long CreateDigitalSignatureField(long paramLong, String paramString);
/*      */   
/*      */   static native long CreateDigitalSignatureField(long paramLong);
/*      */   
/*      */   static native long GetDigitalSignatureFieldIteratorBegin(long paramLong);
/*      */   
/*      */   static native int GetDigitalSignaturePermissions(long paramLong);
/*      */   
/*      */   static native void SaveViewerOptimized(long paramLong1, String paramString, long paramLong2);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\PDFDoc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */