/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.DialogInterface;
/*     */ import android.content.Intent;
/*     */ import android.graphics.Canvas;
/*     */ import android.graphics.Matrix;
/*     */ import android.graphics.Paint;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.net.Uri;
/*     */ import android.util.Pair;
/*     */ import android.util.Patterns;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.AnnotationClipboardHelper;
/*     */ import com.pdftron.pdf.utils.ShortcutHelper;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class Pan
/*     */   extends Tool
/*     */ {
/*     */   private Paint mPaint;
/*     */   boolean mSuppressSingleTapConfirmed;
/*     */   private QuickMenuItem mPasteMenuEntry;
/*     */   private PointF mTargetPoint;
/*     */   private RectF mAnchor;
/*     */   
/*     */   public Pan(@NonNull PDFViewCtrl ctrl) {
/*  56 */     super(ctrl);
/*     */     
/*  58 */     this.mPaint = new Paint();
/*  59 */     this.mPaint.setAntiAlias(true);
/*  60 */     this.mSuppressSingleTapConfirmed = false;
/*     */ 
/*     */ 
/*     */     
/*  64 */     this.mPdfViewCtrl.setBuiltInPageSlidingState(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  72 */     return ToolManager.ToolMode.PAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  77 */     return 28;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate() {
/*  85 */     this.mPasteMenuEntry = new QuickMenuItem(this.mPdfViewCtrl.getContext(), R.id.qm_paste, 0);
/*  86 */     this.mPasteMenuEntry.setTitle(R.string.tools_qm_paste);
/*     */   }
/*     */ 
/*     */   
/*     */   protected QuickMenu createQuickMenu() {
/*  91 */     QuickMenu quickMenu = super.createQuickMenu();
/*  92 */     quickMenu.inflate(R.menu.pan);
/*     */     
/*  94 */     if (AnnotationClipboardHelper.isItemCopied(this.mPdfViewCtrl.getContext())) {
/*  95 */       QuickMenuItem menuItem = (QuickMenuItem)quickMenu.getMenu().add(R.id.qm_first_row_group, R.id.qm_paste, -1, R.string.tools_qm_paste);
/*  96 */       menuItem.setIcon(R.drawable.ic_content_paste_black_24dp);
/*     */     } 
/*  98 */     quickMenu.addMenuEntries(((QuickMenuBuilder)quickMenu.getMenu()).getMenuItems(), 4);
/*  99 */     quickMenu.setDividerVisibility(4);
/* 100 */     return quickMenu;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 108 */     super.onDown(e);
/* 109 */     boolean stylusAsPen = ((ToolManager)this.mPdfViewCtrl.getToolManager()).isStylusAsPen();
/* 110 */     if (stylusAsPen && e.getPointerCount() == 1 && e.getToolType(0) == 2) {
/* 111 */       if (e.getButtonState() == 32) {
/* 112 */         this.mNextToolMode = ToolManager.ToolMode.INK_ERASER;
/*     */       } else {
/* 114 */         this.mNextToolMode = ToolManager.ToolMode.INK_CREATE;
/*     */       } 
/*     */     }
/* 117 */     if (this.mNextToolMode == ToolManager.ToolMode.PAN && ShortcutHelper.isTextSelect(e)) {
/* 118 */       int x = (int)(e.getX() + 0.5D);
/* 119 */       int y = (int)(e.getY() + 0.5D);
/* 120 */       if (!Utils.isNougat() || this.mPdfViewCtrl.isThereTextInRect((x - 1), (y - 1), (x + 1), (y + 1))) {
/* 121 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_SELECT;
/*     */       }
/*     */     } 
/*     */     
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 133 */     super.onMove(e1, e2, x_dist, y_dist);
/* 134 */     this.mJustSwitchedFromAnotherTool = false;
/* 135 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 143 */     super.onUp(e, priorEventMode);
/* 144 */     this.mJustSwitchedFromAnotherTool = false;
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 153 */     super.onSingleTapConfirmed(e);
/* 154 */     showTransientPageNumber();
/*     */     
/* 156 */     if (this.mSuppressSingleTapConfirmed) {
/* 157 */       this.mSuppressSingleTapConfirmed = false;
/* 158 */       this.mJustSwitchedFromAnotherTool = false;
/* 159 */       return false;
/*     */     } 
/*     */     
/* 162 */     int x = (int)(e.getX() + 0.5D);
/* 163 */     int y = (int)(e.getY() + 0.5D);
/* 164 */     selectAnnot(x, y);
/*     */     
/* 166 */     boolean isRTReply = false;
/*     */     try {
/* 168 */       isRTReply = AnnotUtils.hasReplyTypeReply(this.mAnnot);
/* 169 */     } catch (Exception ex) {
/* 170 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } 
/*     */     
/* 173 */     if (this.mAnnot != null && !isRTReply) {
/* 174 */       boolean isLink = false;
/* 175 */       boolean shouldUnlockRead = false;
/*     */       try {
/* 177 */         this.mPdfViewCtrl.docLockRead();
/* 178 */         shouldUnlockRead = true;
/*     */         
/* 180 */         isLink = (this.mAnnot.getType() == 1);
/*     */         
/* 182 */         this.mNextToolMode = safeSetNextToolMode(ToolConfig.getInstance().getAnnotationHandlerToolMode(AnnotUtils.getAnnotType(this.mAnnot)));
/* 183 */         this.mAnnotPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/* 184 */       } catch (Exception ex) {
/* 185 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } finally {
/* 187 */         if (shouldUnlockRead) {
/* 188 */           this.mPdfViewCtrl.docUnlockRead();
/*     */         }
/*     */       } 
/* 191 */       if (!isLink) {
/* 192 */         Pair<ToolManager.ToolMode, ArrayList<Annot>> pair = canSelectGroupAnnot(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/* 193 */         if (pair != null && pair.first != null) {
/* 194 */           this.mNextToolMode = (ToolManager.ToolModeBase)pair.first;
/* 195 */           this.mAnnot = null;
/* 196 */           this.mGroupAnnots = (ArrayList<Annot>)pair.second;
/*     */         } 
/*     */       } 
/*     */     } else {
/* 200 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */ 
/*     */       
/*     */       try {
/* 204 */         PDFViewCtrl.LinkInfo linkInfo = this.mPdfViewCtrl.getLinkAt(x, y);
/* 205 */         if (linkInfo != null) {
/* 206 */           int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/* 207 */           if (onInterceptAnnotationHandling(linkInfo, page)) {
/* 208 */             return true;
/*     */           }
/*     */           
/* 211 */           String url = linkInfo.getURL();
/* 212 */           if (url.startsWith("mailto:") || Patterns.EMAIL_ADDRESS.matcher(url).matches()) {
/* 213 */             if (url.startsWith("mailto:")) {
/* 214 */               url = url.substring(7);
/*     */             }
/* 216 */             Intent i = new Intent("android.intent.action.SENDTO", Uri.fromParts("mailto", url, null));
/* 217 */             this.mPdfViewCtrl.getContext().startActivity(Intent.createChooser(i, getStringFromResId(R.string.tools_misc_sendemail)));
/*     */           } else {
/*     */             
/* 220 */             if (!url.startsWith("https://") && !url.startsWith("http://")) {
/* 221 */               url = "http://" + url;
/*     */             }
/*     */             
/* 224 */             final String finalUrl = url;
/*     */             
/* 226 */             AlertDialog.Builder builder = new AlertDialog.Builder(this.mPdfViewCtrl.getContext());
/* 227 */             builder.setTitle(R.string.tools_dialog_open_web_page_title)
/* 228 */               .setMessage(String.format(getStringFromResId(R.string.tools_dialog_open_web_page_message), new Object[] { finalUrl
/* 229 */                   })).setIcon(null)
/* 230 */               .setPositiveButton(R.string.open, new DialogInterface.OnClickListener()
/*     */                 {
/*     */                   public void onClick(DialogInterface dialog, int which) {
/* 233 */                     Intent i = new Intent("android.intent.action.VIEW", Uri.parse(finalUrl));
/* 234 */                     Pan.this.mPdfViewCtrl.getContext().startActivity(Intent.createChooser(i, Pan.this.getStringFromResId(R.string.tools_misc_openwith)));
/*     */                   }
/* 237 */                 }).setNegativeButton(R.string.cancel, null);
/* 238 */             builder.create().show();
/*     */           } 
/*     */         } 
/* 241 */       } catch (Exception ex) {
/* 242 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */       } 
/*     */     } 
/* 245 */     this.mPdfViewCtrl.invalidate();
/*     */     
/* 247 */     this.mJustSwitchedFromAnotherTool = false;
/* 248 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/* 256 */     if (changed && isQuickMenuShown() && this.mAnnot == null) {
/* 257 */       closeQuickMenu();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onLongPress(MotionEvent e) {
/* 266 */     if (this.mAvoidLongPressAttempt) {
/* 267 */       this.mAvoidLongPressAttempt = false;
/* 268 */       return false;
/*     */     } 
/*     */     
/* 271 */     int x = (int)(e.getX() + 0.5D);
/* 272 */     int y = (int)(e.getY() + 0.5D);
/* 273 */     selectAnnot(x, y);
/*     */     
/* 275 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 277 */       this.mPdfViewCtrl.docLockRead();
/* 278 */       shouldUnlockRead = true;
/* 279 */       boolean is_form = (this.mAnnot != null && this.mAnnot.getType() == 19);
/*     */       
/* 281 */       RectF textSelectRect = getTextSelectRect(e.getX(), e.getY());
/* 282 */       boolean isTextSelect = (!is_form && this.mPdfViewCtrl.selectByRect(textSelectRect.left, textSelectRect.top, textSelectRect.right, textSelectRect.bottom));
/*     */       
/* 284 */       boolean isMadeByPDFTron = isMadeByPDFTron(this.mAnnot);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 289 */       ToolManager.ToolMode toolMode = ToolConfig.getInstance().getPanLongPressSwitchToolCallback().onPanLongPressSwitchTool(this.mAnnot, isMadeByPDFTron, isTextSelect);
/*     */       
/* 291 */       this.mNextToolMode = toolMode;
/*     */       
/* 293 */       if (this.mAnnot != null) {
/* 294 */         this.mAnnotPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */       }
/*     */ 
/*     */       
/* 298 */       if (toolMode == ToolManager.ToolMode.PAN) {
/* 299 */         this.mSelectPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/* 300 */         if (this.mSelectPageNum > 0) {
/* 301 */           this.mAnchor = new RectF((x - 5), y, (x + 5), (y + 1));
/* 302 */           this.mTargetPoint = new PointF(e.getX(), e.getY());
/* 303 */           showMenu(this.mAnchor);
/*     */         } 
/*     */       } 
/* 306 */     } catch (Exception ex) {
/* 307 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 309 */       if (shouldUnlockRead) {
/* 310 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */     
/* 314 */     this.mJustSwitchedFromAnotherTool = false;
/* 315 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PointF getTargetPoint() {
/* 323 */     return this.mTargetPoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleBegin(float x, float y) {
/* 331 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onScaleEnd(float x, float y) {
/* 339 */     super.onScaleEnd(x, y);
/* 340 */     this.mJustSwitchedFromAnotherTool = false;
/* 341 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDraw(Canvas canvas, Matrix tfm) {
/* 349 */     this.mPageNumPosAdjust = 0.0F;
/* 350 */     super.onDraw(canvas, tfm);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 358 */     if (super.onQuickMenuClicked(menuItem)) {
/* 359 */       return true;
/*     */     }
/*     */     
/* 362 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 363 */     if (toolManager.isReadOnly()) {
/* 364 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 365 */       return true;
/*     */     } 
/*     */     
/* 368 */     if (menuItem.getItemId() == R.id.qm_line) {
/* 369 */       this.mNextToolMode = ToolManager.ToolMode.LINE_CREATE;
/* 370 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 371 */       toolManager.setTool(tool);
/* 372 */     } else if (menuItem.getItemId() == R.id.qm_arrow) {
/* 373 */       this.mNextToolMode = ToolManager.ToolMode.ARROW_CREATE;
/* 374 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 375 */       toolManager.setTool(tool);
/* 376 */     } else if (menuItem.getItemId() == R.id.qm_ruler) {
/* 377 */       this.mNextToolMode = ToolManager.ToolMode.RULER_CREATE;
/* 378 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 379 */       toolManager.setTool(tool);
/* 380 */     } else if (menuItem.getItemId() == R.id.qm_perimeter_measure) {
/* 381 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 382 */         toolManager.onOpenEditToolbar(ToolManager.ToolMode.PERIMETER_MEASURE_CREATE);
/*     */       }
/* 384 */     } else if (menuItem.getItemId() == R.id.qm_area_measure) {
/* 385 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 386 */         toolManager.onOpenEditToolbar(ToolManager.ToolMode.AREA_MEASURE_CREATE);
/*     */       }
/* 388 */     } else if (menuItem.getItemId() == R.id.qm_rect_area_measure) {
/* 389 */       this.mNextToolMode = ToolManager.ToolMode.RECT_AREA_MEASURE_CREATE;
/* 390 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 391 */       toolManager.setTool(tool);
/* 392 */     } else if (menuItem.getItemId() == R.id.qm_polyline) {
/* 393 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 394 */         toolManager.onOpenEditToolbar(ToolManager.ToolMode.POLYLINE_CREATE);
/*     */       }
/* 396 */     } else if (menuItem.getItemId() == R.id.qm_rectangle) {
/* 397 */       this.mNextToolMode = ToolManager.ToolMode.RECT_CREATE;
/* 398 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 399 */       toolManager.setTool(tool);
/* 400 */     } else if (menuItem.getItemId() == R.id.qm_oval) {
/* 401 */       this.mNextToolMode = ToolManager.ToolMode.OVAL_CREATE;
/* 402 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 403 */       toolManager.setTool(tool);
/* 404 */     } else if (menuItem.getItemId() == R.id.qm_sound) {
/* 405 */       if (null != this.mTargetPoint) {
/* 406 */         this.mNextToolMode = ToolManager.ToolMode.SOUND_CREATE;
/* 407 */         SoundCreate tool = (SoundCreate)toolManager.createTool(this.mNextToolMode, this);
/* 408 */         toolManager.setTool(tool);
/* 409 */         tool.setTargetPoint(this.mTargetPoint, true);
/*     */       } 
/* 411 */     } else if (menuItem.getItemId() == R.id.qm_file_attachment) {
/* 412 */       if (null != this.mTargetPoint) {
/* 413 */         this.mNextToolMode = ToolManager.ToolMode.FILE_ATTACHMENT_CREATE;
/* 414 */         FileAttachmentCreate tool = (FileAttachmentCreate)toolManager.createTool(this.mNextToolMode, this);
/* 415 */         toolManager.setTool(tool);
/* 416 */         tool.setTargetPoint(this.mTargetPoint, true);
/*     */       } 
/* 418 */     } else if (menuItem.getItemId() == R.id.qm_polygon) {
/* 419 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 420 */         toolManager.onOpenEditToolbar(ToolManager.ToolMode.POLYGON_CREATE);
/*     */       }
/* 422 */     } else if (menuItem.getItemId() == R.id.qm_cloud) {
/* 423 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 424 */         toolManager.onOpenEditToolbar(ToolManager.ToolMode.CLOUD_CREATE);
/*     */       }
/* 426 */     } else if (menuItem.getItemId() == R.id.qm_free_hand) {
/* 427 */       this.mNextToolMode = ToolManager.ToolMode.INK_CREATE;
/* 428 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 429 */       ((FreehandCreate)tool).setMultiStrokeMode(false);
/* 430 */       ((FreehandCreate)tool).setTimedModeEnabled(false);
/* 431 */       toolManager.setTool(tool);
/* 432 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 433 */         toolManager.onInkEditSelected(null, 0);
/*     */       }
/* 435 */     } else if (menuItem.getItemId() == R.id.qm_free_highlighter) {
/* 436 */       this.mNextToolMode = ToolManager.ToolMode.FREE_HIGHLIGHTER;
/* 437 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 438 */       toolManager.setTool(tool);
/* 439 */     } else if (menuItem.getItemId() == R.id.qm_free_text) {
/* 440 */       if (null != this.mTargetPoint) {
/* 441 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_CREATE;
/* 442 */         FreeTextCreate freeTextTool = (FreeTextCreate)toolManager.createTool(this.mNextToolMode, this);
/* 443 */         toolManager.setTool(freeTextTool);
/* 444 */         freeTextTool.initFreeText(this.mTargetPoint);
/*     */       } 
/* 446 */     } else if (menuItem.getItemId() == R.id.qm_callout) {
/* 447 */       if (null != this.mTargetPoint) {
/* 448 */         this.mNextToolMode = ToolManager.ToolMode.CALLOUT_CREATE;
/* 449 */         CalloutCreate calloutCreate = (CalloutCreate)toolManager.createTool(this.mNextToolMode, this);
/* 450 */         toolManager.setTool(calloutCreate);
/* 451 */         calloutCreate.initFreeText(this.mTargetPoint);
/* 452 */         AnnotEditAdvancedShape annotEdit = (AnnotEditAdvancedShape)toolManager.createTool(ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE, calloutCreate);
/* 453 */         toolManager.setTool(annotEdit);
/* 454 */         annotEdit.enterText();
/* 455 */         annotEdit.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_ADVANCED_SHAPE;
/*     */       } 
/* 457 */     } else if (menuItem.getItemId() == R.id.qm_sticky_note) {
/* 458 */       if (null != this.mTargetPoint) {
/* 459 */         this.mNextToolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/* 460 */         StickyNoteCreate stickyNoteTool = (StickyNoteCreate)toolManager.createTool(this.mNextToolMode, this);
/* 461 */         toolManager.setTool(stickyNoteTool);
/* 462 */         stickyNoteTool.setTargetPoint(this.mTargetPoint);
/*     */       } 
/* 464 */     } else if (menuItem.getItemId() == R.id.qm_floating_sig) {
/* 465 */       if (null != this.mTargetPoint) {
/* 466 */         this.mNextToolMode = ToolManager.ToolMode.SIGNATURE;
/* 467 */         Signature signatureTool = (Signature)toolManager.createTool(this.mNextToolMode, this);
/* 468 */         toolManager.setTool(signatureTool);
/* 469 */         signatureTool.setTargetPoint(this.mTargetPoint);
/*     */       } 
/* 471 */     } else if (menuItem.getItemId() == R.id.qm_image_stamper) {
/* 472 */       if (null != this.mTargetPoint) {
/* 473 */         this.mNextToolMode = ToolManager.ToolMode.STAMPER;
/* 474 */         Stamper stamperTool = (Stamper)toolManager.createTool(this.mNextToolMode, this);
/* 475 */         toolManager.setTool(stamperTool);
/* 476 */         stamperTool.setTargetPoint(this.mTargetPoint, true);
/*     */       } 
/* 478 */     } else if (menuItem.getItemId() == R.id.qm_rubber_stamper) {
/* 479 */       if (null != this.mTargetPoint) {
/* 480 */         this.mNextToolMode = ToolManager.ToolMode.RUBBER_STAMPER;
/* 481 */         RubberStampCreate tool = (RubberStampCreate)toolManager.createTool(this.mNextToolMode, this);
/* 482 */         toolManager.setTool(tool);
/* 483 */         tool.setTargetPoint(this.mTargetPoint, true);
/*     */       } 
/* 485 */     } else if (menuItem.getItemId() == R.id.qm_paste) {
/* 486 */       if (null != this.mTargetPoint) {
/* 487 */         pasteAnnot(this.mTargetPoint);
/*     */       }
/* 489 */     } else if (menuItem.getItemId() == R.id.qm_link) {
/* 490 */       this.mNextToolMode = ToolManager.ToolMode.RECT_LINK;
/* 491 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 492 */       toolManager.setTool(tool);
/* 493 */     } else if (menuItem.getItemId() == R.id.qm_ink_eraser) {
/* 494 */       this.mNextToolMode = ToolManager.ToolMode.INK_ERASER;
/* 495 */       if (toolManager.isOpenEditToolbarFromPan()) {
/* 496 */         toolManager.onOpenAnnotationToolbar(ToolManager.ToolMode.INK_ERASER);
/*     */       }
/* 498 */     } else if (menuItem.getItemId() == R.id.qm_form_text) {
/* 499 */       this.mNextToolMode = ToolManager.ToolMode.FORM_TEXT_FIELD_CREATE;
/* 500 */       TextFieldCreate tool = (TextFieldCreate)toolManager.createTool(this.mNextToolMode, this);
/* 501 */       toolManager.setTool(tool);
/* 502 */     } else if (menuItem.getItemId() == R.id.qm_form_check_box) {
/* 503 */       this.mNextToolMode = ToolManager.ToolMode.FORM_CHECKBOX_CREATE;
/* 504 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 505 */       toolManager.setTool(tool);
/* 506 */     } else if (menuItem.getItemId() == R.id.qm_form_combo_box) {
/* 507 */       this.mNextToolMode = ToolManager.ToolMode.FORM_COMBO_BOX_CREATE;
/* 508 */       ComboBoxFieldCreate tool = (ComboBoxFieldCreate)toolManager.createTool(this.mNextToolMode, this);
/* 509 */       toolManager.setTool(tool);
/* 510 */     } else if (menuItem.getItemId() == R.id.qm_form_list_box) {
/* 511 */       this.mNextToolMode = ToolManager.ToolMode.FORM_LIST_BOX_CREATE;
/* 512 */       ListBoxFieldCreate tool = (ListBoxFieldCreate)toolManager.createTool(this.mNextToolMode, this);
/* 513 */       toolManager.setTool(tool);
/* 514 */     } else if (menuItem.getItemId() == R.id.qm_form_signature) {
/* 515 */       this.mNextToolMode = ToolManager.ToolMode.FORM_SIGNATURE_CREATE;
/* 516 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 517 */       toolManager.setTool(tool);
/* 518 */     } else if (menuItem.getItemId() == R.id.qm_form_radio_group) {
/* 519 */       this.mNextToolMode = ToolManager.ToolMode.FORM_RADIO_GROUP_CREATE;
/* 520 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 521 */       toolManager.setTool(tool);
/* 522 */     } else if (menuItem.getItemId() == R.id.qm_rect_group_select) {
/* 523 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT_RECT_GROUP;
/* 524 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 525 */       toolManager.setTool(tool);
/* 526 */     } else if (menuItem.getItemId() == R.id.qm_rect_redaction) {
/* 527 */       this.mNextToolMode = ToolManager.ToolMode.RECT_REDACTION;
/* 528 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, this);
/* 529 */       toolManager.setTool(tool);
/* 530 */     } else if (menuItem.getItemId() == R.id.qm_page_redaction) {
/* 531 */       toolManager.getRedactionManager().openPageRedactionDialog();
/* 532 */     } else if (menuItem.getItemId() == R.id.qm_search_redaction) {
/* 533 */       toolManager.getRedactionManager().openRedactionBySearchDialog();
/*     */     } else {
/*     */       
/* 536 */       return false;
/*     */     } 
/*     */     
/* 539 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean showMenu(RectF anchor_rect) {
/* 544 */     if (onInterceptAnnotationHandling(this.mAnnot)) {
/* 545 */       return true;
/*     */     }
/*     */     
/* 548 */     if (this.mPdfViewCtrl == null) {
/* 549 */       return false;
/*     */     }
/*     */     
/* 552 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 553 */     return (toolManager != null && !toolManager.isQuickMenuDisabled() && super.showMenu(anchor_rect));
/*     */   }
/*     */   
/*     */   private void selectAnnot(int x, int y) {
/* 557 */     unsetAnnot();
/*     */     
/* 559 */     this.mAnnotPageNum = 0;
/*     */     
/* 561 */     this.mPdfViewCtrl.cancelFindText();
/* 562 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 564 */       this.mPdfViewCtrl.docLockRead();
/* 565 */       shouldUnlockRead = true;
/* 566 */       Annot a = this.mPdfViewCtrl.getAnnotationAt(x, y);
/*     */       
/* 568 */       if (a != null && a.isValid()) {
/* 569 */         setAnnot(a, this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y));
/* 570 */         buildAnnotBBox();
/*     */       } 
/* 572 */     } catch (Exception ex) {
/* 573 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 575 */       if (shouldUnlockRead) {
/* 576 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getModeAHLabel() {
/* 586 */     return 102;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getQuickMenuAnalyticType() {
/* 594 */     return 1;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\Pan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */