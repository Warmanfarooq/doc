/*    */ package com.pdftron.pdf.utils;
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
/*    */ public class Constants
/*    */ {
/*    */   public static final int FILE_TYPE_PDF = 0;
/*    */   public static final int FILE_TYPE_DOC = 1;
/*    */   public static final int FILE_TYPE_IMAGE = 2;
/*    */   public static final int FILE_TYPE_TEXT = 3;
/* 34 */   public static final String[] FILE_NAME_EXTENSIONS_PDF = new String[] { "pdf" };
/*    */ 
/*    */ 
/*    */   
/* 38 */   public static final String[] FILE_NAME_EXTENSIONS_DOC = new String[] { "docx", "doc", "pptx", "ppt", "xlsx", "md", "txt" };
/*    */ 
/*    */ 
/*    */   
/* 42 */   public static final String[] FILE_NAME_EXTENSIONS_OFFICE = new String[] { "docx", "doc", "pptx", "ppt", "xlsx" };
/*    */ 
/*    */ 
/*    */   
/* 46 */   public static final String[] FILE_NAME_EXTENSIONS_IMAGE = new String[] { "jpeg", "jpg", "gif", "png", "bmp", "tif", "tiff", "cbz" };
/*    */ 
/*    */ 
/*    */   
/* 50 */   public static final String[] FILE_EXTENSIONS_TEXT = new String[] { "txt", "md" };
/*    */ 
/*    */ 
/*    */   
/* 54 */   public static final String[] FILE_NAME_EXTENSIONS_OTHERS = new String[] { "xps", "xod", "oxps" };
/*    */ 
/*    */ 
/*    */   
/* 58 */   public static final String[] FILE_NAME_EXTENSIONS_VALID = new String[] { "pdf", "docx", "doc", "pptx", "ppt", "xlsx", "jpeg", "jpg", "gif", "png", "bmp", "cbz", "md", "txt", "tif", "tiff" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 64 */   public static final String[] ALL_NONPDF_FILETYPES_WILDCARD = new String[] { "*.docx", "*.doc", "*.pptx", "*.ppt", "*.xlsx", "*.jpeg", "*.jpg", "*.gif", "*.png", "*.bmp", "*.cbz", "*.md", "*.txt", "*.tif", "*.tiff" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 70 */   public static final String[] ALL_FILE_MIME_TYPES = new String[] { "application/pdf", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", "application/msword", "application/vnd.ms-powerpoint", "image/jpeg", "image/gif", "image/png", "image/bmp", "application/x-cbr", "text/markdown", "text/plain", "image/tiff" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 77 */   public static final String[] OFFICE_FILE_MIME_TYPES = new String[] { "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "application/vnd.openxmlformats-officedocument.presentationml.presentation", "application/msword", "application/vnd.ms-powerpoint", "text/markdown", "text/plain", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 84 */   public static final String[] IMAGE_FILE_MIME_TYPES = new String[] { "image/jpeg", "image/gif", "image/png", "image/bmp", "application/x-cbr", "image/tiff" };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 89 */   public static final String[] ALL_GOOGLE_DOCS_TYPES = new String[] { "application/vnd.google-apps.document", "application/vnd.google-apps.drawing", "application/vnd.google-apps.presentation", "application/vnd.google-apps.spreadsheet" };
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\Constants.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */