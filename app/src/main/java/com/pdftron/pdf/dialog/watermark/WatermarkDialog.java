/*     */ package com.pdftron.pdf.dialog.watermark;
/*     */ 
/*     */ import android.os.Bundle;
/*     */ import android.view.View;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WatermarkDialog
/*     */   extends AnnotStyleDialogFragment
/*     */ {
/*  21 */   public static final String TAG = WatermarkDialog.class.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WatermarkDialog newInstance(@NonNull PDFViewCtrl pdfViewCtrl) {
/*  28 */     AnnotStyle annotStyle = new AnnotStyle();
/*  29 */     annotStyle.setAnnotType(23);
/*  30 */     annotStyle.setOpacity(0.8F);
/*  31 */     annotStyle.setTextColor(-65536);
/*  32 */     annotStyle.setTextSize(72.0F);
/*  33 */     annotStyle.hasFillColor();
/*  34 */     annotStyle.setOverlayText("Sample Watermark");
/*  35 */     return newInstance(annotStyle, pdfViewCtrl);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WatermarkDialog newInstance(@NonNull AnnotStyle annotStyle, @NonNull final PDFViewCtrl pdfViewCtrl) {
/*  46 */     Bundle bundle = new Bundle();
/*  47 */     bundle.putString("annotStyle", annotStyle.toJSONString());
/*     */     
/*  49 */     final WatermarkDialog watermarkDialog = new WatermarkDialog();
/*  50 */     watermarkDialog.setArguments(bundle);
/*  51 */     watermarkDialog.setOnAnnotStyleChangeListener(new AnnotStyle.OnAnnotStyleChangeListener()
/*     */         {
/*     */           public void onChangeAnnotThickness(float thickness, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextSize(float textSize, boolean done) {
/*  60 */             if (done) {
/*  61 */               WatermarkUtil.clearWatermark(pdfViewCtrl);
/*  62 */               int color = watermarkDialog.getAnnotStyle().getTextColor();
/*  63 */               float opacity = watermarkDialog.getAnnotStyle().getOpacity();
/*  64 */               String text = watermarkDialog.getAnnotStyle().getOverlayText();
/*  65 */               WatermarkUtil.setTextWatermark(pdfViewCtrl, text, color, opacity, textSize, true);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextColor(int textColor) {
/*  72 */             WatermarkUtil.clearWatermark(pdfViewCtrl);
/*  73 */             float opacity = watermarkDialog.getAnnotStyle().getOpacity();
/*  74 */             float textSize = watermarkDialog.getAnnotStyle().getTextSize();
/*  75 */             String text = watermarkDialog.getAnnotStyle().getOverlayText();
/*  76 */             WatermarkUtil.setTextWatermark(pdfViewCtrl, text, textColor, opacity, textSize, true);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotOpacity(float opacity, boolean done) {
/*  82 */             if (done) {
/*  83 */               WatermarkUtil.clearWatermark(pdfViewCtrl);
/*  84 */               int color = watermarkDialog.getAnnotStyle().getTextColor();
/*  85 */               float textSize = watermarkDialog.getAnnotStyle().getTextSize();
/*  86 */               String text = watermarkDialog.getAnnotStyle().getOverlayText();
/*  87 */               WatermarkUtil.setTextWatermark(pdfViewCtrl, text, color, opacity, textSize, true);
/*     */             } 
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotStrokeColor(int color) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFillColor(int color) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotIcon(String icon) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFont(FontResource font) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRulerProperty(RulerItem rulerItem) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeOverlayText(String overlayText) {
/* 119 */             WatermarkUtil.clearWatermark(pdfViewCtrl);
/* 120 */             int color = watermarkDialog.getAnnotStyle().getTextColor();
/* 121 */             float opacity = watermarkDialog.getAnnotStyle().getOpacity();
/* 122 */             float textSize = watermarkDialog.getAnnotStyle().getTextSize();
/* 123 */             WatermarkUtil.setTextWatermark(pdfViewCtrl, overlayText, color, opacity, textSize, true);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeSnapping(boolean snap) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRichContentEnabled(boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeDateFormat(String dateFormat) {}
/*     */         });
/* 142 */     return watermarkDialog;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 150 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/* 152 */     setAnnotPreviewVisibility(8);
/*     */   }
/*     */ 
/*     */   
/*     */   public void show(@NonNull FragmentManager fragmentManager) {
/* 157 */     if (isAdded()) {
/*     */       return;
/*     */     }
/* 160 */     show(fragmentManager, TAG);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\watermark\WatermarkDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */