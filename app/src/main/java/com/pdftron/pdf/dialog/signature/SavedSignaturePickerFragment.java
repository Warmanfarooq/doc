/*     */ package com.pdftron.pdf.dialog.signature;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
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
/*     */ import androidx.recyclerview.widget.GridLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.github.clans.fab.FloatingActionButton;
/*     */ import com.pdftron.pdf.adapter.SavedSignatureAdapter;
/*     */ import com.pdftron.pdf.interfaces.OnSavedSignatureListener;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.ToolbarActionMode;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemSelectionHelper;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SavedSignaturePickerFragment
/*     */   extends Fragment
/*     */ {
/*     */   private OnSavedSignatureListener mOnSavedSignatureListener;
/*     */   private SavedSignatureAdapter mSavedSignatureAdapter;
/*     */   private ItemSelectionHelper mItemSelectionHelper;
/*     */   private ToolbarActionMode mActionMode;
/*     */   private Toolbar mToolbar;
/*     */   private Toolbar mCabToolbar;
/*     */   
/*     */   public static SavedSignaturePickerFragment newInstance() {
/*  51 */     return new SavedSignaturePickerFragment();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  57 */     return inflater.inflate(R.layout.fragment_custom_rubber_stamp_picker, container, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/*  62 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/*  64 */     FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.add_custom_stamp_fab);
/*  65 */     fab.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  68 */             if (SavedSignaturePickerFragment.this.mOnSavedSignatureListener != null) {
/*  69 */               SavedSignaturePickerFragment.this.mOnSavedSignatureListener.onCreateSignatureClicked();
/*     */             }
/*     */           }
/*     */         });
/*     */     
/*  74 */     SimpleRecyclerView recyclerView = (SimpleRecyclerView)view.findViewById(R.id.stamp_list);
/*  75 */     recyclerView.setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(getContext(), 2));
/*     */     
/*  77 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/*  78 */     itemClickHelper.attachToRecyclerView((RecyclerView)recyclerView);
/*  79 */     itemClickHelper.setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*     */         {
/*     */           public void onItemClick(RecyclerView parent, View view, int position, long id) {
/*  82 */             if (SavedSignaturePickerFragment.this.mActionMode == null) {
/*  83 */               File file = SavedSignaturePickerFragment.this.mSavedSignatureAdapter.getItem(position);
/*  84 */               if (SavedSignaturePickerFragment.this.mOnSavedSignatureListener != null && file != null) {
/*  85 */                 SavedSignaturePickerFragment.this.mOnSavedSignatureListener.onSignatureSelected(file.getAbsolutePath());
/*     */               }
/*     */             } else {
/*  88 */               SavedSignaturePickerFragment.this.mItemSelectionHelper.setItemChecked(position, !SavedSignaturePickerFragment.this.mItemSelectionHelper.isItemChecked(position));
/*  89 */               SavedSignaturePickerFragment.this.mActionMode.invalidate();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/*  94 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(RecyclerView recyclerView, View view, int position, long id) {
/*  97 */             if (SavedSignaturePickerFragment.this.mActionMode == null) {
/*  98 */               SavedSignaturePickerFragment.this.mItemSelectionHelper.setItemChecked(position, true);
/*  99 */               SavedSignaturePickerFragment.this.mActionMode = new ToolbarActionMode(view.getContext(), SavedSignaturePickerFragment.this.mCabToolbar);
/* 100 */               SavedSignaturePickerFragment.this.mActionMode.startActionMode(SavedSignaturePickerFragment.this.mActionModeCallback);
/* 101 */               return true;
/*     */             } 
/* 103 */             return false;
/*     */           }
/*     */         });
/*     */     
/* 107 */     this.mItemSelectionHelper = new ItemSelectionHelper();
/* 108 */     this.mItemSelectionHelper.attachToRecyclerView((RecyclerView)recyclerView);
/* 109 */     this.mItemSelectionHelper.setChoiceMode(2);
/*     */     
/* 111 */     this.mSavedSignatureAdapter = new SavedSignatureAdapter(view.getContext(), (ViewHolderBindListener)this.mItemSelectionHelper);
/* 112 */     this.mSavedSignatureAdapter.registerAdapterDataObserver(this.mItemSelectionHelper.getDataObserver());
/* 113 */     recyclerView.setAdapter((RecyclerView.Adapter)this.mSavedSignatureAdapter);
/*     */     
/* 115 */     TextView emptyTextView = (TextView)view.findViewById(R.id.new_custom_stamp_guide_text_view);
/* 116 */     emptyTextView.setText(R.string.signature_new_guide);
/*     */     
/* 118 */     view.setOnKeyListener(new View.OnKeyListener()
/*     */         {
/*     */           public boolean onKey(View v, int keyCode, KeyEvent event) {
/* 121 */             return (event.getAction() == 1 && keyCode == 4 && SavedSignaturePickerFragment.this
/* 122 */               .onBackPressed());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 129 */     super.onStop();
/* 130 */     this.mSavedSignatureAdapter.dispose();
/*     */   }
/*     */   
/*     */   public void setOnSavedSignatureListener(OnSavedSignatureListener listener) {
/* 134 */     this.mOnSavedSignatureListener = listener;
/*     */   }
/*     */   
/*     */   public void setToolbars(@NonNull Toolbar toolbar, @NonNull Toolbar cabToolbar) {
/* 138 */     this.mToolbar = toolbar;
/* 139 */     this.mCabToolbar = cabToolbar;
/*     */   }
/*     */   
/*     */   public void resetToolbar(final Context context) {
/* 143 */     if (this.mToolbar != null) {
/* 144 */       finishActionMode();
/* 145 */       this.mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */           {
/*     */             public boolean onMenuItemClick(MenuItem item)
/*     */             {
/* 149 */               if (SavedSignaturePickerFragment.this.mToolbar == null || SavedSignaturePickerFragment.this.mCabToolbar == null) {
/* 150 */                 return false;
/*     */               }
/*     */               
/* 153 */               if (item.getItemId() == R.id.controls_action_edit) {
/*     */                 
/* 155 */                 SavedSignaturePickerFragment.this.mActionMode = new ToolbarActionMode(context, SavedSignaturePickerFragment.this.mCabToolbar);
/* 156 */                 SavedSignaturePickerFragment.this.mActionMode.startActionMode(SavedSignaturePickerFragment.this.mActionModeCallback);
/* 157 */                 return true;
/*     */               } 
/* 159 */               return false;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/* 165 */   private ToolbarActionMode.Callback mActionModeCallback = new ToolbarActionMode.Callback()
/*     */     {
/*     */       public boolean onCreateActionMode(ToolbarActionMode mode, Menu menu)
/*     */       {
/* 169 */         mode.inflateMenu(R.menu.cab_fragment_saved_signature);
/*     */         
/* 171 */         if (SavedSignaturePickerFragment.this.mOnSavedSignatureListener != null) {
/* 172 */           SavedSignaturePickerFragment.this.mOnSavedSignatureListener.onEditModeChanged(true);
/*     */         }
/*     */         
/* 175 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean onActionItemClicked(ToolbarActionMode mode, MenuItem item) {
/* 180 */         int id = item.getItemId();
/*     */         
/* 182 */         SparseBooleanArray selectedItems = SavedSignaturePickerFragment.this.mItemSelectionHelper.getCheckedItemPositions();
/* 183 */         int count = selectedItems.size();
/* 184 */         final List<Integer> indexes = new ArrayList<>();
/* 185 */         for (int i = 0; i < count; i++) {
/* 186 */           if (selectedItems.valueAt(i)) {
/* 187 */             indexes.add(Integer.valueOf(selectedItems.keyAt(i)));
/*     */           }
/*     */         } 
/*     */         
/* 191 */         if (indexes.size() == 0) {
/* 192 */           return false;
/*     */         }
/*     */         
/* 195 */         if (id == R.id.controls_signature_action_delete) {
/* 196 */           AlertDialog.Builder builder = new AlertDialog.Builder(SavedSignaturePickerFragment.this.getContext());
/* 197 */           builder.setMessage(R.string.signature_dialog_delete_message)
/* 198 */             .setTitle(R.string.signature_dialog_delete_title)
/* 199 */             .setPositiveButton(R.string.tools_misc_yes, new DialogInterface.OnClickListener()
/*     */               {
/*     */                 public void onClick(DialogInterface dialog, int which)
/*     */                 {
/* 203 */                   Set<Integer> hs = new HashSet<>(indexes);
/* 204 */                   indexes.clear();
/* 205 */                   indexes.addAll(hs);
/* 206 */                   Collections.sort(indexes);
/*     */                   
/* 208 */                   for (int i = indexes.size() - 1; i >= 0; i--) {
/* 209 */                     int index = ((Integer)indexes.get(i)).intValue();
/* 210 */                     SavedSignaturePickerFragment.this.mSavedSignatureAdapter.removeAt(index);
/* 211 */                     SavedSignaturePickerFragment.this.mSavedSignatureAdapter.notifyItemRemoved(index);
/*     */                   } 
/*     */                   
/* 214 */                   SavedSignaturePickerFragment.this.clearSelectedList();
/*     */                 }
/* 217 */               }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener()
/*     */               {
/*     */ 
/*     */ 
/*     */                 
/*     */                 public void onClick(DialogInterface dialog, int which) {}
/* 223 */               }).create()
/* 224 */             .show();
/*     */         } 
/*     */         
/* 227 */         return true;
/*     */       }
/*     */ 
/*     */       
/*     */       public void onDestroyActionMode(ToolbarActionMode mode) {
/* 232 */         SavedSignaturePickerFragment.this.mActionMode = null;
/* 233 */         SavedSignaturePickerFragment.this.clearSelectedList();
/*     */         
/* 235 */         if (SavedSignaturePickerFragment.this.mOnSavedSignatureListener != null) {
/* 236 */           SavedSignaturePickerFragment.this.mOnSavedSignatureListener.onEditModeChanged(false);
/*     */         }
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean onPrepareActionMode(ToolbarActionMode mode, Menu menu) {
/* 242 */         if (Utils.isTablet(SavedSignaturePickerFragment.this.getContext()) || (SavedSignaturePickerFragment.this.getResources().getConfiguration()).orientation == 2) {
/* 243 */           mode.setTitle(SavedSignaturePickerFragment.this.getString(R.string.controls_thumbnails_view_selected, new Object[] {
/* 244 */                   Utils.getLocaleDigits(Integer.toString(SavedSignaturePickerFragment.access$300(this.this$0).getCheckedItemCount())) }));
/*     */         } else {
/* 246 */           mode.setTitle(Utils.getLocaleDigits(Integer.toString(SavedSignaturePickerFragment.this.mItemSelectionHelper.getCheckedItemCount())));
/*     */         } 
/* 248 */         return true;
/*     */       }
/*     */     };
/*     */   
/*     */   private boolean finishActionMode() {
/* 253 */     boolean success = false;
/* 254 */     if (this.mActionMode != null) {
/* 255 */       success = true;
/* 256 */       this.mActionMode.finish();
/* 257 */       this.mActionMode = null;
/*     */     } 
/* 259 */     clearSelectedList();
/* 260 */     return success;
/*     */   }
/*     */   
/*     */   private void clearSelectedList() {
/* 264 */     if (this.mItemSelectionHelper != null) {
/* 265 */       this.mItemSelectionHelper.clearChoices();
/*     */     }
/* 267 */     if (this.mActionMode != null) {
/* 268 */       this.mActionMode.invalidate();
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean onBackPressed() {
/* 273 */     if (!isAdded()) {
/* 274 */       return false;
/*     */     }
/*     */     
/* 277 */     boolean handled = false;
/* 278 */     if (this.mActionMode != null) {
/* 279 */       handled = finishActionMode();
/*     */     }
/* 281 */     return handled;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\signature\SavedSignaturePickerFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */