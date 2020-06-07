/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
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
/*     */ public class CustomStampPreviewAppearance
/*     */   implements Parcelable
/*     */ {
/*     */   private static final String BUNDLE_CUSTOM_STAMP_APPEARANCES = "custom_stamp_appearances";
/*     */   public String colorName;
/*     */   public int bgColorStart;
/*     */   public int bgColorMiddle;
/*     */   public int bgColorEnd;
/*     */   public int textColor;
/*     */   public int borderColor;
/*     */   public double fillOpacity;
/*     */   
/*     */   public CustomStampPreviewAppearance(@NonNull String colorName, @ColorInt int bgColorStart, @ColorInt int bgColorMiddle, @ColorInt int bgColorEnd, @ColorInt int textColor, @ColorInt int borderColor, @FloatRange(from = 0.0D, to = 1.0D) double fillOpacity) {
/*  42 */     this.colorName = colorName;
/*  43 */     this.bgColorStart = bgColorStart;
/*  44 */     this.bgColorMiddle = bgColorMiddle;
/*  45 */     this.bgColorEnd = bgColorEnd;
/*  46 */     this.textColor = textColor;
/*  47 */     this.borderColor = borderColor;
/*  48 */     this.fillOpacity = fillOpacity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putCustomStampAppearancesToBundle(Bundle bundle, CustomStampPreviewAppearance[] customStampPreviewAppearances) {
/*  58 */     bundle.putParcelableArray("custom_stamp_appearances", (Parcelable[])customStampPreviewAppearances);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CustomStampPreviewAppearance[] getCustomStampAppearancesFromBundle(@Nullable Bundle bundle) {
/*  68 */     if (bundle == null) {
/*  69 */       return null;
/*     */     }
/*  71 */     return (CustomStampPreviewAppearance[])bundle.getParcelableArray("custom_stamp_appearances");
/*     */   }
/*     */   
/*     */   private CustomStampPreviewAppearance(Parcel in) {
/*  75 */     this.colorName = in.readString();
/*  76 */     this.bgColorStart = in.readInt();
/*  77 */     this.bgColorMiddle = in.readInt();
/*  78 */     this.bgColorEnd = in.readInt();
/*  79 */     this.textColor = in.readInt();
/*  80 */     this.borderColor = in.readInt();
/*  81 */     this.fillOpacity = in.readDouble();
/*     */   }
/*     */   
/*  84 */   public static final Creator<CustomStampPreviewAppearance> CREATOR = new Creator<CustomStampPreviewAppearance>()
/*     */     {
/*     */       public CustomStampPreviewAppearance createFromParcel(Parcel in) {
/*  87 */         return new CustomStampPreviewAppearance(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public CustomStampPreviewAppearance[] newArray(int size) {
/*  92 */         return new CustomStampPreviewAppearance[size];
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public int describeContents() {
/*  98 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 103 */     dest.writeString(this.colorName);
/* 104 */     dest.writeInt(this.bgColorStart);
/* 105 */     dest.writeInt(this.bgColorMiddle);
/* 106 */     dest.writeInt(this.bgColorEnd);
/* 107 */     dest.writeInt(this.textColor);
/* 108 */     dest.writeInt(this.borderColor);
/* 109 */     dest.writeDouble(this.fillOpacity);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\CustomStampPreviewAppearance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */