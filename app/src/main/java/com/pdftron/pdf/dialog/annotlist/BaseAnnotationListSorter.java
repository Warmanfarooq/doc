/*    */ package com.pdftron.pdf.dialog.annotlist;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.RestrictTo;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import androidx.lifecycle.Observer;
/*    */ import androidx.lifecycle.ViewModel;
/*    */ import java.util.Collections;
/*    */ import java.util.Comparator;
/*    */ import java.util.List;
/*    */ 
/*    */ @RestrictTo({RestrictTo.Scope.LIBRARY})
/*    */ public abstract class BaseAnnotationListSorter<T>
/*    */   extends ViewModel {
/* 16 */   protected final MutableLiveData<BaseAnnotationSortOrder> mSortOrder = new MutableLiveData();
/*    */   
/*    */   public BaseAnnotationListSorter(@NonNull BaseAnnotationSortOrder annotationListSortOrder) {
/* 19 */     this.mSortOrder.setValue(annotationListSortOrder);
/*    */   }
/*    */ 
/*    */   
/*    */   public void observeSortOrderChanges(@NonNull LifecycleOwner owner, @NonNull Observer<BaseAnnotationSortOrder> observer) {
/* 24 */     this.mSortOrder.observe(owner, observer);
/*    */   }
/*    */   
/*    */   public void publishSortOrderChange(@NonNull BaseAnnotationSortOrder sortOrder) {
/* 28 */     this.mSortOrder.setValue(sortOrder);
/*    */   }
/*    */   
/*    */   public void sort(@NonNull List<T> annotationInfos) {
/* 32 */     Collections.sort(annotationInfos, getComparator());
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public abstract Comparator<T> getComparator();
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\annotlist\BaseAnnotationListSorter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */