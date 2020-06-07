/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.annotation.SuppressLint;
/*     */ import android.content.Context;
/*     */ import android.database.DataSetObserver;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.FloatRange;
/*     */ import androidx.annotation.NonNull;
/*     */ import com.pdftron.pdf.model.AnnotStyle;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IconPickerGridViewAdapter
/*     */   extends ArrayAdapter<String>
/*     */ {
/*     */   private Context mContext;
/*     */   private String mSelected;
/*     */   private List<String> mSource;
/*     */   private int mIconColor;
/*     */   private float mIconOpacity;
/*     */   
/*     */   public IconPickerGridViewAdapter(Context context, List<String> list) {
/*  46 */     super(context, 0, list);
/*     */     
/*  48 */     this.mContext = context;
/*  49 */     this.mSelected = "";
/*  50 */     this.mSource = list;
/*  51 */     this.mIconColor = 0;
/*  52 */     this.mIconOpacity = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/*  61 */     return this.mSource.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItem(int position) {
/*  71 */     if (this.mSource != null && position >= 0 && position < this.mSource.size()) {
/*  72 */       return this.mSource.get(position);
/*     */     }
/*  74 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getItemIndex(String icon) {
/*  84 */     for (int i = 0; i < this.mSource.size(); i++) {
/*  85 */       if (icon.equals(this.mSource.get(i))) {
/*  86 */         return i;
/*     */       }
/*     */     } 
/*  89 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelected() {
/*  98 */     return this.mSelected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(int position) {
/* 107 */     if (position > -1) {
/* 108 */       this.mSelected = getItem(position);
/*     */     } else {
/* 110 */       this.mSelected = "";
/*     */     } 
/* 112 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   @SuppressLint({"InflateParams"})
/*     */   public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*     */     ViewHolder holder;
/* 123 */     if (convertView == null) {
/*     */       
/* 125 */       convertView = LayoutInflater.from(getContext()).inflate(R.layout.tools_gridview_icon_picker, parent, false);
/*     */       
/* 127 */       RelativeLayout layout = (RelativeLayout)convertView.findViewById(R.id.cell_layout);
/* 128 */       ImageView imageView = (ImageView)convertView.findViewById(R.id.icon_image_view);
/*     */       
/* 130 */       holder = new ViewHolder();
/* 131 */       holder.mIconLayout = layout;
/* 132 */       holder.mIconImage = imageView;
/* 133 */       convertView.setTag(holder);
/*     */     } else {
/* 135 */       holder = (ViewHolder)convertView.getTag();
/*     */     } 
/*     */     
/* 138 */     String iconOutline = "annotation_note_icon_" + ((String)this.mSource.get(position)).toLowerCase() + "_outline";
/*     */     
/* 140 */     int iconOutlineID = this.mContext.getResources().getIdentifier(iconOutline, "drawable", this.mContext.getPackageName());
/*     */ 
/*     */     
/* 143 */     int r = Color.red(this.mIconColor);
/* 144 */     int g = Color.green(this.mIconColor);
/* 145 */     int b = Color.blue(this.mIconColor);
/* 146 */     int a = (int)(this.mIconOpacity * 255.0F);
/* 147 */     int color = Color.argb(a, r, g, b);
/*     */ 
/*     */     
/* 150 */     if (!this.mSelected.equals("")) {
/*     */       try {
/* 152 */         if (this.mSelected.equals(getItem(position))) {
/* 153 */           holder.mIconImage.setImageDrawable(AnnotStyle.getIconDrawable(getContext(), ((String)this.mSource.get(position)).toLowerCase(), color, 1.0F));
/*     */         } else {
/* 155 */           holder.mIconImage.setImageDrawable(this.mContext.getResources().getDrawable(iconOutlineID));
/* 156 */           if (Utils.isDeviceNightMode(getContext())) {
/* 157 */             holder.mIconImage.getDrawable().mutate();
/* 158 */             holder.mIconImage.getDrawable().setColorFilter(getContext().getResources().getColor(R.color.gray400), PorterDuff.Mode.SRC_IN);
/*     */           } 
/*     */         } 
/* 161 */       } catch (Exception e) {
/* 162 */         AnalyticsHandlerAdapter.getInstance().sendException(e);
/*     */       } 
/*     */     }
/* 165 */     return convertView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateIconColor(@ColorInt int color) {
/* 174 */     this.mIconColor = color;
/* 175 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateIconOpacity(@FloatRange(from = 0.0D, to = 1.0D) float opacity) {
/* 184 */     this.mIconOpacity = opacity;
/* 185 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ViewHolder
/*     */   {
/*     */     RelativeLayout mIconLayout;
/*     */ 
/*     */     
/*     */     ImageView mIconImage;
/*     */ 
/*     */     
/*     */     private ViewHolder() {}
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterDataSetObserver(DataSetObserver observer) {
/* 203 */     if (observer != null)
/* 204 */       super.unregisterDataSetObserver(observer); 
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\IconPickerGridViewAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */