/*    */ package com.pdftron.pdf.dialog.annotlist;
/*    */ 
/*    */ import androidx.annotation.RestrictTo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum AnnotationListSortOrder
/*    */   implements BaseAnnotationSortOrder
/*    */ {
/* 11 */   POSITION_ASCENDING(1),
/* 12 */   DATE_ASCENDING(2);
/*    */   
/*    */   public final int value;
/*    */   
/*    */   AnnotationListSortOrder(int i) {
/* 17 */     this.value = i;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getType() {
/* 27 */     return AnnotationListSortOrder.class.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     return Integer.toString(this.value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @RestrictTo({RestrictTo.Scope.LIBRARY})
/*    */   public static AnnotationListSortOrder fromValue(int value) {
/* 40 */     for (AnnotationListSortOrder annotationListSortOrder : values()) {
/* 41 */       if (annotationListSortOrder.value == value)
/* 42 */         return annotationListSortOrder; 
/*    */     } 
/* 44 */     return DATE_ASCENDING;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\annotlist\AnnotationListSortOrder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */