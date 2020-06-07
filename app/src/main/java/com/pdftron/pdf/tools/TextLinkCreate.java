/*    */ package com.pdftron.pdf.tools;
/*    */ 
/*    */ import android.graphics.RectF;
/*    */ import android.util.SparseArray;
/*    */ import android.view.MotionEvent;
/*    */ import androidx.annotation.Keep;
/*    */ import androidx.annotation.Nullable;
/*    */ import com.pdftron.common.PDFNetException;
/*    */ import com.pdftron.pdf.Annot;
/*    */ import com.pdftron.pdf.PDFDoc;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.Rect;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Keep
/*    */ public class TextLinkCreate
/*    */   extends TextMarkupCreate
/*    */ {
/*    */   private SparseArray<RectF> mPageSelectedRects;
/*    */   
/*    */   public TextLinkCreate(PDFViewCtrl ctrl) {
/* 35 */     super(ctrl);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ToolManager.ToolModeBase getToolMode() {
/* 43 */     return ToolManager.ToolMode.TEXT_LINK_CREATE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCreateAnnotType() {
/* 48 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onDown(MotionEvent e) {
/* 53 */     if (this.mPageSelectedRects != null) {
/* 54 */       this.mPageSelectedRects.clear();
/*    */     }
/* 56 */     return super.onDown(e);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Annot createMarkup(PDFDoc doc, Rect bbox) throws PDFNetException {
/* 61 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setAnnotRect(@Nullable Annot annot, Rect rect, int pageNum) throws PDFNetException {
/* 66 */     RectF pageRect = new RectF();
/* 67 */     if (this.mPageSelectedRects != null && this.mPageSelectedRects.indexOfKey(pageNum) > -1) {
/* 68 */       pageRect = (RectF)this.mPageSelectedRects.get(pageNum);
/* 69 */     } else if (this.mPageSelectedRects == null) {
/* 70 */       this.mPageSelectedRects = new SparseArray();
/*    */     } 
/* 72 */     pageRect.union((float)rect.getX1(), (float)rect.getY1(), (float)rect.getX2(), (float)rect.getY2());
/* 73 */     this.mPageSelectedRects.put(pageNum, pageRect);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean onQuickMenuClicked(QuickMenuItem menuItem) {
/* 78 */     super.onQuickMenuClicked(menuItem);
/* 79 */     if (this.mPageSelectedRects == null || this.mPageSelectedRects.size() == 0) {
/* 80 */       return true;
/*    */     }
/* 82 */     HashMap<Rect, Integer> selRect = new HashMap<>();
/*    */     try {
/* 84 */       for (int i = 0; i < this.mPageSelectedRects.size(); i++) {
/* 85 */         int pageNum = this.mPageSelectedRects.keyAt(i);
/* 86 */         RectF pageRect = (RectF)this.mPageSelectedRects.get(pageNum);
/* 87 */         selRect.put(new Rect(pageRect.left, pageRect.top, pageRect.right, pageRect.bottom), Integer.valueOf(pageNum));
/*    */       } 
/* 89 */     } catch (PDFNetException e) {
/* 90 */       AnalyticsHandlerAdapter.getInstance().sendException((Exception)e);
/*    */     } 
/* 92 */     DialogLinkEditor linkEditor = new DialogLinkEditor(this.mPdfViewCtrl, this, selRect);
/* 93 */     linkEditor.setColor(this.mColor);
/* 94 */     linkEditor.setThickness(this.mThickness);
/* 95 */     linkEditor.show();
/*    */     
/* 97 */     this.mNextToolMode = ToolManager.ToolMode.PAN;
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\TextLinkCreate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */