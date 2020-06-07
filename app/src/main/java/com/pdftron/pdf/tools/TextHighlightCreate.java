/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.content.SharedPreferences;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.NonNull;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.annots.Highlight;
/*    */ import com.pdftron.sdf.Doc;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class TextHighlightCreate
/*    */   extends TextMarkupCreate
/*    */ {
/*    */   public TextHighlightCreate(@NonNull PDFViewCtrl ctrl) {
/* 30 */     super(ctrl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 38 */     return ToolManager.ToolMode.TEXT_HIGHLIGHT;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 43 */     return 8;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(PDFDoc doc, Rect bbox) throws PDFNetException {
/* 51 */     return (Annot)Highlight.create((Doc)doc, bbox);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/* 59 */     this.mColor = color;
/* 60 */     this.mOpacity = opacity;
/* 61 */     this.mThickness = thickness;
/*    */     
/* 63 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 64 */     SharedPreferences.Editor editor = settings.edit();
/*    */     
/* 66 */     editor.putInt(getColorKey(getCreateAnnotType()), this.mColor);
/* 67 */     editor.putFloat(getOpacityKey(getCreateAnnotType()), this.mOpacity);
/*    */     
/* 69 */     editor.apply();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextHighlightCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */