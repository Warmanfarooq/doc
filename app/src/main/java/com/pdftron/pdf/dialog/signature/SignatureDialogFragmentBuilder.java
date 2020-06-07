/*     */ package com.pdftron.pdf.dialog.signature;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.os.Bundle;
/*     */ import android.os.Parcel;
/*     */ import android.os.Parcelable;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.StringRes;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import com.pdftron.pdf.interfaces.builder.SkeletalFragmentBuilder;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ 
/*     */ public class SignatureDialogFragmentBuilder
/*     */   extends SkeletalFragmentBuilder<SignatureDialogFragment>
/*     */ {
/*     */   static final String BUNDLE_TARGET_POINT_X = "target_point_x";
/*     */   static final String BUNDLE_TARGET_POINT_Y = "target_point_y";
/*     */   static final String BUNDLE_TARGET_WIDGET = "target_widget";
/*     */   static final String BUNDLE_TARGET_PAGE = "target_page";
/*     */   static final String BUNDLE_COLOR = "bundle_color";
/*     */   static final String BUNDLE_STROKE_WIDTH = "bundle_stroke_width";
/*     */   static final String BUNDLE_SHOW_SAVED_SIGNATURES = "bundle_show_saved_signatures";
/*     */   static final String BUNDLE_SHOW_SIGNATURE_FROM_IMAGE = "bundle_signature_from_image";
/*     */   static final String BUNDLE_CONFIRM_BUTTON_STRING_RES = "bundle_confirm_button_string_res";
/*     */   static final String BUNDLE_PRESSURE_SENSITIVE = "bundle_pressure_sensitive";
/*     */   public static final String BUNDLE_HAS_DEFAULT_KEYSTORE = "bundle_digital_signature";
/*     */   public static final boolean HAS_DEFAULT_KEYSTORE = false;
/*     */   static final int TARGET_POINT_X = -1;
/*     */   static final int TARGET_POINT_Y = -1;
/*     */   static final int TARGET_PAGE = -1;
/*     */   static final boolean SHOW_SAVED_SIGNATURE = true;
/*     */   static final boolean SHOW_SIGNATURE_FROM_IMAGE = true;
/*     */   @StringRes
/*  35 */   static final int CONFIRM_BUTTON_RES = R.string.add;
/*     */   
/*     */   static final boolean PRESSURE_SENSITIVE = true;
/*     */   
/*     */   private PointF mTargetPoint;
/*     */   
/*     */   private int mTargetPage;
/*     */   private Long mTargetWidget;
/*     */   private int mColor;
/*     */   private float mStrokeWidth;
/*     */   private boolean mShowSavedSignatures = true;
/*     */   private boolean mShowSignatureFromImage = true;
/*     */   private boolean mPressureSensitive = true;
/*     */   @Deprecated
/*     */   private boolean mDigitalSignature = false;
/*     */   private boolean mHasDefaultKeystore = false;
/*     */   @StringRes
/*  52 */   private int mConfirmBtnStrRes = CONFIRM_BUTTON_RES;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SignatureDialogFragment build(@NonNull Context context) {
/*  60 */     return (SignatureDialogFragment)build(context, SignatureDialogFragment.class);
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingTargetPoint(PointF targetPoint) {
/*  64 */     this.mTargetPoint = targetPoint;
/*  65 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingTargetPage(int targetPage) {
/*  69 */     this.mTargetPage = targetPage;
/*  70 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingTargetWidget(Long targetWidget) {
/*  74 */     this.mTargetWidget = targetWidget;
/*  75 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingColor(int color) {
/*  79 */     this.mColor = color;
/*  80 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingStrokeWidth(float strokeWidth) {
/*  84 */     this.mStrokeWidth = strokeWidth;
/*  85 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingShowSavedSignatures(boolean showSavedSignatures) {
/*  89 */     this.mShowSavedSignatures = showSavedSignatures;
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingShowSignatureFromImage(boolean showSignatureFromImage) {
/*  94 */     this.mShowSignatureFromImage = showSignatureFromImage;
/*  95 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingPressureSensitive(boolean pressureSensitive) {
/*  99 */     this.mPressureSensitive = pressureSensitive;
/* 100 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingDefaultKeystore(boolean hasDefaultKeystore) {
/* 104 */     this.mHasDefaultKeystore = hasDefaultKeystore;
/* 105 */     return this;
/*     */   }
/*     */   
/*     */   public SignatureDialogFragmentBuilder usingConfirmBtnStrRes(int confirmBtnStrRes) {
/* 109 */     this.mConfirmBtnStrRes = confirmBtnStrRes;
/* 110 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Bundle createBundle(@NonNull Context context) {
/* 115 */     Bundle bundle = new Bundle();
/* 116 */     if (this.mTargetPoint != null) {
/* 117 */       bundle.putFloat("target_point_x", this.mTargetPoint.x);
/* 118 */       bundle.putFloat("target_point_y", this.mTargetPoint.y);
/*     */     } 
/* 120 */     bundle.putInt("target_page", this.mTargetPage);
/* 121 */     if (this.mTargetWidget != null) {
/* 122 */       bundle.putLong("target_widget", this.mTargetWidget.longValue());
/*     */     }
/* 124 */     bundle.putInt("bundle_color", this.mColor);
/* 125 */     bundle.putFloat("bundle_stroke_width", this.mStrokeWidth);
/* 126 */     bundle.putBoolean("bundle_show_saved_signatures", this.mShowSavedSignatures);
/* 127 */     bundle.putBoolean("bundle_signature_from_image", this.mShowSignatureFromImage);
/* 128 */     bundle.putBoolean("bundle_pressure_sensitive", this.mPressureSensitive);
/* 129 */     bundle.putBoolean("bundle_digital_signature", this.mHasDefaultKeystore);
/* 130 */     if (this.mConfirmBtnStrRes != 0) {
/* 131 */       bundle.putInt("bundle_confirm_button_string_res", this.mConfirmBtnStrRes);
/*     */     }
/*     */     
/* 134 */     return bundle;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkArgs(@NonNull Context context) {}
/*     */ 
/*     */   
/*     */   public int describeContents() {
/* 143 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeToParcel(Parcel dest, int flags) {
/* 148 */     dest.writeParcelable((Parcelable)this.mTargetPoint, flags);
/* 149 */     dest.writeInt(this.mTargetPage);
/* 150 */     dest.writeValue(this.mTargetWidget);
/* 151 */     dest.writeInt(this.mColor);
/* 152 */     dest.writeFloat(this.mStrokeWidth);
/* 153 */     dest.writeByte(this.mShowSavedSignatures ? 1 : 0);
/* 154 */     dest.writeByte(this.mShowSignatureFromImage ? 1 : 0);
/* 155 */     dest.writeByte(this.mPressureSensitive ? 1 : 0);
/* 156 */     dest.writeByte(this.mDigitalSignature ? 1 : 0);
/* 157 */     dest.writeInt(this.mConfirmBtnStrRes);
/* 158 */     dest.writeByte(this.mHasDefaultKeystore ? 1 : 0);
/*     */   }
/*     */   
/*     */   protected SignatureDialogFragmentBuilder(Parcel in) {
/* 162 */     this.mTargetPoint = (PointF)in.readParcelable(PointF.class.getClassLoader());
/* 163 */     this.mTargetPage = in.readInt();
/* 164 */     this.mTargetWidget = (Long)in.readValue(Long.class.getClassLoader());
/* 165 */     this.mColor = in.readInt();
/* 166 */     this.mStrokeWidth = in.readFloat();
/* 167 */     this.mShowSavedSignatures = (in.readByte() != 0);
/* 168 */     this.mShowSignatureFromImage = (in.readByte() != 0);
/* 169 */     this.mPressureSensitive = (in.readByte() != 0);
/* 170 */     this.mDigitalSignature = (in.readByte() != 0);
/* 171 */     this.mConfirmBtnStrRes = in.readInt();
/* 172 */     this.mHasDefaultKeystore = (in.readByte() != 0);
/*     */   }
/*     */   
/* 175 */   public static final Parcelable.Creator<SignatureDialogFragmentBuilder> CREATOR = new Parcelable.Creator<SignatureDialogFragmentBuilder>()
/*     */     {
/*     */       public SignatureDialogFragmentBuilder createFromParcel(Parcel source) {
/* 178 */         return new SignatureDialogFragmentBuilder(source);
/*     */       }
/*     */ 
/*     */       
/*     */       public SignatureDialogFragmentBuilder[] newArray(int size) {
/* 183 */         return new SignatureDialogFragmentBuilder[size];
/*     */       }
/*     */     };
/*     */   
/*     */   public SignatureDialogFragmentBuilder() {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\signature\SignatureDialogFragmentBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */