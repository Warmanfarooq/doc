/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class UnitConverter
/*    */ {
/*    */   public static final String CM = "cm";
/*    */   public static final String INCH = "inch";
/*    */   public static final String YARD = "yard";
/*    */   public static final String INCH_SHORT = "in";
/*    */   public static final String YARD_SHORT = "yd";
/*    */   private float mDistance;
/*    */   private String mUnitFrom;
/*    */   private String mUnitTo;
/*    */   
/*    */   public UnitConverter(float distance) {
/* 18 */     this.mDistance = distance;
/*    */   }
/*    */   
/*    */   public UnitConverter from(String unit) {
/* 22 */     this.mUnitFrom = unit;
/* 23 */     return this;
/*    */   }
/*    */   
/*    */   public float to(String unit) {
/* 27 */     this.mUnitTo = unit;
/*    */     
/* 29 */     if (this.mUnitFrom == null || this.mUnitTo == null) {
/* 30 */       return this.mDistance;
/*    */     }
/*    */     
/* 33 */     if (this.mUnitFrom.equals("inch")) {
/* 34 */       if (this.mUnitTo.equals("cm"))
/* 35 */         return inchesToCm(this.mDistance); 
/* 36 */       if (this.mUnitTo.equals("yard")) {
/* 37 */         return inchesToYard(this.mDistance);
/*    */       }
/* 39 */       return this.mDistance;
/*    */     } 
/* 41 */     if (this.mUnitFrom.equals("cm")) {
/* 42 */       if (this.mUnitTo.equals("inch"))
/* 43 */         return cmToInches(this.mDistance); 
/* 44 */       if (this.mUnitTo.equals("yard")) {
/* 45 */         return cmToYard(this.mDistance);
/*    */       }
/* 47 */       return this.mDistance;
/*    */     } 
/*    */     
/* 50 */     return this.mDistance;
/*    */   }
/*    */   
/*    */   public static UnitConverter convert(float distance) {
/* 54 */     return new UnitConverter(distance);
/*    */   }
/*    */   
/*    */   public static float inchesToCm(float inches) {
/* 58 */     return inches * 2.54F;
/*    */   }
/*    */   
/*    */   public static float inchesToYard(float inches) {
/* 62 */     return inches / 36.0F;
/*    */   }
/*    */   
/*    */   public static float cmToInches(float cm) {
/* 66 */     return cm / 2.54F;
/*    */   }
/*    */   
/*    */   public static float cmToYard(float cm) {
/* 70 */     return cm / 91.44F;
/*    */   }
/*    */   
/*    */   public static float pointsToInches(float points) {
/* 74 */     return points / 72.0F;
/*    */   }
/*    */   
/*    */   public static String getDisplayUnit(String unit) {
/* 78 */     if (unit.equals("inch"))
/* 79 */       return "in"; 
/* 80 */     if (unit.equals("yard")) {
/* 81 */       return "yd";
/*    */     }
/* 83 */     return "cm";
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\UnitConverter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */