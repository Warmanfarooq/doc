/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.os.Bundle;
/*     */ import android.util.Log;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.fdf.FDFDoc;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ExternalAnnotManager;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDocWithoutOwnership;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.SDFDoc;
/*     */ import java.io.File;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AnnotManager
/*     */ {
/*  35 */   private static final String TAG = AnnotManager.class.getName();
/*     */   
/*     */   private static boolean sDebug = true;
/*  38 */   private final Lock mDataLock = new ReentrantLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExternalAnnotManager mExternalAnnotManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ToolManager mToolManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bundle mInitialAnnot;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bundle mAnnots;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotationSyncingListener mListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotManager(@NonNull ToolManager toolManager, @NonNull String userId, AnnotationSyncingListener listener) throws PDFNetException {
/* 109 */     this(toolManager, userId, null, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotManager(@NonNull ToolManager toolManager, @NonNull String userId, @Nullable String userName, AnnotationSyncingListener listener) throws PDFNetException {
/* 123 */     this(toolManager, userId, userName, null, listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotManager(@NonNull ToolManager toolManager, @NonNull String userId, @Nullable String userName, @Nullable Bundle initialAnnot, AnnotationSyncingListener listener) throws PDFNetException {
/* 139 */     if (toolManager.getPDFViewCtrl() == null) {
/* 140 */       throw new NullPointerException("PDFfViewCtrl can't be null");
/*     */     }
/* 142 */     this.mToolManager = toolManager;
/* 143 */     this.mPdfViewCtrl = toolManager.getPDFViewCtrl();
/*     */     
/* 145 */     this.mExternalAnnotManager = toolManager.getPDFViewCtrl().enableExternalAnnotManager(userId);
/* 146 */     if (this.mExternalAnnotManager == null) {
/* 147 */       throw new NullPointerException("ExternalAnnotManager can't be null");
/*     */     }
/* 149 */     toolManager.setAuthorId(userId);
/* 150 */     toolManager.setAuthorName(userName);
/* 151 */     this.mInitialAnnot = initialAnnot;
/* 152 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnnotationSyncingListener(AnnotationSyncingListener listener) {
/* 160 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLocalChange(String action) {
/*     */     try {
/* 170 */       String lastChanges = this.mExternalAnnotManager.getLastXFDF();
/* 171 */       if (sDebug) {
/* 172 */         Log.d(TAG, "onLocalChange: [" + action + "] " + lastChanges);
/*     */       }
/* 174 */       String lastJSON = this.mExternalAnnotManager.getLastJSON();
/* 175 */       if (sDebug) {
/* 176 */         Log.d(TAG, "onLocalChange json: [" + action + "] " + lastJSON);
/*     */       }
/*     */       
/* 179 */       if (this.mListener != null && !Utils.isNullOrEmpty(lastChanges) && !Utils.isNullOrEmpty(lastJSON)) {
/* 180 */         this.mListener.onLocalChange(action, lastChanges, lastJSON);
/*     */       }
/* 182 */     } catch (Exception ex) {
/* 183 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRemoteChange(String incoming) {
/* 192 */     if (sDebug) {
/* 193 */       Log.d(TAG, "onRemoteChange: " + incoming);
/*     */     }
/* 195 */     boolean shouldUnlock = false;
/*     */     try {
/* 197 */       this.mPdfViewCtrl.docLock(true);
/* 198 */       shouldUnlock = true;
/* 199 */       this.mExternalAnnotManager.mergeXFDF(incoming);
/* 200 */     } catch (Exception e) {
/* 201 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 203 */       if (shouldUnlock) {
/* 204 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/* 207 */     if (this.mInitialAnnot != null) {
/* 208 */       String annotId = this.mInitialAnnot.getString("chatId");
/*     */       try {
/* 210 */         String page = this.mInitialAnnot.getString("page");
/* 211 */         int pageNum = Integer.parseInt(page);
/* 212 */         jumpToAnnot(annotId, pageNum);
/* 213 */       } catch (Exception ex) {
/* 214 */         ex.printStackTrace();
/*     */       } 
/* 216 */       this.mInitialAnnot = null;
/*     */     } 
/* 218 */     this.mToolManager.resetIndicator();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jumpToAnnot(String annotId) {
/* 226 */     this.mPdfViewCtrl.jumpToAnnotWithID(annotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void jumpToAnnot(String annotId, int page) {
/* 235 */     this.mPdfViewCtrl.jumpToAnnotWithID(annotId);
/* 236 */     page++;
/* 237 */     if (page > 0) {
/* 238 */       this.mToolManager.selectAnnot(annotId, page);
/*     */     } else {
/* 240 */       this.mToolManager.deselectAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void exportToFile(File targetFile) {
/* 251 */     boolean shouldUnlockRead = false;
/* 252 */     PDFDoc exportedDoc = null;
/*     */     
/*     */     try {
/* 255 */       this.mPdfViewCtrl.docLockRead();
/* 256 */       shouldUnlockRead = true;
/*     */       
/* 258 */       PDFDoc mainDoc = this.mPdfViewCtrl.getDoc();
/* 259 */       exportedDoc = new PDFDoc(targetFile.getAbsolutePath());
/*     */ 
/*     */       
/* 262 */       FDFDoc mainFDFDoc = mainDoc.fdfExtract(1);
/* 263 */       exportedDoc.fdfUpdate(mainFDFDoc);
/*     */       
/* 265 */       long mainDocImpl = mainDoc.getSDFDoc().__GetHandle();
/*     */ 
/*     */       
/* 268 */       long extraDocImpl = 0L;
/* 269 */       int pageCount = this.mPdfViewCtrl.getPageCount();
/* 270 */       for (int pageNumber = 1; pageNumber <= pageCount; pageNumber++) {
/* 271 */         ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationsOnPage(pageNumber);
/* 272 */         for (Annot annot : annots) {
/* 273 */           if (annot == null || !annot.isValid()) {
/*     */             continue;
/*     */           }
/* 276 */           Obj annotObj = annot.getSDFObj();
/* 277 */           if (annotObj == null) {
/*     */             continue;
/*     */           }
/* 280 */           SDFDoc annotDoc = annotObj.getDoc();
/*     */           
/* 282 */           long annotDocImpl = annotDoc.__GetHandle();
/* 283 */           if (annotDocImpl != mainDocImpl) {
/* 284 */             extraDocImpl = annotDocImpl;
/*     */             break;
/*     */           } 
/*     */         } 
/* 288 */         if (extraDocImpl != 0L) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 294 */       if (extraDocImpl != 0L) {
/*     */         
/* 296 */         PDFDocWithoutOwnership extraDoc = PDFDocWithoutOwnership.__Create(extraDocImpl);
/*     */ 
/*     */         
/* 299 */         FDFDoc fdfDoc = extraDoc.fdfExtract(1);
/* 300 */         exportedDoc.fdfMerge(fdfDoc);
/*     */       } 
/*     */       
/* 303 */       exportedDoc.save();
/* 304 */     } catch (Exception e) {
/* 305 */       e.printStackTrace();
/*     */     } finally {
/* 307 */       if (shouldUnlockRead) {
/* 308 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/* 310 */       if (exportedDoc != null) {
/* 311 */         Utils.closeQuietly(exportedDoc);
/* 312 */         exportedDoc = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAnnotationItemsChange(Bundle annots) {
/* 321 */     if (this.mDataLock.tryLock()) {
/*     */       try {
/* 323 */         this.mAnnots = annots;
/*     */       } finally {
/* 325 */         this.mDataLock.unlock();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldShowIndicator(Annot annot) {
/*     */     try {
/* 335 */       if (null == this.mAnnots || null == annot || null == annot
/*     */         
/* 337 */         .getUniqueID()) {
/* 338 */         return false;
/*     */       }
/* 340 */       String annotId = annot.getUniqueID().getAsPDFText();
/* 341 */       if (null == annotId) {
/* 342 */         return false;
/*     */       }
/* 344 */       Bundle annotItem = null;
/* 345 */       if (this.mDataLock.tryLock()) {
/*     */         try {
/* 347 */           annotItem = this.mAnnots.getBundle(annotId);
/*     */         } finally {
/* 349 */           this.mDataLock.unlock();
/*     */         } 
/*     */       }
/* 352 */       if (null != annotItem) {
/* 353 */         double msgCount = annotItem.getDouble("msgCount", 0.0D);
/* 354 */         if (msgCount > 0.0D) {
/* 355 */           return true;
/*     */         }
/*     */       } 
/* 358 */     } catch (Exception ignored) {
/* 359 */       ignored.printStackTrace();
/*     */     } 
/* 361 */     return false;
/*     */   }
/*     */   
/*     */   public static interface AnnotItem {
/*     */     public static final String MSG_COUNT = "msgCount";
/*     */   }
/*     */   
/*     */   public static interface AnnotationAction {
/*     */     public static final String ADD = "add";
/*     */     public static final String MODIFY = "modify";
/*     */     public static final String DELETE = "delete";
/*     */     public static final String UNDO = "undo";
/*     */     public static final String REDO = "redo";
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface AnnotAction {}
/*     */   
/*     */   public static interface AnnotationSyncingListener {
/*     */     void onLocalChange(String param1String1, String param1String2, String param1String3);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\AnnotManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */