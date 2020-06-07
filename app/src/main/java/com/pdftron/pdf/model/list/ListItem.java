package com.pdftron.pdf.model.list;

import androidx.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
public interface ListItem {
  public static final int LAYOUT_HEADER = 0;
  
  public static final int LAYOUT_CONTENT = 1;
  
  boolean isHeader();
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\model\list\ListItem.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */