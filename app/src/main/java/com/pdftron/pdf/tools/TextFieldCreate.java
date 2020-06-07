/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.Font;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.TextWidget;
/*     */ import com.pdftron.pdf.annots.Widget;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import java.util.UUID;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class TextFieldCreate
/*     */   extends RectCreate
/*     */ {
/*     */   private boolean mIsMultiline;
/*     */   private int mJustification;
/*     */   @ColorInt
/*     */   protected int mTextColor;
/*     */   protected float mTextSize;
/*     */   protected String mPDFTronFontName;
/*     */   
/*     */   public TextFieldCreate(PDFViewCtrl ctrl) {
/*  50 */     this(ctrl, true, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextFieldCreate(PDFViewCtrl ctrl, boolean isMultiline, int justification) {
/*  57 */     super(ctrl);
/*  58 */     this.mIsMultiline = isMultiline;
/*  59 */     this.mJustification = justification;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/*  64 */     initTextField();
/*  65 */     return super.onDown(e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  73 */     return ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  78 */     return 19;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/*  83 */     TextWidget widget = TextWidget.create((Doc)doc, bbox, UUID.randomUUID().toString());
/*  84 */     ColorPt colorPt = Utils.color2ColorPt(-1);
/*  85 */     widget.setBackgroundColor(colorPt, 3);
/*  86 */     widget.getSDFObj().putString("pdftron", "");
/*     */     
/*  88 */     Field field = widget.getField();
/*  89 */     field.setFlag(7, this.mIsMultiline);
/*  90 */     field.setJustification(this.mJustification);
/*     */     
/*  92 */     setWidgetStyle(doc, (Widget)widget, "");
/*     */     
/*  94 */     return (Annot)widget;
/*     */   }
/*     */   
/*     */   protected void setWidgetStyle(@NonNull PDFDoc doc, @NonNull Widget widget, @NonNull String contents) throws PDFNetException {
/*  98 */     ColorPt color = Utils.color2ColorPt(this.mTextColor);
/*  99 */     widget.setFontSize(this.mTextSize);
/* 100 */     widget.setTextColor(color, 3);
/*     */     
/* 102 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 103 */     if (!Utils.isNullOrEmpty(this.mPDFTronFontName) && toolManager.isFontLoaded()) {
/* 104 */       Font font = Font.create((Doc)doc, this.mPDFTronFontName, contents);
/* 105 */       String fontName = font.getName();
/* 106 */       widget.setFont(font);
/*     */ 
/*     */       
/* 109 */       updateFontMap(this.mPdfViewCtrl.getContext(), widget.getType(), this.mPDFTronFontName, fontName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void initTextField() {
/* 114 */     Context context = this.mPdfViewCtrl.getContext();
/* 115 */     SharedPreferences settings = Tool.getToolPreferences(context);
/* 116 */     this.mTextColor = settings.getInt(getTextColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultTextColor(context, 19));
/* 117 */     this.mTextSize = settings.getFloat(getTextSizeKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultTextSize(context, 19));
/* 118 */     this.mStrokeColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(context, 19));
/* 119 */     this.mThickness = settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(context, 19));
/* 120 */     this.mFillColor = settings.getInt(getColorFillKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFillColor(context, 19));
/* 121 */     this.mOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(context, 19));
/* 122 */     this.mPDFTronFontName = settings.getString(getFontKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultFont(this.mPdfViewCtrl.getContext(), 19));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextFieldCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */