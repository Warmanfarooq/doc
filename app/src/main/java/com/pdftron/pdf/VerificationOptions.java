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
/*     */ public class VerificationOptions
/*     */ {
/*     */   private long a;
/*     */   
/*     */   public enum SecurityLevel
/*     */   {
/*  20 */     e_compatibility_and_archiving(0),
/*  21 */     e_maximum(1);
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
/*  33 */     private static HashMap<Integer, SecurityLevel> b = new HashMap<>(); static { SecurityLevel[] arrayOfSecurityLevel;
/*     */       int i;
/*     */       byte b;
/*  36 */       for (i = (arrayOfSecurityLevel = values()).length, b = 0; b < i; ) { SecurityLevel securityLevel = arrayOfSecurityLevel[b];
/*     */         
/*  38 */         b.put(Integer.valueOf(securityLevel.a), securityLevel);
/*     */         b++; }
/*     */        }
/*     */ 
/*     */     
/*     */     SecurityLevel(int param1Int1) {
/*     */       this.a = param1Int1;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TimeMode
/*     */   {
/*  50 */     e_signing(0),
/*  51 */     e_timestamp(1),
/*  52 */     e_current(2);
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
/*  64 */     private static HashMap<Integer, TimeMode> b = new HashMap<>(); static { TimeMode[] arrayOfTimeMode;
/*     */       int i;
/*     */       byte b;
/*  67 */       for (i = (arrayOfTimeMode = values()).length, b = 0; b < i; ) { TimeMode timeMode = arrayOfTimeMode[b];
/*     */         
/*  69 */         b.put(Integer.valueOf(timeMode.a), timeMode);
/*     */         b++; }
/*     */        }
/*     */      TimeMode(int param1Int1) {
/*     */       this.a = param1Int1;
/*     */     } static TimeMode a(int param1Int) {
/*  75 */       return b.get(Integer.valueOf(param1Int));
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
/*  89 */     if (this.a != 0L) {
/*     */       
/*  91 */       Destroy(this.a);
/*  92 */       this.a = 0L;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 101 */     destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerificationOptions(long paramLong) {
/* 109 */     this.a = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VerificationOptions(SecurityLevel paramSecurityLevel) throws PDFNetException {
/* 120 */     this.a = Create(paramSecurityLevel.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTrustedCertificate(byte[] paramArrayOfbyte) throws PDFNetException {
/* 131 */     AddTrustedCertificate(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrustedCertificate(byte[] paramArrayOfbyte) throws PDFNetException {
/* 142 */     RemoveTrustedCertificate(this.a, paramArrayOfbyte);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableModificationVerification(boolean paramBoolean) throws PDFNetException {
/* 153 */     EnableModificationVerification(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableDigestVerification(boolean paramBoolean) throws PDFNetException {
/* 164 */     EnableDigestVerification(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableTrustVerification(boolean paramBoolean) throws PDFNetException {
/* 175 */     EnableTrustVerification(this.a, paramBoolean);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long __GetHandle() {
/* 183 */     return this.a;
/*     */   }
/*     */   
/*     */   static native void Destroy(long paramLong);
/*     */   
/*     */   static native long Create(int paramInt);
/*     */   
/*     */   static native void AddTrustedCertificate(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void RemoveTrustedCertificate(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native void EnableModificationVerification(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void EnableDigestVerification(long paramLong, boolean paramBoolean);
/*     */   
/*     */   static native void EnableTrustVerification(long paramLong, boolean paramBoolean);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\VerificationOptions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */