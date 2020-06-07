package com.pdftron.pdf.model;

public interface BaseFileInfo {
  public static final int FILE_TYPE_UNKNOWN = -1;
  
  public static final int FILE_TYPE_FOLDER = 1;
  
  public static final int FILE_TYPE_FILE = 2;
  
  public static final int FILE_TYPE_OPEN_URL = 5;
  
  public static final int FILE_TYPE_EXTERNAL = 6;
  
  public static final int FILE_TYPE_EXTERNAL_ROOT = 7;
  
  public static final int FILE_TYPE_EXTERNAL_FOLDER = 9;
  
  public static final int FILE_TYPE_EDIT_URI = 13;
  
  public static final int FILE_TYPE_OFFICE_URI = 15;
  
  int getFileType();
  
  String getAbsolutePath();
  
  String getFileName();
  
  String getSizeInfo();
  
  long getSize();
  
  String getIdentifier();
  
  String getModifiedDate();
  
  boolean exists();
  
  boolean isDirectory();
  
  boolean isHidden();
  
  void setHidden(boolean paramBoolean);
  
  void setIsSecured(boolean paramBoolean);
  
  boolean isSecured();
  
  void setIsPackage(boolean paramBoolean);
  
  boolean isPackage();
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\BaseFileInfo.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */