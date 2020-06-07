/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import android.graphics.Bitmap;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.filters.FilterReader;
/*     */ import com.pdftron.filters.FilterWriter;
/*     */ import com.pdftron.helpers.BitmapHelper;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Image
/*     */ {
/*     */   public static final int e_none = 0;
/*     */   public static final int e_jpeg = 1;
/*     */   public static final int e_jp2 = 2;
/*     */   public static final int e_flate = 3;
/*     */   public static final int e_g3 = 4;
/*     */   public static final int e_g4 = 5;
/*     */   public static final int e_ascii_hex = 6;
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public static Image create(Doc paramDoc, String paramString) throws PDFNetException {
/*  41 */     return new Image(Create(paramDoc.__GetHandle(), paramString, 0L), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, String paramString, Obj paramObj) throws PDFNetException {
/*  60 */     return new Image(Create(paramDoc.__GetHandle(), paramString, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace) throws PDFNetException {
/*  90 */     return new Image(Create(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramColorSpace.a, 0L), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace, Obj paramObj) throws PDFNetException {
/* 119 */     return new Image(Create(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramColorSpace.a, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, byte[] paramArrayOfbyte) throws PDFNetException {
/* 134 */     return new Image(Create(paramDoc.__GetHandle(), paramArrayOfbyte, 0L), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, byte[] paramArrayOfbyte, Obj paramObj) throws PDFNetException {
/* 150 */     return new Image(Create(paramDoc.__GetHandle(), paramArrayOfbyte, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, Filter paramFilter) throws PDFNetException {
/* 167 */     Image image = new Image(Create(paramDoc.__GetHandle(), paramFilter.__GetHandle(), 0L), paramDoc);
/* 168 */     paramFilter.__SetRefHandle(image);
/* 169 */     return image;
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
/*     */   public static Image create(Doc paramDoc, Filter paramFilter, Obj paramObj) throws PDFNetException {
/* 187 */     Image image = new Image(Create(paramDoc.__GetHandle(), paramFilter.__GetHandle(), paramObj.__GetHandle()), paramDoc);
/* 188 */     paramFilter.__SetRefHandle(image);
/* 189 */     return image;
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
/*     */   public static Image create(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace) throws PDFNetException {
/* 215 */     return new Image(Create(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramInt3, paramColorSpace.a, 0L), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace, Obj paramObj) throws PDFNetException {
/* 243 */     return new Image(Create(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramInt3, paramColorSpace.a, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image createImageMask(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2) throws PDFNetException {
/* 272 */     return new Image(CreateImageMask(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, 0L), paramDoc);
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
/*     */   public static Image createImageMask(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, Obj paramObj) throws PDFNetException {
/* 295 */     return new Image(CreateImageMask(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image createImageMask(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2) throws PDFNetException {
/* 316 */     return new Image(CreateImageMask(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, 0L), paramDoc);
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
/*     */   public static Image createImageMask(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, Obj paramObj) throws PDFNetException {
/* 339 */     return new Image(CreateImageMask(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image createSoftMask(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3) throws PDFNetException {
/* 368 */     return new Image(CreateSoftMask(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3, 0L), paramDoc);
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
/*     */   public static Image createSoftMask(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, Obj paramObj) throws PDFNetException {
/* 392 */     return new Image(CreateSoftMask(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image createSoftMask(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, int paramInt3) throws PDFNetException {
/* 415 */     return new Image(CreateSoftMask(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramInt3, 0L), paramDoc);
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
/*     */   public static Image createSoftMask(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, int paramInt3, Obj paramObj) throws PDFNetException {
/* 439 */     return new Image(CreateSoftMask(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramInt3, paramObj.__GetHandle()), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace, int paramInt4) throws PDFNetException {
/* 492 */     return new Image(Create(paramDoc.__GetHandle(), paramArrayOfbyte, paramInt1, paramInt2, paramInt3, paramColorSpace.a, paramInt4), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, FilterReader paramFilterReader, int paramInt1, int paramInt2, int paramInt3, ColorSpace paramColorSpace, int paramInt4) throws PDFNetException {
/* 519 */     return new Image(Create(paramDoc.__GetHandle(), paramFilterReader.__GetHandle(), paramInt1, paramInt2, paramInt3, paramColorSpace.a, paramInt4), paramDoc);
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
/*     */   public static Image create(Doc paramDoc, Bitmap paramBitmap) throws PDFNetException, InterruptedException {
/* 538 */     int i = BitmapHelper.getHeight(paramBitmap);
/*     */     int j;
/* 540 */     if ((j = BitmapHelper.getWidth(paramBitmap)) * i <= 0) {
/* 541 */       return null;
/*     */     }
/*     */     
/* 544 */     int[] arrayOfInt = new int[j * i];
/* 545 */     BitmapHelper.createArrayFromBitmap(paramBitmap, arrayOfInt, j, i);
/* 546 */     return new Image(Create(paramDoc.__GetHandle(), arrayOfInt, j, i), paramDoc);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Bitmap getBitmap() throws PDFNetException {
/*     */     int[] arrayOfInt;
/* 558 */     return BitmapHelper.getBitmap(arrayOfInt = GetRawImageData(this.a));
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
/*     */   public Image(Obj paramObj) {
/* 574 */     this.a = paramObj.__GetHandle();
/* 575 */     this.b = paramObj.__GetRefHandle();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() {
/* 584 */     return Obj.__Create(this.a, this.b);
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
/*     */   public boolean isValid() throws PDFNetException {
/* 597 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Filter getImageData() throws PDFNetException {
/* 608 */     return Filter.__Create(GetImageData(this.a), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageDataSize() throws PDFNetException {
/* 619 */     return GetImageDataSize(this.a);
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
/*     */   public ColorSpace getImageColorSpace() throws PDFNetException {
/* 636 */     return ColorSpace.__Create(GetImageColorSpace(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageWidth() throws PDFNetException {
/* 647 */     return GetImageWidth(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageHeight() throws PDFNetException {
/* 658 */     return GetImageHeight(this.a);
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
/*     */   public Obj getDecodeArray() throws PDFNetException {
/* 674 */     return Obj.__Create(GetDecodeArray(this.a), this.b);
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
/*     */   public int getBitsPerComponent() throws PDFNetException {
/* 687 */     return GetBitsPerComponent(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getComponentNum() throws PDFNetException {
/* 698 */     return GetComponentNum(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImageMask() throws PDFNetException {
/* 709 */     return IsImageMask(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isImageInterpolate() throws PDFNetException {
/* 720 */     return IsImageInterpolate(this.a);
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
/*     */   public Obj getMask() throws PDFNetException {
/* 737 */     return Obj.__Create(GetMask(this.a), this.b);
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
/*     */   public void setMask(Image paramImage) throws PDFNetException {
/* 752 */     SetMask(this.a, paramImage.a);
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
/*     */   public void setMask(Obj paramObj) throws PDFNetException {
/* 769 */     SetMaskObj(this.a, paramObj.__GetHandle());
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
/*     */   public Obj getSoftMask() throws PDFNetException {
/* 782 */     return Obj.__Create(GetSoftMask(this.a), this.b);
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
/*     */   public void setSoftMask(Image paramImage) throws PDFNetException {
/* 797 */     SetSoftMask(this.a, paramImage.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getImageRenderingIntent() throws PDFNetException {
/* 808 */     return GetImageRenderingIntent(this.a);
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
/*     */   public int export(String paramString) throws PDFNetException {
/* 838 */     return Export(this.a, paramString);
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
/*     */   public int export(FilterWriter paramFilterWriter) throws PDFNetException {
/* 860 */     return Export(this.a, paramFilterWriter.__GetHandle());
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
/*     */   public void exportAsTiff(String paramString) throws PDFNetException {
/* 872 */     ExportAsTiff(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportAsTiff(FilterWriter paramFilterWriter) throws PDFNetException {
/* 883 */     ExportAsTiff(this.a, paramFilterWriter.__GetHandle());
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
/*     */   public void exportAsPng(String paramString) throws PDFNetException {
/* 895 */     ExportAsPng(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportAsPng(FilterWriter paramFilterWriter) throws PDFNetException {
/* 906 */     ExportAsPng(this.a, paramFilterWriter.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 914 */     return this.a;
/*     */   } static native long Create(long paramLong1, String paramString, long paramLong2); static native long Create(long paramLong1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, long paramLong2, long paramLong3); static native long Create(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, long paramLong4); static native long Create(long paramLong1, byte[] paramArrayOfbyte, long paramLong2); static native long Create(long paramLong1, long paramLong2, long paramLong3); static native long CreateImageMask(long paramLong1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, long paramLong2); static native long CreateImageMask(long paramLong1, long paramLong2, int paramInt1, int paramInt2, long paramLong3); static native long CreateSoftMask(long paramLong1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, long paramLong2); static native long CreateSoftMask(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3); static native long Create(long paramLong1, byte[] paramArrayOfbyte, int paramInt1, int paramInt2, int paramInt3, long paramLong2, int paramInt4); static native long Create(long paramLong1, long paramLong2, int paramInt1, int paramInt2, int paramInt3, long paramLong3, int paramInt4); static native long Create(long paramLong, int[] paramArrayOfint, int paramInt1, int paramInt2); static native boolean IsValid(long paramLong); static native long GetImageData(long paramLong); static native int GetImageDataSize(long paramLong); static native long GetImageColorSpace(long paramLong); static native int GetImageWidth(long paramLong);
/*     */   static native int GetImageHeight(long paramLong);
/*     */   private Image(long paramLong, Object paramObject) {
/* 918 */     this.a = paramLong;
/* 919 */     this.b = paramObject;
/*     */   } static native long GetDecodeArray(long paramLong); static native int GetBitsPerComponent(long paramLong); static native int GetComponentNum(long paramLong); static native boolean IsImageMask(long paramLong); static native boolean IsImageInterpolate(long paramLong); static native long GetMask(long paramLong); static native void SetMask(long paramLong1, long paramLong2); static native void SetMaskObj(long paramLong1, long paramLong2); static native long GetSoftMask(long paramLong); static native void SetSoftMask(long paramLong1, long paramLong2);
/*     */   static native int GetImageRenderingIntent(long paramLong);
/*     */   static native int Export(long paramLong, String paramString);
/*     */   static native int Export(long paramLong1, long paramLong2);
/*     */   static native void ExportAsTiff(long paramLong, String paramString);
/*     */   static native void ExportAsTiff(long paramLong1, long paramLong2);
/*     */   static native void ExportAsPng(long paramLong, String paramString);
/*     */   static native void ExportAsPng(long paramLong1, long paramLong2);
/*     */   static native int[] GetRawImageData(long paramLong);
/*     */   public static class Compat { public static int[] GetRawImageData(long param1Long) {
/* 930 */       return Image.GetRawImageData(param1Long);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Image.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */