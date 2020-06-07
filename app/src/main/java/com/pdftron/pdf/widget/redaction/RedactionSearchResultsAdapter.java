/*    */ package com.pdftron.pdf.widget.redaction;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.View;
/*    */ import android.view.ViewGroup;
/*    */ import android.widget.CheckBox;
/*    */ import androidx.annotation.NonNull;
/*    */ import androidx.recyclerview.widget.RecyclerView;
/*    */ import com.pdftron.pdf.TextSearchResult;
/*    */ import com.pdftron.pdf.controls.SearchResultsAdapter;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashSet;
/*    */ 
/*    */ 
/*    */ public class RedactionSearchResultsAdapter
/*    */   extends SearchResultsAdapter
/*    */ {
/* 20 */   private final HashSet<Integer> mSelectedList = new HashSet<>();
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
/*    */   public RedactionSearchResultsAdapter(Context context, int resource, ArrayList<TextSearchResult> objects, ArrayList<String> titles) {
/* 32 */     super(context, resource, objects, titles);
/*    */   }
/*    */ 
/*    */   
/*    */   @NonNull
/*    */   public SearchResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/* 38 */     return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
/* 43 */     super.onBindViewHolder(vh, position);
/*    */     
/* 45 */     if (vh instanceof ViewHolder) {
/* 46 */       ViewHolder holder = (ViewHolder)vh;
/* 47 */       if (this.mSelectedList.contains(Integer.valueOf(position))) {
/* 48 */         holder.mCheckBox.setChecked(true);
/*    */       } else {
/* 50 */         holder.mCheckBox.setChecked(false);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addSelected(int position) {
/* 56 */     this.mSelectedList.add(Integer.valueOf(position));
/* 57 */     notifyItemChanged(position);
/*    */   }
/*    */   
/*    */   public void removeSelected(int position) {
/* 61 */     this.mSelectedList.remove(Integer.valueOf(position));
/* 62 */     notifyItemChanged(position);
/*    */   }
/*    */   
/*    */   public boolean isSelected(int position) {
/* 66 */     return this.mSelectedList.contains(Integer.valueOf(position));
/*    */   }
/*    */   
/*    */   public boolean isAllSelected() {
/* 70 */     return (this.mSelectedList.size() == this.mResults.size());
/*    */   }
/*    */   
/*    */   public void selectAll() {
/* 74 */     this.mSelectedList.clear();
/* 75 */     for (int i = 0; i < this.mResults.size(); i++) {
/* 76 */       this.mSelectedList.add(Integer.valueOf(i));
/*    */     }
/* 78 */     notifyDataSetChanged();
/*    */   }
/*    */   
/*    */   public void deselectAll() {
/* 82 */     this.mSelectedList.clear();
/* 83 */     notifyDataSetChanged();
/*    */   }
/*    */   
/*    */   @NonNull
/*    */   public HashSet<Integer> getSelected() {
/* 88 */     return this.mSelectedList;
/*    */   }
/*    */   
/*    */   static class ViewHolder extends SearchResultsAdapter.ViewHolder {
/*    */     CheckBox mCheckBox;
/*    */     
/*    */     public ViewHolder(@NonNull View itemView) {
/* 95 */       super(itemView);
/*    */       
/* 97 */       this.mCheckBox = (CheckBox)itemView.findViewById(R.id.checkbox);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\redaction\RedactionSearchResultsAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */