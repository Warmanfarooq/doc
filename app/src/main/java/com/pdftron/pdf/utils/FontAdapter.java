/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.model.FontResource;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ public class FontAdapter
/*     */   extends ArrayAdapter<FontResource>
/*     */ {
/*     */   private Context mContext;
/*     */   private List<FontResource> mSource;
/*     */   private int mLayoutResourceId;
/*     */   private int mDropDownResourceId;
/*     */   
/*     */   public FontAdapter(Context context, int textViewResource, List<FontResource> list) {
/*  32 */     super(context, textViewResource, list);
/*     */     
/*  34 */     this.mContext = context;
/*  35 */     this.mSource = list;
/*  36 */     this.mLayoutResourceId = textViewResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDropDownViewResource(int resource) {
/*  41 */     super.setDropDownViewResource(resource);
/*  42 */     this.mDropDownResourceId = resource;
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*  48 */     if (convertView == null) {
/*  49 */       convertView = LayoutInflater.from(this.mContext).inflate(this.mLayoutResourceId, parent, false);
/*     */     }
/*     */     
/*  52 */     if (convertView instanceof TextView) {
/*     */       
/*  54 */       TextView textView = (TextView)convertView;
/*  55 */       FontResource font = this.mSource.get(position);
/*  56 */       textView.setText(font.getDisplayName());
/*     */     } 
/*     */     
/*  59 */     return convertView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
/*  66 */     convertView = LayoutInflater.from(this.mContext).inflate(this.mDropDownResourceId, parent, false);
/*     */     
/*  68 */     if (convertView instanceof TextView) {
/*     */       
/*  70 */       TextView textView = (TextView)convertView;
/*  71 */       FontResource font = this.mSource.get(position);
/*  72 */       textView.setText(font.getDisplayName());
/*     */     } 
/*     */     
/*  75 */     if (position == 0) {
/*  76 */       return new View(this.mContext);
/*     */     }
/*  78 */     return convertView;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPosition(FontResource font) {
/*  83 */     if (font == null) {
/*  84 */       return -1;
/*     */     }
/*     */     
/*  87 */     String filePath = font.getFilePath();
/*  88 */     for (int i = 0; i < this.mSource.size(); i++) {
/*  89 */       if (((FontResource)this.mSource.get(i)).getFilePath().equals(filePath)) {
/*  90 */         return i;
/*     */       }
/*     */     } 
/*  93 */     return -1;
/*     */   }
/*     */   
/*     */   public void setData(List<FontResource> data) {
/*  97 */     if (this.mSource == null) {
/*  98 */       this.mSource = new ArrayList<>();
/*     */     }
/* 100 */     this.mSource.clear();
/* 101 */     this.mSource.addAll(data);
/* 102 */     notifyDataSetChanged();
/*     */   }
/*     */   
/*     */   public List<FontResource> getData() {
/* 106 */     if (this.mSource == null) {
/* 107 */       this.mSource = new ArrayList<>();
/*     */     }
/* 109 */     return this.mSource;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\FontAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */