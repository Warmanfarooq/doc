/*     */ package co.paulburke.android.itemtouchhelperdemo.helper;
/*     */ 
/*     */ import android.graphics.Canvas;
/*     */ import androidx.recyclerview.widget.ItemTouchHelper;
/*     */ import androidx.recyclerview.widget.RecyclerView;
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
/*     */ public class SimpleItemTouchHelperCallback
/*     */   extends ItemTouchHelper.Callback
/*     */ {
/*     */   public static final float ALPHA_FULL = 1.0F;
/*     */   public static final int VIEW_TYPE_HEADER = 1;
/*     */   public static final int VIEW_TYPE_CONTENT = 0;
/*     */   public static final int VIEW_TYPE_FOOTER = 2;
/*     */   protected final ItemTouchHelperAdapter mAdapter;
/*     */   protected int mSpan;
/*     */   protected final boolean mLongPressDragEnabled;
/*     */   protected final boolean mItemViewSwipeEnabled;
/*     */   protected int mDragFromPosition;
/*     */   protected int mDragToPosition;
/*     */   protected boolean mAllowDragAmongSections;
/*     */   
/*     */   public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter, int span, boolean enableLongPress, boolean enableSwipe) {
/*  54 */     this.mAdapter = adapter;
/*  55 */     this.mSpan = span;
/*  56 */     this.mLongPressDragEnabled = enableLongPress;
/*  57 */     this.mItemViewSwipeEnabled = enableSwipe;
/*     */     
/*  59 */     this.mDragFromPosition = -1;
/*  60 */     this.mDragToPosition = -1;
/*     */   }
/*     */   
/*     */   public void setSpan(int span) {
/*  64 */     this.mSpan = span;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowDragAmongSections(boolean allowDragAmongSections) {
/*  71 */     this.mAllowDragAmongSections = allowDragAmongSections;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLongPressDragEnabled() {
/*  76 */     return this.mLongPressDragEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isItemViewSwipeEnabled() {
/*  81 */     return this.mItemViewSwipeEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
/*  87 */     if (viewHolder.getItemViewType() == 1) {
/*  88 */       return makeMovementFlags(0, 0);
/*     */     }
/*  90 */     if (recyclerView.getLayoutManager() instanceof androidx.recyclerview.widget.GridLayoutManager) {
/*  91 */       int i = 15;
/*  92 */       int j = 0;
/*  93 */       return makeMovementFlags(15, 0);
/*     */     } 
/*  95 */     int dragFlags = 3;
/*  96 */     int swipeFlags = 0;
/*  97 */     return makeMovementFlags(3, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
/* 103 */     if (source.getItemViewType() != target.getItemViewType() && !this.mAllowDragAmongSections) {
/* 104 */       return false;
/*     */     }
/*     */     
/* 107 */     if (this.mDragFromPosition == -1) {
/* 108 */       this.mDragFromPosition = source.getAdapterPosition();
/*     */     }
/* 110 */     this.mDragToPosition = target.getAdapterPosition();
/*     */ 
/*     */     
/* 113 */     this.mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
/* 120 */     this.mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
/* 125 */     if (actionState == 1) {
/*     */       
/* 127 */       float alpha = 1.0F - Math.abs(dX) / viewHolder.itemView.getWidth();
/* 128 */       viewHolder.itemView.setAlpha(alpha);
/* 129 */       viewHolder.itemView.setTranslationX(dX);
/*     */     } else {
/* 131 */       super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
/* 138 */     if (actionState != 0 && 
/* 139 */       viewHolder instanceof ItemTouchHelperViewHolder) {
/*     */       
/* 141 */       ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder)viewHolder;
/* 142 */       itemViewHolder.onItemSelected();
/*     */     } 
/*     */     
/* 145 */     if (this.mAllowDragAmongSections && actionState == 0)
/*     */     {
/*     */       
/* 148 */       this.mAdapter.onItemDrop(-1, -1);
/*     */     }
/*     */     
/* 151 */     super.onSelectedChanged(viewHolder, actionState);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
/* 156 */     super.clearView(recyclerView, viewHolder);
/*     */     
/* 158 */     viewHolder.itemView.setAlpha(1.0F);
/*     */     
/* 160 */     if (viewHolder instanceof ItemTouchHelperViewHolder) {
/*     */       
/* 162 */       ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder)viewHolder;
/* 163 */       itemViewHolder.onItemClear();
/*     */     } 
/*     */     
/* 166 */     if (this.mDragFromPosition != -1 && this.mDragToPosition != -1)
/*     */     {
/* 168 */       this.mAdapter.onItemDrop(this.mDragFromPosition, this.mDragToPosition);
/*     */     }
/* 170 */     this.mDragFromPosition = this.mDragToPosition = -1;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\co\paulburke\android\itemtouchhelperdemo\helper\SimpleItemTouchHelperCallback.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */