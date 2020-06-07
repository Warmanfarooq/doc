/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.pdf.Annot;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Sound
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_Speaker = 0;
/*     */   public static final int e_Mic = 1;
/*     */   public static final int e_Unknown = 2;
/*     */   
/*     */   public Sound(Obj paramObj) {
/*  30 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sound() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Sound(long paramLong, Object paramObject) {
/*  44 */     super(paramLong, paramObject);
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
/*     */   public Sound(Annot paramAnnot) throws PDFNetException {
/*  58 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Sound create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  72 */     return new Sound(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public static Sound createWithData(Doc paramDoc, Rect paramRect, Filter paramFilter, int paramInt1, int paramInt2, int paramInt3) throws PDFNetException {
/* 100 */     return new Sound(CreateWithData(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramFilter.__GetHandle(), paramInt1, paramInt2, paramInt3), paramDoc);
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
/*     */   public Obj getSoundStream() throws PDFNetException {
/* 114 */     return Obj.__Create(GetSoundStream(__GetHandle()), __GetRefHandle());
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
/*     */   public void setSoundStream(Obj paramObj) throws PDFNetException {
/* 128 */     SetSoundStream(__GetHandle(), paramObj.__GetHandle());
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
/*     */   public int getIcon() throws PDFNetException {
/* 168 */     return GetIcon(__GetHandle());
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
/*     */   public void setIcon(int paramInt) throws PDFNetException {
/* 193 */     SetIcon(__GetHandle(), paramInt);
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
/*     */   public String getIconName() throws PDFNetException {
/* 214 */     return GetIconName(__GetHandle());
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
/*     */   public void setIcon(String paramString) throws PDFNetException {
/* 237 */     SetIcon(__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateWithData(long paramLong1, long paramLong2, long paramLong3, int paramInt1, int paramInt2, int paramInt3);
/*     */   
/*     */   static native long GetSoundStream(long paramLong);
/*     */   
/*     */   static native void SetSoundStream(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetIcon(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, int paramInt);
/*     */   
/*     */   static native String GetIconName(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Sound.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */