/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Point;
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
/*     */ public class Text
/*     */   extends Markup
/*     */ {
/*     */   public static final int e_Comment = 0;
/*     */   public static final int e_Key = 1;
/*     */   public static final int e_Help = 2;
/*     */   public static final int e_NewParagraph = 3;
/*     */   public static final int e_Paragraph = 4;
/*     */   public static final int e_Insert = 5;
/*     */   public static final int e_Note = 6;
/*     */   public static final int e_Unknown = 7;
/*     */   
/*     */   public Text(Obj paramObj) {
/*  30 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Text() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Text(long paramLong, Object paramObject) {
/*  49 */     super(paramLong, paramObject);
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
/*     */   public Text(Annot paramAnnot) throws PDFNetException {
/*  65 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Text create(Doc paramDoc, Point paramPoint) throws PDFNetException {
/*  79 */     return new Text(Create(paramDoc.__GetHandle(), paramPoint.x, paramPoint.y), paramDoc);
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
/*     */   public static Text create(Doc paramDoc, Point paramPoint, String paramString) throws PDFNetException {
/*  94 */     return new Text(Create(paramDoc.__GetHandle(), paramPoint.x, paramPoint.y, paramString), paramDoc);
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
/*     */ 
/*     */ 
/*     */ 
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
/* 183 */     return GetIcon(__GetHandle());
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
/*     */   public String getIconName() throws PDFNetException {
/* 205 */     return GetIconName(__GetHandle());
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
/*     */   public void setIcon(int paramInt) throws PDFNetException {
/* 226 */     SetIcon(__GetHandle(), paramInt);
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
/* 246 */     SetIcon(__GetHandle(), paramString);
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
/*     */   public String getState() throws PDFNetException {
/* 261 */     return GetState(__GetHandle());
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
/*     */   public void setState(String paramString) throws PDFNetException {
/* 277 */     SetState(__GetHandle(), paramString);
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
/*     */   public String getStateModel() throws PDFNetException {
/* 289 */     return GetStateModel(__GetHandle());
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
/*     */   public void setStateModel(String paramString) throws PDFNetException {
/* 302 */     SetStateModel(__GetHandle(), paramString);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native long Create(long paramLong, double paramDouble1, double paramDouble2, String paramString);
/*     */   
/*     */   static native int GetIcon(long paramLong);
/*     */   
/*     */   static native String GetIconName(long paramLong);
/*     */   
/*     */   static native void SetIcon(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetIcon(long paramLong, String paramString);
/*     */   
/*     */   static native String GetState(long paramLong);
/*     */   
/*     */   static native void SetState(long paramLong, String paramString);
/*     */   
/*     */   static native String GetStateModel(long paramLong);
/*     */   
/*     */   static native void SetStateModel(long paramLong, String paramString);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Text.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */