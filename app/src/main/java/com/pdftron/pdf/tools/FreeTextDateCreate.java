/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.SharedPreferences;
/*     */ import android.view.MotionEvent;
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import org.json.JSONException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Keep
/*     */ public class FreeTextDateCreate
/*     */   extends FreeTextCreate
/*     */ {
/*     */   private String mDateFormat;
/*     */   
/*     */   public FreeTextDateCreate(@NonNull PDFViewCtrl ctrl) {
/*  27 */     super(ctrl);
/*     */   }
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/*  32 */     return ToolManager.ToolMode.FREE_TEXT_DATE_CREATE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/*  37 */     return 1011;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupAnnotProperty(AnnotStyle annotStyle) {
/*  42 */     super.setupAnnotProperty(annotStyle);
/*     */     
/*  44 */     this.mDateFormat = annotStyle.getDateFormat();
/*     */     
/*  46 */     SharedPreferences settings = Tool.getToolPreferences(this.mPdfViewCtrl.getContext());
/*  47 */     SharedPreferences.Editor editor = settings.edit();
/*  48 */     editor.putString(getDateFormatKey(getCreateAnnotType()), this.mDateFormat);
/*  49 */     editor.apply();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initTextStyle() {
/*  54 */     super.initTextStyle();
/*     */     
/*  56 */     Context context = this.mPdfViewCtrl.getContext();
/*  57 */     SharedPreferences settings = Tool.getToolPreferences(context);
/*  58 */     this.mDateFormat = settings.getString(getDateFormatKey(getCreateAnnotType()), ToolStyleConfig.getInstance().getDefaultDateFormat(context, getCreateAnnotType()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onUp(MotionEvent e, PDFViewCtrl.PriorEventMode priorEventMode) {
/*  63 */     if (this.mOnUpOccurred) {
/*  64 */       return false;
/*     */     }
/*  66 */     this.mOnUpOccurred = true;
/*     */ 
/*     */     
/*  69 */     if (this.mAllowTwoFingerScroll) {
/*  70 */       doneTwoFingerScrolling();
/*  71 */       return false;
/*     */     } 
/*     */     
/*  74 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/*     */ 
/*     */     
/*  77 */     if (toolManager.isQuickMenuJustClosed()) {
/*  78 */       return true;
/*     */     }
/*     */     
/*  81 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.PAGE_SLIDING) {
/*  82 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  86 */     if (priorEventMode == PDFViewCtrl.PriorEventMode.FLING || priorEventMode == PDFViewCtrl.PriorEventMode.PINCH)
/*     */     {
/*     */       
/*  89 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  94 */     if (this.mAnnotPushedBack && this.mForceSameNextToolMode) {
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     if (this.mPageNum >= 1) {
/*     */ 
/*     */ 
/*     */       
/* 102 */       Annot tappedAnnot = didTapOnSameTypeAnnot(e);
/* 103 */       int x = (int)e.getX();
/* 104 */       int y = (int)e.getY();
/* 105 */       int page = this.mPdfViewCtrl.getPageNumberFromScreenPt(x, y);
/* 106 */       if (tappedAnnot != null) {
/*     */         
/* 108 */         setCurrentDefaultToolModeHelper(getToolMode());
/* 109 */         toolManager.selectAnnot(tappedAnnot, page);
/*     */       }
/* 111 */       else if (this.mDateFormat != null) {
/* 112 */         String dateStr = AnnotUtils.getCurrentTime(this.mDateFormat);
/* 113 */         commitFreeTextImpl(dateStr, true);
/* 114 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createAnnot(String contents) throws PDFNetException, JSONException {
/* 123 */     super.createAnnot(contents);
/*     */ 
/*     */     
/* 126 */     if (this.mAnnot != null)
/* 127 */       this.mAnnot.setCustomData(AnnotUtils.KEY_FreeTextDate, this.mDateFormat); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\FreeTextDateCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */