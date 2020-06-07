/*    */ package com.pdftron.pdf.viewmodel;
/*    */ 
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.pdf.model.AnnotStyle;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import jp.wasabeef.richeditor.RichEditor;
/*    */ 
/*    */ public class RichTextEvent
/*    */ {
/*    */   @NonNull
/*    */   private final Type mType;
/*    */   @Nullable
/*    */   private AnnotStyle mAnnotStyle;
/*    */   @NonNull
/* 17 */   private List<RichEditor.Type> mDecorationTypes = new ArrayList<>();
/*    */ 
/*    */   
/*    */   RichTextEvent(@NonNull Type eventType) {
/* 21 */     this.mType = eventType;
/*    */   }
/*    */   
/*    */   RichTextEvent(@NonNull Type eventType, @Nullable AnnotStyle annotStyle) {
/* 25 */     this.mType = eventType;
/* 26 */     this.mAnnotStyle = annotStyle;
/*    */   }
/*    */   
/*    */   RichTextEvent(@NonNull Type eventType, @Nullable List<RichEditor.Type> types) {
/* 30 */     this.mType = eventType;
/* 31 */     this.mDecorationTypes.clear();
/* 32 */     if (types != null) {
/* 33 */       this.mDecorationTypes.addAll(types);
/*    */     }
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public Type getEventType() {
/* 39 */     return this.mType;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public AnnotStyle getAnnotStyle() {
/* 44 */     return this.mAnnotStyle;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public List<RichEditor.Type> getDecorationTypes() {
/* 49 */     return this.mDecorationTypes;
/*    */   }
/*    */   
/*    */   public enum Type {
/* 53 */     OPEN_TOOLBAR,
/* 54 */     CLOSE_TOOLBAR,
/* 55 */     UPDATE_TOOLBAR,
/* 56 */     SHOW_KEYBOARD,
/* 57 */     HIDE_KEYBOARD,
/* 58 */     UNDO,
/* 59 */     REDO,
/* 60 */     TEXT_STYLE,
/* 61 */     BOLD,
/* 62 */     ITALIC,
/* 63 */     STRIKE_THROUGH,
/* 64 */     UNDERLINE,
/* 65 */     INDENT,
/* 66 */     OUTDENT,
/* 67 */     ALIGN_LEFT,
/* 68 */     ALIGN_CENTER,
/* 69 */     ALIGN_RIGHT,
/* 70 */     BULLETS,
/* 71 */     NUMBERS,
/* 72 */     BLOCK_QUOTE;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\viewmodel\RichTextEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */