/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.graphics.RectF;
/*     */ import android.text.Layout;
/*     */ import android.text.StaticLayout;
/*     */ import android.text.TextPaint;
/*     */ import android.text.TextWatcher;
/*     */ import android.util.TypedValue;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MotionEvent;
/*     */ import android.view.View;
/*     */ import android.view.inputmethod.InputMethodManager;
/*     */ import android.widget.ImageButton;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Point;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.FreeText;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.FreeTextCreate;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.viewmodel.RichTextEvent;
/*     */ import com.pdftron.pdf.viewmodel.RichTextViewModel;
/*     */ import com.pdftron.pdf.widget.AutoScrollEditText;
/*     */ import com.pdftron.pdf.widget.AutoScrollEditor;
/*     */ import com.pdftron.pdf.widget.richtext.PTRichEditor;
/*     */ import io.reactivex.disposables.CompositeDisposable;
/*     */ import io.reactivex.functions.Consumer;
/*     */ import java.util.List;
/*     */ import jp.wasabeef.richeditor.RichEditor;
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
/*     */ public class InlineEditText
/*     */ {
/*     */   private AutoScrollEditor mEditor;
/*     */   private ImageButton mToggleButton;
/*     */   private int mToggleButtonWidth;
/*     */   private boolean mIsEditing;
/*     */   private boolean mCreatingAnnot;
/*     */   private InlineEditTextListener mListener;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private boolean mTapToCloseConfirmed = false;
/*     */   private boolean mDelayViewRemoval = false;
/*     */   private boolean mDelaySetContents = false;
/*     */   private String mDelayedContents;
/*     */   @ColorInt
/*     */   private int mTextColor;
/*     */   private int mTextSize;
/*     */   @Nullable
/*     */   private RichTextViewModel mRichTextViewModel;
/*  93 */   private CompositeDisposable mDisposable = new CompositeDisposable();
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
/*     */   @SuppressLint({"ClickableViewAccessibility"})
/*     */   public InlineEditText(PDFViewCtrl pdfView, Annot annot, int pageNum, Point targetPagePoint, @NonNull InlineEditTextListener listener) {
/* 114 */     this(pdfView, annot, pageNum, targetPagePoint, true, false, listener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"ClickableViewAccessibility"})
/*     */   public InlineEditText(PDFViewCtrl pdfView, Annot annot, int pageNum, Point targetPagePoint, boolean freeTextInlineToggleEnabled, boolean richContentEnabled, @NonNull InlineEditTextListener listener) {
/* 136 */     this(pdfView, annot, pageNum, targetPagePoint, freeTextInlineToggleEnabled, richContentEnabled, true, listener);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @SuppressLint({"ClickableViewAccessibility"})
/*     */   public InlineEditText(@NonNull PDFViewCtrl pdfView, @Nullable final Annot annot, int pageNum, @Nullable Point targetPagePoint, boolean freeTextInlineToggleEnabled, boolean richContentEnabled, boolean editingEnabled, @NonNull InlineEditTextListener listener) {
/* 162 */     this.mCreatingAnnot = (targetPagePoint != null);
/* 163 */     this.mPdfViewCtrl = pdfView;
/* 164 */     this.mListener = listener;
/*     */ 
/*     */     
/* 167 */     this.mEditor = new AutoScrollEditor(this.mPdfViewCtrl.getContext());
/* 168 */     this.mEditor.setRichContentEnabled(richContentEnabled);
/* 169 */     boolean tapOutsideToCommit = false;
/*     */     
/*     */     try {
/* 172 */       AnnotStyle annotStyle = (annot != null) ? AnnotUtils.getAnnotStyle(annot) : ToolStyleConfig.getInstance().getCustomAnnotStyle(this.mPdfViewCtrl.getContext(), 2, "");
/* 173 */       boolean isSpacingText = (annotStyle != null && annotStyle.getAnnotType() == 1010);
/* 174 */       boolean isAutoResize = false;
/* 175 */       ToolManager tm = null;
/* 176 */       if (pdfView.getToolManager() instanceof ToolManager) {
/* 177 */         tm = (ToolManager)pdfView.getToolManager();
/* 178 */         isAutoResize = tm.isAutoResizeFreeText();
/*     */       } 
/* 180 */       tapOutsideToCommit = (isSpacingText || isAutoResize);
/* 181 */       if (annot != null && !richContentEnabled && !isSpacingText && !isAutoResize) {
/*     */ 
/*     */         
/* 184 */         this.mEditor.setAnnot(this.mPdfViewCtrl, annot, pageNum);
/* 185 */         boolean shouldUnlockRead = false;
/*     */         
/* 187 */         try { this.mPdfViewCtrl.docLockRead();
/* 188 */           shouldUnlockRead = true;
/* 189 */           this.mEditor.setAnnotStyle(pdfView, annotStyle); }
/* 190 */         catch (Exception exception) {  }
/*     */         finally
/* 192 */         { if (shouldUnlockRead) {
/* 193 */             this.mPdfViewCtrl.docUnlockRead();
/*     */           } }
/*     */       
/*     */       } else {
/* 197 */         if (null == targetPagePoint && annot != null) {
/*     */ 
/*     */           
/* 200 */           boolean shouldUnlockRead = false;
/*     */           
/* 202 */           try { this.mPdfViewCtrl.docLockRead();
/* 203 */             shouldUnlockRead = true;
/* 204 */             if (annot.getType() == 2) {
/* 205 */               FreeText freeText = new FreeText(annot);
/* 206 */               Rect r = freeText.getContentRect();
/* 207 */               r.normalize();
/* 208 */               if (this.mPdfViewCtrl.getRightToLeftLanguage()) {
/* 209 */                 targetPagePoint = new Point(r.getX2(), r.getY2());
/*     */               } else {
/* 211 */                 targetPagePoint = new Point(r.getX1(), r.getY2());
/*     */               } 
/*     */             }  }
/* 214 */           catch (Exception exception) {  }
/*     */           finally
/* 216 */           { if (shouldUnlockRead) {
/* 217 */               this.mPdfViewCtrl.docUnlockRead();
/*     */             } }
/*     */         
/*     */         } 
/* 221 */         if (targetPagePoint != null) {
/*     */           
/* 223 */           Rect screenRect = Utils.getScreenRectInPageSpace(pdfView, pageNum);
/* 224 */           Rect pageRect = Utils.getPageRect(pdfView, pageNum);
/* 225 */           Rect intersectRect = new Rect();
/* 226 */           if (screenRect != null && pageRect != null) {
/* 227 */             screenRect.normalize();
/* 228 */             pageRect.normalize();
/* 229 */             intersectRect.intersectRect(screenRect, pageRect);
/* 230 */             intersectRect.normalize();
/* 231 */             Rect freeTextRect = new Rect();
/*     */             
/* 233 */             int pageRotation = 0;
/* 234 */             int viewRotation = 0;
/*     */             
/* 236 */             boolean shouldUnlockRead = false;
/*     */             
/* 238 */             try { this.mPdfViewCtrl.docLockRead();
/* 239 */               shouldUnlockRead = true;
/*     */               
/* 241 */               pageRotation = this.mPdfViewCtrl.getDoc().getPage(pageNum).getRotation();
/* 242 */               viewRotation = this.mPdfViewCtrl.getPageRotation(); }
/* 243 */             catch (Exception exception) {  }
/*     */             finally
/* 245 */             { if (shouldUnlockRead) {
/* 246 */                 this.mPdfViewCtrl.docUnlockRead();
/*     */               } }
/*     */             
/* 249 */             int annotRotation = (pageRotation + viewRotation) % 4 * 90;
/*     */             
/* 251 */             if (this.mPdfViewCtrl.getRightToLeftLanguage()) {
/* 252 */               if (annotRotation == 0) {
/* 253 */                 freeTextRect.set(targetPagePoint.x, targetPagePoint.y, 0.0D, 0.0D);
/* 254 */               } else if (annotRotation == 90) {
/* 255 */                 freeTextRect.set(targetPagePoint.x, targetPagePoint.y, pageRect.getX2(), 0.0D);
/* 256 */               } else if (annotRotation == 180) {
/* 257 */                 freeTextRect.set(targetPagePoint.x, targetPagePoint.y, pageRect.getX2(), pageRect.getY2());
/*     */               } else {
/* 259 */                 freeTextRect.set(targetPagePoint.x, targetPagePoint.y, 0.0D, pageRect.getY2());
/*     */               }
/*     */             
/* 262 */             } else if (annotRotation == 0) {
/* 263 */               freeTextRect.set(targetPagePoint.x, targetPagePoint.y, pageRect.getX2(), 0.0D);
/* 264 */             } else if (annotRotation == 90) {
/* 265 */               freeTextRect.set(targetPagePoint.x, targetPagePoint.y, pageRect.getX2(), pageRect.getY2());
/* 266 */             } else if (annotRotation == 180) {
/* 267 */               freeTextRect.set(targetPagePoint.x, targetPagePoint.y, 0.0D, pageRect.getY2());
/*     */             } else {
/* 269 */               freeTextRect.set(targetPagePoint.x, targetPagePoint.y, 0.0D, 0.0D);
/*     */             } 
/*     */             
/* 272 */             freeTextRect.normalize();
/* 273 */             intersectRect.intersectRect(intersectRect, freeTextRect);
/* 274 */             intersectRect.normalize();
/* 275 */             this.mEditor.setRect(this.mPdfViewCtrl, intersectRect, pageNum);
/* 276 */             this.mEditor.setAnnotStyle(this.mPdfViewCtrl, annotStyle);
/*     */             
/* 278 */             final ToolManager tmFinal = tm;
/* 279 */             this.mPdfViewCtrl.docLockRead(new PDFViewCtrl.LockRunnable()
/*     */                 {
/*     */                   public void run() throws Exception {
/* 282 */                     Rect defaultRect = null;
/* 283 */                     if (tmFinal != null && !tmFinal.isDeleteEmptyFreeText() && tmFinal.isAutoResizeFreeText() && 
/* 284 */                       annot.getType() == 2) {
/* 285 */                       FreeText freeText = new FreeText(annot);
/* 286 */                       defaultRect = FreeTextCreate.getDefaultRect(freeText);
/*     */                     } 
/*     */                     
/* 289 */                     InlineEditText.this.mEditor.getEditText().setDefaultRect(defaultRect);
/*     */                   }
/*     */                 });
/*     */           } 
/*     */         } 
/*     */       } 
/* 295 */     } catch (Exception e) {
/* 296 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } 
/*     */     
/* 299 */     LayoutInflater inflater = (LayoutInflater)this.mPdfViewCtrl.getContext().getSystemService("layout_inflater");
/* 300 */     if (inflater == null) {
/*     */       return;
/*     */     }
/*     */     
/* 304 */     int padding = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.padding_small);
/* 305 */     this.mEditor.getEditText().setPadding(padding, 0, 0, 0);
/*     */ 
/*     */     
/* 308 */     View toggleButtonView = inflater.inflate(R.layout.tools_free_text_inline_toggle_button, null);
/* 309 */     this.mToggleButton = (ImageButton)toggleButtonView.findViewById(R.id.inline_toggle_button);
/* 310 */     this.mToggleButton.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 313 */             InlineEditText.this.mListener.toggleToFreeTextDialog(InlineEditText.this.mEditor.getEditText().getText().toString());
/*     */           }
/*     */         });
/* 316 */     if (!freeTextInlineToggleEnabled || richContentEnabled) {
/* 317 */       this.mToggleButton.setVisibility(8);
/*     */     }
/* 319 */     this.mToggleButtonWidth = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.free_text_inline_toggle_button_width);
/*     */ 
/*     */     
/* 322 */     this.mPdfViewCtrl.setVerticalScrollBarEnabled(false);
/* 323 */     this.mPdfViewCtrl.setHorizontalScrollBarEnabled(false);
/*     */ 
/*     */ 
/*     */     
/* 327 */     this.mEditor.getActiveEditor().post(new Runnable()
/*     */         {
/*     */           public void run() {
/* 330 */             RectF editBoxRect = InlineEditText.this.mListener.getInlineEditTextPosition();
/* 331 */             InlineEditText.this.setEditTextLocation(editBoxRect);
/*     */           }
/*     */         });
/*     */     
/* 335 */     this.mPdfViewCtrl.addView((View)this.mEditor);
/* 336 */     this.mPdfViewCtrl.addView((View)this.mToggleButton);
/*     */ 
/*     */     
/* 339 */     if (editingEnabled && this.mEditor.getActiveEditor().requestFocus()) {
/*     */       
/* 341 */       InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 342 */       if (imm != null) {
/* 343 */         imm.toggleSoftInput(2, 1);
/*     */       }
/*     */     } 
/* 346 */     if (this.mEditor.isRichContentEnabled()) {
/* 347 */       this.mEditor.getRichEditor().setOnInitialLoadListener(new RichEditor.AfterInitialLoadListener()
/*     */           {
/*     */             public void onAfterInitialLoad(boolean isReady) {
/* 350 */               if (isReady) {
/* 351 */                 InlineEditText.this.mEditor.getRichEditor().focusEditor();
/* 352 */                 Utils.showSoftKeyboard(InlineEditText.this.mEditor.getRichEditor().getContext(), (View)InlineEditText.this.mEditor.getRichEditor());
/* 353 */                 InlineEditText.this.setTextColor(InlineEditText.this.mTextColor);
/* 354 */                 InlineEditText.this.setTextSize(InlineEditText.this.mTextSize);
/*     */               } 
/*     */             }
/*     */           });
/* 358 */       this.mEditor.getRichEditor().setOnDecorationChangeListener(new RichEditor.OnDecorationStateListener()
/*     */           {
/*     */             public void onStateChangeListener(String text, List<RichEditor.Type> types) {
/* 361 */               if (InlineEditText.this.mRichTextViewModel != null) {
/* 362 */                 InlineEditText.this.mRichTextViewModel.onUpdateDecorationTypes(types);
/*     */               }
/*     */             }
/*     */           });
/*     */     } 
/* 367 */     if (this.mCreatingAnnot || tapOutsideToCommit)
/*     */     {
/*     */ 
/*     */ 
/*     */       
/* 372 */       this.mEditor.getActiveEditor().setOnTouchListener(new View.OnTouchListener() {
/*     */             public boolean onTouch(View v, MotionEvent event) {
/*     */               int y;
/* 375 */               switch (event.getAction()) {
/*     */                 case 0:
/* 377 */                   y = (int)event.getY();
/*     */                   
/* 379 */                   if (InlineEditText.this.mEditor.getActiveEditor() != null) {
/* 380 */                     int height = 0;
/* 381 */                     if (InlineEditText.this.mEditor.getActiveEditor() instanceof android.widget.EditText) {
/* 382 */                       int width = InlineEditText.this.mEditor.getEditText().getMeasuredWidth();
/*     */                       
/* 384 */                       TextPaint textPaint = InlineEditText.this.mEditor.getEditText().getPaint();
/* 385 */                       String text = InlineEditText.this.mEditor.getEditText().getText().toString();
/*     */                       
/* 387 */                       StaticLayout layout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
/*     */                       
/* 389 */                       height = layout.getHeight();
/* 390 */                     } else if (InlineEditText.this.mEditor.getActiveEditor() instanceof PTRichEditor) {
/* 391 */                       height = InlineEditText.this.mEditor.getRichEditor().getContentHeight();
/*     */                     } 
/*     */ 
/*     */                     
/* 395 */                     int buffer = (int)TypedValue.applyDimension(1, 50.0F, InlineEditText.this.mPdfViewCtrl.getContext().getResources().getDisplayMetrics());
/* 396 */                     if (y > height + buffer) {
/* 397 */                       InlineEditText.this.mTapToCloseConfirmed = true;
/*     */                     }
/*     */                   } 
/*     */                   break;
/*     */                 
/*     */                 case 8:
/* 403 */                   InlineEditText.this.mTapToCloseConfirmed = false;
/*     */                   break;
/*     */                 
/*     */                 case 1:
/* 407 */                   if (InlineEditText.this.mTapToCloseConfirmed) {
/* 408 */                     InlineEditText.this.mTapToCloseConfirmed = false;
/*     */ 
/*     */ 
/*     */ 
/*     */                     
/* 413 */                     InlineEditText.this.mPdfViewCtrl.getToolManager().onUp(event, PDFViewCtrl.PriorEventMode.OTHER);
/*     */                     
/* 415 */                     return true;
/*     */                   } 
/*     */                   break;
/*     */               } 
/*     */ 
/*     */               
/* 421 */               return false;
/*     */             }
/*     */           });
/*     */     }
/*     */ 
/*     */     
/* 427 */     ((ToolManager)this.mPdfViewCtrl.getToolManager()).onInlineFreeTextEditingStarted();
/*     */     
/* 429 */     this.mIsEditing = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(boolean manuallyRemoveView) {
/* 438 */     close(manuallyRemoveView, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close(boolean manuallyRemoveView, boolean hideKeyboard) {
/* 449 */     this.mPdfViewCtrl.removeView((View)this.mToggleButton);
/*     */     
/* 451 */     if (Utils.isLollipop()) {
/* 452 */       this.mEditor.getEditText().removeSpacingHandle();
/*     */     }
/*     */ 
/*     */     
/* 456 */     if (hideKeyboard) {
/* 457 */       InputMethodManager imm = (InputMethodManager)this.mPdfViewCtrl.getContext().getSystemService("input_method");
/* 458 */       if (imm != null) {
/* 459 */         imm.hideSoftInputFromWindow(this.mPdfViewCtrl.getRootView().getWindowToken(), 0);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 464 */     if (this.mEditor.isRichContentEnabled()) {
/* 465 */       this.mEditor.getRichEditor().setOnInitialLoadListener(null);
/* 466 */       this.mEditor.getRichEditor().setOnDecorationChangeListener(null);
/*     */     } 
/*     */ 
/*     */     
/* 470 */     this.mPdfViewCtrl.setVerticalScrollBarEnabled(false);
/* 471 */     this.mPdfViewCtrl.setHorizontalScrollBarEnabled(false);
/*     */     
/* 473 */     this.mIsEditing = false;
/*     */     
/* 475 */     if (manuallyRemoveView) {
/* 476 */       this.mPdfViewCtrl.removeView((View)this.mEditor);
/*     */     } else {
/* 478 */       this.mDelayViewRemoval = true;
/*     */     } 
/*     */     
/* 481 */     this.mDisposable.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Boolean isEditing() {
/* 490 */     return Boolean.valueOf(this.mIsEditing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delayViewRemoval() {
/* 499 */     return this.mDelayViewRemoval;
/*     */   }
/*     */   
/*     */   public void setHTMLContents(String htmlContents) {
/* 503 */     getRichEditor().setHtml(htmlContents);
/* 504 */     getRichEditor().clearFocusEditor();
/* 505 */     getRichEditor().focusEditor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContents() {
/* 514 */     return this.mEditor.getActiveText();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContents() {
/* 521 */     if (null != this.mDelayedContents) {
/* 522 */       AutoScrollEditText autoScrollEditText = this.mEditor.getEditText();
/* 523 */       autoScrollEditText.setText(this.mDelayedContents);
/* 524 */       if (autoScrollEditText.getText() != null) {
/* 525 */         autoScrollEditText.setSelection(autoScrollEditText.getText().length());
/*     */       }
/*     */     } 
/* 528 */     this.mDelaySetContents = false;
/* 529 */     this.mDelayedContents = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setContents(String contents) {
/* 538 */     AutoScrollEditText autoScrollEditText = this.mEditor.getEditText();
/* 539 */     autoScrollEditText.setText(contents);
/* 540 */     if (autoScrollEditText.getText() != null) {
/* 541 */       autoScrollEditText.setSelection(autoScrollEditText.getText().length());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean delaySetContents() {
/* 551 */     return this.mDelaySetContents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDelaySetContents(String contents) {
/* 560 */     if (this.mPdfViewCtrl.isAnnotationLayerEnabled()) {
/* 561 */       setContents(contents);
/*     */     } else {
/* 563 */       this.mDelaySetContents = true;
/* 564 */       this.mDelayedContents = contents;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextSize(int textSize) {
/* 574 */     this.mTextSize = textSize;
/* 575 */     if (this.mEditor.isRichContentEnabled()) {
/* 576 */       this.mEditor.getRichEditor().setEditorFontSize(textSize);
/*     */     }
/* 578 */     textSize = (int)(textSize * (float)this.mPdfViewCtrl.getZoom());
/* 579 */     this.mEditor.getEditText().setTextSize(0, textSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(@ColorInt int textColor) {
/* 588 */     this.mTextColor = textColor;
/* 589 */     this.mEditor.getEditText().setTextColor(textColor);
/* 590 */     if (this.mEditor.isRichContentEnabled()) {
/* 591 */       this.mEditor.getRichEditor().setTextColor(textColor);
/*     */     }
/*     */   }
/*     */   
/*     */   public AutoScrollEditor getEditor() {
/* 596 */     return this.mEditor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AutoScrollEditText getEditText() {
/* 605 */     return this.mEditor.getEditText();
/*     */   }
/*     */   
/*     */   public PTRichEditor getRichEditor() {
/* 609 */     return this.mEditor.getRichEditor();
/*     */   }
/*     */   
/*     */   public void setRichTextViewModel(@Nullable RichTextViewModel viewModel) {
/* 613 */     this.mRichTextViewModel = viewModel;
/* 614 */     if (this.mRichTextViewModel != null) {
/* 615 */       this.mDisposable.add(this.mRichTextViewModel.getObservable()
/* 616 */           .subscribe(new Consumer<RichTextEvent>() {
/*     */               public void accept(RichTextEvent richTextEvent) throws Exception {
/*     */                 AnnotStyle annotStyle;
/* 619 */                 switch (richTextEvent.getEventType()) {
/*     */                   case TEXT_STYLE:
/* 621 */                     annotStyle = richTextEvent.getAnnotStyle();
/* 622 */                     InlineEditText.this.updateRichContentStyle(annotStyle);
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case HIDE_KEYBOARD:
/* 627 */                     Utils.hideSoftKeyboard(InlineEditText.this.getRichEditor().getContext(), (View)InlineEditText.this.getRichEditor());
/*     */                     break;
/*     */                   case UNDO:
/* 630 */                     InlineEditText.this.getRichEditor().undo();
/*     */                     break;
/*     */                   case REDO:
/* 633 */                     InlineEditText.this.getRichEditor().redo();
/*     */                     break;
/*     */                   case BOLD:
/* 636 */                     InlineEditText.this.getRichEditor().setBold();
/*     */                     break;
/*     */                   case ITALIC:
/* 639 */                     InlineEditText.this.getRichEditor().setItalic();
/*     */                     break;
/*     */                   case STRIKE_THROUGH:
/* 642 */                     InlineEditText.this.getRichEditor().setStrikeThrough();
/*     */                     break;
/*     */                   case UNDERLINE:
/* 645 */                     InlineEditText.this.getRichEditor().setUnderline();
/*     */                     break;
/*     */                   case INDENT:
/* 648 */                     InlineEditText.this.getRichEditor().setIndent();
/*     */                     break;
/*     */                   case OUTDENT:
/* 651 */                     InlineEditText.this.getRichEditor().setOutdent();
/*     */                     break;
/*     */                   case ALIGN_LEFT:
/* 654 */                     InlineEditText.this.getRichEditor().setAlignLeft();
/*     */                     break;
/*     */                   case ALIGN_CENTER:
/* 657 */                     InlineEditText.this.getRichEditor().setAlignCenter();
/*     */                     break;
/*     */                   case ALIGN_RIGHT:
/* 660 */                     InlineEditText.this.getRichEditor().setAlignRight();
/*     */                     break;
/*     */                   case BULLETS:
/* 663 */                     InlineEditText.this.getRichEditor().setBullets();
/*     */                     break;
/*     */                   case NUMBERS:
/* 666 */                     InlineEditText.this.getRichEditor().setNumbers();
/*     */                     break;
/*     */                   case BLOCK_QUOTE:
/* 669 */                     InlineEditText.this.getRichEditor().setBlockquote();
/*     */                     break;
/*     */                 } 
/*     */               }
/*     */             }new Consumer<Throwable>()
/*     */             {
/*     */               public void accept(Throwable throwable) throws Exception {
/* 676 */                 AnalyticsHandlerAdapter.getInstance().sendException(new Exception(throwable));
/*     */               }
/*     */             }));
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateRichContentStyle(@Nullable AnnotStyle annotStyle) {
/* 683 */     if (null == annotStyle) {
/*     */       return;
/*     */     }
/* 686 */     setTextColor(annotStyle.getTextColor());
/* 687 */     setTextSize(Math.round(annotStyle.getTextSize()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBackgroundColor(@ColorInt int backgroundColor) {
/* 696 */     this.mEditor.getEditText().setBackgroundColor(backgroundColor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setEditTextLocation(RectF position) {
/* 705 */     int left = (int)position.left;
/* 706 */     int top = (int)position.top;
/* 707 */     int right = (int)position.right;
/* 708 */     int bottom = (int)position.bottom;
/*     */     
/* 710 */     if (this.mCreatingAnnot) {
/*     */ 
/*     */       
/* 713 */       int lineHeight = this.mEditor.getEditText().getLineHeight();
/* 714 */       if (Math.abs(bottom - top) < lineHeight * 1.5D) {
/* 715 */         top = bottom - (int)(lineHeight * 1.5D);
/*     */       }
/*     */       
/* 718 */       int minWidth = this.mPdfViewCtrl.getContext().getResources().getDimensionPixelSize(R.dimen.free_text_inline_min_textbox_width);
/* 719 */       if (Math.abs(left - right) < minWidth) {
/* 720 */         left = right - minWidth;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 725 */     int screenWidth = Utils.getScreenWidth(this.mPdfViewCtrl.getContext());
/* 726 */     int editTextWidth = right - left;
/*     */ 
/*     */     
/* 729 */     int screenButtonPosRight = right - this.mPdfViewCtrl.getScrollX() + this.mPdfViewCtrl.getHScrollPos() + this.mToggleButtonWidth;
/* 730 */     int screenButtonPosLeft = left - this.mPdfViewCtrl.getScrollX() + this.mPdfViewCtrl.getHScrollPos() - this.mToggleButtonWidth;
/*     */     
/* 732 */     int screenPageLeft = this.mPdfViewCtrl.getHScrollPos();
/* 733 */     int screenPageRight = screenPageLeft + this.mPdfViewCtrl.getWidth();
/* 734 */     int buttonViewLeftPos = left - this.mToggleButtonWidth;
/* 735 */     int buttonViewRightPos = right + this.mToggleButtonWidth;
/*     */ 
/*     */     
/* 738 */     int buttonPosBottom = top + this.mToggleButtonWidth;
/* 739 */     if (this.mEditor.getEditText().getLineHeight() < this.mToggleButtonWidth) {
/* 740 */       buttonPosBottom = top + this.mEditor.getEditText().getLineHeight();
/*     */       
/* 742 */       int screenButtonPostTop = buttonPosBottom - this.mPdfViewCtrl.getScrollY() + this.mPdfViewCtrl.getVScrollPos() - this.mToggleButtonWidth;
/* 743 */       if (screenButtonPostTop < this.mPdfViewCtrl.getScrollY()) {
/* 744 */         buttonPosBottom = top + this.mToggleButtonWidth;
/*     */       }
/*     */     } 
/*     */     
/* 748 */     if (this.mPdfViewCtrl.getRightToLeftLanguage()) {
/* 749 */       if (editTextWidth >= screenWidth) {
/*     */         
/* 751 */         this.mToggleButton.layout(left, buttonPosBottom - this.mToggleButtonWidth, left + this.mToggleButtonWidth, buttonPosBottom);
/* 752 */         this.mPdfViewCtrl.scrollBy(right - this.mPdfViewCtrl.getScrollX(), 0);
/*     */         
/* 754 */         this.mToggleButton.setRotation(270.0F);
/* 755 */         this.mToggleButton.setBackgroundResource(R.drawable.annotation_free_text_toggle_button_transparent_bgd);
/* 756 */       } else if (screenPageRight > screenButtonPosRight) {
/*     */ 
/*     */         
/* 759 */         this.mToggleButton.setRotation(0.0F);
/* 760 */         this.mToggleButton.layout(right, buttonPosBottom - this.mToggleButtonWidth, buttonViewRightPos, buttonPosBottom);
/* 761 */       } else if (screenButtonPosRight < screenPageRight) {
/*     */ 
/*     */         
/* 764 */         this.mToggleButton.setRotation(0.0F);
/* 765 */         this.mPdfViewCtrl.scrollBy(buttonViewRightPos - this.mPdfViewCtrl.getScrollX() - this.mPdfViewCtrl.getWidth(), 0);
/* 766 */         this.mToggleButton.layout(right, buttonPosBottom - this.mToggleButtonWidth, buttonViewRightPos, buttonPosBottom);
/* 767 */       } else if (screenButtonPosLeft > screenPageLeft) {
/*     */ 
/*     */         
/* 770 */         this.mToggleButton.layout(left - this.mToggleButtonWidth, buttonPosBottom - this.mToggleButtonWidth, left, buttonPosBottom);
/*     */         
/* 772 */         this.mToggleButton.setRotation(270.0F);
/*     */       } else {
/*     */         
/* 775 */         this.mToggleButton.setRotation(270.0F);
/* 776 */         this.mToggleButton.setBackgroundResource(R.drawable.annotation_free_text_toggle_button_transparent_bgd);
/*     */         
/* 778 */         this.mToggleButton.layout(left, buttonPosBottom - this.mToggleButtonWidth, left + this.mToggleButtonWidth, buttonPosBottom);
/*     */       }
/*     */     
/* 781 */     } else if (editTextWidth >= screenWidth) {
/*     */       
/* 783 */       this.mToggleButton.layout(right - this.mToggleButtonWidth, buttonPosBottom - this.mToggleButtonWidth, right, buttonPosBottom);
/* 784 */       this.mPdfViewCtrl.scrollBy(left - this.mPdfViewCtrl.getScrollX(), 0);
/*     */       
/* 786 */       this.mToggleButton.setRotation(0.0F);
/* 787 */       this.mToggleButton.setBackgroundResource(R.drawable.annotation_free_text_toggle_button_transparent_bgd);
/* 788 */     } else if (screenPageLeft < screenButtonPosLeft) {
/*     */ 
/*     */       
/* 791 */       this.mToggleButton.setRotation(270.0F);
/* 792 */       this.mToggleButton.layout(buttonViewLeftPos, buttonPosBottom - this.mToggleButtonWidth, left, buttonPosBottom);
/* 793 */     } else if (screenButtonPosLeft > 0) {
/*     */ 
/*     */       
/* 796 */       this.mToggleButton.setRotation(270.0F);
/* 797 */       this.mPdfViewCtrl.scrollBy(buttonViewLeftPos - this.mPdfViewCtrl.getScrollX(), 0);
/* 798 */       this.mToggleButton.layout(buttonViewLeftPos, buttonPosBottom - this.mToggleButtonWidth, left, buttonPosBottom);
/* 799 */     } else if (screenButtonPosRight < screenPageRight) {
/*     */ 
/*     */       
/* 802 */       this.mToggleButton.layout(right, buttonPosBottom - this.mToggleButtonWidth, right + this.mToggleButtonWidth, buttonPosBottom);
/*     */       
/* 804 */       this.mToggleButton.setRotation(0.0F);
/*     */     } else {
/*     */       
/* 807 */       this.mToggleButton.setRotation(0.0F);
/* 808 */       this.mToggleButton.setBackgroundResource(R.drawable.annotation_free_text_toggle_button_transparent_bgd);
/*     */       
/* 810 */       this.mToggleButton.layout(right - this.mToggleButtonWidth, buttonPosBottom - this.mToggleButtonWidth, right, buttonPosBottom);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeView() {
/* 819 */     if (this.mEditor != null) {
/* 820 */       this.mPdfViewCtrl.removeView((View)this.mEditor);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addTextWatcher(TextWatcher textWatcherListener) {
/* 830 */     this.mEditor.getEditText().addTextChangedListener(textWatcherListener);
/*     */   }
/*     */   
/*     */   public static interface InlineEditTextListener {
/*     */     RectF getInlineEditTextPosition();
/*     */     
/*     */     void toggleToFreeTextDialog(String param1String);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\InlineEditText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */