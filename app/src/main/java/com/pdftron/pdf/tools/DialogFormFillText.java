/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.text.InputFilter;
/*     */ import android.text.method.PasswordTransformationMethod;
/*     */ import android.text.method.TransformationMethod;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.widget.EditText;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Field;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.ViewChangeCollection;
/*     */ import com.pdftron.pdf.annots.Widget;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import java.util.HashMap;
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
/*     */ public class DialogFormFillText
/*     */   extends AlertDialog
/*     */ {
/*     */   private PDFViewCtrl mCtrl;
/*     */   private Annot mAnnot;
/*     */   private int mAnnotPageNum;
/*     */   private Field mField;
/*     */   private EditText mTextBox;
/*     */   
/*     */   public DialogFormFillText(@NonNull PDFViewCtrl ctrl, @NonNull Annot annot, int annotPageNum) {
/*  51 */     super(ctrl.getContext());
/*  52 */     this.mCtrl = ctrl;
/*  53 */     this.mAnnot = annot;
/*  54 */     this.mAnnotPageNum = annotPageNum;
/*  55 */     this.mField = null;
/*     */     try {
/*  57 */       Widget w = new Widget(this.mAnnot);
/*  58 */       this.mField = w.getField();
/*  59 */       if (!this.mField.isValid()) {
/*  60 */         dismiss();
/*     */         return;
/*     */       } 
/*  63 */     } catch (Exception e) {
/*  64 */       dismiss();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  69 */     LayoutInflater inflater = (LayoutInflater)this.mCtrl.getContext().getSystemService("layout_inflater");
/*  70 */     View view = inflater.inflate(R.layout.tools_dialog_formfilltext, null);
/*  71 */     this.mTextBox = (EditText)view.findViewById(R.id.tools_dialog_formfilltext_edit_text);
/*  72 */     setTitle(this.mCtrl.getContext().getString(R.string.tools_dialog_formfilltext_title));
/*  73 */     setView(view);
/*     */ 
/*     */     
/*     */     try {
/*  77 */       boolean multiple_line = this.mField.getFlag(7);
/*  78 */       this.mTextBox.setSingleLine(!multiple_line);
/*  79 */       int just = this.mField.getJustification();
/*  80 */       if (just == 0) {
/*  81 */         this.mTextBox.setGravity(19);
/*  82 */       } else if (just == 1) {
/*  83 */         this.mTextBox.setGravity(17);
/*  84 */       } else if (just == 2) {
/*  85 */         this.mTextBox.setGravity(21);
/*     */       } 
/*     */ 
/*     */       
/*  89 */       if (this.mField.getFlag(8)) {
/*  90 */         this.mTextBox.setTransformationMethod((TransformationMethod)new PasswordTransformationMethod());
/*     */       }
/*     */ 
/*     */       
/*  94 */       String str = this.mField.getValueAsString();
/*  95 */       this.mTextBox.setText(str);
/*     */       
/*  97 */       this.mTextBox.setSelection(this.mTextBox.getText().length());
/*     */ 
/*     */       
/* 100 */       int max_len = this.mField.getMaxLen();
/* 101 */       if (max_len >= 0) {
/* 102 */         InputFilter.LengthFilter[] filters = new InputFilter.LengthFilter[1];
/* 103 */         filters[0] = new InputFilter.LengthFilter(max_len);
/* 104 */         this.mTextBox.setFilters((InputFilter[])filters);
/*     */       }
/*     */     
/* 107 */     } catch (Exception e) {
/* 108 */       dismiss();
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 113 */     setButton(-1, this.mCtrl.getContext().getString(R.string.ok), new OnClickListener() {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 115 */             String str = DialogFormFillText.this.mTextBox.getText().toString();
/* 116 */             boolean shouldUnlock = false;
/*     */             try {
/* 118 */               DialogFormFillText.this.mCtrl.docLock(true);
/* 119 */               shouldUnlock = true;
/* 120 */               DialogFormFillText.this.raiseAnnotationPreEditEvent(DialogFormFillText.this.mAnnot);
/* 121 */               ViewChangeCollection view_change = DialogFormFillText.this.mField.setValue(str);
/* 122 */               DialogFormFillText.this.mCtrl.refreshAndUpdate(view_change);
/* 123 */               DialogFormFillText.this.raiseAnnotationEditedEvent(DialogFormFillText.this.mAnnot);
/* 124 */             } catch (Exception e) {
/* 125 */               AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */             } finally {
/* 127 */               if (shouldUnlock) {
/* 128 */                 DialogFormFillText.this.mCtrl.docUnlock();
/*     */               }
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 134 */     setButton(-2, this.mCtrl.getContext().getString(R.string.cancel), new OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void raiseAnnotationPreEditEvent(Annot annot) {
/* 142 */     ToolManager toolManager = (ToolManager)this.mCtrl.getToolManager();
/* 143 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 144 */     annots.put(annot, Integer.valueOf(this.mAnnotPageNum));
/* 145 */     toolManager.raiseAnnotationsPreModifyEvent(annots);
/*     */   }
/*     */   
/*     */   private void raiseAnnotationEditedEvent(Annot annot) {
/* 149 */     ToolManager toolManager = (ToolManager)this.mCtrl.getToolManager();
/* 150 */     HashMap<Annot, Integer> annots = new HashMap<>(1);
/* 151 */     annots.put(annot, Integer.valueOf(this.mAnnotPageNum));
/* 152 */     Bundle bundle = Tool.getAnnotationModificationBundle(null);
/* 153 */     toolManager.raiseAnnotationsModifiedEvent(annots, bundle);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\DialogFormFillText.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */