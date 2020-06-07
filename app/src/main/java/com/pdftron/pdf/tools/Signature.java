/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.SharedPreferences;
/*     */ import android.graphics.PointF;
/*     */ import android.graphics.RectF;
/*     */ import android.util.Log;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.annotation.StringRes;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.common.Matrix2D;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.DigitalSignatureField;
/*     */ import com.pdftron.pdf.Element;
/*     */ import com.pdftron.pdf.ElementReader;
/*     */ import com.pdftron.pdf.Image;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFDraw;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.PageSet;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.Stamper;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.annots.SignatureWidget;
/*     */ import com.pdftron.pdf.annots.Widget;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.controls.AnnotStyleDialogFragment;
/*     */ import com.pdftron.pdf.dialog.signature.SignatureDialogFragment;
/*     */ import com.pdftron.pdf.dialog.signature.SignatureDialogFragmentBuilder;
/*     */ import com.pdftron.pdf.interfaces.OnCreateSignatureListener;
/*     */ import com.pdftron.pdf.interfaces.OnDialogDismissListener;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.StampManager;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.io.File;
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
/*     */ @Keep
/*     */ public class Signature
/*     */   extends Tool
/*     */ {
/*  57 */   public static String SIGNATURE_ANNOTATION_ID = "pdftronSignatureStamp";
/*     */   
/*  59 */   protected static String SIGNATURE_TEMP_FILE = "SignatureTempFile.jpg";
/*     */   
/*  61 */   protected PointF mTargetPoint = null;
/*     */   
/*     */   protected int mTargetPageNum;
/*     */   
/*     */   protected Widget mWidget;
/*     */   
/*     */   protected boolean mMenuBeingShown;
/*     */   
/*     */   protected int mColor;
/*     */   
/*     */   protected float mStrokeThickness;
/*     */   @StringRes
/*     */   protected int mConfirmBtnStrRes;
/*  74 */   protected int mQuickMenuAnalyticType = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Signature(@NonNull PDFViewCtrl ctrl) {
/*  80 */     super(ctrl);
/*  81 */     this.mNextToolMode = getToolMode();
/*     */ 
/*     */     
/*  84 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  85 */     this.mColor = settings.getInt(getColorKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultColor(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*  86 */     this.mStrokeThickness = settings.getFloat(getThicknessKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultThickness(this.mPdfViewCtrl.getContext(), getCreateAnnotType()));
/*     */     
/*  88 */     this.mConfirmBtnStrRes = R.string.add;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  96 */     return ToolManager.ToolMode.SIGNATURE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/* 101 */     return 1002;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 109 */     if (super.onQuickMenuClicked(menuItem)) {
/* 110 */       return true;
/*     */     }
/*     */     
/* 113 */     this.mMenuBeingShown = false;
/* 114 */     safeSetNextToolMode();
/*     */     
/* 116 */     if (menuItem.getItemId() == R.id.qm_use_saved_sig) {
/* 117 */       Page page = StampManager.getInstance().getDefaultSignature(this.mPdfViewCtrl.getContext());
/* 118 */       if (this.mWidget != null) {
/* 119 */         addSignatureStampToWidget(page);
/* 120 */         unsetAnnot();
/*     */       } else {
/* 122 */         addSignatureStamp(page);
/*     */       } 
/* 124 */       this.mTargetPoint = null;
/* 125 */     } else if (menuItem.getItemId() == R.id.qm_new_signature || menuItem
/* 126 */       .getItemId() == R.id.qm_edit) {
/* 127 */       showSignaturePickerDialog();
/* 128 */     } else if (menuItem.getItemId() == R.id.qm_delete) {
/* 129 */       boolean shouldUnlock = false;
/*     */       try {
/* 131 */         this.mPdfViewCtrl.docLock(true);
/* 132 */         shouldUnlock = true;
/* 133 */         raiseAnnotationPreRemoveEvent(this.mAnnot, this.mAnnotPageNum);
/* 134 */         this.mWidget.getSDFObj().erase("AP");
/* 135 */         this.mWidget.refreshAppearance();
/*     */         
/* 137 */         this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 138 */         raiseAnnotationRemovedEvent(this.mAnnot, this.mAnnotPageNum);
/* 139 */       } catch (Exception e) {
/* 140 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } finally {
/* 142 */         if (shouldUnlock) {
/* 143 */           this.mPdfViewCtrl.docUnlock();
/*     */         }
/*     */       } 
/* 146 */       unsetAnnot();
/*     */     } 
/* 148 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onSingleTapConfirmed(MotionEvent e) {
/* 156 */     if (this.mAnnot != null && 
/* 157 */       this.mJustSwitchedFromAnotherTool) {
/* 158 */       this.mJustSwitchedFromAnotherTool = false;
/*     */ 
/*     */       
/* 161 */       this.mWidget = null;
/* 162 */       setWidget(this.mAnnot);
/*     */       
/* 164 */       if (this.mWidget != null) {
/*     */         
/*     */         try {
/* 167 */           SignatureWidget signatureWidget = new SignatureWidget(this.mAnnot);
/* 168 */           DigitalSignatureField digitalSignatureField = signatureWidget.getDigitalSignatureField();
/* 169 */           if (digitalSignatureField.hasVisibleAppearance()) {
/* 170 */             int x = (int)(e.getX() + 0.5D);
/* 171 */             int y = (int)(e.getY() + 0.5D);
/* 172 */             handleExistingSignatureWidget(x, y);
/* 173 */             return true;
/*     */           } 
/* 175 */           showSignaturePickerDialog();
/* 176 */           return true;
/* 177 */         } catch (PDFNetException e1) {
/* 178 */           e1.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 184 */     return false;
/*     */   }
/*     */   
/*     */   protected void handleExistingSignatureWidget(int x, int y) {
/* 188 */     QuickMenu quickMenu = new QuickMenu(this.mPdfViewCtrl);
/* 189 */     quickMenu.initMenuEntries(R.menu.annot_widget_signature);
/* 190 */     this.mQuickMenuAnalyticType = 3;
/*     */     
/* 192 */     RectF anchor = new RectF((x - 5), y, (x + 5), (y + 1));
/* 193 */     showMenu(anchor, quickMenu);
/* 194 */     this.mMenuBeingShown = true;
/*     */   }
/*     */   
/*     */   private void setWidget(Annot annot) {
/* 198 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 200 */       this.mPdfViewCtrl.docLockRead();
/* 201 */       shouldUnlockRead = true;
/* 202 */       if (annot.getType() == 19) {
/* 203 */         this.mWidget = new Widget(annot);
/*     */       }
/* 205 */     } catch (Exception ex) {
/* 206 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*     */     } finally {
/* 208 */       if (shouldUnlockRead) {
/* 209 */         this.mPdfViewCtrl.docUnlockRead();
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
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/* 221 */     if (this.mMenuBeingShown) {
/* 222 */       safeSetNextToolMode();
/* 223 */       this.mTargetPoint = null;
/* 224 */       this.mMenuBeingShown = false;
/* 225 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 229 */     if (this.mTargetPoint != null) {
/* 230 */       return false;
/*     */     }
/*     */     
/* 233 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */ 
/*     */     
/* 236 */     if (toolManager.isQuickMenuJustClosed()) {
/* 237 */       return true;
/*     */     }
/*     */     
/* 240 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PINCH || priorEventMode == PDFViewCtrl.PriorEventMode.SCROLLING || priorEventMode == PDFViewCtrl.PriorEventMode.FLING)
/*     */     {
/*     */       
/* 243 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 247 */     boolean shouldCreate = true;
/* 248 */     int x = (int)e.getX();
/* 249 */     int y = (int)e.getY();
/* 250 */     ArrayList<Annot> annots = this.mPdfViewCtrl.getAnnotationListAt(x, y, x, y);
/* 251 */     int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/*     */     
/* 253 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/*     */     try {
/* 256 */       for (Annot annot : annots) {
/* 257 */         if (annot.isValid() && annot.getType() == 12) {
/* 258 */           shouldCreate = false;
/*     */           
/* 260 */           toolManager.selectAnnot(annot, page);
/*     */           break;
/*     */         } 
/*     */       } 
/* 264 */     } catch (PDFNetException ex) {
/* 265 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*     */     } 
/*     */     
/* 268 */     if (shouldCreate && page > 0) {
/* 269 */       createSignature(e.getX(), e.getY());
/* 270 */       return true;
/*     */     } 
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean addSignatureStampToWidget(Page page) {
/* 283 */     if (this.mAnnot == null) {
/* 284 */       return false;
/*     */     }
/* 286 */     PDFDraw pdfDraw = null;
/*     */     
/*     */     try {
/* 289 */       String sigTempFilePath = this.mPdfViewCtrl.getContext().getFilesDir().getAbsolutePath() + "/" + SIGNATURE_TEMP_FILE;
/*     */       
/* 291 */       Rect cropBox = page.getCropBox();
/* 292 */       int width = (int)cropBox.getWidth();
/* 293 */       int height = (int)cropBox.getHeight();
/*     */       
/* 295 */       pdfDraw = new PDFDraw();
/* 296 */       pdfDraw.setPageTransparent(true);
/* 297 */       pdfDraw.setImageSize(width, height, true);
/* 298 */       pdfDraw.export(page, sigTempFilePath, "jpeg");
/*     */ 
/*     */       
/* 301 */       SignatureWidget signatureWidget = new SignatureWidget(this.mAnnot);
/* 302 */       Image img = Image.create((Doc)this.mPdfViewCtrl.getDoc(), sigTempFilePath);
/* 303 */       signatureWidget.createSignatureAppearance(img);
/*     */       
/* 305 */       File sigTempFile = new File(sigTempFilePath);
/* 306 */       if (sigTempFile.exists())
/*     */       {
/* 308 */         sigTempFile.delete();
/*     */       }
/*     */       
/* 311 */       this.mPdfViewCtrl.update(this.mAnnot, this.mAnnotPageNum);
/* 312 */       raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/* 313 */       return true;
/* 314 */     } catch (PDFNetException e) {
/* 315 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 316 */       return false;
/*     */     } finally {
/* 318 */       if (pdfDraw != null) {
/*     */         try {
/* 320 */           pdfDraw.destroy();
/* 321 */         } catch (PDFNetException pDFNetException) {}
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void showSignaturePickerDialog() {
/* 328 */     this.mNextToolMode = getToolMode();
/*     */     
/* 330 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 331 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 332 */     if (activity == null) {
/* 333 */       Log.e(Signature.class.getName(), "ToolManager is not attached to with an Activity");
/*     */       
/*     */       return;
/*     */     } 
/* 337 */     Long targetWidget = (this.mWidget != null) ? Long.valueOf(this.mWidget.__GetHandle()) : null;
/*     */     
/* 339 */     SignatureDialogFragment fragment = createSignatureDialogFragment(targetWidget, toolManager);
/* 340 */     fragment.setStyle(0, R.style.CustomAppTheme);
/* 341 */     fragment.setOnCreateSignatureListener(new OnCreateSignatureListener()
/*     */         {
/*     */           public void onSignatureCreated(@Nullable String filepath) {
/* 344 */             Signature.this.create(filepath, (Annot)Signature.this.mWidget);
/*     */           }
/*     */ 
/*     */           
/*     */           public void onSignatureFromImage(@Nullable PointF targetPoint, int targetPage, @Nullable Long widget) {
/* 349 */             if (widget == null && targetPoint == null) {
/* 350 */               AnalyticsHandlerAdapter.getInstance().sendException(new Exception("both target point and widget are not specified for signature."));
/*     */               
/*     */               return;
/*     */             } 
/*     */             
/* 355 */             ((ToolManager)Signature.this.mPdfViewCtrl.getToolManager()).onImageSignatureSelected(targetPoint, targetPage, widget);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onAnnotStyleDialogFragmentDismissed(AnnotStyleDialogFragment styleDialog) {
/* 363 */             ToolStyleConfig.getInstance().saveAnnotStyle(Signature.this.mPdfViewCtrl.getContext(), styleDialog.getAnnotStyle(), "");
/* 364 */             int color = styleDialog.getAnnotStyle().getColor();
/* 365 */             float thickness = styleDialog.getAnnotStyle().getThickness();
/* 366 */             Signature.this.editColor(color);
/* 367 */             Signature.this.editThickness(thickness);
/*     */           }
/*     */         });
/*     */     
/* 371 */     fragment.setOnDialogDismissListener(new OnDialogDismissListener()
/*     */         {
/*     */           public void onDialogDismiss() {
/* 374 */             Signature.this.mTargetPoint = null;
/* 375 */             Signature.this.safeSetNextToolMode();
/*     */           }
/*     */         });
/* 378 */     fragment.show(activity.getSupportFragmentManager(), SignatureDialogFragment.TAG);
/*     */   }
/*     */   
/*     */   private void editColor(int color) {
/* 382 */     this.mColor = color;
/*     */     
/* 384 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 385 */     SharedPreferences.Editor editor = settings.edit();
/* 386 */     editor.putInt(getColorKey(getCreateAnnotType()), color);
/* 387 */     editor.apply();
/*     */   }
/*     */   
/*     */   private void editThickness(float thickness) {
/* 391 */     this.mStrokeThickness = thickness;
/*     */     
/* 393 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/* 394 */     SharedPreferences.Editor editor = settings.edit();
/* 395 */     editor.putFloat(getThicknessKey(getCreateAnnotType()), thickness);
/* 396 */     editor.apply();
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
/*     */   protected SignatureDialogFragment createSignatureDialogFragment(Long targetWidget, ToolManager toolManager) {
/* 410 */     return (new SignatureDialogFragmentBuilder()).usingTargetPoint(this.mTargetPoint).usingTargetPage(this.mTargetPageNum).usingTargetWidget(targetWidget).usingColor(this.mColor).usingStrokeWidth(this.mStrokeThickness).usingShowSavedSignatures(toolManager.isShowSavedSignature()).usingShowSignatureFromImage(toolManager.isShowSignatureFromImage()).usingConfirmBtnStrRes(this.mConfirmBtnStrRes).usingPressureSensitive(toolManager.isUsingPressureSensitiveSignatures()).build(this.mPdfViewCtrl.getContext());
/*     */   }
/*     */   
/*     */   protected void addSignatureStamp(Page stampPage) {
/* 414 */     boolean shouldUnlock = false;
/*     */     try {
/* 416 */       this.mPdfViewCtrl.docLock(true);
/* 417 */       shouldUnlock = true;
/*     */       
/* 419 */       PDFDoc doc = this.mPdfViewCtrl.getDoc();
/* 420 */       Rect stampRect = stampPage.getCropBox();
/* 421 */       Page page = doc.getPage(this.mTargetPageNum);
/* 422 */       Rect pageCropBox = page.getCropBox();
/* 423 */       Rect pageViewBox = page.getBox(this.mPdfViewCtrl.getPageBox());
/*     */       
/* 425 */       int viewRotation = this.mPdfViewCtrl.getPageRotation();
/* 426 */       int pageRotation = page.getRotation();
/*     */ 
/*     */       
/* 429 */       double pageWidth = pageViewBox.getWidth();
/* 430 */       if (pageRotation == 1 || pageRotation == 3) {
/* 431 */         pageWidth = pageViewBox.getHeight();
/*     */       }
/* 433 */       double pageHeight = pageViewBox.getHeight();
/* 434 */       if (pageRotation == 1 || pageRotation == 3) {
/* 435 */         pageHeight = pageViewBox.getWidth();
/*     */       }
/*     */       
/* 438 */       double maxWidth = 200.0D;
/* 439 */       double maxHeight = 200.0D;
/*     */       
/* 441 */       if (pageWidth < maxWidth) {
/* 442 */         maxWidth = pageWidth;
/*     */       }
/* 444 */       if (pageHeight < maxHeight) {
/* 445 */         maxHeight = pageHeight;
/*     */       }
/* 447 */       double stampWidth = stampRect.getWidth();
/* 448 */       double stampHeight = stampRect.getHeight();
/*     */ 
/*     */       
/* 451 */       if (viewRotation == 1 || viewRotation == 3) {
/* 452 */         double temp = stampWidth;
/*     */         
/* 454 */         stampWidth = stampHeight;
/* 455 */         stampHeight = temp;
/*     */       } 
/*     */       
/* 458 */       double scaleFactor = Math.min(maxWidth / stampWidth, maxHeight / stampHeight);
/* 459 */       stampWidth *= scaleFactor;
/* 460 */       stampHeight *= scaleFactor;
/*     */       
/* 462 */       Stamper stamper = new Stamper(2, stampWidth, stampHeight);
/* 463 */       stamper.setAlignment(-1, -1);
/* 464 */       stamper.setAsAnnotation(true);
/*     */       
/* 466 */       Matrix2D mtx = page.getDefaultMatrix();
/* 467 */       Point pt = mtx.multPoint(this.mTargetPoint.x, this.mTargetPoint.y);
/*     */       
/* 469 */       double xPos = pt.x - stampWidth / 2.0D;
/* 470 */       double yPos = pt.y - stampHeight / 2.0D;
/*     */ 
/*     */ 
/*     */       
/* 474 */       double leftEdge = pageViewBox.getX1() - pageCropBox.getX1();
/* 475 */       double bottomEdge = pageViewBox.getY1() - pageCropBox.getY1();
/*     */       
/* 477 */       if (xPos > leftEdge + pageWidth - stampWidth) {
/* 478 */         xPos = leftEdge + pageWidth - stampWidth;
/*     */       }
/* 480 */       if (xPos < leftEdge) {
/* 481 */         xPos = leftEdge;
/*     */       }
/*     */       
/* 484 */       if (yPos > bottomEdge + pageHeight - stampHeight) {
/* 485 */         yPos = bottomEdge + pageHeight - stampHeight;
/*     */       }
/* 487 */       if (yPos < bottomEdge) {
/* 488 */         yPos = bottomEdge;
/*     */       }
/*     */       
/* 491 */       stamper.setPosition(xPos, yPos);
/*     */       
/* 493 */       int stampRotation = (4 - viewRotation) % 4;
/* 494 */       stamper.setRotation(stampRotation * 90.0D);
/* 495 */       stamper.stampPage(doc, stampPage, new PageSet(this.mTargetPageNum));
/*     */       
/* 497 */       int numAnnots = page.getNumAnnots();
/* 498 */       Annot annot = page.getAnnot(numAnnots - 1);
/* 499 */       Obj obj = annot.getSDFObj();
/* 500 */       obj.putString(SIGNATURE_ANNOTATION_ID, "");
/*     */       
/* 502 */       if (annot.isMarkup()) {
/* 503 */         Markup markup = new Markup(annot);
/* 504 */         setAuthor(markup);
/*     */       } 
/*     */       
/* 507 */       setAnnot(annot, this.mTargetPageNum);
/* 508 */       buildAnnotBBox();
/*     */       
/* 510 */       this.mPdfViewCtrl.update(annot, this.mTargetPageNum);
/* 511 */       raiseAnnotationAddedEvent(annot, this.mTargetPageNum);
/* 512 */     } catch (Exception e) {
/* 513 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 515 */       if (shouldUnlock) {
/* 516 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Element getFirstElementUsingReader(ElementReader reader, Obj obj, int type) {
/* 523 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 525 */       this.mPdfViewCtrl.docLockRead();
/* 526 */       shouldUnlockRead = true;
/* 527 */       if (obj != null) {
/* 528 */         reader.begin(obj);
/*     */         try {
/*     */           Element element;
/* 531 */           while ((element = reader.next()) != null) {
/* 532 */             if (element.getType() == type) {
/* 533 */               return element;
/*     */             }
/*     */           } 
/*     */         } finally {
/* 537 */           reader.end();
/*     */         } 
/*     */       } 
/* 540 */     } catch (Exception e) {
/* 541 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 543 */       if (shouldUnlockRead) {
/* 544 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */     
/* 548 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTargetPoint(PointF point) {
/* 557 */     createSignature(point.x, point.y);
/*     */     
/* 559 */     safeSetNextToolMode();
/*     */   }
/*     */   
/*     */   public void setTargetPoint(PointF pagePoint, int page) {
/* 563 */     this.mTargetPoint = pagePoint;
/* 564 */     this.mTargetPageNum = page;
/*     */     
/* 566 */     safeSetNextToolMode();
/*     */   }
/*     */   
/*     */   public void create(String filepath, Annot widget) {
/* 570 */     if (filepath != null) {
/* 571 */       Page page = StampManager.getInstance().getSignature(filepath);
/* 572 */       if (page != null) {
/* 573 */         if (widget != null) {
/* 574 */           if (this.mWidget == null) {
/* 575 */             this.mAnnot = widget;
/* 576 */             setWidget(this.mAnnot);
/*     */           } 
/* 578 */           addSignatureStampToWidget(page);
/* 579 */           unsetAnnot();
/*     */         } else {
/* 581 */           addSignatureStamp(page);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createSignature(float ptx, float pty) {
/* 588 */     setCurrentDefaultToolModeHelper(getToolMode());
/*     */     
/* 590 */     this.mTargetPageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(ptx, pty);
/* 591 */     double[] pts = this.mPdfViewCtrl.convScreenPtToPagePt(ptx, pty, this.mTargetPageNum);
/* 592 */     this.mTargetPoint = new PointF();
/* 593 */     this.mTargetPoint.x = (float)pts[0];
/* 594 */     this.mTargetPoint.y = (float)pts[1];
/*     */     
/* 596 */     showSignaturePickerDialog();
/*     */   }
/*     */   
/*     */   private void safeSetNextToolMode() {
/* 600 */     if (this.mForceSameNextToolMode) {
/* 601 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*     */     } else {
/* 603 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getQuickMenuAnalyticType() {
/* 609 */     return this.mQuickMenuAnalyticType;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\Signature.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */