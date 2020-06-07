/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.ClipData;
/*     */ import android.content.ClipboardManager;
/*     */ import android.content.Context;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.os.Handler;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*     */ public class AnnotationClipboardHelper
/*     */ {
/*  36 */   private static CopyOnWriteArrayList<Obj> sCurrentAnnotations = null;
/*     */   private static PDFDoc sTempDoc;
/*     */   private static RectF sUnionBound;
/*  39 */   private static Lock sClipboardLock = new ReentrantLock();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static void copyAnnot(Context context, Annot annot, PDFViewCtrl pdfViewCopyFrom, OnClipboardTaskListener listener) {
/*  63 */     ArrayList<Annot> annotsToCopy = new ArrayList<>();
/*  64 */     annotsToCopy.add(annot);
/*  65 */     copyAnnot(context, annotsToCopy, pdfViewCopyFrom, listener);
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
/*     */   public static void copyAnnot(Context context, ArrayList<Annot> annots, PDFViewCtrl pdfViewCopyFrom, OnClipboardTaskListener listener) {
/*  77 */     (new CopyPasteTask(context, pdfViewCopyFrom, annots, null, 0, null, listener)).execute((Object[])new Void[0]);
/*     */     
/*  79 */     ClipboardManager clipboard = (ClipboardManager)context.getSystemService("clipboard");
/*  80 */     if (clipboard != null) {
/*  81 */       ClipData clip = ClipData.newPlainText("text", "");
/*  82 */       clipboard.setPrimaryClip(clip);
/*     */     } 
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
/*     */   public static void pasteAnnot(Context context, PDFViewCtrl pdfViewPasteTo, int pageNo, PointF target, OnClipboardTaskListener listener) {
/*  96 */     (new CopyPasteTask(context, null, null, pdfViewPasteTo, pageNo, target, listener)).execute((Object[])new Void[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void clearClipboard() {
/* 104 */     sClipboardLock.lock();
/* 105 */     sCurrentAnnotations = null;
/* 106 */     sUnionBound = null;
/* 107 */     if (sTempDoc != null) {
/* 108 */       Utils.closeQuietly(sTempDoc);
/*     */     }
/* 110 */     sTempDoc = null;
/* 111 */     sClipboardLock.unlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAnnotCopied() {
/* 118 */     sClipboardLock.lock();
/*     */     try {
/* 120 */       return (sCurrentAnnotations != null && !sCurrentAnnotations.isEmpty());
/*     */     } finally {
/* 122 */       sClipboardLock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean isItemCopied(@Nullable Context context) {
/* 127 */     return (isAnnotCopied() || Utils.isImageCopied(context));
/*     */   }
/*     */   
/*     */   private static class CopyPasteTask extends CustomAsyncTask<Void, Void, String> {
/*     */     private ArrayList<Annot> mAnnotsToCopy;
/*     */     private PDFViewCtrl mPdfViewCopyFrom;
/*     */     private PDFViewCtrl mPdfViewToPaste;
/*     */     private int mPageNoToPaste;
/*     */     private PDFDoc mDoc;
/*     */     private Handler mHandler;
/* 137 */     private ProgressDialog mProgress = null;
/*     */     
/*     */     private PointF mTarget;
/*     */     
/*     */     private double[] mPageTarget;
/*     */     private ArrayList<Annot> mPastedAnnots;
/*     */     private OnClipboardTaskListener mOnClipboardTaskListener;
/*     */     
/*     */     CopyPasteTask(Context context, PDFViewCtrl pdfViewCopyFrom, @Nullable ArrayList<Annot> annotsToCopy, PDFViewCtrl pdfViewToPaste, int pageNoToPaste, PointF target, OnClipboardTaskListener listener) {
/* 146 */       super(context);
/* 147 */       this.mAnnotsToCopy = null;
/* 148 */       if (annotsToCopy != null && !annotsToCopy.isEmpty()) {
/* 149 */         this.mAnnotsToCopy = new ArrayList<>(annotsToCopy);
/*     */         
/* 151 */         AnnotationClipboardHelper.sClipboardLock.lock();
/* 152 */         AnnotationClipboardHelper.sCurrentAnnotations = new CopyOnWriteArrayList();
/* 153 */         AnnotationClipboardHelper.sUnionBound = null;
/* 154 */         AnnotationClipboardHelper.sClipboardLock.unlock();
/*     */       } 
/* 156 */       this.mPdfViewToPaste = pdfViewToPaste;
/* 157 */       this.mPdfViewCopyFrom = pdfViewCopyFrom;
/* 158 */       this.mPageNoToPaste = pageNoToPaste;
/* 159 */       this.mHandler = new Handler();
/* 160 */       this.mTarget = target;
/* 161 */       this.mOnClipboardTaskListener = listener;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPreExecute() {
/* 166 */       super.onPreExecute();
/* 167 */       final Context context = getContext();
/* 168 */       if (context == null) {
/*     */         return;
/*     */       }
/* 171 */       this.mHandler.postDelayed(new Runnable()
/*     */           {
/*     */             public void run() {
/* 174 */               CopyPasteTask.this.mProgress = new ProgressDialog(context);
/* 175 */               CopyPasteTask.this.mProgress.setProgressStyle(0);
/* 176 */               CopyPasteTask.this.mProgress.setMessage(context.getString((CopyPasteTask.this.mAnnotsToCopy != null) ? R.string.tools_copy_annot_waiting : R.string.tools_paste_annot_waiting));
/* 177 */               CopyPasteTask.this.mProgress.show();
/*     */             }
/*     */           },  750L);
/* 180 */       if (this.mPdfViewToPaste != null) {
/* 181 */         this.mDoc = this.mPdfViewToPaste.getDoc();
/* 182 */         this.mPageTarget = this.mPdfViewToPaste.convScreenPtToPagePt(this.mTarget.x, this.mTarget.y, this.mPageNoToPaste);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doInBackground(Void... params) {
/* 188 */       String error = null;
/* 189 */       if (this.mAnnotsToCopy != null) {
/* 190 */         boolean shouldUnlockRead = false;
/* 191 */         boolean shouldUnlockClipboard = false;
/*     */         
/*     */         try {
/* 194 */           AnnotationClipboardHelper.sTempDoc = new PDFDoc();
/* 195 */         } catch (Exception ex) {
/* 196 */           AnnotationClipboardHelper.sTempDoc = null;
/*     */         } 
/*     */         
/* 199 */         if (null == AnnotationClipboardHelper.sTempDoc) {
/* 200 */           AnnotationClipboardHelper.sCurrentAnnotations = null;
/* 201 */           return "Unable to create temp doc";
/*     */         } 
/*     */         
/* 204 */         for (Annot annot : this.mAnnotsToCopy) {
/*     */           
/*     */           try {
/* 207 */             this.mPdfViewCopyFrom.docLockRead();
/* 208 */             shouldUnlockRead = true;
/*     */             
/* 210 */             Obj srcAnnotation = annot.getSDFObj();
/*     */             
/* 212 */             Obj p = srcAnnotation.findObj("P");
/* 213 */             if (p == null) {
/* 214 */               return "Cannot find the object";
/*     */             }
/* 216 */             Obj[] pageArray = { p };
/* 217 */             Obj[] srcAnnotArray = { srcAnnotation };
/*     */             
/* 219 */             AnnotationClipboardHelper.sClipboardLock.lock();
/* 220 */             shouldUnlockClipboard = true;
/* 221 */             Rect bbox = annot.getRect();
/* 222 */             bbox.normalize();
/* 223 */             if (null == AnnotationClipboardHelper.sUnionBound) {
/* 224 */               AnnotationClipboardHelper.sUnionBound = new RectF((float)bbox.getX1(), (float)bbox.getY1(), (float)bbox.getX2(), (float)bbox.getY2());
/*     */             } else {
/* 226 */               AnnotationClipboardHelper.sUnionBound.union((float)bbox.getX1(), (float)bbox.getY1(), (float)bbox.getX2(), (float)bbox.getY2());
/*     */             } 
/* 228 */             AnnotationClipboardHelper.sCurrentAnnotations.add(AnnotationClipboardHelper.sTempDoc.getSDFDoc().importObjs(srcAnnotArray, pageArray)[0]);
/* 229 */           } catch (Exception ex) {
/*     */             
/* 231 */             error = (ex.getMessage() != null) ? ex.getMessage() : "Unknown Error";
/* 232 */             AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */           } finally {
/* 234 */             if (shouldUnlockRead)
/*     */             {
/* 236 */               this.mPdfViewCopyFrom.docUnlockRead();
/*     */             }
/* 238 */             if (shouldUnlockClipboard) {
/* 239 */               AnnotationClipboardHelper.sClipboardLock.unlock();
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } else {
/* 244 */         boolean shouldUnlock = false;
/*     */         try {
/* 246 */           if (!AnnotationClipboardHelper.isAnnotCopied()) {
/* 247 */             return null;
/*     */           }
/* 249 */           this.mPastedAnnots = new ArrayList<>();
/* 250 */           AnnotationClipboardHelper.sClipboardLock.lock();
/* 251 */           RectF unionRect = new RectF(AnnotationClipboardHelper.sUnionBound);
/* 252 */           AnnotationClipboardHelper.sClipboardLock.unlock();
/*     */ 
/*     */           
/* 255 */           Rect pcClipBound = new Rect(unionRect.left, Math.min(unionRect.top, unionRect.bottom), unionRect.right, Math.max(unionRect.top, unionRect.bottom));
/* 256 */           pcClipBound.normalize();
/*     */ 
/*     */           
/* 259 */           this.mPdfViewToPaste.docLock(true);
/* 260 */           shouldUnlock = true;
/*     */           
/* 262 */           Page page = this.mDoc.getPage(this.mPageNoToPaste);
/* 263 */           Rect cropBox = page.getBox(5);
/* 264 */           cropBox.normalize();
/*     */ 
/*     */ 
/*     */           
/* 268 */           if (this.mPageTarget[0] + (unionRect.width() / 2.0F) > cropBox.getX2()) {
/* 269 */             this.mPageTarget[0] = cropBox.getX2() - (unionRect.width() / 2.0F);
/*     */           }
/*     */           
/* 272 */           if (this.mPageTarget[0] - (unionRect.width() / 2.0F) < cropBox.getX1()) {
/* 273 */             this.mPageTarget[0] = cropBox.getX1() + (unionRect.width() / 2.0F);
/*     */           }
/*     */           
/* 276 */           if (this.mPageTarget[1] + (unionRect.height() / 2.0F) > cropBox.getY2()) {
/* 277 */             this.mPageTarget[1] = cropBox.getY2() - (unionRect.height() / 2.0F);
/*     */           }
/*     */           
/* 280 */           if (this.mPageTarget[1] - (unionRect.height() / 2.0F) < cropBox.getY1()) {
/* 281 */             this.mPageTarget[1] = cropBox.getY1() + (unionRect.height() / 2.0F);
/*     */           }
/*     */           
/* 284 */           for (Obj annotObj : AnnotationClipboardHelper.sCurrentAnnotations) {
/*     */             
/* 286 */             Obj destAnnot = this.mDoc.getSDFDoc().importObj(annotObj, true);
/* 287 */             Annot newAnnot = new Annot(destAnnot);
/*     */             
/* 289 */             if (this.mPdfViewToPaste.getToolManager() instanceof ToolManager) {
/* 290 */               ToolManager toolManager = (ToolManager)this.mPdfViewToPaste.getToolManager();
/* 291 */               String key = toolManager.generateKey();
/* 292 */               if (key != null) {
/* 293 */                 newAnnot.setUniqueID(key);
/*     */               }
/*     */             } 
/*     */             
/* 297 */             if (newAnnot.getType() == 2)
/*     */             {
/* 299 */               newAnnot.deleteCustomData(AnnotUtils.KEY_RawRichContent);
/*     */             }
/*     */             
/* 302 */             Rect boundingBox = newAnnot.getRect();
/* 303 */             boundingBox.normalize();
/*     */ 
/*     */             
/* 306 */             Point pcAnnotLeft = new Point(boundingBox.getX1(), boundingBox.getY1());
/* 307 */             Point pcUnionLeft = new Point(pcClipBound.getX1(), pcClipBound.getY1());
/* 308 */             Point pcDisp = new Point(pcAnnotLeft.x - pcUnionLeft.x, pcAnnotLeft.y - pcUnionLeft.y);
/* 309 */             double width = boundingBox.getWidth();
/* 310 */             double height = boundingBox.getHeight();
/*     */             
/* 312 */             Point pcAnnotBtmLeft = new Point(this.mPageTarget[0] - pcClipBound.getWidth() / 2.0D + pcDisp.x, this.mPageTarget[1] - pcClipBound.getHeight() / 2.0D + pcDisp.y);
/* 313 */             Rect pcAnnotDestRect = new Rect(pcAnnotBtmLeft.x, pcAnnotBtmLeft.y, pcAnnotBtmLeft.x + width, pcAnnotBtmLeft.y + height);
/*     */ 
/*     */             
/* 316 */             page.annotPushBack(newAnnot);
/* 317 */             newAnnot.resize(pcAnnotDestRect);
/*     */             
/* 319 */             if (newAnnot.getType() == 2) {
/* 320 */               int pageRotation = this.mPdfViewToPaste.getDoc().getPage(this.mPageNoToPaste).getRotation();
/* 321 */               int viewRotation = this.mPdfViewToPaste.getPageRotation();
/* 322 */               int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/* 323 */               newAnnot.setRotation(annotRotation);
/*     */             } 
/*     */             
/* 326 */             Context context = getContext();
/* 327 */             if (context != null) {
/* 328 */               AnnotUtils.refreshAnnotAppearance(context, newAnnot);
/*     */             }
/* 330 */             this.mPastedAnnots.add(newAnnot);
/*     */           } 
/* 332 */         } catch (Exception ex) {
/*     */           
/* 334 */           error = (ex.getMessage() != null) ? ex.getMessage() : "Unknown Error";
/* 335 */           AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */         } finally {
/* 337 */           if (shouldUnlock)
/*     */           {
/* 339 */             this.mPdfViewToPaste.docUnlock();
/*     */           }
/*     */         } 
/*     */       } 
/* 343 */       return error;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onPostExecute(String error) {
/* 348 */       if (null != error) {
/*     */         
/* 350 */         if (this.mPdfViewToPaste != null && this.mPdfViewToPaste.getToolManager() != null && this.mPdfViewToPaste
/* 351 */           .getToolManager() instanceof ToolManager) {
/* 352 */           ToolManager toolManager = (ToolManager)this.mPdfViewToPaste.getToolManager();
/* 353 */           toolManager.annotationCouldNotBeAdded(error);
/*     */         }
/*     */       
/* 356 */       } else if (this.mPdfViewToPaste != null && AnnotationClipboardHelper.isAnnotCopied() && this.mPastedAnnots != null) {
/* 357 */         HashMap<Annot, Integer> annots = new HashMap<>(this.mPastedAnnots.size());
/* 358 */         for (Annot annot : this.mPastedAnnots) {
/*     */           try {
/* 360 */             this.mPdfViewToPaste.update(annot, this.mPageNoToPaste);
/* 361 */             annots.put(annot, Integer.valueOf(this.mPageNoToPaste));
/* 362 */           } catch (Exception ex) {
/* 363 */             AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */           } 
/*     */         } 
/* 366 */         if (this.mPdfViewToPaste.getToolManager() != null && this.mPdfViewToPaste
/* 367 */           .getToolManager() instanceof ToolManager) {
/* 368 */           ToolManager toolManager = (ToolManager)this.mPdfViewToPaste.getToolManager();
/* 369 */           toolManager.raiseAnnotationsAddedEvent(annots);
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 374 */       this.mHandler.removeCallbacksAndMessages(null);
/* 375 */       if (this.mProgress != null) {
/* 376 */         if (this.mProgress.isShowing())
/* 377 */           this.mProgress.dismiss(); 
/* 378 */         this.mProgress = null;
/*     */       } 
/*     */       
/* 381 */       if (this.mOnClipboardTaskListener != null)
/* 382 */         this.mOnClipboardTaskListener.onnClipboardTaskDone(error); 
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnClipboardTaskListener {
/*     */     void onnClipboardTaskDone(String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\AnnotationClipboardHelper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */