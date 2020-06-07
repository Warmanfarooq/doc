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
/*    */ public class StrikeOut
/*    */   extends TextMarkup
/*    */ {
/*    */   public StrikeOut(Obj paramObj) {
/* 28 */     super(paramObj);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private StrikeOut(long paramLong, Object paramObject) {
/* 39 */     super(paramLong, paramObject);
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
/*    */   public StrikeOut(Annot paramAnnot) throws PDFNetException {
/* 53 */     super(paramAnnot.getSDFObj());
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
/*    */   public static StrikeOut create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 67 */     return new StrikeOut(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
/*    */   }
/*    */   
/*    */   static native long Create(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\StrikeOut.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */