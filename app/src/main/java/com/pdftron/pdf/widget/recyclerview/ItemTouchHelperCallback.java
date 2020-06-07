/*    */ package com.pdftron.pdf.widget.recyclerview;
/*    */ 
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*    */ import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTouchHelperCallback
/*    */   extends SimpleItemTouchHelperCallback
/*    */ {
/*    */   public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter, int span, boolean enableLongPress, boolean enableSwipe) {
/* 18 */     super(adapter, span, enableLongPress, enableSwipe);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
/* 27 */     if (viewHolder.getItemViewType() == 1) {
/* 28 */       return makeMovementFlags(0, 0);
/*    */     }
/* 30 */     if (recyclerView.getLayoutManager() instanceof androidx.recyclerview.widget.GridLayoutManager || (recyclerView
/* 31 */       .getLayoutManager() instanceof RecyclerView.LayoutManager && this.mSpan > 1)) {
/* 32 */       int i = 15;
/* 33 */       int j = 0;
/* 34 */       return makeMovementFlags(15, 0);
/*    */     } 
/* 36 */     int dragFlags = 3;
/* 37 */     int swipeFlags = 0;
/* 38 */     return makeMovementFlags(3, 0);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\ItemTouchHelperCallback.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */