/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.app.AlertDialog;
/*     */ import android.app.Dialog;
/*     */ import android.content.Context;
/*     */ import android.content.DialogInterface;
/*     */ import android.graphics.drawable.GradientDrawable;
/*     */ import android.os.AsyncTask;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import android.widget.AdapterView;
/*     */ import android.widget.ArrayAdapter;
/*     */ import android.widget.EditText;
/*     */ import android.widget.ImageView;
/*     */ import android.widget.LinearLayout;
/*     */ import android.widget.Spinner;
/*     */ import android.widget.SpinnerAdapter;
/*     */ import android.widget.TextView;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.Page;
/*     */ import com.pdftron.pdf.asynctask.GeneratePagesTask;
/*     */ import com.pdftron.pdf.tools.R;
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
/*     */ public class AddPageDialogFragment
/*     */   extends DialogFragment
/*     */ {
/*     */   public enum PageType
/*     */   {
/*  51 */     Blank,
/*  52 */     Lined,
/*  53 */     Grid,
/*  54 */     Graph,
/*  55 */     Music,
/*  56 */     Dotted,
/*  57 */     IsometricDotted;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum PageSize
/*     */   {
/*  64 */     Custom,
/*  65 */     Letter,
/*  66 */     Legal,
/*  67 */     A4,
/*  68 */     A3,
/*  69 */     Ledger;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum PageOrientation
/*     */   {
/*  76 */     Portrait,
/*  77 */     Landscape;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum PageColor
/*     */   {
/*  84 */     White,
/*  85 */     Yellow,
/*  86 */     Blueprint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  92 */   public static int[] PageColorValues = new int[] { -1, -103, -16771964 };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 101 */   public static int[] PageColorStrings = new int[] { R.string.dialog_add_page_color_white, R.string.dialog_add_page_color_yellow, R.string.dialog_add_page_color_blueprint };
/*     */ 
/*     */   
/*     */   private static final String ARG_CREATE_NEW_PDF = "create_new_pdf";
/*     */ 
/*     */   
/*     */   private static final String ARG_CUSTOM_PAGE_WIDTH = "custom_page_width";
/*     */   
/*     */   private static final String ARG_CUSTOM_PAGE_HEIGHT = "custom_page_height";
/*     */   
/*     */   private static final float MM_PER_INCH = 25.4F;
/*     */   
/*     */   private float mDeviceScale;
/*     */   
/* 115 */   private PageSize mCurrentPageSize = PageSize.Letter;
/* 116 */   private PageType mCurrentPageType = PageType.Blank;
/*     */ 
/*     */   
/*     */   private PageOrientation mCurrentPageOrientation;
/*     */ 
/*     */   
/*     */   private PageColor mCurrentPageColor;
/*     */ 
/*     */   
/*     */   private double mCustomPageWidth;
/*     */ 
/*     */   
/*     */   private double mCustomPageHeight;
/*     */ 
/*     */   
/*     */   private ViewPager mViewPager;
/*     */ 
/*     */   
/*     */   private LinearLayout mViewPagerDotLayout;
/*     */ 
/*     */   
/*     */   private ArrayList<LinearLayout> mViewPagerChildren;
/*     */ 
/*     */   
/*     */   private OnAddNewPagesListener mOnAddNewPagesListener;
/*     */   
/*     */   private OnCreateNewDocumentListener mOnCreateNewDocumentListener;
/*     */   
/*     */   private boolean mShouldCreateNewPdf = false;
/*     */   
/*     */   private EditText mNumOfPagesEdit;
/*     */   
/*     */   private EditText mTitleEdit;
/*     */ 
/*     */   
/*     */   public static AddPageDialogFragment newInstance() {
/* 152 */     AddPageDialogFragment fragment = new AddPageDialogFragment();
/* 153 */     Bundle args = new Bundle();
/* 154 */     args.putBoolean("create_new_pdf", true);
/* 155 */     fragment.setArguments(args);
/* 156 */     return fragment;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static AddPageDialogFragment newInstance(double lastPageWidth, double lastPageHeight) {
/* 174 */     AddPageDialogFragment fragment = new AddPageDialogFragment();
/* 175 */     Bundle args = new Bundle();
/* 176 */     args.putBoolean("create_new_pdf", false);
/* 177 */     args.putDouble("custom_page_width", lastPageWidth / 72.0D);
/* 178 */     args.putDouble("custom_page_height", lastPageHeight / 72.0D);
/* 179 */     fragment.setArguments(args);
/* 180 */     return fragment;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnCreateNewDocumentListener(OnCreateNewDocumentListener listener) {
/* 189 */     this.mOnCreateNewDocumentListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOnAddNewPagesListener(OnAddNewPagesListener listener) {
/* 198 */     this.mOnAddNewPagesListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddPageDialogFragment setInitialPageType(PageType initialPageType) {
/* 206 */     this.mCurrentPageType = initialPageType;
/* 207 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddPageDialogFragment setInitialPageSize(PageSize initialPageSize) {
/* 215 */     this.mCurrentPageSize = initialPageSize;
/* 216 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AddPageDialogFragment setInitialPageColor(PageColor initialPageColor) {
/* 224 */     this.mCurrentPageColor = initialPageColor;
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCreate(Bundle savedInstanceState) {
/* 233 */     super.onCreate(savedInstanceState);
/* 234 */     Bundle args = getArguments();
/* 235 */     if (args != null) {
/* 236 */       this.mShouldCreateNewPdf = args.getBoolean("create_new_pdf");
/* 237 */       this.mCustomPageWidth = args.getDouble("custom_page_width");
/* 238 */       this.mCustomPageHeight = args.getDouble("custom_page_height");
/* 239 */       this.mCurrentPageOrientation = (this.mCustomPageWidth > this.mCustomPageHeight) ? PageOrientation.Landscape : PageOrientation.Portrait;
/* 240 */       if (getContext() != null) {
/* 241 */         this.mDeviceScale = (getContext().getResources().getDisplayMetrics()).density;
/*     */       }
/* 243 */       this.mViewPagerChildren = new ArrayList<>();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NonNull
/*     */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/* 253 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/* 254 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/* 255 */     View view = inflater.inflate(R.layout.fragment_add_page_dialog, null);
/* 256 */     builder.setView(view);
/*     */     
/* 258 */     builder.setPositiveButton(getActivity().getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 261 */             AddPageDialogFragment.this.generateDoc();
/* 262 */             AddPageDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/* 265 */     builder.setNegativeButton(getActivity().getResources().getString(R.string.cancel), new DialogInterface.OnClickListener()
/*     */         {
/*     */           public void onClick(DialogInterface dialog, int which) {
/* 268 */             AddPageDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*     */     
/* 272 */     if (this.mShouldCreateNewPdf) {
/* 273 */       TextView title = (TextView)view.findViewById(R.id.addpagedialog_title);
/* 274 */       title.setText(R.string.dialog_add_page_title_newdoc);
/*     */     } 
/*     */     
/* 277 */     this.mViewPagerDotLayout = (LinearLayout)view.findViewById(R.id.dot_layout);
/* 278 */     initViewPagerDotLayout();
/*     */     
/* 280 */     this.mViewPager = (ViewPager)view.findViewById(R.id.page_type_view_pager);
/* 281 */     this.mViewPager.setAdapter(new PageTypePagerAdapter());
/*     */     
/* 283 */     this.mViewPager.setOffscreenPageLimit((PageType.values()).length - 1);
/* 284 */     this.mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
/*     */         {
/*     */           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onPageSelected(int position) {
/* 292 */             AddPageDialogFragment.this.changeDotPosition(position);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onPageScrollStateChanged(int state) {}
/*     */         });
/* 300 */     if (Utils.isRtlLayout(getContext())) {
/* 301 */       this.mViewPager.setCurrentItem(this.mViewPager.getAdapter().getCount() - 1);
/*     */     }
/* 303 */     this.mTitleEdit = (EditText)view.findViewById(R.id.add_page_document_title_input);
/* 304 */     this.mNumOfPagesEdit = (EditText)view.findViewById(R.id.addpagedialog_numpages_edit);
/* 305 */     TextView titleEditLabel = (TextView)view.findViewById(R.id.addpagedialog_doctitle_label);
/*     */     
/* 307 */     if (!this.mShouldCreateNewPdf) {
/* 308 */       this.mTitleEdit.setVisibility(8);
/* 309 */       titleEditLabel.setVisibility(8);
/*     */     } 
/*     */     
/* 312 */     Spinner pageSizeSpinner = (Spinner)view.findViewById(R.id.pageSize_spinner);
/* 313 */     pageSizeSpinner.setAdapter((SpinnerAdapter)getPageSizeSpinnerAdapter());
/* 314 */     pageSizeSpinner.setSelection(this.mCurrentPageSize.ordinal() - (this.mShouldCreateNewPdf ? 1 : 0));
/* 315 */     pageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 318 */             AddPageDialogFragment.this.mCurrentPageSize = PageSize.values()[position + (AddPageDialogFragment.this.mShouldCreateNewPdf ? 1 : 0)];
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/* 328 */     Spinner pageOrientationSpinner = (Spinner)view.findViewById(R.id.pageOrientation_spinner);
/* 329 */     pageOrientationSpinner.setAdapter((SpinnerAdapter)getOrientationSpinnerAdapter());
/* 330 */     pageOrientationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
/* 333 */             AddPageDialogFragment.this.mCurrentPageOrientation = PageOrientation.values()[position];
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/* 342 */     this.mCurrentPageColor = PageColor.White;
/* 343 */     Spinner pageColorSpinner = (Spinner)view.findViewById(R.id.pageColor_spinner);
/* 344 */     pageColorSpinner.setAdapter((SpinnerAdapter)getPageColorSpinnerAdapter());
/* 345 */     pageColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
/*     */         {
/*     */           public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
/*     */           {
/* 349 */             AddPageDialogFragment.this.mCurrentPageColor = PageColor.values()[position];
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void onNothingSelected(AdapterView<?> parent) {}
/*     */         });
/* 357 */     pageColorSpinner.setSelection(0);
/*     */     
/* 359 */     return (Dialog)builder.create();
/*     */   }
/*     */   
/*     */   private void initViewPagerDotLayout() {
/* 363 */     if ((int)Math.ceil((PageType.values()).length / 3.0D) < 2) {
/*     */       return;
/*     */     }
/* 366 */     int initEnabled = 0;
/* 367 */     if (Utils.isRtlLayout(getContext())) {
/* 368 */       initEnabled = (int)Math.ceil((PageType.values()).length / 3.0D) - 1;
/*     */     }
/* 370 */     for (int i = 0; i < (int)Math.ceil((PageType.values()).length / 3.0D); i++) {
/* 371 */       ImageView dot = new ImageView(getContext());
/* 372 */       LinearLayout.LayoutParams dotParams = new LinearLayout.LayoutParams((int)(8.0F * this.mDeviceScale + 0.5F), (int)(8.0F * this.mDeviceScale + 0.5F));
/* 373 */       int margin = (int)(5.0F * this.mDeviceScale + 0.5F);
/* 374 */       dotParams.setMargins(margin, margin, margin, margin);
/* 375 */       dot.setLayoutParams((ViewGroup.LayoutParams)dotParams);
/* 376 */       dot.setImageDrawable(getResources().getDrawable(R.drawable.viewpager_point));
/* 377 */       dot.setEnabled((i == initEnabled));
/* 378 */       this.mViewPagerDotLayout.addView((View)dot);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void changeDotPosition(int position) {
/* 383 */     if ((int)Math.ceil((PageType.values()).length / 3.0D) < 2) {
/*     */       return;
/*     */     }
/* 386 */     if (Utils.isRtlLayout(getContext())) {
/* 387 */       position = this.mViewPager.getAdapter().getCount() - position - 1;
/*     */     }
/* 389 */     for (int i = 0; i < (int)Math.ceil((PageType.values()).length / 3.0D); i++) {
/* 390 */       ImageView dot = (ImageView)this.mViewPagerDotLayout.getChildAt(i);
/* 391 */       dot.setEnabled((i == position));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setActiveMode(PageType type) {
/* 396 */     this.mCurrentPageType = type;
/* 397 */     for (PageType pType : PageType.values()) {
/* 398 */       LinearLayout btnLayout = this.mViewPagerChildren.get(pType.ordinal());
/* 399 */       ImageView imgView = (ImageView)btnLayout.getChildAt(0);
/* 400 */       switch (pType) {
/*     */         case Portrait:
/* 402 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.blankpage_selected : R.drawable.blankpage_regular));
/*     */           break;
/*     */         case Landscape:
/* 405 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.linedpage_selected : R.drawable.linedpage_regular));
/*     */           break;
/*     */         case null:
/* 408 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.graphpage2_selected : R.drawable.graphpage2_regular));
/*     */           break;
/*     */         case null:
/* 411 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.graphpage_selected : R.drawable.graphpage_regular));
/*     */           break;
/*     */         case null:
/* 414 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.musicpage_selected : R.drawable.musicpage_regular));
/*     */           break;
/*     */         case null:
/* 417 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.dottedpage_selected : R.drawable.dottedpage_regular));
/*     */           break;
/*     */         case null:
/* 420 */           imgView.setImageDrawable(getResources().getDrawable((type == pType) ? R.drawable.isodottedpage_selected : R.drawable.isodottedpage_regular));
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private ArrayAdapter<CharSequence> getPageColorSpinnerAdapter() {
/* 427 */     Context context = getContext();
/* 428 */     if (context == null) {
/* 429 */       return null;
/*     */     }
/*     */     
/* 432 */     ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(context, R.layout.simple_image_spinner_item, 16908308)
/*     */       {
/*     */         @NonNull
/*     */         public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
/*     */         {
/*     */           View view;
/* 438 */           if (convertView != null) {
/* 439 */             view = convertView;
/*     */           } else {
/* 441 */             view = LayoutInflater.from(getContext()).inflate(R.layout.simple_image_spinner_item, parent, false);
/*     */           } 
/*     */           
/* 444 */           ImageView preview = (ImageView)view.findViewById(R.id.spinner_image);
/* 445 */           ((GradientDrawable)preview.getBackground()).setColor(-16777216);
/* 446 */           GradientDrawable bgShape = (GradientDrawable)preview.getDrawable();
/* 447 */           bgShape.setColor(AddPageDialogFragment.PageColorValues[position]);
/*     */           
/* 449 */           TextView textView = (TextView)view.findViewById(16908308);
/* 450 */           textView.setText(AddPageDialogFragment.this.getString(AddPageDialogFragment.PageColorStrings[position]));
/* 451 */           return view;
/*     */         }
/*     */ 
/*     */         
/*     */         public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
/*     */           View view;
/* 457 */           if (convertView != null) {
/* 458 */             view = convertView;
/*     */           } else {
/* 460 */             LayoutInflater lInflater = (LayoutInflater)getContext().getSystemService("layout_inflater");
/* 461 */             if (lInflater == null) {
/* 462 */               lInflater = LayoutInflater.from(getContext());
/*     */             }
/* 464 */             view = lInflater.inflate(R.layout.simple_image_spinner_dropdown_item, parent, false);
/*     */           } 
/*     */           
/* 467 */           ImageView preview = (ImageView)view.findViewById(R.id.spinner_image);
/* 468 */           ((GradientDrawable)preview.getBackground()).setColor(-16777216);
/* 469 */           GradientDrawable bgShape = (GradientDrawable)preview.getDrawable();
/* 470 */           bgShape.setColor(AddPageDialogFragment.PageColorValues[position]);
/*     */           
/* 472 */           TextView textView = (TextView)view.findViewById(16908308);
/* 473 */           textView.setText(AddPageDialogFragment.this.getString(AddPageDialogFragment.PageColorStrings[position]));
/* 474 */           return view;
/*     */         }
/*     */       };
/* 477 */     for (int i = 0, cnt = (PageColor.values()).length; i < cnt; i++) {
/* 478 */       adapter.add(getString(PageColorStrings[i]));
/*     */     }
/* 480 */     adapter.setDropDownViewResource(R.layout.simple_image_spinner_dropdown_item);
/*     */     
/* 482 */     return adapter;
/*     */   }
/*     */   
/*     */   private ArrayAdapter<CharSequence> getPageSizeSpinnerAdapter() {
/* 486 */     Context context = getContext();
/* 487 */     if (context == null) {
/* 488 */       return null;
/*     */     }
/*     */     
/* 491 */     ArrayAdapter<CharSequence> adapter = new ArrayAdapter(context, 17367048);
/* 492 */     for (PageSize size : PageSize.values()) {
/* 493 */       switch (size) {
/*     */         case Portrait:
/* 495 */           adapter.add(getString(R.string.dialog_add_page_page_size_letter));
/*     */           break;
/*     */         case Landscape:
/* 498 */           adapter.add(getString(R.string.dialog_add_page_page_size_legal));
/*     */           break;
/*     */         case null:
/* 501 */           adapter.add(getString(R.string.dialog_add_page_page_size_ledger));
/*     */           break;
/*     */         case null:
/* 504 */           if (!this.mShouldCreateNewPdf)
/* 505 */             adapter.add(getString(R.string.dialog_add_page_page_size_custom, new Object[] {
/* 506 */                     Integer.valueOf((int)Math.round(this.mCustomPageWidth * 25.399999618530273D)), Integer.valueOf((int)Math.round(this.mCustomPageHeight * 25.399999618530273D)) })); 
/*     */           break;
/*     */         case null:
/* 509 */           adapter.add(getString(R.string.dialog_add_page_page_size_a4));
/*     */           break;
/*     */         case null:
/* 512 */           adapter.add(getString(R.string.dialog_add_page_page_size_a3));
/*     */           break;
/*     */       } 
/*     */     } 
/* 516 */     adapter.setDropDownViewResource(17367049);
/* 517 */     return adapter;
/*     */   }
/*     */   
/*     */   private ArrayAdapter<CharSequence> getOrientationSpinnerAdapter() {
/* 521 */     Context context = getContext();
/* 522 */     if (context == null) {
/* 523 */       return null;
/*     */     }
/*     */     
/* 526 */     ArrayAdapter<CharSequence> adapter = new ArrayAdapter(context, 17367048);
/* 527 */     for (PageOrientation size : PageOrientation.values()) {
/* 528 */       switch (size) {
/*     */         case Portrait:
/* 530 */           adapter.add(getString(R.string.dialog_add_page_orientation_portrait));
/*     */           break;
/*     */         case Landscape:
/* 533 */           adapter.add(getString(R.string.dialog_add_page_orientation_landscape));
/*     */           break;
/*     */       } 
/*     */     } 
/* 537 */     adapter.setDropDownViewResource(17367049);
/* 538 */     return adapter;
/*     */   }
/*     */   
/*     */   private void generateDoc() {
/* 542 */     Context context = getContext();
/* 543 */     if (context == null) {
/*     */       return;
/*     */     }
/*     */     
/* 547 */     int numOfPages = 1;
/*     */     try {
/* 549 */       if (!Utils.isNullOrEmpty(this.mNumOfPagesEdit.getText().toString())) {
/* 550 */         numOfPages = Integer.parseInt(this.mNumOfPagesEdit.getText().toString());
/* 551 */         if (numOfPages < 1 || numOfPages > 1000)
/* 552 */           numOfPages = 1; 
/*     */       } 
/* 554 */     } catch (NumberFormatException e) {
/* 555 */       numOfPages = 1;
/*     */     } 
/*     */     
/* 558 */     String title = getString(R.string.empty_title);
/* 559 */     if (this.mShouldCreateNewPdf && 
/* 560 */       !Utils.isNullOrEmpty(this.mTitleEdit.getText().toString())) {
/* 561 */       title = this.mTitleEdit.getText().toString();
/*     */     }
/*     */     
/* 564 */     (new GeneratePagesTask(context, numOfPages, title, this.mCurrentPageSize, this.mCurrentPageOrientation, this.mCurrentPageColor, this.mCurrentPageType, this.mCustomPageWidth, this.mCustomPageHeight, this.mShouldCreateNewPdf, this.mOnCreateNewDocumentListener, this.mOnAddNewPagesListener))
/*     */ 
/*     */       
/* 567 */       .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, (Object[])new Void[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDetach() {
/* 575 */     super.onDetach();
/* 576 */     this.mOnCreateNewDocumentListener = null;
/* 577 */     this.mOnAddNewPagesListener = null;
/*     */   }
/*     */   
/*     */   private class PageTypePagerAdapter extends PagerAdapter {
/*     */     private PageTypePagerAdapter() {}
/*     */     
/*     */     @NonNull
/*     */     public Object instantiateItem(@NonNull ViewGroup collection, int position) {
/* 585 */       LinearLayout layout = new LinearLayout(AddPageDialogFragment.this.getContext());
/* 586 */       LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
/* 587 */       layout.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
/* 588 */       layout.setGravity(17);
/* 589 */       layout.setOrientation(0);
/*     */       
/* 591 */       if (Utils.isRtlLayout(AddPageDialogFragment.this.getContext())) {
/* 592 */         position = getCount() - position - 1;
/*     */       }
/*     */       
/* 595 */       for (int i = position * 3, cnt = (PageType.values()).length; i < position * 3 + 3 && i < cnt; i++) {
/* 596 */         PageType type = PageType.values()[i];
/* 597 */         LinearLayout btnLayout = new LinearLayout(AddPageDialogFragment.this.getContext());
/* 598 */         LinearLayout.LayoutParams btnLayoutParams = new LinearLayout.LayoutParams(-2, -2);
/* 599 */         btnLayoutParams.setMargins((int)(10.0F * AddPageDialogFragment.this.mDeviceScale + 0.5F), 0, (int)(10.0F * AddPageDialogFragment.this.mDeviceScale + 0.5F), (int)(10.0F * AddPageDialogFragment.this.mDeviceScale + 0.5F));
/* 600 */         btnLayout.setLayoutParams((ViewGroup.LayoutParams)btnLayoutParams);
/* 601 */         btnLayout.setGravity(17);
/* 602 */         btnLayout.setOrientation(1);
/* 603 */         btnLayout.setTag(Integer.valueOf(i));
/* 604 */         btnLayout.setOnClickListener(new View.OnClickListener()
/*     */             {
/*     */               public void onClick(View v) {
/* 607 */                 AddPageDialogFragment.this.setActiveMode(PageType.values()[((Integer)v.getTag()).intValue()]);
/*     */               }
/*     */             });
/* 610 */         layout.addView((View)btnLayout);
/*     */         
/* 612 */         AddPageDialogFragment.this.mViewPagerChildren.add(btnLayout);
/*     */         
/* 614 */         ImageView btnImage = new ImageView(AddPageDialogFragment.this.getContext());
/* 615 */         LinearLayout.LayoutParams imgParams = new LinearLayout.LayoutParams(-2, (int)(100.0F * AddPageDialogFragment.this.mDeviceScale + 0.5F));
/* 616 */         imgParams.setMargins(0, (int)(5.0F * AddPageDialogFragment.this.mDeviceScale + 0.5F), 0, 0);
/* 617 */         btnImage.setLayoutParams((ViewGroup.LayoutParams)imgParams);
/* 618 */         btnImage.setAdjustViewBounds(true);
/* 619 */         btnLayout.addView((View)btnImage);
/*     */         
/* 621 */         TextView btnTextView = new TextView(AddPageDialogFragment.this.getContext());
/* 622 */         btnTextView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
/* 623 */         btnLayout.addView((View)btnTextView);
/* 624 */         switch (type) {
/*     */           case Portrait:
/* 626 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.blankpage_selected : R.drawable.blankpage_regular));
/* 627 */             btnTextView.setText(R.string.dialog_add_page_blank);
/*     */             break;
/*     */           case Landscape:
/* 630 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.linedpage_selected : R.drawable.linedpage_regular));
/* 631 */             btnTextView.setText(R.string.dialog_add_page_lined);
/*     */             break;
/*     */           case null:
/* 634 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.graphpage2_selected : R.drawable.graphpage2_regular));
/* 635 */             btnTextView.setText(R.string.dialog_add_page_graph);
/*     */             break;
/*     */           case null:
/* 638 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.graphpage_selected : R.drawable.graphpage_regular));
/* 639 */             btnTextView.setText(R.string.dialog_add_page_grid);
/*     */             break;
/*     */           case null:
/* 642 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.musicpage_selected : R.drawable.musicpage_regular));
/* 643 */             btnTextView.setText(R.string.dialog_add_page_music);
/*     */             break;
/*     */           case null:
/* 646 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.dottedpage_selected : R.drawable.dottedpage_regular));
/* 647 */             btnTextView.setText(R.string.dialog_add_page_dotted);
/*     */             break;
/*     */           case null:
/* 650 */             btnImage.setImageDrawable(AddPageDialogFragment.this.getResources().getDrawable((type == AddPageDialogFragment.this.mCurrentPageType) ? R.drawable.isodottedpage_selected : R.drawable.isodottedpage_regular));
/* 651 */             btnTextView.setText(R.string.dialog_add_page_iso_dotted);
/*     */             break;
/*     */         } 
/*     */       } 
/* 655 */       collection.addView((View)layout);
/*     */       
/* 657 */       return layout;
/*     */     }
/*     */ 
/*     */     
/*     */     public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
/* 662 */       collection.removeView((View)view);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 667 */       return (int)Math.ceil((PageType.values()).length / 3.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
/* 672 */       return (view == object);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface OnCreateNewDocumentListener {
/*     */     void onCreateNewDocument(PDFDoc param1PDFDoc, String param1String);
/*     */   }
/*     */   
/*     */   public static interface OnAddNewPagesListener {
/*     */     void onAddNewPages(Page[] param1ArrayOfPage);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\AddPageDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */