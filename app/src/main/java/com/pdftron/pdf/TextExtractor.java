/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.helpers.BitmapHelper;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextExtractor
/*     */ {
/*     */   public static final int e_no_ligature_exp = 1;
/*     */   public static final int e_no_dup_remove = 2;
/*     */   public static final int e_punct_break = 4;
/*     */   public static final int e_remove_hidden_text = 8;
/*     */   public static final int e_no_invisible_text = 16;
/*     */   public static final int e_words_as_elements = 1;
/*     */   public static final int e_output_bbox = 2;
/*     */   public static final int e_output_style_info = 4;
/*     */   private Object a;
/*  91 */   private long b = TextExtractorCreate();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 101 */     if (this.b != 0L) {
/* 102 */       Destroy(this.b);
/* 103 */       this.b = 0L;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 108 */     destroy();
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
/*     */   public void begin(Page paramPage) {
/* 154 */     Begin(this.b, paramPage.a, 0L, 0);
/* 155 */     this.a = paramPage.b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void begin(Page paramPage, Rect paramRect) {
/* 166 */     long l = 0L;
/* 167 */     if (paramRect != null) l = paramRect.a; 
/* 168 */     Begin(this.b, paramPage.a, l, 0);
/* 169 */     this.a = paramPage.b;
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
/*     */   
/*     */   public void begin(Page paramPage, Rect paramRect, int paramInt) {
/* 182 */     long l = 0L;
/* 183 */     if (paramRect != null) l = paramRect.a; 
/* 184 */     Begin(this.b, paramPage.a, l, paramInt);
/* 185 */     this.a = paramPage.b;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWordCount() {
/* 194 */     return GetWordCount(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsText() {
/* 205 */     return GetAsText(this.b, true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsText(boolean paramBoolean) {
/* 221 */     return GetAsText(this.b, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTextUnderAnnot(Annot paramAnnot) {
/* 230 */     return GetTextUnderAnnot(this.b, paramAnnot.__GetHandle());
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
/*     */   public String getAsXML() {
/* 258 */     return GetAsXML(this.b, 0);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAsXML(int paramInt) {
/* 310 */     return GetAsXML(this.b, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumLines() {
/* 319 */     return GetNumLines(this.b);
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
/*     */   public Line getFirstLine() {
/* 331 */     return new Line(this, GetFirstLine(this.b), this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getRightToLeftLanguage() {
/* 338 */     return GetRightToLeftLanguage(this.b);
/*     */   } static native int LineGetNumWords(long paramLong); static native boolean LineIsSimpleLine(long paramLong); static native long LineGetBBox(long paramLong); static native double[] LineGetQuad(long paramLong); static native long LineGetFirstWord(long paramLong); static native long LineGetWord(long paramLong, int paramInt); static native long LineGetNextLine(long paramLong); static native int LineGetCurrentNum(long paramLong); static native long LineGetStyle(long paramLong); static native int LineGetParagraphID(long paramLong); static native int LineGetFlowID(long paramLong); static native boolean LineEndsWithHyphen(long paramLong); static native boolean LineEquals(long paramLong1, long paramLong2); static native boolean LineIsValid(long paramLong); static native void LineDestroy(long paramLong); static native long TextExtractorCreate(); static native void Destroy(long paramLong); static native void Begin(long paramLong1, long paramLong2, long paramLong3, int paramInt); static native int GetWordCount(long paramLong); static native String GetAsText(long paramLong, boolean paramBoolean); static native String GetAsXML(long paramLong, int paramInt); static native int GetNumLines(long paramLong); static native long GetFirstLine(long paramLong); static native String GetTextUnderAnnot(long paramLong1, long paramLong2); static native boolean GetRightToLeftLanguage(long paramLong); static native void SetRightToLeftLanguage(long paramLong, boolean paramBoolean); static native long StyleGetFont(long paramLong); static native String StyleGetFontName(long paramLong); static native double StyleGetFontSize(long paramLong);
/*     */   static native int StyleGetWeight(long paramLong);
/*     */   static native boolean StyleIsItalic(long paramLong);
/*     */   static native boolean StyleIsSerif(long paramLong);
/*     */   static native int[] StyleGetColor(long paramLong);
/*     */   static native boolean StyleEquals(long paramLong1, long paramLong2);
/*     */   static native void StyleDestroy(long paramLong);
/*     */   static native int WordGetNumGlyphs(long paramLong);
/*     */   public void setRightToLeftLanguage(boolean paramBoolean) {
/* 348 */     SetRightToLeftLanguage(this.b, paramBoolean);
/*     */   } static native long WordGetBBox(long paramLong); static native double[] WordGetQuad(long paramLong); static native double[] WordGetGlyphQuad(long paramLong, int paramInt); static native long WordGetCharStyle(long paramLong, int paramInt); static native long WordGetStyle(long paramLong);
/*     */   static native int WordGetStringLen(long paramLong);
/*     */   static native String WordGetString(long paramLong);
/*     */   static native long WordGetNextWord(long paramLong);
/*     */   static native int WordGetCurrentNum(long paramLong);
/*     */   static native boolean WordEquals(long paramLong1, long paramLong2);
/*     */   static native boolean WordIsValid(long paramLong);
/*     */   static native void WordDestroy(long paramLong);
/*     */   public class Style { Style(TextExtractor this$0, long param1Long, Object param1Object) {
/* 358 */       this.b = param1Long;
/* 359 */       this.a = param1Object;
/*     */     }
/*     */ 
/*     */     
/*     */     private Object a;
/*     */     
/*     */     private long b;
/*     */ 
/*     */     
/*     */     public void destroy() {
/* 369 */       if (this.b != 0L) {
/* 370 */         TextExtractor.StyleDestroy(this.b);
/* 371 */         this.b = 0L;
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 376 */       destroy();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Obj getFont() {
/* 388 */       return Obj.__Create(TextExtractor.StyleGetFont(this.b), this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getFontName() {
/* 397 */       return TextExtractor.StyleGetFontName(this.b);
/*     */     }
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
/*     */     public double getFontSize() {
/* 412 */       return TextExtractor.StyleGetFontSize(this.b);
/*     */     }
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
/*     */     public int getWeight() {
/* 426 */       return TextExtractor.StyleGetWeight(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isItalic() {
/* 437 */       return TextExtractor.StyleIsItalic(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSerif() {
/* 449 */       return TextExtractor.StyleIsSerif(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public byte[] getColor() {
/*     */       int[] arrayOfInt;
/* 459 */       return BitmapHelper.getColor(arrayOfInt = TextExtractor.StyleGetColor(this.b));
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 463 */       if (param1Object != null && param1Object.getClass().equals(getClass())) {
/* 464 */         return TextExtractor.StyleEquals(this.b, ((Style)param1Object).b);
/*     */       }
/* 466 */       return false;
/*     */     } }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public class Word
/*     */   {
/*     */     private Object b;
/*     */ 
/*     */     
/*     */     long a;
/*     */ 
/*     */ 
/*     */     
/*     */     public void destroy() {
/* 482 */       if (this.a != 0L) {
/* 483 */         TextExtractor.WordDestroy(this.a);
/* 484 */         this.a = 0L;
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 489 */       destroy();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNumGlyphs() {
/* 498 */       return TextExtractor.WordGetNumGlyphs(this.a);
/*     */     }
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
/*     */     public Rect getBBox() {
/* 511 */       return new Rect(TextExtractor.WordGetBBox(this.a));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getQuad() {
/* 521 */       return TextExtractor.WordGetQuad(this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getGlyphQuad(int param1Int) {
/* 532 */       return TextExtractor.WordGetGlyphQuad(this.a, param1Int);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Style getCharStyle(int param1Int) {
/* 542 */       return new Style(this.c, TextExtractor.WordGetCharStyle(this.a, param1Int), this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Style getStyle() {
/* 551 */       return new Style(this.c, TextExtractor.WordGetStyle(this.a), this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getStringLen() {
/* 560 */       return TextExtractor.WordGetStringLen(this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getString() {
/* 569 */       return TextExtractor.WordGetString(this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Word getNextWord() {
/* 578 */       return new Word(this.c, TextExtractor.WordGetNextWord(this.a), this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCurrentNum() {
/* 589 */       return TextExtractor.WordGetCurrentNum(this.a);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 593 */       if (param1Object != null && param1Object.getClass().equals(getClass())) {
/* 594 */         return TextExtractor.WordEquals(this.a, ((Word)param1Object).a);
/*     */       }
/* 596 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 605 */       if (this.a != 0L) return TextExtractor.WordIsValid(this.a); 
/* 606 */       return false;
/*     */     }
/*     */     
/*     */     Word(TextExtractor this$0, long param1Long, Object param1Object) {
/* 610 */       this.a = param1Long;
/* 611 */       this.b = param1Object;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public class Line
/*     */   {
/*     */     private Object a;
/*     */ 
/*     */     
/*     */     private long b;
/*     */ 
/*     */ 
/*     */     
/*     */     public void destroy() {
/* 627 */       if (this.b != 0L) {
/* 628 */         TextExtractor.LineDestroy(this.b);
/* 629 */         this.b = 0L;
/*     */       } 
/*     */     }
/*     */     
/*     */     protected void finalize() throws Throwable {
/* 634 */       destroy();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getNumWords() {
/* 643 */       return TextExtractor.LineGetNumWords(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isSimpleLine() {
/* 653 */       return TextExtractor.LineIsSimpleLine(this.b);
/*     */     }
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
/*     */     public Rect getBBox() {
/* 666 */       return new Rect(TextExtractor.LineGetBBox(this.b));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getQuad() {
/* 676 */       return TextExtractor.LineGetQuad(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Word getFirstWord() {
/* 687 */       return new Word(this.c, TextExtractor.LineGetFirstWord(this.b), this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Word getWord(int param1Int) {
/* 697 */       return new Word(this.c, TextExtractor.LineGetWord(this.b, param1Int), this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Line getNextLine() {
/* 706 */       return new Line(this.c, TextExtractor.LineGetNextLine(this.b), this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getCurrentNum() {
/* 715 */       return TextExtractor.LineGetCurrentNum(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Style getStyle() {
/* 724 */       return new Style(this.c, TextExtractor.LineGetStyle(this.b), this.a);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getParagraphID() {
/* 735 */       return TextExtractor.LineGetParagraphID(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getFlowID() {
/* 746 */       return TextExtractor.LineGetFlowID(this.b);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean endsWithHyphen() {
/* 755 */       return TextExtractor.LineEndsWithHyphen(this.b);
/*     */     }
/*     */     
/*     */     public boolean equals(Object param1Object) {
/* 759 */       if (param1Object != null && param1Object.getClass().equals(getClass())) {
/* 760 */         return TextExtractor.LineEquals(this.b, ((Word)param1Object).a);
/*     */       }
/* 762 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 771 */       if (this.b != 0L) return TextExtractor.LineIsValid(this.b); 
/* 772 */       return false;
/*     */     }
/*     */     
/*     */     Line(TextExtractor this$0, long param1Long, Object param1Object) {
/* 776 */       this.b = param1Long;
/* 777 */       this.a = param1Object;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Compat
/*     */   {
/*     */     public static void SetRightToLeftLanguage(long param1Long, boolean param1Boolean) {
/* 790 */       TextExtractor.SetRightToLeftLanguage(param1Long, param1Boolean);
/*     */     }
/*     */     public static boolean GetRightToLeftLanguage(long param1Long) {
/* 793 */       return TextExtractor.GetRightToLeftLanguage(param1Long);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\TextExtractor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */