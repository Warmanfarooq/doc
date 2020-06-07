/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import androidx.annotation.Keep;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.common.PDFNetException;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.Rect;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.annots.RubberStamp;
/*     */ import com.pdftron.pdf.controls.RubberStampDialogFragment;
/*     */ import com.pdftron.pdf.interfaces.OnDialogDismissListener;
/*     */ import com.pdftron.pdf.interfaces.OnRubberStampSelectedListener;
/*     */ import com.pdftron.pdf.model.CustomStampPreviewAppearance;
/*     */ import com.pdftron.pdf.model.StandardStampPreviewAppearance;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnnotUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import com.pdftron.sdf.Doc;
/*     */ import com.pdftron.sdf.Obj;
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
/*     */ public class RubberStampCreate
/*     */   extends Stamper
/*     */ {
/*  40 */   private CustomStampPreviewAppearance[] mCustomStampPreviewAppearances = new CustomStampPreviewAppearance[] { new CustomStampPreviewAppearance("green", -722706, -2168872, -2826036, -14254336, -13743343, 0.85D), new CustomStampPreviewAppearance("red", -332824, -76074, -13879, -6550012, -6550012, 0.85D), new CustomStampPreviewAppearance("blue", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D), new CustomStampPreviewAppearance("dark yellow", -264278, -462763, -1713655, -12633086, -3101394, 1.0D), new CustomStampPreviewAppearance("dark_purple", -3752218, -7438387, -7833398, -15199697, -12504446, 1.0D), new CustomStampPreviewAppearance("dark_red", -2459033, -3191243, -2792885, -14020855, -9568251, 1.0D) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  48 */   private StandardStampPreviewAppearance[] mStandardStampPreviewAppearance = new StandardStampPreviewAppearance[] { new StandardStampPreviewAppearance("APPROVED", new CustomStampPreviewAppearance("", -722706, -2168872, -2826036, -14254336, -13743343, 0.85D)), new StandardStampPreviewAppearance("AS IS", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("COMPLETED", new CustomStampPreviewAppearance("", -722706, -2168872, -2826036, -14254336, -13743343, 0.85D)), new StandardStampPreviewAppearance("CONFIDENTIAL", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("DEPARTMENTAL", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("DRAFT", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("EXPERIMENTAL", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("EXPIRED", new CustomStampPreviewAppearance("", -332824, -76074, -13879, -6550012, -6550012, 0.85D)), new StandardStampPreviewAppearance("FINAL", new CustomStampPreviewAppearance("", -722706, -2168872, -2826036, -14254336, -13743343, 0.85D)), new StandardStampPreviewAppearance("FOR COMMENT", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("FOR PUBLIC RELEASE", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("INFORMATION ONLY", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("NOT APPROVED", new CustomStampPreviewAppearance("", -332824, -76074, -13879, -6550012, -6550012, 0.85D)), new StandardStampPreviewAppearance("NOT FOR PUBLIC RELEASE", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("PRELIMINARY RESULTS", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("SOLD", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("TOP SECRET", new CustomStampPreviewAppearance("", -1051654, -2037514, -5849627, -13750128, -13750128, 0.85D)), new StandardStampPreviewAppearance("VOID", new CustomStampPreviewAppearance("", -332824, -76074, -13879, -6550012, -6550012, 0.85D)), new StandardStampPreviewAppearance("SIGN HERE", new CustomStampPreviewAppearance("", -2459033, -3191243, -2792885, -14020855, -9568251, 1.0D), true, false), new StandardStampPreviewAppearance("WITNESS", new CustomStampPreviewAppearance("", -264278, -462763, -1713655, -12633086, -3101394, 1.0D), true, false), new StandardStampPreviewAppearance("INITIAL HERE", new CustomStampPreviewAppearance("", -3752218, -7438387, -7833398, -15199697, -12504446, 1.0D), true, false), new StandardStampPreviewAppearance("CHECK_MARK"), new StandardStampPreviewAppearance("CROSS_MARK") };
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
/*     */   private String mStampLabel;
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
/*     */   public RubberStampCreate(@NonNull PDFViewCtrl ctrl) {
/*  80 */     super(ctrl);
/*  81 */     this.mNextToolMode = getToolMode();
/*     */     
/*  83 */     FragmentActivity activity = ((ToolManager)ctrl.getToolManager()).getCurrentActivity();
/*  84 */     if (activity != null) {
/*  85 */       Fragment fragment = activity.getSupportFragmentManager().findFragmentByTag(RubberStampDialogFragment.TAG);
/*  86 */       if (fragment instanceof RubberStampDialogFragment) {
/*  87 */         setRubberStampDialogFragmentListeners((RubberStampDialogFragment)fragment);
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
/*     */   
/*     */   public void setCustomStampAppearance(@Nullable StandardStampPreviewAppearance[] standardStampPreviewAppearance, @Nullable CustomStampPreviewAppearance[] customStampPreviewAppearance) {
/* 100 */     if (standardStampPreviewAppearance != null) {
/* 101 */       this.mStandardStampPreviewAppearance = standardStampPreviewAppearance;
/*     */     }
/* 103 */     if (customStampPreviewAppearance != null) {
/* 104 */       this.mCustomStampPreviewAppearances = customStampPreviewAppearance;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStampName(String stampLabel) {
/* 113 */     this.mStampLabel = stampLabel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ToolManager.ToolModeBase getToolMode() {
/* 121 */     return ToolManager.ToolMode.RUBBER_STAMPER;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCreateAnnotType() {
/* 126 */     return 12;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addStamp() {
/* 134 */     if (this.mTargetPoint == null) {
/* 135 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("target point is not specified."));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 140 */     if (this.mPdfViewCtrl == null) {
/*     */       return;
/*     */     }
/*     */     
/* 144 */     ToolManager toolManager = (ToolManager)this.mPdfViewCtrl.getToolManager();
/* 145 */     FragmentActivity activity = toolManager.getCurrentActivity();
/* 146 */     if (activity == null) {
/*     */       return;
/*     */     }
/*     */     
/* 150 */     if (this.mStampLabel != null) {
/*     */       
/* 152 */       createStandardRubberStamp(this.mStampLabel);
/*     */       
/* 154 */       clearTargetPoint();
/* 155 */       safeSetNextToolMode();
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     RubberStampDialogFragment fragment = RubberStampDialogFragment.newInstance(this.mTargetPoint, this.mStandardStampPreviewAppearance, this.mCustomStampPreviewAppearances);
/* 160 */     fragment.setStyle(0, R.style.CustomAppTheme);
/* 161 */     fragment.show(activity.getSupportFragmentManager(), RubberStampDialogFragment.TAG);
/* 162 */     setRubberStampDialogFragmentListeners(fragment);
/*     */   }
/*     */   
/*     */   private void setRubberStampDialogFragmentListeners(@NonNull final RubberStampDialogFragment fragment) {
/* 166 */     fragment.setOnRubberStampSelectedListener(new OnRubberStampSelectedListener()
/*     */         {
/*     */           public void onRubberStampSelected(@NonNull String stampLabel) {
/* 169 */             RubberStampCreate.this.mTargetPoint = fragment.getTargetPoint();
/* 170 */             if (!Utils.isNullOrEmpty(stampLabel) && RubberStampCreate.this.mTargetPoint != null) {
/* 171 */               RubberStampCreate.this.createStandardRubberStamp(stampLabel);
/*     */             }
/*     */           }
/*     */ 
/*     */           
/*     */           public void onRubberStampSelected(@Nullable Obj stampObj) {
/* 177 */             RubberStampCreate.this.mTargetPoint = fragment.getTargetPoint();
/* 178 */             if (stampObj != null && RubberStampCreate.this.mTargetPoint != null) {
/* 179 */               RubberStampCreate.this.createCustomStamp(stampObj);
/*     */             }
/*     */           }
/*     */         });
/*     */     
/* 184 */     fragment.setOnDialogDismissListener(new OnDialogDismissListener()
/*     */         {
/*     */           
/*     */           public void onDialogDismiss()
/*     */           {
/* 189 */             RubberStampCreate.this.clearTargetPoint();
/* 190 */             RubberStampCreate.this.safeSetNextToolMode();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void createStandardRubberStamp(@NonNull String stampName) {
/* 196 */     if (this.mTargetPoint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 200 */     boolean shouldUnlock = false;
/*     */     try {
/* 202 */       this.mPdfViewCtrl.docLock(true);
/* 203 */       shouldUnlock = true;
/*     */       
/* 205 */       int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(this.mTargetPoint.x, this.mTargetPoint.y);
/* 206 */       if (pageNum < 1) {
/*     */         return;
/*     */       }
/*     */       
/* 210 */       double[] size = AnnotUtils.getStampSize(this.mPdfViewCtrl.getContext(), stampName);
/* 211 */       if (size == null) {
/*     */         return;
/*     */       }
/* 214 */       int width = (int)(size[0] + 0.5D);
/* 215 */       int height = (int)(size[1] + 0.5D);
/* 216 */       double[] pageTarget = this.mPdfViewCtrl.convScreenPtToPagePt(this.mTargetPoint.x, this.mTargetPoint.y, pageNum);
/* 217 */       Rect rect = new Rect(pageTarget[0] - (width / 2), pageTarget[1] - (height / 2), pageTarget[0] + (width / 2), pageTarget[1] + (height / 2));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 222 */       Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/* 223 */       boundToCropBox(page, rect);
/* 224 */       RubberStamp stamp = RubberStamp.create((Doc)this.mPdfViewCtrl.getDoc(), rect);
/* 225 */       stamp.setIcon(stampName);
/* 226 */       AnnotUtils.refreshAnnotAppearance(this.mPdfViewCtrl.getContext(), (Annot)stamp);
/* 227 */       setAuthor((Markup)stamp);
/*     */       
/* 229 */       page.annotPushBack((Annot)stamp);
/*     */       
/* 231 */       this.mPdfViewCtrl.update((Annot)stamp, pageNum);
/* 232 */       raiseAnnotationAddedEvent((Annot)stamp, pageNum);
/* 233 */     } catch (Exception e) {
/* 234 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 236 */       if (shouldUnlock) {
/* 237 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void createCustomStamp(@NonNull Obj stampObj) {
/* 243 */     if (this.mTargetPoint == null) {
/*     */       return;
/*     */     }
/*     */     
/* 247 */     boolean shouldUnlock = false;
/*     */     try {
/* 249 */       this.mPdfViewCtrl.docLock(true);
/* 250 */       shouldUnlock = true;
/*     */       
/* 252 */       int pageNum = this.mPdfViewCtrl.getPageNumberFromScreenPt(this.mTargetPoint.x, this.mTargetPoint.y);
/* 253 */       if (pageNum < 1) {
/*     */         return;
/*     */       }
/*     */       
/* 257 */       Rect rubberRect = getCustomRubberRect(stampObj);
/* 258 */       int width = (int)(rubberRect.getWidth() + 0.5D);
/* 259 */       int height = (int)(rubberRect.getHeight() + 0.5D);
/* 260 */       double[] pageTarget = this.mPdfViewCtrl.convScreenPtToPagePt(this.mTargetPoint.x, this.mTargetPoint.y, pageNum);
/* 261 */       Rect rect = new Rect(pageTarget[0] - (width / 2), pageTarget[1] - (height / 2), pageTarget[0] + (width / 2), pageTarget[1] + (height / 2));
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 266 */       Page page = this.mPdfViewCtrl.getDoc().getPage(pageNum);
/* 267 */       boundToCropBox(page, rect);
/* 268 */       RubberStamp stamp = RubberStamp.createCustom((Doc)this.mPdfViewCtrl.getDoc(), rect, stampObj);
/* 269 */       setAuthor((Markup)stamp);
/*     */       
/* 271 */       page.annotPushBack((Annot)stamp);
/*     */       
/* 273 */       this.mPdfViewCtrl.update((Annot)stamp, pageNum);
/* 274 */       raiseAnnotationAddedEvent((Annot)stamp, pageNum);
/* 275 */     } catch (Exception e) {
/* 276 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 278 */       if (shouldUnlock) {
/* 279 */         this.mPdfViewCtrl.docUnlock();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private Rect getCustomRubberRect(@NonNull Obj stampObj) throws PDFNetException {
/* 285 */     PDFDoc tempDoc = null;
/*     */     try {
/* 287 */       tempDoc = new PDFDoc();
/* 288 */       tempDoc.initSecurityHandler();
/* 289 */       RubberStamp rubberStamp = RubberStamp.createCustom((Doc)tempDoc, new Rect(), stampObj);
/* 290 */       return rubberStamp.getRect();
/*     */     } finally {
/* 292 */       Utils.closeQuietly(tempDoc);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void boundToCropBox(@NonNull Page page, @NonNull Rect rect) throws PDFNetException {
/* 297 */     Rect cropBox = page.getBox(this.mPdfViewCtrl.getPageBox());
/* 298 */     cropBox.normalize();
/*     */     
/* 300 */     double width = rect.getWidth();
/* 301 */     double height = rect.getHeight();
/* 302 */     if (rect.getX1() < cropBox.getX1()) {
/* 303 */       rect.setX1(cropBox.getX1());
/* 304 */       rect.setX2(cropBox.getX1() + width);
/*     */     } 
/* 306 */     if (rect.getX2() > cropBox.getX2()) {
/* 307 */       rect.setX2(cropBox.getX2());
/* 308 */       rect.setX1(cropBox.getX2() - width);
/*     */     } 
/* 310 */     if (rect.getY1() < cropBox.getY1()) {
/* 311 */       rect.setY1(cropBox.getY1());
/* 312 */       rect.setY2(cropBox.getY1() + height);
/*     */     } 
/* 314 */     if (rect.getY2() > cropBox.getY2()) {
/* 315 */       rect.setY2(cropBox.getY2());
/* 316 */       rect.setY1(cropBox.getY2() - height);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\RubberStampCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */