/*     */ package com.pdftron.pdf.utils;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.graphics.Color;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.util.Pair;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AbsListView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.RelativeLayout;
/*     */ import androidx.annotation.ColorInt;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.RestrictTo;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.widget.TransparentDrawable;
/*     */ import java.lang.annotation.Retention;
/*     */ import java.lang.annotation.RetentionPolicy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public class ColorPickerGridViewAdapter
/*     */   extends ArrayAdapter<String>
/*     */ {
/*     */   public static final String TYPE_ADD_COLOR = "add_custom_color";
/*     */   public static final String TYPE_REMOVE_COLOR = "remove_custom_color";
/*     */   public static final String TYPE_TRANSPARENT = "no_fill_color";
/*     */   public static final String TYPE_RESTORE_COLOR = "restore_color";
/*     */   private static final float DARK_COLOR_THESHOLD = 0.15F;
/*     */   private final Context mContext;
/*     */   private List<String> mSource;
/*     */   private String mSelected;
/*     */   private ArrayList<String> mSelectedList;
/*     */   private int mCellHeight;
/*     */   private int mCellWidth;
/*     */   private int mBackgroundColorResId;
/*     */   private boolean mCustomizeBackground;
/*     */   private boolean mCustomizeSize;
/*     */   private boolean mEditing;
/*  83 */   private int mMaxSize = -1;
/*     */ 
/*     */   
/*     */   private ArrayList<String> mDisabledList;
/*     */ 
/*     */   
/*     */   private ArrayList<String> mFavoriteList;
/*     */ 
/*     */   
/*     */   public ColorPickerGridViewAdapter(Context context, List<String> list) {
/*  93 */     super(context, 0, list);
/*  94 */     this.mContext = context;
/*  95 */     setSource(list);
/*  96 */     this.mSelected = "";
/*     */     
/*  98 */     this.mCustomizeSize = false;
/*  99 */     this.mCustomizeBackground = false;
/* 100 */     this.mEditing = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCellSize(int width, int height) {
/* 110 */     this.mCustomizeSize = true;
/* 111 */     this.mCellWidth = width;
/* 112 */     this.mCellHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSource(List<String> list) {
/* 121 */     list = getListLowerCase(list);
/* 122 */     this.mSource = list;
/* 123 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(int index, String item) {
/* 133 */     this.mSource.set(index, item.toLowerCase());
/* 134 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCellBackground(int resId) {
/* 143 */     this.mCustomizeBackground = true;
/* 144 */     this.mBackgroundColorResId = resId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(int position) {
/* 153 */     if (position > -1) {
/* 154 */       this.mSelected = getItem(position);
/*     */     } else {
/* 156 */       this.mSelected = "";
/*     */     } 
/* 158 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelected(String selected) {
/* 167 */     this.mSelected = selected.toLowerCase();
/* 168 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelected() {
/* 177 */     return this.mSelected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditing(boolean editing) {
/* 187 */     this.mEditing = editing;
/* 188 */     if (editing) {
/* 189 */       add("restore_color");
/*     */     } else {
/* 191 */       remove("restore_color");
/*     */     } 
/* 193 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEditing() {
/* 202 */     return this.mEditing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeItem(int position) {
/* 211 */     this.mSource.remove(position);
/* 212 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int removeItem(String value) {
/* 222 */     int result = -1;
/* 223 */     if (this.mSource.contains(value.toLowerCase())) {
/* 224 */       result = this.mSource.indexOf(value);
/*     */     }
/* 226 */     this.mSource.remove(value.toLowerCase());
/* 227 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(String object) {
/* 237 */     if (object != null && !this.mSource.contains(object.toLowerCase())) {
/* 238 */       this.mSource.add(object.toLowerCase());
/* 239 */       if (this.mMaxSize >= 0 && this.mSource.size() > this.mMaxSize) {
/* 240 */         this.mSource.remove(0);
/*     */       }
/* 242 */       notifyDataSetChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addFront(String object) {
/* 253 */     if (object != null && !this.mSource.contains(object.toLowerCase())) {
/* 254 */       this.mSource.add(0, object.toLowerCase());
/* 255 */       if (this.mMaxSize >= 0 && this.mSource.size() > this.mMaxSize) {
/* 256 */         this.mSource.remove(this.mSource.size() - 1);
/*     */       }
/* 258 */       notifyDataSetChanged();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(int position, String value) {
/* 269 */     this.mSource.add(position, value.toLowerCase());
/* 270 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount() {
/* 280 */     return this.mSource.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getItem(int position) {
/* 291 */     if (this.mSource != null && position >= 0 && position < this.mSource.size()) {
/* 292 */       return this.mSource.get(position);
/*     */     }
/* 294 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pair<Boolean, Integer> getItemIndex(@ColorInt int colorInt) {
/* 305 */     for (int i = 0; i < this.mSource.size(); i++) {
/*     */       try {
/* 307 */         if (Color.parseColor(this.mSource.get(i)) == colorInt) {
/* 308 */           return Pair.create(Boolean.valueOf(true), Integer.valueOf(i));
/*     */         }
/* 310 */       } catch (Exception e) {
/* 311 */         return Pair.create(Boolean.valueOf(false), Integer.valueOf(-1));
/*     */       } 
/*     */     } 
/* 314 */     return Pair.create(Boolean.valueOf(false), Integer.valueOf(-1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Pair<Boolean, Integer> getItemIndex(String item) {
/* 326 */     for (int i = 0; i < this.mSource.size(); i++) {
/*     */       try {
/* 328 */         if (((String)this.mSource.get(i)).equals(item)) {
/* 329 */           return Pair.create(Boolean.valueOf(true), Integer.valueOf(i));
/*     */         }
/* 331 */       } catch (Exception e) {
/* 332 */         return Pair.create(Boolean.valueOf(false), Integer.valueOf(-1));
/*     */       } 
/*     */     } 
/* 335 */     return Pair.create(Boolean.valueOf(false), Integer.valueOf(-1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getItems() {
/* 344 */     return this.mSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addAll(@NonNull Collection<? extends String> collection) {
/* 354 */     this.mSource.addAll(collection);
/* 355 */     super.addAll(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean contains(String item) {
/* 365 */     return this.mSource.contains(item.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxSize(int size) {
/* 374 */     this.mMaxSize = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*     */     ViewHolder holder;
/* 384 */     if (convertView == null) {
/* 385 */       convertView = LayoutInflater.from(getContext()).inflate(R.layout.tools_gridview_color_picker, parent, false);
/*     */       
/* 387 */       RelativeLayout layout = (RelativeLayout)convertView.findViewById(R.id.cell_layout);
/* 388 */       int size = getContext().getResources().getDimensionPixelSize(R.dimen.quick_menu_button_size);
/*     */       
/* 390 */       RelativeLayout.LayoutParams lp = this.mCustomizeSize ? new RelativeLayout.LayoutParams(-1, this.mCellHeight) : new RelativeLayout.LayoutParams(-1, size);
/*     */ 
/*     */       
/* 393 */       if (this.mCustomizeBackground) {
/* 394 */         layout.setBackgroundColor(this.mContext.getResources().getColor(this.mBackgroundColorResId));
/*     */       }
/* 396 */       ImageView imageView = (ImageView)convertView.findViewById(R.id.color_image_view);
/* 397 */       imageView.setLayoutParams((ViewGroup.LayoutParams)lp);
/* 398 */       ImageView selectedImageView = (ImageView)convertView.findViewById(R.id.color_selected);
/* 399 */       ImageView removeImageView = (ImageView)convertView.findViewById(R.id.color_remove);
/* 400 */       ImageView buttonImageView = (ImageView)convertView.findViewById(R.id.color_buttons);
/* 401 */       buttonImageView.setLayoutParams((ViewGroup.LayoutParams)lp);
/* 402 */       layout.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, -1));
/* 403 */       holder = new ViewHolder();
/* 404 */       holder.colorLayout = layout;
/* 405 */       holder.colorImage = imageView;
/* 406 */       holder.selectedIndicator = selectedImageView;
/* 407 */       holder.removeIndicator = removeImageView;
/* 408 */       holder.colorButtons = buttonImageView;
/* 409 */       convertView.setTag(holder);
/*     */     } else {
/* 411 */       holder = (ViewHolder)convertView.getTag();
/*     */     } 
/* 413 */     holder.colorImage.setBackgroundColor(this.mContext.getResources().getColor(R.color.tools_colors_white));
/* 414 */     holder.colorImage.setImageResource(17170445);
/* 415 */     if (this.mEditing) {
/* 416 */       holder.selectedIndicator.setVisibility(8);
/* 417 */       holder.removeIndicator.setVisibility(0);
/*     */     } else {
/* 419 */       holder.removeIndicator.setVisibility(8);
/* 420 */       if (this.mSelected != null)
/*     */       {
/* 422 */         if (((String)this.mSource.get(position)).equalsIgnoreCase(this.mSelected) && (this.mSelectedList == null || this.mSelectedList.isEmpty())) {
/* 423 */           int selectedIndicatorColor = -1;
/* 424 */           float selectedIndicatorAlpha = 0.87F;
/* 425 */           boolean isTransparent = this.mSelected.equalsIgnoreCase("no_fill_color");
/* 426 */           if (!isTransparent) {
/*     */             try {
/* 428 */               int color = Color.parseColor(this.mSelected);
/* 429 */               isTransparent = (color == 0);
/* 430 */             } catch (IllegalArgumentException illegalArgumentException) {}
/*     */           }
/*     */ 
/*     */ 
/*     */           
/* 435 */           int drawableRes = isTransparent ? R.drawable.ic_check_circle_black_24dp : R.drawable.ic_check_black_24dp;
/* 436 */           Drawable icon = getContext().getResources().getDrawable(drawableRes);
/* 437 */           if (!isTransparent) {
/* 438 */             icon.mutate();
/*     */             try {
/* 440 */               int color = Color.parseColor(this.mSelected);
/* 441 */               if (!Utils.isColorDark(color, 0.15F)) {
/* 442 */                 selectedIndicatorColor = -16777216;
/* 443 */                 selectedIndicatorAlpha = 0.54F;
/*     */               } 
/* 445 */             } catch (IllegalArgumentException e) {
/* 446 */               e.printStackTrace();
/*     */             } 
/* 448 */             icon.setColorFilter(selectedIndicatorColor, PorterDuff.Mode.SRC_IN);
/* 449 */             icon.setAlpha((int)(selectedIndicatorAlpha * 255.0F));
/*     */           } 
/* 451 */           holder.selectedIndicator.setImageDrawable(icon);
/*     */           
/* 453 */           holder.selectedIndicator.setVisibility(0);
/*     */         } else {
/* 455 */           holder.selectedIndicator.setVisibility(8);
/*     */         } 
/*     */       }
/*     */     } 
/* 459 */     if (((String)this.mSource.get(position)).equalsIgnoreCase("add_custom_color")) {
/* 460 */       holder.colorButtons.setImageResource(R.drawable.ic_add_white_24dp);
/* 461 */       holder.colorButtons.setColorFilter(Utils.getThemeAttrColor(getContext(), 16842806), PorterDuff.Mode.SRC_IN);
/* 462 */       holder.colorButtons.setAlpha(0.54F);
/* 463 */       holder.colorButtons.setVisibility(0);
/*     */       
/* 465 */       if (position == 0) {
/* 466 */         GradientDrawable background = (GradientDrawable)this.mContext.getResources().getDrawable(R.drawable.rounded_corners);
/* 467 */         background.mutate();
/* 468 */         background.setStroke(1, -7829368);
/* 469 */         background.setColor(0);
/* 470 */         holder.colorButtons.setBackground((Drawable)background);
/*     */       } else {
/* 472 */         TypedArray typedArray = getContext().obtainStyledAttributes(new int[] { R.attr.selectableItemBackground });
/* 473 */         Drawable background = typedArray.getDrawable(0);
/* 474 */         typedArray.recycle();
/* 475 */         if (background != null) {
/* 476 */           holder.colorButtons.setBackground(background);
/*     */         }
/*     */       } 
/* 479 */       holder.removeIndicator.setVisibility(8);
/* 480 */       holder.selectedIndicator.setVisibility(8);
/* 481 */       holder.colorImage.setVisibility(8);
/* 482 */     } else if (((String)this.mSource.get(position)).equalsIgnoreCase("remove_custom_color")) {
/* 483 */       if (this.mEditing) {
/* 484 */         holder.colorButtons.setImageResource(R.drawable.ic_check_black_24dp);
/* 485 */         holder.colorButtons.getDrawable().mutate();
/* 486 */         holder.colorButtons.getDrawable().setColorFilter(getContext().getResources().getColor(R.color.qm_item_color), PorterDuff.Mode.SRC_IN);
/*     */       } else {
/* 488 */         holder.colorButtons.setImageResource(R.drawable.ic_remove_white_24dp);
/* 489 */         holder.colorButtons.getDrawable().mutate();
/* 490 */         holder.colorButtons.getDrawable().setColorFilter(getContext().getResources().getColor(R.color.qm_item_color), PorterDuff.Mode.SRC_IN);
/*     */       } 
/* 492 */       holder.colorButtons.setVisibility(0);
/* 493 */       holder.selectedIndicator.setVisibility(8);
/* 494 */       holder.removeIndicator.setVisibility(8);
/* 495 */     } else if (((String)this.mSource.get(position)).equalsIgnoreCase("restore_color")) {
/* 496 */       holder.colorImage.setImageResource(R.drawable.restore);
/* 497 */       holder.colorImage.getDrawable().mutate();
/* 498 */       holder.colorImage.getDrawable().setColorFilter(getContext().getResources().getColor(R.color.gray600), PorterDuff.Mode.SRC_IN);
/* 499 */       holder.colorImage.setScaleType(ImageView.ScaleType.CENTER);
/*     */       
/* 501 */       holder.selectedIndicator.setVisibility(8);
/* 502 */       holder.removeIndicator.setVisibility(8);
/* 503 */       holder.colorButtons.setVisibility(8);
/*     */     } else {
/* 505 */       holder.colorButtons.setVisibility(8);
/* 506 */       holder.colorImage.setVisibility(0);
/* 507 */       String colorStr = this.mSource.get(position);
/* 508 */       int color = 0;
/* 509 */       if (!colorStr.equalsIgnoreCase("no_fill_color")) {
/*     */         try {
/* 511 */           color = Color.parseColor(colorStr);
/* 512 */         } catch (IllegalArgumentException e) {
/* 513 */           AnalyticsHandlerAdapter.getInstance().sendException(e, "\ncolorStr: " + colorStr + "; index: " + position);
/*     */         } 
/*     */       }
/* 516 */       if (color != 0) {
/* 517 */         GradientDrawable colorSwatch = (GradientDrawable)this.mContext.getResources().getDrawable(R.drawable.shape_oval);
/* 518 */         colorSwatch.mutate();
/* 519 */         colorSwatch.setColor(color);
/* 520 */         if (color != -1) {
/* 521 */           colorSwatch.setStroke(0, 0);
/*     */         } else {
/* 523 */           colorSwatch.setStroke(1, -7829368);
/*     */         } 
/* 525 */         holder.colorImage.setBackground((Drawable)colorSwatch);
/*     */       } else {
/* 527 */         TransparentDrawable drawable = new TransparentDrawable(getContext());
/* 528 */         drawable.setDrawCircle(true);
/* 529 */         holder.colorImage.setBackground((Drawable)drawable);
/*     */       } 
/*     */       
/* 532 */       if (this.mFavoriteList != null && this.mFavoriteList.contains(colorStr)) {
/* 533 */         int drawableRes = (Utils.isColorDark(color, 0.15F) && color != 0) ? R.drawable.ic_star_filled_white_24dp : R.drawable.ic_star_white_border_24dp;
/* 534 */         Drawable icon = getContext().getResources().getDrawable(drawableRes);
/* 535 */         holder.colorImage.setImageDrawable(icon);
/* 536 */         icon.mutate();
/* 537 */         if (this.mDisabledList != null && this.mDisabledList.contains(colorStr)) {
/* 538 */           int colorFilter = (color == -16777216) ? -7829368 : -16777216;
/* 539 */           icon.setColorFilter(colorFilter, PorterDuff.Mode.SRC_IN);
/* 540 */           icon.setAlpha(137);
/*     */         } else {
/* 542 */           icon.setAlpha(255);
/*     */         } 
/*     */       } 
/* 545 */       if (this.mSelectedList != null && this.mSelectedList.contains(colorStr)) {
/* 546 */         int drawableRes = (color == 0) ? R.drawable.ic_check_circle_black_24dp : R.drawable.ic_check_black_24dp;
/* 547 */         Drawable icon = getContext().getResources().getDrawable(drawableRes);
/* 548 */         holder.colorImage.setImageDrawable(icon);
/* 549 */         icon.mutate();
/* 550 */         if (!Utils.isColorDark(color, 0.15F) && color != 0) {
/* 551 */           icon.setColorFilter(-16777216, PorterDuff.Mode.SRC_IN);
/* 552 */           icon.setAlpha(137);
/* 553 */         } else if (color != 0) {
/* 554 */           icon.setColorFilter(-1, PorterDuff.Mode.SRC_IN);
/* 555 */           icon.setAlpha(255);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 560 */     return convertView;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFavoriteList(ArrayList<String> favoriteList) {
/* 570 */     this.mFavoriteList = favoriteList;
/* 571 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDisabledColorList(ArrayList<String> disabledColorList) {
/* 582 */     this.mDisabledList = disabledColorList;
/* 583 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectedList(ArrayList<String> selectedList) {
/* 593 */     this.mSelectedList = selectedList;
/* 594 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.LIBRARY})
/*     */   public static List<String> getListLowerCase(List<String> list) {
/* 604 */     ListIterator<String> listIterator = list.listIterator();
/* 605 */     while (listIterator.hasNext()) {
/* 606 */       String next = listIterator.next();
/* 607 */       listIterator.set(next.toLowerCase());
/*     */     } 
/* 609 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSelected(int position) {
/* 618 */     String item = getItem(position);
/* 619 */     if (this.mSelectedList == null) {
/* 620 */       this.mSelectedList = new ArrayList<>();
/*     */     }
/* 622 */     this.mSelectedList.add(item);
/* 623 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<String> getSelectedList() {
/* 632 */     return this.mSelectedList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteAllSelected() {
/* 639 */     this.mSource.removeAll(this.mSelectedList);
/*     */     
/* 641 */     if (this.mSelectedList != null) {
/* 642 */       this.mSelectedList.clear();
/*     */     }
/*     */     
/* 645 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeAllSelected() {
/* 652 */     if (this.mSelectedList != null) {
/* 653 */       this.mSelectedList.clear();
/*     */     }
/*     */     
/* 656 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSelected(int position) {
/* 665 */     if (this.mSelectedList != null) {
/* 666 */       String item = getItem(position);
/* 667 */       this.mSelectedList.remove(item);
/*     */     } 
/*     */     
/* 670 */     notifyDataSetChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectedListCount() {
/* 679 */     return (this.mSelectedList == null) ? 0 : this.mSelectedList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInSelectedList(int position) {
/* 689 */     String item = getItem(position);
/* 690 */     return (this.mSelectedList != null && this.mSelectedList.contains(item));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirstSelectedInList() {
/* 699 */     return (this.mSelectedList == null) ? null : this.mSelectedList.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemDisabled(String item) {
/* 709 */     return (this.mDisabledList != null && this.mDisabledList.contains(item.toLowerCase()));
/*     */   }
/*     */   
/*     */   private static class ViewHolder {
/*     */     RelativeLayout colorLayout;
/*     */     ImageView colorImage;
/*     */     ImageView selectedIndicator;
/*     */     ImageView removeIndicator;
/*     */     ImageView colorButtons;
/*     */     
/*     */     private ViewHolder() {}
/*     */   }
/*     */   
/*     */   @Retention(RetentionPolicy.SOURCE)
/*     */   public static @interface GRID_COLOR_TYPE {}
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pd\\utils\ColorPickerGridViewAdapter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */