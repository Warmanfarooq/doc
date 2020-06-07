/*     */ package com.pdftron.pdf.model;
/*     */ 
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StandardStampPreviewAppearance
/*     */   implements Parcelable
/*     */ {
/*     */   private static final String BUNDLE_STANDARD_STAMP_APPEARANCES = "standard_stamp_appearances";
/*     */   public String text;
/*     */   public CustomStampPreviewAppearance previewAppearance;
/*     */   public boolean pointLeft;
/*     */   public boolean pointRight;
/*     */   
/*     */   public StandardStampPreviewAppearance(@NonNull String stampLabel) {
/*  25 */     this.text = stampLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StandardStampPreviewAppearance(@NonNull String text, @NonNull CustomStampPreviewAppearance previewAppearance) {
/*  35 */     this(text, previewAppearance, false, false);
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
/*     */   public StandardStampPreviewAppearance(@NonNull String text, @NonNull CustomStampPreviewAppearance previewAppearance, boolean pointLeft, boolean pointRight) {
/*  47 */     this.text = text;
/*  48 */     this.previewAppearance = previewAppearance;
/*  49 */     this.pointLeft = pointLeft;
/*  50 */     this.pointRight = pointRight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void putStandardStampAppearancesToBundle(Bundle bundle, StandardStampPreviewAppearance[] standardStampPreviewAppearances) {
/*  60 */     bundle.putParcelableArray("standard_stamp_appearances", (Parcelable[])standardStampPreviewAppearances);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static StandardStampPreviewAppearance[] getStandardStampAppearancesFromBundle(@Nullable Bundle bundle) {
/*  70 */     if (bundle == null) {
/*  71 */       return null;
/*     */     }
/*  73 */     return (StandardStampPreviewAppearance[])bundle.getParcelableArray("standard_stamp_appearances");
/*     */   }
/*     */   
/*     */   private StandardStampPreviewAppearance(Parcel in) {
/*  77 */     this.text = in.readString();
/*  78 */     this.previewAppearance = (CustomStampPreviewAppearance)in.readParcelable(CustomStampPreviewAppearance.class.getClassLoader());
/*  79 */     this.pointLeft = (in.readByte() != 0);
/*  80 */     this.pointRight = (in.readByte() != 0);
/*     */   }
/*     */   
/*  83 */   public static final Creator<StandardStampPreviewAppearance> CREATOR = new Creator<StandardStampPreviewAppearance>()
/*     */     {
/*     */       public StandardStampPreviewAppearance createFromParcel(Parcel in) {
/*  86 */         return new StandardStampPreviewAppearance(in);
/*     */       }
/*     */ 
/*     */       
/*     */       public StandardStampPreviewAppearance[] newArray(int size) {
/*  91 */         return new StandardStampPreviewAppearance[size];
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public int describeContents() {
/*  97 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 102 */     dest.writeString(this.text);
/* 103 */     dest.writeParcelable(this.previewAppearance, flags);
/* 104 */     dest.writeByte((byte)(this.pointLeft ? 1 : 0));
/* 105 */     dest.writeByte((byte)(this.pointRight ? 1 : 0));
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\StandardStampPreviewAppearance.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */