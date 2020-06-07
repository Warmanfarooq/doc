/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.graphics.drawable.Drawable;
/*    */ import android.os.Bundle;
/*    */ import androidx.annotation.MenuRes;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DialogFragmentTab
/*    */ {
/*    */   public Class<?> _class;
/*    */   public String tabTag;
/*    */   @Nullable
/*    */   public Drawable tabIcon;
/*    */   @Nullable
/*    */   public String tabText;
/*    */   @Nullable
/*    */   public String toolbarTitle;
/*    */   @Nullable
/*    */   public Bundle bundle;
/*    */   @MenuRes
/*    */   public int menuResId;
/*    */   
/*    */   public DialogFragmentTab(@NonNull Class<?> _class, @NonNull String tabTag) {
/* 70 */     this(_class, tabTag, null, null, null, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DialogFragmentTab(@NonNull Class<?> _class, @NonNull String tabTag, @Nullable Drawable tabIcon, @Nullable String tabText, @Nullable String toolbarTitle, @Nullable Bundle bundle) {
/* 79 */     this(_class, tabTag, tabIcon, tabText, toolbarTitle, bundle, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DialogFragmentTab(@NonNull Class<?> _class, @NonNull String tabTag, @Nullable Drawable tabIcon, @Nullable String tabText, @Nullable String toolbarTitle, @Nullable Bundle bundle, @MenuRes int menuResId) {
/* 89 */     this._class = _class;
/* 90 */     this.tabTag = tabTag;
/* 91 */     this.tabIcon = tabIcon;
/* 92 */     this.tabText = tabText;
/* 93 */     this.toolbarTitle = toolbarTitle;
/* 94 */     this.bundle = bundle;
/* 95 */     this.menuResId = menuResId;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\DialogFragmentTab.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */