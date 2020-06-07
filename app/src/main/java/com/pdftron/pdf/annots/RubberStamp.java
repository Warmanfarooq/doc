/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ public class RubberStamp
/*     */   extends Markup {
/*     */   public static final int e_Approved = 0;
/*     */   public static final int e_Experimental = 1;
/*     */   public static final int e_NotApproved = 2;
/*     */   public static final int e_AsIs = 3;
/*     */   public static final int e_Expired = 4;
/*     */   public static final int e_NotForPublicRelease = 5;
/*     */   public static final int e_Confidential = 6;
/*     */   public static final int e_Final = 7;
/*     */   public static final int e_Sold = 8;
/*     */   public static final int e_Departmental = 9;
/*     */   public static final int e_ForComment = 10;
/*     */   public static final int e_TopSecret = 11;
/*     */   public static final int e_ForPublicRelease = 12;
/*     */   public static final int e_Draft = 13;
/*     */   public static final int e_Unknown = 14;
/*     */   
/*     */   public RubberStamp(Obj paramObj) {
/*  28 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RubberStamp() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private RubberStamp(long paramLong, Object paramObject) {
/*  48 */     super(paramLong, paramObject);
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
/*     */   public RubberStamp(Annot paramAnnot) throws PDFNetException {
/*  63 */     super(paramAnnot.getSDFObj());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static RubberStamp create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 140 */     return new RubberStamp(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public static RubberStamp createCustom(Doc paramDoc, Rect paramRect, Obj paramObj) throws PDFNetException {
/* 169 */     return new RubberStamp(CreateCustom(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramObj.__GetHandle()), paramDoc);
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
/*     */   public int getIcon() throws PDFNetException {
/* 190 */     return GetIcon(__GetHandle());
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
/*     */   public void SetIcon(int paramInt) throws PDFNetException {
/* 211 */     SetIcon(__GetHandle(), paramInt);
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
/*     */   public String getIconName() throws PDFNetException {
/* 231 */     return GetIconName(__GetHandle());
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
/*     */   public void setIcon(String paramString) throws PDFNetException {
/* 251 */     SetIcon(__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateCustom(long paramLong1, long paramLong2, long paramLong3);
/*     */   
/*     */   static native int GetIcon(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, int paramInt);
/*     */   
/*     */   static native String GetIconName(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\RubberStamp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */