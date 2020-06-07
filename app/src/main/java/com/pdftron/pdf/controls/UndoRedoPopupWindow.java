/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.PopupWindow;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.LayoutRes;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.tools.UndoRedoManager;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class UndoRedoPopupWindow
/*     */   extends PopupWindow
/*     */ {
/*     */   private TextView mUndoTextView;
/*     */   private TextView mRedoTextView;
/*     */   private UndoRedoManager mUndoRedoManager;
/*     */   private OnUndoRedoListener mOnUndoRedoListener;
/*     */   
/*     */   public UndoRedoPopupWindow(Context context, UndoRedoManager undoRedoManager, OnUndoRedoListener listener) {
/*  48 */     this(context, undoRedoManager, listener, 0);
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
/*     */   public UndoRedoPopupWindow(Context context, UndoRedoManager undoRedoManager, OnUndoRedoListener listener, int locationId) {
/*  61 */     this(context, undoRedoManager, listener, R.layout.dialog_undo_redo, locationId);
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
/*     */   protected UndoRedoPopupWindow(Context context, UndoRedoManager undoRedoManager, OnUndoRedoListener listener, @LayoutRes int layoutResource, final int locationId) {
/*  76 */     super(context);
/*     */     
/*  78 */     setOutsideTouchable(true);
/*  79 */     setFocusable(false);
/*     */     
/*  81 */     setAnimationStyle(R.style.Controls_AnnotationPopupAnimation);
/*  82 */     this.mUndoRedoManager = undoRedoManager;
/*  83 */     this.mOnUndoRedoListener = listener;
/*     */     
/*  85 */     View rootView = LayoutInflater.from(context).inflate(layoutResource, null);
/*  86 */     setContentView(rootView);
/*     */     
/*  88 */     this.mUndoTextView = (TextView)rootView.findViewById(R.id.undo_title);
/*  89 */     if (!undoRedoEnabled()) {
/*  90 */       this.mUndoTextView.setVisibility(8);
/*     */     }
/*  92 */     this.mUndoTextView.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  95 */             if (UndoRedoPopupWindow.this.mUndoRedoManager != null && UndoRedoPopupWindow.this.mUndoRedoManager.getPdfViewCtrl() != null) {
/*  96 */               PDFViewCtrl pdfViewCtrl = UndoRedoPopupWindow.this.mUndoRedoManager.getPdfViewCtrl();
/*  97 */               String undoInfo = UndoRedoPopupWindow.this.mUndoRedoManager.undo(locationId, false);
/*  98 */               UndoRedoManager.jumpToUndoRedo(pdfViewCtrl, undoInfo, true);
/*  99 */               UndoRedoPopupWindow.this.updateUndoRedo();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 104 */     this.mRedoTextView = (TextView)rootView.findViewById(R.id.redo_title);
/* 105 */     if (!undoRedoEnabled()) {
/* 106 */       this.mRedoTextView.setVisibility(8);
/*     */     }
/* 108 */     this.mRedoTextView.setOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/* 111 */             if (UndoRedoPopupWindow.this.mUndoRedoManager != null && UndoRedoPopupWindow.this.mUndoRedoManager.getPdfViewCtrl() != null) {
/* 112 */               PDFViewCtrl pdfViewCtrl = UndoRedoPopupWindow.this.mUndoRedoManager.getPdfViewCtrl();
/* 113 */               String redoInfo = UndoRedoPopupWindow.this.mUndoRedoManager.redo(locationId, false);
/* 114 */               UndoRedoManager.jumpToUndoRedo(pdfViewCtrl, redoInfo, false);
/* 115 */               UndoRedoPopupWindow.this.updateUndoRedo();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 120 */     updateUndoRedo();
/*     */   }
/*     */   
/*     */   private void updateUndoRedo() {
/* 124 */     if (undoRedoEnabled()) {
/* 125 */       if (this.mUndoTextView != null) {
/* 126 */         String nextUndoAction = this.mUndoRedoManager.getNextUndoAction();
/* 127 */         if (!Utils.isNullOrEmpty(nextUndoAction)) {
/* 128 */           this.mUndoTextView.setEnabled(true);
/* 129 */           this.mUndoTextView.setText(nextUndoAction);
/*     */         } else {
/* 131 */           this.mUndoTextView.setEnabled(false);
/* 132 */           this.mUndoTextView.setText(R.string.undo);
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       if (this.mRedoTextView != null) {
/* 137 */         String nextRedoAction = this.mUndoRedoManager.getNextRedoAction();
/* 138 */         if (!Utils.isNullOrEmpty(nextRedoAction)) {
/* 139 */           this.mRedoTextView.setEnabled(true);
/* 140 */           this.mRedoTextView.setText(nextRedoAction);
/*     */         } else {
/* 142 */           this.mRedoTextView.setEnabled(false);
/* 143 */           this.mRedoTextView.setText(R.string.redo);
/*     */         } 
/*     */       } 
/*     */       
/* 147 */       setWidth(-2);
/* 148 */       setHeight(-2);
/*     */       
/* 150 */       if (this.mOnUndoRedoListener != null) {
/* 151 */         this.mOnUndoRedoListener.onUndoRedoCalled();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean undoRedoEnabled() {
/* 157 */     return (this.mUndoRedoManager != null && this.mOnUndoRedoListener != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 162 */     super.dismiss();
/* 163 */     this.mUndoRedoManager.sendConsecutiveUndoRedoEvent();
/*     */   }
/*     */   
/*     */   public static interface OnUndoRedoListener {
/*     */     void onUndoRedoCalled();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\UndoRedoPopupWindow.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */