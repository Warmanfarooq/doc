/*    */ package com.pdftron.pdf.utils;
/*    */ 
/*    */ import android.content.Context;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.common.RecentlyUsedCache;
/*    */ import com.pdftron.pdf.model.FileInfo;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecentFilesManager
/*    */   extends FileInfoManager
/*    */ {
/*    */   public static final int MAX_NUM_RECENT_FILES = 50;
/*    */   private static final String KEY_PREFS_RECENT_FILES = "prefs_recent_files";
/*    */   
/*    */   protected RecentFilesManager() {
/* 30 */     super("prefs_recent_files", 50);
/*    */   }
/*    */   
/*    */   private static class LazyHolder {
/* 34 */     private static final RecentFilesManager INSTANCE = new RecentFilesManager();
/*    */   }
/*    */   
/*    */   public static RecentFilesManager getInstance() {
/* 38 */     return LazyHolder.INSTANCE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void clearFiles(@NonNull Context context) {
/* 43 */     super.clearFiles(context);
/* 44 */     RecentlyUsedCache.resetCache();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean removeFile(@Nullable Context context, @Nullable FileInfo fileToRemove) {
/* 49 */     if (context == null || fileToRemove == null) {
/* 50 */       return false;
/*    */     }
/* 52 */     if (super.removeFile(context, fileToRemove)) {
/* 53 */       RecentlyUsedCache.removeDocument(fileToRemove.getAbsolutePath());
/* 54 */       return true;
/*    */     } 
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public List<FileInfo> removeFiles(@Nullable Context context, @Nullable List<FileInfo> filesToRemove) {
/* 62 */     List<FileInfo> filesRemoved = super.removeFiles(context, filesToRemove);
/* 63 */     if (context == null) {
/* 64 */       return filesRemoved;
/*    */     }
/* 66 */     for (FileInfo fileRemoved : filesRemoved) {
/* 67 */       if (fileRemoved != null) {
/* 68 */         RecentlyUsedCache.removeDocument(fileRemoved.getAbsolutePath());
/*    */       }
/*    */     } 
/* 71 */     return filesRemoved;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean updateFile(@NonNull Context context, @Nullable FileInfo oldFile, @Nullable FileInfo newFile) {
/* 76 */     if (oldFile == null || newFile == null) {
/* 77 */       return false;
/*    */     }
/* 79 */     if (super.updateFile(context, oldFile, newFile)) {
/* 80 */       RecentlyUsedCache.renameDocument(oldFile.getAbsolutePath(), newFile.getAbsolutePath());
/* 81 */       return true;
/*    */     } 
/* 83 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\RecentFilesManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */