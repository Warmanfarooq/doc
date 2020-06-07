/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.FileSpec;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileAttachment
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_Graph = 0;
/*     */   public static final int e_PushPin = 1;
/*     */   public static final int e_Paperclip = 2;
/*     */   public static final int e_Tag = 3;
/*     */   public static final int e_Unknown = 4;
/*     */   
/*     */   public FileAttachment(Obj paramObj) {
/*  26 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileAttachment() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private FileAttachment(long paramLong, Object paramObject) {
/*  46 */     super(paramLong, paramObject);
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
/*     */   public FileAttachment(Annot paramAnnot) throws PDFNetException {
/*  61 */     super(paramAnnot.getSDFObj());
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
/*     */   public static FileAttachment create(Doc paramDoc, Rect paramRect, String paramString1, String paramString2) throws PDFNetException {
/*  78 */     return new FileAttachment(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString1, paramString2), paramDoc);
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
/*     */   public static FileAttachment create(Doc paramDoc, Rect paramRect, String paramString) throws PDFNetException {
/* 101 */     return new FileAttachment(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramString), paramDoc);
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
/*     */   public void export(String paramString) throws PDFNetException {
/* 118 */     Export(__GetHandle(), paramString);
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
/*     */   public void export() throws PDFNetException {
/* 132 */     Export(__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileSpec getFileSpec() throws PDFNetException {
/* 143 */     return FileSpec.__Create(GetFileSpec(__GetHandle()), __GetRefHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void SetFileSpec(FileSpec paramFileSpec) throws PDFNetException {
/* 154 */     SetFileSpec(__GetHandle(), paramFileSpec.__GetHandle());
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
/*     */   public int getIcon() throws PDFNetException {
/* 199 */     return GetIcon(__GetHandle());
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
/*     */   public void setIcon(int paramInt) throws PDFNetException {
/* 222 */     SetIcon(__GetHandle(), paramInt);
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
/*     */   public String getIconName() throws PDFNetException {
/* 245 */     return GetIconName(__GetHandle());
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
/*     */   public void SetIconName(String paramString) throws PDFNetException {
/* 268 */     SetIconName(__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, String paramString1, String paramString2);
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2, String paramString);
/*     */   
/*     */   static native void Export(long paramLong, String paramString);
/*     */   
/*     */   static native void Export(long paramLong);
/*     */   
/*     */   static native long GetFileSpec(long paramLong);
/*     */   
/*     */   static native void SetFileSpec(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetIcon(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, int paramInt);
/*     */   
/*     */   static native String GetIconName(long paramLong);
/*     */   
/*     */   static native void SetIconName(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\FileAttachment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */