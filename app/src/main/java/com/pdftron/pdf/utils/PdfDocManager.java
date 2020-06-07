package com.pdftron.pdf.utils;

public class PdfDocManager {
  public static final int DOCUMENT_SETDOC_ERROR_NONE = 0;
  
  public static final int DOCUMENT_SETDOC_ERROR_NULL_PDFDOC = 1;
  
  public static final int DOCUMENT_SETDOC_ERROR_CORRUPTED = 2;
  
  public static final int DOCUMENT_SETDOC_ERROR_ZERO_PAGE = 3;
  
  public static final int DOCUMENT_SETDOC_ERROR_OPENURL_CANCELLED = 4;
  
  public static final int DOCUMENT_SETDOC_ERROR_WRONG_PASSWORD = 6;
  
  public static final int DOCUMENT_SETDOC_ERROR_NOT_EXIST = 7;
  
  public static final int DOCUMENT_SETDOC_ERROR_DOWNLOAD_CANCEL = 9;
  
  public static final int DOCUMENT_STATE_CLEAN = 0;
  
  public static final int DOCUMENT_STATE_NORMAL = 1;
  
  public static final int DOCUMENT_STATE_MODIFIED = 2;
  
  public static final int DOCUMENT_STATE_CORRUPTED = 3;
  
  public static final int DOCUMENT_STATE_CORRUPTED_AND_MODIFIED = 4;
  
  public static final int DOCUMENT_STATE_READ_ONLY = 5;
  
  public static final int DOCUMENT_STATE_READ_ONLY_AND_MODIFIED = 6;
  
  public static final int DOCUMENT_STATE_COULD_NOT_SAVE = 7;
  
  public static final int DOCUMENT_STATE_DURING_CONVERSION = 8;
  
  public static final int DOCUMENT_STATE_FROM_CONVERSION = 9;
  
  public static final int DOCUMENT_STATE_OUT_OF_SPACE = 10;
  
  public static final int DOCUMENT_ERROR_MISSING_PERMISSIONS = 11;
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\PdfDocManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */