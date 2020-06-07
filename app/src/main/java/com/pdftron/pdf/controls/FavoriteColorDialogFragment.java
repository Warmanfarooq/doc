/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.view.WindowManager;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.Button;
/*     */ import android.widget.GridView;
/*     */ import android.widget.ImageButton;
/*     */ import android.widget.ListAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.ColorPickerGridViewAdapter;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ public class FavoriteColorDialogFragment
/*     */   extends DialogFragment
/*     */   implements ViewPager.OnPageChangeListener, TabLayout.BaseOnTabSelectedListener
/*     */ {
/*  53 */   private static final String TAG = FavoriteColorDialogFragment.class.getName();
/*     */   
/*     */   public static final int ADD_COLOR = 0;
/*     */   
/*     */   public static final int EDIT_COLOR = 1;
/*     */   
/*     */   public static final String FAVORITE_DIALOG_MODE = "favDialogMode";
/*     */   
/*     */   private ViewPager mViewPager;
/*     */   
/*     */   private View mStandardColorTab;
/*     */   
/*     */   private View mAdvancedColorTab;
/*     */   
/*     */   private TabLayout mTablayout;
/*     */   
/*     */   private AdvancedColorView mAdvancedColorPicker;
/*     */   
/*     */   private Button mAddColorButton;
/*     */   
/*     */   private GridView mAddedColorsGridView;
/*     */   
/*     */   private ColorPickerGridViewAdapter mAddedColorsAdapter;
/*     */   private Button mFinishButton;
/*     */   private GridView mRecentColors;
/*     */   private PresetColorGridView mPresetColors;
/*     */   private OnEditFinishedListener mFinishedListener;
/*     */   
/*     */   public static FavoriteColorDialogFragment newInstance(Bundle bundle) {
/*  82 */     FavoriteColorDialogFragment fragment = new FavoriteColorDialogFragment();
/*  83 */     fragment.setArguments(bundle);
/*  84 */     return fragment;
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
/*  99 */   private int mSelectedColor = -16777216;
/*     */   private ArrayList<String> mFavoriteColors;
/* 101 */   private int mDialogMode = 0;
/*     */   
/*     */   private HashMap<String, Integer> mSelectedColorLabels;
/*     */   
/*     */   private ArrayList<String> mSelectedColors;
/*     */ 
/*     */   
/*     */   public FavoriteColorDialogFragment() {
/* 109 */     this.mSelectedColors = new ArrayList<>();
/* 110 */     this.mSelectedColorLabels = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 118 */     Dialog d = new Dialog((Context)getActivity(), R.style.FullScreenDialogStyle);
/* 119 */     if (d.getWindow() == null) {
/* 120 */       return d;
/*     */     }
/* 122 */     WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
/* 123 */     lp.copyFrom(d.getWindow().getAttributes());
/* 124 */     lp.width = -1;
/* 125 */     lp.height = -1;
/* 126 */     d.getWindow().setAttributes(lp);
/* 127 */     return d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
/* 136 */     View view = inflater.inflate(R.layout.fragment_add_favorite_color, container, false);
/*     */     
/* 138 */     this.mStandardColorTab = inflater.inflate(R.layout.controls_add_favorite_standard, null);
/* 139 */     this.mAdvancedColorTab = inflater.inflate(R.layout.controls_add_favorite_advanced, null);
/*     */     
/* 141 */     this.mAdvancedColorPicker = (AdvancedColorView)this.mAdvancedColorTab.findViewById(R.id.advanced_color_picker);
/* 142 */     this.mAdvancedColorPicker.setSelectedColor(this.mSelectedColor);
/* 143 */     this.mAdvancedColorPicker.setOnColorChangeListener(new ColorPickerView.OnColorChangeListener()
/*     */         {
/*     */           public void OnColorChanged(View view, int color) {
/* 146 */             FavoriteColorDialogFragment.this.onAdvancedColorChanged(view, color);
/*     */           }
/*     */         });
/*     */     
/* 150 */     this.mAddColorButton = (Button)this.mAdvancedColorTab.findViewById(R.id.add_color_btn);
/* 151 */     this.mAddColorButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 154 */             FavoriteColorDialogFragment.this.onAddColorButtonClicked(view);
/*     */           }
/*     */         });
/*     */     
/* 158 */     this.mAddedColorsGridView = (GridView)this.mAdvancedColorTab.findViewById(R.id.added_colors);
/* 159 */     this.mAddedColorsAdapter = new ColorPickerGridViewAdapter((Context)getActivity(), new ArrayList());
/* 160 */     this.mAddedColorsGridView.setAdapter((ListAdapter)this.mAddedColorsAdapter);
/* 161 */     this.mAddedColorsGridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 164 */             FavoriteColorDialogFragment.this.onGridItemClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/*     */     
/* 168 */     ImageButton closeButton = (ImageButton)view.findViewById(R.id.close_btn);
/* 169 */     closeButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 172 */             FavoriteColorDialogFragment.this.onCloseButtonClicked(view);
/*     */           }
/*     */         });
/*     */     
/* 176 */     this.mFinishButton = (Button)view.findViewById(R.id.finish_btn);
/* 177 */     this.mFinishButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 180 */             FavoriteColorDialogFragment.this.onCloseButtonClicked(view);
/*     */           }
/*     */         });
/* 183 */     TextView toolbarTitleTextView = (TextView)view.findViewById(R.id.toolbar_title);
/*     */     
/* 185 */     this.mRecentColors = (GridView)this.mStandardColorTab.findViewById(R.id.recent_colors);
/*     */ 
/*     */     
/* 188 */     this.mPresetColors = (PresetColorGridView)this.mStandardColorTab.findViewById(R.id.preset_colors);
/* 189 */     this.mPresetColors.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 192 */             FavoriteColorDialogFragment.this.onGridItemClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/* 195 */     ArrayList<String> recentColors = new ArrayList<>();
/*     */     
/* 197 */     if (getArguments() != null) {
/*     */ 
/*     */       
/* 200 */       if (getArguments().containsKey("recent_colors")) {
/* 201 */         recentColors = getArguments().getStringArrayList("recent_colors");
/*     */       }
/*     */ 
/*     */       
/* 205 */       if (getArguments().containsKey("favorite_colors")) {
/* 206 */         this.mFavoriteColors = getArguments().getStringArrayList("favorite_colors");
/* 207 */         this.mFavoriteColors = (ArrayList<String>)ColorPickerGridViewAdapter.getListLowerCase(this.mFavoriteColors);
/* 208 */         this.mSelectedColors.addAll(this.mFavoriteColors);
/* 209 */         this.mPresetColors.getAdapter().setDisabledColorList(this.mFavoriteColors);
/*     */       } 
/*     */ 
/*     */       
/* 213 */       if (getArguments().containsKey("favDialogMode")) {
/* 214 */         this.mDialogMode = getArguments().getInt("favDialogMode");
/* 215 */         if (this.mDialogMode == 1) {
/* 216 */           toolbarTitleTextView.setText(R.string.controls_fav_color_editor_edit_color);
/* 217 */           this.mAddedColorsAdapter.setMaxSize(1);
/* 218 */           this.mSelectedColors.clear();
/* 219 */           this.mAddColorButton.setText(R.string.controls_fav_color_editor_select_color);
/*     */         } 
/*     */       } 
/*     */     } 
/* 223 */     if (this.mDialogMode == 0) {
/* 224 */       this.mPresetColors.getAdapter().setFavoriteList(this.mSelectedColors);
/* 225 */       this.mAddedColorsAdapter.setFavoriteList(this.mSelectedColors);
/*     */     } else {
/* 227 */       this.mPresetColors.getAdapter().setFavoriteList(this.mFavoriteColors);
/* 228 */       this.mAddedColorsAdapter.setFavoriteList(this.mFavoriteColors);
/* 229 */       this.mPresetColors.getAdapter().setSelectedList(this.mSelectedColors);
/* 230 */       this.mAddedColorsAdapter.setSelectedList(this.mSelectedColors);
/*     */     } 
/*     */     
/* 233 */     if (recentColors == null || recentColors.isEmpty()) {
/* 234 */       this.mStandardColorTab.findViewById(R.id.recent_colors_title).setVisibility(8);
/* 235 */       this.mRecentColors.setVisibility(8);
/*     */     } else {
/* 237 */       this.mStandardColorTab.findViewById(R.id.recent_colors_title).setVisibility(0);
/* 238 */       this.mRecentColors.setVisibility(0);
/* 239 */       this.mRecentColors.setAdapter((ListAdapter)new ColorPickerGridViewAdapter((Context)getActivity(), recentColors));
/* 240 */       ((ColorPickerGridViewAdapter)this.mRecentColors.getAdapter()).setDisabledColorList(this.mFavoriteColors);
/* 241 */       if (this.mDialogMode == 1) {
/* 242 */         ((ColorPickerGridViewAdapter)this.mRecentColors.getAdapter()).setSelectedList(this.mSelectedColors);
/*     */       }
/* 244 */       ((ColorPickerGridViewAdapter)this.mRecentColors.getAdapter()).setFavoriteList(this.mSelectedColors);
/* 245 */       this.mRecentColors.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */           {
/*     */             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 248 */               FavoriteColorDialogFragment.this.onGridItemClicked(adapterView, view, i, l);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 253 */     this.mViewPager = (ViewPager)view.findViewById(R.id.view_pager);
/* 254 */     this.mTablayout = (TabLayout)view.findViewById(R.id.tab_layout);
/* 255 */     this.mViewPager.setAdapter(new FavoriteViewPagerAdapter());
/* 256 */     this.mViewPager.addOnPageChangeListener(this);
/* 257 */     this.mTablayout.addOnTabSelectedListener(this);
/*     */ 
/*     */     
/* 260 */     if (savedInstanceState != null && 
/* 261 */       savedInstanceState.containsKey("selectedTab")) {
/* 262 */       int selectedIndex = savedInstanceState.getInt("selectedTab");
/* 263 */       TabLayout.Tab tab = this.mTablayout.getTabAt(selectedIndex);
/* 264 */       if (tab != null) {
/* 265 */         tab.select();
/*     */       }
/* 267 */       this.mViewPager.setCurrentItem(selectedIndex);
/*     */     } 
/*     */ 
/*     */     
/* 271 */     int tabCount = this.mTablayout.getTabCount();
/* 272 */     for (int i = 0; i < tabCount; i++) {
/* 273 */       TabLayout.Tab tab = this.mTablayout.getTabAt(i);
/* 274 */       if (tab != null) {
/*     */ 
/*     */         
/* 277 */         Drawable icon = tab.getIcon();
/* 278 */         if (icon != null) {
/*     */ 
/*     */           
/* 281 */           icon.mutate();
/* 282 */           icon.setColorFilter(getActivity().getResources().getColor(17170433), PorterDuff.Mode.SRC_IN);
/* 283 */           if (i != this.mTablayout.getSelectedTabPosition())
/* 284 */           { icon.setAlpha(137); }
/*     */           else
/* 286 */           { icon.setAlpha(255); } 
/*     */         } 
/*     */       } 
/* 289 */     }  return view;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSaveInstanceState(Bundle outState) {
/* 299 */     super.onSaveInstanceState(outState);
/* 300 */     outState.putInt("selectedTab", this.mTablayout.getSelectedTabPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   private void addFavoriteColor(String color, int labelFrom) {
/* 305 */     if (this.mDialogMode == 0 || this.mSelectedColors.isEmpty()) {
/* 306 */       this.mSelectedColors.add(color.toLowerCase());
/* 307 */       this.mSelectedColorLabels.put(color.toLowerCase(), Integer.valueOf(labelFrom));
/*     */     } else {
/* 309 */       this.mSelectedColors.set(0, color.toLowerCase());
/* 310 */       this.mSelectedColorLabels.clear();
/* 311 */       this.mSelectedColorLabels.put(color.toLowerCase(), Integer.valueOf(labelFrom));
/*     */     } 
/* 313 */     if (this.mAddedColorsGridView.getVisibility() == 4) {
/* 314 */       this.mAddedColorsGridView.setVisibility(0);
/*     */     }
/* 316 */     this.mFinishButton.setVisibility(0);
/* 317 */     if (this.mRecentColors.getAdapter() != null) {
/* 318 */       ((ColorPickerGridViewAdapter)this.mRecentColors.getAdapter()).notifyDataSetChanged();
/*     */     }
/* 320 */     this.mPresetColors.getAdapter().notifyDataSetChanged();
/*     */   }
/*     */   
/*     */   private void removeFavoriteColor(String color) {
/* 324 */     this.mSelectedColors.remove(color.toLowerCase());
/* 325 */     this.mSelectedColorLabels.remove(color.toLowerCase());
/* 326 */     this.mAddedColorsAdapter.removeItem(color);
/* 327 */     if (this.mAddedColorsAdapter.getCount() == 0) {
/* 328 */       this.mAddedColorsGridView.setVisibility(4);
/*     */     }
/*     */     
/* 331 */     if (this.mSelectedColors.equals(this.mFavoriteColors)) {
/* 332 */       this.mFinishButton.setVisibility(8);
/*     */     } else {
/* 334 */       this.mFinishButton.setVisibility(0);
/*     */     } 
/*     */     
/* 337 */     this.mPresetColors.getAdapter().notifyDataSetChanged();
/* 338 */     if (this.mRecentColors.getAdapter() != null) {
/* 339 */       ((ColorPickerGridViewAdapter)this.mRecentColors.getAdapter()).notifyDataSetChanged();
/*     */     }
/* 341 */     this.mAddedColorsAdapter.notifyDataSetChanged();
/*     */   }
/*     */   
/*     */   private void onAddColorButtonClicked(View v) {
/* 345 */     String color = Utils.getColorHexString(this.mAdvancedColorPicker.getColor());
/* 346 */     this.mAdvancedColorPicker.setSelectedColor(this.mAdvancedColorPicker.getColor());
/* 347 */     this.mAddedColorsAdapter.add(color);
/* 348 */     addFavoriteColor(color, 123);
/* 349 */     this.mAddedColorsAdapter.notifyDataSetChanged();
/* 350 */     this.mAddColorButton.setEnabled(false);
/*     */   }
/*     */   
/*     */   private void onCloseButtonClicked(View v) {
/* 354 */     if (v.getId() == this.mFinishButton.getId()) {
/* 355 */       StringBuilder sb = new StringBuilder();
/* 356 */       for (String str : this.mSelectedColors) {
/* 357 */         sb = sb.append(str);
/* 358 */         if (this.mSelectedColors.indexOf(str) < this.mSelectedColors.size() - 1) {
/* 359 */           sb = sb.append(',').append(' ');
/*     */         }
/*     */       } 
/*     */       
/* 363 */       PdfViewCtrlSettingsManager.setFavoriteColors((Context)getActivity(), sb.toString());
/* 364 */       for (Map.Entry<String, Integer> entry : this.mSelectedColorLabels.entrySet())
/*     */       {
/* 366 */         AnalyticsHandlerAdapter.getInstance().sendEvent(42, 
/* 367 */             AnalyticsParam.colorParam(entry.getKey()));
/*     */       }
/* 369 */       if (this.mFinishedListener != null) {
/* 370 */         this.mFinishedListener.onEditFinished(this.mSelectedColors, this.mDialogMode);
/*     */       }
/*     */     } 
/* 373 */     dismiss();
/*     */   }
/*     */   
/*     */   private void onGridItemClicked(AdapterView<?> parent, View view, int position, long id) {
/* 377 */     ColorPickerGridViewAdapter adapter = (ColorPickerGridViewAdapter)parent.getAdapter();
/* 378 */     String item = adapter.getItem(position);
/* 379 */     if (item == null) {
/*     */       return;
/*     */     }
/* 382 */     if (parent.getId() == this.mAddedColorsGridView.getId()) {
/* 383 */       removeFavoriteColor(item);
/* 384 */     } else if (parent.getId() == this.mPresetColors.getId() || parent.getId() == this.mRecentColors.getId()) {
/* 385 */       if (adapter.isItemDisabled(item)) {
/*     */         return;
/*     */       }
/* 388 */       if (this.mSelectedColors.contains(item.toLowerCase())) {
/* 389 */         removeFavoriteColor(item);
/*     */       } else {
/* 391 */         int labelId = (parent.getId() == this.mPresetColors.getId()) ? 122 : 124;
/* 392 */         addFavoriteColor(item, labelId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void onAdvancedColorChanged(View view, @ColorInt int color) {
/* 398 */     if (this.mSelectedColors.contains(Utils.getColorHexString(color).toLowerCase())) {
/* 399 */       this.mAddColorButton.setEnabled(false);
/*     */     } else {
/* 401 */       this.mAddColorButton.setEnabled(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 410 */     super.dismiss();
/* 411 */     this.mViewPager.removeOnPageChangeListener(this);
/* 412 */     this.mTablayout.removeOnTabSelectedListener(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<String> getSelectedColors() {
/* 421 */     return this.mSelectedColors;
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
/*     */   public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
/* 436 */     this.mTablayout.setScrollPosition(position, positionOffset, true);
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
/*     */   public void onPageSelected(int position) {
/* 449 */     TabLayout.Tab tab = this.mTablayout.getTabAt(position);
/* 450 */     if (tab != null) {
/* 451 */       tab.select();
/*     */     }
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
/*     */   public void onPageScrollStateChanged(int state) {}
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
/*     */   public void onTabSelected(TabLayout.Tab tab) {
/* 476 */     int index = tab.getPosition();
/* 477 */     this.mViewPager.setCurrentItem(index, true);
/* 478 */     Drawable icon = tab.getIcon();
/* 479 */     if (icon != null) {
/* 480 */       icon.mutate();
/* 481 */       icon.setAlpha(255);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabUnselected(TabLayout.Tab tab) {
/* 492 */     Drawable icon = tab.getIcon();
/* 493 */     if (icon != null) {
/* 494 */       icon.mutate();
/* 495 */       icon.setAlpha(137);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabReselected(TabLayout.Tab tab) {
/* 507 */     int index = tab.getPosition();
/* 508 */     this.mViewPager.setCurrentItem(index, true);
/* 509 */     Drawable icon = tab.getIcon();
/* 510 */     if (icon != null) {
/* 511 */       icon.mutate();
/* 512 */       icon.setAlpha(255);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(@ColorInt int color) {
/* 522 */     this.mSelectedColor = color;
/* 523 */     if (this.mAdvancedColorPicker != null) {
/* 524 */       this.mAdvancedColorPicker.setSelectedColor(this.mSelectedColor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnEditFinishedListener(OnEditFinishedListener listener) {
/* 534 */     this.mFinishedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface FavoriteDialogMode {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected class FavoriteViewPagerAdapter
/*     */     extends PagerAdapter
/*     */   {
/*     */     public int getCount() {
/* 550 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isViewFromObject(View view, Object object) {
/* 555 */       return (view == object);
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
/*     */ 
/*     */     
/*     */     public Object instantiateItem(ViewGroup container, int position) {
/* 572 */       switch (position)
/*     */       { case 0:
/* 574 */           view = FavoriteColorDialogFragment.this.mStandardColorTab;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 580 */           container.addView(view);
/* 581 */           return view; }  View view = FavoriteColorDialogFragment.this.mAdvancedColorTab; container.addView(view); return view;
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
/*     */     public void destroyItem(ViewGroup container, int position, Object object) {
/* 596 */       container.removeView((View)object);
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
/*     */     public CharSequence getPageTitle(int position) {
/* 610 */       switch (position) {
/*     */         case 0:
/* 612 */           return "standard";
/*     */       } 
/* 614 */       return "advanced";
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnEditFinishedListener {
/*     */     void onEditFinished(ArrayList<String> param1ArrayList, int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\FavoriteColorDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */