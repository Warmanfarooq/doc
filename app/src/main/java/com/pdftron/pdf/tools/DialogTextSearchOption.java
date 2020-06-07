/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.app.AlertDialog;
/*    */ import android.content.Context;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.widget.CheckBox;
/*    */ import android.widget.LinearLayout;
/*    */ import androidx.annotation.NonNull;
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
/*    */ public class DialogTextSearchOption
/*    */   extends AlertDialog
/*    */ {
/*    */   private CheckBox mWholeWord;
/*    */   private CheckBox mCaseSensitive;
/*    */   private CheckBox mUseReg;
/*    */   
/*    */   public DialogTextSearchOption(@NonNull Context context) {
/* 29 */     super(context);
/*    */     
/* 31 */     LayoutInflater inflater = (LayoutInflater)context.getSystemService("layout_inflater");
/* 32 */     LinearLayout layout = (LinearLayout)inflater.inflate(R.layout.tools_dialog_textsearch, null);
/*    */     
/* 34 */     this.mCaseSensitive = (CheckBox)layout.findViewById(R.id.tools_dialog_textsearch_case_sensitive);
/* 35 */     this.mWholeWord = (CheckBox)layout.findViewById(R.id.tools_dialog_textsearch_wholeword);
/* 36 */     this.mUseReg = (CheckBox)layout.findViewById(R.id.tools_dialog_textsearch_regex);
/*    */     
/* 38 */     setTitle(context.getString(R.string.tools_dialog_textsearch_title));
/* 39 */     setView((View)layout);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getWholeWord() {
/* 46 */     return this.mWholeWord.isChecked();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getCaseSensitive() {
/* 53 */     return this.mCaseSensitive.isChecked();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean getRegExps() {
/* 60 */     return this.mUseReg.isChecked();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setWholeWord(boolean flag) {
/* 69 */     this.mWholeWord.setChecked(flag);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCaseSensitive(boolean flag) {
/* 78 */     this.mCaseSensitive.setChecked(flag);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRegExps(boolean flag) {
/* 87 */     this.mUseReg.setChecked(flag);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogTextSearchOption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */