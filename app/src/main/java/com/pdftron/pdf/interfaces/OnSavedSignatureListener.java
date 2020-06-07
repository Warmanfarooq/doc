package com.pdftron.pdf.interfaces;

import androidx.annotation.NonNull;

public interface OnSavedSignatureListener {
  void onSignatureSelected(@NonNull String paramString);
  
  void onCreateSignatureClicked();
  
  void onEditModeChanged(boolean paramBoolean);
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\interfaces\OnSavedSignatureListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */