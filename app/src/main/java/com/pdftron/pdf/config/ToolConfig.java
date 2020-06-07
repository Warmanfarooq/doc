/*     */ package com.pdftron.pdf.config;
/*     */ 
/*     */ import android.util.SparseArray;
/*     */ import androidx.annotation.IdRes;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolConfig
/*     */ {
/*     */   private static ToolConfig _INSTANCE;
/*     */   private SparseArray<ToolManager.ToolMode> mToolQMItemPair;
/*     */   private SparseArray<ToolManager.ToolMode> mToolAnnotationToolbarItemPair;
/*     */   private ArrayList<Integer> mNoAnnotPermissionQMList;
/*     */   private SparseArray<ToolManager.ToolModeBase> mAnnotHandlerToolPair;
/*     */   private PanLongPressSwitchToolCallback mPanLongPressSwitchToolCallback;
/*     */   private Set<Integer> mDisabledAnnotEditingTypes;
/*     */   private ArrayList<FontResource> mFontList;
/*     */   
/*     */   public static ToolConfig getInstance() {
/*  37 */     if (_INSTANCE == null) {
/*  38 */       _INSTANCE = new ToolConfig();
/*     */     }
/*  40 */     return _INSTANCE;
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
/*     */   private ToolConfig() {
/*  55 */     this.mToolQMItemPair = new SparseArray();
/*  56 */     this.mToolQMItemPair.put(R.id.qm_line, ToolManager.ToolMode.LINE_CREATE);
/*  57 */     this.mToolQMItemPair.put(R.id.qm_arrow, ToolManager.ToolMode.ARROW_CREATE);
/*  58 */     this.mToolQMItemPair.put(R.id.qm_ruler, ToolManager.ToolMode.RULER_CREATE);
/*  59 */     this.mToolQMItemPair.put(R.id.qm_perimeter_measure, ToolManager.ToolMode.PERIMETER_MEASURE_CREATE);
/*  60 */     this.mToolQMItemPair.put(R.id.qm_area_measure, ToolManager.ToolMode.AREA_MEASURE_CREATE);
/*  61 */     this.mToolQMItemPair.put(R.id.qm_rect_area_measure, ToolManager.ToolMode.RECT_AREA_MEASURE_CREATE);
/*  62 */     this.mToolQMItemPair.put(R.id.qm_polyline, ToolManager.ToolMode.POLYLINE_CREATE);
/*  63 */     this.mToolQMItemPair.put(R.id.qm_free_text, ToolManager.ToolMode.TEXT_CREATE);
/*  64 */     this.mToolQMItemPair.put(R.id.qm_callout, ToolManager.ToolMode.CALLOUT_CREATE);
/*  65 */     this.mToolQMItemPair.put(R.id.qm_sticky_note, ToolManager.ToolMode.TEXT_ANNOT_CREATE);
/*  66 */     this.mToolQMItemPair.put(R.id.qm_free_hand, ToolManager.ToolMode.INK_CREATE);
/*  67 */     this.mToolQMItemPair.put(R.id.qm_free_highlighter, ToolManager.ToolMode.FREE_HIGHLIGHTER);
/*  68 */     this.mToolQMItemPair.put(R.id.qm_floating_sig, ToolManager.ToolMode.SIGNATURE);
/*  69 */     this.mToolQMItemPair.put(R.id.qm_image_stamper, ToolManager.ToolMode.STAMPER);
/*  70 */     this.mToolQMItemPair.put(R.id.qm_link, ToolManager.ToolMode.TEXT_LINK_CREATE);
/*  71 */     this.mToolQMItemPair.put(R.id.qm_rectangle, ToolManager.ToolMode.RECT_CREATE);
/*  72 */     this.mToolQMItemPair.put(R.id.qm_oval, ToolManager.ToolMode.OVAL_CREATE);
/*  73 */     this.mToolQMItemPair.put(R.id.qm_sound, ToolManager.ToolMode.SOUND_CREATE);
/*  74 */     this.mToolQMItemPair.put(R.id.qm_file_attachment, ToolManager.ToolMode.FILE_ATTACHMENT_CREATE);
/*  75 */     this.mToolQMItemPair.put(R.id.qm_polygon, ToolManager.ToolMode.POLYGON_CREATE);
/*  76 */     this.mToolQMItemPair.put(R.id.qm_cloud, ToolManager.ToolMode.CLOUD_CREATE);
/*  77 */     this.mToolQMItemPair.put(R.id.qm_ink_eraser, ToolManager.ToolMode.INK_ERASER);
/*  78 */     this.mToolQMItemPair.put(R.id.qm_form_text, ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE);
/*  79 */     this.mToolQMItemPair.put(R.id.qm_form_check_box, ToolManager.ToolMode.FORM_CHECKBOX_CREATE);
/*  80 */     this.mToolQMItemPair.put(R.id.qm_form_combo_box, ToolManager.ToolMode.FORM_COMBO_BOX_CREATE);
/*  81 */     this.mToolQMItemPair.put(R.id.qm_form_list_box, ToolManager.ToolMode.FORM_LIST_BOX_CREATE);
/*  82 */     this.mToolQMItemPair.put(R.id.qm_form_signature, ToolManager.ToolMode.FORM_SIGNATURE_CREATE);
/*  83 */     this.mToolQMItemPair.put(R.id.qm_form_radio_group, ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE);
/*  84 */     this.mToolQMItemPair.put(R.id.qm_highlight, ToolManager.ToolMode.TEXT_HIGHLIGHT);
/*  85 */     this.mToolQMItemPair.put(R.id.qm_strikeout, ToolManager.ToolMode.TEXT_STRIKEOUT);
/*  86 */     this.mToolQMItemPair.put(R.id.qm_squiggly, ToolManager.ToolMode.TEXT_SQUIGGLY);
/*  87 */     this.mToolQMItemPair.put(R.id.qm_underline, ToolManager.ToolMode.TEXT_UNDERLINE);
/*  88 */     this.mToolQMItemPair.put(R.id.qm_redaction, ToolManager.ToolMode.TEXT_REDACTION);
/*  89 */     this.mToolQMItemPair.put(R.id.qm_rect_redaction, ToolManager.ToolMode.RECT_REDACTION);
/*  90 */     this.mToolQMItemPair.put(R.id.qm_rect_group_select, ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP);
/*  91 */     this.mToolQMItemPair.put(R.id.qm_rubber_stamper, ToolManager.ToolMode.RUBBER_STAMPER);
/*     */ 
/*     */     
/*  94 */     this.mToolAnnotationToolbarItemPair = new SparseArray();
/*     */     
/*  96 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_line, ToolManager.ToolMode.LINE_CREATE);
/*  97 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_arrow, ToolManager.ToolMode.ARROW_CREATE);
/*  98 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_ruler, ToolManager.ToolMode.RULER_CREATE);
/*  99 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_perimeter_measure, ToolManager.ToolMode.PERIMETER_MEASURE_CREATE);
/* 100 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_area_measure, ToolManager.ToolMode.AREA_MEASURE_CREATE);
/* 101 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_polyline, ToolManager.ToolMode.POLYLINE_CREATE);
/* 102 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_freetext, ToolManager.ToolMode.TEXT_CREATE);
/* 103 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_callout, ToolManager.ToolMode.CALLOUT_CREATE);
/* 104 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_stickynote, ToolManager.ToolMode.TEXT_ANNOT_CREATE);
/* 105 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_freehand, ToolManager.ToolMode.INK_CREATE);
/* 106 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_free_highlighter, ToolManager.ToolMode.FREE_HIGHLIGHTER);
/* 107 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_rectangle, ToolManager.ToolMode.RECT_CREATE);
/* 108 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_oval, ToolManager.ToolMode.OVAL_CREATE);
/* 109 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_polygon, ToolManager.ToolMode.POLYGON_CREATE);
/* 110 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_cloud, ToolManager.ToolMode.CLOUD_CREATE);
/* 111 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_eraser, ToolManager.ToolMode.INK_ERASER);
/* 112 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_text_highlight, ToolManager.ToolMode.TEXT_HIGHLIGHT);
/* 113 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_text_strikeout, ToolManager.ToolMode.TEXT_STRIKEOUT);
/* 114 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_text_squiggly, ToolManager.ToolMode.TEXT_SQUIGGLY);
/* 115 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_text_underline, ToolManager.ToolMode.TEXT_UNDERLINE);
/* 116 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_multi_select, ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP);
/* 117 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_image_stamper, ToolManager.ToolMode.STAMPER);
/* 118 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_rubber_stamper, ToolManager.ToolMode.RUBBER_STAMPER);
/* 119 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_stamp, ToolManager.ToolMode.SIGNATURE);
/* 120 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_annotation_toolbar_tool_sound, ToolManager.ToolMode.SOUND_CREATE);
/*     */     
/* 122 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_text, ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE);
/* 123 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_checkbox, ToolManager.ToolMode.FORM_CHECKBOX_CREATE);
/* 124 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_radio, ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE);
/* 125 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_listbox, ToolManager.ToolMode.FORM_LIST_BOX_CREATE);
/* 126 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_combobox, ToolManager.ToolMode.FORM_COMBO_BOX_CREATE);
/* 127 */     this.mToolAnnotationToolbarItemPair.put(R.id.controls_form_field_toolbar_widget_signature, ToolManager.ToolMode.FORM_SIGNATURE_CREATE);
/*     */     
/* 129 */     this.mNoAnnotPermissionQMList = new ArrayList<>();
/* 130 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_appearance));
/* 131 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_flatten));
/* 132 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_edit));
/* 133 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_type));
/* 134 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_delete));
/* 135 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(R.id.qm_text));
/*     */ 
/*     */     
/* 138 */     this.mAnnotHandlerToolPair = new SparseArray();
/* 139 */     this.mAnnotHandlerToolPair.put(1, ToolManager.ToolMode.LINK_ACTION);
/* 140 */     this.mAnnotHandlerToolPair.put(19, ToolManager.ToolMode.FORM_FILL);
/* 141 */     this.mAnnotHandlerToolPair.put(27, ToolManager.ToolMode.RICH_MEDIA);
/* 142 */     this.mAnnotHandlerToolPair.put(3, ToolManager.ToolMode.ANNOT_EDIT_LINE);
/* 143 */     this.mAnnotHandlerToolPair.put(1001, ToolManager.ToolMode.ANNOT_EDIT_LINE);
/* 144 */     this.mAnnotHandlerToolPair.put(1006, ToolManager.ToolMode.ANNOT_EDIT_LINE);
/* 145 */     this.mAnnotHandlerToolPair.put(8, ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP);
/* 146 */     this.mAnnotHandlerToolPair.put(9, ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP);
/* 147 */     this.mAnnotHandlerToolPair.put(11, ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP);
/* 148 */     this.mAnnotHandlerToolPair.put(10, ToolManager.ToolMode.ANNOT_EDIT_TEXT_MARKUP);
/* 149 */     this.mAnnotHandlerToolPair.put(7, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/* 150 */     this.mAnnotHandlerToolPair.put(6, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/* 151 */     this.mAnnotHandlerToolPair.put(1008, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/* 152 */     this.mAnnotHandlerToolPair.put(1009, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/* 153 */     this.mAnnotHandlerToolPair.put(1005, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/* 154 */     this.mAnnotHandlerToolPair.put(1007, ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ToolManager.ToolMode getToolModeByQMItemId(@IdRes int qmItemId) {
/* 165 */     if (this.mToolQMItemPair != null && this.mToolQMItemPair.indexOfKey(qmItemId) >= 0) {
/* 166 */       return (ToolManager.ToolMode)this.mToolQMItemPair.get(qmItemId);
/*     */     }
/* 168 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putCustomToolQMItemPair(@IdRes int qmItemId, ToolManager.ToolMode mode) {
/* 178 */     this.mToolQMItemPair.put(qmItemId, mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ToolManager.ToolMode getToolModeByAnnotationToolbarItemId(@IdRes int itemId) {
/* 189 */     if (this.mToolAnnotationToolbarItemPair != null && this.mToolAnnotationToolbarItemPair.indexOfKey(itemId) >= 0) {
/* 190 */       return (ToolManager.ToolMode)this.mToolAnnotationToolbarItemPair.get(itemId);
/*     */     }
/* 192 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putCustomToolAnnotationToolbarItemPair(@IdRes int itemId, ToolManager.ToolMode mode) {
/* 202 */     this.mToolAnnotationToolbarItemPair.put(itemId, mode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHideQMItem(@IdRes int itemId) {
/* 212 */     return this.mNoAnnotPermissionQMList.contains(Integer.valueOf(itemId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addQMHideItem(@IdRes int itemId) {
/* 222 */     this.mNoAnnotPermissionQMList.add(Integer.valueOf(itemId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removeQMHideItem(@IdRes int itemId) {
/* 232 */     return this.mNoAnnotPermissionQMList.remove(Integer.valueOf(itemId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getAnnotationHandlerToolMode(int annotType) {
/* 242 */     if (isAnnotEditingDisabled(annotType)) {
/* 243 */       return (ToolManager.ToolModeBase)ToolManager.ToolMode.PAN;
/*     */     }
/* 245 */     if (this.mAnnotHandlerToolPair.indexOfKey(annotType) > -1) {
/* 246 */       return (ToolManager.ToolModeBase)this.mAnnotHandlerToolPair.get(annotType);
/*     */     }
/* 248 */     return (ToolManager.ToolModeBase)ToolManager.ToolMode.ANNOT_EDIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putAnnotationToolModePair(int annotType, ToolManager.ToolModeBase toolMode) {
/* 258 */     this.mAnnotHandlerToolPair.put(annotType, toolMode);
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
/*     */   public PanLongPressSwitchToolCallback getPanLongPressSwitchToolCallback() {
/* 312 */     if (this.mPanLongPressSwitchToolCallback != null) {
/* 313 */       return this.mPanLongPressSwitchToolCallback;
/*     */     }
/*     */     
/* 316 */     return new PanLongPressSwitchToolCallback()
/*     */       {
/*     */         public ToolManager.ToolMode onPanLongPressSwitchTool(@Nullable Annot annot, boolean isMadeByPDFTron, boolean isTextSelect) throws PDFNetException {
/* 319 */           if (isTextSelect) {
/* 320 */             return ToolManager.ToolMode.TEXT_SELECT;
/*     */           }
/* 322 */           if (annot == null) {
/* 323 */             return ToolManager.ToolMode.PAN;
/*     */           }
/* 325 */           int annotType = AnnotUtils.getAnnotType(annot);
/* 326 */           if (ToolConfig.this.isAnnotEditingDisabled(annotType)) {
/* 327 */             return ToolManager.ToolMode.PAN;
/*     */           }
/* 329 */           switch (annotType) {
/*     */             case 1:
/* 331 */               if (isMadeByPDFTron) {
/* 332 */                 return ToolManager.ToolMode.ANNOT_EDIT;
/*     */               }
/* 334 */               return ToolManager.ToolMode.PAN;
/*     */             
/*     */             case 19:
/* 337 */               if (isMadeByPDFTron)
/* 338 */                 return ToolManager.ToolMode.ANNOT_EDIT; 
/*     */               break;
/*     */           } 
/* 341 */           ToolManager.ToolModeBase mode = ToolConfig.this.getAnnotationHandlerToolMode(annotType);
/* 342 */           if (null != mode && mode instanceof ToolManager.ToolMode) {
/* 343 */             return (ToolManager.ToolMode)mode;
/*     */           }
/* 345 */           return ToolManager.ToolMode.ANNOT_EDIT;
/*     */         }
/*     */       };
/*     */   }
/*     */   
/*     */   public static interface PanLongPressSwitchToolCallback
/*     */   {
/*     */     ToolManager.ToolMode onPanLongPressSwitchTool(@Nullable Annot param1Annot, boolean param1Boolean1, boolean param1Boolean2) throws PDFNetException;
/*     */   }
/*     */   
/*     */   public void setPanToolLongPressSwitchToolCallback(PanLongPressSwitchToolCallback callback) {
/* 356 */     this.mPanLongPressSwitchToolCallback = callback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFontList(ArrayList<FontResource> fonts) {
/* 364 */     this.mFontList = fonts;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<FontResource> getFontList() {
/* 372 */     return this.mFontList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableAnnotEditing(Integer[] annotTypes) {
/* 381 */     if (this.mDisabledAnnotEditingTypes == null) {
/* 382 */       this.mDisabledAnnotEditingTypes = new HashSet<>();
/*     */     }
/* 384 */     Collections.addAll(this.mDisabledAnnotEditingTypes, annotTypes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableAnnotEditing(Integer[] annotTypes) {
/* 393 */     if (this.mDisabledAnnotEditingTypes == null) {
/*     */       return;
/*     */     }
/* 396 */     List<Integer> annotTypeList = Arrays.asList(annotTypes);
/* 397 */     this.mDisabledAnnotEditingTypes.removeAll(annotTypeList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAnnotEditingDisabled(int annotType) {
/* 407 */     return (this.mDisabledAnnotEditingTypes != null && this.mDisabledAnnotEditingTypes.contains(Integer.valueOf(annotType)));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\ToolConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */