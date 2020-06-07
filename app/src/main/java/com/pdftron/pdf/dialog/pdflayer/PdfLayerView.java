/*    */ package com.pdftron.pdf.dialog.pdflayer;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.util.AttributeSet;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.LinearLayout;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.recyclerview.widget.LinearLayoutManager;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*    */ import com.pdftron.pdf.widget.recyclerview.SimpleRecyclerView;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfLayerView
/*    */   extends LinearLayout
/*    */ {
/*    */   private SimpleRecyclerView mRecyclerView;
/*    */   private PdfLayerViewAdapter mAdapter;
/*    */   private ItemClickHelper mItemClickHelper;
/*    */   
/*    */   public PdfLayerView(Context context) {
/* 28 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public PdfLayerView(Context context, @Nullable AttributeSet attrs) {
/* 32 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public PdfLayerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
/* 36 */     super(context, attrs, defStyleAttr);
/* 37 */     init();
/*    */   }
/*    */   
/*    */   private void init() {
/* 41 */     LayoutInflater.from(getContext()).inflate(R.layout.view_pdf_layer, (ViewGroup)this);
/* 42 */     setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
/* 43 */     setOrientation(1);
/*    */     
/* 45 */     this.mRecyclerView = (SimpleRecyclerView)findViewById(R.id.layer_list);
/* 46 */     this.mRecyclerView.setLayoutManager((RecyclerView.LayoutManager)new LinearLayoutManager(getContext()));
/* 47 */     this.mRecyclerView.setHasFixedSize(true);
/* 48 */     this.mItemClickHelper = new ItemClickHelper();
/* 49 */     this.mItemClickHelper.attachToRecyclerView((RecyclerView)this.mRecyclerView);
/*    */   }
/*    */   
/*    */   public void setup(@NonNull ArrayList<PdfLayerUtils.LayerInfo> layers) {
/* 53 */     this.mAdapter = new PdfLayerViewAdapter(layers);
/* 54 */     this.mRecyclerView.setAdapter(this.mAdapter);
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public SimpleRecyclerView getRecyclerView() {
/* 59 */     return this.mRecyclerView;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public PdfLayerViewAdapter getAdapter() {
/* 64 */     return this.mAdapter;
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public ItemClickHelper getItemClickHelper() {
/* 69 */     return this.mItemClickHelper;
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pdflayer\PdfLayerView.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */