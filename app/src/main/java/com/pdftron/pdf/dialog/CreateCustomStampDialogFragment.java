/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.Bitmap;
/*     */ import android.graphics.Rect;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Bundle;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.CompoundButton;
/*     */ import android.widget.ImageButton;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.AppCompatEditText;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import androidx.appcompat.widget.SwitchCompat;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.adapter.CustomStampColorAdapter;
/*     */ import com.pdftron.pdf.asynctask.CreateBitmapFromCustomStampTask;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnCustomStampChangedListener;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.model.CustomStampPreviewAppearance;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.lang.ref.WeakReference;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CreateCustomStampDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */   implements CreateBitmapFromCustomStampTask.OnCustomStampCreatedCallback
/*     */ {
/*  48 */   public static final String TAG = CreateCustomStampDialogFragment.class.getName();
/*     */   
/*     */   private static final String BUNDLE_EDIT_INDEX = "edit_index";
/*     */   
/*     */   private static final int COLOR_COLUMN_COUNT = 3;
/*  53 */   private Shape mShapeSelected = Shape.ROUNDED_RECTANGLE;
/*     */   
/*     */   private CustomStampPreviewAppearance[] mCustomStampPreviewAppearances;
/*     */   
/*     */   private int mEditIndex;
/*     */   
/*     */   private AppCompatEditText mStampText;
/*     */   private AppCompatImageView mStampPreview;
/*     */   private SwitchCompat mDateSwitch;
/*     */   private SwitchCompat mTimeSwitch;
/*     */   private ImageButton mPointingLeftImage;
/*     */   private ImageButton mPointingRightImage;
/*     */   private ImageButton mRoundedRectangleImage;
/*     */   private CustomStampColorAdapter mCustomStampColorAdapter;
/*     */   private CreateBitmapFromCustomStampTask mCreateBitmapFromCustomStampTask;
/*     */   private OnCustomStampChangedListener mOnCustomStampChangedListener;
/*     */   
/*     */   public static CreateCustomStampDialogFragment newInstance(CustomStampPreviewAppearance[] customStampPreviewAppearances) {
/*  71 */     return newInstance(customStampPreviewAppearances, -1);
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
/*     */   public static CreateCustomStampDialogFragment newInstance(CustomStampPreviewAppearance[] customStampPreviewAppearances, int editIndex) {
/*  83 */     CreateCustomStampDialogFragment fragment = new CreateCustomStampDialogFragment();
/*  84 */     Bundle bundle = new Bundle();
/*  85 */     CustomStampPreviewAppearance.putCustomStampAppearancesToBundle(bundle, customStampPreviewAppearances);
/*  86 */     bundle.putInt("edit_index", editIndex);
/*  87 */     fragment.setArguments(bundle);
/*  88 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnCustomStampChangedListener(OnCustomStampChangedListener listener) {
/*  97 */     this.mOnCustomStampChangedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCustomStampCreated(@Nullable Bitmap bitmap) {
/* 105 */     if (this.mStampPreview != null) {
/* 106 */       this.mStampPreview.setImageBitmap(bitmap);
/* 107 */       if (bitmap != null) {
/* 108 */         ViewGroup.LayoutParams lp = this.mStampPreview.getLayoutParams();
/* 109 */         lp.height = bitmap.getHeight();
/* 110 */         lp.width = bitmap.getWidth();
/* 111 */         this.mStampPreview.setLayoutParams(lp);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/* 119 */     View view = inflater.inflate(R.layout.fragment_create_custom_rubber_stamp_dialog, container, false);
/*     */     
/* 121 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.create_stamp_dialog_toolbar);
/* 122 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 125 */             CreateCustomStampDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 128 */     toolbar.inflateMenu(R.menu.save);
/* 129 */     toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem item)
/*     */           {
/* 133 */             int id = item.getItemId();
/* 134 */             if (id == R.id.action_save) {
/* 135 */               CreateCustomStampDialogFragment.this.saveCustomStamp();
/* 136 */               CreateCustomStampDialogFragment.this.dismiss();
/* 137 */               return true;
/*     */             } 
/* 139 */             return false;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 144 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 149 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/* 151 */     Context context = view.getContext();
/*     */     
/* 153 */     Bundle bundle = getArguments();
/* 154 */     this.mCustomStampPreviewAppearances = CustomStampPreviewAppearance.getCustomStampAppearancesFromBundle(bundle);
/* 155 */     if (this.mCustomStampPreviewAppearances == null || this.mCustomStampPreviewAppearances.length == 0) {
/*     */       return;
/*     */     }
/* 158 */     this.mEditIndex = bundle.getInt("edit_index", -1);
/*     */     
/* 160 */     this.mStampText = (AppCompatEditText)view.findViewById(R.id.stamp_text);
/* 161 */     this.mStampText.addTextChangedListener(new TextWatcher()
/*     */         {
/*     */           public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 168 */             CreateCustomStampDialogFragment.this.showPreview();
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void afterTextChanged(Editable s) {}
/*     */         });
/* 175 */     CompoundButton.OnCheckedChangeListener checkedChangeListener = new CompoundButton.OnCheckedChangeListener()
/*     */       {
/*     */         public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
/*     */         {
/* 179 */           CreateCustomStampDialogFragment.this.showPreview();
/*     */         }
/*     */       };
/* 182 */     View.OnClickListener onShapeClickListener = new View.OnClickListener()
/*     */       {
/*     */         public void onClick(View v) {
/* 185 */           if (v == CreateCustomStampDialogFragment.this.mPointingLeftImage) {
/* 186 */             CreateCustomStampDialogFragment.this.mShapeSelected = Shape.POINTING_LEFT;
/* 187 */           } else if (v == CreateCustomStampDialogFragment.this.mPointingRightImage) {
/* 188 */             CreateCustomStampDialogFragment.this.mShapeSelected = Shape.POINTING_RIGHT;
/* 189 */           } else if (v == CreateCustomStampDialogFragment.this.mRoundedRectangleImage) {
/* 190 */             CreateCustomStampDialogFragment.this.mShapeSelected = Shape.ROUNDED_RECTANGLE;
/*     */           } 
/* 192 */           CreateCustomStampDialogFragment.this.showPreview();
/*     */         }
/*     */       };
/* 195 */     this.mDateSwitch = (SwitchCompat)view.findViewById(R.id.date_switch);
/* 196 */     this.mDateSwitch.setOnCheckedChangeListener(checkedChangeListener);
/* 197 */     this.mTimeSwitch = (SwitchCompat)view.findViewById(R.id.time_switch);
/* 198 */     this.mTimeSwitch.setOnCheckedChangeListener(checkedChangeListener);
/* 199 */     this.mPointingLeftImage = (ImageButton)view.findViewById(R.id.pointing_left_shape);
/* 200 */     this.mPointingLeftImage.setOnClickListener(onShapeClickListener);
/* 201 */     this.mPointingRightImage = (ImageButton)view.findViewById(R.id.pointing_right_shape);
/* 202 */     this.mPointingRightImage.setOnClickListener(onShapeClickListener);
/* 203 */     this.mRoundedRectangleImage = (ImageButton)view.findViewById(R.id.rounded_rectangle_shape);
/* 204 */     this.mRoundedRectangleImage.setOnClickListener(onShapeClickListener);
/* 205 */     this.mStampPreview = (AppCompatImageView)view.findViewById(R.id.stamp_preview);
/*     */     
/* 207 */     SimpleRecyclerView recyclerView = (SimpleRecyclerView)view.findViewById(R.id.stamp_color_recycler);
/* 208 */     recyclerView.initView(3, 0);
/* 209 */     int len = this.mCustomStampPreviewAppearances.length;
/* 210 */     int[] bgColors = new int[len];
/* 211 */     int[] textColors = new int[len];
/* 212 */     for (int i = 0; i < len; i++) {
/* 213 */       bgColors[i] = (this.mCustomStampPreviewAppearances[i]).bgColorMiddle;
/* 214 */       textColors[i] = (this.mCustomStampPreviewAppearances[i]).textColor;
/*     */     } 
/* 216 */     this.mCustomStampColorAdapter = new CustomStampColorAdapter(bgColors, textColors);
/* 217 */     recyclerView.setAdapter((RecyclerView.Adapter)this.mCustomStampColorAdapter);
/* 218 */     recyclerView.addItemDecoration(new CustomSpaceItemDecoration(context));
/*     */     
/* 220 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 221 */     itemClickHelper.attachToRecyclerView((RecyclerView)recyclerView);
/* 222 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView parent, View view, int position, long id) {
/* 225 */             CustomStampColorAdapter adapter = (CustomStampColorAdapter)parent.getAdapter();
/* 226 */             adapter.select(position);
/* 227 */             Utils.safeNotifyDataSetChanged((RecyclerView.Adapter)adapter);
/* 228 */             CreateCustomStampDialogFragment.this.showPreview();
/*     */           }
/*     */         });
/*     */     
/* 232 */     if (this.mEditIndex >= 0) {
/* 233 */       Obj stampObj = CustomStampOption.getCustomStampObj(context, this.mEditIndex);
/* 234 */       if (stampObj == null) {
/* 235 */         this.mEditIndex = -1;
/*     */       } else {
/*     */         
/*     */         try {
/* 239 */           CustomStampOption customStamp = new CustomStampOption(stampObj);
/*     */           
/* 241 */           this.mStampText.setText(customStamp.text);
/* 242 */           if (customStamp.isPointingLeft) {
/* 243 */             this.mShapeSelected = Shape.POINTING_LEFT;
/* 244 */           } else if (customStamp.isPointingRight) {
/* 245 */             this.mShapeSelected = Shape.POINTING_RIGHT;
/*     */           } else {
/* 247 */             this.mShapeSelected = Shape.ROUNDED_RECTANGLE;
/*     */           } 
/* 249 */           int index = bgColors.length - 1;
/* 250 */           for (; index > 0 && (
/* 251 */             customStamp.textColor != textColors[index] || customStamp.bgColorStart != (this.mCustomStampPreviewAppearances[index]).bgColorStart || customStamp.bgColorEnd != (this.mCustomStampPreviewAppearances[index]).bgColorEnd); index--);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 257 */           this.mTimeSwitch.setChecked(customStamp.hasTimeStamp());
/* 258 */           this.mDateSwitch.setChecked(customStamp.hasDateStamp());
/* 259 */           this.mCustomStampColorAdapter.select(index);
/* 260 */         } catch (Exception e) {
/* 261 */           this.mEditIndex = -1;
/* 262 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 267 */     showPreview();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 272 */     super.onDismiss(dialog);
/* 273 */     if (this.mCreateBitmapFromCustomStampTask != null) {
/* 274 */       this.mCreateBitmapFromCustomStampTask.cancel(true);
/* 275 */       this.mCreateBitmapFromCustomStampTask.setOnCustomStampCreatedCallback(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showPreview() {
/* 283 */     Context context = getContext();
/* 284 */     if (context == null || this.mCustomStampPreviewAppearances == null || this.mCustomStampPreviewAppearances.length == 0) {
/*     */       return;
/*     */     }
/*     */     
/* 288 */     String text = getFirstText();
/* 289 */     String secondText = CustomStampOption.createSecondText(this.mDateSwitch.isChecked(), this.mTimeSwitch.isChecked());
/* 290 */     int index = this.mCustomStampColorAdapter.getSelectedIndex();
/*     */     
/* 292 */     int borderColor = (this.mCustomStampPreviewAppearances[index]).borderColor;
/* 293 */     this.mPointingRightImage.setSelected((this.mShapeSelected == Shape.POINTING_RIGHT));
/* 294 */     this.mPointingLeftImage.setSelected((this.mShapeSelected == Shape.POINTING_LEFT));
/* 295 */     this.mRoundedRectangleImage.setSelected((this.mShapeSelected == Shape.ROUNDED_RECTANGLE));
/*     */     
/* 297 */     int bgColorStart = (this.mCustomStampPreviewAppearances[index]).bgColorStart;
/* 298 */     int bgColorEnd = (this.mCustomStampPreviewAppearances[index]).bgColorEnd;
/* 299 */     int textColor = (this.mCustomStampPreviewAppearances[index]).textColor;
/* 300 */     double fillOpacity = (this.mCustomStampPreviewAppearances[index]).fillOpacity;
/*     */     
/* 302 */     CustomStampOption customStampOption = new CustomStampOption(text, secondText, bgColorStart, bgColorEnd, textColor, borderColor, fillOpacity, (this.mShapeSelected == Shape.POINTING_LEFT), (this.mShapeSelected == Shape.POINTING_RIGHT));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 307 */     if (this.mCreateBitmapFromCustomStampTask != null) {
/* 308 */       this.mCreateBitmapFromCustomStampTask.cancel(true);
/*     */     }
/* 310 */     this.mCreateBitmapFromCustomStampTask = new CreateBitmapFromCustomStampTask(context, customStampOption);
/* 311 */     this.mCreateBitmapFromCustomStampTask.setOnCustomStampCreatedCallback(this);
/* 312 */     this.mCreateBitmapFromCustomStampTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */   }
/*     */   
/*     */   @NonNull
/*     */   private String getFirstText() {
/* 317 */     String text = this.mStampText.getText().toString();
/* 318 */     if (Utils.isNullOrEmpty(text)) {
/* 319 */       text = getString(R.string.custom_stamp_text_hint);
/*     */     }
/* 321 */     return text;
/*     */   }
/*     */   
/*     */   private void saveCustomStamp() {
/* 325 */     Context context = getContext();
/* 326 */     if (context == null || this.mOnCustomStampChangedListener == null || this.mCustomStampPreviewAppearances == null || this.mCustomStampPreviewAppearances.length == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 331 */     String text = getFirstText();
/* 332 */     String secondText = CustomStampOption.createSecondText(this.mDateSwitch.isChecked(), this.mTimeSwitch.isChecked());
/* 333 */     int index = this.mCustomStampColorAdapter.getSelectedIndex();
/* 334 */     int bgColorStart = (this.mCustomStampPreviewAppearances[index]).bgColorStart;
/* 335 */     int bgColorEnd = (this.mCustomStampPreviewAppearances[index]).bgColorEnd;
/* 336 */     int textColor = (this.mCustomStampPreviewAppearances[index]).textColor;
/* 337 */     int borderColor = (this.mCustomStampPreviewAppearances[index]).borderColor;
/* 338 */     double fillOpacity = (this.mCustomStampPreviewAppearances[index]).fillOpacity;
/*     */     
/* 340 */     CustomStampOption customStampOption = new CustomStampOption(text, secondText, bgColorStart, bgColorEnd, textColor, borderColor, fillOpacity, (this.mShapeSelected == Shape.POINTING_LEFT), (this.mShapeSelected == Shape.POINTING_RIGHT));
/*     */ 
/*     */ 
/*     */     
/* 344 */     SaveCustomStampOptionTask task = new SaveCustomStampOptionTask(context, this.mEditIndex, customStampOption, this.mOnCustomStampChangedListener);
/* 345 */     task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */   }
/*     */   
/*     */   private static class SaveCustomStampOptionTask
/*     */     extends CustomAsyncTask<Void, Void, Bitmap>
/*     */   {
/*     */     CustomStampOption mCustomStampOption;
/*     */     int mEditIndex;
/*     */     
/*     */     SaveCustomStampOptionTask(Context context, int editIndex, CustomStampOption customStampOption, OnCustomStampChangedListener listener) {
/* 355 */       super(context);
/* 356 */       this.mEditIndex = editIndex;
/* 357 */       this.mCustomStampOption = customStampOption;
/* 358 */       this.mListenerRef = new WeakReference<>(listener);
/* 359 */       this.mSingleLineHeight = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height);
/* 360 */       this.mTwoLinesHeight = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height_two_lines);
/*     */     }
/*     */     private int mSingleLineHeight; private int mTwoLinesHeight; WeakReference<OnCustomStampChangedListener> mListenerRef;
/*     */     
/*     */     protected Bitmap doInBackground(Void... voids) {
/* 365 */       Context context = getContext();
/* 366 */       if (context == null || isCancelled()) {
/* 367 */         return null;
/*     */       }
/*     */       
/*     */       try {
/* 371 */         int height = context.getResources().getDimensionPixelSize(R.dimen.stamp_image_height);
/* 372 */         Bitmap bitmap = CreateBitmapFromCustomStampTask.createBitmapFromCustomStamp(this.mCustomStampOption, this.mSingleLineHeight, this.mTwoLinesHeight);
/*     */         
/* 374 */         if (bitmap == null || isCancelled()) {
/* 375 */           return null;
/*     */         }
/*     */         
/* 378 */         int maxWidth = (int)Utils.convDp2Pix(context, 200.0F);
/* 379 */         int marginWidth = (int)Utils.convDp2Pix(context, 175.0F);
/* 380 */         int width = (int)Math.min(maxWidth, (height * bitmap.getWidth() / bitmap.getHeight()) + 0.5D);
/* 381 */         if (width > marginWidth && width < maxWidth) {
/* 382 */           width = maxWidth;
/* 383 */           bitmap = CreateBitmapFromCustomStampTask.createBitmapFromCustomStamp(this.mCustomStampOption, this.mSingleLineHeight, this.mTwoLinesHeight, width);
/*     */         } 
/*     */         
/* 386 */         if (isCancelled() || bitmap == null) {
/* 387 */           return null;
/*     */         }
/*     */         
/* 390 */         if (this.mEditIndex >= 0) {
/* 391 */           CustomStampOption.updateCustomStamp(getContext(), this.mEditIndex, this.mCustomStampOption, bitmap);
/*     */         } else {
/* 393 */           CustomStampOption.addCustomStamp(getContext(), this.mCustomStampOption, bitmap);
/*     */         } 
/* 395 */         return bitmap;
/* 396 */       } catch (Exception e) {
/* 397 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */         
/* 399 */         return null;
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void onPostExecute(Bitmap bitmap) {
/* 404 */       super.onPostExecute(bitmap);
/* 405 */       OnCustomStampChangedListener listener = this.mListenerRef.get();
/* 406 */       if (listener != null)
/* 407 */         if (this.mEditIndex == -1) {
/* 408 */           listener.onCustomStampCreated(bitmap);
/*     */         } else {
/* 410 */           listener.onCustomStampUpdated(bitmap, this.mEditIndex);
/*     */         }  
/*     */     }
/*     */   }
/*     */   
/*     */   private class CustomSpaceItemDecoration
/*     */     extends RecyclerView.ItemDecoration
/*     */   {
/*     */     int space;
/*     */     
/*     */     CustomSpaceItemDecoration(Context context) {
/* 421 */       this.space = Math.round(Utils.convDp2Pix(context, 8.0F));
/*     */     }
/*     */ 
/*     */     
/*     */     public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
/* 426 */       if (CreateCustomStampDialogFragment.this.mCustomStampPreviewAppearances == null) {
/*     */         return;
/*     */       }
/* 429 */       if (parent.getChildAdapterPosition(view) < CreateCustomStampDialogFragment.this.mCustomStampPreviewAppearances.length / 3) {
/* 430 */         outRect.bottom = this.space;
/*     */       }
/*     */       
/* 433 */       if (Utils.isRtlLayout(view.getContext())) {
/* 434 */         outRect.right = this.space;
/*     */       } else {
/* 436 */         outRect.left = this.space;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private enum Shape
/*     */   {
/* 443 */     POINTING_LEFT,
/* 444 */     POINTING_RIGHT,
/* 445 */     ROUNDED_RECTANGLE;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\CreateCustomStampDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */