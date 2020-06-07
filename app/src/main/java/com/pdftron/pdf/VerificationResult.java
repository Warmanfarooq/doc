/*     */ package com.pdftron.pdf;
/*     */ 
/*     */ import com.pdftron.common.PDFNetException;
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
/*     */ public class VerificationResult
/*     */ {
/*     */   private Object a;
/*     */   private long b;
/*     */   
/*     */   public enum DocumentStatus
/*     */   {
/*  22 */     e_no_error(0),
/*  23 */     e_corrupt_file(1),
/*  24 */     e_unsigned(2),
/*  25 */     e_bad_byteranges(3),
/*  26 */     e_corrupt_cryptographic_contents(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  38 */     private static HashMap<Integer, DocumentStatus> b = new HashMap<>(); static { DocumentStatus[] arrayOfDocumentStatus;
/*     */       int i;
/*     */       byte b;
/*  41 */       for (i = (arrayOfDocumentStatus = values()).length, b = 0; b < i; ) { DocumentStatus documentStatus = arrayOfDocumentStatus[b];
/*     */         
/*  43 */         b.put(Integer.valueOf(documentStatus.a), documentStatus);
/*     */         b++; }
/*     */        }
/*     */      DocumentStatus(int param1Int1) {
/*     */       this.a = param1Int1;
/*     */     } static DocumentStatus a(int param1Int) {
/*  49 */       return b.get(Integer.valueOf(param1Int));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum DigestStatus
/*     */   {
/*  55 */     e_digest_invalid(0),
/*  56 */     e_digest_verified(1),
/*  57 */     e_digest_verification_disabled(2),
/*  58 */     e_weak_digest_algorithm_but_digest_verifiable(3),
/*  59 */     e_no_digest_status(4),
/*  60 */     e_unsupported_encoding(5);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     private static HashMap<Integer, DigestStatus> b = new HashMap<>(); DigestStatus(int param1Int1) { this.a = param1Int1; }
/*     */     static { DigestStatus[] arrayOfDigestStatus; int i;
/*     */       byte b;
/*  75 */       for (i = (arrayOfDigestStatus = values()).length, b = 0; b < i; ) { DigestStatus digestStatus = arrayOfDigestStatus[b];
/*     */         
/*  77 */         b.put(Integer.valueOf(digestStatus.a), digestStatus);
/*     */         b++; }
/*     */        }
/*     */ 
/*     */     
/*     */     static DigestStatus a(int param1Int) {
/*  83 */       return b.get(Integer.valueOf(param1Int));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TrustStatus
/*     */   {
/*  89 */     e_trust_verified(0),
/*  90 */     e_untrusted(1),
/*  91 */     e_trust_verification_disabled(2),
/*  92 */     e_no_trust_status(3);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 104 */     private static HashMap<Integer, TrustStatus> b = new HashMap<>(); TrustStatus(int param1Int1) { this.a = param1Int1; }
/*     */     static { TrustStatus[] arrayOfTrustStatus; int i;
/*     */       byte b;
/* 107 */       for (i = (arrayOfTrustStatus = values()).length, b = 0; b < i; ) { TrustStatus trustStatus = arrayOfTrustStatus[b];
/*     */         
/* 109 */         b.put(Integer.valueOf(trustStatus.a), trustStatus);
/*     */         b++; }
/*     */        }
/*     */ 
/*     */     
/*     */     static TrustStatus a(int param1Int) {
/* 115 */       return b.get(Integer.valueOf(param1Int));
/*     */     }
/*     */   }
/*     */   
/*     */   public enum ModificationPermissionsStatus
/*     */   {
/* 121 */     e_invalidated_by_disallowed_changes(0),
/* 122 */     e_has_allowed_changes(1),
/* 123 */     e_unmodified(2),
/* 124 */     e_permissions_verification_disabled(3),
/* 125 */     e_no_permissions_status(4);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private int a;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     private static HashMap<Integer, ModificationPermissionsStatus> b = new HashMap<>(); ModificationPermissionsStatus(int param1Int1) { this.a = param1Int1; }
/*     */     static { ModificationPermissionsStatus[] arrayOfModificationPermissionsStatus; int i;
/*     */       byte b;
/* 140 */       for (i = (arrayOfModificationPermissionsStatus = values()).length, b = 0; b < i; ) { ModificationPermissionsStatus modificationPermissionsStatus = arrayOfModificationPermissionsStatus[b];
/*     */         
/* 142 */         b.put(Integer.valueOf(modificationPermissionsStatus.a), modificationPermissionsStatus);
/*     */         b++; }
/*     */        }
/*     */ 
/*     */     
/*     */     static ModificationPermissionsStatus a(int param1Int) {
/* 148 */       return b.get(Integer.valueOf(param1Int));
/*     */     }
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
/* 162 */     if (this.b != 0L) {
/*     */       
/* 164 */       Destroy(this.b);
/* 165 */       this.b = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 174 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerificationResult(long paramLong, Object paramObject) {
/* 182 */     this.b = paramLong;
/* 183 */     this.a = paramObject;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigitalSignatureField getDigitalSignatureField() throws PDFNetException {
/* 194 */     return DigitalSignatureField.__Create(GetDigitalSignatureField(__GetHandle()), __GetRefHandle());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getVerificationStatus() throws PDFNetException {
/* 205 */     return GetVerificationStatus(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DocumentStatus getDocumentStatus() throws PDFNetException {
/* 216 */     return DocumentStatus.a(GetDocumentStatus(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestStatus getDigestStatus() throws PDFNetException {
/* 227 */     return DigestStatus.a(GetDigestStatus(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustStatus getTrustStatus() throws PDFNetException {
/* 238 */     return TrustStatus.a(GetTrustStatus(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ModificationPermissionsStatus getPermissionsStatus() throws PDFNetException {
/* 249 */     return ModificationPermissionsStatus.a(GetPermissionsStatus(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasTrustVerificationResult() throws PDFNetException {
/* 260 */     return HasTrustVerificationResult(this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TrustVerificationResult getTrustVerificationResult() throws PDFNetException {
/* 271 */     return new TrustVerificationResult(GetTrustVerificationResult(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DisallowedChange[] getDisallowedChanges() throws PDFNetException {
/*     */     long[] arrayOfLong;
/* 283 */     DisallowedChange[] arrayOfDisallowedChange = new DisallowedChange[(arrayOfLong = GetDisallowedChanges(this.b)).length];
/* 284 */     for (byte b = 0; b < arrayOfLong.length; b++)
/*     */     {
/* 286 */       arrayOfDisallowedChange[b] = new DisallowedChange(arrayOfLong[b]);
/*     */     }
/* 288 */     return arrayOfDisallowedChange;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DigestAlgorithm getSignersDigestAlgorithm() throws PDFNetException {
/* 299 */     return DigestAlgorithm.a(GetSignersDigestAlgorithm(this.b));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 306 */     return this.b;
/*     */   }
/*     */ 
/*     */   
/*     */   public Object __GetRefHandle() {
/* 311 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long GetDigitalSignatureField(long paramLong);
/*     */   
/*     */   static native boolean GetVerificationStatus(long paramLong);
/*     */   
/*     */   static native int GetDocumentStatus(long paramLong);
/*     */   
/*     */   static native int GetDigestStatus(long paramLong);
/*     */   
/*     */   static native int GetTrustStatus(long paramLong);
/*     */   
/*     */   static native int GetPermissionsStatus(long paramLong);
/*     */   
/*     */   static native boolean HasTrustVerificationResult(long paramLong);
/*     */   
/*     */   static native long GetTrustVerificationResult(long paramLong);
/*     */   
/*     */   static native long[] GetDisallowedChanges(long paramLong);
/*     */   
/*     */   static native int GetSignersDigestAlgorithm(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\VerificationResult.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */