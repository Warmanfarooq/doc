/*    */ package com.pdftron.pdf.utils.cache;
/*    */ 
/*    */ import android.content.Context;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.RestrictTo;
/*    */ import java.io.File;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/*    */ public class UriCacheManager
/*    */ {
/*    */   public static final String BUNDLE_USE_CACHE_FOLDER = "PdfViewCtrlTabFragment_bundle_cache_folder_uri";
/*    */   private static final String cacheFolder = "uri_cache";
/*    */   
/*    */   public static File getCacheDir(@NonNull Context context) {
/* 19 */     return new File(context.getCacheDir(), "uri_cache");
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\cache\UriCacheManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */