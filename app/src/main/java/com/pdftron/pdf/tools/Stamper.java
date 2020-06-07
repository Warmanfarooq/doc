/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.ClipData;
/*     */ import android.content.ClipboardManager;
/*     */ import android.content.ContentResolver;
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.Point;
/*     */ import android.graphics.PointF;
/*     */ import android.net.Uri;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.filters.Filter;
/*     */ import com.pdftron.filters.SecondaryFileFilter;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Image;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.PageSet;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import com.pdftron.sdf.ObjSet;
/*     */ import java.util.ArrayList;
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
/*     */ @Keep
/*     */ public class Stamper
/*     */   extends Tool
/*     */ {
/*     */   public static final String STAMPER_ROTATION_ID = "pdftronImageStampRotation";
/*     */   public static final String STAMPER_ROTATION_DEGREE_ID = "pdftronImageStampRotationDegree";
/*     */   private static final String IMAGE_STAMPER_MOST_RECENTLY_USED_FILES = "image_stamper_most_recently_used_files";
/*     */   protected PointF mTargetPoint;
/*     */   
/*     */   public Stamper(@NonNull PDFViewCtrl ctrl) {
/*  57 */     super(ctrl);
/*  58 */     this.mNextToolMode = getToolMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  66 */     return ToolManager.ToolMode.STAMPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  71 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  88 */     if (this.mTargetPoint != null) {
/*  89 */       return false;
/*     */     }
/*     */     
/*  92 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.FLING)
/*     */     {
/*     */       
/*  95 */       return false;
/*     */     }
/*     */     
/*  98 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */ 
/*     */     
/* 101 */     if (toolManager.isQuickMenuJustClosed()) {
/* 102 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 106 */     boolean shouldCreate = true;
/* 107 */     int x = (int)e.getX();
/* 108 */     int y = (int)e.getY();
/* 109 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt(x, y, x, y);
/* 110 */     int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */     
/* 112 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/*     */     try {
/* 115 */       for (Annot annot : annots) {
/* 116 */         if (annot.isValid() && annot.getType() == 12) {
/* 117 */           shouldCreate = false;
/*     */           
/* 119 */           toolManager.selectAnnot(annot, page);
/*     */           break;
/*     */         } 
/*     */       } 
/* 123 */     } catch (PDFNetException ex) {
/* 124 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */     
/* 127 */     if (shouldCreate && page > 0) {
/* 128 */       this.mTargetPoint = new PointF(e.getX(), e.getY());
/* 129 */       addStamp();
/* 130 */       return true;
/*     */     } 
/* 132 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 141 */     if (super.onQuickMenuClicked(menuItem)) {
/* 142 */       return true;
/*     */     }
/* 144 */     if (menuItem.getItemId() == R.id.qm_image_stamper) {
/* 145 */       addStamp();
/*     */     }
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetPoint(PointF targetPoint, boolean createImageStamp) {
/* 157 */     this.mTargetPoint = targetPoint;
/*     */     
/* 159 */     if (createImageStamp) {
/* 160 */       addStamp();
/*     */     }
/*     */     
/* 163 */     safeSetNextToolMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addStamp() {
/* 171 */     if (this.mTargetPoint == null) {
/* 172 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("target point is not specified."));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 177 */     ((ToolManager)this.mPdfViewCtrl.getToolManager()).onImageStamperSelected(this.mTargetPoint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addStampFromClipboard(@Nullable PointF targetPoint) {
/* 187 */     if (targetPoint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 191 */     this.mTargetPoint = targetPoint;
/*     */     
/* 193 */     if (this.mPdfViewCtrl == null || this.mPdfViewCtrl.getContext() == null) {
/*     */       return;
/*     */     }
/*     */     
/* 197 */     Context context = this.mPdfViewCtrl.getContext();
/* 198 */     ClipboardManager clipboard = (ClipboardManager)context.getSystemService("clipboard");
/* 199 */     ContentResolver cr = context.getContentResolver();
/* 200 */     if (clipboard == null || cr == null) {
/*     */       return;
/*     */     }
/* 203 */     ClipData clip = clipboard.getPrimaryClip();
/*     */     
/* 205 */     if (clip != null) {
/* 206 */       ClipData.Item item = clip.getItemAt(0);
/* 207 */       Uri pasteUri = item.getUri();
/* 208 */       if (Utils.isImageFile(cr, pasteUri)) {
/*     */         try {
/* 210 */           createImageStamp(pasteUri, 0, (String)null);
/* 211 */         } catch (SecurityException securityException) {}
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
/*     */   public void clearTargetPoint() {
/* 223 */     this.mTargetPoint = null;
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
/*     */   public boolean createImageStamp(Uri uri, int imageRotation, String filePath) {
/* 236 */     boolean shouldUnlock = false;
/* 237 */     SecondaryFileFilter filter = null; try {
/*     */       int pageNum;
/* 239 */       this.mPdfViewCtrl.docLock(true);
/* 240 */       shouldUnlock = true;
/* 241 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
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
/* 258 */       filter = new SecondaryFileFilter(this.mPdfViewCtrl.getContext(), uri);
/*     */ 
/*     */       
/* 261 */       ObjSet hintSet = new ObjSet();
/* 262 */       Obj encoderHints = hintSet.createArray();
/* 263 */       encoderHints.pushBackName("JPEG");
/* 264 */       encoderHints.pushBackName("Quality");
/* 265 */       encoderHints.pushBackNumber(85.0D);
/*     */ 
/*     */       
/* 268 */       Image img = Image.create((Doc)doc.getSDFDoc(), (Filter)filter, encoderHints);
/*     */ 
/*     */       
/* 271 */       if (this.mTargetPoint != null) {
/* 272 */         pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(this.mTargetPoint.x, this.mTargetPoint.y);
/* 273 */         if (pageNum <= 0) {
/* 274 */           pageNum = this.mPdfViewCtrl.getCurrentPage();
/*     */         }
/*     */       } else {
/* 277 */         pageNum = this.mPdfViewCtrl.getCurrentPage();
/*     */       } 
/*     */       
/* 280 */       if (pageNum <= 0) {
/* 281 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 285 */       Page page = doc.getPage(pageNum);
/* 286 */       int viewRotation = this.mPdfViewCtrl.getPageRotation();
/*     */       
/* 288 */       Rect pageViewBox = page.getBox(this.mPdfViewCtrl.getPageBox());
/* 289 */       Rect pageCropBox = page.getCropBox();
/* 290 */       int pageRotation = page.getRotation();
/*     */ 
/*     */       
/* 293 */       Point size = new Point();
/* 294 */       Utils.getDisplaySize(this.mPdfViewCtrl.getContext(), size);
/* 295 */       int screenWidth = (size.x < size.y) ? size.x : size.y;
/* 296 */       int screenHeight = (size.x < size.y) ? size.y : size.x;
/*     */ 
/*     */       
/* 299 */       double maxImageHeightPixels = screenHeight * 0.25D;
/* 300 */       double maxImageWidthPixels = screenWidth * 0.25D;
/*     */ 
/*     */       
/* 303 */       double[] point1 = this.mPdfViewCtrl.convScreenPtToPagePt(0.0D, 0.0D, pageNum);
/* 304 */       double[] point2 = this.mPdfViewCtrl.convScreenPtToPagePt(20.0D, 20.0D, pageNum);
/*     */       
/* 306 */       double pixelsToPageRatio = Math.abs(point1[0] - point2[0]) / 20.0D;
/* 307 */       double maxImageHeightPage = maxImageHeightPixels * pixelsToPageRatio;
/* 308 */       double maxImageWidthPage = maxImageWidthPixels * pixelsToPageRatio;
/*     */ 
/*     */       
/* 311 */       double stampWidth = img.getImageWidth();
/* 312 */       double stampHeight = img.getImageHeight();
/* 313 */       if (imageRotation == 90 || imageRotation == 270) {
/* 314 */         double temp = stampWidth;
/* 315 */         stampWidth = stampHeight;
/* 316 */         stampHeight = temp;
/*     */       } 
/*     */       
/* 319 */       double pageWidth = pageViewBox.getWidth();
/* 320 */       double pageHeight = pageViewBox.getHeight();
/* 321 */       if (pageRotation == 1 || pageRotation == 3) {
/* 322 */         double temp = pageWidth;
/* 323 */         pageWidth = pageHeight;
/* 324 */         pageHeight = temp;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 329 */       if (pageWidth < maxImageWidthPage) {
/* 330 */         maxImageWidthPage = pageWidth;
/*     */       }
/* 332 */       if (pageHeight < maxImageHeightPage) {
/* 333 */         maxImageHeightPage = pageHeight;
/*     */       }
/*     */       
/* 336 */       double scaleFactor = Math.min(maxImageWidthPage / stampWidth, maxImageHeightPage / stampHeight);
/* 337 */       stampWidth *= scaleFactor;
/* 338 */       stampHeight *= scaleFactor;
/*     */ 
/*     */       
/* 341 */       if (viewRotation == 1 || viewRotation == 3) {
/* 342 */         double temp = stampWidth;
/* 343 */         stampWidth = stampHeight;
/* 344 */         stampHeight = temp;
/*     */       } 
/*     */       
/* 347 */       com.pdftron.pdf.Stamper stamper = new com.pdftron.pdf.Stamper(2, stampWidth, stampHeight);
/*     */ 
/*     */       
/* 350 */       if (this.mTargetPoint != null) {
/*     */         
/* 352 */         double[] pageTarget = this.mPdfViewCtrl.convScreenPtToPagePt(this.mTargetPoint.x, this.mTargetPoint.y, pageNum);
/* 353 */         Matrix2D mtx = page.getDefaultMatrix();
/* 354 */         Point pageTargetPoint = mtx.multPoint(pageTarget[0], pageTarget[1]);
/*     */ 
/*     */         
/* 357 */         stamper.setAlignment(-1, -1);
/*     */ 
/*     */ 
/*     */         
/* 361 */         pageTargetPoint.x -= stampWidth / 2.0D;
/* 362 */         pageTargetPoint.y -= stampHeight / 2.0D;
/*     */ 
/*     */ 
/*     */         
/* 366 */         double leftEdge = pageViewBox.getX1() - pageCropBox.getX1();
/* 367 */         double bottomEdge = pageViewBox.getY1() - pageCropBox.getY1();
/* 368 */         if (pageTargetPoint.x > leftEdge + pageWidth - stampWidth) {
/* 369 */           pageTargetPoint.x = leftEdge + pageWidth - stampWidth;
/*     */         }
/* 371 */         if (pageTargetPoint.x < leftEdge) {
/* 372 */           pageTargetPoint.x = leftEdge;
/*     */         }
/*     */         
/* 375 */         if (pageTargetPoint.y > bottomEdge + pageHeight - stampHeight) {
/* 376 */           pageTargetPoint.y = bottomEdge + pageHeight - stampHeight;
/*     */         }
/* 378 */         if (pageTargetPoint.y < bottomEdge) {
/* 379 */           pageTargetPoint.y = bottomEdge;
/*     */         }
/*     */         
/* 382 */         stamper.setPosition(pageTargetPoint.x, pageTargetPoint.y);
/*     */       } else {
/*     */         
/* 385 */         stamper.setPosition(0.0D, 0.0D);
/*     */       } 
/*     */ 
/*     */       
/* 389 */       stamper.setAsAnnotation(true);
/*     */ 
/*     */       
/* 392 */       int stampRotation = (4 - viewRotation) % 4;
/* 393 */       stamper.setRotation(stampRotation * 90.0D + imageRotation);
/*     */ 
/*     */       
/* 396 */       stamper.stampImage(doc, img, new PageSet(pageNum));
/*     */ 
/*     */       
/* 399 */       int numAnnots = page.getNumAnnots();
/* 400 */       Annot annot = page.getAnnot(numAnnots - 1);
/* 401 */       Obj obj = annot.getSDFObj();
/* 402 */       obj.putNumber("pdftronImageStampRotation", 0.0D);
/*     */       
/* 404 */       if (annot.isMarkup()) {
/* 405 */         Markup markup = new Markup(annot);
/* 406 */         setAuthor(markup);
/*     */       } 
/*     */       
/* 409 */       setAnnot(annot, pageNum);
/* 410 */       buildAnnotBBox();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 415 */       this.mPdfViewCtrl.update(annot, pageNum);
/* 416 */       raiseAnnotationAddedEvent(annot, pageNum);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 422 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 423 */       String recentlyUsedFiles = settings.getString("image_stamper_most_recently_used_files", "");
/* 424 */       String[] recentlyUsedFilesArray = recentlyUsedFiles.split(" ");
/* 425 */       Boolean recentlyUsed = Boolean.valueOf(false);
/*     */       
/* 427 */       if (filePath != null) {
/* 428 */         for (String recentlyUsedFile : recentlyUsedFilesArray) {
/* 429 */           if (filePath.equals(recentlyUsedFile)) {
/* 430 */             recentlyUsed = Boolean.valueOf(true);
/* 431 */             AnalyticsHandlerAdapter.getInstance().sendEvent(8, "stamper recent file");
/*     */           } 
/*     */         } 
/*     */         
/* 435 */         if (!recentlyUsed.booleanValue()) {
/* 436 */           int length = (recentlyUsedFilesArray.length < 6) ? recentlyUsedFilesArray.length : 5;
/* 437 */           StringBuilder str = new StringBuilder(filePath);
/* 438 */           for (int i = 0; i < length; i++) {
/* 439 */             str.append(" ").append(recentlyUsedFilesArray[i]);
/*     */           }
/*     */           
/* 442 */           SharedPreferences.Editor editor = settings.edit();
/* 443 */           editor.putString("image_stamper_most_recently_used_files", str.toString());
/* 444 */           editor.apply();
/*     */         } 
/*     */       } 
/* 447 */       return true;
/* 448 */     } catch (Exception e) {
/* 449 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/* 450 */       return false;
/*     */     } finally {
/* 452 */       if (shouldUnlock) {
/* 453 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/* 455 */       Utils.closeQuietly(filter);
/*     */       
/* 457 */       this.mTargetPoint = null;
/* 458 */       safeSetNextToolMode();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void safeSetNextToolMode() {
/* 467 */     if (this.mForceSameNextToolMode) {
/* 468 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*     */     } else {
/* 470 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\Stamper.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */