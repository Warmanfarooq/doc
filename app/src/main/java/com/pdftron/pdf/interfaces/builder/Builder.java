package com.pdftron.pdf.interfaces.builder;

import android.content.Context;
import androidx.annotation.NonNull;

public interface Builder<E> {
  <T extends E> T build(@NonNull Context paramContext, @NonNull Class<T> paramClass);
}


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\interfaces\builder\Builder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */