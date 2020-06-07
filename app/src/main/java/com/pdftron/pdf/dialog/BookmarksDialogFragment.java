/*     */ package com.pdftron.pdf.dialog;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.graphics.PorterDuff;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.DialogFragment;
/*     */ import androidx.fragment.app.FragmentActivity;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.controls.BookmarksTabLayout;
/*     */ import com.pdftron.pdf.controls.NavigationListDialogFragment;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.DialogFragmentTab;
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
/*     */ public class BookmarksDialogFragment
/*     */   extends DialogFragment
/*     */   implements TabLayout.BaseOnTabSelectedListener, Toolbar.OnMenuItemClickListener
/*     */ {
/*     */   public static final String BUNDLE_MODE = "BookmarksDialogFragment_mode";
/*     */   protected BookmarksTabLayout mTabLayout;
/*     */   protected Toolbar mToolbar;
/*     */   private ArrayList<DialogFragmentTab> mDialogFragmentTabs;
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */   private Bookmark mCurrentBookmark;
/*     */   protected int mInitialTabIndex;
/*     */   private boolean mHasEventAction;
/*  51 */   private static final String TAG = BookmarksDialogFragment.class.getName();
/*     */   private BookmarksTabLayout.BookmarksTabsListener mBookmarksTabsListener;
/*     */   private BookmarksDialogListener mBookmarksDialogListener;
/*     */   
/*     */   public enum DialogMode {
/*  56 */     DIALOG,
/*  57 */     SHEET;
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
/*  71 */   private DialogMode mDialogMode = DialogMode.DIALOG;
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
/*     */   public static BookmarksDialogFragment newInstance() {
/*  92 */     return newInstance((DialogMode)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BookmarksDialogFragment newInstance(DialogMode mode) {
/*  99 */     Bundle args = new Bundle();
/* 100 */     args.putString("BookmarksDialogFragment_mode", (mode != null) ? mode.name() : DialogMode.DIALOG.name());
/* 101 */     BookmarksDialogFragment fragment = new BookmarksDialogFragment();
/* 102 */     fragment.setArguments(args);
/* 103 */     return fragment;
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
/*     */   public BookmarksDialogFragment setPdfViewCtrl(@NonNull PDFViewCtrl pdfViewCtrl) {
/* 117 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 118 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksDialogFragment setDialogFragmentTabs(@NonNull ArrayList<DialogFragmentTab> dialogFragmentTabs) {
/* 129 */     return setDialogFragmentTabs(dialogFragmentTabs, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksDialogFragment setDialogFragmentTabs(@NonNull ArrayList<DialogFragmentTab> dialogFragmentTabs, int initialTabIndex) {
/* 140 */     this.mDialogFragmentTabs = dialogFragmentTabs;
/* 141 */     if (dialogFragmentTabs.size() > initialTabIndex) {
/* 142 */       this.mInitialTabIndex = initialTabIndex;
/*     */     }
/* 144 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksDialogFragment setCurrentBookmark(Bookmark currentBookmark) {
/* 155 */     this.mCurrentBookmark = currentBookmark;
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBookmarksDialogListener(BookmarksDialogListener listener) {
/* 166 */     this.mBookmarksDialogListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBookmarksTabsListener(BookmarksTabLayout.BookmarksTabsListener listener) {
/* 176 */     this.mBookmarksTabsListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/* 181 */     super.onCreate(savedInstanceState);
/*     */     
/* 183 */     Bundle args = getArguments();
/* 184 */     if (args != null) {
/* 185 */       this.mDialogMode = DialogMode.valueOf(args.getString("BookmarksDialogFragment_mode", DialogMode.DIALOG.name()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
/* 191 */     super.onCreateOptionsMenu(menu, inflater);
/* 192 */     menu.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
/* 200 */     View view = inflater.inflate(R.layout.fragment_bookmarks_dialog, null);
/*     */     
/* 202 */     FragmentActivity activity = getActivity();
/*     */     
/* 204 */     if (this.mPdfViewCtrl == null || activity == null)
/*     */     {
/*     */ 
/*     */       
/* 208 */       return view;
/*     */     }
/*     */     
/* 211 */     this.mToolbar = (Toolbar)view.findViewById(R.id.toolbar);
/*     */     
/* 213 */     if (this.mDialogMode == DialogMode.SHEET) {
/* 214 */       this.mToolbar.getContext().setTheme(R.style.NavigationListToolbarTheme);
/* 215 */       this.mToolbar.setNavigationIcon(null);
/* 216 */       this.mToolbar.setBackgroundColor(Utils.getBackgroundColor((Context)activity));
/* 217 */       this.mToolbar.setTitleTextColor(activity.getResources().getColor(R.color.navigation_list_title_color));
/*     */       
/* 219 */       if (Utils.isLollipop()) {
/* 220 */         this.mToolbar.setElevation(0.0F);
/*     */       }
/*     */     } 
/*     */     
/* 224 */     this.mToolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View view) {
/* 227 */             if (BookmarksDialogFragment.this.mBookmarksDialogListener != null) {
/* 228 */               BookmarksDialogFragment.this.mBookmarksDialogListener.onBookmarksDialogWillDismiss(BookmarksDialogFragment.this.mTabLayout.getSelectedTabPosition());
/*     */             }
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 234 */     this.mTabLayout = (BookmarksTabLayout)view.findViewById(R.id.tabhost);
/*     */     
/* 236 */     if (this.mDialogMode == DialogMode.SHEET) {
/* 237 */       if (Utils.isLollipop()) {
/* 238 */         this.mTabLayout.setElevation(0.0F);
/*     */       }
/* 240 */       this.mTabLayout.setBackgroundColor(Utils.getBackgroundColor((Context)activity));
/*     */     } 
/*     */     
/* 243 */     ViewPager viewPager = (ViewPager)view.findViewById(R.id.view_pager);
/*     */     
/* 245 */     if (this.mDialogFragmentTabs == null) {
/* 246 */       throw new NullPointerException("DialogFragmentTabs cannot be null. Call setDialogFragmentTabs(ArrayList<DialogFragmentTab>)");
/*     */     }
/*     */     
/* 249 */     this.mTabLayout.setup((Context)activity, getChildFragmentManager(), R.id.view_pager, this.mPdfViewCtrl, this.mCurrentBookmark);
/*     */     
/* 251 */     for (DialogFragmentTab dialogFragmentTab : this.mDialogFragmentTabs) {
/* 252 */       if (dialogFragmentTab._class == null || dialogFragmentTab.tabTag == null) {
/*     */         continue;
/*     */       }
/*     */       
/* 256 */       TabLayout.Tab tab = this.mTabLayout.newTab().setTag(dialogFragmentTab.tabTag);
/* 257 */       if (dialogFragmentTab.tabIcon != null) {
/* 258 */         dialogFragmentTab.tabIcon.mutate();
/* 259 */         tab.setIcon(dialogFragmentTab.tabIcon);
/*     */       } 
/* 261 */       if (dialogFragmentTab.tabText != null) {
/* 262 */         tab.setText(dialogFragmentTab.tabText);
/*     */       }
/* 264 */       this.mTabLayout.addTab(tab, dialogFragmentTab._class, dialogFragmentTab.bundle);
/*     */     } 
/*     */     
/* 267 */     this.mTabLayout.setupWithViewPager(viewPager);
/*     */     
/* 269 */     TabLayout.Tab selectedTab = this.mTabLayout.getTabAt(this.mInitialTabIndex);
/* 270 */     if (selectedTab != null) {
/* 271 */       selectedTab.select();
/* 272 */       setToolbarTitleBySelectedTab((String)selectedTab.getTag());
/* 273 */       this.mTabLayout.onTabSelected(selectedTab);
/*     */     } 
/*     */     
/* 276 */     int selectedColor = getSelectedColor(this.mTabLayout);
/* 277 */     int normalColor = getNormalColor(this.mTabLayout);
/* 278 */     this.mTabLayout.setTabTextColors(normalColor, selectedColor);
/*     */     
/* 280 */     for (int i = 0, cnt = this.mTabLayout.getTabCount(); i < cnt; i++) {
/* 281 */       TabLayout.Tab tab = this.mTabLayout.getTabAt(i);
/* 282 */       if (tab != null) {
/*     */ 
/*     */         
/* 285 */         Drawable icon = tab.getIcon();
/* 286 */         if (icon != null) {
/* 287 */           icon.mutate().setColorFilter(tab.isSelected() ? selectedColor : normalColor, PorterDuff.Mode.SRC_IN);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 292 */     if (this.mDialogFragmentTabs.size() == 1) {
/* 293 */       this.mTabLayout.setVisibility(8);
/*     */     }
/*     */     
/* 296 */     if (this.mBookmarksTabsListener != null) {
/* 297 */       this.mTabLayout.setBookmarksTabsListener(this.mBookmarksTabsListener);
/*     */     }
/* 299 */     this.mTabLayout.setAnalyticsEventListener(new NavigationListDialogFragment.AnalyticsEventListener()
/*     */         {
/*     */           public void onEventAction()
/*     */           {
/* 303 */             BookmarksDialogFragment.this.mHasEventAction = true;
/*     */           }
/*     */         });
/*     */     
/* 307 */     this.mHasEventAction = false;
/*     */ 
/*     */     
/* 310 */     this.mTabLayout.addOnTabSelectedListener(this);
/* 311 */     return view;
/*     */   }
/*     */   
/*     */   private void setToolbarTitleBySelectedTab(String tag) {
/* 315 */     String toolbarTitle = getString(R.string.bookmark_dialog_fragment_bookmark_tab_title);
/* 316 */     for (DialogFragmentTab dialogFragmentTab : this.mDialogFragmentTabs) {
/* 317 */       if (dialogFragmentTab._class == null || dialogFragmentTab.tabTag == null) {
/*     */         continue;
/*     */       }
/* 320 */       if (dialogFragmentTab.tabTag.equals(tag)) {
/* 321 */         toolbarTitle = dialogFragmentTab.toolbarTitle;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 326 */     this.mToolbar.setTitle(toolbarTitle);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onMenuItemClick(MenuItem menuItem) {
/* 331 */     int id = menuItem.getItemId();
/* 332 */     if (id == R.id.action_close) {
/* 333 */       if (this.mBookmarksDialogListener != null) {
/* 334 */         this.mBookmarksDialogListener.onBookmarksDialogWillDismiss(this.mTabLayout.getSelectedTabPosition());
/*     */       }
/* 336 */       return true;
/* 337 */     }  if (id == R.id.action_sort) {
/* 338 */       if (this.mTabLayout != null && this.mToolbar != null) {
/* 339 */         this.mTabLayout.onPrepareMenu(this.mToolbar.getMenu(), this.mTabLayout.getCurrentFragment());
/*     */       }
/* 341 */       return true;
/*     */     } 
/* 343 */     if (this.mTabLayout != null) {
/* 344 */       return this.mTabLayout.onMenuItemClicked(menuItem, this.mTabLayout.getCurrentFragment());
/*     */     }
/* 346 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStart() {
/* 352 */     super.onStart();
/* 353 */     if (this.mTabLayout != null) {
/* 354 */       TabLayout.Tab selectedTab = this.mTabLayout.getTabAt(this.mInitialTabIndex);
/* 355 */       AnalyticsHandlerAdapter.getInstance().sendTimedEvent(31, 
/* 356 */           AnalyticsParam.navigationListsTabParam(BookmarksTabLayout.getNavigationId(selectedTab)));
/*     */     } 
/*     */     
/* 359 */     setMenuVisible(this.mInitialTabIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStop() {
/* 364 */     super.onStop();
/* 365 */     AnalyticsHandlerAdapter.getInstance().endTimedEvent(31);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDestroyView() {
/* 370 */     super.onDestroyView();
/*     */     
/* 372 */     if (this.mTabLayout != null) {
/* 373 */       TabLayout.Tab selectedTab = this.mTabLayout.getTabAt(this.mTabLayout.getSelectedTabPosition());
/* 374 */       AnalyticsHandlerAdapter.getInstance().sendEvent(32, 
/* 375 */           AnalyticsParam.navigateListCloseParam(selectedTab, this.mHasEventAction));
/*     */       
/* 377 */       this.mTabLayout.removeAllFragments();
/* 378 */       this.mTabLayout.removeAllViews();
/* 379 */       this.mTabLayout.removeOnTabSelectedListener(this);
/* 380 */       if (this.mBookmarksDialogListener != null) {
/* 381 */         this.mBookmarksDialogListener.onBookmarksDialogDismissed(this.mTabLayout.getSelectedTabPosition());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabSelected(TabLayout.Tab tab) {
/* 391 */     setToolbarTitleBySelectedTab((String)tab.getTag());
/* 392 */     Drawable icon = tab.getIcon();
/* 393 */     if (icon != null) {
/* 394 */       setTabIconColor(icon, true);
/*     */     }
/* 396 */     setMenuVisible(tab.getPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabUnselected(TabLayout.Tab tab) {
/* 404 */     Drawable icon = tab.getIcon();
/* 405 */     FragmentActivity activity = getActivity();
/* 406 */     if (icon != null && activity != null) {
/* 407 */       setTabIconColor(icon, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabReselected(TabLayout.Tab tab) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getSelectedColor(@NonNull BookmarksTabLayout tabLayout) {
/* 420 */     return (this.mDialogMode == DialogMode.SHEET) ? tabLayout
/* 421 */       .getTabTintSelectedColorSheet() : tabLayout
/* 422 */       .getTabTintSelectedColorDialog();
/*     */   }
/*     */   
/*     */   private int getNormalColor(@NonNull BookmarksTabLayout tabLayout) {
/* 426 */     return (this.mDialogMode == DialogMode.SHEET) ? tabLayout
/* 427 */       .getTabTintColorSheet() : tabLayout
/* 428 */       .getTabTintColorDialog();
/*     */   }
/*     */   
/*     */   private void setTabIconColor(Drawable icon, boolean selected) {
/* 432 */     if (icon != null && this.mTabLayout != null) {
/* 433 */       int selectedColor = getSelectedColor(this.mTabLayout);
/* 434 */       int normalColor = getNormalColor(this.mTabLayout);
/* 435 */       icon.mutate().setColorFilter(selected ? selectedColor : normalColor, PorterDuff.Mode.SRC_IN);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setMenuVisible(int position) {
/* 440 */     if (this.mToolbar != null && this.mTabLayout != null) {
/* 441 */       this.mToolbar.getMenu().clear();
/* 442 */       DialogFragmentTab tabInfo = this.mDialogFragmentTabs.get(position);
/* 443 */       if (tabInfo != null && tabInfo.menuResId != 0) {
/* 444 */         this.mToolbar.inflateMenu(tabInfo.menuResId);
/*     */       }
/* 446 */       if (this.mDialogMode == DialogMode.SHEET) {
/* 447 */         this.mToolbar.inflateMenu(R.menu.fragment_navigation_list);
/*     */       }
/* 449 */       this.mToolbar.setOnMenuItemClickListener(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface BookmarksDialogListener {
/*     */     void onBookmarksDialogWillDismiss(int param1Int);
/*     */     
/*     */     void onBookmarksDialogDismissed(int param1Int);
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\BookmarksDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */