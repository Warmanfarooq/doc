/*     */ package com.pdftron.sdf;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SecurityHandler
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int e_owner = 1;
/*     */   public static final int e_doc_open = 2;
/*     */   public static final int e_doc_modify = 3;
/*     */   public static final int e_print = 4;
/*     */   public static final int e_print_high = 5;
/*     */   public static final int e_extract_content = 6;
/*     */   public static final int e_mod_annot = 7;
/*     */   public static final int e_fill_forms = 8;
/*     */   public static final int e_access_support = 9;
/*     */   public static final int e_assemble_doc = 10;
/*     */   public static final int e_RC4_40 = 1;
/*     */   public static final int e_RC4_128 = 2;
/*     */   public static final int e_AES = 3;
/*     */   public static final int e_AES_256 = 4;
/*     */   long a;
/*     */   Object b;
/*     */   
/*     */   public boolean getPermission(int paramInt) {
/*  48 */     return GetPermission(this.a, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object clone() {
/*  53 */     return new SecurityHandler(Clone(this.a), null);
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
/*     */   public int getKeyLength() {
/*  65 */     return GetKeyLength(this.a);
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
/*     */   public int getEncryptionAlgorithmID() {
/*  78 */     return GetEncryptionAlgorithmID(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHandlerDocName() {
/*  89 */     return GetHandlerDocName(this.a);
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
/*     */   public boolean isModified() {
/* 104 */     return IsModified(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setModified() {
/* 114 */     SetModified(this.a, true);
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
/*     */   public void setModified(boolean paramBoolean) {
/* 126 */     SetModified(this.a, paramBoolean);
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
/*     */   public SecurityHandler(int paramInt) {
/* 151 */     this.a = SecurityHandlerCreate(paramInt);
/* 152 */     this.b = null;
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
/*     */   public SecurityHandler(int paramInt1, int paramInt2) {
/* 174 */     this.a = SecurityHandlerCreate(paramInt1, paramInt2);
/* 175 */     this.b = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SecurityHandler() {
/* 183 */     this.a = SecurityHandlerCreate();
/* 184 */     this.b = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 192 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 203 */     if (this.a != 0L && this.b == null) {
/*     */       
/* 205 */       Destroy(this.a);
/* 206 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeUserPassword(String paramString) {
/* 217 */     ChangeUserPassword(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeUserPassword(byte[] paramArrayOfbyte) {
/* 227 */     ChangeUserPasswordBuffer(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUserPassword() {
/* 237 */     return GetUserPassword(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeMasterPassword(String paramString) {
/* 247 */     ChangeMasterPassword(this.a, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeMasterPassword(byte[] paramArrayOfbyte) {
/* 257 */     ChangeMasterPasswordBuffer(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMasterPassword() {
/* 267 */     return GetMasterPassword(this.a);
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
/*     */   public void setPermission(int paramInt, boolean paramBoolean) {
/* 288 */     SetPermission(this.a, paramInt, paramBoolean);
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
/*     */   public void changeRevisionNumber(int paramInt) {
/* 305 */     ChangeRevisionNumber(this.a, paramInt);
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
/*     */   public void setEncryptMetadata(boolean paramBoolean) {
/* 321 */     SetEncryptMetadata(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRevisionNumber() {
/* 331 */     return GetRevisionNumber(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUserPasswordRequired() {
/* 341 */     return IsUserPasswordRequired(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMasterPasswordRequired() {
/* 351 */     return IsMasterPasswordRequired(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAES() {
/* 362 */     return IsAES(this.a);
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
/*     */   public boolean isAES(Obj paramObj) {
/* 374 */     return IsAES(this.a, paramObj.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRC4() {
/* 384 */     return IsRC4(this.a);
/*     */   }
/*     */ 
/*     */   
/*     */   private SecurityHandler(long paramLong, Object paramObject) {
/* 389 */     this.a = paramLong;
/* 390 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   public static SecurityHandler __Create(long paramLong, Object paramObject) {
/* 394 */     return new SecurityHandler(paramLong, paramObject);
/*     */   }
/*     */   
/*     */   public long __GetHandle() {
/* 398 */     return this.a;
/*     */   }
/*     */   
/*     */   public void __SetRefHandle(Object paramObject) {
/* 402 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native boolean GetPermission(long paramLong, int paramInt);
/*     */   
/*     */   static native long Clone(long paramLong);
/*     */   
/*     */   static native int GetKeyLength(long paramLong);
/*     */   
/*     */   static native int GetEncryptionAlgorithmID(long paramLong);
/*     */   
/*     */   static native String GetHandlerDocName(long paramLong);
/*     */   
/*     */   static native boolean IsModified(long paramLong);
/*     */   
/*     */   static native void SetModified(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native long SecurityHandlerCreate(int paramInt);
/*     */   
/*     */   static native long SecurityHandlerCreate(int paramInt1, int paramInt2);
/*     */   
/*     */   static native long SecurityHandlerCreate();
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native void ChangeUserPassword(long paramLong, String paramString);
/*     */   
/*     */   static native String GetUserPassword(long paramLong);
/*     */   
/*     */   static native void ChangeMasterPassword(long paramLong, String paramString);
/*     */   
/*     */   static native String GetMasterPassword(long paramLong);
/*     */   
/*     */   static native void SetPermission(long paramLong, int paramInt, boolean paramBoolean);
/*     */   
/*     */   static native void ChangeRevisionNumber(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetEncryptMetadata(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native int GetRevisionNumber(long paramLong);
/*     */   
/*     */   static native boolean IsUserPasswordRequired(long paramLong);
/*     */   
/*     */   static native boolean IsMasterPasswordRequired(long paramLong);
/*     */   
/*     */   static native boolean IsAES(long paramLong);
/*     */   
/*     */   static native boolean IsAES(long paramLong1, long paramLong2);
/*     */   
/*     */   static native boolean IsRC4(long paramLong);
/*     */   
/*     */   static native void ChangeUserPasswordBuffer(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void ChangeMasterPasswordBuffer(long paramLong, byte[] paramArrayOfbyte);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\sdf\SecurityHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */