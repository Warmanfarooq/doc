/*     */ package com.pdftron.pdf.controls;
/*     */ 
/*     */ import android.content.Context;
/*     */ import android.content.res.TypedArray;
/*     */ import android.os.Bundle;
/*     */ import android.util.AttributeSet;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.fragment.app.FragmentManager;
/*     */ import androidx.fragment.app.FragmentStatePagerAdapter;
/*     */ import androidx.viewpager.widget.PagerAdapter;
/*     */ import androidx.viewpager.widget.ViewPager;
/*     */ import com.google.android.material.tabs.TabLayout;
/*     */ import com.pdftron.pdf.Annot;
/*     */ import com.pdftron.pdf.Bookmark;
/*     */ import com.pdftron.pdf.PDFDoc;
/*     */ import com.pdftron.pdf.PDFViewCtrl;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.utils.AnalyticsHandlerAdapter;
/*     */ import com.pdftron.pdf.utils.AnalyticsParam;
/*     */ import com.pdftron.pdf.utils.Utils;
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
/*     */ public class BookmarksTabLayout
/*     */   extends CustomFragmentTabLayout
/*     */   implements UserBookmarkDialogFragment.UserBookmarkDialogListener, OutlineDialogFragment.OutlineDialogListener, AnnotationDialogFragment.AnnotationDialogListener
/*     */ {
/*  40 */   private static final String TAG = BookmarksTabLayout.class.getName();
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean sDebug;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TAG_TAB_OUTLINE = "tab-outline";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TAG_TAB_ANNOTATION = "tab-annotation";
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TAG_TAB_BOOKMARK = "tab-bookmark";
/*     */ 
/*     */ 
/*     */   
/*     */   private PDFViewCtrl mPdfViewCtrl;
/*     */ 
/*     */ 
/*     */   
/*     */   private Bookmark mCurrentBookmark;
/*     */ 
/*     */ 
/*     */   
/*     */   private BookmarksTabsListener mBookmarksTabsListener;
/*     */ 
/*     */ 
/*     */   
/*     */   private NavigationListDialogFragment.AnalyticsEventListener mAnalyticsEventListener;
/*     */ 
/*     */ 
/*     */   
/*     */   private ViewPager mViewPager;
/*     */ 
/*     */ 
/*     */   
/*     */   private PagerAdapter mPagerAdapter;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean mTabSelectInitialized;
/*     */ 
/*     */ 
/*     */   
/*     */   private TabLayout.TabLayoutOnPageChangeListener mPageChangeListener;
/*     */ 
/*     */   
/*     */   private int mTabTintColorDialog;
/*     */ 
/*     */   
/*     */   private int mTabTintColorSheet;
/*     */ 
/*     */   
/*     */   private int mTabTintSelectedColorDialog;
/*     */ 
/*     */   
/*     */   private int mTabTintSelectedColorSheet;
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksTabLayout(Context context) {
/* 105 */     this(context, (AttributeSet)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksTabLayout(Context context, AttributeSet attrs) {
/* 112 */     this(context, attrs, R.attr.custom_bookmarks_tab_layout);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BookmarksTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
/* 119 */     super(context, attrs, defStyleAttr);
/* 120 */     init(context, attrs, defStyleAttr, R.style.BookmarksTabLayoutStyle);
/* 121 */     this.mShouldMemorizeTab = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void init(@NonNull Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 126 */     TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.BookmarksTabLayout, defStyleAttr, defStyleRes);
/*     */     try {
/* 128 */       this.mTabTintColorDialog = typedArray.getColor(R.styleable.BookmarksTabLayout_tabTintColorDialog, -1);
/* 129 */       this.mTabTintColorSheet = typedArray.getColor(R.styleable.BookmarksTabLayout_tabTintColorSheet, context.getResources().getColor(R.color.navigation_list_icon_color));
/* 130 */       this.mTabTintSelectedColorDialog = typedArray.getColor(R.styleable.BookmarksTabLayout_tabTintSelectedColorDialog, -1);
/* 131 */       this.mTabTintSelectedColorSheet = typedArray.getColor(R.styleable.BookmarksTabLayout_tabTintSelectedColorSheet, Utils.getAccentColor(context));
/* 132 */       if (this.mTabTintColorDialog == this.mTabTintSelectedColorDialog) {
/* 133 */         this.mTabTintColorDialog = Utils.adjustAlphaColor(this.mTabTintSelectedColorDialog, 0.5F);
/*     */       }
/*     */     } finally {
/* 136 */       typedArray.recycle();
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTabTintColorDialog() {
/* 141 */     return this.mTabTintColorDialog;
/*     */   }
/*     */   
/*     */   public int getTabTintColorSheet() {
/* 145 */     return this.mTabTintColorSheet;
/*     */   }
/*     */   
/*     */   public int getTabTintSelectedColorDialog() {
/* 149 */     return this.mTabTintSelectedColorDialog;
/*     */   }
/*     */   
/*     */   public int getTabTintSelectedColorSheet() {
/* 153 */     return this.mTabTintSelectedColorSheet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setup(Context context, FragmentManager manager, int containerId) {
/* 161 */     throw new IllegalStateException("Must call setup() that takes also a PDFViewCtrl, a Bookmark, and a String");
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
/*     */   public void setup(Context context, FragmentManager manager, int containerId, PDFViewCtrl pdfViewCtrl, Bookmark currentBookmark) {
/* 176 */     super.setup(context, manager, containerId);
/*     */     
/* 178 */     this.mPdfViewCtrl = pdfViewCtrl;
/* 179 */     this.mCurrentBookmark = currentBookmark;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBookmarksTabsListener(BookmarksTabsListener listener) {
/* 188 */     this.mBookmarksTabsListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAnalyticsEventListener(NavigationListDialogFragment.AnalyticsEventListener listener) {
/* 197 */     this.mAnalyticsEventListener = listener;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startFragment(String tag) {}
/*     */ 
/*     */ 
/*     */   
/*     */   private Fragment getTabFragmentAt(int position) {
/*     */     OutlineDialogFragment outlineDialogFragment;
/*     */     AnnotationDialogFragment annotationDialogFragment;
/*     */     UserBookmarkDialogFragment fragment;
/* 210 */     if (this.mPdfViewCtrl == null) {
/* 211 */       return null;
/*     */     }
/*     */     
/* 214 */     CustomFragmentTabLayout.TabInfo tabInfo = this.mTabs.get(position);
/*     */     
/* 216 */     switch (tabInfo.mTag) {
/*     */       
/*     */       case "tab-outline":
/*     */         try {
/* 220 */           outlineDialogFragment = (OutlineDialogFragment)tabInfo.mClass.newInstance();
/* 221 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */           
/* 225 */           outlineDialogFragment = OutlineDialogFragment.newInstance();
/*     */         } 
/* 227 */         outlineDialogFragment.setPdfViewCtrl(this.mPdfViewCtrl)
/* 228 */           .setCurrentBookmark(this.mCurrentBookmark)
/* 229 */           .setArguments(tabInfo.mArgs);
/* 230 */         outlineDialogFragment.setOutlineDialogListener(this);
/* 231 */         tabInfo.mFragment = (Fragment)outlineDialogFragment;
/*     */         break;
/*     */ 
/*     */       
/*     */       case "tab-annotation":
/*     */         try {
/* 237 */           annotationDialogFragment = (AnnotationDialogFragment)tabInfo.mClass.newInstance();
/* 238 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */           
/* 242 */           annotationDialogFragment = AnnotationDialogFragment.newInstance();
/*     */         } 
/* 244 */         annotationDialogFragment.setPdfViewCtrl(this.mPdfViewCtrl)
/* 245 */           .setArguments(tabInfo.mArgs);
/* 246 */         annotationDialogFragment.setAnnotationDialogListener(this);
/* 247 */         tabInfo.mFragment = (Fragment)annotationDialogFragment;
/*     */         break;
/*     */ 
/*     */       
/*     */       case "tab-bookmark":
/*     */         try {
/* 253 */           fragment = (UserBookmarkDialogFragment)tabInfo.mClass.newInstance();
/* 254 */         } catch (Exception e) {
/*     */ 
/*     */ 
/*     */           
/* 258 */           fragment = UserBookmarkDialogFragment.newInstance();
/*     */         } 
/* 260 */         fragment.setPdfViewCtrl(this.mPdfViewCtrl)
/* 261 */           .setArguments(tabInfo.mArgs);
/* 262 */         fragment.setUserBookmarkListener(this);
/* 263 */         tabInfo.mFragment = (Fragment)fragment;
/*     */         break;
/*     */       
/*     */       default:
/* 267 */         tabInfo.mFragment = Fragment.instantiate(this.mContext, tabInfo.mClass
/* 268 */             .getName(), tabInfo.mArgs);
/*     */         break;
/*     */     } 
/* 271 */     if (tabInfo.mFragment instanceof NavigationListDialogFragment) {
/* 272 */       ((NavigationListDialogFragment)tabInfo.mFragment).setAnalyticsEventListener(new NavigationListDialogFragment.AnalyticsEventListener()
/*     */           {
/*     */             public void onEventAction()
/*     */             {
/* 276 */               if (BookmarksTabLayout.this.mAnalyticsEventListener != null) {
/* 277 */                 BookmarksTabLayout.this.mAnalyticsEventListener.onEventAction();
/*     */               }
/*     */             }
/*     */           });
/*     */     }
/* 282 */     return tabInfo.mFragment;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onOutlineClicked(Bookmark parent, Bookmark bookmark) {
/* 287 */     if (this.mBookmarksTabsListener != null) {
/* 288 */       this.mBookmarksTabsListener.onOutlineClicked(parent, bookmark);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onAnnotationClicked(Annot annotation, int pageNum) {
/* 294 */     if (this.mBookmarksTabsListener != null) {
/* 295 */       this.mBookmarksTabsListener.onAnnotationClicked(annotation, pageNum);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onExportAnnotations(PDFDoc outputDoc) {
/* 301 */     if (this.mBookmarksTabsListener != null) {
/* 302 */       this.mBookmarksTabsListener.onExportAnnotations(outputDoc);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUserBookmarkClicked(int pageNum) {
/* 308 */     if (this.mBookmarksTabsListener != null) {
/* 309 */       this.mBookmarksTabsListener.onUserBookmarkClick(pageNum);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onPrepareMenu(Menu menu, Fragment fragment) {
/* 314 */     if (fragment instanceof AnnotationDialogFragment) {
/* 315 */       AnnotationDialogFragment annotationFrag = (AnnotationDialogFragment)fragment;
/* 316 */       annotationFrag.prepareOptionsMenu(menu);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onMenuItemClicked(MenuItem item, Fragment fragment) {
/* 326 */     if (fragment instanceof AnnotationDialogFragment) {
/* 327 */       AnnotationDialogFragment annotationFrag = (AnnotationDialogFragment)fragment;
/* 328 */       return annotationFrag.onOptionsItemSelected(item);
/*     */     } 
/* 330 */     return false;
/*     */   }
/*     */   
/*     */   public static void setDebug(boolean debug) {
/* 334 */     sDebug = debug;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setupWithViewPager(@Nullable ViewPager viewPager) {
/* 339 */     if (viewPager == null) {
/*     */       return;
/*     */     }
/*     */     
/* 343 */     if (this.mPageChangeListener != null && this.mViewPager != null) {
/* 344 */       this.mViewPager.removeOnPageChangeListener((ViewPager.OnPageChangeListener)this.mPageChangeListener);
/*     */     }
/*     */     
/* 347 */     this.mViewPager = viewPager;
/* 348 */     if (this.mPagerAdapter == null) {
/* 349 */       this.mPagerAdapter = (PagerAdapter)new BookmarkViewPagerAdapter(this.mFragmentManager);
/*     */     }
/* 351 */     this.mViewPager.setAdapter(this.mPagerAdapter);
/*     */     
/* 353 */     if (this.mPageChangeListener == null) {
/* 354 */       this.mPageChangeListener = new TabLayout.TabLayoutOnPageChangeListener(this);
/*     */     }
/* 356 */     this.mViewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener)this.mPageChangeListener);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onTabSelected(TabLayout.Tab tab) {
/* 362 */     super.onTabSelected(tab);
/* 363 */     if (this.mViewPager != null) {
/* 364 */       this.mViewPager.setCurrentItem(tab.getPosition());
/*     */     }
/* 366 */     if (this.mTabSelectInitialized) {
/*     */       
/* 368 */       AnalyticsHandlerAdapter.getInstance().sendEvent(33, 
/* 369 */           AnalyticsParam.navigationListsTabParam(getNavigationId(tab)));
/*     */     } else {
/* 371 */       this.mTabSelectInitialized = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addTab(@NonNull TabLayout.Tab tab, Class<?> _class, Bundle args) {
/* 377 */     super.addTab(tab, _class, args);
/* 378 */     if (this.mPagerAdapter != null)
/* 379 */       this.mPagerAdapter.notifyDataSetChanged(); 
/*     */   } public static interface BookmarksTabsListener {
/*     */     void onUserBookmarkClick(int param1Int); void onOutlineClicked(Bookmark param1Bookmark1, Bookmark param1Bookmark2); void onAnnotationClicked(Annot param1Annot, int param1Int);
/*     */     void onExportAnnotations(PDFDoc param1PDFDoc); }
/*     */   public static int getNavigationId(@Nullable TabLayout.Tab tab) {
/* 384 */     if (tab != null && tab.getTag() instanceof String) {
/* 385 */       String tag = (String)tab.getTag();
/* 386 */       switch (tag) {
/*     */         case "tab-outline":
/* 388 */           return 1;
/*     */         case "tab-annotation":
/* 390 */           return 3;
/*     */         case "tab-bookmark":
/* 392 */           return 2;
/*     */       } 
/*     */     } 
/* 395 */     return 0;
/*     */   }
/*     */   
/*     */   private class BookmarkViewPagerAdapter
/*     */     extends FragmentStatePagerAdapter {
/*     */     BookmarkViewPagerAdapter(FragmentManager fm) {
/* 401 */       super(fm);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getCount() {
/* 406 */       return BookmarksTabLayout.this.mTabs.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public Fragment getItem(int position) {
/* 411 */       return BookmarksTabLayout.this.getTabFragmentAt(position);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\controls\BookmarksTabLayout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */