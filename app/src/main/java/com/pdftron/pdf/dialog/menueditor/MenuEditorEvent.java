/*    */ package com.pdftron.pdf.dialog.menueditor;
/*    */ 
/*    */ public class MenuEditorEvent
/*    */ {
/*    */   private final Type mType;
/*    */   
/*    */   MenuEditorEvent(Type eventType) {
/*  8 */     this.mType = eventType;
/*    */   }
/*    */   
/*    */   public Type getEventType() {
/* 12 */     return this.mType;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 16 */     RESET;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\MenuEditorEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */