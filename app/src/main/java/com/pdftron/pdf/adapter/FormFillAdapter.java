/*     */ package com.pdftron.pdf.adapter;
/*     */ 
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.CheckBox;
/*     */ import android.widget.RadioButton;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.util.HashSet;
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
/*     */ public class FormFillAdapter
/*     */   extends RecyclerView.Adapter<FormFillAdapter.ViewHolder>
/*     */ {
/*     */   private Field mField;
/*     */   private OnItemSelectListener mOnItemSelectListener;
/*     */   private HashSet<Integer> mSelectedPositions;
/*     */   private boolean mSingleChoice = true;
/*     */   
/*     */   public FormFillAdapter(Field field, HashSet<Integer> selectedPositions, OnItemSelectListener listener) {
/*  53 */     this.mField = field;
/*  54 */     this.mSelectedPositions = selectedPositions;
/*  55 */     this.mOnItemSelectListener = listener;
/*     */     
/*     */     try {
/*  58 */       this.mSingleChoice = (this.mField.getFlag(14) || !this.mField.getFlag(17));
/*  59 */     } catch (Exception e) {
/*  60 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*  69 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_form, parent, false);
/*  70 */     return new ViewHolder(view);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(ViewHolder holder, int position) {
/*     */     try {
/*  79 */       String text = this.mField.getOpt(position);
/*  80 */       if (this.mSingleChoice) {
/*  81 */         holder.checkBox.setVisibility(8);
/*  82 */         holder.radioButton.setVisibility(0);
/*  83 */         holder.radioButton.setChecked(this.mSelectedPositions.contains(Integer.valueOf(position)));
/*  84 */         holder.radioButton.setText(text);
/*     */       } else {
/*  86 */         holder.radioButton.setVisibility(8);
/*  87 */         holder.checkBox.setVisibility(0);
/*  88 */         holder.checkBox.setChecked(this.mSelectedPositions.contains(Integer.valueOf(position)));
/*  89 */         holder.checkBox.setText(text);
/*     */       } 
/*  91 */     } catch (Exception e) {
/*  92 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashSet<Integer> getSelectedPositions() {
/* 101 */     return this.mSelectedPositions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSingleSelectedPosition() {
/* 109 */     if (hasSingleSelectedPosition()) {
/* 110 */       return ((Integer)this.mSelectedPositions.iterator().next()).intValue();
/*     */     }
/* 112 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasSingleSelectedPosition() {
/* 120 */     return (this.mSingleChoice && !this.mSelectedPositions.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSingleSelectedPosition() {
/* 127 */     if (hasSingleSelectedPosition()) {
/* 128 */       this.mSelectedPositions.clear();
/*     */     }
/* 130 */     Utils.safeNotifyDataSetChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/*     */     try {
/* 139 */       return this.mField.getOptCount();
/* 140 */     } catch (Exception e) {
/* 141 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 142 */       return 0;
/*     */     } 
/*     */   }
/*     */   
/*     */   class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
/*     */     RadioButton radioButton;
/*     */     CheckBox checkBox;
/*     */     
/*     */     public ViewHolder(View view) {
/* 151 */       super(view);
/* 152 */       this.radioButton = (RadioButton)view.findViewById(R.id.radio_button_form_fill);
/* 153 */       this.radioButton.setOnClickListener(this);
/* 154 */       this.checkBox = (CheckBox)view.findViewById(R.id.check_box_form_fill);
/* 155 */       this.checkBox.setOnClickListener(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onClick(View view) {
/* 160 */       int pos = getAdapterPosition();
/* 161 */       if (pos == -1) {
/*     */         return;
/*     */       }
/* 164 */       if (FormFillAdapter.this.mSingleChoice) {
/* 165 */         FormFillAdapter.this.mSelectedPositions.clear();
/* 166 */         FormFillAdapter.this.mSelectedPositions.add(Integer.valueOf(pos));
/* 167 */         Utils.safeNotifyDataSetChanged(FormFillAdapter.this);
/*     */       }
/* 169 */       else if (FormFillAdapter.this.mSelectedPositions.contains(Integer.valueOf(pos))) {
/* 170 */         FormFillAdapter.this.mSelectedPositions.remove(Integer.valueOf(pos));
/*     */       } else {
/* 172 */         FormFillAdapter.this.mSelectedPositions.add(Integer.valueOf(pos));
/*     */       } 
/*     */       
/* 175 */       if (FormFillAdapter.this.mOnItemSelectListener != null) {
/* 176 */         FormFillAdapter.this.mOnItemSelectListener.onItemSelected(pos);
/*     */       }
/* 178 */       Utils.safeNotifyDataSetChanged(FormFillAdapter.this);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnItemSelectListener {
/*     */     void onItemSelected(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\FormFillAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */