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
/*    */ 
/*    */ public class Polygon
/*    */   extends PolyLine
/*    */ {
/*    */   public Polygon() {}
/*    */   
/*    */   public Polygon(Annot paramAnnot) throws PDFNetException {
/* 33 */     super(paramAnnot);
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
/*    */   public Polygon(Obj paramObj) {
/* 46 */     super(paramObj);
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
/*    */   public static Polygon create(Doc paramDoc, Rect paramRect) throws PDFNetException {
/* 60 */     return new Polygon(PolyLine.create(paramDoc, paramRect));
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\annots\Polygon.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */