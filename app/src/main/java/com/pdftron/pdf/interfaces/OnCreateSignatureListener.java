package com.pdftron.pdf.interfaces;

import android.graphics.PointF;
import android.net.Uri;
import androidx.annotation.Nullable;
import com.pdftron.pdf.controls.AnnotStyleDialogFragment;

public interface OnCreateSignatureListener {
  void onSignatureCreated(@Nullable String paramString);
  
  void onSignatureFromImage(@Nullable PointF paramPointF, int paramInt, @Nullable Long paramLong);
  
  void onAnnotStyleDialogFragmentDismissed(AnnotStyleDialogFragment paramAnnotStyleDialogFragment);
  
  public static interface OnKeystoreUpdatedListener {
    void onKeystoreFileUpdated(@Nullable Uri param1Uri);
    
    void onKeystorePasswordUpdated(@Nullable String param1String);
  }
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\interfaces\OnCreateSignatureListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */