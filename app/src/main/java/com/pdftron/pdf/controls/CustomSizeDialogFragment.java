/*    */ package com.pdftron.pdf.controls;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.Configuration;
/*    */ import android.view.View;
/*    */ import android.view.Window;
/*    */ import android.view.WindowManager;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.fragment.app.DialogFragment;
/*    */ import com.pdftron.pdf.utils.Utils;
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
/*    */ public abstract class CustomSizeDialogFragment
/*    */   extends DialogFragment
/*    */ {
/* 24 */   protected int mWidth = 500;
/* 25 */   protected int mHeight = -1;
/*    */ 
/*    */   
/*    */   public void onStart() {
/* 29 */     super.onStart();
/* 30 */     setDialogSize();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onConfigurationChanged(Configuration newConfig) {
/* 35 */     super.onConfigurationChanged(newConfig);
/* 36 */     setDialogSize();
/*    */   }
/*    */   
/*    */   private void setDialogSize() {
/* 40 */     View view = getView();
/* 41 */     Window window = getDialog().getWindow();
/* 42 */     if (view == null || window == null) {
/*    */       return;
/*    */     }
/*    */     
/* 46 */     Context context = view.getContext();
/*    */     
/* 48 */     int marginWidth = (int)Utils.convDp2Pix(context, 600.0F);
/* 49 */     if (Utils.isTablet(context) && Utils.getScreenWidth(context) > marginWidth) {
/* 50 */       dimBackground(window);
/*    */       
/* 52 */       int width = (int)Utils.convDp2Pix(context, this.mWidth);
/* 53 */       int height = Utils.getScreenHeight(context);
/* 54 */       if (this.mHeight > 0) {
/* 55 */         window.setLayout(width, (int)Utils.convDp2Pix(context, this.mHeight));
/*    */       } else {
/* 57 */         int dh = (int)Utils.convDp2Pix(context, 100.0F);
/* 58 */         window.setLayout(width, height - dh);
/*    */       }
/*    */     
/* 61 */     } else if (this.mHeight > 0) {
/* 62 */       dimBackground(window);
/* 63 */       int width = (int)(Utils.getScreenWidth(context) * 0.9D);
/* 64 */       window.setLayout(width, (int)Utils.convDp2Pix(context, this.mHeight));
/*    */     } else {
/* 66 */       window.setLayout(-1, -1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void dimBackground(@NonNull Window window) {
/* 72 */     WindowManager.LayoutParams windowParams = window.getAttributes();
/* 73 */     windowParams.dimAmount = 0.6F;
/* 74 */     windowParams.flags |= 0x2;
/* 75 */     window.setAttributes(windowParams);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\CustomSizeDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */