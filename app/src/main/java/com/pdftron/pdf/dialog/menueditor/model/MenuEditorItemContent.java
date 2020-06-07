/*    */ package com.pdftron.pdf.dialog.menueditor.model;
/*    */ 
/*    */ import android.graphics.drawable.Drawable;
/*    */ import androidx.annotation.DrawableRes;
/*    */ import androidx.annotation.IdRes;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ 
/*    */ public class MenuEditorItemContent
/*    */   implements MenuEditorItem
/*    */ {
/*    */   @IdRes
/*    */   private int mId;
/*    */   private String mTitle;
/*    */   
/*    */   public MenuEditorItemContent(@IdRes int id, @NonNull String title, @DrawableRes int iconRes) {
/* 17 */     this.mId = id;
/* 18 */     this.mTitle = title;
/* 19 */     this.mIconRes = iconRes;
/*    */   } @DrawableRes
/*    */   private int mIconRes; private Drawable mDrawable;
/*    */   public MenuEditorItemContent(@IdRes int id, @NonNull String title, Drawable drawable) {
/* 23 */     this.mId = id;
/* 24 */     this.mTitle = title;
/* 25 */     this.mDrawable = drawable;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHeader() {
/* 30 */     return false;
/*    */   }
/*    */   @IdRes
/*    */   public int getId() {
/* 34 */     return this.mId;
/*    */   }
/*    */   
/*    */   public String getTitle() {
/* 38 */     return this.mTitle;
/*    */   }
/*    */   @DrawableRes
/*    */   public int getIconRes() {
/* 42 */     return this.mIconRes;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public Drawable getDrawable() {
/* 47 */     return this.mDrawable;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\model\MenuEditorItemContent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */