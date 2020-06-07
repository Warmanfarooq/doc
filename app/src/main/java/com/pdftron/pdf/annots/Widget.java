/*      */ package com.pdftron.pdf.annots;
/*      */ 
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Action;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.Font;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Widget
/*      */   extends Annot
/*      */ {
/*      */   public static final int e_none = 0;
/*      */   public static final int e_invert = 1;
/*      */   public static final int e_outline = 2;
/*      */   public static final int e_push = 3;
/*      */   public static final int e_toggle = 4;
/*      */   public static final int e_Anamorphic = 0;
/*      */   public static final int e_Proportional = 1;
/*      */   public static final int e_NoIcon = 0;
/*      */   public static final int e_NoCaption = 1;
/*      */   
/*      */   public Widget(Obj paramObj) {
/*   31 */     super(paramObj);
/*      */   }
/*      */ 
/*      */   
/*      */   public static final int e_CBelowI = 2;
/*      */   
/*      */   public static final int e_CAboveI = 3;
/*      */   
/*      */   public static final int e_CRightILeft = 4;
/*      */   
/*      */   public static final int e_CLeftIRight = 5;
/*      */   public static final int e_COverlayI = 6;
/*      */   public static final int e_Always = 0;
/*      */   public static final int e_WhenBigger = 1;
/*      */   public static final int e_WhenSmaller = 2;
/*      */   public static final int e_Never = 3;
/*      */   
/*      */   public Widget() {}
/*      */   
/*      */   Widget(long paramLong, Object paramObject) {
/*   51 */     super(paramLong, paramObject);
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
/*      */   public Widget(Annot paramAnnot) throws PDFNetException {
/*   67 */     super(paramAnnot.getSDFObj());
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
/*      */   public static Widget create(Doc paramDoc, Rect paramRect, Field paramField) throws PDFNetException {
/*   82 */     return new Widget(Create(paramDoc.__GetHandle(), paramRect.__GetHandle(), paramField.__GetHandle()), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Field getField() throws PDFNetException {
/*   93 */     return Field.__Create(GetField(__GetHandle()), __GetRefHandle());
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
/*      */   public int getHighlightingMode() throws PDFNetException {
/*  145 */     return GetHighlightingMode(__GetHandle());
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
/*      */   public void setHighlightingMode(int paramInt) throws PDFNetException {
/*  173 */     SetHighlightingMode(__GetHandle(), paramInt);
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
/*      */   public Action getAction() throws PDFNetException {
/*  185 */     return Action.__Create(GetAction(__GetHandle()), __GetRefHandle());
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
/*      */   public void setAction(Action paramAction) throws PDFNetException {
/*  200 */     SetAction(__GetHandle(), paramAction.__GetHandle());
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
/*      */   public int getBorderColorCompNum() throws PDFNetException {
/*  218 */     return GetBorderColorCompNum(__GetHandle());
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
/*      */   public ColorPt getBorderColor() throws PDFNetException {
/*  236 */     return ColorPt.__Create(GetBorderColor(__GetHandle()));
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
/*      */   public void setBorderColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/*  262 */     SetBorderColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*      */   public int getBackgroundColorCompNum() throws PDFNetException {
/*  281 */     return GetBackgroundColorCompNum(__GetHandle());
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
/*      */   public ColorPt getBackgroundColor() throws PDFNetException {
/*  299 */     return ColorPt.__Create(GetBackgroundColor(__GetHandle()));
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
/*      */   public void setBackgroundColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/*  324 */     SetBackgroundColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*      */   public String getStaticCaptionText() throws PDFNetException {
/*  344 */     return GetStaticCaptionText(__GetHandle());
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
/*      */   public void setStaticCaptionText(String paramString) throws PDFNetException {
/*  366 */     SetStaticCaptionText(__GetHandle(), paramString);
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
/*      */   public String getRolloverCaptionText() throws PDFNetException {
/*  384 */     return GetRolloverCaptionText(__GetHandle());
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
/*      */   public void setRolloverCaptionText(String paramString) throws PDFNetException {
/*  399 */     SetRolloverCaptionText(__GetHandle(), paramString);
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
/*      */   public String getMouseDownCaptionText() throws PDFNetException {
/*  418 */     return GetMouseDownCaptionText(__GetHandle());
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
/*      */   public void setMouseDownCaptionText(String paramString) throws PDFNetException {
/*  437 */     SetMouseDownCaptionText(__GetHandle(), paramString);
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
/*      */   public Obj getStaticIcon() throws PDFNetException {
/*  457 */     return Obj.__Create(GetStaticIcon(__GetHandle()), __GetRefHandle());
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
/*      */   public void setStaticIcon(Obj paramObj) throws PDFNetException {
/*  478 */     SetStaticIcon(__GetHandle(), paramObj.__GetHandle());
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
/*      */   public Obj getRolloverIcon() throws PDFNetException {
/*  499 */     return Obj.__Create(GetRolloverIcon(__GetHandle()), __GetRefHandle());
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
/*      */   public void setRolloverIcon(Obj paramObj) throws PDFNetException {
/*  522 */     SetRolloverIcon(__GetHandle(), paramObj.__GetHandle());
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
/*      */   public Obj getMouseDownIcon() throws PDFNetException {
/*  542 */     return Obj.__Create(GetMouseDownIcon(__GetHandle()), __GetRefHandle());
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
/*      */   public void setMouseDownIcon(Obj paramObj) throws PDFNetException {
/*  564 */     SetMouseDownIcon(__GetHandle(), paramObj.__GetHandle());
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
/*      */   public int getScaleType() throws PDFNetException {
/*  614 */     return GetScaleType(__GetHandle());
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
/*      */   public void setScaleType(int paramInt) throws PDFNetException {
/*  644 */     SetScaleType(__GetHandle(), paramInt);
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
/*      */   public int getIconCaptionRelation() throws PDFNetException {
/*  701 */     return GetIconCaptionRelation(__GetHandle());
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
/*      */   public void setIconCaptionRelation(int paramInt) throws PDFNetException {
/*  728 */     SetIconCaptionRelation(__GetHandle(), paramInt);
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
/*      */   public int getScaleCondition() throws PDFNetException {
/*  775 */     return GetScaleCondition(__GetHandle());
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
/*      */   public void SetScaleCondition(int paramInt) throws PDFNetException {
/*  803 */     SetScaleCondition(__GetHandle(), paramInt);
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
/*      */   public double getHIconLeftOver() throws PDFNetException {
/*  823 */     return GetHIconLeftOver(__GetHandle());
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
/*      */   public void setHIconLeftOver(double paramDouble) throws PDFNetException {
/*  844 */     SetHIconLeftOver(__GetHandle(), paramDouble);
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
/*      */   public double getVIconLeftOver() throws PDFNetException {
/*  867 */     return GetVIconLeftOver(__GetHandle());
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
/*      */   public void setVIconLeftOver(double paramDouble) throws PDFNetException {
/*  891 */     SetVIconLeftOver(__GetHandle(), paramDouble);
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
/*      */   public boolean getFitFull() throws PDFNetException {
/*  908 */     return GetFitFull(__GetHandle());
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
/*      */   public void setFitFull(boolean paramBoolean) throws PDFNetException {
/*  926 */     SetFitFull(__GetHandle(), paramBoolean);
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
/*      */   public ColorPt getTextColor() throws PDFNetException {
/*  941 */     return ColorPt.__Create(GetTextColor(__GetHandle()));
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
/*      */   public int getTextColorCompNum() throws PDFNetException {
/*  954 */     return GetTextColorCompNum(__GetHandle());
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
/*      */   public void setTextColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/*  967 */     SetTextColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*      */   public double getFontSize() throws PDFNetException {
/*  981 */     return GetFontSize(__GetHandle());
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
/*      */   public void setFontSize(double paramDouble) throws PDFNetException {
/*  995 */     SetFontSize(__GetHandle(), paramDouble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font getFont() throws PDFNetException {
/* 1006 */     return Font.__Create(GetFont(__GetHandle()), __GetRefHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFont(Font paramFont) throws PDFNetException {
/* 1017 */     SetFont(__GetHandle(), paramFont.__GetHandle());
/*      */   }
/*      */   
/*      */   static native long Create(long paramLong1, long paramLong2, long paramLong3);
/*      */   
/*      */   static native long GetField(long paramLong);
/*      */   
/*      */   static native int GetHighlightingMode(long paramLong);
/*      */   
/*      */   static native void SetHighlightingMode(long paramLong, int paramInt);
/*      */   
/*      */   static native long GetAction(long paramLong);
/*      */   
/*      */   static native void SetAction(long paramLong1, long paramLong2);
/*      */   
/*      */   static native int GetBorderColorCompNum(long paramLong);
/*      */   
/*      */   static native long GetBorderColor(long paramLong);
/*      */   
/*      */   static native void SetBorderColor(long paramLong1, long paramLong2, int paramInt);
/*      */   
/*      */   static native int GetBackgroundColorCompNum(long paramLong);
/*      */   
/*      */   static native long GetBackgroundColor(long paramLong);
/*      */   
/*      */   static native void SetBackgroundColor(long paramLong1, long paramLong2, int paramInt);
/*      */   
/*      */   static native String GetStaticCaptionText(long paramLong);
/*      */   
/*      */   static native void SetStaticCaptionText(long paramLong, String paramString);
/*      */   
/*      */   static native String GetRolloverCaptionText(long paramLong);
/*      */   
/*      */   static native void SetRolloverCaptionText(long paramLong, String paramString);
/*      */   
/*      */   static native String GetMouseDownCaptionText(long paramLong);
/*      */   
/*      */   static native void SetMouseDownCaptionText(long paramLong, String paramString);
/*      */   
/*      */   static native long GetStaticIcon(long paramLong);
/*      */   
/*      */   static native void SetStaticIcon(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetRolloverIcon(long paramLong);
/*      */   
/*      */   static native void SetRolloverIcon(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetMouseDownIcon(long paramLong);
/*      */   
/*      */   static native void SetMouseDownIcon(long paramLong1, long paramLong2);
/*      */   
/*      */   static native int GetScaleType(long paramLong);
/*      */   
/*      */   static native void SetScaleType(long paramLong, int paramInt);
/*      */   
/*      */   static native int GetScaleCondition(long paramLong);
/*      */   
/*      */   static native void SetScaleCondition(long paramLong, int paramInt);
/*      */   
/*      */   static native double GetHIconLeftOver(long paramLong);
/*      */   
/*      */   static native void SetHIconLeftOver(long paramLong, double paramDouble);
/*      */   
/*      */   static native double GetVIconLeftOver(long paramLong);
/*      */   
/*      */   static native void SetVIconLeftOver(long paramLong, double paramDouble);
/*      */   
/*      */   static native boolean GetFitFull(long paramLong);
/*      */   
/*      */   static native void SetFitFull(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native int GetIconCaptionRelation(long paramLong);
/*      */   
/*      */   static native void SetIconCaptionRelation(long paramLong, int paramInt);
/*      */   
/*      */   static native void SetTextColor(long paramLong1, long paramLong2, int paramInt);
/*      */   
/*      */   static native long GetTextColor(long paramLong);
/*      */   
/*      */   static native int GetTextColorCompNum(long paramLong);
/*      */   
/*      */   static native void SetFontSize(long paramLong, double paramDouble);
/*      */   
/*      */   static native double GetFontSize(long paramLong);
/*      */   
/*      */   static native void SetFont(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long GetFont(long paramLong);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Widget.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */