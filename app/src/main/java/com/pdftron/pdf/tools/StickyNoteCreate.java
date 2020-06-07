/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.DialogInterface;
/*     */ import android.content.SharedPreferences;
/*     */ import android.content.res.Configuration;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.PointF;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.annots.Popup;
/*     */ import com.pdftron.pdf.annots.Text;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
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
/*     */ @Keep
/*     */ public class StickyNoteCreate
/*     */   extends SimpleShapeCreate
/*     */   implements DialogAnnotNote.DialogAnnotNoteListener, AnnotStyle.OnAnnotStyleChangeListener
/*     */ {
/*     */   private String mIconType;
/*     */   private int mIconColor;
/*     */   private float mIconOpacity;
/*     */   private int mAnnotButtonPressed;
/*     */   private boolean mClosed;
/*     */   private DialogStickyNote mDialogStickyNote;
/*     */   
/*     */   public StickyNoteCreate(@NonNull PDFViewCtrl ctrl) {
/*  62 */     super(ctrl);
/*     */     
/*  64 */     this.mNextToolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*     */ 
/*     */     
/*  67 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*     */     
/*  69 */     this.mIconColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*  70 */     this.mIconType = settings.getString(getIconKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultIcon(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*  71 */     this.mIconOpacity = settings.getFloat(getOpacityKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultOpacity(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  79 */     return ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  84 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(int color, float opacity, float thickness, int fillColor, String icon, String pdfTronFontName) {
/*  92 */     this.mIconColor = color;
/*  93 */     this.mIconType = icon;
/*  94 */     this.mIconOpacity = opacity;
/*     */     
/*  96 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  97 */     SharedPreferences.Editor editor = settings.edit();
/*     */     
/*  99 */     editor.putString(getIconKey(getCreateAnnotType()), icon);
/* 100 */     editor.putInt(getColorKey(getCreateAnnotType()), color);
/* 101 */     editor.putFloat(getOpacityKey(getCreateAnnotType()), opacity);
/*     */     
/* 103 */     editor.apply();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMove(MotionEvent e1, MotionEvent e2, float x_dist, float y_dist) {
/* 111 */     super.onMove(e1, e2, x_dist, y_dist);
/*     */     
/* 113 */     if (this.mAllowTwoFingerScroll) {
/* 114 */       return false;
/*     */     }
/*     */     
/* 117 */     this.mPt1.x = e2.getX() + this.mPdfViewCtrl.getScrollX();
/* 118 */     this.mPt1.y = e2.getY() + this.mPdfViewCtrl.getScrollY();
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onFlingStop() {
/* 128 */     if (this.mAllowTwoFingerScroll) {
/* 129 */       doneTwoFingerScrolling();
/*     */     }
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Annot createMarkup(@NonNull PDFDoc doc, Rect bbox) throws PDFNetException {
/* 139 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 148 */     if (this.mAllowTwoFingerScroll || this.mPt1.x < 0.0F || this.mPt1.y < 0.0F) {
/* 149 */       doneTwoFingerScrolling();
/*     */       
/* 151 */       return false;
/*     */     } 
/*     */     
/* 154 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */ 
/*     */     
/* 157 */     if (toolManager.isQuickMenuJustClosed()) {
/* 158 */       return true;
/*     */     }
/*     */     
/* 161 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/* 162 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 166 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH)
/*     */     {
/*     */       
/* 169 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 173 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/* 174 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 178 */     if (this.mIsAllPointsOutsidePage) {
/* 179 */       return true;
/*     */     }
/*     */     
/* 182 */     boolean shouldCreate = true;
/* 183 */     int x = (int)e.getX();
/* 184 */     int y = (int)e.getY();
/* 185 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt(x, y, x, y);
/* 186 */     int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */     try {
/* 188 */       for (Annot annot : annots) {
/* 189 */         if (annot.getType() == 0) {
/* 190 */           shouldCreate = false;
/* 191 */           setAnnot(annot, pageNum);
/* 192 */           buildAnnotBBox();
/* 193 */           this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/* 194 */           setCurrentDefaultToolModeHelper(getToolMode());
/*     */           break;
/*     */         } 
/*     */       } 
/* 198 */     } catch (PDFNetException ex) {
/* 199 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */     
/* 202 */     if (shouldCreate) {
/* 203 */       setTargetPoint(new PointF(x, y));
/*     */     }
/*     */     
/* 206 */     return skipOnUpPriorEvent(priorEventMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onDown(MotionEvent e) {
/* 217 */     this.mAnnotPushedBack = super.onDown(e);
/*     */     
/* 219 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose() {
/* 227 */     if (!this.mClosed) {
/* 228 */       this.mClosed = true;
/* 229 */       unsetAnnot();
/*     */     } 
/*     */     
/* 232 */     if (this.mDialogStickyNote != null && this.mDialogStickyNote.isShowing()) {
/*     */       
/* 234 */       this.mAnnotButtonPressed = -1;
/* 235 */       prepareDialogStickyNoteDismiss();
/* 236 */       this.mDialogStickyNote.dismiss();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 242 */     super.onQuickMenuClicked(menuItem);
/* 243 */     this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/* 244 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onConfigurationChanged(Configuration newConfig) {
/* 252 */     super.onConfigurationChanged(newConfig);
/* 253 */     setNextToolMode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onAnnotButtonPressed(int button) {
/* 261 */     this.mAnnotButtonPressed = button;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetPoint(PointF point) {
/* 270 */     point.x += this.mPdfViewCtrl.getScrollX();
/* 271 */     point.y += this.mPdfViewCtrl.getScrollY();
/* 272 */     this.mDownPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(point.x, point.y);
/*     */     
/* 274 */     createStickyNote();
/*     */     
/* 276 */     showPopup();
/*     */   }
/*     */   
/*     */   private void createStickyNote() {
/* 280 */     boolean shouldUnlock = false;
/*     */     
/*     */     try {
/* 283 */       addOldTools();
/*     */       
/* 285 */       this.mPdfViewCtrl.docLock(true);
/* 286 */       shouldUnlock = true;
/*     */       
/* 288 */       double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt((this.mPt1.x - this.mPdfViewCtrl.getScrollX()), (this.mPt1.y - this.mPdfViewCtrl.getScrollY()), this.mDownPageNum);
/* 289 */       pts[1] = pts[1] - 20.0D;
/* 290 */       Point p = new Point(pts[0] - 0.0D, pts[1] - 0.0D);
/* 291 */       Text text = Text.create((Doc)this.mPdfViewCtrl.getDoc(), p);
/* 292 */       text.setIcon(this.mIconType);
/* 293 */       ColorPt color = getColorPoint(this.mIconColor);
/* 294 */       if (color != null) {
/* 295 */         text.setColor(color, 3);
/*     */       } else {
/* 297 */         text.setColor(new ColorPt(1.0D, 1.0D, 0.0D), 3);
/*     */       } 
/* 299 */       text.setOpacity(this.mIconOpacity);
/*     */       
/* 301 */       Rect rect = new Rect();
/* 302 */       rect.set(pts[0] + 20.0D, pts[1] + 20.0D, pts[0] + 90.0D, pts[1] + 90.0D);
/* 303 */       Popup pop = Popup.create((Doc)this.mPdfViewCtrl.getDoc(), rect);
/* 304 */       pop.setParent((Annot)text);
/* 305 */       text.setPopup(pop);
/*     */       
/* 307 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mDownPageNum);
/* 308 */       page.annotPushBack((Annot)text);
/* 309 */       page.annotPushBack((Annot)pop);
/* 310 */       setAnnot((Annot)text, this.mDownPageNum);
/* 311 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/*     */       
/* 313 */       this.mAnnotPushedBack = true;
/* 314 */       buildAnnotBBox();
/* 315 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 316 */       raiseAnnotationAddedEvent(this.mAnnot, this.mAnnotPageNum);
/* 317 */     } catch (Exception ex) {
/* 318 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 319 */       ((ToolManager)this.mPdfViewCtrl.getToolManager()).annotationCouldNotBeAdded(ex.getMessage());
/* 320 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 322 */       if (shouldUnlock) {
/* 323 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private ColorPt getColorPoint(int color) {
/* 329 */     double r = Color.red(color) / 255.0D;
/* 330 */     double g = Color.green(color) / 255.0D;
/* 331 */     double b = Color.blue(color) / 255.0D;
/* 332 */     ColorPt c = null;
/*     */     try {
/* 334 */       c = new ColorPt(r, g, b);
/* 335 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 338 */     return c;
/*     */   }
/*     */   
/*     */   private void showPopup() {
/* 342 */     this.mNextToolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*     */     
/* 344 */     if (this.mAnnot == null) {
/*     */       return;
/*     */     }
/*     */     
/* 348 */     boolean canShow = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getStickyNoteShowPopup();
/* 349 */     if (!canShow) {
/* 350 */       setNextToolMode();
/* 351 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 352 */       if (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode) {
/* 353 */         toolManager.selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 359 */       String iconType = "";
/* 360 */       float opacity = 1.0F;
/*     */       try {
/* 362 */         Text t = new Text(this.mAnnot);
/* 363 */         iconType = t.getIconName();
/* 364 */         opacity = (float)t.getOpacity();
/* 365 */       } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */       
/* 369 */       ColorPt colorPt = this.mAnnot.getColorAsRGB();
/* 370 */       int color = Utils.colorPt2color(colorPt);
/* 371 */       this.mDialogStickyNote = new DialogStickyNote(this.mPdfViewCtrl, "", false, iconType, color, opacity);
/* 372 */       this.mDialogStickyNote.setAnnotAppearanceChangeListener(this);
/* 373 */       this.mDialogStickyNote.setAnnotNoteListener(this);
/* 374 */       this.mDialogStickyNote.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */           {
/*     */             public void onDismiss(DialogInterface dialogInterface) {
/* 377 */               StickyNoteCreate.this.prepareDialogStickyNoteDismiss();
/*     */             }
/*     */           });
/* 380 */       this.mDialogStickyNote.show();
/*     */     }
/* 382 */     catch (Exception e1) {
/* 383 */       AnalyticsHandlerAdapter.getInstance().sendException(e1);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void prepareDialogStickyNoteDismiss() {
/* 388 */     if (this.mPdfViewCtrl == null || this.mAnnot == null || this.mDialogStickyNote == null) {
/*     */       return;
/*     */     }
/* 391 */     boolean createdAnnot = false;
/* 392 */     if (this.mAnnotButtonPressed == -1) {
/* 393 */       boolean shouldUnlock = false;
/*     */       try {
/* 395 */         Markup markup = new Markup(this.mAnnot);
/*     */ 
/*     */         
/* 398 */         this.mPdfViewCtrl.docLock(true);
/* 399 */         shouldUnlock = true;
/* 400 */         raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 401 */         Utils.handleEmptyPopup(this.mPdfViewCtrl, markup);
/* 402 */         Popup popup = markup.getPopup();
/* 403 */         popup.setContents(this.mDialogStickyNote.getNote());
/* 404 */         setAuthor(markup);
/* 405 */         raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/* 406 */         createdAnnot = true;
/* 407 */       } catch (Exception e) {
/* 408 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 410 */         if (shouldUnlock) {
/* 411 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 416 */       deleteStickyAnnot();
/*     */     } 
/* 418 */     this.mAnnotButtonPressed = 0;
/* 419 */     this.mDialogStickyNote.prepareDismiss();
/* 420 */     setNextToolMode();
/* 421 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 422 */     if (createdAnnot && (toolManager.isAutoSelectAnnotation() || !this.mForceSameNextToolMode)) {
/* 423 */       toolManager.selectAnnot(this.mAnnot, this.mAnnotPageNum);
/*     */     }
/*     */   }
/*     */   
/*     */   private void deleteStickyAnnot() {
/* 428 */     boolean shouldUnlock = false;
/*     */ 
/*     */     
/*     */     try {
/* 432 */       this.mPdfViewCtrl.docLock(true);
/* 433 */       shouldUnlock = true;
/*     */       
/* 435 */       raiseAnnotationPreRemoveEvent(this.mAnnot, this.mAnnotPageNum);
/* 436 */       Page page = this.mPdfViewCtrl.getDoc().getPage(this.mAnnotPageNum);
/* 437 */       page.annotRemove(this.mAnnot);
/* 438 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/*     */ 
/*     */       
/* 441 */       raiseAnnotationRemovedEvent(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 443 */       unsetAnnot();
/* 444 */     } catch (Exception e) {
/* 445 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 447 */       if (shouldUnlock) {
/* 448 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void editColor(int color) {
/* 455 */     boolean shouldUnlock = false;
/*     */ 
/*     */     
/*     */     try {
/* 459 */       this.mPdfViewCtrl.docLock(true);
/* 460 */       shouldUnlock = true;
/* 461 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 462 */       ColorPt colorPt = Utils.color2ColorPt(color);
/* 463 */       this.mAnnot.setColor(colorPt, 3);
/*     */       
/* 465 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/* 466 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 467 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 469 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 470 */       SharedPreferences.Editor editor = settings.edit();
/* 471 */       editor.putInt(getColorKey(AnnotUtils.getAnnotType(this.mAnnot)), color);
/* 472 */       editor.apply();
/* 473 */     } catch (Exception e) {
/* 474 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 476 */       if (shouldUnlock) {
/* 477 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void editOpacity(float opacity) {
/* 484 */     boolean shouldUnlock = false;
/*     */ 
/*     */     
/*     */     try {
/* 488 */       this.mPdfViewCtrl.docLock(true);
/* 489 */       shouldUnlock = true;
/* 490 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 491 */       ((Markup)this.mAnnot).setOpacity(opacity);
/* 492 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/* 493 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 494 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 496 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 497 */       SharedPreferences.Editor editor = settings.edit();
/* 498 */       editor.putFloat(getOpacityKey(AnnotUtils.getAnnotType(this.mAnnot)), opacity);
/* 499 */       editor.apply();
/* 500 */     } catch (Exception e) {
/* 501 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 503 */       if (shouldUnlock) {
/* 504 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void editIcon(String icon) {
/* 511 */     boolean shouldUnlock = false;
/*     */ 
/*     */     
/*     */     try {
/* 515 */       this.mPdfViewCtrl.docLock(true);
/* 516 */       shouldUnlock = true;
/* 517 */       raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/* 518 */       Text text = new Text(this.mAnnot);
/* 519 */       text.setIcon(icon);
/* 520 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), this.mAnnot);
/* 521 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 522 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*     */       
/* 524 */       SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 525 */       SharedPreferences.Editor editor = settings.edit();
/* 526 */       editor.putString(getIconKey(AnnotUtils.getAnnotType(this.mAnnot)), icon);
/* 527 */       editor.apply();
/* 528 */     } catch (Exception e) {
/* 529 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 531 */       if (shouldUnlock) {
/* 532 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setNextToolMode() {
/* 538 */     if (this.mAnnot != null && (((ToolManager)this.mPdfViewCtrl.getToolManager()).isAutoSelectAnnotation() || !this.mForceSameNextToolMode)) {
/* 539 */       this.mNextToolMode = ToolManager.ToolMode.ANNOT_EDIT;
/* 540 */       setCurrentDefaultToolModeHelper(getToolMode());
/* 541 */     } else if (this.mForceSameNextToolMode) {
/* 542 */       this.mNextToolMode = ToolManager.ToolMode.TEXT_ANNOT_CREATE;
/*     */     } else {
/* 544 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 545 */       ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 546 */       ToolManager.Tool tool = toolManager.createTool(this.mNextToolMode, null);
/* 547 */       toolManager.setTool(tool);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotThickness(float thickness, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotTextSize(float textSize, boolean done) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotTextColor(int textColor) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotOpacity(float opacity, boolean done) {
/* 568 */     if (done) {
/* 569 */       editOpacity(opacity);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onChangeAnnotStrokeColor(int color) {
/* 575 */     editColor(color);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotFillColor(int color) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void onChangeAnnotIcon(String icon) {
/* 585 */     editIcon(icon);
/*     */   }
/*     */   
/*     */   public void onChangeAnnotFont(FontResource font) {}
/*     */   
/*     */   public void onChangeRulerProperty(RulerItem rulerItem) {}
/*     */   
/*     */   public void onChangeOverlayText(String overlayText) {}
/*     */   
/*     */   public void onChangeSnapping(boolean snap) {}
/*     */   
/*     */   public void onChangeRichContentEnabled(boolean enabled) {}
/*     */   
/*     */   public void onChangeDateFormat(String dateFormat) {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\StickyNoteCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */