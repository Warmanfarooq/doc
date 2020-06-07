/*      */ package com.pdftron.pdf.model;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.LayerDrawable;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.RestrictTo;
/*      */ import androidx.core.content.ContextCompat;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.tools.Eraser;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotationPropertyPreviewView;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
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
/*      */ public class AnnotStyle
/*      */ {
/*   41 */   private static String KEY_ANNOT_TYPE = "annotType";
/*   42 */   private static String KEY_THICKNESS = "thickness";
/*   43 */   private static String KEY_STROKE_COLOR = "strokeColor";
/*   44 */   private static String KEY_FILL_COLOR = "fillColor";
/*   45 */   private static String KEY_OPACITY = "opacity";
/*   46 */   private static String KEY_ICON = "icon";
/*   47 */   private static String KEY_TEXT_SIZE = "textSize";
/*   48 */   private static String KEY_TEXT_COLOR = "textColor";
/*   49 */   private static String KEY_FONT_PATH = "fontPath";
/*   50 */   private static String KEY_FONT_NAME = "fontName";
/*   51 */   private static String KEY_PDFTRON_NAME = "pdftronName";
/*   52 */   private static String KEY_OVERLAY_TEXT = "overlayText";
/*   53 */   public static String KEY_PDFTRON_RULER = "pdftronRuler";
/*   54 */   public static String KEY_RULER_BASE = "rulerBase";
/*   55 */   public static String KEY_RULER_BASE_UNIT = "rulerBaseUnit";
/*   56 */   public static String KEY_RULER_TRANSLATE = "rulerTranslate";
/*   57 */   public static String KEY_RULER_TRANSLATE_UNIT = "rulerTranslateUnit";
/*   58 */   private static String KEY_RULER_PRECISION = "rulerPrecision";
/*   59 */   private static String KEY_SNAP = "snap";
/*   60 */   private static String KEY_RICH_CONTENT = "freeTextRC";
/*   61 */   private static String KEY_ERASER_TYPE = "eraserType";
/*   62 */   private static String KEY_INK_ERASER_MODE = "inkEraserMode";
/*   63 */   private static String KEY_DATE_FORMAT = "dateFormat";
/*   64 */   private static String KEY_PRESSURE_SENSITIVE = "pressureSensitive";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_ARROW = 1001;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_SIGNATURE = 1002;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_ERASER = 1003;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_FREE_HIGHLIGHTER = 1004;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_CLOUD = 1005;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_RULER = 1006;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_CALLOUT = 1007;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_PERIMETER_MEASURE = 1008;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_AREA_MEASURE = 1009;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_FREE_TEXT_SPACING = 1010;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_FREE_TEXT_DATE = 1011;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int CUSTOM_ANNOT_TYPE_RECT_AREA_MEASURE = 1012;
/*      */ 
/*      */ 
/*      */   
/*      */   private float mThickness;
/*      */ 
/*      */ 
/*      */   
/*      */   private float mTextSize;
/*      */ 
/*      */ 
/*      */   
/*      */   private int mTextColor;
/*      */ 
/*      */ 
/*      */   
/*  129 */   private String mTextContent = "";
/*  130 */   private String mTextHTMLContent = "";
/*      */   @ColorInt
/*      */   private int mStrokeColor;
/*      */   @ColorInt
/*      */   private int mFillColor;
/*      */   private float mOpacity;
/*      */   private String mOverlayText;
/*  137 */   private double mBorderEffectIntensity = 2.0D;
/*  138 */   private String mIcon = "";
/*  139 */   private String mEraserType = Eraser.EraserType.INK_ERASER.name();
/*  140 */   private String mInkEraserMode = Eraser.InkEraserMode.PIXEL.name();
/*      */   
/*      */   private String mDateFormat;
/*      */   
/*      */   private float mLetterSpacing;
/*      */   private OnAnnotStyleChangeListener mAnnotChangeListener;
/*      */   private boolean mUpdateListener = true;
/*      */   private AnnotationPropertyPreviewView mPreview;
/*  148 */   private FontResource mFont = new FontResource("");
/*      */   
/*  150 */   private int mAnnotType = 28;
/*      */   
/*      */   private boolean mHasAppearance = true;
/*      */   
/*  154 */   private RulerItem mRuler = new RulerItem();
/*      */ 
/*      */   
/*      */   private RulerItem mRulerCopy;
/*      */ 
/*      */   
/*      */   private boolean mSnap;
/*      */ 
/*      */   
/*      */   private boolean mPressureSensitive = false;
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyle() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyle(AnnotStyle other) {
/*  172 */     this.mThickness = other.getThickness();
/*  173 */     this.mTextSize = other.mTextSize;
/*  174 */     this.mStrokeColor = other.getColor();
/*  175 */     this.mFillColor = other.getFillColor();
/*  176 */     this.mIcon = other.getIcon();
/*  177 */     this.mOpacity = other.getOpacity();
/*  178 */     this.mAnnotChangeListener = other.mAnnotChangeListener;
/*  179 */     this.mUpdateListener = other.mUpdateListener;
/*  180 */     this.mPreview = other.mPreview;
/*  181 */     this.mFont = other.getFont();
/*  182 */     this.mAnnotType = other.getAnnotType();
/*  183 */     this.mTextColor = other.mTextColor;
/*  184 */     this.mRuler = other.mRuler;
/*  185 */     this.mOverlayText = other.mOverlayText;
/*  186 */     this.mSnap = other.mSnap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toJSONString() {
/*      */     try {
/*  196 */       JSONObject object = new JSONObject();
/*  197 */       object.put(KEY_ANNOT_TYPE, String.valueOf(this.mAnnotType));
/*  198 */       object.put(KEY_THICKNESS, String.valueOf(this.mThickness));
/*  199 */       object.put(KEY_STROKE_COLOR, this.mStrokeColor);
/*  200 */       object.put(KEY_FILL_COLOR, this.mFillColor);
/*  201 */       object.put(KEY_OPACITY, String.valueOf(this.mOpacity));
/*  202 */       if (hasIcon()) {
/*  203 */         object.put(KEY_ICON, this.mIcon);
/*      */       }
/*  205 */       if (hasTextStyle()) {
/*  206 */         object.put(KEY_TEXT_SIZE, String.valueOf(this.mTextSize));
/*  207 */         object.put(KEY_TEXT_COLOR, this.mTextColor);
/*  208 */         object.put(KEY_RICH_CONTENT, this.mTextHTMLContent);
/*      */       } 
/*  210 */       if (hasFont()) {
/*  211 */         object.put(KEY_FONT_PATH, this.mFont.getFilePath());
/*  212 */         object.put(KEY_FONT_NAME, this.mFont.getFontName());
/*  213 */         object.put(KEY_PDFTRON_NAME, this.mFont.getPDFTronName());
/*      */       } 
/*  215 */       if (isMeasurement()) {
/*  216 */         object.put(KEY_RULER_BASE, String.valueOf(this.mRuler.mRulerBase));
/*  217 */         object.put(KEY_RULER_BASE_UNIT, this.mRuler.mRulerBaseUnit);
/*  218 */         object.put(KEY_RULER_TRANSLATE, String.valueOf(this.mRuler.mRulerTranslate));
/*  219 */         object.put(KEY_RULER_TRANSLATE_UNIT, this.mRuler.mRulerTranslateUnit);
/*  220 */         object.put(KEY_RULER_PRECISION, String.valueOf(this.mRuler.mPrecision));
/*  221 */         object.put(KEY_SNAP, this.mSnap);
/*      */       } 
/*  223 */       if (isRedaction() || isWatermark()) {
/*  224 */         object.put(KEY_OVERLAY_TEXT, this.mOverlayText);
/*      */       }
/*  226 */       if (isEraser()) {
/*  227 */         object.put(KEY_ERASER_TYPE, this.mEraserType);
/*  228 */         object.put(KEY_INK_ERASER_MODE, this.mInkEraserMode);
/*      */       } 
/*  230 */       if (isDateFreeText()) {
/*  231 */         object.put(KEY_DATE_FORMAT, this.mDateFormat);
/*      */       }
/*  233 */       if (hasPressureSensitivity()) {
/*  234 */         object.put(KEY_PRESSURE_SENSITIVE, this.mPressureSensitive);
/*      */       }
/*  236 */       return object.toString();
/*  237 */     } catch (JSONException e) {
/*  238 */       e.printStackTrace();
/*      */       
/*  240 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static AnnotStyle loadJSONString(String jsonStr) {
/*  250 */     return loadJSONString(null, jsonStr, -1);
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
/*      */   public static AnnotStyle loadJSONString(Context context, String jsonStr, int annotType) {
/*  262 */     AnnotStyle annotStyle = new AnnotStyle();
/*  263 */     if (context != null && annotType > -1) {
/*  264 */       annotStyle = ToolStyleConfig.getInstance().getDefaultAnnotStyle(context, annotType);
/*      */     }
/*  266 */     if (!Utils.isNullOrEmpty(jsonStr)) {
/*      */       try {
/*  268 */         JSONObject object = new JSONObject(jsonStr);
/*  269 */         if (object.has(KEY_ANNOT_TYPE)) {
/*  270 */           annotStyle.setAnnotType(Integer.valueOf(object.getString(KEY_ANNOT_TYPE)).intValue());
/*      */         }
/*  272 */         if (object.has(KEY_THICKNESS)) {
/*  273 */           annotStyle.setThickness(Float.valueOf(object.getString(KEY_THICKNESS)).floatValue());
/*      */         }
/*  275 */         if (object.has(KEY_STROKE_COLOR)) {
/*  276 */           annotStyle.setStrokeColor(object.getInt(KEY_STROKE_COLOR));
/*      */         }
/*  278 */         if (object.has(KEY_FILL_COLOR)) {
/*  279 */           annotStyle.setFillColor(object.getInt(KEY_FILL_COLOR));
/*      */         }
/*  281 */         if (object.has(KEY_OPACITY)) {
/*  282 */           annotStyle.setOpacity(Float.valueOf(object.getString(KEY_OPACITY)).floatValue());
/*      */         }
/*  284 */         if (object.has(KEY_TEXT_SIZE)) {
/*  285 */           annotStyle.setTextSize(Float.valueOf(object.getString(KEY_TEXT_SIZE)).floatValue());
/*      */         }
/*  287 */         if (object.has(KEY_TEXT_COLOR)) {
/*  288 */           annotStyle.setTextColor(object.getInt(KEY_TEXT_COLOR));
/*      */         }
/*  290 */         if (object.has(KEY_RICH_CONTENT)) {
/*  291 */           annotStyle.setTextHTMLContent(object.getString(KEY_RICH_CONTENT));
/*      */         }
/*  293 */         if (object.has(KEY_ICON)) {
/*  294 */           String icon = object.getString(KEY_ICON);
/*  295 */           if (!Utils.isNullOrEmpty(icon)) {
/*  296 */             annotStyle.setIcon(icon);
/*      */           }
/*      */         } 
/*  299 */         if (object.has(KEY_FONT_NAME)) {
/*  300 */           String fontName = object.getString(KEY_FONT_NAME);
/*  301 */           if (!Utils.isNullOrEmpty(fontName)) {
/*  302 */             FontResource f = new FontResource(fontName);
/*  303 */             annotStyle.setFont(f);
/*  304 */             if (object.has(KEY_FONT_PATH)) {
/*  305 */               String fontPath = object.getString(KEY_FONT_PATH);
/*  306 */               if (!Utils.isNullOrEmpty(fontPath)) {
/*  307 */                 f.setFilePath(fontPath);
/*      */               }
/*      */             } 
/*  310 */             if (object.has(KEY_PDFTRON_NAME)) {
/*  311 */               String pdftronName = object.getString(KEY_PDFTRON_NAME);
/*  312 */               if (!Utils.isNullOrEmpty(pdftronName)) {
/*  313 */                 f.setPDFTronName(pdftronName);
/*  314 */                 if (!f.hasFontName().booleanValue()) {
/*  315 */                   f.setFontName(pdftronName);
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*  321 */         if (object.has(KEY_RULER_BASE) && object
/*  322 */           .has(KEY_RULER_BASE_UNIT) && object
/*  323 */           .has(KEY_RULER_TRANSLATE) && object
/*  324 */           .has(KEY_RULER_TRANSLATE_UNIT) && object
/*  325 */           .has(KEY_RULER_PRECISION)) {
/*  326 */           annotStyle.setRulerBaseValue(Float.valueOf(object.getString(KEY_RULER_BASE)).floatValue());
/*  327 */           annotStyle.setRulerBaseUnit(object.getString(KEY_RULER_BASE_UNIT));
/*  328 */           annotStyle.setRulerTranslateValue(Float.valueOf(object.getString(KEY_RULER_TRANSLATE)).floatValue());
/*  329 */           annotStyle.setRulerTranslateUnit(object.getString(KEY_RULER_TRANSLATE_UNIT));
/*  330 */           annotStyle.setRulerPrecision(Integer.valueOf(object.getString(KEY_RULER_PRECISION)).intValue());
/*      */         } 
/*  332 */         if (object.has(KEY_SNAP)) {
/*  333 */           annotStyle.setSnap(object.getBoolean(KEY_SNAP));
/*      */         }
/*  335 */         if (object.has(KEY_OVERLAY_TEXT)) {
/*  336 */           annotStyle.setOverlayText(object.getString(KEY_OVERLAY_TEXT));
/*      */         }
/*  338 */         if (object.has(KEY_ERASER_TYPE)) {
/*  339 */           annotStyle.setEraserType(Eraser.EraserType.valueOf(object.getString(KEY_ERASER_TYPE)));
/*      */         }
/*  341 */         if (object.has(KEY_INK_ERASER_MODE)) {
/*  342 */           annotStyle.setInkEraserMode(Eraser.InkEraserMode.valueOf(object.getString(KEY_INK_ERASER_MODE)));
/*      */         }
/*  344 */         if (object.has(KEY_DATE_FORMAT)) {
/*  345 */           annotStyle.setDateFormat(object.getString(KEY_DATE_FORMAT));
/*      */         }
/*  347 */         if (object.has(KEY_PRESSURE_SENSITIVE)) {
/*  348 */           annotStyle.setPressureSensitivity(object.getBoolean(KEY_PRESSURE_SENSITIVE));
/*      */         }
/*  350 */       } catch (JSONException e) {
/*  351 */         e.printStackTrace();
/*  352 */       } catch (Exception e) {
/*  353 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "Failed converting annotStype from json to object");
/*      */       } 
/*      */     }
/*      */     
/*  357 */     return annotStyle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotType(int annotType) {
/*  366 */     this.mAnnotType = annotType;
/*  367 */     if (this.mPreview != null) {
/*  368 */       this.mPreview.setAnnotType(this.mAnnotType);
/*      */     }
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
/*      */   public void setStyle(@ColorInt int strokeColor, @ColorInt int fillColor, float thickness, float opacity) {
/*  381 */     this.mStrokeColor = strokeColor;
/*  382 */     this.mFillColor = fillColor;
/*  383 */     this.mThickness = thickness;
/*  384 */     this.mOpacity = opacity;
/*  385 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStyle(AnnotStyle other) {
/*  394 */     setStrokeColor(other.getColor());
/*  395 */     setFillColor(other.getFillColor());
/*  396 */     setThickness(other.getThickness());
/*  397 */     setOpacity(other.getOpacity());
/*  398 */     setIcon(other.getIcon());
/*  399 */     setFont(other.getFont());
/*  400 */     setTextSize(other.getTextSize());
/*  401 */     setTextColor(other.getTextColor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrokeColor(@ColorInt int strokeColor) {
/*  412 */     updateStrokeColorListener(strokeColor);
/*  413 */     this.mStrokeColor = strokeColor;
/*  414 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFillColor(@ColorInt int fillColor) {
/*  425 */     updateFillColorListener(fillColor);
/*  426 */     this.mFillColor = fillColor;
/*  427 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThickness(float thickness) {
/*  437 */     setThickness(thickness, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThickness(float thickness, boolean done) {
/*  448 */     updateThicknessListener(thickness, done);
/*  449 */     this.mThickness = thickness;
/*  450 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextSize(float textSize) {
/*  460 */     setTextSize(textSize, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextSize(float textSize, boolean done) {
/*  471 */     updateTextSizeListener(textSize, done);
/*  472 */     this.mTextSize = textSize;
/*  473 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextColor(@ColorInt int textColor) {
/*  484 */     updateTextColorListener(textColor);
/*  485 */     this.mTextColor = textColor;
/*  486 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTextContent(String content) {
/*  495 */     if (null != content) {
/*  496 */       this.mTextContent = content;
/*      */     }
/*      */   }
/*      */   
/*      */   public void setTextHTMLContent(String html) {
/*  501 */     if (null != html) {
/*  502 */       this.mTextHTMLContent = html;
/*      */     }
/*  504 */     if (Utils.isNullOrEmpty(html)) {
/*  505 */       updateRichContentEnabledListener(false);
/*      */     } else {
/*  507 */       updateRichContentEnabledListener(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOpacity(float opacity) {
/*  518 */     setOpacity(opacity, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOpacity(float opacity, boolean done) {
/*  529 */     updateOpacityListener(opacity, done);
/*  530 */     this.mOpacity = opacity;
/*  531 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getOverlayText() {
/*  541 */     return this.mOverlayText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverlayText(String text) {
/*  551 */     updateOverlayTextListener(text);
/*  552 */     this.mOverlayText = text;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSnap(boolean snap) {
/*  561 */     if (isMeasurement()) {
/*  562 */       updateSnapListener(snap);
/*      */     }
/*  564 */     this.mSnap = snap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEraserType(@NonNull Eraser.EraserType type) {
/*  573 */     this.mEraserType = type.name();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInkEraserMode(@NonNull Eraser.InkEraserMode mode) {
/*  582 */     this.mInkEraserMode = mode.name();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPressureSensitivity(boolean hasPressure) {
/*  591 */     this.mPressureSensitive = hasPressure;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderEffectIntensity(double intensity) {
/*  600 */     this.mBorderEffectIntensity = intensity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderEffectIntensity() {
/*  609 */     return this.mBorderEffectIntensity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRulerItem(RulerItem ruler) {
/*  618 */     this.mRuler = ruler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRulerBaseValue(float value) {
/*  627 */     updateRulerBaseValueListener(value);
/*  628 */     this.mRuler.mRulerBase = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRulerTranslateValue(float value) {
/*  637 */     updateRulerTranslateValueListener(value);
/*  638 */     this.mRuler.mRulerTranslate = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRulerBaseUnit(String unit) {
/*  647 */     updateRulerBaseUnitListener(unit);
/*  648 */     this.mRuler.mRulerBaseUnit = unit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRulerTranslateUnit(String unit) {
/*  657 */     updateRulerTranslateUnitListener(unit);
/*  658 */     this.mRuler.mRulerTranslateUnit = unit;
/*      */   }
/*      */   
/*      */   public void setRulerPrecision(int precision) {
/*  662 */     updateRulerPrecisionListener(precision);
/*  663 */     this.mRuler.mPrecision = precision;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public RulerItem getRulerItem() {
/*  672 */     return this.mRuler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRulerBaseValue() {
/*  681 */     return this.mRuler.mRulerBase;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRulerTranslateValue() {
/*  690 */     return this.mRuler.mRulerTranslate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRulerBaseUnit() {
/*  700 */     if (this.mRuler.mRulerBaseUnit.equals("inch")) {
/*  701 */       return "in";
/*      */     }
/*  703 */     return this.mRuler.mRulerBaseUnit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getRulerTranslateUnit() {
/*  713 */     if (this.mRuler.mRulerTranslateUnit.equals("inch"))
/*  714 */       return "in"; 
/*  715 */     if (this.mRuler.mRulerTranslateUnit.equals("yard")) {
/*  716 */       return "yd";
/*      */     }
/*  718 */     return this.mRuler.mRulerTranslateUnit;
/*      */   }
/*      */   
/*      */   public int getPrecision() {
/*  722 */     return this.mRuler.mPrecision;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHasAppearance(boolean hasAppearance) {
/*  729 */     this.mHasAppearance = hasAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasAppearance() {
/*  736 */     return this.mHasAppearance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIcon(String icon) {
/*  746 */     if (hasIcon() && !Utils.isNullOrEmpty(icon)) {
/*  747 */       updateIconListener(icon);
/*  748 */       this.mIcon = icon;
/*  749 */       updatePreviewStyle();
/*      */     } 
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
/*      */   public void setFont(FontResource font) {
/*  780 */     updateFontListener(font);
/*  781 */     this.mFont = font;
/*  782 */     updatePreviewStyle();
/*      */   }
/*      */   
/*      */   public void setDateFormat(String format) {
/*  786 */     updateDateFormatListener(format);
/*  787 */     this.mDateFormat = format;
/*      */   }
/*      */   
/*      */   public String getDateFormat() {
/*  791 */     return this.mDateFormat;
/*      */   }
/*      */   
/*      */   public void setLetterSpacing(float spacing) {
/*  795 */     this.mLetterSpacing = spacing;
/*      */   }
/*      */   
/*      */   public float getLetterSpacing() {
/*  799 */     return this.mLetterSpacing;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void disableUpdateListener(boolean disable) {
/*  808 */     this.mUpdateListener = !disable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getColor() {
/*  817 */     return this.mStrokeColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getThickness() {
/*  826 */     return this.mThickness;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getTextSize() {
/*  835 */     return this.mTextSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTextContent() {
/*  844 */     return this.mTextContent;
/*      */   }
/*      */   
/*      */   public String getTextHTMLContent() {
/*  848 */     return this.mTextHTMLContent;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTextColor() {
/*  857 */     return this.mTextColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getFillColor() {
/*  866 */     return this.mFillColor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getOpacity() {
/*  875 */     return this.mOpacity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getIcon() {
/*  884 */     return this.mIcon;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontResource getFont() {
/*  893 */     return this.mFont;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getSnap() {
/*  902 */     return this.mSnap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Eraser.EraserType getEraserType() {
/*  911 */     return Eraser.EraserType.valueOf(this.mEraserType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Eraser.InkEraserMode getInkEraserMode() {
/*  920 */     return Eraser.InkEraserMode.valueOf(this.mInkEraserMode);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getPressureSensitive() {
/*  929 */     return this.mPressureSensitive;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasPressureSensitivity() {
/*  938 */     switch (this.mAnnotType) {
/*      */       case 14:
/*  940 */         return true;
/*      */     } 
/*  942 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPDFTronFontName() {
/*  953 */     if (this.mFont != null) {
/*  954 */       return this.mFont.getPDFTronName();
/*      */     }
/*  956 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getAnnotType() {
/*  963 */     return this.mAnnotType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasThickness() {
/*  972 */     if (isRCFreeText()) {
/*  973 */       return false;
/*      */     }
/*  975 */     switch (this.mAnnotType) {
/*      */       case 0:
/*      */       case 8:
/*      */       case 17:
/*      */       case 19:
/*      */       case 23:
/*      */       case 25:
/*  982 */         return false;
/*      */     } 
/*  984 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasFillColor() {
/*  994 */     if (isRCFreeText()) {
/*  995 */       return false;
/*      */     }
/*  997 */     switch (this.mAnnotType) {
/*      */       case 2:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 25:
/*      */       case 1005:
/*      */       case 1007:
/*      */       case 1010:
/*      */       case 1011:
/* 1007 */         return true;
/*      */     } 
/* 1009 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasColor() {
/* 1019 */     if (isRCFreeText()) {
/* 1020 */       return false;
/*      */     }
/* 1022 */     switch (this.mAnnotType) {
/*      */       case 19:
/*      */       case 23:
/*      */       case 1003:
/* 1026 */         return false;
/*      */     } 
/* 1028 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasOpacity() {
/* 1038 */     if (isRCFreeText()) {
/* 1039 */       return false;
/*      */     }
/* 1041 */     switch (this.mAnnotType) {
/*      */       case 1:
/*      */       case 17:
/*      */       case 19:
/*      */       case 1002:
/*      */       case 1003:
/* 1047 */         return false;
/*      */     } 
/* 1049 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasIcon() {
/* 1059 */     switch (this.mAnnotType) {
/*      */       case 0:
/*      */       case 17:
/* 1062 */         return true;
/*      */     } 
/* 1064 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isStickyNote() {
/* 1074 */     switch (this.mAnnotType) {
/*      */       case 0:
/* 1076 */         return true;
/*      */     } 
/* 1078 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSound() {
/* 1088 */     switch (this.mAnnotType) {
/*      */       case 17:
/* 1090 */         return true;
/*      */     } 
/* 1092 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFreeText() {
/* 1102 */     switch (this.mAnnotType) {
/*      */       case 2:
/*      */       case 1007:
/* 1105 */         return true;
/*      */     } 
/* 1107 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCallout() {
/* 1117 */     switch (this.mAnnotType) {
/*      */       case 1007:
/* 1119 */         return true;
/*      */     } 
/* 1121 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRCFreeText() {
/* 1131 */     if (!isFreeText() || isCallout()) {
/* 1132 */       return false;
/*      */     }
/* 1134 */     return !Utils.isNullOrEmpty(this.mTextHTMLContent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpacingFreeText() {
/* 1143 */     return (this.mAnnotType == 1010);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDateFreeText() {
/* 1154 */     return (this.mAnnotType == 1011);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWidget() {
/* 1163 */     switch (this.mAnnotType) {
/*      */       case 19:
/* 1165 */         return true;
/*      */     } 
/* 1167 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasFont() {
/* 1177 */     if (isRCFreeText()) {
/* 1178 */       return false;
/*      */     }
/* 1180 */     switch (this.mAnnotType) {
/*      */       case 2:
/*      */       case 19:
/*      */       case 1007:
/*      */       case 1010:
/*      */       case 1011:
/* 1186 */         return true;
/*      */     } 
/* 1188 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasTextStyle() {
/* 1198 */     switch (this.mAnnotType) {
/*      */       case 2:
/*      */       case 19:
/*      */       case 23:
/*      */       case 1007:
/*      */       case 1010:
/*      */       case 1011:
/* 1205 */         return true;
/*      */     } 
/* 1207 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isLine() {
/* 1217 */     switch (this.mAnnotType) {
/*      */       case 3:
/* 1219 */         return true;
/*      */     } 
/* 1221 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRuler() {
/* 1231 */     switch (this.mAnnotType) {
/*      */       case 1006:
/* 1233 */         return true;
/*      */     } 
/* 1235 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMeasurement() {
/* 1245 */     switch (this.mAnnotType) {
/*      */       case 1006:
/*      */       case 1008:
/*      */       case 1009:
/*      */       case 1012:
/* 1250 */         return true;
/*      */     } 
/* 1252 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRedaction() {
/* 1262 */     switch (this.mAnnotType) {
/*      */       case 25:
/* 1264 */         return true;
/*      */     } 
/* 1266 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWatermark() {
/* 1276 */     switch (this.mAnnotType) {
/*      */       case 23:
/* 1278 */         return true;
/*      */     } 
/* 1280 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFreeTextGroup() {
/* 1291 */     return isFreeTextGroup(this.mAnnotType);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFreeTextGroup(int annotType) {
/* 1301 */     switch (annotType) {
/*      */       case 2:
/*      */       case 1007:
/*      */       case 1010:
/*      */       case 1011:
/* 1306 */         return true;
/*      */     } 
/* 1308 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEraser() {
/* 1318 */     return (this.mAnnotType == 1003);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFontPath() {
/* 1328 */     if (this.mFont != null) {
/* 1329 */       return this.mFont.getFilePath();
/*      */     }
/*      */     
/* 1332 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Drawable getIconDrawable(Context context) {
/* 1342 */     return getIconDrawable(context, this.mIcon, this.mStrokeColor, this.mOpacity);
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
/*      */   public static Drawable getIconDrawable(Context context, String iconOutline, String iconFill, int color, float opacity) {
/* 1355 */     int alpha = (int)(255.0F * opacity);
/* 1356 */     int iconOutlineID = context.getResources().getIdentifier(iconOutline, "drawable", context.getPackageName());
/* 1357 */     int iconFillID = context.getResources().getIdentifier(iconFill, "drawable", context.getPackageName());
/* 1358 */     if (iconOutlineID != 0 && iconFillID != 0) {
/*      */       try {
/* 1360 */         Drawable[] layers = new Drawable[2];
/* 1361 */         layers[0] = ContextCompat.getDrawable(context, iconFillID);
/* 1362 */         layers[0].mutate();
/* 1363 */         layers[0].setAlpha(alpha);
/* 1364 */         layers[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
/* 1365 */         layers[1] = ContextCompat.getDrawable(context, iconOutlineID);
/* 1366 */         layers[1].mutate();
/* 1367 */         layers[1].setAlpha(alpha);
/* 1368 */         LayerDrawable layerDrawable = new LayerDrawable(layers);
/* 1369 */         return (Drawable)layerDrawable;
/* 1370 */       } catch (Exception e) {
/* 1371 */         AnalyticsHandlerAdapter.getInstance().sendException(e, iconFillID + ", " + iconOutlineID);
/*      */       } 
/*      */     }
/* 1374 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Drawable getIconDrawable(Context context, String icon, int color, float opacity) {
/*      */     String iconOutline;
/*      */     String iconFill;
/* 1387 */     if (icon.equals("sound")) {
/* 1388 */       iconOutline = "annotation_icon_sound_outline";
/* 1389 */       iconFill = "annotation_icon_sound_fill";
/*      */     } else {
/* 1391 */       iconOutline = "annotation_note_icon_" + icon.toLowerCase() + "_outline";
/* 1392 */       iconFill = "annotation_note_icon_" + icon.toLowerCase() + "_fill";
/*      */     } 
/* 1394 */     return getIconDrawable(context, iconOutline, iconFill, color, opacity);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1399 */     return "AnnotStyle{mThickness=" + this.mThickness + ", mStrokeColor=" + this.mStrokeColor + ", mFillColor=" + this.mFillColor + ", mOpacity=" + this.mOpacity + ", mIcon='" + this.mIcon + '\'' + ", mFont=" + this.mFont
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1405 */       .toString() + ", mRuler=" + this.mRuler
/* 1406 */       .toString() + '}';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotAppearanceChangeListener(OnAnnotStyleChangeListener listener) {
/* 1416 */     this.mAnnotChangeListener = listener;
/*      */   }
/*      */   
/*      */   private void updateThicknessListener(float thickness, boolean done) {
/* 1420 */     updateThicknessListener(thickness, (this.mThickness != thickness), done);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateThicknessListener(float thickness, boolean update, boolean done) {
/* 1430 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && (update || done)) {
/* 1431 */       this.mAnnotChangeListener.onChangeAnnotThickness(thickness, done);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateTextSizeListener(float textSize, boolean done) {
/* 1436 */     updateTextSizeListener(textSize, (this.mTextSize != textSize), done);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTextSizeListener(float textSize, boolean update, boolean done) {
/* 1446 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && (update || done)) {
/* 1447 */       this.mAnnotChangeListener.onChangeAnnotTextSize(textSize, done);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateTextColorListener(@ColorInt int textColor) {
/* 1452 */     updateTextColorListener(textColor, (this.mTextColor != textColor));
/*      */   }
/*      */   
/*      */   private void updateTextColorListener(@ColorInt int textColor, boolean update) {
/* 1456 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1457 */       this.mAnnotChangeListener.onChangeAnnotTextColor(textColor);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void bindPreview(AnnotationPropertyPreviewView previewView) {
/* 1467 */     this.mPreview = previewView;
/* 1468 */     updatePreviewStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationPropertyPreviewView getBindedPreview() {
/* 1477 */     return this.mPreview;
/*      */   }
/*      */   
/*      */   private void updatePreviewStyle() {
/* 1481 */     if (this.mPreview != null) {
/* 1482 */       this.mPreview.updateFillPreview(this);
/*      */     }
/*      */   }
/*      */   
/*      */   public void updateAllListeners() {
/* 1487 */     updateStrokeColorListener(this.mStrokeColor, true);
/* 1488 */     updateFillColorListener(this.mFillColor, true);
/* 1489 */     updateThicknessListener(this.mThickness, true, true);
/* 1490 */     updateOpacityListener(this.mOpacity, true, true);
/* 1491 */     if (isStickyNote() && !Utils.isNullOrEmpty(this.mIcon)) {
/* 1492 */       updateIconListener(this.mIcon, true);
/*      */     }
/* 1494 */     if (hasTextStyle()) {
/* 1495 */       updateTextColorListener(this.mTextColor, true);
/* 1496 */       updateTextSizeListener(this.mTextSize, true);
/*      */     } 
/* 1498 */     if (hasFont() && !Utils.isNullOrEmpty(this.mFont.getPDFTronName())) {
/* 1499 */       updateFontListener(this.mFont, true);
/*      */     }
/* 1501 */     if (isMeasurement()) {
/* 1502 */       updateRulerBaseValueListener(this.mRuler.mRulerBase, true);
/* 1503 */       updateRulerBaseUnitListener(this.mRuler.mRulerBaseUnit, true);
/* 1504 */       updateRulerTranslateValueListener(this.mRuler.mRulerTranslate, true);
/* 1505 */       updateRulerTranslateUnitListener(this.mRuler.mRulerTranslateUnit, true);
/* 1506 */       updateRulerPrecisionListener(this.mRuler.mPrecision, true);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateStrokeColorListener(@ColorInt int strokeColor) {
/* 1511 */     updateStrokeColorListener(strokeColor, (strokeColor != this.mStrokeColor));
/*      */   }
/*      */   
/*      */   private void updateStrokeColorListener(@ColorInt int strokeColor, boolean update) {
/* 1515 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1516 */       this.mAnnotChangeListener.onChangeAnnotStrokeColor(strokeColor);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateFillColorListener(@ColorInt int color) {
/* 1521 */     updateFillColorListener(color, (color != this.mFillColor));
/*      */   }
/*      */   
/*      */   private void updateFillColorListener(@ColorInt int color, boolean update) {
/* 1525 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1526 */       this.mAnnotChangeListener.onChangeAnnotFillColor(color);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateOpacityListener(float opacity, boolean done) {
/* 1531 */     updateOpacityListener(opacity, (opacity != this.mOpacity), done);
/*      */   }
/*      */   
/*      */   private void updateOpacityListener(float opacity, boolean update, boolean done) {
/* 1535 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && (update || done)) {
/* 1536 */       this.mAnnotChangeListener.onChangeAnnotOpacity(opacity, done);
/* 1537 */       if (isStickyNote()) {
/* 1538 */         updateIconListener(this.mIcon, update);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateSnapListener(boolean snap) {
/* 1544 */     if (this.mUpdateListener && this.mAnnotChangeListener != null) {
/* 1545 */       this.mAnnotChangeListener.onChangeSnapping(snap);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateRichContentEnabledListener(boolean enabled) {
/* 1550 */     if (this.mUpdateListener && this.mAnnotChangeListener != null) {
/* 1551 */       this.mAnnotChangeListener.onChangeRichContentEnabled(enabled);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateOverlayTextListener(String overlayText) {
/* 1556 */     if (this.mUpdateListener && this.mAnnotChangeListener != null) {
/* 1557 */       this.mAnnotChangeListener.onChangeOverlayText(overlayText);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateIconListener(String icon) {
/* 1562 */     updateIconListener(icon, !icon.equals(this.mIcon));
/*      */   }
/*      */   
/*      */   private void updateIconListener(String icon, boolean update) {
/* 1566 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1567 */       this.mAnnotChangeListener.onChangeAnnotIcon(icon);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateFontListener(FontResource font) {
/* 1572 */     updateFontListener(font, !font.equals(this.mFont));
/*      */   }
/*      */   
/*      */   private void updateFontListener(FontResource font, boolean update) {
/* 1576 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1577 */       this.mAnnotChangeListener.onChangeAnnotFont(font);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateDateFormatListener(String dateFormat) {
/* 1582 */     if (this.mUpdateListener && this.mAnnotChangeListener != null) {
/* 1583 */       this.mAnnotChangeListener.onChangeDateFormat(dateFormat);
/*      */     }
/*      */   }
/*      */   
/*      */   private void updateRulerBaseValueListener(float val) {
/* 1588 */     updateRulerBaseValueListener(val, (val != this.mRuler.mRulerBase));
/*      */   }
/*      */   
/*      */   private void updateRulerBaseValueListener(float val, boolean update) {
/* 1592 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1593 */       if (this.mRulerCopy == null) {
/* 1594 */         this.mRulerCopy = new RulerItem(this.mRuler);
/*      */       }
/* 1596 */       this.mRulerCopy.mRulerBase = val;
/* 1597 */       this.mAnnotChangeListener.onChangeRulerProperty(this.mRulerCopy);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRulerBaseUnitListener(String val) {
/* 1602 */     updateRulerBaseUnitListener(val, !val.equals(this.mRuler.mRulerBaseUnit));
/*      */   }
/*      */   
/*      */   private void updateRulerBaseUnitListener(String val, boolean update) {
/* 1606 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1607 */       if (this.mRulerCopy == null) {
/* 1608 */         this.mRulerCopy = new RulerItem(this.mRuler);
/*      */       }
/* 1610 */       this.mRulerCopy.mRulerBaseUnit = val;
/* 1611 */       this.mAnnotChangeListener.onChangeRulerProperty(this.mRulerCopy);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRulerTranslateValueListener(float val) {
/* 1616 */     updateRulerTranslateValueListener(val, (val != this.mRuler.mRulerTranslate));
/*      */   }
/*      */   
/*      */   private void updateRulerTranslateValueListener(float val, boolean update) {
/* 1620 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1621 */       if (this.mRulerCopy == null) {
/* 1622 */         this.mRulerCopy = new RulerItem(this.mRuler);
/*      */       }
/* 1624 */       this.mRulerCopy.mRulerTranslate = val;
/* 1625 */       this.mAnnotChangeListener.onChangeRulerProperty(this.mRulerCopy);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRulerTranslateUnitListener(String val) {
/* 1630 */     updateRulerTranslateUnitListener(val, !val.equals(this.mRuler.mRulerTranslateUnit));
/*      */   }
/*      */   
/*      */   private void updateRulerTranslateUnitListener(String val, boolean update) {
/* 1634 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1635 */       if (this.mRulerCopy == null) {
/* 1636 */         this.mRulerCopy = new RulerItem(this.mRuler);
/*      */       }
/* 1638 */       this.mRulerCopy.mRulerTranslateUnit = val;
/* 1639 */       this.mAnnotChangeListener.onChangeRulerProperty(this.mRulerCopy);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateRulerPrecisionListener(int val) {
/* 1644 */     updateRulerPrecisionListener(val, (val != this.mRuler.mPrecision));
/*      */   }
/*      */   
/*      */   private void updateRulerPrecisionListener(int val, boolean update) {
/* 1648 */     if (this.mUpdateListener && this.mAnnotChangeListener != null && update) {
/* 1649 */       if (this.mRulerCopy == null) {
/* 1650 */         this.mRulerCopy = new RulerItem(this.mRuler);
/*      */       }
/* 1652 */       this.mRulerCopy.mPrecision = val;
/* 1653 */       this.mAnnotChangeListener.onChangeRulerProperty(this.mRulerCopy);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 1659 */     if (obj != null && obj instanceof AnnotStyle) {
/* 1660 */       AnnotStyle other = (AnnotStyle)obj;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1665 */       boolean styleEquals = (other.getThickness() == getThickness() && other.getAnnotType() == getAnnotType() && other.getOpacity() == getOpacity() && other.getColor() == getColor() && other.getFillColor() == getFillColor());
/*      */       
/* 1667 */       boolean fontEquals = other.getFont().equals(getFont());
/* 1668 */       boolean iconEquals = other.getIcon().equals(getIcon());
/* 1669 */       boolean annotTypeEquals = (getAnnotType() == other.getAnnotType());
/*      */       
/* 1671 */       boolean textStyleEquals = (other.getTextSize() == getTextSize() && other.getTextColor() == getTextColor());
/*      */       
/* 1673 */       if (!annotTypeEquals) {
/* 1674 */         return false;
/*      */       }
/*      */       
/* 1677 */       if (isStickyNote()) {
/* 1678 */         return (iconEquals && other.getOpacity() == getOpacity() && other
/* 1679 */           .getColor() == getColor());
/*      */       }
/*      */       
/* 1682 */       if (hasTextStyle() && hasFont())
/* 1683 */         return (fontEquals && textStyleEquals && styleEquals); 
/* 1684 */       if (hasTextStyle())
/* 1685 */         return (textStyleEquals && styleEquals); 
/* 1686 */       if (hasFont()) {
/* 1687 */         return (fontEquals && styleEquals);
/*      */       }
/*      */       
/* 1690 */       if (isMeasurement()) {
/* 1691 */         return (styleEquals && other
/* 1692 */           .getRulerItem().equals(getRulerItem()));
/*      */       }
/*      */       
/* 1695 */       return styleEquals;
/*      */     } 
/* 1697 */     return super.equals(obj);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1703 */     int result = (this.mThickness != 0.0F) ? Float.floatToIntBits(this.mThickness) : 0;
/* 1704 */     result = 31 * result + ((this.mTextSize != 0.0F) ? Float.floatToIntBits(this.mTextSize) : 0);
/* 1705 */     result = 31 * result + this.mTextColor;
/* 1706 */     result = 31 * result + ((this.mTextContent != null) ? this.mTextContent.hashCode() : 0);
/* 1707 */     result = 31 * result + this.mStrokeColor;
/* 1708 */     result = 31 * result + this.mFillColor;
/* 1709 */     result = 31 * result + ((this.mOpacity != 0.0F) ? Float.floatToIntBits(this.mOpacity) : 0);
/* 1710 */     result = 31 * result + ((this.mOverlayText != null) ? this.mOverlayText.hashCode() : 0);
/* 1711 */     result = 31 * result + ((this.mIcon != null) ? this.mIcon.hashCode() : 0);
/* 1712 */     result = 31 * result + ((this.mFont != null) ? this.mFont.hashCode() : 0);
/* 1713 */     result = 31 * result + this.mAnnotType;
/* 1714 */     result = 31 * result + ((this.mRuler != null) ? this.mRuler.hashCode() : 0);
/* 1715 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @RestrictTo({RestrictTo.Scope.LIBRARY})
/*      */   public float getMaxInternalThickness() {
/* 1723 */     switch (this.mAnnotType) {
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/* 1728 */         return 40.0F;
/*      */       case 1006:
/* 1730 */         return 10.0F;
/*      */     } 
/* 1732 */     return 70.0F;
/*      */   }
/*      */   
/*      */   public static interface AnnotStyleHolder {
/*      */     AnnotStyle getAnnotStyle();
/*      */     
/*      */     AnnotationPropertyPreviewView getAnnotPreview();
/*      */     
/*      */     void setAnnotPreviewVisibility(int param1Int);
/*      */   }
/*      */   
/*      */   public static interface OnAnnotStyleChangeListener {
/*      */     void onChangeAnnotThickness(float param1Float, boolean param1Boolean);
/*      */     
/*      */     void onChangeAnnotTextSize(float param1Float, boolean param1Boolean);
/*      */     
/*      */     void onChangeAnnotTextColor(@ColorInt int param1Int);
/*      */     
/*      */     void onChangeAnnotOpacity(float param1Float, boolean param1Boolean);
/*      */     
/*      */     void onChangeAnnotStrokeColor(@ColorInt int param1Int);
/*      */     
/*      */     void onChangeAnnotFillColor(@ColorInt int param1Int);
/*      */     
/*      */     void onChangeAnnotIcon(String param1String);
/*      */     
/*      */     void onChangeAnnotFont(FontResource param1FontResource);
/*      */     
/*      */     void onChangeRulerProperty(RulerItem param1RulerItem);
/*      */     
/*      */     void onChangeOverlayText(String param1String);
/*      */     
/*      */     void onChangeSnapping(boolean param1Boolean);
/*      */     
/*      */     void onChangeRichContentEnabled(boolean param1Boolean);
/*      */     
/*      */     void onChangeDateFormat(String param1String);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\AnnotStyle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */