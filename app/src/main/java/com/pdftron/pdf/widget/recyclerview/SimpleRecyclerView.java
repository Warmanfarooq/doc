/*     */ package com.pdftron.pdf.widget.recyclerview;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.util.AttributeSet;
/*     */ import androidx.recyclerview.widget.GridLayoutManager;
/*     */ import androidx.recyclerview.widget.LinearLayoutManager;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.pdf.widget.recyclerview.decoration.DividerItemDecoration;
/*     */ import com.pdftron.pdf.widget.recyclerview.decoration.SpacesItemDecoration;
/*     */ 
/*     */ public class SimpleRecyclerView
/*     */   extends RecyclerView
/*     */ {
/*  16 */   private static final String TAG = SimpleRecyclerView.class.getName();
/*     */   
/*  18 */   private int mSpanCount = 1;
/*     */   
/*     */   private int mGridSpacing;
/*     */   
/*     */   private SimpleRecyclerViewAdapter mAdapter;
/*     */   private RecyclerView.LayoutManager mLayoutManager;
/*     */   private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;
/*     */   private DividerItemDecoration mDividerDecorator;
/*     */   private SpacesItemDecoration mSpacesDecorator;
/*     */   
/*     */   public SimpleRecyclerView(Context context) {
/*  29 */     this(context, (AttributeSet)null);
/*     */   }
/*     */   
/*     */   public SimpleRecyclerView(Context context, AttributeSet attrs) {
/*  33 */     this(context, attrs, 0);
/*     */   }
/*     */   
/*     */   public SimpleRecyclerView(Context context, AttributeSet attrs, int defStyle) {
/*  37 */     super(context, attrs, defStyle);
/*     */     
/*  39 */     initDefaultView();
/*     */   }
/*     */   
/*     */   public void initDefaultView() {
/*  43 */     initView(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initView(int spanCount) {
/*  53 */     initView(spanCount, getResources().getDimensionPixelSize(R.dimen.file_list_grid_spacing));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initView(int spanCount, int gridSpacing) {
/*  63 */     this.mSpanCount = spanCount;
/*  64 */     this.mGridSpacing = gridSpacing;
/*  65 */     setHasFixedSize(true);
/*     */     
/*  67 */     if (this.mSpanCount > 0) {
/*  68 */       this.mLayoutManager = (RecyclerView.LayoutManager)new GridLayoutManager(getContext(), this.mSpanCount);
/*  69 */       if (this.mSpanSizeLookup != null) {
/*  70 */         ((GridLayoutManager)this.mLayoutManager).setSpanSizeLookup(this.mSpanSizeLookup);
/*     */       }
/*     */     } else {
/*  73 */       this.mLayoutManager = (RecyclerView.LayoutManager)getLinearLayoutManager();
/*     */     } 
/*  75 */     setLayoutManager(this.mLayoutManager);
/*     */     
/*  77 */     if (this.mDividerDecorator != null) {
/*  78 */       removeItemDecoration();
/*  79 */       this.mDividerDecorator = null;
/*     */     } 
/*     */     
/*  82 */     Utils.safeRemoveItemDecoration(this, (RecyclerView.ItemDecoration)this.mSpacesDecorator);
/*  83 */     this.mSpacesDecorator = new SpacesItemDecoration(this.mSpanCount, this.mGridSpacing, false);
/*     */     
/*  85 */     addItemDecoration((RecyclerView.ItemDecoration)this.mSpacesDecorator);
/*     */   }
/*     */   
/*     */   public void updateLayoutManager(int count) {
/*  89 */     if ((this.mSpanCount == 0 && count > 0) || (this.mSpanCount > 0 && count == 0)) {
/*     */       
/*  91 */       if (count > 0) {
/*  92 */         this.mLayoutManager = (RecyclerView.LayoutManager)new GridLayoutManager(getContext(), count);
/*  93 */         if (this.mSpanSizeLookup != null) {
/*  94 */           ((GridLayoutManager)this.mLayoutManager).setSpanSizeLookup(this.mSpanSizeLookup);
/*     */         }
/*     */       } else {
/*  97 */         this.mLayoutManager = (RecyclerView.LayoutManager)getLinearLayoutManager();
/*     */       } 
/*  99 */       setLayoutManager(this.mLayoutManager);
/*     */     
/*     */     }
/* 102 */     else if (count > 0) {
/* 103 */       if (this.mLayoutManager instanceof GridLayoutManager) {
/* 104 */         ((GridLayoutManager)this.mLayoutManager).setSpanCount(count);
/* 105 */         this.mLayoutManager.requestLayout();
/*     */       } else {
/* 107 */         this.mLayoutManager = (RecyclerView.LayoutManager)new GridLayoutManager(getContext(), count);
/* 108 */         setLayoutManager(this.mLayoutManager);
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     if (this.mDividerDecorator != null) {
/* 113 */       Utils.safeRemoveItemDecoration(this, (RecyclerView.ItemDecoration)this.mDividerDecorator);
/* 114 */       this.mDividerDecorator = null;
/*     */     } 
/*     */     
/* 117 */     Utils.safeRemoveItemDecoration(this, (RecyclerView.ItemDecoration)this.mSpacesDecorator);
/* 118 */     this.mSpacesDecorator = new SpacesItemDecoration(count, this.mGridSpacing, false);
/*     */     
/* 120 */     addItemDecoration((RecyclerView.ItemDecoration)this.mSpacesDecorator);
/*     */   }
/*     */   
/*     */   public void updateSpanCount(int count) {
/* 124 */     if (this.mAdapter != null) {
/* 125 */       this.mAdapter.updateSpanCount(count);
/*     */     }
/* 127 */     updateLayoutManager(count);
/* 128 */     setRecycledViewPool(null);
/* 129 */     this.mSpanCount = count;
/* 130 */     Utils.safeNotifyDataSetChanged(getAdapter());
/* 131 */     invalidate();
/*     */   }
/*     */   
/*     */   public void update(int spanCount) {
/* 135 */     updateSpanCount(spanCount);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAdapter(RecyclerView.Adapter adapter) {
/* 140 */     super.setAdapter(adapter);
/*     */     
/* 142 */     if (adapter instanceof SimpleRecyclerViewAdapter) {
/* 143 */       this.mAdapter = (SimpleRecyclerViewAdapter)adapter;
/*     */     }
/*     */   }
/*     */   
/*     */   protected LinearLayoutManager getLinearLayoutManager() {
/* 148 */     return new LinearLayoutManager(getContext());
/*     */   }
/*     */   
/*     */   public void setSpanSizeLookup(GridLayoutManager.SpanSizeLookup spanSizeLookup) {
/* 152 */     this.mSpanSizeLookup = spanSizeLookup;
/*     */   }
/*     */   
/*     */   public void removeItemDecoration() {
/* 156 */     Utils.safeRemoveItemDecoration(this, (RecyclerView.ItemDecoration)this.mDividerDecorator);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\SimpleRecyclerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */