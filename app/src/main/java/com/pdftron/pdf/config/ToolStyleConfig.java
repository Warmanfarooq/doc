/*      */ package com.pdftron.pdf.config;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.content.SharedPreferences;
/*      */ import android.content.res.TypedArray;
/*      */ import android.util.SparseIntArray;
/*      */ import androidx.annotation.ArrayRes;
/*      */ import androidx.annotation.AttrRes;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.StyleRes;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.FontResource;
/*      */ import com.pdftron.pdf.tools.Eraser;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.tools.Tool;
/*      */ import com.pdftron.pdf.utils.MeasureUtils;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import java.util.ArrayList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ToolStyleConfig
/*      */ {
/*      */   private static ToolStyleConfig _INSTANCE;
/*      */   private static final String PREF_ANNOTATION_PROPERTY_LINE = "annotation_property_shape";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_ARROW = "annotation_property_arrow";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER = "annotation_property_ruler";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_POLYLINE = "annotation_property_polyline";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RECTANGLE = "annotation_property_rectangle";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_OVAL = "annotation_property_oval";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_POLYGON = "annotation_property_polygon";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_CLOUD = "annotation_property_cloud";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_HIGHLIGHT = "annotation_property_highlight";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_UNDERLINE = "annotation_property_text_markup";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_LINK = "annotation_property_link";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_STRIKEOUT = "annotation_property_strikeout";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_SQUIGGLY = "annotation_property_squiggly";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FREETEXT = "annotation_property_freetext";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_CALLOUT = "annotation_property_callout";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FREETEXT_DATE = "annotation_property_freetext_date";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FREETEXT_SPACING = "annotation_property_freetext_spacing";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FREEHAND = "annotation_property_freehand";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FREE_HIGHLIGHTER = "annotation_property_free_highlighter";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_NOTE = "annotation_property_note";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_ERASER = "annotation_property_eraser";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_SIGNATURE = "annotation_property_signature";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_SOUND = "annotation_property_sound";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_REDACTION = "annotation_property_redaction";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_PERIMETER_MEASURE = "annotation_property_perimeter_measure";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_AREA_MEASURE = "annotation_property_area_measure";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RECT_AREA_MEASURE = "annotation_property_rect_area_measure";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_WIDGET = "annotation_property_widget";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FILL_COLORS = "_fill_colors";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_COLOR = "_color";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_TEXT_COLOR = "_text_color";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_TEXT_SIZE = "_text_size";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FILL_COLOR = "_fill_color";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_OPACITY = "_opacity";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_THICKNESS = "_thickness";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_ICON = "_icon";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_FONT = "_font";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_ERASER_TYPE = "_eraser_type";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_INK_ERASER_MODE_TYPE = "_ink_eraser_mode";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_PRESSURE_SENSITIVITY = "_pressure_sensitive";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_DATE = "_date";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_CUSTOM = "_custom";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER_BASE_UNIT = "_ruler_base_unit";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER_BASE_VALUE = "_ruler_base_value";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER_TRANSLATE_UNIT = "_ruler_translate_unit";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER_TRANSLATE_VALUE = "_ruler_translate_value";
/*      */   private static final String PREF_ANNOTATION_PROPERTY_RULER_PRECISION = "_ruler_precision";
/*      */   private SparseIntArray mAnnotStyleMap;
/*      */   private SparseIntArray mAnnotPresetMap;
/*      */   
/*      */   public static ToolStyleConfig getInstance() {
/*   97 */     if (null == _INSTANCE) {
/*   98 */       _INSTANCE = new ToolStyleConfig();
/*      */     }
/*  100 */     return _INSTANCE;
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
/*      */   public ToolStyleConfig() {
/*  117 */     this.mAnnotStyleMap = new SparseIntArray();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDefaultStyleMap(int annotType, @StyleRes int styleRes) {
/*  127 */     this.mAnnotStyleMap.put(annotType, styleRes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addAnnotPresetMap(int annotType, @AttrRes int attrRes) {
/*  137 */     if (this.mAnnotPresetMap == null) {
/*  138 */       this.mAnnotPresetMap = new SparseIntArray();
/*      */     }
/*  140 */     this.mAnnotPresetMap.put(annotType, attrRes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @StyleRes
/*      */   public int getDefaultStyle(int annotType) {
/*  151 */     if (this.mAnnotStyleMap.indexOfKey(annotType) >= 0) {
/*  152 */       return this.mAnnotStyleMap.get(annotType);
/*      */     }
/*  154 */     switch (annotType) {
/*      */       case 19:
/*  156 */         return R.style.WidgetPreset1;
/*      */       case 8:
/*  158 */         return R.style.HighlightPresetStyle1;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  162 */         return R.style.TextMarkupStyle1;
/*      */       case 2:
/*      */       case 1010:
/*  165 */         return R.style.FreeTextPresetStyle1;
/*      */       case 1007:
/*  167 */         return R.style.CalloutPresetStyle1;
/*      */       case 1011:
/*  169 */         return R.style.FreeTextDatePresetStyle1;
/*      */       case 0:
/*  171 */         return R.style.AnnotPresetStyle1;
/*      */       case 1002:
/*  173 */         return R.style.SignaturePresetStyle1;
/*      */       case 14:
/*  175 */         return R.style.AnnotPresetStyle4;
/*      */       case 1003:
/*  177 */         return R.style.EraserStyle1;
/*      */       case 1004:
/*  179 */         return R.style.FreeHighlighterStyle4;
/*      */       case 1006:
/*  181 */         return R.style.RulerStyle1;
/*      */     } 
/*  183 */     return R.style.AnnotPresetStyle4;
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
/*      */   @AttrRes
/*      */   public int getDefaultAttr(int annotType) {
/*  196 */     switch (annotType) {
/*      */       case 19:
/*  198 */         return R.attr.widget_default_style;
/*      */       case 8:
/*  200 */         return R.attr.highlight_default_style;
/*      */       case 9:
/*  202 */         return R.attr.underline_default_style;
/*      */       case 10:
/*  204 */         return R.attr.squiggly_default_style;
/*      */       case 11:
/*  206 */         return R.attr.strikeout_default_style;
/*      */       case 1004:
/*  208 */         return R.attr.free_highlighter_default_style;
/*      */       case 2:
/*      */       case 1007:
/*      */       case 1010:
/*  212 */         return R.attr.free_text_default_style;
/*      */       case 1011:
/*  214 */         return R.attr.free_date_text_default_style;
/*      */       case 0:
/*  216 */         return R.attr.sticky_note_default_style;
/*      */       case 1002:
/*  218 */         return R.attr.signature_default_style;
/*      */       case 1:
/*  220 */         return R.attr.link_default_style;
/*      */       case 14:
/*  222 */         return R.attr.freehand_default_style;
/*      */       case 3:
/*      */       case 1001:
/*  225 */         return R.attr.line_default_style;
/*      */       case 1006:
/*  227 */         return R.attr.ruler_default_style;
/*      */       case 7:
/*      */       case 1008:
/*  230 */         return R.attr.polyline_default_style;
/*      */       case 4:
/*      */       case 1012:
/*  233 */         return R.attr.rect_default_style;
/*      */       case 5:
/*  235 */         return R.attr.oval_default_style;
/*      */       case 6:
/*      */       case 1009:
/*  238 */         return R.attr.polygon_default_style;
/*      */       case 1005:
/*  240 */         return R.attr.cloud_default_style;
/*      */     } 
/*  242 */     return R.attr.other_default_style;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @AttrRes
/*      */   public int getPresetsAttr(int annotType) {
/*  254 */     if (this.mAnnotPresetMap != null && this.mAnnotPresetMap.indexOfKey(annotType) >= 0) {
/*  255 */       return this.mAnnotPresetMap.get(annotType);
/*      */     }
/*  257 */     switch (annotType) {
/*      */       case 19:
/*  259 */         return R.attr.widget_presets;
/*      */       case 8:
/*  261 */         return R.attr.highlight_presets;
/*      */       case 9:
/*  263 */         return R.attr.underline_presets;
/*      */       case 10:
/*  265 */         return R.attr.squiggly_presets;
/*      */       case 11:
/*  267 */         return R.attr.strikeout_presets;
/*      */       case 1004:
/*  269 */         return R.attr.free_highlighter_presets;
/*      */       case 2:
/*      */       case 1010:
/*  272 */         return R.attr.free_text_presets;
/*      */       case 1007:
/*  274 */         return R.attr.callout_presets;
/*      */       case 1011:
/*  276 */         return R.attr.free_date_text_presets;
/*      */       case 0:
/*  278 */         return R.attr.sticky_note_presets;
/*      */       case 1002:
/*  280 */         return R.attr.signature_presets;
/*      */       case 1:
/*  282 */         return R.attr.link_presets;
/*      */       case 14:
/*  284 */         return R.attr.freehand_presets;
/*      */       case 3:
/*      */       case 1001:
/*  287 */         return R.attr.line_presets;
/*      */       case 1006:
/*  289 */         return R.attr.ruler_presets;
/*      */       case 7:
/*      */       case 1008:
/*  292 */         return R.attr.polyline_presets;
/*      */       case 4:
/*      */       case 1012:
/*  295 */         return R.attr.rect_presets;
/*      */       case 5:
/*  297 */         return R.attr.oval_presets;
/*      */       case 6:
/*      */       case 1009:
/*  300 */         return R.attr.polygon_presets;
/*      */       case 1005:
/*  302 */         return R.attr.cloud_presets;
/*      */     } 
/*  304 */     return R.attr.other_presets;
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
/*      */   @ArrayRes
/*      */   public int getDefaultPresetsArrayRes(int annotType) {
/*  317 */     switch (annotType) {
/*      */       case 19:
/*  319 */         return R.array.widget_presets;
/*      */       case 8:
/*  321 */         return R.array.highlight_presets;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*  325 */         return R.array.text_markup_presets;
/*      */       case 0:
/*  327 */         return R.array.color_only_presets;
/*      */       case 2:
/*      */       case 1010:
/*  330 */         return R.array.free_text_presets;
/*      */       case 1007:
/*  332 */         return R.array.callout_presets;
/*      */       case 1011:
/*  334 */         return R.array.free_text_presets;
/*      */       case 1002:
/*  336 */         return R.array.signature_presets;
/*      */       case 1003:
/*  338 */         return R.array.eraser_presets;
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 1005:
/*  343 */         return R.array.fill_only_presets;
/*      */       case 14:
/*  345 */         return R.array.freehand_presets;
/*      */       case 1004:
/*  347 */         return R.array.freehand_highlighter_presets;
/*      */       case 1006:
/*  349 */         return R.array.ruler_presets;
/*      */     } 
/*  351 */     return R.array.stroke_only_presets;
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
/*      */   @ColorInt
/*      */   public int getDefaultColor(Context context, int annotType, String extraTag) {
/*  365 */     int index = 0;
/*  366 */     if (extraTag.endsWith("1") || Utils.isNullOrEmpty(extraTag))
/*  367 */       return getDefaultColor(context, getDefaultAttr(annotType), getDefaultStyle(annotType)); 
/*  368 */     if (extraTag.endsWith("2")) {
/*  369 */       index = 1;
/*  370 */     } else if (extraTag.endsWith("3")) {
/*  371 */       index = 2;
/*  372 */     } else if (extraTag.endsWith("4")) {
/*  373 */       index = 3;
/*  374 */     } else if (extraTag.endsWith("5")) {
/*  375 */       index = 4;
/*      */     } 
/*  377 */     return getPresetColor(context, index, getPresetsAttr(annotType), getDefaultPresetsArrayRes(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public int getDefaultColor(@NonNull Context context, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
/*  388 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defStyleAttr, defStyleRes);
/*  389 */     int color = a.getColor(R.styleable.ToolStyle_annot_color, -16777216);
/*  390 */     a.recycle();
/*  391 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ArrayList<String> getIconsList(Context context) {
/*  401 */     return getIconsList(context, R.attr.sticky_note_icons, R.array.stickynote_icons);
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
/*      */   public ArrayList<String> getIconsList(@NonNull Context context, @AttrRes int defAttrRes, @ArrayRes int defArrayRes) {
/*  414 */     TypedArray typedArray = context.obtainStyledAttributes(new int[] { defAttrRes });
/*  415 */     int iconsRes = typedArray.getResourceId(0, defArrayRes);
/*  416 */     typedArray.recycle();
/*      */     
/*  418 */     TypedArray iconsArray = context.getResources().obtainTypedArray(iconsRes);
/*  419 */     ArrayList<String> icons = new ArrayList<>();
/*  420 */     for (int i = 0; i < iconsArray.length(); i++) {
/*  421 */       String icon = iconsArray.getString(i);
/*  422 */       if (!Utils.isNullOrEmpty(icon)) {
/*  423 */         icons.add(icon);
/*      */       }
/*      */     } 
/*  426 */     iconsArray.recycle();
/*  427 */     return icons;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public int getDefaultColor(Context context, int annotType) {
/*  439 */     return getDefaultColor(context, annotType, "");
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
/*      */   @Deprecated
/*      */   @ColorInt
/*      */   public int getDefaultTextColor(Context context) {
/*  453 */     return getDefaultTextColor(context, getDefaultAttr(2), getDefaultStyle(2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public int getDefaultTextColor(@NonNull Context context, int annotType) {
/*  465 */     return getDefaultTextColor(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
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
/*      */   @ColorInt
/*      */   public int getDefaultTextColor(@NonNull Context context, @AttrRes int attrRes, @StyleRes int defStyleRes) {
/*  478 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, attrRes, defStyleRes);
/*  479 */     int color = a.getColor(R.styleable.ToolStyle_annot_text_color, -16777216);
/*  480 */     a.recycle();
/*  481 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultDateFormat(Context context, int annotType) {
/*  492 */     return getDefaultDateFormat(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultDateFormat(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  502 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  503 */     String dateFormat = a.getString(R.styleable.ToolStyle_annot_date_format);
/*  504 */     a.recycle();
/*  505 */     if (null == dateFormat) {
/*  506 */       dateFormat = context.getResources().getString(R.string.style_picker_date_format1);
/*      */     }
/*  508 */     return dateFormat;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultThickness(Context context, int annotType) {
/*  519 */     return getDefaultThickness(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultThickness(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  529 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  530 */     float thickness = a.getFloat(R.styleable.ToolStyle_annot_thickness, 1.0F);
/*  531 */     a.recycle();
/*  532 */     return thickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultRulerBaseValue(Context context, int annotType) {
/*  543 */     return getDefaultRulerBaseValue(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultRulerBaseValue(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  553 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  554 */     float value = a.getFloat(R.styleable.ToolStyle_ruler_base_value, 1.0F);
/*  555 */     a.recycle();
/*  556 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRulerBaseUnit(Context context, int annotType) {
/*  567 */     return getDefaultRulerBaseUnit(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRulerBaseUnit(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  577 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  578 */     String unit = a.getString(R.styleable.ToolStyle_ruler_base_unit);
/*  579 */     if (unit == null) {
/*  580 */       unit = "in";
/*      */     }
/*  582 */     a.recycle();
/*  583 */     return unit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultRulerTranslateValue(Context context, int annotType) {
/*  594 */     return getDefaultRulerTranslateValue(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultRulerTranslateValue(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  604 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  605 */     float value = a.getFloat(R.styleable.ToolStyle_ruler_translate_value, 1.0F);
/*  606 */     a.recycle();
/*  607 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRulerTranslateUnit(Context context, int annotType) {
/*  618 */     return getDefaultRulerTranslateUnit(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultRulerTranslateUnit(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  628 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  629 */     String unit = a.getString(R.styleable.ToolStyle_ruler_translate_unit);
/*  630 */     if (unit == null) {
/*  631 */       unit = "in";
/*      */     }
/*  633 */     a.recycle();
/*  634 */     return unit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRulerPrecision(Context context, int annotType) {
/*  645 */     return getDefaultRulerPrecision(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultRulerPrecision(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*  655 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*  656 */     int value = a.getInt(R.styleable.ToolStyle_ruler_precision, MeasureUtils.PRECISION_DEFAULT);
/*  657 */     a.recycle();
/*  658 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultTextSize(@NonNull Context context, int annotType) {
/*  669 */     return getDefaultTextSize(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultTextSize(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float fontSize;
/*  680 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  683 */       fontSize = a.getFloat(R.styleable.ToolStyle_annot_font_size, 16.0F);
/*      */     } finally {
/*  685 */       a.recycle();
/*      */     } 
/*  687 */     return fontSize;
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
/*      */   @ColorInt
/*      */   public int getDefaultFillColor(Context context, int annotType) {
/*  700 */     return getDefaultFillColor(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @ColorInt
/*      */   public int getDefaultFillColor(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     int color;
/*  711 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  714 */       color = a.getColor(R.styleable.ToolStyle_annot_fill_color, 0);
/*      */     } finally {
/*  716 */       a.recycle();
/*      */     } 
/*  718 */     return color;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMaxThickness(Context context, int annotType) {
/*  729 */     return getDefaultMaxThickness(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMaxThickness(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float thickness;
/*  739 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  742 */       thickness = a.getFloat(R.styleable.ToolStyle_annot_thickness_max, 1.0F);
/*      */     } finally {
/*  744 */       a.recycle();
/*      */     } 
/*  746 */     return thickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMinThickness(Context context, int annotType) {
/*  757 */     return getDefaultMinThickness(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMinThickness(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float thickness;
/*  767 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  770 */       thickness = a.getFloat(R.styleable.ToolStyle_annot_thickness_min, 0.0F);
/*      */     } finally {
/*  772 */       a.recycle();
/*      */     } 
/*  774 */     return thickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMinTextSize(Context context) {
/*  784 */     return getDefaultMinTextSize(context, getDefaultAttr(2), getDefaultStyle(2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMinTextSize(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float thickness;
/*  794 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  797 */       thickness = a.getFloat(R.styleable.ToolStyle_annot_text_size_min, 1.0F);
/*      */     } finally {
/*  799 */       a.recycle();
/*      */     } 
/*  801 */     return thickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMaxTextSize(Context context) {
/*  811 */     return getDefaultMaxTextSize(context, getDefaultAttr(2), getDefaultStyle(2));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultMaxTextSize(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float thickness;
/*  821 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  824 */       thickness = a.getFloat(R.styleable.ToolStyle_annot_text_size_max, 72.0F);
/*      */     } finally {
/*  826 */       a.recycle();
/*      */     } 
/*  828 */     return thickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultFont(Context context, int annotType) {
/*  839 */     return getDefaultFont(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultFont(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     String font;
/*  849 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  852 */       font = a.getString(R.styleable.ToolStyle_annot_font);
/*      */     } finally {
/*  854 */       a.recycle();
/*      */     } 
/*  856 */     if (null == font) {
/*  857 */       return "";
/*      */     }
/*  859 */     return font;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultIcon(Context context, int annotType) {
/*  870 */     return getDefaultIcon(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDefaultIcon(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     String result;
/*  880 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  883 */       result = a.getString(R.styleable.ToolStyle_annot_icon);
/*      */     } finally {
/*  885 */       a.recycle();
/*      */     } 
/*  887 */     if (null == result) {
/*  888 */       return "";
/*      */     }
/*  890 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultOpacity(Context context, int annotType) {
/*  901 */     return getDefaultOpacity(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultOpacity(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float result;
/*  911 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  914 */       result = a.getFloat(R.styleable.ToolStyle_annot_opacity, 1.0F);
/*      */     } finally {
/*  916 */       a.recycle();
/*      */     } 
/*  918 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDefaultPressureSensitivity(@NonNull Context context, int annotType) {
/*  928 */     return getDefaultPressureSensitivity(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDefaultPressureSensitivity(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     boolean result;
/*  938 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  941 */       result = a.getBoolean(R.styleable.ToolStyle_ink_pressure, false);
/*      */     } finally {
/*  943 */       a.recycle();
/*      */     } 
/*  945 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultThicknessRange(Context context, int annotType) {
/*  956 */     return getDefaultThicknessRange(context, getDefaultAttr(annotType), getDefaultStyle(annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDefaultThicknessRange(@NonNull Context context, @AttrRes int defAttrRes, @StyleRes int defStyleRes) {
/*      */     float thicknessRange;
/*  967 */     TypedArray a = context.obtainStyledAttributes(null, R.styleable.ToolStyle, defAttrRes, defStyleRes);
/*      */     
/*      */     try {
/*  970 */       float thicknessMin = a.getFloat(R.styleable.ToolStyle_annot_thickness_min, 0.0F);
/*  971 */       float thicknessMax = a.getFloat(R.styleable.ToolStyle_annot_thickness_max, 1.0F);
/*  972 */       thicknessRange = thicknessMax - thicknessMin;
/*      */     } finally {
/*  974 */       a.recycle();
/*      */     } 
/*  976 */     return thicknessRange;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyle getDefaultAnnotStyle(Context context, int annotType) {
/*  987 */     AnnotStyle annotStyle = new AnnotStyle();
/*  988 */     annotStyle.setAnnotType(annotType);
/*  989 */     annotStyle.setStrokeColor(getDefaultColor(context, annotType));
/*  990 */     annotStyle.setFillColor(getDefaultFillColor(context, annotType));
/*  991 */     annotStyle.setOpacity(getDefaultOpacity(context, annotType));
/*  992 */     if (annotStyle.hasTextStyle()) {
/*  993 */       annotStyle.setTextSize(getDefaultTextSize(context, annotType));
/*  994 */       annotStyle.setTextColor(getDefaultTextColor(context, annotType));
/*      */     } 
/*      */     
/*  997 */     if (annotStyle.hasFont()) {
/*  998 */       annotStyle.setFont(new FontResource(getDefaultFont(context, annotType)));
/*      */     }
/*      */     
/* 1001 */     annotStyle.setThickness(getDefaultThickness(context, annotType));
/*      */     
/* 1003 */     if (annotStyle.isStickyNote()) {
/* 1004 */       annotStyle.setIcon(getDefaultIcon(context, annotType));
/* 1005 */     } else if (annotStyle.isSound()) {
/* 1006 */       annotStyle.setIcon("sound");
/* 1007 */     } else if (annotStyle.isMeasurement()) {
/* 1008 */       annotStyle.setRulerBaseValue(getDefaultRulerBaseValue(context, annotType));
/* 1009 */       annotStyle.setRulerBaseUnit(getDefaultRulerBaseUnit(context, annotType));
/* 1010 */       annotStyle.setRulerTranslateValue(getDefaultRulerTranslateValue(context, annotType));
/* 1011 */       annotStyle.setRulerTranslateUnit(getDefaultRulerTranslateUnit(context, annotType));
/* 1012 */       annotStyle.setRulerPrecision(getDefaultRulerPrecision(context, annotType));
/* 1013 */     } else if (annotStyle.isDateFreeText()) {
/* 1014 */       annotStyle.setDateFormat(getDefaultDateFormat(context, annotType));
/*      */     } 
/* 1016 */     return annotStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyle getCustomAnnotStyle(@NonNull Context context, int annotType, String extraTag) {
/* 1027 */     AnnotStyle annotStyle = new AnnotStyle();
/* 1028 */     annotStyle.setAnnotType(annotType);
/*      */     
/* 1030 */     annotStyle.setThickness(getCustomThickness(context, annotType, extraTag));
/* 1031 */     annotStyle.setOpacity(getCustomOpacity(context, annotType, extraTag));
/* 1032 */     annotStyle.setStrokeColor(getCustomColor(context, annotType, extraTag));
/* 1033 */     annotStyle.setFillColor(getCustomFillColor(context, annotType, extraTag));
/* 1034 */     annotStyle.setTextColor(getCustomTextColor(context, annotType, extraTag));
/* 1035 */     annotStyle.setTextSize(getCustomTextSize(context, annotType, extraTag));
/* 1036 */     annotStyle.setIcon(getCustomIconName(context, annotType, extraTag));
/* 1037 */     annotStyle.setRulerBaseValue(getCustomRulerBaseValue(context, annotType, extraTag));
/* 1038 */     annotStyle.setRulerBaseUnit(getCustomRulerBaseUnit(context, annotType, extraTag));
/* 1039 */     annotStyle.setRulerTranslateValue(getCustomRulerTranslateValue(context, annotType, extraTag));
/* 1040 */     annotStyle.setRulerTranslateUnit(getCustomRulerTranslateUnit(context, annotType, extraTag));
/* 1041 */     annotStyle.setRulerPrecision(getCustomRulerPrecision(context, annotType, extraTag));
/* 1042 */     annotStyle.setEraserType(getCustomEraserType(context, annotType, extraTag));
/* 1043 */     annotStyle.setInkEraserMode(getCustomInkEraserMode(context, annotType, extraTag));
/* 1044 */     annotStyle.setDateFormat(getCustomDateFormat(context, annotType, extraTag));
/* 1045 */     annotStyle.setPressureSensitivity(getCustomPressureSensitive(context, annotType, extraTag));
/*      */     
/* 1047 */     String fontPDFTronName = getCustomFontName(context, annotType, extraTag);
/* 1048 */     FontResource mFont = new FontResource(fontPDFTronName);
/* 1049 */     annotStyle.setFont(mFont);
/*      */     
/* 1051 */     return annotStyle;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getThicknessKey(int annotType, String extraTag) {
/* 1056 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_thickness");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOpacityKey(int annotType, String extraTag) {
/* 1062 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_opacity");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getColorKey(int annotType, String extraTag) {
/* 1068 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_color");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextColorKey(int annotType, String extraTag) {
/* 1074 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_text_color");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextSizeKey(int annotType, String extraTag) {
/* 1080 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_text_size");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDateFormatKey(int annotType, String extraTag) {
/* 1085 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_date");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFillColorKey(int annotType, String extraTag) {
/* 1091 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_fill_color");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIconKey(int annotType, String extraTag) {
/* 1097 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_icon");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFontKey(int annotType, String extraTag) {
/* 1103 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_font");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRulerBaseUnitKey(int annotType, String extraTag) {
/* 1108 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ruler_base_unit");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRulerTranslateUnitKey(int annotType, String extraTag) {
/* 1113 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ruler_translate_unit");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRulerBaseValueKey(int annotType, String extraTag) {
/* 1118 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ruler_base_value");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRulerTranslateValueKey(int annotType, String extraTag) {
/* 1123 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ruler_translate_value");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getRulerPrecisionKey(int annotType, String extraTag) {
/* 1128 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ruler_precision");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getEraserTypeKey(int annotType, String extraTag) {
/* 1133 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_eraser_type");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getInkEraserModeKey(int annotType, String extraTag) {
/* 1138 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_ink_eraser_mode");
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPressureSensitiveKey(int annotType, String extraTag) {
/* 1143 */     return getAnnotationPropertySettingsKey(annotType, extraTag, "_custom_pressure_sensitive");
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
/*      */   public void saveAnnotStyle(@NonNull Context context, AnnotStyle annotStyle, String extraTag) {
/* 1155 */     SharedPreferences settings = Tool.getToolPreferences(context);
/* 1156 */     SharedPreferences.Editor editor = settings.edit();
/* 1157 */     int annotType = annotStyle.getAnnotType();
/* 1158 */     editor.putFloat(getThicknessKey(annotType, extraTag), annotStyle.getThickness());
/* 1159 */     editor.putFloat(getOpacityKey(annotType, extraTag), annotStyle.getOpacity());
/* 1160 */     editor.putInt(getColorKey(annotType, extraTag), annotStyle.getColor());
/* 1161 */     editor.putInt(getTextColorKey(annotType, extraTag), annotStyle.getTextColor());
/* 1162 */     editor.putFloat(getTextSizeKey(annotType, extraTag), annotStyle.getTextSize());
/* 1163 */     editor.putInt(getFillColorKey(annotType, extraTag), annotStyle.getFillColor());
/* 1164 */     editor.putString(getIconKey(annotType, extraTag), annotStyle.getIcon());
/* 1165 */     editor.putString(getRulerBaseUnitKey(annotType, extraTag), annotStyle.getRulerBaseUnit());
/* 1166 */     editor.putFloat(getRulerBaseValueKey(annotType, extraTag), annotStyle.getRulerBaseValue());
/* 1167 */     editor.putString(getRulerTranslateUnitKey(annotType, extraTag), annotStyle.getRulerTranslateUnit());
/* 1168 */     editor.putFloat(getRulerTranslateValueKey(annotType, extraTag), annotStyle.getRulerTranslateValue());
/* 1169 */     editor.putInt(getRulerPrecisionKey(annotType, extraTag), annotStyle.getPrecision());
/* 1170 */     editor.putBoolean(getPressureSensitiveKey(annotType, extraTag), annotStyle.getPressureSensitive());
/* 1171 */     String font = (annotStyle.getFont() != null) ? annotStyle.getFont().getPDFTronName() : "";
/* 1172 */     editor.putString(getFontKey(annotType, extraTag), font);
/* 1173 */     editor.apply();
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
/*      */   public int getCustomColor(@NonNull Context context, int annotType, String extraTag) {
/* 1185 */     return Tool.getToolPreferences(context).getInt(
/* 1186 */         getColorKey(annotType, extraTag), 
/* 1187 */         getDefaultColor(context, annotType, extraTag));
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
/*      */   public int getCustomTextColor(@NonNull Context context, int annotType, String extraTag) {
/* 1199 */     return Tool.getToolPreferences(context).getInt(
/* 1200 */         getTextColorKey(annotType, extraTag), 
/* 1201 */         getDefaultTextColor(context, annotType));
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
/*      */   public float getCustomTextSize(@NonNull Context context, int annotType, String extraTag) {
/* 1213 */     return Tool.getToolPreferences(context).getFloat(
/* 1214 */         getTextSizeKey(annotType, extraTag), 
/* 1215 */         getDefaultTextSize(context, annotType));
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
/*      */   public String getCustomDateFormat(@NonNull Context context, int annotType, String extraTag) {
/* 1227 */     return Tool.getToolPreferences(context).getString(
/* 1228 */         getDateFormatKey(annotType, extraTag), 
/* 1229 */         getDefaultDateFormat(context, annotType));
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
/*      */   public int getCustomFillColor(@NonNull Context context, int annotType, String extraTag) {
/* 1241 */     return Tool.getToolPreferences(context).getInt(
/* 1242 */         getFillColorKey(annotType, extraTag), 
/* 1243 */         getDefaultFillColor(context, annotType));
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
/*      */   public float getCustomThickness(@NonNull Context context, int annotType, String extraTag) {
/* 1255 */     return Tool.getToolPreferences(context).getFloat(
/* 1256 */         getThicknessKey(annotType, extraTag), 
/* 1257 */         getDefaultThickness(context, annotType));
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
/*      */   public float getCustomOpacity(@NonNull Context context, int annotType, String extraTag) {
/* 1269 */     return Tool.getToolPreferences(context).getFloat(
/* 1270 */         getOpacityKey(annotType, extraTag), 
/* 1271 */         getDefaultOpacity(context, annotType));
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
/*      */   public String getCustomFontName(@NonNull Context context, int annotType, String extraTag) {
/* 1283 */     return Tool.getToolPreferences(context).getString(
/* 1284 */         getFontKey(annotType, extraTag), 
/* 1285 */         getDefaultFont(context, annotType));
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
/*      */   public String getCustomIconName(@NonNull Context context, int annotType, String extraTag) {
/* 1297 */     return Tool.getToolPreferences(context).getString(
/* 1298 */         getIconKey(annotType, extraTag), 
/* 1299 */         getDefaultIcon(context, annotType));
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
/*      */   public float getCustomRulerBaseValue(@NonNull Context context, int annotType, String extraTag) {
/* 1311 */     return Tool.getToolPreferences(context).getFloat(
/* 1312 */         getRulerBaseValueKey(annotType, extraTag), 
/* 1313 */         getDefaultRulerBaseValue(context, annotType));
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
/*      */   public String getCustomRulerBaseUnit(@NonNull Context context, int annotType, String extraTag) {
/* 1325 */     return Tool.getToolPreferences(context).getString(
/* 1326 */         getRulerBaseUnitKey(annotType, extraTag), 
/* 1327 */         getDefaultRulerBaseUnit(context, annotType));
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
/*      */   public float getCustomRulerTranslateValue(@NonNull Context context, int annotType, String extraTag) {
/* 1339 */     return Tool.getToolPreferences(context).getFloat(
/* 1340 */         getRulerTranslateValueKey(annotType, extraTag), 
/* 1341 */         getDefaultRulerTranslateValue(context, annotType));
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
/*      */   public String getCustomRulerTranslateUnit(@NonNull Context context, int annotType, String extraTag) {
/* 1353 */     return Tool.getToolPreferences(context).getString(
/* 1354 */         getRulerTranslateUnitKey(annotType, extraTag), 
/* 1355 */         getDefaultRulerTranslateUnit(context, annotType));
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
/*      */   public int getCustomRulerPrecision(@NonNull Context context, int annotType, String extraTag) {
/* 1367 */     return Tool.getToolPreferences(context).getInt(
/* 1368 */         getRulerPrecisionKey(annotType, extraTag), 
/* 1369 */         getDefaultRulerPrecision(context, annotType));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Eraser.EraserType getCustomEraserType(@NonNull Context context, int annotType, String extraTag) {
/* 1380 */     String eraserTypeStr = Tool.getToolPreferences(context).getString(
/* 1381 */         getEraserTypeKey(annotType, extraTag), Eraser.EraserType.INK_ERASER
/* 1382 */         .name());
/* 1383 */     return Eraser.EraserType.valueOf(eraserTypeStr);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Eraser.InkEraserMode getCustomInkEraserMode(@NonNull Context context, int annotType, String extraTag) {
/* 1394 */     String inkEraserMode = Tool.getToolPreferences(context).getString(
/* 1395 */         getInkEraserModeKey(annotType, extraTag), Eraser.InkEraserMode.PIXEL
/* 1396 */         .name());
/* 1397 */     return Eraser.InkEraserMode.valueOf(inkEraserMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCustomPressureSensitive(@NonNull Context context, int annotType, String extraTag) {
/* 1408 */     return Tool.getToolPreferences(context).getBoolean(
/* 1409 */         getPressureSensitiveKey(annotType, extraTag), false);
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
/*      */   public AnnotStyle getAnnotPresetStyle(Context context, int annotType, int index) {
/* 1423 */     String presetJSON = PdfViewCtrlSettingsManager.getAnnotStylePreset(context, annotType, index);
/* 1424 */     if (!Utils.isNullOrEmpty(presetJSON)) {
/* 1425 */       return AnnotStyle.loadJSONString(context, presetJSON, annotType);
/*      */     }
/*      */     
/* 1428 */     return getDefaultAnnotPresetStyle(context, annotType, index, getPresetsAttr(annotType), getDefaultPresetsArrayRes(annotType));
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
/*      */   public AnnotStyle getDefaultAnnotPresetStyle(@NonNull Context context, int annotType, int index, @AttrRes int attrRes, @ArrayRes int arrayRes) {
/* 1445 */     TypedArray typedArray = context.obtainStyledAttributes(new int[] { attrRes });
/* 1446 */     int presetArrayRes = typedArray.getResourceId(0, arrayRes);
/* 1447 */     typedArray.recycle();
/*      */     
/* 1449 */     TypedArray presetsArray = context.getResources().obtainTypedArray(presetArrayRes);
/* 1450 */     int styleResId = presetsArray.getResourceId(index, getDefaultStyle(annotType));
/*      */     
/* 1452 */     presetsArray.recycle();
/*      */     
/* 1454 */     AnnotStyle annotStyle = new AnnotStyle();
/* 1455 */     annotStyle.setAnnotType(annotType);
/* 1456 */     annotStyle.setStrokeColor(getDefaultColor(context, 0, styleResId));
/* 1457 */     annotStyle.setFillColor(getDefaultFillColor(context, 0, styleResId));
/* 1458 */     annotStyle.setOpacity(getDefaultOpacity(context, 0, styleResId));
/* 1459 */     if (annotStyle.hasTextStyle()) {
/* 1460 */       annotStyle.setTextSize(getDefaultTextSize(context, 0, styleResId));
/* 1461 */       annotStyle.setTextColor(getDefaultTextColor(context, 0, styleResId));
/*      */     } 
/* 1463 */     if (annotStyle.hasFont()) {
/* 1464 */       annotStyle.setFont(new FontResource(getDefaultFont(context, 0, styleResId)));
/*      */     }
/* 1466 */     annotStyle.setThickness(getDefaultThickness(context, 0, styleResId));
/* 1467 */     if (annotStyle.isStickyNote()) {
/* 1468 */       annotStyle.setIcon(getDefaultIcon(context, 0, styleResId));
/* 1469 */     } else if (annotStyle.isSound()) {
/* 1470 */       annotStyle.setIcon("sound");
/* 1471 */     } else if (annotStyle.isMeasurement()) {
/* 1472 */       annotStyle.setRulerBaseValue(getDefaultRulerBaseValue(context, 0, styleResId));
/* 1473 */       annotStyle.setRulerBaseUnit(getDefaultRulerBaseUnit(context, 0, styleResId));
/* 1474 */       annotStyle.setRulerTranslateValue(getDefaultRulerTranslateValue(context, 0, styleResId));
/* 1475 */       annotStyle.setRulerTranslateUnit(getDefaultRulerTranslateUnit(context, 0, styleResId));
/* 1476 */       annotStyle.setRulerPrecision(getDefaultRulerPrecision(context, 0, styleResId));
/* 1477 */     } else if (annotStyle.isDateFreeText()) {
/* 1478 */       annotStyle.setDateFormat(getDefaultDateFormat(context, 0, styleResId));
/*      */     } 
/* 1480 */     annotStyle.setPressureSensitivity(getDefaultPressureSensitivity(context, 0, styleResId));
/* 1481 */     return annotStyle;
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
/*      */   public int getPresetColor(@NonNull Context context, int index, @AttrRes int attrRes, @ArrayRes int arrayRes, @StyleRes int defaultStyleRes) {
/* 1496 */     TypedArray typedArray = context.obtainStyledAttributes(new int[] { attrRes });
/* 1497 */     int presetArrayRes = typedArray.getResourceId(0, arrayRes);
/* 1498 */     typedArray.recycle();
/*      */     
/* 1500 */     TypedArray presetsArray = context.getResources().obtainTypedArray(presetArrayRes);
/* 1501 */     int styleResId = presetsArray.getResourceId(index, defaultStyleRes);
/*      */     
/* 1503 */     presetsArray.recycle();
/*      */     
/* 1505 */     return getDefaultColor(context, 0, styleResId);
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
/*      */   public String getAnnotationPropertySettingsKey(int annotType, String extraTag, String mode) {
/* 1518 */     switch (annotType)
/*      */     { case 8:
/* 1520 */         annotProperty = "annotation_property_highlight";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1604 */         return annotProperty + extraTag + mode;case 9: annotProperty = "annotation_property_text_markup"; return annotProperty + extraTag + mode;case 1: annotProperty = "annotation_property_link"; return annotProperty + extraTag + mode;case 11: annotProperty = "annotation_property_strikeout"; return annotProperty + extraTag + mode;case 10: annotProperty = "annotation_property_squiggly"; return annotProperty + extraTag + mode;case 2: annotProperty = "annotation_property_freetext"; return annotProperty + extraTag + mode;case 1007: annotProperty = "annotation_property_callout"; return annotProperty + extraTag + mode;case 1011: annotProperty = "annotation_property_freetext_date"; return annotProperty + extraTag + mode;case 1010: annotProperty = "annotation_property_freetext_spacing"; return annotProperty + extraTag + mode;case 14: annotProperty = "annotation_property_freehand"; return annotProperty + extraTag + mode;case 1001: annotProperty = "annotation_property_arrow"; return annotProperty + extraTag + mode;case 1006: annotProperty = "annotation_property_ruler"; return annotProperty + extraTag + mode;case 1008: annotProperty = "annotation_property_perimeter_measure"; return annotProperty + extraTag + mode;case 1009: annotProperty = "annotation_property_area_measure"; return annotProperty + extraTag + mode;case 1012: annotProperty = "annotation_property_rect_area_measure"; return annotProperty + extraTag + mode;case 7: annotProperty = "annotation_property_polyline"; return annotProperty + extraTag + mode;case 4: annotProperty = "annotation_property_rectangle"; return annotProperty + extraTag + mode;case 5: annotProperty = "annotation_property_oval"; return annotProperty + extraTag + mode;case 6: annotProperty = "annotation_property_polygon"; return annotProperty + extraTag + mode;case 1005: annotProperty = "annotation_property_cloud"; return annotProperty + extraTag + mode;case 1002: annotProperty = "annotation_property_signature"; return annotProperty + extraTag + mode;case 0: annotProperty = "annotation_property_note"; return annotProperty + extraTag + mode;case 17: annotProperty = "annotation_property_sound"; return annotProperty + extraTag + mode;case 1003: annotProperty = "annotation_property_eraser"; return annotProperty + extraTag + mode;case 1004: annotProperty = "annotation_property_free_highlighter"; return annotProperty + extraTag + mode;case 25: annotProperty = "annotation_property_redaction"; return annotProperty + extraTag + mode;case 19: annotProperty = "annotation_property_widget"; return annotProperty + extraTag + mode; }  String annotProperty = "annotation_property_shape"; return annotProperty + extraTag + mode;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\config\ToolStyleConfig.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */