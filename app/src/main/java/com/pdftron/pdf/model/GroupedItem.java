/*    */ package com.pdftron.pdf.model;
/*    */ 
/*    */ import com.pdftron.pdf.config.ToolConfig;
/*    */ import com.pdftron.pdf.controls.AnnotationToolbar;
/*    */ import com.pdftron.pdf.tools.ToolManager;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class GroupedItem
/*    */ {
/*    */   private AnnotationToolbar annotationToolbar;
/*    */   private String mPrefKey;
/*    */   private int[] mAnnotTypes;
/*    */   
/*    */   public GroupedItem(AnnotationToolbar annotationToolbar, String prefKey, int[] annotTypes) {
/* 15 */     this.annotationToolbar = annotationToolbar;
/* 16 */     this.mPrefKey = prefKey;
/* 17 */     this.mAnnotTypes = annotTypes;
/*    */   }
/*    */   
/*    */   public ArrayList<Integer> getButtonIds() {
/* 21 */     ArrayList<Integer> buttonIds = new ArrayList<>();
/* 22 */     for (int annotType : this.mAnnotTypes) {
/* 23 */       buttonIds.add(Integer.valueOf(this.annotationToolbar.getButtonIdFromAnnotType(annotType)));
/*    */     }
/* 25 */     return buttonIds;
/*    */   }
/*    */   
/*    */   public String getPrefKey() {
/* 29 */     return this.mPrefKey;
/*    */   }
/*    */   
/*    */   public ArrayList<Integer> getAvailableAnnotTypes() {
/* 33 */     ArrayList<Integer> result = new ArrayList<>();
/* 34 */     for (int annotType : this.mAnnotTypes) {
/* 35 */       int buttonId = this.annotationToolbar.getButtonIdFromAnnotType(annotType);
/* 36 */       ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(buttonId);
/* 37 */       if (this.annotationToolbar.getToolManager() != null && !this.annotationToolbar.getToolManager().isToolModeDisabled(toolMode)) {
/* 38 */         result.add(Integer.valueOf(annotType));
/*    */       }
/*    */     } 
/* 41 */     return result;
/*    */   }
/*    */   
/*    */   public int getVisibleButtonId() {
/* 45 */     if (this.annotationToolbar.getVisibleAnnotTypeMap() != null && this.annotationToolbar.getVisibleAnnotTypeMap().containsKey(this.mPrefKey)) {
/* 46 */       int annotType = ((Integer)this.annotationToolbar.getVisibleAnnotTypeMap().get(this.mPrefKey)).intValue();
/* 47 */       int buttonId = this.annotationToolbar.getButtonIdFromAnnotType(annotType);
/* 48 */       ToolManager.ToolMode toolMode = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(buttonId);
/* 49 */       if (!this.annotationToolbar.getToolManager().isToolModeDisabled(toolMode)) {
/* 50 */         return buttonId;
/*    */       }
/*    */     } 
/* 53 */     ArrayList<Integer> annotTypes = getAvailableAnnotTypes();
/* 54 */     if (annotTypes == null || annotTypes.isEmpty()) {
/* 55 */       return -1;
/*    */     }
/* 57 */     if (!this.annotationToolbar.hasAllTool() && this.annotationToolbar.getToolManager().hasAnnotToolbarPrecedence()) {
/* 58 */       return findPreferredButton();
/*    */     }
/* 60 */     int firstAnnotType = ((Integer)annotTypes.get(0)).intValue();
/* 61 */     return this.annotationToolbar.getButtonIdFromAnnotType(firstAnnotType);
/*    */   }
/*    */ 
/*    */   
/*    */   private int findPreferredButton() {
/* 66 */     for (GroupedItem item : this.annotationToolbar.getGroupItems()) {
/* 67 */       if (item.getPrefKey().equals(this.mPrefKey)) {
/* 68 */         for (Integer aType : item.getAvailableAnnotTypes()) {
/* 69 */           int bId = this.annotationToolbar.getButtonIdFromAnnotType(aType.intValue());
/* 70 */           ToolManager.ToolMode tm = ToolConfig.getInstance().getToolModeByAnnotationToolbarItemId(bId);
/* 71 */           if (this.annotationToolbar.getToolManager().getAnnotToolbarPrecedence().contains(tm)) {
/* 72 */             return bId;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/* 77 */     return -1;
/*    */   }
/*    */   
/*    */   public boolean contains(int annotType) {
/* 81 */     for (int type : this.mAnnotTypes) {
/* 82 */       if (type == annotType) {
/* 83 */         return true;
/*    */       }
/*    */     } 
/* 86 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\GroupedItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */