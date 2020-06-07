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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttrObj
/*    */ {
/*    */   private long a;
/*    */   private Object b;
/*    */   
/*    */   public AttrObj(Obj paramObj) {
/* 28 */     this.a = paramObj.__GetHandle();
/* 29 */     this.b = paramObj.__GetRefHandle();
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
/*    */   public String getOwner() throws PDFNetException {
/* 42 */     return GetOwner(this.a);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Obj getSDFObj() {
/* 52 */     return Obj.__Create(this.a, this.b);
/*    */   }
/*    */   
/*    */   static native String GetOwner(long paramLong);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\struct\AttrObj.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */