/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.sdf.Doc;
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
/*     */ public class ColorSpace
/*     */ {
/*     */   public static final int e_device_gray = 0;
/*     */   public static final int e_device_rgb = 1;
/*     */   public static final int e_device_cmyk = 2;
/*     */   public static final int e_cal_gray = 3;
/*     */   public static final int e_cal_rgb = 4;
/*     */   public static final int e_lab = 5;
/*     */   public static final int e_icc = 6;
/*     */   public static final int e_indexed = 7;
/*     */   public static final int e_pattern = 8;
/*     */   public static final int e_separation = 9;
/*     */   public static final int e_device_n = 10;
/*     */   public static final int e_null = 11;
/*     */   long a;
/*     */   private Object b;
/*     */   
/*     */   public static ColorSpace createDeviceGray() throws PDFNetException {
/*  34 */     return __Create(CreateDeviceGrayL(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColorSpace createDeviceRGB() throws PDFNetException {
/*  45 */     return __Create(CreateDeviceRGBL(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColorSpace createDeviceCMYK() throws PDFNetException {
/*  56 */     return __Create(CreateDeviceCMYKL(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ColorSpace createPattern() throws PDFNetException {
/*  67 */     return __Create(CreatePatternL(), null);
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
/*     */   public static ColorSpace createICCFromFile(Doc paramDoc, String paramString) throws PDFNetException {
/*  80 */     return __Create(CreateICCFromFile(paramDoc.__GetHandle(), paramString), null);
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
/*     */   public static ColorSpace createICCFromFilter(Doc paramDoc, Filter paramFilter) throws PDFNetException {
/*  93 */     return __Create(CreateICCFromFilter(paramDoc.__GetHandle(), paramFilter.__GetHandle()), null);
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
/*     */   public static ColorSpace createICCFromBuffer(Doc paramDoc, byte[] paramArrayOfbyte) throws PDFNetException {
/* 106 */     return __Create(CreateICCFromBuffer(paramDoc.__GetHandle(), paramArrayOfbyte), null);
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
/*     */   public ColorSpace(Obj paramObj) {
/* 118 */     this.a = paramObj.__GetHandle();
/* 119 */     this.b = paramObj.__GetRefHandle();
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
/*     */ 
/*     */   
/*     */   public static int getComponentNum(int paramInt, Obj paramObj) throws PDFNetException {
/* 173 */     return GetComponentNum(paramInt, paramObj.__GetHandle());
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
/*     */   public static int getType(Obj paramObj) throws PDFNetException {
/* 186 */     return GetTypeStatic(paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getType() throws PDFNetException {
/* 197 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 207 */     return Obj.__Create(this.a, this.b);
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
/*     */   public int getComponentNum() throws PDFNetException {
/* 220 */     return GetComponentNum(this.a);
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
/*     */   public void initColor(ColorPt paramColorPt) throws PDFNetException {
/* 232 */     InitColor(this.a, paramColorPt.a);
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
/*     */   public void initComponentRanges(double[] paramArrayOfdouble1, double[] paramArrayOfdouble2) throws PDFNetException {
/*     */     int i;
/* 252 */     if ((i = getComponentNum()) != paramArrayOfdouble1.length || i != paramArrayOfdouble2.length)
/*     */     {
/* 254 */       throw new PDFNetException("", 0L, "", "", "Error: Arrays passed to InitComponentRanges must have a length\nequal to the number of components in the ColorSpace.");
/*     */     }
/* 256 */     InitComponentRanges(this.a, paramArrayOfdouble1, paramArrayOfdouble2);
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
/*     */   public ColorPt convert2Gray(ColorPt paramColorPt) throws PDFNetException {
/* 273 */     return new ColorPt(Convert2Gray(this.a, paramColorPt.a));
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
/*     */   public ColorPt convert2RGB(ColorPt paramColorPt) throws PDFNetException {
/* 289 */     return new ColorPt(Convert2RGB(this.a, paramColorPt.a));
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
/*     */   public ColorPt convert2CMYK(ColorPt paramColorPt) throws PDFNetException {
/* 305 */     return new ColorPt(Convert2CMYK(this.a, paramColorPt.a));
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
/*     */   public ColorSpace getAlternateColorSpace() throws PDFNetException {
/* 318 */     return __Create(GetAlternateColorSpace(this.a), this.b);
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
/*     */   public ColorSpace getBaseColorSpace() throws PDFNetException {
/* 330 */     return __Create(GetBaseColorSpace(this.a), this.b);
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
/*     */   public byte[] getLookupTable() throws PDFNetException {
/* 346 */     return GetLookupTable(this.a);
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
/*     */   public ColorPt getBaseColor(byte paramByte) throws PDFNetException {
/* 360 */     return new ColorPt(GetBaseColor(this.a, paramByte));
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
/*     */   public Function getTintFunction() throws PDFNetException {
/* 376 */     return Function.a(GetTintFunction(this.a), this.b);
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
/*     */   public boolean isAll() throws PDFNetException {
/* 389 */     return IsAll(this.a);
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
/*     */   public boolean isNone() throws PDFNetException {
/* 404 */     return IsNone(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   private ColorSpace(long paramLong, Object paramObject) {
/* 409 */     this.a = paramLong;
/* 410 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   public static ColorSpace __Create(long paramLong, Object paramObject) {
/* 414 */     if (paramLong == 0L) {
/* 415 */       return null;
/*     */     }
/* 417 */     return new ColorSpace(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   static native long CreateDeviceGrayL();
/*     */   
/*     */   static native long CreateDeviceRGBL();
/*     */   
/*     */   static native long CreateDeviceCMYKL();
/*     */   
/*     */   static native long CreatePatternL();
/*     */   
/*     */   static native long CreateICCFromFile(long paramLong, String paramString);
/*     */   
/*     */   static native long CreateICCFromFilter(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long CreateICCFromBuffer(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native int GetComponentNum(int paramInt, long paramLong);
/*     */   
/*     */   static native int GetTypeStatic(long paramLong);
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native int GetComponentNum(long paramLong);
/*     */   
/*     */   static native void InitColor(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void InitComponentRanges(long paramLong, double[] paramArrayOfdouble1, double[] paramArrayOfdouble2);
/*     */   
/*     */   static native long Convert2Gray(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long Convert2RGB(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long Convert2CMYK(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetAlternateColorSpace(long paramLong);
/*     */   
/*     */   static native long GetBaseColorSpace(long paramLong);
/*     */   
/*     */   static native byte[] GetLookupTable(long paramLong);
/*     */   
/*     */   static native long GetBaseColor(long paramLong, byte paramByte);
/*     */   
/*     */   static native long GetTintFunction(long paramLong);
/*     */   
/*     */   static native boolean IsAll(long paramLong);
/*     */   
/*     */   static native boolean IsNone(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\ColorSpace.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */