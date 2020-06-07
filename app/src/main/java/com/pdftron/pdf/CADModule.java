/*    */ package com.pdftron.pdf;
/*    */ 
/*    */ import com.pdftron.common.PDFNetException;
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
/*    */ public class CADModule
/*    */ {
/*    */   public static boolean isModuleAvailable() throws PDFNetException {
/* 36 */     return IsModuleAvailable();
/*    */   }
/*    */   
/*    */   static native boolean IsModuleAvailable();
/*    */ }


/* Location:              D:\ppt\library\pagetrffn\jars\libs\PDFNet.jar!\com\pdftron\pdf\CADModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */