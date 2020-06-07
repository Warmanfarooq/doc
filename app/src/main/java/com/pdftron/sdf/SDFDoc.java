/*      */ package com.pdftron.sdf;
/*      */ 
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.FilterReader;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class SDFDoc
/*      */   extends Doc
/*      */ {
/*      */   public static final int e_incremental = 1;
/*      */   public static final int e_remove_unused = 2;
/*      */   public static final int e_hex_strings = 4;
/*      */   public static final int e_omit_xref = 8;
/*      */   public static final int e_linearized = 16;
/*      */   public static final int e_compatibility = 32;
/*      */   private Object a;
/*      */   
/*      */   public SDFDoc() throws PDFNetException {
/*  112 */     this.impl = SDFDocCreate();
/*  113 */     this.a = null;
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
/*      */   public SDFDoc(String paramString) throws PDFNetException {
/*  127 */     this.impl = SDFDocCreate(paramString);
/*  128 */     this.a = null;
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
/*      */   public SDFDoc(Filter paramFilter) throws PDFNetException {
/*  147 */     paramFilter.__SetRefHandle(this);
/*  148 */     this.impl = SDFDocCreate(paramFilter.__GetHandle());
/*  149 */     this.a = null;
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
/*      */   public SDFDoc(byte[] paramArrayOfbyte) throws PDFNetException {
/*  167 */     this.impl = SDFDocCreate(paramArrayOfbyte);
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
/*      */   public SDFDoc(InputStream paramInputStream) throws PDFNetException, IOException {
/*  181 */     this(paramInputStream, 1048576);
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
/*      */   public SDFDoc(InputStream paramInputStream, int paramInt) throws PDFNetException, IOException {
/*  195 */     long l = 0L;
/*      */     
/*      */     try {
/*  198 */       l = MemStreamCreateMemFilt(paramInputStream.available());
/*  199 */       byte[] arrayOfByte = new byte[paramInt];
/*      */       int i;
/*  201 */       while ((i = paramInputStream.read(arrayOfByte)) != -1)
/*      */       {
/*  203 */         MemStreamWriteData(l, arrayOfByte, i);
/*      */       }
/*  205 */       this.impl = MemStreamCreateDoc(l);
/*      */       return;
/*  207 */     } catch (PDFNetException pDFNetException) {
/*      */       Filter filter;
/*      */       
/*  210 */       (filter = Filter.__Create(l, null)).destroy();
/*  211 */       throw pDFNetException;
/*      */     }
/*  213 */     catch (IOException iOException) {
/*      */       Filter filter;
/*      */       
/*  216 */       (filter = Filter.__Create(l, null)).destroy();
/*  217 */       throw iOException;
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
/*      */   public void close() throws PDFNetException {
/*  235 */     if (this.impl != 0L && this.a == null) {
/*      */       
/*  237 */       Destroy(this.impl);
/*  238 */       this.impl = 0L;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void finalize() throws Throwable {
/*  247 */     close();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEncrypted() throws PDFNetException {
/*  258 */     return IsEncrypted(this.impl);
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
/*      */   public boolean initSecurityHandler() throws PDFNetException {
/*  281 */     return InitSecurityHandler(this.impl, null);
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
/*      */   public boolean initSecurityHandler(Object paramObject) throws PDFNetException {
/*  306 */     return InitSecurityHandler(this.impl, paramObject);
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
/*  333 */     return InitStdSecurityHandler(this.impl, paramString);
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
/*  362 */     return InitStdSecurityHandlerBuffer(this.impl, paramArrayOfbyte);
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
/*  373 */     return IsModified(this.impl);
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
/*  389 */     return HasRepairedXRef(this.impl);
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
/*      */   public void enableDiskCaching(boolean paramBoolean) {
/*  404 */     EnableDiskCaching(this.impl, paramBoolean);
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
/*      */   public boolean isFullSaveRequired() throws PDFNetException {
/*  416 */     return IsFullSaveRequired(this.impl);
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
/*      */   public boolean canSaveToPath(String paramString, long paramLong) throws PDFNetException {
/*  429 */     return CanSaveToPath(this.impl, paramString, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSaveToPath(String paramString, SaveMode paramSaveMode) throws PDFNetException {
/*  440 */     return CanSaveToPath(this.impl, paramString, paramSaveMode.getValue());
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
/*      */   public Obj getTrailer() throws PDFNetException {
/*  452 */     return Obj.__Create(GetTrailer(this.impl), this);
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
/*      */   public Obj getObj(long paramLong) throws PDFNetException {
/*  464 */     return Obj.__Create(GetObj(this.impl, paramLong), this);
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
/*      */   public Obj importObj(Obj paramObj, boolean paramBoolean) throws PDFNetException {
/*  487 */     return Obj.__Create(ImportObj(this.impl, paramObj.a, paramBoolean), this);
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
/*      */   public Obj[] importObjs(Obj[] paramArrayOfObj) throws PDFNetException {
/*  506 */     return importObjs(paramArrayOfObj, null);
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
/*      */   public Obj[] importObjs(Obj[] paramArrayOfObj1, Obj[] paramArrayOfObj2) throws PDFNetException {
/*  526 */     long[] arrayOfLong1 = new long[paramArrayOfObj1.length];
/*  527 */     long[] arrayOfLong2 = null; byte b2;
/*  528 */     for (b2 = 0; b2 < arrayOfLong1.length; b2++)
/*      */     {
/*  530 */       arrayOfLong1[b2] = (paramArrayOfObj1[b2]).a;
/*      */     }
/*  532 */     if (paramArrayOfObj2 != null) {
/*      */       
/*  534 */       arrayOfLong2 = new long[paramArrayOfObj2.length];
/*  535 */       for (b2 = 0; b2 < arrayOfLong2.length; b2++)
/*      */       {
/*  537 */         arrayOfLong2[b2] = (paramArrayOfObj2[b2]).a;
/*      */       }
/*      */     } 
/*      */     long[] arrayOfLong3;
/*  541 */     paramArrayOfObj1 = new Obj[(arrayOfLong3 = ImportObjs(this.impl, arrayOfLong1, arrayOfLong2)).length];
/*  542 */     for (byte b1 = 0; b1 < arrayOfLong3.length; b1++)
/*      */     {
/*  544 */       paramArrayOfObj1[b1] = Obj.__Create(arrayOfLong3[b1], this);
/*      */     }
/*  546 */     return paramArrayOfObj1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long xRefSize() throws PDFNetException {
/*  557 */     return XRefSize(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void clearMarks() throws PDFNetException {
/*  567 */     ClearMarks(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum SaveMode
/*      */   {
/*  577 */     INCREMENTAL(1),
/*      */ 
/*      */ 
/*      */     
/*  581 */     REMOVE_UNUSED(2),
/*      */ 
/*      */ 
/*      */     
/*  585 */     HEX_STRINGS(4),
/*      */ 
/*      */ 
/*      */     
/*  589 */     OMIT_XREF(8),
/*      */ 
/*      */ 
/*      */     
/*  593 */     LINEARIZED(16),
/*      */ 
/*      */ 
/*      */     
/*  597 */     COMPATIBILITY(32),
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  602 */     NO_FLAGS(0);
/*      */     private final int a;
/*      */     
/*  605 */     SaveMode(int param1Int1) { this.a = param1Int1; } public final int getValue() {
/*  606 */       return this.a;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void save(String paramString1, long paramLong, ProgressMonitor paramProgressMonitor, String paramString2) throws PDFNetException {
/*  685 */     Save(this.impl, paramString1, paramLong, paramProgressMonitor, paramString2);
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
/*      */   public void save(String paramString1, SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor, String paramString2) throws PDFNetException {
/*  718 */     Save(this.impl, paramString1, paramSaveMode.getValue(), paramProgressMonitor, paramString2);
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
/*      */   public byte[] save(long paramLong, ProgressMonitor paramProgressMonitor, String paramString) throws PDFNetException {
/*  747 */     return Save(this.impl, paramLong, paramProgressMonitor, paramString);
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
/*      */   public byte[] save(SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor, String paramString) throws PDFNetException {
/*  764 */     return Save(this.impl, paramSaveMode.getValue(), paramProgressMonitor, paramString);
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
/*      */   public void save(OutputStream paramOutputStream, long paramLong, ProgressMonitor paramProgressMonitor, String paramString) throws PDFNetException, IOException {
/*  792 */     save(paramOutputStream, paramLong, paramProgressMonitor, paramString, 1048576);
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
/*      */   public void save(OutputStream paramOutputStream, SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor, String paramString) throws PDFNetException, IOException {
/*  810 */     save(paramOutputStream, paramSaveMode.getValue(), paramProgressMonitor, paramString, 1048576);
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
/*      */   public void save(OutputStream paramOutputStream, long paramLong, ProgressMonitor paramProgressMonitor, String paramString, int paramInt) throws PDFNetException, IOException {
/*  841 */     long arrayOfLong[], l1 = (arrayOfLong = SaveStream(this.impl, paramLong, paramProgressMonitor, paramString))[0];
/*  842 */     long l2 = arrayOfLong[1];
/*  843 */     byte[] arrayOfByte = new byte[paramInt];
/*      */     
/*  845 */     long l3 = l2 - paramInt;
/*      */     
/*  847 */     while (l1 < l3) {
/*  848 */       ReadData(arrayOfByte, paramInt, l1);
/*  849 */       paramOutputStream.write(arrayOfByte);
/*  850 */       l1 += paramInt;
/*      */     } 
/*      */     int i;
/*  853 */     if ((i = (int)(l2 - l1)) > 0) {
/*  854 */       ReadData(arrayOfByte, i, l1);
/*  855 */       paramOutputStream.write(arrayOfByte, 0, i);
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
/*      */   public void save(OutputStream paramOutputStream, SaveMode paramSaveMode, ProgressMonitor paramProgressMonitor, String paramString, int paramInt) throws PDFNetException, IOException {
/*  875 */     save(paramOutputStream, paramSaveMode.getValue(), paramProgressMonitor, paramString, paramInt);
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
/*      */   public String getHeader() throws PDFNetException {
/*  891 */     return GetHeader(this.impl);
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
/*      */   public SecurityHandler getSecurityHandler() {
/*  910 */     return SecurityHandler.__Create(GetSecurityHandler(this.impl), this);
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
/*      */   public void setSecurityHandler(SecurityHandler paramSecurityHandler) {
/*  924 */     paramSecurityHandler.b = this;
/*  925 */     SetSecurityHandler(this.impl, paramSecurityHandler.a);
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
/*      */   public void swap(long paramLong1, long paramLong2) throws PDFNetException {
/*  944 */     Swap(this.impl, paramLong1, paramLong2);
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
/*      */   public Obj createIndirectName(String paramString) throws PDFNetException {
/*  959 */     return Obj.__Create(CreateIndirectName(this.impl, paramString), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectArray() throws PDFNetException {
/*  970 */     return Obj.__Create(CreateIndirectArray(this.impl), this);
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
/*      */   public Obj createIndirectBool(boolean paramBoolean) throws PDFNetException {
/*  982 */     return Obj.__Create(CreateIndirectBool(this.impl, paramBoolean), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectDict() throws PDFNetException {
/*  993 */     return Obj.__Create(CreateIndirectDict(this.impl), this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj createIndirectNull() throws PDFNetException {
/* 1004 */     return Obj.__Create(CreateIndirectNull(this.impl), this);
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
/*      */   public Obj createIndirectNumber(double paramDouble) throws PDFNetException {
/* 1016 */     return Obj.__Create(CreateIndirectNumber(this.impl, paramDouble), this);
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
/* 1028 */     return Obj.__Create(CreateIndirectString(this.impl, paramArrayOfbyte), this);
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
/* 1040 */     return Obj.__Create(CreateIndirectString(this.impl, paramString), this);
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
/*      */   public Obj createIndirectStream(FilterReader paramFilterReader) throws PDFNetException {
/* 1052 */     return Obj.__Create(CreateIndirectStream(this.impl, paramFilterReader.__GetHandle(), 0L), this);
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
/*      */   public Obj createIndirectStream(FilterReader paramFilterReader, Filter paramFilter) throws PDFNetException {
/* 1065 */     if (paramFilter != null)
/* 1066 */       paramFilter.__SetRefHandle(this); 
/* 1067 */     return Obj.__Create(CreateIndirectStream(this.impl, paramFilterReader.__GetHandle(), paramFilter.__GetHandle()), this);
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
/*      */   public Obj createIndirectStream(byte[] paramArrayOfbyte) throws PDFNetException {
/* 1079 */     return Obj.__Create(CreateIndirectStream(this.impl, paramArrayOfbyte, 0L), this);
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
/* 1092 */     if (paramFilter != null)
/* 1093 */       paramFilter.__SetRefHandle(this); 
/* 1094 */     return Obj.__Create(CreateIndirectStream(this.impl, paramArrayOfbyte, paramFilter.__GetHandle()), this);
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
/*      */   public boolean isLinearized() throws PDFNetException {
/* 1120 */     return IsLinearized(this.impl);
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
/*      */   public Obj getLinearizationDict() throws PDFNetException {
/* 1132 */     return Obj.__Create(GetLinearizationDict(this.impl), this);
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
/*      */   public Obj getHintStream() throws PDFNetException {
/* 1144 */     return Obj.__Create(GetHintStream(this.impl), this);
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
/* 1156 */     Lock(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unlock() throws PDFNetException {
/* 1166 */     Unlock(this.impl);
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
/*      */   public boolean timedLock(int paramInt) throws PDFNetException {
/* 1178 */     return TryLock(this.impl, paramInt);
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
/* 1194 */     LockRead(this.impl);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void unlockRead() throws PDFNetException {
/* 1203 */     UnlockRead(this.impl);
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
/* 1215 */     return TryLockRead(this.impl, 0);
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
/* 1229 */     return TryLockRead(this.impl, paramInt);
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
/*      */   public String getFileName() throws PDFNetException {
/* 1242 */     return GetFileName(this.impl);
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
/*      */   SDFDoc(long paramLong, Object paramObject) {
/* 1255 */     this.impl = paramLong;
/* 1256 */     this.a = paramObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static SDFDoc __Create(long paramLong, Object paramObject) {
/* 1261 */     return new SDFDoc(paramLong, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   public void __SetRef(Object paramObject) {
/* 1266 */     this.a = paramObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object __GetRefHandle() {
/* 1271 */     return this.a;
/*      */   }
/*      */ 
/*      */   
/*      */   public long __GetHandle() {
/* 1276 */     return this.impl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeSecurity() {
/* 1286 */     RemoveSecurity(this.impl);
/*      */   }
/*      */   
/*      */   static native long SDFDocCreate();
/*      */   
/*      */   static native long SDFDocCreate(String paramString);
/*      */   
/*      */   static native long SDFDocCreate(long paramLong);
/*      */   
/*      */   static native long SDFDocCreate(byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long MemStreamCreateMemFilt(long paramLong) throws PDFNetException;
/*      */   
/*      */   static native void MemStreamWriteData(long paramLong, byte[] paramArrayOfbyte, int paramInt);
/*      */   
/*      */   static native long MemStreamCreateDoc(long paramLong);
/*      */   
/*      */   static native void Destroy(long paramLong);
/*      */   
/*      */   static native boolean IsEncrypted(long paramLong);
/*      */   
/*      */   static native boolean InitSecurityHandler(long paramLong, Object paramObject);
/*      */   
/*      */   static native boolean InitStdSecurityHandler(long paramLong, String paramString);
/*      */   
/*      */   static native boolean InitStdSecurityHandlerBuffer(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native boolean IsModified(long paramLong);
/*      */   
/*      */   static native boolean HasRepairedXRef(long paramLong);
/*      */   
/*      */   static native boolean IsFullSaveRequired(long paramLong);
/*      */   
/*      */   static native boolean CanSaveToPath(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void EnableDiskCaching(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native long GetTrailer(long paramLong);
/*      */   
/*      */   static native long GetObj(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long ImportObj(long paramLong1, long paramLong2, boolean paramBoolean);
/*      */   
/*      */   static native long[] ImportObjs(long paramLong, long[] paramArrayOflong1, long[] paramArrayOflong2);
/*      */   
/*      */   static native long XRefSize(long paramLong);
/*      */   
/*      */   static native void ClearMarks(long paramLong);
/*      */   
/*      */   static native void Save(long paramLong1, String paramString1, long paramLong2, ProgressMonitor paramProgressMonitor, String paramString2);
/*      */   
/*      */   static native byte[] Save(long paramLong1, long paramLong2, ProgressMonitor paramProgressMonitor, String paramString);
/*      */   
/*      */   static native long[] SaveStream(long paramLong1, long paramLong2, ProgressMonitor paramProgressMonitor, String paramString);
/*      */   
/*      */   static native void ReadData(byte[] paramArrayOfbyte, int paramInt, long paramLong);
/*      */   
/*      */   static native String GetHeader(long paramLong);
/*      */   
/*      */   static native long GetSecurityHandler(long paramLong);
/*      */   
/*      */   static native void SetSecurityHandler(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void RemoveSecurity(long paramLong);
/*      */   
/*      */   static native void Swap(long paramLong1, long paramLong2, long paramLong3);
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
/*      */   static native boolean IsLinearized(long paramLong);
/*      */   
/*      */   static native long GetLinearizationDict(long paramLong);
/*      */   
/*      */   static native long GetHintStream(long paramLong);
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
/*      */   static native String GetFileName(long paramLong);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\SDFDoc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */