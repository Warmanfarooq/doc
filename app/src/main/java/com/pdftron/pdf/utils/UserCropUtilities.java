/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.PageIterator;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ public class UserCropUtilities
/*     */ {
/*     */   private static final String CROPPING_OBJECT_STRING = "TRN_UserCrop";
/*     */   
/*     */   public static class AutoCropInBackgroundTask
/*     */     extends CustomAsyncTask<Void, Integer, Boolean>
/*     */   {
/*  33 */     private static final String TAG = AutoCropInBackgroundTask.class.getName();
/*     */ 
/*     */     
/*     */     private static final int MINIMUM_CROP_RECT_SIZE = 10;
/*     */ 
/*     */     
/*     */     private static final int CROP_RECT_WHITE_SPACE_MARGIN = 2;
/*     */ 
/*     */     
/*     */     private WeakReference<PDFViewCtrl> mPdfViewCtrlRef;
/*     */ 
/*     */     
/*     */     private PDFDoc mDoc;
/*     */ 
/*     */     
/*     */     private Rect[] mUserCropRects;
/*     */     
/*     */     private int mMaxPages;
/*     */     
/*     */     ProgressDialog mAutoCropProgressDialog;
/*     */     
/*     */     private AutoCropTaskListener mAutoCropTaskListener;
/*     */ 
/*     */     
/*     */     public AutoCropInBackgroundTask(Context context, PDFViewCtrl pdfViewCtrl, AutoCropTaskListener listener) {
/*  58 */       super(context);
/*     */       
/*  60 */       this.mPdfViewCtrlRef = new WeakReference<>(pdfViewCtrl);
/*  61 */       this.mAutoCropTaskListener = listener;
/*     */       
/*  63 */       this.mDoc = pdfViewCtrl.getDoc();
/*  64 */       boolean shouldUnlockRead = false;
/*     */       try {
/*  66 */         this.mDoc.lockRead();
/*  67 */         shouldUnlockRead = true;
/*  68 */         this.mMaxPages = this.mDoc.getPageCount();
/*  69 */         this.mUserCropRects = new Rect[this.mMaxPages];
/*  70 */       } catch (PDFNetException ex) {
/*  71 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex, "USER_CROP");
/*     */       } finally {
/*  73 */         if (shouldUnlockRead) {
/*  74 */           Utils.unlockReadQuietly(this.mDoc);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPreExecute() {
/*  81 */       super.onPreExecute();
/*  82 */       Context context = getContext();
/*  83 */       if (context == null) {
/*     */         return;
/*     */       }
/*     */       
/*  87 */       PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/*  88 */       if (pdfViewCtrl == null) {
/*     */         return;
/*     */       }
/*  91 */       pdfViewCtrl.cancelRendering();
/*     */       
/*  93 */       this.mAutoCropProgressDialog = new ProgressDialog(context);
/*  94 */       this.mAutoCropProgressDialog.setMessage(context.getResources().getString(R.string.user_crop_auto_crop_title));
/*  95 */       this.mAutoCropProgressDialog.setIndeterminate(false);
/*  96 */       this.mAutoCropProgressDialog.setProgressStyle(1);
/*  97 */       this.mAutoCropProgressDialog.setCancelable(true);
/*  98 */       this.mAutoCropProgressDialog.setCanceledOnTouchOutside(false);
/*  99 */       this.mAutoCropProgressDialog.setProgress(0);
/* 100 */       this.mAutoCropProgressDialog.setMax(this.mMaxPages);
/* 101 */       this.mAutoCropProgressDialog.setProgressNumberFormat(context.getResources().getString(R.string.user_crop_auto_crop_progress));
/* 102 */       this.mAutoCropProgressDialog.setProgressPercentFormat(null);
/* 103 */       this.mAutoCropProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
/*     */           {
/*     */             public void onCancel(DialogInterface dialog) {
/* 106 */               AutoCropInBackgroundTask.this.cancel(false);
/*     */             }
/*     */           });
/*     */       
/* 110 */       this.mAutoCropProgressDialog.show();
/*     */     }
/*     */ 
/*     */     
/*     */     protected Boolean doInBackground(Void... params) {
/* 115 */       boolean success = false;
/* 116 */       Rect previousRect = null;
/* 117 */       boolean shouldUnlockRead = false;
/*     */       
/*     */       try {
/* 120 */         this.mDoc.lockRead();
/* 121 */         shouldUnlockRead = true;
/*     */         
/* 123 */         PageIterator itr = this.mDoc.getPageIterator();
/* 124 */         int lastPageDone = 0;
/*     */         
/* 126 */         while (!isCancelled() && itr.hasNext()) {
/* 127 */           Object obj = itr.next();
/* 128 */           Page page = (Page)obj;
/*     */           try {
/* 130 */             Rect cropRect = page.getCropBox();
/* 131 */             Rect visibleRect = page.getVisibleContentBox();
/* 132 */             visibleRect.inflate(2.0D);
/*     */             
/* 134 */             boolean intersects = visibleRect.intersectRect(visibleRect, cropRect);
/* 135 */             if (intersects && (cropRect
/* 136 */               .getWidth() - visibleRect.getWidth() > 0.5D || cropRect.getHeight() - visibleRect.getHeight() > 0.5D) && visibleRect
/* 137 */               .getHeight() > 10.0D && visibleRect.getWidth() > 10.0D) {
/* 138 */               this.mUserCropRects[lastPageDone] = visibleRect;
/* 139 */             } else if (previousRect != null) {
/* 140 */               visibleRect.set(cropRect.getX1(), cropRect.getY1(), cropRect.getX2(), cropRect.getY2());
/* 141 */               double prevWidth = previousRect.getWidth();
/* 142 */               double prevHeight = previousRect.getHeight();
/* 143 */               if (visibleRect.getWidth() > prevWidth) {
/* 144 */                 double newMargin = (visibleRect.getWidth() - prevWidth) / 2.0D;
/* 145 */                 visibleRect.setX1(visibleRect.getX1() + newMargin);
/* 146 */                 visibleRect.setX2(visibleRect.getX2() - newMargin);
/*     */               } 
/* 148 */               if (visibleRect.getHeight() > prevHeight) {
/* 149 */                 double newMargin = (visibleRect.getHeight() - prevHeight) / 2.0D;
/* 150 */                 visibleRect.setY1(visibleRect.getY1() + newMargin);
/* 151 */                 visibleRect.setY2(visibleRect.getY2() - newMargin);
/*     */               } 
/* 153 */               this.mUserCropRects[lastPageDone] = visibleRect;
/*     */             } 
/* 155 */           } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */           
/* 158 */           previousRect = this.mUserCropRects[lastPageDone];
/* 159 */           lastPageDone++;
/* 160 */           publishProgress((Object[])new Integer[] { Integer.valueOf(lastPageDone) });
/*     */         } 
/* 162 */       } catch (PDFNetException e) {
/* 163 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "USER_CROP");
/*     */       } finally {
/* 165 */         if (shouldUnlockRead) {
/* 166 */           Utils.unlockReadQuietly(this.mDoc);
/*     */         }
/*     */       } 
/*     */       
/* 170 */       boolean shouldUnlock = false;
/*     */       try {
/* 172 */         this.mDoc.lock();
/* 173 */         shouldUnlock = true;
/*     */ 
/*     */ 
/*     */         
/* 177 */         PageIterator itr = this.mDoc.getPageIterator();
/* 178 */         int lastPageDone = 0;
/*     */         
/* 180 */         while (!isCancelled() && itr.hasNext()) {
/* 181 */           Object obj = itr.next();
/* 182 */           Page page = (Page)obj;
/*     */           
/*     */           try {
/* 185 */             if (this.mUserCropRects[lastPageDone] != null) {
/* 186 */               page.setBox(5, this.mUserCropRects[lastPageDone]);
/*     */             } else {
/* 188 */               UserCropUtilities.removeUserCropFromPage(page);
/*     */             } 
/* 190 */           } catch (PDFNetException pDFNetException) {}
/*     */ 
/*     */           
/* 193 */           lastPageDone++;
/*     */         } 
/* 195 */         success = true;
/* 196 */       } catch (PDFNetException e) {
/* 197 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e, "USER_CROP");
/*     */       } finally {
/* 199 */         if (shouldUnlock) {
/* 200 */           Utils.unlockQuietly(this.mDoc);
/*     */         }
/*     */       } 
/*     */       
/* 204 */       return Boolean.valueOf(success);
/*     */     }
/*     */     public static interface AutoCropTaskListener {
/*     */       void onAutoCropTaskDone(); }
/*     */     protected void onProgressUpdate(Integer... values) {
/* 209 */       super.onProgressUpdate((Object[])values);
/*     */       
/* 211 */       if (this.mAutoCropProgressDialog != null) {
/* 212 */         int pagesDone = values[0].intValue();
/* 213 */         this.mAutoCropProgressDialog.setProgress(pagesDone);
/* 214 */         if (pagesDone == this.mMaxPages) {
/* 215 */           this.mAutoCropProgressDialog.setCancelable(false);
/*     */         }
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onCancelled(Boolean aBoolean) {
/* 222 */       super.onCancelled(aBoolean);
/*     */       
/* 224 */       PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 225 */       if (pdfViewCtrl == null) {
/*     */         return;
/*     */       }
/* 228 */       pdfViewCtrl.requestRendering();
/*     */       
/* 230 */       if (this.mAutoCropProgressDialog != null && this.mAutoCropProgressDialog.isShowing()) {
/* 231 */         this.mAutoCropProgressDialog.dismiss();
/*     */       }
/*     */       
/* 234 */       if (this.mAutoCropTaskListener != null) {
/* 235 */         this.mAutoCropTaskListener.onAutoCropTaskDone();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Boolean result) {
/* 241 */       super.onPostExecute(result);
/*     */       
/* 243 */       PDFViewCtrl pdfViewCtrl = this.mPdfViewCtrlRef.get();
/* 244 */       if (pdfViewCtrl == null) {
/*     */         return;
/*     */       }
/*     */       
/* 248 */       if (result.booleanValue()) {
/*     */         try {
/* 250 */           pdfViewCtrl.updatePageLayout();
/* 251 */         } catch (Exception e) {
/* 252 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "USER_CROP");
/*     */         } 
/*     */       } else {
/* 255 */         pdfViewCtrl.requestRendering();
/*     */       } 
/* 257 */       if (this.mAutoCropProgressDialog != null && this.mAutoCropProgressDialog.isShowing()) {
/* 258 */         this.mAutoCropProgressDialog.dismiss();
/*     */       }
/*     */       
/* 261 */       if (this.mAutoCropTaskListener != null) {
/* 262 */         this.mAutoCropTaskListener.onAutoCropTaskDone();
/*     */       }
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
/*     */   public static void removeUserCropFromPage(Page page) throws PDFNetException {
/* 277 */     Obj pageRootObj = page.getSDFObj();
/* 278 */     if (pageRootObj.findObj("TRN_UserCrop") != null) {
/* 279 */       pageRootObj.erase("TRN_UserCrop");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void cropDoc(PDFDoc doc) {
/* 289 */     if (doc != null) {
/* 290 */       boolean shouldUnlock = false;
/*     */       try {
/* 292 */         doc.lock();
/* 293 */         shouldUnlock = true;
/* 294 */         PageIterator pageIterator = doc.getPageIterator();
/* 295 */         while (pageIterator.hasNext()) {
/* 296 */           Page page = pageIterator.next();
/* 297 */           page.setCropBox(page.getBox(5));
/*     */         } 
/* 299 */       } catch (Exception e) {
/* 300 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 302 */         if (shouldUnlock)
/* 303 */           Utils.unlockQuietly(doc); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\UserCropUtilities.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */