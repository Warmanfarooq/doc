package com.pdftron.pdf.dialog.pagelabel;

import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

abstract class PageLabelView {
  PageLabelView(ViewGroup parent, PageLabelSettingChangeListener listener) {}
  
  abstract void updatePreview(String paramString);
  
  abstract void initViewStates(@Nullable PageLabelSetting paramPageLabelSetting);
  
  abstract void invalidFromPage(boolean paramBoolean);
  
  abstract void invalidToPage(boolean paramBoolean);
  
  abstract void invalidStartNumber(boolean paramBoolean);
  
  static interface PageLabelSettingChangeListener {
    void setAll(boolean param1Boolean);
    
    void setSelectedPage(boolean param1Boolean);
    
    void setPageRange(@NonNull String param1String1, @NonNull String param1String2);
    
    void setStartNumber(@NonNull String param1String);
    
    void setStyle(@NonNull PageLabelSetting.PageLabelStyle param1PageLabelStyle);
    
    void setPrefix(@NonNull String param1String);
    
    void completeSettings();
  }
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pagelabel\PageLabelView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */