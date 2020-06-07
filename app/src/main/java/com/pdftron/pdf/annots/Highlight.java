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
/*    */ public class Highlight
/*    */   extends TextMarkup
/*    */ {
/*    */   public Highlight(Obj paramObj) {
/* 27 */     super(paramObj);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Highlight() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Highlight(long paramLong, Object paramObject) {
/* 47 */     super(paramLong, paramObject);
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
/*    */   public Highlight(Annot paramAnnot) throws PDFNetException {
/* 62 */     super(paramAnnot.getSDFObj());
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
/*    */   public static Highlight create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 76 */     return new Highlight(Create(paramDoc.__GetHandle(), paramRect.__GetHandle()), paramDoc);
/*    */   }
/*    */   
/*    */   static native long Create(long paramLong1, long paramLong2);
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Highlight.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */