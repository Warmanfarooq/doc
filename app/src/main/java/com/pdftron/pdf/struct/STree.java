/*     */ package com.pdftron.pdf.struct;
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
/*     */ public class STree
/*     */ {
/*     */   private long a;
/*     */   private Object b;
/*     */   
/*     */   public STree(Obj paramObj) {
/*  25 */     this.a = paramObj.__GetHandle();
/*  26 */     this.b = paramObj.__GetRefHandle();
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
/*     */   public boolean isValid() throws PDFNetException {
/*  38 */     return IsValid(this.a);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumKids() throws PDFNetException {
/*  49 */     return GetNumKids(this.a);
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
/*     */   public SElement getKid(int paramInt) throws PDFNetException {
/*  61 */     return new SElement(GetKid(this.a, paramInt), this.b);
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
/*     */   public SElement getElement(String paramString) throws PDFNetException {
/*  73 */     return new SElement(GetElement(this.a, paramString), this.b);
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
/*     */   public SElement getElement(byte[] paramArrayOfbyte) throws PDFNetException {
/*  86 */     return new SElement(GetElement(this.a, paramArrayOfbyte), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RoleMap getRoleMap() throws PDFNetException {
/*  97 */     return new RoleMap(GetRoleMap(this.a), this.b);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClassMap getClassMap() throws PDFNetException {
/* 108 */     return new ClassMap(GetClassMap(this.a), this.b);
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
/* 119 */     return Obj.__Create(this.a, this.b);
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
/*     */   public static STree __Create(long paramLong, Object paramObject) {
/* 131 */     return new STree(paramLong, paramObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   STree(long paramLong, Object paramObject) {
/* 142 */     this.a = paramLong;
/* 143 */     this.b = paramObject;
/*     */   }
/*     */   
/*     */   static native boolean IsValid(long paramLong);
/*     */   
/*     */   static native int GetNumKids(long paramLong);
/*     */   
/*     */   static native long GetKid(long paramLong, int paramInt);
/*     */   
/*     */   static native long GetElement(long paramLong, String paramString);
/*     */   
/*     */   static native long GetElement(long paramLong, byte[] paramArrayOfbyte);
/*     */   
/*     */   static native long GetRoleMap(long paramLong);
/*     */   
/*     */   static native long GetClassMap(long paramLong);
/*     */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\STree.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */