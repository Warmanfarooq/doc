/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.View;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.ColorPt;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.annots.Markup;
/*     */ import com.pdftron.pdf.config.ToolStyleConfig;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import com.pdftron.pdf.model.RulerItem;
/*     */ import com.pdftron.pdf.tools.AdvancedShapeCreate;
/*     */ import com.pdftron.pdf.tools.Eraser;
/*     */ import com.pdftron.pdf.tools.FreehandCreate;
/*     */ import com.pdftron.pdf.tools.Tool;
/*     */ import com.pdftron.pdf.tools.ToolManager;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.PressureInkUtils;
/*     */ import com.pdftron.pdf.utils.Utils;
/*     */ import java.lang.ref.WeakReference;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EditToolbarImpl
/*     */   implements OnToolbarStateUpdateListener, OnToolSelectedListener, EditToolbar.OnEditToolbarChangedListener
/*     */ {
/*     */   private static final String INK_TAG_1 = "ink_tag_1";
/*     */   private static final String INK_TAG_2 = "ink_tag_2";
/*     */   private static final String INK_TAG_3 = "ink_tag_3";
/*     */   private static final String INK_TAG_4 = "ink_tag_4";
/*     */   private static final String INK_TAG_5 = "ink_tag_5";
/*     */   private WeakReference<FragmentActivity> mActivityRef;
/*     */   private EditToolbar mEditToolbar;
/*     */   private ToolManager mToolManager;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private ToolManager.ToolMode mStartToolMode;
/*  63 */   private ArrayList<AnnotStyle> mDrawStyles = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AnnotStyle mEraserStyle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mIsStyleFixed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private OnEditToolbarListener mOnEditToolbarListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EditToolbarImpl(@NonNull FragmentActivity activity, @NonNull EditToolbar editToolbar, @NonNull ToolManager toolManager, @NonNull ToolManager.ToolMode toolMode, @Nullable Annot editAnnot, int pageNumber, boolean shouldExpand) {
/*  99 */     this.mActivityRef = new WeakReference<>(activity);
/* 100 */     this.mEditToolbar = editToolbar;
/* 101 */     this.mToolManager = toolManager;
/* 102 */     this.mPdfViewCtrl = this.mToolManager.getPDFViewCtrl();
/* 103 */     this.mStartToolMode = toolMode;
/*     */     
/* 105 */     this.mEditToolbar.setVisibility(8);
/*     */     
/* 107 */     boolean hasEraseBtn = false;
/* 108 */     boolean isInkEditing = false;
/* 109 */     initTool(toolMode);
/* 110 */     if (toolMode == ToolManager.ToolMode.INK_CREATE) {
/* 111 */       FreehandCreate tool = (FreehandCreate)this.mToolManager.getTool();
/* 112 */       tool.commitAnnotation();
/* 113 */       if (editAnnot != null) {
/* 114 */         this.mIsStyleFixed = true;
/* 115 */         this.mDrawStyles.add(getAnnotStyleFromAnnot(editAnnot));
/* 116 */         tool.setTimedModeEnabled(false);
/* 117 */         isInkEditing = true;
/*     */       } else {
/* 119 */         tool.setTimedModeEnabled(true);
/* 120 */         for (int i = 0; i < 5; i++) {
/* 121 */           AnnotStyle annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 14, getInkTag(i));
/* 122 */           this.mDrawStyles.add(annotStyle);
/*     */         } 
/*     */       } 
/* 125 */       this.mEraserStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 1003, "");
/* 126 */       hasEraseBtn = true;
/*     */       
/* 128 */       tool.setOnToolbarStateUpdateListener(this);
/* 129 */     } else if (toolMode == ToolManager.ToolMode.POLYLINE_CREATE || toolMode == ToolManager.ToolMode.POLYGON_CREATE || toolMode == ToolManager.ToolMode.CLOUD_CREATE || toolMode == ToolManager.ToolMode.PERIMETER_MEASURE_CREATE || toolMode == ToolManager.ToolMode.AREA_MEASURE_CREATE) {
/*     */       AnnotStyle annotStyle;
/*     */       
/* 132 */       switch (toolMode) {
/*     */         case PERIMETER_MEASURE_CREATE:
/* 134 */           annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 1008, "");
/*     */           break;
/*     */         case AREA_MEASURE_CREATE:
/* 137 */           annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 1009, "");
/*     */           break;
/*     */         case POLYLINE_CREATE:
/* 140 */           annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 7, "");
/*     */           break;
/*     */         case POLYGON_CREATE:
/* 143 */           annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 6, "");
/*     */           break;
/*     */         
/*     */         default:
/* 147 */           annotStyle = ToolStyleConfig.getInstance().getCustomAnnotStyle((Context)activity, 1005, "");
/*     */           break;
/*     */       } 
/* 150 */       annotStyle.setSnap(this.mToolManager.isSnappingEnabledForMeasurementTools());
/* 151 */       this.mDrawStyles.add(annotStyle);
/* 152 */       ((AdvancedShapeCreate)this.mToolManager.getTool()).setOnToolbarStateUpdateListener(this);
/*     */     } 
/*     */     
/* 155 */     this.mEditToolbar.setup(this.mPdfViewCtrl, this, this.mDrawStyles, true, hasEraseBtn, true, shouldExpand, this.mIsStyleFixed);
/*     */     
/* 157 */     this.mEditToolbar.setOnEditToolbarChangeListener(this);
/* 158 */     updateToolbarControlButtons();
/* 159 */     if (!this.mDrawStyles.isEmpty()) {
/* 160 */       updateAnnotProperties(this.mDrawStyles.get(0));
/*     */     }
/* 162 */     if (isInkEditing) {
/* 163 */       FreehandCreate tool = (FreehandCreate)this.mToolManager.getTool();
/* 164 */       tool.setInitInkItem(editAnnot, pageNumber);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showToolbar() {
/* 173 */     this.mEditToolbar.show();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isToolbarShown() {
/* 181 */     return this.mEditToolbar.isShown();
/*     */   }
/*     */   
/*     */   private boolean startWith(ToolManager.ToolMode toolMode) {
/* 185 */     if (this.mToolManager == null) {
/* 186 */       return false;
/*     */     }
/* 188 */     if (this.mStartToolMode == toolMode) {
/* 189 */       if (this.mStartToolMode != this.mToolManager.getTool().getToolMode()) {
/* 190 */         initTool(this.mStartToolMode);
/*     */       }
/* 192 */       return true;
/*     */     } 
/*     */     
/* 195 */     return false;
/*     */   }
/*     */   
/*     */   private boolean startWithClickBasedAnnot() {
/* 199 */     if (this.mToolManager == null) {
/* 200 */       return false;
/*     */     }
/* 202 */     if (this.mStartToolMode == ToolManager.ToolMode.POLYLINE_CREATE || this.mStartToolMode == ToolManager.ToolMode.POLYGON_CREATE || this.mStartToolMode == ToolManager.ToolMode.CLOUD_CREATE || this.mStartToolMode == ToolManager.ToolMode.PERIMETER_MEASURE_CREATE || this.mStartToolMode == ToolManager.ToolMode.AREA_MEASURE_CREATE) {
/*     */       
/* 204 */       if (this.mStartToolMode != this.mToolManager.getTool().getToolMode()) {
/* 205 */         initTool(this.mStartToolMode);
/*     */       }
/* 207 */       return true;
/*     */     } 
/*     */     
/* 210 */     return false;
/*     */   }
/*     */   
/*     */   private AnnotStyle getAnnotStyleFromAnnot(Annot annot) {
/* 214 */     if (this.mToolManager == null || this.mPdfViewCtrl == null) {
/* 215 */       return null;
/*     */     }
/*     */     
/* 218 */     boolean shouldUnlockRead = false;
/*     */     try {
/* 220 */       this.mPdfViewCtrl.docLockRead();
/* 221 */       shouldUnlockRead = true;
/*     */       
/* 223 */       ColorPt colorPt = annot.getColorAsRGB();
/* 224 */       int color = Utils.colorPt2color(colorPt);
/*     */       
/* 226 */       boolean isPressure = PressureInkUtils.isPressureSensitive(annot);
/*     */ 
/*     */       
/* 229 */       Markup m = new Markup(annot);
/* 230 */       float opacity = (float)m.getOpacity();
/*     */ 
/*     */       
/* 233 */       float thickness = (float)annot.getBorderStyle().getWidth();
/*     */       
/* 235 */       AnnotStyle annotStyle = new AnnotStyle();
/* 236 */       annotStyle.setAnnotType(annot.getType());
/*     */       
/* 238 */       annotStyle.setStyle(color, 0, thickness, opacity);
/* 239 */       annotStyle.setPressureSensitivity(isPressure);
/* 240 */       return annotStyle;
/* 241 */     } catch (Exception e) {
/* 242 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */     } finally {
/* 244 */       if (shouldUnlockRead) {
/* 245 */         this.mPdfViewCtrl.docUnlockRead();
/*     */       }
/*     */     } 
/* 248 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDrawSelected(int drawIndex, boolean wasSelectedBefore, View anchor) {
/* 258 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 262 */     AnnotStyle annotStyle = this.mDrawStyles.get(drawIndex);
/* 263 */     if (annotStyle != null) {
/* 264 */       if (!this.mIsStyleFixed && wasSelectedBefore) {
/* 265 */         annotStyle.setSnap(this.mToolManager.isSnappingEnabledForMeasurementTools());
/* 266 */         AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(annotStyle)).setAnchorView(anchor).build();
/* 267 */         if (startWith(ToolManager.ToolMode.INK_CREATE)) {
/* 268 */           showAnnotPropertyPopup(popupWindow, drawIndex, getInkTag(drawIndex), 5);
/* 269 */         } else if (startWith(ToolManager.ToolMode.POLYLINE_CREATE)) {
/* 270 */           showAnnotPropertyPopup(popupWindow, drawIndex, "", 21);
/* 271 */         } else if (startWith(ToolManager.ToolMode.POLYGON_CREATE)) {
/* 272 */           showAnnotPropertyPopup(popupWindow, drawIndex, "", 22);
/* 273 */         } else if (startWith(ToolManager.ToolMode.CLOUD_CREATE)) {
/* 274 */           showAnnotPropertyPopup(popupWindow, drawIndex, "", 23);
/* 275 */         } else if (startWith(ToolManager.ToolMode.PERIMETER_MEASURE_CREATE)) {
/* 276 */           showAnnotPropertyPopup(popupWindow, drawIndex, "", 29);
/* 277 */         } else if (startWith(ToolManager.ToolMode.AREA_MEASURE_CREATE)) {
/* 278 */           showAnnotPropertyPopup(popupWindow, drawIndex, "", 30);
/*     */         } 
/*     */       } 
/* 281 */       updateAnnotProperties(annotStyle);
/*     */     } 
/*     */     
/* 284 */     if (this.mToolManager.isSkipNextTapEvent())
/*     */     {
/*     */       
/* 287 */       this.mToolManager.resetSkipNextTapEvent();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClearSelected() {
/* 296 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 300 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 301 */     if (tool instanceof Eraser || startWith(ToolManager.ToolMode.INK_CREATE)) {
/* 302 */       if (tool instanceof FreehandCreate) {
/* 303 */         ((FreehandCreate)this.mToolManager.getTool()).clearStrokes();
/*     */       }
/* 305 */       updateToolbarControlButtons();
/* 306 */     } else if (startWithClickBasedAnnot()) {
/* 307 */       ((AdvancedShapeCreate)this.mToolManager.getTool()).clear();
/* 308 */       updateToolbarControlButtons();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEraserSelected(boolean wasSelectedBefore, View anchor) {
/* 318 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/* 321 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 322 */     if ((tool instanceof FreehandCreate || tool instanceof Eraser) && 
/* 323 */       wasSelectedBefore && this.mEraserStyle != null) {
/* 324 */       AnnotStyleDialogFragment popupWindow = (new AnnotStyleDialogFragment.Builder(this.mEraserStyle)).setAnchorView(anchor).build();
/* 325 */       showInkEraserAnnotPropertyPopup(popupWindow);
/*     */     } 
/*     */ 
/*     */     
/* 329 */     if (this.mToolManager.isSkipNextTapEvent())
/*     */     {
/*     */       
/* 332 */       this.mToolManager.resetSkipNextTapEvent();
/*     */     }
/*     */     
/* 335 */     updateInkEraserAnnotProperties();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUndoSelected() {
/* 343 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 347 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 348 */     if (tool instanceof Eraser || startWith(ToolManager.ToolMode.INK_CREATE)) {
/* 349 */       if (tool instanceof FreehandCreate) {
/* 350 */         ((FreehandCreate)this.mToolManager.getTool()).undoStroke();
/*     */       }
/* 352 */     } else if (startWithClickBasedAnnot()) {
/* 353 */       ((AdvancedShapeCreate)this.mToolManager.getTool()).undo();
/*     */     } 
/*     */     
/* 356 */     updateToolbarControlButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRedoSelected() {
/* 364 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 368 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 369 */     if (tool instanceof Eraser || startWith(ToolManager.ToolMode.INK_CREATE)) {
/* 370 */       if (tool instanceof FreehandCreate) {
/* 371 */         ((FreehandCreate)this.mToolManager.getTool()).redoStroke();
/*     */       }
/* 373 */     } else if (startWithClickBasedAnnot()) {
/* 374 */       ((AdvancedShapeCreate)this.mToolManager.getTool()).redo();
/*     */     } 
/*     */     
/* 377 */     updateToolbarControlButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCloseSelected() {
/* 385 */     if (this.mToolManager == null || this.mEditToolbar == null) {
/*     */       return;
/*     */     }
/*     */     
/* 389 */     if (startWithClickBasedAnnot()) {
/* 390 */       ((AdvancedShapeCreate)this.mToolManager.getTool()).commit();
/*     */     }
/*     */     
/* 393 */     this.mEditToolbar.setVisibility(8);
/* 394 */     if (this.mOnEditToolbarListener != null) {
/* 395 */       this.mOnEditToolbarListener.onEditToolbarDismissed();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onToolbarStateUpdated() {
/* 404 */     updateToolbarControlButtons();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 411 */     onCloseSelected();
/*     */   }
/*     */   
/*     */   private void updateToolbarControlButtons() {
/* 415 */     if (this.mToolManager == null) {
/*     */       return;
/*     */     }
/* 418 */     boolean canClear = false, canErase = false, canUndo = false, canRedo = false;
/*     */     
/* 420 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 421 */     if (tool instanceof Eraser || startWith(ToolManager.ToolMode.INK_CREATE)) {
/* 422 */       if (tool instanceof FreehandCreate) {
/* 423 */         canClear = ((FreehandCreate)this.mToolManager.getTool()).canEraseStroke();
/* 424 */         canUndo = ((FreehandCreate)this.mToolManager.getTool()).canUndoStroke();
/* 425 */         canRedo = ((FreehandCreate)this.mToolManager.getTool()).canRedoStroke();
/* 426 */         canErase = ((FreehandCreate)this.mToolManager.getTool()).canEraseStroke();
/*     */       } 
/* 428 */     } else if (startWithClickBasedAnnot()) {
/* 429 */       canClear = ((AdvancedShapeCreate)this.mToolManager.getTool()).canClear();
/* 430 */       canUndo = ((AdvancedShapeCreate)this.mToolManager.getTool()).canUndo();
/* 431 */       canRedo = ((AdvancedShapeCreate)this.mToolManager.getTool()).canRedo();
/*     */     } 
/*     */     
/* 434 */     this.mEditToolbar.updateControlButtons(canClear, canErase, canUndo, canRedo);
/*     */   }
/*     */   
/*     */   private void initTool(ToolManager.ToolMode toolMode) {
/* 438 */     if (this.mToolManager.getTool().getToolMode() != toolMode) {
/* 439 */       this.mToolManager.setTool(this.mToolManager.createTool((ToolManager.ToolModeBase)toolMode, this.mToolManager.getTool()));
/*     */     }
/* 441 */     if (toolMode == ToolManager.ToolMode.INK_CREATE) {
/* 442 */       ((FreehandCreate)this.mToolManager.getTool()).setForceSameNextToolMode(true);
/* 443 */       ((FreehandCreate)this.mToolManager.getTool()).setMultiStrokeMode(true);
/* 444 */       ((FreehandCreate)this.mToolManager.getTool()).setFromEditToolbar(true);
/* 445 */       ((FreehandCreate)this.mToolManager.getTool()).setOnToolbarStateUpdateListener(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void showAnnotPropertyPopup(@NonNull final AnnotStyleDialogFragment popupWindow, final int drawIndex, final String extraTag, int analyticsScreenId) {
/* 453 */     FragmentActivity activity = this.mActivityRef.get();
/* 454 */     if (activity == null || this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 458 */     if (this.mToolManager.isSkipNextTapEvent()) {
/* 459 */       this.mToolManager.resetSkipNextTapEvent();
/*     */       return;
/*     */     } 
/* 462 */     popupWindow.setCanShowPressureSwitch(true);
/*     */     
/* 464 */     popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */         {
/*     */           public void onDismiss(DialogInterface dialogInterface) {
/* 467 */             if (EditToolbarImpl.this.mToolManager == null || EditToolbarImpl.this.mPdfViewCtrl == null) {
/*     */               return;
/*     */             }
/* 470 */             Context context = EditToolbarImpl.this.mPdfViewCtrl.getContext();
/* 471 */             if (context == null) {
/*     */               return;
/*     */             }
/*     */             
/* 475 */             AnnotStyle annotStyle = popupWindow.getAnnotStyle();
/* 476 */             EditToolbarImpl.this.updateAnnotProperties(annotStyle);
/* 477 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, annotStyle, extraTag);
/*     */             
/* 479 */             EditToolbarImpl.this.mDrawStyles.set(drawIndex, annotStyle);
/* 480 */             EditToolbarImpl.this.mEditToolbar.updateDrawStyles(EditToolbarImpl.this.mDrawStyles);
/*     */           }
/*     */         });
/* 483 */     popupWindow.setOnAnnotStyleChangeListener(new AnnotStyle.OnAnnotStyleChangeListener()
/*     */         {
/*     */           public void onChangeAnnotThickness(float thickness, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextSize(float textSize, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotTextColor(int textColor) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotOpacity(float opacity, boolean done) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotStrokeColor(int color) {
/* 506 */             EditToolbarImpl.this.mEditToolbar.updateDrawColor(drawIndex, color);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFillColor(int color) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotIcon(String icon) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeAnnotFont(FontResource font) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRulerProperty(RulerItem rulerItem) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeOverlayText(String overlayText) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeSnapping(boolean snap) {
/* 536 */             EditToolbarImpl.this.mToolManager.setSnappingEnabledForMeasurementTools(snap);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeRichContentEnabled(boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onChangeDateFormat(String dateFormat) {}
/*     */         });
/* 549 */     popupWindow.show(activity.getSupportFragmentManager(), 2, 
/*     */         
/* 551 */         AnalyticsHandlerAdapter.getInstance().getAnnotationTool(analyticsScreenId));
/*     */   }
/*     */   
/*     */   private void showInkEraserAnnotPropertyPopup(@NonNull final AnnotStyleDialogFragment popupWindow) {
/* 555 */     FragmentActivity activity = this.mActivityRef.get();
/* 556 */     if (activity == null || this.mToolManager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 560 */     if (this.mToolManager.isSkipNextTapEvent()) {
/* 561 */       this.mToolManager.resetSkipNextTapEvent();
/*     */       
/*     */       return;
/*     */     } 
/* 565 */     popupWindow.setOnDismissListener(new DialogInterface.OnDismissListener()
/*     */         {
/*     */           public void onDismiss(DialogInterface dialogInterface) {
/* 568 */             if (EditToolbarImpl.this.mToolManager == null || EditToolbarImpl.this.mPdfViewCtrl == null) {
/*     */               return;
/*     */             }
/* 571 */             Context context = EditToolbarImpl.this.mPdfViewCtrl.getContext();
/* 572 */             if (context == null) {
/*     */               return;
/*     */             }
/*     */             
/* 576 */             AnnotStyle annotStyle = popupWindow.getAnnotStyle();
/* 577 */             ToolStyleConfig.getInstance().saveAnnotStyle(context, annotStyle, "");
/* 578 */             Tool tool = (Tool)EditToolbarImpl.this.mToolManager.getTool();
/* 579 */             if (tool instanceof Eraser) {
/* 580 */               ((Eraser)tool).setupAnnotProperty(annotStyle);
/* 581 */             } else if (tool instanceof FreehandCreate) {
/* 582 */               ((FreehandCreate)tool).setupEraserProperty(annotStyle);
/*     */             } 
/*     */             
/* 585 */             EditToolbarImpl.this.mEraserStyle = annotStyle;
/*     */           }
/*     */         });
/* 588 */     popupWindow.show(activity.getSupportFragmentManager(), 2, 
/*     */         
/* 590 */         AnalyticsHandlerAdapter.getInstance().getAnnotationTool(4));
/*     */   }
/*     */   
/*     */   private void updateAnnotProperties(AnnotStyle annotStyle) {
/* 594 */     if (this.mToolManager == null || annotStyle == null) {
/*     */       return;
/*     */     }
/*     */     
/* 598 */     ToolManager.Tool tool = this.mToolManager.getTool();
/* 599 */     ((Tool)tool).setupAnnotProperty(annotStyle);
/*     */   }
/*     */   
/*     */   private void updateInkEraserAnnotProperties() {
/* 603 */     if (this.mToolManager == null || this.mEraserStyle == null) {
/*     */       return;
/*     */     }
/*     */     
/* 607 */     if (this.mToolManager.getTool().getToolMode() == ToolManager.ToolMode.INK_CREATE) {
/* 608 */       ((FreehandCreate)this.mToolManager.getTool()).setupEraserProperty(this.mEraserStyle);
/*     */     }
/*     */   }
/*     */   
/*     */   private String getInkTag(int inkIndex) {
/* 613 */     switch (inkIndex) {
/*     */       case 0:
/* 615 */         return "ink_tag_1";
/*     */       case 1:
/* 617 */         return "ink_tag_2";
/*     */       case 2:
/* 619 */         return "ink_tag_3";
/*     */       case 3:
/* 621 */         return "ink_tag_4";
/*     */       case 4:
/* 623 */         return "ink_tag_5";
/*     */     } 
/* 625 */     return "";
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
/*     */   public boolean handleKeyUp(int keyCode, KeyEvent event) {
/* 638 */     return this.mEditToolbar.handleKeyUp(keyCode, event);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnEditToolbarListener(OnEditToolbarListener listener) {
/* 648 */     this.mOnEditToolbarListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onOrientationChanged() {
/* 653 */     if (this.mEditToolbar.isShown())
/* 654 */       updateToolbarControlButtons(); 
/*     */   }
/*     */   
/*     */   public static interface OnEditToolbarListener {
/*     */     void onEditToolbarDismissed();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\EditToolbarImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */