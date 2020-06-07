/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.text.SpannableStringBuilder;
/*     */ import android.text.style.BackgroundColorSpan;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import com.pdftron.pdf.TextSearchResult;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class SearchResultsAdapter
/*     */   extends RecyclerView.Adapter<RecyclerView.ViewHolder>
/*     */ {
/*     */   protected int mLayoutResourceId;
/*     */   protected ArrayList<TextSearchResult> mResults;
/*     */   private ArrayList<String> mSectionTitles;
/*     */   private boolean mIsRtlMode;
/*     */   private boolean mIsNightMode;
/*     */   private boolean mWholeWord;
/*     */   
/*     */   public SearchResultsAdapter(Context context, int resource, ArrayList<TextSearchResult> objects, ArrayList<String> titles) {
/*  51 */     this.mLayoutResourceId = resource;
/*  52 */     this.mResults = objects;
/*  53 */     this.mSectionTitles = titles;
/*  54 */     this.mIsRtlMode = false;
/*  55 */     this.mIsNightMode = Utils.isDeviceNightMode(context);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWholeWord(boolean wholeWord) {
/*  63 */     this.mWholeWord = wholeWord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TextSearchResult getItem(int position) {
/*  74 */     if (this.mResults != null && position >= 0 && position < this.mResults.size()) {
/*  75 */       return this.mResults.get(position);
/*     */     }
/*  77 */     return null;
/*     */   }
/*     */   
/*     */   private SpannableStringBuilder formatResultText(Context context, TextSearchResult result) {
/*  81 */     String match = result.getResultStr();
/*  82 */     String ambient = result.getAmbientStr();
/*     */ 
/*     */     
/*  85 */     ambient = Utils.getBidiString(ambient);
/*  86 */     match = Utils.getBidiString(match);
/*     */     
/*  88 */     String processMatch = match;
/*  89 */     if (this.mWholeWord)
/*     */     {
/*  91 */       processMatch = " " + match + " ";
/*     */     }
/*     */     
/*  94 */     int start = ambient.indexOf(processMatch);
/*  95 */     int end = start + processMatch.length();
/*     */     
/*  97 */     if (this.mWholeWord) {
/*     */       
/*  99 */       start++;
/* 100 */       end--;
/*     */     } 
/*     */     
/* 103 */     if (start < 0 || end > ambient.length() || start > end) {
/*     */       
/* 105 */       AnalyticsHandlerAdapter.getInstance().sendException(new Exception("start/end of result text is invalid -> match: " + match + ", ambient: " + ambient + ", start: " + start + "end:" + end));
/*     */       
/* 107 */       start = end = 0;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 112 */     int color = this.mIsNightMode ? context.getResources().getColor(R.color.tools_text_highlighter_highlight_color_inverse) : context.getResources().getColor(R.color.tools_text_highlighter_highlight_color);
/* 113 */     SpannableStringBuilder builder = new SpannableStringBuilder(ambient);
/* 114 */     builder.setSpan(new BackgroundColorSpan(color), start, end, 33);
/* 115 */     return builder;
/*     */   }
/*     */   
/*     */   private String formatSectionTitle(int position) {
/* 119 */     if (!this.mSectionTitles.isEmpty()) {
/* 120 */       String text = this.mSectionTitles.get(position);
/* 121 */       if (text != null) {
/* 122 */         return text;
/*     */       }
/*     */     } 
/*     */     
/* 126 */     return "";
/*     */   }
/*     */   
/*     */   void setRtlMode(boolean isRtlMode) {
/* 130 */     this.mIsRtlMode = isRtlMode;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/* 136 */     return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(this.mLayoutResourceId, parent, false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int position) {
/* 141 */     if (vh instanceof ViewHolder) {
/* 142 */       ViewHolder holder = (ViewHolder)vh;
/* 143 */       if (Utils.isJellyBeanMR1()) {
/* 144 */         holder.mSectionTitle.setTextDirection(5);
/* 145 */         holder.mPageNumber.setTextDirection(5);
/* 146 */         if (Utils.isRtlLayout(holder.mSectionTitle.getContext())) {
/* 147 */           holder.mSectionTitle.setLayoutDirection(1);
/* 148 */           holder.mPageNumber.setTextDirection(3);
/*     */         } else {
/* 150 */           holder.mSectionTitle.setLayoutDirection(0);
/* 151 */           holder.mPageNumber.setTextDirection(4);
/*     */         } 
/* 153 */         if (this.mIsRtlMode) {
/* 154 */           holder.mSearchText.setTextDirection(4);
/* 155 */           holder.mSearchText.setLayoutDirection(1);
/*     */         } else {
/* 157 */           holder.mSearchText.setTextDirection(3);
/* 158 */           holder.mSearchText.setLayoutDirection(0);
/*     */         } 
/*     */       } 
/*     */       
/* 162 */       TextSearchResult result = getItem(position);
/* 163 */       if (result != null) {
/* 164 */         holder.mSearchText.setText((CharSequence)formatResultText(holder.mSearchText.getContext(), result));
/* 165 */         holder.mPageNumber.setText(holder.mPageNumber.getContext().getResources().getString(R.string.controls_annotation_dialog_page, new Object[] { Integer.valueOf(result.getPageNum()) }));
/* 166 */         holder.mSectionTitle.setText(formatSectionTitle(position));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 173 */     if (this.mResults != null) {
/* 174 */       return this.mResults.size();
/*     */     }
/* 176 */     return 0;
/*     */   }
/*     */   
/*     */   public static class ViewHolder
/*     */     extends RecyclerView.ViewHolder {
/*     */     TextView mSectionTitle;
/*     */     TextView mPageNumber;
/*     */     TextView mSearchText;
/*     */     
/*     */     public ViewHolder(@NonNull View itemView) {
/* 186 */       super(itemView);
/*     */       
/* 188 */       this.mSectionTitle = (TextView)itemView.findViewById(R.id.section_title);
/* 189 */       this.mPageNumber = (TextView)itemView.findViewById(R.id.page_number);
/* 190 */       this.mSearchText = (TextView)itemView.findViewById(R.id.search_text);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\SearchResultsAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */