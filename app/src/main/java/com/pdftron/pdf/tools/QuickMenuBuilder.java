/*     */ package com.pdftron.pdf.tools;
/*     */ 
/*     */ import android.content.ComponentName;
/*     */ import android.content.Context;
/*     */ import android.content.Intent;
/*     */ import android.graphics.drawable.Drawable;
/*     */ import android.view.KeyEvent;
/*     */ import android.view.Menu;
/*     */ import android.view.MenuItem;
/*     */ import android.view.SubMenu;
/*     */ import android.view.View;
/*     */ import androidx.annotation.RestrictTo;
/*     */ import com.pdftron.pdf.config.ToolConfig;
/*     */ import java.util.ArrayList;
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
/*     */ public class QuickMenuBuilder
/*     */   implements Menu, SubMenu
/*     */ {
/*     */   private QuickMenuItem mParentItem;
/*     */   private ArrayList<QuickMenuItem> mQuickMenuItems;
/*     */   private Context mContext;
/*     */   private ToolManager mToolManager;
/*     */   private boolean mAnnotationPermission = true;
/*     */   
/*     */   public QuickMenuBuilder(Context context, ToolManager toolManager, boolean annotationPermission) {
/*  36 */     this.mContext = context;
/*  37 */     this.mQuickMenuItems = new ArrayList<>();
/*  38 */     this.mToolManager = toolManager;
/*  39 */     this.mAnnotationPermission = annotationPermission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getDisplayModeByGroupId(int groupId) {
/*  48 */     if (groupId == R.id.qm_overflow_row_group)
/*  49 */       return 2; 
/*  50 */     if (groupId == R.id.qm_second_row_group) {
/*  51 */       return 1;
/*     */     }
/*  53 */     return 0;
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
/*     */   public MenuItem add(CharSequence title) {
/*  67 */     QuickMenuItem result = new QuickMenuItem(this.mContext, title.toString());
/*  68 */     this.mQuickMenuItems.add(result);
/*  69 */     return result;
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
/*     */   public MenuItem add(int titleRes) {
/*  81 */     return add(this.mContext.getString(titleRes));
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
/*     */ 
/*     */   
/*     */   public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
/* 101 */     int displayMode = getDisplayModeByGroupId(groupId);
/* 102 */     QuickMenuItem result = new QuickMenuItem(this.mContext, title.toString(), displayMode);
/* 103 */     if (isQuickMenuItemValid(itemId)) {
/* 104 */       result.setItemId(itemId);
/* 105 */       result.setOrder(order);
/* 106 */       this.mQuickMenuItems.add(result);
/*     */     } 
/* 108 */     return result;
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
/*     */ 
/*     */   
/*     */   public MenuItem add(int groupId, int itemId, int order, int titleRes) {
/* 128 */     return add(groupId, itemId, order, this.mContext.getString(titleRes));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isQuickMenuItemValid(int itemId) {
/*     */     ToolManager.ToolMode toolMode;
/* 139 */     if (itemId != -1 && (toolMode = ToolConfig.getInstance().getToolModeByQMItemId(itemId)) != null && 
/* 140 */       this.mToolManager.isToolModeDisabled(toolMode)) {
/* 141 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 146 */     boolean shouldHide = (!this.mAnnotationPermission || this.mToolManager.isReadOnly());
/* 147 */     if (shouldHide && itemId != -1 && ToolConfig.getInstance().isHideQMItem(itemId)) {
/* 148 */       return false;
/*     */     }
/*     */     
/* 151 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeItem(int id) {
/* 162 */     for (QuickMenuItem item : this.mQuickMenuItems) {
/* 163 */       if (item.getItemId() == id) {
/* 164 */         this.mQuickMenuItems.remove(item);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeGroup(int groupId) {
/* 178 */     int displayMode = getDisplayModeByGroupId(groupId);
/* 179 */     ListIterator<QuickMenuItem> iterator = this.mQuickMenuItems.listIterator();
/* 180 */     while (iterator.hasNext()) {
/* 181 */       QuickMenuItem item = iterator.next();
/* 182 */       if (item.getDisplayMode() == displayMode) {
/* 183 */         iterator.remove();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear() {
/* 194 */     this.mQuickMenuItems.clear();
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
/*     */   public MenuItem findItem(int id) {
/* 207 */     for (QuickMenuItem item : this.mQuickMenuItems) {
/* 208 */       if (item.getItemId() == id) {
/* 209 */         return item;
/*     */       }
/*     */     } 
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 223 */     return this.mQuickMenuItems.size();
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
/*     */   public MenuItem getItem(int index) {
/* 236 */     return this.mQuickMenuItems.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 244 */     clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList<QuickMenuItem> getMenuItems() {
/* 253 */     ListIterator<QuickMenuItem> listIterator = this.mQuickMenuItems.listIterator();
/* 254 */     while (listIterator.hasNext()) {
/* 255 */       QuickMenuItem item = listIterator.next();
/* 256 */       if (!isQuickMenuItemValid(item.getItemId())) {
/* 257 */         listIterator.remove();
/*     */         
/*     */         continue;
/*     */       } 
/* 261 */       if (item.hasSubMenu()) {
/* 262 */         QuickMenuBuilder subMenu = (QuickMenuBuilder)item.getSubMenu();
/* 263 */         ArrayList<QuickMenuItem> subItems = subMenu.getMenuItems();
/* 264 */         if (subItems.isEmpty()) {
/* 265 */           listIterator.remove();
/*     */         }
/*     */       } 
/*     */     } 
/* 269 */     return this.mQuickMenuItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public void setGroupCheckable(int group, boolean checkable, boolean exclusive) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public void setGroupVisible(int group, boolean visible) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public void setGroupEnabled(int group, boolean enabled) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean hasVisibleItems() {
/* 297 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
/* 304 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean isShortcutKey(int keyCode, KeyEvent event) {
/* 311 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public boolean performIdentifierAction(int id, int flags) {
/* 318 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public void setQwertyMode(boolean isQwerty) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubMenu addSubMenu(CharSequence title) {
/* 338 */     QuickMenuItem item = (QuickMenuItem)add(title);
/* 339 */     item.initSubMenu(this.mToolManager, this.mAnnotationPermission);
/* 340 */     return item.getSubMenu();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubMenu addSubMenu(int titleRes) {
/* 351 */     QuickMenuItem item = (QuickMenuItem)add(titleRes);
/* 352 */     item.initSubMenu(this.mToolManager, this.mAnnotationPermission);
/* 353 */     return item.getSubMenu();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SubMenu addSubMenu(int groupId, int itemId, int order, CharSequence title) {
/* 379 */     QuickMenuItem item = (QuickMenuItem)add(groupId, itemId, order, title);
/* 380 */     item.initSubMenu(this.mToolManager, this.mAnnotationPermission);
/* 381 */     return item.getSubMenu();
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
/*     */   public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
/* 398 */     QuickMenuItem item = (QuickMenuItem)add(groupId, itemId, order, titleRes);
/* 399 */     item.initSubMenu(this.mToolManager, this.mAnnotationPermission);
/* 400 */     return item.getSubMenu();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @RestrictTo({RestrictTo.Scope.SUBCLASSES})
/*     */   public int addIntentOptions(int groupId, int itemId, int order, ComponentName caller, Intent[] specifics, Intent intent, int flags, MenuItem[] outSpecificItems) {
/* 407 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setHeaderTitle(int titleRes) {
/* 412 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setHeaderTitle(CharSequence title) {
/* 417 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setHeaderIcon(int iconRes) {
/* 422 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setHeaderIcon(Drawable icon) {
/* 427 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setHeaderView(View view) {
/* 432 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearHeader() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public SubMenu setIcon(int iconRes) {
/* 442 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SubMenu setIcon(Drawable icon) {
/* 447 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MenuItem getItem() {
/* 458 */     return this.mParentItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setParentMenuItem(QuickMenuItem item) {
/* 466 */     this.mParentItem = item;
/*     */   }
/*     */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\tools\QuickMenuBuilder.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */