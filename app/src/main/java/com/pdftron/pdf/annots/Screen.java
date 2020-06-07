/*     */ package com.pdftron.pdf.annots;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ public class Screen extends Annot {
/*     */   public static final int e_Anamorphic = 0;
/*     */   public static final int e_Proportional = 1;
/*     */   public static final int e_NoIcon = 0;
/*     */   public static final int e_NoCaption = 1;
/*     */   public static final int e_CBelowI = 2;
/*     */   public static final int e_CAboveI = 3;
/*     */   public static final int e_CRightILeft = 4;
/*     */   public static final int e_CLeftIRight = 5;
/*     */   public static final int e_COverlayI = 6;
/*     */   public static final int e_Always = 0;
/*     */   public static final int e_WhenBigger = 1;
/*     */   public static final int e_WhenSmaller = 2;
/*     */   public static final int e_Never = 3;
/*     */   
/*     */   public Screen(Obj paramObj) {
/*  27 */     super(paramObj);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Screen() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Screen(long paramLong, Object paramObject) {
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
/*     */   public Screen(Annot paramAnnot) throws PDFNetException {
/*  60 */     super(paramAnnot.getSDFObj());
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
/*     */   public static Screen create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/*  74 */     return new Screen(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
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
/*     */   public Action getAction() throws PDFNetException {
/*  87 */     return Action.__Create(GetAction(__GetHandle()), __GetRefHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTitle() throws PDFNetException {
/*  98 */     return GetTitle(__GetHandle());
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
/*     */   public void setTitle(String paramString) throws PDFNetException {
/* 110 */     SetTitle(__GetHandle(), paramString);
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
/*     */   public void setAction(Action paramAction) throws PDFNetException {
/* 124 */     SetAction(__GetHandle(), paramAction.__GetHandle());
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
/*     */   public int getBorderColorCompNum() throws PDFNetException {
/* 141 */     return GetBorderColorCompNum(__GetHandle());
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
/*     */   public ColorPt getBorderColor() throws PDFNetException {
/* 158 */     return ColorPt.__Create(GetBorderColor(__GetHandle()));
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
/*     */   public void setBorderColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 181 */     SetBorderColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*     */   public int getBackgroundColorCompNum() throws PDFNetException {
/* 199 */     return GetBackgroundColorCompNum(__GetHandle());
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
/*     */   public ColorPt getBackgroundColor() throws PDFNetException {
/* 216 */     return ColorPt.__Create(GetBackgroundColor(__GetHandle()));
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
/*     */   public void setBackgroundColor(ColorPt paramColorPt, int paramInt) throws PDFNetException {
/* 240 */     SetBackgroundColor(__GetHandle(), paramColorPt.__GetHandle(), paramInt);
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
/*     */   public String getStaticCaptionText() throws PDFNetException {
/* 260 */     return GetStaticCaptionText(__GetHandle());
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
/*     */   public void setStaticCaptionText(String paramString) throws PDFNetException {
/* 281 */     SetStaticCaptionText(__GetHandle(), paramString);
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
/*     */   public String getRolloverCaptionText() throws PDFNetException {
/* 298 */     return GetRolloverCaptionText(__GetHandle());
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
/*     */   public void setRolloverCaptionText(String paramString) throws PDFNetException {
/* 313 */     SetRolloverCaptionText(__GetHandle(), paramString);
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
/*     */   public String getMouseDownCaptionText() throws PDFNetException {
/* 331 */     return GetMouseDownCaptionText(__GetHandle());
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
/*     */   public void setMouseDownCaptionText(String paramString) throws PDFNetException {
/* 349 */     SetMouseDownCaptionText(__GetHandle(), paramString);
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
/*     */   public Obj getStaticIcon() throws PDFNetException {
/* 368 */     return Obj.__Create(GetStaticIcon(__GetHandle()), __GetRefHandle());
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
/*     */   public void setStaticIcon(Obj paramObj) throws PDFNetException {
/* 388 */     SetStaticIcon(__GetHandle(), paramObj.__GetHandle());
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
/*     */   public Obj getRolloverIcon() throws PDFNetException {
/* 408 */     return Obj.__Create(GetRolloverIcon(__GetHandle()), __GetRefHandle());
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
/*     */   public void setRolloverIcon(Obj paramObj) throws PDFNetException {
/* 430 */     SetRolloverIcon(__GetHandle(), paramObj.__GetHandle());
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
/*     */   public Obj getMouseDownIcon() throws PDFNetException {
/* 449 */     return Obj.__Create(GetMouseDownIcon(__GetHandle()), __GetRefHandle());
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
/*     */   public void setMouseDownIcon(Obj paramObj) throws PDFNetException {
/* 470 */     SetMouseDownIcon(__GetHandle(), paramObj.__GetHandle());
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
/*     */   public int getScaleType() throws PDFNetException {
/* 520 */     return GetScaleType(__GetHandle());
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
/*     */   public void setScaleType(int paramInt) throws PDFNetException {
/* 550 */     SetScaleType(__GetHandle(), paramInt);
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
/*     */   public int getIconCaptionRelation() throws PDFNetException {
/* 607 */     return GetIconCaptionRelation(__GetHandle());
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
/*     */   public void setIconCaptionRelation(int paramInt) throws PDFNetException {
/* 634 */     SetIconCaptionRelation(__GetHandle(), paramInt);
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
/*     */   public int getScaleCondition() throws PDFNetException {
/* 680 */     return GetScaleCondition(__GetHandle());
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
/*     */   public void SetScaleCondition(int paramInt) throws PDFNetException {
/* 707 */     SetScaleCondition(__GetHandle(), paramInt);
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
/*     */   public double getHIconLeftOver() throws PDFNetException {
/* 726 */     return GetHIconLeftOver(__GetHandle());
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
/*     */   public void setHIconLeftOver(double paramDouble) throws PDFNetException {
/* 746 */     SetHIconLeftOver(__GetHandle(), paramDouble);
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
/*     */   public double getVIconLeftOver() throws PDFNetException {
/* 768 */     return GetVIconLeftOver(__GetHandle());
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
/*     */   public void setVIconLeftOver(double paramDouble) throws PDFNetException {
/* 791 */     SetVIconLeftOver(__GetHandle(), paramDouble);
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
/*     */   public boolean getFitFull() throws PDFNetException {
/* 807 */     return GetFitFull(__GetHandle());
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
/*     */   public void setFitFull(boolean paramBoolean) throws PDFNetException {
/* 824 */     SetFitFull(__GetHandle(), paramBoolean);
/*     */   }
/*     */   
/*     */   static native long Create(long paramLong1, long paramLong2);
/*     */   
/*     */   static native String GetTitle(long paramLong);
/*     */   
/*     */   static native void SetTitle(long paramLong, String paramString);
/*     */   
/*     */   static native long GetAction(long paramLong);
/*     */   
/*     */   static native void SetAction(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetBorderColorCompNum(long paramLong);
/*     */   
/*     */   static native long GetBorderColor(long paramLong);
/*     */   
/*     */   static native void SetBorderColor(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native int GetBackgroundColorCompNum(long paramLong);
/*     */   
/*     */   static native long GetBackgroundColor(long paramLong);
/*     */   
/*     */   static native void SetBackgroundColor(long paramLong1, long paramLong2, int paramInt);
/*     */   
/*     */   static native String GetStaticCaptionText(long paramLong);
/*     */   
/*     */   static native void SetStaticCaptionText(long paramLong, String paramString);
/*     */   
/*     */   static native String GetRolloverCaptionText(long paramLong);
/*     */   
/*     */   static native void SetRolloverCaptionText(long paramLong, String paramString);
/*     */   
/*     */   static native String GetMouseDownCaptionText(long paramLong);
/*     */   
/*     */   static native void SetMouseDownCaptionText(long paramLong, String paramString);
/*     */   
/*     */   static native long GetStaticIcon(long paramLong);
/*     */   
/*     */   static native void SetStaticIcon(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetRolloverIcon(long paramLong);
/*     */   
/*     */   static native void SetRolloverIcon(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetMouseDownIcon(long paramLong);
/*     */   
/*     */   static native void SetMouseDownIcon(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int GetScaleType(long paramLong);
/*     */   
/*     */   static native void SetScaleType(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetScaleCondition(long paramLong);
/*     */   
/*     */   static native void SetScaleCondition(long paramLong, int paramInt);
/*     */   
/*     */   static native double GetHIconLeftOver(long paramLong);
/*     */   
/*     */   static native void SetHIconLeftOver(long paramLong, double paramDouble);
/*     */   
/*     */   static native double GetVIconLeftOver(long paramLong);
/*     */   
/*     */   static native void SetVIconLeftOver(long paramLong, double paramDouble);
/*     */   
/*     */   static native boolean GetFitFull(long paramLong);
/*     */   
/*     */   static native void SetFitFull(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetIconCaptionRelation(long paramLong);
/*     */   
/*     */   static native void SetIconCaptionRelation(long paramLong, int paramInt);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Screen.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */