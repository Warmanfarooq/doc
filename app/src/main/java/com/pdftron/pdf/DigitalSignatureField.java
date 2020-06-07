/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DigitalSignatureField
/*     */   extends h
/*     */ {
/*     */   public enum SubFilterType
/*     */   {
/*  24 */     e_adbe_x509_rsa_sha1,
/*  25 */     e_adbe_pkcs7_detached,
/*  26 */     e_adbe_pkcs7_sha1,
/*  27 */     e_ETSI_CAdES_detached,
/*  28 */     e_ETSI_RFC3161,
/*  29 */     e_unknown,
/*  30 */     e_absent;
/*     */   }
/*  32 */   private static final SubFilterType[] a = SubFilterType.values();
/*     */   private long d;
/*     */   private Object e;
/*     */   
/*     */   public enum DocumentPermissions {
/*  37 */     e_no_changes_allowed(1),
/*     */     
/*  39 */     e_formfilling_signing_allowed(2),
/*     */     
/*  41 */     e_annotating_formfilling_signing_allowed(3),
/*     */     
/*  43 */     e_unrestricted(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     final int a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     private static HashMap<Integer, DocumentPermissions> b = new HashMap<>(); static { DocumentPermissions[] arrayOfDocumentPermissions;
/*     */       int i;
/*     */       byte b;
/*  58 */       for (i = (arrayOfDocumentPermissions = values()).length, b = 0; b < i; ) { DocumentPermissions documentPermissions = arrayOfDocumentPermissions[b];
/*     */         
/*  60 */         b.put(Integer.valueOf(documentPermissions.a), documentPermissions);
/*     */         b++; }
/*     */        }
/*     */      DocumentPermissions(int param1Int1) {
/*     */       this.a = param1Int1;
/*     */     } static DocumentPermissions a(int param1Int) {
/*  66 */       return b.get(Integer.valueOf(param1Int));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public enum FieldPermissions
/*     */   {
/*  73 */     e_lock_all,
/*     */     
/*  75 */     e_include,
/*     */     
/*  77 */     e_exclude;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigitalSignatureField(Field paramField) throws PDFNetException {
/*  88 */     this.d = Create(paramField.__GetHandle());
/*  89 */     this.e = paramField.__GetRefHandle();
/*  90 */     clearList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCryptographicSignature() throws PDFNetException {
/* 101 */     return HasCryptographicSignature(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubFilterType getSubFilter() throws PDFNetException {
/* 112 */     return a[GetSubFilter(this.d)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSignatureName() throws PDFNetException {
/* 123 */     return GetSignatureName(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date getSigningTime() throws PDFNetException {
/* 133 */     return new Date(GetSigningTime(this.d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocation() throws PDFNetException {
/* 144 */     return GetLocation(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getReason() throws PDFNetException {
/* 155 */     return GetReason(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContactInfo() throws PDFNetException {
/* 166 */     return GetContactInfo(this.d);
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
/*     */   public byte[] getCert(int paramInt) throws PDFNetException {
/* 178 */     return GetCert(this.d, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCertCount() throws PDFNetException {
/* 189 */     return GetCertCount(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasVisibleAppearance() throws PDFNetException {
/* 200 */     return HasVisibleAppearance(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContactInfo(String paramString) throws PDFNetException {
/* 211 */     SetContactInfo(this.d, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLocation(String paramString) throws PDFNetException {
/* 222 */     SetLocation(this.d, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReason(String paramString) throws PDFNetException {
/* 233 */     SetReason(this.d, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDocumentPermissions(DocumentPermissions paramDocumentPermissions) throws PDFNetException {
/* 244 */     SetDocumentPermissions(this.d, paramDocumentPermissions.a);
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
/*     */   public void setFieldPermissions(FieldPermissions paramFieldPermissions, String[] paramArrayOfString) throws PDFNetException {
/* 256 */     SetFieldPermissions(this.d, paramFieldPermissions.ordinal(), paramArrayOfString);
/*     */   }
/*     */   
/*     */   public void setFieldPermissions(FieldPermissions paramFieldPermissions) throws PDFNetException {
/* 260 */     SetFieldPermissions(this.d, paramFieldPermissions.ordinal());
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
/*     */   public void signOnNextSave(String paramString1, String paramString2) throws PDFNetException {
/* 272 */     SignOnNextSave(this.d, paramString1, paramString2);
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
/*     */   public void signOnNextSave(byte[] paramArrayOfbyte, String paramString) throws PDFNetException {
/* 284 */     SignOnNextSave(this.d, paramArrayOfbyte, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void signOnNextSaveWithCustomHandler(long paramLong) throws PDFNetException {
/* 295 */     SignOnNextSaveWithCustomHandler(this.d, paramLong);
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
/*     */   public void certifyOnNextSave(String paramString1, String paramString2) throws PDFNetException {
/* 307 */     CertifyOnNextSave(this.d, paramString1, paramString2);
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
/*     */   public void certifyOnNextSave(byte[] paramArrayOfbyte, String paramString) throws PDFNetException {
/* 319 */     CertifyOnNextSave(this.d, paramArrayOfbyte, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void certifyOnNextSaveWithCustomHandler(long paramLong) throws PDFNetException {
/* 330 */     CertifyOnNextSaveWithCustomHandler(this.d, paramLong);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obj getSDFObj() throws PDFNetException {
/* 341 */     return Obj.__Create(GetSDFObj(this.d), this.e);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLockedByDigitalSignature() throws PDFNetException {
/* 352 */     return IsLockedByDigitalSignature(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getLockedFields() throws PDFNetException {
/* 363 */     return GetLockedFields(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentPermissions getDocumentPermissions() throws PDFNetException {
/* 374 */     return DocumentPermissions.a(GetDocumentPermissions(this.d));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearSignature() throws PDFNetException {
/* 384 */     ClearSignature(this.d);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerificationResult verify(VerificationOptions paramVerificationOptions) throws PDFNetException {
/* 395 */     return new VerificationResult(Verify(this.d, paramVerificationOptions.__GetHandle()), __GetRefHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 403 */     return this.d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   DigitalSignatureField(long paramLong, Object paramObject) throws PDFNetException {
/* 411 */     this.d = paramLong;
/* 412 */     this.e = paramObject;
/* 413 */     clearList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DigitalSignatureField __Create(long paramLong, Object paramObject) throws PDFNetException {
/* 421 */     if (paramLong == 0L) {
/* 422 */       return null;
/*     */     }
/* 424 */     return new DigitalSignatureField(paramLong, paramObject);
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
/*     */   public void destroy() throws PDFNetException {
/* 437 */     if (this.d != 0L && !(this.e instanceof DigitalSignatureFieldIterator)) {
/*     */       
/* 439 */       Destroy(this.d);
/* 440 */       this.d = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 446 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object __GetRefHandle() {
/* 454 */     return this.e;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long Create(long paramLong);
/*     */   
/*     */   static native boolean HasCryptographicSignature(long paramLong);
/*     */   
/*     */   static native int GetSubFilter(long paramLong);
/*     */   
/*     */   static native String GetSignatureName(long paramLong);
/*     */   
/*     */   static native long GetSigningTime(long paramLong);
/*     */   
/*     */   static native String GetLocation(long paramLong);
/*     */   
/*     */   static native String GetReason(long paramLong);
/*     */   
/*     */   static native String GetContactInfo(long paramLong);
/*     */   
/*     */   static native byte[] GetCert(long paramLong, int paramInt);
/*     */   
/*     */   static native int GetCertCount(long paramLong);
/*     */   
/*     */   static native boolean HasVisibleAppearance(long paramLong);
/*     */   
/*     */   static native void SetContactInfo(long paramLong, String paramString);
/*     */   
/*     */   static native void SetLocation(long paramLong, String paramString);
/*     */   
/*     */   static native void SetReason(long paramLong, String paramString);
/*     */   
/*     */   static native void SetDocumentPermissions(long paramLong, int paramInt);
/*     */   
/*     */   static native void SetFieldPermissions(long paramLong, int paramInt, String[] paramArrayOfString);
/*     */   
/*     */   static native void SetFieldPermissions(long paramLong, int paramInt);
/*     */   
/*     */   static native void SignOnNextSave(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   static native void SignOnNextSave(long paramLong, byte[] paramArrayOfbyte, String paramString);
/*     */   
/*     */   static native void SignOnNextSaveWithCustomHandler(long paramLong1, long paramLong2);
/*     */   
/*     */   static native void CertifyOnNextSave(long paramLong, String paramString1, String paramString2);
/*     */   
/*     */   static native void CertifyOnNextSave(long paramLong, byte[] paramArrayOfbyte, String paramString);
/*     */   
/*     */   static native void CertifyOnNextSaveWithCustomHandler(long paramLong1, long paramLong2);
/*     */   
/*     */   static native long GetSDFObj(long paramLong);
/*     */   
/*     */   static native boolean IsLockedByDigitalSignature(long paramLong);
/*     */   
/*     */   static native String[] GetLockedFields(long paramLong);
/*     */   
/*     */   static native int GetDocumentPermissions(long paramLong);
/*     */   
/*     */   static native void ClearSignature(long paramLong);
/*     */   
/*     */   static native long Verify(long paramLong1, long paramLong2);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\DigitalSignatureField.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */