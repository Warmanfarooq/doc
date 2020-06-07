/*    */ package com.pdftron.pdf.model;
/*    */ 
/*    */ import android.util.SparseArray;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.utils.AnnotUtils;
/*    */ 
/*    */ 
/*    */ public enum AnnotReviewState
/*    */ {
/* 11 */   ACCEPTED(0),
/* 12 */   REJECTED(1),
/* 13 */   CANCELLED(2),
/* 14 */   COMPLETED(3),
/* 15 */   NONE(4); private int value;
/*    */   
/*    */   static {
/* 18 */     map = new SparseArray(5);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 25 */     for (AnnotReviewState reviewState : values())
/* 26 */       map.put(reviewState.value, reviewState); 
/*    */   }
/*    */   private static SparseArray<AnnotReviewState> map;
/*    */   
/*    */   public int getValue() {
/* 31 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   AnnotReviewState(int value) {
/*    */     this.value = value;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public static AnnotReviewState from(@NonNull String reviewState) {
/* 44 */     if (AnnotUtils.Key_StateNone.equals(reviewState))
/* 45 */       return NONE; 
/* 46 */     if (AnnotUtils.Key_StateAccepted.equals(reviewState))
/* 47 */       return ACCEPTED; 
/* 48 */     if (AnnotUtils.Key_StateRejected.equals(reviewState))
/* 49 */       return REJECTED; 
/* 50 */     if (AnnotUtils.Key_StateCancelled.equals(reviewState))
/* 51 */       return CANCELLED; 
/* 52 */     if (AnnotUtils.Key_StateCompleted.equals(reviewState)) {
/* 53 */       return COMPLETED;
/*    */     }
/* 55 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\AnnotReviewState.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */