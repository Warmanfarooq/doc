/*     */ package com.pdftron.pdf.widget.recyclerview;
/*     */ 
/*     */ import android.view.View;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemClickHelper
/*     */   implements RecyclerView.OnChildAttachStateChangeListener
/*     */ {
/*     */   private RecyclerView mRecyclerView;
/*     */   private OnItemClickListener mOnItemClickListener;
/*     */   private OnItemLongClickListener mOnItemLongClickListener;
/*     */   
/*     */   public void attachToRecyclerView(RecyclerView recyclerView) {
/*  31 */     if (this.mRecyclerView == recyclerView) {
/*     */       return;
/*     */     }
/*  34 */     if (this.mRecyclerView != null) {
/*  35 */       this.mRecyclerView.removeOnChildAttachStateChangeListener(this);
/*     */     }
/*  37 */     this.mRecyclerView = recyclerView;
/*  38 */     if (this.mRecyclerView != null) {
/*  39 */       this.mRecyclerView.addOnChildAttachStateChangeListener(this);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnItemClickListener(OnItemClickListener listener) {
/*  80 */     this.mOnItemClickListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnItemLongClickListener(OnItemLongClickListener listener) {
/*  89 */     this.mOnItemLongClickListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChildViewAttachedToWindow(View view) {
/*  94 */     if (this.mOnItemClickListener != null) {
/*  95 */       view.setOnClickListener(this.mOnClickListener);
/*     */     }
/*  97 */     if (this.mOnItemLongClickListener != null) {
/*  98 */       view.setOnLongClickListener(this.mOnLongClickListener);
/*     */     }
/* 100 */     if (Utils.isMarshmallow()) {
/* 101 */       view.setOnContextClickListener(new View.OnContextClickListener()
/*     */           {
/*     */             public boolean onContextClick(View v) {
/* 104 */               return v.performLongClick();
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChildViewDetachedFromWindow(View view) {}
/*     */   
/* 113 */   private View.OnClickListener mOnClickListener = new View.OnClickListener()
/*     */     {
/*     */       public void onClick(View view) {
/* 116 */         if (ItemClickHelper.this.mOnItemClickListener != null) {
/* 117 */           RecyclerView.ViewHolder holder = ItemClickHelper.this.mRecyclerView.getChildViewHolder(view);
/* 118 */           if (holder != null) {
/* 119 */             int pos = holder.getAdapterPosition();
/* 120 */             if (pos == -1) {
/*     */               return;
/*     */             }
/* 123 */             ItemClickHelper.this.mOnItemClickListener.onItemClick(ItemClickHelper.this.mRecyclerView, view, pos, holder.getItemId());
/*     */           } 
/*     */         } 
/*     */       }
/*     */     };
/*     */   
/* 129 */   private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener()
/*     */     {
/*     */       public boolean onLongClick(View view) {
/* 132 */         if (ItemClickHelper.this.mOnItemLongClickListener != null) {
/* 133 */           RecyclerView.ViewHolder holder = ItemClickHelper.this.mRecyclerView.getChildViewHolder(view);
/* 134 */           if (holder != null) {
/* 135 */             int pos = holder.getAdapterPosition();
/* 136 */             return (pos != -1 && ItemClickHelper.this
/* 137 */               .mOnItemLongClickListener.onItemLongClick(ItemClickHelper.this.mRecyclerView, view, pos, holder.getItemId()));
/*     */           } 
/*     */         } 
/* 140 */         return false;
/*     */       }
/*     */     };
/*     */   
/*     */   public static interface OnItemClickListener {
/*     */     void onItemClick(RecyclerView param1RecyclerView, View param1View, int param1Int, long param1Long);
/*     */   }
/*     */   
/*     */   public static interface OnItemLongClickListener {
/*     */     boolean onItemLongClick(RecyclerView param1RecyclerView, View param1View, int param1Int, long param1Long);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\ItemClickHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */