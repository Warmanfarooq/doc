/*    */ package com.pdftron.pdf.utils;
/*    */ 
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
/*    */ public class Event<T>
/*    */ {
/*    */   private T mContent;
/*    */   private boolean hasBeenHandled = false;
/*    */   
/*    */   public Event(@NonNull T content) {
/* 19 */     if (content == null) {
/* 20 */       throw new IllegalArgumentException("Null values in Event are not allowed.");
/*    */     }
/* 22 */     this.mContent = content;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public T getContentIfNotHandled() {
/* 27 */     if (this.hasBeenHandled) {
/* 28 */       return null;
/*    */     }
/* 30 */     this.hasBeenHandled = true;
/* 31 */     return this.mContent;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasBeenHandled() {
/* 36 */     return this.hasBeenHandled;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\Event.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */