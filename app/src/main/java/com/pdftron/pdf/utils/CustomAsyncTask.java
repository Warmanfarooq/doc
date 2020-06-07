/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.app.Activity;
/*    */ import android.content.Context;
/*    */ import android.os.AsyncTask;
/*    */ import androidx.annotation.Nullable;
/*    */ import java.lang.ref.WeakReference;
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
/*    */ public abstract class CustomAsyncTask<Params, Progress, Result>
/*    */   extends AsyncTask<Params, Progress, Result>
/*    */ {
/*    */   private WeakReference<Context> mContext;
/*    */   
/*    */   public CustomAsyncTask(Context context) {
/* 25 */     this.mContext = new WeakReference<>(context);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   protected Context getContext() {
/* 33 */     Context context = this.mContext.get();
/* 34 */     if (context instanceof Activity && ((Activity)context).isFinishing()) {
/* 35 */       context = null;
/*    */     }
/* 37 */     return context;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\CustomAsyncTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */