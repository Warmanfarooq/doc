/*    */ package com.pdftron.pdf.dialog.menueditor.model;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.annotation.StringRes;
/*    */ 
/*    */ public class MenuEditorItemHeader
/*    */   implements MenuEditorItem
/*    */ {
/*    */   private String mTitle;
/*    */   @StringRes
/*    */   private int mTitleId;
/*    */   private String mDescription;
/*    */   @StringRes
/*    */   private int mDescriptionId;
/*    */   private int mGroup;
/*    */   private String mDraggingTitle;
/*    */   @StringRes
/*    */   private int mDraggingTitleId;
/*    */   
/*    */   public MenuEditorItemHeader(int group, @NonNull String title, @Nullable String description) {
/* 22 */     this.mGroup = group;
/* 23 */     this.mTitle = title;
/* 24 */     this.mDescription = description;
/*    */   }
/*    */   
/*    */   public MenuEditorItemHeader(int group, @StringRes int titleId, @StringRes int descriptionId) {
/* 28 */     this.mGroup = group;
/* 29 */     this.mTitleId = titleId;
/* 30 */     this.mDescriptionId = descriptionId;
/*    */   }
/*    */   
/*    */   public void setDraggingTitle(@NonNull String title) {
/* 34 */     this.mDraggingTitle = title;
/*    */   }
/*    */   
/*    */   public void setDraggingTitleId(@StringRes int titleId) {
/* 38 */     this.mDraggingTitleId = titleId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHeader() {
/* 43 */     return true;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getTitle() {
/* 48 */     return this.mTitle;
/*    */   }
/*    */   
/*    */   @StringRes
/*    */   public int getTitleId() {
/* 53 */     return this.mTitleId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getDescription() {
/* 58 */     return this.mDescription;
/*    */   }
/*    */   
/*    */   @StringRes
/*    */   public int getDescriptionId() {
/* 63 */     return this.mDescriptionId;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getDraggingTitle() {
/* 68 */     return this.mDraggingTitle;
/*    */   }
/*    */   
/*    */   @StringRes
/*    */   public int getDraggingTitleId() {
/* 73 */     return this.mDraggingTitleId;
/*    */   }
/*    */   
/*    */   public int getGroup() {
/* 77 */     return this.mGroup;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\model\MenuEditorItemHeader.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */