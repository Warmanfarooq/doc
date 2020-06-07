/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.pdf.struct.SElement;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Element
/*     */ {
/*     */   public static final int e_null = 0;
/*     */   public static final int e_path = 1;
/*     */   public static final int e_text_begin = 2;
/*     */   public static final int e_text = 3;
/*     */   public static final int e_text_new_line = 4;
/*     */   public static final int e_text_end = 5;
/*     */   public static final int e_image = 6;
/*     */   public static final int e_inline_image = 7;
/*     */   public static final int e_shading = 8;
/*     */   public static final int e_form = 9;
/*     */   public static final int e_group_begin = 10;
/*     */   public static final int e_group_end = 11;
/*     */   public static final int e_marked_content_begin = 12;
/*     */   public static final int e_marked_content_end = 13;
/*     */   public static final int e_marked_content_point = 14;
/*     */   long a;
/*     */   private Object b;
/*     */   private Object c;
/*     */   
/*     */   public int getType() throws PDFNetException {
/*  79 */     return GetType(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GState getGState() throws PDFNetException {
/*  90 */     return new GState(GetGState(this.a), this.b, this.c);
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
/*     */   public Matrix2D getCTM() throws PDFNetException {
/* 102 */     return Matrix2D.__Create(GetCTM(this.a));
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
/*     */   public Rect getBBox() throws PDFNetException {
/*     */     long l;
/* 126 */     if ((l = GetBBox(this.a)) == 0L) {
/* 127 */       return null;
/*     */     }
/* 129 */     return new Rect(l);
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
/*     */   public SElement getParentStructElement() throws PDFNetException {
/* 142 */     return SElement.__Create(GetParentStructElement(this.a), this);
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
/*     */   public int getStructMCID() throws PDFNetException {
/* 158 */     return GetStructMCID(this.a);
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
/*     */   public boolean isOCVisible() throws PDFNetException {
/* 176 */     return IsOCVisible(this.a);
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
/*     */   public boolean isClippingPath() throws PDFNetException {
/* 190 */     return IsClippingPath(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathData getPathData() throws PDFNetException {
/* 200 */     double[] arrayOfDouble = GetPathPoints(this.a);
/* 201 */     byte[] arrayOfByte = GetPathTypes(this.a);
/* 202 */     return new PathData(true, arrayOfByte, arrayOfDouble);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isStroked() throws PDFNetException {
/* 213 */     return IsStroked(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFilled() throws PDFNetException {
/* 224 */     return IsFilled(this.a);
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
/*     */   public boolean isWindingFill() throws PDFNetException {
/* 249 */     return IsWindingFill(this.a);
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
/*     */   public boolean isClipWindingFill() throws PDFNetException {
/* 261 */     return IsClipWindingFill(this.a);
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
/*     */   public void setPathClip(boolean paramBoolean) throws PDFNetException {
/* 274 */     SetPathClip(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPathData(PathData paramPathData) throws PDFNetException {
/* 283 */     SetPathPoints(this.a, paramPathData.getPoints());
/* 284 */     SetPathTypes(this.a, paramPathData.getOperators());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPathStroke(boolean paramBoolean) throws PDFNetException {
/* 295 */     SetPathStroke(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPathFill(boolean paramBoolean) throws PDFNetException {
/* 306 */     SetPathFill(this.a, paramBoolean);
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
/*     */   public void setWindingFill(boolean paramBoolean) throws PDFNetException {
/* 318 */     SetWindingFill(this.a, paramBoolean);
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
/*     */   public void setClipWindingFill(boolean paramBoolean) throws PDFNetException {
/* 330 */     SetClipWindingFill(this.a, paramBoolean);
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
/*     */   public Obj getXObject() throws PDFNetException {
/* 343 */     return Obj.__Create(GetXObject(this.a), this.c);
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
/*     */   public Filter getImageData() throws PDFNetException {
/* 356 */     return Filter.__Create(GetImageData(this.a), null);
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
/* 367 */     return GetImageDataSize(this.a);
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
/*     */   public ColorSpace getImageColorSpace() throws PDFNetException {
/* 383 */     return ColorSpace.__Create(GetImageColorSpace(this.a), this.c);
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
/* 394 */     return GetImageWidth(this.a);
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
/* 405 */     return GetImageHeight(this.a);
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
/* 421 */     return Obj.__Create(GetDecodeArray(this.a), this.c);
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
/* 434 */     return GetBitsPerComponent(this.a);
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
/* 445 */     return GetComponentNum(this.a);
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
/* 456 */     return IsImageMask(this.a);
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
/* 467 */     return IsImageInterpolate(this.a);
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
/*     */   public Obj getMask() throws PDFNetException {
/* 482 */     return Obj.__Create(GetMask(this.a), this.c);
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
/* 493 */     return GetImageRenderingIntent(this.a);
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
/*     */   public String getTextString() throws PDFNetException {
/* 520 */     return GetTextString(this.a);
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
/*     */   public byte[] getTextData() throws PDFNetException {
/* 543 */     return GetTextData(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Matrix2D getTextMatrix() throws PDFNetException {
/* 554 */     return Matrix2D.__Create(GetTextMatrix(this.a));
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
/*     */   public CharIterator getCharIterator() throws PDFNetException {
/* 595 */     return new CharIterator(GetCharIterator(this.a), this.b);
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
/*     */   public double getTextLength() throws PDFNetException {
/* 617 */     return GetTextLength(this.a);
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
/*     */   public double getPosAdjustment() throws PDFNetException {
/* 634 */     return GetPosAdjustment(this.a);
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
/*     */   public double[] getNewTextLineOffset() throws PDFNetException {
/*     */     double[] arrayOfDouble;
/* 652 */     return arrayOfDouble = GetNewTextLineOffset(this.a);
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
/*     */   public boolean hasTextMatrix() throws PDFNetException {
/* 667 */     return HasTextMatrix(this.a);
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
/*     */   public void setTextData(byte[] paramArrayOfbyte) throws PDFNetException {
/* 680 */     SetTextData(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextMatrix(Matrix2D paramMatrix2D) throws PDFNetException {
/* 691 */     SetTextMatrix(this.a, paramMatrix2D.__GetHandle());
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
/*     */   public void setTextMatrix(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6) throws PDFNetException {
/* 716 */     SetTextMatrix(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramDouble5, paramDouble6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosAdjustment(double paramDouble) throws PDFNetException {
/* 727 */     SetPosAdjustment(this.a, paramDouble);
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
/*     */   public void updateTextMetrics() throws PDFNetException {
/* 743 */     UpdateTextMetrics(this.a);
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
/*     */   public void setNewTextLineOffset(double paramDouble1, double paramDouble2) throws PDFNetException {
/* 758 */     SetNewTextLineOffset(this.a, paramDouble1, paramDouble2);
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
/*     */   public Shading getShading() throws PDFNetException {
/* 771 */     return Shading.a(GetShading(this.a), this.c);
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
/*     */   public Obj getMCPropertyDict() throws PDFNetException {
/* 790 */     return Obj.__Create(GetMCPropertyDict(this.a), this.c);
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
/*     */   public Obj getMCTag() throws PDFNetException {
/* 802 */     return Obj.__Create(GetMCTag(this.a), this.c);
/*     */   }
/*     */ 
/*     */   
/*     */   Element(long paramLong, Object paramObject1, Object paramObject2) {
/* 807 */     this.a = paramLong;
/* 808 */     this.b = paramObject1;
/* 809 */     this.c = paramObject2;
/*     */   }
/*     */   
/*     */   static native int GetType(long paramLong);
/*     */   
/*     */   static native long GetGState(long paramLong);
/*     */   
/*     */   static native long GetCTM(long paramLong);
/*     */   
/*     */   static native long GetBBox(long paramLong);
/*     */   
/*     */   static native long GetParentStructElement(long paramLong);
/*     */   
/*     */   static native int GetStructMCID(long paramLong);
/*     */   
/*     */   static native boolean IsOCVisible(long paramLong);
/*     */   
/*     */   static native boolean IsClippingPath(long paramLong);
/*     */   
/*     */   static native boolean IsStroked(long paramLong);
/*     */   
/*     */   static native boolean IsFilled(long paramLong);
/*     */   
/*     */   static native boolean IsWindingFill(long paramLong);
/*     */   
/*     */   static native boolean IsClipWindingFill(long paramLong);
/*     */   
/*     */   static native byte[] GetPathTypes(long paramLong);
/*     */   
/*     */   static native double[] GetPathPoints(long paramLong);
/*     */   
/*     */   static native void SetPathClip(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPathStroke(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPathFill(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetWindingFill(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetClipWindingFill(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void SetPathPoints(long paramLong, double[] paramArrayOfdouble);
/*     */   
/*     */   static native void SetPathTypes(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long GetXObject(long paramLong);
/*     */   
/*     */   static native long GetImageData(long paramLong);
/*     */   
/*     */   static native int GetImageDataSize(long paramLong);
/*     */   
/*     */   static native long GetImageColorSpace(long paramLong);
/*     */   
/*     */   static native int GetImageWidth(long paramLong);
/*     */   
/*     */   static native int GetImageHeight(long paramLong);
/*     */   
/*     */   static native long GetDecodeArray(long paramLong);
/*     */   
/*     */   static native int GetBitsPerComponent(long paramLong);
/*     */   
/*     */   static native int GetComponentNum(long paramLong);
/*     */   
/*     */   static native boolean IsImageMask(long paramLong);
/*     */   
/*     */   static native boolean IsImageInterpolate(long paramLong);
/*     */   
/*     */   static native long GetMask(long paramLong);
/*     */   
/*     */   static native int GetImageRenderingIntent(long paramLong);
/*     */   
/*     */   static native String GetTextString(long paramLong);
/*     */   
/*     */   static native byte[] GetTextData(long paramLong);
/*     */   
/*     */   static native long GetTextMatrix(long paramLong);
/*     */   
/*     */   static native long GetCharIterator(long paramLong);
/*     */   
/*     */   static native double GetTextLength(long paramLong);
/*     */   
/*     */   static native double GetPosAdjustment(long paramLong);
/*     */   
/*     */   static native double[] GetNewTextLineOffset(long paramLong);
/*     */   
/*     */   static native boolean HasTextMatrix(long paramLong);
/*     */   
/*     */   static native void SetTextData(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void SetTextMatrix(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void SetTextMatrix(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6);
/*     */   
/*     */   static native void SetPosAdjustment(long paramLong, double paramDouble);
/*     */   
/*     */   static native void UpdateTextMetrics(long paramLong);
/*     */   
/*     */   static native void SetNewTextLineOffset(long paramLong, double paramDouble1, double paramDouble2);
/*     */   
/*     */   static native long GetShading(long paramLong);
/*     */   
/*     */   static native long GetMCPropertyDict(long paramLong);
/*     */   
/*     */   static native long GetMCTag(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Element.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */