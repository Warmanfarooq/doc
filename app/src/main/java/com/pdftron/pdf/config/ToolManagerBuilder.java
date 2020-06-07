/*     */ package com.pdftron.pdf.config;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import android.util.SparseArray;
/*     */ import androidx.annotation.ArrayRes;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.RequiresApi;
/*     */ import androidx.annotation.StyleRes;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.controls.PdfViewCtrlTabFragment;
/*     */ import com.pdftron.pdf.tools.Eraser;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ToolManagerBuilder
/*     */   implements Parcelable
/*     */ {
/*     */   private boolean editInk = true;
/*     */   private boolean addImage = true;
/*     */   private boolean openToolbar = true;
/*     */   private boolean buildInPageIndicator;
/*     */   private boolean annotPermission;
/*     */   private boolean showAuthor;
/*     */   private boolean textMarkupAdobeHack = true;
/*     */   private boolean copyAnnot = true;
/*     */   private boolean stylusAsPen;
/*     */   private boolean inkSmoothing = true;
/*     */   private boolean autoSelect = true;
/*     */   private boolean disableQuickMenu;
/*     */   private boolean doubleTapToZoom = true;
/*     */   private boolean autoResizeFreeText;
/*     */   private boolean realtimeAnnotEdit = true;
/*     */   private boolean editFreeTextOnTap;
/*  63 */   private int disableToolModesId = -1;
/*  64 */   private int modeSize = 0;
/*     */   private String[] modes;
/*     */   private SparseArray<Object> customToolClassMap;
/*     */   private SparseArray<Object> customToolParamMap;
/*  68 */   private int disableEditingAnnotTypesId = -1;
/*  69 */   private int annotTypeSize = 0;
/*     */   private int[] annotTypes;
/*     */   private boolean showSavedSignatures = true;
/*     */   private boolean showAnnotIndicator;
/*     */   private boolean showSignatureFromImage = true;
/*     */   private boolean annotationLayerEnabled;
/*     */   private boolean useDigitalSignature;
/*     */   private String digitalSignatureKeystorePath;
/*     */   private String digitalSignatureKeystorePassword;
/*  78 */   private int annotToolbarPrecedenceSize = 0;
/*     */   private String[] annotToolbarPrecedence;
/*     */   private boolean usePressureSensitiveSignatures = true;
/*  81 */   private String eraserType = Eraser.EraserType.INK_ERASER.name();
/*     */   private boolean showUndoRedo = true;
/*  83 */   private int selectionBoxMargin = 16;
/*     */   
/*     */   private boolean freeTextInlineToggleEnabled = true;
/*     */   private boolean showRichContentOption = true;
/*     */   
/*     */   private ToolManagerBuilder() {}
/*     */   
/*     */   protected ToolManagerBuilder(Parcel in) {
/*  91 */     this.editInk = (in.readByte() != 0);
/*  92 */     this.addImage = (in.readByte() != 0);
/*  93 */     this.openToolbar = (in.readByte() != 0);
/*  94 */     this.copyAnnot = (in.readByte() != 0);
/*  95 */     this.stylusAsPen = (in.readByte() != 0);
/*  96 */     this.inkSmoothing = (in.readByte() != 0);
/*  97 */     this.autoSelect = (in.readByte() != 0);
/*  98 */     this.buildInPageIndicator = (in.readByte() != 0);
/*  99 */     this.annotPermission = (in.readByte() != 0);
/* 100 */     this.showAuthor = (in.readByte() != 0);
/* 101 */     this.textMarkupAdobeHack = (in.readByte() != 0);
/* 102 */     this.disableQuickMenu = (in.readByte() != 0);
/* 103 */     this.doubleTapToZoom = (in.readByte() != 0);
/* 104 */     this.autoResizeFreeText = (in.readByte() != 0);
/* 105 */     this.realtimeAnnotEdit = (in.readByte() != 0);
/* 106 */     this.editFreeTextOnTap = (in.readByte() != 0);
/* 107 */     this.disableToolModesId = in.readInt();
/* 108 */     this.modeSize = in.readInt();
/* 109 */     this.modes = new String[this.modeSize];
/* 110 */     in.readStringArray(this.modes);
/* 111 */     this.customToolClassMap = in.readSparseArray(Tool.class.getClassLoader());
/* 112 */     this.customToolParamMap = in.readSparseArray(Object[].class.getClassLoader());
/* 113 */     this.disableEditingAnnotTypesId = in.readInt();
/* 114 */     this.annotTypeSize = in.readInt();
/* 115 */     this.annotTypes = new int[this.annotTypeSize];
/* 116 */     in.readIntArray(this.annotTypes);
/* 117 */     this.showSavedSignatures = (in.readByte() != 0);
/* 118 */     this.showAnnotIndicator = (in.readByte() != 0);
/* 119 */     this.showSignatureFromImage = (in.readByte() != 0);
/* 120 */     this.annotationLayerEnabled = (in.readByte() != 0);
/* 121 */     this.useDigitalSignature = (in.readByte() != 0);
/* 122 */     this.digitalSignatureKeystorePath = in.readString();
/* 123 */     this.digitalSignatureKeystorePassword = in.readString();
/* 124 */     this.annotToolbarPrecedenceSize = in.readInt();
/* 125 */     this.annotToolbarPrecedence = new String[this.annotToolbarPrecedenceSize];
/* 126 */     in.readStringArray(this.annotToolbarPrecedence);
/* 127 */     this.usePressureSensitiveSignatures = (in.readByte() != 0);
/* 128 */     this.eraserType = in.readString();
/* 129 */     this.showUndoRedo = (in.readByte() != 0);
/* 130 */     this.selectionBoxMargin = in.readInt();
/* 131 */     this.freeTextInlineToggleEnabled = (in.readByte() != 0);
/* 132 */     this.showRichContentOption = (in.readByte() != 0);
/*     */   }
/*     */   
/* 135 */   public static final Creator<ToolManagerBuilder> CREATOR = new Creator<ToolManagerBuilder>()
/*     */     {
/*     */       public ToolManagerBuilder createFromParcel(Parcel in) {
/* 138 */         return new ToolManagerBuilder(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public ToolManagerBuilder[] newArray(int size) {
/* 143 */         return new ToolManagerBuilder[size];
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToolManagerBuilder from() {
/* 153 */     return new ToolManagerBuilder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ToolManagerBuilder from(Context context, @StyleRes int styleRes) {
/* 164 */     return from().setStyle(context, styleRes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setStyle(Context context, @StyleRes int styleRes) {
/* 174 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolManager, 0, styleRes);
/*     */     
/*     */     try {
/* 177 */       String keystoreFilepath = a.getString(R.styleable.ToolManager_digital_signature_keystore_path);
/* 178 */       String keystorePassword = a.getString(R.styleable.ToolManager_digital_signature_keystore_password);
/* 179 */       String customEraserType = a.getString(R.styleable.ToolManager_eraser_type);
/* 180 */       if (null == customEraserType) {
/* 181 */         customEraserType = this.eraserType;
/*     */       }
/*     */       
/* 184 */       setEditInk(a.getBoolean(R.styleable.ToolManager_edit_ink_annots, this.editInk))
/* 185 */         .setAddImage(a.getBoolean(R.styleable.ToolManager_add_image_stamper_tool, this.addImage))
/* 186 */         .setOpenToolbar(a.getBoolean(R.styleable.ToolManager_open_toolbar_on_pan_ink_selected, this.openToolbar))
/* 187 */         .setBuildInPageIndicator(a.getBoolean(R.styleable.ToolManager_build_in_page_number_indicator, this.buildInPageIndicator))
/* 188 */         .setAnnotPermission(a.getBoolean(R.styleable.ToolManager_annot_permission_check, this.annotPermission))
/* 189 */         .setShowAuthor(a.getBoolean(R.styleable.ToolManager_show_author_dialog, this.showAuthor))
/* 190 */         .setTextMarkupAdobeHack(a.getBoolean(R.styleable.ToolManager_text_markup_adobe_hack, this.textMarkupAdobeHack))
/* 191 */         .setCopyAnnot(a.getBoolean(R.styleable.ToolManager_copy_annotated_text_to_note, this.copyAnnot))
/* 192 */         .setStylusAsPen(a.getBoolean(R.styleable.ToolManager_stylus_as_pen, this.stylusAsPen))
/* 193 */         .setInkSmoothing(a.getBoolean(R.styleable.ToolManager_ink_smoothing_enabled, this.inkSmoothing))
/* 194 */         .setDisableQuickMenu(a.getBoolean(R.styleable.ToolManager_quick_menu_disable, this.disableQuickMenu))
/* 195 */         .setDoubleTapToZoom(a.getBoolean(R.styleable.ToolManager_double_tap_to_zoom, this.doubleTapToZoom))
/* 196 */         .setAutoResizeFreeText(a.getBoolean(R.styleable.ToolManager_auto_resize_freetext, this.autoResizeFreeText))
/* 197 */         .setRealTimeAnnotEdit(a.getBoolean(R.styleable.ToolManager_realtime_annot_edit, this.realtimeAnnotEdit))
/* 198 */         .setEditFreeTextOnTap(a.getBoolean(R.styleable.ToolManager_edit_freetext_on_tap, this.editFreeTextOnTap))
/* 199 */         .freeTextInlineToggleEnabled(a.getBoolean(R.styleable.ToolManager_freeText_inline_toggle_enabled, this.freeTextInlineToggleEnabled))
/* 200 */         .setShowRichContentOption(a.getBoolean(R.styleable.ToolManager_freeText_show_rich_content_switch, this.showRichContentOption))
/* 201 */         .setShowSavedSignatures(a.getBoolean(R.styleable.ToolManager_show_saved_signatures, this.showSavedSignatures))
/* 202 */         .setShowAnnotIndicator(a.getBoolean(R.styleable.ToolManager_show_annot_indicator, this.showAnnotIndicator))
/* 203 */         .setShowSignatureFromImage(a.getBoolean(R.styleable.ToolManager_show_signature_from_image, this.showSignatureFromImage))
/* 204 */         .setAutoSelect(a.getBoolean(R.styleable.ToolManager_auto_select_annotation, this.autoSelect))
/* 205 */         .setAnnotationLayerEnabled(a.getBoolean(R.styleable.ToolManager_annotation_layer_enabled, this.annotationLayerEnabled))
/* 206 */         .setUseDigitalSignature(a.getBoolean(R.styleable.ToolManager_use_digital_signature, this.useDigitalSignature))
/* 207 */         .setDigitalSignatureKeystorePath((keystoreFilepath == null) ? this.digitalSignatureKeystorePath : keystoreFilepath)
/* 208 */         .setDigitalSignatureKeystorePassword((keystorePassword == null) ? this.digitalSignatureKeystorePassword : keystorePassword)
/* 209 */         .setDisableToolModesId(a.getResourceId(R.styleable.ToolManager_disable_tool_modes, this.disableToolModesId))
/* 210 */         .setDisableEditingAnnotTypesId(a.getResourceId(R.styleable.ToolManager_disable_annot_editing_by_types, this.disableEditingAnnotTypesId))
/* 211 */         .setUsePressureSensitiveSignatures(a.getBoolean(R.styleable.ToolManager_use_pressure_sensitive_signatures, this.usePressureSensitiveSignatures))
/* 212 */         .setEraserType((customEraserType == null) ? null : Eraser.EraserType.valueOf(customEraserType))
/* 213 */         .setShowUndoRedo(a.getBoolean(R.styleable.ToolManager_show_undo_redo, this.showUndoRedo))
/* 214 */         .setSelectionBoxMargin(a.getInteger(R.styleable.ToolManager_selection_box_margin, this.selectionBoxMargin));
/* 215 */       if (this.disableToolModesId != -1) {
/* 216 */         this.modes = context.getResources().getStringArray(this.disableToolModesId);
/*     */       }
/* 218 */       if (this.disableEditingAnnotTypesId != -1) {
/* 219 */         this.annotTypes = context.getResources().getIntArray(this.disableEditingAnnotTypesId);
/*     */       }
/*     */     } finally {
/* 222 */       a.recycle();
/*     */     } 
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setEditInk(boolean editInk) {
/* 234 */     this.editInk = editInk;
/* 235 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAddImage(boolean addImage) {
/* 245 */     this.addImage = addImage;
/* 246 */     return this;
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
/*     */   public ToolManagerBuilder setOpenToolbar(boolean openToolbar) {
/* 258 */     this.openToolbar = openToolbar;
/* 259 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setBuildInPageIndicator(boolean buildInPageIndicator) {
/* 269 */     this.buildInPageIndicator = buildInPageIndicator;
/* 270 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAnnotPermission(boolean annotPermission) {
/* 280 */     this.annotPermission = annotPermission;
/* 281 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setShowAuthor(boolean showAuthor) {
/* 290 */     this.showAuthor = showAuthor;
/* 291 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setTextMarkupAdobeHack(boolean textMarkupAdobeHack) {
/* 300 */     this.textMarkupAdobeHack = textMarkupAdobeHack;
/* 301 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setCopyAnnot(boolean copyAnnot) {
/* 310 */     this.copyAnnot = copyAnnot;
/* 311 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setStylusAsPen(boolean stylusAsPen) {
/* 320 */     this.stylusAsPen = stylusAsPen;
/* 321 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setInkSmoothing(boolean inkSmoothing) {
/* 330 */     this.inkSmoothing = inkSmoothing;
/* 331 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAutoSelect(boolean autoSelect) {
/* 340 */     this.autoSelect = autoSelect;
/* 341 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDisableQuickMenu(boolean disableQuickMenu) {
/* 350 */     this.disableQuickMenu = disableQuickMenu;
/* 351 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDoubleTapToZoom(boolean doubleTapToZoom) {
/* 360 */     this.doubleTapToZoom = doubleTapToZoom;
/* 361 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAutoResizeFreeText(boolean autoResizeFreeText) {
/* 370 */     this.autoResizeFreeText = autoResizeFreeText;
/* 371 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setRealTimeAnnotEdit(boolean realTimeAnnotEdit) {
/* 380 */     this.realtimeAnnotEdit = realTimeAnnotEdit;
/* 381 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setEditFreeTextOnTap(boolean editFreeTextOnTap) {
/* 390 */     this.editFreeTextOnTap = editFreeTextOnTap;
/* 391 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder freeTextInlineToggleEnabled(boolean freeTextInlineToggleEnabled) {
/* 400 */     this.freeTextInlineToggleEnabled = freeTextInlineToggleEnabled;
/* 401 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RequiresApi(api = 21)
/*     */   public ToolManagerBuilder setShowRichContentOption(boolean showRichContentOption) {
/* 410 */     this.showRichContentOption = showRichContentOption;
/* 411 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setShowSavedSignatures(boolean showSavedSignatures) {
/* 420 */     this.showSavedSignatures = showSavedSignatures;
/* 421 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setShowAnnotIndicator(boolean showAnnotIndicator) {
/* 430 */     this.showAnnotIndicator = showAnnotIndicator;
/* 431 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setShowSignatureFromImage(boolean showSignatureFromImage) {
/* 440 */     this.showSignatureFromImage = showSignatureFromImage;
/* 441 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAnnotationLayerEnabled(boolean annotationLayerEnabled) {
/* 450 */     this.annotationLayerEnabled = annotationLayerEnabled;
/* 451 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setUseDigitalSignature(boolean useDigitalSignature) {
/* 461 */     this.useDigitalSignature = useDigitalSignature;
/* 462 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDigitalSignatureKeystorePath(String digitalSignatureKeystorePath) {
/* 471 */     this.digitalSignatureKeystorePath = digitalSignatureKeystorePath;
/* 472 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDigitalSignatureKeystorePassword(String digitalSignatureKeystorePassword) {
/* 481 */     this.digitalSignatureKeystorePassword = digitalSignatureKeystorePassword;
/* 482 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDisableToolModesId(@ArrayRes int disableToolModesId) {
/* 492 */     this.disableToolModesId = disableToolModesId;
/* 493 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder disableToolModes(ToolManager.ToolMode[] toolModes) {
/* 503 */     this.modes = new String[toolModes.length];
/* 504 */     for (int i = 0; i < toolModes.length; i++) {
/* 505 */       this.modes[i] = toolModes[i].name();
/*     */     }
/* 507 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setAnnotToolbarPrecedence(ToolManager.ToolMode[] toolModes) {
/* 518 */     this.annotToolbarPrecedence = new String[toolModes.length];
/* 519 */     for (int i = 0; i < toolModes.length; i++) {
/* 520 */       this.annotToolbarPrecedence[i] = toolModes[i].name();
/*     */     }
/* 522 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setDisableEditingAnnotTypesId(@ArrayRes int disableEditingAnnotId) {
/* 532 */     this.disableEditingAnnotTypesId = disableEditingAnnotId;
/* 533 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder disableAnnotEditing(int[] annotTypes) {
/* 543 */     this.annotTypes = new int[annotTypes.length];
/* 544 */     System.arraycopy(annotTypes, 0, this.annotTypes, 0, annotTypes.length);
/* 545 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder addCustomizedTool(Tool tool) {
/* 555 */     if (null == this.customToolClassMap) {
/* 556 */       this.customToolClassMap = new SparseArray();
/*     */     }
/* 558 */     this.customToolClassMap.put(tool.getToolMode().getValue(), tool.getClass());
/* 559 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder addCustomizedTool(ToolManager.ToolModeBase toolMode, Class<? extends Tool> toolClass) {
/* 570 */     if (null == this.customToolClassMap) {
/* 571 */       this.customToolClassMap = new SparseArray();
/*     */     }
/* 573 */     this.customToolClassMap.put(toolMode.getValue(), toolClass);
/* 574 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder addCustomizedTool(Tool tool, Object... params) {
/* 585 */     addCustomizedTool(tool);
/* 586 */     if (null == this.customToolParamMap) {
/* 587 */       this.customToolParamMap = new SparseArray();
/*     */     }
/* 589 */     this.customToolParamMap.put(tool.getToolMode().getValue(), params);
/* 590 */     return this;
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
/*     */   public ToolManagerBuilder addCustomizedTool(ToolManager.ToolModeBase toolMode, Class<? extends Tool> toolClass, Object... params) {
/* 602 */     addCustomizedTool(toolMode, toolClass);
/* 603 */     if (null == this.customToolParamMap) {
/* 604 */       this.customToolParamMap = new SparseArray();
/*     */     }
/* 606 */     this.customToolParamMap.put(toolMode.getValue(), params);
/* 607 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setUsePressureSensitiveSignatures(boolean usePressureSensitiveSignatures) {
/* 616 */     this.usePressureSensitiveSignatures = usePressureSensitiveSignatures;
/* 617 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setEraserType(Eraser.EraserType type) {
/* 627 */     if (type != null) {
/* 628 */       this.eraserType = type.name();
/*     */     }
/* 630 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setShowUndoRedo(boolean showUndoRedo) {
/* 639 */     this.showUndoRedo = showUndoRedo;
/* 640 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManagerBuilder setSelectionBoxMargin(int selectionBoxMargin) {
/* 649 */     this.selectionBoxMargin = selectionBoxMargin;
/* 650 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager build(@Nullable FragmentActivity activity, @NonNull PDFViewCtrl pdfViewCtrl) {
/* 660 */     ToolManager toolManager = new ToolManager(pdfViewCtrl);
/* 661 */     Context context = pdfViewCtrl.getContext();
/* 662 */     toolManager.setEditInkAnnots(this.editInk);
/* 663 */     toolManager.setAddImageStamperTool(this.addImage);
/* 664 */     toolManager.setCanOpenEditToolbarFromPan(this.openToolbar);
/* 665 */     toolManager.setCopyAnnotatedTextToNoteEnabled(this.copyAnnot);
/* 666 */     toolManager.setStylusAsPen(this.stylusAsPen);
/* 667 */     toolManager.setInkSmoothingEnabled(this.inkSmoothing);
/* 668 */     toolManager.setFreeTextFonts(PdfViewCtrlSettingsManager.getFreeTextFonts(context));
/* 669 */     toolManager.setAutoSelectAnnotation(this.autoSelect);
/* 670 */     toolManager.setBuiltInPageNumberIndicatorVisible(this.buildInPageIndicator);
/* 671 */     toolManager.setAnnotPermissionCheckEnabled(this.annotPermission);
/* 672 */     toolManager.setShowAuthorDialog(this.showAuthor);
/* 673 */     toolManager.setTextMarkupAdobeHack(this.textMarkupAdobeHack);
/* 674 */     toolManager.setDisableQuickMenu(this.disableQuickMenu);
/* 675 */     toolManager.setDoubleTapToZoom(this.doubleTapToZoom);
/* 676 */     toolManager.setAutoResizeFreeText(this.autoResizeFreeText);
/* 677 */     toolManager.setRealTimeAnnotEdit(this.realtimeAnnotEdit);
/* 678 */     toolManager.setEditFreeTextOnTap(this.editFreeTextOnTap);
/* 679 */     toolManager.freeTextInlineToggleEnabled(this.freeTextInlineToggleEnabled);
/* 680 */     toolManager.setShowRichContentOption(this.showRichContentOption);
/* 681 */     toolManager.setShowSavedSignatures(this.showSavedSignatures);
/* 682 */     toolManager.setShowAnnotIndicators(this.showAnnotIndicator);
/* 683 */     toolManager.setShowSignatureFromImage(this.showSignatureFromImage);
/* 684 */     toolManager.setUsePressureSensitiveSignatures(this.usePressureSensitiveSignatures);
/* 685 */     if (this.eraserType != null) {
/* 686 */       toolManager.setEraserType(Eraser.EraserType.valueOf(this.eraserType));
/*     */     }
/* 688 */     toolManager.setShowUndoRedo(this.showUndoRedo);
/* 689 */     toolManager.setSelectionBoxMargin(this.selectionBoxMargin);
/* 690 */     if (this.annotationLayerEnabled) {
/* 691 */       toolManager.enableAnnotationLayer();
/*     */     }
/* 693 */     toolManager.setUsingDigitalSignature(this.useDigitalSignature);
/* 694 */     toolManager.setDigitalSignatureKeystorePath(this.digitalSignatureKeystorePath);
/* 695 */     toolManager.setDigitalSignatureKeystorePassword(this.digitalSignatureKeystorePassword);
/* 696 */     toolManager.setCurrentActivity(activity);
/*     */     
/* 698 */     if (this.modes == null && this.disableToolModesId != -1) {
/* 699 */       this.modes = context.getResources().getStringArray(this.disableToolModesId);
/*     */     }
/*     */     
/* 702 */     if (this.annotTypes == null && this.disableEditingAnnotTypesId != -1) {
/* 703 */       this.annotTypes = context.getResources().getIntArray(this.disableEditingAnnotTypesId);
/*     */     }
/*     */     
/* 706 */     if (this.modes != null) {
/* 707 */       ArrayList<ToolManager.ToolMode> disabledModes = new ArrayList<>(this.modes.length);
/* 708 */       for (String mode : this.modes) {
/* 709 */         disabledModes.add(ToolManager.ToolMode.valueOf(mode));
/*     */       }
/* 711 */       toolManager.disableToolMode(disabledModes.<ToolManager.ToolMode>toArray(new ToolManager.ToolMode[disabledModes.size()]));
/*     */     } 
/* 713 */     if (this.annotToolbarPrecedence != null) {
/* 714 */       ArrayList<ToolManager.ToolMode> modes = new ArrayList<>(this.annotToolbarPrecedence.length);
/* 715 */       for (String mode : this.annotToolbarPrecedence) {
/* 716 */         modes.add(ToolManager.ToolMode.valueOf(mode));
/*     */       }
/* 718 */       toolManager.setAnnotToolbarPrecedence(modes.<ToolManager.ToolMode>toArray(new ToolManager.ToolMode[modes.size()]));
/*     */     } 
/* 720 */     if (this.annotTypes != null) {
/* 721 */       toolManager.disableAnnotEditing(ArrayUtils.toObject(this.annotTypes));
/*     */     }
/* 723 */     if (this.customToolClassMap != null) {
/* 724 */       HashMap<ToolManager.ToolModeBase, Class<? extends Tool>> map = new HashMap<>();
/* 725 */       for (int i = 0; i < this.customToolClassMap.size(); i++) {
/* 726 */         int toolModeVal = this.customToolClassMap.keyAt(i);
/* 727 */         ToolManager.ToolModeBase toolMode = ToolManager.ToolMode.toolModeFor(toolModeVal);
/* 728 */         Class<? extends Tool> value = (Class<? extends Tool>)this.customToolClassMap.valueAt(i);
/* 729 */         map.put(toolMode, value);
/*     */       } 
/* 731 */       toolManager.addCustomizedTool(map);
/*     */     } 
/* 733 */     if (this.customToolParamMap != null) {
/* 734 */       HashMap<ToolManager.ToolModeBase, Object[]> map = (HashMap)new HashMap<>();
/* 735 */       for (int i = 0; i < this.customToolParamMap.size(); i++) {
/* 736 */         int toolModeVal = this.customToolParamMap.keyAt(i);
/* 737 */         ToolManager.ToolModeBase toolMode = ToolManager.ToolMode.toolModeFor(toolModeVal);
/* 738 */         Object[] value = (Object[])this.customToolParamMap.valueAt(i);
/* 739 */         map.put(toolMode, value);
/*     */       } 
/* 741 */       toolManager.addCustomizedToolParams(map);
/*     */     } 
/* 743 */     pdfViewCtrl.setToolManager((PDFViewCtrl.ToolManager)toolManager);
/* 744 */     return toolManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager build(@NonNull PdfViewCtrlTabFragment fragment) {
/* 754 */     ToolManager toolManager = build(fragment.getActivity(), fragment.getPDFViewCtrl());
/* 755 */     toolManager.setPreToolManagerListener((ToolManager.PreToolManagerListener)fragment);
/* 756 */     toolManager.setQuickMenuListener((ToolManager.QuickMenuListener)fragment);
/* 757 */     toolManager.addAnnotationModificationListener((ToolManager.AnnotationModificationListener)fragment);
/* 758 */     toolManager.addPdfDocModificationListener((ToolManager.PdfDocModificationListener)fragment);
/* 759 */     toolManager.setBasicAnnotationListener((ToolManager.BasicAnnotationListener)fragment);
/* 760 */     toolManager.setOnGenericMotionEventListener((ToolManager.OnGenericMotionEventListener)fragment);
/* 761 */     return toolManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 766 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 771 */     dest.writeByte((byte)(this.editInk ? 1 : 0));
/* 772 */     dest.writeByte((byte)(this.addImage ? 1 : 0));
/* 773 */     dest.writeByte((byte)(this.openToolbar ? 1 : 0));
/* 774 */     dest.writeByte((byte)(this.copyAnnot ? 1 : 0));
/* 775 */     dest.writeByte((byte)(this.stylusAsPen ? 1 : 0));
/* 776 */     dest.writeByte((byte)(this.inkSmoothing ? 1 : 0));
/* 777 */     dest.writeByte((byte)(this.autoSelect ? 1 : 0));
/* 778 */     dest.writeByte((byte)(this.buildInPageIndicator ? 1 : 0));
/* 779 */     dest.writeByte((byte)(this.annotPermission ? 1 : 0));
/* 780 */     dest.writeByte((byte)(this.showAuthor ? 1 : 0));
/* 781 */     dest.writeByte((byte)(this.textMarkupAdobeHack ? 1 : 0));
/* 782 */     dest.writeByte((byte)(this.disableQuickMenu ? 1 : 0));
/* 783 */     dest.writeByte((byte)(this.doubleTapToZoom ? 1 : 0));
/* 784 */     dest.writeByte((byte)(this.autoResizeFreeText ? 1 : 0));
/* 785 */     dest.writeByte((byte)(this.realtimeAnnotEdit ? 1 : 0));
/* 786 */     dest.writeByte((byte)(this.editFreeTextOnTap ? 1 : 0));
/* 787 */     dest.writeInt(this.disableToolModesId);
/* 788 */     if (this.modes == null) {
/* 789 */       this.modes = new String[0];
/*     */     }
/* 791 */     this.modeSize = this.modes.length;
/* 792 */     dest.writeInt(this.modeSize);
/* 793 */     dest.writeStringArray(this.modes);
/* 794 */     dest.writeSparseArray(this.customToolClassMap);
/* 795 */     dest.writeSparseArray(this.customToolParamMap);
/* 796 */     dest.writeInt(this.disableEditingAnnotTypesId);
/* 797 */     if (this.annotTypes == null) {
/* 798 */       this.annotTypes = new int[0];
/*     */     }
/* 800 */     this.annotTypeSize = this.annotTypes.length;
/* 801 */     dest.writeInt(this.annotTypeSize);
/* 802 */     dest.writeIntArray(this.annotTypes);
/* 803 */     dest.writeByte((byte)(this.showSavedSignatures ? 1 : 0));
/* 804 */     dest.writeByte((byte)(this.showAnnotIndicator ? 1 : 0));
/* 805 */     dest.writeByte((byte)(this.showSignatureFromImage ? 1 : 0));
/* 806 */     dest.writeByte((byte)(this.annotationLayerEnabled ? 1 : 0));
/* 807 */     dest.writeByte((byte)(this.useDigitalSignature ? 1 : 0));
/* 808 */     dest.writeString(this.digitalSignatureKeystorePath);
/* 809 */     dest.writeString(this.digitalSignatureKeystorePassword);
/* 810 */     if (this.annotToolbarPrecedence == null) {
/* 811 */       this.annotToolbarPrecedence = new String[0];
/*     */     }
/* 813 */     this.annotToolbarPrecedenceSize = this.annotToolbarPrecedence.length;
/* 814 */     dest.writeInt(this.annotToolbarPrecedenceSize);
/* 815 */     dest.writeStringArray(this.annotToolbarPrecedence);
/* 816 */     dest.writeByte((byte)(this.usePressureSensitiveSignatures ? 1 : 0));
/* 817 */     dest.writeString(this.eraserType);
/* 818 */     dest.writeByte((byte)(this.showUndoRedo ? 1 : 0));
/* 819 */     dest.writeInt(this.selectionBoxMargin);
/* 820 */     dest.writeByte((byte)(this.freeTextInlineToggleEnabled ? 1 : 0));
/* 821 */     dest.writeByte((byte)(this.showRichContentOption ? 1 : 0));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\ToolManagerBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */