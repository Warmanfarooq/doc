/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FavoriteFilesManager
/*    */   extends FileInfoManager
/*    */ {
/*    */   private static final int MAX_NUM_FAVORITE_FILES = 50;
/*    */   private static final String KEY_PREFS_FAVORITE_FILES = "prefs_favorite_files";
/*    */   
/*    */   protected FavoriteFilesManager() {
/* 18 */     super("prefs_favorite_files", 50);
/*    */   }
/*    */   
/*    */   private static class LazyHolder {
/* 22 */     private static final FavoriteFilesManager INSTANCE = new FavoriteFilesManager();
/*    */   }
/*    */   
/*    */   public static FavoriteFilesManager getInstance() {
/* 26 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\FavoriteFilesManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */