/*     */ package com.rarepebble.colorpicker;
/*     */ 
/*     */ import android.text.Editable;
/*     */ import android.text.InputFilter;
/*     */ import android.text.Spanned;
/*     */ import android.text.TextWatcher;
/*     */ import android.widget.EditText;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class HexEdit
/*     */ {
/*  27 */   private static InputFilter[] withoutAlphaDigits = new InputFilter[] { new ColorPasteLengthFilter() };
/*  28 */   private static InputFilter[] withAlphaDigits = new InputFilter[] { (InputFilter)new InputFilter.LengthFilter(8) };
/*     */ 
/*     */   
/*     */   public static void setUpListeners(final EditText hexEdit, final ObservableColor observableColor) {
/*     */     class MultiObserver
/*     */       implements ObservableColor.Observer, TextWatcher
/*     */     {
/*     */       public void updateColor(ObservableColor observableColor) {
/*  36 */         String colorString = formatColor(observableColor.getColor());
/*     */         
/*  38 */         hexEdit.removeTextChangedListener(this);
/*  39 */         hexEdit.setText(colorString);
/*  40 */         hexEdit.addTextChangedListener(this);
/*     */       }
/*     */       
/*     */       private String formatColor(int color) {
/*  44 */         return shouldTrimAlphaDigits() ? 
/*  45 */           String.format("%06x", new Object[] { Integer.valueOf(color & 0xFFFFFF)
/*  46 */             }) : String.format("%08x", new Object[] { Integer.valueOf(color) });
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */       
/*     */       public void onTextChanged(CharSequence s, int start, int before, int count) {
/*     */         try {
/*  56 */           int color = (int)(Long.parseLong(s.toString(), 16) & 0xFFFFFFFFFFFFFFFFL);
/*  57 */           if (shouldTrimAlphaDigits()) color |= 0xFF000000; 
/*  58 */           observableColor.updateColor(color, this);
/*     */         }
/*  60 */         catch (NumberFormatException e) {
/*  61 */           observableColor.updateColor(0, this);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/*     */       public void afterTextChanged(Editable s) {}
/*     */ 
/*     */       
/*     */       private boolean shouldTrimAlphaDigits() {
/*  70 */         return (hexEdit.getFilters() == HexEdit.withoutAlphaDigits);
/*     */       }
/*     */     };
/*     */     
/*  74 */     MultiObserver multiObserver = new MultiObserver();
/*  75 */     hexEdit.addTextChangedListener(multiObserver);
/*  76 */     observableColor.addObserver(multiObserver);
/*  77 */     setShowAlphaDigits(hexEdit, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setShowAlphaDigits(EditText hexEdit, boolean showAlphaDigits) {
/*  82 */     hexEdit.setFilters(showAlphaDigits ? withAlphaDigits : withoutAlphaDigits);
/*  83 */     hexEdit.setText((CharSequence)hexEdit.getText());
/*     */   }
/*     */   
/*     */   private static class ColorPasteLengthFilter
/*     */     implements InputFilter
/*     */   {
/*     */     private static final int MAX_LENGTH = 6;
/*     */     private static final int PASTED_LEN = 8;
/*  91 */     private final InputFilter sixDigitFilter = (InputFilter)new LengthFilter(6);
/*     */ 
/*     */ 
/*     */     
/*     */     public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
/*  96 */       int srcLength = end - start;
/*  97 */       int dstSelLength = dend - dstart;
/*  98 */       if (srcLength == 8 && dstSelLength == dest.length())
/*     */       {
/* 100 */         return source.subSequence(2, 8);
/*     */       }
/*     */       
/* 103 */       return this.sixDigitFilter.filter(source, start, end, dest, dstart, dend);
/*     */     }
/*     */     
/*     */     private ColorPasteLengthFilter() {}
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\rarepebble\colorpicker\HexEdit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */