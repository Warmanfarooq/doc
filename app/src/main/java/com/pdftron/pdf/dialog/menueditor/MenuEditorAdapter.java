/*     */ package com.pdftron.pdf.dialog.menueditor;
/*     */ 
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.appcompat.widget.AppCompatImageView;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.ItemTouchHelperAdapter;
/*     */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItem;
/*     */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemContent;
/*     */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItemHeader;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuEditorAdapter
/*     */   extends RecyclerView.Adapter<RecyclerView.ViewHolder>
/*     */   implements ItemTouchHelperAdapter
/*     */ {
/*     */   public static final int VIEW_TYPE_HEADER = 1;
/*     */   public static final int VIEW_TYPE_CONTENT = 0;
/*  28 */   private final ArrayList<MenuEditorItem> mMenuEditorItems = new ArrayList<>();
/*     */   
/*     */   private MenuEditorViewModel mViewModel;
/*     */   
/*     */   private boolean mDragging;
/*     */ 
/*     */   
/*     */   public MenuEditorAdapter() {}
/*     */   
/*     */   public MenuEditorAdapter(@NonNull ArrayList<MenuEditorItem> menuEditorItems) {
/*  38 */     this.mMenuEditorItems.addAll(menuEditorItems);
/*     */   }
/*     */   
/*     */   public void setData(@NonNull ArrayList<MenuEditorItem> menuEditorItems) {
/*  42 */     this.mMenuEditorItems.clear();
/*  43 */     this.mMenuEditorItems.addAll(menuEditorItems);
/*  44 */     notifyDataSetChanged();
/*     */   }
/*     */   
/*     */   public void setViewModel(MenuEditorViewModel viewModel) {
/*  48 */     this.mViewModel = viewModel;
/*     */   }
/*     */   
/*     */   public void setDragging(boolean dragging) {
/*  52 */     this.mDragging = dragging;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemViewType(int position) {
/*  57 */     if (this.mMenuEditorItems != null) {
/*  58 */       if (((MenuEditorItem)this.mMenuEditorItems.get(position)).isHeader()) {
/*  59 */         return 1;
/*     */       }
/*  61 */       return 0;
/*     */     } 
/*     */     
/*  64 */     return super.getItemViewType(position);
/*     */   }
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
/*  70 */     if (viewType == 1) {
/*  71 */       View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_editor_header, parent, false);
/*  72 */       return new HeaderViewHolder(view1);
/*     */     } 
/*  74 */     View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_menu_editor_content, parent, false);
/*  75 */     return new ContentViewHolder(view);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
/*  81 */     if (this.mMenuEditorItems != null) {
/*  82 */       MenuEditorItem item = this.mMenuEditorItems.get(position);
/*  83 */       if (holder.getItemViewType() == 1 && item instanceof MenuEditorItemHeader) {
/*  84 */         initItemHeader((MenuEditorItemHeader)item, (HeaderViewHolder)holder);
/*  85 */       } else if (holder.getItemViewType() == 0 && item instanceof MenuEditorItemContent) {
/*  86 */         initItemContent((MenuEditorItemContent)item, (ContentViewHolder)holder);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initItemHeader(@NonNull MenuEditorItemHeader itemHeader, @NonNull HeaderViewHolder headerViewHolder) {
/*  92 */     if (this.mDragging) {
/*  93 */       if (itemHeader.getDraggingTitle() != null) {
/*  94 */         headerViewHolder.mTitle.setText(itemHeader.getDraggingTitle());
/*     */       } else {
/*  96 */         headerViewHolder.mTitle.setText(itemHeader.getDraggingTitleId());
/*     */       }
/*     */     
/*  99 */     } else if (itemHeader.getTitle() != null) {
/* 100 */       headerViewHolder.mTitle.setText(itemHeader.getTitle());
/*     */     } else {
/* 102 */       headerViewHolder.mTitle.setText(itemHeader.getTitleId());
/*     */     } 
/*     */     
/* 105 */     if (itemHeader.getDescription() != null) {
/* 106 */       headerViewHolder.mDescription.setText(itemHeader.getDescription());
/* 107 */       headerViewHolder.mDescription.setVisibility(0);
/* 108 */     } else if (itemHeader.getDescriptionId() != 0) {
/* 109 */       headerViewHolder.mDescription.setText(itemHeader.getDescriptionId());
/* 110 */       headerViewHolder.mDescription.setVisibility(0);
/*     */     } else {
/* 112 */       headerViewHolder.mDescription.setVisibility(8);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void initItemContent(@NonNull MenuEditorItemContent itemContent, @NonNull ContentViewHolder contentViewHolder) {
/* 117 */     contentViewHolder.mTitle.setText(itemContent.getTitle());
/* 118 */     if (itemContent.getDrawable() != null) {
/* 119 */       contentViewHolder.mIcon.setImageDrawable(itemContent.getDrawable());
/* 120 */       contentViewHolder.mIcon.getDrawable().setAlpha(255);
/* 121 */     } else if (itemContent.getIconRes() != 0) {
/* 122 */       contentViewHolder.mIcon.setImageResource(itemContent.getIconRes());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemCount() {
/* 128 */     if (this.mMenuEditorItems != null) {
/* 129 */       return this.mMenuEditorItems.size();
/*     */     }
/* 131 */     return 0;
/*     */   }
/*     */   
/*     */   public void insert(MenuEditorItem item, int position) {
/* 135 */     if (this.mMenuEditorItems != null && item != null) {
/* 136 */       this.mMenuEditorItems.add(position, item);
/*     */     }
/*     */   }
/*     */   
/*     */   public MenuEditorItem removeAt(int position) {
/* 141 */     if (this.mMenuEditorItems != null) {
/* 142 */       return this.mMenuEditorItems.remove(position);
/*     */     }
/* 144 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onItemMove(int fromPosition, int toPosition) {
/* 149 */     if (toPosition == 0)
/*     */     {
/* 151 */       return false;
/*     */     }
/* 153 */     MenuEditorItem item = removeAt(fromPosition);
/* 154 */     if (item != null) {
/* 155 */       insert(item, toPosition);
/* 156 */       notifyItemMoved(fromPosition, toPosition);
/* 157 */       return true;
/*     */     } 
/* 159 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemDrop(int fromPosition, int toPosition) {
/* 164 */     this.mDragging = false;
/* 165 */     notifyHeadersChanged();
/* 166 */     if (this.mViewModel != null) {
/* 167 */       this.mViewModel.setItems(this.mMenuEditorItems);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isHeader(int position) {
/* 172 */     if (this.mMenuEditorItems != null) {
/* 173 */       MenuEditorItem item = this.mMenuEditorItems.get(position);
/* 174 */       return item.isHeader();
/*     */     } 
/* 176 */     return false;
/*     */   }
/*     */   
/*     */   public void notifyHeadersChanged() {
/* 180 */     if (this.mMenuEditorItems != null) {
/* 181 */       for (int i = 0; i < this.mMenuEditorItems.size(); i++) {
/* 182 */         MenuEditorItem item = this.mMenuEditorItems.get(i);
/* 183 */         if (item.isHeader()) {
/* 184 */           notifyItemChanged(i);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onItemDismiss(int position) {}
/*     */ 
/*     */   
/*     */   class ContentViewHolder
/*     */     extends RecyclerView.ViewHolder
/*     */   {
/*     */     TextView mTitle;
/*     */     AppCompatImageView mIcon;
/*     */     
/*     */     ContentViewHolder(View itemView) {
/* 201 */       super(itemView);
/* 202 */       this.mTitle = (TextView)itemView.findViewById(R.id.title);
/* 203 */       this.mIcon = (AppCompatImageView)itemView.findViewById(R.id.icon);
/*     */     }
/*     */   }
/*     */   
/*     */   class HeaderViewHolder
/*     */     extends RecyclerView.ViewHolder {
/*     */     TextView mTitle;
/*     */     TextView mDescription;
/*     */     
/*     */     HeaderViewHolder(View itemView) {
/* 213 */       super(itemView);
/* 214 */       this.mTitle = (TextView)itemView.findViewById(R.id.title);
/* 215 */       this.mDescription = (TextView)itemView.findViewById(R.id.description);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\MenuEditorAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */