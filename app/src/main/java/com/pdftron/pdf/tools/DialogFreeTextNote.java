/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.PorterDuff;
/*    */ import android.text.TextWatcher;
/*    */ import android.view.View;
/*    */ import android.view.inputmethod.InputMethodManager;
/*    */ import android.widget.ImageButton;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.core.content.ContextCompat;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DialogFreeTextNote
/*    */   extends DialogAnnotNote
/*    */ {
/*    */   private ImageButton mToggleButton;
/*    */   
/*    */   public DialogFreeTextNote(@NonNull PDFViewCtrl pdfViewCtrl, String note, boolean enablePositiveButton) {
/* 33 */     super(pdfViewCtrl, note);
/*    */     
/* 35 */     this.mPositiveButton.setEnabled(enablePositiveButton);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initTextBox(String note) {
/* 43 */     if (!note.equals("")) {
/* 44 */       this.mTextBox.setText(note);
/*    */       
/* 46 */       this.mTextBox.setSelection(this.mTextBox.getText().length());
/*    */     } 
/*    */     
/* 49 */     this.mTextBox.addTextChangedListener(this.textWatcher);
/* 50 */     this.mTextBox.setHint(getContext().getString(R.string.tools_dialog_annotation_popup_text_hint));
/*    */ 
/*    */     
/* 53 */     this.mToggleButton = (ImageButton)this.mMainView.findViewById(R.id.tools_dialog_annotation_popup_button_style);
/* 54 */     this.mToggleButton.setVisibility(0);
/* 55 */     this.mToggleButton.setImageResource(R.drawable.ic_call_received_black_24dp);
/* 56 */     this.mToggleButton.getDrawable().mutate().setColorFilter(Utils.getAccentColor(getContext()), PorterDuff.Mode.SRC_ATOP);
/* 57 */     this.mToggleButton.setOnClickListener(new View.OnClickListener()
/*    */         {
/*    */           public void onClick(View v) {
/* 60 */             DialogFreeTextNote.this.setButtonPressed(-3);
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void disableToggleButton() {
/* 69 */     this.mToggleButton.setEnabled(false);
/* 70 */     this.mToggleButton.getDrawable().mutate().setColorFilter(ContextCompat.getColor(getContext(), R.color.tools_gray), PorterDuff.Mode.SRC_ATOP);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onStop() {
/* 78 */     super.onStop();
/* 79 */     InputMethodManager imm = (InputMethodManager)getContext().getSystemService("input_method");
/* 80 */     if (imm != null) {
/* 81 */       imm.hideSoftInputFromWindow(this.mTextBox.getWindowToken(), 0);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addTextWatcher(TextWatcher textWatcherListener) {
/* 92 */     this.mTextBox.addTextChangedListener(textWatcherListener);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogFreeTextNote.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */