/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import com.pdftron.pdf.PDFViewCtrl;
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
/*    */ public class PageBackButtonInfo
/*    */ {
/*    */   public int hScrollPos;
/*    */   public int vScrollPos;
/*    */   public double zoom;
/* 40 */   public int pageNum = -1;
/*    */   
/*    */   public int pageRotation;
/*    */   
/*    */   public PDFViewCtrl.PagePresentationMode pagePresentationMode;
/*    */ 
/*    */   
/*    */   public void copyPageInfo(PageBackButtonInfo pageStateToCopy) {
/* 48 */     this.pageNum = pageStateToCopy.pageNum;
/* 49 */     this.hScrollPos = pageStateToCopy.hScrollPos;
/* 50 */     this.vScrollPos = pageStateToCopy.vScrollPos;
/* 51 */     this.zoom = pageStateToCopy.zoom;
/* 52 */     this.pageNum = pageStateToCopy.pageNum;
/* 53 */     this.pageRotation = pageStateToCopy.pageRotation;
/* 54 */     this.pagePresentationMode = pageStateToCopy.pagePresentationMode;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PageBackButtonInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */