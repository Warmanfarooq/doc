/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Color;
/*     */ import android.os.Bundle;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.GridView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.ListAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsAnnotStylePicker;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.ColorPickerGridViewAdapter;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.TreeSet;
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
/*     */ public class CustomColorPickerView
/*     */   extends LinearLayout
/*     */ {
/*     */   public static final String KEY_RECENT_COLORS = "recent_colors";
/*     */   public static final String KEY_FAVORITE_COLORS = "favorite_colors";
/*     */   public static final int MAX_COLORS = 12;
/*     */   private GridView mFavoriteColorGrid;
/*     */   private GridView mRecentColorGrid;
/*     */   private ColorPickerGridViewAdapter mFavoriteColorAdapter;
/*     */   private ColorPickerGridViewAdapter mRecentColorAdapter;
/*     */   private TextView mRecentColorHint;
/*     */   private ColorPickerView.OnColorChangeListener mColorChangeListener;
/*     */   private FavoriteColorDialogFragment mFavoriteColorEditDialog;
/*     */   private OnEditFavoriteColorListener mEditFavoriteListener;
/*     */   private TextView mRecentTitle;
/*     */   private TextView mFavoriteTitle;
/*  59 */   private int mSelectedFavColorPosition = -1;
/*     */   
/*     */   private WeakReference<FragmentActivity> mActivityRef;
/*     */   
/*     */   private AdapterView.OnItemLongClickListener mRecentColorLongClickListener;
/*     */ 
/*     */   
/*     */   public CustomColorPickerView(Context context) {
/*  67 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomColorPickerView(Context context, @Nullable AttributeSet attrs) {
/*  74 */     this(context, attrs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomColorPickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/*  81 */     super(context, attrs, defStyleAttr);
/*  82 */     init();
/*     */   }
/*     */   
/*     */   private void init() {
/*  86 */     LayoutInflater.from(getContext()).inflate(R.layout.color_picker_layout_custom, (ViewGroup)this);
/*  87 */     this.mFavoriteColorGrid = (GridView)findViewById(R.id.favorite_color_grid);
/*  88 */     this.mRecentColorGrid = (GridView)findViewById(R.id.recent_color_grid);
/*  89 */     this.mRecentColorHint = (TextView)findViewById(R.id.recent_color_hint);
/*  90 */     this.mRecentTitle = (TextView)findViewById(R.id.recent_title);
/*  91 */     this.mFavoriteTitle = (TextView)findViewById(R.id.favorite_title);
/*  92 */     loadColors();
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadColors() {
/*  97 */     String savedFavColors = PdfViewCtrlSettingsManager.getFavoriteColors(getContext());
/*  98 */     this.mFavoriteColorAdapter = new ColorPickerGridViewAdapter(getContext(), new ArrayList());
/*  99 */     ArrayList<String> favColors = setStoredColors(this.mFavoriteColorAdapter, savedFavColors);
/* 100 */     if (favColors.size() < 12 && !favColors.contains("add_custom_color")) {
/* 101 */       this.mFavoriteColorAdapter.addItem(this.mFavoriteColorAdapter.getCount(), "add_custom_color");
/*     */     }
/* 103 */     this.mFavoriteColorGrid.setAdapter((ListAdapter)this.mFavoriteColorAdapter);
/* 104 */     this.mFavoriteColorGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 107 */             CustomColorPickerView.this.onItemClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/* 110 */     this.mFavoriteColorGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 113 */             return CustomColorPickerView.this.onItemLongClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/*     */     
/* 117 */     String savedRecentColors = PdfViewCtrlSettingsManager.getRecentColors(getContext());
/* 118 */     this.mRecentColorAdapter = new ColorPickerGridViewAdapter(getContext(), new ArrayList());
/* 119 */     ArrayList<String> recentColorSources = setStoredColors(this.mRecentColorAdapter, savedRecentColors);
/* 120 */     this.mRecentColorGrid.setAdapter((ListAdapter)this.mRecentColorAdapter);
/* 121 */     this.mRecentColorGrid.setOnItemClickListener(new AdapterView.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 124 */             CustomColorPickerView.this.onItemClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/* 127 */     this.mRecentColorGrid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
/* 130 */             return CustomColorPickerView.this.onItemLongClicked(adapterView, view, i, l);
/*     */           }
/*     */         });
/* 133 */     if (recentColorSources.isEmpty()) {
/* 134 */       this.mRecentColorHint.setVisibility(0);
/* 135 */       this.mRecentColorGrid.setVisibility(8);
/*     */     } else {
/* 137 */       this.mRecentColorGrid.setVisibility(0);
/* 138 */       this.mRecentColorHint.setVisibility(8);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static ArrayList<String> setStoredColors(ColorPickerGridViewAdapter adapter, String colorListStr) {
/* 143 */     ArrayList<String> colorSources = new ArrayList<>();
/* 144 */     if (!Utils.isNullOrEmpty(colorListStr)) {
/* 145 */       String[] colorList = colorListStr.split(", ");
/* 146 */       colorSources.addAll(Arrays.asList(colorList));
/*     */     } 
/*     */     
/* 149 */     adapter.setMaxSize(12);
/* 150 */     adapter.setSource(colorSources);
/* 151 */     return colorSources;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addRecentColorSource(String source) {
/* 160 */     this.mRecentColorAdapter.addFront(source);
/* 161 */     if (this.mRecentColorHint.getVisibility() == 0 && this.mRecentColorAdapter
/* 162 */       .getCount() > 0) {
/* 163 */       this.mRecentColorHint.setVisibility(8);
/* 164 */       this.mRecentColorGrid.setVisibility(0);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String getColorsToString(List<String> items) {
/* 169 */     if (items == null || items.isEmpty()) {
/* 170 */       return "";
/*     */     }
/* 172 */     StringBuilder sb = new StringBuilder();
/* 173 */     for (String str : items) {
/* 174 */       sb = sb.append(str);
/* 175 */       if (items.indexOf(str) < items.size() - 1) {
/* 176 */         sb = sb.append(',').append(' ');
/*     */       }
/*     */     } 
/* 179 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveColors() {
/* 186 */     PdfViewCtrlSettingsManager.setRecentColors(getContext(), getColorsToString(this.mRecentColorAdapter.getItems()));
/* 187 */     List<String> favorites = this.mFavoriteColorAdapter.getItems();
/* 188 */     favorites.remove("add_custom_color");
/* 189 */     PdfViewCtrlSettingsManager.setFavoriteColors(getContext(), getColorsToString(favorites));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnColorChangeListener(ColorPickerView.OnColorChangeListener listener) {
/* 198 */     this.mColorChangeListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedColor(String colorStr) {
/* 207 */     this.mRecentColorAdapter.setSelected(colorStr);
/* 208 */     this.mFavoriteColorAdapter.setSelected(colorStr);
/*     */   }
/*     */   
/*     */   private void onItemClicked(AdapterView<?> parent, View view, int position, long id) {
/* 212 */     ColorPickerGridViewAdapter adapter = (ColorPickerGridViewAdapter)parent.getAdapter();
/* 213 */     String item = adapter.getItem(position);
/*     */ 
/*     */     
/* 216 */     if (parent.getId() == this.mFavoriteColorGrid.getId() && this.mRecentColorAdapter.getSelectedListCount() > 0)
/*     */       return; 
/* 218 */     if (parent.getId() == this.mRecentColorGrid.getId() && this.mFavoriteColorAdapter.getSelectedListCount() > 0)
/*     */       return; 
/* 220 */     if (adapter.getSelectedListCount() > 0 && onItemLongClicked(parent, view, position, id)) {
/*     */       return;
/*     */     }
/*     */     
/* 224 */     if (item != null && !item.equalsIgnoreCase("add_custom_color") && 
/* 225 */       !item.equalsIgnoreCase("remove_custom_color") && 
/* 226 */       !item.equalsIgnoreCase("restore_color")) {
/* 227 */       if (!item.equalsIgnoreCase(adapter.getSelected()) && this.mColorChangeListener != null) {
/* 228 */         int color, labelId = (parent.getId() == this.mFavoriteColorGrid.getId()) ? 115 : 114;
/* 229 */         AnalyticsHandlerAdapter.getInstance().sendEvent(12, 
/* 230 */             String.format("color selected %s", new Object[] { item }), labelId);
/*     */         
/* 232 */         if (item.equals("no_fill_color")) {
/* 233 */           color = 0;
/*     */         } else {
/*     */           try {
/* 236 */             color = Color.parseColor(item);
/* 237 */           } catch (IllegalArgumentException e) {
/* 238 */             color = 0;
/*     */           } 
/*     */         } 
/* 241 */         this.mColorChangeListener.OnColorChanged((View)this, color);
/*     */       } 
/* 243 */       adapter.setSelected(position);
/* 244 */       if (adapter != this.mFavoriteColorAdapter) {
/* 245 */         this.mFavoriteColorAdapter.setSelected(item);
/* 246 */         AnalyticsAnnotStylePicker.getInstance().selectColor(item.toUpperCase(), 2);
/*     */       } else {
/* 248 */         this.mRecentColorAdapter.setSelected(item);
/* 249 */         AnalyticsAnnotStylePicker.getInstance().selectColor(item.toUpperCase(), 3);
/*     */       } 
/* 251 */     } else if (item != null && item.equalsIgnoreCase("add_custom_color")) {
/* 252 */       openFavoriteEditDialog(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean onItemLongClicked(AdapterView<?> parent, View view, int position, long id) {
/* 258 */     ColorPickerGridViewAdapter adapter = (ColorPickerGridViewAdapter)parent.getAdapter();
/* 259 */     if (adapter.getItem(position) != null && "add_custom_color".equalsIgnoreCase(adapter.getItem(position))) {
/* 260 */       return false;
/*     */     }
/*     */     
/* 263 */     if (parent.getId() == this.mRecentColorGrid.getId() && this.mRecentColorLongClickListener != null) {
/* 264 */       boolean result = this.mRecentColorLongClickListener.onItemLongClick(parent, view, position, id);
/* 265 */       boolean favoriteClickable = (adapter.getSelectedListCount() <= 0);
/* 266 */       float alpha = (adapter.getSelectedListCount() > 0) ? 0.38F : 1.0F;
/* 267 */       this.mFavoriteColorGrid.setClickable(favoriteClickable);
/* 268 */       this.mFavoriteColorGrid.setLongClickable(favoriteClickable);
/* 269 */       this.mFavoriteColorGrid.setAlpha(alpha);
/* 270 */       this.mFavoriteTitle.setAlpha(alpha);
/* 271 */       return result;
/*     */     } 
/*     */     
/* 274 */     if (adapter.isInSelectedList(position)) {
/* 275 */       adapter.removeSelected(position);
/*     */     } else {
/* 277 */       adapter.addSelected(position);
/*     */     } 
/*     */     
/* 280 */     if (adapter.getSelectedListCount() > 0) {
/* 281 */       this.mSelectedFavColorPosition = position;
/* 282 */       onFavoriteItemSelected();
/*     */     } else {
/* 284 */       onFavoriteItemCleared();
/*     */     } 
/* 286 */     return true;
/*     */   }
/*     */   
/*     */   private void onFavoriteItemSelected() {
/* 290 */     this.mFavoriteColorAdapter.remove("add_custom_color");
/* 291 */     this.mRecentColorGrid.setClickable(false);
/* 292 */     this.mRecentColorGrid.setAlpha(0.38F);
/* 293 */     this.mRecentColorGrid.setLongClickable(false);
/* 294 */     this.mRecentTitle.setAlpha(0.38F);
/*     */     
/* 296 */     if (this.mEditFavoriteListener != null) {
/* 297 */       this.mEditFavoriteListener.onEditFavoriteItemSelected(this.mFavoriteColorAdapter.getSelectedListCount());
/*     */     }
/*     */   }
/*     */   
/*     */   private void onFavoriteItemCleared() {
/* 302 */     if (this.mFavoriteColorAdapter.getCount() < 12 && !this.mFavoriteColorAdapter.contains("add_custom_color")) {
/* 303 */       this.mFavoriteColorAdapter.add("add_custom_color");
/*     */     }
/* 305 */     this.mRecentColorGrid.setClickable(true);
/* 306 */     this.mRecentColorGrid.setLongClickable(true);
/* 307 */     this.mRecentColorGrid.setAlpha(1.0F);
/* 308 */     this.mRecentTitle.setAlpha(1.0F);
/* 309 */     this.mSelectedFavColorPosition = -1;
/* 310 */     if (this.mEditFavoriteListener != null) {
/* 311 */       this.mEditFavoriteListener.onEditFavoriteColorDismissed();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActivity(FragmentActivity activity) {
/* 321 */     this.mActivityRef = new WeakReference<>(activity);
/*     */   }
/*     */ 
/*     */   
/*     */   private void openFavoriteEditDialog(int favDialogMode) {
/* 326 */     FragmentActivity activity = null;
/* 327 */     if (this.mActivityRef != null) {
/* 328 */       activity = this.mActivityRef.get();
/*     */     }
/* 330 */     if (activity == null && getContext() instanceof FragmentActivity) {
/* 331 */       activity = (FragmentActivity)getContext();
/*     */     }
/* 333 */     if (activity == null) {
/*     */       return;
/*     */     }
/*     */     
/* 337 */     Bundle bundle = new Bundle();
/* 338 */     bundle.putStringArrayList("recent_colors", (ArrayList)this.mRecentColorAdapter.getItems());
/* 339 */     ArrayList<String> favoriteItem = new ArrayList<>(this.mFavoriteColorAdapter.getItems());
/* 340 */     favoriteItem.remove("add_custom_color");
/* 341 */     if (this.mSelectedFavColorPosition >= 0) {
/* 342 */       favoriteItem.remove(this.mSelectedFavColorPosition);
/*     */     }
/* 344 */     bundle.putStringArrayList("favorite_colors", favoriteItem);
/* 345 */     bundle.putInt("favDialogMode", favDialogMode);
/*     */     
/* 347 */     this.mFavoriteColorEditDialog = FavoriteColorDialogFragment.newInstance(bundle);
/*     */     
/* 349 */     this.mFavoriteColorEditDialog.setOnEditFinishedListener(new FavoriteColorDialogFragment.OnEditFinishedListener()
/*     */         {
/*     */           public void onEditFinished(ArrayList<String> colors, int dialogMode) {
/* 352 */             CustomColorPickerView.this.setColorsToFavorites(colors, dialogMode);
/*     */           }
/*     */         });
/*     */     
/* 356 */     this.mFavoriteColorEditDialog.show(activity.getSupportFragmentManager(), "dialog");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBackButonPressed() {
/* 363 */     if (this.mFavoriteColorAdapter.getSelectedListCount() > 0) {
/* 364 */       this.mFavoriteColorAdapter.removeAllSelected();
/* 365 */       onFavoriteItemCleared();
/* 366 */       return true;
/* 367 */     }  if (this.mRecentColorAdapter.getSelectedListCount() > 0) {
/* 368 */       this.mFavoriteColorGrid.setClickable(true);
/* 369 */       this.mFavoriteColorGrid.setLongClickable(true);
/* 370 */       this.mFavoriteColorGrid.setAlpha(1.0F);
/* 371 */       this.mFavoriteTitle.setAlpha(1.0F);
/*     */     } 
/* 373 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColorsToFavorites(ArrayList<String> colors, int dialogMode) {
/* 384 */     TreeSet<String> toRetain = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
/* 385 */     toRetain.addAll(colors);
/* 386 */     LinkedHashSet<String> set = new LinkedHashSet<>(colors);
/* 387 */     set.retainAll(new LinkedHashSet(toRetain));
/* 388 */     colors = new ArrayList<>(set);
/* 389 */     if (colors.size() > 12) {
/* 390 */       colors = new ArrayList<>(colors.subList(colors.size() - 12, colors.size()));
/*     */     }
/* 392 */     if (colors.size() < 12 && dialogMode == 0) {
/* 393 */       colors.add("add_custom_color");
/*     */     }
/* 395 */     if (dialogMode == 0) {
/* 396 */       this.mFavoriteColorAdapter.setSource(colors);
/*     */     } else {
/* 398 */       String originalColor = this.mFavoriteColorAdapter.getItem(this.mSelectedFavColorPosition);
/* 399 */       AnalyticsHandlerAdapter.getInstance().sendEvent(43, 
/* 400 */           AnalyticsParam.colorParam(originalColor));
/* 401 */       this.mFavoriteColorAdapter.setItem(this.mSelectedFavColorPosition, colors.get(0));
/* 402 */       this.mFavoriteColorAdapter.setSelectedList(null);
/* 403 */       this.mSelectedFavColorPosition = -1;
/* 404 */       onFavoriteItemCleared();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<String> getFavoriteColors() {
/* 414 */     ArrayList<String> favoriteColors = new ArrayList<>(this.mFavoriteColorAdapter.getItems());
/* 415 */     favoriteColors.remove("add_custom_color");
/* 416 */     return favoriteColors;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void editSelectedColor() {
/* 423 */     openFavoriteEditDialog(1);
/*     */     try {
/* 425 */       int color = Color.parseColor(this.mFavoriteColorAdapter.getFirstSelectedInList());
/* 426 */       this.mFavoriteColorEditDialog.setSelectedColor(color);
/* 427 */     } catch (IllegalArgumentException e) {
/* 428 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteAllSelectedFavColors() {
/* 436 */     ArrayList<String> selectedItems = this.mFavoriteColorAdapter.getSelectedList();
/* 437 */     for (String color : selectedItems) {
/* 438 */       AnalyticsHandlerAdapter.getInstance().sendEvent(43, 
/* 439 */           AnalyticsParam.colorParam(color));
/*     */     }
/* 441 */     this.mFavoriteColorAdapter.deleteAllSelected();
/* 442 */     onFavoriteItemCleared();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnEditFavoriteColorlistener(OnEditFavoriteColorListener listener) {
/* 451 */     this.mEditFavoriteListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRecentColorLongPressListener(AdapterView.OnItemLongClickListener listener) {
/* 460 */     this.mRecentColorLongClickListener = listener;
/*     */   }
/*     */   
/*     */   public static interface OnEditFavoriteColorListener {
/*     */     void onEditFavoriteItemSelected(int param1Int);
/*     */     
/*     */     void onEditFavoriteColorDismissed();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\CustomColorPickerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */