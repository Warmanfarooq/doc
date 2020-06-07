/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.sdf.DictIterator;
/*     */ import com.pdftron.sdf.Obj;
/*     */ 
/*     */ public class RulerItem
/*     */   implements Parcelable
/*     */ {
/*     */   public float mRulerBase;
/*  15 */   public String mRulerBaseUnit = "";
/*     */   public float mRulerTranslate;
/*  17 */   public String mRulerTranslateUnit = "";
/*     */   
/*     */   public int mPrecision;
/*     */   
/*     */   public RulerItem() {}
/*     */   
/*     */   public RulerItem(float base, String baseUnit, float translate, String translateUnit, int precision) {
/*  24 */     this.mRulerBase = base;
/*  25 */     this.mRulerBaseUnit = baseUnit;
/*  26 */     this.mRulerTranslate = translate;
/*  27 */     this.mRulerTranslateUnit = translateUnit;
/*  28 */     this.mPrecision = precision;
/*     */   }
/*     */   
/*     */   public RulerItem(RulerItem rulerItem) {
/*  32 */     this.mRulerBase = rulerItem.mRulerBase;
/*  33 */     this.mRulerBaseUnit = rulerItem.mRulerBaseUnit;
/*  34 */     this.mRulerTranslate = rulerItem.mRulerTranslate;
/*  35 */     this.mRulerTranslateUnit = rulerItem.mRulerTranslateUnit;
/*  36 */     this.mPrecision = rulerItem.mPrecision;
/*     */   }
/*     */   
/*     */   protected RulerItem(Parcel in) {
/*  40 */     this.mRulerBase = in.readFloat();
/*  41 */     this.mRulerBaseUnit = in.readString();
/*  42 */     this.mRulerTranslate = in.readFloat();
/*  43 */     this.mRulerTranslateUnit = in.readString();
/*  44 */     this.mPrecision = in.readInt();
/*     */   }
/*     */   
/*  47 */   public static final Creator<RulerItem> CREATOR = new Creator<RulerItem>()
/*     */     {
/*     */       public RulerItem createFromParcel(Parcel in) {
/*  50 */         return new RulerItem(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public RulerItem[] newArray(int size) {
/*  55 */         return new RulerItem[size];
/*     */       }
/*     */     };
/*     */   
/*     */   public static void removeRulerItem(Annot annot) {
/*     */     try {
/*  61 */       if (annot == null || !annot.isValid()) {
/*     */         return;
/*     */       }
/*  64 */       Obj obj = annot.getSDFObj();
/*  65 */       if (obj.get(AnnotStyle.KEY_PDFTRON_RULER) != null) {
/*  66 */         obj.erase(AnnotStyle.KEY_PDFTRON_RULER);
/*     */       }
/*  68 */     } catch (PDFNetException pDFNetException) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static RulerItem getRulerItem(Annot annot) {
/*     */     try {
/*  76 */       if (annot == null || !annot.isValid()) {
/*  77 */         return null;
/*     */       }
/*  79 */       Obj obj = annot.getSDFObj();
/*  80 */       if (obj.get(AnnotStyle.KEY_PDFTRON_RULER) != null) {
/*  81 */         RulerItem rulerItem = new RulerItem();
/*  82 */         Obj rulerObj = obj.get(AnnotStyle.KEY_PDFTRON_RULER).value();
/*  83 */         DictIterator rulerItr = rulerObj.getDictIterator();
/*  84 */         if (rulerItr != null) {
/*  85 */           while (rulerItr.hasNext()) {
/*  86 */             String key = rulerItr.key().getName();
/*  87 */             String val = rulerItr.value().getAsPDFText();
/*     */             
/*  89 */             if (key.equals(AnnotStyle.KEY_RULER_BASE)) {
/*  90 */               rulerItem.mRulerBase = Float.valueOf(val).floatValue();
/*  91 */             } else if (key.equals(AnnotStyle.KEY_RULER_BASE_UNIT)) {
/*  92 */               rulerItem.mRulerBaseUnit = val;
/*  93 */             } else if (key.equals(AnnotStyle.KEY_RULER_TRANSLATE)) {
/*  94 */               rulerItem.mRulerTranslate = Float.valueOf(val).floatValue();
/*  95 */             } else if (key.equals(AnnotStyle.KEY_RULER_TRANSLATE_UNIT)) {
/*  96 */               rulerItem.mRulerTranslateUnit = val;
/*     */             } 
/*     */             
/*  99 */             rulerItr.next();
/*     */           } 
/* 101 */           return rulerItem;
/*     */         } 
/*     */       } 
/* 104 */     } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */     
/* 107 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 112 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 117 */     dest.writeFloat(this.mRulerBase);
/* 118 */     dest.writeString(this.mRulerBaseUnit);
/* 119 */     dest.writeFloat(this.mRulerTranslate);
/* 120 */     dest.writeString(this.mRulerTranslateUnit);
/* 121 */     dest.writeInt(this.mPrecision);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public String toString() {
/* 127 */     return "RulerItem:\ndocument scale: " + this.mRulerBase + " " + this.mRulerBaseUnit + "\nworld scale: " + this.mRulerTranslate + " " + this.mRulerTranslateUnit + "\nprecision: " + this.mPrecision;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 134 */     if (this == o) return true; 
/* 135 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 137 */     RulerItem rulerItem = (RulerItem)o;
/*     */     
/* 139 */     if (Float.compare(rulerItem.mRulerBase, this.mRulerBase) != 0) return false; 
/* 140 */     if (Float.compare(rulerItem.mRulerTranslate, this.mRulerTranslate) != 0) return false; 
/* 141 */     if (this.mPrecision != rulerItem.mPrecision) return false; 
/* 142 */     if ((this.mRulerBaseUnit != null) ? !this.mRulerBaseUnit.equals(rulerItem.mRulerBaseUnit) : (rulerItem.mRulerBaseUnit != null))
/* 143 */       return false; 
/* 144 */     return (this.mRulerTranslateUnit != null) ? this.mRulerTranslateUnit.equals(rulerItem.mRulerTranslateUnit) : ((rulerItem.mRulerTranslateUnit == null));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 149 */     int result = (this.mRulerBase != 0.0F) ? Float.floatToIntBits(this.mRulerBase) : 0;
/* 150 */     result = 31 * result + ((this.mRulerBaseUnit != null) ? this.mRulerBaseUnit.hashCode() : 0);
/* 151 */     result = 31 * result + ((this.mRulerTranslate != 0.0F) ? Float.floatToIntBits(this.mRulerTranslate) : 0);
/* 152 */     result = 31 * result + ((this.mRulerTranslateUnit != null) ? this.mRulerTranslateUnit.hashCode() : 0);
/* 153 */     result = 31 * result + this.mPrecision;
/* 154 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\RulerItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */