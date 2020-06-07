/*      */ package com.pdftron.pdf;
/*      */ 
/*      */ import com.pdftron.common.Matrix2D;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.sdf.Doc;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import java.io.InputStream;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Font
/*      */ {
/*      */   public static final int e_times_roman = 0;
/*      */   public static final int e_times_bold = 1;
/*      */   public static final int e_times_italic = 2;
/*      */   public static final int e_times_bold_italic = 3;
/*      */   public static final int e_helvetica = 4;
/*      */   public static final int e_helvetica_bold = 5;
/*      */   public static final int e_helvetica_oblique = 6;
/*      */   public static final int e_helvetica_bold_oblique = 7;
/*      */   public static final int e_courier = 8;
/*      */   public static final int e_courier_bold = 9;
/*      */   public static final int e_courier_oblique = 10;
/*      */   public static final int e_courier_bold_oblique = 11;
/*      */   public static final int e_symbol = 12;
/*      */   public static final int e_zapf_dingbats = 13;
/*      */   public static final int e_null = 14;
/*      */   public static final int e_IdentityH = 0;
/*      */   public static final int e_Indices = 1;
/*      */   public static final int e_Type1 = 0;
/*      */   public static final int e_TrueType = 1;
/*      */   public static final int e_MMType1 = 2;
/*      */   public static final int e_Type3 = 3;
/*      */   public static final int e_Type0 = 4;
/*      */   public static final int e_CIDType0 = 5;
/*      */   public static final int e_CIDType2 = 6;
/*      */   long a;
/*      */   Object b;
/*      */   
/*      */   public Font() {
/*  101 */     this.a = 0L;
/*  102 */     this.b = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Font(Obj paramObj) {
/*  113 */     this.a = paramObj.__GetHandle();
/*  114 */     this.b = paramObj.__GetRefHandle();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font create(Doc paramDoc, int paramInt) throws PDFNetException {
/*  128 */     return __Create(Create(paramDoc.__GetHandle(), paramInt, false), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font create(Doc paramDoc, int paramInt, boolean paramBoolean) throws PDFNetException {
/*  143 */     return __Create(Create(paramDoc.__GetHandle(), paramInt, paramBoolean), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font create(Doc paramDoc, Font paramFont, String paramString) throws PDFNetException {
/*  157 */     return __Create(Create(paramDoc.__GetHandle(), paramFont.a, paramString), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font create(Doc paramDoc, String paramString1, String paramString2) throws PDFNetException {
/*  171 */     return __Create(Create(paramDoc.__GetHandle(), paramString1, paramString2), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, String paramString) throws PDFNetException {
/*  188 */     return __Create(CreateTrueTypeFont(paramDoc.__GetHandle(), paramString, true, true), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, InputStream paramInputStream) throws PDFNetException {
/*  205 */     return __Create(CreateTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, true, true), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, String paramString, boolean paramBoolean) throws PDFNetException {
/*  220 */     return __Create(CreateTrueTypeFont(paramDoc.__GetHandle(), paramString, paramBoolean, true), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, InputStream paramInputStream, boolean paramBoolean) throws PDFNetException {
/*  235 */     return __Create(CreateTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, paramBoolean, true), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, String paramString, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*  251 */     return __Create(CreateTrueTypeFont(paramDoc.__GetHandle(), paramString, paramBoolean1, paramBoolean2), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createTrueTypeFont(Doc paramDoc, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*  267 */     return __Create(CreateTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, paramBoolean1, paramBoolean2), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, String paramString) throws PDFNetException {
/*  284 */     return __Create(CreateCIDTrueTypeFont(paramDoc.__GetHandle(), paramString, true, true, 0, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, InputStream paramInputStream) throws PDFNetException {
/*  301 */     return __Create(CreateCIDTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, true, true, 0, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, String paramString, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*  317 */     return __Create(CreateCIDTrueTypeFont(paramDoc.__GetHandle(), paramString, paramBoolean1, paramBoolean2, 0, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2) throws PDFNetException {
/*  333 */     return __Create(CreateCIDTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, paramBoolean1, paramBoolean2, 0, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt) throws PDFNetException {
/*  350 */     return __Create(CreateCIDTrueTypeFont(paramDoc.__GetHandle(), paramString, paramBoolean1, paramBoolean2, paramInt, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2, int paramInt) throws PDFNetException {
/*  367 */     return __Create(CreateCIDTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, paramBoolean1, paramBoolean2, paramInt, 0L), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong) throws PDFNetException {
/*  386 */     return __Create(CreateCIDTrueTypeFont(paramDoc.__GetHandle(), paramString, paramBoolean1, paramBoolean2, paramInt, paramLong), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createCIDTrueTypeFont(Doc paramDoc, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong) throws PDFNetException {
/*  404 */     return __Create(CreateCIDTrueTypeFontFromStream(paramDoc.__GetHandle(), paramInputStream, paramBoolean1, paramBoolean2, paramInt, paramLong), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createType1Font(Doc paramDoc, String paramString) throws PDFNetException {
/*  417 */     return __Create(CreateType1Font(paramDoc.__GetHandle(), paramString, true), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Font createType1Font(Doc paramDoc, String paramString, boolean paramBoolean) throws PDFNetException {
/*  432 */     return __Create(CreateType1Font(paramDoc.__GetHandle(), paramString, paramBoolean), paramDoc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getType() throws PDFNetException {
/*  466 */     return GetType(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSimple() throws PDFNetException {
/*  490 */     return IsSimple(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getType(Obj paramObj) throws PDFNetException {
/*  502 */     return GetTypeStatic(paramObj.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj GetSDFObj() {
/*  512 */     return Obj.__Create(this.a, this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj GetDescriptor() throws PDFNetException {
/*  524 */     return Obj.__Create(GetDescriptor(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() throws PDFNetException {
/*  537 */     return GetName(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFamilyName() throws PDFNetException {
/*  550 */     return GetFamilyName(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFixedWidth() throws PDFNetException {
/*  561 */     return IsFixedWidth(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSerif() throws PDFNetException {
/*  572 */     return IsSerif(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSymbolic() throws PDFNetException {
/*  583 */     return IsSymbolic(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isItalic() throws PDFNetException {
/*  594 */     return IsItalic(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAllCap() throws PDFNetException {
/*  605 */     return IsAllCap(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isForceBold() throws PDFNetException {
/*  616 */     return IsForceBold(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isHorizontalMode() throws PDFNetException {
/*  627 */     return IsHorizontalMode(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getWidth(long paramLong) throws PDFNetException {
/*  646 */     return GetWidth(this.a, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMaxWidth() throws PDFNetException {
/*  657 */     return GetMaxWidth(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMissingWidth() throws PDFNetException {
/*  669 */     return GetMissingWidth(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FontCharCodeIterator getCharCodeIterator() throws PDFNetException {
/*  684 */     return new FontCharCodeIterator(GetCharCodeIterator(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PathData getGlyphPath(long paramLong, boolean paramBoolean) {
/*  708 */     return GetGlyphPath(this.a, paramLong, paramBoolean, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PathData getGlyphPath(long paramLong, boolean paramBoolean, Matrix2D paramMatrix2D) {
/*  725 */     return GetGlyphPath(this.a, paramLong, paramBoolean, paramMatrix2D.__GetHandle());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] mapToUnicode(long paramLong) throws PDFNetException {
/*  747 */     return MapToUnicode(this.a, paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getEncoding() throws PDFNetException {
/*  771 */     return GetEncoding(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmbedded() throws PDFNetException {
/*  783 */     return IsEmbedded(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEmbeddedFontName() throws PDFNetException {
/*  795 */     return GetEmbeddedFontName(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getEmbeddedFont() throws PDFNetException {
/*  809 */     return Obj.__Create(GetEmbeddedFont(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getEmbeddedFontBufSize() throws PDFNetException {
/*  826 */     return GetEmbeddedFontBufSize(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public short getUnitsPerEm() throws PDFNetException {
/*  843 */     return GetUnitsPerEm(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Rect getBBox() throws PDFNetException {
/*  857 */     return new Rect(GetBBox(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getAscent() throws PDFNetException {
/*  876 */     return GetAscent(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDescent() throws PDFNetException {
/*  894 */     return GetDescent(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStandardType1FontType() throws PDFNetException {
/*  909 */     return GetStandardType1FontType(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isCFF() throws PDFNetException {
/*  922 */     return IsCFF(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix2D getType3FontMatrix() throws PDFNetException {
/*  939 */     return Matrix2D.__Create(GetType3FontMatrix(this.a));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj getType3GlyphStream(long paramLong) {
/*  954 */     return Obj.__Create(GetType3GlyphStream(this.a, paramLong), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Font(long paramLong, Object paramObject) {
/* 1031 */     this.a = paramLong;
/* 1032 */     this.b = paramObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Font __Create(long paramLong, Object paramObject) {
/* 1037 */     if (paramLong == 0L) {
/* 1038 */       return null;
/*      */     }
/* 1040 */     return new Font(paramLong, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   public long __GetHandle() {
/* 1045 */     return this.a;
/*      */   }
/*      */ 
/*      */   
/*      */   public Object __GetRefHandle() {
/* 1050 */     return this.b;
/*      */   }
/*      */   
/*      */   static native long Create(long paramLong, int paramInt, boolean paramBoolean);
/*      */   
/*      */   static native long Create(long paramLong1, long paramLong2, String paramString);
/*      */   
/*      */   static native long Create(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native long CreateTrueTypeFont(long paramLong, String paramString, boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   static native long CreateTrueTypeFontFromStream(long paramLong, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   static native long CreateCIDTrueTypeFont(long paramLong1, String paramString, boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong2);
/*      */   
/*      */   static native long CreateCIDTrueTypeFontFromStream(long paramLong1, InputStream paramInputStream, boolean paramBoolean1, boolean paramBoolean2, int paramInt, long paramLong2);
/*      */   
/*      */   static native long CreateType1Font(long paramLong, String paramString, boolean paramBoolean);
/*      */   
/*      */   static native int GetType(long paramLong);
/*      */   
/*      */   static native boolean IsSimple(long paramLong);
/*      */   
/*      */   static native int GetTypeStatic(long paramLong);
/*      */   
/*      */   static native long GetDescriptor(long paramLong);
/*      */   
/*      */   static native String GetName(long paramLong);
/*      */   
/*      */   static native String GetFamilyName(long paramLong);
/*      */   
/*      */   static native boolean IsFixedWidth(long paramLong);
/*      */   
/*      */   static native boolean IsSerif(long paramLong);
/*      */   
/*      */   static native boolean IsSymbolic(long paramLong);
/*      */   
/*      */   static native boolean IsItalic(long paramLong);
/*      */   
/*      */   static native boolean IsAllCap(long paramLong);
/*      */   
/*      */   static native boolean IsForceBold(long paramLong);
/*      */   
/*      */   static native boolean IsHorizontalMode(long paramLong);
/*      */   
/*      */   static native double GetWidth(long paramLong1, long paramLong2);
/*      */   
/*      */   static native double GetMaxWidth(long paramLong);
/*      */   
/*      */   static native double GetMissingWidth(long paramLong);
/*      */   
/*      */   static native long GetCharCodeIterator(long paramLong);
/*      */   
/*      */   static native PathData GetGlyphPath(long paramLong1, long paramLong2, boolean paramBoolean, long paramLong3);
/*      */   
/*      */   static native char[] MapToUnicode(long paramLong1, long paramLong2);
/*      */   
/*      */   static native String[] GetEncoding(long paramLong);
/*      */   
/*      */   static native boolean IsEmbedded(long paramLong);
/*      */   
/*      */   static native String GetEmbeddedFontName(long paramLong);
/*      */   
/*      */   static native long GetEmbeddedFont(long paramLong);
/*      */   
/*      */   static native int GetEmbeddedFontBufSize(long paramLong);
/*      */   
/*      */   static native short GetUnitsPerEm(long paramLong);
/*      */   
/*      */   static native long GetBBox(long paramLong);
/*      */   
/*      */   static native double GetAscent(long paramLong);
/*      */   
/*      */   static native double GetDescent(long paramLong);
/*      */   
/*      */   static native int GetStandardType1FontType(long paramLong);
/*      */   
/*      */   static native boolean IsCFF(long paramLong);
/*      */   
/*      */   static native long GetType3FontMatrix(long paramLong);
/*      */   
/*      */   static native long GetType3GlyphStream(long paramLong1, long paramLong2);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Font.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */