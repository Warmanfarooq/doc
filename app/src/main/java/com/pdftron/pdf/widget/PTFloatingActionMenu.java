/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.TypedArray;
/*    */ import android.util.AttributeSet;
/*    */ import android.widget.ImageView;
/*    */ import com.github.clans.fab.FloatingActionMenu;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ public class PTFloatingActionMenu
/*    */   extends FloatingActionMenu {
/*    */   public PTFloatingActionMenu(Context context) {
/* 14 */     this(context, null);
/*    */   }
/*    */   
/*    */   public PTFloatingActionMenu(Context context, AttributeSet attrs) {
/* 18 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public PTFloatingActionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
/* 22 */     super(context, attrs, defStyleAttr);
/* 23 */     init(context, attrs, defStyleAttr);
/*    */   }
/*    */   
/*    */   private void init(Context context, AttributeSet attrs, int defStyleAttr) {
/* 27 */     TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PTFloatingActionMenu, defStyleAttr, 0);
/*    */     try {
/* 29 */       ImageView iconView = getMenuIconView();
/* 30 */       if (iconView != null && 
/* 31 */         attr.hasValue(R.styleable.PTFloatingActionMenu_android_tint)) {
/* 32 */         int tintColor = attr.getColor(R.styleable.PTFloatingActionMenu_android_tint, 
/* 33 */             Utils.getThemeAttrColor(context, 16842806));
/* 34 */         iconView.setColorFilter(tintColor);
/*    */       } 
/*    */     } finally {
/*    */       
/* 38 */       attr.recycle();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\PTFloatingActionMenu.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */