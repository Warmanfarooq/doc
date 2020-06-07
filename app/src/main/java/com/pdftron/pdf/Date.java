/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ public class Date
/*     */ {
/*     */   long a;
/*     */   
/*     */   public Date() throws PDFNetException {
/*  29 */     this.a = DateCreate();
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
/*     */   public Date(Obj paramObj) throws PDFNetException {
/*  41 */     this.a = DateCreate(paramObj.__GetHandle());
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
/*     */   public Date(short paramShort, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5) throws PDFNetException {
/*  58 */     this.a = DateCreate(paramShort, paramByte1, paramByte2, paramByte3, paramByte4, paramByte5);
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
/*     */   public boolean isValid() throws PDFNetException {
/*  73 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  81 */     if (paramObject != null && paramObject.getClass().equals(getClass()))
/*     */     {
/*  83 */       return Equals(this.a, ((Date)paramObject).a);
/*     */     }
/*     */     
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  95 */     return HashCode(this.a);
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
/*     */   public void attach(Obj paramObj) throws PDFNetException {
/* 109 */     Attach(this.a, paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean update() throws PDFNetException {
/* 120 */     return Update(this.a, 0L);
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
/*     */   public boolean update(Obj paramObj) throws PDFNetException {
/* 134 */     return Update(this.a, paramObj.__GetHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentTime() {
/* 142 */     SetCurrentTime(this.a);
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
/*     */   public short getYear() throws PDFNetException {
/* 154 */     return GetYear(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getMonth() throws PDFNetException {
/* 165 */     return GetMonth(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getDay() throws PDFNetException {
/* 176 */     return GetDay(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getHour() throws PDFNetException {
/* 187 */     return GetHour(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getMinute() throws PDFNetException {
/* 198 */     return GetMinute(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getSecond() throws PDFNetException {
/* 209 */     return GetSecond(this.a);
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
/*     */   public byte getUT() throws PDFNetException {
/* 222 */     return GetUT(this.a);
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
/*     */   public byte getUTHour() throws PDFNetException {
/* 234 */     return GetUTHour(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getUTMinutes() throws PDFNetException {
/* 245 */     return GetUTMinutes(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setYear(short paramShort) throws PDFNetException {
/* 256 */     SetYear(this.a, paramShort);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMonth(byte paramByte) throws PDFNetException {
/* 267 */     SetMonth(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDay(byte paramByte) throws PDFNetException {
/* 278 */     SetDay(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHour(byte paramByte) throws PDFNetException {
/* 289 */     SetHour(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMinute(byte paramByte) throws PDFNetException {
/* 300 */     SetMinute(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSecond(byte paramByte) throws PDFNetException {
/* 311 */     SetSecond(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUT(byte paramByte) throws PDFNetException {
/* 322 */     SetUT(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setUTHour(byte paramByte) throws PDFNetException {
/* 333 */     SetUTHour(this.a, paramByte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void seUTMinutes(byte paramByte) throws PDFNetException {
/* 344 */     SetUTMinutes(this.a, paramByte);
/*     */   }
/*     */ 
/*     */   
/*     */   Date(long paramLong) {
/* 349 */     this.a = paramLong;
/*     */   }
/*     */   
/*     */   public static Date __Create(long paramLong) {
/* 353 */     if (paramLong == 0L) {
/* 354 */       return null;
/*     */     }
/* 356 */     return new Date(paramLong);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 360 */     return this.a;
/*     */   }
/*     */   
/*     */   static native long DateCreate();
/*     */   
/*     */   static native long DateCreate(long paramLong);
/*     */   
/*     */   static native long DateCreate(short paramShort, byte paramByte1, byte paramByte2, byte paramByte3, byte paramByte4, byte paramByte5);
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native boolean Equals(long paramLong1, long paramLong2);
/*     */   
/*     */   static native int HashCode(long paramLong);
/*     */   
/*     */   static native void Attach(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean Update(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean SetCurrentTime(long paramLong);
/*     */   
/*     */   static native short GetYear(long paramLong);
/*     */   
/*     */   static native byte GetMonth(long paramLong);
/*     */   
/*     */   static native byte GetDay(long paramLong);
/*     */   
/*     */   static native byte GetHour(long paramLong);
/*     */   
/*     */   static native byte GetMinute(long paramLong);
/*     */   
/*     */   static native byte GetSecond(long paramLong);
/*     */   
/*     */   static native byte GetUT(long paramLong);
/*     */   
/*     */   static native byte GetUTHour(long paramLong);
/*     */   
/*     */   static native byte GetUTMinutes(long paramLong);
/*     */   
/*     */   static native void SetYear(long paramLong, short paramShort);
/*     */   
/*     */   static native void SetMonth(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetDay(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetHour(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetMinute(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetSecond(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetUT(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetUTHour(long paramLong, byte paramByte);
/*     */   
/*     */   static native void SetUTMinutes(long paramLong, byte paramByte);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\Date.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */