/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnalyticsAnnotStylePicker
/*     */ {
/*     */   private static AnalyticsAnnotStylePicker _INSTANCE;
/*  11 */   private static final Object sLock = new Object();
/*     */   
/*     */   public static AnalyticsAnnotStylePicker getInstance() {
/*  14 */     synchronized (sLock) {
/*  15 */       if (_INSTANCE == null) {
/*  16 */         _INSTANCE = new AnalyticsAnnotStylePicker();
/*     */       }
/*     */     } 
/*  19 */     return _INSTANCE;
/*     */   }
/*     */ 
/*     */   
/*     */   private int mOpenedFromLocation;
/*  24 */   private String mOpenedAnnotation = "unknown";
/*     */   private int mSelectedColorMode;
/*  26 */   private int mPresetIndex = -1;
/*     */   private boolean mHasAction = false;
/*     */   
/*     */   private AnalyticsAnnotStylePicker() {
/*  30 */     reset();
/*     */   }
/*     */   
/*     */   public void showAnnotStyleDialog(int from, String annotation) {
/*  34 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(40, 
/*  35 */         AnalyticsParam.stylePickerBasicParam(from, annotation));
/*  36 */     setOpenedAnnotation(annotation);
/*  37 */     setOpenedLocation(from);
/*     */   }
/*     */   
/*     */   public void dismissAnnotStyleDialog() {
/*  41 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(40);
/*  42 */     AnalyticsHandlerAdapter.getInstance().sendEvent(44, 
/*  43 */         AnalyticsParam.stylePickerCloseParam(this.mHasAction, this.mOpenedFromLocation, this.mOpenedAnnotation));
/*  44 */     reset();
/*     */   }
/*     */   
/*     */   private void reset() {
/*  48 */     this.mOpenedFromLocation = -1;
/*  49 */     this.mOpenedAnnotation = "unknown";
/*  50 */     this.mSelectedColorMode = -1;
/*  51 */     this.mPresetIndex = -1;
/*  52 */     this.mHasAction = false;
/*     */   }
/*     */   
/*     */   public int getOpenedFromLocation() {
/*  56 */     return this.mOpenedFromLocation;
/*     */   }
/*     */   
/*     */   private void setOpenedLocation(int from) {
/*  60 */     this.mOpenedFromLocation = from;
/*     */   }
/*     */   
/*     */   private void setOpenedAnnotation(String annotation) {
/*  64 */     this.mOpenedAnnotation = annotation;
/*     */   }
/*     */   
/*     */   public void setSelectedColorMode(int colorMode) {
/*  68 */     this.mSelectedColorMode = colorMode;
/*     */   }
/*     */   
/*     */   public void setPresetIndex(int presetIndex) {
/*  72 */     this.mPresetIndex = presetIndex;
/*     */   }
/*     */   
/*     */   public void selectColor(String color, int picker) {
/*  76 */     AnalyticsHandlerAdapter.getInstance().sendEvent(41, 
/*  77 */         AnalyticsParam.stylePickerSelectColorParam(color, picker, this.mSelectedColorMode, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/*  78 */     this.mHasAction = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectThickness(float thickness) {
/*  83 */     AnalyticsHandlerAdapter.getInstance().sendEvent(51, 
/*     */         
/*  85 */         AnalyticsParam.stylePickerSelectThicknessParam(thickness, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/*  86 */     this.mHasAction = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectOpacity(float opacity) {
/*  91 */     AnalyticsHandlerAdapter.getInstance().sendEvent(52, 
/*     */         
/*  93 */         AnalyticsParam.stylePickerSelectOpacityParam(opacity, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/*  94 */     this.mHasAction = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectTextSize(float textSize) {
/*  99 */     AnalyticsHandlerAdapter.getInstance().sendEvent(53, 
/*     */         
/* 101 */         AnalyticsParam.stylePickerSelectTextSizeParam(textSize, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/* 102 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void selectRulerBaseValue(float base) {
/* 106 */     AnalyticsHandlerAdapter.getInstance().sendEvent(60, 
/*     */         
/* 108 */         AnalyticsParam.stylePickerSelectRulerBaseParam(base, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/* 109 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void selectRulerTranslateValue(float translate) {
/* 113 */     AnalyticsHandlerAdapter.getInstance().sendEvent(60, 
/*     */         
/* 115 */         AnalyticsParam.stylePickerSelectRulerTranslateParam(translate, this.mOpenedFromLocation, this.mOpenedAnnotation, this.mPresetIndex));
/* 116 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void setRichTextEnabled(boolean enabled) {
/* 120 */     AnalyticsHandlerAdapter.getInstance().sendEvent(76, 
/*     */         
/* 122 */         AnalyticsParam.stylePickerSelectRichTextParam(enabled, this.mOpenedFromLocation, this.mOpenedAnnotation));
/* 123 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void setEraseInkOnlyEnabled(boolean enabled) {
/* 127 */     AnalyticsHandlerAdapter.getInstance().sendEvent(76, 
/*     */         
/* 129 */         AnalyticsParam.stylePickerSelectEraserParam(enabled, this.mOpenedFromLocation, this.mOpenedAnnotation));
/* 130 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void setPressureSensitiveEnabled(boolean enabled) {
/* 134 */     AnalyticsHandlerAdapter.getInstance().sendEvent(76, 
/*     */         
/* 136 */         AnalyticsParam.stylePickerSelectPressureParam(enabled, this.mOpenedFromLocation, this.mOpenedAnnotation));
/* 137 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void selectPreset(int presetIndex, boolean isInDefaultStyles) {
/* 141 */     AnalyticsHandlerAdapter.getInstance().sendEvent(54, 
/*     */         
/* 143 */         AnalyticsParam.stylePickerSelectPresetParam(this.mOpenedFromLocation, this.mOpenedAnnotation, presetIndex, isInDefaultStyles));
/* 144 */     setPresetIndex(presetIndex);
/* 145 */     this.mHasAction = true;
/*     */   }
/*     */   
/*     */   public void deselectPreset(int presetIndex) {
/* 149 */     AnalyticsHandlerAdapter.getInstance().sendEvent(55, 
/*     */         
/* 151 */         AnalyticsParam.stylePickerDeselectPresetParam(this.mOpenedFromLocation, this.mOpenedAnnotation, presetIndex));
/* 152 */     setPresetIndex(-1);
/* 153 */     this.mHasAction = true;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnalyticsAnnotStylePicker.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */