/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.RectF;
/*     */ import android.text.Editable;
/*     */ import android.text.TextWatcher;
/*     */ import android.util.Log;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.Button;
/*     */ import android.widget.EditText;
/*     */ import android.widget.RadioButton;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Action;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.Destination;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Link;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
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
/*     */ public class DialogLinkEditor
/*     */   extends AlertDialog
/*     */   implements View.OnClickListener, View.OnFocusChangeListener, DialogInterface.OnClickListener
/*     */ {
/*     */   private PDFViewCtrl mCtrl;
/*     */   private View dialogView;
/*     */   private RadioButton externalRadioButton;
/*     */   private RadioButton internalRadioButton;
/*     */   private RadioButton mSelectedRadioButton;
/*     */   private EditText pageEditText;
/*     */   private EditText urlEditText;
/*     */   private Tool mTool;
/*     */   private HashMap<Rect, Integer> mSelRect;
/*     */   private int mStrokeColor;
/*     */   private float mThickness;
/*     */   private Link mLink;
/*     */   private int mLinkPage;
/*     */   private int mPageCount;
/*     */   private Button mPosButton;
/*     */   private HashMap<Annot, Integer> mAnnots;
/*     */   public static final int LINK_OPTION_URL = 0;
/*     */   public static final int LINK_OPTION_PAGE = 1;
/*     */   
/*     */   public DialogLinkEditor(@NonNull PDFViewCtrl ctrl, @NonNull Tool tool, HashMap<Rect, Integer> selRect) {
/*  81 */     super(ctrl.getContext());
/*  82 */     this.mCtrl = ctrl;
/*  83 */     this.mTool = tool;
/*  84 */     this.mSelRect = new HashMap<>();
/*  85 */     this.mSelRect.putAll(selRect);
/*  86 */     if (this.mSelRect == null) {
/*  87 */       ((ToolManager)this.mCtrl.getToolManager()).annotationCouldNotBeAdded("no link selected");
/*  88 */       dismiss();
/*     */     } 
/*     */     
/*  91 */     init();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DialogLinkEditor(@NonNull PDFViewCtrl ctrl, @NonNull Tool tool, Link link) {
/*  99 */     super(ctrl.getContext());
/* 100 */     this.mCtrl = ctrl;
/* 101 */     this.mLink = link;
/* 102 */     this.mTool = tool;
/* 103 */     if (this.mLink == null) {
/* 104 */       ((ToolManager)this.mCtrl.getToolManager()).annotationCouldNotBeAdded("no link selected");
/* 105 */       dismiss();
/*     */     } 
/* 107 */     init();
/* 108 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 110 */       this.mCtrl.docLockRead();
/* 111 */       shouldUnlockRead = true;
/* 112 */       Action linkAction = this.mLink.getAction();
/* 113 */       if (!linkAction.isValid()) {
/*     */         return;
/*     */       }
/* 116 */       if (linkAction.getType() == 0) {
/* 117 */         Destination dest = linkAction.getDest();
/* 118 */         if (dest.isValid()) {
/* 119 */           Page linkPage = dest.getPage();
/*     */           
/* 121 */           if (linkPage != null) {
/*     */             
/* 123 */             int linkPageNum = dest.getPage().getIndex();
/* 124 */             this.pageEditText.setText(String.format(Locale.getDefault(), "%d", new Object[] { Integer.valueOf(linkPageNum) }));
/* 125 */             this.pageEditText.requestFocus();
/* 126 */             this.externalRadioButton.setChecked(false);
/* 127 */             this.mSelectedRadioButton = this.internalRadioButton;
/*     */           } 
/*     */         } 
/* 130 */       } else if (linkAction.getType() == 5) {
/* 131 */         String target = linkAction.getSDFObj().get("URI").value().getAsPDFText();
/* 132 */         this.urlEditText.setText(target);
/* 133 */         this.urlEditText.requestFocus();
/*     */       } 
/* 135 */     } catch (PDFNetException e) {
/* 136 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 137 */       e.printStackTrace();
/*     */     } finally {
/* 139 */       if (shouldUnlockRead) {
/* 140 */         this.mCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void init() {
/* 147 */     LayoutInflater inflater = (LayoutInflater)this.mCtrl.getContext().getSystemService("layout_inflater");
/* 148 */     this.dialogView = inflater.inflate(R.layout.tools_dialog_link_editor, null);
/* 149 */     this.mPosButton = null;
/*     */     
/* 151 */     this.externalRadioButton = (RadioButton)this.dialogView.findViewById(R.id.tools_dialog_link_external_radio_button);
/* 152 */     this.externalRadioButton.setOnClickListener(this);
/* 153 */     this.internalRadioButton = (RadioButton)this.dialogView.findViewById(R.id.tools_dialog_link_internal_radio_button);
/* 154 */     this.internalRadioButton.setOnClickListener(this);
/*     */     
/* 156 */     this.urlEditText = (EditText)this.dialogView.findViewById(R.id.tools_dialog_link_external_edit_text);
/* 157 */     this.urlEditText.setOnFocusChangeListener(this);
/* 158 */     this.urlEditText.setSelectAllOnFocus(true);
/* 159 */     this.urlEditText.addTextChangedListener(new UrlChangeListener());
/*     */     
/* 161 */     this.pageEditText = (EditText)this.dialogView.findViewById(R.id.tools_dialog_link_internal_edit_text);
/* 162 */     this.pageEditText.setOnFocusChangeListener(this);
/* 163 */     this.pageEditText.setSelectAllOnFocus(true);
/* 164 */     this.pageEditText.addTextChangedListener(new PageChangeListener());
/* 165 */     this.mPageCount = this.mCtrl.getPageCount();
/* 166 */     if (this.mPageCount > 0) {
/* 167 */       String mHint = String.format(this.mCtrl.getResources().getString(R.string.dialog_gotopage_number), new Object[] {
/* 168 */             Integer.valueOf(1), Integer.valueOf(this.mPageCount) });
/* 169 */       this.pageEditText.setHint(mHint);
/*     */     } 
/* 171 */     this.mSelectedRadioButton = this.externalRadioButton;
/* 172 */     this.mSelectedRadioButton.setChecked(true);
/*     */     
/* 174 */     int lastOption = PdfViewCtrlSettingsManager.getLinkEditLastOption(getContext());
/* 175 */     boolean isLinkingPage = (lastOption == 1);
/* 176 */     if (isLinkingPage) {
/* 177 */       this.urlEditText.clearFocus();
/* 178 */       this.pageEditText.requestFocus();
/*     */     } 
/*     */     
/* 181 */     setView(this.dialogView);
/*     */ 
/*     */     
/* 184 */     setButton(-1, this.mCtrl.getContext().getString(R.string.ok), this);
/*     */     
/* 186 */     setButton(-2, this.mCtrl.getContext().getString(R.string.cancel), this);
/*     */     
/* 188 */     this.mAnnots = new HashMap<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void show() {
/* 196 */     super.show();
/* 197 */     this.mPosButton = getButton(-1);
/* 198 */     this.mPosButton.setEnabled(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(View v) {
/* 206 */     if (v instanceof RadioButton && this.mSelectedRadioButton != v) {
/* 207 */       if (this.mSelectedRadioButton == this.internalRadioButton) {
/* 208 */         this.urlEditText.requestFocus();
/*     */       } else {
/* 210 */         this.pageEditText.requestFocus();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(DialogInterface dialog, int which) {
/* 220 */     switch (which) {
/*     */       case -1:
/* 222 */         if (this.mSelectedRadioButton == null || this.dialogView == null) {
/* 223 */           dismiss();
/*     */           return;
/*     */         } 
/* 226 */         if (this.mSelectedRadioButton == this.externalRadioButton && !this.urlEditText.getText().toString().equals("")) {
/* 227 */           PdfViewCtrlSettingsManager.setLinkEditLastOption(getContext(), 0);
/* 228 */           if (this.mLink == null) {
/* 229 */             createLink(this.urlEditText.getText().toString(), -1);
/*     */           } else {
/* 231 */             editLink(this.urlEditText.getText().toString(), -1);
/*     */           } 
/*     */         } 
/* 234 */         if (this.mSelectedRadioButton == this.internalRadioButton && !this.pageEditText.getText().toString().equals("")) {
/* 235 */           int pageNum = Integer.parseInt(this.pageEditText.getText().toString());
/* 236 */           if (pageNum > 0 && pageNum <= this.mCtrl.getPageCount()) {
/* 237 */             PdfViewCtrlSettingsManager.setLinkEditLastOption(getContext(), 1);
/* 238 */             if (this.mLink == null) {
/* 239 */               createLink("", pageNum);
/*     */             } else {
/* 241 */               editLink("", pageNum);
/*     */             } 
/*     */           } 
/*     */         } 
/* 245 */         dismiss();
/*     */         break;
/*     */       case -2:
/* 248 */         cancel();
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 258 */     super.dismiss();
/* 259 */     this.mTool.unsetAnnot();
/* 260 */     this.mCtrl.invalidate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFocusChange(View v, boolean hasFocus) {
/* 268 */     if (!hasFocus) {
/*     */       return;
/*     */     }
/* 271 */     if (v == this.urlEditText && this.mSelectedRadioButton != this.externalRadioButton) {
/* 272 */       this.mSelectedRadioButton.setChecked(false);
/* 273 */       this.mSelectedRadioButton = this.externalRadioButton;
/* 274 */     } else if (v == this.pageEditText) {
/* 275 */       this.mSelectedRadioButton.setChecked(false);
/* 276 */       this.mSelectedRadioButton = this.internalRadioButton;
/*     */     } 
/*     */     
/* 279 */     if (v instanceof EditText && this.mPosButton != null) {
/* 280 */       String txt = ((EditText)v).getText().toString();
/* 281 */       if (Utils.isNullOrEmpty(txt)) {
/* 282 */         this.mPosButton.setEnabled(false);
/* 283 */       } else if (v == this.pageEditText) {
/*     */         try {
/* 285 */           int page = Integer.parseInt(txt);
/* 286 */           if (page <= this.mPageCount && page > 0) {
/* 287 */             this.mPosButton.setEnabled(true);
/*     */           } else {
/* 289 */             this.mPosButton.setEnabled(false);
/*     */           } 
/* 291 */         } catch (NumberFormatException e) {
/* 292 */           this.mPosButton.setEnabled(false);
/*     */         } 
/*     */       } else {
/* 295 */         this.mPosButton.setEnabled(true);
/*     */       } 
/*     */     } 
/* 298 */     this.mSelectedRadioButton.setChecked(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setColor(int color) {
/* 307 */     this.mStrokeColor = color;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThickness(float thickness) {
/* 316 */     this.mThickness = thickness;
/*     */   }
/*     */   
/*     */   private void setStyle(Link linkMarkup) {
/*     */     try {
/* 321 */       ColorPt colorPt = Utils.color2ColorPt(this.mStrokeColor);
/* 322 */       linkMarkup.setColor(colorPt, 3);
/*     */       
/* 324 */       Annot.BorderStyle bs = linkMarkup.getBorderStyle();
/*     */       
/* 326 */       bs.setWidth(this.mThickness);
/* 327 */       linkMarkup.setBorderStyle(bs);
/* 328 */     } catch (PDFNetException e) {
/* 329 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/* 330 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createLink(String url, int page) {
/* 335 */     if (this.mSelRect == null || (url.equals("") && page == -1)) {
/*     */       return;
/*     */     }
/* 338 */     LinkedList<AnnotUpdateInfo> updateInfoList = new LinkedList<>();
/* 339 */     boolean shouldUnlock = false;
/*     */     
/*     */     try {
/* 342 */       this.mCtrl.docLock(true);
/* 343 */       shouldUnlock = true;
/* 344 */       for (Map.Entry<Rect, Integer> entry : this.mSelRect.entrySet()) {
/* 345 */         Link linkMarkup; Rect bbox = entry.getKey();
/* 346 */         int pg = ((Integer)entry.getValue()).intValue();
/*     */         
/* 348 */         if (page > -1) {
/* 349 */           Action gotoPage = Action.createGoto(Destination.createFitBH(this.mCtrl.getDoc().getPage(page), this.mCtrl.getDoc().getPage(page).getPageHeight()));
/* 350 */           linkMarkup = Link.create((Doc)this.mCtrl.getDoc().getSDFDoc(), bbox, gotoPage);
/*     */         } else {
/* 352 */           linkMarkup = Link.create((Doc)this.mCtrl.getDoc(), bbox, Action.createURI(this.mCtrl.getDoc(), url));
/*     */         } 
/* 354 */         setStyle(linkMarkup);
/*     */         
/* 356 */         linkMarkup.refreshAppearance();
/*     */         
/* 358 */         Page page1 = this.mCtrl.getDoc().getPage(pg);
/* 359 */         page1.annotPushBack((Annot)linkMarkup);
/* 360 */         this.mTool.mAnnotPushedBack = true;
/*     */         
/* 362 */         Obj obj = linkMarkup.getSDFObj();
/* 363 */         obj.putString("pdftron", "");
/*     */         
/* 365 */         this.mTool.setAnnot((Annot)linkMarkup, pg);
/* 366 */         this.mTool.buildAnnotBBox();
/*     */         
/* 368 */         RectF rectF = this.mTool.getAnnotRect();
/*     */         
/* 370 */         Rect ur = new Rect(rectF.left, rectF.top, rectF.right, rectF.bottom);
/* 371 */         updateInfoList.add(new AnnotUpdateInfo(linkMarkup, pg, ur));
/*     */       } 
/*     */       
/* 374 */       for (AnnotUpdateInfo updateInfo : updateInfoList) {
/* 375 */         if (!this.mCtrl.isAnnotationLayerEnabled()) {
/* 376 */           Rect rect = updateInfo.mRect;
/* 377 */           this.mCtrl.update(rect);
/*     */         } 
/* 379 */         if (updateInfo.mAnnot != null) {
/* 380 */           this.mAnnots.put(updateInfo.mAnnot, Integer.valueOf(updateInfo.mPageNum));
/* 381 */           this.mCtrl.update((Annot)updateInfo.mAnnot, updateInfo.mPageNum);
/*     */         } 
/*     */       } 
/* 384 */       this.mCtrl.docUnlock();
/* 385 */       shouldUnlock = false;
/* 386 */       this.mTool.raiseAnnotationAddedEvent(this.mAnnots);
/* 387 */     } catch (Exception e) {
/* 388 */       ((ToolManager)this.mCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/* 389 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 391 */       if (shouldUnlock) {
/* 392 */         this.mCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void editLink(String url, int page) {
/* 398 */     if (this.mLink == null || (url.equals("") && page == -1)) {
/*     */       return;
/*     */     }
/* 401 */     boolean shouldUnlock = false;
/*     */     try {
/* 403 */       this.mCtrl.docLock(true);
/* 404 */       shouldUnlock = true;
/*     */       
/* 406 */       if (page == -1 && !url.equals("")) {
/* 407 */         this.mLink.setAction(Action.createURI(this.mCtrl.getDoc(), url));
/*     */       }
/*     */       
/* 410 */       int pageCount = this.mCtrl.getPageCount();
/* 411 */       if (page > 0 && page <= pageCount) {
/* 412 */         Action gotoPage = Action.createGoto(Destination.createFitBH(this.mCtrl.getDoc().getPage(page), this.mCtrl.getDoc().getPage(page).getPageHeight()));
/* 413 */         this.mLink.setAction(gotoPage);
/*     */       } 
/* 415 */       if (this.mTool != null) {
/* 416 */         this.mLinkPage = this.mLink.getPage().getIndex();
/* 417 */         this.mTool.raiseAnnotationPreModifyEvent((Annot)this.mLink, this.mLinkPage);
/* 418 */         this.mCtrl.update((Annot)this.mLink, this.mLinkPage);
/* 419 */         this.mLink.refreshAppearance();
/*     */ 
/*     */         
/* 422 */         this.mTool.raiseAnnotationModifiedEvent((Annot)this.mLink, this.mLinkPage);
/* 423 */         this.mAnnots.put(this.mLink, Integer.valueOf(this.mLinkPage));
/*     */       } 
/* 425 */     } catch (Exception e) {
/* 426 */       ((ToolManager)this.mCtrl.getToolManager()).annotationCouldNotBeAdded(e.getMessage());
/* 427 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 429 */       if (shouldUnlock)
/* 430 */         this.mCtrl.docUnlock(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface LinkOption {}
/*     */   
/*     */   private class PageChangeListener
/*     */     implements TextWatcher {
/*     */     private PageChangeListener() {}
/*     */     
/*     */     public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */     
/*     */     public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 444 */       if (DialogLinkEditor.this.mPosButton == null) {
/*     */         return;
/*     */       }
/* 447 */       if (Utils.isNullOrEmpty(s.toString())) {
/* 448 */         DialogLinkEditor.this.mPosButton.setEnabled(false);
/*     */         return;
/*     */       } 
/*     */       try {
/* 452 */         int page = Integer.parseInt(s.toString().trim());
/* 453 */         if (page > 0 && page <= DialogLinkEditor.this.mPageCount) {
/* 454 */           DialogLinkEditor.this.mPosButton.setEnabled(true);
/*     */         } else {
/* 456 */           Log.d("DialogLinkEditor", "page greater");
/* 457 */           DialogLinkEditor.this.mPosButton.setEnabled(false);
/*     */         } 
/* 459 */       } catch (NumberFormatException e) {
/* 460 */         DialogLinkEditor.this.mPosButton.setEnabled(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void afterTextChanged(Editable s) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private class UrlChangeListener
/*     */     implements TextWatcher
/*     */   {
/*     */     private UrlChangeListener() {}
/*     */ 
/*     */     
/*     */     public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
/*     */ 
/*     */     
/*     */     public void onTextChanged(CharSequence s, int start, int before, int count) {
/* 479 */       if (DialogLinkEditor.this.mPosButton == null) {
/*     */         return;
/*     */       }
/* 482 */       if (Utils.isNullOrEmpty(s.toString())) {
/* 483 */         DialogLinkEditor.this.mPosButton.setEnabled(false);
/*     */         return;
/*     */       } 
/* 486 */       DialogLinkEditor.this.mPosButton.setEnabled(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void afterTextChanged(Editable s) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private class AnnotUpdateInfo
/*     */   {
/*     */     public Link mAnnot;
/*     */     public int mPageNum;
/*     */     Rect mRect;
/*     */     
/*     */     AnnotUpdateInfo(Link linkMarkup, int pageNum, Rect rect) {
/* 501 */       this.mAnnot = linkMarkup;
/* 502 */       this.mPageNum = pageNum;
/* 503 */       this.mRect = rect;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogLinkEditor.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */