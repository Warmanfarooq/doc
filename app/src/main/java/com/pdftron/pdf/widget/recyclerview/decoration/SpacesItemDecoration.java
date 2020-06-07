/*    */ package com.pdftron.pdf.widget.recyclerview.decoration;
/*    */ 
/*    */ import android.graphics.Rect;
/*    */ import android.view.View;
/*    */ import androidx.recyclerview.widget.GridLayoutManager;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ 
/*    */ public class SpacesItemDecoration
/*    */   extends RecyclerView.ItemDecoration
/*    */ {
/*    */   private int mSpanCount;
/*    */   private int mSpacing;
/*    */   private boolean mHeadersEnabled;
/*    */   
/*    */   public SpacesItemDecoration(int spanCount, int spacing, boolean headersEnabled) {
/* 16 */     this.mSpanCount = spanCount;
/* 17 */     this.mSpacing = spacing;
/* 18 */     this.mHeadersEnabled = headersEnabled;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
/* 24 */     if (this.mSpanCount > 0) {
/* 25 */       int spanIndex; RecyclerView.LayoutManager lm = parent.getLayoutManager();
/*    */       
/* 27 */       int position = parent.getChildAdapterPosition(view);
/*    */       
/* 29 */       int spanSize = 0;
/* 30 */       if (lm instanceof GridLayoutManager) {
/* 31 */         GridLayoutManager gridLm = (GridLayoutManager)lm;
/* 32 */         spanIndex = gridLm.getSpanSizeLookup().getSpanIndex(position, this.mSpanCount);
/* 33 */         spanSize = gridLm.getSpanSizeLookup().getSpanSize(position);
/*    */       } else {
/*    */         
/* 36 */         spanIndex = position % this.mSpanCount;
/*    */       } 
/*    */       
/* 39 */       if (this.mHeadersEnabled && spanSize == this.mSpanCount) {
/* 40 */         outRect.left = 0;
/* 41 */         outRect.right = 0;
/*    */       } else {
/* 43 */         outRect.left = this.mSpacing - spanIndex * this.mSpacing / this.mSpanCount;
/* 44 */         outRect.right = (spanIndex + 1) * this.mSpacing / this.mSpanCount;
/*    */       } 
/*    */       
/* 47 */       if (!this.mHeadersEnabled && position < this.mSpanCount) {
/* 48 */         outRect.top = this.mSpacing;
/*    */       } else {
/* 50 */         outRect.top = 0;
/*    */       } 
/* 52 */       outRect.bottom = this.mSpacing;
/*    */     } else {
/* 54 */       outRect.setEmpty();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\recyclerview\decoration\SpacesItemDecoration.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */