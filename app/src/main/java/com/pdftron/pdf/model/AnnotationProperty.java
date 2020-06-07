/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import androidx.annotation.NonNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotationProperty
/*     */ {
/*     */   public enum Property
/*     */   {
/*  17 */     OPACITY,
/*     */ 
/*     */ 
/*     */     
/*  21 */     THICKNESS,
/*     */ 
/*     */ 
/*     */     
/*  25 */     COLOR,
/*     */ 
/*     */ 
/*     */     
/*  29 */     FILL_COLOR,
/*     */ 
/*     */ 
/*     */     
/*  33 */     TEXT_SIZE,
/*     */ 
/*     */ 
/*     */     
/*  37 */     TEXT_FONT,
/*     */ 
/*     */ 
/*     */     
/*  41 */     TEXT_CONTENT,
/*     */ 
/*     */ 
/*     */     
/*  45 */     NOTE_CONTENT,
/*     */ 
/*     */ 
/*     */     
/*  49 */     FORM_TEXT_CONTENT,
/*     */ 
/*     */ 
/*     */     
/*  53 */     STICKY_NOTE_ICON,
/*     */ 
/*     */ 
/*     */     
/*  57 */     TEXT_MARKUP_TYPE,
/*     */ 
/*     */ 
/*     */     
/*  61 */     OTHER;
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
/*     */   public static String getPropertyString(@NonNull String methodName) {
/*  74 */     if (methodName.equals("editOpacity"))
/*  75 */       return Property.OPACITY.toString(); 
/*  76 */     if (methodName.equals("editThickness"))
/*  77 */       return Property.THICKNESS.toString(); 
/*  78 */     if (methodName.equals("editTextSize"))
/*  79 */       return Property.TEXT_SIZE.toString(); 
/*  80 */     if (methodName.equals("editFillColor"))
/*  81 */       return Property.FILL_COLOR.toString(); 
/*  82 */     if (methodName.equals("editFont"))
/*  83 */       return Property.TEXT_FONT.toString(); 
/*  84 */     if (methodName.equals("editIcon"))
/*  85 */       return Property.STICKY_NOTE_ICON.toString(); 
/*  86 */     if (methodName.equals("editColor"))
/*  87 */       return Property.COLOR.toString(); 
/*  88 */     if (methodName.equals("updateFreeText"))
/*  89 */       return Property.TEXT_CONTENT.toString(); 
/*  90 */     if (methodName.equals("prepareDialogStickyNoteDismiss"))
/*  91 */       return Property.NOTE_CONTENT.toString(); 
/*  92 */     if (methodName.equals("prepareDialogAnnotNoteDismiss"))
/*  93 */       return Property.NOTE_CONTENT.toString(); 
/*  94 */     if (methodName.equals("changeAnnotType"))
/*  95 */       return Property.TEXT_MARKUP_TYPE.toString(); 
/*  96 */     if (methodName.equals("applyFormFieldEditBoxAndQuit")) {
/*  97 */       return Property.FORM_TEXT_CONTENT.toString();
/*     */     }
/*  99 */     return methodName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Property getProperty(@NonNull String methodName) {
/*     */     try {
/* 111 */       return Property.valueOf(getPropertyString(methodName));
/* 112 */     } catch (Exception e) {
/* 113 */       return Property.OTHER;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\AnnotationProperty.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */