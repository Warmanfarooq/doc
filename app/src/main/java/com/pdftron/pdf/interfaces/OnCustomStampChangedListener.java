package com.pdftron.pdf.interfaces;

import android.graphics.Bitmap;
import androidx.annotation.Nullable;

public interface OnCustomStampChangedListener {
  void onCustomStampCreated(@Nullable Bitmap paramBitmap);
  
  void onCustomStampUpdated(@Nullable Bitmap paramBitmap, int paramInt);
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\interfaces\OnCustomStampChangedListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */