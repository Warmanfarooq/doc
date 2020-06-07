/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.Bitmap;
/*     */ import android.os.Bundle;
/*     */ import android.util.SparseBooleanArray;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.recyclerview.widget.ItemTouchHelper;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;
/*     */ import com.github.clans.fab.FloatingActionButton;
/*     */ import com.pdftron.pdf.adapter.CustomStampAdapter;
/*     */ import com.pdftron.pdf.interfaces.OnCustomStampChangedListener;
/*     */ import com.pdftron.pdf.interfaces.OnCustomStampSelectedListener;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.model.CustomStampPreviewAppearance;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.ToolbarActionMode;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemSelectionHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
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
/*     */ public class CustomStampPickerFragment
/*     */   extends Fragment
/*     */   implements OnCustomStampChangedListener
/*     */ {
/*  56 */   public static final String TAG = CustomStampPickerFragment.class.getName();
/*     */   private CustomStampPreviewAppearance[] mCustomStampPreviewAppearances;
/*     */   private TextView mEmptyStampTextView;
/*     */   private SimpleRecyclerView mRecyclerView;
/*     */   private CustomStampAdapter mCustomStampAdapter;
/*     */   private ItemTouchHelper mItemTouchHelper;
/*     */   private ItemSelectionHelper mItemSelectionHelper;
/*     */   private ToolbarActionMode mActionMode;
/*     */   private Toolbar mToolbar;
/*     */   private Toolbar mCabToolbar;
/*     */   private MenuItem mMenuItemModify;
/*     */   private MenuItem mMenuItemDuplicate;
/*     */   private MenuItem mMenuItemDelete;
/*     */   private OnCustomStampSelectedListener mOnCustomStampSelectedListener;
/*     */   
/*     */   public static CustomStampPickerFragment newInstance(@NonNull CustomStampPreviewAppearance[] customStampPreviewAppearances) {
/*  72 */     CustomStampPickerFragment fragment = new CustomStampPickerFragment();
/*  73 */     Bundle bundle = new Bundle();
/*  74 */     CustomStampPreviewAppearance.putCustomStampAppearancesToBundle(bundle, customStampPreviewAppearances);
/*  75 */     fragment.setArguments(bundle);
/*  76 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnCustomStampSelectedListener(@Nullable OnCustomStampSelectedListener listener) {
/*  85 */     this.mOnCustomStampSelectedListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setToolbars(@NonNull Toolbar toolbar, @NonNull Toolbar cabToolbar) {
/*  95 */     this.mToolbar = toolbar;
/*  96 */     this.mCabToolbar = cabToolbar;
/*  97 */     updateUIVisibility();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/* 103 */     Bundle bundle = getArguments();
/* 104 */     if (bundle != null) {
/* 105 */       this.mCustomStampPreviewAppearances = CustomStampPreviewAppearance.getCustomStampAppearancesFromBundle(bundle);
/*     */     }
/*     */     
/* 108 */     View view = inflater.inflate(R.layout.fragment_custom_rubber_stamp_picker, container, false);
/* 109 */     FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add_custom_stamp_fab);
/* 110 */     fab.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 113 */             FragmentManager fragmentManager = CustomStampPickerFragment.this.getFragmentManager();
/* 114 */             if (fragmentManager == null) {
/*     */               return;
/*     */             }
/* 117 */             CreateCustomStampDialogFragment fragment = CreateCustomStampDialogFragment.newInstance(CustomStampPickerFragment.this.mCustomStampPreviewAppearances);
/* 118 */             fragment.setStyle(0, R.style.CustomAppTheme);
/* 119 */             fragment.show(fragmentManager, CreateCustomStampDialogFragment.TAG);
/* 120 */             fragment.setOnCustomStampChangedListener(CustomStampPickerFragment.this);
/* 121 */             CustomStampPickerFragment.this.finishActionMode();
/*     */           }
/*     */         });
/*     */     
/* 125 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 130 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/* 132 */     if (this.mToolbar != null) {
/* 133 */       this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */           {
/*     */             public boolean onMenuItemClick(MenuItem item)
/*     */             {
/* 137 */               if (CustomStampPickerFragment.this.mToolbar == null || CustomStampPickerFragment.this.mCabToolbar == null) {
/* 138 */                 return false;
/*     */               }
/*     */               
/* 141 */               if (item.getItemId() == R.id.controls_action_edit) {
/*     */                 
/* 143 */                 CustomStampPickerFragment.this.mActionMode = new ToolbarActionMode((Context)CustomStampPickerFragment.this.getActivity(), CustomStampPickerFragment.this.mCabToolbar);
/* 144 */                 CustomStampPickerFragment.this.mActionMode.startActionMode(CustomStampPickerFragment.this.mActionModeCallback);
/* 145 */                 return true;
/*     */               } 
/* 147 */               return false;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 153 */     this.mRecyclerView = (SimpleRecyclerView)view.findViewById(R.id.stamp_list);
/* 154 */     this.mRecyclerView.initView(2);
/*     */     
/* 156 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 157 */     itemClickHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/* 158 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView parent, View view, int position, long id)
/*     */           {
/* 162 */             if (CustomStampPickerFragment.this.mActionMode == null) {
/* 163 */               if (CustomStampPickerFragment.this.mOnCustomStampSelectedListener != null) {
/* 164 */                 Obj stampObj = CustomStampOption.getCustomStampObj(view.getContext(), position);
/* 165 */                 CustomStampPickerFragment.this.mOnCustomStampSelectedListener.onCustomStampSelected(stampObj);
/*     */               } 
/*     */             } else {
/* 168 */               CustomStampPickerFragment.this.mItemSelectionHelper.setItemChecked(position, !CustomStampPickerFragment.this.mItemSelectionHelper.isItemChecked(position));
/* 169 */               CustomStampPickerFragment.this.mActionMode.invalidate();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 174 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(RecyclerView recyclerView, View v, final int position, long id)
/*     */           {
/* 178 */             if (CustomStampPickerFragment.this.mActionMode == null) {
/* 179 */               CustomStampPickerFragment.this.mItemSelectionHelper.setItemChecked(position, true);
/* 180 */               CustomStampPickerFragment.this.mActionMode = new ToolbarActionMode((Context)CustomStampPickerFragment.this.getActivity(), CustomStampPickerFragment.this.mCabToolbar);
/* 181 */               CustomStampPickerFragment.this.mActionMode.startActionMode(CustomStampPickerFragment.this.mActionModeCallback);
/*     */             } else {
/* 183 */               CustomStampPickerFragment.this.mRecyclerView.post(new Runnable()
/*     */                   {
/*     */                     public void run() {
/* 186 */                       RecyclerView.ViewHolder holder = CustomStampPickerFragment.this.mRecyclerView.findViewHolderForAdapterPosition(position);
/* 187 */                       CustomStampPickerFragment.this.mItemTouchHelper.startDrag(holder);
/*     */                     }
/*     */                   });
/*     */             } 
/*     */             
/* 192 */             return true;
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 197 */     this.mItemSelectionHelper = new ItemSelectionHelper();
/* 198 */     this.mItemSelectionHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/* 199 */     this.mItemSelectionHelper.setChoiceMode(2);
/*     */     
/* 201 */     this.mCustomStampAdapter = new CustomStampAdapter(view.getContext(), (ViewHolderBindListener)this.mItemSelectionHelper);
/* 202 */     this.mCustomStampAdapter.registerAdapterDataObserver(this.mItemSelectionHelper.getDataObserver());
/* 203 */     this.mRecyclerView.setAdapter((RecyclerView.Adapter)this.mCustomStampAdapter);
/*     */     
/* 205 */     this.mItemTouchHelper = new ItemTouchHelper((ItemTouchHelper.Callback)new SimpleItemTouchHelperCallback((ItemTouchHelperAdapter)this.mCustomStampAdapter, 2, false, false));
/* 206 */     this.mItemTouchHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*     */     
/* 208 */     this.mEmptyStampTextView = (TextView)view.findViewById(R.id.new_custom_stamp_guide_text_view);
/*     */     
/* 210 */     view.setFocusableInTouchMode(true);
/* 211 */     view.requestFocus();
/* 212 */     view.setOnKeyListener(new View.OnKeyListener()
/*     */         {
/*     */           public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 215 */             return (event.getAction() == 1 && keyCode == 4 && CustomStampPickerFragment.this
/* 216 */               .onBackPressed());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCustomStampCreated(@Nullable Bitmap bitmap) {
/* 223 */     if (this.mCustomStampAdapter == null) {
/*     */       return;
/*     */     }
/* 226 */     this.mCustomStampAdapter.add(bitmap);
/* 227 */     this.mCustomStampAdapter.notifyItemInserted(this.mCustomStampAdapter.getItemCount());
/* 228 */     updateUIVisibility();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCustomStampUpdated(@Nullable Bitmap bitmap, int index) {
/* 233 */     if (this.mCustomStampAdapter == null) {
/*     */       return;
/*     */     }
/* 236 */     this.mCustomStampAdapter.onCustomStampUpdated(bitmap, index);
/*     */   }
/*     */   
/*     */   private void updateUIVisibility() {
/* 240 */     Context context = getContext();
/* 241 */     if (context == null) {
/*     */       return;
/*     */     }
/* 244 */     int count = CustomStampOption.getCustomStampsCount(context);
/* 245 */     if (this.mEmptyStampTextView != null) {
/* 246 */       this.mEmptyStampTextView.setVisibility((count == 0) ? 0 : 8);
/*     */     }
/* 248 */     if (this.mToolbar != null) {
/* 249 */       MenuItem menuEdit = this.mToolbar.getMenu().findItem(R.id.controls_action_edit);
/* 250 */       menuEdit.setVisible((count != 0));
/* 251 */       if (count == 0) {
/* 252 */         finishActionMode();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/* 257 */   private ToolbarActionMode.Callback mActionModeCallback = new ToolbarActionMode.Callback()
/*     */     {
/*     */       public boolean onCreateActionMode(ToolbarActionMode mode, Menu menu)
/*     */       {
/* 261 */         mode.inflateMenu(R.menu.cab_controls_fragment_rubber_stamp);
/* 262 */         CustomStampPickerFragment.this.mMenuItemModify = menu.findItem(R.id.controls_rubber_stamp_action_modify);
/* 263 */         CustomStampPickerFragment.this.mMenuItemDuplicate = menu.findItem(R.id.controls_rubber_stamp_action_duplicate);
/* 264 */         CustomStampPickerFragment.this.mMenuItemDelete = menu.findItem(R.id.controls_rubber_stamp_action_delete);
/* 265 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public boolean onPrepareActionMode(ToolbarActionMode mode, Menu menu) {
/* 271 */         if (CustomStampPickerFragment.this.mMenuItemModify != null) {
/* 272 */           boolean isEnabled = (CustomStampPickerFragment.this.mItemSelectionHelper.getCheckedItemCount() == 1);
/* 273 */           CustomStampPickerFragment.this.mMenuItemModify.setEnabled(isEnabled);
/* 274 */           if (CustomStampPickerFragment.this.mMenuItemModify.getIcon() != null) {
/* 275 */             CustomStampPickerFragment.this.mMenuItemModify.getIcon().mutate().setAlpha(isEnabled ? 255 : 150);
/*     */           }
/*     */         } 
/* 278 */         if (CustomStampPickerFragment.this.mMenuItemDuplicate != null) {
/* 279 */           boolean isEnabled = (CustomStampPickerFragment.this.mItemSelectionHelper.getCheckedItemCount() == 1);
/* 280 */           CustomStampPickerFragment.this.mMenuItemDuplicate.setEnabled(isEnabled);
/* 281 */           if (CustomStampPickerFragment.this.mMenuItemDuplicate.getIcon() != null) {
/* 282 */             CustomStampPickerFragment.this.mMenuItemDuplicate.getIcon().mutate().setAlpha(isEnabled ? 255 : 150);
/*     */           }
/*     */         } 
/* 285 */         if (CustomStampPickerFragment.this.mMenuItemDelete != null) {
/* 286 */           boolean isEnabled = (CustomStampPickerFragment.this.mItemSelectionHelper.getCheckedItemCount() > 0);
/* 287 */           CustomStampPickerFragment.this.mMenuItemDelete.setEnabled(isEnabled);
/* 288 */           if (CustomStampPickerFragment.this.mMenuItemDelete.getIcon() != null) {
/* 289 */             CustomStampPickerFragment.this.mMenuItemDelete.getIcon().mutate().setAlpha(isEnabled ? 255 : 150);
/*     */           }
/*     */         } 
/*     */         
/* 293 */         if (Utils.isTablet(CustomStampPickerFragment.this.getContext()) || (CustomStampPickerFragment.this.getResources().getConfiguration()).orientation == 2) {
/* 294 */           mode.setTitle(CustomStampPickerFragment.this.getString(R.string.controls_thumbnails_view_selected, new Object[] {
/* 295 */                   Utils.getLocaleDigits(Integer.toString(CustomStampPickerFragment.access$700(this.this$0).getCheckedItemCount())) }));
/*     */         } else {
/* 297 */           mode.setTitle(Utils.getLocaleDigits(Integer.toString(CustomStampPickerFragment.this.mItemSelectionHelper.getCheckedItemCount())));
/*     */         } 
/* 299 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean onActionItemClicked(ToolbarActionMode mode, MenuItem item) {
/* 304 */         final Context context = CustomStampPickerFragment.this.getContext();
/* 305 */         View view = CustomStampPickerFragment.this.getView();
/* 306 */         FragmentManager fragmentManager = CustomStampPickerFragment.this.getFragmentManager();
/* 307 */         if (context == null || view == null || fragmentManager == null) {
/* 308 */           return false;
/*     */         }
/*     */         
/* 311 */         SparseBooleanArray selectedItems = CustomStampPickerFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 312 */         int count = selectedItems.size();
/* 313 */         final List<Integer> indexes = new ArrayList<>();
/* 314 */         int lastSelectedPosition = -1;
/* 315 */         for (int i = 0; i < count; i++) {
/* 316 */           if (selectedItems.valueAt(i)) {
/* 317 */             indexes.add(Integer.valueOf(selectedItems.keyAt(i)));
/* 318 */             lastSelectedPosition = selectedItems.keyAt(i);
/*     */           } 
/*     */         } 
/*     */         
/* 322 */         if (lastSelectedPosition == -1 || indexes.size() == 0) {
/* 323 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 327 */         Set<Integer> hs = new HashSet<>(indexes);
/* 328 */         indexes.clear();
/* 329 */         indexes.addAll(hs);
/* 330 */         Collections.sort(indexes);
/*     */         
/* 332 */         int id = item.getItemId();
/* 333 */         if (id == R.id.controls_rubber_stamp_action_modify) {
/*     */           try {
/* 335 */             CreateCustomStampDialogFragment fragment = CreateCustomStampDialogFragment.newInstance(CustomStampPickerFragment.this
/* 336 */                 .mCustomStampPreviewAppearances, lastSelectedPosition);
/* 337 */             fragment.setStyle(0, R.style.CustomAppTheme);
/* 338 */             fragment.show(fragmentManager, CreateCustomStampDialogFragment.TAG);
/* 339 */             fragment.setOnCustomStampChangedListener(CustomStampPickerFragment.this);
/* 340 */           } catch (Exception e) {
/* 341 */             AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */           } 
/* 343 */         } else if (id == R.id.controls_rubber_stamp_action_duplicate) {
/* 344 */           CustomStampOption.duplicateCustomStamp(context, lastSelectedPosition);
/* 345 */           Bitmap bitmap = CustomStampPickerFragment.this.mCustomStampAdapter.getItem(lastSelectedPosition);
/* 346 */           CustomStampPickerFragment.this.mCustomStampAdapter.insert(bitmap, lastSelectedPosition + 1);
/* 347 */           CustomStampPickerFragment.this.mCustomStampAdapter.notifyItemInserted(lastSelectedPosition + 1);
/* 348 */         } else if (id == R.id.controls_rubber_stamp_action_delete) {
/* 349 */           AlertDialog.Builder builder = new AlertDialog.Builder((Context)CustomStampPickerFragment.this.getActivity());
/* 350 */           builder.setMessage(R.string.custom_stamp_dialog_delete_message)
/* 351 */             .setTitle(R.string.custom_stamp_dialog_delete_title)
/* 352 */             .setPositiveButton(R.string.tools_misc_yes, new DialogInterface.OnClickListener()
/*     */               {
/*     */                 public void onClick(DialogInterface dialog, int which) {
/* 355 */                   CustomStampOption.removeCustomStamps(context, indexes);
/* 356 */                   for (int i = indexes.size() - 1; i >= 0; i--) {
/* 357 */                     int index = ((Integer)indexes.get(i)).intValue();
/* 358 */                     CustomStampPickerFragment.this.mCustomStampAdapter.removeAt(index);
/* 359 */                     CustomStampPickerFragment.this.mCustomStampAdapter.notifyItemRemoved(index);
/*     */                   }
/*     */                 
/*     */                 }
/* 363 */               }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */               {
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onClick(DialogInterface dialog, int which) {}
/* 369 */               }).create()
/* 370 */             .show();
/*     */         } 
/*     */         
/* 373 */         CustomStampPickerFragment.this.clearSelectedList();
/* 374 */         CustomStampPickerFragment.this.updateUIVisibility();
/*     */         
/* 376 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void onDestroyActionMode(ToolbarActionMode mode) {
/* 381 */         CustomStampPickerFragment.this.mActionMode = null;
/* 382 */         CustomStampPickerFragment.this.clearSelectedList();
/*     */       }
/*     */     };
/*     */   
/*     */   private boolean finishActionMode() {
/* 387 */     boolean success = false;
/* 388 */     if (this.mActionMode != null) {
/* 389 */       success = true;
/* 390 */       this.mActionMode.finish();
/* 391 */       this.mActionMode = null;
/*     */     } 
/* 393 */     clearSelectedList();
/* 394 */     return success;
/*     */   }
/*     */   
/*     */   private void clearSelectedList() {
/* 398 */     if (this.mItemSelectionHelper != null) {
/* 399 */       this.mItemSelectionHelper.clearChoices();
/*     */     }
/* 401 */     if (this.mActionMode != null) {
/* 402 */       this.mActionMode.invalidate();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean onBackPressed() {
/* 407 */     if (!isAdded()) {
/* 408 */       return false;
/*     */     }
/*     */     
/* 411 */     boolean handled = false;
/* 412 */     if (this.mActionMode != null) {
/* 413 */       handled = finishActionMode();
/*     */     }
/* 415 */     return handled;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\CustomStampPickerFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */