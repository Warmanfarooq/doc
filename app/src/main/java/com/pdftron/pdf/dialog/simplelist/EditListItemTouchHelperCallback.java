/*    */ package com.pdftron.pdf.dialog.simplelist;
/*    */ 
/*    */ import android.graphics.Canvas;
/*    */ import android.graphics.PorterDuff;
/*    */ import androidx.annotation.ColorInt;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*    */ import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;
/*    */ 
/*    */ 
/*    */ public class EditListItemTouchHelperCallback
/*    */   extends SimpleItemTouchHelperCallback
/*    */ {
/*    */   private boolean mDragging;
/*    */   @ColorInt
/*    */   private int mDragColor;
/*    */   
/*    */   public EditListItemTouchHelperCallback(ItemTouchHelperAdapter adapter, boolean enableLongPress, @ColorInt int dragColor) {
/* 19 */     super(adapter, 1, enableLongPress, false);
/* 20 */     this.mDragColor = dragColor;
/*    */   }
/*    */   
/*    */   public void setDragging(boolean dragging) {
/* 24 */     this.mDragging = dragging;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
/* 30 */     if (viewHolder.getItemViewType() == 1) {
/* 31 */       return makeMovementFlags(0, 0);
/*    */     }
/* 33 */     int dragFlags = 3;
/* 34 */     int swipeFlags = 0;
/* 35 */     return makeMovementFlags(3, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
/* 40 */     super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
/* 41 */     if (this.mDragging) {
/* 42 */       viewHolder.itemView.getBackground().mutate().setColorFilter(this.mDragColor, PorterDuff.Mode.MULTIPLY);
/* 43 */       viewHolder.itemView.getBackground().invalidateSelf();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
/* 49 */     super.clearView(recyclerView, viewHolder);
/* 50 */     if (this.mDragging) {
/* 51 */       viewHolder.itemView.getBackground().setColorFilter(null);
/* 52 */       viewHolder.itemView.getBackground().invalidateSelf();
/* 53 */       this.mDragging = false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\simplelist\EditListItemTouchHelperCallback.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */