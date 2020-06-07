/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.Date;
/*     */ import com.pdftron.pdf.Rect;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Markup
/*     */   extends Annot
/*     */ {
/*     */   public static final int e_None = 0;
/*     */   public static final int e_Cloudy = 1;
/*     */   
/*     */   public Markup(Obj paramObj) {
/*  60 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Markup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Markup(long paramLong, Object paramObject) {
/*  79 */     super(paramLong, paramObject);
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
/*     */   public Markup(Annot paramAnnot) throws PDFNetException {
/*  93 */     super(paramAnnot.getSDFObj());
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
/*     */   public String getTitle() throws PDFNetException {
/* 109 */     return GetTitle(__GetHandle());
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
/*     */   public void setTitle(String paramString) throws PDFNetException {
/* 125 */     SetTitle(__GetHandle(), paramString);
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
/*     */   public Popup getPopup() throws PDFNetException {
/* 140 */     return new Popup(GetPopup(__GetHandle()), __GetRefHandle());
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
/*     */   public void setPopup(Popup paramPopup) throws PDFNetException {
/* 155 */     SetPopup(__GetHandle(), paramPopup.__GetHandle());
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
/*     */   public double getOpacity() throws PDFNetException {
/* 180 */     return GetOpacity(__GetHandle());
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
/*     */   public void setOpacity(double paramDouble) throws PDFNetException {
/* 207 */     SetOpacity(__GetHandle(), paramDouble);
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
/*     */   public String getSubject() throws PDFNetException {
/* 221 */     return GetSubject(__GetHandle());
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
/*     */   public void setSubject(String paramString) throws PDFNetException {
/* 237 */     SetSubject(__GetHandle(), paramString);
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
/*     */   public Date getCreationDates() throws PDFNetException {
/* 253 */     return Date.__Create(GetCreationDates(__GetHandle()));
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
/*     */   public void setCreationDates(Date paramDate) throws PDFNetException {
/* 270 */     SetCreationDates(__GetHandle(), paramDate.__GetHandle());
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
/*     */   public int getBorderEffect() throws PDFNetException {
/* 304 */     return GetBorderEffect(__GetHandle());
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
/*     */   public void setBorderEffect(int paramInt) throws PDFNetException {
/* 325 */     SetBorderEffect(__GetHandle(), paramInt);
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
/*     */   public double getBorderEffectIntensity() throws PDFNetException {
/* 340 */     return GetBorderEffectIntensity(__GetHandle());
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
/*     */   public void setBorderEffectIntensity(double paramDouble) throws PDFNetException {
/* 356 */     SetBorderEffectIntensity(__GetHandle(), paramDouble);
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
/*     */   public ColorPt getInteriorColor() throws PDFNetException {
/* 370 */     return ColorPt.__Create(GetInteriorColor(__GetHandle()));
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
/*     */   public int getInteriorColorCompNum() throws PDFNetException {
/* 383 */     return GetInteriorColorCompNum(__GetHandle());
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
/*     */   public void setInteriorColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 404 */     SetInteriorColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*     */   public Rect getContentRect() throws PDFNetException {
/* 418 */     return Rect.__Create(GetContentRect(__GetHandle()));
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
/*     */   public void setContentRect(Rect paramRect) throws PDFNetException {
/* 430 */     SetContentRect(__GetHandle(), paramRect.__GetHandle());
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
/*     */   public Rect getPadding() throws PDFNetException {
/* 451 */     return Rect.__Create(GetPadding(__GetHandle()));
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
/*     */   public void setPadding(Rect paramRect) throws PDFNetException {
/* 472 */     SetPadding(__GetHandle(), paramRect.__GetHandle());
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
/*     */   public void setPadding(double paramDouble) throws PDFNetException {
/* 484 */     setPadding(new Rect(paramDouble, paramDouble, paramDouble, paramDouble));
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
/*     */   public void rotateAppearance(double paramDouble) throws PDFNetException {
/* 501 */     RotateAppearance(__GetHandle(), paramDouble);
/*     */   }
/*     */   
/*     */   static native String GetTitle(long paramLong);
/*     */   
/*     */   static native void SetTitle(long paramLong, String paramString);
/*     */   
/*     */   static native long GetPopup(long paramLong);
/*     */   
/*     */   static native void SetPopup(long paramLong1, long paramLong2);
/*     */   
/*     */   static native double GetOpacity(long paramLong);
/*     */   
/*     */   static native void SetOpacity(long paramLong, double paramDouble);
/*     */   
/*     */   static native String GetSubject(long paramLong);
/*     */   
/*     */   static native void SetSubject(long paramLong, String paramString);
/*     */   
/*     */   static native long GetCreationDates(long paramLong);
/*     */   
/*     */   static native void SetCreationDates(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetBorderEffect(long paramLong);
/*     */   
/*     */   static native void SetBorderEffect(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetBorderEffectIntensity(long paramLong);
/*     */   
/*     */   static native void SetBorderEffectIntensity(long paramLong, double paramDouble);
/*     */   
/*     */   static native long GetInteriorColor(long paramLong);
/*     */   
/*     */   static native int GetInteriorColorCompNum(long paramLong);
/*     */   
/*     */   static native void SetInteriorColor(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native long GetContentRect(long paramLong);
/*     */   
/*     */   static native void SetContentRect(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetPadding(long paramLong);
/*     */   
/*     */   static native void SetPadding(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void RotateAppearance(long paramLong, double paramDouble);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Markup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */