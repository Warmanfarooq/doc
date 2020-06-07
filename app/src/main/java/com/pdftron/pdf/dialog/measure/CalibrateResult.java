/*    */ package com.pdftron.pdf.dialog.measure;
/*    */ 
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
/*    */ 
/*    */ public class CalibrateResult
/*    */   implements Parcelable {
/*    */   public Float userInput;
/*    */   public String worldUnit;
/*    */   public long annot;
/*    */   public int page;
/*    */   
/*    */   public CalibrateResult(long annot, int page) {
/* 14 */     this.annot = annot;
/* 15 */     this.page = page;
/*    */   }
/*    */   
/*    */   protected CalibrateResult(Parcel in) {
/* 19 */     this.userInput = Float.valueOf(in.readFloat());
/* 20 */     this.annot = in.readLong();
/* 21 */     this.page = in.readInt();
/* 22 */     this.worldUnit = in.readString();
/*    */   }
/*    */   
/* 25 */   public static final Creator<CalibrateResult> CREATOR = new Creator<CalibrateResult>()
/*    */     {
/*    */       public CalibrateResult createFromParcel(Parcel in) {
/* 28 */         return new CalibrateResult(in);
/*    */       }
/*    */ 
/*    */       
/*    */       public CalibrateResult[] newArray(int size) {
/* 33 */         return new CalibrateResult[size];
/*    */       }
/*    */     };
/*    */ 
/*    */   
/*    */   public int describeContents() {
/* 39 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel dest, int flags) {
/* 44 */     dest.writeFloat(this.userInput.floatValue());
/* 45 */     dest.writeLong(this.annot);
/* 46 */     dest.writeInt(this.page);
/* 47 */     dest.writeString(this.worldUnit);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\measure\CalibrateResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */