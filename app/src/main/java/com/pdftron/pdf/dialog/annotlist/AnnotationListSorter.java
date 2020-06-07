/*    */ package com.pdftron.pdf.dialog.annotlist;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.RestrictTo;
/*    */ import androidx.lifecycle.ViewModelProvider;
/*    */ import com.pdftron.pdf.controls.AnnotationDialogFragment;
/*    */ import com.pdftron.pdf.utils.AnnotUtils;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestrictTo({RestrictTo.Scope.LIBRARY})
/*    */ public class AnnotationListSorter
/*    */   extends BaseAnnotationListSorter<AnnotationDialogFragment.AnnotationInfo>
/*    */ {
/* 16 */   protected final Comparator<AnnotationDialogFragment.AnnotationInfo> mTopToBottomComparator = new Comparator<AnnotationDialogFragment.AnnotationInfo>()
/*    */     {
/*    */       public int compare(AnnotationDialogFragment.AnnotationInfo thisObj, AnnotationDialogFragment.AnnotationInfo thatObj)
/*    */       {
/* 20 */         return AnnotationListSorter.compareYPosition(thisObj, thatObj);
/*    */       }
/*    */     };
/*    */   
/* 24 */   protected final Comparator<AnnotationDialogFragment.AnnotationInfo> mDateComparator = new Comparator<AnnotationDialogFragment.AnnotationInfo>()
/*    */     {
/*    */       public int compare(AnnotationDialogFragment.AnnotationInfo thisObj, AnnotationDialogFragment.AnnotationInfo thatObj)
/*    */       {
/* 28 */         return AnnotationListSorter.compareCreationDate(thisObj, thatObj);
/*    */       }
/*    */     };
/*    */   
/*    */   public AnnotationListSorter(@NonNull BaseAnnotationSortOrder sortOrder) {
/* 33 */     super(sortOrder);
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public Comparator<AnnotationDialogFragment.AnnotationInfo> getComparator() {
/* 39 */     BaseAnnotationSortOrder value = (BaseAnnotationSortOrder)this.mSortOrder.getValue();
/* 40 */     if (value != null && 
/* 41 */       value instanceof AnnotationListSortOrder) {
/* 42 */       switch ((AnnotationListSortOrder)value) {
/*    */         case DATE_ASCENDING:
/* 44 */           return this.mDateComparator;
/*    */         case POSITION_ASCENDING:
/* 46 */           return this.mTopToBottomComparator;
/*    */       } 
/*    */     
/*    */     }
/* 50 */     return this.mDateComparator;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int compareDate(AnnotationDialogFragment.AnnotationInfo thisObj, AnnotationDialogFragment.AnnotationInfo thatObj) {
/* 56 */     return AnnotUtils.compareDate(thisObj.getAnnotation(), thatObj.getAnnotation());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int compareCreationDate(AnnotationDialogFragment.AnnotationInfo thisObj, AnnotationDialogFragment.AnnotationInfo thatObj) {
/* 62 */     return AnnotUtils.compareCreationDate(thisObj.getAnnotation(), thatObj.getAnnotation());
/*    */   }
/*    */ 
/*    */   
/*    */   public static int compareYPosition(AnnotationDialogFragment.AnnotationInfo thisObj, AnnotationDialogFragment.AnnotationInfo thatObj) {
/* 67 */     double thisY2 = thisObj.getY2();
/* 68 */     double thatY2 = thatObj.getY2();
/* 69 */     return Double.compare(thatY2, thisY2);
/*    */   }
/*    */   
/*    */   public static class Factory implements ViewModelProvider.Factory {
/*    */     private BaseAnnotationSortOrder mSortOrder;
/*    */     
/*    */     public Factory(BaseAnnotationSortOrder sortOrder) {
/* 76 */       this.mSortOrder = sortOrder;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     @NonNull
/*    */     public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
/* 83 */       if (modelClass.isAssignableFrom(AnnotationListSorter.class)) {
/* 84 */         return (T)new AnnotationListSorter(this.mSortOrder);
/*    */       }
/* 86 */       throw new IllegalArgumentException("Unknown ViewModel class");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\annotlist\AnnotationListSorter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */