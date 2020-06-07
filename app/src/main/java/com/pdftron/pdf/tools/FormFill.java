/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.graphics.Color;
/*      */ import android.text.Editable;
/*      */ import android.text.InputFilter;
/*      */ import android.text.TextWatcher;
/*      */ import android.text.method.PasswordTransformationMethod;
/*      */ import android.text.method.TransformationMethod;
/*      */ import android.view.KeyEvent;
/*      */ import android.view.MotionEvent;
/*      */ import android.view.View;
/*      */ import android.view.inputmethod.InputMethodManager;
/*      */ import android.widget.DatePicker;
/*      */ import android.widget.EditText;
/*      */ import android.widget.TextView;
/*      */ import android.widget.TimePicker;
/*      */ import androidx.annotation.Keep;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.fragment.app.Fragment;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Action;
/*      */ import com.pdftron.pdf.ActionParameter;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.ColorPt;
/*      */ import com.pdftron.pdf.ColorSpace;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.Font;
/*      */ import com.pdftron.pdf.GState;
/*      */ import com.pdftron.pdf.KeyStrokeActionResult;
/*      */ import com.pdftron.pdf.KeyStrokeEventData;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Page;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.ViewChangeCollection;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.dialog.SimpleDateTimePickerFragment;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.ShortcutHelper;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import com.pdftron.pdf.widget.AutoScrollEditText;
/*      */ import com.pdftron.pdf.widget.AutoScrollEditor;
/*      */ import com.pdftron.sdf.Obj;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Locale;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Keep
/*      */ public class FormFill
/*      */   extends Tool
/*      */ {
/*      */   private Field mField;
/*      */   private AutoScrollEditor mEditor;
/*      */   private boolean mIsMultiLine;
/*      */   private double mBorderWidth;
/*      */   private boolean mHasClosed = false;
/*      */   private boolean mCanUseDateTimePicker = true;
/*      */   private static final String PICKER_TAG = "simple_date_picker";
/*      */   
/*      */   public FormFill(@NonNull PDFViewCtrl ctrl) {
/*   84 */     super(ctrl);
/*   85 */     this.mEditor = null;
/*   86 */     this.mBorderWidth = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ToolManager.ToolModeBase getToolMode() {
/*   94 */     return ToolManager.ToolMode.FORM_FILL;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCreateAnnotType() {
/*   99 */     return 28;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean onFlingStop() {
/*  104 */     if (this.mEditor != null) {
/*  105 */       this.mEditor.onCanvasSizeChanged();
/*      */     }
/*  107 */     return super.onFlingStop();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void executeAction(Field fld, int type) {
/*  119 */     if (this.mAnnot != null) {
/*      */       try {
/*  121 */         Obj aa = this.mAnnot.getTriggerAction(type);
/*  122 */         if (aa != null) {
/*      */           
/*  124 */           Action a = new Action(aa);
/*      */           
/*  126 */           ActionParameter action_param = new ActionParameter(a, fld);
/*  127 */           executeAction(action_param);
/*      */         } 
/*  129 */       } catch (PDFNetException e) {
/*  130 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void executeAction(Annot annot, int type) {
/*  144 */     if (annot != null) {
/*      */       try {
/*  146 */         Obj aa = annot.getTriggerAction(type);
/*  147 */         if (aa != null) {
/*      */           
/*  149 */           Action a = new Action(aa);
/*      */           
/*  151 */           ActionParameter action_param = new ActionParameter(a, annot);
/*  152 */           executeAction(action_param);
/*      */           
/*  154 */           if (action_param.getAction().getType() == 18) {
/*  155 */             String mediaCmd = getMediaCmd(annot);
/*  156 */             if (!Utils.isNullOrEmpty(mediaCmd))
/*      */             {
/*  158 */               if (mediaCmd.contains("rewind") || mediaCmd.contains("play")) {
/*      */                 
/*  160 */                 Obj cmdTA = getLinkedMedia(annot);
/*  161 */                 if (cmdTA != null) {
/*  162 */                   Annot linkedAnnot = new Annot(cmdTA);
/*  163 */                   if (linkedAnnot.isValid()) {
/*  164 */                     this.mNextToolMode = ToolManager.ToolMode.RICH_MEDIA;
/*  165 */                     RichMedia tool = (RichMedia)((ToolManager)this.mPdfViewCtrl.getToolManager()).createTool(ToolManager.ToolMode.RICH_MEDIA, this);
/*  166 */                     ((ToolManager)this.mPdfViewCtrl.getToolManager()).setTool(tool);
/*  167 */                     tool.handleRichMediaAnnot(linkedAnnot, linkedAnnot.getPage().getIndex());
/*      */                   } 
/*      */                 } 
/*      */               } 
/*      */             }
/*      */           } 
/*      */         } 
/*  174 */       } catch (PDFNetException e) {
/*  175 */         e.printStackTrace();
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private static String getMediaCmd(Annot annot) throws PDFNetException {
/*  181 */     Obj aso = annot.getSDFObj();
/*  182 */     if (null != aso && aso
/*  183 */       .findObj("A") != null && aso
/*  184 */       .findObj("A").findObj("CMD") != null && aso
/*  185 */       .findObj("A").findObj("CMD").findObj("C") != null) {
/*  186 */       Obj cmdC = aso.findObj("A").findObj("CMD").findObj("C");
/*  187 */       if (cmdC.isString()) {
/*  188 */         return cmdC.getAsPDFText();
/*      */       }
/*      */     } 
/*  191 */     return null;
/*      */   }
/*      */   
/*      */   private static Obj getLinkedMedia(Annot annot) throws PDFNetException {
/*  195 */     Obj aso = annot.getSDFObj();
/*  196 */     if (null != aso && aso
/*  197 */       .findObj("A") != null && aso
/*  198 */       .findObj("A").findObj("TA") != null && aso
/*  199 */       .findObj("A").findObj("TA").findObj("Type") != null) {
/*  200 */       Obj cmdTA = aso.findObj("A").findObj("TA");
/*  201 */       Obj cmdTAType = aso.findObj("A").findObj("TA").findObj("Type");
/*  202 */       if (cmdTAType.isName()) {
/*  203 */         String name = cmdTAType.getName();
/*  204 */         if (name.equals("Annot")) {
/*  205 */           return cmdTA;
/*      */         }
/*      */       } 
/*  208 */       return cmdTA;
/*      */     } 
/*  210 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onClose() {
/*  218 */     super.onClose();
/*  219 */     applyFormFieldEditBoxAndQuit(true);
/*  220 */     closeAllDialogs();
/*      */   }
/*      */   
/*      */   private SimpleDateTimePickerFragment getDialog() {
/*  224 */     FragmentActivity fragmentActivity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/*  225 */     if (fragmentActivity != null) {
/*  226 */       Fragment fragment = fragmentActivity.getSupportFragmentManager().findFragmentByTag("simple_date_picker");
/*  227 */       if (fragment != null && fragment instanceof SimpleDateTimePickerFragment) {
/*  228 */         return (SimpleDateTimePickerFragment)fragment;
/*      */       }
/*      */     } 
/*  231 */     return null;
/*      */   }
/*      */   
/*      */   private void closeAllDialogs() {
/*  235 */     if (getDialog() != null) {
/*  236 */       getDialog().dismiss();
/*      */     }
/*      */   }
/*      */   
/*      */   private boolean isDialogShowing() {
/*  241 */     return (getDialog() != null && getDialog().isAdded());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onSingleTapConfirmed(MotionEvent e) {
/*  249 */     return handleForm(e, (Annot)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPostSingleTapConfirmed() {
/*  257 */     if (this.mEditor == null)
/*      */     {
/*      */ 
/*      */       
/*  261 */       safeSetNextToolMode();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPageTurning(int old_page, int cur_page) {
/*  270 */     safeSetNextToolMode();
/*  271 */     if (this.mAnnot != null && this.mEditor != null)
/*      */     {
/*      */       
/*  274 */       applyFormFieldEditBoxAndQuit(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLayout(boolean changed, int l, int t, int r, int b) {
/*  283 */     if (this.mAnnot != null && this.mPdfViewCtrl != null && 
/*  284 */       !this.mPdfViewCtrl.isContinuousPagePresentationMode(this.mPdfViewCtrl.getPagePresentationMode()) && 
/*  285 */       this.mAnnotPageNum != this.mPdfViewCtrl.getCurrentPage()) {
/*      */ 
/*      */       
/*  288 */       if (this.mEditor != null) {
/*  289 */         applyFormFieldEditBoxAndQuit(true);
/*      */       }
/*  291 */       unsetAnnot();
/*  292 */       safeSetNextToolMode();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onLongPress(MotionEvent e) {
/*  303 */     return handleForm(e, (Annot)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean tabToNextField(int pageNum) {
/*  314 */     if (this.mAnnot == null || this.mPdfViewCtrl == null) {
/*  315 */       return false;
/*      */     }
/*      */     
/*      */     try {
/*  319 */       Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/*  320 */       int annotCount = page.getNumAnnots();
/*  321 */       boolean currentAnnotFound = false;
/*  322 */       for (int i = 0; i < annotCount; i++) {
/*  323 */         Annot annot = page.getAnnot(i);
/*  324 */         Rect annotRect = annot.getRect();
/*  325 */         Rect toolAnnotRect = this.mAnnot.getRect();
/*  326 */         if (annotRect.getX1() == toolAnnotRect.getX1() && annotRect.getY1() == toolAnnotRect.getY1())
/*  327 */         { currentAnnotFound = true;
/*      */            }
/*      */         
/*  330 */         else if (currentAnnotFound)
/*      */         
/*  332 */         { if (annot.getType() == 19)
/*      */           
/*  334 */           { Widget annotWidget = new Widget(annot);
/*  335 */             Field annotWidgetField = annotWidget.getField();
/*  336 */             if (annotWidgetField != null && annotWidgetField.isValid() && !annotWidgetField.getFlag(0) && annotWidgetField.getType() == 3)
/*      */             
/*  338 */             { this.mHasClosed = false;
/*  339 */               applyFormFieldEditBoxAndQuit(false);
/*  340 */               this.mHasClosed = false;
/*  341 */               handleForm((MotionEvent)null, annot);
/*  342 */               setAnnot(annot, pageNum);
/*  343 */               buildAnnotBBox();
/*  344 */               handleForm((MotionEvent)null, annot);
/*  345 */               return true; }  }  } 
/*      */       } 
/*  347 */     } catch (PDFNetException e) {
/*  348 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*  350 */     applyFormFieldEditBoxAndQuit(true);
/*  351 */     return false;
/*      */   }
/*      */   
/*      */   private boolean handleForm(MotionEvent e, Annot annot) {
/*  355 */     boolean handled = false;
/*  356 */     int x = 0;
/*  357 */     int y = 0;
/*  358 */     if (annot == null) {
/*  359 */       x = (int)(e.getX() + 0.5D);
/*  360 */       y = (int)(e.getY() + 0.5D);
/*      */     } 
/*      */     
/*  363 */     if (this.mAnnot != null && this.mPdfViewCtrl != null) {
/*  364 */       this.mNextToolMode = ToolManager.ToolMode.FORM_FILL;
/*      */       
/*  366 */       Annot tempAnnot = null;
/*  367 */       boolean isAnnotValid = false;
/*  368 */       int annotType = -1;
/*  369 */       boolean shouldUnlockRead = false;
/*      */       try {
/*  371 */         this.mPdfViewCtrl.docLockRead();
/*  372 */         shouldUnlockRead = true;
/*  373 */         if (annot == null) {
/*  374 */           tempAnnot = this.mPdfViewCtrl.getAnnotationAt(x, y);
/*      */         } else {
/*  376 */           tempAnnot = annot;
/*      */         } 
/*  378 */         isAnnotValid = tempAnnot.isValid();
/*  379 */         if (isAnnotValid) {
/*  380 */           annotType = tempAnnot.getType();
/*      */         }
/*  382 */       } catch (Exception ex) {
/*  383 */         AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*      */       } finally {
/*  385 */         if (shouldUnlockRead) {
/*  386 */           this.mPdfViewCtrl.docUnlockRead();
/*      */         }
/*      */       } 
/*      */       
/*  390 */       if (this.mAnnot.equals(tempAnnot)) {
/*  391 */         handled = handleWidget();
/*      */       } else {
/*      */         
/*  394 */         if (this.mEditor != null) {
/*  395 */           boolean hideKeyboard = true;
/*  396 */           if (isAnnotValid && annotType == 19) {
/*  397 */             hideKeyboard = false;
/*      */           }
/*      */           
/*  400 */           applyFormFieldEditBoxAndQuit(hideKeyboard);
/*      */         } 
/*  402 */         unsetAnnot();
/*  403 */         safeSetNextToolMode();
/*      */       } 
/*      */     } 
/*  406 */     return handled;
/*      */   }
/*      */   
/*      */   private boolean handleWidget() {
/*  410 */     return handleWidget(this.mAnnot, this.mAnnotPageNum);
/*      */   }
/*      */   
/*      */   private boolean handleWidget(Annot annot, int pageNum) {
/*  414 */     if (onInterceptAnnotationHandling(annot)) {
/*  415 */       return true;
/*      */     }
/*  417 */     if (annot == null) {
/*  418 */       return false;
/*      */     }
/*  420 */     boolean shouldUnlock = false;
/*  421 */     boolean handled = false;
/*  422 */     boolean hasModification = false;
/*  423 */     boolean hasOnlyExecutionChanges = false;
/*      */     try {
/*  425 */       this.mPdfViewCtrl.docLock(true);
/*  426 */       shouldUnlock = true;
/*  427 */       raiseAnnotationPreModifyEvent(annot, pageNum);
/*  428 */       executeAction(annot, 1);
/*  429 */       executeAction(annot, 3);
/*  430 */       executeAction(annot, 5);
/*  431 */       Widget w = new Widget(annot);
/*  432 */       this.mField = w.getField();
/*  433 */       if (this.mField != null && this.mField.isValid() && !this.mField.getFlag(0)) {
/*  434 */         int field_type = this.mField.getType();
/*      */         
/*  436 */         if (field_type == 1) {
/*  437 */           ViewChangeCollection view_change = this.mField.setValue(!this.mField.getValueAsBool());
/*  438 */           this.mPdfViewCtrl.refreshAndUpdate(view_change);
/*  439 */           executeAction(this.mField, 6);
/*  440 */           executeAction(this.mField, 2);
/*  441 */           hasModification = true;
/*  442 */         } else if (field_type == 2) {
/*  443 */           if (!this.mField.getValueAsBool()) {
/*  444 */             ViewChangeCollection view_change = this.mField.setValue(true);
/*  445 */             this.mPdfViewCtrl.refreshAndUpdate(view_change);
/*  446 */             executeAction(this.mField, 6);
/*  447 */             executeAction(this.mField, 2);
/*  448 */             hasModification = true;
/*      */           } 
/*  450 */         } else if (field_type == 0) {
/*  451 */           safeSetNextToolMode();
/*  452 */           handled = true;
/*  453 */         } else if (field_type == 4) {
/*  454 */           DialogFormFillChoice d = new DialogFormFillChoice(this.mPdfViewCtrl, annot, pageNum);
/*  455 */           d.show();
/*  456 */         } else if (field_type == 3) {
/*  457 */           this.mIsMultiLine = this.mField.getFlag(7);
/*  458 */           boolean inline_edit = canUseInlineEditing();
/*  459 */           if (!inline_edit) {
/*      */             
/*  461 */             DialogFormFillText d = new DialogFormFillText(this.mPdfViewCtrl, annot, pageNum);
/*  462 */             d.show();
/*      */           } else {
/*      */             
/*  465 */             handleTextInline();
/*      */           } 
/*  467 */         } else if (field_type == 5) {
/*  468 */           if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isUsingDigitalSignature()) {
/*  469 */             this.mNextToolMode = ToolManager.ToolMode.DIGITAL_SIGNATURE;
/*      */           } else {
/*  471 */             this.mNextToolMode = ToolManager.ToolMode.SIGNATURE;
/*      */           } 
/*  473 */           if (((ToolManager)this.mPdfViewCtrl.getToolManager()).isToolModeDisabled((ToolManager.ToolMode)this.mNextToolMode)) {
/*      */             
/*  475 */             safeSetNextToolMode();
/*  476 */             handled = true;
/*      */           } 
/*      */         } 
/*  479 */         executeAction(annot, 0);
/*  480 */         executeAction(annot, 4);
/*      */       } 
/*  482 */       if (hasModification) {
/*  483 */         raiseAnnotationModifiedEvent(annot, pageNum);
/*      */       } else {
/*  485 */         hasOnlyExecutionChanges = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/*      */       } 
/*  487 */     } catch (PDFNetException ex) {
/*  488 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)ex);
/*      */     } finally {
/*  490 */       if (shouldUnlock) {
/*  491 */         this.mPdfViewCtrl.docUnlock();
/*  492 */         if (hasOnlyExecutionChanges) {
/*  493 */           raiseAnnotationActionEvent();
/*      */         }
/*      */       } 
/*      */     } 
/*  497 */     return handled;
/*      */   }
/*      */   
/*      */   private boolean canUseInlineEditing() {
/*  501 */     if (this.mPdfViewCtrl == null) {
/*  502 */       return false;
/*      */     }
/*      */     
/*      */     try {
/*  506 */       if (this.mPdfViewCtrl.getPageRotation() != 0 && this.mPdfViewCtrl.getPageRotation() != 2)
/*      */       {
/*  508 */         return false;
/*      */       }
/*  510 */       float font_sz = 12.0F * (float)this.mPdfViewCtrl.getZoom();
/*  511 */       GState gs = this.mField.getDefaultAppearance();
/*  512 */       if (gs != null) {
/*  513 */         font_sz = (float)gs.getFontSize();
/*  514 */         if (font_sz <= 0.0F) {
/*  515 */           if (this.mIsMultiLine) {
/*  516 */             font_sz = 12.0F * (float)this.mPdfViewCtrl.getZoom();
/*      */           } else {
/*      */             
/*  519 */             double x1 = this.mAnnotBBox.left + this.mBorderWidth;
/*  520 */             double y1 = this.mAnnotBBox.bottom - this.mBorderWidth;
/*  521 */             double x2 = this.mAnnotBBox.right - this.mBorderWidth;
/*  522 */             double y2 = this.mAnnotBBox.top + this.mBorderWidth;
/*  523 */             double[] pts1 = this.mPdfViewCtrl.convPagePtToScreenPt(x1, y1, this.mAnnotPageNum);
/*  524 */             double[] pts2 = this.mPdfViewCtrl.convPagePtToScreenPt(x2, y2, this.mAnnotPageNum);
/*  525 */             double height = Math.abs(pts1[1] - pts2[1]);
/*  526 */             font_sz = (float)(height * 0.800000011920929D);
/*      */           } 
/*      */         } else {
/*  529 */           font_sz *= (float)this.mPdfViewCtrl.getZoom();
/*      */         } 
/*      */       } 
/*  532 */       if (font_sz > 12.0F)
/*      */       {
/*  534 */         return true;
/*      */       }
/*  536 */     } catch (Exception e) {
/*  537 */       return false;
/*      */     } 
/*      */     
/*  540 */     return false;
/*      */   }
/*      */   
/*      */   private void adjustFontSize(EditText editText) {
/*  544 */     if (this.mPdfViewCtrl != null && this.mField != null) {
/*      */       try {
/*  546 */         float font_sz = 12.0F * (float)this.mPdfViewCtrl.getZoom();
/*  547 */         GState gs = this.mField.getDefaultAppearance();
/*  548 */         if (gs != null) {
/*  549 */           font_sz = (float)gs.getFontSize();
/*  550 */           if (font_sz <= 0.0F) {
/*      */             
/*  552 */             font_sz = 12.0F * (float)this.mPdfViewCtrl.getZoom();
/*      */           } else {
/*  554 */             font_sz *= (float)this.mPdfViewCtrl.getZoom();
/*      */           } 
/*      */         } 
/*      */         
/*  558 */         editText.setPadding(0, 0, 0, 0);
/*  559 */         editText.setTextSize(0, font_sz);
/*      */       }
/*  561 */       catch (PDFNetException e) {
/*  562 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private void mapColorFont(EditText editText) {
/*  568 */     if (this.mField != null) {
/*      */       try {
/*  570 */         GState gs = this.mField.getDefaultAppearance();
/*  571 */         if (gs != null)
/*      */         {
/*  573 */           ColorPt color = gs.getFillColor();
/*  574 */           color = gs.getFillColorSpace().convert2RGB(color);
/*  575 */           int r = (int)Math.floor(color.get(0) * 255.0D + 0.5D);
/*  576 */           int g = (int)Math.floor(color.get(1) * 255.0D + 0.5D);
/*  577 */           int b = (int)Math.floor(color.get(2) * 255.0D + 0.5D);
/*  578 */           int color_int = Color.argb(255, r, g, b);
/*  579 */           editText.setTextColor(color_int);
/*      */ 
/*      */           
/*  582 */           color = getFieldBkColor();
/*  583 */           if (color == null) {
/*  584 */             r = 255;
/*  585 */             g = 255;
/*  586 */             b = 255;
/*      */           } else {
/*  588 */             r = (int)Math.floor(color.get(0) * 255.0D + 0.5D);
/*  589 */             g = (int)Math.floor(color.get(1) * 255.0D + 0.5D);
/*  590 */             b = (int)Math.floor(color.get(2) * 255.0D + 0.5D);
/*      */           } 
/*  592 */           color_int = Color.argb(255, r, g, b);
/*  593 */           editText.setBackgroundColor(color_int);
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  598 */           Font font = gs.getFont();
/*  599 */           if (font != null) {
/*  600 */             String family_name = font.getFamilyName();
/*  601 */             if (family_name == null || family_name.length() == 0) {
/*  602 */               family_name = "Times";
/*      */             }
/*  604 */             String name = font.getName();
/*  605 */             if (name == null || name.length() == 0) {
/*  606 */               name = "Times New Roman";
/*      */             }
/*  608 */             if (family_name.contains("Times") || name.contains("Times"));
/*      */           
/*      */           }
/*      */         
/*      */         }
/*      */       
/*      */       }
/*  615 */       catch (PDFNetException e) {
/*  616 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   private ColorPt getFieldBkColor() {
/*  622 */     if (this.mAnnot != null) {
/*      */       try {
/*  624 */         Obj o = this.mAnnot.getSDFObj().findObj("MK");
/*  625 */         if (o != null) {
/*  626 */           Obj bgc = o.findObj("BG");
/*  627 */           if (bgc != null && bgc.isArray()) {
/*  628 */             Obj n, r, g, b, c, m, y, k; int sz = (int)bgc.size();
/*  629 */             switch (sz) {
/*      */               case 1:
/*  631 */                 n = bgc.getAt(0);
/*  632 */                 if (n.isNumber()) {
/*  633 */                   return new ColorPt(n.getNumber(), n.getNumber(), n.getNumber());
/*      */                 }
/*      */                 break;
/*      */               case 3:
/*  637 */                 r = bgc.getAt(0); g = bgc.getAt(1); b = bgc.getAt(2);
/*  638 */                 if (r.isNumber() && g.isNumber() && b.isNumber()) {
/*  639 */                   return new ColorPt(r.getNumber(), g.getNumber(), b.getNumber());
/*      */                 }
/*      */                 break;
/*      */               case 4:
/*  643 */                 c = bgc.getAt(0); m = bgc.getAt(1); y = bgc.getAt(2); k = bgc.getAt(3);
/*  644 */                 if (c.isNumber() && m.isNumber() && y.isNumber() && k.isNumber()) {
/*  645 */                   ColorPt cp = new ColorPt(c.getNumber(), m.getNumber(), y.getNumber(), k.getNumber());
/*  646 */                   ColorSpace cs = ColorSpace.createDeviceCMYK();
/*  647 */                   return cs.convert2RGB(cp);
/*      */                 } 
/*      */                 break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  653 */       } catch (Exception e) {
/*  654 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*  658 */     return null;
/*      */   }
/*      */   
/*      */   private void handleTextInline() {
/*      */     try {
/*  663 */       if (this.mAnnot != null && this.mField != null && this.mField.isValid()) {
/*  664 */         int max_len = this.mField.getMaxLen();
/*      */         
/*  666 */         if (this.mEditor != null) {
/*  667 */           applyFormFieldEditBoxAndQuit(false);
/*      */         }
/*      */         
/*  670 */         this.mEditor = new AutoScrollEditor(this.mPdfViewCtrl.getContext());
/*  671 */         this.mEditor.getEditText().setSingleLine(!this.mIsMultiLine);
/*  672 */         this.mEditor.getEditText().setImeOptions(5);
/*      */         
/*  674 */         this.mEditor.getEditText().addTextChangedListener(new TextWatcher() {
/*  675 */               String originalText = "";
/*  676 */               String newText = "";
/*      */               
/*      */               int cursorPosition;
/*      */               
/*      */               public void beforeTextChanged(CharSequence s, int start, int count, int after) {
/*  681 */                 this.originalText = s.toString();
/*  682 */                 if (FormFill.this.mEditor != null) {
/*  683 */                   this.cursorPosition = FormFill.this.mEditor.getEditText().getSelectionStart();
/*      */                 }
/*      */               }
/*      */ 
/*      */               
/*      */               public void onTextChanged(CharSequence s, int start, int before, int count) {
/*      */                 try {
/*  690 */                   this.newText = s.toString();
/*  691 */                   if (this.newText.equals(this.originalText))
/*  692 */                     return;  Obj aa = FormFill.this.mField.getTriggerAction(13);
/*  693 */                   if (aa != null) {
/*      */                     
/*  695 */                     Action a = new Action(aa);
/*  696 */                     int to = start + before;
/*  697 */                     String fieldName = FormFill.this.mField.getName();
/*  698 */                     String changedText = s.toString();
/*  699 */                     String addedText = changedText.substring(start, start + count);
/*      */                     
/*  701 */                     KeyStrokeEventData data = new KeyStrokeEventData(fieldName, this.originalText, addedText, start, to);
/*      */                     
/*  703 */                     KeyStrokeActionResult actionResult = a.executeKeyStrokeAction(data);
/*  704 */                     if (actionResult.isValid()) {
/*  705 */                       String addedValue = actionResult.getText();
/*  706 */                       if (!addedValue.equals(addedText)) {
/*  707 */                         String textBefore = this.originalText.substring(0, start);
/*  708 */                         String textAfter = this.originalText.substring(to, this.originalText.length());
/*  709 */                         this.newText = textBefore + addedValue + textAfter;
/*  710 */                         FormFill.this.mEditor.getEditText().setTextKeepState(this.newText);
/*      */                       } 
/*      */                     } else {
/*  713 */                       this.newText = this.originalText;
/*  714 */                       FormFill.this.mEditor.getEditText().setTextKeepState(this.newText);
/*  715 */                       if (this.cursorPosition < this.newText.length()) {
/*  716 */                         FormFill.this.mEditor.getEditText().setSelection(this.cursorPosition);
/*      */                       }
/*      */                     } 
/*      */                   } 
/*  720 */                 } catch (Exception e) {
/*  721 */                   AnalyticsHandlerAdapter.getInstance().sendException(e, "originalText:" + this.originalText + ",newText:" + this.newText + ", cursorPosition:" + this.cursorPosition);
/*      */                 } 
/*      */               }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               public void afterTextChanged(Editable s) {}
/*      */             });
/*  732 */         if (this.mAnnot == null) {
/*      */           return;
/*      */         }
/*      */         
/*  736 */         Annot.BorderStyle bs = this.mAnnot.getBorderStyle();
/*  737 */         this.mBorderWidth = bs.getWidth();
/*  738 */         if (bs.getStyle() == 2 || bs.getStyle() == 3) {
/*  739 */           this.mBorderWidth = bs.getWidth() * 2.0D;
/*      */         }
/*      */         
/*  742 */         Obj trigAction = this.mField.getTriggerAction(13);
/*  743 */         boolean isNumberField = false;
/*      */         
/*  745 */         if (trigAction != null && trigAction.isDict()) {
/*  746 */           Obj js = trigAction.findObj("JS");
/*  747 */           if (js != null && js.isString()) {
/*  748 */             String str = js.getAsPDFText();
/*  749 */             if (str.contains("AFNumber") || str.contains("AFPercent")) {
/*  750 */               isNumberField = true;
/*      */             }
/*      */           } 
/*      */         } 
/*  754 */         if (isNumberField) {
/*  755 */           this.mEditor.getEditText().setInputType(8194);
/*      */         }
/*      */         
/*  758 */         String jsStr = null;
/*  759 */         boolean isDate = false;
/*  760 */         boolean isTime = false;
/*      */         
/*  762 */         if (trigAction != null && trigAction.isDict()) {
/*  763 */           Obj js = trigAction.findObj("JS");
/*  764 */           if (js != null && js.isString()) {
/*  765 */             jsStr = js.getAsPDFText();
/*  766 */             if (jsStr.contains("AFDate")) {
/*  767 */               isDate = true;
/*  768 */             } else if (jsStr.contains("AFTime")) {
/*  769 */               isTime = true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  774 */         final String formatString = jsStr;
/*  775 */         if ((isDate || isTime) && this.mCanUseDateTimePicker) {
/*  776 */           FragmentActivity fragmentActivity = ((ToolManager)this.mPdfViewCtrl.getToolManager()).getCurrentActivity();
/*  777 */           if (fragmentActivity != null) {
/*  778 */             int mode = isDate ? 0 : 1;
/*  779 */             SimpleDateTimePickerFragment picker = SimpleDateTimePickerFragment.newInstance(mode);
/*  780 */             picker.setSimpleDatePickerListener(new SimpleDateTimePickerFragment.SimpleDatePickerListener()
/*      */                 {
/*      */                   public void onDateSet(DatePicker view, int year, int month, int day) {
/*      */                     try {
/*  784 */                       String format = Utils.getDateTimeFormatFromField(formatString, true);
/*  785 */                       SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
/*      */                       
/*  787 */                       Calendar cal = Calendar.getInstance();
/*  788 */                       cal.set(view.getYear(), view.getMonth(), view.getDayOfMonth());
/*  789 */                       String dateStr = dateFormat.format(cal.getTime());
/*      */                       
/*  791 */                       FormFill.this.applyFormFieldEditBoxAndQuit(false, dateStr);
/*  792 */                     } catch (Exception e) {
/*  793 */                       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                     } 
/*      */                   }
/*      */ 
/*      */                   
/*      */                   public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
/*      */                     try {
/*  800 */                       String format = Utils.getDateTimeFormatFromField(formatString, false);
/*  801 */                       SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.US);
/*      */                       
/*  803 */                       Calendar cal = Calendar.getInstance();
/*  804 */                       int year = cal.get(1);
/*  805 */                       int month = cal.get(2);
/*  806 */                       int day = cal.get(5);
/*  807 */                       cal.set(year, month, day, hourOfDay, minute);
/*  808 */                       String dateStr = dateFormat.format(cal.getTime());
/*      */                       
/*  810 */                       FormFill.this.applyFormFieldEditBoxAndQuit(false, dateStr);
/*  811 */                     } catch (Exception e) {
/*  812 */                       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */                     } 
/*      */                   }
/*      */ 
/*      */                   
/*      */                   public void onClear() {
/*  818 */                     FormFill.this.applyFormFieldEditBoxAndQuit(false, "");
/*      */                   }
/*      */ 
/*      */                   
/*      */                   public void onDismiss(boolean manuallyEnterValue, boolean dismissedWithNoSelection) {
/*  823 */                     if (dismissedWithNoSelection) {
/*  824 */                       FormFill.this.applyFormFieldEditBoxAndQuit(false);
/*      */                     }
/*  826 */                     FormFill.this.mCanUseDateTimePicker = !manuallyEnterValue;
/*  827 */                     FormFill.this.mEditor = null;
/*  828 */                     if (manuallyEnterValue) {
/*  829 */                       FormFill.this.handleTextInline();
/*      */                     }
/*      */                   }
/*      */                 });
/*  833 */             picker.show(fragmentActivity.getSupportFragmentManager(), "simple_date_picker");
/*      */             
/*      */             return;
/*      */           } 
/*      */         } 
/*      */         
/*  839 */         if (max_len > 0) {
/*  840 */           InputFilter.LengthFilter[] filters = new InputFilter.LengthFilter[1];
/*  841 */           filters[0] = new InputFilter.LengthFilter(max_len);
/*  842 */           this.mEditor.getEditText().setFilters((InputFilter[])filters);
/*      */         } 
/*      */ 
/*      */         
/*  846 */         if (this.mField.getFlag(8)) {
/*  847 */           this.mEditor.getEditText().setTransformationMethod((TransformationMethod)new PasswordTransformationMethod());
/*      */         }
/*      */ 
/*      */         
/*  851 */         String init_str = this.mField.getValueAsString();
/*  852 */         this.mEditor.getEditText().setText(init_str);
/*      */ 
/*      */         
/*  855 */         int just = this.mField.getJustification();
/*  856 */         int gravityVertical = 48;
/*  857 */         if (!this.mIsMultiLine) {
/*  858 */           gravityVertical = 16;
/*      */         }
/*  860 */         if (just == 0) {
/*  861 */           this.mEditor.setGravity(0x800003 | gravityVertical);
/*  862 */           if (Utils.isJellyBeanMR1()) {
/*  863 */             this.mEditor.getEditText().setTextAlignment(2);
/*      */           }
/*  865 */         } else if (just == 1) {
/*  866 */           this.mEditor.setGravity(0x11 | gravityVertical);
/*  867 */           if (Utils.isJellyBeanMR1()) {
/*  868 */             this.mEditor.getEditText().setTextAlignment(4);
/*      */           }
/*  870 */         } else if (just == 2) {
/*  871 */           this.mEditor.setGravity(0x800005 | gravityVertical);
/*  872 */           if (Utils.isJellyBeanMR1()) {
/*  873 */             this.mEditor.getEditText().setTextAlignment(3);
/*      */           }
/*      */         } 
/*  876 */         this.mEditor.setAnnot(this.mPdfViewCtrl, this.mAnnot, this.mAnnotPageNum);
/*  877 */         this.mEditor.setAnnotStyle(this.mPdfViewCtrl, AnnotUtils.getAnnotStyle(this.mAnnot));
/*  878 */         ViewerUtils.scrollToAnnotRect(this.mPdfViewCtrl, this.mAnnot.getRect(), this.mAnnotPageNum);
/*      */ 
/*      */         
/*  881 */         adjustFontSize((EditText)this.mEditor.getEditText());
/*      */ 
/*      */         
/*  884 */         mapColorFont((EditText)this.mEditor.getEditText());
/*      */ 
/*      */         
/*  887 */         this.mPdfViewCtrl.addView((View)this.mEditor);
/*  888 */         this.mEditor.getEditText().requestFocus();
/*      */         
/*  890 */         this.mEditor.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener()
/*      */             {
/*      */               public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
/*  893 */                 if (i == 6) {
/*  894 */                   String str = textView.getText().toString();
/*  895 */                   FormFill.this.applyFormFieldEditBoxAndQuit(true, str);
/*  896 */                   return true;
/*  897 */                 }  if (i == 5) {
/*  898 */                   return FormFill.this.tabToNextField(FormFill.this.mPdfViewCtrl.getCurrentPage());
/*      */                 }
/*  900 */                 return false;
/*      */               }
/*      */             });
/*      */         
/*  904 */         this.mEditor.getEditText().setAutoScrollEditTextListener(new AutoScrollEditText.AutoScrollEditTextListener()
/*      */             {
/*      */               public boolean onKeyUp(int keyCode, KeyEvent event) {
/*  907 */                 if (event.getKeyCode() == 4 && event
/*  908 */                   .getAction() == 1) {
/*  909 */                   FormFill.this.applyFormFieldEditBoxAndQuit(true);
/*  910 */                   return true;
/*      */                 } 
/*  912 */                 if (!FormFill.this.mIsMultiLine && event
/*  913 */                   .getKeyCode() == 66) {
/*  914 */                   FormFill.this.applyFormFieldEditBoxAndQuit(true);
/*  915 */                   return true;
/*      */                 } 
/*  917 */                 if (ShortcutHelper.isSwitchForm(keyCode, event)) {
/*  918 */                   FormFill.this.tabToNextField(FormFill.this.mPdfViewCtrl.getCurrentPage());
/*      */                 }
/*  920 */                 return true;
/*      */               }
/*      */ 
/*      */               
/*      */               public boolean onKeyPreIme(int keyCode, KeyEvent event) {
/*  925 */                 return false;
/*      */               }
/*      */             });
/*      */ 
/*      */         
/*  930 */         InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/*  931 */         if (imm != null) {
/*  932 */           imm.showSoftInput((View)this.mEditor.getEditText(), 2);
/*      */         }
/*      */       } 
/*  935 */     } catch (PDFNetException e) {
/*  936 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScale(float x, float y) {
/*  945 */     if (this.mEditor != null) {
/*  946 */       adjustFontSize((EditText)this.mEditor.getEditText());
/*      */     }
/*  948 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean onScaleEnd(float x, float y) {
/*  956 */     if (this.mEditor != null) {
/*  957 */       adjustFontSize((EditText)this.mEditor.getEditText());
/*  958 */       this.mEditor.getEditText().requestFocus();
/*      */     } 
/*  960 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDoubleTapEnd(MotionEvent e) {
/*  968 */     if (this.mEditor != null) {
/*  969 */       adjustFontSize((EditText)this.mEditor.getEditText());
/*  970 */       this.mEditor.getEditText().requestFocus();
/*      */     } 
/*      */   }
/*      */   
/*      */   private void applyFormFieldEditBoxAndQuit(boolean hideKeyboard) {
/*  975 */     applyFormFieldEditBoxAndQuit(hideKeyboard, (String)null);
/*      */   }
/*      */   
/*      */   private void applyFormFieldEditBoxAndQuit(boolean hideKeyboard, String str) {
/*  979 */     if (!this.mHasClosed && this.mPdfViewCtrl != null && this.mEditor != null) {
/*  980 */       boolean shouldUnlock = false;
/*  981 */       boolean hasModification = false;
/*  982 */       boolean hasOnlyExecutionChanges = false;
/*      */       try {
/*  984 */         this.mPdfViewCtrl.docLock(true);
/*  985 */         shouldUnlock = true;
/*  986 */         if (str == null && !isDialogShowing()) {
/*  987 */           str = this.mEditor.getEditText().getText().toString();
/*      */         }
/*  989 */         if (str != null) {
/*  990 */           if (!this.mField.getValueAsString().equals(str)) {
/*  991 */             hasModification = true;
/*      */           }
/*      */           
/*  994 */           if (hasModification) {
/*  995 */             raiseAnnotationPreModifyEvent(this.mAnnot, this.mAnnotPageNum);
/*  996 */             Widget widget = new Widget(this.mAnnot);
/*  997 */             updateFont(this.mPdfViewCtrl, widget, str);
/*      */             
/*  999 */             ViewChangeCollection view_change = this.mField.setValue(str);
/* 1000 */             this.mPdfViewCtrl.refreshAndUpdate(view_change);
/*      */           } 
/*      */           
/* 1003 */           executeAction(this.mField, 6);
/* 1004 */           executeAction(this.mField, 2);
/*      */         } 
/*      */         
/* 1007 */         if (hideKeyboard) {
/*      */           
/* 1009 */           InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 1010 */           if (imm != null) {
/* 1011 */             imm.hideSoftInputFromWindow(this.mEditor.getEditText().getWindowToken(), 0);
/*      */           }
/*      */         } 
/*      */         
/* 1015 */         this.mPdfViewCtrl.removeView((View)this.mEditor);
/* 1016 */         this.mEditor = null;
/*      */         
/* 1018 */         if (hasModification) {
/* 1019 */           raiseAnnotationModifiedEvent(this.mAnnot, this.mAnnotPageNum);
/*      */         } else {
/* 1021 */           hasOnlyExecutionChanges = this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot();
/*      */         } 
/*      */         
/* 1024 */         this.mHasClosed = true;
/* 1025 */       } catch (Exception e) {
/* 1026 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } finally {
/* 1028 */         unsetAnnot();
/* 1029 */         safeSetNextToolMode();
/* 1030 */         if (shouldUnlock) {
/* 1031 */           this.mPdfViewCtrl.docUnlock();
/* 1032 */           if (hasOnlyExecutionChanges) {
/* 1033 */             raiseAnnotationActionEvent();
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void safeSetNextToolMode() {
/* 1041 */     if (this.mForceSameNextToolMode) {
/* 1042 */       this.mNextToolMode = this.mCurrentDefaultToolMode;
/*      */     } else {
/* 1044 */       this.mNextToolMode = ToolManager.ToolMode.PAN;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FormFill.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */