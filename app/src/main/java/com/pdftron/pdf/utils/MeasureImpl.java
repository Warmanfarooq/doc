/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.MeasureInfo;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import org.json.JSONObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MeasureImpl
/*     */ {
/*     */   private JSONObject mMeasureFDF;
/*     */   private int mAnnotType;
/*     */   
/*     */   public MeasureImpl(int annotType) {
/*  23 */     this.mAnnotType = annotType;
/*  24 */     String json = MeasureUtils.getDefaultMeasureInfo();
/*  25 */     initMeasureFDF(json);
/*     */   }
/*     */   
/*     */   private void initMeasureFDF(String json) {
/*  29 */     if (json != null) {
/*     */       try {
/*  31 */         this.mMeasureFDF = new JSONObject(json);
/*  32 */       } catch (Exception ex) {
/*  33 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateRulerItem(RulerItem rulerItem) {
/*  39 */     if (this.mMeasureFDF != null) {
/*  40 */       String result = MeasureUtils.setScaleAndPrecision(this.mAnnotType, this.mMeasureFDF, rulerItem);
/*  41 */       initMeasureFDF(result);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(Context context, AnnotStyle annotStyle) {
/*  51 */     RulerItem rulerItem = new RulerItem(annotStyle.getRulerBaseValue(), annotStyle.getRulerBaseUnit(), annotStyle.getRulerTranslateValue(), annotStyle.getRulerTranslateUnit(), annotStyle.getPrecision());
/*     */     
/*  53 */     updateRulerItem(rulerItem);
/*     */     
/*  55 */     SharedPreferences settings = Tool.getToolPreferences(context);
/*  56 */     SharedPreferences.Editor editor = settings.edit();
/*  57 */     editor.putString(ToolStyleConfig.getInstance().getRulerBaseUnitKey(this.mAnnotType, ""), rulerItem.mRulerBaseUnit);
/*  58 */     editor.putString(ToolStyleConfig.getInstance().getRulerTranslateUnitKey(this.mAnnotType, ""), rulerItem.mRulerTranslateUnit);
/*  59 */     editor.putFloat(ToolStyleConfig.getInstance().getRulerBaseValueKey(this.mAnnotType, ""), rulerItem.mRulerBase);
/*  60 */     editor.putFloat(ToolStyleConfig.getInstance().getRulerTranslateValueKey(this.mAnnotType, ""), rulerItem.mRulerTranslate);
/*  61 */     editor.putInt(ToolStyleConfig.getInstance().getRulerPrecisionKey(this.mAnnotType, ""), rulerItem.mPrecision);
/*  62 */     editor.apply();
/*     */   }
/*     */   
/*     */   public void handleDown(Context context) {
/*  66 */     SharedPreferences settings = Tool.getToolPreferences(context);
/*     */     
/*  68 */     RulerItem rulerItem = new RulerItem();
/*  69 */     rulerItem.mRulerBaseUnit = settings.getString(ToolStyleConfig.getInstance().getRulerBaseUnitKey(this.mAnnotType, ""), 
/*  70 */         ToolStyleConfig.getInstance().getDefaultRulerBaseUnit(context, this.mAnnotType));
/*  71 */     rulerItem.mRulerBase = settings.getFloat(ToolStyleConfig.getInstance().getRulerBaseValueKey(this.mAnnotType, ""), 
/*  72 */         ToolStyleConfig.getInstance().getDefaultRulerBaseValue(context, this.mAnnotType));
/*  73 */     rulerItem.mRulerTranslateUnit = settings.getString(ToolStyleConfig.getInstance().getRulerTranslateUnitKey(this.mAnnotType, ""), 
/*  74 */         ToolStyleConfig.getInstance().getDefaultRulerTranslateUnit(context, this.mAnnotType));
/*  75 */     rulerItem.mRulerTranslate = settings.getFloat(ToolStyleConfig.getInstance().getRulerTranslateValueKey(this.mAnnotType, ""), 
/*  76 */         ToolStyleConfig.getInstance().getDefaultRulerTranslateValue(context, this.mAnnotType));
/*  77 */     rulerItem.mPrecision = settings.getInt(ToolStyleConfig.getInstance().getRulerPrecisionKey(this.mAnnotType, ""), 
/*  78 */         ToolStyleConfig.getInstance().getDefaultRulerPrecision(context, this.mAnnotType));
/*     */     
/*  80 */     updateRulerItem(rulerItem);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MeasureInfo getAxis() {
/*  85 */     if (this.mMeasureFDF != null) {
/*  86 */       return MeasureUtils.getAxisInfo(this.mMeasureFDF);
/*     */     }
/*  88 */     return null;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public MeasureInfo getMeasure() {
/*  93 */     if (this.mMeasureFDF != null) {
/*  94 */       return MeasureUtils.getMeasureInfo(this.mAnnotType, this.mMeasureFDF);
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */   
/*     */   public String getMeasurementText(double value, MeasureInfo measureInfo) {
/* 100 */     return MeasureUtils.getMeasurementText(value, measureInfo);
/*     */   }
/*     */   
/*     */   public void commit(Annot annot) {
/* 104 */     if (this.mMeasureFDF != null)
/*     */       try {
/* 106 */         MeasureUtils.putMeasurementInfo(annot, this.mMeasureFDF.toString());
/* 107 */       } catch (Exception ex) {
/* 108 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\MeasureImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */