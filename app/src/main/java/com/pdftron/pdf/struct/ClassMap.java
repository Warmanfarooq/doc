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
/*    */ public class ClassMap
/*    */ {
/*    */   private long a;
/*    */   private Object b;
/*    */   
/*    */   public ClassMap(Obj paramObj) {
/* 22 */     this.a = paramObj.__GetHandle();
/* 23 */     this.b = paramObj.__GetRefHandle();
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
/*    */   
/*    */   public boolean isValid() throws PDFNetException {
/* 37 */     return IsValid(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Obj getSDFObj() {
/* 47 */     return Obj.__Create(this.a, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   ClassMap(long paramLong, Object paramObject) {
/* 52 */     this.a = paramLong;
/* 53 */     this.b = paramObject;
/*    */   }
/*    */   
/*    */   static native boolean IsValid(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\ClassMap.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */