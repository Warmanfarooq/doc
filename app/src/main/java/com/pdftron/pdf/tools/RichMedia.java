/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.ProgressDialog;
/*     */ import android.content.res.Configuration;
/*     */ import android.media.MediaPlayer;
/*     */ import android.os.AsyncTask;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.FrameLayout;
/*     */ import android.widget.MediaController;
/*     */ import android.widget.VideoView;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.FileSpec;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.CommonToast;
/*     */ import com.pdftron.pdf.utils.CustomAsyncTask;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.NameTree;
/*     */ import com.pdftron.sdf.NameTreeIterator;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.io.File;
/*     */ import java.util.Arrays;
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
/*     */ @Keep
/*     */ public class RichMedia
/*     */   extends Tool
/*     */ {
/*  45 */   private static final String[] SUPPORTED_FORMATS = new String[] { ".3gp", ".mp4", ".m4a", ".ts", ".webm", ".mkv", ".mp3", ".ogg", ".wav" };
/*     */ 
/*     */   
/*     */   private CustomRelativeLayout mRootView;
/*     */   
/*     */   private VideoView mVideoView;
/*     */   
/*     */   private FrameLayout mCoverView;
/*     */   
/*  54 */   protected int mCoverHideTime = 250;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RichMedia(@NonNull PDFViewCtrl ctrl) {
/*  60 */     super(ctrl);
/*  61 */     this.mRootView = null;
/*  62 */     this.mVideoView = null;
/*  63 */     this.mCoverView = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  71 */     return ToolManager.ToolMode.RICH_MEDIA;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  76 */     return 28;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*  84 */     handleRichMediaAnnot(e);
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/*  93 */     handleRichMediaAnnot(e);
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDoubleTap(MotionEvent e) {
/* 102 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleBegin(float x, float y) {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 119 */     if (this.mVideoView != null || this.mCoverView != null) {
/* 120 */       (new CloseVideoViewTask()).execute((Object[])new Void[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void handleRichMediaAnnot(MotionEvent e) {
/* 125 */     if (this.mAnnot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 129 */     int x = (int)(e.getX() + 0.5D);
/* 130 */     int y = (int)(e.getY() + 0.5D);
/*     */ 
/*     */     
/* 133 */     this.mNextToolMode = getToolMode();
/*     */     
/* 135 */     Annot tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/* 136 */     if (this.mAnnot.equals(tempAnnot)) {
/* 137 */       if (onInterceptAnnotationHandling(this.mAnnot)) {
/*     */         return;
/*     */       }
/* 140 */       handleRichMediaAnnot(this.mAnnot, this.mAnnotPageNum);
/*     */     } else {
/*     */       
/* 143 */       (new CloseVideoViewTask()).execute((Object[])new Void[0]);
/* 144 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void handleRichMediaAnnot(Annot annot, int pageNum) {
/* 149 */     this.mAnnot = annot;
/* 150 */     this.mAnnotPageNum = pageNum;
/* 151 */     this.mNextToolMode = getToolMode();
/* 152 */     if (this.mVideoView == null)
/*     */     {
/* 154 */       (new ExtractMediaTask()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Annot[] { annot });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 163 */     if (this.mVideoView != null || this.mCoverView != null) {
/* 164 */       (new CloseVideoViewTask()).execute((Object[])new Void[0]);
/*     */     }
/*     */   }
/*     */   
/*     */   private void setupAndPlayMedia(String path) {
/* 169 */     View view = LayoutInflater.from(this.mPdfViewCtrl.getContext()).inflate(R.layout.controls_rich_media_layout, (ViewGroup)this.mPdfViewCtrl, true);
/* 170 */     this.mRootView = (CustomRelativeLayout)view.findViewById(R.id.root_layout);
/* 171 */     this.mVideoView = (VideoView)this.mRootView.findViewById(R.id.video_view);
/*     */     
/* 173 */     this.mCoverView = (FrameLayout)this.mRootView.findViewById(R.id.cover_view);
/*     */     
/* 175 */     this.mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
/*     */         {
/*     */           public void onPrepared(MediaPlayer mp) {
/* 178 */             (new StartVideoViewTask()).execute((Object[])new Void[0]);
/*     */           }
/*     */         });
/*     */     
/* 182 */     this.mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
/*     */         {
/*     */           public void onCompletion(MediaPlayer mp) {
/* 185 */             CommonToast.showText(RichMedia.this.mPdfViewCtrl.getContext(), RichMedia.this.getStringFromResId(R.string.tools_richmedia_playback_end), 0);
/*     */           }
/*     */         });
/*     */     
/* 189 */     this.mRootView.setAnnot(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/* 190 */     this.mVideoView.setVideoPath(path);
/* 191 */     this.mVideoView.setMediaController(new MediaController(this.mPdfViewCtrl.getContext()));
/*     */   }
/*     */   
/*     */   private boolean isMediaFileValid(String result) {
/* 195 */     if (result.isEmpty()) {
/*     */       
/* 197 */       CommonToast.showText(this.mPdfViewCtrl.getContext(), getStringFromResId(R.string.tools_richmedia_error_extracting_media), 1);
/* 198 */       return false;
/*     */     } 
/* 200 */     if (!isMediaFileSupported(result)) {
/*     */       
/* 202 */       CommonToast.showText(this.mPdfViewCtrl.getContext(), getStringFromResId(R.string.tools_richmedia_unsupported_format), 1);
/* 203 */       return false;
/*     */     } 
/*     */     
/* 206 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isMediaFileSupported(String fileName) {
/* 216 */     int idx = fileName.lastIndexOf(".");
/* 217 */     return (idx != -1 && Arrays.<String>asList(SUPPORTED_FORMATS).contains(fileName.substring(idx)));
/*     */   }
/*     */   
/*     */   private class StartVideoViewTask extends AsyncTask<Void, Void, Void> {
/*     */     private StartVideoViewTask() {}
/*     */     
/*     */     protected Void doInBackground(Void... params) {
/* 224 */       publishProgress((Object[])new Void[0]);
/*     */       try {
/* 226 */         Thread.sleep(RichMedia.this.mCoverHideTime);
/* 227 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */       
/* 230 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onProgressUpdate(Void... values) {
/* 235 */       super.onProgressUpdate((Object[])values);
/*     */       
/* 237 */       if (RichMedia.this.mVideoView != null) {
/* 238 */         RichMedia.this.mVideoView.start();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Void aVoid) {
/* 244 */       if (RichMedia.this.mCoverView != null)
/* 245 */         RichMedia.this.mCoverView.setVisibility(4); 
/*     */     }
/*     */   }
/*     */   
/*     */   private class CloseVideoViewTask
/*     */     extends AsyncTask<Void, Void, Void> {
/*     */     private CloseVideoViewTask() {}
/*     */     
/*     */     protected Void doInBackground(Void... params) {
/* 254 */       publishProgress((Object[])new Void[0]);
/*     */       try {
/* 256 */         Thread.sleep(RichMedia.this.mCoverHideTime);
/* 257 */       } catch (InterruptedException interruptedException) {}
/*     */ 
/*     */       
/* 260 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onProgressUpdate(Void... values) {
/* 265 */       super.onProgressUpdate((Object[])values);
/*     */       
/* 267 */       if (RichMedia.this.mCoverView != null) {
/* 268 */         RichMedia.this.mCoverView.setVisibility(0);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(Void aVoid) {
/* 274 */       if (RichMedia.this.mRootView != null) {
/* 275 */         if (RichMedia.this.mVideoView != null) {
/* 276 */           RichMedia.this.mVideoView.stopPlayback();
/*     */         }
/* 278 */         RichMedia.this.mPdfViewCtrl.removeView((View)RichMedia.this.mRootView);
/* 279 */         RichMedia.this.mRootView = null;
/* 280 */         RichMedia.this.mVideoView = null;
/* 281 */         RichMedia.this.mCoverView = null;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExtractMediaTask extends CustomAsyncTask<Annot, Void, String> {
/* 287 */     private ProgressDialog dialog = null;
/*     */     
/*     */     ExtractMediaTask() {
/* 290 */       super(RichMedia.this.mPdfViewCtrl.getContext());
/*     */     }
/*     */ 
/*     */     
/*     */     protected String doInBackground(Annot... annots) {
/* 295 */       String fileName = "";
/*     */       
/* 297 */       boolean shouldUnlockRead = false;
/*     */       
/*     */       try {
/* 300 */         RichMedia.this.mPdfViewCtrl.docLockRead();
/* 301 */         shouldUnlockRead = true;
/*     */         
/* 303 */         Obj ad = annots[0].getSDFObj();
/* 304 */         Obj mc = ad.findObj("RichMediaContent");
/* 305 */         if (mc != null) {
/* 306 */           NameTree assets = new NameTree(mc.findObj("Assets"));
/* 307 */           if (assets.isValid()) {
/* 308 */             NameTreeIterator j = assets.getIterator();
/* 309 */             for (; j.hasNext(); j.next()) {
/* 310 */               String asset_name = j.key().getAsPDFText();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 319 */               File cropName = new File(asset_name);
/* 320 */               File externalFileDir = Utils.getExternalFilesDir(getContext(), null);
/* 321 */               if (externalFileDir == null) {
/* 322 */                 return "";
/*     */               }
/* 324 */               File file = new File(externalFileDir, cropName.getName());
/* 325 */               if (file.exists()) {
/* 326 */                 fileName = file.getAbsolutePath();
/*     */                 break;
/*     */               } 
/* 329 */               if (RichMedia.this.isMediaFileSupported(asset_name)) {
/* 330 */                 FileSpec file_spec = new FileSpec(j.value());
/* 331 */                 Filter stm = file_spec.getFileData();
/* 332 */                 if (stm != null) {
/* 333 */                   stm.writeToFile(file.getAbsolutePath(), false);
/* 334 */                   fileName = file.getAbsolutePath();
/*     */                   
/*     */                   break;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 342 */       } catch (Exception e) {
/*     */         
/* 344 */         fileName = "";
/* 345 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 347 */         if (shouldUnlockRead)
/*     */         {
/* 349 */           RichMedia.this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/*     */       
/* 353 */       return fileName;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPostExecute(String result) {
/* 358 */       super.onPostExecute(result);
/*     */       
/* 360 */       if (this.dialog != null) {
/* 361 */         this.dialog.dismiss();
/*     */       }
/*     */       
/* 364 */       if (RichMedia.this.isMediaFileValid(result)) {
/* 365 */         RichMedia.this.setupAndPlayMedia(result);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected void onPreExecute() {
/* 371 */       super.onPreExecute();
/*     */       
/* 373 */       this.dialog = new ProgressDialog(RichMedia.this.mPdfViewCtrl.getContext());
/* 374 */       this.dialog.setMessage(RichMedia.this.getStringFromResId(R.string.tools_richmedia_please_wait_loading));
/* 375 */       this.dialog.setIndeterminate(true);
/* 376 */       this.dialog.setCancelable(false);
/* 377 */       this.dialog.show();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RichMedia.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */