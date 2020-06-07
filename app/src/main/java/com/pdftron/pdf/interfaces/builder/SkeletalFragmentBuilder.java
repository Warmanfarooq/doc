/*    */ package com.pdftron.pdf.interfaces.builder;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.os.Bundle;
/*    */ import android.os.Parcelable;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.fragment.app.Fragment;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class SkeletalFragmentBuilder<E extends Fragment>
/*    */   implements Builder<E>, Parcelable
/*    */ {
/*    */   public <T extends E> T build(@NonNull Context context, @NonNull Class<T> clazz) {
/* 26 */     checkArgs(context);
/*    */     
/* 28 */     return (T)Fragment.instantiate(context, clazz.getName(), createBundle(context));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E build(@NonNull Context context) {
/* 39 */     return null;
/*    */   }
/*    */   
/*    */   public abstract Bundle createBundle(@NonNull Context paramContext);
/*    */   
/*    */   public abstract void checkArgs(@NonNull Context paramContext);
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\interfaces\builder\SkeletalFragmentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */