/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.res.Configuration;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.Bundle;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.Button;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.RadioGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import com.pdftron.pdf.utils.SegmentedGroup;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerViewAdapter;
/*     */ import com.rarepebble.colorpicker.ColorPickerView;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomColorModeDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*  51 */   private static String ARG_BGCOLOR = "arg_bgcolor";
/*  52 */   private static String ARG_TXTCOLOR = "arg_txtcolor";
/*     */   
/*  54 */   private static int[][] DEFAULT_PRESET_COLORS = new int[][] { { -920588, -13223107 }, { -4864632, -13552070 }, { -4204350, -12563648 }, { -3089949, -14074792 }, { -2175316, -10796242 }, { -1454635, -5618340 }, { -6684724, -16777216 }, { -6697729, -16777216 }, { -13357010, -7764092 }, { -9687764, -205604 }, { -10854601, -205604 }, { -12570847, -2175316 }, { -16240571, -2367005 }, { -13288643, -7891303 }, { -16764160, -1 } };
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
/*  73 */   private CustomColorModeSelectedListener mCustomColorModeSelectedListener = null;
/*     */   
/*     */   private boolean mDarkSelected = false;
/*     */   
/*     */   private boolean mEditing = false;
/*     */   
/*     */   private ColorPickerView mColorPickerView;
/*     */   
/*     */   private LinearLayout mPreviewLayout;
/*     */   
/*     */   private SegmentedGroup mColorCompSelector;
/*     */   
/*     */   private int mCurrentDarkColor;
/*     */   private int mCurrentLightColor;
/*     */   private View mColorPickingView;
/*     */   private Button mPickerCancelBtn;
/*     */   private Button mPickerOKBtn;
/*     */   private View mPresetView;
/*     */   private PresetRecyclerAdapter mPresetRecyclerAdapter;
/*     */   private Button mPresetCancelBtn;
/*     */   private Button mPresetOKBtn;
/*     */   private CustomViewPager mViewPager;
/*     */   private JsonArray mPresetArray;
/*     */   private boolean mHasAction = false;
/*  97 */   private int mPreviousClickedPosition = -1;
/*     */   
/*  99 */   private View.OnClickListener mPickerCancelListener = new View.OnClickListener()
/*     */     {
/*     */       public void onClick(View v) {
/* 102 */         if (CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos() < 0) {
/* 103 */           CustomColorModeDialogFragment.this.mCurrentLightColor = -1;
/* 104 */           CustomColorModeDialogFragment.this.mCurrentDarkColor = -16777216;
/*     */         } else {
/* 106 */           JsonObject presetObj = CustomColorModeDialogFragment.this.mPresetArray.get(CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos()).getAsJsonObject();
/* 107 */           CustomColorModeDialogFragment.this.mCurrentDarkColor = presetObj.get("fg").getAsInt();
/* 108 */           CustomColorModeDialogFragment.this.mCurrentLightColor = presetObj.get("bg").getAsInt();
/*     */         } 
/* 110 */         CustomColorModeDialogFragment.this.mEditing = false;
/* 111 */         CustomColorModeDialogFragment.this.mViewPager.setCurrentItem(0, true);
/* 112 */         AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 113 */             AnalyticsParam.customColorParam(3));
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomColorModeDialogFragment newInstance(int bgColor, int txtColor) {
/* 124 */     CustomColorModeDialogFragment fragment = new CustomColorModeDialogFragment();
/* 125 */     Bundle args = new Bundle();
/* 126 */     args.putInt(ARG_BGCOLOR, bgColor);
/* 127 */     args.putInt(ARG_TXTCOLOR, txtColor);
/* 128 */     fragment.setArguments(args);
/* 129 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate(Bundle savedInstanceState) {
/* 139 */     super.onCreate(savedInstanceState);
/* 140 */     if (getArguments() != null) {
/* 141 */       this.mCurrentDarkColor = getArguments().getInt(ARG_TXTCOLOR);
/* 142 */       this.mCurrentLightColor = getArguments().getInt(ARG_BGCOLOR);
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
/*     */   @SuppressLint({"InflateParams"})
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 156 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 157 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/*     */     
/* 159 */     View view = inflater.inflate(R.layout.fragment_custom_color_mode_dialog, null);
/* 160 */     builder.setView(view);
/*     */     
/* 162 */     builder.setPositiveButton(null, null);
/*     */     
/* 164 */     builder.setNegativeButton(null, null);
/*     */     
/* 166 */     this.mViewPager = (CustomViewPager)view.findViewById(R.id.colormode_viewpager);
/* 167 */     this.mViewPager.setDimens(((getResources().getConfiguration()).orientation == 1), 
/* 168 */         getResources().getDimensionPixelSize(R.dimen.colormode_height_portrait), 
/* 169 */         getResources().getDimensionPixelSize(R.dimen.colormode_height_landscape));
/*     */     
/* 171 */     this.mViewPager.setOnKeyListener(new View.OnKeyListener()
/*     */         {
/*     */           public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 174 */             return true;
/*     */           }
/*     */         });
/* 177 */     builder.setOnKeyListener(new DialogInterface.OnKeyListener()
/*     */         {
/*     */           public boolean onKey(DialogInterface dialogInterface, int keyCode, KeyEvent event) {
/* 180 */             if (event.getKeyCode() == 4) {
/* 181 */               if (CustomColorModeDialogFragment.this.mEditing) {
/* 182 */                 CustomColorModeDialogFragment.this.mPickerCancelListener.onClick(null);
/*     */               }
/* 184 */               return false;
/*     */             } 
/* 186 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 190 */     this.mColorPickingView = inflater.inflate(R.layout.fragment_custom_color_mode_colorpicker_page, null);
/* 191 */     this.mPresetView = inflater.inflate(R.layout.fragment_custom_color_mode_preset_page, null);
/*     */     
/* 193 */     this.mPresetCancelBtn = (Button)this.mPresetView.findViewById(R.id.colormode_preset_cancelbtn);
/* 194 */     this.mPresetOKBtn = (Button)this.mPresetView.findViewById(R.id.colormode_preset_okbtn);
/*     */     
/* 196 */     this.mPickerCancelBtn = (Button)this.mColorPickingView.findViewById(R.id.colormode_picker_cancelbtn);
/* 197 */     this.mPickerOKBtn = (Button)this.mColorPickingView.findViewById(R.id.colormode_picker_okbtn);
/*     */     
/* 199 */     String serializedPresetArray = PdfViewCtrlSettingsManager.getColorModePresets(getContext());
/* 200 */     if (Utils.isNullOrEmpty(serializedPresetArray)) {
/* 201 */       serializedPresetArray = loadDefaultPresets();
/*     */     }
/* 203 */     JsonParser jParser = new JsonParser();
/* 204 */     this.mPresetArray = jParser.parse(serializedPresetArray).getAsJsonArray();
/*     */     
/* 206 */     SimpleRecyclerView presetRecyclerView = (SimpleRecyclerView)this.mPresetView.findViewById(R.id.colormode_preset_recycler);
/* 207 */     presetRecyclerView.initView(4);
/*     */     
/* 209 */     this.mPresetRecyclerAdapter = new PresetRecyclerAdapter(this.mPresetArray, 4, new PresetRecyclerAdapterListener()
/*     */         {
/*     */           public void onPositionSelected(int position) {
/* 212 */             JsonObject presetObj = CustomColorModeDialogFragment.this.mPresetArray.get(position).getAsJsonObject();
/* 213 */             CustomColorModeDialogFragment.this.mCurrentDarkColor = presetObj.get("fg").getAsInt();
/* 214 */             CustomColorModeDialogFragment.this.mCurrentLightColor = presetObj.get("bg").getAsInt();
/*     */           }
/*     */         });
/* 217 */     presetRecyclerView.setAdapter((RecyclerView.Adapter)this.mPresetRecyclerAdapter);
/* 218 */     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)this.mPresetRecyclerAdapter);
/* 219 */     this.mPresetRecyclerAdapter.setSelectedPos(PdfViewCtrlSettingsManager.getSelectedColorModePreset(getContext()));
/*     */     
/* 221 */     this.mColorCompSelector = (SegmentedGroup)this.mColorPickingView.findViewById(R.id.colormode_comp_selector);
/* 222 */     this.mColorCompSelector.check(R.id.colormode_bgcolor_selector);
/* 223 */     this.mColorCompSelector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
/*     */         {
/*     */           public void onCheckedChanged(RadioGroup group, int checkedId) {
/* 226 */             if (checkedId == R.id.colormode_textcolor_selector) {
/* 227 */               CustomColorModeDialogFragment.this.mDarkSelected = true;
/* 228 */               CustomColorModeDialogFragment.this.mColorPickerView.setColor(CustomColorModeDialogFragment.this.mCurrentDarkColor);
/* 229 */             } else if (checkedId == R.id.colormode_bgcolor_selector) {
/* 230 */               CustomColorModeDialogFragment.this.mDarkSelected = false;
/* 231 */               CustomColorModeDialogFragment.this.mColorPickerView.setColor(CustomColorModeDialogFragment.this.mCurrentLightColor);
/*     */             } 
/*     */           }
/*     */         });
/* 235 */     this.mColorCompSelector.setTintColor(getResources().getColor(R.color.gray600));
/*     */     
/* 237 */     this.mColorPickerView = (ColorPickerView)this.mColorPickingView.findViewById(R.id.color_picker_picker);
/*     */     
/* 239 */     this.mColorPickerView.setColor(this.mCurrentLightColor);
/*     */     
/* 241 */     this.mColorPickerView.setListener(new ColorPickerView.ColorPickerViewListener()
/*     */         {
/*     */           public void onColorChanged(int color) {
/* 244 */             if (CustomColorModeDialogFragment.this.mDarkSelected) {
/* 245 */               CustomColorModeDialogFragment.this.mCurrentDarkColor = color;
/*     */             } else {
/* 247 */               CustomColorModeDialogFragment.this.mCurrentLightColor = color;
/*     */             } 
/* 249 */             CustomColorModeDialogFragment.this.updatePreview();
/*     */           }
/*     */         });
/*     */     
/* 253 */     this.mPreviewLayout = (LinearLayout)this.mColorPickingView.findViewById(R.id.colormode_testchars);
/*     */     
/* 255 */     this.mPreviewLayout.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 258 */             int temp = CustomColorModeDialogFragment.this.mCurrentDarkColor;
/* 259 */             CustomColorModeDialogFragment.this.mCurrentDarkColor = CustomColorModeDialogFragment.this.mCurrentLightColor;
/* 260 */             CustomColorModeDialogFragment.this.mCurrentLightColor = temp;
/*     */             
/* 262 */             CustomColorModeDialogFragment.this.mDarkSelected = true;
/* 263 */             CustomColorModeDialogFragment.this.mColorPickerView.setColor(CustomColorModeDialogFragment.this.mCurrentDarkColor);
/* 264 */             CustomColorModeDialogFragment.this.mColorCompSelector.check(R.id.colormode_textcolor_selector);
/*     */           }
/*     */         });
/*     */     
/* 268 */     this.mViewPager.setAdapter(new CustomPagerAdapter());
/*     */     
/* 270 */     updatePreview();
/*     */     
/* 272 */     return (Dialog)builder.create();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStart() {
/* 281 */     super.onStart();
/*     */     
/* 283 */     this.mPresetOKBtn.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 286 */             if (CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos() < 0) {
/* 287 */               CustomColorModeDialogFragment.this.dismiss();
/*     */               return;
/*     */             } 
/* 290 */             CustomColorModeDialogFragment.this.savePresets();
/* 291 */             PdfViewCtrlSettingsManager.setSelectedColorModePreset(CustomColorModeDialogFragment.this.getContext(), CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos());
/* 292 */             if (CustomColorModeDialogFragment.this.mCustomColorModeSelectedListener != null)
/* 293 */               CustomColorModeDialogFragment.this.mCustomColorModeSelectedListener.onCustomColorModeSelected(CustomColorModeDialogFragment.this.mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor); 
/* 294 */             if (CustomColorModeDialogFragment.this.mPreviousClickedPosition > -1) {
/* 295 */               AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 296 */                   AnalyticsParam.customColorParam(1, CustomColorModeDialogFragment.this
/* 297 */                     .mPreviousClickedPosition, CustomColorModeDialogFragment.this.isColorDefault(CustomColorModeDialogFragment.this.mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor), CustomColorModeDialogFragment.this
/* 298 */                     .mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor, true));
/*     */             }
/* 300 */             CustomColorModeDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 303 */     this.mPresetCancelBtn.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 306 */             CustomColorModeDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 310 */     this.mPickerOKBtn.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 313 */             if (CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos() < 0) {
/* 314 */               CustomColorModeDialogFragment.this.dismiss();
/*     */               return;
/*     */             } 
/* 317 */             CustomColorModeDialogFragment.this.savePresets();
/* 318 */             Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)CustomColorModeDialogFragment.this.mPresetRecyclerAdapter);
/* 319 */             CustomColorModeDialogFragment.this.mViewPager.setCurrentItem(0, true);
/* 320 */             AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 321 */                 AnalyticsParam.customColorParam(2, CustomColorModeDialogFragment.this
/* 322 */                   .mPresetRecyclerAdapter.getSelectedPos(), CustomColorModeDialogFragment.this
/* 323 */                   .isColorDefault(CustomColorModeDialogFragment.this.mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor), CustomColorModeDialogFragment.this
/* 324 */                   .mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor));
/*     */           }
/*     */         });
/* 327 */     this.mPickerCancelBtn.setOnClickListener(this.mPickerCancelListener);
/*     */   }
/*     */   
/*     */   private String loadDefaultPresets() {
/* 331 */     int defaultColorArraySize = DEFAULT_PRESET_COLORS.length;
/* 332 */     JsonArray array = new JsonArray();
/* 333 */     for (int i = 0; i < 15; i++) {
/* 334 */       JsonObject obj = new JsonObject();
/* 335 */       if (i < defaultColorArraySize) {
/* 336 */         obj.addProperty("bg", Integer.valueOf(DEFAULT_PRESET_COLORS[i][0]));
/* 337 */         obj.addProperty("fg", Integer.valueOf(DEFAULT_PRESET_COLORS[i][1]));
/*     */       } else {
/* 339 */         obj.addProperty("bg", Integer.valueOf(-1));
/* 340 */         obj.addProperty("fg", Integer.valueOf(-16777216));
/*     */       } 
/* 342 */       array.add((JsonElement)obj);
/*     */     } 
/*     */     
/* 345 */     Gson gson = new Gson();
/* 346 */     String serialize = gson.toJson((JsonElement)array);
/* 347 */     PdfViewCtrlSettingsManager.setColorModePresets(getContext(), serialize);
/* 348 */     return serialize;
/*     */   }
/*     */   
/*     */   private void loadDefaultPresets(JsonArray presets) {
/* 352 */     int defaultColorArraySize = DEFAULT_PRESET_COLORS.length;
/* 353 */     for (int i = 0; i < 15; i++) {
/* 354 */       if (i < presets.size()) {
/* 355 */         presets.get(i).getAsJsonObject().remove("bg");
/* 356 */         presets.get(i).getAsJsonObject().remove("fg");
/*     */       } 
/* 358 */       int bg = (i < defaultColorArraySize) ? DEFAULT_PRESET_COLORS[i][0] : -1;
/* 359 */       int fg = (i < defaultColorArraySize) ? DEFAULT_PRESET_COLORS[i][1] : -16777216;
/* 360 */       if (i < presets.size()) {
/* 361 */         presets.get(i).getAsJsonObject().addProperty("bg", Integer.valueOf(bg));
/* 362 */         presets.get(i).getAsJsonObject().addProperty("fg", Integer.valueOf(fg));
/*     */       } else {
/* 364 */         JsonObject obj = new JsonObject();
/* 365 */         obj.addProperty("bg", Integer.valueOf(bg));
/* 366 */         obj.addProperty("fg", Integer.valueOf(fg));
/* 367 */         presets.add((JsonElement)obj);
/*     */       } 
/*     */     } 
/* 370 */     this.mCurrentDarkColor = -16777216;
/* 371 */     this.mCurrentLightColor = -1;
/* 372 */     this.mPresetRecyclerAdapter.setSelectedPos(0);
/*     */   }
/*     */   
/*     */   private void savePresets() {
/* 376 */     int selectedPreset = this.mPresetRecyclerAdapter.getSelectedPos();
/* 377 */     if (selectedPreset < 0)
/*     */       return; 
/* 379 */     this.mPresetArray.get(selectedPreset).getAsJsonObject().remove("bg");
/* 380 */     this.mPresetArray.get(selectedPreset).getAsJsonObject().remove("fg");
/* 381 */     this.mPresetArray.get(selectedPreset).getAsJsonObject().addProperty("bg", Integer.valueOf(this.mCurrentLightColor));
/* 382 */     this.mPresetArray.get(selectedPreset).getAsJsonObject().addProperty("fg", Integer.valueOf(this.mCurrentDarkColor));
/* 383 */     Gson gson = new Gson();
/* 384 */     String serialized = gson.toJson((JsonElement)this.mPresetArray);
/* 385 */     PdfViewCtrlSettingsManager.setColorModePresets(getContext(), serialized);
/*     */   }
/*     */   
/*     */   private void updatePreview() {
/* 389 */     this.mPreviewLayout.setBackgroundColor(this.mCurrentLightColor);
/*     */     
/* 391 */     int redBase = (this.mCurrentDarkColor & 0xFF0000) >> 16;
/* 392 */     int greenBase = (this.mCurrentDarkColor & 0xFF00) >> 8;
/* 393 */     int blueBase = this.mCurrentDarkColor & 0xFF;
/*     */     
/* 395 */     int redDiff = ((this.mCurrentLightColor & 0xFF0000) >> 16) - redBase;
/* 396 */     int greenDiff = ((this.mCurrentLightColor & 0xFF00) >> 8) - greenBase;
/* 397 */     int blueDiff = (this.mCurrentLightColor & 0xFF) - blueBase;
/*     */     
/* 399 */     for (int i = 0; i < this.mPreviewLayout.getChildCount(); i++) {
/* 400 */       TextView tView = (TextView)this.mPreviewLayout.getChildAt(i);
/* 401 */       float t = i * 0.2F;
/*     */       
/* 403 */       int redComp = redBase + (int)(redDiff * t);
/* 404 */       int greenComp = greenBase + (int)(greenDiff * t);
/* 405 */       int blueComp = blueBase + (int)(blueDiff * t);
/*     */       
/* 407 */       int finalColor = -16777216 + (redComp << 16 & 0xFF0000) + (greenComp << 8 & 0xFF00) + (blueComp & 0xFF);
/*     */       
/* 409 */       tView.setTextColor(finalColor);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 416 */     super.onDismiss(dialog);
/* 417 */     if (!this.mHasAction) {
/* 418 */       AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 419 */           AnalyticsParam.customColorParam(5));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomColorModeSelectedListener(CustomColorModeSelectedListener listener) {
/* 428 */     this.mCustomColorModeSelectedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface CustomColorModeSelectedListener
/*     */   {
/*     */     void onCustomColorModeSelected(int param1Int1, int param1Int2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class CustomPagerAdapter
/*     */     extends PagerAdapter
/*     */   {
/*     */     private CustomPagerAdapter() {}
/*     */ 
/*     */     
/*     */     public Object instantiateItem(ViewGroup collection, int pos) {
/* 447 */       if (pos == 0) {
/* 448 */         collection.addView(CustomColorModeDialogFragment.this.mPresetView);
/* 449 */         return CustomColorModeDialogFragment.this.mPresetView;
/* 450 */       }  if (pos == 1) {
/* 451 */         collection.addView(CustomColorModeDialogFragment.this.mColorPickingView);
/* 452 */         return CustomColorModeDialogFragment.this.mColorPickingView;
/*     */       } 
/* 454 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void destroyItem(ViewGroup collection, int pos, Object obj) {
/* 459 */       collection.removeView((View)obj);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 464 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isViewFromObject(View view, Object object) {
/* 469 */       return (view == object);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class PresetRecyclerAdapter
/*     */     extends SimpleRecyclerViewAdapter<JsonObject, RecyclerView.ViewHolder>
/*     */   {
/*     */     private JsonArray mItems;
/*     */     
/* 479 */     private int mSelectedPosition = -1;
/* 480 */     private ArrayList<ItemViewHolder> mHolders = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private PresetRecyclerAdapterListener mListener;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PresetRecyclerAdapter(JsonArray items, int spanCount, PresetRecyclerAdapterListener listener) {
/* 491 */       this.mItems = items;
/* 492 */       this.mListener = listener;
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
/*     */     public JsonObject getItem(int position) {
/* 504 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void add(JsonObject item) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean remove(JsonObject item) {
/* 522 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonObject removeAt(int location) {
/* 530 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void insert(JsonObject item, int position) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getItemViewType(int position) {
/* 548 */       return 0;
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
/*     */     public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/* 560 */       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_colorpreset_list, parent, false);
/*     */       
/* 562 */       return new ItemViewHolder(view, -1, false);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint({"RecyclerView"}) int position) {
/* 570 */       ItemViewHolder itemViewHolder = (ItemViewHolder)holder;
/* 571 */       itemViewHolder.position = position;
/* 572 */       itemViewHolder.isResetButton = (position == getItemCount() - 1);
/* 573 */       if (position < this.mHolders.size()) {
/* 574 */         this.mHolders.remove(position);
/* 575 */         this.mHolders.add(position, itemViewHolder);
/*     */       } else {
/* 577 */         this.mHolders.add(itemViewHolder);
/*     */       } 
/*     */       
/* 580 */       if (position == getItemCount() - 1) {
/* 581 */         itemViewHolder.selectedBgIcon.setVisibility(4);
/* 582 */         itemViewHolder.editButton.setVisibility(8);
/* 583 */         itemViewHolder.fgIcon.setVisibility(8);
/* 584 */         itemViewHolder.bgIcon.setImageDrawable(CustomColorModeDialogFragment.this.getResources().getDrawable(R.drawable.ic_settings_backup_restore_black_24dp));
/* 585 */         itemViewHolder.bgIcon.getDrawable().mutate().setColorFilter(CustomColorModeDialogFragment.this.getResources().getColor(R.color.gray600), PorterDuff.Mode.SRC_IN);
/*     */         
/*     */         return;
/*     */       } 
/* 589 */       JsonObject jObj = this.mItems.get(position).getAsJsonObject();
/* 590 */       if (this.mSelectedPosition == position) {
/* 591 */         itemViewHolder.selectedBgIcon.setVisibility(0);
/* 592 */         itemViewHolder.editButton.setVisibility(0);
/* 593 */         itemViewHolder.editButton.setEnabled(true);
/*     */       } else {
/* 595 */         itemViewHolder.selectedBgIcon.setVisibility(4);
/* 596 */         itemViewHolder.editButton.setVisibility(4);
/* 597 */         itemViewHolder.fgIcon.setVisibility(0);
/* 598 */         itemViewHolder.bgIcon.setColorFilter(null);
/* 599 */         itemViewHolder.bgIcon.setImageDrawable(CustomColorModeDialogFragment.this.getResources().getDrawable(R.drawable.ic_custommode_icon));
/*     */       } 
/*     */       
/* 602 */       int bgColor = jObj.get("bg").getAsInt();
/* 603 */       int fgColor = jObj.get("fg").getAsInt();
/*     */       
/* 605 */       Drawable drawable = itemViewHolder.bgIcon.getDrawable();
/* 606 */       drawable.mutate().setColorFilter(bgColor, PorterDuff.Mode.SRC_ATOP);
/*     */       
/* 608 */       itemViewHolder.fgIcon.setColorFilter(fgColor);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getItemCount() {
/* 617 */       return this.mItems.size() + 1;
/*     */     }
/*     */     
/*     */     void setSelectedPos(int position) {
/* 621 */       this.mSelectedPosition = position;
/* 622 */       for (int i = 0; i < this.mHolders.size() - 1; i++) {
/* 623 */         if (i == position) {
/* 624 */           ((ItemViewHolder)this.mHolders.get(i)).selectedBgIcon.setVisibility(0);
/* 625 */           ((ItemViewHolder)this.mHolders.get(i)).editButton.setVisibility(0);
/* 626 */           ((ItemViewHolder)this.mHolders.get(i)).editButton.setEnabled(true);
/*     */         }
/* 628 */         else if (((ItemViewHolder)this.mHolders.get(i)).editButton.getVisibility() != 8) {
/*     */           
/* 630 */           ((ItemViewHolder)this.mHolders.get(i)).selectedBgIcon.setVisibility(4);
/* 631 */           ((ItemViewHolder)this.mHolders.get(i)).editButton.setVisibility(4);
/* 632 */           ((ItemViewHolder)this.mHolders.get(i)).editButton.setEnabled(false);
/*     */         } 
/*     */       } 
/* 635 */       if (this.mSelectedPosition >= 0) {
/* 636 */         this.mListener.onPositionSelected(position);
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getSelectedPos() {
/* 645 */       return this.mSelectedPosition;
/*     */     }
/*     */     
/*     */     int[] getColorsAtPos(int position) {
/* 649 */       JsonObject jObj = this.mItems.get(position).getAsJsonObject();
/* 650 */       int[] result = new int[2];
/* 651 */       result[0] = jObj.get("bg").getAsInt();
/* 652 */       result[1] = jObj.get("fg").getAsInt();
/* 653 */       return result;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateSpanCount(int count) {}
/*     */ 
/*     */     
/*     */     class ItemViewHolder
/*     */       extends RecyclerView.ViewHolder
/*     */       implements View.OnClickListener
/*     */     {
/*     */       ImageView fgIcon;
/*     */       
/*     */       ImageView bgIcon;
/*     */       
/*     */       ImageView selectedBgIcon;
/*     */       Button editButton;
/*     */       public int position;
/*     */       boolean isResetButton;
/*     */       
/*     */       ItemViewHolder(View itemView, int position, boolean isResetButton) {
/* 674 */         super(itemView);
/*     */         
/* 676 */         this.fgIcon = (ImageView)itemView.findViewById(R.id.fg_icon);
/* 677 */         this.bgIcon = (ImageView)itemView.findViewById(R.id.bg_icon);
/* 678 */         this.selectedBgIcon = (ImageView)itemView.findViewById(R.id.select_bg_icon);
/* 679 */         this.editButton = (Button)itemView.findViewById(R.id.color_editbutton);
/*     */         
/* 681 */         int accentColor = Utils.getAccentColor(CustomColorModeDialogFragment.this.getContext());
/* 682 */         this.selectedBgIcon.setColorFilter(accentColor, PorterDuff.Mode.SRC_IN);
/*     */         
/* 684 */         this.position = position;
/* 685 */         this.isResetButton = isResetButton;
/* 686 */         this.bgIcon.setOnClickListener(this);
/* 687 */         if (!isResetButton) {
/* 688 */           this.editButton.setOnClickListener(this);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public void onClick(View v) {
/* 694 */         if (v == this.bgIcon) {
/* 695 */           CustomColorModeDialogFragment.this.mHasAction = true;
/* 696 */           if (this.isResetButton) {
/* 697 */             AlertDialog.Builder alertBuilder = new AlertDialog.Builder(CustomColorModeDialogFragment.this.getContext());
/* 698 */             alertBuilder.setTitle(R.string.pref_colormode_custom_defaults);
/* 699 */             alertBuilder.setMessage(R.string.pref_colormode_custom_defaults_msg);
/* 700 */             alertBuilder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener()
/*     */                 {
/*     */                   public void onClick(DialogInterface dialog, int which) {
/* 703 */                     CustomColorModeDialogFragment.this.loadDefaultPresets(CustomColorModeDialogFragment.this.mPresetArray);
/* 704 */                     Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)CustomColorModeDialogFragment.this.mPresetRecyclerAdapter);
/* 705 */                     AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 706 */                         AnalyticsParam.customColorParam(4));
/* 707 */                     dialog.dismiss();
/*     */                   }
/*     */                 });
/* 710 */             alertBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */                 {
/*     */                   public void onClick(DialogInterface dialog, int which) {
/* 713 */                     AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 714 */                         AnalyticsParam.customColorParam(6));
/* 715 */                     dialog.dismiss();
/*     */                   }
/*     */                 });
/* 718 */             alertBuilder.create().show();
/*     */             
/*     */             return;
/*     */           } 
/* 722 */           if (CustomColorModeDialogFragment.this.mPreviousClickedPosition > -1 && CustomColorModeDialogFragment.this.mPreviousClickedPosition != this.position) {
/* 723 */             AnalyticsHandlerAdapter.getInstance().sendEvent(57, 
/* 724 */                 AnalyticsParam.customColorParam(1, CustomColorModeDialogFragment.this
/* 725 */                   .mPreviousClickedPosition, CustomColorModeDialogFragment.this
/* 726 */                   .isColorDefault(CustomColorModeDialogFragment.this.mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor), CustomColorModeDialogFragment.this
/* 727 */                   .mCurrentLightColor, CustomColorModeDialogFragment.this.mCurrentDarkColor, false));
/*     */           }
/*     */           
/* 730 */           PresetRecyclerAdapter.this.setSelectedPos(this.position);
/* 731 */           CustomColorModeDialogFragment.this.mPreviousClickedPosition = this.position;
/* 732 */         } else if (v == this.editButton) {
/* 733 */           if (!v.isEnabled())
/*     */             return; 
/* 735 */           int selectedPos = CustomColorModeDialogFragment.this.mPresetRecyclerAdapter.getSelectedPos();
/* 736 */           if (selectedPos < 0)
/*     */             return; 
/* 738 */           CustomColorModeDialogFragment.this.mHasAction = true;
/* 739 */           CustomColorModeDialogFragment.this.mDarkSelected = false;
/* 740 */           CustomColorModeDialogFragment.this.mColorPickerView.setColor(CustomColorModeDialogFragment.this.mCurrentLightColor);
/* 741 */           CustomColorModeDialogFragment.this.mColorCompSelector.check(R.id.colormode_bgcolor_selector);
/* 742 */           CustomColorModeDialogFragment.this.mEditing = true;
/* 743 */           CustomColorModeDialogFragment.this.mViewPager.setCurrentItem(1, true);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean isColorDefault(int bgColor, int fgColor) {
/* 750 */     for (int[] defaultColor : DEFAULT_PRESET_COLORS) {
/* 751 */       if (bgColor == defaultColor[0] && fgColor == defaultColor[1]) {
/* 752 */         return true;
/*     */       }
/*     */     } 
/* 755 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static interface PresetRecyclerAdapterListener
/*     */   {
/*     */     void onPositionSelected(int param1Int);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CustomViewPager
/*     */     extends ViewPager
/*     */   {
/*     */     private boolean mIsPortrait = true;
/*     */ 
/*     */     
/* 775 */     private int mHeightPortrait = 0;
/* 776 */     private int mHeightLandscape = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CustomViewPager(Context context) {
/* 782 */       super(context);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CustomViewPager(Context context, AttributeSet attributeSet) {
/* 789 */       super(context, attributeSet);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean onInterceptTouchEvent(MotionEvent event) {
/* 799 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void setDimens(boolean isPortrait, int heightPortrait, int heightLandscape) {
/* 809 */       this.mIsPortrait = isPortrait;
/* 810 */       this.mHeightPortrait = heightPortrait;
/* 811 */       this.mHeightLandscape = heightLandscape;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @SuppressLint({"ClickableViewAccessibility"})
/*     */     public boolean onTouchEvent(MotionEvent event) {
/* 822 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onConfigurationChanged(Configuration newConfig) {
/* 831 */       this.mIsPortrait = (newConfig.orientation == 1);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
/* 841 */       int height = 0;
/* 842 */       int h = this.mIsPortrait ? this.mHeightPortrait : this.mHeightLandscape;
/* 843 */       if (h > height) height = h;
/*     */       
/* 845 */       heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, 1073741824);
/*     */       
/* 847 */       super.onMeasure(widthMeasureSpec, heightMeasureSpec);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\CustomColorModeDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */