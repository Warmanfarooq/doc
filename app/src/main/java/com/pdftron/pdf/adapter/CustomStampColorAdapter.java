/*    */ package com.pdftron.pdf.adapter;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.PorterDuff;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.appcompat.widget.AppCompatImageView;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomStampColorAdapter
/*    */   extends RecyclerView.Adapter<CustomStampColorAdapter.ViewHolder>
/*    */ {
/*    */   private int[] mBgColors;
/*    */   private int[] mStrokeColors;
/*    */   private int mSelectedIndex;
/*    */   
/*    */   public CustomStampColorAdapter(int[] bgColors, int[] strokeColors) {
/* 35 */     if (bgColors.length != strokeColors.length) {
/*    */       return;
/*    */     }
/* 38 */     this.mBgColors = bgColors;
/* 39 */     this.mStrokeColors = strokeColors;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void select(int index) {
/* 48 */     if (this.mSelectedIndex >= 0 && this.mSelectedIndex < this.mBgColors.length) {
/* 49 */       this.mSelectedIndex = index;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSelectedIndex() {
/* 57 */     return this.mSelectedIndex;
/*    */   }
/*    */ 
/*    */   
/*    */   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/* 62 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_color_icon, parent, false);
/* 63 */     return new ViewHolder(view);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBindViewHolder(ViewHolder holder, int position) {
/* 68 */     holder.mInnerIcon.setColorFilter(this.mBgColors[position], PorterDuff.Mode.SRC_IN);
/* 69 */     holder.mStrokeIcon.setColorFilter(this.mStrokeColors[position], PorterDuff.Mode.SRC_IN);
/* 70 */     holder.mOuterIcon.setVisibility((position == this.mSelectedIndex) ? 0 : 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemCount() {
/* 75 */     return (this.mStrokeColors == null) ? 0 : this.mStrokeColors.length;
/*    */   }
/*    */   
/*    */   class ViewHolder
/*    */     extends RecyclerView.ViewHolder {
/*    */     AppCompatImageView mInnerIcon;
/*    */     
/*    */     public ViewHolder(View itemView) {
/* 83 */       super(itemView);
/* 84 */       Context context = itemView.getContext();
/* 85 */       this.mOuterIcon = (AppCompatImageView)itemView.findViewById(R.id.select_bg_icon);
/* 86 */       setSize((View)this.mOuterIcon, Math.round(Utils.convDp2Pix(context, 40.0F)));
/* 87 */       this.mInnerIcon = (AppCompatImageView)itemView.findViewById(R.id.bg_icon);
/* 88 */       setSize((View)this.mInnerIcon, Math.round(Utils.convDp2Pix(context, 36.0F)));
/* 89 */       this.mStrokeIcon = (AppCompatImageView)itemView.findViewById(R.id.fg_icon);
/* 90 */       setSize((View)this.mStrokeIcon, Math.round(Utils.convDp2Pix(context, 20.0F)));
/*    */     }
/*    */     AppCompatImageView mOuterIcon; AppCompatImageView mStrokeIcon;
/*    */     private void setSize(@NonNull View view, int size) {
/* 94 */       ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
/* 95 */       layoutParams.width = size;
/* 96 */       layoutParams.height = size;
/* 97 */       view.setLayoutParams(layoutParams);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\CustomStampColorAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */