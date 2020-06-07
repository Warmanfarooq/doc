/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Color;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsAnnotStylePicker;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.AnnotationPropertyPreviewView;
/*     */ import com.pdftron.pdf.utils.ColorPickerGridViewAdapter;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.CustomViewPager;
/*     */ import java.util.ArrayList;
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
/*     */ public class ColorPickerView
/*     */   extends LinearLayout
/*     */   implements CustomColorPickerView.OnEditFavoriteColorListener
/*     */ {
/*     */   private LinearLayout mToolbar;
/*     */   private ImageButton mBackButton;
/*     */   private ImageButton mArrowBackward;
/*     */   private ImageButton mArrowForward;
/*     */   private TextView mToolbarTitle;
/*     */   private ImageButton mEditButton;
/*     */   private ImageButton mRemoveButton;
/*     */   private ImageButton mAddFavButton;
/*     */   private CustomViewPager mColorPager;
/*     */   private ColorPagerAdapter mColorPagerAdapter;
/*     */   private CharSequence mToolbarText;
/*     */   private PresetColorGridView mPresetColorView;
/*     */   private CustomColorPickerView mCustomColorView;
/*     */   private AdvancedColorView mAdvancedColorView;
/*     */   private TabLayout mPagerIndicator;
/*     */   private String mLatestAdvancedColor;
/*     */   private AnnotStyle.AnnotStyleHolder mAnnotStyleHolder;
/*     */   private OnBackButtonPressedListener mBackPressedListener;
/*  70 */   private int mColorMode = 3;
/*     */ 
/*     */   
/*     */   private ArrayList<String> mSelectedAddFavoriteColors;
/*     */ 
/*     */   
/*     */   private ColorPickerGridViewAdapter mAddFavoriteAdapter;
/*     */ 
/*     */   
/*     */   public ColorPickerView(Context context) {
/*  80 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorPickerView(Context context, @Nullable AttributeSet attrs) {
/*  87 */     this(context, attrs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  94 */     super(context, attrs, defStyleAttr);
/*  95 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  99 */     LayoutInflater.from(getContext()).inflate(R.layout.color_picker_layout, (ViewGroup)this);
/* 100 */     setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/* 101 */     setOrientation(1);
/* 102 */     this.mToolbar = (LinearLayout)findViewById(R.id.toolbar);
/* 103 */     this.mBackButton = (ImageButton)findViewById(R.id.back_btn);
/* 104 */     this.mBackButton.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 107 */             ColorPickerView.this.onBackButtonPressed();
/*     */           }
/*     */         });
/* 110 */     this.mArrowBackward = (ImageButton)findViewById(R.id.nav_backward);
/* 111 */     this.mArrowBackward.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 114 */             if (ColorPickerView.this.mColorPager == null) {
/*     */               return;
/*     */             }
/* 117 */             int index = ColorPickerView.this.mColorPager.getCurrentItem();
/* 118 */             int prevIndex = index - 1;
/* 119 */             index = Math.max(0, prevIndex);
/* 120 */             ColorPickerView.this.mColorPager.setCurrentItem(index);
/* 121 */             ColorPickerView.this.setArrowVisibility(index);
/*     */           }
/*     */         });
/* 124 */     this.mArrowForward = (ImageButton)findViewById(R.id.nav_forward);
/* 125 */     this.mArrowForward.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 128 */             if (ColorPickerView.this.mColorPager == null || ColorPickerView.this.mColorPagerAdapter == null) {
/*     */               return;
/*     */             }
/* 131 */             int index = ColorPickerView.this.mColorPager.getCurrentItem();
/* 132 */             int nextIndex = index + 1;
/* 133 */             int lastIndex = ColorPickerView.this.mColorPagerAdapter.getCount() - 1;
/* 134 */             index = Math.min(lastIndex, nextIndex);
/* 135 */             ColorPickerView.this.mColorPager.setCurrentItem(index);
/* 136 */             ColorPickerView.this.setArrowVisibility(index);
/*     */           }
/*     */         });
/* 139 */     this.mToolbarTitle = (TextView)findViewById(R.id.toolbar_title);
/* 140 */     this.mColorPager = (CustomViewPager)findViewById(R.id.color_pager);
/* 141 */     this.mPagerIndicator = (TabLayout)findViewById(R.id.pager_indicator_tabs);
/* 142 */     this.mPagerIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener()
/*     */         {
/*     */           public void onTabSelected(TabLayout.Tab tab) {
/* 145 */             ColorPickerView.this.setArrowVisibility(tab.getPosition());
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTabUnselected(TabLayout.Tab tab) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTabReselected(TabLayout.Tab tab) {}
/*     */         });
/* 159 */     this.mPresetColorView = new PresetColorGridView(getContext());
/* 160 */     this.mAdvancedColorView = new AdvancedColorView(getContext());
/* 161 */     this.mCustomColorView = new CustomColorPickerView(getContext());
/* 162 */     this.mRemoveButton = (ImageButton)this.mToolbar.findViewById(R.id.remove_btn);
/* 163 */     this.mEditButton = (ImageButton)this.mToolbar.findViewById(R.id.edit_btn);
/* 164 */     this.mAddFavButton = (ImageButton)this.mToolbar.findViewById(R.id.fav_btn);
/* 165 */     this.mEditButton.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 168 */             ColorPickerView.this.mCustomColorView.editSelectedColor();
/*     */           }
/*     */         });
/* 171 */     this.mRemoveButton.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 174 */             ColorPickerView.this.mCustomColorView.deleteAllSelectedFavColors();
/*     */           }
/*     */         });
/* 177 */     this.mAddFavButton.setOnClickListener(new OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 180 */             ColorPickerView.this.addColorsToFavorites();
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 186 */     MarginLayoutParams mlp = new MarginLayoutParams(-1, -1);
/*     */     
/* 188 */     this.mPresetColorView.setLayoutParams((ViewGroup.LayoutParams)mlp);
/* 189 */     this.mPresetColorView.setClipToPadding(false);
/*     */     
/* 191 */     this.mColorPagerAdapter = new ColorPagerAdapter();
/* 192 */     this.mColorPager.setAdapter(this.mColorPagerAdapter);
/* 193 */     int currentItem = PdfViewCtrlSettingsManager.getColorPickerPage(getContext());
/* 194 */     this.mColorPager.setCurrentItem(currentItem);
/* 195 */     this.mPagerIndicator.setupWithViewPager((ViewPager)this.mColorPager, true);
/* 196 */     setArrowVisibility(currentItem);
/*     */ 
/*     */     
/* 199 */     this.mAdvancedColorView.setOnColorChangeListener(new OnColorChangeListener()
/*     */         {
/*     */           public void OnColorChanged(View view, int color) {
/* 202 */             ColorPickerView.this.onColorChanged(view, color);
/*     */           }
/*     */         });
/* 205 */     this.mCustomColorView.setOnColorChangeListener(new OnColorChangeListener()
/*     */         {
/*     */           public void OnColorChanged(View view, int color) {
/* 208 */             ColorPickerView.this.onColorChanged(view, color);
/*     */           }
/*     */         });
/* 211 */     this.mCustomColorView.setOnEditFavoriteColorlistener(this);
/* 212 */     this.mCustomColorView.setRecentColorLongPressListener(new AdapterView.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/* 215 */             return ColorPickerView.this.onColorItemLongClickListener(parent, position);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnotStyleHolder(AnnotStyle.AnnotStyleHolder annotStyleHolder) {
/* 226 */     this.mAnnotStyleHolder = annotStyleHolder;
/*     */   }
/*     */   
/*     */   private AnnotStyle getAnnotStyle() {
/* 230 */     return this.mAnnotStyleHolder.getAnnotStyle();
/*     */   }
/*     */   
/*     */   private AnnotationPropertyPreviewView getAnnotStylePreview() {
/* 234 */     return this.mAnnotStyleHolder.getAnnotPreview();
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
/*     */   public void show(int colorMode) {
/*     */     boolean presetShowTransparent;
/* 247 */     AnalyticsAnnotStylePicker.getInstance().setSelectedColorMode(colorMode);
/* 248 */     this.mColorMode = colorMode;
/* 249 */     getAnnotStylePreview().setAnnotType(getAnnotStyle().getAnnotType());
/*     */     
/* 251 */     getAnnotStylePreview().updateFillPreview(getAnnotStyle());
/*     */ 
/*     */ 
/*     */     
/* 255 */     switch (colorMode) {
/*     */       case 0:
/* 257 */         presetShowTransparent = true;
/* 258 */         setSelectedColor(getAnnotStyle().getColor());
/* 259 */         this.mToolbarTitle.setText(R.string.tools_qm_stroke_color);
/*     */         break;
/*     */       case 1:
/* 262 */         presetShowTransparent = true;
/* 263 */         setSelectedColor(getAnnotStyle().getFillColor());
/* 264 */         if (getAnnotStyle().isFreeText()) {
/* 265 */           this.mToolbarTitle.setText(R.string.pref_colormode_custom_bg_color); break;
/*     */         } 
/* 267 */         this.mToolbarTitle.setText(R.string.tools_qm_fill_color);
/*     */         break;
/*     */       
/*     */       case 2:
/* 271 */         presetShowTransparent = false;
/* 272 */         setSelectedColor(getAnnotStyle().getTextColor());
/* 273 */         this.mToolbarTitle.setText(R.string.pref_colormode_custom_text_color);
/*     */         break;
/*     */       
/*     */       default:
/* 277 */         presetShowTransparent = getAnnotStyle().hasFillColor();
/* 278 */         setSelectedColor(getAnnotStyle().getColor());
/* 279 */         this.mToolbarTitle.setText(R.string.tools_qm_color);
/*     */         break;
/*     */     } 
/* 282 */     this.mPresetColorView.showTransparentColor(presetShowTransparent);
/* 283 */     this.mPresetColorView.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/* 286 */             ColorPickerView.this.onPresetColorGridItemClicked(parent, position);
/*     */           }
/*     */         });
/* 289 */     this.mPresetColorView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
/* 292 */             return ColorPickerView.this.onColorItemLongClickListener(parent, position);
/*     */           }
/*     */         });
/* 295 */     this.mToolbarText = this.mToolbarTitle.getText();
/*     */     
/* 297 */     setVisibility(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 304 */     setVisibility(8);
/*     */   }
/*     */   
/*     */   private void onBackButtonPressed() {
/* 308 */     if (this.mCustomColorView.onBackButonPressed()) {
/*     */       return;
/*     */     }
/* 311 */     if (this.mSelectedAddFavoriteColors != null && !this.mSelectedAddFavoriteColors.isEmpty()) {
/* 312 */       this.mSelectedAddFavoriteColors.clear();
/* 313 */       if (this.mAddFavoriteAdapter != null) {
/* 314 */         this.mAddFavoriteAdapter.notifyDataSetChanged();
/*     */       }
/* 316 */       toggleFavoriteToolbar();
/*     */       return;
/*     */     } 
/* 319 */     if (!Utils.isNullOrEmpty(this.mLatestAdvancedColor)) {
/* 320 */       this.mCustomColorView.addRecentColorSource(this.mLatestAdvancedColor);
/* 321 */       AnalyticsAnnotStylePicker.getInstance().selectColor(this.mLatestAdvancedColor.toUpperCase(), 4);
/*     */     } 
/* 323 */     if (this.mBackPressedListener != null) {
/* 324 */       this.mBackPressedListener.onBackPressed();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(@ColorInt int color) {
/* 335 */     this.mAdvancedColorView.setSelectedColor(color);
/* 336 */     this.mPresetColorView.setSelectedColor(color);
/* 337 */     this.mCustomColorView.setSelectedColor(Utils.getColorHexString(color));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnBackButtonPressedListener(OnBackButtonPressedListener listener) {
/* 346 */     this.mBackPressedListener = listener;
/*     */   }
/*     */   
/*     */   private void onColorChanged(View view, @ColorInt int color) {
/* 350 */     switch (this.mColorMode) {
/*     */       case 1:
/* 352 */         getAnnotStyle().setFillColor(color);
/*     */         break;
/*     */       case 2:
/* 355 */         getAnnotStyle().setTextColor(color);
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 360 */         getAnnotStyle().setStrokeColor(color);
/*     */         break;
/*     */     } 
/* 363 */     getAnnotStylePreview().updateFillPreview(getAnnotStyle());
/* 364 */     String colorStr = Utils.getColorHexString(color);
/* 365 */     if (view != this.mPresetColorView) {
/* 366 */       this.mPresetColorView.setSelectedColor(colorStr);
/*     */     } else {
/* 368 */       AnalyticsAnnotStylePicker.getInstance().selectColor(colorStr, 1);
/*     */     } 
/* 370 */     if (view != this.mCustomColorView) {
/* 371 */       this.mCustomColorView.setSelectedColor(colorStr);
/*     */     }
/*     */ 
/*     */     
/* 375 */     String source = (color == 0) ? "no_fill_color" : Utils.getColorHexString(color);
/* 376 */     if (view != this.mAdvancedColorView) {
/* 377 */       this.mAdvancedColorView.setSelectedColor(color);
/* 378 */       this.mCustomColorView.addRecentColorSource(source);
/* 379 */       this.mLatestAdvancedColor = "";
/*     */     } else {
/* 381 */       this.mLatestAdvancedColor = source;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onPresetColorGridItemClicked(AdapterView<?> parent, int position) {
/* 386 */     if (this.mSelectedAddFavoriteColors != null && !this.mSelectedAddFavoriteColors.isEmpty() && 
/* 387 */       onColorItemLongClickListener(parent, position)) {
/*     */       return;
/*     */     }
/*     */     
/* 391 */     String colorStr = (String)parent.getAdapter().getItem(position);
/* 392 */     if (colorStr == null) {
/*     */       return;
/*     */     }
/* 395 */     this.mPresetColorView.setSelectedColor(colorStr);
/*     */ 
/*     */     
/* 398 */     if (colorStr.equals("no_fill_color")) {
/* 399 */       int color = 0;
/* 400 */       onColorChanged((View)parent, color);
/*     */     } else {
/*     */       try {
/* 403 */         int color = Color.parseColor(colorStr);
/* 404 */         onColorChanged((View)parent, color);
/* 405 */       } catch (IllegalArgumentException e) {
/* 406 */         e.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean onColorItemLongClickListener(AdapterView<?> parent, int position) {
/* 412 */     ColorPickerGridViewAdapter adapter = (ColorPickerGridViewAdapter)parent.getAdapter();
/* 413 */     String color = adapter.getItem(position);
/* 414 */     if (color == null) {
/* 415 */       return false;
/*     */     }
/* 417 */     if (this.mSelectedAddFavoriteColors == null) {
/* 418 */       this.mSelectedAddFavoriteColors = new ArrayList<>();
/* 419 */       adapter.setSelectedList(this.mSelectedAddFavoriteColors);
/*     */     } 
/* 421 */     if (this.mSelectedAddFavoriteColors.contains(color)) {
/* 422 */       this.mSelectedAddFavoriteColors.remove(color);
/*     */     } else {
/* 424 */       this.mSelectedAddFavoriteColors.add(color);
/*     */     } 
/* 426 */     adapter.notifyDataSetChanged();
/* 427 */     toggleFavoriteToolbar();
/* 428 */     this.mAddFavoriteAdapter = adapter;
/* 429 */     return true;
/*     */   }
/*     */   
/*     */   private void addColorsToFavorites() {
/* 433 */     ArrayList<String> allFavorites = new ArrayList<>(this.mCustomColorView.getFavoriteColors());
/* 434 */     allFavorites.addAll(this.mSelectedAddFavoriteColors);
/* 435 */     this.mCustomColorView.setColorsToFavorites(allFavorites, 0);
/* 436 */     for (String color : this.mSelectedAddFavoriteColors) {
/* 437 */       AnalyticsHandlerAdapter.getInstance().sendEvent(42, 
/* 438 */           AnalyticsParam.colorParam(color));
/*     */     }
/* 440 */     onBackButtonPressed();
/*     */   }
/*     */   
/*     */   private void toggleFavoriteToolbar() {
/* 444 */     if (this.mSelectedAddFavoriteColors == null || this.mSelectedAddFavoriteColors.isEmpty()) {
/* 445 */       this.mToolbar.setBackgroundColor(Utils.getThemeAttrColor(getContext(), 16842801));
/* 446 */       int textColor = Utils.getThemeAttrColor(getContext(), 16842806);
/*     */       
/* 448 */       this.mToolbarTitle.setTextColor(textColor);
/* 449 */       this.mToolbarTitle.setAlpha(0.54F);
/* 450 */       this.mToolbarTitle.setText(this.mToolbarText);
/*     */       
/* 452 */       this.mAnnotStyleHolder.setAnnotPreviewVisibility(0);
/* 453 */       this.mAddFavButton.setVisibility(8);
/* 454 */       this.mPagerIndicator.setVisibility(0);
/* 455 */       this.mColorPager.setSwippingEnabled(true);
/* 456 */       this.mBackButton.setImageResource(R.drawable.ic_arrow_back_black_24dp);
/* 457 */       this.mBackButton.setColorFilter(textColor);
/* 458 */       this.mBackButton.setAlpha(0.54F);
/* 459 */       this.mSelectedAddFavoriteColors = null;
/* 460 */       this.mAddFavoriteAdapter = null;
/*     */       
/* 462 */       this.mArrowBackward.setVisibility(0);
/* 463 */       this.mArrowForward.setVisibility(0);
/*     */     } else {
/* 465 */       this.mToolbar.setBackgroundColor(Utils.getAccentColor(getContext()));
/* 466 */       this.mToolbarTitle.setText(getContext().getString(R.string.controls_thumbnails_view_selected, new Object[] {
/* 467 */               Utils.getLocaleDigits(Integer.toString(this.mSelectedAddFavoriteColors.size())) }));
/* 468 */       int textColor = Utils.getThemeAttrColor(getContext(), 16842809);
/* 469 */       this.mToolbarTitle.setTextColor(textColor);
/* 470 */       this.mToolbarTitle.setAlpha(1.0F);
/* 471 */       this.mAnnotStyleHolder.setAnnotPreviewVisibility(8);
/* 472 */       this.mBackButton.setImageResource(R.drawable.ic_close_black_24dp);
/* 473 */       this.mBackButton.setColorFilter(textColor);
/* 474 */       this.mBackButton.setAlpha(1.0F);
/* 475 */       this.mColorPager.setSwippingEnabled(false);
/* 476 */       this.mAddFavButton.setVisibility(0);
/* 477 */       this.mPagerIndicator.setVisibility(4);
/*     */       
/* 479 */       this.mArrowBackward.setVisibility(8);
/* 480 */       this.mArrowForward.setVisibility(8);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setArrowVisibility(int currentTab) {
/* 485 */     if (this.mArrowBackward == null || this.mArrowForward == null || this.mColorPager == null || this.mColorPagerAdapter == null) {
/*     */       return;
/*     */     }
/*     */     
/* 489 */     int lastTab = this.mColorPagerAdapter.getCount() - 1;
/* 490 */     if (currentTab == lastTab) {
/* 491 */       this.mArrowForward.setVisibility(4);
/*     */     } else {
/* 493 */       this.mArrowForward.setVisibility(0);
/*     */     } 
/* 495 */     if (currentTab == 0) {
/* 496 */       this.mArrowBackward.setVisibility(4);
/*     */     } else {
/* 498 */       this.mArrowBackward.setVisibility(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveColors() {
/* 506 */     this.mCustomColorView.saveColors();
/* 507 */     PdfViewCtrlSettingsManager.setColorPickerPage(getContext(), this.mColorPager.getCurrentItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActivity(FragmentActivity activity) {
/* 517 */     this.mCustomColorView.setActivity(activity);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEditFavoriteItemSelected(int selectedCount) {
/* 527 */     this.mToolbar.setBackgroundColor(Utils.getAccentColor(getContext()));
/* 528 */     this.mToolbarTitle.setText(getContext().getString(R.string.controls_thumbnails_view_selected, new Object[] {
/* 529 */             Utils.getLocaleDigits(Integer.toString(selectedCount))
/*     */           }));
/* 531 */     int textColor = Utils.getThemeAttrColor(getContext(), 16842809);
/* 532 */     this.mToolbarTitle.setTextColor(textColor);
/* 533 */     this.mToolbarTitle.setAlpha(1.0F);
/* 534 */     this.mAnnotStyleHolder.setAnnotPreviewVisibility(8);
/* 535 */     this.mBackButton.setImageResource(R.drawable.ic_close_black_24dp);
/* 536 */     this.mBackButton.setColorFilter(textColor);
/* 537 */     this.mBackButton.setAlpha(1.0F);
/* 538 */     this.mColorPager.setSwippingEnabled(false);
/* 539 */     this.mRemoveButton.setVisibility(0);
/* 540 */     this.mPagerIndicator.setVisibility(4);
/* 541 */     if (selectedCount == 1) {
/* 542 */       this.mEditButton.setVisibility(0);
/*     */     } else {
/* 544 */       this.mEditButton.setVisibility(8);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEditFavoriteColorDismissed() {
/* 553 */     this.mToolbar.setBackgroundColor(Utils.getThemeAttrColor(getContext(), 16842801));
/* 554 */     int textColor = Utils.getThemeAttrColor(getContext(), 16842806);
/*     */     
/* 556 */     this.mToolbarTitle.setTextColor(textColor);
/* 557 */     this.mToolbarTitle.setAlpha(0.54F);
/* 558 */     this.mToolbarTitle.setText(this.mToolbarText);
/*     */     
/* 560 */     this.mAnnotStyleHolder.setAnnotPreviewVisibility(0);
/* 561 */     this.mRemoveButton.setVisibility(8);
/* 562 */     this.mEditButton.setVisibility(8);
/* 563 */     this.mPagerIndicator.setVisibility(0);
/* 564 */     this.mColorPager.setSwippingEnabled(true);
/* 565 */     this.mBackButton.setImageResource(R.drawable.ic_arrow_back_black_24dp);
/* 566 */     this.mBackButton.setColorFilter(textColor);
/* 567 */     this.mBackButton.setAlpha(0.54F);
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
/*     */   protected class ColorPagerAdapter
/*     */     extends PagerAdapter
/*     */   {
/*     */     public int getCount() {
/* 582 */       return 3;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
/* 592 */       return (view == object);
/*     */     }
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
/*     */     @NonNull
/*     */     public Object instantiateItem(@NonNull ViewGroup container, int position) {
/*     */       CustomColorPickerView customColorPickerView;
/*     */       PresetColorGridView presetColorGridView;
/* 610 */       switch (position)
/*     */       { case 0:
/* 612 */           customColorPickerView = ColorPickerView.this.mCustomColorView;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 621 */           customColorPickerView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/* 622 */           container.addView((View)customColorPickerView);
/* 623 */           return customColorPickerView;case 1: presetColorGridView = ColorPickerView.this.mPresetColorView; presetColorGridView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1)); container.addView((View)presetColorGridView); return presetColorGridView; }  AdvancedColorView advancedColorView = ColorPickerView.this.mAdvancedColorView; advancedColorView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1)); container.addView((View)advancedColorView); return advancedColorView;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
/* 631 */       container.removeView((View)object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnColorChangeListener {
/*     */     void OnColorChanged(View param1View, @ColorInt int param1Int);
/*     */   }
/*     */   
/*     */   public static interface OnBackButtonPressedListener {
/*     */     void onBackPressed();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ColorPickerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */