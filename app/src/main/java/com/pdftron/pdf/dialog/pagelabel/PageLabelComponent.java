/*     */ package com.pdftron.pdf.dialog.pagelabel;
/*     */ 
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ class PageLabelComponent
/*     */   implements PageLabelView.PageLabelSettingChangeListener
/*     */ {
/*     */   private final PageLabelView mPageLabelDialogView;
/*     */   private final PageLabelSettingViewModel mPageLabelViewModel;
/*     */   private final DialogButtonInteractionListener mListener;
/*     */   
/*     */   PageLabelComponent(@NonNull FragmentActivity activity, @NonNull ViewGroup parent, int selectedPage, int numPages, @NonNull DialogButtonInteractionListener listener) {
/*  20 */     this(activity, parent, new PageLabelSetting(selectedPage, numPages), listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PageLabelComponent(@NonNull FragmentActivity activity, @NonNull ViewGroup parent, int fromPage, int toPage, int numPages, @NonNull DialogButtonInteractionListener listener) {
/*  30 */     this(activity, parent, new PageLabelSetting(fromPage, toPage, numPages), listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PageLabelComponent(@NonNull FragmentActivity activity, @NonNull ViewGroup parent, int fromPage, int toPage, int numPages, @NonNull String prefix, @NonNull DialogButtonInteractionListener listener) {
/*  41 */     this(activity, parent, new PageLabelSetting(fromPage, toPage, numPages, prefix), listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PageLabelComponent(@NonNull FragmentActivity activity, @NonNull ViewGroup parent, @NonNull PageLabelSetting pageLabelSetting, @NonNull DialogButtonInteractionListener listener) {
/*  49 */     this.mListener = listener;
/*  50 */     this.mPageLabelViewModel = (PageLabelSettingViewModel)ViewModelProviders.of(activity).get(PageLabelSettingViewModel.class);
/*  51 */     this.mPageLabelViewModel.set(pageLabelSetting);
/*  52 */     this.mPageLabelDialogView = new PageLabelDialogView(parent, this);
/*  53 */     this.mPageLabelDialogView.initViewStates(pageLabelSetting);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAll(boolean isAll) {
/*  58 */     this.mPageLabelViewModel.get().setAll(isAll);
/*  59 */     updateViewValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelectedPage(boolean isSelected) {
/*  64 */     this.mPageLabelViewModel.get().setSelectedPage(isSelected);
/*  65 */     updateViewValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPageRange(@NonNull String fromText, @NonNull String toText) {
/*     */     try {
/*  71 */       int from = Integer.valueOf(fromText).intValue();
/*  72 */       int to = Integer.valueOf(toText).intValue();
/*  73 */       this.mPageLabelViewModel.get().setToPage(to);
/*  74 */       this.mPageLabelViewModel.get().setFromPage(from);
/*  75 */       updateViewValidity();
/*  76 */     } catch (NumberFormatException e) {
/*  77 */       this.mListener.disallowSave();
/*  78 */       this.mPageLabelDialogView.invalidToPage(false);
/*  79 */       this.mPageLabelDialogView.invalidFromPage(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStyle(@NonNull PageLabelSetting.PageLabelStyle style) {
/*  85 */     this.mPageLabelViewModel.get().setStyle(style);
/*  86 */     updatePreview();
/*  87 */     updateViewValidity();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrefix(@NonNull String prefix) {
/*  92 */     this.mPageLabelViewModel.get().setPrefix(prefix);
/*  93 */     updatePreview();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStartNumber(@NonNull String startStr) {
/*     */     try {
/* 100 */       int start = Integer.valueOf(startStr).intValue();
/* 101 */       this.mPageLabelViewModel.get().setStartNum(start);
/* 102 */       updateViewValidity();
/* 103 */       updatePreview();
/* 104 */     } catch (NumberFormatException e) {
/* 105 */       this.mListener.disallowSave();
/* 106 */       this.mPageLabelDialogView.invalidStartNumber(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void completeSettings() {
/*     */     int fromPage, toPage;
/* 113 */     if (this.mPageLabelViewModel.get().isAll()) {
/* 114 */       fromPage = 1;
/* 115 */       toPage = (this.mPageLabelViewModel.get()).numPages;
/* 116 */     } else if (this.mPageLabelViewModel.get().isSelectedPage()) {
/* 117 */       fromPage = (this.mPageLabelViewModel.get()).selectedPage;
/* 118 */       toPage = (this.mPageLabelViewModel.get()).selectedPage;
/*     */     } else {
/* 120 */       fromPage = this.mPageLabelViewModel.get().getFromPage();
/* 121 */       toPage = this.mPageLabelViewModel.get().getToPage();
/*     */     } 
/*     */     
/* 124 */     this.mPageLabelViewModel.get().setFromPage(fromPage);
/* 125 */     this.mPageLabelViewModel.get().setToPage(toPage);
/*     */     
/* 127 */     this.mPageLabelViewModel.complete();
/*     */   }
/*     */   
/*     */   private void updatePreview() {
/* 131 */     String preview = "";
/* 132 */     PageLabelSetting.PageLabelStyle style = this.mPageLabelViewModel.get().getStyle();
/* 133 */     String prefix = this.mPageLabelViewModel.get().getPrefix();
/* 134 */     int startNum = this.mPageLabelViewModel.get().getStartNum();
/* 135 */     switch (style) {
/*     */       case NONE:
/* 137 */         preview = String.format("%1$s, %1$s, %1$s, ...", new Object[] { prefix });
/*     */         break;
/*     */       case ROMAN_UPPER:
/* 140 */         preview = String.format(getRomanPreviewFormat(startNum, false), new Object[] { prefix });
/*     */         break;
/*     */       case ROMAN_LOWER:
/* 143 */         preview = String.format(getRomanPreviewFormat(startNum, true), new Object[] { prefix });
/*     */         break;
/*     */       case ALPHA_UPPER:
/* 146 */         preview = String.format(getAlphabeticPreviewFormat(startNum, false), new Object[] { prefix });
/*     */         break;
/*     */       case ALPHA_LOWER:
/* 149 */         preview = String.format(getAlphabeticPreviewFormat(startNum, true), new Object[] { prefix });
/*     */         break;
/*     */       case DECIMAL:
/* 152 */         preview = String.format("%1$s%2$d, %1$s%3$d, %1$s%4$d, ...", new Object[] { prefix, Integer.valueOf(startNum), Integer.valueOf(startNum + 1), Integer.valueOf(startNum + 2) });
/*     */         break;
/*     */     } 
/*     */     
/* 156 */     this.mPageLabelDialogView.updatePreview(preview);
/*     */   }
/*     */   
/*     */   private void updateViewValidity() {
/* 160 */     int numPages = (this.mPageLabelViewModel.get()).numPages;
/* 161 */     int to = this.mPageLabelViewModel.get().getToPage();
/* 162 */     int from = this.mPageLabelViewModel.get().getFromPage();
/* 163 */     int startNum = this.mPageLabelViewModel.get().getStartNum();
/*     */ 
/*     */ 
/*     */     
/* 167 */     boolean isFromPageCorrect = (this.mPageLabelViewModel.get().isAll() || this.mPageLabelViewModel.get().isSelectedPage() || (from <= to && from >= 1 && from <= numPages));
/*     */ 
/*     */     
/* 170 */     boolean isToPageCorrect = (this.mPageLabelViewModel.get().isAll() || this.mPageLabelViewModel.get().isSelectedPage() || (from <= to && to >= 1 && to <= numPages));
/*     */ 
/*     */     
/* 173 */     this.mPageLabelDialogView.invalidToPage(isToPageCorrect);
/* 174 */     this.mPageLabelDialogView.invalidFromPage(isFromPageCorrect);
/*     */ 
/*     */     
/* 177 */     boolean isStartingNumberValid = (this.mPageLabelViewModel.get().getStyle() == PageLabelSetting.PageLabelStyle.NONE || startNum >= 1);
/*     */     
/* 179 */     if (isStartingNumberValid) {
/* 180 */       this.mPageLabelDialogView.invalidStartNumber(true);
/*     */     } else {
/* 182 */       this.mPageLabelDialogView.invalidStartNumber(false);
/*     */     } 
/*     */ 
/*     */     
/* 186 */     if (isFromPageCorrect && isToPageCorrect && isStartingNumberValid) {
/* 187 */       this.mListener.allowSave();
/*     */     } else {
/* 189 */       this.mListener.disallowSave();
/*     */     } 
/*     */   }
/*     */   
/*     */   private String getRomanPreviewFormat(int startNum, boolean isLowercase) {
/* 194 */     String first = toLowerCase(PageLabelNumber.toRoman(startNum), isLowercase);
/* 195 */     String second = toLowerCase(PageLabelNumber.toRoman(startNum + 1), isLowercase);
/* 196 */     String third = toLowerCase(PageLabelNumber.toRoman(startNum + 2), isLowercase);
/*     */     
/* 198 */     return "%1$s" + first + ", " + "%1$s" + second + ", " + "%1$s" + third + ", ...";
/*     */   }
/*     */   
/*     */   private String getAlphabeticPreviewFormat(int startNum, boolean isLowercase) {
/* 202 */     String first = toLowerCase(PageLabelNumber.toAlphabetic(startNum), isLowercase);
/* 203 */     String second = toLowerCase(PageLabelNumber.toAlphabetic(startNum + 1), isLowercase);
/* 204 */     String third = toLowerCase(PageLabelNumber.toAlphabetic(startNum + 2), isLowercase);
/*     */     
/* 206 */     return "%1$s" + first + ", " + "%1$s" + second + ", " + "%1$s" + third + ", ...";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String toLowerCase(String str, boolean shouldLowercase) {
/* 218 */     return shouldLowercase ? str.toLowerCase() : str.toUpperCase();
/*     */   }
/*     */   
/*     */   static interface DialogButtonInteractionListener {
/*     */     void disallowSave();
/*     */     
/*     */     void allowSave(); }
/*     */   
/*     */   private static class PageLabelNumber {
/* 227 */     private static final TreeMap<Integer, String> map = new TreeMap<>();
/* 228 */     static char[] alpha = "abcdefghijklmnopqrstuvwxyz".toCharArray();
/*     */     
/*     */     static {
/* 231 */       map.put(Integer.valueOf(1000), "M");
/* 232 */       map.put(Integer.valueOf(900), "CM");
/* 233 */       map.put(Integer.valueOf(500), "D");
/* 234 */       map.put(Integer.valueOf(400), "CD");
/* 235 */       map.put(Integer.valueOf(100), "C");
/* 236 */       map.put(Integer.valueOf(90), "XC");
/* 237 */       map.put(Integer.valueOf(50), "L");
/* 238 */       map.put(Integer.valueOf(40), "XL");
/* 239 */       map.put(Integer.valueOf(10), "X");
/* 240 */       map.put(Integer.valueOf(9), "IX");
/* 241 */       map.put(Integer.valueOf(5), "V");
/* 242 */       map.put(Integer.valueOf(4), "IV");
/* 243 */       map.put(Integer.valueOf(1), "I");
/*     */     }
/*     */     
/*     */     static String toRoman(int number) {
/* 247 */       if (number > 40000) {
/* 248 */         return "MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM";
/*     */       }
/* 250 */       int l = ((Integer)map.floorKey(Integer.valueOf(number))).intValue();
/* 251 */       if (number == l) {
/* 252 */         return map.get(Integer.valueOf(number));
/*     */       }
/* 254 */       return (String)map.get(Integer.valueOf(l)) + toRoman(number - l);
/*     */     }
/*     */ 
/*     */     
/*     */     static String toAlphabetic(int startNum) {
/* 259 */       if (startNum > 0) {
/* 260 */         int times2Repeat = (startNum - 1) / 26 + 1;
/* 261 */         int pos = (startNum - 1) % 26;
/* 262 */         String letter = String.valueOf(alpha[pos]);
/* 263 */         StringBuilder result = new StringBuilder();
/* 264 */         while (times2Repeat > 0) {
/* 265 */           result.append(letter);
/* 266 */           times2Repeat--;
/*     */         } 
/* 268 */         return result.toString();
/*     */       } 
/* 270 */       return "";
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelComponent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */