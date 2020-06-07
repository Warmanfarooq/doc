/*     */ package com.pdftron.pdf.dialog.widgetchoice;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.PopupMenu;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import androidx.recyclerview.widget.ItemTouchHelper;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*     */ import com.github.clans.fab.FloatingActionButton;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListAdapter;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListItemTouchHelperCallback;
/*     */ import com.pdftron.pdf.dialog.simplelist.EditListViewHolder;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import org.apache.commons.lang3.RandomStringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChoiceDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */ {
/*  38 */   public static final String TAG = ChoiceDialogFragment.class.getName();
/*     */   
/*     */   public static final String WIDGET = "ChoiceDialogFragment_WIDGET";
/*     */   
/*     */   public static final String WIDGET_PAGE = "ChoiceDialogFragment_WIDGET_PAGE";
/*     */   
/*     */   public static final String FIELD_TYPE = "ChoiceDialogFragment_FIELD_TYPE";
/*     */   
/*     */   public static final String SELECTION_TYPE = "ChoiceDialogFragment_SELECTION_TYPE";
/*     */   
/*     */   public static final String EXISTING_OPTIONS = "ChoiceDialogFragment_EXISTING_OPTIONS";
/*     */   
/*     */   private long mWidget;
/*     */   
/*     */   private int mPage;
/*     */   private boolean mSingleChoice;
/*     */   private boolean mIsCombo;
/*     */   @Nullable
/*     */   private String[] mOptions;
/*     */   private SimpleRecyclerView mRecyclerView;
/*     */   private ChoiceAdapter mAdapter;
/*     */   private ItemTouchHelper mItemTouchHelper;
/*     */   private EditListItemTouchHelperCallback mTouchCallback;
/*     */   private FloatingActionButton mFab;
/*     */   private ChoiceViewModel mViewModel;
/*     */   private boolean mModified;
/*     */   
/*     */   public static ChoiceDialogFragment newInstance(long widget, int page, boolean isSingleChoice, boolean isCombo) {
/*  66 */     return newInstance(widget, page, isSingleChoice, isCombo, (String[])null);
/*     */   }
/*     */   
/*     */   public static ChoiceDialogFragment newInstance(long widget, int page, boolean isSingleChoice, boolean isCombo, @Nullable String[] options) {
/*  70 */     ChoiceDialogFragment dialog = new ChoiceDialogFragment();
/*  71 */     Bundle args = new Bundle();
/*  72 */     args.putLong("ChoiceDialogFragment_WIDGET", widget);
/*  73 */     args.putInt("ChoiceDialogFragment_WIDGET_PAGE", page);
/*  74 */     args.putBoolean("ChoiceDialogFragment_FIELD_TYPE", isCombo);
/*  75 */     args.putBoolean("ChoiceDialogFragment_SELECTION_TYPE", isSingleChoice);
/*  76 */     args.putStringArray("ChoiceDialogFragment_EXISTING_OPTIONS", options);
/*  77 */     dialog.setArguments(args);
/*  78 */     return dialog;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  83 */     super.onCreate(savedInstanceState);
/*     */     
/*  85 */     Bundle args = getArguments();
/*  86 */     if (args != null) {
/*  87 */       this.mWidget = args.getLong("ChoiceDialogFragment_WIDGET");
/*  88 */       this.mPage = args.getInt("ChoiceDialogFragment_WIDGET_PAGE");
/*  89 */       this.mIsCombo = args.getBoolean("ChoiceDialogFragment_FIELD_TYPE");
/*  90 */       this.mSingleChoice = args.getBoolean("ChoiceDialogFragment_SELECTION_TYPE");
/*  91 */       this.mOptions = args.getStringArray("ChoiceDialogFragment_EXISTING_OPTIONS");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  98 */     FragmentActivity activity = getActivity();
/*  99 */     if (null == activity) {
/* 100 */       return null;
/*     */     }
/*     */     
/* 103 */     View view = inflater.inflate(R.layout.fragment_widget_choice_dialog, container);
/*     */     
/* 105 */     this.mViewModel = (ChoiceViewModel)ViewModelProviders.of(activity).get(ChoiceViewModel.class);
/*     */ 
/*     */     
/* 108 */     this.mRecyclerView = (SimpleRecyclerView)view.findViewById(R.id.recycler_view);
/* 109 */     this.mRecyclerView.initView(0, 0);
/* 110 */     ArrayList<String> options = null;
/* 111 */     if (this.mOptions != null) {
/* 112 */       options = new ArrayList<>(Arrays.asList(this.mOptions));
/*     */     }
/* 114 */     this.mAdapter = new ChoiceAdapter(options);
/* 115 */     this.mRecyclerView.setAdapter((RecyclerView.Adapter)this.mAdapter);
/*     */ 
/*     */     
/* 118 */     this.mTouchCallback = new EditListItemTouchHelperCallback(this.mAdapter, true, getResources().getColor(R.color.gray));
/* 119 */     this.mItemTouchHelper = new ItemTouchHelper((ItemTouchHelper.Callback)this.mTouchCallback);
/* 120 */     this.mItemTouchHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*     */     
/* 122 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 123 */     itemClickHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*     */ 
/*     */     
/* 126 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(RecyclerView recyclerView, View view, final int position, long id) {
/* 129 */             ChoiceDialogFragment.this.mRecyclerView.post(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 132 */                     ChoiceDialogFragment.this.mTouchCallback.setDragging(true);
/* 133 */                     RecyclerView.ViewHolder holder = ChoiceDialogFragment.this.mRecyclerView.findViewHolderForAdapterPosition(position);
/* 134 */                     if (holder != null) {
/* 135 */                       ChoiceDialogFragment.this.mItemTouchHelper.startDrag(holder);
/*     */                     }
/*     */                   }
/*     */                 });
/*     */             
/* 140 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 144 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/* 145 */     toolbar.setTitle(R.string.widget_choice_title);
/* 146 */     toolbar.setNavigationIcon(R.drawable.ic_close_white_24dp);
/* 147 */     toolbar.inflateMenu(R.menu.fragment_widget_choice_dialog);
/* 148 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 151 */             ChoiceDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 154 */     if (this.mIsCombo) {
/*     */       
/* 156 */       toolbar.getMenu().findItem(R.id.select_type).setVisible(false);
/*     */     } else {
/*     */       
/* 159 */       toolbar.getMenu().findItem(R.id.select_type).setVisible(true);
/* 160 */       if (this.mSingleChoice) {
/* 161 */         toolbar.getMenu().findItem(R.id.single_select).setChecked(true);
/*     */       } else {
/* 163 */         toolbar.getMenu().findItem(R.id.multiple_select).setChecked(true);
/*     */       } 
/*     */     } 
/* 166 */     toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem menuItem) {
/* 169 */             if (menuItem.getItemId() == R.id.done) {
/* 170 */               ChoiceResult result = new ChoiceResult(ChoiceDialogFragment.this.mWidget, ChoiceDialogFragment.this.mPage, ChoiceDialogFragment.this.mSingleChoice, ChoiceDialogFragment.this.mAdapter.getItems());
/* 171 */               if (ChoiceDialogFragment.this.mModified) {
/* 172 */                 ChoiceDialogFragment.this.mViewModel.set(result);
/*     */               }
/* 174 */               ChoiceDialogFragment.this.dismiss();
/* 175 */               return true;
/* 176 */             }  if (menuItem.getItemId() == R.id.single_select) {
/* 177 */               menuItem.setChecked(true);
/* 178 */               ChoiceDialogFragment.this.mSingleChoice = true;
/* 179 */               ChoiceDialogFragment.this.mModified = true;
/* 180 */               return true;
/* 181 */             }  if (menuItem.getItemId() == R.id.multiple_select) {
/* 182 */               menuItem.setChecked(true);
/* 183 */               ChoiceDialogFragment.this.mSingleChoice = false;
/* 184 */               ChoiceDialogFragment.this.mModified = true;
/* 185 */               return true;
/*     */             } 
/* 187 */             return false;
/*     */           }
/*     */         });
/*     */     
/* 191 */     this.mFab = (FloatingActionButton)view.findViewById(R.id.add);
/* 192 */     this.mFab.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 195 */             if (null == ChoiceDialogFragment.this.mAdapter) {
/*     */               return;
/*     */             }
/* 198 */             ChoiceDialogFragment.this.mAdapter.safeAddNewOption(ChoiceDialogFragment.this.getString(R.string.widget_choice_default_item));
/* 199 */             ChoiceDialogFragment.this.mAdapter.notifyItemInserted(ChoiceDialogFragment.this.mAdapter.getItemCount() - 1);
/* 200 */             ChoiceDialogFragment.this.mRecyclerView.smoothScrollToPosition(ChoiceDialogFragment.this.mAdapter.getItemCount() - 1);
/* 201 */             ChoiceDialogFragment.this.mModified = true;
/*     */           }
/*     */         });
/*     */     
/* 205 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(DialogInterface dialog) {
/* 210 */     super.onDismiss(dialog);
/* 211 */     this.mViewModel.complete();
/*     */   }
/*     */   
/*     */   private void showPopupMenu(final int position, View anchor) {
/* 215 */     FragmentActivity activity = getActivity();
/* 216 */     if (null == activity) {
/*     */       return;
/*     */     }
/*     */     
/* 220 */     PopupMenu popup = new PopupMenu((Context)activity, anchor);
/* 221 */     popup.inflate(R.menu.popup_widget_choice_edit);
/* 222 */     popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem menuItem) {
/* 225 */             if (menuItem.getItemId() == R.id.item_rename) {
/* 226 */               ChoiceDialogFragment.this.setEditingItem(true);
/* 227 */               ChoiceDialogFragment.this.mAdapter.setSelectedIndex(position);
/* 228 */               ChoiceDialogFragment.this.mAdapter.notifyItemChanged(position);
/*     */               
/* 230 */               return true;
/* 231 */             }  if (menuItem.getItemId() == R.id.item_delete) {
/* 232 */               ChoiceDialogFragment.this.mModified = true;
/* 233 */               ChoiceDialogFragment.this.mAdapter.removeAt(position);
/* 234 */               ChoiceDialogFragment.this.mAdapter.notifyItemRemoved(position);
/*     */             } 
/*     */             
/* 237 */             return false;
/*     */           }
/*     */         });
/* 240 */     popup.show();
/*     */   }
/*     */   
/*     */   private void setEditingItem(boolean editing) {
/* 244 */     if (null == this.mAdapter) {
/*     */       return;
/*     */     }
/* 247 */     this.mAdapter.setEditing(editing);
/* 248 */     if (editing) {
/* 249 */       this.mFab.setVisibility(8);
/*     */     } else {
/* 251 */       this.mFab.setVisibility(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class ChoiceAdapter
/*     */     extends EditListAdapter<String>
/*     */     implements ItemTouchHelperAdapter
/*     */   {
/* 261 */     private ArrayList<String> mOptions = new ArrayList<>(); ChoiceAdapter(ArrayList<String> items) {
/* 262 */       if (null != items) {
/* 263 */         this.mOptions.addAll(items);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void onBindViewHolder(@NonNull EditListViewHolder holder, int position) {
/* 269 */       super.onBindViewHolder(holder, position);
/*     */       
/* 271 */       holder.textView.setText(getItem(position));
/*     */       
/* 273 */       if (this.mEditing) {
/* 274 */         holder.editText.setText(getItem(position));
/* 275 */         holder.editText.requestFocus();
/* 276 */         holder.editText.selectAll();
/* 277 */         Utils.showSoftKeyboard(holder.editText.getContext(), null);
/*     */       } 
/*     */     }
/*     */     
/*     */     public String[] getItems() {
/* 282 */       return this.mOptions.<String>toArray(new String[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public String getItem(int position) {
/* 287 */       if (isValidIndex(position)) {
/* 288 */         return this.mOptions.get(position);
/*     */       }
/* 290 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void add(String item) {
/* 295 */       this.mOptions.add(item);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean remove(String item) {
/* 300 */       if (this.mOptions.contains(item)) {
/* 301 */         this.mOptions.remove(item);
/* 302 */         return true;
/*     */       } 
/* 304 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public String removeAt(int position) {
/* 309 */       if (isValidIndex(position)) {
/* 310 */         this.mOptions.remove(position);
/*     */       }
/* 312 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public void insert(String item, int position) {
/* 317 */       this.mOptions.add(position, item);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateSpanCount(int count) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public int getItemCount() {
/* 327 */       return this.mOptions.size();
/*     */     }
/*     */ 
/*     */     
/*     */     protected void contextButtonClicked(@NonNull EditListViewHolder holder, View v) {
/* 332 */       if (this.mEditing) {
/* 333 */         holder.itemView.requestFocus();
/*     */       } else {
/* 335 */         ChoiceDialogFragment.this.showPopupMenu(holder.getAdapterPosition(), v);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void handleEditTextFocusChange(@NonNull EditListViewHolder holder, View v, boolean hasFocus) {
/* 341 */       if (hasFocus) {
/*     */         return;
/*     */       }
/* 344 */       int pos = holder.getAdapterPosition();
/* 345 */       if (pos == -1) {
/*     */         return;
/*     */       }
/* 348 */       Utils.hideSoftKeyboard(v.getContext(), v);
/*     */       
/* 350 */       saveEditTextChanges((TextView)v, pos);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean onItemMove(int fromPosition, int toPosition) {
/* 355 */       String item = getItem(fromPosition);
/* 356 */       this.mOptions.remove(fromPosition);
/* 357 */       this.mOptions.add(toPosition, item);
/* 358 */       notifyItemMoved(fromPosition, toPosition);
/* 359 */       ChoiceDialogFragment.this.mModified = true;
/* 360 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void onItemDrop(int fromPosition, int toPosition) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void onItemDismiss(int position) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void safeAddNewOption(String option) {
/* 374 */       add(getValidOption(option));
/*     */     }
/*     */     
/*     */     private void saveEditTextChanges(TextView v, int position) {
/* 378 */       v.clearFocus();
/*     */       
/* 380 */       ChoiceDialogFragment.this.setEditingItem(false);
/*     */       
/* 382 */       String oldName = this.mOptions.get(position);
/*     */       
/* 384 */       String newName = v.getText().toString();
/* 385 */       if (newName.isEmpty()) {
/* 386 */         newName = oldName;
/*     */       }
/* 388 */       newName = getValidOption(newName);
/* 389 */       this.mOptions.set(position, newName);
/* 390 */       notifyItemChanged(position);
/* 391 */       if (!oldName.equals(newName)) {
/* 392 */         ChoiceDialogFragment.this.mModified = true;
/*     */       }
/*     */     }
/*     */     
/*     */     private boolean isValidOption(String option) {
/* 397 */       return !this.mOptions.contains(option);
/*     */     }
/*     */     
/*     */     private String getValidOption(String option) {
/* 401 */       if (isValidOption(option)) {
/* 402 */         return option;
/*     */       }
/* 404 */       while (!isValidOption(option)) {
/* 405 */         option = option + "-" + RandomStringUtils.randomAlphabetic(4);
/*     */       }
/* 407 */       return option;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\widgetchoice\ChoiceDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */