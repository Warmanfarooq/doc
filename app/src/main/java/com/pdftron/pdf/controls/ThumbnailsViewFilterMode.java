/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.lifecycle.LifecycleOwner;
/*    */ import androidx.lifecycle.MutableLiveData;
/*    */ import androidx.lifecycle.Observer;
/*    */ import androidx.lifecycle.ViewModel;
/*    */ import androidx.lifecycle.ViewModelProvider;
/*    */ 
/*    */ public class ThumbnailsViewFilterMode extends ViewModel {
/* 11 */   private final MutableLiveData<Integer> mFilterMode = new MutableLiveData();
/*    */   
/*    */   public ThumbnailsViewFilterMode(@NonNull Integer filterMode) {
/* 14 */     this.mFilterMode.setValue(filterMode);
/*    */   }
/*    */ 
/*    */   
/*    */   public void observeFilterTypeChanges(@NonNull LifecycleOwner owner, @NonNull Observer<Integer> observer) {
/* 19 */     this.mFilterMode.observe(owner, observer);
/*    */   }
/*    */   
/*    */   public void publishFilterTypeChange(@NonNull Integer sortOrder) {
/* 23 */     this.mFilterMode.setValue(sortOrder);
/*    */   }
/*    */   
/*    */   public Integer getFilterMode() {
/* 27 */     return (Integer)this.mFilterMode.getValue();
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements ViewModelProvider.Factory {
/*    */     private Integer mFilterMode;
/*    */     
/*    */     public Factory(Integer filterMode) {
/* 35 */       this.mFilterMode = filterMode;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     @NonNull
/*    */     public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
/* 42 */       if (modelClass.isAssignableFrom(ThumbnailsViewFilterMode.class)) {
/* 43 */         return (T)new ThumbnailsViewFilterMode(this.mFilterMode);
/*    */       }
/* 45 */       throw new IllegalArgumentException("Unknown ViewModel class");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\ThumbnailsViewFilterMode.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */