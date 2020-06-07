/*      */ package com.pdftron.pdf.controls;
/*      */ 
/*      */ import android.annotation.SuppressLint;
/*      */ import android.content.Context;
/*      */ import android.content.res.TypedArray;
/*      */ import android.graphics.PorterDuff;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.GradientDrawable;
/*      */ import android.os.AsyncTask;
/*      */ import android.text.Editable;
/*      */ import android.util.AttributeSet;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.AdapterView;
/*      */ import android.widget.ArrayAdapter;
/*      */ import android.widget.EditText;
/*      */ import android.widget.ImageView;
/*      */ import android.widget.LinearLayout;
/*      */ import android.widget.ListAdapter;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.SeekBar;
/*      */ import android.widget.Spinner;
/*      */ import android.widget.SpinnerAdapter;
/*      */ import android.widget.TextView;
/*      */ import androidx.annotation.ColorInt;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import androidx.appcompat.widget.AppCompatImageButton;
/*      */ import androidx.appcompat.widget.TooltipCompat;
/*      */ import com.pdftron.pdf.asynctask.LoadFontAsyncTask;
/*      */ import com.pdftron.pdf.config.ToolStyleConfig;
/*      */ import com.pdftron.pdf.model.AnnotStyle;
/*      */ import com.pdftron.pdf.model.FontResource;
/*      */ import com.pdftron.pdf.tools.Eraser;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.utils.AnalyticsAnnotStylePicker;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.AnnotationPropertyPreviewView;
/*      */ import com.pdftron.pdf.utils.CommonToast;
/*      */ import com.pdftron.pdf.utils.ExpandableGridView;
/*      */ import com.pdftron.pdf.utils.FontAdapter;
/*      */ import com.pdftron.pdf.utils.IconPickerGridViewAdapter;
/*      */ import com.pdftron.pdf.utils.MeasureUtils;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.widget.InertSwitch;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Calendar;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
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
/*      */ public class AnnotStyleView
/*      */   extends LinearLayout
/*      */   implements TextView.OnEditorActionListener, SeekBar.OnSeekBarChangeListener, View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
/*      */ {
/*      */   public static final String SOUND_ICON_OUTLINE = "annotation_icon_sound_outline";
/*      */   public static final String SOUND_ICON_FILL = "annotation_icon_sound_fill";
/*      */   private static final int MAX_PROGRESS = 100;
/*      */   private static final int PRESET_SIZE = 4;
/*   85 */   private int mAnnotType = 28;
/*      */   
/*      */   private Set<String> mWhiteListFonts;
/*      */   
/*      */   private LinearLayout mMoreToolLayout;
/*      */   
/*      */   private LinearLayout mStrokeLayout;
/*      */   
/*      */   private TextView mStrokeColorTextView;
/*      */   
/*      */   private AnnotationPropertyPreviewView mStrokePreview;
/*      */   
/*      */   private LinearLayout mFillLayout;
/*      */   
/*      */   private TextView mFillColorTextView;
/*      */   
/*      */   private AnnotationPropertyPreviewView mFillPreview;
/*      */   
/*      */   private LinearLayout mThicknessLayout;
/*      */   
/*      */   private SeekBar mThicknessSeekbar;
/*      */   
/*      */   private EditText mThicknessEditText;
/*      */   
/*      */   private LinearLayout mThicknessValueGroup;
/*      */   
/*      */   private LinearLayout mOpacityLayout;
/*      */   
/*      */   private TextView mOpacityTextView;
/*      */   
/*      */   private SeekBar mOpacitySeekbar;
/*      */   
/*      */   private EditText mOpacityEditText;
/*      */   
/*      */   private LinearLayout mOpacityValueGroup;
/*      */   
/*      */   private LinearLayout mFontLayout;
/*      */   
/*      */   private Spinner mFontSpinner;
/*      */   
/*      */   private FontAdapter mFontAdapter;
/*      */   
/*      */   private LinearLayout mDateFormatLayout;
/*      */   
/*      */   private Spinner mDateFormatSpinner;
/*      */   
/*      */   private ArrayAdapter<CharSequence> mDateFormatSpinnerAdapter;
/*      */   
/*      */   private LinearLayout mTextColorLayout;
/*      */   
/*      */   private AnnotationPropertyPreviewView mTextColorPreview;
/*      */   
/*      */   private LinearLayout mTextSizeLayout;
/*      */   
/*      */   private SeekBar mTextSizeSeekbar;
/*      */   
/*      */   private EditText mTextSizeEditText;
/*      */   
/*      */   private LinearLayout mIconLayout;
/*      */   
/*      */   private ImageView mIconExpandableBtn;
/*      */   
/*      */   private ExpandableGridView mIconExpandableGridView;
/*      */   
/*      */   private IconPickerGridViewAdapter mIconAdapter;
/*      */   
/*      */   private AnnotationPropertyPreviewView mIconPreview;
/*      */   
/*      */   private boolean mIconExpanded;
/*      */   
/*      */   private LinearLayout mRulerUnitLayout;
/*      */   
/*      */   private EditText mRulerBaseEditText;
/*      */   
/*      */   private Spinner mRulerBaseSpinner;
/*      */   
/*      */   private ArrayAdapter<CharSequence> mRulerBaseSpinnerAdapter;
/*      */   private EditText mRulerTranslateEditText;
/*      */   private Spinner mRulerTranslateSpinner;
/*      */   private ArrayAdapter<CharSequence> mRulerTranslateSpinnerAdapter;
/*      */   private LinearLayout mRulerPrecisionLayout;
/*      */   private Spinner mRulerPrecisionSpinner;
/*      */   private ArrayAdapter<CharSequence> mRulerPrecisionSpinnerAdapter;
/*      */   private LinearLayout mSnapLayout;
/*      */   private InertSwitch mSnapSwitch;
/*      */   private boolean mCanShowRCOption;
/*      */   private LinearLayout mRCEnableLayout;
/*      */   private InertSwitch mRCEnableSwitch;
/*      */   private LinearLayout mTextOverlayLayout;
/*      */   private EditText mOverlayEditText;
/*      */   private LinearLayout mEraserTypeLayout;
/*      */   private InertSwitch mEraserTypeSwitch;
/*      */   private LinearLayout mInkEraserModeLayout;
/*      */   private Spinner mInkEraserModeSpinner;
/*      */   private ArrayAdapter<CharSequence> mInkEraserModeAdapter;
/*      */   private LinearLayout mPressureSensitiveLayout;
/*      */   private InertSwitch mPressureSensitiveSwitch;
/*      */   private boolean mCanShowPressureOption;
/*      */   private LinearLayout mPresetContainer;
/*  184 */   private AnnotationPropertyPreviewView[] mPresetViews = new AnnotationPropertyPreviewView[4];
/*  185 */   private AnnotStyle[] mPresetStyles = new AnnotStyle[4];
/*      */   
/*      */   private OnPresetSelectedListener mPresetSelectedListner;
/*      */   
/*      */   private float mMaxThickness;
/*      */   
/*      */   private float mMinThickness;
/*      */   
/*      */   private float mMaxTextSize;
/*      */   
/*      */   private float mMinTextSize;
/*      */   
/*      */   private boolean mPrevThicknessFocus = false;
/*      */   
/*      */   private boolean mPrevOpacityFocus = false;
/*      */   
/*      */   private boolean mInitSpinner = true;
/*      */   
/*      */   private AnnotStyle.AnnotStyleHolder mAnnotStyleHolder;
/*      */   
/*      */   private ArrayList<Integer> mMoreAnnotTypes;
/*      */   private OnMoreAnnotTypeClickedListener mMoreAnnotTypeListener;
/*      */   private OnColorLayoutClickedListener mColorLayoutClickedListener;
/*      */   private boolean mTextJustChanged;
/*      */   
/*      */   public AnnotStyleView(Context context) {
/*  211 */     this(context, (AttributeSet)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyleView(Context context, @Nullable AttributeSet attrs) {
/*  218 */     this(context, attrs, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotStyleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  225 */     super(context, attrs, defStyleAttr);
/*  226 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotType(int annotType) {
/*  233 */     this.mAnnotType = annotType;
/*  234 */     this.mMaxThickness = ToolStyleConfig.getInstance().getDefaultMaxThickness(getContext(), annotType);
/*  235 */     this.mMinThickness = ToolStyleConfig.getInstance().getDefaultMinThickness(getContext(), annotType);
/*  236 */     this.mMinTextSize = ToolStyleConfig.getInstance().getDefaultMinTextSize(getContext());
/*  237 */     this.mMaxTextSize = ToolStyleConfig.getInstance().getDefaultMaxTextSize(getContext());
/*      */     
/*  239 */     this.mAnnotStyleHolder.getAnnotPreview().setAnnotType(this.mAnnotType);
/*      */     
/*  241 */     if (getAnnotStyle().isFreeTextGroup() || this.mAnnotType == 19) {
/*  242 */       this.mStrokePreview.setAnnotType(this.mAnnotType);
/*      */       
/*  244 */       setupFontSpinner();
/*      */     } 
/*  246 */     if (this.mAnnotType == 0) {
/*      */       
/*  248 */       List<String> source = ToolStyleConfig.getInstance().getIconsList(getContext());
/*  249 */       this.mIconAdapter = new IconPickerGridViewAdapter(getContext(), source);
/*  250 */       this.mIconExpandableGridView.setAdapter((ListAdapter)this.mIconAdapter);
/*  251 */       this.mIconExpandableGridView.setOnItemClickListener(this);
/*      */     } 
/*      */     
/*  254 */     loadPresets();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanShowRichContentSwitch(boolean canShow) {
/*  261 */     this.mCanShowRCOption = canShow;
/*      */   }
/*      */   
/*      */   public void setCanShowPressureSwitch(boolean canShow) {
/*  265 */     this.mCanShowPressureOption = canShow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadPresets() {
/*  272 */     for (int i = 0; i < 4; i++) {
/*  273 */       AnnotationPropertyPreviewView presetView = this.mPresetViews[i];
/*  274 */       AnnotStyle preset = ToolStyleConfig.getInstance().getAnnotPresetStyle(getContext(), this.mAnnotType, i);
/*  275 */       presetView.setAnnotType(this.mAnnotType);
/*  276 */       preset.bindPreview(presetView);
/*  277 */       if (!preset.getFont().hasFontName().booleanValue() && this.mFontAdapter != null && this.mFontAdapter.getData() != null && this.mFontAdapter.getData().size() > 1) {
/*  278 */         preset.setFont(this.mFontAdapter.getData().get(1));
/*      */       }
/*      */       
/*  281 */       preset.setSnap(getAnnotStyle().getSnap());
/*  282 */       preset.setTextHTMLContent(getAnnotStyle().getTextHTMLContent());
/*  283 */       this.mPresetStyles[i] = preset;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAnnotStyleHolder(AnnotStyle.AnnotStyleHolder annotStyleHolder) {
/*  293 */     this.mAnnotStyleHolder = annotStyleHolder;
/*      */   }
/*      */   
/*      */   private AnnotStyle getAnnotStyle() {
/*  297 */     return this.mAnnotStyleHolder.getAnnotStyle();
/*      */   }
/*      */   
/*      */   private void setIcon(String icon) {
/*  301 */     getAnnotStyle().setIcon(icon);
/*  302 */     int iconPosition = this.mIconAdapter.getItemIndex(icon);
/*  303 */     this.mIconAdapter.setSelected(iconPosition);
/*  304 */     this.mAnnotStyleHolder.getAnnotPreview().setImageDrawable(getAnnotStyle().getIconDrawable(getContext()));
/*  305 */     this.mIconPreview.setImageDrawable(AnnotStyle.getIconDrawable(getContext(), getAnnotStyle().getIcon(), getAnnotStyle().getColor(), 1.0F));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWhiteFontList(Set<String> whiteFontList) {
/*  314 */     this.mWhiteListFonts = whiteFontList;
/*  315 */     if (!checkPresets()) {
/*  316 */       setFontSpinner();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMoreAnnotTypes(ArrayList<Integer> annotTypes) {
/*  326 */     this.mMoreAnnotTypes = annotTypes;
/*      */     
/*  328 */     View firstView = this.mMoreToolLayout.getChildAt(0);
/*  329 */     this.mMoreToolLayout.removeAllViews();
/*  330 */     this.mMoreToolLayout.addView(firstView);
/*  331 */     for (Integer type : this.mMoreAnnotTypes) {
/*  332 */       AppCompatImageButton imageButton = getAnnotTypeButtonForTool(type.intValue());
/*  333 */       imageButton.setOnClickListener(new OnClickListener()
/*      */           {
/*      */             public void onClick(View v) {
/*  336 */               if (!v.isSelected() && AnnotStyleView.this.mMoreAnnotTypeListener != null) {
/*  337 */                 AnnotStyleView.this.mMoreAnnotTypeListener.onAnnotTypeClicked(type.intValue());
/*      */               }
/*      */             }
/*      */           });
/*  341 */       this.mMoreToolLayout.addView((View)imageButton);
/*      */     } 
/*  343 */     this.mMoreToolLayout.setVisibility(annotTypes.isEmpty() ? 8 : 0);
/*      */   }
/*      */   
/*      */   public void updateAnnotTypes() {
/*  347 */     if (this.mMoreAnnotTypes == null || this.mMoreAnnotTypes.isEmpty()) {
/*      */       return;
/*      */     }
/*  350 */     int annotTypeIndex = this.mMoreAnnotTypes.indexOf(Integer.valueOf(this.mAnnotType));
/*      */     
/*  352 */     int childCount = this.mMoreToolLayout.getChildCount();
/*  353 */     for (int i = 0; i < childCount; i++) {
/*  354 */       View child = this.mMoreToolLayout.getChildAt(i);
/*  355 */       if (child instanceof android.widget.ImageButton) {
/*  356 */         child.setSelected((i == annotTypeIndex + 1));
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setFont(FontResource font) {
/*  362 */     getAnnotStyle().setFont(font);
/*  363 */     setFontSpinner();
/*      */   }
/*      */   
/*      */   private void init() {
/*  367 */     LayoutInflater.from(getContext()).inflate(R.layout.controls_annotation_styles, (ViewGroup)this);
/*  368 */     setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/*  369 */     setOrientation(1);
/*      */ 
/*      */     
/*  372 */     this.mStrokeLayout = (LinearLayout)findViewById(R.id.stroke_color_layout);
/*  373 */     this.mStrokeColorTextView = (TextView)findViewById(R.id.stroke_color_textivew);
/*  374 */     this.mStrokePreview = (AnnotationPropertyPreviewView)findViewById(R.id.stroke_preview);
/*      */ 
/*      */     
/*  377 */     this.mMoreToolLayout = (LinearLayout)findViewById(R.id.more_tools_layout);
/*      */ 
/*      */     
/*  380 */     this.mFillLayout = (LinearLayout)findViewById(R.id.fill_color_layout);
/*  381 */     this.mFillColorTextView = (TextView)findViewById(R.id.fill_color_textview);
/*  382 */     this.mFillPreview = (AnnotationPropertyPreviewView)findViewById(R.id.fill_preview);
/*      */ 
/*      */     
/*  385 */     this.mThicknessLayout = (LinearLayout)findViewById(R.id.thickness_layout);
/*  386 */     this.mThicknessSeekbar = (SeekBar)findViewById(R.id.thickness_seekbar);
/*  387 */     this.mThicknessEditText = (EditText)findViewById(R.id.thickness_edit_text);
/*  388 */     this.mThicknessValueGroup = (LinearLayout)findViewById(R.id.thickness_value_group);
/*      */ 
/*      */     
/*  391 */     this.mOpacityLayout = (LinearLayout)findViewById(R.id.opacity_layout);
/*  392 */     this.mOpacityTextView = (TextView)findViewById(R.id.opacity_textivew);
/*  393 */     this.mOpacitySeekbar = (SeekBar)findViewById(R.id.opacity_seekbar);
/*  394 */     this.mOpacityEditText = (EditText)findViewById(R.id.opacity_edit_text);
/*  395 */     this.mOpacityValueGroup = (LinearLayout)findViewById(R.id.opacity_value_group);
/*      */ 
/*      */     
/*  398 */     this.mIconLayout = (LinearLayout)findViewById(R.id.icon_layout);
/*  399 */     this.mIconExpandableBtn = (ImageView)findViewById(R.id.icon_expandable_btn);
/*  400 */     this.mIconExpandableGridView = (ExpandableGridView)findViewById(R.id.icon_grid);
/*  401 */     this.mIconPreview = (AnnotationPropertyPreviewView)findViewById(R.id.icon_preview);
/*  402 */     this.mIconExpandableGridView.setExpanded(true);
/*  403 */     this.mIconLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  406 */     this.mFontLayout = (LinearLayout)findViewById(R.id.font_layout);
/*  407 */     this.mFontSpinner = (Spinner)findViewById(R.id.font_dropdown);
/*      */ 
/*      */     
/*  410 */     this.mDateFormatLayout = (LinearLayout)findViewById(R.id.date_format_layout);
/*  411 */     this.mDateFormatSpinner = (Spinner)findViewById(R.id.date_format_spinner);
/*      */     
/*  413 */     CharSequence[] strings = getContext().getResources().getTextArray(R.array.style_picker_date_formats);
/*  414 */     CharSequence[] nowStrings = new CharSequence[strings.length];
/*  415 */     for (int i = 0; i < strings.length; i++) {
/*  416 */       CharSequence c = strings[i];
/*  417 */       SimpleDateFormat dateFormat = new SimpleDateFormat(c.toString(), Locale.getDefault());
/*  418 */       Calendar cal = Calendar.getInstance();
/*  419 */       String dateStr = dateFormat.format(cal.getTime());
/*  420 */       nowStrings[i] = dateStr;
/*      */     } 
/*  422 */     this
/*  423 */       .mDateFormatSpinnerAdapter = new ArrayAdapter(getContext(), 17367048, 0, Arrays.asList(nowStrings));
/*  424 */     this.mDateFormatSpinnerAdapter.setDropDownViewResource(17367049);
/*  425 */     this.mDateFormatSpinner.setAdapter((SpinnerAdapter)this.mDateFormatSpinnerAdapter);
/*  426 */     this.mDateFormatSpinner.setOnItemSelectedListener(this);
/*      */ 
/*      */     
/*  429 */     this.mTextColorLayout = (LinearLayout)findViewById(R.id.text_color_layout);
/*  430 */     this.mTextColorPreview = (AnnotationPropertyPreviewView)findViewById(R.id.text_color_preview);
/*  431 */     this.mTextColorPreview.setAnnotType(2);
/*  432 */     this.mTextColorLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  435 */     this.mTextSizeLayout = (LinearLayout)findViewById(R.id.text_size_layout);
/*  436 */     this.mTextSizeSeekbar = (SeekBar)findViewById(R.id.text_size_seekbar);
/*  437 */     this.mTextSizeEditText = (EditText)findViewById(R.id.text_size_edit_text);
/*  438 */     this.mTextSizeSeekbar.setOnSeekBarChangeListener(this);
/*  439 */     this.mTextSizeEditText.setOnFocusChangeListener(this);
/*  440 */     this.mTextSizeEditText.setOnEditorActionListener(this);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  445 */     this.mRulerUnitLayout = (LinearLayout)findViewById(R.id.ruler_unit_layout);
/*  446 */     this.mRulerBaseEditText = (EditText)findViewById(R.id.ruler_base_edit_text);
/*  447 */     this.mRulerBaseEditText.setText("1.0");
/*  448 */     this.mRulerBaseSpinner = (Spinner)findViewById(R.id.ruler_base_unit_spinner);
/*  449 */     this.mRulerBaseSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ruler_base_unit, 17367048);
/*      */     
/*  451 */     this.mRulerBaseSpinnerAdapter.setDropDownViewResource(17367049);
/*  452 */     this.mRulerBaseSpinner.setAdapter((SpinnerAdapter)this.mRulerBaseSpinnerAdapter);
/*  453 */     this.mRulerBaseSpinner.setOnItemSelectedListener(this);
/*      */     
/*  455 */     this.mRulerTranslateEditText = (EditText)findViewById(R.id.ruler_translate_edit_text);
/*  456 */     this.mRulerTranslateEditText.setText("1.0");
/*  457 */     this.mRulerTranslateSpinner = (Spinner)findViewById(R.id.ruler_translate_unit_spinner);
/*  458 */     this.mRulerTranslateSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ruler_translate_unit, 17367048);
/*      */     
/*  460 */     this.mRulerTranslateSpinnerAdapter.setDropDownViewResource(17367049);
/*  461 */     this.mRulerTranslateSpinner.setAdapter((SpinnerAdapter)this.mRulerTranslateSpinnerAdapter);
/*  462 */     this.mRulerTranslateSpinner.setOnItemSelectedListener(this);
/*      */ 
/*      */     
/*  465 */     this.mRulerPrecisionLayout = (LinearLayout)findViewById(R.id.ruler_precision_layout);
/*  466 */     this.mRulerPrecisionSpinner = (Spinner)findViewById(R.id.ruler_precision_spinner);
/*  467 */     this.mRulerPrecisionSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.ruler_precision, 17367048);
/*      */     
/*  469 */     this.mRulerPrecisionSpinnerAdapter.setDropDownViewResource(17367049);
/*  470 */     this.mRulerPrecisionSpinner.setAdapter((SpinnerAdapter)this.mRulerPrecisionSpinnerAdapter);
/*  471 */     this.mRulerPrecisionSpinner.setOnItemSelectedListener(this);
/*      */ 
/*      */     
/*  474 */     this.mSnapLayout = (LinearLayout)findViewById(R.id.snap_layout);
/*  475 */     this.mSnapSwitch = (InertSwitch)findViewById(R.id.snap_switch);
/*  476 */     this.mSnapLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  479 */     this.mRCEnableLayout = (LinearLayout)findViewById(R.id.rich_text_enabled_layout);
/*  480 */     this.mRCEnableSwitch = (InertSwitch)findViewById(R.id.rich_text_enabled_switch);
/*  481 */     this.mRCEnableLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  484 */     this.mTextOverlayLayout = (LinearLayout)findViewById(R.id.overlay_text_layout);
/*  485 */     this.mOverlayEditText = (EditText)findViewById(R.id.overlay_edittext);
/*      */ 
/*      */     
/*  488 */     this.mEraserTypeLayout = (LinearLayout)findViewById(R.id.eraser_type);
/*  489 */     this.mEraserTypeSwitch = (InertSwitch)findViewById(R.id.eraser_type_switch);
/*  490 */     this.mEraserTypeLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  493 */     this.mInkEraserModeLayout = (LinearLayout)findViewById(R.id.eraser_ink_mode);
/*  494 */     this.mInkEraserModeSpinner = (Spinner)findViewById(R.id.eraser_ink_mode_spinner);
/*  495 */     this.mInkEraserModeAdapter = ArrayAdapter.createFromResource(getContext(), R.array.style_ink_eraser_mode, 17367048);
/*      */     
/*  497 */     this.mInkEraserModeAdapter.setDropDownViewResource(17367049);
/*  498 */     this.mInkEraserModeSpinner.setAdapter((SpinnerAdapter)this.mInkEraserModeAdapter);
/*  499 */     this.mInkEraserModeSpinner.setOnItemSelectedListener(this);
/*      */ 
/*      */     
/*  502 */     this.mPressureSensitiveLayout = (LinearLayout)findViewById(R.id.pressure_sensitive_layout);
/*  503 */     this.mPressureSensitiveSwitch = (InertSwitch)findViewById(R.id.pressure_sensitive_enabled_switch);
/*  504 */     this.mPressureSensitiveLayout.setOnClickListener(this);
/*      */ 
/*      */     
/*  507 */     this.mPresetContainer = (LinearLayout)findViewById(R.id.presets_layout);
/*  508 */     this.mPresetViews[0] = (AnnotationPropertyPreviewView)findViewById(R.id.preset1);
/*  509 */     this.mPresetViews[1] = (AnnotationPropertyPreviewView)findViewById(R.id.preset2);
/*  510 */     this.mPresetViews[2] = (AnnotationPropertyPreviewView)findViewById(R.id.preset3);
/*  511 */     this.mPresetViews[3] = (AnnotationPropertyPreviewView)findViewById(R.id.preset4);
/*      */     
/*  513 */     TypedArray typedArray = getContext().obtainStyledAttributes(new int[] { R.attr.colorBackgroundLight });
/*  514 */     int background = typedArray.getColor(0, getContext().getResources().getColor(R.color.controls_annot_style_preview_bg));
/*  515 */     typedArray.recycle();
/*      */     
/*  517 */     for (AnnotationPropertyPreviewView presetView : this.mPresetViews) {
/*  518 */       presetView.setOnClickListener(this);
/*  519 */       presetView.setParentBackgroundColor(background);
/*      */     } 
/*      */ 
/*      */     
/*  523 */     this.mStrokeLayout.setOnClickListener(this);
/*  524 */     this.mFillLayout.setOnClickListener(this);
/*      */     
/*  526 */     this.mThicknessSeekbar.setOnSeekBarChangeListener(this);
/*  527 */     this.mOpacitySeekbar.setOnSeekBarChangeListener(this);
/*      */     
/*  529 */     this.mThicknessEditText.setOnEditorActionListener(this);
/*  530 */     this.mOpacityEditText.setOnEditorActionListener(this);
/*  531 */     this.mRulerBaseEditText.setOnEditorActionListener(this);
/*  532 */     this.mRulerTranslateEditText.setOnEditorActionListener(this);
/*  533 */     this.mOverlayEditText.setOnEditorActionListener(this);
/*      */     
/*  535 */     this.mThicknessEditText.setOnFocusChangeListener(this);
/*  536 */     this.mOpacityEditText.setOnFocusChangeListener(this);
/*  537 */     this.mRulerBaseEditText.setOnFocusChangeListener(this);
/*  538 */     this.mRulerTranslateEditText.setOnFocusChangeListener(this);
/*  539 */     this.mOverlayEditText.setOnFocusChangeListener(this);
/*      */     
/*  541 */     this.mThicknessValueGroup.setOnClickListener(this);
/*  542 */     this.mOpacityValueGroup.setOnClickListener(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismiss() {
/*  549 */     setVisibility(8);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void show() {
/*  556 */     setVisibility(0);
/*  557 */     initLayoutStyle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkPresets() {
/*  567 */     int i = 0;
/*  568 */     for (AnnotStyle preset : this.mPresetStyles) {
/*  569 */       if (preset == null) {
/*      */         break;
/*      */       }
/*  572 */       if (preset != getAnnotStyle() && preset.equals(getAnnotStyle()) && 
/*  573 */         this.mPresetSelectedListner != null) {
/*      */         
/*  575 */         this.mPresetSelectedListner.onPresetSelected(preset);
/*  576 */         AnalyticsAnnotStylePicker.getInstance().setPresetIndex(i);
/*  577 */         return true;
/*      */       } 
/*      */       
/*  580 */       i++;
/*      */     } 
/*  582 */     return false;
/*      */   }
/*      */   private void initLayoutStyle() {
/*      */     Drawable drawable;
/*  586 */     this.mAnnotStyleHolder.getAnnotPreview().updateFillPreview(getAnnotStyle());
/*      */     
/*  588 */     int backgroundColor = Utils.getBackgroundColor(getContext());
/*      */ 
/*      */ 
/*      */     
/*  592 */     if (getAnnotStyle().getColor() == 0) {
/*  593 */       drawable = getContext().getResources().getDrawable(R.drawable.oval_fill_transparent);
/*  594 */     } else if (getAnnotStyle().getColor() == backgroundColor) {
/*  595 */       if (getAnnotStyle().hasFillColor()) {
/*  596 */         drawable = getContext().getResources().getDrawable(R.drawable.ring_stroke_preview);
/*      */       } else {
/*  598 */         drawable = getContext().getResources().getDrawable(R.drawable.oval_stroke_preview);
/*      */       } 
/*      */       
/*  601 */       drawable.mutate();
/*  602 */       ((GradientDrawable)drawable).setStroke((int)Utils.convDp2Pix(getContext(), 1.0F), -7829368);
/*      */     } else {
/*  604 */       if (getAnnotStyle().hasFillColor()) {
/*  605 */         drawable = getContext().getResources().getDrawable(R.drawable.oval_stroke_preview);
/*      */       } else {
/*  607 */         drawable = getContext().getResources().getDrawable(R.drawable.oval_fill_preview);
/*      */       } 
/*  609 */       drawable.mutate();
/*  610 */       drawable.setColorFilter(getAnnotStyle().getColor(), PorterDuff.Mode.SRC_IN);
/*      */     } 
/*  612 */     this.mStrokePreview.setImageDrawable(drawable);
/*      */ 
/*      */     
/*  615 */     if (getAnnotStyle().getFillColor() != backgroundColor) {
/*  616 */       int fillDrawableRes = (getAnnotStyle().getFillColor() == 0) ? R.drawable.oval_fill_transparent : R.drawable.oval_fill_preview;
/*  617 */       Drawable fillDrawable = getContext().getResources().getDrawable(fillDrawableRes);
/*  618 */       if (fillDrawableRes != R.drawable.oval_fill_transparent) {
/*  619 */         fillDrawable.mutate();
/*  620 */         fillDrawable.setColorFilter(getAnnotStyle().getFillColor(), PorterDuff.Mode.SRC_IN);
/*      */       } 
/*  622 */       this.mFillPreview.setImageDrawable(fillDrawable);
/*      */     } else {
/*  624 */       GradientDrawable fillDrawable = (GradientDrawable)getContext().getResources().getDrawable(R.drawable.oval_stroke_preview);
/*  625 */       fillDrawable.mutate();
/*  626 */       fillDrawable.setStroke((int)Utils.convDp2Pix(getContext(), 1.0F), -7829368);
/*  627 */       this.mFillPreview.setImageDrawable((Drawable)fillDrawable);
/*      */     } 
/*      */ 
/*      */     
/*  631 */     if (getAnnotStyle().hasThickness()) {
/*  632 */       String annotThickness = String.format(getContext().getString(R.string.tools_misc_thickness), new Object[] { Float.valueOf(getAnnotStyle().getThickness()) });
/*  633 */       if (!this.mThicknessEditText.getText().toString().equals(annotThickness)) {
/*  634 */         this.mThicknessEditText.setText(annotThickness);
/*      */       }
/*  636 */       this.mTextJustChanged = true;
/*  637 */       this.mThicknessSeekbar.setProgress(Math.round((getAnnotStyle().getThickness() - this.mMinThickness) / (this.mMaxThickness - this.mMinThickness) * 100.0F));
/*      */     } 
/*      */ 
/*      */     
/*  641 */     if (getAnnotStyle().hasTextStyle()) {
/*  642 */       String textSizeStr = getContext().getString(R.string.tools_misc_textsize, new Object[] { Integer.valueOf((int)getAnnotStyle().getTextSize()) });
/*  643 */       if (!this.mTextSizeEditText.getText().toString().equals(textSizeStr)) {
/*  644 */         this.mTextSizeEditText.setText(textSizeStr);
/*      */       }
/*  646 */       this.mTextJustChanged = true;
/*  647 */       this.mTextSizeSeekbar.setProgress(Math.round((getAnnotStyle().getTextSize() - this.mMinTextSize) / (this.mMaxTextSize - this.mMinTextSize) * 100.0F));
/*  648 */       this.mTextColorPreview.updateFillPreview(0, 0, 0.0D, 1.0D);
/*  649 */       this.mTextColorPreview.updateFreeTextStyle(getAnnotStyle().getTextColor(), 1.0F);
/*      */     } 
/*      */ 
/*      */     
/*  653 */     if (getAnnotStyle().hasFont()) {
/*  654 */       setFont(getAnnotStyle().getFont());
/*      */     }
/*      */ 
/*      */     
/*  658 */     if (getAnnotStyle().isDateFreeText()) {
/*  659 */       String format = getAnnotStyle().getDateFormat();
/*  660 */       CharSequence[] strings = getContext().getResources().getTextArray(R.array.style_picker_date_formats);
/*  661 */       for (int i = 0; i < strings.length; i++) {
/*  662 */         CharSequence s = strings[i];
/*  663 */         if (s.equals(format)) {
/*  664 */           this.mDateFormatSpinner.setSelection(i);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  671 */     if (getAnnotStyle().isFreeText() && !getAnnotStyle().isCallout()) {
/*  672 */       this.mRCEnableSwitch.setChecked(getAnnotStyle().isRCFreeText());
/*      */     }
/*      */ 
/*      */     
/*  676 */     if (getAnnotStyle().hasOpacity()) {
/*  677 */       int progress = (int)(getAnnotStyle().getOpacity() * 100.0F);
/*  678 */       this.mOpacityEditText.setText(String.valueOf(progress));
/*  679 */       this.mTextJustChanged = true;
/*  680 */       this.mOpacitySeekbar.setProgress(progress);
/*      */     } 
/*      */ 
/*      */     
/*  684 */     if (getAnnotStyle().hasIcon()) {
/*  685 */       if (!Utils.isNullOrEmpty(getAnnotStyle().getIcon())) {
/*  686 */         this.mAnnotStyleHolder.getAnnotPreview().setImageDrawable(getAnnotStyle().getIconDrawable(getContext()));
/*  687 */         if (this.mIconAdapter != null) {
/*  688 */           this.mIconAdapter.setSelected(this.mIconAdapter.getItemIndex(getAnnotStyle().getIcon()));
/*      */         }
/*  690 */         this.mIconPreview.setImageDrawable(AnnotStyle.getIconDrawable(getContext(), getAnnotStyle().getIcon(), getAnnotStyle().getColor(), 1.0F));
/*      */       } 
/*  692 */       if (this.mIconAdapter != null) {
/*  693 */         this.mIconAdapter.updateIconColor(getAnnotStyle().getColor());
/*  694 */         this.mIconAdapter.updateIconOpacity(getAnnotStyle().getOpacity());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  699 */     if (getAnnotStyle().isMeasurement()) {
/*      */       
/*  701 */       this.mSnapSwitch.setChecked(getAnnotStyle().getSnap());
/*      */       
/*  703 */       this.mRulerBaseEditText.setText(String.valueOf(getAnnotStyle().getRulerBaseValue()));
/*      */       
/*  705 */       String documentUnit = getAnnotStyle().getRulerBaseUnit();
/*  706 */       int dIndex = this.mRulerBaseSpinnerAdapter.getPosition(documentUnit);
/*  707 */       if (dIndex >= 0) {
/*  708 */         this.mRulerBaseSpinner.setSelection(dIndex);
/*      */       }
/*      */       
/*  711 */       this.mRulerTranslateEditText.setText(String.valueOf(getAnnotStyle().getRulerTranslateValue()));
/*      */       
/*  713 */       String worldUnit = getAnnotStyle().getRulerTranslateUnit();
/*  714 */       int wIndex = this.mRulerTranslateSpinnerAdapter.getPosition(worldUnit);
/*  715 */       if (wIndex >= 0) {
/*  716 */         this.mRulerTranslateSpinner.setSelection(wIndex);
/*      */       }
/*      */       
/*  719 */       boolean found = false;
/*  720 */       int precision = getAnnotStyle().getPrecision();
/*  721 */       HashMap<String, Integer> precisions = MeasureUtils.getPrecisions();
/*  722 */       for (Map.Entry<String, Integer> entry : precisions.entrySet()) {
/*  723 */         Integer value = entry.getValue();
/*  724 */         if (value != null && value.intValue() == precision) {
/*  725 */           String key = entry.getKey();
/*  726 */           int pIndex = this.mRulerPrecisionSpinnerAdapter.getPosition(key);
/*  727 */           if (wIndex >= 0) {
/*  728 */             this.mRulerPrecisionSpinner.setSelection(pIndex);
/*  729 */             found = true;
/*      */           } 
/*      */         } 
/*      */       } 
/*  733 */       if (!found && 
/*  734 */         this.mRulerPrecisionSpinnerAdapter.getCount() >= 3) {
/*  735 */         this.mRulerPrecisionSpinner.setSelection(2);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  741 */     if (getAnnotStyle().isRedaction() || getAnnotStyle().isWatermark()) {
/*  742 */       this.mOverlayEditText.setText(getAnnotStyle().getOverlayText());
/*      */     }
/*      */     
/*  745 */     if (getAnnotStyle().isEraser()) {
/*      */       
/*  747 */       this.mEraserTypeSwitch.setChecked(getAnnotStyle().getEraserType().equals(Eraser.EraserType.INK_ERASER));
/*      */ 
/*      */       
/*  750 */       Eraser.InkEraserMode mode = getAnnotStyle().getInkEraserMode();
/*  751 */       CharSequence[] strings = getContext().getResources().getTextArray(R.array.style_ink_eraser_mode);
/*  752 */       for (int i = 0; i < strings.length; i++) {
/*  753 */         CharSequence s = strings[i];
/*  754 */         if (s.equals(getContext().getResources().getString(mode.mLabelRes))) {
/*  755 */           this.mInkEraserModeSpinner.setSelection(i);
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  762 */     if (getAnnotStyle().hasPressureSensitivity()) {
/*  763 */       this.mPressureSensitiveSwitch.setChecked(getAnnotStyle().getPressureSensitive());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deselectAllPresetsPreview() {
/*  772 */     for (AnnotStyle preset : this.mPresetStyles) {
/*  773 */       if (preset != null) {
/*  774 */         AnnotationPropertyPreviewView preview = preset.getBindedPreview();
/*  775 */         if (preview != null) {
/*  776 */           preview.setSelected(false);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setupFontSpinner() {
/*  784 */     ArrayList<FontResource> fonts = new ArrayList<>();
/*  785 */     FontResource loading = new FontResource(getContext().getString(R.string.free_text_fonts_loading), "", "", "");
/*  786 */     fonts.add(loading);
/*      */     
/*  788 */     this.mFontAdapter = new FontAdapter(getContext(), 17367048, fonts);
/*  789 */     this.mFontAdapter.setDropDownViewResource(17367049);
/*      */     
/*  791 */     this.mFontSpinner.setAdapter((SpinnerAdapter)this.mFontAdapter);
/*  792 */     this.mFontSpinner.setOnItemSelectedListener(this);
/*      */     
/*  794 */     LoadFontAsyncTask fontAsyncTask = new LoadFontAsyncTask(getContext(), this.mWhiteListFonts);
/*  795 */     fontAsyncTask.setCallback(new LoadFontAsyncTask.Callback()
/*      */         {
/*      */           public void onFinish(ArrayList<FontResource> fonts) {
/*  798 */             FontResource fontHint = new FontResource(AnnotStyleView.this.getContext().getString(R.string.free_text_fonts_prompt), "", "", "");
/*      */ 
/*      */ 
/*      */             
/*  802 */             fonts.add(0, fontHint);
/*  803 */             AnnotStyleView.this.mFontAdapter.setData(fonts);
/*  804 */             if (AnnotStyleView.this.getAnnotStyle() != null && AnnotStyleView.this.getAnnotStyle().getFont() != null) {
/*  805 */               AnnotStyleView.this.setFontSpinner();
/*      */             }
/*  807 */             AnnotStyleView.this.setPresetFonts(fonts);
/*      */           }
/*      */         });
/*  810 */     fontAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*      */   }
/*      */   
/*      */   private void setPresetFonts(ArrayList<FontResource> fonts) {
/*  814 */     for (AnnotStyle preset : this.mPresetStyles) {
/*  815 */       boolean fontFound = false;
/*  816 */       for (FontResource font : fonts) {
/*  817 */         if (preset.getFont().equals(font)) {
/*  818 */           preset.setFont(font);
/*  819 */           fontFound = true;
/*      */           break;
/*      */         } 
/*      */       } 
/*  823 */       if (!fontFound)
/*      */       {
/*  825 */         preset.setFont(fonts.get(1));
/*      */       }
/*      */     } 
/*  828 */     checkPresets();
/*      */   }
/*      */   
/*      */   private void setFontSpinner() {
/*  832 */     if (this.mFontAdapter != null && this.mFontAdapter.getData() != null && this.mFontSpinner != null) {
/*      */       
/*  834 */       boolean matchFound = false;
/*  835 */       if (getAnnotStyle().getFont().hasPDFTronName().booleanValue()) {
/*      */         
/*  837 */         for (int i = 0; i < this.mFontAdapter.getData().size(); i++) {
/*  838 */           if (((FontResource)this.mFontAdapter.getData().get(i)).getPDFTronName().equals(getAnnotStyle().getFont().getPDFTronName())) {
/*  839 */             this.mFontSpinner.setSelection(i);
/*  840 */             matchFound = true;
/*      */             break;
/*      */           } 
/*      */         } 
/*  844 */       } else if (getAnnotStyle().getFont().hasFontName().booleanValue()) {
/*      */         
/*  846 */         for (int i = 0; i < this.mFontAdapter.getData().size(); i++) {
/*  847 */           if (((FontResource)this.mFontAdapter.getData().get(i)).getFontName().equals(getAnnotStyle().getFont().getFontName())) {
/*  848 */             this.mFontSpinner.setSelection(i);
/*  849 */             matchFound = true;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*  855 */       if (!matchFound) {
/*  856 */         this.mFontSpinner.setSelection(0);
/*      */       } else {
/*  858 */         int index = this.mFontSpinner.getSelectedItemPosition();
/*  859 */         FontResource fontResource = (FontResource)this.mFontAdapter.getItem(index);
/*  860 */         if (fontResource != null && !Utils.isNullOrEmpty(fontResource.getFilePath())) {
/*  861 */           this.mAnnotStyleHolder.getAnnotPreview().setFontPath(fontResource.getFilePath());
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setPreviewThickness() {
/*  868 */     setPreviewThickness(getAnnotStyle().getThickness());
/*      */   }
/*      */   
/*      */   private void setPreviewThickness(float thickness) {
/*  872 */     this.mAnnotStyleHolder.getAnnotPreview().updateFillPreview(getAnnotStyle().getColor(), getAnnotStyle().getFillColor(), thickness, getAnnotStyle().getOpacity());
/*      */   }
/*      */   
/*      */   private void setPreviewOpacity() {
/*  876 */     setPreviewOpacity(getAnnotStyle().getOpacity());
/*      */   }
/*      */   
/*      */   private void setPreviewOpacity(float opacity) {
/*  880 */     this.mAnnotStyleHolder.getAnnotPreview().updateFillPreview(getAnnotStyle().getColor(), getAnnotStyle().getFillColor(), 
/*  881 */         getAnnotStyle().getThickness(), opacity);
/*      */     
/*  883 */     if (getAnnotStyle().isStickyNote()) {
/*  884 */       this.mIconAdapter.updateIconOpacity(opacity);
/*      */     }
/*      */   }
/*      */   
/*      */   private void setPreviewTextSize() {
/*  889 */     setPreviewTextSize(getAnnotStyle().getTextSize());
/*      */   }
/*      */   
/*      */   private void setPreviewTextSize(float textSize) {
/*  893 */     this.mAnnotStyleHolder.getAnnotPreview().updateFreeTextStyle(getAnnotStyle().getTextColor(), textSize / this.mMaxTextSize);
/*      */   }
/*      */   
/*      */   private void updateUIVisibility() {
/*  897 */     if (this.mRCEnableSwitch.isChecked()) {
/*  898 */       this.mMoreToolLayout.setVisibility(8);
/*      */     } else {
/*  900 */       this.mMoreToolLayout.setVisibility((this.mMoreAnnotTypes == null || this.mMoreAnnotTypes.isEmpty()) ? 8 : 0);
/*      */     } 
/*  902 */     this.mStrokeLayout.setVisibility(getAnnotStyle().hasColor() ? 0 : 8);
/*  903 */     this.mFillLayout.setVisibility(getAnnotStyle().hasFillColor() ? 0 : 8);
/*  904 */     this.mThicknessLayout.setVisibility(getAnnotStyle().hasThickness() ? 0 : 8);
/*  905 */     this.mOpacityLayout.setVisibility(getAnnotStyle().hasOpacity() ? 0 : 8);
/*  906 */     this.mFontLayout.setVisibility(getAnnotStyle().hasFont() ? 0 : 8);
/*  907 */     this.mIconLayout.setVisibility(getAnnotStyle().isStickyNote() ? 0 : 8);
/*  908 */     if (this.mIconExpanded) {
/*  909 */       this.mIconExpandableGridView.setVisibility(getAnnotStyle().isStickyNote() ? 0 : 8);
/*      */     }
/*  911 */     this.mTextSizeLayout.setVisibility(getAnnotStyle().hasTextStyle() ? 0 : 8);
/*  912 */     this.mTextColorLayout.setVisibility(getAnnotStyle().hasTextStyle() ? 0 : 8);
/*  913 */     this.mRulerUnitLayout.setVisibility(getAnnotStyle().isMeasurement() ? 0 : 8);
/*  914 */     this.mRulerPrecisionLayout.setVisibility(getAnnotStyle().isMeasurement() ? 0 : 8);
/*  915 */     this.mSnapLayout.setVisibility(getAnnotStyle().isMeasurement() ? 0 : 8);
/*  916 */     if (this.mCanShowRCOption) {
/*  917 */       this.mRCEnableLayout.setVisibility((getAnnotStyle().isFreeText() && !getAnnotStyle().isCallout()) ? 0 : 8);
/*      */     } else {
/*  919 */       this.mRCEnableLayout.setVisibility(8);
/*      */     } 
/*  921 */     this.mTextOverlayLayout.setVisibility((getAnnotStyle().isRedaction() || getAnnotStyle().isWatermark()) ? 0 : 8);
/*  922 */     this.mPresetContainer.setVisibility(!getAnnotStyle().isWatermark() ? 0 : 8);
/*  923 */     this.mEraserTypeLayout.setVisibility(getAnnotStyle().isEraser() ? 0 : 8);
/*  924 */     this.mInkEraserModeLayout.setVisibility(getAnnotStyle().isEraser() ? 0 : 8);
/*  925 */     this.mDateFormatLayout.setVisibility(getAnnotStyle().isDateFreeText() ? 0 : 8);
/*  926 */     if (this.mCanShowPressureOption) {
/*  927 */       this.mPressureSensitiveLayout.setVisibility(getAnnotStyle().hasPressureSensitivity() ? 0 : 8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnColorLayoutClickedListener(OnColorLayoutClickedListener listener) {
/*  937 */     this.mColorLayoutClickedListener = listener;
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
/*      */   public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/*  950 */     if (actionId == 6) {
/*  951 */       Utils.hideSoftKeyboard(getContext(), (View)v);
/*  952 */       v.clearFocus();
/*  953 */       if (v.getId() == this.mOverlayEditText.getId()) {
/*  954 */         Editable s = this.mOverlayEditText.getText();
/*  955 */         getAnnotStyle().setOverlayText(s.toString());
/*      */       } else {
/*  957 */         this.mAnnotStyleHolder.getAnnotPreview().requestFocus();
/*      */       } 
/*  959 */       return true;
/*      */     } 
/*  961 */     return false;
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
/*      */   public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
/*  973 */     if (this.mTextJustChanged) {
/*  974 */       this.mTextJustChanged = false;
/*      */       return;
/*      */     } 
/*  977 */     if (seekBar.getId() == this.mThicknessSeekbar.getId()) {
/*  978 */       float thickness = (this.mMaxThickness - this.mMinThickness) * progress / 100.0F + this.mMinThickness;
/*  979 */       getAnnotStyle().setThickness(thickness, false);
/*  980 */       this.mThicknessEditText.setText(String.format(getContext().getString(R.string.tools_misc_thickness), new Object[] { Float.valueOf(thickness) }));
/*      */       
/*  982 */       setPreviewThickness(thickness);
/*  983 */     } else if (seekBar.getId() == this.mOpacitySeekbar.getId()) {
/*  984 */       float opacity = progress / 100.0F;
/*  985 */       getAnnotStyle().setOpacity(opacity, false);
/*  986 */       this.mOpacityEditText.setText(String.valueOf(progress));
/*      */       
/*  988 */       setPreviewOpacity(opacity);
/*  989 */     } else if (seekBar.getId() == this.mTextSizeSeekbar.getId()) {
/*  990 */       int textSize = Math.round((this.mMaxTextSize - this.mMinTextSize) * progress / 100.0F + this.mMinTextSize);
/*  991 */       getAnnotStyle().setTextSize(textSize, false);
/*  992 */       this.mTextSizeEditText.setText(getContext().getString(R.string.tools_misc_textsize, new Object[] { Integer.valueOf(textSize) }));
/*  993 */       setPreviewTextSize(textSize);
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
/*      */   public void onStartTrackingTouch(SeekBar seekBar) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStopTrackingTouch(SeekBar seekBar) {
/* 1014 */     int progress = seekBar.getProgress();
/* 1015 */     if (seekBar.getId() == this.mThicknessSeekbar.getId()) {
/* 1016 */       float thickness = (this.mMaxThickness - this.mMinThickness) * progress / 100.0F + this.mMinThickness;
/* 1017 */       getAnnotStyle().setThickness(thickness);
/* 1018 */       this.mThicknessEditText.setText(String.format(getContext().getString(R.string.tools_misc_thickness), new Object[] { Float.valueOf(thickness) }));
/*      */       
/* 1020 */       setPreviewThickness();
/*      */       
/* 1022 */       AnalyticsAnnotStylePicker.getInstance().selectThickness(thickness);
/* 1023 */     } else if (seekBar.getId() == this.mOpacitySeekbar.getId()) {
/* 1024 */       getAnnotStyle().setOpacity(progress / 100.0F);
/* 1025 */       this.mOpacityEditText.setText(String.valueOf(progress));
/*      */       
/* 1027 */       setPreviewOpacity();
/*      */       
/* 1029 */       AnalyticsAnnotStylePicker.getInstance().selectOpacity(getAnnotStyle().getOpacity());
/* 1030 */     } else if (seekBar.getId() == this.mTextSizeSeekbar.getId()) {
/* 1031 */       int textSize = Math.round((this.mMaxTextSize - this.mMinTextSize) * progress / 100.0F + this.mMinTextSize);
/* 1032 */       getAnnotStyle().setTextSize(textSize);
/* 1033 */       this.mTextSizeEditText.setText(getContext().getString(R.string.tools_misc_textsize, new Object[] { Integer.valueOf(textSize) }));
/* 1034 */       setPreviewTextSize();
/*      */       
/* 1036 */       AnalyticsAnnotStylePicker.getInstance().selectTextSize(textSize);
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
/*      */   public void onFocusChange(View v, boolean hasFocus) {
/* 1050 */     this.mTextJustChanged = true;
/* 1051 */     if (v.getId() == this.mThicknessEditText.getId()) {
/* 1052 */       if (!hasFocus && this.mPrevThicknessFocus) {
/* 1053 */         Editable s = this.mThicknessEditText.getText();
/* 1054 */         String number = s.toString();
/*      */         
/*      */         try {
/* 1057 */           number = number.replace(",", ".");
/* 1058 */           float value = Float.valueOf(number).floatValue();
/*      */           
/* 1060 */           if (value > getAnnotStyle().getMaxInternalThickness()) {
/* 1061 */             value = getAnnotStyle().getMaxInternalThickness();
/* 1062 */             this.mThicknessEditText.setText(getContext().getString(R.string.tools_misc_thickness, new Object[] { Float.valueOf(value) }));
/*      */           } 
/* 1064 */           getAnnotStyle().setThickness(value);
/* 1065 */           int progress = Math.round(getAnnotStyle().getThickness() / (this.mMaxThickness - this.mMinThickness) * 100.0F);
/* 1066 */           this.mThicknessSeekbar.setProgress(progress);
/* 1067 */           setPreviewThickness();
/* 1068 */           AnalyticsAnnotStylePicker.getInstance().selectThickness(value);
/* 1069 */         } catch (Exception e) {
/* 1070 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "annot style invalid number");
/* 1071 */           CommonToast.showText(getContext(), R.string.invalid_number);
/*      */         } 
/*      */       } 
/* 1074 */       this.mPrevThicknessFocus = hasFocus;
/* 1075 */     } else if (v.getId() == this.mOpacityEditText.getId()) {
/* 1076 */       if (!hasFocus && this.mPrevOpacityFocus) {
/*      */         try {
/* 1078 */           float value = Float.valueOf(this.mOpacityEditText.getText().toString()).floatValue();
/* 1079 */           if (value > 100.0F) {
/* 1080 */             value = 100.0F;
/* 1081 */             this.mOpacityEditText.setText(String.valueOf(value));
/*      */           } 
/* 1083 */           getAnnotStyle().setOpacity(value / 100.0F);
/* 1084 */           this.mOpacitySeekbar.setProgress((int)value);
/* 1085 */           setPreviewOpacity();
/* 1086 */           AnalyticsAnnotStylePicker.getInstance().selectThickness(getAnnotStyle().getOpacity());
/* 1087 */         } catch (Exception e) {
/* 1088 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "annot style invalid number");
/* 1089 */           CommonToast.showText(getContext(), R.string.invalid_number);
/*      */         } 
/*      */       }
/* 1092 */       this.mPrevOpacityFocus = hasFocus;
/* 1093 */     } else if (v.getId() == this.mTextSizeEditText.getId() && !hasFocus) {
/* 1094 */       Editable s = this.mTextSizeEditText.getText();
/* 1095 */       String number = s.toString();
/*      */       try {
/* 1097 */         float value = Float.valueOf(number).floatValue();
/* 1098 */         value = Math.round(value);
/* 1099 */         getAnnotStyle().setTextSize(value);
/* 1100 */         int progress = Math.round(getAnnotStyle().getTextSize() / (this.mMaxTextSize - this.mMinTextSize) * 100.0F);
/* 1101 */         this.mTextSizeSeekbar.setProgress(progress);
/* 1102 */         setPreviewTextSize();
/* 1103 */         AnalyticsAnnotStylePicker.getInstance().selectThickness(value);
/* 1104 */       } catch (Exception e) {
/* 1105 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "annot style invalid number");
/* 1106 */         CommonToast.showText(getContext(), R.string.invalid_number);
/*      */       } 
/* 1108 */     } else if (v.getId() == this.mRulerBaseEditText.getId() && !hasFocus) {
/* 1109 */       Editable s = this.mRulerBaseEditText.getText();
/* 1110 */       String number = s.toString();
/*      */       try {
/* 1112 */         float value = Float.valueOf(number).floatValue();
/* 1113 */         if (value < 0.1D) {
/* 1114 */           value = 0.1F;
/* 1115 */           this.mRulerBaseEditText.setText("0.1");
/*      */         } 
/* 1117 */         getAnnotStyle().setRulerBaseValue(value);
/* 1118 */         AnalyticsAnnotStylePicker.getInstance().selectRulerBaseValue(value);
/* 1119 */       } catch (Exception e) {
/* 1120 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "annot style invalid number");
/* 1121 */         CommonToast.showText(getContext(), R.string.invalid_number);
/*      */       } 
/* 1123 */     } else if (v.getId() == this.mRulerTranslateEditText.getId() && !hasFocus) {
/* 1124 */       Editable s = this.mRulerTranslateEditText.getText();
/* 1125 */       String number = s.toString();
/*      */       try {
/* 1127 */         float value = Float.valueOf(number).floatValue();
/* 1128 */         if (value < 0.1D) {
/* 1129 */           value = 0.1F;
/* 1130 */           this.mRulerTranslateEditText.setText("0.1");
/*      */         } 
/* 1132 */         getAnnotStyle().setRulerTranslateValue(value);
/* 1133 */         AnalyticsAnnotStylePicker.getInstance().selectRulerTranslateValue(value);
/* 1134 */       } catch (Exception e) {
/* 1135 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "annot style invalid number");
/* 1136 */         CommonToast.showText(getContext(), R.string.invalid_number);
/*      */       } 
/* 1138 */     } else if (v.getId() == this.mOverlayEditText.getId() && !hasFocus) {
/* 1139 */       Editable s = this.mOverlayEditText.getText();
/* 1140 */       getAnnotStyle().setOverlayText(s.toString());
/*      */     } 
/* 1142 */     if (!hasFocus) {
/* 1143 */       Utils.hideSoftKeyboard(getContext(), v);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClick(View v) {
/* 1154 */     if (v.getId() == this.mThicknessValueGroup.getId()) {
/* 1155 */       Utils.showSoftKeyboard(getContext(), (View)this.mThicknessEditText);
/* 1156 */       this.mThicknessEditText.requestFocus();
/* 1157 */     } else if (v.getId() == this.mOpacityValueGroup.getId()) {
/* 1158 */       Utils.showSoftKeyboard(getContext(), (View)this.mOpacityTextView);
/* 1159 */       this.mOpacityEditText.requestFocus();
/* 1160 */     } else if (v.getId() == this.mIconLayout.getId()) {
/* 1161 */       boolean isGridViewVisible = (this.mIconExpandableGridView.getVisibility() == 0);
/* 1162 */       this.mIconExpandableGridView.setVisibility(isGridViewVisible ? 8 : 0);
/* 1163 */       this.mIconExpandableBtn.setImageResource(isGridViewVisible ? R.drawable.ic_chevron_right_black_24dp : R.drawable.ic_arrow_down_white_24dp);
/*      */       
/* 1165 */       this.mIconExpanded = (this.mIconExpandableGridView.getVisibility() == 0);
/* 1166 */     } else if (v.getId() == this.mStrokeLayout.getId() && this.mColorLayoutClickedListener != null) {
/* 1167 */       int colorMode = getAnnotStyle().hasFillColor() ? 0 : 3;
/* 1168 */       this.mColorLayoutClickedListener.onColorLayoutClicked(colorMode);
/* 1169 */     } else if (v.getId() == this.mTextColorLayout.getId() && this.mColorLayoutClickedListener != null) {
/* 1170 */       this.mColorLayoutClickedListener.onColorLayoutClicked(2);
/* 1171 */     } else if (v.getId() == this.mFillLayout.getId() && this.mColorLayoutClickedListener != null) {
/* 1172 */       this.mColorLayoutClickedListener.onColorLayoutClicked(1);
/* 1173 */     } else if (v.getId() == this.mSnapLayout.getId()) {
/* 1174 */       this.mSnapSwitch.toggle();
/* 1175 */       getAnnotStyle().setSnap(this.mSnapSwitch.isChecked());
/* 1176 */     } else if (v.getId() == this.mRCEnableLayout.getId()) {
/* 1177 */       this.mRCEnableSwitch.toggle();
/* 1178 */       if (this.mRCEnableSwitch.isChecked()) {
/* 1179 */         getAnnotStyle().setTextHTMLContent("rc");
/*      */       } else {
/* 1181 */         getAnnotStyle().setTextHTMLContent("");
/*      */       } 
/* 1183 */       AnalyticsAnnotStylePicker.getInstance().setRichTextEnabled(this.mRCEnableSwitch.isChecked());
/* 1184 */       updateUIVisibility();
/* 1185 */     } else if (v.getId() == this.mEraserTypeLayout.getId()) {
/* 1186 */       this.mEraserTypeSwitch.toggle();
/* 1187 */       getAnnotStyle().setEraserType(this.mEraserTypeSwitch.isChecked() ? Eraser.EraserType.INK_ERASER : Eraser.EraserType.HYBRID_ERASER);
/* 1188 */       AnalyticsAnnotStylePicker.getInstance().setEraseInkOnlyEnabled(this.mEraserTypeSwitch.isChecked());
/* 1189 */     } else if (v.getId() == this.mPressureSensitiveLayout.getId()) {
/* 1190 */       this.mPressureSensitiveSwitch.toggle();
/* 1191 */       getAnnotStyle().setPressureSensitivity(this.mPressureSensitiveSwitch.isChecked());
/* 1192 */       AnalyticsAnnotStylePicker.getInstance().setPressureSensitiveEnabled(this.mPressureSensitiveSwitch.isChecked());
/*      */     } else {
/* 1194 */       for (int i = 0; i < 4; i++) {
/* 1195 */         AnnotationPropertyPreviewView annotationPropertyPreviewView = this.mPresetViews[i];
/* 1196 */         AnnotStyle presetStyle = this.mPresetStyles[i];
/* 1197 */         if (v.getId() == annotationPropertyPreviewView.getId() && this.mPresetSelectedListner != null) {
/* 1198 */           if (v.isSelected()) {
/* 1199 */             this.mPresetSelectedListner.onPresetDeselected(presetStyle);
/* 1200 */             AnalyticsAnnotStylePicker.getInstance().deselectPreset(i);
/*      */           } else {
/* 1202 */             this.mPresetSelectedListner.onPresetSelected(presetStyle);
/* 1203 */             AnalyticsAnnotStylePicker.getInstance().selectPreset(i, isAnnotStyleInDefaults(presetStyle));
/*      */             break;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isAnnotStyleInDefaults(AnnotStyle annotStyle) {
/* 1212 */     for (int i = 0; i < 4; i++) {
/* 1213 */       AnnotStyle defaultStyle = ToolStyleConfig.getInstance().getDefaultAnnotPresetStyle(
/* 1214 */           getContext(), this.mAnnotType, i, ToolStyleConfig.getInstance().getPresetsAttr(this.mAnnotType), 
/* 1215 */           ToolStyleConfig.getInstance().getDefaultPresetsArrayRes(this.mAnnotType));
/* 1216 */       if (defaultStyle.equals(annotStyle)) {
/* 1217 */         return true;
/*      */       }
/*      */     } 
/* 1220 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateLayout() {
/* 1227 */     if (getAnnotStyle().isFreeText()) {
/* 1228 */       this.mFillColorTextView.setText(R.string.pref_colormode_custom_bg_color);
/* 1229 */     } else if (!getAnnotStyle().hasFillColor()) {
/* 1230 */       this.mStrokeColorTextView.setText(R.string.tools_qm_color);
/*      */     } else {
/* 1232 */       this.mStrokeColorTextView.setText(R.string.tools_qm_stroke_color);
/*      */     } 
/* 1234 */     updateUIVisibility();
/* 1235 */     initLayoutStyle();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
/* 1240 */     super.onVisibilityChanged(changedView, visibility);
/* 1241 */     if (visibility == 0) {
/* 1242 */       updateLayout();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void savePresets() {
/* 1250 */     for (int i = 0; i < 4; i++) {
/* 1251 */       AnnotStyle preset = this.mPresetStyles[i];
/* 1252 */       PdfViewCtrlSettingsManager.setAnnotStylePreset(getContext(), this.mAnnotType, i, preset.toJSONString());
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
/*      */   @SuppressLint({"SwitchIntDef"})
/*      */   @ColorInt
/*      */   public int getColor(int colorMode) {
/* 1266 */     switch (colorMode) {
/*      */       case 1:
/* 1268 */         return getAnnotStyle().getFillColor();
/*      */     } 
/* 1270 */     return getAnnotStyle().getColor();
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
/*      */   public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 1285 */     String item = this.mIconAdapter.getItem(position);
/* 1286 */     this.mIconAdapter.setSelected(position);
/* 1287 */     setIcon(item);
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
/*      */   public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 1300 */     if (parent.getId() == this.mFontSpinner.getId()) {
/* 1301 */       if (position >= 0 && this.mFontAdapter != null) {
/* 1302 */         FontResource font = (FontResource)this.mFontAdapter.getItem(position);
/* 1303 */         if (font != null && !this.mInitSpinner) {
/* 1304 */           setFont(font);
/* 1305 */         } else if (this.mInitSpinner) {
/* 1306 */           this.mInitSpinner = false;
/*      */         } 
/*      */       } 
/* 1309 */     } else if (parent.getId() == this.mRulerBaseSpinner.getId()) {
/* 1310 */       if (position >= 0 && this.mRulerBaseSpinnerAdapter != null) {
/* 1311 */         CharSequence unit = (CharSequence)this.mRulerBaseSpinnerAdapter.getItem(position);
/* 1312 */         if (unit != null) {
/* 1313 */           getAnnotStyle().setRulerBaseUnit(unit.toString());
/*      */         }
/*      */       } 
/* 1316 */     } else if (parent.getId() == this.mRulerTranslateSpinner.getId()) {
/* 1317 */       if (position >= 0 && this.mRulerTranslateSpinnerAdapter != null) {
/* 1318 */         CharSequence unit = (CharSequence)this.mRulerTranslateSpinnerAdapter.getItem(position);
/* 1319 */         if (unit != null) {
/* 1320 */           getAnnotStyle().setRulerTranslateUnit(unit.toString());
/*      */         }
/*      */       } 
/* 1323 */     } else if (parent.getId() == this.mRulerPrecisionSpinner.getId()) {
/* 1324 */       if (position >= 0 && this.mRulerPrecisionSpinnerAdapter != null) {
/* 1325 */         CharSequence which = (CharSequence)this.mRulerPrecisionSpinnerAdapter.getItem(position);
/* 1326 */         if (which != null) {
/* 1327 */           getAnnotStyle().setRulerPrecision(MeasureUtils.getPrecision(which.toString()));
/*      */         }
/*      */       } 
/* 1330 */     } else if (parent.getId() == this.mDateFormatSpinner.getId()) {
/* 1331 */       if (position >= 0 && this.mDateFormatSpinnerAdapter != null) {
/* 1332 */         CharSequence[] strings = getContext().getResources().getTextArray(R.array.style_picker_date_formats);
/* 1333 */         CharSequence format = strings[position];
/* 1334 */         if (format != null) {
/* 1335 */           getAnnotStyle().setDateFormat(format.toString());
/*      */         }
/*      */       } 
/* 1338 */     } else if (parent.getId() == this.mInkEraserModeSpinner.getId() && 
/* 1339 */       position >= 0 && this.mInkEraserModeAdapter != null) {
/* 1340 */       CharSequence[] strings = getContext().getResources().getTextArray(R.array.style_ink_eraser_mode);
/* 1341 */       Eraser.InkEraserMode mode = Eraser.InkEraserMode.fromLabel(getContext(), strings[position].toString());
/* 1342 */       if (mode != null) {
/* 1343 */         getAnnotStyle().setInkEraserMode(mode);
/*      */       }
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
/*      */   public void onNothingSelected(AdapterView<?> parent) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnPresetSelectedListener(OnPresetSelectedListener listener) {
/* 1365 */     this.mPresetSelectedListner = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOnMoreAnnotTypesClickListener(OnMoreAnnotTypeClickedListener listener) {
/* 1374 */     this.mMoreAnnotTypeListener = listener;
/*      */   }
/*      */   
/*      */   private AppCompatImageButton getAnnotTypeButtonForTool(int annotType) {
/* 1378 */     AppCompatImageButton imageButton = new AppCompatImageButton(getContext());
/* 1379 */     imageButton.setImageResource(AnnotUtils.getAnnotImageResId(annotType));
/* 1380 */     imageButton.setBackgroundResource(R.drawable.annot_property_preview_bg);
/*      */     
/* 1382 */     imageButton.setAlpha(0.54F);
/* 1383 */     imageButton.setColorFilter(Utils.getThemeAttrColor(getContext(), 16842806));
/* 1384 */     String text = AnnotUtils.getAnnotTypeAsString(getContext(), annotType);
/* 1385 */     TooltipCompat.setTooltipText((View)imageButton, text);
/* 1386 */     imageButton.setContentDescription(text);
/* 1387 */     imageButton.setLayoutParams((ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(
/*      */           
/* 1389 */           getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size), 
/* 1390 */           getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size)));
/* 1391 */     imageButton.setPadding(getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), 
/* 1392 */         getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), 
/* 1393 */         getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding), 
/* 1394 */         getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_padding));
/* 1395 */     if (annotType == getAnnotStyle().getAnnotType()) {
/* 1396 */       imageButton.setSelected(true);
/*      */     }
/* 1398 */     return imageButton;
/*      */   }
/*      */   
/*      */   public static interface OnColorLayoutClickedListener {
/*      */     void onColorLayoutClicked(int param1Int);
/*      */   }
/*      */   
/*      */   public static interface OnPresetSelectedListener {
/*      */     void onPresetSelected(AnnotStyle param1AnnotStyle);
/*      */     
/*      */     void onPresetDeselected(AnnotStyle param1AnnotStyle);
/*      */   }
/*      */   
/*      */   public static interface OnMoreAnnotTypeClickedListener {
/*      */     void onAnnotTypeClicked(int param1Int);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AnnotStyleView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */