/*      */ package com.pdftron.pdf.dialog;
/*      */ import android.app.AlertDialog;
/*      */ import android.content.Context;
/*      */ import android.content.DialogInterface;
/*      */ import android.content.res.ColorStateList;
/*      */ import android.content.res.Resources;
/*      */ import android.graphics.drawable.Drawable;
/*      */ import android.graphics.drawable.GradientDrawable;
/*      */ import android.graphics.drawable.LayerDrawable;
/*      */ import android.os.Bundle;
/*      */ import android.view.LayoutInflater;
/*      */ import android.view.View;
/*      */ import android.view.ViewGroup;
/*      */ import android.widget.Adapter;
/*      */ import android.widget.AdapterView;
/*      */ import android.widget.ImageButton;
/*      */ import android.widget.ImageView;
/*      */ import android.widget.LinearLayout;
/*      */ import android.widget.ListView;
/*      */ import android.widget.RelativeLayout;
/*      */ import android.widget.ScrollView;
/*      */ import androidx.annotation.NonNull;
/*      */ import androidx.appcompat.content.res.AppCompatResources;
/*      */ import androidx.core.graphics.drawable.DrawableCompat;
/*      */ import androidx.fragment.app.FragmentActivity;
/*      */ import com.pdftron.pdf.PDFViewCtrl;
/*      */ import com.pdftron.pdf.controls.ReflowPagerAdapter;
/*      */ import com.pdftron.pdf.tools.R;
/*      */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*      */ import com.pdftron.pdf.utils.AnalyticsParam;
/*      */ import com.pdftron.pdf.utils.PdfViewCtrlSettingsManager;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class ViewModePickerDialogFragment extends DialogFragment {
/*      */   protected static final String BUNDLE_CURRENT_VIEW_MODE = "current_view_mode";
/*      */   protected static final String BUNDLE_CURRENT_RTL_MODE = "current_rtl_mode";
/*      */   protected static final String BUNDLE_CURRENT_REFLOW_MODE = "current_reflow_mode";
/*      */   protected static final String BUNDLE_CURRENT_REFLOW_TEXT_SIZE = "current_reflow_text_size";
/*      */   protected static final String BUNDLE_HIDDEN_ITEMS = "disabled_view_mode_items";
/*      */   protected static final String BUNDLE_ACTION = "action";
/*      */   protected static final int ITEM_ID_CONTINUOUS = 100;
/*      */   protected static final int ITEM_ID_TEXT_SIZE = 101;
/*      */   protected static final int ITEM_ID_ROTATION = 103;
/*      */   protected static final int ITEM_ID_USERCROP = 105;
/*      */   protected static final int ITEM_ID_RTLMODE = 106;
/*      */   protected static final int ITEM_ID_BLANK = 107;
/*      */   protected static final int ITEM_ID_COLORMODE = 108;
/*      */   protected static final int ITEM_ID_REFLOW = 109;
/*      */   protected static final int ITEM_ID_FACING_COVER = 110;
/*      */   protected static final String KEY_ITEM_ICON = "item_view_mode_picker_list_icon";
/*      */   protected static final String KEY_ITEM_TEXT = "item_view_mode_picker_list_text";
/*      */   protected static final String KEY_ITEM_ID = "item_view_mode_picker_list_id";
/*      */   protected static final String KEY_ITEM_CONTROL = "item_view_mode_picker_list_control";
/*      */   protected static final int CONTROL_TYPE_RADIO = 0;
/*      */   protected static final int CONTROL_TYPE_SWITCH = 1;
/*      */   protected static final int CONTROL_TYPE_SIZE = 2;
/*      */   protected static final int CONTROL_TYPE_NONE = 3;
/*      */   private boolean mHasEventAction;
/*      */   protected RelativeLayout mColorModeLayout;
/*      */   protected LinearLayout mViewModeLayout;
/*      */   protected ListView mOptionListView;
/*      */   protected SeparatedListAdapter mListAdapter;
/*      */   protected PDFViewCtrl.PagePresentationMode mCurrentViewMode;
/*      */   protected boolean mIsRtlMode;
/*      */   protected boolean mIsReflowMode;
/*      */   protected int mReflowTextSize;
/*      */   protected List<Map<String, Object>> mViewModeOptionsList;
/*      */   protected ViewModePickerDialogFragmentListener mViewModePickerDialogListener;
/*      */   @NonNull
/*      */   protected List<Integer> mHiddenItems;
/*      */   
/*      */   public enum ViewModePickerItems {
/*   76 */     ITEM_ID_CONTINUOUS(100),
/*   77 */     ITEM_ID_TEXT_SIZE(101),
/*   78 */     ITEM_ID_ROTATION(103),
/*   79 */     ITEM_ID_USERCROP(105),
/*   80 */     ITEM_ID_COLORMODE(108),
/*   81 */     ITEM_ID_REFLOW(109),
/*   82 */     ITEM_ID_FACING_COVER(110);
/*      */     
/*      */     final int itemId;
/*      */     
/*      */     ViewModePickerItems(int item) {
/*   87 */       this.itemId = item;
/*      */     }
/*      */     
/*      */     public int getValue() {
/*   91 */       return this.itemId;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ViewModePickerDialogFragment newInstance(PDFViewCtrl.PagePresentationMode currentViewMode, boolean isRTLMode, boolean isReflowMode, int reflowTextSize, @Nullable ArrayList<Integer> hiddenItems) {
/*  266 */     ViewModePickerDialogFragment f = new ViewModePickerDialogFragment();
/*  267 */     Bundle args = new Bundle();
/*  268 */     args.putInt("current_view_mode", currentViewMode.getValue());
/*  269 */     args.putBoolean("current_rtl_mode", isRTLMode);
/*  270 */     args.putBoolean("current_reflow_mode", isReflowMode);
/*  271 */     args.putInt("current_reflow_text_size", reflowTextSize);
/*  272 */     if (hiddenItems != null) {
/*  273 */       args.putIntegerArrayList("disabled_view_mode_items", hiddenItems);
/*      */     }
/*  275 */     f.setArguments(args);
/*  276 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ViewModePickerDialogFragment newInstance(PDFViewCtrl.PagePresentationMode currentViewMode, boolean isRTLMode, boolean isReflowMode, int reflowTextSize) {
/*  294 */     return newInstance(currentViewMode, isRTLMode, isReflowMode, reflowTextSize, (ArrayList<Integer>)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void populateOptionsList() {
/*  301 */     Context context = getContext();
/*  302 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/*  306 */     this.mViewModeOptionsList = new ArrayList<>();
/*  307 */     Resources res = getResources();
/*      */     
/*  309 */     addItem(this.mViewModeOptionsList, 
/*  310 */         createItem(100, res
/*  311 */           .getDrawable(R.drawable.ic_view_mode_continuous_black_24dp), 
/*  312 */           getString(R.string.pref_viewmode_scrolling_direction), 1));
/*      */ 
/*      */     
/*  315 */     addItem(this.mViewModeOptionsList, 
/*  316 */         createItem(101, res
/*  317 */           .getDrawable(R.drawable.ic_font_size_black_24dp), 
/*  318 */           getString(R.string.pref_viewmode_reflow_text_size), 2));
/*      */ 
/*      */     
/*  321 */     addItem(this.mViewModeOptionsList, 
/*  322 */         createItem(108, (Drawable)null, (String)null, 0));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  327 */     if (PdfViewCtrlSettingsManager.hasRtlModeOption(context)) {
/*  328 */       addItem(this.mViewModeOptionsList, createItem(106, res
/*  329 */             .getDrawable(R.drawable.rtl), 
/*  330 */             getString(R.string.pref_viewmode_rtl_mode), 1));
/*      */     }
/*      */ 
/*      */     
/*  334 */     if (this.mViewModeOptionsList.size() > 0) {
/*  335 */       addItem(this.mViewModeOptionsList, 
/*  336 */           createItem(107, (Drawable)null, (String)null, 3));
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  341 */       this.mListAdapter.addSection(null, (Adapter)new ViewModeEntryAdapter((Context)getActivity(), this.mViewModeOptionsList));
/*      */     } 
/*      */     
/*  344 */     List<Map<String, Object>> list = new ArrayList<>();
/*      */     
/*  346 */     addItem(list, 
/*  347 */         createItem(103, res
/*  348 */           .getDrawable(R.drawable.ic_rotate_right_black_24dp), 
/*  349 */           getString(R.string.pref_viewmode_rotation), 3));
/*      */ 
/*      */     
/*  352 */     addItem(list, 
/*  353 */         createItem(105, res
/*  354 */           .getDrawable(R.drawable.user_crop), 
/*  355 */           getString(R.string.pref_viewmode_user_crop), 3));
/*      */ 
/*      */     
/*  358 */     if (list.size() > 0) {
/*  359 */       this.mListAdapter.addSection(getString(R.string.pref_viewmode_actions), (Adapter)new ViewModeEntryAdapter((Context)getActivity(), list));
/*      */     }
/*      */   }
/*      */   
/*      */   private void addItem(List<Map<String, Object>> list, Map<String, Object> item) {
/*  364 */     int id = ((Integer)item.get("item_view_mode_picker_list_id")).intValue();
/*  365 */     if (!this.mHiddenItems.contains(Integer.valueOf(id))) {
/*  366 */       list.add(item);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected Map<String, Object> createItem(int id, Drawable drawable, String title, int controlType) {
/*  380 */     Map<String, Object> item = new HashMap<>();
/*  381 */     item.put("item_view_mode_picker_list_id", Integer.valueOf(id));
/*  382 */     item.put("item_view_mode_picker_list_icon", drawable);
/*  383 */     item.put("item_view_mode_picker_list_text", title);
/*  384 */     item.put("item_view_mode_picker_list_control", Integer.valueOf(controlType));
/*  385 */     return item;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setViewModePickerDialogFragmentListener(ViewModePickerDialogFragmentListener listener) {
/*  394 */     this.mViewModePickerDialogListener = listener;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCreate(Bundle savedInstanceState) {
/*  405 */     super.onCreate(savedInstanceState);
/*  406 */     if (getArguments() == null) {
/*      */       return;
/*      */     }
/*  409 */     int mode = getArguments().getInt("current_view_mode", PDFViewCtrl.PagePresentationMode.SINGLE.getValue());
/*  410 */     this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.valueOf(mode);
/*  411 */     this.mIsRtlMode = getArguments().getBoolean("current_rtl_mode", false);
/*  412 */     this.mIsReflowMode = getArguments().getBoolean("current_reflow_mode", false);
/*  413 */     this.mReflowTextSize = getArguments().getInt("current_reflow_text_size", 100);
/*  414 */     this.mHasEventAction = getArguments().getBoolean("action", false);
/*  415 */     this.mHiddenItems = getArguments().getIntegerArrayList("disabled_view_mode_items");
/*  416 */     if (this.mHiddenItems == null) {
/*  417 */       this.mHiddenItems = new ArrayList<>();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onStart() {
/*  423 */     super.onStart();
/*  424 */     AnalyticsHandlerAdapter.getInstance().sendTimedEvent(24);
/*      */   }
/*      */ 
/*      */   
/*      */   public void onStop() {
/*  429 */     super.onStop();
/*  430 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(24);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDismiss(DialogInterface dialog) {
/*  440 */     if (this.mViewModePickerDialogListener != null) {
/*  441 */       this.mViewModePickerDialogListener.onViewModePickerDialogFragmentDismiss();
/*      */     }
/*  443 */     super.onDismiss(dialog);
/*  444 */     AnalyticsHandlerAdapter.getInstance().sendEvent(25, 
/*  445 */         AnalyticsParam.noActionParam(this.mHasEventAction));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setListViewHeightBasedOnChildren(ListView listView, LinearLayout layout) {
/*  455 */     if (this.mListAdapter == null) {
/*      */       return;
/*      */     }
/*      */ 
/*      */     
/*  460 */     int totalHeight = 0;
/*  461 */     for (int i = 0; i < this.mListAdapter.getCount(); i++) {
/*  462 */       View listItem = this.mListAdapter.getView(i, null, (ViewGroup)listView);
/*  463 */       listItem.setLayoutParams(new ViewGroup.LayoutParams(0, 0));
/*  464 */       listItem.measure(0, 0);
/*  465 */       totalHeight += listItem.getMeasuredHeight();
/*      */     } 
/*      */     
/*  468 */     ViewGroup.LayoutParams params = listView.getLayoutParams();
/*  469 */     params.height = totalHeight + listView.getDividerHeight() * (this.mListAdapter.getCount() - 1) + 10;
/*  470 */     listView.setLayoutParams(params);
/*  471 */     listView.requestLayout();
/*      */     
/*  473 */     ViewGroup.LayoutParams params2 = layout.getLayoutParams();
/*  474 */     params2.height = totalHeight + listView.getDividerHeight() * (this.mListAdapter.getCount() - 1) + 10;
/*  475 */     layout.setLayoutParams(params2);
/*  476 */     layout.requestLayout();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @NonNull
/*      */   @SuppressLint({"InflateParams"})
/*      */   public Dialog onCreateDialog(Bundle savedInstanceState) {
/*  490 */     AlertDialog.Builder builder = new AlertDialog.Builder((Context)getActivity());
/*  491 */     LayoutInflater inflater = getActivity().getLayoutInflater();
/*  492 */     View view = inflater.inflate(R.layout.fragment_view_mode_picker_dialog, null);
/*  493 */     builder.setView(view);
/*      */     
/*  495 */     this.mViewModeLayout = (LinearLayout)view.findViewById(R.id.fragment_view_mode_button_table_layout);
/*  496 */     View reflowItem = this.mViewModeLayout.findViewById(R.id.fragment_view_mode_button_reflow);
/*  497 */     View facingCover = this.mViewModeLayout.findViewById(R.id.fragment_view_mode_button_cover);
/*  498 */     if (this.mHiddenItems.contains(Integer.valueOf(109))) {
/*  499 */       reflowItem.setVisibility(8);
/*      */     }
/*  501 */     if (this.mHiddenItems.contains(Integer.valueOf(110))) {
/*  502 */       facingCover.setVisibility(8);
/*      */     }
/*  504 */     for (int i = 0; i < this.mViewModeLayout.getChildCount() * 2; i++) {
/*  505 */       View child = ((TableRow)this.mViewModeLayout.getChildAt(i / 2)).getChildAt(i % 2);
/*  506 */       child.setOnClickListener(new View.OnClickListener()
/*      */           {
/*      */             public void onClick(View v) {
/*  509 */               int id = v.getId();
/*  510 */               if (id == R.id.fragment_view_mode_button_reflow && 
/*  511 */                 ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null && ViewModePickerDialogFragment.this.mViewModePickerDialogListener
/*  512 */                 .checkTabConversionAndAlert(R.string.cant_reflow_while_converting_message, true)) {
/*      */                 return;
/*      */               }
/*      */ 
/*      */               
/*  517 */               ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  518 */               int viewMode = -1;
/*  519 */               if (id == R.id.fragment_view_mode_button_single) {
/*  520 */                 viewMode = 1;
/*  521 */               } else if (id == R.id.fragment_view_mode_button_facing) {
/*  522 */                 viewMode = 2;
/*  523 */               } else if (id == R.id.fragment_view_mode_button_cover) {
/*  524 */                 viewMode = 3;
/*  525 */               } else if (id == R.id.fragment_view_mode_button_reflow) {
/*  526 */                 viewMode = 4;
/*      */               } 
/*      */               
/*  529 */               if (viewMode != -1) {
/*  530 */                 boolean isCurrent = (id == ViewModePickerDialogFragment.this.getActiveMode());
/*  531 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  532 */                     AnalyticsParam.viewModeParam(viewMode, isCurrent));
/*      */               } 
/*      */               
/*  535 */               if (id != ViewModePickerDialogFragment.this.getActiveMode()) {
/*      */                 
/*  537 */                 ViewModePickerDialogFragment.this.setActiveMode(v.getId());
/*      */                 
/*  539 */                 ViewModePickerDialogFragment.this.setViewMode(ViewModePickerDialogFragment.this.isContinuousMode());
/*  540 */                 ViewModePickerDialogFragment.this.updateDialogLayout();
/*      */               } 
/*      */             }
/*      */           });
/*  544 */       child.setOnLongClickListener(new View.OnLongClickListener()
/*      */           {
/*      */             public boolean onLongClick(View v) {
/*  547 */               String description = v.getContentDescription().toString();
/*      */               
/*  549 */               int[] location = new int[2];
/*  550 */               v.getLocationOnScreen(location);
/*  551 */               CommonToast.showText((Context)ViewModePickerDialogFragment.this.getActivity(), description, 0, 8388659, location[0], location[1] + v.getMeasuredHeight() / 2);
/*  552 */               return true;
/*      */             }
/*      */           });
/*      */       
/*  556 */       View img = ((LinearLayout)child).getChildAt(0);
/*  557 */       if (img instanceof ImageView) {
/*  558 */         ImageView imageView = (ImageView)img;
/*  559 */         Drawable icon = imageView.getDrawable();
/*  560 */         if (icon != null && icon.getConstantState() != null) {
/*  561 */           icon = DrawableCompat.wrap(icon.getConstantState().newDrawable()).mutate();
/*  562 */           ColorStateList iconTintList = AppCompatResources.getColorStateList((Context)getActivity(), R.color.selector_action_item_icon_color);
/*  563 */           DrawableCompat.setTintList(icon, iconTintList);
/*      */         } 
/*  565 */         imageView.setImageDrawable(icon);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  570 */     this.mOptionListView = (ListView)view.findViewById(R.id.fragment_view_mode_picker_dialog_listview);
/*  571 */     this.mOptionListView.setChoiceMode(2);
/*  572 */     this.mOptionListView.setItemsCanFocus(false);
/*      */     
/*  574 */     View v = new View((Context)getActivity());
/*  575 */     v.setBackground(this.mOptionListView.getDivider());
/*  576 */     v.setLayoutParams((ViewGroup.LayoutParams)new AbsListView.LayoutParams(-1, this.mOptionListView.getDividerHeight()));
/*  577 */     this.mOptionListView.addHeaderView(v);
/*      */     
/*  579 */     this.mListAdapter = new SeparatedListAdapter((Context)getActivity());
/*  580 */     populateOptionsList();
/*  581 */     this.mOptionListView.setAdapter((ListAdapter)this.mListAdapter);
/*      */     
/*  583 */     setListViewHeightBasedOnChildren(this.mOptionListView, (LinearLayout)view.findViewById(R.id.scroll_layout));
/*      */     
/*  585 */     this.mOptionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
/*      */           public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
/*      */             boolean checked;
/*  588 */             switch ((int)id) {
/*      */               case 100:
/*  590 */                 checked = ViewModePickerDialogFragment.this.mOptionListView.isItemChecked(position);
/*  591 */                 ViewModePickerDialogFragment.this.setViewMode(checked);
/*  592 */                 ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  593 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  594 */                     AnalyticsParam.viewModeParam(checked ? 5 : 6));
/*      */                 break;
/*      */               case 106:
/*  597 */                 ViewModePickerDialogFragment.this.mIsRtlMode = !ViewModePickerDialogFragment.this.mIsRtlMode;
/*  598 */                 if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/*  599 */                   ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeSelected("pref_rtlmode");
/*      */                 }
/*  601 */                 ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  602 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  603 */                     AnalyticsParam.viewModeParam(ViewModePickerDialogFragment.this.mIsRtlMode ? 11 : 12));
/*      */                 break;
/*      */               case 103:
/*  606 */                 if (!ViewModePickerDialogFragment.this.mIsReflowMode) {
/*  607 */                   if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/*  608 */                     ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeSelected("rotation");
/*      */                   }
/*  610 */                   ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  611 */                   AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  612 */                       AnalyticsParam.viewModeParam(13));
/*      */                 } 
/*      */                 break;
/*      */               case 105:
/*  616 */                 if (!ViewModePickerDialogFragment.this.mIsReflowMode) {
/*  617 */                   if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/*  618 */                     ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeSelected("user_crop");
/*      */                   }
/*  620 */                   ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  621 */                   AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  622 */                       AnalyticsParam.viewModeParam(14));
/*  623 */                   ViewModePickerDialogFragment.this.dismiss();
/*      */                 } 
/*      */                 break;
/*      */             } 
/*      */ 
/*      */ 
/*      */           
/*      */           }
/*      */         });
/*  632 */     builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener()
/*      */         {
/*      */           public void onClick(DialogInterface dialog, int i) {
/*  635 */             ViewModePickerDialogFragment.this.dismiss();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*  640 */     int checkedItem = -1;
/*  641 */     if (this.mIsReflowMode) {
/*  642 */       checkedItem = R.id.fragment_view_mode_button_reflow;
/*      */     } else {
/*  644 */       switch (this.mCurrentViewMode) {
/*      */         case SINGLE:
/*  646 */           checkedItem = R.id.fragment_view_mode_button_single;
/*      */           break;
/*      */         case SINGLE_CONT:
/*  649 */           checkedItem = R.id.fragment_view_mode_button_single;
/*      */           break;
/*      */         case FACING:
/*  652 */           checkedItem = R.id.fragment_view_mode_button_facing;
/*      */           break;
/*      */         case FACING_COVER:
/*  655 */           checkedItem = R.id.fragment_view_mode_button_cover;
/*      */           break;
/*      */         case FACING_CONT:
/*  658 */           checkedItem = R.id.fragment_view_mode_button_facing;
/*      */           break;
/*      */         case FACING_COVER_CONT:
/*  661 */           checkedItem = R.id.fragment_view_mode_button_cover;
/*      */           break;
/*      */       } 
/*      */     } 
/*  665 */     if (this.mIsReflowMode)
/*  666 */       listSwapFirstSecondViewModeOptions(); 
/*  667 */     setActiveMode(checkedItem);
/*      */     
/*  669 */     updateDialogLayout();
/*      */     
/*  671 */     final ScrollView sView = (ScrollView)view.findViewById(R.id.viewMode_scrollView);
/*      */     try {
/*  673 */       sView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
/*      */           {
/*      */             public void onGlobalLayout()
/*      */             {
/*      */               try {
/*  678 */                 sView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
/*  679 */               } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */               
/*  683 */               sView.fullScroll(33);
/*      */             }
/*      */           });
/*  686 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  689 */     return (Dialog)builder.create();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isContinuousMode() {
/*  694 */     boolean continuous = false;
/*      */     
/*  696 */     switch (this.mCurrentViewMode) {
/*      */       case SINGLE:
/*  698 */         continuous = false;
/*      */         break;
/*      */       case SINGLE_CONT:
/*  701 */         continuous = true;
/*      */         break;
/*      */       case FACING:
/*  704 */         continuous = false;
/*      */         break;
/*      */       case FACING_COVER:
/*  707 */         continuous = false;
/*      */         break;
/*      */       case FACING_CONT:
/*  710 */         continuous = true;
/*      */         break;
/*      */       case FACING_COVER_CONT:
/*  713 */         continuous = true;
/*      */         break;
/*      */     } 
/*      */     
/*  717 */     return continuous;
/*      */   }
/*      */   
/*      */   private int getActiveMode() {
/*  721 */     for (int i = 0; i < this.mViewModeLayout.getChildCount() * 2; i++) {
/*  722 */       View view = ((TableRow)this.mViewModeLayout.getChildAt(i / 2)).getChildAt(i % 2);
/*  723 */       if (view.isActivated()) return view.getId(); 
/*      */     } 
/*  725 */     return -1;
/*      */   }
/*      */   
/*      */   private void setActiveMode(int id) {
/*  729 */     for (int i = 0; i < this.mViewModeLayout.getChildCount() * 2; i++) {
/*  730 */       View view = ((TableRow)this.mViewModeLayout.getChildAt(i / 2)).getChildAt(i % 2);
/*  731 */       view.setActivated((id == view.getId()));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setButtonChecked(@IdRes int buttonId, @DrawableRes int iconId, boolean checked) {
/*  736 */     FragmentActivity fragmentActivity = getActivity();
/*  737 */     if (this.mColorModeLayout == null || fragmentActivity == null) {
/*      */       return;
/*      */     }
/*      */     
/*      */     try {
/*  742 */       LayerDrawable layerDrawable = (LayerDrawable)AppCompatResources.getDrawable((Context)fragmentActivity, iconId);
/*  743 */       if (layerDrawable != null) {
/*  744 */         if (checked) {
/*  745 */           GradientDrawable shape = (GradientDrawable)layerDrawable.findDrawableByLayerId(R.id.selectable_shape);
/*  746 */           if (shape != null) {
/*  747 */             shape.mutate();
/*  748 */             int w = (int)Utils.convDp2Pix((Context)fragmentActivity, 4.0F);
/*  749 */             shape.setStroke(w, Utils.getAccentColor((Context)fragmentActivity));
/*      */           } 
/*      */         } 
/*  752 */         ((ImageButton)this.mColorModeLayout.findViewById(buttonId)).setImageDrawable((Drawable)layerDrawable);
/*      */       } 
/*  754 */     } catch (Exception e) {
/*  755 */       AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setActiveColorMode(@IdRes int id) {
/*  765 */     Context context = getContext();
/*  766 */     if (context == null) {
/*      */       return;
/*      */     }
/*      */     
/*  770 */     if (id == R.id.item_view_mode_picker_customcolor_button) {
/*  771 */       dismiss();
/*  772 */       CustomColorModeDialogFragment frag = CustomColorModeDialogFragment.newInstance(PdfViewCtrlSettingsManager.getCustomColorModeBGColor(context), 
/*  773 */           PdfViewCtrlSettingsManager.getCustomColorModeTextColor(context));
/*  774 */       frag.setCustomColorModeSelectedListener(new CustomColorModeDialogFragment.CustomColorModeSelectedListener()
/*      */           {
/*      */             public void onCustomColorModeSelected(int bgColor, int txtColor) {
/*  777 */               if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/*  778 */                 ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onCustomColorModeSelected(bgColor, txtColor);
/*      */               }
/*      */             }
/*      */           });
/*  782 */       frag.setStyle(0, R.style.CustomAppTheme);
/*  783 */       FragmentManager fragmentManager = getFragmentManager();
/*  784 */       if (fragmentManager != null) {
/*  785 */         frag.show(fragmentManager, "custom_color_mode");
/*      */       }
/*      */     } else {
/*  788 */       setButtonChecked(R.id.item_view_mode_picker_daymode_button, R.drawable.ic_daymode_icon, (id == R.id.item_view_mode_picker_daymode_button));
/*      */       
/*  790 */       setButtonChecked(R.id.item_view_mode_picker_nightmode_button, R.drawable.ic_nightmode_icon, (id == R.id.item_view_mode_picker_nightmode_button));
/*      */       
/*  792 */       setButtonChecked(R.id.item_view_mode_picker_sepiamode_button, R.drawable.ic_sepiamode_icon, (id == R.id.item_view_mode_picker_sepiamode_button));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDialogLayout() {
/*  798 */     int activeId = getActiveMode();
/*  799 */     this.mIsReflowMode = (activeId == R.id.fragment_view_mode_button_reflow);
/*      */     
/*  801 */     boolean continuous = isContinuousMode();
/*      */     
/*  803 */     for (int i = 0; i < this.mOptionListView.getCount(); i++) {
/*  804 */       int id = (int)this.mOptionListView.getItemIdAtPosition(i);
/*  805 */       switch (id) {
/*      */         
/*      */         case 100:
/*  808 */           this.mOptionListView.setItemChecked(i, continuous);
/*      */           break;
/*      */         
/*      */         case 106:
/*  812 */           this.mOptionListView.setItemChecked(i, this.mIsRtlMode);
/*      */           break;
/*      */       } 
/*      */     } 
/*  816 */     this.mListAdapter.notifyDataSetChanged();
/*      */   }
/*      */   
/*      */   private void listSwapFirstSecondViewModeOptions() {
/*  820 */     if (this.mViewModeOptionsList.size() > 1) {
/*  821 */       Map<String, Object> tmp = this.mViewModeOptionsList.get(0);
/*  822 */       this.mViewModeOptionsList.set(0, this.mViewModeOptionsList.get(1));
/*  823 */       this.mViewModeOptionsList.set(1, tmp);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void setViewMode(boolean verticalScrolling) {
/*  828 */     int activeId = getActiveMode();
/*  829 */     if (this.mViewModeOptionsList.size() > 0 && activeId != R.id.fragment_view_mode_button_reflow && ((Integer)((Map)this.mViewModeOptionsList
/*  830 */       .get(0)).get("item_view_mode_picker_list_id")).intValue() == 101) {
/*  831 */       listSwapFirstSecondViewModeOptions();
/*      */     }
/*  833 */     if (verticalScrolling) {
/*      */       
/*  835 */       if (activeId == R.id.fragment_view_mode_button_single) {
/*  836 */         if (this.mViewModePickerDialogListener != null) {
/*  837 */           this.mViewModePickerDialogListener.onViewModeSelected("continuous");
/*      */         }
/*  839 */         this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.SINGLE_CONT;
/*  840 */       } else if (activeId == R.id.fragment_view_mode_button_facing) {
/*  841 */         if (this.mViewModePickerDialogListener != null) {
/*  842 */           this.mViewModePickerDialogListener.onViewModeSelected("facing_cont");
/*      */         }
/*  844 */         this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.FACING_CONT;
/*  845 */       } else if (activeId == R.id.fragment_view_mode_button_cover) {
/*  846 */         if (this.mViewModePickerDialogListener != null) {
/*  847 */           this.mViewModePickerDialogListener.onViewModeSelected("facingcover_cont");
/*      */         }
/*  849 */         this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.FACING_COVER_CONT;
/*  850 */       } else if (activeId == R.id.fragment_view_mode_button_reflow) {
/*  851 */         if (this.mViewModeOptionsList.size() > 0 && ((Integer)((Map)this.mViewModeOptionsList.get(0)).get("item_view_mode_picker_list_id")).intValue() == 100) {
/*  852 */           listSwapFirstSecondViewModeOptions();
/*      */         }
/*  854 */         if (this.mViewModePickerDialogListener != null) {
/*  855 */           this.mViewModePickerDialogListener.onViewModeSelected("pref_reflowmode");
/*      */         }
/*      */       }
/*      */     
/*      */     }
/*  860 */     else if (activeId == R.id.fragment_view_mode_button_single) {
/*  861 */       if (this.mViewModePickerDialogListener != null) {
/*  862 */         this.mViewModePickerDialogListener.onViewModeSelected("singlepage");
/*      */       }
/*  864 */       this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.SINGLE;
/*  865 */     } else if (activeId == R.id.fragment_view_mode_button_facing) {
/*  866 */       if (this.mViewModePickerDialogListener != null) {
/*  867 */         this.mViewModePickerDialogListener.onViewModeSelected("facing");
/*      */       }
/*  869 */       this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.FACING;
/*  870 */     } else if (activeId == R.id.fragment_view_mode_button_cover) {
/*  871 */       if (this.mViewModePickerDialogListener != null) {
/*  872 */         this.mViewModePickerDialogListener.onViewModeSelected("facingcover");
/*      */       }
/*  874 */       this.mCurrentViewMode = PDFViewCtrl.PagePresentationMode.FACING_COVER;
/*  875 */     } else if (activeId == R.id.fragment_view_mode_button_reflow) {
/*  876 */       if (this.mViewModeOptionsList.size() > 0 && ((Integer)((Map)this.mViewModeOptionsList.get(0)).get("item_view_mode_picker_list_id")).intValue() == 100) {
/*  877 */         listSwapFirstSecondViewModeOptions();
/*      */       }
/*  879 */       if (this.mViewModePickerDialogListener != null) {
/*  880 */         this.mViewModePickerDialogListener.onViewModeSelected("pref_reflowmode");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected class ViewModeEntryAdapter
/*      */     extends ArrayAdapter<Map<String, Object>>
/*      */   {
/*      */     private List<Map<String, Object>> mEntries;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ColorStateList mIconTintList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ColorStateList mTextTintList;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ViewModeEntryAdapter(Context context, List<Map<String, Object>> entries) {
/*  952 */       super(context, 0, entries);
/*  953 */       this.mEntries = entries;
/*  954 */       this.mIconTintList = AppCompatResources.getColorStateList(getContext(), R.color.selector_color);
/*  955 */       this.mTextTintList = AppCompatResources.getColorStateList(getContext(), R.color.selector_color);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setEntries(List<Map<String, Object>> list) {
/*  964 */       this.mEntries = list;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     @NonNull
/*      */     public View getView(int position, View convertView, @NonNull ViewGroup parent) {
/*      */       final ViewHolder holder;
/*  975 */       Map<String, Object> map = this.mEntries.get(position);
/*  976 */       int id = ((Integer)map.get("item_view_mode_picker_list_id")).intValue();
/*      */       
/*  978 */       if (convertView == null || convertView.getTag() == null) {
/*  979 */         holder = new ViewHolder();
/*  980 */         if (id == 108) {
/*  981 */           convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_view_mode_color_mode_row, parent, false);
/*  982 */           RelativeLayout layoutView = (RelativeLayout)convertView;
/*  983 */           LinearLayout colorBtnLayout = (LinearLayout)layoutView.findViewById(R.id.item_view_mode_picker_modebtn_layout);
/*  984 */           for (int i = 0; i < colorBtnLayout.getChildCount(); i++) {
/*  985 */             View child = colorBtnLayout.getChildAt(i);
/*  986 */             if (child instanceof ImageButton) {
/*  987 */               child.setOnClickListener(new View.OnClickListener()
/*      */                   {
/*      */                     public void onClick(View v) {
/*  990 */                       int id1 = v.getId();
/*  991 */                       ViewModePickerDialogFragment.this.setActiveColorMode(id1);
/*      */                       
/*  993 */                       if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/*  994 */                         if (id1 == R.id.item_view_mode_picker_daymode_button) {
/*  995 */                           ViewModePickerDialogFragment.this.mHasEventAction = true;
/*  996 */                           AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/*  997 */                               AnalyticsParam.viewModeParam(7, 
/*  998 */                                 (PdfViewCtrlSettingsManager.getColorMode(ViewModeEntryAdapter.this.getContext()) == 1)));
/*  999 */                           if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeColorSelected(1))
/*      */                           {
/* 1001 */                             ViewModePickerDialogFragment.this.dismiss();
/*      */                           }
/* 1003 */                         } else if (id1 == R.id.item_view_mode_picker_nightmode_button) {
/* 1004 */                           ViewModePickerDialogFragment.this.mHasEventAction = true;
/* 1005 */                           AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/* 1006 */                               AnalyticsParam.viewModeParam(8, 
/* 1007 */                                 (PdfViewCtrlSettingsManager.getColorMode(ViewModeEntryAdapter.this.getContext()) == 3)));
/* 1008 */                           if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeColorSelected(3))
/*      */                           {
/* 1010 */                             ViewModePickerDialogFragment.this.dismiss();
/*      */                           }
/* 1012 */                         } else if (id1 == R.id.item_view_mode_picker_sepiamode_button) {
/* 1013 */                           ViewModePickerDialogFragment.this.mHasEventAction = true;
/* 1014 */                           AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/* 1015 */                               AnalyticsParam.viewModeParam(9, 
/* 1016 */                                 (PdfViewCtrlSettingsManager.getColorMode(ViewModeEntryAdapter.this.getContext()) == 2)));
/* 1017 */                           if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onViewModeColorSelected(2))
/*      */                           {
/* 1019 */                             ViewModePickerDialogFragment.this.dismiss();
/*      */                           }
/* 1021 */                         } else if (id1 == R.id.item_view_mode_picker_customcolor_button) {
/* 1022 */                           ViewModePickerDialogFragment.this.mHasEventAction = true;
/* 1023 */                           AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/* 1024 */                               AnalyticsParam.viewModeParam(10, 
/* 1025 */                                 (PdfViewCtrlSettingsManager.getColorMode(ViewModeEntryAdapter.this.getContext()) == 4)));
/*      */                         } 
/*      */                       }
/*      */                     }
/*      */                   });
/*      */             }
/*      */           } 
/* 1032 */           applyTintList((ImageView)layoutView.findViewById(R.id.item_view_mode_picker_color_list_icon), this.mIconTintList);
/* 1033 */           ViewModePickerDialogFragment.this.mColorModeLayout = layoutView;
/* 1034 */           convertView.setTag(holder);
/*      */         } else {
/* 1036 */           convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item_view_mode_picker_list, parent, false);
/*      */           
/* 1038 */           holder.icon = (ImageView)convertView.findViewById(R.id.item_view_mode_picker_list_icon);
/* 1039 */           holder.text = (TextView)convertView.findViewById(R.id.item_view_mode_picker_list_text);
/* 1040 */           holder.radioButton = (RadioButton)convertView.findViewById(R.id.item_view_mode_picker_list_radiobutton);
/* 1041 */           holder.switchButton = (InertSwitch)convertView.findViewById(R.id.item_view_mode_picker_list_switch);
/* 1042 */           holder.sizeLayout = (LinearLayout)convertView.findViewById(R.id.item_view_mode_picker_list_size_layout);
/* 1043 */           holder.decButton = (ImageButton)convertView.findViewById(R.id.item_view_mode_picker_list_dec);
/* 1044 */           holder.sizeText = (TextView)convertView.findViewById(R.id.item_view_mode_picker_list_size_text);
/* 1045 */           holder.incButton = (ImageButton)convertView.findViewById(R.id.item_view_mode_picker_list_inc);
/*      */           
/* 1047 */           convertView.setTag(holder);
/*      */         } 
/*      */         try {
/* 1050 */           int res = -1;
/* 1051 */           int currentColorMode = PdfViewCtrlSettingsManager.getColorMode(getContext());
/* 1052 */           if (currentColorMode == 3) {
/* 1053 */             res = R.id.item_view_mode_picker_nightmode_button;
/* 1054 */           } else if (currentColorMode == 2) {
/* 1055 */             res = R.id.item_view_mode_picker_sepiamode_button;
/* 1056 */           } else if (currentColorMode == 1) {
/* 1057 */             res = R.id.item_view_mode_picker_daymode_button;
/*      */           } 
/* 1059 */           if (res != -1) {
/* 1060 */             ViewModePickerDialogFragment.this.setActiveColorMode(res);
/*      */           }
/* 1062 */         } catch (Exception e) {
/* 1063 */           AnalyticsHandlerAdapter.getInstance().sendException(e);
/*      */         } 
/*      */       } else {
/* 1066 */         holder = (ViewHolder)convertView.getTag();
/*      */       } 
/*      */ 
/*      */       
/* 1070 */       switch (id) {
/*      */         case 107:
/* 1072 */           return new View(getContext());
/*      */         case 100:
/* 1074 */           if (ViewModePickerDialogFragment.this.mIsReflowMode) return new View(getContext()); 
/*      */           break;
/*      */         case 101:
/* 1077 */           if (!ViewModePickerDialogFragment.this.mIsReflowMode) return new View(getContext()); 
/*      */           break;
/*      */         case 103:
/* 1080 */           holder.icon.setEnabled(!ViewModePickerDialogFragment.this.mIsReflowMode);
/* 1081 */           holder.text.setEnabled(!ViewModePickerDialogFragment.this.mIsReflowMode);
/*      */           break;
/*      */         case 105:
/* 1084 */           holder.icon.setEnabled(!ViewModePickerDialogFragment.this.mIsReflowMode);
/* 1085 */           holder.text.setEnabled(!ViewModePickerDialogFragment.this.mIsReflowMode);
/*      */           break;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1091 */       if (id == 108) {
/* 1092 */         return convertView;
/*      */       }
/*      */       
/* 1095 */       Drawable icon = (Drawable)map.get("item_view_mode_picker_list_icon");
/* 1096 */       holder.icon.setImageDrawable(icon);
/* 1097 */       applyTintList(holder.icon, this.mIconTintList);
/*      */       
/* 1099 */       holder.text.setText((String)map.get("item_view_mode_picker_list_text"));
/* 1100 */       holder.text.setTextColor(this.mTextTintList);
/*      */       
/* 1102 */       int controlType = ((Integer)map.get("item_view_mode_picker_list_control")).intValue();
/* 1103 */       holder.radioButton.setVisibility((controlType == 0) ? 0 : 8);
/* 1104 */       holder.switchButton.setVisibility((controlType == 1) ? 0 : 8);
/* 1105 */       holder.sizeLayout.setVisibility((controlType == 2) ? 0 : 8);
/*      */       
/* 1107 */       if (controlType == 2) {
/*      */         
/* 1109 */         applyTintList((ImageView)holder.decButton, this.mIconTintList);
/* 1110 */         applyTintList((ImageView)holder.incButton, this.mIconTintList);
/*      */         
/* 1112 */         if (ViewModePickerDialogFragment.this.mReflowTextSize == ReflowPagerAdapter.TH_MIN_SCAlE) {
/* 1113 */           holder.decButton.setEnabled(false);
/*      */         }
/* 1115 */         if (ViewModePickerDialogFragment.this.mReflowTextSize == ReflowPagerAdapter.TH_MAX_SCAlE) {
/* 1116 */           holder.incButton.setEnabled(false);
/*      */         }
/*      */         
/* 1119 */         holder.decButton.setOnClickListener(new View.OnClickListener()
/*      */             {
/*      */               public void onClick(View v) {
/* 1122 */                 if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/* 1123 */                   ViewModePickerDialogFragment.this.mReflowTextSize = ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onReflowZoomInOut(false);
/*      */                 }
/* 1125 */                 ViewModePickerDialogFragment.this.mHasEventAction = true;
/* 1126 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/* 1127 */                     AnalyticsParam.viewModeParam(16));
/* 1128 */                 if (ViewModePickerDialogFragment.this.mReflowTextSize == ReflowPagerAdapter.TH_MIN_SCAlE) {
/*      */                   
/* 1130 */                   holder.decButton.setEnabled(false);
/*      */                 } else {
/*      */                   
/* 1133 */                   holder.incButton.setEnabled(true);
/*      */                 } 
/*      */                 
/* 1136 */                 holder.sizeText.setText(String.format(Locale.getDefault(), "%d%%", new Object[] { Integer.valueOf(this.this$1.this$0.mReflowTextSize) }));
/*      */               }
/*      */             });
/* 1139 */         holder.incButton.setOnClickListener(new View.OnClickListener()
/*      */             {
/*      */               public void onClick(View v) {
/* 1142 */                 if (ViewModePickerDialogFragment.this.mViewModePickerDialogListener != null) {
/* 1143 */                   ViewModePickerDialogFragment.this.mReflowTextSize = ViewModePickerDialogFragment.this.mViewModePickerDialogListener.onReflowZoomInOut(true);
/*      */                 }
/* 1145 */                 ViewModePickerDialogFragment.this.mHasEventAction = true;
/* 1146 */                 AnalyticsHandlerAdapter.getInstance().sendEvent(26, 
/* 1147 */                     AnalyticsParam.viewModeParam(15));
/* 1148 */                 if (ViewModePickerDialogFragment.this.mReflowTextSize == ReflowPagerAdapter.TH_MAX_SCAlE) {
/*      */                   
/* 1150 */                   holder.incButton.setEnabled(false);
/*      */                 } else {
/*      */                   
/* 1153 */                   holder.decButton.setEnabled(true);
/*      */                 } 
/*      */                 
/* 1156 */                 holder.sizeText.setText(String.format(Locale.getDefault(), "%d%%", new Object[] { Integer.valueOf(this.this$1.this$0.mReflowTextSize) }));
/*      */               }
/*      */             });
/* 1159 */         holder.sizeText.setText(String.format(Locale.getDefault(), "%d%%", new Object[] { Integer.valueOf(this.this$0.mReflowTextSize) }));
/*      */       } 
/*      */       
/* 1162 */       return convertView;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getItemId(int position) {
/* 1173 */       Map<String, Object> map = this.mEntries.get(position);
/* 1174 */       return ((Integer)map.get("item_view_mode_picker_list_id")).intValue();
/*      */     }
/*      */     
/*      */     private void applyTintList(ImageView imageView, ColorStateList tintList) {
/* 1178 */       if (imageView == null) {
/*      */         return;
/*      */       }
/* 1181 */       Drawable icon = imageView.getDrawable();
/* 1182 */       if (icon != null && icon.getConstantState() != null) {
/*      */         try {
/* 1184 */           icon = DrawableCompat.wrap(icon.getConstantState().newDrawable()).mutate();
/* 1185 */           DrawableCompat.setTintList(icon, tintList);
/* 1186 */         } catch (NullPointerException nullPointerException) {}
/*      */       }
/*      */ 
/*      */       
/* 1190 */       imageView.setImageDrawable(icon);
/*      */     }
/*      */ 
/*      */     
/*      */     private class ViewHolder
/*      */     {
/*      */       ImageView icon;
/*      */       
/*      */       TextView text;
/*      */       
/*      */       RadioButton radioButton;
/*      */       
/*      */       InertSwitch switchButton;
/*      */       
/*      */       LinearLayout sizeLayout;
/*      */       
/*      */       ImageButton decButton;
/*      */       TextView sizeText;
/*      */       ImageButton incButton;
/*      */       
/*      */       private ViewHolder() {}
/*      */     }
/*      */   }
/*      */   
/*      */   protected class SeparatedListAdapter
/*      */     extends BaseAdapter
/*      */   {
/*      */     static final int TYPE_SECTION_HEADER = -1;
/*      */     final Map<String, Adapter> mSections;
/*      */     final ArrayAdapter<String> mHeaders;
/*      */     
/*      */     SeparatedListAdapter(Context context) {
/* 1222 */       this.mSections = new LinkedHashMap<>();
/* 1223 */       this.mHeaders = new ArrayAdapter(context, R.layout.listview_header_view_mode_picker_list);
/*      */     }
/*      */     
/*      */     void addSection(String section, Adapter adapter) {
/* 1227 */       this.mHeaders.add(section);
/* 1228 */       this.mSections.put(section, adapter);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Object getItem(int position) {
/* 1239 */       for (String section : this.mSections.keySet()) {
/* 1240 */         Adapter adapter = this.mSections.get(section);
/* 1241 */         int size = adapter.getCount() + 1;
/*      */ 
/*      */         
/* 1244 */         if (position == 0) return section; 
/* 1245 */         if (position < size) return adapter.getItem(position - 1);
/*      */ 
/*      */         
/* 1248 */         position -= size;
/*      */       } 
/* 1250 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getCount() {
/* 1261 */       int total = 0;
/* 1262 */       for (Adapter adapter : this.mSections.values()) {
/* 1263 */         total += adapter.getCount() + 1;
/*      */       }
/* 1265 */       return total;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getViewTypeCount() {
/* 1284 */       int total = 1;
/* 1285 */       for (Adapter adapter : this.mSections.values()) {
/* 1286 */         total += adapter.getViewTypeCount();
/*      */       }
/* 1288 */       return total;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getItemViewType(int position) {
/* 1300 */       int type = 1;
/* 1301 */       for (String section : this.mSections.keySet()) {
/* 1302 */         Adapter adapter = this.mSections.get(section);
/* 1303 */         int size = adapter.getCount() + 1;
/*      */ 
/*      */         
/* 1306 */         if (position == 0) return -1; 
/* 1307 */         if (position < size) return type + adapter.getItemViewType(position - 1);
/*      */ 
/*      */         
/* 1310 */         position -= size;
/* 1311 */         type += adapter.getViewTypeCount();
/*      */       } 
/* 1313 */       return -1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean isEnabled(int position) {
/* 1324 */       return (getItemViewType(position) != -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public View getView(int position, View convertView, ViewGroup parent) {
/* 1332 */       if (parent == null) {
/* 1333 */         return null;
/*      */       }
/* 1335 */       int sectionNum = 0;
/* 1336 */       for (String section : this.mSections.keySet()) {
/* 1337 */         Adapter adapter = this.mSections.get(section);
/* 1338 */         int size = adapter.getCount() + 1;
/*      */ 
/*      */         
/* 1341 */         if (position == 0) {
/* 1342 */           if (!Utils.isNullOrEmpty(section)) {
/* 1343 */             return this.mHeaders.getView(sectionNum, convertView, parent);
/*      */           }
/* 1345 */           return new View(parent.getContext());
/*      */         } 
/*      */         
/* 1348 */         if (position < size) return adapter.getView(position - 1, convertView, parent);
/*      */ 
/*      */         
/* 1351 */         position -= size;
/* 1352 */         sectionNum++;
/*      */       } 
/* 1354 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public long getItemId(int position) {
/* 1365 */       int id = position;
/* 1366 */       for (String section : this.mSections.keySet()) {
/* 1367 */         Adapter adapter = this.mSections.get(section);
/* 1368 */         int size = adapter.getCount() + 1;
/*      */ 
/*      */         
/* 1371 */         if (id == 0) return position; 
/* 1372 */         if (id < size) return adapter.getItemId(id - 1);
/*      */ 
/*      */         
/* 1375 */         id -= size;
/*      */       } 
/* 1377 */       return position;
/*      */     }
/*      */   }
/*      */   
/*      */   public static interface ViewModePickerDialogFragmentListener {
/*      */     void onViewModeSelected(String param1String);
/*      */     
/*      */     boolean onViewModeColorSelected(int param1Int);
/*      */     
/*      */     boolean onCustomColorModeSelected(int param1Int1, int param1Int2);
/*      */     
/*      */     void onViewModePickerDialogFragmentDismiss();
/*      */     
/*      */     int onReflowZoomInOut(boolean param1Boolean);
/*      */     
/*      */     boolean checkTabConversionAndAlert(int param1Int, boolean param1Boolean);
/*      */   }
/*      */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\ViewModePickerDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */