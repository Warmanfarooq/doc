/*    */ package com.pdftron.pdf.dialog.pdflayer;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.os.Bundle;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.recyclerview.widget.GridLayoutManager;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.google.android.material.bottomsheet.BottomSheetDialog;
/*    */ import com.pdftron.pdf.PDFViewCtrl;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*    */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfLayerDialog
/*    */   extends BottomSheetDialog
/*    */ {
/*    */   private PdfLayerView mPdfLayerView;
/*    */   private PDFViewCtrl mPdfViewCtrl;
/*    */   private ArrayList<PdfLayerUtils.LayerInfo> mLayers;
/*    */   
/*    */   public PdfLayerDialog(@NonNull Context context, PDFViewCtrl pdfViewCtrl) {
/* 27 */     super(context);
/* 28 */     init(context);
/*    */     
/* 30 */     this.mPdfViewCtrl = pdfViewCtrl;
/*    */   }
/*    */   
/*    */   private void init(@NonNull Context context) {
/* 34 */     View view = LayoutInflater.from(context).inflate(R.layout.controls_pdf_layers_layout, null);
/* 35 */     this.mPdfLayerView = (PdfLayerView)view.findViewById(R.id.pdf_layer_view);
/* 36 */     setContentView(view);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onCreate(Bundle savedInstanceState) {
/* 41 */     super.onCreate(savedInstanceState);
/*    */     
/* 43 */     boolean shouldUnlockRead = false;
/*    */     try {
/* 45 */       this.mPdfViewCtrl.docLockRead();
/* 46 */       shouldUnlockRead = true;
/* 47 */       this.mLayers = PdfLayerUtils.getLayers(this.mPdfViewCtrl, this.mPdfViewCtrl.getDoc());
/* 48 */     } catch (Exception ex) {
/* 49 */       AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*    */     } finally {
/* 51 */       if (shouldUnlockRead) {
/* 52 */         this.mPdfViewCtrl.docUnlockRead();
/*    */       }
/*    */     } 
/* 55 */     this.mPdfLayerView.setup(this.mLayers);
/* 56 */     this.mPdfLayerView.getRecyclerView().setLayoutManager((RecyclerView.LayoutManager)new GridLayoutManager(
/* 57 */           getContext(), 2));
/* 58 */     this.mPdfLayerView.getItemClickHelper().setOnItemClickListener(new ItemClickHelper.OnItemClickListener()
/*    */         {
/*    */           public void onItemClick(RecyclerView recyclerView, View view, int position, long id) {
/* 61 */             PdfLayerViewAdapter adapter = PdfLayerDialog.this.mPdfLayerView.getAdapter();
/* 62 */             if (adapter != null) {
/* 63 */               PdfLayerUtils.LayerInfo layer = adapter.getItem(position);
/* 64 */               if (layer != null) {
/* 65 */                 layer.checked = !layer.checked;
/* 66 */                 adapter.notifyItemChanged(position);
/*    */                 try {
/* 68 */                   PdfLayerUtils.setLayerCheckedChange(PdfLayerDialog.this.mPdfViewCtrl, layer.group, layer.checked);
/* 69 */                 } catch (Exception ex) {
/* 70 */                   AnalyticsHandlerAdapter.getInstance().sendException(ex);
/*    */                 } 
/*    */               } 
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pdflayer\PdfLayerDialog.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */