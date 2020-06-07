/*     */ package com.pdftron.pdf.dialog.menueditor;
/*     */ 
/*     */ import android.content.DialogInterface;
/*     */ import android.os.Bundle;
/*     */ import android.view.LayoutInflater;
/*     */ import android.view.MenuItem;
/*     */ import android.view.View;
/*     */ import android.view.ViewGroup;
/*     */ import androidx.annotation.NonNull;
/*     */ import androidx.annotation.Nullable;
/*     */ import androidx.appcompat.widget.Toolbar;
/*     */ import androidx.fragment.app.Fragment;
/*     */ import androidx.lifecycle.Observer;
/*     */ import androidx.lifecycle.ViewModelProviders;
/*     */ import androidx.recyclerview.widget.GridLayoutManager;
/*     */ import androidx.recyclerview.widget.ItemTouchHelper;
/*     */ import androidx.recyclerview.widget.RecyclerView;
/*     */ import co.paulburke.android.itemtouchhelperdemo.helper.SimpleItemTouchHelperCallback;
/*     */ import com.pdftron.pdf.controls.CustomSizeDialogFragment;
/*     */ import com.pdftron.pdf.dialog.menueditor.model.MenuEditorItem;
/*     */ import com.pdftron.pdf.tools.R;
/*     */ import com.pdftron.pdf.widget.recyclerview.ItemClickHelper;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MenuEditorDialogFragment
/*     */   extends CustomSizeDialogFragment
/*     */ {
/*  34 */   public static final String TAG = MenuEditorDialogFragment.class.getName();
/*     */   
/*     */   private RecyclerView mRecyclerView;
/*     */   
/*     */   private MenuEditorAdapter mAdapter;
/*     */   
/*     */   private MenuEditorViewModel mViewModel;
/*     */   private MenuEditorDialogFragmentListener mListener;
/*     */   
/*     */   public static MenuEditorDialogFragment newInstance() {
/*  44 */     return new MenuEditorDialogFragment();
/*     */   }
/*     */   
/*     */   public void setMenuEditorDialogFragmentListener(MenuEditorDialogFragmentListener listener) {
/*  48 */     this.mListener = listener;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onCreate(@Nullable Bundle savedInstanceState) {
/*  53 */     super.onCreate(savedInstanceState);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
/*  59 */     View view = inflater.inflate(R.layout.fragment_menu_editor_dialog, container);
/*     */     
/*  61 */     Toolbar toolbar = (Toolbar)view.findViewById(R.id.toolbar);
/*  62 */     toolbar.setTitle(R.string.action_edit_menu);
/*  63 */     toolbar.inflateMenu(R.menu.fragment_menu_editor);
/*  64 */     toolbar.setNavigationOnClickListener(new View.OnClickListener()
/*     */         {
/*     */           public void onClick(View v) {
/*  67 */             MenuEditorDialogFragment.this.dismiss();
/*     */           }
/*     */         });
/*  70 */     toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
/*     */         {
/*     */           public boolean onMenuItemClick(MenuItem item) {
/*  73 */             if (item.getItemId() == R.id.action_reset) {
/*  74 */               MenuEditorDialogFragment.this.mViewModel.onReset();
/*  75 */               MenuEditorDialogFragment.this.mViewModel.getItemsLiveData().observe(MenuEditorDialogFragment.this.getViewLifecycleOwner(), new Observer<ArrayList<MenuEditorItem>>()
/*     */                   {
/*     */                     public void onChanged(ArrayList<MenuEditorItem> menuEditorItems)
/*     */                     {
/*  79 */                       if (MenuEditorDialogFragment.this.mAdapter != null) {
/*  80 */                         MenuEditorDialogFragment.this.mAdapter.setData(menuEditorItems);
/*     */                       }
/*  82 */                       MenuEditorDialogFragment.this.mViewModel.getItemsLiveData().removeObserver(this);
/*     */                     }
/*     */                   });
/*  85 */               return true;
/*     */             } 
/*  87 */             return false;
/*     */           }
/*     */         });
/*     */     
/*  91 */     this.mRecyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
/*     */ 
/*     */     
/*  94 */     final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
/*  95 */     layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup()
/*     */         {
/*     */           public int getSpanSize(int position) {
/*  98 */             switch (MenuEditorDialogFragment.this.mAdapter.getItemViewType(position)) {
/*     */               case 1:
/* 100 */                 return layoutManager.getSpanCount();
/*     */             } 
/*     */             
/* 103 */             return 1;
/*     */           }
/*     */         });
/*     */     
/* 107 */     this.mRecyclerView.setLayoutManager((RecyclerView.LayoutManager)layoutManager);
/*     */ 
/*     */     
/* 110 */     this.mAdapter = new MenuEditorAdapter();
/* 111 */     this.mRecyclerView.setAdapter(this.mAdapter);
/*     */     
/* 113 */     ItemClickHelper itemClickHelper = new ItemClickHelper();
/* 114 */     itemClickHelper.attachToRecyclerView(this.mRecyclerView);
/*     */     
/* 116 */     SimpleItemTouchHelperCallback simpleItemTouchHelperCallback = new SimpleItemTouchHelperCallback(this.mAdapter, 3, false, false);
/* 117 */     simpleItemTouchHelperCallback.setAllowDragAmongSections(true);
/* 118 */     final ItemTouchHelper itemTouchHelper = new ItemTouchHelper((ItemTouchHelper.Callback)simpleItemTouchHelperCallback);
/* 119 */     itemTouchHelper.attachToRecyclerView(this.mRecyclerView);
/*     */     
/* 121 */     itemClickHelper.setOnItemLongClickListener(new ItemClickHelper.OnItemLongClickListener()
/*     */         {
/*     */           public boolean onItemLongClick(RecyclerView recyclerView, View view, final int position, long id) {
/* 124 */             MenuEditorDialogFragment.this.mRecyclerView.post(new Runnable()
/*     */                 {
/*     */                   public void run() {
/* 127 */                     if (!MenuEditorDialogFragment.this.mAdapter.isHeader(position)) {
/* 128 */                       RecyclerView.ViewHolder holder = MenuEditorDialogFragment.this.mRecyclerView.findViewHolderForAdapterPosition(position);
/* 129 */                       if (holder != null) {
/* 130 */                         MenuEditorDialogFragment.this.mAdapter.setDragging(true);
/* 131 */                         MenuEditorDialogFragment.this.mAdapter.notifyHeadersChanged();
/* 132 */                         itemTouchHelper.startDrag(holder);
/*     */                       } 
/*     */                     } 
/*     */                   }
/*     */                 });
/* 137 */             return true;
/*     */           }
/*     */         });
/*     */     
/* 141 */     return view;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
/* 146 */     super.onViewCreated(view, savedInstanceState);
/*     */     
/* 148 */     Fragment parentFragment = getParentFragment();
/* 149 */     if (parentFragment == null) {
/* 150 */       throw new RuntimeException("This fragment should run as a child fragment of a containing parent fragment.");
/*     */     }
/*     */     
/* 153 */     this.mViewModel = (MenuEditorViewModel)ViewModelProviders.of(parentFragment).get(MenuEditorViewModel.class);
/* 154 */     this.mAdapter.setViewModel(this.mViewModel);
/*     */     
/* 156 */     this.mViewModel.getItemsLiveData().observe(getViewLifecycleOwner(), new Observer<ArrayList<MenuEditorItem>>()
/*     */         {
/*     */           public void onChanged(ArrayList<MenuEditorItem> menuEditorItems)
/*     */           {
/* 160 */             if (MenuEditorDialogFragment.this.mAdapter != null) {
/* 161 */               MenuEditorDialogFragment.this.mAdapter.setData(menuEditorItems);
/*     */             }
/* 163 */             MenuEditorDialogFragment.this.mViewModel.getItemsLiveData().removeObserver(this);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDismiss(@NonNull DialogInterface dialog) {
/* 170 */     super.onDismiss(dialog);
/*     */     
/* 172 */     if (this.mListener != null)
/* 173 */       this.mListener.onMenuEditorDialogDismiss(); 
/*     */   }
/*     */   
/*     */   public static interface MenuEditorDialogFragmentListener {
/*     */     void onMenuEditorDialogDismiss();
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\dialog\menueditor\MenuEditorDialogFragment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */