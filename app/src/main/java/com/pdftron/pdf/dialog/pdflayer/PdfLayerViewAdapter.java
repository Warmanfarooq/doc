/*    */ package com.pdftron.pdf.dialog.pdflayer;
/*    */ 
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.annotation.Nullable;
/*    */ import androidx.appcompat.widget.SwitchCompat;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.ocg.Group;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PdfLayerViewAdapter
/*    */   extends RecyclerView.Adapter<PdfLayerViewAdapter.ViewHolder>
/*    */ {
/*    */   @NonNull
/*    */   private final ArrayList<PdfLayerUtils.LayerInfo> mLayers;
/*    */   
/*    */   public PdfLayerViewAdapter(@NonNull ArrayList<PdfLayerUtils.LayerInfo> layers) {
/* 25 */     this.mLayers = layers;
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
/* 31 */     View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_pdf_layer_item, viewGroup, false);
/* 32 */     return new ViewHolder(view);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
/*    */     try {
/* 38 */       Group group = ((PdfLayerUtils.LayerInfo)this.mLayers.get(i)).group;
/* 39 */       boolean checked = ((PdfLayerUtils.LayerInfo)this.mLayers.get(i)).checked;
/* 40 */       viewHolder.mSwitch.setText(group.getName());
/* 41 */       viewHolder.mSwitch.setChecked(checked);
/* 42 */     } catch (Exception ex) {
/* 43 */       ex.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public PdfLayerUtils.LayerInfo getItem(int i) {
/* 49 */     return this.mLayers.get(i);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemCount() {
/* 54 */     return this.mLayers.size();
/*    */   }
/*    */   
/*    */   static class ViewHolder
/*    */     extends RecyclerView.ViewHolder {
/*    */     SwitchCompat mSwitch;
/*    */     
/*    */     public ViewHolder(@NonNull View itemView) {
/* 62 */       super(itemView);
/* 63 */       this.mSwitch = (SwitchCompat)itemView.findViewById(R.id.layer_switch);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\pdflayer\PdfLayerViewAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */