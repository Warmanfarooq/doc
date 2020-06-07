/*      */ package com.pdftron.pdf.tools;
/*      */ 
/*      */ import android.content.Context;
/*      */ import android.os.Bundle;
/*      */ import android.util.Log;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.annotation.Nullable;
/*      */ import com.pdftron.common.PDFNetException;
/*      */ import com.pdftron.pdf.Annot;
/*      */ import com.pdftron.pdf.Field;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.Rect;
/*      */ import com.pdftron.pdf.annots.Widget;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.AnnotUtils;
/*      */ import com.pdftron.pdf.utils.Utils;
/*      */ import com.pdftron.pdf.utils.ViewerUtils;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import org.json.JSONException;
/*      */ import org.json.JSONObject;
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
/*      */ public class UndoRedoManager
/*      */   implements ToolManager.AnnotationModificationListener, ToolManager.PdfDocModificationListener
/*      */ {
/*   51 */   private static final String TAG = UndoRedoManager.class.getName();
/*      */   private static boolean sDebug;
/*      */   private static final String JSON_DELIMITER = " ";
/*      */   private static final String JSON_INITIAL_LABEL = "label";
/*      */   private static final String JSON_INITIAL_CONTENT = "android_initial";
/*      */   static final String JSON_ACTION = "Action";
/*      */   private static final String JSON_ACTION_EVENT = "Action event";
/*      */   private static final String JSON_STATE_NOT_FOUND = "state not found";
/*      */   private static final String JSON_SAFETY = "safety";
/*      */   private static final String JSON_ANNOT_INFO = "Annot Info";
/*      */   private static final String JSON_ANNOT_PRE_PAGE_NUM = "Page Number Before Modification";
/*      */   private static final String JSON_ANNOT_PRE_RECT = "Rect Before Modification";
/*      */   private static final String JSON_ANNOT_PAGE_NUMS = "Page Numbers";
/*      */   private static final String JSON_ANNOT_RECTS = "Rects";
/*      */   private static final String JSON_ANNOT_XFDF = "xfdf";
/*      */   private static final String JSON_PAGE_LIST = "Pages";
/*      */   private static final String JSON_PAGE_FROM = "From";
/*      */   private static final String JSON_PAGE_TO = "To";
/*      */   private static final String JSON_COLLAB_ANNOT_INFO = "collab_annot_info";
/*      */   private static final String JSON_COLLAB_UNDO_ACTION = "collab_undo_action";
/*      */   private static final String JSON_COLLAB_UNDO_COMMAND = "collab_undo_command";
/*      */   private static final String JSON_COLLAB_REDO_ACTION = "collab_redo_action";
/*      */   private static final String JSON_COLLAB_REDO_COMMAND = "collab_redo_command";
/*      */   private static final String JSON_COLLAB_ANNOT_PARAMS = "collab_annot_params";
/*      */   private static final String JSON_COLLAB_ANNOT_PARAMS_ID = "collab_annot_params_id";
/*      */   private static final String JSON_COLLAB_ANNOT_PARAMS_TYPE = "collab_annot_params_type";
/*      */   private static final String JSON_COLLAB_ANNOT_PARAMS_PAGE_NUM = "collab_annot_params_page_num";
/*      */   private static final String ACTION_EVENT_REMOVE_ANNOTS_FROM_PAGE = "remove_annots_from_page";
/*      */   private static final String ACTION_EVENT_REMOVE_ALL_ANNOTATIONS = "remove_all_annotations";
/*      */   private static final String ACTION_EVENT_ACTION = "action";
/*      */   private static final String ACTION_EVENT_MODIFY_BOOKMARKS = "modify_bookmarks";
/*      */   private static final String ACTION_EVENT_CROP_PAGES = "crop_pages";
/*      */   private static final String ACTION_EVENT_ADD_PAGES = "add_pages";
/*      */   private static final String ACTION_EVENT_DELETE_PAGES = "delete_pages";
/*      */   private static final String ACTION_EVENT_ROTATE_PAGES = "rotate_pages";
/*      */   private static final String ACTION_EVENT_MOVE_PAGE = "move_page";
/*      */   private static final String ACTION_EVENT_REDACTION = "redaction";
/*      */   private static final String ACTION_EVENT_PAGES_LABELS = "page_label_changed";
/*      */   private Context mContext;
/*      */   private ToolManager mToolManager;
/*      */   private PDFViewCtrl mPdfViewCtrl;
/*      */   private Rect mPreModifyAnnotRect;
/*      */   private int mPreModifyPageNum;
/*      */   
/*      */   private enum AnnotAction
/*      */   {
/*   97 */     ADD,
/*   98 */     MODIFY,
/*   99 */     REMOVE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  107 */   private int mUndoCount = 0;
/*  108 */   private int mRedoCount = 0;
/*      */ 
/*      */   
/*      */   private int mLocationId;
/*      */ 
/*      */   
/*      */   private String mLastAction;
/*      */ 
/*      */   
/*      */   private boolean mLastActionIsUndo;
/*      */ 
/*      */   
/*      */   public UndoRedoManager(@NonNull ToolManager toolManager) {
/*  121 */     this.mToolManager = toolManager;
/*  122 */     this.mPdfViewCtrl = toolManager.getPDFViewCtrl();
/*  123 */     if (this.mPdfViewCtrl == null) {
/*  124 */       throw new NullPointerException("PDFViewCtrl can't be null");
/*      */     }
/*      */     
/*  127 */     this.mToolManager.addAnnotationModificationListener(this);
/*  128 */     this.mToolManager.addPdfDocModificationListener(this);
/*  129 */     this.mContext = toolManager.getPDFViewCtrl().getContext();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PDFViewCtrl getPdfViewCtrl() {
/*  136 */     return this.mPdfViewCtrl;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String undo() {
/*  145 */     return undo(0, false);
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
/*      */   public String undo(int locationId, boolean sendEvent) {
/*  157 */     this.mLocationId = locationId;
/*  158 */     this.mLastActionIsUndo = true;
/*  159 */     if (!sendEvent) {
/*  160 */       this.mUndoCount++;
/*      */     } else {
/*  162 */       this.mUndoCount = 0;
/*      */     } 
/*  164 */     return performUndoRedo(true, locationId, sendEvent);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String redo() {
/*  173 */     return redo(0, false);
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
/*      */   public String redo(int locationId, boolean sendEvent) {
/*  185 */     this.mLocationId = locationId;
/*  186 */     this.mLastActionIsUndo = false;
/*  187 */     if (!sendEvent) {
/*  188 */       this.mRedoCount++;
/*      */     } else {
/*  190 */       this.mRedoCount = 0;
/*      */     } 
/*  192 */     return performUndoRedo(false, locationId, sendEvent);
/*      */   }
/*      */   
/*      */   private String performUndoRedo(boolean isUndo, int location, boolean sendEvent) {
/*  196 */     String info = "";
/*      */     
/*  198 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*  199 */       return info;
/*      */     }
/*      */     
/*  202 */     Tool tool = (Tool)this.mToolManager.getTool();
/*  203 */     if (tool instanceof FreehandCreate) {
/*  204 */       ((FreehandCreate)tool).commitAnnotation();
/*      */     }
/*      */     
/*      */     try {
/*  208 */       this.mPdfViewCtrl.cancelRendering();
/*  209 */       removeUnsafeUndoRedoInfo(isUndo);
/*  210 */       if (isUndo) {
/*  211 */         info = this.mPdfViewCtrl.undo();
/*      */       } else {
/*  213 */         info = this.mPdfViewCtrl.redo();
/*      */       } 
/*  215 */       if (sDebug)
/*  216 */         Log.d(TAG, (isUndo ? "undo: " : "redo: ") + info); 
/*  217 */       updatePageLayout(info);
/*      */       
/*  219 */       JSONObject jsonObject = new JSONObject(info);
/*  220 */       if (jsonObject.has("xfdf") && 
/*  221 */         this.mToolManager.getAnnotManager() != null) {
/*  222 */         this.mToolManager.getAnnotManager().onLocalChange(isUndo ? "undo" : "redo");
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  227 */       String action = "";
/*  228 */       if (jsonObject.has("Action event")) {
/*  229 */         action = jsonObject.getString("Action event");
/*      */       }
/*  231 */       if (sendEvent) {
/*  232 */         AnalyticsHandlerAdapter.getInstance().sendEvent(isUndo ? 19 : 20, 
/*      */             
/*  234 */             AnalyticsParam.viewerUndoRedoParam(action, location));
/*      */       } else {
/*  236 */         this.mLastAction = action;
/*      */       } 
/*  238 */     } catch (Exception e) {
/*  239 */       AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */     } 
/*      */     
/*  242 */     return info;
/*      */   }
/*      */   
/*      */   public void sendConsecutiveUndoRedoEvent() {
/*  246 */     if (this.mUndoCount != 0 || this.mRedoCount != 0) {
/*  247 */       AnalyticsHandlerAdapter.getInstance().sendEvent(this.mLastActionIsUndo ? 19 : 20, 
/*  248 */           AnalyticsParam.viewerUndoRedoParam(this.mLastAction, this.mLocationId, this.mUndoCount, this.mRedoCount));
/*      */     } else {
/*  250 */       AnalyticsHandlerAdapter.getInstance().sendEvent(59);
/*      */     } 
/*  252 */     this.mUndoCount = 0;
/*  253 */     this.mRedoCount = 0;
/*  254 */     this.mLastAction = "";
/*  255 */     this.mLocationId = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsAdded(Map<Annot, Integer> annots) {
/*  266 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  273 */       JSONObject obj = prepareAnnotSnapshot(annots, AnnotAction.ADD);
/*  274 */       takeAnnotSnapshot(obj);
/*      */       
/*  276 */       if (this.mToolManager.getAnnotManager() != null) {
/*  277 */         this.mToolManager.getAnnotManager().onLocalChange("add");
/*      */       }
/*  279 */     } catch (Exception e) {
/*  280 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsPreModify(Map<Annot, Integer> annots) {
/*  297 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*  301 */     this.mPreModifyPageNum = 0;
/*  302 */     this.mPreModifyAnnotRect = null;
/*      */     
/*  304 */     for (Map.Entry<Annot, Integer> pair : annots.entrySet()) {
/*  305 */       Annot annot = pair.getKey();
/*  306 */       Integer pageNum = pair.getValue();
/*  307 */       if (this.mPreModifyPageNum != 0 && this.mPreModifyPageNum != pageNum.intValue()) {
/*  308 */         this.mPreModifyPageNum = 0;
/*  309 */         this.mPreModifyAnnotRect = null;
/*      */         return;
/*      */       } 
/*  312 */       this.mPreModifyPageNum = pageNum.intValue();
/*      */       try {
/*  314 */         if (annot != null && annot.isValid()) {
/*      */           Rect annotRect;
/*  316 */           if (annot.getType() == 19) {
/*      */             
/*  318 */             Widget widget = new Widget(annot);
/*  319 */             Field field = widget.getField();
/*  320 */             annotRect = field.getUpdateRect();
/*      */           } else {
/*  322 */             annotRect = this.mPdfViewCtrl.getPageRectForAnnot(annot, pageNum.intValue());
/*      */           } 
/*  324 */           annotRect.normalize();
/*  325 */           if (this.mPreModifyAnnotRect != null) {
/*  326 */             this.mPreModifyAnnotRect.setX1(Math.min(this.mPreModifyAnnotRect.getX1(), annotRect.getX1()));
/*  327 */             this.mPreModifyAnnotRect.setY1(Math.min(this.mPreModifyAnnotRect.getY1(), annotRect.getY1()));
/*  328 */             this.mPreModifyAnnotRect.setX2(Math.max(this.mPreModifyAnnotRect.getX2(), annotRect.getX2()));
/*  329 */             this.mPreModifyAnnotRect.setY2(Math.max(this.mPreModifyAnnotRect.getY2(), annotRect.getY2())); continue;
/*      */           } 
/*  331 */           this.mPreModifyAnnotRect = annotRect;
/*      */         }
/*      */       
/*  334 */       } catch (Exception e) {
/*  335 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onAnnotationsModified(Map<Annot, Integer> annots, Bundle extra) {
/*  348 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  354 */     JSONObject result = prepareAnnotSnapshot(annots, AnnotAction.MODIFY, this.mPreModifyAnnotRect, this.mPreModifyPageNum);
/*  355 */     this.mPreModifyAnnotRect = null;
/*  356 */     takeAnnotSnapshot(result);
/*  357 */     if (this.mToolManager.getAnnotManager() != null) {
/*  358 */       this.mToolManager.getAnnotManager().onLocalChange("modify");
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
/*      */   public void onAnnotationsPreRemove(Map<Annot, Integer> annots) {
/*  370 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
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
/*      */   public void onAnnotationsRemoved(Map<Annot, Integer> annots) {
/*  384 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  391 */       JSONObject result = prepareAnnotSnapshot(annots, AnnotAction.REMOVE);
/*      */       
/*  393 */       takeAnnotSnapshot(result);
/*  394 */       if (this.mToolManager.getAnnotManager() != null) {
/*  395 */         this.mToolManager.getAnnotManager().onLocalChange("delete");
/*      */       }
/*  397 */     } catch (Exception e) {
/*  398 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationsRemovedOnPage(int pageNum) {
/*  408 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  413 */       JSONObject jsonObj = new JSONObject();
/*  414 */       if (this.mContext != null) {
/*  415 */         String strRemoveAnnotations = this.mContext.getResources().getString(R.string.undo_redo_annots_remove_from_page, new Object[] { Integer.valueOf(pageNum) });
/*  416 */         jsonObj.put("Action", strRemoveAnnotations);
/*      */       } 
/*  418 */       jsonObj.put("Action event", "remove_annots_from_page");
/*      */       
/*  420 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  421 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  424 */       takeUndoSnapshot(jsonObj.toString());
/*  425 */       if (this.mToolManager.getAnnotManager() != null) {
/*  426 */         this.mToolManager.getAnnotManager().onLocalChange("delete");
/*      */       }
/*  428 */       if (sDebug)
/*  429 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  430 */     } catch (Exception e) {
/*  431 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAllAnnotationsRemoved() {
/*  441 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  446 */       JSONObject jsonObj = new JSONObject();
/*  447 */       if (this.mContext != null) {
/*  448 */         String strRemoveAnnotations = this.mContext.getResources().getString(R.string.undo_redo_annots_remove);
/*  449 */         jsonObj.put("Action", strRemoveAnnotations);
/*      */       } 
/*  451 */       jsonObj.put("Action event", "remove_all_annotations");
/*      */       
/*  453 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  454 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  457 */       takeUndoSnapshot(jsonObj.toString());
/*  458 */       if (this.mToolManager.getAnnotManager() != null) {
/*  459 */         this.mToolManager.getAnnotManager().onLocalChange("delete");
/*      */       }
/*  461 */       if (sDebug)
/*  462 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  463 */     } catch (Exception e) {
/*  464 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onAnnotationAction() {
/*  474 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  479 */       JSONObject jsonObj = new JSONObject();
/*  480 */       if (this.mContext != null) {
/*  481 */         String strAnnotAction = this.mContext.getResources().getString(R.string.undo_redo_annot_action);
/*  482 */         jsonObj.put("Action", strAnnotAction);
/*      */       } 
/*  484 */       jsonObj.put("Action event", "action");
/*      */       
/*  486 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  487 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  490 */       takeUndoSnapshot(jsonObj.toString());
/*  491 */       if (sDebug)
/*  492 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  493 */     } catch (Exception e) {
/*  494 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void onPageLabelsChanged() {
/*  500 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  505 */       JSONObject jsonObj = new JSONObject();
/*  506 */       if (this.mContext != null) {
/*  507 */         String strPgLabelsChanged = this.mContext.getResources().getString(R.string.undo_redo_page_labels);
/*  508 */         jsonObj.put("Action", strPgLabelsChanged);
/*      */       } 
/*  510 */       jsonObj.put("Action event", "page_label_changed");
/*      */       
/*  512 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  513 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  516 */       takeUndoSnapshot(jsonObj.toString());
/*  517 */       if (sDebug)
/*  518 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  519 */     } catch (Exception e) {
/*  520 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onBookmarkModified() {
/*  530 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  535 */       JSONObject jsonObj = new JSONObject();
/*  536 */       if (this.mContext != null) {
/*  537 */         String strBookmarkModified = this.mContext.getResources().getString(R.string.undo_redo_bookmark_modify);
/*  538 */         jsonObj.put("Action", strBookmarkModified);
/*      */       } 
/*  540 */       jsonObj.put("Action event", "modify_bookmarks");
/*      */       
/*  542 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  543 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  546 */       takeUndoSnapshot(jsonObj.toString());
/*  547 */       if (sDebug)
/*  548 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  549 */     } catch (Exception e) {
/*  550 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onPagesCropped() {
/*  560 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  565 */       JSONObject jsonObj = new JSONObject();
/*  566 */       if (this.mContext != null) {
/*  567 */         String strCropPages = this.mContext.getResources().getString(R.string.pref_viewmode_user_crop);
/*  568 */         jsonObj.put("Action", strCropPages);
/*      */       } 
/*  570 */       jsonObj.put("Action event", "crop_pages");
/*      */       
/*  572 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  573 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  576 */       takeUndoSnapshot(jsonObj.toString());
/*  577 */       if (sDebug)
/*  578 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  579 */     } catch (Exception e) {
/*  580 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onPagesAdded(List<Integer> pageList) {
/*  592 */     if (pageList == null || pageList.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  597 */       JSONObject jsonObj = new JSONObject();
/*  598 */       if (this.mContext != null) {
/*  599 */         String strPageAdd = this.mContext.getResources().getString(R.string.undo_redo_page_add);
/*  600 */         jsonObj.put("Action", strPageAdd);
/*      */       } 
/*  602 */       jsonObj.put("Action event", "add_pages");
/*  603 */       jsonObj.put("Pages", convertPageListToString(pageList));
/*      */       
/*  605 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  606 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  609 */       takeUndoSnapshot(jsonObj.toString());
/*  610 */       if (sDebug)
/*  611 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  612 */     } catch (Exception e) {
/*  613 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onPagesDeleted(List<Integer> pageList) {
/*  625 */     if (pageList == null || pageList.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  630 */       JSONObject jsonObj = new JSONObject();
/*  631 */       if (this.mContext != null) {
/*  632 */         String strPageDelete = this.mContext.getResources().getString(R.string.undo_redo_page_delete);
/*  633 */         jsonObj.put("Action", strPageDelete);
/*      */       } 
/*  635 */       jsonObj.put("Action event", "delete_pages");
/*  636 */       jsonObj.put("Pages", convertPageListToString(pageList));
/*      */       
/*  638 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  639 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  642 */       takeUndoSnapshot(jsonObj.toString());
/*  643 */       if (sDebug)
/*  644 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  645 */     } catch (Exception e) {
/*  646 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onPagesRotated(List<Integer> pageList) {
/*  658 */     if (pageList == null || pageList.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  663 */       JSONObject jsonObj = new JSONObject();
/*  664 */       if (this.mContext != null) {
/*  665 */         String strPageRotate = this.mContext.getResources().getString(R.string.undo_redo_page_rotate);
/*  666 */         jsonObj.put("Action", strPageRotate);
/*      */       } 
/*  668 */       jsonObj.put("Action event", "rotate_pages");
/*  669 */       jsonObj.put("Pages", convertPageListToString(pageList));
/*      */       
/*  671 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  672 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  675 */       takeUndoSnapshot(jsonObj.toString());
/*  676 */       if (sDebug)
/*  677 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  678 */     } catch (Exception e) {
/*  679 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   public void onPageMoved(int from, int to) {
/*  691 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  696 */       JSONObject jsonObj = new JSONObject();
/*  697 */       if (this.mContext != null) {
/*  698 */         String strPageMove = this.mContext.getResources().getString(R.string.undo_redo_page_move);
/*  699 */         jsonObj.put("Action", strPageMove);
/*      */       } 
/*  701 */       jsonObj.put("Action event", "move_page");
/*  702 */       jsonObj.put("From", from);
/*  703 */       jsonObj.put("To", to);
/*      */       
/*  705 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  706 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  709 */       takeUndoSnapshot(jsonObj.toString());
/*  710 */       if (sDebug)
/*  711 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  712 */     } catch (Exception e) {
/*  713 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void annotationsCouldNotBeAdded(String errorMessage) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onRedaction(JSONObject jsonObj) {
/*  726 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  731 */       if (jsonObj == null) {
/*  732 */         jsonObj = new JSONObject();
/*      */       }
/*  734 */       if (this.mContext != null) {
/*  735 */         String redaction = this.mContext.getResources().getString(R.string.undo_redo_redaction);
/*  736 */         jsonObj.put("Action", redaction);
/*      */       } 
/*  738 */       jsonObj.put("Action event", "redaction");
/*      */       
/*  740 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/*  741 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/*  744 */       takeUndoSnapshot(jsonObj.toString());
/*  745 */       if (sDebug)
/*  746 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/*  747 */     } catch (Exception e) {
/*  748 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
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
/*      */   @NonNull
/*      */   public static List<Integer> getPageList(String info) {
/*  762 */     List<Integer> pageList = new ArrayList<>();
/*      */     
/*  764 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/*  766 */         JSONObject jsonObj = new JSONObject(info);
/*  767 */         if (jsonObj.has("Pages")) {
/*  768 */           String allPages = jsonObj.getString("Pages");
/*  769 */           if (!Utils.isNullOrEmpty(allPages)) {
/*  770 */             String[] pages = allPages.split(" ");
/*  771 */             for (String page : pages) {
/*  772 */               pageList.add(Integer.valueOf(page));
/*      */             }
/*      */           } 
/*      */         } 
/*  776 */       } catch (Exception e) {
/*  777 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*  781 */     return pageList;
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
/*      */   public static int getPageFrom(String info) {
/*  793 */     int pageNum = 0;
/*      */     
/*  795 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/*  797 */         JSONObject jsonObj = new JSONObject(info);
/*  798 */         pageNum = jsonObj.getInt("From");
/*  799 */       } catch (Exception e) {
/*  800 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*  804 */     return pageNum;
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
/*      */   public static int getPageTo(String info) {
/*  816 */     int pageNum = 0;
/*      */     
/*  818 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/*  820 */         JSONObject jsonObj = new JSONObject(info);
/*  821 */         pageNum = jsonObj.getInt("To");
/*  822 */       } catch (Exception e) {
/*  823 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*  827 */     return pageNum;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canUndo() {
/*  834 */     return !Utils.isNullOrEmpty(getNextUndoAction());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRedo() {
/*  841 */     return !Utils.isNullOrEmpty(getNextRedoAction());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNextUndoAction() {
/*  850 */     String result = "";
/*  851 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled() || this.mPdfViewCtrl.getDoc() == null) {
/*  852 */       return result;
/*      */     }
/*      */     
/*  855 */     removeUnsafeUndoRedoInfo(true);
/*  856 */     String info = null;
/*  857 */     JSONObject jsonObj = null;
/*      */     try {
/*  859 */       info = this.mPdfViewCtrl.getNextUndoInfo();
/*  860 */       if (sDebug)
/*  861 */         Log.d(TAG, "next undo: " + info); 
/*  862 */       jsonObj = new JSONObject(info);
/*  863 */       if (jsonObj.has("Action")) {
/*  864 */         String action = jsonObj.getString("Action");
/*  865 */         if (this.mContext != null && !Utils.isNullOrEmpty(action) && isValidAction(this.mContext, action)) {
/*  866 */           String strUndo = this.mContext.getResources().getString(R.string.undo);
/*  867 */           result = strUndo + ": " + action;
/*      */         } 
/*      */       } 
/*  870 */     } catch (Exception e) {
/*  871 */       if (info == null || !info.equals("state not found")) {
/*  872 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "next undo info: " + ((info == null) ? "null" : info));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  877 */     if (Utils.isNullOrEmpty(result) && jsonObj != null) {
/*      */       try {
/*  879 */         if (jsonObj.has("label")) {
/*  880 */           String label = jsonObj.getString("label");
/*  881 */           if (!label.equals("initial")) {
/*  882 */             result = this.mContext.getResources().getString(R.string.undo) + "...";
/*      */           }
/*      */         } 
/*  885 */       } catch (Exception e) {
/*  886 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     }
/*      */     
/*  890 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNextRedoAction() {
/*  899 */     String result = "";
/*  900 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled() || this.mPdfViewCtrl.getDoc() == null) {
/*  901 */       return result;
/*      */     }
/*      */     
/*  904 */     removeUnsafeUndoRedoInfo(false);
/*  905 */     String info = null;
/*      */     try {
/*  907 */       info = this.mPdfViewCtrl.getNextRedoInfo();
/*  908 */       if (sDebug)
/*  909 */         Log.d(TAG, "next redo: " + info); 
/*  910 */       JSONObject jsonObj = new JSONObject(info);
/*  911 */       if (jsonObj.has("Action")) {
/*  912 */         String action = jsonObj.getString("Action");
/*  913 */         if (this.mContext != null && isValidAction(this.mContext, action)) {
/*  914 */           String strRedo = this.mContext.getResources().getString(R.string.redo);
/*  915 */           result = strRedo + ": " + action;
/*      */         } 
/*      */       } 
/*  918 */     } catch (Exception e) {
/*  919 */       if (info == null || !info.equals("state not found")) {
/*  920 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "next redo info: " + ((info == null) ? "null" : info));
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  925 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNextUndoEditPageAction() {
/*  934 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*  935 */       return false;
/*      */     }
/*      */     
/*  938 */     String info = null;
/*      */     try {
/*  940 */       info = this.mPdfViewCtrl.getNextUndoInfo();
/*  941 */       if (sDebug)
/*  942 */         Log.d(TAG, "next undo: " + info); 
/*  943 */       return isEditPageAction(this.mContext, info);
/*  944 */     } catch (Exception e) {
/*  945 */       if (info == null || !info.equals("state not found")) {
/*  946 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "next undo info: " + ((info == null) ? "null" : info));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  951 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNextRedoEditPageAction() {
/*  960 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*  961 */       return false;
/*      */     }
/*      */     
/*  964 */     String info = null;
/*      */     try {
/*  966 */       info = this.mPdfViewCtrl.getNextRedoInfo();
/*  967 */       if (sDebug)
/*  968 */         Log.d(TAG, "next redo: " + info); 
/*  969 */       return isEditPageAction(this.mContext, info);
/*  970 */     } catch (Exception e) {
/*  971 */       if (info == null || !info.equals("state not found")) {
/*  972 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "next redo info: " + ((info == null) ? "null" : info));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  977 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAddPagesAction(Context context, String info) {
/*  986 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/*  988 */         JSONObject jsonObj = new JSONObject(info);
/*  989 */         if (jsonObj.has("Action event")) {
/*  990 */           String action = jsonObj.getString("Action event");
/*  991 */           if ("add_pages".equals(action)) {
/*  992 */             return true;
/*      */           }
/*      */         } 
/*  995 */       } catch (Exception e) {
/*  996 */         if (!info.equals("state not found")) {
/*  997 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1002 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDeletePagesAction(Context context, String info) {
/* 1011 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1013 */         JSONObject jsonObj = new JSONObject(info);
/* 1014 */         if (jsonObj.has("Action event")) {
/* 1015 */           String action = jsonObj.getString("Action event");
/* 1016 */           if ("delete_pages".equals(action)) {
/* 1017 */             return true;
/*      */           }
/*      */         } 
/* 1020 */       } catch (Exception e) {
/* 1021 */         if (!info.equals("state not found")) {
/* 1022 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1027 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRotatePagesAction(Context context, String info) {
/* 1036 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1038 */         JSONObject jsonObj = new JSONObject(info);
/* 1039 */         if (jsonObj.has("Action event")) {
/* 1040 */           String action = jsonObj.getString("Action event");
/* 1041 */           if ("rotate_pages".equals(action)) {
/* 1042 */             return true;
/*      */           }
/*      */         } 
/* 1045 */       } catch (Exception e) {
/* 1046 */         if (!info.equals("state not found")) {
/* 1047 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1052 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEditPageLabelsAction(Context context, String info) {
/* 1061 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1063 */         JSONObject jsonObj = new JSONObject(info);
/* 1064 */         if (jsonObj.has("Action event")) {
/* 1065 */           String action = jsonObj.getString("Action event");
/* 1066 */           if ("page_label_changed".equals(action)) {
/* 1067 */             return true;
/*      */           }
/*      */         } 
/* 1070 */       } catch (Exception e) {
/* 1071 */         if (!info.equals("state not found")) {
/* 1072 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1077 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMovePageAction(Context context, String info) {
/* 1086 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1088 */         JSONObject jsonObj = new JSONObject(info);
/* 1089 */         if (jsonObj.has("Action event")) {
/* 1090 */           String action = jsonObj.getString("Action event");
/* 1091 */           if ("move_page".equals(action)) {
/* 1092 */             return true;
/*      */           }
/*      */         } 
/* 1095 */       } catch (Exception e) {
/* 1096 */         if (!info.equals("state not found")) {
/* 1097 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1102 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEditPageAction(Context context, String info) {
/* 1112 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1114 */         JSONObject jsonObj = new JSONObject(info);
/* 1115 */         if (jsonObj.has("Action event")) {
/* 1116 */           String action = jsonObj.getString("Action event");
/* 1117 */           if ("add_pages".equals(action) || "delete_pages"
/* 1118 */             .equals(action) || "rotate_pages"
/* 1119 */             .equals(action) || "move_page"
/* 1120 */             .equals(action) || "crop_pages"
/* 1121 */             .equals(action) || "action"
/* 1122 */             .equals(action) || "page_label_changed"
/* 1123 */             .equals(action)) {
/* 1124 */             return true;
/*      */           }
/*      */         } 
/* 1127 */       } catch (Exception e) {
/* 1128 */         if (!info.equals("state not found")) {
/* 1129 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1134 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAddAnnotationAction(Context context, String info) {
/* 1144 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1146 */         JSONObject jsonObj = new JSONObject(info);
/* 1147 */         if (jsonObj.has("Action event")) {
/* 1148 */           String action = jsonObj.getString("Action event");
/* 1149 */           String strAnnotAdd = context.getResources().getString(R.string.add);
/* 1150 */           if (!Utils.isNullOrEmpty(action) && action.startsWith(strAnnotAdd)) {
/* 1151 */             return true;
/*      */           }
/*      */         } 
/* 1154 */       } catch (Exception e) {
/* 1155 */         if (!info.equals("state not found")) {
/* 1156 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1161 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isModifyAnnotationAction(Context context, String info) {
/* 1171 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1173 */         JSONObject jsonObj = new JSONObject(info);
/* 1174 */         if (jsonObj.has("Action event")) {
/* 1175 */           String action = jsonObj.getString("Action event");
/* 1176 */           String strAnnotModify = context.getResources().getString(R.string.undo_redo_annot_modify);
/* 1177 */           if (!Utils.isNullOrEmpty(action) && action.startsWith(strAnnotModify)) {
/* 1178 */             return true;
/*      */           }
/*      */         } 
/* 1181 */       } catch (Exception e) {
/* 1182 */         if (!info.equals("state not found")) {
/* 1183 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1188 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRemoveAnnotationAction(Context context, String info) {
/* 1198 */     if (context != null && !Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1200 */         JSONObject jsonObj = new JSONObject(info);
/* 1201 */         if (jsonObj.has("Action event")) {
/* 1202 */           String action = jsonObj.getString("Action event");
/* 1203 */           String strAnnotRemove = context.getResources().getString(R.string.undo_redo_annot_remove);
/* 1204 */           if (!Utils.isNullOrEmpty(action) && action.startsWith(strAnnotRemove)) {
/* 1205 */             return true;
/*      */           }
/*      */         } 
/* 1208 */       } catch (Exception e) {
/* 1209 */         if (!info.equals("state not found")) {
/* 1210 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1215 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void takeUndoSnapshotForSafety() {
/* 1222 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1226 */     boolean shouldUnlock = false;
/*      */     try {
/* 1228 */       this.mPdfViewCtrl.docLock(false);
/* 1229 */       shouldUnlock = true;
/* 1230 */       if (this.mPdfViewCtrl.getDoc().hasChangesSinceSnapshot()) {
/* 1231 */         JSONObject jsonObj = new JSONObject();
/* 1232 */         jsonObj.put("Action", "safety");
/* 1233 */         if (Utils.isNullOrEmpty(jsonObj.toString())) {
/* 1234 */           AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */         }
/*      */         
/* 1237 */         this.mPdfViewCtrl.takeUndoSnapshot(jsonObj.toString());
/* 1238 */         if (sDebug)
/* 1239 */           Log.d(TAG, "snapshot for safety"); 
/*      */       } 
/* 1241 */     } catch (PDFNetException|JSONException e) {
/* 1242 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } finally {
/* 1244 */       if (shouldUnlock) {
/* 1245 */         this.mPdfViewCtrl.docUnlock();
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
/*      */   public static void jumpToUndoRedo(PDFViewCtrl pdfViewCtrl, String info, boolean isUndo) {
/* 1259 */     if (pdfViewCtrl == null || Utils.isNullOrEmpty(info)) {
/*      */       return;
/*      */     }
/*      */     
/* 1263 */     if (sDebug) {
/* 1264 */       Log.d(TAG, "jump to " + info);
/*      */     }
/* 1266 */     Context context = pdfViewCtrl.getContext();
/* 1267 */     if (isEditPageAction(context, info)) {
/* 1268 */       if (isDeletePagesAction(context, info)) {
/* 1269 */         List<Integer> pageList = getPageList(info);
/* 1270 */         int minPageNum = ((Integer)Collections.<Integer>min(pageList)).intValue();
/* 1271 */         if (pageList.size() != 0) {
/* 1272 */           if (isUndo) {
/* 1273 */             pdfViewCtrl.setCurrentPage(minPageNum);
/*      */           } else {
/* 1275 */             pdfViewCtrl.setCurrentPage((minPageNum == 1) ? 1 : (minPageNum - 1));
/*      */           } 
/*      */         }
/* 1278 */       } else if (isAddPagesAction(context, info)) {
/* 1279 */         List<Integer> pageList = getPageList(info);
/* 1280 */         if (pageList.size() != 0) {
/* 1281 */           int minPageNum = ((Integer)Collections.<Integer>min(pageList)).intValue();
/* 1282 */           if (isUndo) {
/* 1283 */             pdfViewCtrl.setCurrentPage((minPageNum == 1) ? 1 : (minPageNum - 1));
/*      */           } else {
/* 1285 */             pdfViewCtrl.setCurrentPage(minPageNum);
/*      */           } 
/*      */         } 
/* 1288 */       } else if (isRotatePagesAction(context, info) || isEditPageLabelsAction(context, info)) {
/* 1289 */         List<Integer> pageList = getPageList(info);
/* 1290 */         if (pageList.size() != 0) {
/* 1291 */           int curPage = pdfViewCtrl.getCurrentPage();
/* 1292 */           if (!pageList.contains(Integer.valueOf(curPage))) {
/* 1293 */             int minPageNum = ((Integer)Collections.<Integer>min(pageList)).intValue();
/* 1294 */             pdfViewCtrl.setCurrentPage(minPageNum);
/*      */           } 
/*      */         } 
/* 1297 */       } else if (isMovePageAction(context, info)) {
/* 1298 */         int pageFrom = getPageFrom(info);
/* 1299 */         int pageTo = getPageTo(info);
/* 1300 */         if (isUndo) {
/* 1301 */           pdfViewCtrl.setCurrentPage(pageFrom);
/*      */         } else {
/* 1303 */           pdfViewCtrl.setCurrentPage(pageTo);
/*      */         } 
/*      */       } 
/*      */     } else {
/* 1307 */       boolean isAnnotModify = isModifyAnnotationAction(context, info);
/* 1308 */       if (isAnnotModify && isUndo) {
/* 1309 */         int prePageNum = getPreModifiedAnnotPageNumber(info);
/* 1310 */         Rect preAnnotRect = getPreModifiedAnnotRect(info);
/* 1311 */         if (prePageNum != 0 && preAnnotRect != null) {
/* 1312 */           ViewerUtils.animateUndoRedo(pdfViewCtrl, preAnnotRect, prePageNum);
/*      */         }
/*      */       } else {
/* 1315 */         List<Integer> pageNumbers = getAnnotPageNumbers(info);
/* 1316 */         List<Rect> annotRects = getAnnotRects(info);
/* 1317 */         if (pageNumbers != null && annotRects.size() == pageNumbers.size()) {
/* 1318 */           for (int i = 0, n = annotRects.size(); i < n; i++) {
/* 1319 */             Rect annotRect = annotRects.get(i);
/* 1320 */             int pageNum = ((Integer)pageNumbers.get(i)).intValue();
/* 1321 */             ViewerUtils.animateUndoRedo(pdfViewCtrl, annotRect, pageNum);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void removeUnsafeUndoRedoInfo(boolean isUndo) {
/* 1329 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1333 */     String info = null;
/*      */     try {
/*      */       while (true) {
/* 1336 */         info = null;
/* 1337 */         if (isUndo) {
/* 1338 */           info = this.mPdfViewCtrl.getNextUndoInfo();
/*      */         } else {
/* 1340 */           info = this.mPdfViewCtrl.getNextRedoInfo();
/*      */         } 
/* 1342 */         if (sDebug)
/* 1343 */           Log.e(TAG, "remove unsafe " + (isUndo ? "undo" : "redo") + " info: " + info); 
/* 1344 */         JSONObject jsonObject = new JSONObject(info);
/* 1345 */         if (!jsonObject.has("Action")) {
/*      */           break;
/*      */         }
/* 1348 */         String action = jsonObject.getString("Action");
/* 1349 */         if (Utils.isNullOrEmpty(action) || !action.equals("safety")) {
/*      */           break;
/*      */         }
/* 1352 */         if (isUndo) {
/* 1353 */           this.mPdfViewCtrl.undo(); continue;
/*      */         } 
/* 1355 */         this.mPdfViewCtrl.redo();
/*      */       }
/*      */     
/* 1358 */     } catch (Exception e) {
/* 1359 */       if (info == null || !info.equals("state not found")) {
/* 1360 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "next " + (isUndo ? "undo" : "redo") + " info: " + ((info == null) ? "null" : info));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public JSONObject getAnnotSnapshot(Annot annot, int page) {
/* 1367 */     Map<Annot, Integer> annots = new HashMap<>(1);
/* 1368 */     annots.put(annot, Integer.valueOf(page));
/* 1369 */     return prepareAnnotSnapshot(annots, null);
/*      */   }
/*      */   
/*      */   private JSONObject prepareAnnotSnapshot(Map<Annot, Integer> annots, AnnotAction annotAction) {
/* 1373 */     return prepareAnnotSnapshot(annots, annotAction, null, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private JSONObject prepareAnnotSnapshot(Map<Annot, Integer> annots, AnnotAction annotAction, Rect preModifyAnnotRect, int preModifyPageNum) {
/* 1378 */     if (annots == null || annots.size() == 0 || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/* 1379 */       return null;
/*      */     }
/*      */     
/*      */     try {
/* 1383 */       JSONObject jsonObj = new JSONObject();
/* 1384 */       String actionAnnotType = "";
/* 1385 */       String actionEventAnnotType = "";
/* 1386 */       String type = "";
/* 1387 */       if (this.mContext != null) {
/* 1388 */         for (Map.Entry<Annot, Integer> entry : annots.entrySet()) {
/* 1389 */           Annot annot = entry.getKey();
/* 1390 */           if (annot == null) {
/* 1391 */             AnalyticsHandlerAdapter.getInstance().sendException(new Exception("An entry of annots is null"), "annots: " + annots);
/*      */             
/* 1393 */             return null;
/*      */           } 
/* 1395 */           if (type.isEmpty()) {
/* 1396 */             type = AnnotUtils.getAnnotTypeAsString(annot);
/* 1397 */             if (annots.size() > 1) {
/* 1398 */               actionAnnotType = AnnotUtils.getAnnotTypeAsPluralString(this.mContext, annot);
/* 1399 */               actionEventAnnotType = AnnotUtils.getAnnotTypeAsPluralString(annot); continue;
/*      */             } 
/* 1401 */             actionAnnotType = AnnotUtils.getAnnotTypeAsString(this.mContext, annot);
/* 1402 */             actionEventAnnotType = AnnotUtils.getAnnotTypeAsString(annot); continue;
/*      */           } 
/* 1404 */           if (!type.equals(AnnotUtils.getAnnotTypeAsString(annot))) {
/* 1405 */             actionAnnotType = this.mContext.getResources().getString(R.string.annot_misc_plural);
/* 1406 */             actionEventAnnotType = "annotations";
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/* 1411 */         String action = "";
/* 1412 */         String actionEvent = "";
/* 1413 */         if (annotAction != null) {
/* 1414 */           switch (annotAction) {
/*      */             case ADD:
/* 1416 */               action = this.mContext.getResources().getString(R.string.add);
/* 1417 */               actionEvent = "add";
/*      */               break;
/*      */             case MODIFY:
/* 1420 */               action = this.mContext.getResources().getString(R.string.undo_redo_annot_modify);
/* 1421 */               actionEvent = "modify";
/*      */               break;
/*      */             case REMOVE:
/* 1424 */               action = this.mContext.getResources().getString(R.string.undo_redo_annot_remove);
/* 1425 */               actionEvent = "remove";
/*      */               break;
/*      */           } 
/*      */         }
/* 1429 */         if (!action.isEmpty()) {
/* 1430 */           jsonObj.put("Action", action + " " + actionAnnotType);
/*      */         }
/* 1432 */         if (!actionEvent.isEmpty()) {
/* 1433 */           jsonObj.put("Action event", actionEvent + "_" + actionEventAnnotType);
/*      */         }
/*      */       } 
/*      */       
/* 1437 */       String annotInfo = embedAnnotInfo(this.mPdfViewCtrl, annots, preModifyAnnotRect, preModifyPageNum);
/* 1438 */       jsonObj.put("Annot Info", annotInfo);
/* 1439 */       return jsonObj;
/* 1440 */     } catch (Exception e) {
/* 1441 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       
/* 1443 */       return null;
/*      */     } 
/*      */   }
/*      */   
/*      */   private String takeAnnotSnapshot(JSONObject jsonObj) {
/* 1448 */     if (jsonObj == null || !this.mPdfViewCtrl.isUndoRedoEnabled()) {
/* 1449 */       return null;
/*      */     }
/*      */     
/* 1452 */     String result = null;
/*      */     try {
/* 1454 */       if (Utils.isNullOrEmpty(jsonObj.toString())) {
/* 1455 */         AnalyticsHandlerAdapter.getInstance().sendException(new Exception("takeUndoSnapshot with an empty string"));
/*      */       }
/*      */       
/* 1458 */       result = takeUndoSnapshot(jsonObj.toString());
/* 1459 */       if (sDebug)
/* 1460 */         Log.d(TAG, "snapshot: " + jsonObj.toString()); 
/* 1461 */     } catch (Exception e) {
/* 1462 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/* 1464 */     return result;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String embedCollabAnnotInfo(String undoAction, String undoCommand, String redoAction, String redoCommand, String params) {
/* 1471 */     JSONObject result = new JSONObject();
/*      */     
/*      */     try {
/* 1474 */       result.put("collab_undo_action", undoAction);
/* 1475 */       result.put("collab_undo_command", undoCommand);
/*      */       
/* 1477 */       result.put("collab_redo_action", redoAction);
/* 1478 */       result.put("collab_redo_command", redoCommand);
/*      */       
/* 1480 */       if (!Utils.isNullOrEmpty(params)) {
/* 1481 */         result.put("collab_annot_params", params);
/*      */       }
/* 1483 */     } catch (Exception e) {
/* 1484 */       e.printStackTrace();
/*      */     } 
/* 1486 */     return result.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static String embedAnnotInfo(@NonNull PDFViewCtrl pdfViewCtrl, Map<Annot, Integer> annots, @Nullable Rect preAnnotRect, int prePageNum) {
/* 1491 */     if (annots == null || annots.size() == 0) {
/* 1492 */       return "";
/*      */     }
/*      */     
/* 1495 */     JSONObject result = new JSONObject();
/*      */     
/* 1497 */     StringBuilder sbRects = new StringBuilder();
/* 1498 */     StringBuilder sbPages = new StringBuilder();
/* 1499 */     int origAnnotType = -1;
/* 1500 */     for (Map.Entry<Annot, Integer> pair : annots.entrySet()) {
/* 1501 */       Annot annot = pair.getKey();
/* 1502 */       Integer pageNum = pair.getValue();
/*      */       try {
/* 1504 */         int annotType = annot.getType();
/* 1505 */         if (origAnnotType != -1 && origAnnotType != annotType) {
/* 1506 */           Log.e(TAG, "embedAnnotInfo: all annotations should be from the same type!");
/*      */           continue;
/*      */         } 
/* 1509 */         if (annot.isValid()) {
/* 1510 */           Rect annotRect; origAnnotType = annotType;
/*      */           
/* 1512 */           if (annot.getType() == 19) {
/*      */             
/* 1514 */             Widget widget = new Widget(annot);
/* 1515 */             Field field = widget.getField();
/* 1516 */             annotRect = field.getUpdateRect();
/*      */           } else {
/* 1518 */             annotRect = pdfViewCtrl.getPageRectForAnnot(annot, pageNum.intValue());
/*      */           } 
/* 1520 */           annotRect.normalize();
/* 1521 */           int x1 = (int)(annotRect.getX1() + 0.5D);
/* 1522 */           int x2 = (int)(annotRect.getX2() + 0.5D);
/* 1523 */           int y1 = (int)(annotRect.getY1() + 0.5D);
/* 1524 */           int y2 = (int)(annotRect.getY2() + 0.5D);
/* 1525 */           sbRects.append(x1).append(" ")
/* 1526 */             .append(y1)
/* 1527 */             .append(" ")
/* 1528 */             .append(x2)
/* 1529 */             .append(" ")
/* 1530 */             .append(y2)
/* 1531 */             .append(" ");
/* 1532 */           sbPages.append(pageNum.toString())
/* 1533 */             .append(" ");
/*      */         } 
/* 1535 */       } catch (Exception e) {
/* 1536 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */       } 
/*      */     } 
/*      */     
/* 1540 */     String rectList = sbRects.toString();
/* 1541 */     String pageList = sbPages.toString();
/*      */     try {
/* 1543 */       result.put("Page Numbers", pageList);
/* 1544 */       result.put("Rects", rectList);
/* 1545 */       if (preAnnotRect != null && prePageNum != 0) {
/*      */         try {
/* 1547 */           int x1 = (int)(preAnnotRect.getX1() + 0.5D);
/* 1548 */           int x2 = (int)(preAnnotRect.getX2() + 0.5D);
/* 1549 */           int y1 = (int)(preAnnotRect.getY1() + 0.5D);
/* 1550 */           int y2 = (int)(preAnnotRect.getY2() + 0.5D);
/* 1551 */           result.put("Rect Before Modification", x1 + " " + y1 + " " + x2 + " " + y2);
/* 1552 */           result.put("Page Number Before Modification", Integer.toString(prePageNum));
/* 1553 */         } catch (Exception e) {
/* 1554 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       }
/* 1557 */     } catch (JSONException e) {
/* 1558 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */     } 
/*      */     
/* 1561 */     return result.toString();
/*      */   }
/*      */   
/*      */   private static List<Integer> getAnnotPageNumbers(String info) {
/* 1565 */     List<Integer> pageNums = new ArrayList<>();
/*      */     
/* 1567 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1569 */         JSONObject jsonObj = new JSONObject(info);
/* 1570 */         String annotInfo = jsonObj.optString("Annot Info");
/* 1571 */         JSONObject annotObj = new JSONObject(annotInfo);
/* 1572 */         String str = annotObj.optString("Page Numbers");
/* 1573 */         if (!Utils.isNullOrEmpty(str)) {
/* 1574 */           String[] pages = str.split(" ");
/* 1575 */           for (String page : pages) {
/* 1576 */             pageNums.add(Integer.valueOf(page));
/*      */           }
/*      */         } 
/* 1579 */       } catch (Exception e) {
/* 1580 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */       } 
/*      */     }
/*      */     
/* 1584 */     return pageNums;
/*      */   }
/*      */   
/*      */   private static int getPreModifiedAnnotPageNumber(String info) {
/* 1588 */     int pageNum = 0;
/*      */     
/* 1590 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1592 */         JSONObject jsonObj = new JSONObject(info);
/* 1593 */         String annotInfo = jsonObj.optString("Annot Info");
/* 1594 */         JSONObject annotObj = new JSONObject(annotInfo);
/* 1595 */         String str = annotObj.optString("Page Number Before Modification");
/* 1596 */         if (!Utils.isNullOrEmpty(str)) {
/* 1597 */           pageNum = Integer.valueOf(str).intValue();
/*      */         }
/* 1599 */       } catch (Exception e) {
/* 1600 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */       } 
/*      */     }
/*      */     
/* 1604 */     return pageNum;
/*      */   }
/*      */   
/*      */   @NonNull
/*      */   private static List<Rect> getAnnotRects(String info) {
/* 1609 */     List<Rect> annotRects = new ArrayList<>();
/*      */     
/* 1611 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1613 */         JSONObject jsonObj = new JSONObject(info);
/* 1614 */         String annotInfo = jsonObj.optString("Annot Info");
/* 1615 */         JSONObject annotObj = new JSONObject(annotInfo);
/* 1616 */         String str = annotObj.optString("Rects");
/* 1617 */         if (!Utils.isNullOrEmpty(str)) {
/* 1618 */           String[] coords = str.split(" ");
/* 1619 */           int count = coords.length / 4;
/* 1620 */           for (int i = 0; i < count; i++) {
/* 1621 */             int x1 = Integer.valueOf(coords[i * 4]).intValue();
/* 1622 */             int y1 = Integer.valueOf(coords[i * 4 + 1]).intValue();
/* 1623 */             int x2 = Integer.valueOf(coords[i * 4 + 2]).intValue();
/* 1624 */             int y2 = Integer.valueOf(coords[i * 4 + 3]).intValue();
/* 1625 */             annotRects.add(new Rect(x1, y1, x2, y2));
/*      */           } 
/*      */         } 
/* 1628 */       } catch (Exception e) {
/* 1629 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */       } 
/*      */     }
/*      */     
/* 1633 */     return annotRects;
/*      */   }
/*      */   
/*      */   private static Rect getPreModifiedAnnotRect(String info) {
/* 1637 */     Rect annotRect = null;
/*      */     
/* 1639 */     if (!Utils.isNullOrEmpty(info)) {
/*      */       try {
/* 1641 */         JSONObject jsonObj = new JSONObject(info);
/* 1642 */         String annotInfo = jsonObj.optString("Annot Info");
/* 1643 */         JSONObject annotObj = new JSONObject(annotInfo);
/* 1644 */         String str = annotObj.optString("Rect Before Modification");
/* 1645 */         if (!Utils.isNullOrEmpty(str)) {
/* 1646 */           String[] coords = str.split(" ");
/* 1647 */           if (coords.length == 4) {
/* 1648 */             int x1 = Integer.valueOf(coords[0]).intValue();
/* 1649 */             int y1 = Integer.valueOf(coords[1]).intValue();
/* 1650 */             int x2 = Integer.valueOf(coords[2]).intValue();
/* 1651 */             int y2 = Integer.valueOf(coords[3]).intValue();
/* 1652 */             annotRect = new Rect(x1, y1, x2, y2);
/*      */           } 
/*      */         } 
/* 1655 */       } catch (Exception e) {
/* 1656 */         AnalyticsHandlerAdapter.getInstance().sendException(e, "info: " + info);
/*      */       } 
/*      */     }
/*      */     
/* 1660 */     return annotRect;
/*      */   }
/*      */   
/*      */   private static String convertPageListToString(List<Integer> pageList) {
/* 1664 */     StringBuilder pages = new StringBuilder();
/*      */     
/* 1666 */     if (pageList == null || pageList.size() == 0) {
/* 1667 */       return pages.toString();
/*      */     }
/*      */     
/* 1670 */     boolean start = true;
/* 1671 */     for (Iterator<Integer> iterator = pageList.iterator(); iterator.hasNext(); ) { int position = ((Integer)iterator.next()).intValue();
/* 1672 */       pages.append(start ? "" : " ").append(position);
/* 1673 */       start = false; }
/*      */     
/* 1675 */     return pages.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isValidAction(Context context, String action) {
/* 1680 */     if (context != null && !Utils.isNullOrEmpty(action)) {
/* 1681 */       String strBookmarkModify = context.getResources().getString(R.string.undo_redo_bookmark_modify);
/* 1682 */       String strCropPages = context.getResources().getString(R.string.pref_viewmode_user_crop);
/* 1683 */       String strPageAdd = context.getResources().getString(R.string.undo_redo_page_add);
/* 1684 */       String strPageDelete = context.getResources().getString(R.string.undo_redo_page_delete);
/* 1685 */       String strPageRotate = context.getResources().getString(R.string.undo_redo_page_rotate);
/* 1686 */       String strPageMove = context.getResources().getString(R.string.undo_redo_page_move);
/* 1687 */       String strAnnotAdd = context.getResources().getString(R.string.add);
/* 1688 */       String strAnnotModify = context.getResources().getString(R.string.undo_redo_annot_modify);
/* 1689 */       String strAnnotRemove = context.getResources().getString(R.string.undo_redo_annot_remove);
/* 1690 */       String strAnnotsRemove = context.getResources().getString(R.string.undo_redo_annots_remove);
/* 1691 */       String strAnnotAction = context.getResources().getString(R.string.undo_redo_annot_action);
/* 1692 */       String strRedactionAction = context.getResources().getString(R.string.undo_redo_redaction);
/*      */       
/* 1694 */       return (action.equals(strBookmarkModify) || action.equals(strCropPages) || action
/* 1695 */         .equals(strPageAdd) || action.equals(strPageDelete) || action
/* 1696 */         .equals(strPageRotate) || action.equals(strPageMove) || action
/* 1697 */         .contains(strAnnotAdd) || action.contains(strAnnotModify) || action
/* 1698 */         .contains(strAnnotRemove) || action.equals(strAnnotsRemove) || action
/* 1699 */         .contains(strAnnotAction) || action.equals(strRedactionAction));
/*      */     } 
/*      */     
/* 1702 */     return false;
/*      */   }
/*      */   
/*      */   private void updatePageLayout(String info) {
/* 1706 */     if (!this.mPdfViewCtrl.isUndoRedoEnabled()) {
/*      */       return;
/*      */     }
/*      */     
/* 1710 */     if (sDebug)
/* 1711 */       Log.d(TAG, "update page layout after undo/redo"); 
/* 1712 */     if (isEditPageAction(this.mContext, info)) {
/*      */       try {
/* 1714 */         this.mPdfViewCtrl.updatePageLayout();
/* 1715 */       } catch (PDFNetException e) {
/* 1716 */         AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   String takeUndoSnapshot(String info) throws PDFNetException {
/* 1722 */     boolean shouldUnlock = false;
/*      */     try {
/* 1724 */       this.mPdfViewCtrl.docLock(false);
/* 1725 */       shouldUnlock = true;
/* 1726 */       String result = this.mPdfViewCtrl.takeUndoSnapshot(info);
/* 1727 */       this.mPdfViewCtrl.requestRendering();
/* 1728 */       return result;
/*      */     } finally {
/* 1730 */       if (shouldUnlock) {
/* 1731 */         this.mPdfViewCtrl.docUnlock();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void setDebug(boolean debug) {
/* 1737 */     sDebug = debug;
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\UndoRedoManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */