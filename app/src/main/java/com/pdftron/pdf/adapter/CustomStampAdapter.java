/*     */ package com.pdftron.pdf.adapter;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.Bitmap;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*     */ import com.pdftron.pdf.model.CustomStampOption;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerViewAdapter;
/*     */ import com.pdftron.pdf.widget.recyclerview.ViewHolderBindListener;
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CustomStampAdapter
/*     */   extends SimpleRecyclerViewAdapter<Bitmap, CustomStampAdapter.ViewHolder>
/*     */   implements ItemTouchHelperAdapter
/*     */ {
/*     */   private WeakReference<Context> mContextRef;
/*  33 */   private List<WeakReference<Bitmap>> mBitmapsRef = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CustomStampAdapter(@NonNull Context context, ViewHolderBindListener bindListener) {
/*  41 */     super(bindListener);
/*  42 */     this.mContextRef = new WeakReference<>(context);
/*  43 */     int count = CustomStampOption.getCustomStampsCount(context);
/*  44 */     for (int i = 0; i < count; i++) {
/*  45 */       this.mBitmapsRef.add(new WeakReference<>(null));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCustomStampUpdated(Bitmap item, int position) {
/*  56 */     this.mBitmapsRef.set(position, new WeakReference<>(item));
/*  57 */     notifyItemChanged(position);
/*     */   }
/*     */ 
/*     */   
/*     */   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
/*  62 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_rubber_stamp, parent, false);
/*  63 */     return new ViewHolder(view);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(ViewHolder holder, int position) {
/*  68 */     super.onBindViewHolder(holder, position);
/*  69 */     holder.mStampView.setImageBitmap(getItem(position));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/*  74 */     return this.mBitmapsRef.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public Bitmap getItem(int position) {
/*  79 */     if (position < 0 || position >= this.mBitmapsRef.size()) {
/*  80 */       return null;
/*     */     }
/*     */     
/*  83 */     Bitmap bitmap = ((WeakReference<Bitmap>)this.mBitmapsRef.get(position)).get();
/*  84 */     if (bitmap == null) {
/*  85 */       Context context = this.mContextRef.get();
/*  86 */       if (context == null) {
/*  87 */         return null;
/*     */       }
/*  89 */       bitmap = CustomStampOption.getCustomStampBitmap(context, position);
/*  90 */       if (bitmap == null)
/*     */       {
/*     */ 
/*     */ 
/*     */         
/*  95 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("The bitmap of stamp is not stored in the disk! position: " + position));
/*     */       }
/*     */       
/*  98 */       this.mBitmapsRef.set(position, new WeakReference<>(bitmap));
/*     */     } 
/* 100 */     return bitmap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(Bitmap item) {
/* 105 */     this.mBitmapsRef.add(new WeakReference<>(item));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Bitmap item) {
/* 110 */     for (WeakReference<Bitmap> bitmapRef : this.mBitmapsRef) {
/* 111 */       Bitmap bitmap = bitmapRef.get();
/* 112 */       if (bitmap == item) {
/* 113 */         this.mBitmapsRef.remove(bitmapRef);
/* 114 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bitmap removeAt(int position) {
/* 123 */     return ((WeakReference<Bitmap>)this.mBitmapsRef.remove(position)).get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void insert(Bitmap item, int position) {
/* 128 */     this.mBitmapsRef.add(position, new WeakReference<>(item));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateSpanCount(int count) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onItemMove(int fromPosition, int toPosition) {
/* 138 */     if (toPosition < getItemCount()) {
/* 139 */       Bitmap item = removeAt(fromPosition);
/* 140 */       insert(item, toPosition);
/* 141 */       notifyItemMoved(fromPosition, toPosition);
/* 142 */       return true;
/*     */     } 
/*     */     
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemDrop(int fromPosition, int toPosition) {
/* 150 */     Context context = this.mContextRef.get();
/* 151 */     if (context != null && fromPosition != toPosition && fromPosition != -1 && toPosition != -1)
/*     */     {
/* 153 */       CustomStampOption.moveCustomStamp(context, fromPosition, toPosition);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemDismiss(int position) {}
/*     */ 
/*     */   
/*     */   class ViewHolder
/*     */     extends RecyclerView.ViewHolder
/*     */   {
/*     */     AppCompatImageView mStampView;
/*     */     
/*     */     public ViewHolder(View itemView) {
/* 167 */       super(itemView);
/* 168 */       this.mStampView = (AppCompatImageView)itemView.findViewById(R.id.stamp_image_view);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\adapter\CustomStampAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */