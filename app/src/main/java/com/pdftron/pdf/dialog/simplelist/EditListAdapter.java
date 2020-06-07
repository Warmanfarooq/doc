/*     */ package com.pdftron.pdf.dialog.simplelist;
/*     */ 
/*     */ import android.view.KeyEvent;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerViewAdapter;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EditListAdapter<T>
/*     */   extends SimpleRecyclerViewAdapter<T, EditListViewHolder>
/*     */ {
/*     */   protected boolean mEditing;
/*  24 */   protected int mSelectedIndex = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditListAdapter(ViewHolderBindListener listener) {
/*  30 */     super(listener);
/*     */   }
/*     */   
/*     */   public void setEditing(boolean editing) {
/*  34 */     this.mEditing = editing;
/*     */   }
/*     */   
/*     */   public void setSelectedIndex(int index) {
/*  38 */     this.mSelectedIndex = index;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public EditListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
/*  44 */     return new EditListViewHolder(
/*  45 */         LayoutInflater.from(viewGroup.getContext())
/*  46 */         .inflate(R.layout.dialog_edit_listview_item, viewGroup, false));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(@NonNull final EditListViewHolder holder, int position) {
/*  52 */     super.onBindViewHolder(holder, position);
/*     */     
/*  54 */     holder.contextButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  57 */             EditListAdapter.this.contextButtonClicked(holder, v);
/*     */           }
/*     */         });
/*     */     
/*  61 */     holder.confirmButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  64 */             EditListAdapter.this.confirmButtonClicked(holder, v);
/*     */           }
/*     */         });
/*     */     
/*  68 */     holder.editText.setOnEditorActionListener(new TextView.OnEditorActionListener()
/*     */         {
/*     */           public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
/*  71 */             return EditListAdapter.this.handleEditTextEditorAction(holder, v, actionId, event);
/*     */           }
/*     */         });
/*     */     
/*  75 */     holder.editText.setOnFocusChangeListener(new View.OnFocusChangeListener()
/*     */         {
/*     */           public void onFocusChange(View v, boolean hasFocus) {
/*  78 */             EditListAdapter.this.handleEditTextFocusChange(holder, v, hasFocus);
/*     */           }
/*     */         });
/*     */     
/*  82 */     if (this.mEditing) {
/*  83 */       holder.itemView.setFocusableInTouchMode(true);
/*  84 */       if (position == this.mSelectedIndex) {
/*  85 */         holder.textView.setVisibility(8);
/*  86 */         holder.contextButton.setVisibility(8);
/*  87 */         holder.editText.setVisibility(0);
/*  88 */         holder.confirmButton.setVisibility(0);
/*     */       } 
/*     */     } else {
/*  91 */       holder.editText.clearFocus();
/*  92 */       holder.itemView.setFocusableInTouchMode(false);
/*  93 */       holder.textView.setVisibility(0);
/*  94 */       holder.contextButton.setVisibility(0);
/*  95 */       holder.editText.setVisibility(8);
/*  96 */       holder.confirmButton.setVisibility(8);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void confirmButtonClicked(@NonNull EditListViewHolder holder, View v) {
/* 103 */     holder.itemView.requestFocus();
/*     */   }
/*     */   
/*     */   protected boolean handleEditTextEditorAction(@NonNull EditListViewHolder holder, TextView v, int actionId, KeyEvent event) {
/* 107 */     int pos = holder.getAdapterPosition();
/* 108 */     if (pos == -1) {
/* 109 */       return false;
/*     */     }
/* 111 */     if (actionId == 6 || event.getKeyCode() == 66) {
/* 112 */       v.clearFocus();
/* 113 */       return true;
/*     */     } 
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidIndex(int position) {
/* 121 */     if (position >= 0 && position < getItemCount()) {
/* 122 */       return true;
/*     */     }
/* 124 */     return false;
/*     */   }
/*     */   
/*     */   public EditListAdapter() {}
/*     */   
/*     */   protected abstract void contextButtonClicked(@NonNull EditListViewHolder paramEditListViewHolder, View paramView);
/*     */   
/*     */   protected abstract void handleEditTextFocusChange(@NonNull EditListViewHolder paramEditListViewHolder, View paramView, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\simplelist\EditListAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */