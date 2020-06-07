/*     */ package com.pdftron.pdf.widget.recyclerview;
/*     */ 
/*     */ import android.util.SparseBooleanArray;
/*     */ import android.view.View;
/*     */ import android.widget.Checkable;
/*     */ import androidx.collection.LongSparseArray;
/*     */ import androidx.core.view.ViewCompat;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemSelectionHelper
/*     */   implements ViewHolderBindListener
/*     */ {
/*  21 */   static final String TAG = ItemSelectionHelper.class.getName();
/*     */   
/*     */   public static final int INVALID_POSITION = -1;
/*     */   
/*     */   public static final int CHOICE_MODE_NONE = 0;
/*     */   
/*     */   public static final int CHOICE_MODE_SINGLE = 1;
/*     */   
/*     */   public static final int CHOICE_MODE_MULTIPLE = 2;
/*     */   private RecyclerView mRecyclerView;
/*  31 */   private int mChoiceMode = 0;
/*     */   
/*     */   private SparseBooleanArray mCheckStates;
/*     */   private LongSparseArray<Integer> mCheckedIdStates;
/*     */   private int mCheckedItemCount;
/*     */   private RecyclerView.AdapterDataObserver mDataObserver;
/*     */   
/*     */   public void attachToRecyclerView(RecyclerView recyclerView) {
/*  39 */     if (this.mRecyclerView == recyclerView) {
/*     */       return;
/*     */     }
/*  42 */     if (this.mRecyclerView != null) {
/*  43 */       clearChoices();
/*     */     }
/*  45 */     this.mRecyclerView = recyclerView;
/*     */   }
/*     */   
/*     */   public int getCheckedItemCount() {
/*  49 */     return this.mCheckedItemCount;
/*     */   }
/*     */   
/*     */   public boolean isItemChecked(int position) {
/*  53 */     if (this.mChoiceMode != 0 && this.mCheckStates != null) {
/*  54 */       return this.mCheckStates.get(position);
/*     */     }
/*  56 */     return false;
/*     */   }
/*     */   
/*     */   public int getCheckedItemPosition() {
/*  60 */     if (this.mChoiceMode == 1 && this.mCheckStates != null && this.mCheckStates.size() == 1) {
/*  61 */       return this.mCheckStates.keyAt(0);
/*     */     }
/*  63 */     return -1;
/*     */   }
/*     */   
/*     */   public SparseBooleanArray getCheckedItemPositions() {
/*  67 */     if (this.mChoiceMode != 0) {
/*  68 */       return this.mCheckStates;
/*     */     }
/*  70 */     return null;
/*     */   }
/*     */   
/*     */   public long[] getCheckedItemIds() {
/*  74 */     if (this.mChoiceMode == 0 || this.mCheckedIdStates == null || this.mRecyclerView.getAdapter() == null) {
/*  75 */       return new long[0];
/*     */     }
/*     */     
/*  78 */     LongSparseArray<Integer> idStates = this.mCheckedIdStates;
/*  79 */     int count = idStates.size();
/*  80 */     long[] ids = new long[count];
/*     */     
/*  82 */     for (int i = 0; i < count; i++) {
/*  83 */       ids[i] = idStates.keyAt(i);
/*     */     }
/*     */     
/*  86 */     return ids;
/*     */   }
/*     */   
/*     */   public void setItemChecked(int position, boolean checked) {
/*  90 */     if (this.mChoiceMode == 0 || this.mRecyclerView.getAdapter() == null) {
/*     */       return;
/*     */     }
/*     */     
/*  94 */     RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
/*     */     
/*  96 */     if (this.mChoiceMode == 2) {
/*  97 */       boolean oldValue = this.mCheckStates.get(position);
/*  98 */       this.mCheckStates.put(position, checked);
/*  99 */       if (this.mCheckedIdStates != null && adapter.hasStableIds()) {
/* 100 */         if (checked) {
/* 101 */           this.mCheckedIdStates.put(adapter.getItemId(position), Integer.valueOf(position));
/*     */         } else {
/* 103 */           this.mCheckedIdStates.delete(adapter.getItemId(position));
/*     */         } 
/*     */       }
/* 106 */       if (oldValue != checked) {
/* 107 */         if (checked) {
/* 108 */           this.mCheckedItemCount++;
/*     */         } else {
/* 110 */           this.mCheckedItemCount--;
/*     */         } 
/* 112 */         if (ViewCompat.getLayoutDirection((View)this.mRecyclerView) == 1) {
/*     */ 
/*     */           
/* 115 */           Utils.safeNotifyDataSetChanged(adapter);
/*     */         } else {
/* 117 */           Utils.safeNotifyItemChanged(adapter, position);
/*     */         } 
/*     */       } 
/*     */     } else {
/* 121 */       int oldCheckedPosition = getCheckedItemPosition();
/* 122 */       boolean updateIds = (this.mCheckedIdStates != null && adapter.hasStableIds());
/*     */ 
/*     */       
/* 125 */       if (checked || isItemChecked(position)) {
/* 126 */         this.mCheckStates.clear();
/* 127 */         if (updateIds) {
/* 128 */           this.mCheckedIdStates.clear();
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 133 */       if (checked) {
/* 134 */         this.mCheckStates.put(position, true);
/* 135 */         if (updateIds) {
/* 136 */           this.mCheckedIdStates.put(adapter.getItemId(position), Integer.valueOf(position));
/*     */         }
/* 138 */         this.mCheckedItemCount = 1;
/* 139 */       } else if (this.mCheckStates.size() == 0 || !this.mCheckStates.valueAt(0)) {
/* 140 */         this.mCheckedItemCount = 0;
/*     */       } 
/*     */       
/* 143 */       if (oldCheckedPosition != -1 && oldCheckedPosition != position) {
/* 144 */         Utils.safeNotifyItemChanged(adapter, oldCheckedPosition);
/*     */       }
/* 146 */       Utils.safeNotifyItemChanged(adapter, position);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearChoices() {
/* 151 */     if (this.mCheckStates != null) {
/* 152 */       this.mCheckStates.clear();
/*     */     }
/* 154 */     if (this.mCheckedIdStates != null) {
/* 155 */       this.mCheckedIdStates.clear();
/*     */     }
/* 157 */     this.mCheckedItemCount = 0;
/*     */     
/* 159 */     Utils.safeNotifyDataSetChanged(this.mRecyclerView.getAdapter());
/*     */   }
/*     */   
/*     */   public int getChoiceMode() {
/* 163 */     return this.mChoiceMode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChoiceMode(int choiceMode) {
/* 168 */     if (this.mChoiceMode == choiceMode) {
/*     */       return;
/*     */     }
/*     */     
/* 172 */     this.mChoiceMode = choiceMode;
/*     */     
/* 174 */     if (this.mChoiceMode != 0) {
/* 175 */       if (this.mCheckStates == null) {
/* 176 */         this.mCheckStates = new SparseBooleanArray();
/*     */       }
/*     */       
/* 179 */       RecyclerView.Adapter adapter = this.mRecyclerView.getAdapter();
/* 180 */       if (this.mCheckedIdStates == null && adapter != null && adapter.hasStableIds()) {
/* 181 */         this.mCheckedIdStates = new LongSparseArray();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
/* 188 */     if (holder.itemView instanceof Checkable) {
/* 189 */       ((Checkable)holder.itemView).setChecked(isItemChecked(position));
/*     */     } else {
/* 191 */       holder.itemView.setActivated(isItemChecked(position));
/*     */     } 
/*     */   }
/*     */   
/*     */   public RecyclerView.AdapterDataObserver getDataObserver() {
/* 196 */     return this.mDataObserver;
/*     */   }
/*     */   public ItemSelectionHelper() {
/* 199 */     this.mDataObserver = new RecyclerView.AdapterDataObserver()
/*     */       {
/*     */         public void onItemRangeRemoved(int positionStart, int itemCount) {
/* 202 */           if (ItemSelectionHelper.this.mCheckStates != null) {
/* 203 */             RecyclerView.Adapter adapter = ItemSelectionHelper.this.mRecyclerView.getAdapter();
/*     */             
/* 205 */             SparseBooleanArray states = new SparseBooleanArray();
/* 206 */             LongSparseArray<Integer> ids = null;
/* 207 */             if (ItemSelectionHelper.this.mCheckedIdStates != null && adapter != null && adapter.hasStableIds()) {
/* 208 */               ids = new LongSparseArray();
/*     */             }
/*     */ 
/*     */             
/* 212 */             for (int i = 0; i < ItemSelectionHelper.this.mCheckStates.size(); i++) {
/* 213 */               int position = ItemSelectionHelper.this.mCheckStates.keyAt(i);
/* 214 */               if (position < positionStart || position >= positionStart + itemCount) {
/*     */ 
/*     */                 
/* 217 */                 if (position >= positionStart + itemCount)
/*     */                 {
/* 219 */                   position -= itemCount;
/*     */                 }
/* 221 */                 states.put(position, ItemSelectionHelper.this.mCheckStates.valueAt(i));
/* 222 */                 if (ids != null)
/*     */                 {
/* 224 */                   ids.put(adapter.getItemId(position), Integer.valueOf(position)); } 
/*     */               } 
/*     */             } 
/* 227 */             ItemSelectionHelper.this.mCheckStates = states;
/* 228 */             if (ids != null) {
/* 229 */               ItemSelectionHelper.this.mCheckedIdStates = ids;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void onItemRangeInserted(int positionStart, int itemCount) {
/* 236 */           if (ItemSelectionHelper.this.mCheckStates != null) {
/* 237 */             RecyclerView.Adapter adapter = ItemSelectionHelper.this.mRecyclerView.getAdapter();
/*     */             
/* 239 */             SparseBooleanArray states = new SparseBooleanArray();
/* 240 */             LongSparseArray<Integer> ids = null;
/* 241 */             if (ItemSelectionHelper.this.mCheckedIdStates != null && adapter != null && adapter.hasStableIds()) {
/* 242 */               ids = new LongSparseArray();
/*     */             }
/*     */             
/* 245 */             for (int i = 0; i < ItemSelectionHelper.this.mCheckStates.size(); i++) {
/* 246 */               int position = ItemSelectionHelper.this.mCheckStates.keyAt(i);
/* 247 */               if (position >= positionStart)
/*     */               {
/* 249 */                 position += itemCount;
/*     */               }
/* 251 */               states.put(position, ItemSelectionHelper.this.mCheckStates.valueAt(i));
/* 252 */               if (ids != null)
/*     */               {
/* 254 */                 ids.put(adapter.getItemId(position), Integer.valueOf(position));
/*     */               }
/*     */             } 
/* 257 */             ItemSelectionHelper.this.mCheckStates = states;
/* 258 */             if (ids != null) {
/* 259 */               ItemSelectionHelper.this.mCheckedIdStates = ids;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
/* 266 */           if (ItemSelectionHelper.this.mCheckStates != null) {
/* 267 */             RecyclerView.Adapter adapter = ItemSelectionHelper.this.mRecyclerView.getAdapter();
/*     */             
/* 269 */             SparseBooleanArray states = new SparseBooleanArray();
/* 270 */             boolean updateIds = (ItemSelectionHelper.this.mCheckedIdStates != null && adapter != null && adapter.hasStableIds());
/*     */             
/*     */             int i;
/* 273 */             for (i = fromPosition; i < fromPosition + itemCount; i++) {
/* 274 */               int position = toPosition + i - fromPosition;
/* 275 */               boolean value = ItemSelectionHelper.this.mCheckStates.get(i);
/* 276 */               states.put(position, value);
/*     */             } 
/*     */             
/* 279 */             onItemRangeRemoved(fromPosition, itemCount);
/* 280 */             onItemRangeInserted(toPosition, itemCount);
/*     */ 
/*     */             
/* 283 */             for (i = 0; i < states.size(); i++) {
/* 284 */               int position = states.keyAt(i);
/* 285 */               ItemSelectionHelper.this.mCheckStates.put(position, states.valueAt(i));
/* 286 */               if (updateIds)
/* 287 */                 ItemSelectionHelper.this.mCheckedIdStates.put(adapter.getItemId(position), Integer.valueOf(position)); 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\ItemSelectionHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */