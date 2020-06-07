/*    */ package com.pdftron.pdf.widget.richtext;
/*    */ 
/*    */ import android.content.Context;
/*    */ import android.graphics.drawable.ColorDrawable;
/*    */ import android.graphics.drawable.Drawable;
/*    */ import android.view.LayoutInflater;
/*    */ import android.view.MotionEvent;
/*    */ import android.view.View;
/*    */ import android.widget.PopupWindow;
/*    */ import com.pdftron.pdf.tools.R;
/*    */ import com.pdftron.pdf.tools.ToolManager;
/*    */ import java.util.List;
/*    */ import jp.wasabeef.richeditor.RichEditor;
/*    */ 
/*    */ 
/*    */ public class RCContainer
/*    */   extends PopupWindow
/*    */ {
/*    */   private RCToolbar mRCToolbar;
/*    */   private View mParentRootView;
/*    */   
/*    */   public RCContainer(Context context) {
/* 23 */     super(context);
/*    */     
/* 25 */     View root = LayoutInflater.from(context).inflate(R.layout.rc_container_popupwindow, null, false);
/* 26 */     setContentView(root);
/*    */     
/* 28 */     setSoftInputMode(16);
/* 29 */     setInputMethodMode(1);
/*    */     
/* 31 */     setWidth(-1);
/* 32 */     setHeight(-1);
/*    */     
/* 34 */     setBackgroundDrawable((Drawable)new ColorDrawable(0));
/*    */     
/* 36 */     this.mRCToolbar = (RCToolbar)root.findViewById(R.id.rc_toolbar);
/*    */     
/* 38 */     View placeHolder = root.findViewById(R.id.placeholder);
/* 39 */     placeHolder.setOnTouchListener(new View.OnTouchListener()
/*    */         {
/*    */           public boolean onTouch(View v, MotionEvent event) {
/* 42 */             if (RCContainer.this.mParentRootView != null) {
/* 43 */               return RCContainer.this.mParentRootView.dispatchTouchEvent(event);
/*    */             }
/* 45 */             return false;
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public void setup(View rootView, ToolManager toolManager) {
/* 51 */     this.mParentRootView = rootView;
/* 52 */     this.mRCToolbar.setToolManager(toolManager);
/*    */   }
/*    */   
/*    */   public void updateToolbar(List<RichEditor.Type> types) {
/* 56 */     this.mRCToolbar.updateDecorationTypes(types);
/*    */   }
/*    */ 
/*    */   
/*    */   public void dismiss() {
/* 61 */     super.dismiss();
/* 62 */     this.mRCToolbar.deselectAll();
/*    */   }
/*    */ }


/* Location:              D:\ppt\library\pdftron.tool\jars\classes.jar!\com\pdftron\pdf\widget\richtext\RCContainer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */