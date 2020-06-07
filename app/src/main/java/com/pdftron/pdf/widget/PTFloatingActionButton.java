/*    */ package com.pdftron.pdf.widget;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.content.res.TypedArray;
/*    */ import android.graphics.PorterDuff;
/*    */ import android.graphics.drawable.Drawable;
/*    */ import android.util.AttributeSet;
/*    */ import com.github.clans.fab.FloatingActionButton;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.utils.Utils;
/*    */ 
/*    */ public class PTFloatingActionButton
/*    */   extends FloatingActionButton {
/*    */   public PTFloatingActionButton(Context context) {
/* 15 */     this(context, (AttributeSet)null);
/*    */   }
/*    */   
/*    */   public PTFloatingActionButton(Context context, AttributeSet attrs) {
/* 19 */     this(context, attrs, 0);
/*    */   }
/*    */   
/*    */   public PTFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr) {
/* 23 */     super(context, attrs, defStyleAttr);
/* 24 */     init(context, attrs, defStyleAttr);
/*    */   }
/*    */   
/*    */   public PTFloatingActionButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
/* 28 */     super(context, attrs, defStyleAttr, defStyleRes);
/* 29 */     init(context, attrs, defStyleAttr);
/*    */   }
/*    */   
/*    */   private void init(Context context, AttributeSet attrs, int defStyleAttr) {
/* 33 */     TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.PTFloatingActionButton, defStyleAttr, 0);
/*    */     try {
/* 35 */       Drawable d = null;
/* 36 */       if (attr.hasValue(R.styleable.PTFloatingActionButton_srcCompat)) {
/* 37 */         int id = attr.getResourceId(R.styleable.PTFloatingActionButton_srcCompat, -1);
/* 38 */         if (id != -1) {
/* 39 */           d = context.getResources().getDrawable(id);
/*    */         }
/*    */       } 
/* 42 */       if (attr.hasValue(R.styleable.PTFloatingActionButton_android_tint)) {
/* 43 */         int tintColor = attr.getColor(R.styleable.PTFloatingActionButton_android_tint, 
/* 44 */             Utils.getThemeAttrColor(context, 16842806));
/* 45 */         if (d != null) {
/* 46 */           d = d.mutate();
/* 47 */           d.setColorFilter(tintColor, PorterDuff.Mode.SRC_IN);
/*    */         } else {
/* 49 */           setColorFilter(tintColor);
/*    */         } 
/*    */       } 
/* 52 */       if (d != null) {
/* 53 */         setImageDrawable(d);
/*    */       }
/*    */     } finally {
/* 56 */       attr.recycle();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\PTFloatingActionButton.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */