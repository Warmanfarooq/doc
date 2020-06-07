/*    */ package com.pdftron.pdf.annots;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.sdf.Doc;
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
/*    */ 
/*    */ public class Circle
/*    */   extends Markup
/*    */ {
/*    */   public Circle(Obj paramObj) {
/* 30 */     super(paramObj);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Circle() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Circle(long paramLong, Object paramObject) {
/* 49 */     super(paramLong, paramObject);
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
/*    */   
/*    */   public Circle(Annot paramAnnot) throws PDFNetException {
/* 64 */     super(paramAnnot.getSDFObj());
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
/*    */   public static Circle create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 78 */     return new Circle(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
/*    */   }
/*    */   
/*    */   static native long Create(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Circle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */