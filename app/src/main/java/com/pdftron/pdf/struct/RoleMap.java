/*    */ package com.pdftron.pdf.struct;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.sdf.Obj;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RoleMap
/*    */ {
/*    */   private long a;
/*    */   private Object b;
/*    */   
/*    */   public RoleMap(Obj paramObj) {
/* 25 */     this.a = paramObj.__GetHandle();
/* 26 */     this.b = paramObj.__GetRefHandle();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isValid() throws PDFNetException {
/* 39 */     return IsValid(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDirectMap(String paramString) throws PDFNetException {
/* 52 */     return GetDirectMap(this.a, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Obj getSDFObj() {
/* 61 */     return Obj.__Create(this.a, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   RoleMap(long paramLong, Object paramObject) {
/* 66 */     this.a = paramLong;
/* 67 */     this.b = paramObject;
/*    */   }
/*    */   
/*    */   static native boolean IsValid(long paramLong);
/*    */   
/*    */   static native String GetDirectMap(long paramLong, String paramString);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\RoleMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */