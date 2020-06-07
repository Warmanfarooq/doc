/*    */ package com.pdftron.pdf.adapter;
/*    */ 
/*    */ import android.graphics.Bitmap;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import androidx.appcompat.widget.AppCompatImageView;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StandardRubberStampAdapter
/*    */   extends RecyclerView.Adapter<StandardRubberStampAdapter.ViewHolder>
/*    */ {
/*    */   private Bitmap[] mBitmaps;
/*    */   
/*    */   public StandardRubberStampAdapter(Bitmap[] bitmaps) {
/* 22 */     this.mBitmaps = bitmaps;
/*    */   }
/*    */ 
/*    */   
/*    */   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/* 27 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_rubber_stamp, parent, false);
/* 28 */     return new ViewHolder(view);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBindViewHolder(ViewHolder holder, int position) {
/* 33 */     holder.mStampView.setImageBitmap(this.mBitmaps[position]);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemCount() {
/* 38 */     return (this.mBitmaps == null) ? 0 : this.mBitmaps.length;
/*    */   }
/*    */   
/*    */   class ViewHolder
/*    */     extends RecyclerView.ViewHolder {
/*    */     AppCompatImageView mStampView;
/*    */     
/*    */     public ViewHolder(View itemView) {
/* 46 */       super(itemView);
/* 47 */       this.mStampView = (AppCompatImageView)itemView.findViewById(R.id.stamp_image_view);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\StandardRubberStampAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */