/*    */ package com.pdftron.pdf.widget.richtext;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import jp.wasabeef.richeditor.RichEditor;
/*    */ 
/*    */ public class PTRichEditor
/*    */   extends RichEditor
/*    */ {
/*    */   private int mFontSize;
/*    */   
/*    */   public PTRichEditor(Context context) {
/* 13 */     super(context);
/*    */   }
/*    */   
/*    */   public PTRichEditor(Context context, AttributeSet attrs) {
/* 17 */     super(context, attrs);
/*    */   }
/*    */   
/*    */   public PTRichEditor(Context context, AttributeSet attrs, int defStyleAttr) {
/* 21 */     super(context, attrs, defStyleAttr);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEditorFontSize(int px) {
/* 26 */     super.setEditorFontSize(px);
/* 27 */     this.mFontSize = px;
/*    */   }
/*    */   
/*    */   public int getEditorFontSize() {
/* 31 */     return this.mFontSize;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\richtext\PTRichEditor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */