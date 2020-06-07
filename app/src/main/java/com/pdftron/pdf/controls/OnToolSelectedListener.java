package com.pdftron.pdf.controls;

import android.view.View;

public interface OnToolSelectedListener {
  void onDrawSelected(int paramInt, boolean paramBoolean, View paramView);
  
  void onClearSelected();
  
  void onEraserSelected(boolean paramBoolean, View paramView);
  
  void onUndoSelected();
  
  void onRedoSelected();
  
  void onCloseSelected();
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\OnToolSelectedListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */