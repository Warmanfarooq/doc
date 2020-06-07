/*      */ package com.pdftron.sdf;
/*      */ 
/*      */ import com.pdftron.common.Matrix2D;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.filters.Filter;
/*      */ import com.pdftron.filters.FilterWriter;
/*      */ import com.pdftron.helpers.DoubleRectangle2D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Obj
/*      */ {
/*      */   public static final int e_null = 0;
/*      */   public static final int e_bool = 1;
/*      */   public static final int e_number = 2;
/*      */   public static final int e_name = 3;
/*      */   public static final int e_string = 4;
/*      */   public static final int e_dict = 5;
/*      */   public static final int e_array = 6;
/*      */   public static final int e_stream = 7;
/*      */   long a;
/*      */   Object b;
/*      */   
/*      */   public int getType() throws PDFNetException {
/*   62 */     return GetType(this.a);
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
/*      */   public boolean isBool() throws PDFNetException {
/*   74 */     return IsBool(this.a);
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
/*      */   public boolean isNumber() throws PDFNetException {
/*   86 */     return IsNumber(this.a);
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
/*      */   public boolean isNull() throws PDFNetException {
/*   98 */     return IsNull(this.a);
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
/*      */   public boolean isString() throws PDFNetException {
/*  110 */     return IsString(this.a);
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
/*      */   public boolean isName() throws PDFNetException {
/*  122 */     return IsName(this.a);
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
/*      */   public boolean isIndirect() throws PDFNetException {
/*  135 */     return IsIndirect(this.a);
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
/*      */   public boolean isContainer() throws PDFNetException {
/*  148 */     return IsContainer(this.a);
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
/*      */   public boolean isDict() throws PDFNetException {
/*  160 */     return IsDict(this.a);
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
/*      */   public boolean isArray() throws PDFNetException {
/*  172 */     return IsArray(this.a);
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
/*      */   public boolean isStream() throws PDFNetException {
/*  185 */     return IsStream(this.a);
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
/*      */   public SDFDoc getDoc() throws PDFNetException {
/*  197 */     return new SDFDoc(GetDoc(this.a), this.b);
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
/*      */   public void write(FilterWriter paramFilterWriter) throws PDFNetException {
/*  209 */     Write(this.a, paramFilterWriter.__GetHandle());
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
/*      */   public long size() throws PDFNetException {
/*  229 */     return Size(this.a);
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
/*      */   public long getObjNum() throws PDFNetException {
/*  244 */     return GetObjNum(this.a);
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
/*      */   public short getGenNum() throws PDFNetException {
/*  257 */     return GetGenNum(this.a);
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
/*      */   public long getOffset() throws PDFNetException {
/*  270 */     return GetOffset(this.a);
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
/*      */   public boolean isFree() throws PDFNetException {
/*  282 */     return IsFree(this.a);
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
/*      */   public void setMark(boolean paramBoolean) throws PDFNetException {
/*  296 */     SetMark(this.a, paramBoolean);
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
/*      */   public boolean isMarked() throws PDFNetException {
/*  308 */     return IsMarked(this.a);
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
/*      */   public boolean isLoaded() throws PDFNetException {
/*  320 */     return IsLoaded(this.a);
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
/*      */   public boolean isEqual(Obj paramObj) throws PDFNetException {
/*  333 */     return IsEqual(this.a, paramObj.a);
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
/*      */   public boolean getBool() throws PDFNetException {
/*  345 */     return GetBool(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBool(boolean paramBoolean) throws PDFNetException {
/*  355 */     SetBool(this.a, paramBoolean);
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
/*      */   public double getNumber() throws PDFNetException {
/*  367 */     return GetNumber(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNumber(double paramDouble) throws PDFNetException {
/*  377 */     SetNumber(this.a, paramDouble);
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
/*      */   public byte[] getBuffer() throws PDFNetException {
/*  397 */     return GetBuffer(this.a);
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
/*      */   public byte[] getRawBuffer() throws PDFNetException {
/*  409 */     return GetRawBuffer(this.a);
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
/*      */   public String getAsPDFText() throws PDFNetException {
/*  429 */     return GetAsPDFText(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(byte[] paramArrayOfbyte) throws PDFNetException {
/*  439 */     SetString(this.a, paramArrayOfbyte);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setString(String paramString) throws PDFNetException {
/*  449 */     SetString(this.a, paramString);
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
/*      */   public String getName() throws PDFNetException {
/*  461 */     return GetName(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setName(String paramString) throws PDFNetException {
/*  471 */     SetName(this.a, paramString);
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
/*      */   public DictIterator getDictIterator() throws PDFNetException {
/*  492 */     return new DictIterator(GetDictIterator(this.a), this.b);
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
/*      */   public DictIterator find(String paramString) throws PDFNetException {
/*  515 */     return new DictIterator(Find(this.a, paramString), this.b);
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
/*      */   public Obj findObj(String paramString) throws PDFNetException {
/*  530 */     return __Create(FindObj(this.a, paramString), this.b);
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
/*      */   public DictIterator get(String paramString) throws PDFNetException {
/*  542 */     return new DictIterator(Get(this.a, paramString), this.b);
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
/*      */   public Obj putName(String paramString1, String paramString2) throws PDFNetException {
/*  558 */     return __Create(PutName(this.a, paramString1, paramString2), this.b);
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
/*      */   public Obj putArray(String paramString) throws PDFNetException {
/*  572 */     return __Create(PutArray(this.a, paramString), this.b);
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
/*      */   public Obj putBool(String paramString, boolean paramBoolean) throws PDFNetException {
/*  588 */     return __Create(PutBool(this.a, paramString, paramBoolean), this.b);
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
/*      */   public Obj putDict(String paramString) throws PDFNetException {
/*  602 */     return __Create(PutDict(this.a, paramString), this.b);
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
/*      */   public Obj putNumber(String paramString, double paramDouble) throws PDFNetException {
/*  618 */     return __Create(PutNumber(this.a, paramString, paramDouble), this.b);
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
/*      */   public Obj putString(String paramString1, String paramString2) throws PDFNetException {
/*  634 */     return __Create(PutString(this.a, paramString1, paramString2), this.b);
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
/*      */   public Obj putString(String paramString, byte[] paramArrayOfbyte) throws PDFNetException {
/*  650 */     return __Create(PutString(this.a, paramString, paramArrayOfbyte), this.b);
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
/*      */   public Obj putText(String paramString1, String paramString2) throws PDFNetException {
/*  667 */     return __Create(PutText(this.a, paramString1, paramString2), this.b);
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
/*      */   public void putNull(String paramString) throws PDFNetException {
/*  679 */     PutNull(this.a, paramString);
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
/*      */   public Obj put(String paramString, Obj paramObj) throws PDFNetException {
/*  693 */     return __Create(Put(this.a, paramString, paramObj.a), this.b);
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
/*      */   public Obj putRect(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/*  711 */     return __Create(PutRect(this.a, paramString, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this.b);
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
/*      */   public Obj putRect(String paramString, DoubleRectangle2D paramDoubleRectangle2D) throws PDFNetException {
/*  727 */     return __Create(PutRect(this.a, paramString, paramDoubleRectangle2D.x, paramDoubleRectangle2D.y, paramDoubleRectangle2D.x + paramDoubleRectangle2D.width, paramDoubleRectangle2D.y + paramDoubleRectangle2D.height), this.b);
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
/*      */   public Obj putMatrix(String paramString, Matrix2D paramMatrix2D) throws PDFNetException {
/*  743 */     return __Create(PutMatrix(this.a, paramString, paramMatrix2D.__GetHandle()), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void erase(String paramString) throws PDFNetException {
/*  753 */     Erase(this.a, paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void erase(DictIterator paramDictIterator) throws PDFNetException {
/*  763 */     Erase(this.a, paramDictIterator.__GetHandle());
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
/*      */   public boolean rename(String paramString1, String paramString2) throws PDFNetException {
/*  777 */     return Rename(this.a, paramString1, paramString2);
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
/*      */   public Obj getAt(int paramInt) throws PDFNetException {
/*  790 */     return __Create(GetAt(this.a, paramInt), this.b);
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
/*      */   public Obj insertName(int paramInt, String paramString) throws PDFNetException {
/*  804 */     return __Create(InsertName(this.a, paramInt, paramString), this.b);
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
/*      */   public Obj insertArray(int paramInt) throws PDFNetException {
/*  817 */     return __Create(InsertArray(this.a, paramInt), this.b);
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
/*      */   public Obj insertBool(int paramInt, boolean paramBoolean) throws PDFNetException {
/*  831 */     return __Create(InsertBool(this.a, paramInt, paramBoolean), this.b);
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
/*      */   public Obj insertDict(int paramInt) throws PDFNetException {
/*  844 */     return __Create(InsertDict(this.a, paramInt), this.b);
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
/*      */   public Obj insertNumber(int paramInt, double paramDouble) throws PDFNetException {
/*  858 */     return __Create(InsertNumber(this.a, paramInt, paramDouble), this.b);
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
/*      */   public Obj insertString(int paramInt, String paramString) throws PDFNetException {
/*  872 */     return __Create(InsertString(this.a, paramInt, paramString), this.b);
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
/*      */   public Obj insertString(int paramInt, byte[] paramArrayOfbyte) throws PDFNetException {
/*  887 */     return __Create(InsertString(this.a, paramInt, paramArrayOfbyte), this.b);
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
/*      */   public Obj insertText(int paramInt, String paramString) throws PDFNetException {
/*  903 */     return __Create(InsertText(this.a, paramInt, paramString), this.b);
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
/*      */   public Obj insertNull(int paramInt) throws PDFNetException {
/*  916 */     return __Create(InsertNull(this.a, paramInt), this.b);
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
/*      */   public Obj insert(int paramInt, Obj paramObj) throws PDFNetException {
/*  932 */     return __Create(Insert(this.a, paramInt, paramObj.a), this.b);
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
/*      */   public Obj insertRect(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/*  949 */     return __Create(InsertRect(this.a, paramInt, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this.b);
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
/*      */   public Obj insertRect(int paramInt, DoubleRectangle2D paramDoubleRectangle2D) throws PDFNetException {
/*  964 */     return __Create(InsertRect(this.a, paramInt, paramDoubleRectangle2D.x, paramDoubleRectangle2D.y, paramDoubleRectangle2D.x + paramDoubleRectangle2D.width, paramDoubleRectangle2D.y + paramDoubleRectangle2D.height), this.b);
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
/*      */   public Obj insertMatrix(int paramInt, Matrix2D paramMatrix2D) throws PDFNetException {
/*  979 */     return __Create(InsertMatrix(this.a, paramInt, paramMatrix2D.__GetHandle()), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackName(String paramString) throws PDFNetException {
/*  990 */     return __Create(PushBackName(this.a, paramString), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackArray() throws PDFNetException {
/* 1000 */     return __Create(PushBackArray(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackBool(boolean paramBoolean) throws PDFNetException {
/* 1011 */     return __Create(PushBackBool(this.a, paramBoolean), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackDict() throws PDFNetException {
/* 1021 */     return __Create(PushBackDict(this.a), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackNumber(double paramDouble) throws PDFNetException {
/* 1032 */     return __Create(PushBackNumber(this.a, paramDouble), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackString(String paramString) throws PDFNetException {
/* 1043 */     return __Create(PushBackString(this.a, paramString), this.b);
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
/*      */   public Obj pushBackString(byte[] paramArrayOfbyte) throws PDFNetException {
/* 1055 */     return __Create(PushBackString(this.a, paramArrayOfbyte), this.b);
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
/*      */   public Obj pushBackText(String paramString) throws PDFNetException {
/* 1068 */     return __Create(PushBackText(this.a, paramString), this.b);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Obj pushBackNull() throws PDFNetException {
/* 1078 */     return __Create(PushBackNull(this.a), this.b);
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
/*      */   public Obj pushBack(Obj paramObj) throws PDFNetException {
/* 1091 */     return __Create(PushBack(this.a, paramObj.a), this.b);
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
/*      */   public Obj pushBackRect(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) throws PDFNetException {
/* 1105 */     return __Create(PushBackRect(this.a, paramDouble1, paramDouble2, paramDouble3, paramDouble4), this.b);
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
/*      */   public Obj pushBackRect(DoubleRectangle2D paramDoubleRectangle2D) throws PDFNetException {
/* 1117 */     return __Create(PushBackRect(this.a, paramDoubleRectangle2D.x, paramDoubleRectangle2D.y, paramDoubleRectangle2D.x + paramDoubleRectangle2D.width, paramDoubleRectangle2D.y + paramDoubleRectangle2D.height), this.b);
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
/*      */   public Obj pushBackMatrix(Matrix2D paramMatrix2D) throws PDFNetException {
/* 1129 */     return __Create(PushBackMatrix(this.a, paramMatrix2D.__GetHandle()), this.b);
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
/*      */   public void eraseAt(int paramInt) throws PDFNetException {
/* 1141 */     EraseAt(this.a, paramInt);
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
/*      */   public long getRawStreamLength() throws PDFNetException {
/* 1153 */     return GetRawStreamLength(this.a);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Filter getRawStream(boolean paramBoolean) throws PDFNetException {
/* 1164 */     return Filter.__Create(GetRawStream(this.a, paramBoolean), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Filter getDecodedStream() throws PDFNetException {
/* 1174 */     return Filter.__Create(GetDecodedStream(this.a), null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStreamData(byte[] paramArrayOfbyte, Filter paramFilter) throws PDFNetException {
/* 1185 */     if (paramFilter != null) paramFilter.__SetRefHandle(this); 
/* 1186 */     SetStreamData(this.a, paramArrayOfbyte, paramFilter.__GetHandle());
/*      */   }
/*      */   
/*      */   private Obj(long paramLong, Object paramObject) {
/* 1190 */     this.a = paramLong;
/* 1191 */     this.b = paramObject;
/*      */   }
/*      */   public static Obj __Create(long paramLong, Object paramObject) {
/* 1194 */     if (paramLong == 0L) return null; 
/* 1195 */     return new Obj(paramLong, paramObject);
/*      */   }
/*      */   public long __GetHandle() {
/* 1198 */     return this.a;
/*      */   }
/*      */   public Object __GetRefHandle() {
/* 1201 */     return this.b;
/*      */   }
/*      */   
/*      */   static native int GetType(long paramLong);
/*      */   
/*      */   static native long GetDoc(long paramLong);
/*      */   
/*      */   static native void Write(long paramLong1, long paramLong2);
/*      */   
/*      */   static native boolean IsEqual(long paramLong1, long paramLong2);
/*      */   
/*      */   static native boolean IsBool(long paramLong);
/*      */   
/*      */   static native boolean GetBool(long paramLong);
/*      */   
/*      */   static native void SetBool(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native boolean IsNumber(long paramLong);
/*      */   
/*      */   static native double GetNumber(long paramLong);
/*      */   
/*      */   static native void SetNumber(long paramLong, double paramDouble);
/*      */   
/*      */   static native boolean IsNull(long paramLong);
/*      */   
/*      */   static native boolean IsString(long paramLong);
/*      */   
/*      */   static native byte[] GetBuffer(long paramLong);
/*      */   
/*      */   static native byte[] GetRawBuffer(long paramLong);
/*      */   
/*      */   static native String GetAsPDFText(long paramLong);
/*      */   
/*      */   static native void SetString(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native void SetString(long paramLong, String paramString);
/*      */   
/*      */   static native boolean IsName(long paramLong);
/*      */   
/*      */   static native String GetName(long paramLong);
/*      */   
/*      */   static native void SetName(long paramLong, String paramString);
/*      */   
/*      */   static native boolean IsIndirect(long paramLong);
/*      */   
/*      */   static native long GetObjNum(long paramLong);
/*      */   
/*      */   static native short GetGenNum(long paramLong);
/*      */   
/*      */   static native long GetOffset(long paramLong);
/*      */   
/*      */   static native boolean IsFree(long paramLong);
/*      */   
/*      */   static native void SetMark(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native boolean IsMarked(long paramLong);
/*      */   
/*      */   static native boolean IsLoaded(long paramLong);
/*      */   
/*      */   static native boolean IsContainer(long paramLong);
/*      */   
/*      */   static native long Size(long paramLong);
/*      */   
/*      */   static native long GetDictIterator(long paramLong);
/*      */   
/*      */   static native boolean IsDict(long paramLong);
/*      */   
/*      */   static native long Find(long paramLong, String paramString);
/*      */   
/*      */   static native long FindObj(long paramLong, String paramString);
/*      */   
/*      */   static native long Get(long paramLong, String paramString);
/*      */   
/*      */   static native long PutName(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native long PutArray(long paramLong, String paramString);
/*      */   
/*      */   static native long PutBool(long paramLong, String paramString, boolean paramBoolean);
/*      */   
/*      */   static native long PutDict(long paramLong, String paramString);
/*      */   
/*      */   static native long PutNumber(long paramLong, String paramString, double paramDouble);
/*      */   
/*      */   static native long PutString(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native long PutString(long paramLong, String paramString, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long PutText(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native void PutNull(long paramLong, String paramString);
/*      */   
/*      */   static native long Put(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native long PutRect(long paramLong, String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   static native long PutMatrix(long paramLong1, String paramString, long paramLong2);
/*      */   
/*      */   static native void Erase(long paramLong, String paramString);
/*      */   
/*      */   static native void Erase(long paramLong1, long paramLong2);
/*      */   
/*      */   static native boolean Rename(long paramLong, String paramString1, String paramString2);
/*      */   
/*      */   static native boolean IsArray(long paramLong);
/*      */   
/*      */   static native long GetAt(long paramLong, int paramInt);
/*      */   
/*      */   static native long InsertName(long paramLong, int paramInt, String paramString);
/*      */   
/*      */   static native long InsertArray(long paramLong, int paramInt);
/*      */   
/*      */   static native long InsertBool(long paramLong, int paramInt, boolean paramBoolean);
/*      */   
/*      */   static native long InsertDict(long paramLong, int paramInt);
/*      */   
/*      */   static native long InsertNumber(long paramLong, int paramInt, double paramDouble);
/*      */   
/*      */   static native long InsertString(long paramLong, int paramInt, String paramString);
/*      */   
/*      */   static native long InsertString(long paramLong, int paramInt, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long InsertText(long paramLong, int paramInt, String paramString);
/*      */   
/*      */   static native long InsertNull(long paramLong, int paramInt);
/*      */   
/*      */   static native long Insert(long paramLong1, int paramInt, long paramLong2);
/*      */   
/*      */   static native long InsertRect(long paramLong, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   static native long InsertMatrix(long paramLong1, int paramInt, long paramLong2);
/*      */   
/*      */   static native long PushBackName(long paramLong, String paramString);
/*      */   
/*      */   static native long PushBackArray(long paramLong);
/*      */   
/*      */   static native long PushBackBool(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native long PushBackDict(long paramLong);
/*      */   
/*      */   static native long PushBackNumber(long paramLong, double paramDouble);
/*      */   
/*      */   static native long PushBackString(long paramLong, String paramString);
/*      */   
/*      */   static native long PushBackString(long paramLong, byte[] paramArrayOfbyte);
/*      */   
/*      */   static native long PushBackText(long paramLong, String paramString);
/*      */   
/*      */   static native long PushBackNull(long paramLong);
/*      */   
/*      */   static native long PushBack(long paramLong1, long paramLong2);
/*      */   
/*      */   static native long PushBackRect(long paramLong, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);
/*      */   
/*      */   static native long PushBackMatrix(long paramLong1, long paramLong2);
/*      */   
/*      */   static native void EraseAt(long paramLong, int paramInt);
/*      */   
/*      */   static native boolean IsStream(long paramLong);
/*      */   
/*      */   static native long GetRawStreamLength(long paramLong);
/*      */   
/*      */   static native long GetRawStream(long paramLong, boolean paramBoolean);
/*      */   
/*      */   static native long GetDecodedStream(long paramLong);
/*      */   
/*      */   static native void SetStreamData(long paramLong1, byte[] paramArrayOfbyte, long paramLong2);
/*      */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\Obj.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */