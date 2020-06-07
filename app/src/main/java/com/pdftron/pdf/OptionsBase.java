/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.ObjSet;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OptionsBase
/*     */ {
/*     */   protected ObjSet mObjSet;
/*     */   protected Obj mDict;
/*     */   
/*     */   public OptionsBase() throws PDFNetException {
/*  16 */     this.mObjSet = new ObjSet();
/*  17 */     this.mDict = this.mObjSet.createDict();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OptionsBase(String paramString) throws PDFNetException {
/*  25 */     this.mObjSet = new ObjSet();
/*  26 */     this.mDict = this.mObjSet.createFromJson(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   long a() throws PDFNetException {
/*  34 */     return this.mDict.__GetHandle();
/*     */   }
/*     */ 
/*     */   
/*     */   private Obj a(String paramString) throws PDFNetException {
/*     */     Obj obj;
/*  40 */     if ((obj = this.mDict.findObj(paramString)) == null)
/*     */     {
/*  42 */       obj = this.mDict.putArray(paramString);
/*     */     }
/*  44 */     return obj;
/*     */   }
/*     */ 
/*     */   
/*     */   static ColorPt a(double paramDouble) throws PDFNetException {
/*  49 */     long l = (long)paramDouble;
/*  50 */     return new ColorPt((l >> 16L & 0xFFL) / 255.0D, (l >> 8L & 0xFFL) / 255.0D, (l & 0xFFL) / 255.0D, (l >> 24L & 0xFFL) / 255.0D);
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
/*     */   static double a(ColorPt paramColorPt) throws PDFNetException {
/*  69 */     long l1 = (long)(255.0D * paramColorPt.get(0));
/*  70 */     long l2 = (long)(255.0D * paramColorPt.get(1));
/*  71 */     long l3 = (long)(255.0D * paramColorPt.get(2));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     long l4;
/*     */ 
/*     */ 
/*     */     
/*  80 */     return (l4 = 0xFF000000L | (0xFFL & l1) << 16L | (0xFFL & l2) << 8L | 0xFFL & l3);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void putNumber(String paramString, double paramDouble) throws PDFNetException {
/*  85 */     this.mDict.putNumber(paramString, paramDouble);
/*     */   }
/*     */   
/*     */   protected void putBool(String paramString, Boolean paramBoolean) throws PDFNetException {
/*  89 */     this.mDict.putBool(paramString, paramBoolean.booleanValue());
/*     */   }
/*     */   
/*     */   protected void putText(String paramString1, String paramString2) throws PDFNetException {
/*  93 */     this.mDict.putText(paramString1, paramString2);
/*     */   }
/*     */   
/*     */   protected void putRect(String paramString, Rect paramRect) throws PDFNetException {
/*  97 */     this.mDict.putRect(paramString, paramRect.getX1(), paramRect.getY1(), paramRect.getX2(), paramRect.getY2());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pushBackNumber(String paramString, double paramDouble) throws PDFNetException {
/*     */     Obj obj;
/* 103 */     (obj = a(paramString)).pushBackNumber(paramDouble);
/*     */   }
/*     */   
/*     */   protected void pushBackBool(String paramString, Boolean paramBoolean) throws PDFNetException {
/*     */     Obj obj;
/* 108 */     (obj = a(paramString)).pushBackBool(paramBoolean.booleanValue());
/*     */   }
/*     */   
/*     */   protected void pushBackText(String paramString1, String paramString2) throws PDFNetException {
/*     */     Obj obj;
/* 113 */     (obj = a(paramString1)).pushBackText(paramString2);
/*     */   }
/*     */   
/*     */   protected void pushBackRect(String paramString, Rect paramRect) throws PDFNetException {
/*     */     Obj obj;
/* 118 */     (obj = a(paramString)).pushBackRect(paramRect.getX1(), paramRect.getY1(), paramRect.getX2(), paramRect.getY2());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void insertRectCollection(String paramString, RectCollection paramRectCollection, int paramInt) throws PDFNetException {
/* 123 */     Obj obj = a(paramString);
/* 124 */     while (obj.size() <= paramInt)
/*     */     {
/* 126 */       obj.pushBackArray();
/*     */     }
/* 128 */     obj = obj.getAt(paramInt);
/* 129 */     for (paramInt = 0; paramInt < paramRectCollection.getNumRects(); paramInt++) {
/*     */       
/* 131 */       Rect rect = paramRectCollection.getRectAt(paramInt);
/* 132 */       obj.pushBackRect(rect.getX1(), rect.getY1(), rect.getX2(), rect.getY2());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected static Rect rectFromArray(Obj paramObj) throws PDFNetException {
/* 138 */     return new Rect(paramObj.getAt(0).getNumber(), paramObj.getAt(1).getNumber(), paramObj
/* 139 */         .getAt(2).getNumber(), paramObj.getAt(3).getNumber());
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\OptionsBase.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */