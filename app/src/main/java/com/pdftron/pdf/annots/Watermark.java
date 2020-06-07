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
/*    */ public class Watermark
/*    */   extends Annot
/*    */ {
/*    */   public Watermark(Obj paramObj) {
/* 29 */     super(paramObj);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Watermark() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Watermark(long paramLong, Object paramObject) {
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
/*    */   
/*    */   public Watermark(Annot paramAnnot) throws PDFNetException {
/* 65 */     super(paramAnnot.getSDFObj());
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
/*    */   public static Watermark create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 79 */     return new Watermark(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
/*    */   }
/*    */   
/*    */   static native long Create(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Watermark.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */