/*     */ package com.pdftron.pdf.dialog.pagelabel;
/*     */ 
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class PageLabelSetting
/*     */   implements Parcelable
/*     */ {
/*  13 */   public static final Creator<PageLabelSetting> CREATOR = new Creator<PageLabelSetting>()
/*     */     {
/*     */       public PageLabelSetting createFromParcel(Parcel source) {
/*  16 */         return new PageLabelSetting(source);
/*     */       }
/*     */ 
/*     */       
/*     */       public PageLabelSetting[] newArray(int size) {
/*  21 */         return new PageLabelSetting[size];
/*     */       }
/*     */     };
/*     */   
/*     */   final int selectedPage;
/*     */   final int numPages;
/*     */   private boolean mIsAll = false;
/*     */   private boolean mSelectedPage = true;
/*  29 */   private int mFromPage = 1;
/*  30 */   private int mToPage = 1;
/*  31 */   private PageLabelStyle mStyle = PageLabelStyle.values()[0];
/*  32 */   private String mPrefix = "";
/*  33 */   private int mStartNum = 1;
/*     */   
/*     */   PageLabelSetting(int selectedPage, int numPages) {
/*  36 */     this(selectedPage, selectedPage, numPages);
/*     */   }
/*     */   
/*     */   PageLabelSetting(int fromPage, int toPage, int numPages) {
/*  40 */     this(fromPage, toPage, numPages, "");
/*     */   }
/*     */   
/*     */   PageLabelSetting(int fromPage, int toPage, int numPages, String prefix) {
/*  44 */     this.mFromPage = fromPage;
/*  45 */     this.mToPage = toPage;
/*  46 */     this.selectedPage = fromPage;
/*  47 */     this.numPages = numPages;
/*  48 */     this.mPrefix = prefix;
/*     */ 
/*     */     
/*  51 */     this.mSelectedPage = false;
/*  52 */     this.mIsAll = false;
/*     */   }
/*     */   
/*     */   public int getFromPage() {
/*  56 */     return this.mFromPage;
/*     */   }
/*     */   
/*     */   public int getToPage() {
/*  60 */     return this.mToPage;
/*     */   }
/*     */   
/*     */   public int getStartNum() {
/*  64 */     return this.mStartNum;
/*     */   }
/*     */   
/*     */   public String getPrefix() {
/*  68 */     return this.mPrefix;
/*     */   }
/*     */   
/*     */   public int getPageLabelStyle() {
/*  72 */     return this.mStyle.mPageLabelStyle;
/*     */   }
/*     */   
/*     */   boolean isAll() {
/*  76 */     return this.mIsAll;
/*     */   }
/*     */   
/*     */   void setAll(boolean all) {
/*  80 */     this.mIsAll = all;
/*     */   }
/*     */   
/*     */   boolean isSelectedPage() {
/*  84 */     return this.mSelectedPage;
/*     */   }
/*     */   
/*     */   void setSelectedPage(boolean selectedPage) {
/*  88 */     this.mSelectedPage = selectedPage;
/*     */   }
/*     */   
/*     */   void setFromPage(int fromPage) {
/*  92 */     this.mFromPage = fromPage;
/*     */   }
/*     */   
/*     */   void setToPage(int toPage) {
/*  96 */     this.mToPage = toPage;
/*     */   }
/*     */   
/*     */   PageLabelStyle getStyle() {
/* 100 */     return this.mStyle;
/*     */   }
/*     */   
/*     */   void setStyle(PageLabelStyle style) {
/* 104 */     this.mStyle = style;
/*     */   }
/*     */   
/*     */   void setPrefix(String prefix) {
/* 108 */     this.mPrefix = prefix;
/*     */   }
/*     */   
/*     */   void setStartNum(int startNum) {
/* 112 */     this.mStartNum = startNum;
/*     */   }
/*     */   
/*     */   protected PageLabelSetting(Parcel in) {
/* 116 */     this.selectedPage = in.readInt();
/* 117 */     this.numPages = in.readInt();
/* 118 */     this.mIsAll = (in.readByte() != 0);
/* 119 */     this.mSelectedPage = (in.readByte() != 0);
/* 120 */     this.mFromPage = in.readInt();
/* 121 */     this.mToPage = in.readInt();
/* 122 */     int tmpMStyle = in.readInt();
/* 123 */     this.mStyle = (tmpMStyle == -1) ? null : PageLabelStyle.values()[tmpMStyle];
/* 124 */     this.mPrefix = in.readString();
/* 125 */     this.mStartNum = in.readInt();
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 130 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 135 */     dest.writeInt(this.selectedPage);
/* 136 */     dest.writeInt(this.numPages);
/* 137 */     dest.writeByte(this.mIsAll ? 1 : 0);
/* 138 */     dest.writeByte(this.mSelectedPage ? 1 : 0);
/* 139 */     dest.writeInt(this.mFromPage);
/* 140 */     dest.writeInt(this.mToPage);
/* 141 */     dest.writeInt((this.mStyle == null) ? -1 : this.mStyle.ordinal());
/* 142 */     dest.writeString(this.mPrefix);
/* 143 */     dest.writeInt(this.mStartNum);
/*     */   }
/*     */   
/*     */   enum PageLabelStyle {
/* 147 */     DECIMAL("1, 2, 3", 0),
/* 148 */     ROMAN_UPPER("I, II, III", 1),
/* 149 */     ROMAN_LOWER("i, ii, iii", 2),
/* 150 */     ALPHA_UPPER("A, B, C", 3),
/* 151 */     ALPHA_LOWER("a, b, c", 4),
/* 152 */     NONE("None", 5);
/*     */     
/*     */     final String mLabel;
/*     */     final int mPageLabelStyle;
/*     */     
/*     */     PageLabelStyle(String label, int pageLabelStyle) {
/* 158 */       this.mLabel = label;
/* 159 */       this.mPageLabelStyle = pageLabelStyle;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelSetting.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */