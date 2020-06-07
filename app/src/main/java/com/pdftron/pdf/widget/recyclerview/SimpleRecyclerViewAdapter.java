/*    */ package com.pdftron.pdf.widget.recyclerview;
/*    */ 
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SimpleRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder>
/*    */   extends RecyclerView.Adapter<VH>
/*    */ {
/*    */   private RecyclerView mRecyclerView;
/*    */   private ViewHolderBindListener mBindListener;
/*    */   
/*    */   public SimpleRecyclerViewAdapter() {
/* 20 */     this(null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SimpleRecyclerViewAdapter(ViewHolderBindListener listener) {
/* 27 */     this.mBindListener = listener;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onAttachedToRecyclerView(RecyclerView recyclerView) {
/* 35 */     this.mRecyclerView = recyclerView;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
/* 43 */     this.mRecyclerView = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RecyclerView getRecyclerView() {
/* 50 */     return this.mRecyclerView;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBindViewHolder(VH holder, int position) {
/* 58 */     if (this.mBindListener != null)
/* 59 */       this.mBindListener.onBindViewHolder(holder, position); 
/*    */   }
/*    */   
/*    */   public abstract T getItem(int paramInt);
/*    */   
/*    */   public abstract void add(T paramT);
/*    */   
/*    */   public abstract boolean remove(T paramT);
/*    */   
/*    */   public abstract T removeAt(int paramInt);
/*    */   
/*    */   public abstract void insert(T paramT, int paramInt);
/*    */   
/*    */   public abstract void updateSpanCount(int paramInt);
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\SimpleRecyclerViewAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */