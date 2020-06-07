/*     */ package com.pdftron.fdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.SDFDoc;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FDFDoc
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public FDFDoc() throws PDFNetException {
/*  29 */     this.a = FDFDocCreate();
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
/*     */   public FDFDoc(SDFDoc paramSDFDoc) throws PDFNetException {
/*  41 */     if (paramSDFDoc.__GetRefHandle() != null) {
/*  42 */       throw new PDFNetException("false", 36L, "FDFDoc.java", "FDFDoc(SDFDoc)", "SDFDoc is already owned by another document.");
/*     */     }
/*  44 */     this.a = paramSDFDoc.__GetHandle();
/*  45 */     paramSDFDoc.__SetRef(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FDFDoc(String paramString) throws PDFNetException {
/*  56 */     this.a = FDFDocCreate(paramString);
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
/*     */   public FDFDoc(Filter paramFilter) throws PDFNetException {
/*  73 */     paramFilter.__SetRefHandle(this);
/*  74 */     this.a = FDFDocCreate(paramFilter.__GetHandle());
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
/*     */   public FDFDoc(byte[] paramArrayOfbyte) throws PDFNetException {
/*  87 */     this.a = FDFDocCreate(paramArrayOfbyte);
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
/*     */   public FDFDoc(InputStream paramInputStream) throws PDFNetException, IOException {
/* 101 */     this(paramInputStream, 1048576);
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
/*     */   public FDFDoc(InputStream paramInputStream, int paramInt) throws PDFNetException, IOException {
/* 117 */     long l = 0L;
/*     */     
/*     */     try {
/* 120 */       l = MemStreamCreateMemFilt(paramInputStream.available());
/* 121 */       byte[] arrayOfByte = new byte[paramInt];
/*     */       int i;
/* 123 */       while ((i = paramInputStream.read(arrayOfByte)) != -1)
/*     */       {
/* 125 */         MemStreamWriteData(l, arrayOfByte, i);
/*     */       }
/* 127 */       this.a = MemStreamCreateDoc(l);
/*     */       return;
/* 129 */     } catch (PDFNetException pDFNetException) {
/*     */       Filter filter;
/*     */       
/* 132 */       (filter = Filter.__Create(l, null)).destroy();
/* 133 */       throw pDFNetException;
/*     */     }
/* 135 */     catch (IOException iOException) {
/*     */       Filter filter;
/*     */       
/* 138 */       (filter = Filter.__Create(l, null)).destroy();
/* 139 */       throw iOException;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 148 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws PDFNetException {
/* 158 */     if (this.a != 0L) {
/*     */       
/* 160 */       Close(this.a);
/*     */       
/* 162 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isModified() throws PDFNetException {
/* 174 */     return IsModified(this.a);
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
/*     */   public void save(String paramString) throws PDFNetException {
/* 196 */     Save(this.a, paramString);
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
/*     */   public byte[] save() throws PDFNetException {
/* 208 */     return Save(this.a);
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
/*     */   public void save(OutputStream paramOutputStream) throws PDFNetException, IOException {
/* 220 */     save(paramOutputStream, 1048576);
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
/*     */   public void save(OutputStream paramOutputStream, int paramInt) throws PDFNetException, IOException {
/* 234 */     long arrayOfLong[], l1 = (arrayOfLong = SaveStream(this.a))[0];
/* 235 */     long l2 = arrayOfLong[1];
/* 236 */     byte[] arrayOfByte = new byte[paramInt];
/*     */     
/* 238 */     long l3 = l2 - paramInt;
/*     */     
/* 240 */     while (l1 < l3) {
/*     */       
/* 242 */       ReadData(arrayOfByte, paramInt, l1);
/* 243 */       paramOutputStream.write(arrayOfByte);
/* 244 */       l1 += paramInt;
/*     */     } 
/*     */     
/* 247 */     if ((paramInt = (int)(l2 - l1)) > 0) {
/*     */       
/* 249 */       ReadData(arrayOfByte, paramInt, l1);
/* 250 */       paramOutputStream.write(arrayOfByte, 0, paramInt);
/*     */     } 
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
/*     */   public Obj getTrailer() throws PDFNetException {
/* 264 */     return Obj.__Create(GetTrailer(this.a), this);
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
/*     */   public Obj getRoot() throws PDFNetException {
/* 276 */     return Obj.__Create(GetRoot(this.a), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getFDF() throws PDFNetException {
/* 287 */     return Obj.__Create(GetFDF(this.a), this);
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
/*     */   public String getPDFFileName() throws PDFNetException {
/* 299 */     return GetPDFFileName(this.a);
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
/*     */   public void setPDFFileName(String paramString) throws PDFNetException {
/* 311 */     SetPDFFileName(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getID() throws PDFNetException {
/* 322 */     return Obj.__Create(GetID(this.a), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setID(Obj paramObj) throws PDFNetException {
/* 333 */     SetID(this.a, paramObj.__GetHandle());
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
/*     */   public FDFFieldIterator getFieldIterator() throws PDFNetException {
/* 354 */     return new FDFFieldIterator(GetFieldIteratorBegin(this.a), this);
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
/*     */   public FDFFieldIterator getFieldIterator(String paramString) throws PDFNetException {
/* 366 */     return new FDFFieldIterator(GetFieldIterator(this.a, paramString), this);
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
/*     */   public FDFField getField(String paramString) throws PDFNetException {
/*     */     long l;
/* 381 */     if ((l = GetField(this.a, paramString)) != 0L) {
/* 382 */       return new FDFField(l, this);
/*     */     }
/* 384 */     return null;
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
/*     */   public FDFField fieldCreate(String paramString, int paramInt) throws PDFNetException {
/* 398 */     return new FDFField(FieldCreate(this.a, paramString, paramInt, 0L), this);
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
/*     */   public FDFField fieldCreate(String paramString, int paramInt, Obj paramObj) throws PDFNetException {
/* 412 */     return new FDFField(FieldCreate(this.a, paramString, paramInt, paramObj.__GetHandle()), this);
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
/*     */   public FDFField fieldCreate(String paramString1, int paramInt, String paramString2) throws PDFNetException {
/* 427 */     return new FDFField(FieldCreate(this.a, paramString1, paramInt, paramString2), this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SDFDoc getSDFDoc() {
/* 438 */     return SDFDoc.__Create(this.a, this);
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
/*     */   public static FDFDoc createFromXFDF(String paramString) throws PDFNetException {
/* 451 */     if (paramString == null)
/*     */     {
/* 453 */       throw new PDFNetException("false", 454L, "FDFDoc.java", "createFromXFDF(String)", "Argument may not be null.");
/*     */     }
/*     */ 
/*     */     
/* 457 */     return new FDFDoc(CreateFromXFDF(paramString));
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
/*     */   public void saveAsXFDF(String paramString) throws PDFNetException {
/* 469 */     SaveAsXFDF(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String saveAsXFDF() throws PDFNetException {
/* 480 */     return SaveAsXFDF(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mergeAnnots(String paramString) throws PDFNetException {
/* 491 */     MergeAnnots(this.a, paramString, "");
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
/*     */   public void mergeAnnots(String paramString1, String paramString2) throws PDFNetException {
/* 503 */     MergeAnnots(this.a, paramString1, paramString2);
/*     */   }
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 508 */     return this.a;
/*     */   }
/*     */   
/*     */   public static FDFDoc __Create(long paramLong) {
/* 512 */     return new FDFDoc(paramLong);
/*     */   }
/*     */   
/*     */   private FDFDoc(long paramLong) {
/* 516 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   static native long FDFDocCreate();
/*     */   
/*     */   static native long FDFDocCreate(String paramString);
/*     */   
/*     */   static native long FDFDocCreate(long paramLong);
/*     */   
/*     */   static native long FDFDocCreate(byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long MemStreamCreateMemFilt(long paramLong) throws PDFNetException;
/*     */   
/*     */   static native void MemStreamWriteData(long paramLong, byte[] paramArrayOfbyte, int paramInt);
/*     */   
/*     */   static native long MemStreamCreateDoc(long paramLong);
/*     */   
/*     */   static native void Close(long paramLong);
/*     */   
/*     */   static native boolean IsModified(long paramLong);
/*     */   
/*     */   static native void Save(long paramLong, String paramString);
/*     */   
/*     */   static native byte[] Save(long paramLong);
/*     */   
/*     */   static native long[] SaveStream(long paramLong);
/*     */   
/*     */   static native void ReadData(byte[] paramArrayOfbyte, int paramInt, long paramLong);
/*     */   
/*     */   static native long GetTrailer(long paramLong);
/*     */   
/*     */   static native long GetRoot(long paramLong);
/*     */   
/*     */   static native long GetFDF(long paramLong);
/*     */   
/*     */   static native String GetPDFFileName(long paramLong);
/*     */   
/*     */   static native void SetPDFFileName(long paramLong, String paramString);
/*     */   
/*     */   static native long GetID(long paramLong);
/*     */   
/*     */   static native void SetID(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetFieldIteratorBegin(long paramLong);
/*     */   
/*     */   static native long GetFieldIterator(long paramLong, String paramString);
/*     */   
/*     */   static native long GetField(long paramLong, String paramString);
/*     */   
/*     */   static native long FieldCreate(long paramLong1, String paramString, int paramInt, long paramLong2);
/*     */   
/*     */   static native long FieldCreate(long paramLong, String paramString1, int paramInt, String paramString2);
/*     */   
/*     */   static native long CreateFromXFDF(String paramString);
/*     */   
/*     */   static native long SaveAsXFDF(long paramLong, String paramString);
/*     */   
/*     */   static native String SaveAsXFDF(long paramLong);
/*     */   
/*     */   static native long MergeAnnots(long paramLong, String paramString1, String paramString2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\fdf\FDFDoc.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */