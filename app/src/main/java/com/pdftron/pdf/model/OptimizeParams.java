/*    */ package com.pdftron.pdf.model;
/*    */ 
/*    */ import android.os.Parcel;
/*    */ import android.os.Parcelable;
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
/*    */ public class OptimizeParams
/*    */   implements Parcelable
/*    */ {
/*    */   public int colorDownsampleMode;
/*    */   public double colorMaxDpi;
/*    */   public double colorResampleDpi;
/*    */   public int colorCompressionMode;
/*    */   public long colorQuality;
/*    */   public int monoDownsampleMode;
/*    */   public double monoMaxDpi;
/*    */   public double monoResampleDpi;
/*    */   public int monoCompressionMode;
/*    */   public boolean forceRecompression;
/*    */   public boolean forceChanges;
/*    */   
/*    */   public OptimizeParams() {}
/*    */   
/*    */   public int describeContents() {
/* 36 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void writeToParcel(Parcel pc, int flags) {
/* 41 */     pc.writeInt(this.colorDownsampleMode);
/* 42 */     pc.writeDouble(this.colorMaxDpi);
/* 43 */     pc.writeDouble(this.colorResampleDpi);
/* 44 */     pc.writeInt(this.colorCompressionMode);
/* 45 */     pc.writeLong(this.colorQuality);
/*    */     
/* 47 */     pc.writeInt(this.monoDownsampleMode);
/* 48 */     pc.writeDouble(this.monoMaxDpi);
/* 49 */     pc.writeDouble(this.monoResampleDpi);
/* 50 */     pc.writeInt(this.monoCompressionMode);
/*    */     
/* 52 */     pc.writeInt(this.forceRecompression ? 1 : 0);
/* 53 */     pc.writeInt(this.forceChanges ? 1 : 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 59 */   public static final Creator<OptimizeParams> CREATOR = new Creator<OptimizeParams>() {
/*    */       public OptimizeParams createFromParcel(Parcel pc) {
/* 61 */         return new OptimizeParams(pc);
/*    */       }
/*    */       
/*    */       public OptimizeParams[] newArray(int size) {
/* 65 */         return new OptimizeParams[size];
/*    */       }
/*    */     };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OptimizeParams(Parcel pc) {
/* 73 */     this.colorDownsampleMode = pc.readInt();
/* 74 */     this.colorMaxDpi = pc.readDouble();
/* 75 */     this.colorResampleDpi = pc.readDouble();
/* 76 */     this.colorCompressionMode = pc.readInt();
/* 77 */     this.colorQuality = pc.readLong();
/*    */     
/* 79 */     this.monoDownsampleMode = pc.readInt();
/* 80 */     this.monoMaxDpi = pc.readDouble();
/* 81 */     this.monoResampleDpi = pc.readDouble();
/* 82 */     this.monoCompressionMode = pc.readInt();
/*    */     
/* 84 */     this.forceRecompression = (pc.readInt() == 1);
/* 85 */     this.forceChanges = (pc.readInt() == 1);
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\OptimizeParams.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */